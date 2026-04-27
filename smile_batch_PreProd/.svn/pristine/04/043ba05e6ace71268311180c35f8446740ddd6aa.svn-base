package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

  /**
   * Valider une souscription de contrat de placement
   * @param   ContratPlacement
   * @return  ContratPlacement
   * 
   */
public class PecSouscriptionPlacementCmd implements ICommande{
    public PecSouscriptionPlacementCmd () {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamContratPlacement paramContratPlacement  = (ParamContratPlacement )vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        ContratPlacement contratPlacementNew = (ContratPlacement)placementService.pecSouscriptionPlacement(paramContratPlacement);
        return contratPlacementNew;
    }
}
