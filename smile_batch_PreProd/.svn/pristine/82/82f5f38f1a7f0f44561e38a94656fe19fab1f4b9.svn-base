package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamInsertContrat;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class MAJContratClientTransfertEpargneCmd implements ICommande {
    public MAJContratClientTransfertEpargneCmd() {
    }
    
    /**
     * methode execute
     * @param  vo Objet : ParamInsertContrat
     * @return vo Objet : ContratCpt
     */
    public IValueObject execute(IValueObject vo) {
        ParamInsertContrat paramInsertContrat = (ParamInsertContrat)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        ValueObject v = (ValueObject)souscriptionContratCompteService.MAJContratClientTransfertEpergne(paramInsertContrat);
        return (v);
    
    }

}
