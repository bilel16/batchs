package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

  /**
   * modification  plafond carte .
   * @author Ramzi
   * @return CarteBancaire
   * @since 08/04/2009
   * 
   */
public class ModifPlafondCmd {
    public ModifPlafondCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");            
        return (ValueObject)demandeCartesService.modifPlafond(vo);
    }
}
