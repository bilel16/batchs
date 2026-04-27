package com.bna.batches.interfaces.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsDto {
    private long totalCheque;
    private long totalEffet;
    private long totalPrelevement;
    private double healthScore;
    private String lastExecution;
    private String lastExecutionStatus;
    private int activeJobs;
    private List<BatchExecutionDto> recentExecutions;
    private List<DailyVolumeDto> volumeTrend;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DailyVolumeDto {
        private String date;
        private long cheque;
        private long effet;
        private long prelevement;
    }
}
