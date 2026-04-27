package com.bna.smile.model.domainecontratcompte.moyensPaiement.service;


import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.AnnulerCertificationChequeTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetDetailDemandeCertificationChequeTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetListDemandesChequesCertifTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.PecDemandeCertificationTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.PrevaliderCertificationChequeDeplaceeTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.ReglerCertificationChequeTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.RestituerCertificationChequeTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.ValiderCertificationChequeForceeTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;


public class CertificationChequesService extends BasicService  {

    public CertificationChequesService() {
    }

    /**
     * Methode permettant de prendre en charge la demande de certification d'un chèque.
     * 
     * @return CertificationCheques
     */
    public IValueObject pecDemandeCertification(IValueObject vo) {
        PecDemandeCertificationTrt pecDemandeCertificationTrt = 
            new PecDemandeCertificationTrt();
        return (pecDemandeCertificationTrt.exec(vo));
    }

    /**
     * Methode permettant de retourner la liste de toutes les demandes de cheques à certifier  
     * par structure et par etat de demande
     * 
     * @return Listes
     */
    public IValueObject getListDemandesChequesCertifie(IValueObject vo) {
        GetListDemandesChequesCertifTrt getListDemandesChequesCertifTrt = 
            new GetListDemandesChequesCertifTrt();
        return (getListDemandesChequesCertifTrt.exec(vo));
    }


    /**
     * Methode permettant de retourner l'objet CertificationCheques 
     * 
     * @return CertificationCheques
     */
    public IValueObject getDetailDemandeCertificationCheque(IValueObject vo) {
        GetDetailDemandeCertificationChequeTrt getDetailDemandeCertificationChequeTrt = 
            new GetDetailDemandeCertificationChequeTrt();
        return (getDetailDemandeCertificationChequeTrt.exec(vo));
    }

    /**
     * Methode permettant de prévalider une demande de certification chèque déplacée
     * 
     * @return CertificationCheques
     */
    public IValueObject prevaliderCertificationChequeDeplacee(IValueObject vo) {
        PrevaliderCertificationChequeDeplaceeTrt prevaliderCertificationChequeDeplaceeTrt = 
            new PrevaliderCertificationChequeDeplaceeTrt();
        return (prevaliderCertificationChequeDeplaceeTrt.exec(vo));
    }

    /**
     * Methode permettant d'annuler une demande de certification chèque 
     * 
     * @return CertificationCheques
     */
    public IValueObject annulerCertificationCheque(IValueObject vo) {
        AnnulerCertificationChequeTrt annulerCertificationChequeTrt = 
            new AnnulerCertificationChequeTrt();
        return (annulerCertificationChequeTrt.exec(vo));
    }

    /**
     * Methode permettant de restituer une demande de certification chèque 
     * 
     * @return CertificationCheques
     */
    public IValueObject restituterCertificationCheque(IValueObject vo) {
        RestituerCertificationChequeTrt restituerCertificationChequeTrt = 
            new RestituerCertificationChequeTrt();
        return (restituerCertificationChequeTrt.exec(vo));
    }


    /**
     * Methode permettant de regler une  chèque certifié
     * 
     * @return CertificationCheques
     */
    public IValueObject reglerCertificationCheque(IValueObject vo) {
        ReglerCertificationChequeTrt reglerCertificationChequeTrt = 
            new ReglerCertificationChequeTrt();
        return (reglerCertificationChequeTrt.exec(vo));
    }

    /**
     * Methode permettant de valider une certification cheque forcée
     * 
     * @return CertificationCheques
     */
    public IValueObject validerCertificationChequeForcee(IValueObject vo) {
        ValiderCertificationChequeForceeTrt validerCertificationChequeForceeTrt = 
            new ValiderCertificationChequeForceeTrt();
        return (validerCertificationChequeForceeTrt.exec(vo));
    }

}
