

/** Fichier: GetDetailCategorieContratCmd.java version 1.0.0 du 10/05/2007
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: GetDetailCategorieContratCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * Auteur : El Arbi hassine
 */
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.ContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Commande GetDetailCategorieContratCmd : 
 *  permet de determiner la catégorie d'un contrat donnée (compte epargne)  
 *  @author El Arbi hassine
 *  @since 10/05/2007
 *  @version 1.0
 */
public class GetDetailCategorieContratCmd implements ICommande{
    public GetDetailCategorieContratCmd() {
    }

    /** methode execute
     * @param  Objet ContratCptId  :Identifiant du contrat;
     * @return Objet DetailCatCpt;
     **/
    public IValueObject execute(IValueObject o) {

        ContratCptId contratCptId = (ContratCptId)o;
        Context context = ContextHandler.getContext();

        ContratCompteService contratCompteService = 
            (ContratCompteService)context.getBean("contratCompteService");
        DetailCatCpt detailCatCpt = 
            (DetailCatCpt)contratCompteService.GetCategorieContrat(contratCptId);
        return (detailCatCpt);
    }
}

