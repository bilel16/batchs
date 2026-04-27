
package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.OperationCompte;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.OperationService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: InsertOperationCompteCmd.java
 * @version 1.0.0 du 26/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: InsertOperationCompteCmd
 * package com.bna.smile.model.domainecommun.commande
 * @author : Boussen Youssef & Kriaa Hatem
 */
public class InsertOperationCompteCmd implements ICommande {
    public InsertOperationCompteCmd() {
    }

    /**
     * Methode execute
     * @param vo Objet : OperationCompte
     * @return   Objet :
     */
    public IValueObject execute(IValueObject vo) {
        OperationCompte operationCompte = (OperationCompte)vo;
        Context context = ContextHandler.getContext();

        OperationService operationService = 
            (OperationService)context.getBean("operationService");
        OperationCompte operationCompteRetour = 
            (OperationCompte)operationService.InsertOperationCompte(operationCompte);
        return (operationCompteRetour);
    }

}
