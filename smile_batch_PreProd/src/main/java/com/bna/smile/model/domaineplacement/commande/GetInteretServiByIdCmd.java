package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetInteretServiByIdCmd implements ICommande{
    public GetInteretServiByIdCmd() {
    }
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        PlacementService placementService = (PlacementService)context.getBean("placementService");        
        return placementService.getInteretServiById(vo);
    }
}
