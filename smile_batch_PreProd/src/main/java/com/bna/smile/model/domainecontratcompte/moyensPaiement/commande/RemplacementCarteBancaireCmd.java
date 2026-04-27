package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

    /**
     * Remplacement une carte par une autre suite à une demande.
     * @author Ramzi
     * @param CarteBancaire 
     * @return CarteBancaire
     * @since 26/07/2007
     * 
     */
public class RemplacementCarteBancaireCmd {
    public RemplacementCarteBancaireCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");    
            return null;
    //    return (ValueObject)demandeCartesService.remplacementCarteBancaire(vo);
    }
}
