package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListesDemandesCheques;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
/**
 * Commande permet de fournir la liste des détails opérations chéquiers
 * par numéro de demande
 * @author El arbi hassine
 * @since 03/07/2007    
 * @version 0.1
 */
public class GetListDetailOperationChequierCmd implements ICommande {
    public GetListDetailOperationChequierCmd() {
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
            (ListesDemandesCheques)demandeChequesService.getListDetailOperationChequier(paramDemandeCheque);
        return (listesDemandesCheques);
    }
}
