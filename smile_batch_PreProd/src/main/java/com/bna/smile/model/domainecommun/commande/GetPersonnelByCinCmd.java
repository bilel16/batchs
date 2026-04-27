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
 * @param vo  : Personnel
 * @return vO : Personnel
 */
public class GetPersonnelByCinCmd implements ICommande{
    public GetPersonnelByCinCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
      
        Context context = ContextHandler.getContext();

        PersonneService personneService = 
            (PersonneService)context.getBean("personneService");
        
        return (personneService.getPersonnelByCin(vo));
    }
}
