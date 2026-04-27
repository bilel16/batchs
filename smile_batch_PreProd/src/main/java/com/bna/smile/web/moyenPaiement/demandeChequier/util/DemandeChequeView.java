package com.bna.smile.web.moyenPaiement.demandeChequier.util;

import com.bna.commun.model.DemandeCheque;
import com.bna.commun.model.MandatOperation;

/**
 * Classe qui represente l'objet DemandeCheque, elle est utilisée 
 * pour l'affichage dans les pages JSP
 * @author EL ARBI HASSINE
 */
public class DemandeChequeView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
    private DemandeCheque demandeCheque;
    private String dateDemande;
    private String etatDemande;
    private String codeAgence;
    private String codeProduit;
    private String numeroCompte;
    private String typePieceDem;
    private String numPieceDem;
    private String boolWebDchq;


    public DemandeChequeView() {
    }

    public void setDemandeCheque(DemandeCheque demandeCheque) {
        this.demandeCheque = demandeCheque;
    }

    public DemandeCheque getDemandeCheque() {
        return demandeCheque;
    }

    public void setDateDemande(String dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getDateDemande() {
        return dateDemande;
    }

    public void setEtatDemande(String etatDemande) {
        this.etatDemande = etatDemande;
    }

    public String getEtatDemande() {
        return etatDemande;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setTypePieceDem(String typePieceDem) {
        this.typePieceDem = typePieceDem;
    }

    public String getTypePieceDem() {
        return typePieceDem;
    }

    public void setNumPieceDem(String numPieceDem) {
        this.numPieceDem = numPieceDem;
    }

    public String getNumPieceDem() {
        return numPieceDem;
    }

    public void setBoolWebDchq(String boolWebDchq) {
        this.boolWebDchq = boolWebDchq;
    }

    public String getBoolWebDchq() {
        return boolWebDchq;
    }
}
