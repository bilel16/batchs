
 package com.bna.smile.model.domainecontratcompte.procuration.commande;

/** Fichier: AnnulMandatCmd.java 
 * @version 1.0.0 du 20/02/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: AnnulMandatCmd
 * package com.bna.smile.model.domainecontratcompte.procuration.commande
 * @author :  BOUSSEN Youssef & KRIAA Hatem
 */

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class AnnulMandatCmd  implements ICommande{
    public AnnulMandatCmd() {
    }
    
    /**
         * Methode execute
         * @param vo Objet : Mandat
         * @return   Objet : Mandat
         */
        public IValueObject execute(IValueObject vo){
           Context context = ContextHandler.getContext();
            
           ProcurationService procurationService = (ProcurationService)context.getBean("procurationService");
            ValueObject vo2 = (ValueObject)procurationService.annulMandat(vo);
           return(vo2);
            
        }

}
