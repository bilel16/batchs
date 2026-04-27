package com.bna.smile.web.commun.forms;

import com.bna.commun.model.ExonerationCltTva;
import com.bna.smile.web.commun.view.ExonerationCltTvaView;
import com.bna.smile.web.commun.view.InitialisationView;

import com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.model.ParamConsult;

import java.util.Collection;

import org.apache.struts.action.ActionForm;


public class ExonerationTvaForm extends ActionForm{

    private  InitialisationView initialisationView = new InitialisationView();
    private ParamConsult paramConsult = new ParamConsult();
    private String libelleConfirmation;
    private ExonerationCltTva exonerationCltTva =new ExonerationCltTva() ;
    private ExonerationCltTvaView exonerationCltTvaView =new ExonerationCltTvaView();
    private String nomNomPers;
    private String nomPrnPers;
    private boolean personneExist;
    private String matriculeFiscal;
    private Collection listeExonerationTva;
    private Collection listeExonerationTvaView;
    private String refExoTvaChoisi;
    private String etatExonerationExist;
    private Collection listContratCpt;
    
    public void clearForm() {
        this.paramConsult.clear();
        this.exonerationCltTva =new ExonerationCltTva() ;
        this.exonerationCltTvaView.clear();
        this.nomNomPers="";
        this.nomPrnPers="";
        this.personneExist=true;
        this.matriculeFiscal="";
        this.libelleConfirmation = "";
        this.refExoTvaChoisi = "";
        this.etatExonerationExist = "";
        this.listeExonerationTva = null;
        this.listeExonerationTvaView = null;
        this.listContratCpt = null;
    }
    public void clear() {
        this.exonerationCltTva =new ExonerationCltTva() ;
        this.exonerationCltTvaView.clear();
        this.nomNomPers="";
        this.nomPrnPers="";
        this.personneExist=true;
        this.matriculeFiscal="";
        this.libelleConfirmation = "";
        this.refExoTvaChoisi = "";
        this.etatExonerationExist = "";
        this.listContratCpt = null;
    }
    public void clearListeExoneration(){
        this.listeExonerationTva = null;
        this.listeExonerationTvaView = null;
        this.paramConsult.clear();
    }
    public void clearNomPrenomForm() {
      this.personneExist =true;
      this.etatExonerationExist = "";
      this.nomNomPers="";
      this.nomPrnPers="";
      this.matriculeFiscal="";
      this.listContratCpt = null;
    }
    public ExonerationTvaForm() {
    }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setParamConsult(ParamConsult paramConsult) {
        this.paramConsult = paramConsult;
    }

    public ParamConsult getParamConsult() {
        return paramConsult;
    }


    public void setExonerationCltTva(ExonerationCltTva exonerationCltTva) {
        this.exonerationCltTva = exonerationCltTva;
    }

    public ExonerationCltTva getExonerationCltTva() {
        return exonerationCltTva;
    }

    public void setExonerationCltTvaView(ExonerationCltTvaView exonerationCltTvaView) {
        this.exonerationCltTvaView = exonerationCltTvaView;
    }

    public ExonerationCltTvaView getExonerationCltTvaView() {
        return exonerationCltTvaView;
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

    public void setPersonneExist(boolean personneExist) {
        this.personneExist = personneExist;
    }

    public boolean isPersonneExist() {
        return personneExist;
    }

    public void setMatriculeFiscal(String matriculeFiscal) {
        this.matriculeFiscal = matriculeFiscal;
    }

    public String getMatriculeFiscal() {
        return matriculeFiscal;
    }

    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
    }

    public void setListeExonerationTva(Collection listeExonerationTva) {
        this.listeExonerationTva = listeExonerationTva;
    }

    public Collection getListeExonerationTva() {
        return listeExonerationTva;
    }

    public void setRefExoTvaChoisi(String refExoTvaChoisi) {
        this.refExoTvaChoisi = refExoTvaChoisi;
    }

    public String getRefExoTvaChoisi() {
        return refExoTvaChoisi;
    }

    public void setEtatExonerationExist(String etatExonerationExist) {
        this.etatExonerationExist = etatExonerationExist;
    }

    public String getEtatExonerationExist() {
        return etatExonerationExist;
    }

    public void setListeExonerationTvaView(Collection listeExonerationTvaView) {
        this.listeExonerationTvaView = listeExonerationTvaView;
    }

    public Collection getListeExonerationTvaView() {
        return listeExonerationTvaView;
    }

    public void setListContratCpt(Collection listContratCpt) {
        this.listContratCpt = listContratCpt;
    }

    public Collection getListContratCpt() {
        return listContratCpt;
    }
}
