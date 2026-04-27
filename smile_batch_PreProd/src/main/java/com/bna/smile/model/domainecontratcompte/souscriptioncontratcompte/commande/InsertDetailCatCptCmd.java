package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamDetailCatCpt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: InsertDetailCatCptCmd.java
 * @version 1.0.0 du 04/04/2007
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: InsertDetailCatCptCmd.java
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author :El Arbi Hassine
 */
public class InsertDetailCatCptCmd implements ICommande {
    public InsertDetailCatCptCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        DetailCatCpt detailCatCpt = new DetailCatCpt();
        ParamDetailCatCpt paramDetailCatCpt = (ParamDetailCatCpt)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        detailCatCpt = 
                (DetailCatCpt)souscriptionContratCompteService.insertDetailCatContrat(paramDetailCatCpt);
        return (detailCatCpt);

    }
}
