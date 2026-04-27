package com.bna.habil.application.dto.ldap;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRequest {
    @NotBlank
    String matricule;
    @NotBlank String cin;
    @NotBlank String nom;
    @NotBlank String prenom;
    @NotBlank String structure;
    String adresse;
    @Email
    String email;
    String description;
    String division;
    String password;
}
