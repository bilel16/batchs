package com.bna.smile.model.domainecaisse.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.service.CaisseService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetListJourneeCaisseCmd implements ICommande{
    public GetListJourneeCaisseCmd() {
    }
    
    public   IValueObject execute(IValueObject vo) {
       
        Context context = ContextHandler.getContext();

       CaisseService caisseService = (CaisseService)context.getBean("caisseService");
        
        return (caisseService.getListJourneeCaisse(vo));
    }    
}
