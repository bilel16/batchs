package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.model.Listes;

import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

public class GetMontantMADByIdCmd {
    public GetMontantMADByIdCmd() {
    }
    /**
     * methode execute
     * @param  vo Objet : PrimitiveVO
     * @return vo Objet : MontantMiseDiposition
     */
    public ValueObject execute(ValueObject vo) {
        PrimitiveVO primitiveVO = (PrimitiveVO)vo;
        Context context = ContextHandler.getContext();

        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)guichetService.GetMontantMADById(primitiveVO);
        return (montantMiseDiposition);
    
    }
    
}
