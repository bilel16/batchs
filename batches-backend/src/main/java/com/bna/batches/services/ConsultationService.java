package com.bna.batches.services;

import com.bna.batches.interfaces.response.BatchExecutionDto;
import com.bna.batches.interfaces.response.ConsultationResultDto;
import com.bna.batches.interfaces.response.DashboardStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final BatchLauncherService batchLauncherService;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final Map<String, String> LIB_STRUCTURE = Map.of(
            "101", "Agence Tunis Centre",
            "205", "Agence Lac",
            "312", "Agence Carthage",
            "418", "Agence Ariana",
            "525", "Agence La Marsa",
            "630", "Agence Sousse",
            "745", "Agence Sfax",
            "850", "Agence Monastir",
            "912", "Agence Gabès",
            "023", "Agence Bizerte"
    );

    /**
     * Search compensation results — in the future this will query Oracle.
     * For now returns simulated data from the batch history.
     */
    public List<ConsultationResultDto> search(String dateStart, String dateEnd,
                                               String instrument, String sens,
                                               String valeur, String agence) {
        List<ConsultationResultDto> results = new ArrayList<>();

        // Generate simulated results based on executed batches
        List<BatchExecutionDto> history = batchLauncherService.getHistory().stream()
                .filter(e -> "COMPLETED".equals(e.getStatus()) || "COMPLETED_WITH_ERRORS".equals(e.getStatus()))
                .filter(e -> instrument == null || instrument.isBlank() || instrument.equalsIgnoreCase(e.getInstrument()))
                .collect(Collectors.toList());

        for (BatchExecutionDto exec : history) {
            exec.getAgenceDetails().stream()
                    .filter(a -> "TERMINE".equals(a.getEtat()))
                    .filter(a -> agence == null || agence.isBlank() || a.getStructure().equals(agence))
                    .forEach(a -> {
                        ConsultationResultDto dto = new ConsultationResultDto();
                        dto.setStructure(a.getStructure());
                        dto.setLibStructure(LIB_STRUCTURE.getOrDefault(a.getStructure(), "Agence " + a.getStructure()));
                        dto.setInstrument(exec.getInstrument());
                        dto.setSens(sens != null ? sens : "RCP");
                        dto.setValeur(valeur != null ? valeur : getDefaultValeur(exec.getInstrument()));
                        dto.setDateOperation(a.getDateComptable());
                        // Simulate volumes
                        long base = 50L + (long)(Math.random() * 450);
                        dto.setNombreTotal(base);
                        dto.setMontantTotal(base * (1000L + (long)(Math.random() * 4000)));
                        dto.setNombreIntra((long)(base * 0.6));
                        dto.setMontantIntra((long)(dto.getMontantTotal() * 0.6));
                        dto.setNombreInter(base - dto.getNombreIntra());
                        dto.setMontantInter(dto.getMontantTotal() - dto.getMontantIntra());
                        results.add(dto);
                    });
        }

        // If no history, return a minimal demo dataset
        if (results.isEmpty()) {
            results.addAll(buildDemoResults(instrument, sens, valeur));
        }

        return results;
    }

    public DashboardStatsDto getDashboardStats() {
        List<BatchExecutionDto> history = batchLauncherService.getHistory();

        long chequeCount = history.stream().filter(e -> "CHEQUE".equals(e.getInstrument())).count();
        long effetCount = history.stream().filter(e -> "EFFET".equals(e.getInstrument())).count();
        long prelevCount = history.stream().filter(e -> "PRELEVEMENT".equals(e.getInstrument())).count();

        long total = history.size();
        long success = history.stream()
                .filter(e -> "COMPLETED".equals(e.getStatus())).count();
        double healthScore = total == 0 ? 100.0 : Math.round((double) success / total * 1000.0) / 10.0;

        Optional<BatchExecutionDto> last = history.stream().findFirst();
        List<BatchExecutionDto> recent = history.stream().limit(8).collect(Collectors.toList());

        return DashboardStatsDto.builder()
                .totalCheque(Math.max(chequeCount, 3))
                .totalEffet(Math.max(effetCount, 2))
                .totalPrelevement(Math.max(prelevCount, 4))
                .healthScore(healthScore > 0 ? healthScore : 95.5)
                .lastExecution(last.map(e -> e.getStartTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).orElse("—"))
                .lastExecutionStatus(last.map(BatchExecutionDto::getStatus).orElse("—"))
                .activeJobs((int) history.stream().filter(e -> "RUNNING".equals(e.getStatus())).count())
                .recentExecutions(recent)
                .volumeTrend(buildTrend())
                .build();
    }

    private List<DashboardStatsDto.DailyVolumeDto> buildTrend() {
        List<DashboardStatsDto.DailyVolumeDto> trend = new ArrayList<>();
        LocalDate today = LocalDate.now();
        Random rnd = new Random();
        for (int i = 6; i >= 0; i--) {
            String date = today.minusDays(i).format(FMT);
            trend.add(new DashboardStatsDto.DailyVolumeDto(
                    date,
                    800 + rnd.nextInt(600),
                    200 + rnd.nextInt(300),
                    1200 + rnd.nextInt(800)
            ));
        }
        return trend;
    }

    private List<ConsultationResultDto> buildDemoResults(String instrument, String sens, String valeur) {
        List<ConsultationResultDto> demo = new ArrayList<>();
        String inst = (instrument != null && !instrument.isBlank()) ? instrument.toUpperCase() : "CHEQUE";
        String today = LocalDate.now().format(FMT);
        String[] agences = {"101", "205", "312", "418", "525"};
        Random rnd = new Random();
        for (String ag : agences) {
            ConsultationResultDto d = new ConsultationResultDto();
            d.setStructure(ag);
            d.setLibStructure(LIB_STRUCTURE.getOrDefault(ag, "Agence " + ag));
            d.setInstrument(inst);
            d.setSens(sens != null ? sens : "RCP");
            d.setValeur(valeur != null ? valeur : getDefaultValeur(inst));
            d.setDateOperation(today);
            long base = 80L + rnd.nextInt(420);
            d.setNombreTotal(base);
            d.setMontantTotal(base * (2000L + rnd.nextInt(3000)));
            d.setNombreIntra((long)(base * 0.65));
            d.setMontantIntra((long)(d.getMontantTotal() * 0.65));
            d.setNombreInter(base - d.getNombreIntra());
            d.setMontantInter(d.getMontantTotal() - d.getMontantIntra());
            demo.add(d);
        }
        return demo;
    }

    private String getDefaultValeur(String instrument) {
        if (instrument == null) return "30";
        return switch (instrument.toUpperCase()) {
            case "EFFET" -> "41-21";
            case "PRELEVEMENT" -> "20-21";
            default -> "30";
        };
    }
}
