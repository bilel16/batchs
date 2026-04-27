package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.model.ParamLiquidation;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

  /**
   * Valider la recuperation du bon de caisse
   * @author El arbi hassine
   * @param  DetailBc
   * @return DetailBc
   * @since 13/10/2009
   * 
   */
public class ValiderRecuperationBcCmd implements ICommande{
    public ValiderRecuperationBcCmd () {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamLiquidation paramLiquidation  = (ParamLiquidation )vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        paramLiquidation = (ParamLiquidation)placementService.validerRecuperationBc(paramLiquidation);
        return paramLiquidation;
    }
}
