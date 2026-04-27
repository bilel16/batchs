package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

  /**
   * permet de donner la liste de toutes les demandes de cartes selon critères de recherches
   * @author Ramzi
   * @since 05/07/2007
   * 
   */
public class GetListDemandesCartesCmd {
    public GetListDemandesCartesCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");         
        return (ValueObject)demandeCartesService.getListDemandesCartes(vo);
    }
}
