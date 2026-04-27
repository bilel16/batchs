package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

  /**
   * Prise en charge d’une demande de carte donnée.
   * @author Ramzi
   * @param DemandeCarteSignataire
   * @return DemandeCarte
   * @since 21/06/2007
   * 
   */
public class PecDemandeCarteCmd {
    public PecDemandeCarteCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");
        return (ValueObject)demandeCartesService.pecDemandeCarte(vo);
    }
}
