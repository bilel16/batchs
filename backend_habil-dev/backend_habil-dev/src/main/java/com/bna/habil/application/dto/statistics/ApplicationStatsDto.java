package com.bna.habil.application.dto.statistics;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApplicationStatsDto {
    private Integer totalApplications;
    private Integer totalMenus;
    private List<ApplicationMenuCountDto> perApplication;
}