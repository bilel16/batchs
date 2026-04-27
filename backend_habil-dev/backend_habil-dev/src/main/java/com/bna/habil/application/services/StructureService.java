package com.bna.habil.application.services;


import java.util.List;
import java.util.Map;

import com.bna.habil.application.dto.*;
import com.bna.habil.application.services.crud.CrudService;
import com.bna.habil.domain.exceptions.ValidationException;
import org.springframework.transaction.annotation.Transactional;


public interface StructureService extends CrudService<StructureDto, Integer> {

    StructureDto getUserStructure(String userMatricule) throws ValidationException;

    @Transactional(readOnly = true)
    Map<Integer, String> getStructureLabels(List<Integer> structureIds);

    @Transactional(readOnly = true)
    List<StructureWithSegmentsDto> getAllStructuresWithSegments();

    @Transactional(readOnly = true)
    StructureWithSegmentsDto getStructureWithSegments(Integer structureId);

    @Transactional
    SegmentStructureDto addSegmentToStructure(Integer structureId, String segmentCode);

    @Transactional
    void removeSegmentFromStructure(Integer structureId, String segmentCode);

    List<StructureOptionDto> getAllStructureOptions();

    List<StructureTypeOptionDto> getAllStructureTypeOptions();

    List<StructureOptionDto> getStructuresByType(Integer typeCode);
}
