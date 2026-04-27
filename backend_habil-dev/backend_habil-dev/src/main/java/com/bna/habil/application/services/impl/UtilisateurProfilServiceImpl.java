package com.bna.habil.application.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.bna.habil.application.dto.StructureDto;
import com.bna.habil.application.dto.statistics.UserMenuApplication.*;
import com.bna.habil.application.enums.ProfileAssignmentSource;
import com.bna.habil.application.services.StructureService;
import com.bna.habil.application.services.crud.AbstractCrudService;
import com.bna.habil.domain.entities.Personnel;
import com.bna.habil.domain.exceptions.DuplicateResourceException;
import com.bna.habil.domain.exceptions.ValidationException;
import com.bna.habil.infrastructure.security.util.SecurityUtils;
import com.bna.habil.interfaces.request.AssignedProfile;
import com.bna.habil.interfaces.response.BatchAssignmentResult;
import com.bna.habil.interfaces.response.ProfileAssignmentResult;
import com.bna.habil.application.dto.PersonnelDetailsDto;
import com.bna.habil.infrastructure.persistence.repositories.PersonneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bna.habil.application.dto.ProfilDto;
import com.bna.habil.application.dto.UtilisateurProfilDTO;
import com.bna.habil.application.mappers.ProfilMapper;
import com.bna.habil.application.mappers.UtilisateurProfilMapper;
import com.bna.habil.application.mappers.UtilisateurProfilMapperDetailed;
import com.bna.habil.application.services.StructureHierarchyService;
import com.bna.habil.application.services.UtilisateurProfilService;
import com.bna.habil.domain.beans.UtilisateurProfilBean;
import com.bna.habil.domain.entities.Profil;
import com.bna.habil.domain.entities.ProfilMenuApplication;
import com.bna.habil.domain.entities.UtilisateurProfil;
import com.bna.habil.domain.entities.entitiesId.UtilisateurProfilId;
import com.bna.habil.infrastructure.persistence.repositories.extra.ProfilMenuApplicationRepository;
import com.bna.habil.infrastructure.persistence.repositories.extra.ProfilRepository;
import com.bna.habil.infrastructure.persistence.repositories.extra.UtilisateurProfilRepository;
import com.bna.habil.infrastructure.utils.AssignmentStatistics;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UtilisateurProfilServiceImpl
        extends AbstractCrudService<UtilisateurProfil, UtilisateurProfilBean, UtilisateurProfilId>
        implements UtilisateurProfilService {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurProfilServiceImpl.class);

    private final ProfilMenuApplicationRepository profilMenuApplicationRepository;
    private final UtilisateurProfilRepository utilisateurProfilRepository;
    private final ProfilRepository profilRepository;

    private final ProfilServiceImpl profilServiceImpl;
    private final ProfilMapper profilMapper;
    private final UtilisateurProfilMapper utilisateurProfilMapper;

    private final UtilisateurProfilMapperDetailed utilisateurProfilMapperDetailed;
    private final StructureHierarchyService hierarchyService;
    private final PersonneRepository personneRepository;
    private final StructureService structureService;

    public UtilisateurProfilServiceImpl(UtilisateurProfilRepository utilisateurProfilRepository,
                                        ProfilRepository profilRepository,
                                        ProfilServiceImpl profilServiceImpl,
                                        UtilisateurProfilMapper mapper,
                                        ProfilMenuApplicationRepository profilMenuApplicationRepository,
                                        ProfilMapper profilMapper,
                                        UtilisateurProfilMapper utilisateurProfilMapper,
                                        UtilisateurProfilMapperDetailed utilisateurProfilMapperDetailed,
                                        StructureHierarchyService hierarchyService,
                                        PersonneRepository personneRepository,
                                        StructureService structureService) {
        super(
                utilisateurProfilRepository,
                mapper,
                dto -> new UtilisateurProfilId(dto.getCodPflPfl(), dto.getNumMatrUser()),
                id -> String.format("Profile=%s,User=%s", id.getCodPflPfl(), id.getNumMatrUser()),
                dto -> {
                    if (dto.getCodPflPfl() == null || dto.getCodPflPfl().trim().isEmpty()) {
                        throw new ValidationException("Profile code cannot be empty");
                    }
                    if (dto.getNumMatrUser() == null || dto.getNumMatrUser().trim().isEmpty()) {
                        throw new ValidationException("User matricule cannot be empty");
                    }
                    if (!profilRepository.existsById(dto.getCodPflPfl())) {
                        throw new EntityNotFoundException(
                                String.format("Profile with code %s not found", dto.getCodPflPfl())
                        );
                    }
                },
                UtilisateurProfilServiceImpl::validate
        );
        this.utilisateurProfilRepository = utilisateurProfilRepository;
        this.profilRepository = profilRepository;
        this.profilServiceImpl = profilServiceImpl;
        this.profilMenuApplicationRepository = profilMenuApplicationRepository;
        this.profilMapper = profilMapper;
        this.utilisateurProfilMapper = utilisateurProfilMapper;
        this.utilisateurProfilMapperDetailed = utilisateurProfilMapperDetailed;
        this.hierarchyService = hierarchyService;
        this.personneRepository = personneRepository;
        this.structureService = structureService;
    }

    private static void validate(UtilisateurProfilId id, UtilisateurProfilBean dto, UtilisateurProfil existing) {
        logger.debug("Validating update for UtilisateurProfil: {}", id);
    }

    @Override
    protected String getEntityName() {
        return "UtilisateurProfil";
    }

    // ====================================================================================
    // ===================== THE SINGLE SOURCE OF TRUTH FOR PROFILE ASSIGNMENT ============
    // ====================================================================================

    /**
     * Original method - backward compatible for custom assignments.
     * Calls the extended method with CUSTOM source and full validation.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignProfile(String managerMat, String userMat,
                              String profileCode, String appCode) {

        // Delegate to the full method with CUSTOM source and validation enabled
        assignProfile(
                managerMat,
                userMat,
                profileCode,
                appCode,
                null,  // startDate - will default to now
                null,  // endDate - will default to +1 year
                1,     // etat - active
                ProfileAssignmentSource.CUSTOM,  // source - custom assignment
                false  // skipValidation - do NOT skip validation for custom assignments
        );
    }

    /**
     * ═══════════════════════════════════════════════════════════════════════════════════
     * THE SINGLE SOURCE OF TRUTH FOR ALL PROFILE ASSIGNMENTS
     * ═══════════════════════════════════════════════════════════════════════════════════
     * This is THE ONLY method that performs the actual profile save.
     * All other methods (pack assignment, batch assignment, etc.) MUST call this method.
     *
     * @param managerMat     Manager matricule (required if skipValidation=false)
     * @param userMat        Target user matricule
     * @param profileCode    Profile code to assign
     * @param appCode        Application code (required if skipValidation=false)
     * @param startDate      Start date (null = now)
     * @param endDate        End date (null = +1 year)
     * @param etat           State: 1=active, 0=inactive (null = 1)
     * @param source         CUSTOM or FROM_PACK - determines boolCustomProfil value
     * @param skipValidation If true, skip manager/structure validation
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProfileAssignmentResult assignProfile(
            String managerMat,
            String userMat,
            String profileCode,
            String appCode,
            Date startDate,
            Date endDate,
            Integer etat,
            ProfileAssignmentSource source,
            boolean skipValidation) throws ValidationException {

        logger.info("📝 assignProfile - User: {}, Profile: {}, Source: {}, SkipValidation: {}",
                userMat, profileCode, source, skipValidation);

        // ═══════════════════════════════════════════════════════════════════════════
        // STEP 1: VALIDATION (only if not skipped)
        // ═══════════════════════════════════════════════════════════════════════════

        Integer managerStructId = performValidationIfRequired(
                managerMat, profileCode, appCode, skipValidation, source);
        // ═══════════════════════════════════════════════════════════════════════════
        // STEP 2: LOAD PROFILE ENTITY
        // ═══════════════════════════════════════════════════════════════════════════

        ProfilDto profilDto = loadAndValidateProfile(profileCode);

        // ═══════════════════════════════════════════════════════════════════════════
        // STEP 3: PREPARE DATES
        // ═══════════════════════════════════════════════════════════════════════════

        EffectiveDates effectiveDates = prepareEffectiveDates(startDate, endDate, etat);

        // ═══════════════════════════════════════════════════════════════════════════
        // STEP 4: CREATE OR UPDATE UTILISATEUR_PROFIL
        // ═══════════════════════════════════════════════════════════════════════════
        AssignmentResult assignmentResult = createOrUpdateAssignment(
                userMat, profileCode, profilDto, effectiveDates, source);

        // ═══════════════════════════════════════════════════════════════════════════
        // STEP 5: SAVE TO DATABASE
        // ═══════════════════════════════════════════════════════════════════════════
        utilisateurProfilRepository.save(assignmentResult.userProfil());

        // ═══════════════════════════════════════════════════════════════════════════
        // STEP 6: AUDIT LOGGING
        // ═══════════════════════════════════════════════════════════════════════════
        logAssignmentAudit(managerMat, userMat, profileCode, managerStructId, source);

        // ═══════════════════════════════════════════════════════════════════════════
        // STEP 7: RETURN RESULT
        // ═══════════════════════════════════════════════════════════════════════════
        return ProfileAssignmentResult.builder()
                .profileCode(profileCode)
                .userMatricule(userMat)
                .status(assignmentResult.status())
                .source(source)
                .message(assignmentResult.status().name())
                .build();
    }

    // ====================================================================================
    // ===================== REVOKE PROFILE ===============================================
    // ====================================================================================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProfileAssignmentResult revokeProfile(String userMatricule, String profileCode) {

        logger.info("🚫 Revoking profile {} from user {}", profileCode, userMatricule);

        try {
            UtilisateurProfilId id = new UtilisateurProfilId(profileCode, userMatricule);
            Optional<UtilisateurProfil> existingOpt = utilisateurProfilRepository.findById(id);

            if (existingOpt.isEmpty()) {
                logger.warn("⚠️ Profile {} not found for user {}", profileCode, userMatricule);
                return ProfileAssignmentResult.failed(profileCode, userMatricule, "Profile assignment not found");
            }

            UtilisateurProfil userProfil = existingOpt.get();

            if (userProfil.getBoolEtatUtpr() == 0) {
                logger.info("ℹ️ Profile {} already inactive for user {}", profileCode, userMatricule);
                return ProfileAssignmentResult.alreadyActive(profileCode, userMatricule);
            }

            // Deactivate
            userProfil.setBoolEtatUtpr(0);
            userProfil.setDatFadhUtpr(new Date());
            utilisateurProfilRepository.save(userProfil);

            logger.info("✅ Profile {} revoked from user {}", profileCode, userMatricule);
            return ProfileAssignmentResult.deactivated(profileCode, userMatricule);

        } catch (Exception e) {
            logger.error("❌ Failed to revoke profile {}: {}", profileCode, e.getMessage());
            return ProfileAssignmentResult.failed(profileCode, userMatricule, e.getMessage());
        }
    }

    // ====================================================================================
    // ===================== BATCH OPERATIONS =============================================
    // ====================================================================================

    /**
     * Batch assign profiles - calls the SINGLE assignProfile method for each profile.
     * Used by pack service.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ProfileAssignmentResult> assignProfilesBatch(
            String userMatricule,
            List<String> profileCodes,
            Date startDate,
            Date endDate,
            ProfileAssignmentSource source) {

        if (profileCodes == null || profileCodes.isEmpty()) {
            return Collections.emptyList();
        }

        logger.info("📦 Batch assigning {} profiles to user {} (source: {})",
                profileCodes.size(), userMatricule, source);

        List<ProfileAssignmentResult> results = new ArrayList<>();

        for (String profileCode : profileCodes) {
            try {
                // 🔑 CALL THE SINGLE SOURCE OF TRUTH METHOD
                ProfileAssignmentResult result = assignProfile(
                        null,           // managerMat - not needed for pack assignments
                        userMatricule,
                        profileCode,
                        null,           // appCode - not needed for pack assignments
                        startDate,
                        endDate,
                        1,              // etat - active
                        source,
                        true            // skipValidation - pack is pre-validated
                );
                results.add(result);

            } catch (Exception e) {
                logger.error("❌ Failed to assign profile {}: {}", profileCode, e.getMessage());
                results.add(ProfileAssignmentResult.failed(profileCode, userMatricule, e.getMessage()));
            }
        }

        // Log summary
        long created = results.stream().filter(r -> r.getStatus() == ProfileAssignmentResult.Status.CREATED).count();
        long reactivated = results.stream().filter(r -> r.getStatus() == ProfileAssignmentResult.Status.REACTIVATED).count();
        long failed = results.stream().filter(r -> r.getStatus() == ProfileAssignmentResult.Status.FAILED).count();

        logger.info("📊 Batch assignment complete - Created: {}, Reactivated: {}, Failed: {}",
                created, reactivated, failed);

        return results;
    }

    /**
     * Batch revoke profiles
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ProfileAssignmentResult> revokeProfilesBatch(String userMatricule, List<String> profileCodes) {

        if (profileCodes == null || profileCodes.isEmpty()) {
            return Collections.emptyList();
        }

        logger.info("🚫 Batch revoking {} profiles from user {}", profileCodes.size(), userMatricule);

        List<ProfileAssignmentResult> results = new ArrayList<>();

        for (String profileCode : profileCodes) {
            ProfileAssignmentResult result = revokeProfile(userMatricule, profileCode);
            results.add(result);
        }

        return results;
    }

//    @Override
//    public UserApplicationsResponse getApplicationMenusByMatricule(String matricule) {
//
//        Personnel personnel = personneCustomRepository.findById(matricule).orElseThrow(null);
//        PersonnelDetailsDto personnelDetails = personneRepository.findPersonnelDetailsByCin(personnel.getCin()).orElse(null);
//        String nom_prenom = personnelDetails != null ? personnelDetails.getNom_prenom() : String.valueOf(' ');
//        List<ApplicationMenuDTO> results = utilisateurProfilRepository.findApplicationMenusByMatricule(matricule);
//
//        if (results.isEmpty()) {
//            return new UserApplicationsResponse(matricule, nom_prenom, new ArrayList<>());
//        }
//
//        // Group by application and profil in one pass
//        Map<String, Map<String, List<ApplicationMenuDTO>>> grouped = results.stream()
//                .collect(Collectors.groupingBy(
//                        ApplicationMenuDTO::getCodAppApp,
//                        LinkedHashMap::new,
//                        Collectors.groupingBy(
//                                ApplicationMenuDTO::getCodPflPfl,
//                                LinkedHashMap::new,
//                                Collectors.toList()
//                        )
//                ));
//
//        // Transform to response structure
//        List<ApplicationResponse> applications = grouped.entrySet().stream()
//                .map(appEntry -> {
//                    Map<String, List<ApplicationMenuDTO>> profilsMap = appEntry.getValue();
//                    ApplicationMenuDTO firstDto = profilsMap.values().iterator().next().get(0);
//
//                    List<ProfilResponse> profils = profilsMap.entrySet().stream()
//                            .map(profilEntry -> {
//                                List<ApplicationMenuDTO> dtos = profilEntry.getValue();
//
//                                List<MenuResponse> menus = dtos.stream()
//                                        .filter(dto -> dto.getCodMenuMenu() != null)
//                                        .map(dto -> new MenuResponse(dto.getCodMenuMenu(), dto.getLibMenuMenu()))
//                                        .distinct()
//                                        .collect(Collectors.toList());
//
//                                return new ProfilResponse(
//                                        profilEntry.getKey(),
//                                        dtos.get(0).getLibPflPfl(),
//                                        menus
//                                );
//                            })
//                            .collect(Collectors.toList());
//
//                    return new ApplicationResponse(
//                            appEntry.getKey(),
//                            firstDto.getLibAppApp(),
//                            profils
//                    );
//                })
//                .collect(Collectors.toList());
//
//        return new UserApplicationsResponse(matricule, nom_prenom, applications);
//    }

    // ====================================================================================
    // ===================== EXISTING LEGACY METHODS (UNCHANGED) ==========================
    // ====================================================================================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UtilisateurProfilBean create(UtilisateurProfilBean dto) {
        logger.info("Creating UtilisateurProfil for user: {} and profile: {}",
                dto.getNumMatrUser(), dto.getCodPflPfl());

        if (dto.getCodPflPfl() == null || dto.getNumMatrUser() == null) {
            throw new ValidationException("Profile code and user matricule are required");
        }

        UtilisateurProfilId id = new UtilisateurProfilId(dto.getCodPflPfl(), dto.getNumMatrUser());
        if (repository.existsById(id)) {
            throw new DuplicateResourceException(
                    String.format("User %s already has profile %s",
                            dto.getNumMatrUser(), dto.getCodPflPfl())
            );
        }

        UtilisateurProfil entity = mapper.toEntity(dto);
        entity.setId(id);

        Profil profil = profilRepository.findByCodPflPfl(dto.getCodPflPfl());
        if (profil == null) {
            throw new EntityNotFoundException(
                    String.format("Profile %s not found", dto.getCodPflPfl())
            );
        }
        entity.setProfil(profil);

        if (entity.getBoolEtatUtpr() == null) {
            entity.setBoolEtatUtpr(0);
        }
        if (entity.getDatdadhutpr() == null) {
            entity.setDatdadhutpr(new Date());
        }

        UtilisateurProfil saved = repository.save(entity);
        logger.info("Successfully created UtilisateurProfil: {}", id);

        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getApplicationsByMatricule(String matricule) throws ValidationException {
        logger.info("Getting applications for user: {}", matricule);

        if (matricule == null || matricule.trim().isEmpty()) {
            throw new ValidationException("Matricule cannot be empty");
        }

        return utilisateurProfilRepository.findApplicationsByMatricule(matricule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurProfilDTO> getListUserProfil(String cdp, String matricule) throws ValidationException {
        logger.info("Getting user profiles for app: {} and matricule: {}", cdp, matricule);

        if (cdp == null || cdp.trim().isEmpty()) {
            throw new ValidationException("Application code cannot be empty");
        }

        List<UtilisateurProfil> userProfilList = utilisateurProfilRepository
                .findByProfilCodAppAppAndOptionalMatricule(cdp, matricule);

        logger.info("Found {} user profiles", userProfilList.size());

        return utilisateurProfilMapperDetailed.toDtoList(userProfilList);
    }

    @Override
    @Transactional
    public List<UtilisateurProfilBean> getListUserProfil(String cdp) {
        List<UtilisateurProfil> userProfilList = utilisateurProfilRepository.findByProfilCodAppApp(cdp);
        return utilisateurProfilMapper.toDtoList(userProfilList);
    }

    @Transactional(readOnly = true)
    public List<ProfilDto> getUserProfiles(String userMatricule) {
        logger.info("Getting active profiles for user: {}", userMatricule);

        if (userMatricule == null || userMatricule.trim().isEmpty()) {
            throw new ValidationException("User matricule cannot be empty");
        }

        return utilisateurProfilRepository.findActiveByUserMatricule(userMatricule)
                .stream()
                .map(up -> profilServiceImpl.findById(up.getId().getCodPflPfl()))
                .filter(Objects::nonNull)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeProfile(String userMatricule, String profileCode) {
        String managerMat = SecurityUtils.getCurrentUserMatricule();

        UtilisateurProfilId id = new UtilisateurProfilId(profileCode, userMatricule);
        utilisateurProfilRepository.findById(id).ifPresent(userProfil -> {
            userProfil.setBoolEtatUtpr(0);
            userProfil.setDatFadhUtpr(new Date());
            utilisateurProfilRepository.save(userProfil);
        });

        logger.info("Profile {} removed from user {} by manager {}",
                profileCode, userMatricule, managerMat);
    }

    @Transactional(rollbackFor = Exception.class)
    public BatchAssignmentResult bulkAssignProfile(String managerMat,
                                                   List<String> userMatricules,
                                                   String profileCode,
                                                   String appCode) {
        logger.info("Bulk assigning profile {} to {} users by manager {}",
                profileCode, userMatricules.size(), managerMat);

        BatchAssignmentResult result = new BatchAssignmentResult();

        for (String userMatricule : userMatricules) {
            try {
                // 🔑 USE THE SINGLE SOURCE OF TRUTH METHOD
                assignProfile(managerMat, userMatricule, profileCode, appCode);
                result.addSuccess(userMatricule);
            } catch (Exception e) {
                result.addFailure(userMatricule, e.getMessage());
                logger.error("Failed to assign profile to user {}: {}", userMatricule, e.getMessage());
            }
        }

        return result;
    }

    @Transactional
    public BatchAssignmentResult assignMultipleProfilesToUser(String userMatricule,
                                                              List<AssignedProfile> assignedProfiles,
                                                              List<String> revokedProfiles,
                                                              String appCode) {

        BatchAssignmentResult result = new BatchAssignmentResult();
        String currentUserMatricule = SecurityUtils.getCurrentUserMatricule();

        // Handle Assignments
        if (assignedProfiles != null) {
            for (AssignedProfile profile : assignedProfiles) {
                try {
                    // 🔑 USE THE SINGLE SOURCE OF TRUTH METHOD
                    assignProfile(
                            currentUserMatricule,  // manager
                            userMatricule,         // user
                            profile.getProfileCode(),
                            appCode,
                            profile.getDateDebut(),
                            profile.getDateFin(),
                            profile.getEtat(),
                            ProfileAssignmentSource.CUSTOM,  // source
                            false                             // do NOT skip validation
                    );
                    result.addSuccess(profile.getProfileCode());
                } catch (Exception e) {
                    result.addFailure(profile.getProfileCode(), e.getMessage());
                }
            }
        }

        // Handle unassignments
        if (revokedProfiles != null) {
            for (String profileCode : revokedProfiles) {
                try {
                    removeProfile(userMatricule, profileCode);
                    result.addSuccess(profileCode);
                } catch (Exception e) {
                    result.addFailure(profileCode, e.getMessage());
                }
            }
        }

        return result;
    }


    // ==================== HIERARCHY & AUTHORIZATION (UNCHANGED) ====================

    public Set<String> getManagedUsers(String managerMat) {
        return hierarchyService.getAllManagedUsers(managerMat);
    }

    public Set<String> getManagedUsersCins(String managerMat) {
        return hierarchyService.getAllManagedUsersCins(managerMat);
    }

    public List<PersonnelDetailsDto> getManagedUsersWithDetails(String managerMat) {
        // Admin → see ALL users
//        if (SecurityUtils.isAdmin()) {
//            return personneRepository.findAllPersonnelDetails();
//        }

        // Non-admin → see users in own structure + all descendant structures
        Set<Integer> managedStructureIds = hierarchyService.getManagedStructureIds(managerMat);
        if (managedStructureIds.isEmpty()) {
            return Collections.emptyList();
        }

        return personneRepository.findPersonnelDetailsByStructureIds(managedStructureIds);
    }
    public boolean canManagerAssignProfile(String managerMat, String profileCode, String appCode) {
        Integer managerStructId = hierarchyService.getStructureIdForUser(managerMat);
        if (managerStructId == null) return false;

        Integer structureType = hierarchyService.getTypeForStructure(managerStructId);

        List<ProfilMenuApplication> allowedProfiles = profilMenuApplicationRepository
                .findActiveProfilesByAppAndStructureType(appCode, structureType.toString());

        return allowedProfiles.stream()
                .anyMatch(pma -> pma.getCodPflPfl().equals(profileCode));
    }

    public List<ProfilDto> getAssignableProfiles(String managerMat,
                                                 String userMatricule,
                                                 String appCode) {
        Integer managerStructId = hierarchyService.getStructureIdForUser(managerMat);
        Integer userStructId = hierarchyService.getStructureIdForUser(userMatricule);

        if (managerStructId == null || userStructId == null ||
                !hierarchyService.isDescendantOrSame(managerStructId, userStructId)) {
            return Collections.emptyList();
        }

        Integer userStructType = hierarchyService.getTypeForStructure(userStructId);
        Integer managerStructType = hierarchyService.getTypeForStructure(managerStructId);

        List<ProfilMenuApplication> allowedProfiles = profilMenuApplicationRepository
                .findActiveProfilesByAppAndStructureType(appCode, managerStructType.toString());

        int userLevel = mapToHierarchyLevel(userStructType);

        return allowedProfiles.stream()
                .map(pma -> profilServiceImpl.findById(pma.getCodPflPfl()))
                .filter(Objects::nonNull)
                .filter(profil -> {
                    int profilLevel = mapToHierarchyLevel(Integer.parseInt(profil.getCodCatpPfl()));
                    return profilLevel <= userLevel;
                })
                .toList();
    }

    public AssignmentStatistics getStatistics(String managerMat) {
        Set<String> managedUsers = getManagedUsers(managerMat);
        Integer managerStructId = hierarchyService.getStructureIdForUser(managerMat);

        AssignmentStatistics stats = new AssignmentStatistics();
        stats.setTotalManagedUsers(managedUsers.size());
        stats.setTotalStructures(hierarchyService.getAllDescendants(managerStructId).size() + 1);

        long activeProfiles = managedUsers.stream()
                .mapToLong(utilisateurProfilRepository::countActiveByUserMatricule)
                .sum();
        stats.setTotalActiveProfiles(activeProfiles);

        return stats;
    }

    private int mapToHierarchyLevel(Integer codeTypeStructure) {
        if (codeTypeStructure == null) return 0;
        return switch (codeTypeStructure) {
            case 1, 6, 7 -> 1;
            case 2 -> 3;
            case 3, 4, 5 -> 3;
            default -> 3;
        };
    }
// ═══════════════════════════════════════════════════════════════════════════
// EXTRACTED VALIDATION METHODS
// ═══════════════════════════════════════════════════════════════════════════

    private Integer performValidationIfRequired(
            String managerMat,
            String profileCode,
            String appCode,
            boolean skipValidation,
            ProfileAssignmentSource source) throws ValidationException {

        if (skipValidation) {
            logger.debug("⏭️ Validation skipped for profile {} (source: {})", profileCode, source);
            return null;
        }

        validateRequiredParametersForValidation(managerMat, appCode);
        return validateManagerAndProfileRights(managerMat, profileCode, appCode);
    }

    private void validateRequiredParametersForValidation(String managerMat, String appCode) throws ValidationException {
        if (isBlank(managerMat)) {
            throw new ValidationException("⛔ Manager matricule is required when validation is enabled");
        }
        if (isBlank(appCode)) {
            throw new ValidationException("⛔ Application code is required when validation is enabled");
        }
    }

    private Integer validateManagerAndProfileRights(
            String managerMat,
            String profileCode,
            String appCode) throws ValidationException {

        // Get manager structure
        StructureDto managerStructure = structureService.getUserStructure(managerMat);
        Integer managerStructId = managerStructure.getId();

        // Load and validate profile
        ProfilDto profil = loadAndValidateProfile(profileCode);

        // Verify profile level vs structure type
        validateProfileLevelAgainstStructure(profil, managerStructure);

        // Check manager rights
        validateManagerCanAssignProfile(profileCode, appCode);

        logger.debug("✅ Validation passed for profile {} assignment", profileCode);
        return managerStructId;
    }

    private void validateProfileLevelAgainstStructure(
            ProfilDto profil,
            StructureDto managerStructure) throws ValidationException {

        int profilLevel = Integer.parseInt(profil.getCodNivhPfl());
        if (profilLevel == 0) {
            return;
        }

        Integer userStructType = managerStructure.getCodeTypeStructure();
        if (profilLevel > userStructType) {
            throw new ValidationException(String.format(
                    "⛔ Le profil `%s` est réservé à un type de structure supérieur.",
                    profil.getLibpflpfl()));
        }
    }

    private void validateManagerCanAssignProfile(
            String profileCode,
            String appCode
    ) throws ValidationException {

        List<ProfilDto> allowedProfilesForManager = profilServiceImpl.getManagerProfiles(appCode);

        boolean canAssign = allowedProfilesForManager.stream()
                .anyMatch(p -> p.getCodPflPfl().equals(profileCode));

        if (!canAssign) {
            throw new ValidationException("⛔ Ce profil n'est pas autorisé pour le type de structure du manager.");
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
// EXTRACTED HELPER METHODS
// ═══════════════════════════════════════════════════════════════════════════
    private ProfilDto loadAndValidateProfile(String profileCode) {
        ProfilDto profilDto = profilServiceImpl.findById(profileCode);
        if (profilDto == null) {
            throw new EntityNotFoundException("Profile not found: " + profileCode);
        }
        return profilDto;
    }

    private EffectiveDates prepareEffectiveDates(Date startDate, Date endDate, Integer etat) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);

        return new EffectiveDates(
                startDate != null ? startDate : currentDate,
                endDate != null ? endDate : calendar.getTime(),
                etat != null ? etat : 1
        );
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }


    // ═══════════════════════════════════════════════════════════════════════════
// EXTRACTED CREATE/UPDATE METHODS
// ═══════════════════════════════════════════════════════════════════════════
    private AssignmentResult createOrUpdateAssignment(
            String userMat,
            String profileCode,
            ProfilDto profilDto,
            EffectiveDates dates,
            ProfileAssignmentSource source) {

        UtilisateurProfilId id = new UtilisateurProfilId(profileCode, userMat);
        Optional<UtilisateurProfil> existingOpt = utilisateurProfilRepository.findById(id);

        if (existingOpt.isEmpty()) {
            return createNewAssignment(id, profilDto, dates, source, profileCode);
        }

        return updateExistingAssignment(existingOpt.get(), dates, source, profileCode, userMat);
    }

    private AssignmentResult createNewAssignment(
            UtilisateurProfilId id,
            ProfilDto profilDto,
            EffectiveDates dates,
            ProfileAssignmentSource source,
            String profileCode) {

        UtilisateurProfil userProfil = new UtilisateurProfil();
        userProfil.setId(id);
        userProfil.setProfil(profilMapper.toEntity(profilDto));
        userProfil.setBoolEtatUtpr(dates.etat());
        userProfil.setDatdadhutpr(dates.startDate());
        userProfil.setDatFadhUtpr(dates.endDate());
        userProfil.setBoolCustomProfil(source.getCustomFlag());

        logger.debug("✅ Creating new profile assignment: {} (boolCustomProfil={})",
                profileCode, source.getCustomFlag());

        return new AssignmentResult(userProfil, ProfileAssignmentResult.Status.CREATED);
    }

    private AssignmentResult updateExistingAssignment(
            UtilisateurProfil userProfil,
            EffectiveDates dates,
            ProfileAssignmentSource source,
            String profileCode,
            String userMat) {

        if (userProfil.getBoolEtatUtpr() == 0) {
            return reactivateInactiveProfile(userProfil, dates, source, profileCode);
        }

        return updateActiveProfile(userProfil, dates, profileCode, userMat);
    }

    private AssignmentResult reactivateInactiveProfile(
            UtilisateurProfil userProfil,
            EffectiveDates dates,
            ProfileAssignmentSource source,
            String profileCode) {

        userProfil.setBoolEtatUtpr(dates.etat());
        userProfil.setDatdadhutpr(dates.startDate());
        userProfil.setDatFadhUtpr(dates.endDate());

        updateCustomFlagOnReactivation(userProfil, source);

        logger.debug("🔄 Reactivating profile assignment: {}", profileCode);
        return new AssignmentResult(userProfil, ProfileAssignmentResult.Status.REACTIVATED);
    }

    private void updateCustomFlagOnReactivation(UtilisateurProfil userProfil, ProfileAssignmentSource source) {
        if (source.isCustom()) {
            userProfil.setBoolCustomProfil(ProfileAssignmentSource.CUSTOM.getCustomFlag());
            return;
        }

        // FROM_PACK: only set to 0 if it wasn't custom before
        boolean wasNotCustom = userProfil.getBoolCustomProfil() == null
                || userProfil.getBoolCustomProfil() != 1;

        if (wasNotCustom) {
            userProfil.setBoolCustomProfil(source.getCustomFlag());
        }
        // If it was custom (1), keep it as custom
    }

    private AssignmentResult updateActiveProfile(
            UtilisateurProfil userProfil,
            EffectiveDates dates,
            String profileCode,
            String userMat) {

        userProfil.setDatdadhutpr(dates.startDate());
        userProfil.setDatFadhUtpr(dates.endDate());

        logger.debug("ℹ️ Profile {} already active for user {}", profileCode, userMat);
        return new AssignmentResult(userProfil, ProfileAssignmentResult.Status.ALREADY_ACTIVE);
    }
// ═══════════════════════════════════════════════════════════════════════════
// EXTRACTED AUDIT METHOD
// ═══════════════════════════════════════════════════════════════════════════

    private void logAssignmentAudit(
            String managerMat,
            String userMat,
            String profileCode,
            Integer managerStructId,
            ProfileAssignmentSource source) {

        if (managerStructId != null) {
            List<Integer> path = hierarchyService.getPath(managerStructId, managerStructId);
            logger.info("Profile {} assigned by {} to {} through path: {} (source: {})",
                    profileCode, managerMat, userMat, path, source);
        } else {
            logger.info("Profile {} assigned to {} (source: {}, no manager validation)",
                    profileCode, userMat, source);
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // INNER CLASSES FOR DATA TRANSFER
    // ═══════════════════════════════════════════════════════════════════════════
        private record EffectiveDates(Date startDate, Date endDate, Integer etat) {
    }

    private record AssignmentResult(UtilisateurProfil userProfil, ProfileAssignmentResult.Status status) {
    }
}

