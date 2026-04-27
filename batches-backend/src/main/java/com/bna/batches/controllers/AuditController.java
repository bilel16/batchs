package com.bna.batches.controllers;

import com.bna.batches.interfaces.response.AuditLogDto;
import com.bna.batches.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    /** Get full audit log, optionally filtered */
    @GetMapping("/logs")
    public ResponseEntity<List<AuditLogDto>> getLogs(
            @RequestParam(required = false) String user,
            @RequestParam(required = false) String action) {

        List<AuditLogDto> logs;
        if (user != null && !user.isBlank()) {
            logs = auditService.getByUser(user);
        } else if (action != null && !action.isBlank()) {
            logs = auditService.getByAction(action);
        } else {
            logs = auditService.getAll();
        }
        return ResponseEntity.ok(logs);
    }
}
