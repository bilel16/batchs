package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;

import com.bna.commun.model.ContratCptId;
import com.oxia.fwk.core.ValueObject;

public class BlocageCriteres extends ValueObject{
    private Long numBlocBloc;
    private ContratCptId contratCptId;
    private Long mntBlocSup;
    private Long mntBlocinf;
    private Long codNatuBloc;
    
    
    public BlocageCriteres() {
    }

    public void setNumBlocBloc(Long numBlocBloc) {
        this.numBlocBloc = numBlocBloc;
    }

    public Long getNumBlocBloc() {
        return numBlocBloc;
    }

    public void setMntBlocSup(Long mntBlocSup) {
        this.mntBlocSup = mntBlocSup;
    }

    public Long getMntBlocSup() {
        return mntBlocSup;
    }

    public void setMntBlocinf(Long mntBlocinf) {
        this.mntBlocinf = mntBlocinf;
    }

    public Long getMntBlocinf() {
        return mntBlocinf;
    }

    public void setCodNatuBloc(Long codNatuBloc) {
        this.codNatuBloc = codNatuBloc;
    }

    public Long getCodNatuBloc() {
        return codNatuBloc;
    }

   

    public void setContratCptId(ContratCptId contratCptId) {
        this.contratCptId = contratCptId;
    }

    public ContratCptId getContratCptId() {
        return contratCptId;
    }
}
