package com.bna.habil.interfaces.response;

import com.bna.habil.interfaces.request.ProfileOperation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationResult {
    private ProfileOperation operation;
    private boolean success;
    private String error;
}