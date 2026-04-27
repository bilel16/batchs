package com.bna.habil.interfaces.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public class JwtResponse implements Serializable {
    private String username;
    private String nom;
    private String prenom;
    private String codeStructure;
    private String email;

    private final String token;
    @Serial
    private static final long serialVersionUID = -8091879091924046844L;
    private String roles;

    private String poste;
    private String codePoste;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCodeStructure() {
        return codeStructure;
    }

    public void setCodeStructure(String codeStructure) {
        this.codeStructure = codeStructure;
    }

    public JwtResponse(String token, String username, String roles, String nom, String prenom,
                       String codeStructure, String email, String poste, String codePoste) {
        super();
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.codeStructure = codeStructure;
        this.token = token;
        this.roles = roles;
        this.email = email;
        this.poste = poste;
        this.codePoste = codePoste;
    }

    public String getToken() {
        return this.token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getCodePoste() {
        return codePoste;
    }

    public void setCodePoste(String codePoste) {
        this.codePoste = codePoste;
    }
}