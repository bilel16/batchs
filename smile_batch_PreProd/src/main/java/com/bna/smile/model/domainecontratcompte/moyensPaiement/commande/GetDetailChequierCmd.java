package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.model.Chequier;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.Paramchequiers;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
/**
 * Commande permet de retourner l'objet DemandeCheque  
 * @author El arbi hassine
 * @since 19/05/2007    
 * @version 0.1
 */
public class GetDetailChequierCmd implements ICommande {
    public GetDetailChequierCmd() {
    
    }
    
/**
     * methode execute 
     * @param value Object :  ParamDemandeChequeVo
     * @return value Object : DemandesCheque
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        Paramchequiers Paramchequiers = (Paramchequiers)vo;
        DemandeChequesService demandeChequesService = 
        (DemandeChequesService)context.getBean("demandeChequesService");
        Chequier chequier = (Chequier)demandeChequesService.getDetailChequier(Paramchequiers);
        return (chequier);
    }
}
