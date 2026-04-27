package com.bna.smile.model.debutJournee.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.debutJournee.service.DebutJourneeService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetDonneeDebutJourneeCmd implements ICommande{
    public GetDonneeDebutJourneeCmd() {
    }
    public IValueObject execute(IValueObject vo){
        Context context = ContextHandler.getContext();
        DebutJourneeService debutJourneeService = (DebutJourneeService)context.getBean("debutJourneeService");
        return (debutJourneeService.getdonneeDebJournee(vo));
        
    }
}
