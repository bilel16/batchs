package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

public class InsertMontantMADCmd {
    public InsertMontantMADCmd() {
    }
    
    /**
     * methode execute
     * @param  vo Objet : MontantMiseDiposition
     * @return vo Objet : MontantMiseDiposition
     */
    public ValueObject execute(ValueObject vo) {
        MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)vo;
        Context context = ContextHandler.getContext();

        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        montantMiseDiposition = (MontantMiseDiposition)guichetService.InsertMontantMAD(montantMiseDiposition);
        return (montantMiseDiposition);
    
    }
    
    
}
