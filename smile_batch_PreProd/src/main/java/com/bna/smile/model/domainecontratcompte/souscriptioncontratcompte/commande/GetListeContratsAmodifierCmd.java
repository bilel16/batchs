package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetListeContratsAmodifierCmd implements ICommande {
    public GetListeContratsAmodifierCmd() {
    }
    
    /**
     * methode execute
     * @param  vo Objet : ParamListContratsAmodifierVo
     * @return vo Objet : ParamListContratsAmodifierVo
     */
    public IValueObject execute(IValueObject vo) {
       Context context = ContextHandler.getContext();
       SouscriptionContratCompteService souscriptionContratCompteService = (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
       return (souscriptionContratCompteService.getListContratsAmodifier(vo));
    
    }
}
