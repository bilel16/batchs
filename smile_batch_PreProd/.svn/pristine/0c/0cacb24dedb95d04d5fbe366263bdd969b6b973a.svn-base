package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

 /**
  * Extraire la liste des types de carte qui sont éligible pour un contrat donné. 
  * @author Ramzi
  * @param ContratCpt
  * @return Listes : de TypeCarte
  * @since 28/06/2007
  * 
  */
public class GetCartesEligibleContratCmd {
    public GetCartesEligibleContratCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");
        return  (ValueObject)demandeCartesService.getCartesEligibleContrat(vo);
    }
}
