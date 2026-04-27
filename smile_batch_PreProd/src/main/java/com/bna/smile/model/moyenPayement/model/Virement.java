package com.bna.smile.model.moyenPayement.model;

import com.oxia.fwk.core.ValueObject;

public class Virement  extends ValueObject {
    private String ribBenificiaire;
    private String ribTireur;
    private String nprsTireur;
    private String mntVirement;
    private String motifOperation;
    private String codeBct;
    public void setRibBenificiaire(String ribBenificiaire) {
        this.ribBenificiaire = ribBenificiaire;
    }

    public String getRibBenificiaire() {
        return ribBenificiaire;
    }

    public void setRibTireur(String ribTireur) {
        this.ribTireur = ribTireur;
    }

    public String getRibTireur() {
        return ribTireur;
    }

    public void setNprsTireur(String nprsTireur) {
        this.nprsTireur = nprsTireur;
    }

    public String getNprsTireur() {
        return nprsTireur;
    }

    public void setMntVirement(String mntVirement) {
        this.mntVirement = mntVirement;
    }

    public String getMntVirement() {
        return mntVirement;
    }

    public void setMotifOperation(String motifOperation) {
        this.motifOperation = motifOperation;
    }

    public String getMotifOperation() {
        return motifOperation;
    }

    public void setCodeBct(String codeBct) {
        this.codeBct = codeBct;
    }

    public String getCodeBct() {
        return codeBct;
    }
}
