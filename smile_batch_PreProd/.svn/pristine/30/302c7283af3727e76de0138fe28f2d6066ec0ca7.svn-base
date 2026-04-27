package com.bna.smile.model.moyenPayement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.moyenPayement.model.ParamAccuse;
import com.bna.smile.model.moyenPayement.service.MoyensPayementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

public class GetAccuseByStructureDateCmd {
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamAccuse paramAccuse = (ParamAccuse)vo;
        MoyensPayementService moyensPayementService = (MoyensPayementService)context.getBean("moyensPayementService");
        paramAccuse=(ParamAccuse)moyensPayementService.getAccuseByStructureDateService(vo);
        return (paramAccuse);
    }
}
