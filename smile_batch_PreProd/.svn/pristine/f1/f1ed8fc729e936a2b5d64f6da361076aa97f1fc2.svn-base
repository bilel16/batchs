package com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.forms;

import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.view.InitialisationView;
import com.bna.smile.web.commun.view.ContratView;

import com.bna.smile.web.commun.view.OppositionMoyPaiementView;

import com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.model.ParamConsult;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ConsultationOppMoyPaieForm extends ActionForm {
    //partie commune
    private InitialisationView initialisationView = new InitialisationView();

    //partie specifique opposition Moyent Paiement
    private OppositionMoyPaiementView oppositionMoyPaiementView = 
        new OppositionMoyPaiementView();
        
    // partie consacrée à la consultation opposition moy paie
    private ParamConsult paramConsultOpposition = new ParamConsult();
    
    private Collection listTypMoyPaie;
    private Collection listNatureCheque;
    private Collection listNatureCarte;
    private Collection listOpposition;

    public void clearForm() {
    
        //partie init
        initialisationView.setAlert("");
        
        //partie oppositionMoyPaiement
        oppositionMoyPaiementView.clear();
        
        // partie consultation opposition
        paramConsultOpposition.clear();
        listOpposition = null;
        
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


    public void setParamConsultOpposition(ParamConsult paramConsultOpposition) {
        this.paramConsultOpposition = paramConsultOpposition;
    }

    public ParamConsult getParamConsultOpposition() {
        return paramConsultOpposition;
    }

    public void setListTypMoyPaie(Collection listTypMoyPaie) {
        this.listTypMoyPaie = listTypMoyPaie;
    }

    public Collection getListTypMoyPaie() {
        return listTypMoyPaie;
    }

    public void setListNatureCheque(Collection listNatureCheque) {
        this.listNatureCheque = listNatureCheque;
    }

    public Collection getListNatureCheque() {
        return listNatureCheque;
    }

    public void setListNatureCarte(Collection listNatureCarte) {
        this.listNatureCarte = listNatureCarte;
    }

    public Collection getListNatureCarte() {
        return listNatureCarte;
    }

    public void setListOpposition(Collection listOpposition) {
        this.listOpposition = listOpposition;
    }

    public Collection getListOpposition() {
        return listOpposition;
    }
}
