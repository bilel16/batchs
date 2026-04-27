package com.bna.habil.interfaces.response;

import com.bna.habil.application.enums.ProfileAssignmentSource;
import lombok.Builder;
import lombok.Data;

/**
 * Result of a single profile assignment operation
 */
@Data
@Builder
public class ProfileAssignmentResult {

    public enum Status {
        CREATED,        // New assignment created
        REACTIVATED,    // Existing inactive assignment reactivated
        ALREADY_ACTIVE, // Profile was already active
        DEACTIVATED,    // Profile was deactivated (revoked)
        FAILED          // Assignment failed
    }

    private String profileCode;
    private String userMatricule;
    private Status status;
    private ProfileAssignmentSource source;
    private String message;

    // ==================== FACTORY METHODS ====================

    public static ProfileAssignmentResult created(
            String profileCode,
            String userMatricule,
            ProfileAssignmentSource source) {
        return ProfileAssignmentResult.builder()
                .profileCode(profileCode)
                .userMatricule(userMatricule)
                .status(Status.CREATED)
                .source(source)
                .message("Profile assignment created successfully")
                .build();
    }

    public static ProfileAssignmentResult reactivated(
            String profileCode,
            String userMatricule,
            ProfileAssignmentSource source) {
        return ProfileAssignmentResult.builder()
                .profileCode(profileCode)
                .userMatricule(userMatricule)
                .status(Status.REACTIVATED)
                .source(source)
                .message("Profile assignment reactivated")
                .build();
    }

    public static ProfileAssignmentResult alreadyActive(
            String profileCode,
            String userMatricule) {
        return ProfileAssignmentResult.builder()
                .profileCode(profileCode)
                .userMatricule(userMatricule)
                .status(Status.ALREADY_ACTIVE)
                .message("Profile already active for user")
                .build();
    }

    public static ProfileAssignmentResult deactivated(
            String profileCode,
            String userMatricule) {
        return ProfileAssignmentResult.builder()
                .profileCode(profileCode)
                .userMatricule(userMatricule)
                .status(Status.DEACTIVATED)
                .message("Profile deactivated successfully")
                .build();
    }

    public static ProfileAssignmentResult failed(
            String profileCode,
            String userMatricule,
            String errorMessage) {
        return ProfileAssignmentResult.builder()
                .profileCode(profileCode)
                .userMatricule(userMatricule)
                .status(Status.FAILED)
                .message(errorMessage)
                .build();
    }

    public boolean isSuccess() {
        return status != Status.FAILED;
    }
}