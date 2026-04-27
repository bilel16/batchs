package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.CertificationChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Prise en charge de la prévalidation d’une demande de certification chèque déplacée
 * @author El arbi hassine
 * @param CertificationCheques
 * @return CertificationCheques
 * @since 05/09/2007
 * 
 */
public class PrevaliderCertificationChequeDeplaceeCmd implements ICommande {
    public PrevaliderCertificationChequeDeplaceeCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;
        CertificationChequesService certificationChequesService = 
            (CertificationChequesService)context.getBean("certificationChequesService");
        ValueObject voo = 
            (ValueObject)certificationChequesService.prevaliderCertificationChequeDeplacee(paramDemandeChequeCertifie);
        return voo;
    }
}
