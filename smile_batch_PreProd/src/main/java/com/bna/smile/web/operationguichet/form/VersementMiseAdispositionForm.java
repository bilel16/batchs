package com.bna.smile.web.operationguichet.form;

import com.bna.smile.web.commun.view.InitialisationView;

import com.bna.smile.web.operationguichet.view.VersementMiseAdispositionView;

import org.apache.struts.action.ActionForm;

public class VersementMiseAdispositionForm extends ActionForm {

    //partie commune
    private InitialisationView initialisationView = new InitialisationView();

 
    private VersementMiseAdispositionView versementMiseAdispositionView = 
        new VersementMiseAdispositionView();

    // 
   
    
    
   


    public void clearForm() {

            versementMiseAdispositionView = new VersementMiseAdispositionView();
    }


    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }


    public void setVersementMiseAdispositionView(VersementMiseAdispositionView versementMiseAdispositionView) {
        this.versementMiseAdispositionView = versementMiseAdispositionView;
    }

    public VersementMiseAdispositionView getVersementMiseAdispositionView() {
        return versementMiseAdispositionView;
    }
}
