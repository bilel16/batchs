package com.bna.habil.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonnelDto {
    private String matricule;
    private Boolean active;
    private Integer structureId;
    private String structureName;
    private Integer structureType;
    private String cin;
}