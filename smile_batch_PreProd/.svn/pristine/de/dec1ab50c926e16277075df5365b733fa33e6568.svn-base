package com.bna.smile.web.souscription.forms;

import com.bna.smile.web.commun.view.InitialisationView;

import com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.model.ParamConsult;

import java.util.Collection;

import org.apache.struts.action.ActionForm;

public class ConsultationCompteRejetesForm extends ActionForm {
    private InitialisationView initialisationView = new InitialisationView();
    
    private ParamConsult paramConsult= new ParamConsult();
    private Collection listCcptRejetes;
    private String reqCode;
    public void clearForm(){
       paramConsult.clear();
       listCcptRejetes = null;
    }
    public ConsultationCompteRejetesForm() {
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

    public void setListCcptRejetes(Collection listCcptRejetes) {
        this.listCcptRejetes = listCcptRejetes;
    }

    public Collection getListCcptRejetes() {
        return listCcptRejetes;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }
}
