package com.bna.batches.interfaces.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStatusMessage {
    private String executionId;
    /** STARTED | RUNNING | COMPLETED | FAILED | STOPPED */
    private String status;
    private String instrument;
    private String phase;
    private int globalProgress;
    private int totalAgences;
    private int doneAgences;
    private int errorAgences;
    private String message;
}
