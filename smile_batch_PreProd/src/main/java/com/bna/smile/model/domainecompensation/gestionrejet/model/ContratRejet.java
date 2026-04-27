package com.bna.smile.model.domainecompensation.gestionrejet.model;

import com.bna.commun.model.ContratCpt;
import com.oxia.fwk.core.ValueObject;

public class ContratRejet extends ValueObject{

    private ContratCpt contratCpt;
    private ChequePresente chequePresente ;


    public ContratRejet() {
    }

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setChequePresente(ChequePresente chequePresente) {
        this.chequePresente = chequePresente;
    }

    public ChequePresente getChequePresente() {
        return chequePresente;
    }
}
