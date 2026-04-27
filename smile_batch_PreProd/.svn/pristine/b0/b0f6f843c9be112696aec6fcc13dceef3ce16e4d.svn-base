package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;


import com.bna.commun.model.TraceContrat;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: InsertTraceContratCmd.java
 * @version 1.0.0 du 02/08/2007
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: InsertTraceContratCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author :  El arbi Hassine
 */
 
public class InsertTraceContratCmd implements ICommande {
    public InsertTraceContratCmd() {
    }
    
    
    /**
         * Methode execute
         * @param vo Objet : TraceContrat
         * @return   Objet : TraceContrat
         */
    public IValueObject execute(IValueObject vo) {
        TraceContrat traceContrat = (TraceContrat)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");

        TraceContrat traceContratRetour = (TraceContrat)souscriptionContratCompteService.insertTraceContrat(traceContrat);
        return (traceContratRetour);

    }

}
