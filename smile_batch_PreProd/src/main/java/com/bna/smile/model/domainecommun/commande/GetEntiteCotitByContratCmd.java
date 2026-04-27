package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;


import com.bna.smile.model.domainecommun.service.ContratCompteService;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


/** Fichier: GetEntiteCotitByContratCmd.java 
 * @version 1.0.0 du 18/04/2007
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: GetEntiteCotitByContratCmd
 * package com.bna.smile.model.domainecommun.commande
 * @author : El arbi hassine
 */
public class GetEntiteCotitByContratCmd implements ICommande {
    public GetEntiteCotitByContratCmd() {
    }


    /**
     * Methode execute
     * @param vo Objet : ContratCpt
     * @return   Objet : Cotitulaire
     */
    public IValueObject execute(IValueObject vo) {
        ContratCpt contratCpt = (ContratCpt)vo;
        Context context = ContextHandler.getContext();

        ContratCompteService contratCompteService = 
            (ContratCompteService)context.getBean("contratCompteService");
        CoTitulaire coTitulaire = 
            (CoTitulaire)contratCompteService.GetEntiteCotitByContrat(contratCpt);
        return (coTitulaire);
    }

}
