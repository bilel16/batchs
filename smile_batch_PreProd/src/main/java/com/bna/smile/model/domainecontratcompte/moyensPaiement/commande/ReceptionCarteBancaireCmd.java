package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

   /**
    * Reception une demande de carte.
    * @author Ramzi
    * @param DemandeCarte 
    * @return DemandeCarte
    * @since 21/06/2007
    * 
    */
public class ReceptionCarteBancaireCmd {
    public ReceptionCarteBancaireCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");    
        return (ValueObject)demandeCartesService.receptionCarteBancaire(vo);
    }
}
