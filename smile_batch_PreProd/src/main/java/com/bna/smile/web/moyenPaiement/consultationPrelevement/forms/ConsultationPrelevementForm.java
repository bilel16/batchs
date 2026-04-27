package com.bna.smile.web.moyenPaiement.consultationPrelevement.forms;

import com.bna.smile.model.moyenPayement.model.Prelevement;
import com.bna.smile.model.moyenPayement.model.Virement;
import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ConsultationPrelevementForm extends ActionForm {
    private InitialisationView initialisationView = new InitialisationView();
    private String reqCode;
    private String message;
    private String dateJournee;
    private String ribBenificiaire;
    private String libelleOperation;
    private List<Prelevement> listePrelevements = new ArrayList<Prelevement>();
    private String numeroContratCompte;
    private String codeStructureAgence;
    private String codeProduit;

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

    public void setRibBenificiaire(String ribBenificiaire) {
        this.ribBenificiaire = ribBenificiaire;
    }

    public String getRibBenificiaire() {
        return ribBenificiaire;
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

    public String getLibelleOperation() {
        return libelleOperation;
    }


    public void setNumeroContratCompte(String numeroContratCompte) {
        this.numeroContratCompte = numeroContratCompte;
    }

    public String getNumeroContratCompte() {
        return numeroContratCompte;
    }

    public void setCodeStructureAgence(String codeStructureAgence) {
        this.codeStructureAgence = codeStructureAgence;
    }

    public String getCodeStructureAgence() {
        return codeStructureAgence;
    }


    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

 public void setListePrelevements(List<Prelevement> listePrelevements) {
        this.listePrelevements = listePrelevements;
    }

    public List<Prelevement> getListePrelevements() {
        return listePrelevements;
    }

    public void initialiser() {
        this.message = "";
        this.ribBenificiaire = "";
        this.listePrelevements = null;
        this.numeroContratCompte = "";
        this.codeProduit = "";
    }

   
}
