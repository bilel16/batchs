package com.bna.habil.application.dto.statistics;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileStatsDto {
    private Integer totalProfiles;
    private Integer activeProfiles;
    private Integer inactiveProfiles;
    private List<ApplicationProfileCountDto> perApplication;
}