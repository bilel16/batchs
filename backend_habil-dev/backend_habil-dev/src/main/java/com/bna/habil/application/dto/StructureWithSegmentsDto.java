package com.bna.habil.application.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StructureWithSegmentsDto {
    private Integer id;
    private String libelleStructure;
    private Integer codeTypeStructure;
    private Integer codeStructureMere;
    private String libMailStrc;
    private String codCatStrc;
    private List<String> segments;
}