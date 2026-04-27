
package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.model.MandatOperation;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: UpdateMandatOperationCmd.java
  * @version 1.0.0 du 19/01/2006
  * Copyright(c) 2006 BNA (www.bna.com.tn)
  * Classe: UpdateMandatOperationCmd
  * package: com.bna.smile.model.souscriptionContratCompte.commande
  * @author : Mdimagh Med Lassaad
  * Commande pour la mise à jour d'une opération mandat.
  */
public class UpdateMandatOperationCmd implements ICommande {
    public UpdateMandatOperationCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        MandatOperation mandatOperation = (MandatOperation)vo;
        Context context = ContextHandler.getContext();

        ProcurationService procurationService = 
            (ProcurationService)context.getBean("procurationService");
        MandatOperation mandatOperationRetour = 
            (MandatOperation)procurationService.updateMandatOperation(mandatOperation);
        return (mandatOperationRetour);
    }
}
