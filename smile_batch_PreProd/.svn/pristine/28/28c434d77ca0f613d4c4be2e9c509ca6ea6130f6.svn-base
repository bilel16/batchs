package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.model.Mandat;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamInsertMandat;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: CreationMandatOperation.java
  * @version 1.0.0 du 19/02/2006
  * Copyright(c) 2006 BNA (www.bna.com.tn)
  * Classe: CreationMandatOperation
  * package: com.bna.smile.model.souscriptionContratCompte.commande
  * @author : Boussen youssef & Mdimagh Med Lassaad
  * Commande de création d'un nouveau  Mandat
  */
public class CreationMandatCmd implements ICommande{
    Context context = ContextHandler.getContext();

    public CreationMandatCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        ParamInsertMandat paramInsertMandat = (ParamInsertMandat)vo;
        ProcurationService procurationService = 
            (ProcurationService)context.getBean("procurationService");
        Mandat mandatRetour = 
            (Mandat)procurationService.CreationMandat(paramInsertMandat);
        return (mandatRetour);
    }
}
