package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.EtatContrat;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class ChargerMotifEtatCmd implements ICommande {
    public ChargerMotifEtatCmd() {
    }
    /**
     * methode execute
     * @param  vo Objet : EtatContrat
     * @return vo Objet : ListMotifEtat
     */
    public IValueObject execute(IValueObject vo) {
        EtatContrat etatContrat = (EtatContrat)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        Listes listes = 
            (Listes)souscriptionContratCompteService.chargerMotifEtat(etatContrat);
        return (listes);
    }
}
