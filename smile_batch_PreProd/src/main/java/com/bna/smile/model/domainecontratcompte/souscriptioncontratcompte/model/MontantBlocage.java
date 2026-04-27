package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.Blocage;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.TraceContrat;
import com.oxia.fwk.core.ValueObject;

public class MontantBlocage extends ValueObject{

    private ContratCpt contratCpt;
    private Long montantBlocage;
    private Long codNatureBlocage;
    private TraceContrat traceContrat;
    private Blocage blocage;
    private List listBlocageChoisi= new ArrayList();
    public MontantBlocage() {
    }

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setMontantBlocage(Long montantBlocage) {
        this.montantBlocage = montantBlocage;
    }

    public Long getMontantBlocage() {
        return montantBlocage;
    }

    public void setCodNatureBlocage(Long codNatureBlocage) {
        this.codNatureBlocage = codNatureBlocage;
    }

    public Long getCodNatureBlocage() {
        return codNatureBlocage;
    }


    public void setTraceContrat(TraceContrat traceContrat) {
        this.traceContrat = traceContrat;
    }

    public TraceContrat getTraceContrat() {
        return traceContrat;
    }

    public void setBlocage(Blocage blocage) {
        this.blocage = blocage;
    }

    public Blocage getBlocage() {
        return blocage;
    }

    public void setListBlocageChoisi(List listBlocageChoisi) {
        this.listBlocageChoisi = listBlocageChoisi;
    }

    public List getListBlocageChoisi() {
        return listBlocageChoisi;
    }
}
