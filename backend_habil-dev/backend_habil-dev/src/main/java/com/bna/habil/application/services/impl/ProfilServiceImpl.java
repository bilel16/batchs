package com.bna.habil.application.services.impl;

import java.util.ArrayList;
import java.util.List;


import com.bna.habil.application.dto.statistics.ApplicationProfileCountDto;
import com.bna.habil.application.dto.statistics.ProfileStatsDto;
import com.bna.habil.application.services.crud.AbstractCrudService;
import com.bna.habil.domain.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bna.habil.application.dto.ProfilDto;
import com.bna.habil.application.dto.StructureDto;
import com.bna.habil.application.mappers.ProfilMapper;
import com.bna.habil.application.services.ProfilService;
import com.bna.habil.application.services.StructureService;
import com.bna.habil.domain.entities.Profil;
import com.bna.habil.infrastructure.persistence.repositories.extra.ProfilRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing profiles and their accessibility rules.
 */
@Service
public class ProfilServiceImpl extends AbstractCrudService<Profil, ProfilDto, String> implements ProfilService {

    private static final Logger logger = LoggerFactory.getLogger(ProfilServiceImpl.class);

    private final ProfilRepository profilRepository;
    private final StructureService structureService;
    private final ProfilMapper profilMapper;

    public ProfilServiceImpl(ProfilRepository profilRepository,
                             ProfilMapper profilMapper,
                             StructureService structureService) {
        super(
                profilRepository,
                profilMapper,
                // ID Extractor
                ProfilDto::getCodPflPfl,
                // ID Stringifier
                id -> "CodPfl=" + id,
                // Create Validator
                dto -> {
                    if (dto.getCodPflPfl() == null || dto.getCodPflPfl().trim().isEmpty()) {
                        throw new ValidationException("Profile code cannot be empty");
                    }
                    if (dto.getLibpflpfl() == null || dto.getLibpflpfl().trim().isEmpty()) {
                        throw new ValidationException("Profile name cannot be empty");
                    }
                },
                // Update Validator
                (id, dto, existing) -> {
                    logger.debug("Validating update for Profil with ID: {}", id);
                    if (dto.getLibpflpfl() == null || dto.getLibpflpfl().trim().isEmpty()) {
                        throw new ValidationException("Profile name cannot be empty");
                    }
                }
        );
        this.profilRepository = profilRepository;
        this.profilMapper = profilMapper;
        this.structureService = structureService;
    }

    @Override
    protected String getEntityName() {
        return "Profil";
    }


    @Override
    @Transactional(readOnly = true)

    public List<Profil> getProfilByCodApp(String codAppApp) throws ValidationException {
        logger.info("Getting profiles by application code: {}", codAppApp);

        if (codAppApp == null || codAppApp.trim().isEmpty()) {
            throw new ValidationException("Application code cannot be empty");
        }
        return profilRepository.findByCodAppApp(codAppApp);
    }

