package com.bna.smile.model.domainecompensation.gestionrejet.model;

import com.bna.commun.model.ContratCptId;
import com.oxia.fwk.core.ValueObject;

public class Rejet extends ValueObject{

    private ContratCptId contratCptId;
    private String numchq;




    public Rejet() {
    }

    public void setContratCptId(ContratCptId contratCptId) {
        this.contratCptId = contratCptId;
    }

    public ContratCptId getContratCptId() {
        return contratCptId;
    }

    public void setNumchq(String numchq) {
        this.numchq = numchq;
    }

    public String getNumchq() {
        return numchq;
    }
}
