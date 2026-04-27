package com.bna.habil.interfaces.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkAssignmentRequest {
    private String managerMatricule;
    private List<String> userMatricules;
    private String profileCode;
    private String appCode;
}