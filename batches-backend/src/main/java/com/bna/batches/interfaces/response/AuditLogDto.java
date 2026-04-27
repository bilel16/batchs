package com.bna.batches.interfaces.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogDto {
    private String id;
    private String username;
    private String action;
    private String detail;
    private String ip;
    private LocalDateTime timestamp;
    private String result;
}
