package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;

import com.bna.commun.model.Categorie;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailCatCpt;
import com.oxia.fwk.core.ValueObject;

public class ParamMiseAjourDetailcatCpt extends ValueObject {

    ContratCpt contratCpt = new ContratCpt();
    DetailCatCpt detailCatCpt=new DetailCatCpt();
    
    Categorie nouvelleCategorie = new Categorie();
    String type;

    
    public ParamMiseAjourDetailcatCpt() {  
    }

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

   

    public void setNouvelleCategorie(Categorie nouvelleCategorie) {
        this.nouvelleCategorie = nouvelleCategorie;
    }

    public Categorie getNouvelleCategorie() {
        return nouvelleCategorie;
    }

    public void setDetailCatCpt(DetailCatCpt detailCatCpt) {
        this.detailCatCpt = detailCatCpt;
    }

    public DetailCatCpt getDetailCatCpt() {
        return detailCatCpt;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
