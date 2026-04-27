package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

 /**
  * Vérifier s’il existe une carte de type donnée valide pour un porteur donné sur un contrat donné.
  * @author Ramzi
  * @param PersonneTypeCarteCpt
  * @return CarteBancaire
  * @since 21/06/2007
  * 
  */
public class VerifPossedeTypeCarteCmd {
    public VerifPossedeTypeCarteCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");        
        return (ValueObject)demandeCartesService.verifPossedeTypeCarte(vo);
    }
}
