package com.bna.smile.model.domainecontratcompte.procuration.commande;


import com.bna.commun.model.TraceMandat;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: InsertTraceMandatCmd.java
 * @version 1.0.0 du 20/07/2007
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: InsertTraceMandatCmd
 * package com.bna.smile.model.domainecontratcompte.procuration.commande
 * @author :  BOUSSEN Youssef & KRIAA Hatem
 */
 
public class InsertTraceMandatCmd implements ICommande{
    public InsertTraceMandatCmd() {
    }
     
    
    /**
         * Methode execute
         * @param vo Objet : TraceMandat
         * @return   Objet : TraceMandat
         */
    public IValueObject execute(IValueObject vo) {
        TraceMandat traceMandat = (TraceMandat)vo;
        Context context = ContextHandler.getContext();

        ProcurationService procurationService = 
            (ProcurationService)context.getBean("procurationService");
        TraceMandat traceMandatRetour = (TraceMandat)procurationService.insertTraceMandat(traceMandat);
        return (traceMandatRetour);

    }

}
