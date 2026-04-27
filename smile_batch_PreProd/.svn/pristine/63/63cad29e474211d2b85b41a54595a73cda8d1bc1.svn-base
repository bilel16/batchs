package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;


/**
 * Classe permet la prise en charge des versments 
 * @author Mdimagh Lassaad 
 * @since 28/22/2008
 */
public class PecVersementCmd {
    public PecVersementCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
         return (guichetService.pecVersement(vo));
        
    }
}
