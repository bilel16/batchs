package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.Paramchequiers;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetChequiersCmd implements ICommande{
    public GetChequiersCmd() {
    }
    /**
         * methode execute 
         * @param value Object :  Paramchequiers
         * @return value Object : ListesChequiers
         */
        public IValueObject execute(IValueObject vo) {
            Context context = ContextHandler.getContext();
            Paramchequiers paramchequiers = (Paramchequiers)vo;
            DemandeChequesService demandeChequesService = 
            (DemandeChequesService)context.getBean("demandeChequesService");
            Listes listesChequiers  = 
                (Listes)demandeChequesService.getChequiers(paramchequiers);
            return (listesChequiers);
        }
}
