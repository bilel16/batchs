
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ListTypeCatTpce;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.TypeCatPers;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: ChargerTypeCatPcePersonneCmd.java
 * @version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: ChargerTypeCatPcePersonneCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author : Boussen Youssef & Kriaa Hatem
 */
public class ChargerTypeCatPcePersonneCmd implements ICommande{

    public ChargerTypeCatPcePersonneCmd() {
    }

    /**
     * methode execute
     * @param  vo Objet : TypeCatPers
     * @return vo Objet : ListTypeCatTpce
     */
    public IValueObject execute(IValueObject vo) {
        TypeCatPers typeCatPers = (TypeCatPers)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        ListTypeCatTpce listTypeCatTpce = 
            (ListTypeCatTpce)souscriptionContratCompteService.chargerTypeCatPcePersonne(typeCatPers);
        return (listTypeCatTpce);
    }
}
