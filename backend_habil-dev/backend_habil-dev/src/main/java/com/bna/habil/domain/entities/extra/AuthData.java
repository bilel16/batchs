package com.bna.habil.domain.entities.extra;

import java.util.HashSet;
import java.util.Set;

import com.bna.habil.domain.beans.ProfilHabil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthData {

    @JsonProperty
    private String codeStructure;
    @JsonProperty
    private String typeStructure;
    @JsonProperty
    private String libStructure;
    @JsonProperty
    private String nom;
    @JsonProperty
    private String prenom;
    @JsonProperty
    private Integer codeBct;
    @JsonProperty
    private Integer structureMere;
    @JsonProperty
    private String dateComptable;
    @JsonProperty
    private String dateJournee;
    @JsonProperty
    private String mail;
    @JsonProperty
    private Set<ProfilHabil> profils = new HashSet<ProfilHabil>();
    @JsonProperty
    private Set<String> ressources;
    @JsonProperty
    private String poste;
    @JsonProperty
    private String codePoste;

    public String getCodeStructure() {
        return codeStructure;
    }

    public void setCodeStructure(String codeStructure) {
        this.codeStructure = codeStructure;
    }

    public String getTypeStructure() {
        return typeStructure;
    }

    public void setTypeStructure(String typeStructure) {
        this.typeStructure = typeStructure;
    }

    public String getLibStructure() {
        return libStructure;
    }

    public void setLibStructure(String libStructure) {
        this.libStructure = libStructure;
    }

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

    public Integer getCodeBct() {
        return codeBct;
    }

    public void setCodeBct(Integer codeBct) {
        this.codeBct = codeBct;
    }

    public Integer getStructureMere() {
        return structureMere;
    }

    public void setStructureMere(Integer structureMere) {
        this.structureMere = structureMere;
    }

    public String getDateComptable() {
        return dateComptable;
    }

    public void setDateComptable(String dateComptable) {
        this.dateComptable = dateComptable;
    }

    public String getDateJournee() {
        return dateJournee;
    }

    public void setDateJournee(String dateJournee) {
        this.dateJournee = dateJournee;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<ProfilHabil> getProfils() {
        return profils;
    }

    public void setProfils(Set<ProfilHabil> profils) {
        this.profils = profils;
    }

    public Set<String> getRessources() {
        return ressources;
    }

    public void setRessources(Set<String> ressources) {
        this.ressources = ressources;
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

    public AuthData(String codeStructure, String typeStructure, String libStructure, String nom, String prenom,
                    Integer codeBct, Integer structureMere, String dateComptable, String dateJournee, String mail,
                    Set<ProfilHabil> profils, Set<String> ressources, String poste, String codePoste) {
        super();
        this.codeStructure = codeStructure;
        this.typeStructure = typeStructure;
        this.libStructure = libStructure;
        this.nom = nom;
        this.prenom = prenom;
        this.codeBct = codeBct;
        this.structureMere = structureMere;
        this.dateComptable = dateComptable;
        this.dateJournee = dateJournee;
        this.mail = mail;
        this.profils = profils;
        this.ressources = ressources;
        this.poste = poste;
        this.codePoste = codePoste;
    }

    public AuthData() {
        super();
    }

    @Override
    public String toString() {
        return "AuthData{" +
                "codeStructure='" + codeStructure + '\'' +
                ", typeStructure='" + typeStructure + '\'' +
                ", libStructure='" + libStructure + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", codeBct=" + codeBct +
                ", structureMere=" + structureMere +
                ", dateComptable='" + dateComptable + '\'' +
                ", dateJournee='" + dateJournee + '\'' +
                ", mail='" + mail + '\'' +
                ", profils=" + profils +
                ", ressources=" + ressources +
                ", poste='" + poste + '\'' +
                ", codePoste='" + codePoste + '\'' +
                '}';
    }
}
