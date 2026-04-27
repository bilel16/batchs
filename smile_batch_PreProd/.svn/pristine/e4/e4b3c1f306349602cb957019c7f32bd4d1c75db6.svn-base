package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListesDemandesCheques;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
/**
 *       /**
       * Methode permettant d'avoir les listes de demandes cheques d'un contrat donné
       * effectué par un demandeur donné sur un mandat donnée.
       * @return ListesDemandesCheques       
       * @author El arbi hassine
       * @since 15/06/2007    
       * @version 0.1
       */
public class GetListDemandesChequesMandatPersonneCmd implements ICommande {
    public GetListDemandesChequesMandatPersonneCmd() {
    }
    
/**
     * methode execute 
     * @param value Object :  ParamDemandeChequeVo
     * @return value Object : ListesDemandesCheques
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamDemandeCheque paramDemandeCheque = (ParamDemandeCheque)vo;
        DemandeChequesService demandeChequesService = 
        (DemandeChequesService)context.getBean("demandeChequesService");
        ListesDemandesCheques listesDemandesCheques = 
            (ListesDemandesCheques)demandeChequesService.getListDemandesChequesMandatPersonne(paramDemandeCheque);
        return (listesDemandesCheques);
    }
}
