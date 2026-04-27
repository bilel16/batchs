
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: InsertClientContratCmd.java
 * @version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: InsertClientContratCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author : Boussen Youssef & Kriaa Hatem
 */
public class InsertClientContratCmd implements ICommande {

    public InsertClientContratCmd() {
    }

    /**
     * Methode execute
     * @param vo Objet :    ContratCpt
     * @return   Objet :    ContratCpt
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
      
        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        ValueObject voo = 
            (ValueObject)souscriptionContratCompteService.insertClientContrat(vo);
        return (voo);
    }

}
