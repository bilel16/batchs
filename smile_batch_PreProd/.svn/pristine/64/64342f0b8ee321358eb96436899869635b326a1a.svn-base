package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.ClientService;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamCompteLie;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * cette Classe permet de retourner la liste des personnes qui sont en relationa avec 
 * un client pour une qualité donnée
 * @author Mdimagh Lassaad
 * @since 27/6/07
 */
public class GetPersonneClientQualiteCmd implements ICommande {
    public GetPersonneClientQualiteCmd() {
    }
    
    /**
     * methode execute
     * @param  ParamListQualiteClientVo
     * @return ParamListQualiteClientVo
     */
     public IValueObject execute(IValueObject vo) {
       
         Context context = ContextHandler.getContext();
         ClientService clientService = (ClientService)context.getBean("clientService");
         return (clientService.getPersonneClientQualite(vo));
     }

}
