package com.bna.smile.model.clotureDomaine.model;

import com.bna.commun.model.JourneeStructureDomaine;
import com.oxia.fwk.core.ValueObject;

public class JournStructDomEtatVo extends ValueObject{
    public JournStructDomEtatVo() {
    }
    private JourneeStructureDomaine journeeStructureDomaine;
    private String etat;
   

    public void setJourneeStructureDomaine(JourneeStructureDomaine journeeStructureDomaine) {
        this.journeeStructureDomaine = journeeStructureDomaine;
    }

    public JourneeStructureDomaine getJourneeStructureDomaine() {
        return journeeStructureDomaine;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getEtat() {
        return etat;
    }

   
}
