package com.bna.smile.model.domainecommun.model;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Personne;

import com.oxia.fwk.core.ValueObject;

public class ContratPersonne extends ValueObject implements java.io.Serializable{
    public ContratPersonne() {
    }
    private PersonneStrc personneId;
    private ContratCptId contratCptId = new ContratCptId();
    private Operation operation;

    public void setPersonneId(PersonneStrc personneId) {
        this.personneId = personneId;
    }

    public PersonneStrc getPersonneId() {
        return personneId;
    }


    public void setContratCptId(ContratCptId contratCptId) {
        this.contratCptId = contratCptId;
    }

    public ContratCptId getContratCptId() {
        return contratCptId;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }
}
