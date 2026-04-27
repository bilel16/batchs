package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ContratACloturer;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class CloturerContratCmd implements ICommande {
    public CloturerContratCmd() {
    }
    /**
     * methode execute
     * @param  vo Objet : ContratACloturer
     * @return vo Objet : ContratCpt
     */
    public IValueObject execute(IValueObject vo) {
        ContratACloturer contratACloturer = (ContratACloturer)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        ContratCpt contratCpt = 
            (ContratCpt)souscriptionContratCompteService.cloturerContrat(contratACloturer);
        return (contratCpt);
    
    }
}
