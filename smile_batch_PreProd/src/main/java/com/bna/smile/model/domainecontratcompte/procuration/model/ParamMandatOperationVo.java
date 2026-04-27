package com.bna.smile.model.domainecontratcompte.procuration.model;

import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Operation;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.oxia.fwk.core.ValueObject;
/**
 * Value Object represantant le parametre de la commande:
 * GetMandatOperationCmd
 * @author Mdimagh Mohamed Lassaad
 * @since 07/05/2007
 */
public class ParamMandatOperationVo  extends ValueObject {
    
    private ContratCptId    contraCptId;
    private Operation       operation;
    private PersonneStrc    personneStrc;
    
    
    public ParamMandatOperationVo() {
    }


    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setPersonneStrc(PersonneStrc personneStrc) {
        this.personneStrc = personneStrc;
    }

    public PersonneStrc getPersonneStrc() {
        return personneStrc;
    }

    public void setContraCptId(ContratCptId contraCptId) {
        this.contraCptId = contraCptId;
    }

    public ContratCptId getContraCptId() {
        return contraCptId;
    }
}
