
package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.domainecommun.service.ClientService;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamCompteLie;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;



public class GetNombreContratParClientCmd implements ICommande{

    public GetNombreContratParClientCmd() {
    }

    /**
     * Methode execute
     * @param vo Objet : ParamCompteLie : Code produit  + identiinat du client à travers le contrat
     * @return   Objet : Integer
     */
    public IValueObject execute(IValueObject vo) {
        ParamCompteLie paramCompteLie = (ParamCompteLie)vo;
        Context context = ContextHandler.getContext();
        ClientService clientService = (ClientService)context.getBean("clientService");
        paramCompteLie = (ParamCompteLie)clientService.getNombreContratParClient(paramCompteLie);
        return (paramCompteLie);
    }

}
