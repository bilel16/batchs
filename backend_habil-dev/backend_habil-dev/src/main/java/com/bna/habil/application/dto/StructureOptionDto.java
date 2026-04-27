package com.bna.habil.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StructureOptionDto {
    private Integer id;
    private String label;
    private Integer typeCode;
    private String typeName;
}