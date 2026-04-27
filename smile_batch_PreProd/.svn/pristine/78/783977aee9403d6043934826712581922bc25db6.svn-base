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
import com.bna.smile.model.domainecommun.service.ExonerationTVAService;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class GetListExonerationTvaCmd implements ICommande {
    public GetListExonerationTvaCmd() {
    }

    /**
     * methode execute
     * @param  vo Objet : ParamRechercheOpposition
     * @return vo Objet : Listes des exonerations tva
     * @author lamia
     */
    public IValueObject execute(IValueObject vo) {
        ParamRechercheOpposition paramRecherche = (ParamRechercheOpposition)vo;
        Context context = ContextHandler.getContext();
        ExonerationTVAService exonerationTVAService = 
            (ExonerationTVAService)context.getBean("exonerationTVAService");
        Listes listeExonerationtva = 
            (Listes)exonerationTVAService.getListExonerationTva(paramRecherche);
        return (listeExonerationtva);
    }
   

}
