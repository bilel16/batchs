package com.bna.habil.application.services;

import java.util.Date;
import java.util.List;

import com.bna.habil.application.dto.UtilisateurProfilDTO;
import com.bna.habil.application.dto.statistics.UserMenuApplication.UserApplicationsResponse;
import com.bna.habil.application.enums.ProfileAssignmentSource;
import com.bna.habil.domain.beans.UtilisateurProfilBean;
import com.bna.habil.application.services.crud.CrudService;
import com.bna.habil.domain.entities.entitiesId.UtilisateurProfilId;
import com.bna.habil.domain.exceptions.ValidationException;
import com.bna.habil.interfaces.response.ProfileAssignmentResult;

public interface UtilisateurProfilService extends CrudService<UtilisateurProfilBean, UtilisateurProfilId> {

    // ==================== EXISTING METHODS (UNCHANGED) ====================

    List<UtilisateurProfilBean> getListUserProfil(String codAppApp) throws ValidationException;

    List<UtilisateurProfilDTO> getListUserProfil(String cdp, String matricule) throws ValidationException;

    List<String> getApplicationsByMatricule(String matricule) throws ValidationException;

    // ==================== PROFILE ASSIGNMENT METHODS ====================

    /**
     * Original method for custom profile assignment (backward compatible).
     * Full validation is performed.
     * Sets boolCustomProfil = 1
     */
    void assignProfile(String managerMatricule, String userMatricule,
                       String profileCode, String appCode) throws ValidationException;

    /**
     * Extended method for profile assignment with full control.
     * This is THE SINGLE SOURCE OF TRUTH for all profile assignments.
     *
     * @param managerMatricule Manager performing the assignment (can be null if skipValidation=true)
     * @param userMatricule    Target user
     * @param profileCode      Profile to assign
     * @param appCode          Application code (can be null if skipValidation=true)
     * @param startDate        Assignment start date (null = now)
     * @param endDate          Assignment end date (null = +1 year)
     * @param etat             State: 1=active, 0=inactive (null = 1)
     * @param source           CUSTOM or FROM_PACK - determines boolCustomProfil value
     * @param skipValidation   If true, skip manager/structure validation (for pack assignments)
     * @return ProfileAssignmentResult with status details
     */
    ProfileAssignmentResult assignProfile(
            String managerMatricule,
            String userMatricule,
            String profileCode,
            String appCode,
            Date startDate,
            Date endDate,
            Integer etat,
            ProfileAssignmentSource source,
            boolean skipValidation) throws ValidationException;

    /**
     * Revoke a profile from a user (deactivate)
     */
    ProfileAssignmentResult revokeProfile(String userMatricule, String profileCode);

    /**
     * Batch assign profiles - calls assignProfile for each request
     */
    List<ProfileAssignmentResult> assignProfilesBatch(
            String userMatricule,
            List<String> profileCodes,
            Date startDate,
            Date endDate,
            ProfileAssignmentSource source) throws ValidationException;

    /**
     * Batch revoke profiles
     */
    List<ProfileAssignmentResult> revokeProfilesBatch(String userMatricule, List<String> profileCodes);

//    UserApplicationsResponse getApplicationMenusByMatricule(String matricule);
}