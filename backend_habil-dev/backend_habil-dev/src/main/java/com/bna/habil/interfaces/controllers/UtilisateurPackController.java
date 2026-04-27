package com.bna.habil.interfaces.controllers;

import com.bna.habil.application.dto.UserPacksAssignmentRequest;
import com.bna.habil.application.dto.UtilisateurPackDto;
import com.bna.habil.application.services.UtilisateurPackService;
import com.bna.habil.infrastructure.security.model.ResponseHabil;
import com.bna.habil.interfaces.response.BatchAssignmentResult;
import com.bna.habil.infrastructure.utils.Constants;
import com.bna.habil.infrastructure.utils.ExceptionUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/utilisateur-pack")
@Slf4j
@Validated
public class UtilisateurPackController {

    private UtilisateurPackService utilisateurPackService;

    /**
     * Get all packs assigned to a user by matricule
     * GET /api/utilisateur-pack/by-matricule/{matricule}
     */
    @GetMapping("/by-matricule/{matricule}")
    public ResponseHabil getPacksByMatricule(@PathVariable String matricule) {
        try {
            log.info("Getting packs for user: {}", matricule);

            List<UtilisateurPackDto> packs = utilisateurPackService.getPacksByMatricule(matricule);

            return new ResponseHabil(0, Constants.SUCCES, packs);
        } catch (Exception e) {
            log.error("getPacksByMatricule error: {}", e.getMessage());
            return ExceptionUtils.handleException(e);
        }
    }

    // ==================== PACK ASSIGNMENT ENDPOINTS ====================

    /**
     * Assign and/or revoke multiple packs to/from a user.
     * <p>
     * This endpoint handles PACK-based profile assignments where:
     * - User is assigned a pack (bundle of profiles)
     * - All profiles in the pack are marked with boolCustomProfil = 0
     * - No individual profile validation is performed (pack is pre-validated)
     * <p>
     * POST /api/utilisateur-pack/assign-multiple-packs
     *
     * @param request Contains userMatricule, assignedPacks, and revokedPacks
     * @return BatchAssignmentResult with success/failure details
     */
    @PostMapping("/assign-multiple-packs")
    @Operation(
            summary = "Assign/Revoke multiple packs to a user",
            description = "Assigns new packs and/or revokes existing packs for a user. " +
                    "When a pack is assigned, all profiles within the pack are automatically assigned " +
                    "with boolCustomProfil = 0 (indicating they came from a pack, not manual assignment). " +
                    "When a pack is revoked, all associated profiles are deactivated."
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
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User or pack not found"
            )
    })
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISEUR')") UNTIL WE FIGURE OUT WHO CAN GIVE THESE ROLES
    public ResponseEntity<BatchAssignmentResult> assignMultiplePacks(
            @Valid @RequestBody UserPacksAssignmentRequest request) {

        log.info("📦 [API] Assign multiple packs request - User: {}, Assign: {}, Revoke: {}",
                request.getUserMatricule(),
                request.getAssignedPacks() != null ? request.getAssignedPacks().size() : 0,
                request.getRevokedPacks() != null ? request.getRevokedPacks().size() : 0);

        // Validate request
        if (request.getUserMatricule() == null || request.getUserMatricule().trim().isEmpty()) {
            BatchAssignmentResult errorResult = new BatchAssignmentResult();
            errorResult.addFailure("REQUEST", "User matricule is required");
            return ResponseEntity.badRequest().body(errorResult);
        }

        // Check if there's anything to process
        boolean hasAssignments = request.getAssignedPacks() != null && !request.getAssignedPacks().isEmpty();
        boolean hasRevocations = request.getRevokedPacks() != null && !request.getRevokedPacks().isEmpty();

        if (!hasAssignments && !hasRevocations) {
            BatchAssignmentResult emptyResult = new BatchAssignmentResult();
            log.info("ℹ️ No packs to assign or revoke");
            return ResponseEntity.ok(emptyResult);
        }

        // Call service
        BatchAssignmentResult result = utilisateurPackService.assignMultiplePacksToUser(
                request.getUserMatricule(),
                request.getAssignedPacks(),
                request.getRevokedPacks()
        );

        // Determine response status
        HttpStatus status = determineResponseStatus(result);

        log.info("📊 [API] Pack assignment result - Success: {}, Failed: {}, Status: {}",
                result.getSuccessCount(), result.getFailureCount(), status);

        return ResponseEntity.status(status).body(result);
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