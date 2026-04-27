package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetDetailsOperationPlacCmd implements ICommande{
    public GetDetailsOperationPlacCmd() {
    }
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        DetailsOperationPlacement detailsOperationPlacement = (DetailsOperationPlacement)vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        DetailsOperationPlacement detailsOperationPlacementNew = (DetailsOperationPlacement)placementService.getDetailsOperationPlac(detailsOperationPlacement);
        return detailsOperationPlacementNew;
    }
}
