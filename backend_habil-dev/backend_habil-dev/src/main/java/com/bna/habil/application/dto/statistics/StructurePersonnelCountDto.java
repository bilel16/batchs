package com.bna.habil.application.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StructurePersonnelCountDto {

    private Integer structureId;
    private Long total;
    private Long active;
    private Long inactive;
}