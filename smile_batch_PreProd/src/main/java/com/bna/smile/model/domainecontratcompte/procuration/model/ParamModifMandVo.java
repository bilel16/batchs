package com.bna.smile.model.domainecontratcompte.procuration.model;

import com.bna.commun.model.Mandat;
import com.oxia.fwk.core.ValueObject;

public class ParamModifMandVo extends ValueObject {
    public ParamModifMandVo() {
    }
    
    private Mandat mandat;
    private ParamInsertMandat paramInsertMandat;
    private String typevalidation;

    public void setMandat(Mandat mandat) {
        this.mandat = mandat;
    }

    public Mandat getMandat() {
        return mandat;
    }

    public void setParamInsertMandat(ParamInsertMandat paramInsertMandat) {
        this.paramInsertMandat = paramInsertMandat;
    }

    public ParamInsertMandat getParamInsertMandat() {
        return paramInsertMandat;
    }

    public void setTypevalidation(String typevalidation) {
        this.typevalidation = typevalidation;
    }

    public String getTypevalidation() {
        return typevalidation;
    }
}
