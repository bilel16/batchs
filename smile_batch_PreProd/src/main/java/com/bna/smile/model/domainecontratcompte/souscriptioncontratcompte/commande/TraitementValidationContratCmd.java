package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Commande TraitementValidationContratCmd : elle traite les différentes transactions
 * de validation d'un contrat  
 *  @author el arbi hassine
 *  @since 16/04/2007
 *  @version 1.0
 */
public class TraitementValidationContratCmd implements ICommande {
    public TraitementValidationContratCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
        
        Context context = ContextHandler.getContext();
        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        ValueObject voo = (ValueObject)souscriptionContratCompteService.traitementValidationContrat(vo);
        return (voo);
    }
}
