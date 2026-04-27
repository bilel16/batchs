package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
/**
 * classe pemetl'ajout dans la table OperationEpargne
 * @author Mdimagh Lassaad
 */
public class AjoutOperationEpargneCmd {
    public AjoutOperationEpargneCmd() {
    }
    /**
     * 
     * @param  vo OperationEpargnes
     * @return vo OperationEpargnes
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();

        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)guichetService.ajoutOperationEpargne(vo);
        return (montantMiseDiposition);
    
    }    
}
