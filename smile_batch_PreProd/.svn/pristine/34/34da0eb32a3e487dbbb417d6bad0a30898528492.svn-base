package com.bna.smile.web.moyenPaiement.gestionAccuse.forms;

import com.bna.smile.model.moyenPayement.model.Accuse;
import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ConsultationAccusesForm extends ActionForm {
    
    private InitialisationView initialisationView = new InitialisationView();
    private String reqCode;
    private String message;
    private String dateJournee;
    private List<Accuse> listeAccuses=new ArrayList<Accuse>();
    private String libelleOperation;
    

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
        
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setDateJournee(String dateJournee) {
        this.dateJournee = dateJournee;
    }

    public String getDateJournee() {
        return dateJournee;
    }

    public void setListeAccuses(List<Accuse> listeAccuses) {
        this.listeAccuses = listeAccuses;
    }

    public List<Accuse> getListeAccuses() {
        return listeAccuses;
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

    public String getLibelleOperation() {
        return libelleOperation;
    }
    public void initialiser (){

        
        this.message="";
        this.listeAccuses=null;
    }

    
}
