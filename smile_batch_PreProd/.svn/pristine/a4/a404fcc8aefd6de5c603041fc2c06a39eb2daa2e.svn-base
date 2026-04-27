package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.model.ParamAvanRembLiq;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetListAvancRembLiquidByEtatCmd implements ICommande{
    public GetListAvancRembLiquidByEtatCmd() {
    }
    
    /**
         * methode execute 
         * @param value Object :  ParamVO
         * @return value Object : Listes
         */
        public IValueObject execute(IValueObject vo) {
            Context context = ContextHandler.getContext();
            ParamAvanRembLiq paramAvanRembLiq = (ParamAvanRembLiq)vo;
            PlacementService placementService = (PlacementService)context.getBean("placementService");
            Listes listesAvances = (Listes)placementService.getListAvancRembLiquidByEtat(paramAvanRembLiq);
            return (listesAvances);
        }

}
