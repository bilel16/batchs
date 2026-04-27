package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.model.ContratCpt;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;


import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

public class GetProvisionCmd {
    public GetProvisionCmd() {
    }
    
    /**
     * methode execute
     * @param  vo Objet : ContratCpt
     * @return vo Objet : PrimitiveVO
     */
    public ValueObject execute(ValueObject vo) {
        ContratCpt contratCpt = (ContratCpt)vo;
        Context context = ContextHandler.getContext();

        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        PrimitiveVO primitiveVO = (PrimitiveVO)guichetService.getProvision(contratCpt);
        return (primitiveVO);
    
    }
}
