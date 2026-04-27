package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.DemandeDecision;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

  /**
   * recherche d'une demande de décision de placement
   * @author El arbi hassine
   * @param demandeDecision
   * @return demandeDecision
   * @since 05/11/2007
   * 
   */
public class GetDemandeDecisionCmd implements ICommande{
    public GetDemandeDecisionCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeDecision demandeDecision = (DemandeDecision)vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        DemandeDecision demandeDecisionNew = (DemandeDecision)placementService.getDemandeDecisionPlacement(demandeDecision);
        return demandeDecisionNew;
    }

}
