package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.model.Listes;

import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

public class GetOperationMoyPayByIDCmd {
    public GetOperationMoyPayByIDCmd() {
    }
    /**
     * methode execute permet trouver une OperationsMoyenPay par son identifiant
     * @param vo : PrimitiveVO (N° OperationMoyPay)
     * @return   : OperationMoyPay : OperationsMoyenPay
     */
    public ValueObject execute(ValueObject vo) {
        PrimitiveVO primitiveVO = (PrimitiveVO)vo;
        Context context = ContextHandler.getContext();

        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        OperationMoyPay operationMoyPay = (OperationMoyPay)guichetService.GetOperationMoyPayByID(primitiveVO);
        return (operationMoyPay);
    
    }

}
