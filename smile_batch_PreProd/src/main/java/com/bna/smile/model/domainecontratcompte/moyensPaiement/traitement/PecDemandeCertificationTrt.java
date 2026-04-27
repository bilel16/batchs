package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.bna.commun.model.CertifChqMandPers;
import com.bna.commun.model.CertificationCheques;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.GetNumSequenceAgenceTrt;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetDetailContratTrt;
import com.bna.smile.model.domainecommun.traitement.GetNomencElemCondTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Prise en charge d’une demande de certification chèques.
 * @author El arbi hassine
 * @param CertificationCheques
 * @return CertificationCheques
 * @since 31/08/2007
 * 
 */
public class PecDemandeCertificationTrt extends Traitement {
    public PecDemandeCertificationTrt() {
    }
    //formation du nouveau numéro de la demande certification

    public String getNewNumDemandeCertification(CertificationCheques certificationCheques) {

        String strc = 
            StrHandler.lpad(certificationCheques.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                            '0', 3);
        String dateJour = DateHandler.dateJour();
        String d = dateJour.substring(dateJour.length() - 4);
        //appel au traitrement pour extraire le nouveau num de sequence 
        SeqAgence seqAgence = new SeqAgence();
        Structure structure = new Structure();
        structure.setCodStrcStrc(Long.valueOf(strc));
        seqAgence.setStructure(structure);
        seqAgence.setLibSeqSeqa(Constants.LIB_SEQ_SEQA_NumCertCchq);
        GetNumSequenceAgenceTrt getNumSequenceAgenceTrt = 
            new GetNumSequenceAgenceTrt();
        PrimitiveVO numSeq = 
            (PrimitiveVO)getNumSequenceAgenceTrt.exec(seqAgence);
        String m = StrHandler.lpad(numSeq.getVLong().toString(), '0', 6);

        String numDem = strc + d + m;
        return numDem;
    }


    public IValueObject perform(IValueObject vo) throws Exception {
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;
        Context context = ContextHandler.getContext();

        try {

            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");

            Long totalMnt = Long.valueOf(0);
            if (paramDemandeChequeCertifie.getOperationMoyPay().getMontTvaOmp() == 
                null)
                paramDemandeChequeCertifie.getOperationMoyPay().setMontTvaOmp(Long.valueOf(0));
            totalMnt = 
                    paramDemandeChequeCertifie.getCertificationCheques().getMontCertCchq() + 
                    paramDemandeChequeCertifie.getOperationMoyPay().getMontTvaOmp() + 
                    calculerCommissions(paramDemandeChequeCertifie);

            GetDetailContratTrt getDetailContratTrt = 
                new GetDetailContratTrt();
            ContratCpt contratCptRech = new ContratCpt();
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(paramDemandeChequeCertifie.getCertificationCheques().getContratCpt().getContratCptId().getCodStrcStrc());
            contratCptId.setCodPrdPrd(paramDemandeChequeCertifie.getCertificationCheques().getContratCpt().getContratCptId().getCodPrdPrd());
            contratCptId.setNumCcptCcpt(paramDemandeChequeCertifie.getCertificationCheques().getContratCpt().getContratCptId().getNumCcptCcpt());
            contratCptRech = 
                    (ContratCpt)getDetailContratTrt.exec(contratCptId);

            if (totalMnt > contratCptRech.getMontSoldCcpt() && 
                paramDemandeChequeCertifie.getForcage().equals("false")) {
                com.oxia.fwk.core.Error provisionIndisponible = 
                    new com.oxia.fwk.core.Error();
                provisionIndisponible.setCode("provision");
                provisionIndisponible.setDescription("Provision indisponible.");
                ;
                paramDemandeChequeCertifie.getCertificationCheques().addError(provisionIndisponible);
                return paramDemandeChequeCertifie.getCertificationCheques();
            }

            CertificationCheques certificationCheques = 
                paramDemandeChequeCertifie.getCertificationCheques();
            String numDem = 
                getNewNumDemandeCertification(certificationCheques);
            paramDemandeChequeCertifie.getCertificationCheques().setNumCertCchq(numDem);

            //insertion de la demande de certification    
            crudService.create(paramDemandeChequeCertifie.getCertificationCheques());
            if (paramDemandeChequeCertifie.getListeCerChqMandPers() != null && 
                paramDemandeChequeCertifie.getListeCerChqMandPers().size() > 
                0) {
                for (Iterator it = 
                     paramDemandeChequeCertifie.getListeCerChqMandPers().iterator(); 
                     it.hasNext(); ) {
                    CertifChqMandPers certifChqMandPers = 
                        (CertifChqMandPers)it.next();
                    //insertion dans la table CertifChqMandPers                    
                    certifChqMandPers.getCertifChqMandPersId().setNumCertCchq(paramDemandeChequeCertifie.getCertificationCheques().getNumCertCchq());
                    crudService.create(certifChqMandPers);
                }
            }
            // insertion dans la table Operation_Moy_Pay  juste dans le cas ou la certification cheque est valide : 

            if (paramDemandeChequeCertifie.getCertificationCheques().getCodEtatCchq().equals(Constants.ETAT_CERT_VALIDE)) {

                this.setCroFlag(true);
                // si l'etat de l'operation_moy_pay est valide alors on doit mettre à jour le solde du contrat:

                ContratCptSold contratCptSold = new ContratCptSold();
                contratCptSold.setContratCpt(contratCptRech);

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

            }
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("PecDemandeCertificationTrt " + 
                                  e.toString());
            ;
            paramDemandeChequeCertifie.getCertificationCheques().addError(erreur);
            logger.error("Exception : ",e); 
            throw new RuntimeException();
        }
        return paramDemandeChequeCertifie.getCertificationCheques();
    }

    public void genCroText(ValueObject vo) {
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;
        GetNomencElemCondTrt getNomencElemCondTrt = new GetNomencElemCondTrt();
        /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

        this.setNumRefCro(Long.valueOf(paramDemandeChequeCertifie.getOperationMoyPay().getNumOperOmp()));
        this.setLibRefCro("smile.operation_moy_pay");
        this.setDatValCro(paramDemandeChequeCertifie.getOperationMoyPay().getDatValOmp());
        this.setCodeStructInitiatrice(paramDemandeChequeCertifie.getOperationMoyPay().getStructureInitiatrice().getCodStrcStrc().toString());
        
        this.setCodEtatCro(0);
        
        this.setCodeProduit(paramDemandeChequeCertifie.getOperationMoyPay().getContratCpt().getContratCptId().getCodPrdPrd().toString());
        this.setOperationId(paramDemandeChequeCertifie.getOperationMoyPay().getTache().getTacheId().getCodOperOper().toString());
        this.setDateOperation(paramDemandeChequeCertifie.getOperationMoyPay().getDatOperOmp());
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

        cro.append("cod_agd_cchq="); // structure domiciliatrice
        cro.append(paramDemandeChequeCertifie.getOperationMoyPay().getStructureReceptrice().getCodStrcStrc() + 
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
