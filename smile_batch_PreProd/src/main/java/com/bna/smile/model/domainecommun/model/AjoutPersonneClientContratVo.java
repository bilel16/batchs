package com.bna.smile.model.domainecommun.model;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Personne;

import com.oxia.fwk.core.ValueObject;

/** Classe de données permettant de communiquer la personne, le client et le contrat
     * qui seront ajouter au niveau de la base, elle renvoi les txt des erreurs
     * pour chaque Objet non inséré
     *  @author Mdimagh Lassaad
     *  @since 18-01-07
     *  @version 1.0
     *  */
public class AjoutPersonneClientContratVo extends ValueObject {
    private Personne personne;
    private Client client;
    private ContratCpt contratCpt;

    private boolean estAjouterPersonne = true;
    private String TextErreurAjoutPersonne;
    private boolean estAjouterClient = true;
    private String TextErreurAjoutClient;
    private boolean estAjouterContrat = true;
    private String TextErreurAjoutContrat;

    public AjoutPersonneClientContratVo() {
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


    public void setEstAjouterPersonne(boolean estAjouterPersonne) {
        this.estAjouterPersonne = estAjouterPersonne;
    }

    public boolean isEstAjouterPersonne() {
        return estAjouterPersonne;
    }

    public void setTextErreurAjoutPersonne(String textErreurAjoutPersonne) {
        this.TextErreurAjoutPersonne = textErreurAjoutPersonne;
    }

    public String getTextErreurAjoutPersonne() {
        return TextErreurAjoutPersonne;
    }

    public void setEstAjouterClient(boolean estAjouterClient) {
        this.estAjouterClient = estAjouterClient;
    }

    public boolean isEstAjouterClient() {
        return estAjouterClient;
    }

    public void setTextErreurAjoutClient(String textErreurAjoutClient) {
        this.TextErreurAjoutClient = textErreurAjoutClient;
    }

    public String getTextErreurAjoutClient() {
        return TextErreurAjoutClient;
    }

    public void setEstAjouterContrat(boolean estAjouterContrat) {
        this.estAjouterContrat = estAjouterContrat;
    }

    public boolean isEstAjouterContrat() {
        return estAjouterContrat;
    }

    public void setTextErreurAjoutContrat(String textErreurAjoutContrat) {
        this.TextErreurAjoutContrat = textErreurAjoutContrat;
    }

    public String getTextErreurAjoutContrat() {
        return TextErreurAjoutContrat;
    }
}
