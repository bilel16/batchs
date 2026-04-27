package com.bna.habil.interfaces.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfilesAssignmentRequest {
    private String userMatricule;
    private String appCode;
    private List<AssignedProfile> assignedProfiles;
    private List<String> revokedProfiles;

}