package com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.forms;

import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.view.InitialisationView;
import com.bna.smile.web.commun.view.ContratView;

import com.bna.smile.web.commun.view.OppositionMoyPaiementView;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class OppositionMoyenPaiementForm extends ActionForm {
    //partie commune
    private InitialisationView initialisationView = new InitialisationView();

    //partie contratView
    private ContratView contratView = new ContratView();

    //partie personne demandeur
    private PersonneDemandeur personneDemandeur = new PersonneDemandeur();

    //partie specifique opposition Moyent Paiement
    private OppositionMoyPaiementView oppositionMoyPaiementView = 
        new OppositionMoyPaiementView();
        
    //partie pour ajax
    private String numBonCaisseAjax ;
    private String codeStructureAjax;
    private String existBCAjax;
    private String existDBCAjax;
    private String numSeqBCAjax;

    public void clearForm() {
    
        //partie init
        initialisationView.setAlert("");
        
        //partie contrat
        contratView.clear();

        //partie personne demandeur
        personneDemandeur.clear();

        //partie oppositionMoyPaiement
        oppositionMoyPaiementView.clear();
        
       numBonCaisseAjax   = "";
       codeStructureAjax  = "";
       existBCAjax        = "";
       existDBCAjax       = "";
       numSeqBCAjax       = "";

    }

    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    /**Validate all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return ActionErrors A list of all errors found.
     */
    public ActionErrors validate(ActionMapping mapping, 
                                 HttpServletRequest request) {
        return super.validate(mapping, request);
    }


    public void setContratView(ContratView contratView) {
        this.contratView = contratView;
    }

    public ContratView getContratView() {
        return contratView;
    }

    public void setPersonneDemandeur(PersonneDemandeur personneDemandeur) {
        this.personneDemandeur = personneDemandeur;
    }

    public PersonneDemandeur getPersonneDemandeur() {
        return personneDemandeur;
    }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setOppositionMoyPaiementView(OppositionMoyPaiementView oppositionMoyPaiementView) {
        this.oppositionMoyPaiementView = oppositionMoyPaiementView;
    }

    public OppositionMoyPaiementView getOppositionMoyPaiementView() {
        return oppositionMoyPaiementView;
    }

    public void setNumBonCaisseAjax(String numBonCaisseAjax) {
        this.numBonCaisseAjax = numBonCaisseAjax;
    }

    public String getNumBonCaisseAjax() {
        return numBonCaisseAjax;
    }

    public void setCodeStructureAjax(String codeStructureAjax) {
        this.codeStructureAjax = codeStructureAjax;
    }

    public String getCodeStructureAjax() {
        return codeStructureAjax;
    }

    public void setExistBCAjax(String existBCAjax) {
        this.existBCAjax = existBCAjax;
    }

    public String getExistBCAjax() {
        return existBCAjax;
    }

    public void setExistDBCAjax(String existDBCAjax) {
        this.existDBCAjax = existDBCAjax;
    }

    public String getExistDBCAjax() {
        return existDBCAjax;
    }

    public void setNumSeqBCAjax(String numSeqBCAjax) {
        this.numSeqBCAjax = numSeqBCAjax;
    }

    public String getNumSeqBCAjax() {
        return numSeqBCAjax;
    }
}
