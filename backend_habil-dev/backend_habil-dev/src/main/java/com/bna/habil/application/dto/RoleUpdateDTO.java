package com.bna.habil.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleUpdateDTO {
    private String codPflPfl;
    private String codMenuMenu;
    private Integer boolEtatPma; // 1 or 0

    public RoleUpdateDTO(String codPflPfl, String codMenuMenu, Integer boolEtatPma) {
        super();
        this.codPflPfl = codPflPfl;
        this.codMenuMenu = codMenuMenu;
        this.boolEtatPma = boolEtatPma;
    }

    public RoleUpdateDTO() {
        super();
    }


}
