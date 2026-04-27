package com.bna.smile.web.operationguichet.form;

import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GuichetRetraitDeplaceForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
     
     private InitialisationView initialisationView = new InitialisationView();
     private PersonneDemandeur personneDemandeur = new PersonneDemandeur();
     private String reqCode;
     private List listRetraitRecu = new ArrayList();
     private List listRetraitEmis = new ArrayList();
     private String codetrait;

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset( mapping, request);
    }

    /**Validate all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return ActionErrors A list of all errors found.
     */
    public ActionErrors validate(ActionMapping mapping, 
                                 HttpServletRequest request) {
        return super.validate( mapping, request);
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

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setListRetraitRecu(List listRetraitRecu) {
        this.listRetraitRecu = listRetraitRecu;
    }

    public List getListRetraitRecu() {
        return listRetraitRecu;
    }

    public void setListRetraitEmis(List listRetraitEmis) {
        this.listRetraitEmis = listRetraitEmis;
    }

    public List getListRetraitEmis() {
        return listRetraitEmis;
    }

    public void setCodetrait(String codetrait) {
        this.codetrait = codetrait;
    }

    public String getCodetrait() {
        return codetrait;
    }
}
