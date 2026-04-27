package com.bna.habil.application.services.impl;

import com.bna.habil.application.dto.UtilisateurPackDto;
import com.bna.habil.application.dto.AssignedPack;
import com.bna.habil.application.enums.ProfileAssignmentSource;
import com.bna.habil.application.mappers.UtilisateurPackMapper;
import com.bna.habil.application.services.UtilisateurPackService;
import com.bna.habil.application.services.UtilisateurProfilService;
import com.bna.habil.application.services.crud.AbstractCrudService;
import com.bna.habil.application.services.crud.strategy.CreateValidator;
import com.bna.habil.application.services.crud.strategy.UpdateValidator;
import com.bna.habil.domain.entities.*;
import com.bna.habil.domain.entities.entitiesId.UtilisateurPackId;
import com.bna.habil.infrastructure.persistence.repositories.PersonnelCustomRepository;
import com.bna.habil.infrastructure.persistence.repositories.PersonnelRepository;
import com.bna.habil.infrastructure.persistence.repositories.extra.*;
import com.bna.habil.infrastructure.security.util.SecurityUtils;
import com.bna.habil.interfaces.response.BatchAssignmentResult;
import com.bna.habil.interfaces.response.ProfileAssignmentResult;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import java.util.Optional;


@Slf4j
@Service
public class UtilisateurPackServiceImpl extends AbstractCrudService<UtilisateurPack, UtilisateurPackDto, UtilisateurPackId>
        implements UtilisateurPackService {

    private final UtilisateurPackRepository utilisateurPackRepository;
    private final PackRepository packRepository;
    private final PackProfilRepository packProfilRepository;
    private final UtilisateurProfilService utilisateurProfilService;
    private final PersonnelCustomRepository personnelRepository;
    private final UtilisateurPackMapper utilisateurPackMapper;


    public UtilisateurPackServiceImpl(@Qualifier("utilisateurPackRepository") UtilisateurPackRepository repository,
                                      UtilisateurPackMapper mapper,
                                      PackRepository packRepository,
                                      PackProfilRepository packProfilRepository,
                                      PersonnelRepository personnelRepository, UtilisateurProfilService utilisateurProfilService, PersonnelCustomRepository personnelCustomRepository) {
        super(
                repository,
                mapper,
                // ID Extractor
                UtilisateurPackServiceImpl::extractId,
                UtilisateurPackServiceImpl::stringifyId,
                createValidator(personnelRepository, packRepository),
                updateValidator()
        );
        this.utilisateurPackRepository = repository;
        this.packRepository = packRepository;
        this.packProfilRepository = packProfilRepository;
        this.utilisateurProfilService = utilisateurProfilService;
        this.personnelRepository = personnelCustomRepository;

        this.utilisateurPackMapper = mapper;
    }
    // ═══════════════════════════════════════════════════════════════════════════
    // EXTRACTED METHODS FOR SUPER() CALL - Reduces Cognitive Complexity
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Extracts composite ID from DTO
     */
    private static UtilisateurPackId extractId(UtilisateurPackDto dto) {
        return new UtilisateurPackId(dto.getNumMatrUser(), dto.getCodPackPack());
    }

    /**
     * Converts ID to readable string for logging
     */
    private static String stringifyId(UtilisateurPackId id) {
        return String.format("User=%s,Pack=%s", id.getNumMatrUser(), id.getCodPackPack());
    }

    /**
     * Creates the validation for CREATE operations
     *
     * @return CreateValidator with correct type
     */
    private static CreateValidator<UtilisateurPackDto> createValidator(
            PersonnelRepository personnelRepository,
            PackRepository packRepository) {

        return dto -> {
            validateRequiredFields(dto);
            validateUserExists(dto.getNumMatrUser(), personnelRepository);
            validatePackExists(dto.getCodPackPack(), packRepository);
            validateDateRange(dto.getDatDebAffect(), dto.getDatFinAffect());
        };
    }

    /**
     * Creates the validation consumer for UPDATE operations
     */
    private static UpdateValidator<UtilisateurPackDto, UtilisateurPack, UtilisateurPackId> updateValidator() {
        return (id, dto, existing) -> {
            log.debug("Validating update for UtilisateurPack: {}", id);
            validateDateRange(dto.getDatDebAffect(), dto.getDatFinAffect());
        };
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // PRIVATE VALIDATION HELPER METHODS - Each does ONE thing
    // ═══════════════════════════════════════════════════════════════════════════

    private static void validateRequiredFields(UtilisateurPackDto dto) {
        requireNonBlank(dto.getNumMatrUser(), "User matricule cannot be empty");
        requireNonBlank(dto.getCodPackPack(), "Pack code cannot be empty");
    }

    private static void requireNonBlank(String value, String errorMessage) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }

    private static void validateUserExists(String matricule, PersonnelRepository repository) {
        if (!repository.existsById(matricule)) {
            throw new EntityNotFoundException(
                    String.format("User with matricule %s not found", matricule));
        }
    }

    private static void validatePackExists(String packCode, PackRepository repository) {
        if (!repository.existsById(packCode)) {
            throw new EntityNotFoundException(
                    String.format("Pack with code %s not found", packCode));
        }
    }

    private static void validateDateRange(Date startDate, Date endDate) {
        boolean bothDatesPresent = startDate != null && endDate != null;
        boolean endBeforeStart = bothDatesPresent && endDate.before(startDate);

        if (endBeforeStart) {
            throw new ValidationException("End date cannot be before start date");
        }
    }

    @Override
    protected String getEntityName() {
        return "UtilisateurPack";
    }


    @Override
    public List<UtilisateurPackDto> getPacksByMatricule(String matricule) throws EntityNotFoundException, ValidationException {
        log.info("Getting packs for user: {}", matricule);

        if (matricule == null || matricule.trim().isEmpty()) {
            throw new ValidationException("User matricule cannot be empty");
        }

        if (!personnelRepository.existsById(matricule)) {
            throw new EntityNotFoundException(
                    String.format("User with matricule %s not found", matricule)
            );
        }

        List<UtilisateurPack> packs =
                utilisateurPackRepository.findByNumMatrUser(matricule)
                        .stream()
                        .filter(p -> Integer.valueOf(1).equals(p.getBoolEtatAffect()))
                        .toList();


        log.info("Found {} packs for user {}", packs.size(), matricule);

        return utilisateurPackMapper.toDtoList(packs);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BatchAssignmentResult assignMultiplePacksToUser(
            String userMatricule,
            List<AssignedPack> assignedPacks,
            List<String> revokedPacks) {


        BatchAssignmentResult result = new BatchAssignmentResult();

        // Handle Assignments
        if (assignedPacks != null && !assignedPacks.isEmpty()) {
            for (AssignedPack pack : assignedPacks) {
                try {
                    assignPackToUser(userMatricule, pack, result);
                    log.info("✅ Pack {} assigned successfully", pack.getPackCode());
                } catch (Exception e) {
                    result.addFailure(pack.getPackCode(), e.getMessage());
                    log.error("❌ Failed to assign pack {}: {}", pack.getPackCode(), e.getMessage());
                }
            }
        }

        // Handle Revocations
        if (revokedPacks != null && !revokedPacks.isEmpty()) {
            for (String packCode : revokedPacks) {
                try {
                    revokePackFromUser(userMatricule, packCode, result);
                    log.info("✅ Pack {} revoked successfully", packCode);
                } catch (Exception e) {
                    result.addFailure(packCode, e.getMessage());
                    log.error("❌ Failed to revoke pack {}: {}", packCode, e.getMessage());
                }
            }
        }

        log.info("📊 Batch pack operation completed: {} successful, {} failed",
                result.getSuccessCount(), result.getFailureCount());

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void assignPackToUser(String userMatricule, AssignedPack assignedPack,
                                 BatchAssignmentResult result) throws Exception {

        String packCode = assignedPack.getPackCode();
        log.info("🎯 Assigning pack {} to user {}", packCode, userMatricule);

        // Validate user exists
        if (!personnelRepository.existsById(userMatricule)) {
            throw new EntityNotFoundException("User not found: " + userMatricule);
        }

        // Validate pack exists and is active
        Pack pack = packRepository.findById(packCode)
                .orElseThrow(() -> new EntityNotFoundException("Pack not found: " + packCode));

        if (pack.getBoolActifPack() != 1) {
            throw new ValidationException("Pack is not active: " + packCode);
        }

        // Step 1: Create or update UTILISATEUR_PACK entry
        UtilisateurPack utilisateurPack = createOrUpdateUtilisateurPack(userMatricule, packCode, assignedPack);
        log.info("✅ Step 1: Created/Updated UtilisateurPack entry");

        // Step 2: Get all profiles from the pack
        List<PackProfil> packProfils = packProfilRepository.findByCodPackPack(packCode);
        log.info("📦 Step 2: Found {} profiles in pack", packProfils.size());

        if (packProfils.isEmpty()) {
            log.warn("⚠️ Pack {} has no profiles", packCode);
            result.addSuccess(packCode);
            return;
        }

        // Step 3: Get profile codes
        List<String> profileCodes = packProfils.stream()
                .map(PackProfil::getCodPflPfl)
                .toList();

        // ═══════════════════════════════════════════════════════════════════════════
        // STEP 4: 🔑 DELEGATE TO PROFILE SERVICE - THE KEY CALL!
        // ═══════════════════════════════════════════════════════════════════════════
        // This calls assignProfilesBatch which internally calls assignProfile()
        // for each profile with:
        //   - source = FROM_PACK (so boolCustomProfil = 0)
        //   - skipValidation = true (pack itself is pre-validated)
        // ═══════════════════════════════════════════════════════════════════════════

        List<ProfileAssignmentResult> profileResults = utilisateurProfilService.assignProfilesBatch(
                userMatricule,
                profileCodes,
                utilisateurPack.getDatDebAffect(),
                utilisateurPack.getDatFinAffect(),
                ProfileAssignmentSource.FROM_PACK  // 🔑 This sets boolCustomProfil = 0
        );

        // Count results
        long created = profileResults.stream()
                .filter(r -> r.getStatus() == ProfileAssignmentResult.Status.CREATED)
                .count();
        long reactivated = profileResults.stream()
                .filter(r -> r.getStatus() == ProfileAssignmentResult.Status.REACTIVATED)
                .count();
        long failed = profileResults.stream()
                .filter(r -> r.getStatus() == ProfileAssignmentResult.Status.FAILED)
                .count();

        log.info("✅ Step 3: Profile assignment results - Created: {}, Reactivated: {}, Failed: {}",
                created, reactivated, failed);

        // Add pack to result
        if (failed == 0) {
            result.addSuccess(packCode);
        } else if (created + reactivated > 0) {
            result.addSuccess(packCode);
            log.warn("⚠️ Pack {} had {} profile failures", packCode, failed);
        } else {
            result.addFailure(packCode, "All profile assignments failed");
        }

        log.info("🎉 Pack {} assignment completed for user {}", packCode, userMatricule);
    }

    @Transactional(rollbackFor = Exception.class)
    public void revokePackFromUser(String userMatricule, String packCode,
                                   BatchAssignmentResult result) {

        log.info("🚫 Revoking pack {} from user {}", packCode, userMatricule);

        // Step 1: Deactivate UTILISATEUR_PACK entry
        UtilisateurPackId packId = new UtilisateurPackId(userMatricule, packCode);
        Optional<UtilisateurPack> utilisateurPackOpt = utilisateurPackRepository.findById(packId);

        if (utilisateurPackOpt.isEmpty()) {
            throw new EntityNotFoundException(
                    "Pack assignment not found for user: " + userMatricule + ", pack: " + packCode);
        }

        UtilisateurPack utilisateurPack = utilisateurPackOpt.get();
        utilisateurPack.setBoolEtatAffect(0);
        utilisateurPackRepository.save(utilisateurPack);
        log.info("✅ Step 1: Deactivated UtilisateurPack entry");

        // Step 2: Get all profiles from the pack
        List<PackProfil> packProfils = packProfilRepository.findByCodPackPack(packCode);

        if (!packProfils.isEmpty()) {
            // Step 3: Revoke all pack profiles
            List<String> profileCodes = packProfils.stream()
                    .map(PackProfil::getCodPflPfl)
                    .toList();

            // 🔑 DELEGATE TO PROFILE SERVICE
            List<ProfileAssignmentResult> revokeResults =
                    utilisateurProfilService.revokeProfilesBatch(userMatricule, profileCodes);

            long deactivated = revokeResults.stream()
                    .filter(r -> r.getStatus() == ProfileAssignmentResult.Status.DEACTIVATED)
                    .count();

            log.info("✅ Step 2: Deactivated {} profiles from pack", deactivated);
        }

        result.addSuccess(packCode);
        log.info("🎉 Pack {} revoked from user {}", packCode, userMatricule);
    }

    private UtilisateurPack createOrUpdateUtilisateurPack(
            String userMatricule,
            String packCode,
            AssignedPack assignedPack) {

        UtilisateurPackId id = new UtilisateurPackId(userMatricule, packCode);
        UtilisateurPack utilisateurPack = utilisateurPackRepository.findById(id)
                .orElse(new UtilisateurPack());

        utilisateurPack.setNumMatrUser(userMatricule);
        utilisateurPack.setCodPackPack(packCode);

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);

        utilisateurPack.setDatDebAffect(
                assignedPack.getDateDebut() != null ? assignedPack.getDateDebut() : currentDate);
        utilisateurPack.setDatFinAffect(
                assignedPack.getDateFin() != null ? assignedPack.getDateFin() : calendar.getTime());
        utilisateurPack.setUserAffectPack(SecurityUtils.getCurrentUserMatricule());
        utilisateurPack.setBoolEtatAffect(
                assignedPack.getEtat() != null ? assignedPack.getEtat() : 1);

        return utilisateurPackRepository.save(utilisateurPack);
    }

}