package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

/**
 * permettant de Vérifier si un type de carte est éligible sur un contrat donné. 
 * @author Ramzi
 * @param TypeCarteCpt
 * @return PrimitiveVO
 * @since 15/06/2007
 * 
 */
public class VerifEligibiliteCarteCmd {
    public VerifEligibiliteCarteCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");      
        return (ValueObject)demandeCartesService.verifEligibiliteCarte(vo);
    }
}
