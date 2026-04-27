
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.PersClient;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: InsertPersonneClientCmd.java
 * @version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: InsertPersonneClientCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author : Mdimagh lassaad
 */
public class InsertPersonneClientCmd implements ICommande{
    public InsertPersonneClientCmd() {
    }

    /**
     * Methode Execute
     * @param vo Objet : PersClient
     * @return   Objet : PersClient
     */
    public IValueObject execute(IValueObject vo) {
        PersClient persClient = (PersClient)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        PersClient persClientRetour = 
            (PersClient)souscriptionContratCompteService.insertPersonneClient(persClient);
        return (persClientRetour);
    }
}
