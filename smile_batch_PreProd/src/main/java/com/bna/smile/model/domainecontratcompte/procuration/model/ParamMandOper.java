package com.bna.smile.model.domainecontratcompte.procuration.model;

import java.util.Date;
import java.util.List;

import com.bna.commun.model.ContratCptId;
import com.oxia.fwk.core.ValueObject;

public class ParamMandOper extends ValueObject{

    private Date dateDebutOper;
    private Date dateFinOper;
    private Long codOper;
    private Long codtach;
    private List listeMandatOperations;
    private ContratCptId contratCptId;
   
    
    
    public ParamMandOper() {
    }

    public void setDateDebutOper(Date dateDebutOper) {
        this.dateDebutOper = dateDebutOper;
    }

    public Date getDateDebutOper() {
        return dateDebutOper;
    }

    public void setDateFinOper(Date dateFinOper) {
        this.dateFinOper = dateFinOper;
    }

    public Date getDateFinOper() {
        return dateFinOper;
    }

    public void setCodOper(Long codOper) {
        this.codOper = codOper;
    }

    public Long getCodOper() {
        return codOper;
    }

    public void setCodtach(Long codtach) {
        this.codtach = codtach;
    }

    public Long getCodtach() {
        return codtach;
    }

    public void setListeMandatOperations(List listeMandatOperations) {
        this.listeMandatOperations = listeMandatOperations;
    }

    public List getListeMandatOperations() {
        return listeMandatOperations;
    }

    public void setContratCptId(ContratCptId contratCptId) {
        this.contratCptId = contratCptId;
    }

    public ContratCptId getContratCptId() {
        return contratCptId;
    }
}
