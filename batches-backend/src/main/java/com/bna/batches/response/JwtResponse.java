package com.bna.batches.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String username;
    private String profils;     // JSON string of profil list
    private String nom;
    private String prenom;
    private String codeStructure;
    private String email;
    private String poste;
    private String codePoste;
}
