package com.bna.smile.model.domainecommun.commande;


import com.bna.commun.model.CompteInterne;
import com.bna.commun.model.CompteInterneId;
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


/** Fichier: GetDetailCptInterneCmd.java 
 * @version 1.0.0 du 14/10/2008
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: GetDetailCptInterneCmd
 * package com.bna.smile.model.domainecommun.commande
 * @author : El arbi hassine
 */
public class GetDetailCptInterneCmd implements ICommande {
    public GetDetailCptInterneCmd() {
    }


    /**
     * Methode execute
     * @param vo Objet : ContratCptId
     * @return   Objet : ContratCpt
     */
    public IValueObject execute(IValueObject vo) {
        CompteInterneId compteInterneId = (CompteInterneId)vo;
        Context context = ContextHandler.getContext();

        ContratCompteService contratCompteService = 
            (ContratCompteService)context.getBean("contratCompteService");
        CompteInterne compteInterne = 
            (CompteInterne)contratCompteService.GetDetailCptInterne(compteInterneId);
        return (compteInterne);
    }

}
