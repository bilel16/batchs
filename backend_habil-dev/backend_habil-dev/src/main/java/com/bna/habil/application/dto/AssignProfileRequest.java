package com.bna.habil.application.dto;

import lombok.Data;

@Data
public class AssignProfileRequest {
    private String managerMatricule;
    private String userMatricule;
    private String profileCode;
    private String appCode;
}