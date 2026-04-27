package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.MontantMiseDiposition;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.SeqAgenceId;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;

import com.bna.commun.service.CURService;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.Date;

public class InsertMontantMADTrt  extends Traitement{
    public Context context = ContextHandler.getContext();

    public InsertMontantMADTrt() {
    }
    

    /**
     * Methode permet d'inserer un MontantMiseDiposition 
     * @param vo : MontantMiseDiposition 
     * @return   : MontantMiseDiposition
     * @autor    : Youssef BOUSSEN 
     */

    public IValueObject perform (IValueObject vo) {

        MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)vo;
    try{
        /* insertion de la trace de la mise à disposition dans la BD */
       
       //  SequenceDAO sequenceDAO = (SequenceDAO)context.getBean("sequenceDAO");
       //  Long seq =  sequenceDAO.getSequenceMiseAdisposition();
       //  montantMiseDiposition.getMontantMiseDipositionId().setNumMmadMmad(seq.toString());
        BigDecimal valeur = (BigDecimal)getSeuilValue("retrait_MAD",montantMiseDiposition);
        
        if ((valeur != null) && (valeur.longValue()>montantMiseDiposition.getMontMontMmad().longValue())){

//        if (!montantMiseDiposition.getMontantMiseDipositionId().getCodTypeMmad().equalsIgnoreCase(Constants.COD_MONEYGRAM)){
            String strc=StrHandler.lpad(montantMiseDiposition.getStructureByCodEmetStrc().getCodStrcStrc().toString(),'0',3);
            String d=""+(new Date().getYear()+1900);
            String  m=StrHandler.lpad(getNumDossierMAD(montantMiseDiposition).toString(),'0',5);
            
            String numMAD=strc+d+m;
            montantMiseDiposition.getMontantMiseDipositionId().setNumMmadMmad(numMAD);
//        }
        this.setCroFlag(true);      
            
        CURService crudService = (CURService)context.getBean("CURService");
        crudService.create(montantMiseDiposition);

        return (montantMiseDiposition);
        
        }else {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text =  new StringBuffer("Erreur dans l'Opération : Seuil dépassé");
            erreur.setCode("2000");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertMiseDipositionTrt");
            montantMiseDiposition.addError(erreur);
            montantMiseDiposition.setErrorMessage(erreur.getDescription());
            return (montantMiseDiposition);

        }

    }
        catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans InsertMontantMADTrt : ");
            text.append(e.toString());
            erreur.setCode("300");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertMontantMADTrt");
            montantMiseDiposition.addError(erreur);
            return (montantMiseDiposition);
        }
    }

    public Long getNumDossierMAD(MontantMiseDiposition montantMiseDiposition) {

        Context context = ContextHandler.getContext();
        CURService crudService = (CURService)context.getBean("CURService");
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");

        /* Rechercher la sequence N° MAD relative à la structure donnée */

        SeqAgenceId seqAgenceId=new SeqAgenceId();
        seqAgenceId.setLibSeqSeqa("SEQ_MIS_A_DISPOSITION");
        seqAgenceId.setCodStrcStrc(montantMiseDiposition.getStructureByCodEmetStrc().getCodStrcStrc());

        SeqAgence seqAgence = (SeqAgence)searchEngine.get(SeqAgence.class, seqAgenceId);
    
        long valeur = seqAgence.getNumValSeqa().intValue() + 1;
        seqAgence.setNumValSeqa(new Long(valeur));
        /* MAJ de la sequence */
        crudService.update(seqAgence);
        /* Inserer le N° du ContratCpt*/
        return (new Long(seqAgence.getNumValSeqa().intValue()));
    }

    public void genCroText(ValueObject vo) {
            MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)vo;
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */
    try{          
              this.setNumRefCro(Long.valueOf(montantMiseDiposition.getMontantMiseDipositionId().getNumMmadMmad()));
              this.setDatValCro(new Date());
              this.setCodeStructInitiatrice(montantMiseDiposition.getStructureByCodEmetStrc().getCodStrcStrc().toString());
     //         this.setTypeCro("F");
              this.setCodEtatCro(0);
     //         this.setCodHistCro(1);
              if (montantMiseDiposition.getMontantMiseDipositionId().getCodTypeMmad().equalsIgnoreCase(Constants.COD_MONEYGRAM)){
                  this.setCodeProduit("1107");
                  this.setOperationId(Constants.COD_OPER_RETRAIT_MG.toString());// mad
                  this.setLibRefCro("smile.OMP.RetraitMoneyGr");                  
              }else if (montantMiseDiposition.getMontantMiseDipositionId().getCodTypeMmad().equalsIgnoreCase(Constants.COD_CASHADVANCE)){
                  this.setCodeProduit("1107");
                  this.setOperationId(Constants.COD_OPER_RETRAIT_CA.toString());// mad
                  this.setLibRefCro("smile.OMP.RetraitCashAdv");                  
              }else{
                  this.setCodeProduit("1251");
                  this.setOperationId(Constants.COD_OPER_RETRAIT_MAD.toString());// mad
                  this.setLibRefCro("smile.OMP.RetraitMAD");                  
              }
              this.setDateOperation(DateHandler.strToDate(DateHandler.dateToStr(montantMiseDiposition.getDatRetMmad())));
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);                    
              this.setNumCinUser(montantMiseDiposition.getPersonnelRetrait().getNumMatrUser().toString());
              this.setTypeOperationCro("O");
                
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
                
                StringBuffer cro=new StringBuffer("");
                
                cro.append("MONT_MONT_MMAD=");
                cro.append(";");
                cro.append(montantMiseDiposition.getMontMontMmad() +";");
                
                if(montantMiseDiposition.getDevise().getCodDevDev() !=null ){
                cro.append("COD_DEV_DEV=");
                cro.append(montantMiseDiposition.getDevise().getCodDevDev() +";");
                }
                if(montantMiseDiposition.getMontDevMmad()!=null && (!montantMiseDiposition.getMontDevMmad().equals(0))){
                cro.append("MONT_DEV_MMAD=");
                cro.append(montantMiseDiposition.getMontDevMmad() +";");
                }
                if(montantMiseDiposition.getCodTpceMmad() !=null && (!montantMiseDiposition.getCodTpceMmad().equals(0))){
                cro.append("COD_TPCE_MMAD=");
                cro.append(montantMiseDiposition.getCodTpceMmad() +";");
                }
                if(montantMiseDiposition.getNumPceMmad() !=null && (!montantMiseDiposition.getNumPceMmad().equals(0))){
                cro.append("NUM_PCE_MMAD=");
                cro.append(montantMiseDiposition.getNumPceMmad() +";");
                }
                if(montantMiseDiposition.getCodRtpeMmad() !=null && (!montantMiseDiposition.getCodRtpeMmad().equals(0))){
                cro.append("COD_RTPE_MMAD=");
                cro.append(montantMiseDiposition.getCodRtpeMmad() +";");
                }
                if(montantMiseDiposition.getNumCartMmad() !=null && (!montantMiseDiposition.getNumCartMmad().equals(0))){
                cro.append("NUM_CART_MMAD=");
                cro.append(montantMiseDiposition.getNumCartMmad() +";");
                }
                if(montantMiseDiposition.getPays() !=null && (!montantMiseDiposition.getPays().getCodPaysPays().equals(0))){
                cro.append("COD_PAYS_PAYS=");
                cro.append(montantMiseDiposition.getPays().getCodPaysPays() +";");
                }
                if(montantMiseDiposition.getTauxCourMmad() !=null && (!montantMiseDiposition.getTauxCourMmad().equals(0))){
                cro.append("TAUX_COUR_MMAD=");
                cro.append(montantMiseDiposition.getTauxCourMmad() +"; ");
                }
                
                //cro.append("MONT_TVA_OMP=");
                //cro.append(paramDemandeChequeCertifie.getOperationMoyPay().getMontTvaOmp()+"; ");
                
             
                this.setCroText(cro.toString());
                
    }           catch (Exception e) {
               System.out.println(e.getMessage());
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans cro : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("cro");
              montantMiseDiposition.addError(erreur);
             // return (operationMoyPay);
          }

    
        }  
        
        
              public String getNumeroTache(IValueObject vo){
                 
                  String numT = this.getOperationId().toString()+StrHandler.lpad(Constants.COD_TACHE_RETRAIT_MAD.toString(),'0',2);
                  return (numT);
              }
              
}
