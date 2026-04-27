package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

  /**
   * Valider une avance sur capital
   * @param   ContratPlacement
   * @return  ContratPlacement
   * 
   */
public class ValiderPECAvancePlacementCmd implements ICommande{
    public ValiderPECAvancePlacementCmd () {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamContratPlacement paramContratPlacement  = (ParamContratPlacement )vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        AvancRembLiquid avancRembLiquidNew = (AvancRembLiquid)placementService.avancePlacement(paramContratPlacement);
        return avancRembLiquidNew;
    }
}
