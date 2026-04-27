package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ContratABloquer;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class DebloquerContratCptCmd implements ICommande {
    public DebloquerContratCptCmd() {
    }
    /**
     * methode execute
     * @param  vo Objet : ContratABloquer
     * @return vo Objet : ContratCpt
     */
    public IValueObject execute(IValueObject vo) {
        ContratABloquer contratABloquer = (ContratABloquer)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        ContratCpt contratCpt = 
            (ContratCpt)souscriptionContratCompteService.debloquerContratCpt(contratABloquer);
        return (contratCpt);
    
    }
}
