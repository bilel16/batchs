

package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.Personne;

import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.domainecommun.service.PersonneService;

import com.bna.smile.model.domainecommun.model.PersonneCpt;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: UpdatePersonneCmd.java
 * @version 1.0.0 du 19/10/2009
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: UpdatePersonneCmd
 * package com.bna.smile.model.domainecommun.commande
 * @author : el arbi hassine
 */
public class UpdatePersonneCmd implements ICommande {
    public UpdatePersonneCmd() {
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
            (Personne)personneService.updatePersonne(personne);
        return (personneRetour);
    }
}
