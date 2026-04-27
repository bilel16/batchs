package com.bna.smile.model.clotureDomaine.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.clotureDomaine.service.ClotureDomaineService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class DetailAvancRembLiqCmd implements ICommande{
    public DetailAvancRembLiqCmd() {
    }
    public IValueObject execute(IValueObject vo){
        Context context = ContextHandler.getContext();
        ClotureDomaineService clotureDomaineService = (ClotureDomaineService)context.getBean("clotureDomaineService");
        return (clotureDomaineService.detailAvancRembLiq(vo));
        
    }
}
