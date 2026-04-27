package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.CertificationChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
/**
 * Commande permet de fournir la liste de toutes les demandes de cheques à certifier  
 * par structure et par etat de demande
 * @author El arbi hassine
 * @since 04/09/2007    
 * @version 0.1
 */
public class GetListDemandesChequesCertifCmd implements ICommande {
    public GetListDemandesChequesCertifCmd() {
    }
    
/**
     * methode execute 
     * @param value Object :  ParamDemandeChequeCertifieVo
     * @return value Object : Listes
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = (ParamDemandeChequeCertifie)vo;
        CertificationChequesService certificationChequesService = 
            (CertificationChequesService)context.getBean("certificationChequesService");
        
        Listes listesDemandesChequesCertifies = 
            (Listes)certificationChequesService.getListDemandesChequesCertifie(paramDemandeChequeCertifie);
        return (listesDemandesChequesCertifies);
    }
}
