package com.bna.habil.application.dto.statistics;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonnelStatsDto {

    private Long total;      // total personnel (global)
    private Long active;     // active personnel (global)
    private Long inactive;   // inactive personnel (global)
    private List<StructurePersonnelCountDto> perStructure; // per structure details
}