
package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.model.MandatOperation;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: InsertMandatOperation.java
  * @version 1.0.0 du 19/01/2006
  * Copyright(c) 2006 BNA (www.bna.com.tn)
  * Classe: InsertMandatOperation
  * package: com.bna.smile.model.souscriptionContratCompte.commande
  * @author : Mdimagh Med Lassaad
  * Commande d'insertion d'une nouvelle Opération Mandat
  */
public class InsertMandatOperationCmd implements ICommande{
    public InsertMandatOperationCmd() {
    }

    /**
     * Methode eexecute
     * @param vo Objet : MandatOperation
     * @return   Objet : AjoutMandatOperation
     */
    public IValueObject execute(IValueObject vo) {
        MandatOperation mandatOperation = (MandatOperation)vo;
        Context context = ContextHandler.getContext();

        ProcurationService procurationService = 
            (ProcurationService)context.getBean("procurationService");
        MandatOperation mandatOperationRetour = 
            (MandatOperation)procurationService.insertMandatOperation(mandatOperation);
        return (mandatOperationRetour);
    }
}
