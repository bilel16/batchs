package com.bna.smile.model.banqueAssurance.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.service.AssuranceVoyageService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class InsertDetailContratAssuranceVoyageCmd implements ICommande{
    public InsertDetailContratAssuranceVoyageCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
  
    Context context = ContextHandler.getContext();
    AssuranceVoyageService assuranceVoyageService = (AssuranceVoyageService)context.getBean("assuranceVoyageService");
    return (assuranceVoyageService.insertDetailAssranceVoyage(vo));
    }
    
}
