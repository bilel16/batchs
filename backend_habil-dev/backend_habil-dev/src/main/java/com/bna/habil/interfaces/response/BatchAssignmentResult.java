package com.bna.habil.interfaces.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * Result of batch assignment operations (profiles or packs)
 */
@Data
public class BatchAssignmentResult {
    private List<String> successful = new ArrayList<>();
    private Map<String, String> failed = new HashMap<>();
    private int totalProcessed;
    // Detailed results for more granular tracking
    private int createdCount = 0;
    private int reactivatedCount = 0;
    private int alreadyActiveCount = 0;
    private int deactivatedCount = 0;

    public void addSuccess(String matricule) {
        successful.add(matricule);
        totalProcessed++;
    }

    public void addFailure(String matricule, String reason) {
        failed.put(matricule, reason);
        totalProcessed++;
    }

    public int getSuccessCount() {
        return successful.size();
    }

    public int getFailureCount() {
        return failed.size();
    }

    /**
     * Apply results from ProfileAssignmentResult list
     */
    public void applyResults(List<ProfileAssignmentResult> results) {
        for (ProfileAssignmentResult result : results) {
            if (result.isSuccess()) {
                addSuccess(result.getProfileCode());

                switch (result.getStatus()) {
                    case CREATED:
                        createdCount++;
                        break;
                    case REACTIVATED:
                        reactivatedCount++;
                        break;
                    case ALREADY_ACTIVE:
                        alreadyActiveCount++;
                        break;
                    case DEACTIVATED:
                        deactivatedCount++;
                        break;
                    default:
                        break;
                }
            } else {
                addFailure(result.getProfileCode(), result.getMessage());
            }
        }
    }

    /**
     * Get summary string for logging
     */
    public String getSummary() {
        return String.format(
                "Total: %d | Success: %d (Created: %d, Reactivated: %d, Already Active: %d, Deactivated: %d) | Failed: %d",
                totalProcessed, getSuccessCount(), createdCount, reactivatedCount,
                alreadyActiveCount, deactivatedCount, getFailureCount()
        );
    }
}

