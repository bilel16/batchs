package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.model.OppositionMoyenPaiementId;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;

import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

public class VerifOppositionMoyPayCmd {
    public VerifOppositionMoyPayCmd() {
    }
    
    /**
     * methode execute 
     * @param  vo Objet : OppositionMoyenPaiementId
     * @return vo Objet : PrimitiveVO
     */
    public ValueObject execute(ValueObject vo) {
        OppositionMoyenPaiementId oppositionMoyenPaiementId = (OppositionMoyenPaiementId)vo;
        Context context = ContextHandler.getContext();

        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        PrimitiveVO primitiveVO = (PrimitiveVO)guichetService.verifOppositionMoyPay(oppositionMoyenPaiementId);
        return (primitiveVO);
    
    }

}
