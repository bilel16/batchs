package com.bna.smile.model.domainecaisse.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.service.CaisseService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

public class UpdateSessionJrnCaisseCmd {
    public UpdateSessionJrnCaisseCmd() {
    }
    
    /**
     * Classe qui permet de maj SessionJrnCaisse
     * @author JERBI Lamia
     * @since 01/04/2011
     */

    public   IValueObject execute(IValueObject vo) {
       
        Context context = ContextHandler.getContext();
        CaisseService caisseService = (CaisseService)context.getBean("caisseService");
        return (caisseService.updateSessionJrnCaisse(vo));
    }    

}
