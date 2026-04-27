package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model;

import com.bna.commun.model.ContratCpt;
import com.oxia.fwk.core.ValueObject;

public class ParamModificationIntituleCompteVo extends ValueObject{
    public ParamModificationIntituleCompteVo() {
    }
    
    private ContratCpt contratCpt;
    private Long codeStructure;
    private Long matricule;

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setCodeStructure(Long codeStructure) {
        this.codeStructure = codeStructure;
    }

    public Long getCodeStructure() {
        return codeStructure;
    }

    public void setMatricule(Long matricule) {
        this.matricule = matricule;
    }

    public Long getMatricule() {
        return matricule;
    }
}
