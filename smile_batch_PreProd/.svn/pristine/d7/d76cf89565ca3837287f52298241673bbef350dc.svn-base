package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.CategoriePersonne;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.bna.smile.model.domainecommun.service.PersonneService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Rechercher la personne 
 * @param vo  : PersonneStrc
 * @return vO : Personne
 */
public class GetPersonneCmd implements ICommande {
    public GetPersonneCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
      
        Context context = ContextHandler.getContext();

        PersonneService personneService = 
            (PersonneService)context.getBean("personneService");
        
        return (personneService.getPersonne(vo));
    }
}
