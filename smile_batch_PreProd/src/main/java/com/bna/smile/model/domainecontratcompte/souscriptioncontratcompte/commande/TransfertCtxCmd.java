package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ContratCptACtx;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class TransfertCtxCmd implements ICommande {
    public TransfertCtxCmd() {
    }
    /**
     * methode execute
     * @param  vo Objet : ContratCpt
     * @return vo Objet : ContratCpt
     */
    public IValueObject execute(IValueObject vo) {
        ContratCptACtx contratCptACtx = (ContratCptACtx)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        ContratCpt contratCpt = 
            (ContratCpt)souscriptionContratCompteService.transfertCtx(contratCptACtx);
            
        return (contratCpt);
    
    }
}
