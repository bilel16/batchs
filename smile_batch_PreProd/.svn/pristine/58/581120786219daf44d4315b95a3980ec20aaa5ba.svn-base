package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

  /**
   * recherche d'un contrat de placement
   * @author El arbi hassine
   * @param ContratPlacement
   * @return ContratPlacement
   * @since 30/10/2007
   * 
   */
public class GetContratPlacementCmd  implements ICommande{
    public GetContratPlacementCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ContratPlacement contratPlacement = (ContratPlacement)vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        ContratPlacement contratPlacementNew = (ContratPlacement)placementService.getContratPlacement(contratPlacement);
        return contratPlacementNew;
    }

}
