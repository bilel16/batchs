
package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.model.MandatPersonne;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: InsertMandatPersonneCmd.java
 * @version 1.0.0 du 22/02/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: InsertMandatPersonneCmd
 * package com.bna.smile.model.domainecontratcompte.procuration.commande
 * @author :  BOUSSEN Youssef & KRIAA Hatem
 */
public class InsertMandatPersonneCmd implements ICommande{
    public InsertMandatPersonneCmd() {
    }

    /**
         * Methode execute
         * @param vo Objet : MandatPersonne
         * @return   Objet : MandatPersonne
         */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        MandatPersonne mandatPersonne = (MandatPersonne)vo;

        ProcurationService procurationService = 
            (ProcurationService)context.getBean("procurationService");
        MandatPersonne mandatPersonneRetour = 
            (MandatPersonne)procurationService.InsertMandatPersonne(mandatPersonne);
        return (mandatPersonneRetour);

    }

}
