package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe qui permet de rechercher une mise à disposition à partir de la clé primaire.
 * @author Mdimagh Med Lassaad
 * @since 23/11/2007
 */
public class GetMiseAdispositionByPrimaryKeyCmd {
    public GetMiseAdispositionByPrimaryKeyCmd() {
    }
    
    /**
     * methode execute
     * @param  vo Objet : MontantMiseDiposition
     * @return vo Objet :   MontantMiseDiposition
     */
    public ValueObject execute(ValueObject vo) {
      
        Context context = ContextHandler.getContext();

        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)guichetService.getMiseAdispositionByPrimaryKey(vo);
        return (montantMiseDiposition);
    
    }
}
