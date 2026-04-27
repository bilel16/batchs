package com.bna.habil.application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UtilisateurPackDto {
    private String numMatrUser;
    private String codPackPack;
    private Date datDebAffect;
    private Date datFinAffect;
    private Integer boolEtatAffect;
    private Date datAffectPack;
    private String userAffectPack;

    // Optional: Include related entity names for display
    private String nomUser;        // User name
    private String prenomUser;     // User first name
    private String libPackPack;    // Pack label
}
