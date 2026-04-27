package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.DemandeDecision;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

  /**
   * Valider une demande décision de placement
   * @author Jerbi Lamia & Belhadj Saida
   * @param  DemandeDecision
   * @return DemandeDecision
   * @since 31/10/2007
   * 
   */
   
public class ValiderMajDdeDecisionCmd implements ICommande{
    public ValiderMajDdeDecisionCmd () {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeDecision demandeDecision  = (DemandeDecision )vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        DemandeDecision  demandeDecisionNew = (DemandeDecision)placementService.validerMajDdeDecision(demandeDecision);
        return demandeDecisionNew;
    }
}
