package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.model.ParamLiquidation;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class TraitementLiquidationCmd implements ICommande{
    public TraitementLiquidationCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamLiquidation paramLiquidation = (ParamLiquidation)vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        AvancRembLiquid avancRembLiquidNew = (AvancRembLiquid)placementService.traitementLiquidation(paramLiquidation);
        return avancRembLiquidNew;
    }

}
