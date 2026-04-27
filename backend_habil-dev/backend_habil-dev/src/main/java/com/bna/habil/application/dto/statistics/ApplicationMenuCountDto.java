package com.bna.habil.application.dto.statistics;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApplicationMenuCountDto {
    private String applicationCode;
    private String applicationLabel;
    private Integer menuCount;
}