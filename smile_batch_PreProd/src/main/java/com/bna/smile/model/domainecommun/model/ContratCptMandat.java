package com.bna.smile.model.domainecommun.model;

import com.bna.commun.model.ContratCpt;

import com.oxia.fwk.core.ValueObject;

import java.util.List;

public class ContratCptMandat extends ValueObject {

    private ContratCpt ContratCpt;
    private List listeMandat;
    private List listeMandataire;
    private boolean verifEtat;
    private String messageEtat;

    public ContratCptMandat() {
    }

    public void setContratCpt(ContratCpt contratCpt) {
        this.ContratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return ContratCpt;
    }

    public void setListeMandat(List listeMandat) {
        this.listeMandat = listeMandat;
    }

    public List getListeMandat() {
        return listeMandat;
    }


    public void setListeMandataire(List listeMandataire) {
        this.listeMandataire = listeMandataire;
    }

    public List getListeMandataire() {
        return listeMandataire;
    }

    public void setVerifEtat(boolean verifEtat) {
        this.verifEtat = verifEtat;
    }

    public boolean isVerifEtat() {
        return verifEtat;
    }

    public void setMessageEtat(String messageEtat) {
        this.messageEtat = messageEtat;
    }

    public String getMessageEtat() {
        return messageEtat;
    }
}
