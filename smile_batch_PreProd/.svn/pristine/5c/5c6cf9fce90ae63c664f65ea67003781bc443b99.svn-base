package com.bna.smile.web.operationguichet.form;

import com.bna.commun.model.ContratCpt;

import com.bna.smile.web.commun.forms.PouvoirForm;
import com.bna.smile.web.commun.model.PersonneDemandeur;

import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.commun.view.InitialisationView;

import com.bna.smile.web.operationguichet.view.ValidationMiseAdispositionView;
import com.bna.smile.web.operationguichet.view.VersementMiseAdispositionView;
import com.bna.smile.web.operationguichet.view.VersementView;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ValidationMiseAdispositionForm extends ActionForm {

    //partie commune
    private InitialisationView initialisationView = new InitialisationView();
    //-------------------------------------------------------------------------------//
    //----------------- Liste des versements pour validation
    //------------------------------------------------------------------------------//
    private List listMiseAdisposition          ;
   private Integer nombreMiseAdisposition  = new Integer(0) ;
    //-----------------------------------------------------------------------//
    //--- RMD / RMG / RCA
    private String typeMiseAdisposition;
    
    
    private String message="";
    
    private String numeroOperation ="";
    
    
    private ValidationMiseAdispositionView miseAdispositionChoisi = new ValidationMiseAdispositionView();
   
    
    public void clearForm() {
    
     listMiseAdisposition       = new ArrayList();;

     miseAdispositionChoisi = new ValidationMiseAdispositionView(); 
    }


    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }






    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }



    public void setListMiseAdisposition(List listMiseAdisposition) {
        this.listMiseAdisposition = listMiseAdisposition;
    }

    public List getListMiseAdisposition() {
        return listMiseAdisposition;
    }

    public void setTypeMiseAdisposition(String typeMiseAdisposition) {
        this.typeMiseAdisposition = typeMiseAdisposition;
    }

    public String getTypeMiseAdisposition() {
        return typeMiseAdisposition;
    }

    public void setNombreMiseAdisposition(Integer nombreMiseAdisposition) {
        this.nombreMiseAdisposition = nombreMiseAdisposition;
    }

    public Integer getNombreMiseAdisposition() {
        return nombreMiseAdisposition;
    }

    public void setNumeroOperation(String numeroOperation) {
        this.numeroOperation = numeroOperation;
    }

    public String getNumeroOperation() {
        return numeroOperation;
    }


    public void setMiseAdispositionChoisi(ValidationMiseAdispositionView miseAdispositionChoisi) {
        this.miseAdispositionChoisi = miseAdispositionChoisi;
    }

    public ValidationMiseAdispositionView getMiseAdispositionChoisi() {
        return miseAdispositionChoisi;
    }
}
