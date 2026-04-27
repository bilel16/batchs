package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Methode permettant de regler un cheque certifié
 * @author El arbi hassine
 * @param ParamDemandeChequeCertifie
 * @return CertificationCheques
 * @since 07/09/2007
 * 
 */
public class ReglerCertificationChequeTrt extends Traitement {
    public ReglerCertificationChequeTrt() {
    }

    public IValueObject perform(IValueObject vo) {

        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;
        Context context = ContextHandler.getContext();

        try {

            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            //mise à jour de demande de certification ( mettre l'etat réglé);            
            crudService.update(paramDemandeChequeCertifie.getCertificationCheques());


            this.setCroFlag(true); // generer le cro...


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("ReglerCertificationChequeTrt " + e.toString());            
            paramDemandeChequeCertifie.getCertificationCheques().addError(erreur);
            logger.error("Exception : ",e); 
            throw new RuntimeException(e);  
        }
        return paramDemandeChequeCertifie.getCertificationCheques();
    }

    public void genCroText(ValueObject vo) {
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;
        /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

        this.setNumRefCro(Long.valueOf(paramDemandeChequeCertifie.getCertificationCheques().getNumCertCchq()));
        this.setLibRefCro("smile.certificationCheque");
        this.setDatValCro(paramDemandeChequeCertifie.getCertificationCheques().getDatPayCchq());
        this.setCodeStructInitiatrice(paramDemandeChequeCertifie.getCertificationCheques().getCodAgiCchq().toString());
        
        this.setCodEtatCro(0);
       
        this.setCodeProduit(paramDemandeChequeCertifie.getCertificationCheques().getContratCpt().getContratCptId().getCodPrdPrd().toString());
        this.setOperationId(Constants.COD_OPER_REGL_CHQ_CERT.toString());
        this.setDateOperation(new Date());
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        formater = new SimpleDateFormat("HH:mm:ss");
        String heureString = formater.format(new Date());
        this.setHeureOperation(heureString);       
        this.setTypeOperationCro("G"); // reglement
        this.setCodTachTach(paramDemandeChequeCertifie.getOperationMoyPay().getTache().getTacheId().getCodTachTach());
        this.setCodRefcOmp(paramDemandeChequeCertifie.getOperationMoyPay().getCodRefcOmp());
        this.setCodTypUser(paramDemandeChequeCertifie.getOperationMoyPay().getPersonnelInitiateur().getCodTypUser());  
        this.setNumCinUser(paramDemandeChequeCertifie.getOperationMoyPay().getPersonnelInitiateur().getNumCinUser());  

        /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */

        StringBuffer cro = new StringBuffer("");

        cro.append("NUM_CHQ_CCHQ=");
        cro.append(paramDemandeChequeCertifie.getCertificationCheques().getNumChqCchq() + 
                   "; ");


        cro.append("MONT_CERT_CCHQ=");
        cro.append(paramDemandeChequeCertifie.getCertificationCheques().getMontCertCchq() + 
                   "; ");


        cro.append("COD_AGD_CCHQ="); // structure domiciliatrice
        cro.append(paramDemandeChequeCertifie.getCertificationCheques().getCodAgdCchq().toString() + 
                   "; ");

        cro.append("nom_nom_benef=");
        cro.append(paramDemandeChequeCertifie.getCertificationCheques().getNomTireCchq() + 
                   ";");


        this.setCroText(cro.toString());

    }
    public String getNumeroTache(IValueObject vo) {
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;
      return (paramDemandeChequeCertifie.getOperationMoyPay().getTache().getTacheId().getCodOperOper().toString() + 
              StrHandler.lpad(paramDemandeChequeCertifie.getOperationMoyPay().getTache().getTacheId().getCodTachTach().toString(),'0',2));    
    }


    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(paramDemandeChequeCertifie.getCertificationCheques().getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }
}
