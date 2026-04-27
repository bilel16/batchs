package com.bna.smile.model.domainecommun.model;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Personne;

import com.oxia.fwk.core.ValueObject;

/** Classe de données permettant de communiquer la personne, le client et le contrat
     *  @author Mdimagh Lassaad
     *  @since 18-01-07
     *  @version 1.0
     *  */
public class AjoutPersonneClientVo extends ValueObject {
    private Personne personne;
    private Client client;
    private ContratCpt contratCpt;

    public AjoutPersonneClientVo() {
    }


    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }
}
