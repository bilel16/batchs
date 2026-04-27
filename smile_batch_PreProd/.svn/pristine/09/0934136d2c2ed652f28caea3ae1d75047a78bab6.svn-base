package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetListValidationVersementCmd {
    public GetListValidationVersementCmd() {
    }
    
    /**
     * methode execute
     * @param  vo Objet : ListVersementVo
     * @return vo Objet : ListVersementVo
     */
    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        return (guichetService.getListVersment(vo));
        
    }
}
