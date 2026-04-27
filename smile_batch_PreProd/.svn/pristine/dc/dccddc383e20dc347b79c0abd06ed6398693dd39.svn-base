package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.model.CertificationCheques;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.CertificationChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
/**
 * Commande permet de retourner l'objet CertificationCheques  
 * @author El arbi hassine
 * @since 04/09/2007
 * @version 0.1
 */
public class GetDetailDemandeCertificationChequeCmd implements ICommande {
    public GetDetailDemandeCertificationChequeCmd() {
    }
    
/**
     * methode execute 
     * @param value Object :  ParamDemandeChequeCertifieVo
     * @return value Object : CertificationCheques
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = (ParamDemandeChequeCertifie)vo;
        CertificationChequesService certificationChequesService = 
            (CertificationChequesService)context.getBean("certificationChequesService");
        
        CertificationCheques certificationCheques = (CertificationCheques)certificationChequesService.getDetailDemandeCertificationCheque(paramDemandeChequeCertifie);
        return (certificationCheques);
    }
}
