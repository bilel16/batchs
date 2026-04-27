package com.bna.habil.interfaces.controllers;

import com.bna.habil.application.dto.SegmentStructureDto;
import com.bna.habil.application.dto.StructureOptionDto;
import com.bna.habil.application.dto.StructureTypeOptionDto;
import com.bna.habil.application.dto.StructureWithSegmentsDto;
import com.bna.habil.application.services.StructureService;
import com.bna.habil.infrastructure.security.model.ResponseHabil;
import com.bna.habil.infrastructure.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/structure")
@Slf4j
@Validated
public class StructureController {

    private final StructureService structureService;

    public StructureController(StructureService structureService) {
        this.structureService = structureService;
    }

    /**
     * Get all structures with their segments
     */
    @GetMapping("/with-segments")
    public ResponseEntity<List<StructureWithSegmentsDto>> getAllStructuresWithSegments() {
        log.info("REST request to get all structures with segments");
        List<StructureWithSegmentsDto> structures = structureService.getAllStructuresWithSegments();
        return ResponseEntity.ok(structures);
    }

    /**
     * Get a specific structure with its segments
     */
    @GetMapping("/{id}/with-segments")
    public ResponseEntity<StructureWithSegmentsDto> getStructureWithSegments(
            @PathVariable Integer id) {
        log.info("REST request to get structure {} with segments", id);
        StructureWithSegmentsDto structure = structureService.getStructureWithSegments(id);
        return ResponseEntity.ok(structure);
    }

    /**
     * Add a segment to a structure
     */
    @PostMapping("/{structureId}/segments/{segmentCode}")
    public ResponseEntity<SegmentStructureDto> addSegmentToStructure(
            @PathVariable Integer structureId,
            @PathVariable String segmentCode) {
        log.info("REST request to add segment {} to structure {}", segmentCode, structureId);
        SegmentStructureDto result = structureService.addSegmentToStructure(structureId, segmentCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * Remove a segment from a structure
     */
    @DeleteMapping("/{structureId}/segments/{segmentCode}")
    public ResponseEntity<Void> removeSegmentFromStructure(
            @PathVariable Integer structureId,
            @PathVariable String segmentCode) {
        log.info("REST request to remove segment {} from structure {}", segmentCode, structureId);
        structureService.removeSegmentFromStructure(structureId, segmentCode);
        return ResponseEntity.noContent().build();
    }
    /**
     * Get all structures for dropdown filter
     */
    @GetMapping("/structure-options")
    public ResponseEntity<ResponseHabil> getStructureOptions() {
        List<StructureOptionDto> structures = structureService.getAllStructureOptions();
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, structures));
    }

    /**
     * Get all structure types for dropdown filter
     */
    @GetMapping("/structure-type-options")
    public ResponseEntity<ResponseHabil> getStructureTypeOptions() {
        List<StructureTypeOptionDto> types = structureService.getAllStructureTypeOptions();
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, types));
    }

    /**
     * Get structures filtered by type (for cascading dropdown)
     */
    @GetMapping("/structures-by-type/{typeCode}")
    public ResponseEntity<ResponseHabil> getStructuresByType(@PathVariable Integer typeCode) {
        List<StructureOptionDto> structures = structureService.getStructuresByType(typeCode);
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, structures));
    }
}