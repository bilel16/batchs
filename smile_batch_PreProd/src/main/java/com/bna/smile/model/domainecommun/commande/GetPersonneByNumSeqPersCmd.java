package com.bna.smile.model.domainecommun.commande;


import com.bna.commun.model.Personne;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de cherche une personne par son numero sequentiel
 * @author Mdimagh Med
 * @since 30/05/07
 */
public class GetPersonneByNumSeqPersCmd implements ICommande{
    public GetPersonneByNumSeqPersCmd() {
    }

    /**
     * executer la recherce de l'objet Personne
     * @param vo  : Personne
     * @return vO : Personne
     */
    public IValueObject execute(IValueObject vo) {
        Personne personne = (Personne)vo;
        Context context = ContextHandler.getContext();

        PersonneService personneService = 
            (PersonneService)context.getBean("personneService");
        personne = (Personne)personneService.getPersonneByNumSeqPers(personne);
        return (personne);
    }
}
