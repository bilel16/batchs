package com.bna.smile.web.operationguichet.form;

import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ConsultationGuichetRetraitForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
     
     private InitialisationView initialisationView = new InitialisationView();
     private PersonneDemandeur personneDemandeur = new PersonneDemandeur();
     private String reqCode;
     private List listRetraitEmis = new ArrayList();
     private List listRetraitDepl = new ArrayList();
     private List listRetraitMemeAg = new ArrayList();
     private List listRetraitInitie = new ArrayList();    
     
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

    public void setCodetrait(String codetrait) {
        this.codetrait = codetrait;
    }

    public String getCodetrait() {
        return codetrait;
    }

    public void setListRetraitEmis(List listRetraitEmis) {
        this.listRetraitEmis = listRetraitEmis;
    }

    public List getListRetraitEmis() {
        return listRetraitEmis;
    }

    public void setListRetraitDepl(List listRetraitDepl) {
        this.listRetraitDepl = listRetraitDepl;
    }

    public List getListRetraitDepl() {
        return listRetraitDepl;
    }

    public void setListRetraitMemeAg(List listRetraitMemeAg) {
        this.listRetraitMemeAg = listRetraitMemeAg;
    }

    public List getListRetraitMemeAg() {
        return listRetraitMemeAg;
    }

    public void setListRetraitInitie(List listRetraitInitie) {
        this.listRetraitInitie = listRetraitInitie;
    }

    public List getListRetraitInitie() {
        return listRetraitInitie;
    }
}
