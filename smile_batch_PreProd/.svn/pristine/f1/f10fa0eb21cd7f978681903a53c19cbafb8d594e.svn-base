package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.ContratCptId;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.ContratCompteService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetListCreditCmd implements ICommande {
    public GetListCreditCmd() {
    }
    
    
    public IValueObject execute(IValueObject vo) {
        ContratCptId contratCptId = (ContratCptId)vo;
        Context context = ContextHandler.getContext();

        ContratCompteService contratCompteService = 
            (ContratCompteService)context.getBean("contratCompteService");
         Listes listeCred = 
            (Listes)contratCompteService.getListCredit(contratCptId);
        return (listeCred);
    }
}
