package com.bna.habil.application.services.impl;


import com.bna.habil.application.dto.*;
import com.bna.habil.application.enums.StructureTypeEnum;
import com.bna.habil.application.mappers.SegmentStructureMapper;
import com.bna.habil.application.services.crud.AbstractCrudService;
import com.bna.habil.domain.entities.SegmentStructure;
import com.bna.habil.domain.entities.entitiesId.SegmentStructureId;
import com.bna.habil.domain.exceptions.EntityNotFoundException;
import com.bna.habil.domain.exceptions.ValidationException;
import com.bna.habil.infrastructure.persistence.repositories.extra.SegmentStructureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bna.habil.application.mappers.StructureMapper;
import com.bna.habil.application.services.StructureService;
import com.bna.habil.domain.entities.Structure;
import com.bna.habil.infrastructure.persistence.repositories.StructureCustomRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StructureServiceImpl
        extends AbstractCrudService<Structure, StructureDto, Integer>
        implements StructureService {

    private static final Logger logger = LoggerFactory.getLogger(StructureServiceImpl.class);
    private final StructureCustomRepository structureRepository;
    private final SegmentStructureRepository segmentStructureRepository;
    private final StructureMapper structureMapper;
    private final SegmentStructureMapper segmentStructureMapper;


    public StructureServiceImpl(StructureCustomRepository repository, StructureMapper mapper, SegmentStructureRepository segmentStructureRepository, StructureMapper structureMapper, SegmentStructureMapper segmentStructureMapper) {
        super(
                repository,
                mapper,
                // ID Extractor
                StructureDto::getId,
                // ID Stringifier
                id -> "StructureId=" + id,
                // Create Validator
                dto -> {
                    if (dto.getCodeTypeStructure() == null) {
                        throw new ValidationException("Structure type code cannot be null");
                    }
                    // Add other validations as needed
                    validateStructureData(dto);
                },
                // Update Validator
                (id, dto, existing) -> {
                    logger.debug("Validating update for Structure with ID: {}", id);
                    validateStructureData(dto);
                }
        );
        this.structureRepository = repository;

        this.segmentStructureRepository = segmentStructureRepository;
        this.structureMapper = structureMapper;
        this.segmentStructureMapper = segmentStructureMapper;
    }

    @Override
    protected String getEntityName() {
        return "Structure";
    }

    /**
     * Custom validation logic for Structure
     */
    private static void validateStructureData(StructureDto dto) {
        if (dto.getCodeTypeStructure() == null) {
            throw new ValidationException("Structure type code cannot be null");
        }
        // Add other validations as needed
    }

    /**
     * Get all structures formatted for dropdown
     */
    public List<StructureOptionDto> getAllStructureOptions() {
        return structureRepository.findAllStructuresWithTypes()
                .stream()
                .map(row -> {
                    Integer id = ((Number) row[0]).intValue();
                    String label = (String) row[1];
                    Integer typeCode = row[2] != null ? ((Number) row[2]).intValue() : null;
                    String typeName = StructureTypeEnum.getLabelByCode(typeCode);

                    return new StructureOptionDto(id, label, typeCode, typeName);
                })
                .collect(Collectors.toList());
    }

    /**
     * Get all structure types formatted for dropdown
     */
    public List<StructureTypeOptionDto> getAllStructureTypeOptions() {
        return StructureTypeEnum.getAllOptions();
    }

    /**
     * Get structures filtered by type code
     */
    public List<StructureOptionDto> getStructuresByType(Integer typeCode) {
        return structureRepository.findByTypeCode(typeCode)
                .stream()
                .map(s -> new StructureOptionDto(
                        s.getId(),
                        s.getLibelleStructure(),
                        s.getCodeTypeStructure(),
                        StructureTypeEnum.getLabelByCode(s.getCodeTypeStructure())
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StructureDto getUserStructure(String userMatricule) {
        logger.info("Getting structure for user matricule: {}", userMatricule);

        if (userMatricule == null || userMatricule.trim().isEmpty()) {
            throw new ValidationException("User matricule cannot be empty");
        }

        Structure structure = structureRepository.findStructureByUserMatricule(userMatricule);

        if (structure == null) {
            throw new EntityNotFoundException(
                    String.format("Structure not found for user matricule: %s", userMatricule)
            );
        }

        return mapper.toDto(structure);
    }

    @Transactional(readOnly = true)
    @Override
    public Map<Integer, String> getStructureLabels(List<Integer> structureIds) {
        logger.info("Fetching labels for {} structures", structureIds.size());

        if (structureIds.isEmpty()) {
            return Collections.emptyMap();
        }

        // Single query to fetch all structures
        List<Structure> structures = structureRepository.findByIdIn(structureIds);

        // Convert to Map<ID, Label> for O(1) lookup
        return structures.stream()
                .collect(Collectors.toMap(
                        Structure::getId,
                        s -> s.getLibelleStructure() != null ? s.getLibelleStructure() : "N/A"
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StructureWithSegmentsDto> getAllStructuresWithSegments() {
        logger.info("Getting all structures with their segments");

        List<Structure> structures = structureRepository.findAll();

        return structures.stream()
                .map(this::mapToStructureWithSegments)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StructureWithSegmentsDto getStructureWithSegments(Integer structureId) {
        logger.info("Getting structure with segments for ID: {}", structureId);

        Structure structure = structureRepository.findById(structureId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Structure not found with ID: %s", structureId)
                ));

        return mapToStructureWithSegments(structure);
    }

    @Override
    @Transactional
    public SegmentStructureDto addSegmentToStructure(Integer structureId, String segmentCode) {
        logger.info("Adding segment {} to structure {}", segmentCode, structureId);

        // Validate structure exists
        if (!structureRepository.existsById(structureId)) {
            throw new EntityNotFoundException(
                    String.format("Structure not found with ID: %s", structureId)
            );
        }

        // Validate segment code format - must be a number between 0 and 255
        if (segmentCode == null || segmentCode.trim().isEmpty()) {
            throw new ValidationException("Segment code cannot be empty");
        }

        try {
            int segmentValue = Integer.parseInt(segmentCode.trim());
            if (segmentValue < 0 || segmentValue > 255) {
                throw new ValidationException("Segment code must be between 0 and 255 (IPv4 range)");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid segment code format. Expected: a number between 0 and 255");
        }

        // Check if association already exists
        SegmentStructureId id = new SegmentStructureId(structureId, segmentCode);
        if (segmentStructureRepository.existsById(id)) {
            throw new ValidationException(
                    String.format("Segment %s is already associated with structure %s",
                            segmentCode, structureId)
            );
        }

        // Create and save the association
        SegmentStructure segmentStructure = new SegmentStructure();
        segmentStructure.setId(id);

        SegmentStructure saved = segmentStructureRepository.save(segmentStructure);

        logger.info("Successfully added segment {} to structure {}", segmentCode, structureId);

        return segmentStructureMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void removeSegmentFromStructure(Integer structureId, String segmentCode) {
        logger.info("Removing segment {} from structure {}", segmentCode, structureId);

        SegmentStructureId id = new SegmentStructureId(structureId, segmentCode);

        if (!segmentStructureRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format("Association not found between structure %s and segment %s",
                            structureId, segmentCode)
            );
        }

        segmentStructureRepository.deleteById(id);

        logger.info("Successfully removed segment {} from structure {}", segmentCode, structureId);
    }


    private StructureWithSegmentsDto mapToStructureWithSegments(Structure structure) {
        StructureWithSegmentsDto dto = structureMapper.toStructureWithSegmentsDto(structure);
        List<SegmentStructure> segmentStructures =
                segmentStructureRepository.findByIdCodStrcStrc(structure.getId());

        List<String> segmentCodes = segmentStructures.stream()
                .map(ss -> ss.getId().getCodIpSegs())
                .toList();

        dto.setSegments(segmentCodes);

        return dto;
    }
}