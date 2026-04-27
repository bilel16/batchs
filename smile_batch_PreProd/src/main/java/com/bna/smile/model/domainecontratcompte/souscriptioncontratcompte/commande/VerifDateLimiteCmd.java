package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class VerifDateLimiteCmd implements ICommande {
    public VerifDateLimiteCmd() {
    }


    /**
     * methode execute
     * @param  vo Objet : ContratCpt
     * @return vo Objet : PrimitiveVO
     */
    public IValueObject execute(IValueObject vo) {
        ContratCpt contratCpt = (ContratCpt)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        PrimitiveVO primitiveVO = (PrimitiveVO)souscriptionContratCompteService.VerifDateLimite(contratCpt);
        return (primitiveVO);
    
    }

}


