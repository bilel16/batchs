package com.bna.habil.interfaces.request;

import com.bna.habil.application.enums.ProfileAssignmentSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Internal DTO for unified profile assignment operations.
 * Supports both custom and pack-based assignments.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileAssignmentRequest {

    private String userMatricule;
    private String profileCode;

    // Optional - only required for custom assignments with validation
    private String managerMatricule;
    private String appCode;

    // Assignment configuration
    @Builder.Default
    private ProfileAssignmentSource source = ProfileAssignmentSource.CUSTOM;

    @Builder.Default
    private boolean skipValidation = false;

    // Dates
    private Date startDate;
    private Date endDate;

    // State
    @Builder.Default
    private Integer etat = 1;

    // ==================== FACTORY METHODS ====================

    /**
     * Create request for custom profile assignment (with manager validation)
     */
    public static ProfileAssignmentRequest forCustomAssignment(
            String userMatricule,
            String profileCode,
            Date startDate,
            Date endDate,
            Integer etat,
            String appCode) {

        return ProfileAssignmentRequest.builder()
                .userMatricule(userMatricule)
                .profileCode(profileCode)
                .appCode(appCode)
                .source(ProfileAssignmentSource.CUSTOM)
                .skipValidation(false)
                .startDate(startDate != null ? startDate : new Date())
                .endDate(endDate)
                .etat(etat != null ? etat : 1)
                .build();
    }

    /**
     * Create request for pack-based profile assignment (no validation, boolCustomProfil = 0)
     */
    public static ProfileAssignmentRequest forPackAssignment(
            String userMatricule,
            String profileCode,
            Date startDate,
            Date endDate) {

        return ProfileAssignmentRequest.builder()
                .userMatricule(userMatricule)
                .profileCode(profileCode)
                .source(ProfileAssignmentSource.FROM_PACK)
                .skipValidation(true)  // Pack already validated at pack level
                .startDate(startDate)
                .endDate(endDate)
                .etat(1)
                .build();
    }
}