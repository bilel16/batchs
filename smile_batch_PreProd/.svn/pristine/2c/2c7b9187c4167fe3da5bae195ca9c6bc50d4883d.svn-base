package com.bna.smile.model.moyenPayement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.moyenPayement.model.ParamPrelevement;
import com.bna.smile.model.moyenPayement.service.MoyensPayementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

public class GetPrelevementByStructureDateCmd {
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamPrelevement paramPrelevement = (ParamPrelevement)vo;
        MoyensPayementService moyensPayementService = (MoyensPayementService)context.getBean("moyensPayementService");
        paramPrelevement=(ParamPrelevement)moyensPayementService.getPrelevementByStructureDateService(vo);
        return (paramPrelevement);
    }
}
