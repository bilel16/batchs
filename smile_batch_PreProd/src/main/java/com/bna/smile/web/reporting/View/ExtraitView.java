package com.bna.smile.web.reporting.View;

public class ExtraitView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
    private String dateJour;
    private String libelleOperation;
    private String dateValeur;
    private String soldeApresOp;
    private String montantCredit;
    private String montantDebit;
    private String sensMontant;
    private String numOperation;
    private Long codeOperation; //(pour imprimer l'avis)
    private String netPercu;
    public ExtraitView() {
    }

    public void setDateJour(String dateJour) {
        this.dateJour = dateJour;
    }

    public String getDateJour() {
        return dateJour;
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

    public String getLibelleOperation() {
        return libelleOperation;
    }

    public void setDateValeur(String dateValeur) {
        this.dateValeur = dateValeur;
    }

    public String getDateValeur() {
        return dateValeur;
    }

    public void setSoldeApresOp(String soldeApresOp) {
        this.soldeApresOp = soldeApresOp;
    }

    public String getSoldeApresOp() {
        return soldeApresOp;
    }

    public void setMontantCredit(String montantCredit) {
        this.montantCredit = montantCredit;
    }

    public String getMontantCredit() {
        return montantCredit;
    }

    public void setMontantDebit(String montantDebit) {
        this.montantDebit = montantDebit;
    }

    public String getMontantDebit() {
        return montantDebit;
    }

    public void setSensMontant(String sensMontant) {
        this.sensMontant = sensMontant;
    }

    public String getSensMontant() {
        return sensMontant;
    }

    public void setNumOperation(String numOperation) {
        this.numOperation = numOperation;
    }

    public String getNumOperation() {
        return numOperation;
    }


    public void setCodeOperation(Long codeOperation) {
        this.codeOperation = codeOperation;
    }

    public Long getCodeOperation() {
        return codeOperation;
    }

    public void setNetPercu(String netPercu) {
        this.netPercu = netPercu;
    }

    public String getNetPercu() {
        return netPercu;
    }
}
