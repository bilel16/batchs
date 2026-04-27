package com.bna.habil.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPacksAssignmentRequest {
    private String userMatricule;
    private List<AssignedPack> assignedPacks;
    private List<String> revokedPacks;
}