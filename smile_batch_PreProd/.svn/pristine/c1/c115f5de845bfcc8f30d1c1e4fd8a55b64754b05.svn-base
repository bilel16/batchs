package com.bna.smile.model.domainecommun.model;

import com.bna.commun.model.Personne;

import com.oxia.fwk.core.ValueObject;

import java.util.ArrayList;
import java.util.List;

/** Classe de données permettant de communiquer la personne Tuteur et les personnes mineures
 *  en charge
 *  @author Mdimagh Lassaad
 *  @since 18-01-07
 *  @version 1.0
 *  */
public class

Tuteur extends ValueObject {
    /** la personne Tuteur*/
    private Personne personneTuteur;

    /** si la personne est tuteur retourne TRUE sion False */
    private boolean isTuteur = false;

    /** Liste des personnes Mineures de type Personne */
    private List listeDesMineures = new ArrayList();

    /** Constructeur*/
    public

    Tuteur() {
    }

    public void setPersonneTuteur(Personne personneTuteur) {
        this.personneTuteur = personneTuteur;
    }

    public Personne getPersonneTuteur() {
        return personneTuteur;
    }

    public void setListeDesMineures(List listeDesMineures) {
        this.listeDesMineures = listeDesMineures;
    }

    public List getListeDesMineures() {
        return listeDesMineures;
    }


    public void setIsTuteur(boolean isTuteur) {
        this.isTuteur = isTuteur;
    }

    public boolean isIsTuteur() {
        return isTuteur;
    }
}
