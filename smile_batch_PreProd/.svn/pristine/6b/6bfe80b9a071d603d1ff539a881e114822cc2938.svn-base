package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ValidationVersementMemeAgenceCmd {
    public ValidationVersementMemeAgenceCmd() {
    }
    
    /**
     * methode execute
     * @param  vo Objet : OperationMoyPay
     * @return vo Objet : OperationMoyPay
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        return (guichetService.validerVersementMemeAgence(vo) );
        
    }
}
