package com.bna.batches.services;

import com.bna.batches.interfaces.response.AuditLogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuditService {

    // In-memory ring buffer — last 500 entries
    private static final int MAX_SIZE = 500;
    private final Deque<AuditLogDto> logs = new ConcurrentLinkedDeque<>();

    public void log(String username, String action, String detail, String ip, String result) {
        AuditLogDto entry = new AuditLogDto(
                UUID.randomUUID().toString(),
                username,
                action,
                detail,
                ip != null ? ip : "N/A",
                LocalDateTime.now(),
                result
        );
        logs.addFirst(entry);
        if (logs.size() > MAX_SIZE) logs.pollLast();
        log.info("[AUDIT] user={} action={} detail={} result={}", username, action, detail, result);
    }

    public List<AuditLogDto> getAll() {
        return logs.stream().collect(Collectors.toList());
    }

    public List<AuditLogDto> getByUser(String username) {
        return logs.stream()
                .filter(e -> e.getUsername() != null && e.getUsername().equalsIgnoreCase(username))
                .collect(Collectors.toList());
    }

    public List<AuditLogDto> getByAction(String action) {
        return logs.stream()
                .filter(e -> e.getAction() != null && e.getAction().equalsIgnoreCase(action))
                .collect(Collectors.toList());
    }
}
