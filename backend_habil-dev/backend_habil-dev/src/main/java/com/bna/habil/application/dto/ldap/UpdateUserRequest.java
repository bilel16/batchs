package com.bna.habil.application.dto.ldap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private String prenom;
    private String nom;
    private String cin;
    private String email;
    private String adresse;
    private String structure;
    private String division;
    private String description;
    private String password;
}