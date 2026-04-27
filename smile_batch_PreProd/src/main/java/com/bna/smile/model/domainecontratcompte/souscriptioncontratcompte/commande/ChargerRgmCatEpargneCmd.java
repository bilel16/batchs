package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ListRgmCatEpargne;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamEpargne;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: ChargerRgmCatEpargneCmd.java
 * @version 1.0.0 du 07/03/2007
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: ChargerRgmCatEpargneCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;
 * @author : El arbi hassine
 */
public class ChargerRgmCatEpargneCmd implements ICommande{

    public ChargerRgmCatEpargneCmd() {
    }

    /**
     * methode execute
     * @param  vo Objet : ParamEpargne
     * @return vo Objet : ListRgmCatEpargne
     */
    public IValueObject execute(IValueObject vo) {
        ParamEpargne paramEpargne = (ParamEpargne)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        ListRgmCatEpargne listRgmCatEpargne = 
            (ListRgmCatEpargne)souscriptionContratCompteService.chargerRgmCatEpargne(paramEpargne);
        return (listRgmCatEpargne);
    }
}

