package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.model.ParamBonCaisse;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetListBcRecupereCmd implements ICommande{
    public GetListBcRecupereCmd() {
    }
    
    /**
         * methode execute 
         * @param value Object :  DetailsBc
         * @return value Object : DetailsBc
         */
        public IValueObject execute(IValueObject vo) {
            Context context = ContextHandler.getContext();
            ParamBonCaisse paramBonCaisse = (ParamBonCaisse)vo;
            PlacementService placementService = (PlacementService)context.getBean("placementService");
            Listes listBcRecupere = (Listes)placementService.getListBcRecupere(paramBonCaisse);
            return (listBcRecupere);
        }

}
