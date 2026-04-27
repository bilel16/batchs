
package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.model.DemandeChequeMandatPersonne;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: InsertDemandeChequeMandatPersonneCmd.java
 * @version 1.0.0 du 05/06/2007
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: InsertDemandeChequeMandatPersonneCmd
 * package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande
 * @author : El Arbi Hassine
 */
public class InsertDemandeChequeMandatPersonneCmd implements ICommande {
    public InsertDemandeChequeMandatPersonneCmd() {
    }

    /**
     * Methode Execute
     * @param vo Objet : DemandeChequeMandatPersonne
     * @return   Objet : DemandeChequeMandatPersonne
     */
    public IValueObject execute(IValueObject vo) {
        DemandeChequeMandatPersonne demandeChequeMandatPersonne = (DemandeChequeMandatPersonne)vo;
        Context context = ContextHandler.getContext();

        DemandeChequesService demandeChequesService = 
        (DemandeChequesService)context.getBean("demandeChequesService");
        
        DemandeChequeMandatPersonne demandeChequeMandatPersonneRetour = 
            (DemandeChequeMandatPersonne)demandeChequesService.insertDemandeChequeMandatPersonne(demandeChequeMandatPersonne);
        return (demandeChequeMandatPersonneRetour);
    }
}
