package com.bna.habil.application.dto.ldap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    String matricule;
    String nom;
    String prenom;
    String email;
    String structure;

}
