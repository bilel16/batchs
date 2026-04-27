package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.CertificationChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Prise en charge de la restitution d’une demande de certification chèque 
 * @author El arbi hassine
 * @param CertificationCheques
 * @return CertificationCheques
 * @since 07/09/2007
 * 
 */
public class RestituerCertificationChequeCmd implements ICommande {

    public RestituerCertificationChequeCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;
        CertificationChequesService certificationChequesService = 
            (CertificationChequesService)context.getBean("certificationChequesService");
        ValueObject voo = 
            (ValueObject)certificationChequesService.restituterCertificationCheque(paramDemandeChequeCertifie);
        return voo;
    }
}
