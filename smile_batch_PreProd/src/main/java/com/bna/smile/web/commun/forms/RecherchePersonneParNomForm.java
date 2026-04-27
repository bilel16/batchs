package com.bna.smile.web.commun.forms;

import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * Classe pour la recherche des personnes par le nom  et prenom
 * ou sigle et raison sociale
 * @author Mdimagh Med Lassaad
 * @since 28/05/2008
 */
public class RecherchePersonneParNomForm extends ActionForm {

    private InitialisationView initialisationView = new InitialisationView();
    private String typePersonne;
    private String nom;
    private String prenom;
    
    private String nomApresRecherche;
    private String prenomApresRecherche;
    
    private String raisonSociale;
    private String sigle;
    
    private String raisonSocialeApresRecherche;
    private String sigleApresRecherche;
    
    
    private String typePP;
    
    private String numSeqPersChoisi;
    private String nomNomPers;
    private String nomPrnPers;
    private String nomRsPers;
    private String libSiglPers;
    private String libTpceTpce;
    private String numPcePers;
    
    private List listeDesPiecesAnnexes = new ArrayList();
    
    
    
    private List listeDesPersonnes = new ArrayList();
    private String nombreRecherche;

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
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
        this.listeDesPersonnes = listeDesPersonnes;
    }

    public List getListeDesPersonnes() {
        return listeDesPersonnes;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setTypePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
    }

    public String getTypePersonne() {
        return typePersonne;
    }

    public void setNombreRecherche(String nombreRecherche) {
        this.nombreRecherche = nombreRecherche;
    }

    public String getNombreRecherche() {
        return nombreRecherche;
    }

    public void setNumSeqPersChoisi(String numSeqPersChoisi) {
        this.numSeqPersChoisi = numSeqPersChoisi;
    }

    public String getNumSeqPersChoisi() {
        return numSeqPersChoisi;
    }

    public void setListeDesPiecesAnnexes(List listeDesPiecesAnnexes) {
        this.listeDesPiecesAnnexes = listeDesPiecesAnnexes;
    }

    public List getListeDesPiecesAnnexes() {
        return listeDesPiecesAnnexes;
    }

    public void setNomNomPers(String nomNomPers) {
        this.nomNomPers = nomNomPers;
    }

    public String getNomNomPers() {
        return nomNomPers;
    }

    public void setNomPrnPers(String nomPrnPers) {
        this.nomPrnPers = nomPrnPers;
    }

    public String getNomPrnPers() {
        return nomPrnPers;
    }

    public void setLibTpceTpce(String libTpceTpce) {
        this.libTpceTpce = libTpceTpce;
    }

    public String getLibTpceTpce() {
        return libTpceTpce;
    }

    public void setNumPcePers(String numPcePers) {
        this.numPcePers = numPcePers;
    }

    public String getNumPcePers() {
        return numPcePers;
    }

    public void setNomRsPers(String nomRsPers) {
        this.nomRsPers = nomRsPers;
    }

    public String getNomRsPers() {
        return nomRsPers;
    }

    public void setLibSiglPers(String libSiglPers) {
        this.libSiglPers = libSiglPers;
    }

    public String getLibSiglPers() {
        return libSiglPers;
    }

    public void setTypePP(String typePP) {
        this.typePP = typePP;
    }

    public String getTypePP() {
        return typePP;
    }

    public void setNomApresRecherche(String nomApresRecherche) {
        this.nomApresRecherche = nomApresRecherche;
    }

    public String getNomApresRecherche() {
        return nomApresRecherche;
    }

    public void setPrenomApresRecherche(String prenomApresRecherche) {
        this.prenomApresRecherche = prenomApresRecherche;
    }

    public String getPrenomApresRecherche() {
        return prenomApresRecherche;
    }

    public void setRaisonSocialeApresRecherche(String raisonSocialeApresRecherche) {
        this.raisonSocialeApresRecherche = raisonSocialeApresRecherche;
    }

    public String getRaisonSocialeApresRecherche() {
        return raisonSocialeApresRecherche;
    }

    public void setSigleApresRecherche(String sigleApresRecherche) {
        this.sigleApresRecherche = sigleApresRecherche;
    }

    public String getSigleApresRecherche() {
        return sigleApresRecherche;
    }
}
