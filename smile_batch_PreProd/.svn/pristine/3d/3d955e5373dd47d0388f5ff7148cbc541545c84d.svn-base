package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamDetailCatCpt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class CalculSoldTheorEpargneCmd implements ICommande {
    public CalculSoldTheorEpargneCmd() {
    }
    
    /**
     * methode execute
     * @param  vo Objet : ParamDetailCatCpt
     * @return vo Objet : PrimitiveVO
     */
    public IValueObject execute(IValueObject vo) {
        ParamDetailCatCpt paramDetailCatCpt = (ParamDetailCatCpt)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        PrimitiveVO primitiveVO = (PrimitiveVO)souscriptionContratCompteService.CalculSoldTheorEpargneTrt(paramDetailCatCpt);
        return (primitiveVO);
    
    }
}
