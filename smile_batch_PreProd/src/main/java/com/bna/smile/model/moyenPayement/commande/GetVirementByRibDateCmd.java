package com.bna.smile.model.moyenPayement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.moyenPayement.model.ParamVirement;
import com.bna.smile.model.moyenPayement.service.MoyensPayementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

public class GetVirementByRibDateCmd {
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamVirement paramVirement = (ParamVirement)vo;
        MoyensPayementService moyensPayementService = (MoyensPayementService)context.getBean("moyensPayementService");

        paramVirement=(ParamVirement)moyensPayementService.getVirementByRibDateService(vo);
        return (paramVirement);
    }
}
