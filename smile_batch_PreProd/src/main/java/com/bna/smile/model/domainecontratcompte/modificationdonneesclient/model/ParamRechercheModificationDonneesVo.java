package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.Personne;
import com.bna.commun.model.TypeModification;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe pour la recherche des modifications des données client
 * elle contient les parametres de recherche
 * @author Mdimagh Med Lassaad
 * @since  1/07/07 
 */
public class ParamRechercheModificationDonneesVo extends ValueObject {
    private Personne personne;
    private Date dateModification;
    private Date dateDebut;
    private Date dateFin;
    private TypeModification TypeModification;
    private List listeDesModifications = new ArrayList();

    public ParamRechercheModificationDonneesVo() {
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setTypeModification(TypeModification typeModification) {
        this.TypeModification = typeModification;
    }

    public TypeModification getTypeModification() {
        return TypeModification;
    }

    public void setListeDesModifications(List listeDesModifications) {
        this.listeDesModifications = listeDesModifications;
    }

    public List getListeDesModifications() {
        return listeDesModifications;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateFin() {
        return dateFin;
    }
}
