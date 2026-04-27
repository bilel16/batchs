package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetListAbonnementsInteretsCmd  implements ICommande{
    public GetListAbonnementsInteretsCmd() {
    }
    public IValueObject execute(IValueObject vo) {
    
    Context context = ContextHandler.getContext();
    PlacementService placementService = (PlacementService)context.getBean("placementService");
    Listes listesAbonnements = (Listes)placementService.getListAbonnementsInteretsService(vo);
    return (listesAbonnements);
    }
}
