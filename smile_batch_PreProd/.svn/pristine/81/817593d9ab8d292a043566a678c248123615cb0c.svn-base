
package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;


import com.bna.smile.model.domainecommun.service.PersonneService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


/** Fichier: GetPersonneCptCmd.java
 * @version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetPersonneCptCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author : Boussen Youssef & Kriaa Hatem
 */
public class GetPersonneCptCmd implements ICommande{

    public GetPersonneCptCmd() {
    }

    /**
     * Methode execute
     * @param vo Objet : PersonneStrc : identifiants de la personne + code agence
     * @return   Objet : PersonneCpt
     */
    public IValueObject execute(IValueObject vo) {
        PersonneStrc personneStrc = (PersonneStrc)vo;
        Context context = ContextHandler.getContext();

        PersonneService personneService = 
            (PersonneService)context.getBean("personneService");
        PersonneCpt personneCpt = 
            (PersonneCpt)personneService.getPersonneCpt(personneStrc);
        return (personneCpt);
    }

}
