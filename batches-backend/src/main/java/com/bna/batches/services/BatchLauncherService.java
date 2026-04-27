package com.bna.batches.services;

import com.bna.batches.interfaces.request.BatchRequest;
import com.bna.batches.interfaces.response.AgenceProgressMessage;
import com.bna.batches.interfaces.response.BatchExecutionDto;
import com.bna.batches.interfaces.response.BatchStatusMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BatchLauncherService {

    private final WebSocketNotifService wsNotif;
    private final AuditService auditService;

    // In-memory execution registry (last 100)
    private static final int MAX_HISTORY = 100;
    private final Deque<BatchExecutionDto> executionHistory = new ConcurrentLinkedDeque<>();

    // Track running jobs to allow stop
    private final ConcurrentHashMap<String, CompletableFuture<Void>> runningJobs = new ConcurrentHashMap<>();

    // Simulated agencies per instrument
    private static final Map<String, List<String>> AGENCIES = Map.of(
            "CHEQUE",      List.of("101", "205", "312", "418", "525", "630", "745", "850"),
            "EFFET",       List.of("101", "205", "312", "418", "525", "630"),
            "PRELEVEMENT", List.of("101", "205", "312", "418", "525", "630", "745", "850", "912", "023")
    );

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BatchExecutionDto launch(BatchRequest request, String username, String ip) {
        String execId = "EXEC-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "-" + String.format("%03d", (int)(Math.random() * 999) + 1);

        List<String> agencies = AGENCIES.getOrDefault(request.getInstrument().toUpperCase(),
                List.of("101", "205", "312"));

        BatchExecutionDto execution = new BatchExecutionDto(
                execId,
                request.getInstrument().toUpperCase(),
                request.getPhase().toUpperCase(),
                "STARTED",
                username,
                LocalDateTime.now(),
                null,
                request.isDryRun(),
                agencies.size(),
                0, 0,
                null,
                new ArrayList<>()
        );

        // Initialize all agencies as EN_ATTENTE
        String today = LocalDate.now().format(DATE_FMT);
        for (String agence : agencies) {
            execution.getAgenceDetails().add(new AgenceProgressMessage(
                    execId, agence, "EN_ATTENTE", today,
                    execution.getInstrument(), execution.getPhase(), 0, null));
        }

        addToHistory(execution);
        auditService.log(username, "BATCH_LAUNCH",
                request.getInstrument() + "/" + request.getPhase() + (request.isDryRun() ? " [DRY RUN]" : ""),
                ip, "STARTED");

        // Push initial status
        wsNotif.pushBatchStatus(buildStatus(execution));

        // Run async
        CompletableFuture<Void> job = CompletableFuture.runAsync(() ->
                processAgencies(execution, agencies, today, request.getRetryMax()));

        runningJobs.put(execId, job);
        job.whenComplete((v, ex) -> {
            runningJobs.remove(execId);
            if (ex != null) {
                execution.setStatus("FAILED");
                execution.setEndMessage("Erreur inattendue: " + ex.getMessage());
            }
            execution.setEndTime(LocalDateTime.now());
            auditService.log(username, "BATCH_END",
                    execId + " → " + execution.getStatus(), ip, execution.getStatus());
        });

        return execution;
    }

    private void processAgencies(BatchExecutionDto exec, List<String> agencies,
                                  String today, int retryMax) {
        exec.setStatus("RUNNING");
        wsNotif.pushBatchStatus(buildStatus(exec));

        for (int i = 0; i < agencies.size(); i++) {
            // Check if stopped
            if ("STOPPED".equals(exec.getStatus())) break;

            String agence = agencies.get(i);
            int attempt = 0;
            boolean success = false;

            while (attempt <= Math.max(retryMax, 0) && !success) {
                attempt++;
                // Update to EN_COURS
                updateAgence(exec, agence, "EN_COURS", today, null);

                try {
                    // Simulate processing time (800ms - 2500ms)
                    long delay = 800 + (long)(Math.random() * 1700);
                    Thread.sleep(delay);

                    // Simulate ~15% error rate on first attempt
                    boolean hasError = (Math.random() < 0.15) && attempt == 1 && retryMax > 0;
                    if (hasError) throw new RuntimeException("Erreur fichier agence " + agence);

                    // Success
                    updateAgence(exec, agence, "TERMINE", today, null);
                    exec.setDoneAgences(exec.getDoneAgences() + 1);
                    success = true;

                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    if (attempt > retryMax) {
                        updateAgence(exec, agence, "ERREUR", today, e.getMessage());
                        exec.setErrorAgences(exec.getErrorAgences() + 1);
                        log.warn("Agency {} FAILED after {} attempt(s): {}", agence, attempt, e.getMessage());
                    } else {
                        log.info("Agency {} attempt {}/{} failed, retrying...", agence, attempt, retryMax + 1);
                        try { Thread.sleep(500); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                    }
                }
            }

            // Push global status after each agency
            int progress = (int) (((double)(i + 1) / agencies.size()) * 100);
            exec.setStatus("RUNNING");
            wsNotif.pushBatchStatus(buildStatus(exec));
        }

        // Final status
        if (!"STOPPED".equals(exec.getStatus())) {
            boolean hasErrors = exec.getErrorAgences() > 0;
            exec.setStatus(hasErrors ? "COMPLETED_WITH_ERRORS" : "COMPLETED");
            exec.setEndMessage(hasErrors
                    ? exec.getErrorAgences() + " agence(s) en erreur sur " + exec.getTotalAgences()
                    : "Traitement terminé avec succès — " + exec.getTotalAgences() + " agence(s)");
        }
        exec.setEndTime(LocalDateTime.now());
        wsNotif.pushBatchStatus(buildStatus(exec));
        log.info("Batch {} finished: status={}", exec.getExecutionId(), exec.getStatus());
    }

    private void updateAgence(BatchExecutionDto exec, String agence,
                               String etat, String date, String errorMsg) {
        exec.getAgenceDetails().stream()
                .filter(a -> a.getStructure().equals(agence))
                .findFirst()
                .ifPresent(a -> {
                    a.setEtat(etat);
                    a.setErrorMessage(errorMsg);
                    int doneCount = (int) exec.getAgenceDetails().stream()
                            .filter(x -> "TERMINE".equals(x.getEtat()) || "ERREUR".equals(x.getEtat()))
                            .count();
                    a.setProgressPercent((int)((double) doneCount / exec.getTotalAgences() * 100));
                    wsNotif.pushAgenceProgress(new AgenceProgressMessage(
                            exec.getExecutionId(), agence, etat, date,
                            exec.getInstrument(), exec.getPhase(),
                            a.getProgressPercent(), errorMsg));
                });
    }

    public boolean stop(String executionId) {
        BatchExecutionDto exec = findById(executionId);
        if (exec != null && "RUNNING".equals(exec.getStatus())) {
            exec.setStatus("STOPPED");
            exec.setEndMessage("Arrêté par l'opérateur");
            exec.setEndTime(LocalDateTime.now());
            CompletableFuture<Void> job = runningJobs.remove(executionId);
            if (job != null) job.cancel(true);
            wsNotif.pushBatchStatus(buildStatus(exec));
            return true;
        }
        return false;
    }

    public BatchExecutionDto findById(String executionId) {
        return executionHistory.stream()
                .filter(e -> e.getExecutionId().equals(executionId))
                .findFirst().orElse(null);
    }

    public List<BatchExecutionDto> getHistory() {
        return executionHistory.stream().collect(Collectors.toList());
    }

    public List<BatchExecutionDto> getHistoryByInstrument(String instrument) {
        return executionHistory.stream()
                .filter(e -> instrument.equalsIgnoreCase(e.getInstrument()))
                .collect(Collectors.toList());
    }

    private BatchStatusMessage buildStatus(BatchExecutionDto exec) {
        int progress = exec.getTotalAgences() == 0 ? 0
                : (int)((double)(exec.getDoneAgences() + exec.getErrorAgences()) / exec.getTotalAgences() * 100);
        return new BatchStatusMessage(
                exec.getExecutionId(),
                exec.getStatus(),
                exec.getInstrument(),
                exec.getPhase(),
                progress,
                exec.getTotalAgences(),
                exec.getDoneAgences(),
                exec.getErrorAgences(),
                exec.getEndMessage()
        );
    }

    private void addToHistory(BatchExecutionDto exec) {
        executionHistory.addFirst(exec);
        if (executionHistory.size() > MAX_HISTORY) executionHistory.pollLast();
    }
}
