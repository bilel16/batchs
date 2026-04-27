package com.bna.batches.interfaces.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchExecutionDto {
    private String executionId;
    private String instrument;
    private String phase;
    private String status;
    private String launchedBy;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean dryRun;
    private int totalAgences;
    private int doneAgences;
    private int errorAgences;
    private String endMessage;
    private List<AgenceProgressMessage> agenceDetails;
}
