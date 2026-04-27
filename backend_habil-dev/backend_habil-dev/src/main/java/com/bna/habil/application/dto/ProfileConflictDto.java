package com.bna.habil.application.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileConflictDto {
    private String matricule;
    private String codPflPfl;
    private String conflictType; // "CUSTOM", "REVOKED"
    private int currentStatus;   // 0 = inactive, 1 = active
}