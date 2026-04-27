package com.bna.smile.model.domaineguichet.traitement;


import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.service.CURService;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;


import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.Date;

public class UpdateMontantMADTrt extends Traitement{
    public Context context = ContextHandler.getContext();

    public UpdateMontantMADTrt() {
    }

    /**
     * Methode permet le mise a jour d'un MontantMiseDiposition (lors du retrait) 
     * @param vo : MontantMiseDiposition
     * @return   : MontantMiseDiposition 
     * @autor    : Youssef BOUSSEN 
     */

    public IValueObject perform (IValueObject vo) {
    
            MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)vo;
    
        try{
            /* Mis à jour de la trace de la mise à disposition dans la BD */
                
             BigDecimal valeur = (BigDecimal)getSeuilValue("retrait_MAD",montantMiseDiposition);
             if ((valeur != null) && (valeur.longValue()>montantMiseDiposition.getMontMontMmad().longValue())){

                this.setCroFlag(true);                   
                CURService crudService = (CURService)context.getBean("CURService");
                crudService.update(montantMiseDiposition);
    
                return (montantMiseDiposition);
            }else {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text =  new StringBuffer("Erreur dans l'Opération : Seuil dépassé");
                erreur.setCode("2000");
                erreur.setDescription(text.toString());
                erreur.setKey("UpdateMontantMADTrt");
                montantMiseDiposition.addError(erreur);
                montantMiseDiposition.setErrorMessage(erreur.getDescription());
                return (montantMiseDiposition);
    
            }
    
        }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = new StringBuffer("Erreur dans UpdateMontantMADTrt : ");
                text.append(e.toString());
                erreur.setCode("600");
                erreur.setDescription(text.toString());
                erreur.setKey("UpdateMontantMADTrt");
                montantMiseDiposition.addError(erreur);
                return (montantMiseDiposition);
        }
    }


    public void genCroText(ValueObject vo) {
            MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)vo;
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */
    try{          
              this.setNumRefCro(Long.valueOf(montantMiseDiposition.getMontantMiseDipositionId().getNumMmadMmad()));
              this.setLibRefCro("smile.omp.RetraitMAD");
              this.setDatValCro(new Date());
              this.setCodeStructInitiatrice(montantMiseDiposition.getStructureByCodEmetStrc().getCodStrcStrc().toString());
            //  this.setTypeCro("F");
              this.setCodEtatCro(0);
            //  this.setCodHistCro(1);
              this.setCodeProduit(Constants.COD_PRD_PRD_MAD.toString());/// provisoire
              this.setOperationId(Constants.COD_OPER_RETRAIT_MAD.toString());// mad
              this.setCodTachTach(Constants.COD_TACHE_RETRAIT_MAD);
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
                 
            String numT = Constants.COD_OPER_RETRAIT_MAD.toString()+StrHandler.lpad(Constants.COD_TACHE_RETRAIT_MAD.toString(),'0',2);
            return (numT);
        }

}
