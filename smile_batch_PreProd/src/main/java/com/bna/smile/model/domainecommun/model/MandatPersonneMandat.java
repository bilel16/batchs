package com.bna.smile.model.domainecommun.model;

import com.bna.commun.model.Mandat;

public class MandatPersonneMandat {


    private Mandat mandat;
    private String mandataires;
    private String etatMand;

    public MandatPersonneMandat() {
    }

    public void setMandat(Mandat mandat) {
        this.mandat = mandat;
    }

    public Mandat getMandat() {
        return mandat;
    }


    public void setMandataires(String mandataires) {
        this.mandataires = mandataires;
    }

    public String getMandataires() {
        return mandataires;
    }

    public void setEtatMand(String etatMand) {
        this.etatMand = etatMand;
    }

    public String getEtatMand() {
        return etatMand;
    }
}
