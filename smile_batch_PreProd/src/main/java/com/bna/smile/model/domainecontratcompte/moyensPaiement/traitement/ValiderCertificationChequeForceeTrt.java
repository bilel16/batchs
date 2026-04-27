package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetCommissionTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Methode permettant de valider une demande de certification chèque forcée
 * @author El arbi hassine
 * @param CertificationCheques
 * @return CertificationCheques
 * @since 14/12/2007
 * 
 */
public class ValiderCertificationChequeForceeTrt extends Traitement {
    public ValiderCertificationChequeForceeTrt() {
    }


    public IValueObject perform(IValueObject vo) throws Exception {

        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;
        Context context = ContextHandler.getContext();


        try {
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");


            //mise à jour de demande de certification 
            crudService.update(paramDemandeChequeCertifie.getCertificationCheques());

            // si la demande de certification est valide alors on insere dans la table Opération_moy_pay (V)
            // et mettre à jour le solde du contrat de suite.

            if (paramDemandeChequeCertifie.getCertificationCheques().getCodEtatCchq().equals(Constants.ETAT_CERT_VALIDE)) {

                this.setCroFlag(true); // generer le cro...
                // si l'etat de l'operation_moy_pay est valide alors on doit mettre à jour le solde du contrat:

                ContratCptSold contratCptSold = new ContratCptSold();
                contratCptSold.setContratCpt(paramDemandeChequeCertifie.getCertificationCheques().getContratCpt());
                if (paramDemandeChequeCertifie.getOperationMoyPay().getMontTvaOmp() == 
                    null)
                    paramDemandeChequeCertifie.getOperationMoyPay().setMontTvaOmp((Long.valueOf(0)));

                Long totalMnt = Long.valueOf(0);
                if (paramDemandeChequeCertifie.getOperationMoyPay().getMontTvaOmp() == 
                    null)
                    paramDemandeChequeCertifie.getOperationMoyPay().setMontTvaOmp(Long.valueOf(0));
                totalMnt = 
                        paramDemandeChequeCertifie.getCertificationCheques().getMontCertCchq() + 
                        paramDemandeChequeCertifie.getOperationMoyPay().getMontTvaOmp() + 
                        calculerCommissions(paramDemandeChequeCertifie);

                contratCptSold.setSolde(totalMnt);
                contratCptSold.setSens(Constants.COD_SENS_DB);
                UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
                ContratCpt ContratCptMaj = 
                    (ContratCpt)updateSoldTrt.exec(contratCptSold);

                paramDemandeChequeCertifie.getOperationMoyPay().setMontApreOmp(ContratCptMaj.getMontSoldCcpt());
                InsertOperationMoyPayTrt insertOperationMoyPayTrt = 
                    new InsertOperationMoyPayTrt();
                OperationMoyPay operationMoyPayInserer = 
                    (OperationMoyPay)insertOperationMoyPayTrt.exec(paramDemandeChequeCertifie.getOperationMoyPay());
                paramDemandeChequeCertifie.setOperationMoyPay(operationMoyPayInserer);
            }

            
        }catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("PecDemandeCertificationTrt " + 
                                  e.toString());
            ;
            paramDemandeChequeCertifie.addError(erreur);
            logger.error("Exception : ",e); 
            throw new RuntimeException();
        }
        return paramDemandeChequeCertifie.getCertificationCheques();
    }


    public void genCroText(ValueObject vo) {
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;
        GetCommissionTrt getCommissionTrt = new GetCommissionTrt();

        /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

        this.setNumRefCro(Long.valueOf(paramDemandeChequeCertifie.getOperationMoyPay().getNumOperOmp()));
        this.setLibRefCro("smile.operation_moy_pay");
        this.setDatValCro(paramDemandeChequeCertifie.getOperationMoyPay().getDatValOmp());
        this.setCodeStructInitiatrice(paramDemandeChequeCertifie.getOperationMoyPay().getStructureInitiatrice().getCodStrcStrc().toString());
        
        this.setCodEtatCro(0);
        
        this.setCodeProduit(paramDemandeChequeCertifie.getOperationMoyPay().getContratCpt().getContratCptId().getCodPrdPrd().toString());
        this.setOperationId(paramDemandeChequeCertifie.getOperationMoyPay().getTache().getTacheId().getCodOperOper().toString());
        this.setDateOperation(paramDemandeChequeCertifie.getCertificationCheques().getDatCertCchq());
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        formater = new SimpleDateFormat("HH:mm:ss");
        String heureString = formater.format(new Date());
        this.setHeureOperation(heureString);
        
        this.setTypeOperationCro("O");
        this.setCodTachTach(paramDemandeChequeCertifie.getOperationMoyPay().getTache().getTacheId().getCodTachTach());
        this.setCodRefcOmp(paramDemandeChequeCertifie.getOperationMoyPay().getCodRefcOmp());
        this.setCodTypUser(paramDemandeChequeCertifie.getOperationMoyPay().getPersonnelInitiateur().getCodTypUser());  
        this.setNumCinUser(paramDemandeChequeCertifie.getOperationMoyPay().getPersonnelInitiateur().getNumCinUser());  

        /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */

        StringBuffer cro = new StringBuffer("");

        // contratClient
        // contratClient
        cro.append("numCptBna=");
        cro.append(StrHandler.lpad(paramDemandeChequeCertifie.getOperationMoyPay().getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                   '0', 3) + 
                   StrHandler.lpad(paramDemandeChequeCertifie.getOperationMoyPay().getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                   '0', 4) + 
                   StrHandler.lpad(paramDemandeChequeCertifie.getOperationMoyPay().getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                   '0', 6) + "; ");


        cro.append("num_chq_cchq=");
        cro.append(paramDemandeChequeCertifie.getCertificationCheques().getNumChqCchq() + 
                   ";");

        cro.append("mont_cert_cchq=");
        cro.append(paramDemandeChequeCertifie.getCertificationCheques().getMontCertCchq() + 
                   ";");

        cro.append("mont_tva_omp=");
        cro.append(paramDemandeChequeCertifie.getOperationMoyPay().getMontTvaOmp() + 
                   ";");

        // extarction des commisions
        for (Iterator it = 
             paramDemandeChequeCertifie.getOperationMoyPay().getDetailOperMoyPaiements().iterator(); 
             it.hasNext(); ) {
            DetailOperMoyPaiement detailOperMoyPaiement = 
                (DetailOperMoyPaiement)it.next();
            cro.append(detailOperMoyPaiement.getNomencElemtCondition().getCodNecdNecd() + 
                       "=");
            cro.append(detailOperMoyPaiement.getMontValDomp() + ";");
        }

        cro.append("nom_nom_benef=");
        cro.append(paramDemandeChequeCertifie.getCertificationCheques().getNomTireCchq() + 
                   ";");

        this.setCroText(cro.toString());

    }

    public Long calculerCommissions(ParamDemandeChequeCertifie paramDemandeChequeCertifie) {
        Long sommeCommissions = Long.valueOf(0);
        for (Iterator it = 
             paramDemandeChequeCertifie.getOperationMoyPay().getDetailOperMoyPaiements().iterator(); 
             it.hasNext(); ) {
            DetailOperMoyPaiement detailOperMoyPaiement = 
                (DetailOperMoyPaiement)it.next();
            sommeCommissions = 
                    sommeCommissions + detailOperMoyPaiement.getMontValDomp();
        }
        return sommeCommissions;
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
