package com.bna.habil.application.dto.statistics;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApplicationProfileCountDto {
    private String applicationCode;
    private String applicationLabel;
    private Integer totalProfiles;
    private Integer activeProfiles;
    private Integer inactiveProfiles;
}