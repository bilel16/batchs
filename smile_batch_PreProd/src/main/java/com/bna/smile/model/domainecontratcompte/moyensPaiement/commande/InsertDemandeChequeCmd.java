
package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: InsertDemandeChequeCmd.java
 * @version 1.0.0 du 05/06/2007
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: InsertDemandeChequeCmd
 * package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande
 * @author : El Arbi Hassine
 */
public class InsertDemandeChequeCmd implements ICommande {
    public InsertDemandeChequeCmd() {
    }

    /**
     * Methode Execute
     * @param vo Objet : DemandeCheque
     * @return   Objet : DemandeCheque
     */
    public IValueObject execute(IValueObject vo) {
        
        Context context = ContextHandler.getContext();

        DemandeChequesService demandeChequesService = 
        (DemandeChequesService)context.getBean("demandeChequesService");
        
        ValueObject voo = (ValueObject)demandeChequesService.insertDemandeCheque(vo);
        return (voo);
    }
}
