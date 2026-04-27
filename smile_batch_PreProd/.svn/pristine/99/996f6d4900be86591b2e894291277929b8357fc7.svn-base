
package com.bna.smile.model.domainecontratcompte.procuration.commande;

/** Fichier: InsertMandatCmd.java
 * @version 1.0.0 du 20/02/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: InsertMandatCmd
 * package com.bna.smile.model.domainecontratcompte.procuration.commande
 * @author :  BOUSSEN Youssef & KRIAA Hatem
 */

import com.bna.commun.model.Mandat;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: InsertMandatCmd.java
 * @version 1.0.0 du 20/02/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: InsertMandatCmd
 * package com.bna.smile.model.domainecontratcompte.procuration.commande
 * @author :  BOUSSEN Youssef & KRIAA Hatem
 */
public class

InsertMandatCmd implements ICommande{
    public InsertMandatCmd() {
    }

    /**
         * Methode execute
         * @param vo Objet : Mandat
         * @return   Objet : Mandat
         */
    public IValueObject execute(IValueObject vo) {
        Mandat mandat = (Mandat)vo;
        Context context = ContextHandler.getContext();

        ProcurationService procurationService = 
            (ProcurationService)context.getBean("procurationService");
        Mandat mandatRetour = (Mandat)procurationService.insertMandat(mandat);
        return (mandatRetour);

    }

}
