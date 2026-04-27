package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

    /**
     * Prise en charge décision DR/DQMRP
     * @author Ramzi
     * @param DemandeCarte 
     * @return DemandeCarte
     * @since 20/07/2007
     * 
     */
public class PecDrDqmrpCmd {
    public PecDrDqmrpCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");
        return (ValueObject)demandeCartesService.pecDrDqmrp(vo);
    }
}
