
package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.ContratCptId;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;


import com.bna.smile.model.domainecommun.service.ContratCompteService;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


/** Fichier: GetContratMandatCmd.java 
 * @version 1.0.0 du 20/02/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetContratMandatCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author : Boussen Youssef & Kriaa Hatem
 */
public class GetContratMandatCmd implements ICommande{

    public GetContratMandatCmd() {
    }

    /**
     * Methode execute
     * @param vo Objet : ContratCptId
     * @return   Objet : ContratCptMandat
     */
    public IValueObject execute(IValueObject vo) {
        MandatRecherche mandatRecherche = (MandatRecherche)vo;
        Context context = ContextHandler.getContext();

        ContratCompteService contratCompteService = 
            (ContratCompteService)context.getBean("contratCompteService");
        ContratCptMandat contratCptMandat = 
            (ContratCptMandat)contratCompteService.GetContratMandat(mandatRecherche);
        return (contratCptMandat);
    }

}
