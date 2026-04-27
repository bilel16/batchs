package com.bna.habil.application.dto.statistics.UserMenuApplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfilResponse {
    private String codPflPfl;
    private String libPflPfl;
    private List<MenuResponse> menus;
}
