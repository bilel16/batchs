package com.bna.smile.web.commun.forms;

import com.bna.smile.web.commun.model.PersonneDemandeur;

import org.apache.struts.action.ActionForm;


public class PouvoirForm extends ActionForm{
    private String reqCode;
    private PersonneDemandeur personneDemandeur = new PersonneDemandeur();
  
    public void clearForm() {
        reqCode="";
        personneDemandeur =null;
    }
    public PouvoirForm() {
    }

    public void setPersonneDemandeur(PersonneDemandeur personneDemandeur) {
        this.personneDemandeur = personneDemandeur;
    }

    public PersonneDemandeur getPersonneDemandeur() {
        return personneDemandeur;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }
}
