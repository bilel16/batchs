package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.DetailEtatContrat;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamDetailEtatContrat;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;


/** Fichier: InsertDetailEtatContratCmd.java
 * @version 1.0.0 du 04/04/2007
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: InsertDetailEtatContratCmd.java
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author :El Arbi Hassine
 */
public class InsertDetailEtatContratCmd implements ICommande{
    public InsertDetailEtatContratCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        DetailEtatContrat detailEtatContrat = new DetailEtatContrat();
        ParamDetailEtatContrat paramDetailEtatContrat = 
            (ParamDetailEtatContrat)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        detailEtatContrat = 
                (DetailEtatContrat)souscriptionContratCompteService.insertDetailEtatContrat(paramDetailEtatContrat);
        return (detailEtatContrat);

    }
}
