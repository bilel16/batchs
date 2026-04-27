
package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.model.MandatPersonne;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: UpdatetMandatPersonneCmd.java
 * @version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: UpdateMandatOperationCmd
 * package: com.bna.smile.model.souscriptionContratCompte.commande
 * @author : BOUSSEN Youssef & KRIAA Hatem
 */
public class UpdatetMandatPersonneCmd implements ICommande{
    public UpdatetMandatPersonneCmd() {
    }

    /**
         * Methode execute
         * @param vo Objet : MandatPersonne
         * @return   Objet : MandatPersonne
         */
    public IValueObject execute(IValueObject vo) {
        MandatPersonne mandatPersonne = (MandatPersonne)vo;
        Context context = ContextHandler.getContext();

        ProcurationService procurationService = 
            (ProcurationService)context.getBean("procurationService");
        MandatPersonne mandatPersonneRetour = 
            (MandatPersonne)procurationService.updateMandatPersonne(mandatPersonne);
        return (mandatPersonneRetour);

    }

}
