package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GestionEpargneCmd implements ICommande {
    Context context = ContextHandler.getContext();

    public GestionEpargneCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
    
        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        ValueObject vo1 = (ValueObject)souscriptionContratCompteService.GestionEpargne(vo);
        return (vo1);
    }

}
