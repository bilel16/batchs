package com.bna.habil.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRoleDTO {
    private String codPflPfl;
    private String codMenuMenu;
    private String libMenuMenu;
    private Integer boolEtatPma;

    public UserRoleDTO() {
        super();
    }

    public UserRoleDTO(String codPflPfl, String codMenuMenu, String libMenuMenu, Integer boolEtatPma) {
        super();
        this.codPflPfl = codPflPfl;
        this.codMenuMenu = codMenuMenu;
        this.libMenuMenu = libMenuMenu;
        this.boolEtatPma = boolEtatPma;
    }

}
