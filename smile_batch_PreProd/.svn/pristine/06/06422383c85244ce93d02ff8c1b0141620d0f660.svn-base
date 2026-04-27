package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;


  /**
   * recherche d'une Avance placement par son num seq
   * @author BOUSSEN Youssef
   * @param AvancRembLiquid
   * @return AvancRembLiquid
   * @since 05/03/2009
   * 
   */

public class GetAvancRembLiquidByIdCmd implements ICommande{
    public GetAvancRembLiquidByIdCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        AvancRembLiquid avancRembLiquid = (AvancRembLiquid)vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        AvancRembLiquid avancRembLiquidRetour = (AvancRembLiquid)placementService.getAvancRembLiquidById(avancRembLiquid);
        return avancRembLiquidRetour;
    }

}
