package com.bna.habil.domain.entities;

import com.bna.habil.domain.entities.entitiesId.SegmentStructureId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SEGMENTS_STRUCTURE", schema = "habil")
public class SegmentStructure implements java.io.Serializable {

    @EmbeddedId
    private SegmentStructureId id;
}