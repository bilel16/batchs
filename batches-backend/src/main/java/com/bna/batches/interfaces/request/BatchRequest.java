package com.bna.batches.interfaces.request;

import lombok.Data;

@Data
public class BatchRequest {
    /** CHEQUE | EFFET | PRELEVEMENT */
    private String instrument;
    /** INSERTION | POSITION */
    private String phase;
    /** true = simulate, no DB writes */
    private boolean dryRun;
    /** send email notification on completion */
    private boolean notifyEmail;
    /** max retry attempts per agency (0 = no retry) */
    private int retryMax;
}
