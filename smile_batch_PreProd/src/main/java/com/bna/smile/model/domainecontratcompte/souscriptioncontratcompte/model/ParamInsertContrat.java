
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.Categorie;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.MotifEtat;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.oxia.fwk.core.ValueObject;

/** Fichier: ParamInsertContrat.java version 1.0.0 du 30/03/2007
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: ParamPers
 * package: com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model
 * Auteur : El arbi hassine 
 */
public class ParamInsertContrat extends ValueObject {

    ContratCpt contratCpt = new ContratCpt();
    Personne personneTuteur = new Personne();
    MotifEtat motifEtat = new MotifEtat();
    Categorie Categorie = new Categorie();
    Personnel personnel = new Personnel();  
    private String typeVersementEpargne;
    private List listCotitulaire = new ArrayList();

    public ParamInsertContrat() {
    }


    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setPersonneTuteur(Personne personneTuteur) {
        this.personneTuteur = personneTuteur;
    }

    public Personne getPersonneTuteur() {
        return personneTuteur;
    }

    public void setCategorie(Categorie categorie) {
        this.Categorie = categorie;
    }

    public Categorie getCategorie() {
        return Categorie;
    }

    public void setMotifEtat(MotifEtat motifEtat) {
        this.motifEtat = motifEtat;
    }

    public MotifEtat getMotifEtat() {
        return motifEtat;
    }

    public void setListCotitulaire(List listCotitulaire) {
        this.listCotitulaire = listCotitulaire;
    }

    public List getListCotitulaire() {
        return listCotitulaire;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setTypeVersementEpargne(String typeVersementEpargne) {
        this.typeVersementEpargne = typeVersementEpargne;
    }

    public String getTypeVersementEpargne() {
        return typeVersementEpargne;
    }
}
