package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;

import com.bna.commun.model.LivretEpargne;
import com.oxia.fwk.core.ValueObject;

public class Livrets extends ValueObject {

    private LivretEpargne AncienLivret;
    private LivretEpargne NouveauLivret;

    public Livrets() {
    }

    public void setAncienLivret(LivretEpargne ancienLivret) {
        this.AncienLivret = ancienLivret;
    }

    public LivretEpargne getAncienLivret() {
        return AncienLivret;
    }

    public void setNouveauLivret(LivretEpargne nouveauLivret) {
        this.NouveauLivret = nouveauLivret;
    }

    public LivretEpargne getNouveauLivret() {
        return NouveauLivret;
    }
}
