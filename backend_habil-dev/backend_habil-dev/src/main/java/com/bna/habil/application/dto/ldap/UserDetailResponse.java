package com.bna.habil.application.dto.ldap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponse {
    private String matricule;
    private String cin;
    private String nom;
    private String prenom;
    private String displayName;
    private String email;
    private String structure;
    private String division;
    private String adresse;
    private String description;
    private String userPrincipalName;
    private String cn;
}