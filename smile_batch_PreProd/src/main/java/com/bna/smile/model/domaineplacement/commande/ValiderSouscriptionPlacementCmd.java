package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
  /**
   * Valider une souscription à un contrat de placement
   * @param   ContratPlacement
   * @return  ContratPlacement
   * 
   */
public class ValiderSouscriptionPlacementCmd implements ICommande{
    public ValiderSouscriptionPlacementCmd() {
    }
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamContratPlacement paramContratPlacement  = (ParamContratPlacement )vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        ContratPlacement contratPlacementNew = (ContratPlacement)placementService.validerSouscriptionCpla(paramContratPlacement);
        return contratPlacementNew;
    }
}
