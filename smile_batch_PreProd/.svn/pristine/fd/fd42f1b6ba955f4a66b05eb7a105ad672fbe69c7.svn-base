package com.bna.smile.model.domaineguichet.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineguichet.model.ListMiseAdispositionVo;
import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * Calsse qui permet d'extraction des montants Mises à disposition
 * @author Mdimagh Med Lassaad 
 * @since 23/11/2007
 */
public class GetListMontantMADCmd {
    public GetListMontantMADCmd() {
    }
    
    /**
     * methode execute 
     * @param  vo Objet : ListMiseAdispositionVo
     * @return vo Objet :   ListMiseAdispositionVo
     * @author Mdimagh Med Lassaad 
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();

        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        ListMiseAdispositionVo listMiseAdispositionVo = (ListMiseAdispositionVo)guichetService.getListMontantMAD(vo);
        return (listMiseAdispositionVo);
    
    }    
    
}
