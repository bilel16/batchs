package com.bna.habil.application.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SegmentDto {
    private String codeClasseSegment;
    private Integer codeSousClasseSegment;
    private Integer codeSegment;
    private String libelleSegment;
    private Date dateMiseJour;
}