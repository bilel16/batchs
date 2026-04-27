package com.bna.smile.web.clotureDomaine.forms;

import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ClotureJourneeForm extends ActionForm{
    public ClotureJourneeForm() {
    }
    
    private InitialisationView initialisationView = new InitialisationView();
    private String dateJournee;
    private Long codeStructure;
    private String alert;
    private List listDomaines=new ArrayList(0);
    private Boolean cloture;
    private String datecloturee="";
    private String dateOuverte="";

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setDateJournee(String dateJournee) {
        this.dateJournee = dateJournee;
    }

    public String getDateJournee() {
        return dateJournee;
    }

    public void setCodeStructure(Long codeStructure) {
        this.codeStructure = codeStructure;
    }

    public Long getCodeStructure() {
        return codeStructure;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setListDomaines(List listDomaines) {
        this.listDomaines = listDomaines;
    }

    public List getListDomaines() {
        return listDomaines;
    }

    public void setCloture(Boolean cloture) {
        this.cloture = cloture;
    }

    public Boolean getCloture() {
        return cloture;
    }

    public void setDatecloturee(String datecloturee) {
        this.datecloturee = datecloturee;
    }

    public String getDatecloturee() {
        return datecloturee;
    }

    public void setDateOuverte(String dateOuverte) {
        this.dateOuverte = dateOuverte;
    }

    public String getDateOuverte() {
        return dateOuverte;
    }
}
