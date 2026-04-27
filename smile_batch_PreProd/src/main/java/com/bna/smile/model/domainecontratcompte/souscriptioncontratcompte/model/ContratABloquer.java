package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.MotifEtat;
import com.bna.commun.model.TraceContrat;
import com.oxia.fwk.core.ValueObject;

public class ContratABloquer  extends ValueObject{

    private ContratCpt contratCpt;
    private MotifEtat motifEtat;
    private TraceContrat traceContrat;



    public ContratABloquer() {
    }

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setMotifEtat(MotifEtat motifEtat) {
        this.motifEtat = motifEtat;
    }

    public MotifEtat getMotifEtat() {
        return motifEtat;
    }

    public void setTraceContrat(TraceContrat traceContrat) {
        this.traceContrat = traceContrat;
    }

    public TraceContrat getTraceContrat() {
        return traceContrat;
    }
}
