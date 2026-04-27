package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.ContratCptId;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.service.ContratCompteService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetContratEtatCmd implements ICommande {
    public GetContratEtatCmd() {
    }
    /**
     * Methode execute
     * @param vo Objet : ContratCptId
     * @return   Objet : ContratCptMandat
     */
    public IValueObject execute(IValueObject vo) {
        ContratCptId contratCptId = (ContratCptId)vo;
        Context context = ContextHandler.getContext();

        ContratCompteService contratCompteService = 
            (ContratCompteService)context.getBean("contratCompteService");
        ContratCptMandat contratCptMandat = 
            (ContratCptMandat)contratCompteService.GetContratEtat(contratCptId);
        return (contratCptMandat);
    }

    }