package com.bna.habil.infrastructure.persistence.repositories.extra;

import com.bna.habil.domain.entities.Segment;
import com.bna.habil.domain.entities.extra.SegmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, SegmentId> {
    @Query("SELECT s FROM Segment s WHERE CONCAT(s.codeClasseSegment, '-', s.codeSousClasseSegment, '-', s.codeSegment) IN :segmentCodes")
    List<Segment> findBySegmentCodes(@Param("segmentCodes") List<String> segmentCodes);
}