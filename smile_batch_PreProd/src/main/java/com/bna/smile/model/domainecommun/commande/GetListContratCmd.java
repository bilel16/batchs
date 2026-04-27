/** Fichier: GetListMembreCotitulaireCmd.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetListMembreCotitulaireCmd
 * package: com.bna.smile.model.souscriptionContratCompte.commande
 * Auteur : Ramzi
 */
package com.bna.smile.model.domainecommun.commande;


import com.bna.commun.model.ContratCptId;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneRechercheContratVo;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.ContratCompteService;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class GetListContratCmd implements ICommande {
    public GetListContratCmd() {
    }

    /**
     * methode execute
     * @param  vo Objet : PersonneStrc
     * @return vo Objet : Listes membres cotitulaires
     */
    public IValueObject execute(IValueObject vo) {
        PersonneRechercheContratVo personneRechercheContratVo = (PersonneRechercheContratVo)vo;
        Context context = ContextHandler.getContext();
        PersonneService personneService = 
            (PersonneService)context.getBean("personneService");

        Listes listeContrat = 
            (Listes)personneService.getListContrat(personneRechercheContratVo);
        return (listeContrat);
    }
   

}
