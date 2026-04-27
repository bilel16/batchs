package com.bna.smile.model.domaineplacement.commande;

import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetListProduitPlacementCmd implements ICommande{
    public GetListProduitPlacementCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
      /*  Context context = ContextHandler.getContext();
        Listes liste = (Listes)vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        Listes listeProduitPlac = (Listes)placementService.getListeProduitPlacement(liste);
        return listeProduitPlac;*/return null;
    }
}
