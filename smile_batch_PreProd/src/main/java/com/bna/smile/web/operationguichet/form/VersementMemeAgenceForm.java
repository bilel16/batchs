package com.bna.smile.web.operationguichet.form;

import com.bna.commun.model.ContratCpt;

import com.bna.smile.web.commun.forms.PouvoirForm;
import com.bna.smile.web.commun.model.PersonneDemandeur;

import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.commun.view.InitialisationView;
import com.bna.smile.web.operationguichet.view.VersementMemeAgenceView;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class VersementMemeAgenceForm extends ActionForm {

    //partie commune
    private InitialisationView initialisationView = new InitialisationView();

    //partie contratView
    private ContratView contratView = new ContratView();

    private VersementMemeAgenceView versementMemeAgenceView = 
        new VersementMemeAgenceView();

    // 
    PouvoirForm pouvoirForm = new PouvoirForm();
    //
    private PersonneDemandeur personneDemandeur = new PersonneDemandeur();
    //
    private String numeroLivretSaisi;
    private String codDevDev;
    private String nbrDecDev;
    
    
    
    
    
    private String montCommission = "0.000";
    private String montTva = "0.000";
    
    private List listeCaisseStructure = new ArrayList(0);
    private String numeroCaisse;

    public void clearForm() {

        contratView = new ContratView();
        personneDemandeur = new PersonneDemandeur();
        versementMemeAgenceView = new VersementMemeAgenceView();
        numeroLivretSaisi ="";
        codDevDev ="";
        nbrDecDev ="";
    }


    public void setContratView(ContratView contratView) {
        this.contratView = contratView;
    }

    public ContratView getContratView() {
        return contratView;
    }


    public void setMontCommission(String montCommission) {
        this.montCommission = montCommission;
    }

    public String getMontCommission() {
        return montCommission;
    }

    public void setMontTva(String montTva) {
        this.montTva = montTva;
    }

    public String getMontTva() {
        return montTva;
    }


    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setPersonneDemandeur(PersonneDemandeur personneDemandeur) {
        this.personneDemandeur = personneDemandeur;
    }

    public PersonneDemandeur getPersonneDemandeur() {
        return personneDemandeur;
    }

    public void setVersementMemeAgenceView(VersementMemeAgenceView versementMemeAgenceView) {
        this.versementMemeAgenceView = versementMemeAgenceView;
    }

    public VersementMemeAgenceView getVersementMemeAgenceView() {
        return versementMemeAgenceView;
    }

    public void setPouvoirForm(PouvoirForm pouvoirForm) {
        this.pouvoirForm = pouvoirForm;
    }

    public PouvoirForm getPouvoirForm() {
        return pouvoirForm;
    }

    public void setNumeroLivretSaisi(String numeroLivretSaisi) {
        this.numeroLivretSaisi = numeroLivretSaisi;
    }

    public String getNumeroLivretSaisi() {
        return numeroLivretSaisi;
    }

    public void setCodDevDev(String codDevDev) {
        this.codDevDev = codDevDev;
    }

    public String getCodDevDev() {
        return codDevDev;
    }


    public void setNbrDecDev(String nbrDecDev) {
        this.nbrDecDev = nbrDecDev;
    }

    public String getNbrDecDev() {
        return nbrDecDev;
    }

    public void setListeCaisseStructure(List listeCaisseStructure) {
        this.listeCaisseStructure = listeCaisseStructure;
    }

    public List getListeCaisseStructure() {
        return listeCaisseStructure;
    }

    public void setNumeroCaisse(String numeroCaisse) {
        this.numeroCaisse = numeroCaisse;
    }

    public String getNumeroCaisse() {
        return numeroCaisse;
    }
}
