package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domaineplacement.model.OperationMoyPayAbonnement;

import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;

import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

public class InsertOperationMoyPayInteretPlacTrt extends Traitement{
    public InsertOperationMoyPayInteretPlacTrt() {
    }
    
    public IValueObject perform (IValueObject vo ) {
    
            OperationMoyPayAbonnement operationMoyPayAbonnement = (OperationMoyPayAbonnement)vo;
            OperationMoyPay  operationMoyPay = operationMoyPayAbonnement.getOperationMoyPay();
            OperationMoyPay  operationMoyPayInserer = new OperationMoyPay();
            
            this.setCroFlag(true); 
            
            try {           
                ///*** insertion dans la table Operation_Moy_Pay 
                InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt(); 
                operationMoyPayInserer = (OperationMoyPay)insertOperationMoyPayTrt.exec(operationMoyPay); 
                return operationMoyPayInserer;
            } 
            catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans InsertOperationMoyPayInteretPlacTrt : ");
                text.append(e.getMessage());
                erreur.setCode("300");
                erreur.setDescription(text.toString());
                erreur.setKey("InsertOperationMoyPayInteretPlacTrt");
                operationMoyPayInserer.addError(erreur);
                throw new RuntimeException();
            }   
        }
        
    public void genCroText(ValueObject vo) {
            
            OperationMoyPayAbonnement operationMoyPayAbonnement = (OperationMoyPayAbonnement)vo;
            OperationMoyPay  operationMoyPay = operationMoyPayAbonnement.getOperationMoyPay();
            ParamAbonnementement paramAbonnementement = operationMoyPayAbonnement.getParamAbonnementement();
            
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

               Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        com.oxia.security.abc.model.Personnel user = null;
                        if (obj instanceof UserDetails) {
                            user = (com.oxia.security.abc.model.Personnel)obj;
                       }
              
              this.setNumRefCro(Long.valueOf((operationMoyPay.getNumOperOmp())));
              if(operationMoyPay.getTache().getTacheId().getCodOperOper().equals(Constants.COD_OPER_VERSEMENT_INTERET_LIQUID_ANTICIPE) || 
                 operationMoyPay.getTache().getTacheId().getCodOperOper().equals(Constants.COD_OPER_RISTOURNE_INTERET_LIQUID_ANTICIPE)  )
                 this.setLibRefCro("SMILE.placement.Liq.Int");
              else this.setLibRefCro("SMILE.placement.av.Int");
              
              this.setDatValCro(operationMoyPay.getDatValOmp());
              this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());              
              this.setCodStrcImpt(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());
              this.setCodEtatCro(0);              
              this.setCodeProduit(operationMoyPay.getProduit().getCodPrdPrd().toString());
              this.setOperationId(operationMoyPay.getTache().getTacheId().getCodOperOper().toString());
              this.setDateOperation(operationMoyPay.getDatOperOmp());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);
              this.setTypeOperationCro("O");
                if(operationMoyPay.getTache().getTacheId().getCodOperOper().equals(Constants.COD_OPER_VERSEMENT_INTERET_LIQUID_ANTICIPE) || 
                   operationMoyPay.getTache().getTacheId().getCodOperOper().equals(Constants.COD_OPER_RISTOURNE_INTERET_LIQUID_ANTICIPE)  )
                   this.setCodTachTach(Constants.COD_TACHE_INTERET_LIQUID_ANTICIPE);
                else this.setCodTachTach(Constants.COD_TACHE_INTERET_AVANC_PLAC);
              
              this.setCodRefcOmp(operationMoyPay.getCodRefcOmp());
              this.setDatExecCro(new Date());

              this.setNumCinUser(user.getNumMatrUser());
              this.setCodTypUser(user.getMatriculeTyp());
              
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
            StringBuffer cro=new StringBuffer("");
                
                // contratClient
            cro.append("numCptBna=");
            cro.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3)+StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4)+StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6)+";");               
                                      
            if (operationMoyPay.getCodRefcOmp()!= null){
                cro.append("CONTRAT_PLACEMENT.NUM_SEQ_CPLA=");
                cro.append(operationMoyPay.getCodRefcOmp() +";");
            }

            if (paramAbonnementement!=null){///*** cas 616 remboursement retard
                cro.append("REAJUST_AVREMBLIQ.MONT_INET_REAJ=");
                if (paramAbonnementement.getMontInetReaj()!= null){///***(16)
                    cro.append(paramAbonnementement.getMontInetReaj()+";");
                }else cro.append("0" +";");
                cro.append("REAJUST_AVREMBLIQ.MONT_IPAN_REAJ=");
                if (paramAbonnementement.getMontIpanReaj()!= null){///***(15)
                    cro.append(Math.round(paramAbonnementement.getMontIpanReaj()) +";");
                }else cro.append("0" +";");
                
                cro.append("REAJUST_AVREMBLIQ.MONT_ICOM_REAJ=");
                if (paramAbonnementement.getMontIcomReaj()!= null){///***(14)
                    cro.append(Math.round(paramAbonnementement.getMontIcomReaj()) +";");
                }else cro.append("0" +";");
                cro.append("REAJUST_AVREMBLIQ.MONT_ACMR_REAJ=");
                if (paramAbonnementement.getMontAcmrReaj()!= null){///***(12)
                    cro.append(Math.round(paramAbonnementement.getMontAcmrReaj()) +";");
                }else cro.append("0" +";");
                cro.append("REAJUST_AVREMBLIQ.MONT_RISA_REAJ=");
                if (paramAbonnementement.getMontRisaReaj()!= null){///***(13)
                    cro.append(Math.round(paramAbonnementement.getMontRisaReaj()) +";");
                }else cro.append("0" +";");
            }else{
                    cro.append("AVANC_REMB_LIQUID.MONT_INET_ARL=");
                    cro.append(Math.round(operationMoyPay.getMontDinOmp()) +";");
            }
            if (operationMoyPay.getNumMoypOmp()!= null){
                cro.append("CONTRAT_PLACEMENT.NUM_BC_CPLA=");
                cro.append(operationMoyPay.getNumMoypOmp() +";");
            }
             
            this.setCroText(cro.toString());
        }   

}
