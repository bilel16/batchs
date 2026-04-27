package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class ValiderAvancePlacementCmd implements ICommande{
    public ValiderAvancePlacementCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        AvancRembLiquid avancRembLiquid  = (AvancRembLiquid )vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        AvancRembLiquid avancRembLiquidNew = (AvancRembLiquid)placementService.validerAvancePlacement(avancRembLiquid);
        return avancRembLiquidNew;
    }

}
