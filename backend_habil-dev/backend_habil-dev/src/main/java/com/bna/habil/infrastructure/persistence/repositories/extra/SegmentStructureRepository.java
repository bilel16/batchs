package com.bna.habil.infrastructure.persistence.repositories.extra;

import com.bna.habil.domain.entities.SegmentStructure;
import com.bna.habil.domain.entities.entitiesId.SegmentStructureId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SegmentStructureRepository extends JpaRepository<SegmentStructure, SegmentStructureId> {

    List<SegmentStructure> findByIdCodStrcStrc(Integer structureId);

    void deleteByIdCodStrcStrcAndIdCodIpSegs(Integer structureId, String segmentCode);

    boolean existsByIdCodStrcStrcAndIdCodIpSegs(Integer structureId, String segmentCode);
}