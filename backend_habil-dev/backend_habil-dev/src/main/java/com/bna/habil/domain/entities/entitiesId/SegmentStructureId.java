package com.bna.habil.domain.entities.entitiesId;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SegmentStructureId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "cod_strc_strc")
    private Integer codStrcStrc;

    @Column(name = "cod_ip_segs")
    private String codIpSegs;
}