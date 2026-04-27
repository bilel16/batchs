package com.bna.smile.web.commun.forms;

import com.bna.commun.model.Personne;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ConsultationModificationDonneesForm extends ActionForm {

    //--------------------------------------------------------
    //--------Identification de la personne-------------------
    //--------------------------------------------------------
    private String typePiece;
    private String numeroPiece;
    private String dateDebut;
    private String dateFin;
    private Personne personne;
    private String typePersonne;
    private String nomPersonne;
    private String prenomPersonne;
    private String nomRsPers;
    private String libSiglPers;
    //------------------------------------------------------//
    //------- Liste des modification -----------------------//
    //------------------------------------------------------//
    private List listeDesModifications = new ArrayList();

    private String reqCode;

    public void clearForm() {
        typePiece = "";
        numeroPiece = "";
        dateFin ="";
        dateDebut="";
        personne = null;
        typePersonne="";
        nomPersonne = "";
        prenomPersonne = "";
        nomRsPers="";
        libSiglPers = "";
        listeDesModifications = new ArrayList();
    }

    public ConsultationModificationDonneesForm() {
    }


    public void setTypePiece(String typePiece) {
        this.typePiece = typePiece;
    }

    public String getTypePiece() {
        return typePiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setNomPersonne(String nomPersonne) {
        this.nomPersonne = nomPersonne;
    }

    public String getNomPersonne() {
        return nomPersonne;
    }

    public void setPrenomPersonne(String prenomPersonne) {
        this.prenomPersonne = prenomPersonne;
    }

    public String getPrenomPersonne() {
        return prenomPersonne;
    }


    public void setListeDesModifications(List listeDesModifications) {
        this.listeDesModifications = listeDesModifications;
    }

    public List getListeDesModifications() {
        return listeDesModifications;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setTypePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
    }

    public String getTypePersonne() {
        return typePersonne;
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
}
