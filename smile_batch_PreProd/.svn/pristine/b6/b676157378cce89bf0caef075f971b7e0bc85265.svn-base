package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineguichet.service.GuichetService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;


/**
 * Classe validation d'une mise à disposition
 * @author Mdimagh Med Lassaad
 * @since 26/11/2007
 */
public class ValidationMiseAdispositionCmd {
    Context context = ContextHandler.getContext();
    public ValidationMiseAdispositionCmd() {
    }
    
    /**
     * methode execute permet de valider une mise à disposition
     * @param vo   : MontantMiseDiposition
     * @return        : MontantMiseDiposition
     */
    public IValueObject execute(IValueObject vo) {
               
        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)guichetService.validationMiseAdisposition(vo);
        return (montantMiseDiposition);
    
    }

}
