package com.bna.habil.application.services.impl.interim;

import com.bna.habil.application.enums.ProfileAssignmentSource;
import com.bna.habil.application.services.InterimHabilitationService;
import com.bna.habil.application.services.UtilisateurProfilService;
import com.bna.habil.domain.entities.UtilisateurProfil;
import com.bna.habil.domain.entities.entitiesId.UtilisateurProfilId;
import com.bna.habil.domain.entities.interim.InterimProfilBackup;
import com.bna.habil.domain.entities.interim.InterimProfilGranted;
import com.bna.habil.infrastructure.persistence.repositories.extra.UtilisateurProfilRepository;
import com.bna.habil.infrastructure.persistence.repositories.interim.InterimProfilBackupRepository;
import com.bna.habil.infrastructure.persistence.repositories.interim.InterimProfilGrantedRepository;
import com.bna.habil.interfaces.response.ProfileAssignmentResult;
import lombok.RequiredArgsConstructor;
import com.bna.habil.domain.entities.interim.Interim;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class InterimHabilitationServiceImpl  implements InterimHabilitationService {

    private final UtilisateurProfilRepository utilisateurProfilRepository;
    private final UtilisateurProfilService utilisateurProfilService;
    private final InterimProfilBackupRepository backupRepository;
    private final InterimProfilGrantedRepository grantedRepository;
    /**
     * When interim starts:
     * Make a copy of the user current profiles  source agent to the replacing agent
     * for the destination structure
     */
    // ═══════════════════════════════════════════════════════════════
    //                 GRANT (ACTIVATION)
    // ═══════════════════════════════════════════════════════════════

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantInterimHabilitations(Interim interim) {

        log.info("═══════════════════════════════════════════════════");
        log.info("GRANTING INTERIM HABILITATIONS");
        log.info("  Interim ID:  {}", interim.getId());
        log.info("  Source:      {}", interim.getMatriculeSource());
        log.info("  Cible:       {}", interim.getMatriculeCible());

        String matriculeSource = String.valueOf(interim.getMatriculeSource());
        String matriculeCible = String.valueOf(interim.getMatriculeCible());
        Integer codStrcDestination =interim.getCodStrcDestination();
        Long interimId = interim.getId();

        // STEP 1: Safety
        performActivationSafetyChecks(interimId);

        // STEP 2: Backup cible's active profiles
        int backedUp = backupCibleProfiles(interimId, matriculeCible);

        // STEP 3: Deactivate cible's active profiles
        int deactivated = deactivateCibleProfiles(matriculeCible);

        // STEP 4: Get source's active profiles and assign them to cible
        int granted = assignSourceProfilesToCible(
                interimId, matriculeSource, matriculeCible, interim);

        // STEP 5: Get cible's new structure and mutate them
        int strc =codStrcDestination;
        if (!Objects.equals(interim.getCodStrcOrigine(), codStrcDestination))
             mutateCibleUser(interim.getCodStrcDestination());

        log.info("ACTIVATION DONE — backed up: {}, deactivated: {}, granted: {}",
                backedUp, deactivated, granted);
    }

    private void mutateCibleUser(Integer codeStrc) {
    }

    /**
     * When interim ends:
     * Remove all interim-granted habilitations from the replacing agent
     */
// ═══════════════════════════════════════════════════════════════
    //                 REVOKE (TERMINATION)
    // ═══════════════════════════════════════════════════════════════

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void revokeInterimHabilitations(Interim interim) {

        log.info("═══════════════════════════════════════════════════");
        log.info("REVOKING INTERIM HABILITATIONS");
        log.info("  Interim ID:  {}", interim.getId());
        log.info("  Cible:       {}", interim.getMatriculeCible());
        log.info("═══════════════════════════════════════════════════");

        String matriculeCible = String.valueOf(interim.getMatriculeCible());
        Long interimId = interim.getId();

        // STEP 1: Deactivate profiles that were granted by this interim
        int revoked = revokeGrantedProfiles(interimId, matriculeCible);

        // STEP 2: Restore cible's original profiles from backup
        int restored = restoreBackedUpProfiles(interimId, matriculeCible);

        // STEP 3: Cleanup
        cleanupTrackingData(interimId);

        log.info("TERMINATION DONE — revoked: {}, restored: {}",
                revoked, restored);
    }


    // ═══════════════════════════════════════════════════════════════
    //              ACTIVATION — PRIVATE METHODS
    // ═══════════════════════════════════════════════════════════════

    private void performActivationSafetyChecks(Long interimId) {
        if (backupRepository.existsByInterimId(interimId)) {
            throw new IllegalStateException(
                    "Interim " + interimId + " already has backup records. "
                            + "May have been activated already.");
        }
        if (grantedRepository.existsByInterimId(interimId)) {
            throw new IllegalStateException(
                    "Interim " + interimId + " already has granted records. "
                            + "May have been activated already.");
        }
    }

    /**
     * Read cible's active profiles and save them to backup table.
     */
    private int backupCibleProfiles(Long interimId, String matriculeCible) {

        List<UtilisateurProfil> activeProfiles =
                utilisateurProfilRepository.findActiveByUserMatricule(matriculeCible);

        if (activeProfiles.isEmpty()) {
            log.info("No active profiles to backup for user {}", matriculeCible);
            return 0;
        }

        List<InterimProfilBackup> backups = activeProfiles.stream()
                .map(up -> InterimProfilBackup.builder()
                        .interimId(interimId)
                        .numMatrUser(matriculeCible)
                        .codPflPfl(up.getId().getCodPflPfl())
                        .boolEtatUtpr(up.getBoolEtatUtpr())
                        .datDadhUtpr(up.getDatdadhutpr())
                        .datFadhUtpr(up.getDatFadhUtpr())
                        .boolCustomProfil(up.getBoolCustomProfil())
                        .build())
                .toList();

        backupRepository.saveAll(backups);
        log.info("Backed up {} profiles for user {}", backups.size(), matriculeCible);
        return backups.size();
    }

    /**
     * Deactivate all cible's active profiles using existing service method.
     */
    private int deactivateCibleProfiles(String matriculeCible) {

        List<UtilisateurProfil> activeProfiles =
                utilisateurProfilRepository.findActiveByUserMatricule(matriculeCible);

        if (activeProfiles.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (UtilisateurProfil up : activeProfiles) {
            // Use existing revokeProfile method — NO MODIFICATION
            utilisateurProfilService.revokeProfile(
                    matriculeCible,
                    up.getId().getCodPflPfl()
            );
            count++;
        }

        log.info("Deactivated {} profiles for user {}", count, matriculeCible);
        return count;
    }

    /**
     * THE CORE:
     * 1. Get source's active profiles
     * 2. For each profile, call existing assignProfile to assign it to cible
     * 3. Track what was granted
     */
    private int assignSourceProfilesToCible(
            Long interimId,
            String matriculeSource,
            String matriculeCible,
            Interim interim) {

        // Get source user's active profiles
        List<UtilisateurProfil> sourceProfiles =
                utilisateurProfilRepository.findActiveByUserMatricule(matriculeSource);

        if (sourceProfiles.isEmpty()) {
            log.warn("No active profiles for source user {}", matriculeSource);
            return 0;
        }

        int grantedCount = 0;

        for (UtilisateurProfil sourceProfile : sourceProfiles) {

            String profileCode = sourceProfile.getId().getCodPflPfl();

            // Check if cible already has this profile (to track properly)
            UtilisateurProfilId targetId =
                    new UtilisateurProfilId(profileCode, matriculeCible);
            Optional<UtilisateurProfil> existingOpt =
                    utilisateurProfilRepository.findById(targetId);

            boolean previouslyExisted = existingOpt.isPresent();
            Integer previousEtat = previouslyExisted
                    ? existingOpt.get().getBoolEtatUtpr()
                    : null;

            // ─── USE EXISTING assignProfile METHOD ───
            ProfileAssignmentResult result =
                    utilisateurProfilService.assignProfile(
                            null,                               // managerMat
                            matriculeCible,                     // userMat
                            profileCode,                        // profileCode
                            null,                               // appCode
                            interim.getDateDebutInterim(),      // startDate
                            interim.getDateFinInterim(),        // endDate
                            1,                                  // etat = active
                            ProfileAssignmentSource.CUSTOM,     // source
                            true                                // skipValidation
                    );

            // ─── TRACK what we granted ───
            InterimProfilGranted tracking = InterimProfilGranted.builder()
                    .interimId(interimId)
                    .numMatrUser(matriculeCible)
                    .codPflPfl(profileCode)
                    .previouslyExisted(previouslyExisted ? 1 : 0)
                    .previousEtat(previousEtat)
                    .build();

            grantedRepository.save(tracking);

            grantedCount++;
            log.debug("Assigned profile {} to cible {} (result: {})",
                    profileCode, matriculeCible, result.getStatus());
        }

        log.info("Assigned {} source profiles to cible {}", grantedCount, matriculeCible);
        return grantedCount;
    }

    // ═══════════════════════════════════════════════════════════════
    //              TERMINATION — PRIVATE METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Deactivate all profiles that were granted by this interim.
     * Uses existing revokeProfile method.
     */
    private int revokeGrantedProfiles(Long interimId, String matriculeCible) {

        List<InterimProfilGranted> grantedList =
                grantedRepository.findByInterimIdAndNumMatrUser(
                        interimId, matriculeCible);

        if (grantedList.isEmpty()) {
            log.info("No granted profiles to revoke for interim {}", interimId);
            return 0;
        }

        int revokedCount = 0;

        for (InterimProfilGranted granted : grantedList) {

            String profileCode = granted.getCodPflPfl();

            if (granted.getPreviouslyExisted() == 1
                    && granted.getPreviousEtat() != null) {
                // Profile existed before interim
                // Restore it to its previous etat
                UtilisateurProfilId id =
                        new UtilisateurProfilId(profileCode, matriculeCible);
                Optional<UtilisateurProfil> opt =
                        utilisateurProfilRepository.findById(id);

                if (opt.isPresent()) {
                    UtilisateurProfil up = opt.get();
                    up.setBoolEtatUtpr(granted.getPreviousEtat());
                    utilisateurProfilRepository.save(up);
                }
            } else {
                // Profile did NOT exist before → just deactivate
                utilisateurProfilService.revokeProfile(
                        matriculeCible, profileCode);
            }

            revokedCount++;
            log.debug("Revoked interim profile {} from user {}",
                    profileCode, matriculeCible);
        }

        log.info("Revoked {} interim profiles from user {}",
                revokedCount, matriculeCible);
        return revokedCount;
    }

    /**
     * Restore cible's original profiles from backup.
     * Reads backup records and reactivates each profile
     * to its original state.
     */
    private int restoreBackedUpProfiles(Long interimId, String matriculeCible) {

        List<InterimProfilBackup> backups =
                backupRepository.findByInterimIdAndNumMatrUser(
                        interimId, matriculeCible);

        if (backups.isEmpty()) {
            log.info("No backup profiles to restore for user {}", matriculeCible);
            return 0;
        }

        int restoredCount = 0;

        for (InterimProfilBackup backup : backups) {

            String profileCode = backup.getCodPflPfl();
            UtilisateurProfilId id =
                    new UtilisateurProfilId(profileCode, matriculeCible);

            Optional<UtilisateurProfil> existingOpt =
                    utilisateurProfilRepository.findById(id);

            if (existingOpt.isPresent()) {
                // Profile still exists → restore original values
                UtilisateurProfil up = existingOpt.get();
                up.setBoolEtatUtpr(backup.getBoolEtatUtpr());
                up.setDatdadhutpr(backup.getDatDadhUtpr());
                up.setDatFadhUtpr(backup.getDatFadhUtpr());
                up.setBoolCustomProfil(backup.getBoolCustomProfil());
                utilisateurProfilRepository.save(up);
            } else {
                // Profile got deleted somehow → use existing assignProfile
                utilisateurProfilService.assignProfile(
                        null,
                        matriculeCible,
                        profileCode,
                        null,
                        backup.getDatDadhUtpr(),
                        backup.getDatFadhUtpr(),
                        backup.getBoolEtatUtpr(),
                        ProfileAssignmentSource.CUSTOM,
                        true
                );
            }

            restoredCount++;
            log.debug("Restored profile {} for user {} (etat={})",
                    profileCode, matriculeCible, backup.getBoolEtatUtpr());
        }

        log.info("Restored {} profiles for user {}", restoredCount, matriculeCible);
        return restoredCount;
    }

    private void cleanupTrackingData(Long interimId) {
        long backupCount = backupRepository.countByInterimId(interimId);
        long grantedCount = grantedRepository.countByInterimId(interimId);

        grantedRepository.deleteByInterimId(interimId);
        backupRepository.deleteByInterimId(interimId);

        log.info("Cleaned up interim {} — {} backup, {} granted records",
                interimId, backupCount, grantedCount);
    }
}