    /**
     * Retrieves the list of profiles available for the currently logged-in manager.
     *
     * @param appCode Application code
     * @return List of profiles accessible to the manager
     * @throws ValidationException if structure or data are invalid
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfilDto> getManagerProfiles(String appCode) throws ValidationException {
        String currentUserMatricule = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        logger.debug("Getting manager profiles for user: {} and app: {}", currentUserMatricule, appCode);

        StructureDto currentUserStructure = structureService.getUserStructure(currentUserMatricule);
        Integer codeTypeStructure = currentUserStructure.getCodeTypeStructure();

        validateStructureType(codeTypeStructure);

        // If the manager is not in a box-change | agence structure,
        // return profiles for their specific structure
        if (codeTypeStructure != 7 && codeTypeStructure != 1) {
            return getUserProfilesByStructureId(appCode, codeTypeStructure);
        }

        logger.debug("Retrieving profiles for manager [{}] with structure type [{}]",
                currentUserMatricule, codeTypeStructure);

        return getUserProfilesByStructureType(appCode, codeTypeStructure);
    }

    /**
     * Retrieves the list of profiles available for a target user.
     *
     * @param appCode             Application code
     * @param targetUserMatricule Target user matricule
     * @return List of profiles accessible to that user
     * @throws Exception if structure or data are invalid
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfilDto> getAvailableProfilesForUser(String appCode, String targetUserMatricule)
            throws ValidationException {

        logger.debug("Getting available profiles for user: {} and app: {}", targetUserMatricule, appCode);

        StructureDto targetUserStructure = structureService.getUserStructure(targetUserMatricule);
        Integer codeTypeStructure = targetUserStructure.getCodeTypeStructure();

        validateStructureType(codeTypeStructure);

        // If the user is not in a box-change | agence structure,
        // return profiles for their specific structure
        if (codeTypeStructure != 7 && codeTypeStructure != 1) {
            return getUserProfilesByStructureId(appCode, targetUserStructure.getId());
        }

        logger.debug("Retrieving available profiles for target user [{}] with structure type [{}]",
                targetUserMatricule, codeTypeStructure);

        return getUserProfilesByStructureType(appCode, codeTypeStructure);
    }

    /**
     * Retrieves the list of profiles available for a target user.
     *
     * @param appCode             Application code
     * @param targetUserMatricule Target user matricule
     * @return List of profiles accessible to that user
     * @throws ValidationException if structure or data are invalid
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfilDto> getAvailableProfilesForUserNotAssgined(String appCode, String targetUserMatricule)
            throws ValidationException {
        logger.debug("Getting available profiles (not assigned) for user: {} and app: {}",
                targetUserMatricule, appCode);

        StructureDto targetUserStructure = structureService.getUserStructure(targetUserMatricule);
        Integer codeTypeStructure = targetUserStructure.getCodeTypeStructure();

        validateStructureType(codeTypeStructure);

        // If the user is not in a box-change | agence structure,
        // return profiles for their specific structure
        if (codeTypeStructure != 7 && codeTypeStructure != 1) {
            return getUserProfilesByStructureId(appCode, targetUserStructure.getId());
        }

        logger.debug("Retrieving available profiles (not assigned) for target user [{}] with structure type [{}]",
                targetUserMatricule, codeTypeStructure);

        return getUserProfilesByStructureType(appCode, codeTypeStructure);
    }

    /**
     * Validates that a structure type code is not null.
     */
    private void validateStructureType(Integer codeTypeStructure) {
        if (codeTypeStructure == null) {
            throw new ValidationException("Structure type cannot be null in the structure table");
        }
    }


    /**
     * Retrieves profiles from repository based on app code and structure type.
     */
    private List<ProfilDto> getUserProfilesByStructureType(String appCode, Integer structureType) {
        logger.debug("Fetching profiles for app: {} and structure type: {}", appCode, structureType);

        List<Profil> profiles = profilRepository
                .findActiveProfilesByAppAndStructureTypeForUser(appCode, structureType);

        return profilMapper.toDtoList(profiles);
    }

    /**
     * Retrieves profiles from repository based on app code and specific structure ID.
     */
    private List<ProfilDto> getUserProfilesByStructureId(String appCode, Integer structureId) {
        logger.debug("Fetching profiles for app: {} and structure ID: {}", appCode, structureId);
        List<Profil> profiles = profilRepository
                .findActiveProfilesByAppAndStructureTypeForUser(appCode, structureId);

        return profilMapper.toDtoList(profiles);
    }

    @Override
    public List<ProfilDto> getProfilesByStructureId(Integer structureId) {
        logger.debug("Fetching profiles for structure ID: {}", structureId);

        List<Profil> profiles = profilRepository
                .findActiveProfilesByStructureType(structureId);

        return profilMapper.toDtoList(profiles);
    }

    @Override
    public ProfileStatsDto getProfileStatistics() {
        logger.info("Calculating profile statistics");

        // SINGLE QUERY: Get everything in one database call
        List<Object[]> results = profilRepository.getProfileStatisticsByApplication();

        // Pre-allocate with exact size (avoid ArrayList resizing)
        List<ApplicationProfileCountDto> perApplication = new ArrayList<>(results.size());

        // Use primitive types for performance (avoid autoboxing)
        int totalProfiles = 0;
        int activeProfiles = 0;
        int inactiveProfiles = 0;

        // Single-pass iteration with direct type casting
        for (Object[] row : results) {
            String appCode = (String) row[0];
            String appLabel = row[1] != null ? (String) row[1] : "N/A";

            // Direct conversion from BigDecimal (Oracle) or Long (other DBs)
            int total = ((Number) row[2]).intValue();
            int active = ((Number) row[3]).intValue();
            int inactive = ((Number) row[4]).intValue();

            // Accumulate totals
            totalProfiles += total;
            activeProfiles += active;
            inactiveProfiles += inactive;

            // Build DTO directly (no intermediate objects)
            perApplication.add(new ApplicationProfileCountDto(
                    appCode,
                    appLabel,
                    total,
                    active,
                    inactive
            ));
        }

        logger.info("Profile statistics: {} total ({} active, {} inactive) across {} applications",
                totalProfiles, activeProfiles, inactiveProfiles, results.size());

        return new ProfileStatsDto(
                totalProfiles,
                activeProfiles,
                inactiveProfiles,
                perApplication
        );
    }
}
