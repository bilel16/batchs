package com.bna.habil.application.services.impl;

import com.bna.habil.application.dto.PackDto;
import com.bna.habil.application.dto.StructureDto;
import com.bna.habil.application.mappers.PackMapper;
import com.bna.habil.application.services.PackService;
import com.bna.habil.application.services.StructureService;
import com.bna.habil.application.services.crud.AbstractCrudService;
import com.bna.habil.domain.entities.Pack;
import com.bna.habil.infrastructure.persistence.repositories.extra.PackRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class PackServiceImpl extends AbstractCrudService<Pack, PackDto, String>
        implements PackService {

    private final PackRepository packRepository;
    private final PackMapper packMapper;
    private final StructureService structureService;

    public PackServiceImpl(@Qualifier("packRepository") PackRepository repository,
                           PackMapper mapper,
                           StructureService structureService) {
        super(
                repository,
                mapper,
                // ID Extractor
                PackDto::getCodPackPack,
                // ID Stringifier
                id -> "CodPack=" + id,
                // Create Validator
                dto -> {
                    if (dto.getCodPackPack() == null || dto.getCodPackPack().trim().isEmpty()) {
                        throw new ValidationException("Pack code cannot be empty");
                    }
                    if (dto.getLibPackPack() == null || dto.getLibPackPack().trim().isEmpty()) {
                        throw new ValidationException("Pack label cannot be empty");
                    }
                },
                // Update Validator
                (id, dto, existing) -> {
                    log.debug("Validating update for Pack: {}", id);
                    if (dto.getLibPackPack() == null || dto.getLibPackPack().trim().isEmpty()) {
                        throw new ValidationException("Pack label cannot be empty");
                    }
                }
        );
        this.packRepository = repository;
        this.packMapper = mapper;
        this.structureService = structureService;
    }

    @Override
    protected String getEntityName() {
        return "Pack";
    }

    /**
     * Override create to set user creator
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PackDto create(PackDto dto) {
        // Set the current user as creator
        if (dto.getUserCrePack() == null) {
            dto.setUserCrePack(getCurrentUsername());
        }
        return super.create(dto);
    }

    /**
     * Get active packs only
     */
    @Transactional(readOnly = true)
    @Override
    public List<PackDto> getActivePacks() {
        log.info("Getting all active packs");
        List<Pack> activePacks = packRepository.findByBoolActifPack(1);
        return packMapper.toDtoList(activePacks);
    }

    /**
     * Get packs by hierarchical level
     */
    @Transactional(readOnly = true)
    @Override
    public List<PackDto> getPacksByNiveauHierarchique(String codNivhPfl) {
        log.info("Getting packs by hierarchical level: {}", codNivhPfl);

        if (codNivhPfl == null || codNivhPfl.trim().isEmpty()) {
            throw new ValidationException("Hierarchical level code cannot be empty");
        }

        List<Pack> packs = packRepository.findByCodNivhPfl(codNivhPfl);
        return packMapper.toDtoList(packs);
    }

    /**
     * Get packs by category
     */
    @Transactional(readOnly = true)
    @Override
    public List<PackDto> getPacksByCategorie(String codCatpPfl) {
        log.info("Getting packs by category: {}", codCatpPfl);

        if (codCatpPfl == null || codCatpPfl.trim().isEmpty()) {
            throw new ValidationException("Category code cannot be empty");
        }

        List<Pack> packs = packRepository.findByCodCatpPfl(codCatpPfl);
        return packMapper.toDtoList(packs);
    }

    /**
     * Get manager packs - similar to getManagerProfiles
     */
    @Transactional(readOnly = true)
    @Override
    public List<PackDto> getManagerPacks() throws ValidationException {
        String currentUserMatricule = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        log.debug("Getting manager packs for user: {}", currentUserMatricule);

        StructureDto currentUserStructure = structureService.getUserStructure(currentUserMatricule);
        Integer codeTypeStructure = currentUserStructure.getCodeTypeStructure();

        validateStructureType(codeTypeStructure);

        // If the manager is not in a box-change | agence structure,
        // return packs for their specific structure
        if (codeTypeStructure != 7 && codeTypeStructure != 1) {
            return getPacksByStructureId(currentUserStructure.getId());
        }

        log.debug("Retrieving packs for manager [{}] with structure type [{}]",
                currentUserMatricule, codeTypeStructure);

        return getPacksByStructureType(codeTypeStructure);
    }

    /**
     * Get available packs for a target user
     */
    @Transactional(readOnly = true)
    @Override
    public List<PackDto> getAvailablePacksForUser(String targetUserMatricule)
            throws ValidationException {

        log.debug("Getting available packs for user: {}", targetUserMatricule);

        StructureDto targetUserStructure = structureService.getUserStructure(targetUserMatricule);
        Integer codeTypeStructure = targetUserStructure.getCodeTypeStructure();

        validateStructureType(codeTypeStructure);

        // If the user is not in a box-change | agence structure,
        // return packs for their specific structure
        if (codeTypeStructure != 7 && codeTypeStructure != 1) {
            return getPacksByStructureId(targetUserStructure.getId());
        }

        log.debug("Retrieving available packs for target user [{}] with structure type [{}]",
                targetUserMatricule, codeTypeStructure);

        return getPacksByStructureType(codeTypeStructure);
    }

    /**
     * Get available packs for user (not assigned)
     */
    @Transactional(readOnly = true)
    @Override
    public List<PackDto> getAvailablePacksForUserNotAssigned(String targetUserMatricule)
            throws ValidationException {
        log.debug("Getting available packs (not assigned) for user: {}", targetUserMatricule);

        StructureDto targetUserStructure = structureService.getUserStructure(targetUserMatricule);
        Integer codeTypeStructure = targetUserStructure.getCodeTypeStructure();

        validateStructureType(codeTypeStructure);

        if (codeTypeStructure != 7 && codeTypeStructure != 1) {
            return getPacksByStructureId(targetUserStructure.getId());
        }

        log.debug("Retrieving available packs (not assigned) for target user [{}] with structure type [{}]",
                targetUserMatricule, codeTypeStructure);

        return getPacksByStructureType(codeTypeStructure);
    }

    // ==================== PRIVATE HELPER METHODS ====================

    private void validateStructureType(Integer codeTypeStructure) {
        if (codeTypeStructure == null) {
            throw new ValidationException("Structure type cannot be null in the structure table");
        }
    }

    private List<PackDto> getPacksByStructureType(Integer structureType) {
        log.debug("Fetching packs for structure type: {}", structureType);

        List<Pack> packs = packRepository
                .findActivePacksByStructureType(structureType);

        return packMapper.toDtoList(packs);
    }

    private List<PackDto> getPacksByStructureId(Integer structureId) {
        log.debug("Fetching packs for structure ID: {}", structureId);

        List<Pack> packs = packRepository
                .findActivePacksByStructureType(structureId);

        return packMapper.toDtoList(packs);
    }

    private String getCurrentUsername() {
        try {
            return SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();
        } catch (Exception e) {
            log.warn("Could not get current username, using SYSTEM");
            return "SYSTEM";
        }
    }
}