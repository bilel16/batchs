package com.bna.habil.interfaces.controllers;


import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bna.habil.infrastructure.utils.AssignmentStatistics;
import com.bna.habil.application.services.impl.ProfileManagementService;
import com.bna.habil.interfaces.request.UserProfileUpdateRequest;
import com.bna.habil.interfaces.request.UserProfilesAssignmentRequest;
import com.bna.habil.application.dto.PersonnelDetailsDto;
import com.bna.habil.interfaces.response.ProfileUpdateResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.bna.habil.application.dto.ProfilDto;
import com.bna.habil.application.services.impl.UtilisateurProfilServiceImpl;
import com.bna.habil.interfaces.response.BatchAssignmentResult;
import com.bna.habil.interfaces.request.BulkAssignmentRequest;
import com.bna.habil.interfaces.request.ProfileAssignmentRequest;

@RestController
@RequestMapping("/profiles")
public class ProfileAssignmentController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileAssignmentController.class);

    private final UtilisateurProfilServiceImpl profileService;
    private final ProfileManagementService profileManagementService;

    public ProfileAssignmentController(UtilisateurProfilServiceImpl profileService, ProfileManagementService profileManagementService) {
        this.profileService = profileService;
        this.profileManagementService = profileManagementService;
    }

    @GetMapping("/managed-users")
    public ResponseEntity<Set<String>> getManagedUsers(@RequestParam String managerMatricule) {
        return ResponseEntity.ok(profileService.getManagedUsers(managerMatricule));
    }

    @GetMapping("/managed-users/details")
    public ResponseEntity<List<PersonnelDetailsDto>> getManagedUsersWithDetails(@RequestParam String managerMatricule) {
        return ResponseEntity.ok(profileService.getManagedUsersWithDetails(managerMatricule));
    }

    @GetMapping("/assignable")
    public ResponseEntity<List<ProfilDto>> getAssignableProfiles(
            @RequestParam String managerMatricule,
            @RequestParam String userMatricule,
            @RequestParam String appCode) {
        return ResponseEntity.ok(
                profileService.getAssignableProfiles(managerMatricule, userMatricule, appCode)
        );
    }

    @GetMapping("/can-assign")
    public ResponseEntity<Boolean> canAssign(
            @RequestParam String managerMatricule,
            @RequestParam String profileCode,
            @RequestParam String appCode) {
        return ResponseEntity.ok(
                profileService.canManagerAssignProfile(managerMatricule, profileCode, appCode)
        );
    }

    @GetMapping("/user/{matricule}")
    public ResponseEntity<List<ProfilDto>> getUserProfiles(@PathVariable String matricule) {
        return ResponseEntity.ok(profileService.getUserProfiles(matricule));
    }

    @PostMapping("/assign")
    public ResponseEntity<Map<String, String>> assignProfile(@RequestBody ProfileAssignmentRequest request) {
        try {
            profileService.assignProfile(
                    request.getManagerMatricule(),
                    request.getUserMatricule(),
                    request.getProfileCode(),
                    request.getAppCode()
            );
            return ResponseEntity.ok(Map.of("message", "Profile assigned successfully"));
        } catch (Exception e) {
            logger.error("Error assigning profile: ", e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/bulk-assign")
    public ResponseEntity<BatchAssignmentResult> bulkAssign(@RequestBody BulkAssignmentRequest request) {
        return ResponseEntity.ok(
                profileService.bulkAssignProfile(
                        request.getManagerMatricule(),
                        request.getUserMatricules(),
                        request.getProfileCode(),
                        request.getAppCode()
                )
        );
    }

    @PutMapping("/remove")
    public ResponseEntity<Map<String, String>> removeProfile(
            @RequestParam String userMatricule,
            @RequestParam String profileCode) {
        try {
            profileService.removeProfile(userMatricule, profileCode);
            return ResponseEntity.ok(Map.of("message", "Profile removed successfully"));
        } catch (Exception e) {
            logger.error("Error removing profile: ", e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<AssignmentStatistics> getStatistics(@RequestParam String managerMatricule) {
        return ResponseEntity.ok(profileService.getStatistics(managerMatricule));
    }

    // ==================== NEW ENDPOINT FOR MULTIPLE PROFILE ASSIGNMENT ====================

    /**
     * Assign and/or revoke multiple custom profiles to/from a user.
     * <p>
     * This endpoint handles CUSTOM profile assignments where:
     * - Manager manually selects profiles to assign
     * - Profiles are marked with boolCustomProfil = 1
     * - Manager validation is performed (structure level, authorization)
     * <p>
     * POST /api/utilisateur-profil/assign-multiple-profiles
     *
     * @param request Contains userMatricule, appCode, assignedProfiles, and revokedProfiles
     * @return BatchAssignmentResult with success/failure details
     */
    @PostMapping("/assign-multiple-profiles")
    @Operation(
            summary = "Assign/Revoke multiple custom profiles to a user",
            description = "Assigns new profiles and/or revokes existing profiles for a user. " +
                    "Profiles assigned through this endpoint are marked as custom (boolCustomProfil = 1). " +
                    "Manager validation is performed for each profile."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All operations completed successfully",
                    content = @Content(schema = @Schema(implementation = BatchAssignmentResult.class))
            ),
            @ApiResponse(
                    responseCode = "207",
                    description = "Partial success - some operations failed",
                    content = @Content(schema = @Schema(implementation = BatchAssignmentResult.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "All operations failed or invalid request",
                    content = @Content(schema = @Schema(implementation = BatchAssignmentResult.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - insufficient permissions"
            )
    })
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISEUR')")   UNTILL WE FIGURE OUT WHO CAN GIVE TO WHO
    public ResponseEntity<BatchAssignmentResult> assignMultipleProfiles(
            @Valid @RequestBody UserProfilesAssignmentRequest request) {

        logger.info("📝 [API] Assign multiple profiles request - User: {}, App: {}, Assign: {}, Revoke: {}",
                request.getUserMatricule(),
                request.getAppCode(),
                request.getAssignedProfiles() != null ? request.getAssignedProfiles().size() : 0,
                request.getRevokedProfiles() != null ? request.getRevokedProfiles().size() : 0);

        // Validate request
        if (request.getUserMatricule() == null || request.getUserMatricule().trim().isEmpty()) {
            BatchAssignmentResult errorResult = new BatchAssignmentResult();
            errorResult.addFailure("REQUEST", "User matricule is required");
            return ResponseEntity.badRequest().body(errorResult);
        }

        if (request.getAppCode() == null || request.getAppCode().trim().isEmpty()) {
            BatchAssignmentResult errorResult = new BatchAssignmentResult();
            errorResult.addFailure("REQUEST", "Application code is required");
            return ResponseEntity.badRequest().body(errorResult);
        }

        // Check if there's anything to process
        boolean hasAssignments = request.getAssignedProfiles() != null && !request.getAssignedProfiles().isEmpty();
        boolean hasRevocations = request.getRevokedProfiles() != null && !request.getRevokedProfiles().isEmpty();

        if (!hasAssignments && !hasRevocations) {
            BatchAssignmentResult emptyResult = new BatchAssignmentResult();
            logger.info("ℹ️ No profiles to assign or revoke");
            return ResponseEntity.ok(emptyResult);
        }

        // Call service
        BatchAssignmentResult result = profileService.assignMultipleProfilesToUser(
                request.getUserMatricule(),
                request.getAssignedProfiles(),
                request.getRevokedProfiles(),
                request.getAppCode()
        );

        // Determine response status
        HttpStatus status = determineResponseStatus(result);

        logger.info("📊 [API] Assignment result - Success: {}, Failed: {}, Status: {}",
                result.getSuccessCount(), result.getFailureCount(), status);

        return ResponseEntity.status(status).body(result);
    }

    @PutMapping("/batch-update")
    public ResponseEntity<ProfileUpdateResult> batchUpdateProfiles(
            @Valid @RequestBody UserProfileUpdateRequest request) {

        ProfileUpdateResult result = profileManagementService.updateUserProfiles(request);

        return result.isSuccess() ?
                ResponseEntity.ok(result) :
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(result);
    }
    // ==================== HELPER METHODS ====================

    /**
     * Determine HTTP status based on result
     */
    private HttpStatus determineResponseStatus(BatchAssignmentResult result) {
        if (result.getFailureCount() == 0) {
            // All successful
            return HttpStatus.OK;
        } else if (result.getSuccessCount() > 0) {
            // Partial success
            return HttpStatus.MULTI_STATUS; // 207
        } else {
            // All failed
            return HttpStatus.BAD_REQUEST;
        }
    }
}