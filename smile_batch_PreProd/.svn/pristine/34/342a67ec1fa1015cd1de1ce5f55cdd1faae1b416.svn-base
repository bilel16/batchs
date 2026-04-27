package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.MontantBlocage;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class DebloquerMontantCmd implements ICommande {
    public DebloquerMontantCmd() {
    }
    /**
     * methode execute
     * @param  vo Objet : MontantBlocage
     * @return vo Objet : ContratCpt
     */
    public IValueObject execute(IValueObject vo) {
        MontantBlocage montantBlocage = (MontantBlocage)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        ContratCpt contratCpt = 
            (ContratCpt)souscriptionContratCompteService.debloquerMnt(montantBlocage);
        return (contratCpt);
    
    }
}
