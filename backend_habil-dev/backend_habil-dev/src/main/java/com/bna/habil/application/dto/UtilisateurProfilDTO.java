package com.bna.habil.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UtilisateurProfilDTO {

    // Getters & setters
    private String numMatrUser;
    private String codPflPfl;
    private Date datFadhUtpr;
    private Date datdadhutpr;
    private Integer boolEtatUtpr;

    private ProfilDto profil;


}
