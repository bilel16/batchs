package com.bna.habil.interfaces.response;

import com.bna.habil.application.dto.ProfileConflictDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncResult {
    private int usersUpdated;
    private int profilesAdded;
    private int profilesRemoved;
    private List<String> addedProfileCodes;
    private List<String> removedProfileCodes;
    private List<UserSyncDetail> userDetails;
    private List<ProfileConflictDto> conflicts;

    public SyncResult(int usersUpdated, int profilesAdded, int profilesRemoved) {
        this.usersUpdated = usersUpdated;
        this.profilesAdded = profilesAdded;
        this.profilesRemoved = profilesRemoved;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSyncDetail {
        private String matricule;
        private int profilesAdded;
        private int profilesRemoved;
        private boolean success;
        private String errorMessage;
    }
}