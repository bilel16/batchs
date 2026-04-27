package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

    /**
     * Rejet suite à une delivrance:carte mal confectionnée ou contrat non valide.
     * @author Ramzi
     * @param CarteBancaire 
     * @return CarteBancaire
     * @since 26/07/2007
     * 
     */
public class RejetDelivCarteCmd {
    public RejetDelivCarteCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");            
        return (ValueObject)demandeCartesService.rejetDelivCarte(vo);
    }
}
