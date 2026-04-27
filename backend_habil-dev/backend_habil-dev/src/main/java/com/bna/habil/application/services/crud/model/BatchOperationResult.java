package com.bna.habil.application.services.crud.model;


import com.bna.habil.domain.exceptions.BatchOperationException;

import java.util.List;

/**
 * Result container for batch operations
 */
public record BatchOperationResult<D>(List<D> successful, List<BatchOperationException.BatchError> failed,
                                      BatchMode mode) {
    public enum BatchMode {
        ALL_OR_NOTHING,  // Transaction rollback on any failure
        BEST_EFFORT      // Continue processing despite failures
    }

    public boolean hasFailures() {
        return !failed.isEmpty();
    }

    public boolean isPartialSuccess() {
        return !successful.isEmpty() && !failed.isEmpty();
    }

}