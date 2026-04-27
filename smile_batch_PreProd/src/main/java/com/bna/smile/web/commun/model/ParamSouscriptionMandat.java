package com.bna.smile.web.commun.model;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Personne;
/**
 * Classe pour le passage de paramétres entre le module souscription et le module procuration
 * @author :Mdimagh Mohamed Lassaad
 * @since  :23/04/07
 */
public class ParamSouscriptionMandat extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
    private String      casDeSouscription; // Personne mineur ou personne Morale ("morale"/"mineur") 
    private ContratCpt  contratCompte;
    private Personne    personneMineur;
    private Personne    personneTuteur;
    private Personne    personneMorale;
    
    public ParamSouscriptionMandat() {
    }

    public void setCasDeSouscription(String casDeSouscription) {
        this.casDeSouscription = casDeSouscription;
    }

    public String getCasDeSouscription() {
        return casDeSouscription;
    }

    public void setContratCompte(ContratCpt contratCompte) {
        this.contratCompte = contratCompte;
    }

    public ContratCpt getContratCompte() {
        return contratCompte;
    }

    public void setPersonneMineur(Personne personneMineur) {
        this.personneMineur = personneMineur;
    }

    public Personne getPersonneMineur() {
        return personneMineur;
    }

    public void setPersonneTuteur(Personne personneTuteur) {
        this.personneTuteur = personneTuteur;
    }

    public Personne getPersonneTuteur() {
        return personneTuteur;
    }

    public void setPersonneMorale(Personne personneMorale) {
        this.personneMorale = personneMorale;
    }

    public Personne getPersonneMorale() {
        return personneMorale;
    }
}
