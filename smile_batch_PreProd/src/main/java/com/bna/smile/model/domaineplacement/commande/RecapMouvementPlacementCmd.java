package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class RecapMouvementPlacementCmd implements ICommande{
    public RecapMouvementPlacementCmd() {
    }
    /**
         * methode execute 
         * @param value Object :  ParamDemandeDecision
         * @return value Object : Listes
         */
        public IValueObject execute(IValueObject vo) {
            Context context = ContextHandler.getContext();
            ParamDemandeDecision paramDemandeDecision = (ParamDemandeDecision)vo;
            PlacementService placementService = (PlacementService)context.getBean("placementService");
            Listes recapMouvement =(Listes)placementService.recapMouvementPlacService(paramDemandeDecision);
            return (recapMouvement);
        }
}
