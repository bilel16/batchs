package com.bna.habil.domain.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Comment;

import com.bna.habil.domain.entities.extra.SegmentId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "Segment")
@Table(name = "SEGMENT")
@IdClass(SegmentId.class)
public class Segment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "COD_CSEG_CSEG", columnDefinition = "NUMBER (1,0)")
    @Comment("Code classe Segment")
    private String codeClasseSegment;

    @Id
    @Column(name = "COD_SSEG_SSEG", columnDefinition = "NUMBER (2,0)")
    @Comment("Code sous classe Segment")
    private Integer codeSousClasseSegment;

    @Id
    @Column(name = "COD_SEG_SEG", columnDefinition = "NUMBER (3,0)")
    @Comment("Code segment")
    private Integer codeSegment;

    @Column(name = "LIB_SEG_SEG")
    @Comment("Libelle segment")
    private String libelleSegment;

    @Column(name = "DAT_MAJ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "fr-FR", timezone = "GMT+01:00")
    @jakarta.persistence.Temporal(jakarta.persistence.TemporalType.DATE)
    @Comment("date mise à jour")
    private Date dateMiseJour;


}
