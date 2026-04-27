package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.DetailDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
/**
 * Commande permet de retourner l'objet DemandeCheque  
 * @author El arbi hassine
 * @since 11/06/2007    
 * @version 0.1
 */
public class GetDetailDemandeChequeCmd implements ICommande{
    public GetDetailDemandeChequeCmd() {
    }
    
/**
     * methode execute 
     * @param value Object :  ParamDemandeChequeVo
     * @return value Object : DemandesCheque
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamDemandeCheque paramDemandeCheque = (ParamDemandeCheque)vo;
        DemandeChequesService demandeChequesService = 
        (DemandeChequesService)context.getBean("demandeChequesService");
        DetailDemandeCheque detailDemandeCheque = (DetailDemandeCheque)demandeChequesService.getDetailDemandeCheque(paramDemandeCheque);
        return (detailDemandeCheque);
    }
}
