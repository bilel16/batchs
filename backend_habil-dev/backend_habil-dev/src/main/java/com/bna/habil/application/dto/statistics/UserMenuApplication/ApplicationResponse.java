package com.bna.habil.application.dto.statistics.UserMenuApplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private String codAppApp;
    private String libAppApp;
    private List<ProfilResponse> profils;
}
