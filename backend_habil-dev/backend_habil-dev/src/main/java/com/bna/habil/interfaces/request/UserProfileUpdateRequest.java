package com.bna.habil.interfaces.request;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileUpdateRequest {
    private String userMatricule;
    private String appCode;
    private List<ProfileOperation> operations;
    private List<String> revokedProfiles;
}