package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

    /**
     * Mettre en attente une demande de carte donnée.
     * @author Ramzi
     * @param DemandeCarte 
     * @return DemandeCarte
     * @since 21/06/2007
     * 
     */
public class MiseAttenteDemandeCarteCmd {
    public MiseAttenteDemandeCarteCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");       
        return (ValueObject)demandeCartesService.miseAttenteDemandeCarte(vo);
    }
}
