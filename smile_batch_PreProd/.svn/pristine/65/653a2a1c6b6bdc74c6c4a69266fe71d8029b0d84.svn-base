
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: InsertCompteLieCmd.java
 * @version 1.0.0 du 21/02/2008
 * Copyright(c) 2008 BNA (www.bna.com.tn)
 * Classe: InsertComptePersonnelBnaCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author :  El arbi hassine 
 */
public class InsertComptePersonnelBnaCmd implements ICommande {

    public InsertComptePersonnelBnaCmd() {
    }

    /**
     * Methode execute
     * @param vo Objet :    ParamInsertContrat
     * @return   Objet :    ContratCpt
     */
    public IValueObject execute(IValueObject vo) {
        
        Context context = ContextHandler.getContext();
        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        ValueObject voo = 
            (ValueObject)souscriptionContratCompteService.insertComptePersonnelBna(vo);
        return (voo);
    }

}
