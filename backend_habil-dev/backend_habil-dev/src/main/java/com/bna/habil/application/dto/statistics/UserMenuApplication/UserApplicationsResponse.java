package com.bna.habil.application.dto.statistics.UserMenuApplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserApplicationsResponse {
    private String matricule;
    private String nom_prn;
    private List<ApplicationResponse> applications;
}
