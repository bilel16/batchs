package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

  /**
   * Prise en charge d’une demande de modification carte .
   * @author Ramzi
   * @return DemandeCarte
   * @since 08/04/2009
   * 
   */
public class DemandeModifPlafondCmd {
    public DemandeModifPlafondCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");            
        return (ValueObject)demandeCartesService.demandeModifPlafond(vo);
    }
}
