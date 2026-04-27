package com.bna.smile.web.commun.model;

import com.bna.commun.constant.Constants;


public class PersonneDemandeur extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
    // Information du demandeur
    private String codTpceTpceDemandeur;
    private String numPcePersDemandeur;
    private String nomNomPersDemandeur;
    private String nomPrnPersDemandeur; 
  
    private String numSeqDemandeur; 
    private String messageTexte="";
    private String typePouvoir;
    
    
    public void clear() {
        codTpceTpceDemandeur = "";
        numPcePersDemandeur = "";
        nomNomPersDemandeur = "";
        nomPrnPersDemandeur = "";
        
        numSeqDemandeur = "";
        messageTexte = "";
        typePouvoir = "";
      
        
    }
    
    public PersonneDemandeur(){        
        
    }        

    public void setCodTpceTpceDemandeur(String codTpceTpceDemandeur) {
        this.codTpceTpceDemandeur = codTpceTpceDemandeur;
    }

    public String getCodTpceTpceDemandeur() {
        return codTpceTpceDemandeur;
    }

    public void setNumPcePersDemandeur(String numPcePersDemandeur) {
        this.numPcePersDemandeur = numPcePersDemandeur;
    }

    public String getNumPcePersDemandeur() {
        return numPcePersDemandeur;
    }

    public void setNomNomPersDemandeur(String nomNomPersDemandeur) {
        this.nomNomPersDemandeur = nomNomPersDemandeur;
    }

    public String getNomNomPersDemandeur() {
        return nomNomPersDemandeur;
    }

    public void setNomPrnPersDemandeur(String nomPrnPersDemandeur) {
        this.nomPrnPersDemandeur = nomPrnPersDemandeur;
    }

    public String getNomPrnPersDemandeur() {
        return nomPrnPersDemandeur;
    }

    public void setNumSeqDemandeur(String numSeqDemandeur) {
        this.numSeqDemandeur = numSeqDemandeur;
    }

    public String getNumSeqDemandeur() {
        return numSeqDemandeur;
    }

    public void setTypePouvoir(String typePouvoir) {
        this.typePouvoir = typePouvoir;
    }

    public String getTypePouvoir() {
        return typePouvoir;
    }


    public void setMessageTexte(String messageTexte) {
        this.messageTexte = messageTexte;
    }

    public String getMessageTexte() {
        return messageTexte;
    }


  
}

