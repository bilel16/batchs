package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetListInteretServiCmd implements ICommande{
    public GetListInteretServiCmd() {
    }
    
    /**
         * methode execute 
         * @param value Object :  ParamVO
         * @return value Object : Listes
         */
        public IValueObject execute(IValueObject vo) {
            Context context = ContextHandler.getContext();
            ContratPlacement contratPlacement = (ContratPlacement)vo;
            PlacementService placementService = (PlacementService)context.getBean("placementService");
            Listes listesInteret = (Listes)placementService.getListInteretServi(contratPlacement);
            return (listesInteret);
        }

}
