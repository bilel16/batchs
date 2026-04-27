package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model;

import java.util.ArrayList;
import java.util.List;

import com.oxia.fwk.core.ValueObject;

public class ParamRecherchePersonneVo extends ValueObject {
    
    private Long Structure;
    private Long matricule;
    private String typePersonne;
    private String nom;
    private String prenom;
    private String raisonSociale;
    private String sigle;
    
    private List ListeDesPersonnes = new ArrayList(0);
    
    public ParamRecherchePersonneVo() {
    }

    public void setStructure(Long structure) {
        this.Structure = structure;
    }

    public Long getStructure() {
        return Structure;
    }

    public void setMatricule(Long matricule) {
        this.matricule = matricule;
    }

    public Long getMatricule() {
        return matricule;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getSigle() {
        return sigle;
    }

    public void setListeDesPersonnes(List listeDesPersonnes) {
        this.ListeDesPersonnes = listeDesPersonnes;
    }

    public List getListeDesPersonnes() {
        return ListeDesPersonnes;
    }


    public void setTypePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
    }

    public String getTypePersonne() {
        return typePersonne;
    }
}
