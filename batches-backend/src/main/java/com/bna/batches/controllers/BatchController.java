package com.bna.batches.controllers;

import com.bna.batches.interfaces.request.BatchRequest;
import com.bna.batches.interfaces.response.BatchExecutionDto;
import com.bna.batches.services.AuditService;
import com.bna.batches.services.BatchLauncherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
public class BatchController {

    private final BatchLauncherService batchLauncherService;
    private final AuditService auditService;

    /** Launch a new batch execution */
    @PostMapping("/launch")
    public ResponseEntity<BatchExecutionDto> launch(
            @RequestBody BatchRequest request,
            Authentication auth,
            HttpServletRequest httpRequest) {

        String username = auth != null ? auth.getName() : "anonymous";
        String ip = httpRequest.getRemoteAddr();
        BatchExecutionDto execution = batchLauncherService.launch(request, username, ip);
        return ResponseEntity.ok(execution);
    }

    /** Get real-time status of a specific execution */
    @GetMapping("/status/{executionId}")
    public ResponseEntity<BatchExecutionDto> getStatus(@PathVariable String executionId) {
        BatchExecutionDto exec = batchLauncherService.findById(executionId);
        if (exec == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(exec);
    }

    /** Stop a running batch */
    @PostMapping("/{executionId}/stop")
    public ResponseEntity<Map<String, String>> stop(
            @PathVariable String executionId,
            Authentication auth,
            HttpServletRequest httpRequest) {

        String username = auth != null ? auth.getName() : "anonymous";
        boolean stopped = batchLauncherService.stop(executionId);
        if (stopped) {
            auditService.log(username, "BATCH_STOP", executionId, httpRequest.getRemoteAddr(), "STOPPED");
            return ResponseEntity.ok(Map.of("message", "Batch arrêté", "executionId", executionId));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Impossible d'arrêter ce batch"));
    }

    /** Get all execution history */
    @GetMapping("/history")
    public ResponseEntity<List<BatchExecutionDto>> getHistory(
            @RequestParam(required = false) String instrument) {
        List<BatchExecutionDto> history = instrument != null && !instrument.isBlank()
                ? batchLauncherService.getHistoryByInstrument(instrument)
                : batchLauncherService.getHistory();
        return ResponseEntity.ok(history);
    }
}
