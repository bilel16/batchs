package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
/**
 * Commande permet de fournir la liste de tout les contrats de placement 
 * par numéro de compte
 * @since 04/12/2007    
 * @version 0.1
 */
public class GetListContratsPlacementCmd implements ICommande {
    public GetListContratsPlacementCmd() {
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
        Listes listesContratsPlac = 
            (Listes)placementService.getListContratsPlacement(paramDemandeDecision);
        return (listesContratsPlac);
    }
}
