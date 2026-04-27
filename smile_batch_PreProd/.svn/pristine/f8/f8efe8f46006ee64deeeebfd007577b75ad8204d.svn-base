package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


/**
 * Méhode de validition d'un contrat en attente
 * @param contratAttente
 * @return contratValide :nouveau numéro du contrat validé
 */
public class ValiderContratCmd implements ICommande  {
    public ValiderContratCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
       return ((ValueObject)souscriptionContratCompteService.validerContrat(vo));

    }
}
