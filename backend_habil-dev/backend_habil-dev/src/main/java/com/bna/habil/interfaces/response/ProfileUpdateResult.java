package com.bna.habil.interfaces.response;

import com.bna.habil.interfaces.request.OperationType;
import com.bna.habil.interfaces.request.ProfileOperation;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ProfileUpdateResult {
    private boolean success = true;
    private String globalError;
    private List<OperationResult> results = new ArrayList<>();
    private Map<OperationType, Integer> summary = new HashMap<>();

    public void addSuccess(ProfileOperation operation) {
        results.add(new OperationResult(operation, true, null));
        summary.merge(operation.getType(), 1, Integer::sum);
    }

    public void addFailure(ProfileOperation operation, String error) {
        results.add(new OperationResult(operation, false, error));
        success = false;
    }

    public void setGlobalError(String error) {
        this.globalError = error;
        this.success = false;
    }

    public int getAddedCount() {
        return summary.getOrDefault(OperationType.ADD, 0);
    }

    public int getRevokedCount() {
        return summary.getOrDefault(OperationType.REVOKE, 0);
    }

    public int getUpdatedCount() {
        return summary.getOrDefault(OperationType.UPDATE, 0);
    }
}