package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.DemandeDecision;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

  /**
   * Valider une demande décision de placement
   * @author El arbi hassine
   * @param  DemandeDecision
   * @return DemandeDecision
   * @since 31/10/2007
   * 
   */
public class ValiderDdeDecisionCmd implements ICommande{
    public ValiderDdeDecisionCmd () {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeDecision demandeDecision  = (DemandeDecision )vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        DemandeDecision  demandeDecisionNew = (DemandeDecision)placementService.validerDdeDecision(demandeDecision);
        return demandeDecisionNew;
    }
}
