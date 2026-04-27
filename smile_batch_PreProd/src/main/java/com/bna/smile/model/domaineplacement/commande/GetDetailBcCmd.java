package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.DetailsBc;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetDetailBcCmd implements ICommande{
    public GetDetailBcCmd() {
    }
    
    /**
         * methode execute 
         * @param value Object :  DetailsBc
         * @return value Object : DetailsBc
         */
        public IValueObject execute(IValueObject vo) {
            Context context = ContextHandler.getContext();
            DetailsBc detailsBc = (DetailsBc)vo;
            PlacementService placementService = (PlacementService)context.getBean("placementService");
            DetailsBc DetailsBcNew = (DetailsBc)placementService.getDetailBc(detailsBc);
            return (DetailsBcNew);
        }

}
