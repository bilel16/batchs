package com.bna.smile.web.reporting.forms;

import com.bna.smile.web.commun.view.InitialisationView;

import org.apache.struts.action.ActionForm;

public class ReportRIBForm extends ActionForm {

    private InitialisationView initialisationView = new InitialisationView();
    private String codStrcStrc;
    private String codPrdPrd;
    private String numCcptCcpt;
    private String nomPrnPers;
    private String rib;
    private String iban;
    private String libStrc;
    private String cle;
    private String etatCcpt;
    private String numMatrUser;
    private String alertContrat;

    /* public ReportRIBForm() {
    }*/
 public void clearRibForm(){
     codStrcStrc = "";
     codPrdPrd = "";
     numCcptCcpt = "";
     nomPrnPers = "";
     rib = "";
     libStrc = "";
     cle = "";
     numMatrUser = "";
     iban="";
     alertContrat="";
     etatCcpt="";
 }
    public void setCodStrcStrc(String codStrcStrc) {
        this.codStrcStrc = codStrcStrc;
    }

    public String getCodStrcStrc() {
        return codStrcStrc;
    }

    public void setCodPrdPrd(String codPrdPrd) {
        this.codPrdPrd = codPrdPrd;
    }

    public String getCodPrdPrd() {
        return codPrdPrd;
    }

    public void setNumCcptCcpt(String numCcptCcpt) {
        this.numCcptCcpt = numCcptCcpt;
    }

    public String getNumCcptCcpt() {
        return numCcptCcpt;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getCle() {
        return cle;
    }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setNomPrnPers(String nomPrnPers) {
        this.nomPrnPers = nomPrnPers;
    }

    public String getNomPrnPers() {
        return nomPrnPers;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getRib() {
        return rib;
    }

    public void setLibStrc(String libStrc) {
        this.libStrc = libStrc;
    }

    public String getLibStrc() {
        return libStrc;
    }

    public void setNumMatrUser(String numMatrUser) {
        this.numMatrUser = numMatrUser;
    }

    public String getNumMatrUser() {
        return numMatrUser;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    public void setEtatCcpt(String etatCcpt) {
        this.etatCcpt = etatCcpt;
    }

    public String getEtatCcpt() {
        return etatCcpt;
    }

    public void setAlertContrat(String alertContrat) {
        this.alertContrat = alertContrat;
    }

    public String getAlertContrat() {
        return alertContrat;
    }
}
