package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.MandatOperation;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.service.OperationService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetDateDerniereOperationCmd implements ICommande {
    public GetDateDerniereOperationCmd() {
    }
    /**
     * Methode execute
     * @param vo Objet : MandatOperation
     * @return   Objet : PrimitiveVO
     */
    public IValueObject execute(IValueObject vo) {
        MandatOperation mandatOperation = (MandatOperation)vo;
        Context context = ContextHandler.getContext();

        OperationService operationService = (OperationService)context.getBean("operationService");
        PrimitiveVO primitiveVo = (PrimitiveVO)operationService.GetDateDerniereOperation(mandatOperation);
        return (primitiveVo);
    }
}
