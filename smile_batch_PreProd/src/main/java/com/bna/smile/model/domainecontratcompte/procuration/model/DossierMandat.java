package com.bna.smile.model.domainecontratcompte.procuration.model;

import com.oxia.fwk.core.ValueObject;

public class DossierMandat extends ValueObject{

    private Long numDemMand;
    private Long codStrcConcer;
   
    
    
    public DossierMandat() {
    }


    public void setNumDemMand(Long numDemMand) {
        this.numDemMand = numDemMand;
    }

    public Long getNumDemMand() {
        return numDemMand;
    }

   

    public void setCodStrcConcer(Long codStrcConcer) {
        this.codStrcConcer = codStrcConcer;
    }

    public Long getCodStrcConcer() {
        return codStrcConcer;
    }

    
}
