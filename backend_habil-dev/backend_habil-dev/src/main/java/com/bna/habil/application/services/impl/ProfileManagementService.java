package com.bna.habil.application.services.impl;

import com.bna.habil.domain.entities.UtilisateurProfil;
import com.bna.habil.domain.entities.entitiesId.UtilisateurProfilId;
import com.bna.habil.domain.exceptions.ProfilAssignment.ProfileNotFoundException;
import com.bna.habil.domain.exceptions.ValidationException;
import com.bna.habil.infrastructure.persistence.repositories.extra.UtilisateurProfilRepository;
import com.bna.habil.interfaces.request.ProfileOperation;
import com.bna.habil.interfaces.request.UserProfileUpdateRequest;
import com.bna.habil.interfaces.response.ProfileUpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ProfileManagementService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileManagementService.class);
    private final UtilisateurProfilRepository utilisateurProfilRepository;

    public ProfileManagementService(UtilisateurProfilRepository utilisateurProfilRepository) {
        this.utilisateurProfilRepository = utilisateurProfilRepository;
    }

    /**
     * Main entry point for profile management operations
     */
    @Transactional
    public ProfileUpdateResult updateUserProfiles(UserProfileUpdateRequest request) {
        ProfileUpdateResult result = new ProfileUpdateResult();

        try {
            List<ProfileOperation> updateOps = request.getOperations();

            processOperations(updateOps,
                    request.getUserMatricule(),
                    request.getAppCode(),
                    result,
                    this::updateProfile);

            logger.info("Profile update completed for user {}: {} added, {} revoked, {} updated",
                    request.getUserMatricule(),
                    result.getAddedCount(),
                    result.getRevokedCount(),
                    result.getUpdatedCount());

        } catch (Exception e) {
            logger.error("Failed to validate permissions for user {}: {}",
                    request.getUserMatricule(), e.getMessage());
            result.setGlobalError(e.getMessage());
        }

        return result;
    }

    /**
     * Process a list of operations using the provided processor function
     */
    private void processOperations(List<ProfileOperation> operations,
                                   String userMatricule,
                                   String appCode,
                                   ProfileUpdateResult result,
                                   ProfileOperationProcessor processor) {
        if (operations == null || operations.isEmpty()) {
            return;
        }

        for (ProfileOperation operation : operations) {
            try {
                processor.process(userMatricule, operation, appCode);
                result.addSuccess(operation);
            } catch (Exception e) {
                result.addFailure(operation, e.getMessage());
                logger.error("Failed to process {} operation for profile {}: {}",
                        operation.getType(), operation.getProfileCode(), e.getMessage());
            }
        }
    }


    /**
     * Update an existing profile assignment
     */
    private void updateProfile(String userMatricule, ProfileOperation operation, String appCode) {

        UtilisateurProfilId id = new UtilisateurProfilId(operation.getProfileCode(), userMatricule);
        UtilisateurProfil existingProfile = utilisateurProfilRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "Profile " + operation.getProfileCode() + " not found for user"));

        // Update only provided fields
        if (operation.getDateDebut() != null) {
            existingProfile.setDatdadhutpr(operation.getDateDebut());
        }
        if (operation.getDateFin() != null) {
            existingProfile.setDatFadhUtpr(operation.getDateFin());
        }
        if (operation.getEtat() != null) {
            existingProfile.setBoolEtatUtpr(operation.getEtat());
        }

        utilisateurProfilRepository.save(existingProfile);
        logger.info("Profile {} updated for user {}", operation.getProfileCode(), userMatricule);
    }

    @FunctionalInterface
    private interface ProfileOperationProcessor {
        void process(String userMatricule, ProfileOperation operation, String appCode) throws ValidationException;
    }
}