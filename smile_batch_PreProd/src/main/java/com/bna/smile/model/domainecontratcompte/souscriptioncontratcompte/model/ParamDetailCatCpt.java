package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;

import com.bna.commun.model.Categorie;
import com.bna.commun.model.ContratCpt;
import com.oxia.fwk.core.ValueObject;

/** Fichier: ParamDetailCatCpt.java version 1.0.0 du 04/04/2007
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: ParamDetailCatCpt
 * package: com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model
 * Auteur : El arbi hassine 
 */
public class ParamDetailCatCpt extends ValueObject {

    ContratCpt contratCpt = new ContratCpt();
    Categorie categorie = new Categorie();
    private String typeVersementEpargne;

    public ParamDetailCatCpt() {
    }


    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setTypeVersementEpargne(String typeVersementEpargne) {
        this.typeVersementEpargne = typeVersementEpargne;
    }

    public String getTypeVersementEpargne() {
        return typeVersementEpargne;
    }
}
