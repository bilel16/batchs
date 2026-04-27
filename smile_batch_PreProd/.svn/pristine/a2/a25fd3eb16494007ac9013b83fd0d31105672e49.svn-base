package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.BlocageCriteres;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class ChargerBlocagesCmd implements ICommande{
    public ChargerBlocagesCmd() {
    }
    /**
     * methode execute
     * @param  vo Objet : BlocageCriteres
     * @return vo Objet : Listblocages
     */
    public IValueObject execute(IValueObject vo) {
        BlocageCriteres blocageCriteres = (BlocageCriteres)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        Listes listes = 
            (Listes)souscriptionContratCompteService.chargerBlocages(blocageCriteres);
        return (listes);
    }
}
