package com.bna.smile.web.operationguichet.form;

import com.bna.commun.model.ContratCpt;

import com.bna.smile.web.commun.forms.PouvoirForm;
import com.bna.smile.web.commun.model.PersonneDemandeur;

import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.commun.view.InitialisationView;

import com.bna.smile.web.operationguichet.view.VersementView;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ValidationVersementForm extends ActionForm {

    //partie commune
    private InitialisationView initialisationView = new InitialisationView();
    //-------------------------------------------------------------------//
    //----------------- Liste des versements pour validation ------------//
    //-------------------------------------------------------------------//
    private List listeVersementMemeAgence          ;
    private List listeVersementMemeAgenceChoisi    = new ArrayList();
    private Integer nombreVersementValides = new Integer(0);
    private List listeVersementAutresAgences = new ArrayList();
  
    //-------------------------------------------------------------------//
    //----------------- Liste des versements pour consultation-----------//
    //-------------------------------------------------------------------//
    private List listeVersementMemeAgenceValide            = new ArrayList();
    private List listeVersementAutresAgencesValide         = new ArrayList();
    private List listeVersementRecuAutresAgencesValide     = new ArrayList();
  
    //-----------------------------------------------------------------------//
    //---------------- Type versement prend : M :vers meme agence non valide
    //---------------- A :autres agences non valide ; CM :vers meme agence valide ; 
    //---------------- CA : versement autres agences valide ; CR : versement Reçus autres agences
    private String typeVersement;
    
    
    private String message="";
    private Integer nombreVersMemeAgenceNonValide  = new Integer(0) ;
    private Integer nombreVersAutreAgenceNonValide = new Integer(0) ;
    private VersementView versementChoisi = new VersementView();
    private String numeroOperationChoisi = "";
    private String nbrDecCpt; // nombre de decimal de la devise du contrat
    private String nbrDecOper; // nombre de decimal de la devise de l'operation
     
    
    public void clearForm() {
    
     listeVersementMemeAgence       = new ArrayList();;
     listeVersementAutresAgences    = new ArrayList();;
     listeVersementMemeAgenceChoisi = new ArrayList();;
    
     listeVersementMemeAgenceValide            = new ArrayList();
     listeVersementAutresAgencesValide         = new ArrayList();
     listeVersementRecuAutresAgencesValide     = new ArrayList();
    
     nombreVersementValides         =0;
     nombreVersMemeAgenceNonValide  =0;
     nombreVersAutreAgenceNonValide =0;
     versementChoisi = new VersementView(); 
    }


    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }


    public void setTypeVersement(String typeVersement) {
        this.typeVersement = typeVersement;
    }

    public String getTypeVersement() {
        return typeVersement;
    }

    public void setListeVersementMemeAgence(List listeVersementMemeAgence) {
        this.listeVersementMemeAgence = listeVersementMemeAgence;
    }

    public List getListeVersementMemeAgence() {
        return listeVersementMemeAgence;
    }

    public void setListeVersementAutresAgences(List listeVersementAutresAgences) {
        this.listeVersementAutresAgences = listeVersementAutresAgences;
    }

    public List getListeVersementAutresAgences() {
        return listeVersementAutresAgences;
    }

    public void setListeVersementMemeAgenceChoisi(List listeVersementMemeAgenceChoisi) {
        this.listeVersementMemeAgenceChoisi = listeVersementMemeAgenceChoisi;
    }

    public List getListeVersementMemeAgenceChoisi() {
        return listeVersementMemeAgenceChoisi;
    }

    public void setNombreVersementValides(Integer nombreVersementValides) {
        this.nombreVersementValides = nombreVersementValides;
    }

    public Integer getNombreVersementValides() {
        return nombreVersementValides;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setNombreVersMemeAgenceNonValide(Integer nombreVersMemeAgenceNonValide) {
        this.nombreVersMemeAgenceNonValide = nombreVersMemeAgenceNonValide;
    }

    public Integer getNombreVersMemeAgenceNonValide() {
        return nombreVersMemeAgenceNonValide;
    }

    public void setNombreVersAutreAgenceNonValide(Integer nombreVersAutreAgenceNonValide) {
        this.nombreVersAutreAgenceNonValide = nombreVersAutreAgenceNonValide;
    }

    public Integer getNombreVersAutreAgenceNonValide() {
        return nombreVersAutreAgenceNonValide;
    }


    public void setVersementChoisi(VersementView versementChoisi) {
        this.versementChoisi = versementChoisi;
    }

    public VersementView getVersementChoisi() {
        return versementChoisi;
    }

    public void setNumeroOperationChoisi(String numeroOperationChoisi) {
        this.numeroOperationChoisi = numeroOperationChoisi;
    }

    public String getNumeroOperationChoisi() {
        return numeroOperationChoisi;
    }

    public void setListeVersementMemeAgenceValide(List listeVersementMemeAgenceValide) {
        this.listeVersementMemeAgenceValide = listeVersementMemeAgenceValide;
    }

    public List getListeVersementMemeAgenceValide() {
        return listeVersementMemeAgenceValide;
    }

    public void setListeVersementAutresAgencesValide(List listeVersementAutresAgencesValide) {
        this.listeVersementAutresAgencesValide = listeVersementAutresAgencesValide;
    }

    public List getListeVersementAutresAgencesValide() {
        return listeVersementAutresAgencesValide;
    }

    public void setListeVersementRecuAutresAgencesValide(List listeVersementRecuAutresAgencesValide) {
        this.listeVersementRecuAutresAgencesValide = listeVersementRecuAutresAgencesValide;
    }

    public List getListeVersementRecuAutresAgencesValide() {
        return listeVersementRecuAutresAgencesValide;
    }

    public void setNbrDecCpt(String nbrDecCpt) {
        this.nbrDecCpt = nbrDecCpt;
    }

    public String getNbrDecCpt() {
        return nbrDecCpt;
    }

    public void setNbrDecOper(String nbrDecOper) {
        this.nbrDecOper = nbrDecOper;
    }

    public String getNbrDecOper() {
        return nbrDecOper;
    }
}
