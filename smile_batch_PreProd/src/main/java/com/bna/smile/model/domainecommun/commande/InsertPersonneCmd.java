

package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.Personne;

import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.domainecommun.service.PersonneService;

import com.bna.smile.model.domainecommun.model.PersonneCpt;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: InsertPersonneCmd.java
 * @version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: InsertPersonneCmd
 * package com.bna.smile.model.domainecommun.commande
 * @author : Mdimagh lassaad
 */
public class InsertPersonneCmd implements ICommande {
    public InsertPersonneCmd() {
    }

    /**
     *  methode execute
     * @param   Objet :Personne;
     * @return  Objet :Personne;
     */
    public IValueObject execute(IValueObject vo) {
        Personne personne = (Personne)vo;
        Context context = ContextHandler.getContext();

        PersonneService personneService = 
            (PersonneService)context.getBean("personneService");
        Personne personneRetour = 
            (Personne)personneService.insertPersonne(personne);
        return (personneRetour);
    }
}
