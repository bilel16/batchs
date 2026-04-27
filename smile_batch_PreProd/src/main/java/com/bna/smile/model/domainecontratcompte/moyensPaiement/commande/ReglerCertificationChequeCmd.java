package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.CertificationChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Prise en charge du règlement  d’un cheque certifié
 * @author El arbi hassine
 * @param ParamDemandeChequeCertifie
 * @return CertificationCheques
 * @since 07/09/2007
 * 
 */
public class ReglerCertificationChequeCmd implements ICommande {

    public ReglerCertificationChequeCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;
        CertificationChequesService certificationChequesService = 
            (CertificationChequesService)context.getBean("certificationChequesService");
        ValueObject  voo = 
            (ValueObject)certificationChequesService.reglerCertificationCheque(paramDemandeChequeCertifie);
        return voo;
    }
}
