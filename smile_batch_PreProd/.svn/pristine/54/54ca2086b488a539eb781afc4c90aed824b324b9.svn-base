/** Fichier: GetListCotitulairePersonneCmd.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetListCotitulairePersonneCmd
 * package: com.bna.smile.model.souscriptionContratCompte.commande
 * Auteur : Ramzi
 */
package com.bna.smile.model.domainecommun.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class GetListCotitulairePersonneCmd implements ICommande{
    public GetListCotitulairePersonneCmd() {
    }

    /**
     * methode execute
     * @param  vo Objet : PersonneStrc
     * @return vo Objet : Listes
     */
    public IValueObject execute(IValueObject vo) {
        PersonneStrc personneStrc = (PersonneStrc)vo;
        Context context = ContextHandler.getContext();        
        PersonneService personneService = (PersonneService)context.getBean("personneService");

        Listes listeCotit = 
            (Listes)personneService.getListCotitulairePersonne(personneStrc);
        return (listeCotit);
    }

}
