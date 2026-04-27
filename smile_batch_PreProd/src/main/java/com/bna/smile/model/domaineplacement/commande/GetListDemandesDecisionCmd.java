package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
/**
 * Commande permet de fournir la liste de toutes les demandes de décision de placement 
 * par etat de demande
 * @author El arbi hassine
 * @since 02/11/2007    
 * @version 0.1
 */
public class GetListDemandesDecisionCmd implements ICommande{
    public GetListDemandesDecisionCmd() {
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
        Listes listesDemandesDecisionPlac = 
            (Listes)placementService.getListDemandesDecisionPlacement(paramDemandeDecision);
        return (listesDemandesDecisionPlac);
    }
}
