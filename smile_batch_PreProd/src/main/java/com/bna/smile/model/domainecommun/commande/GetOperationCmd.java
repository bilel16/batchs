package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.Operation;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.NomenclatureService;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: GetOperationCmd.java version 1.0.0 du 17/05/2007
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetOperationCmd
 * package: com.bna.smile.model.dommainecommun.commande
 * Auteur : Lassaad Mdimagh
 */
public class GetOperationCmd implements ICommande{
    public GetOperationCmd() {

    }


    public IValueObject execute(IValueObject vo) {
        Operation operation = (Operation)vo;        
        Context context = ContextHandler.getContext();
        NomenclatureService nomenclatureService = (NomenclatureService)context.getBean("nomenclatureService");

        operation = (Operation)nomenclatureService.getOperation(operation);
        return (operation);
    }

}
