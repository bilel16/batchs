package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineguichet.model.ListMiseAdispositionVo;
import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * Classe permet d'ajouter un versment de mise à disposition
 * @author Mdimagh Lassaad 
 * @since 03/12/2007
 */
public class AjoutVersementMisAdispositionCmd {
    public AjoutVersementMisAdispositionCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();

        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)guichetService.ajoutVersementMisAdisposition(vo);
        return (montantMiseDiposition);
    
    }    
}
