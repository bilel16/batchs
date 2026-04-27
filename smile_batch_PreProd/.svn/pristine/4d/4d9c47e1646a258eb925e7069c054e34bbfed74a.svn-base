package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

    /**
     * Prise en charge décision SCM
     * @author Ramzi
     * @param DemandeCarte 
     * @return DemandeCarte
     * @since 26/03/2009
     * 
     */
public class PecScmCmd {
    public PecScmCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");
        return (ValueObject)demandeCartesService.pecScm(vo);
    }
}
