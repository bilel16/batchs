package com.bna.habil.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfilDto {

    private String codPflPfl;

    private String libpflpfl;

    private String libhdebpfl;

    private String libhfinpfl;

    private String codNivhPfl;

    private String boolEtatPfl;
    private String boolJouvPfl;

    private String codAppApp;

    private String codCatpPfl;

    @Override
    public String toString() {
        return "ProfilDto [codPflPfl=" + codPflPfl + ", libpflpfl=" + libpflpfl + ", libhdebpfl=" + libhdebpfl
                + ", libhfinpfl=" + libhfinpfl + ", codNivhPfl=" + codNivhPfl + ", boolEtatPfl=" + boolEtatPfl
                + ", boolJouvPfl=" + boolJouvPfl + ", codAppApp=" + codAppApp + ", codCatpPfl=" + codCatpPfl + "]";
    }


}
