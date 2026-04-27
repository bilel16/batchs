package com.bna.smile.model.moyenPayement.model;

import com.oxia.fwk.core.ValueObject;

public class Accuse  extends ValueObject{
    public Accuse() {
    }
    private Long codeValeur;
    private Long nbrePresentation;
    private String mntPresentation;
    private String libValeur;
    private String dateComptable;
    private String dateChargement;
    private Long codeStructure;
    private Long codeTypeValeur;
    private Long numeroLot;
    private String codeBct;
    public void setCodeValeur(Long codeValeur) {
        this.codeValeur = codeValeur;
    }

    public Long getCodeValeur() {
        return codeValeur;
    }

   

    public void setMntPresentation(String mntPresentation) {
        this.mntPresentation = mntPresentation;
    }

    public String getMntPresentation() {
        return mntPresentation;
    }

    public void setLibValeur(String libValeur) {
        this.libValeur = libValeur;
    }

    public String getLibValeur() {
        return libValeur;
    }

    public void setDateComptable(String dateComptable) {
        this.dateComptable = dateComptable;
    }

    public String getDateComptable() {
        return dateComptable;
    }

    public void setDateChargement(String dateChargement) {
        this.dateChargement = dateChargement;
    }

    public String getDateChargement() {
        return dateChargement;
    }

    public void setCodeStructure(Long codeStructure) {
        this.codeStructure = codeStructure;
    }

    public Long getCodeStructure() {
        return codeStructure;
    }

    public void setCodeTypeValeur(Long codeTypeValeur) {
        this.codeTypeValeur = codeTypeValeur;
    }

    public Long getCodeTypeValeur() {
        return codeTypeValeur;
    }

    public void setNumeroLot(Long numeroLot) {
        this.numeroLot = numeroLot;
    }

    public Long getNumeroLot() {
        return numeroLot;
    }

    public void setNbrePresentation(Long nbrePresentation) {
        this.nbrePresentation = nbrePresentation;
    }

    public Long getNbrePresentation() {
        return nbrePresentation;
    }

    public void setCodeBct(String codeBct) {
        this.codeBct = codeBct;
    }

    public String getCodeBct() {
        return codeBct;
    }
}
