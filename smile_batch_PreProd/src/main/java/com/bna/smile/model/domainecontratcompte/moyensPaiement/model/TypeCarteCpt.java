package com.bna.smile.model.domainecontratcompte.moyensPaiement.model;


import com.bna.commun.model.ContratCpt;
import com.oxia.fwk.core.ValueObject;

public class TypeCarteCpt  extends ValueObject {

    private Long typeCarte; 
    private ContratCpt contratCpt;

    public TypeCarteCpt() {
    }


   

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setTypeCarte(Long typeCarte) {
        this.typeCarte = typeCarte;
    }

    public Long getTypeCarte() {
        return typeCarte;
    }
}
