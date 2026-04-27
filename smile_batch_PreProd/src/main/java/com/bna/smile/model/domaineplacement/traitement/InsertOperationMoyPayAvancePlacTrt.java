package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

public class InsertOperationMoyPayAvancePlacTrt extends Traitement{
    public InsertOperationMoyPayAvancePlacTrt() {
    }
    
    public IValueObject perform (IValueObject vo ) {
        
            DetailsOperationPlacement  detailsOperationPlacement  = (DetailsOperationPlacement )vo;
            OperationMoyPay  operationMoyPayInserer = new OperationMoyPay();
            
            this.setCroFlag(true); 
            
            try {           
                ///*** insertion dans la table Operation_Moy_Pay 
                InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt(); 
                operationMoyPayInserer = (OperationMoyPay)insertOperationMoyPayTrt.exec(detailsOperationPlacement.getOperationMoyPay()); 
                return operationMoyPayInserer;
            } 
            catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans InsertOperationMoyPayAvancePlacTrt : ");
                text.append(e.getMessage());
                erreur.setCode("300");
                erreur.setDescription(text.toString());
                erreur.setKey("InsertOperationMoyPayAvancePlacTrt");
                operationMoyPayInserer.addError(erreur);
                throw new RuntimeException();
            }   
        }
        
    public void genCroText(ValueObject vo) {
            
            DetailsOperationPlacement  detailsOperationPlacement  = (DetailsOperationPlacement )vo;
            AvancRembLiquid avancRembLiquid = detailsOperationPlacement.getAvancRembLiquid();             
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

               Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        com.oxia.security.abc.model.Personnel user = null;
                        if (obj instanceof UserDetails) {
                            user = (com.oxia.security.abc.model.Personnel)obj;
                       }
              
              this.setNumRefCro(Long.valueOf(detailsOperationPlacement.getOperationMoyPay().getNumOperOmp()));
              this.setLibRefCro("SMILE.placement.Avance");
              this.setDatValCro(detailsOperationPlacement.getOperationMoyPay().getDatValOmp());
              this.setCodeStructInitiatrice(avancRembLiquid.getContratPlacement().getContratCpt().getStructure().getCodStrcStrc().toString());              
              this.setCodStrcImpt(avancRembLiquid.getContratPlacement().getContratCpt().getStructure().getCodStrcStrc());
              this.setCodEtatCro(0);              
              this.setCodeProduit(avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().toString());
              this.setOperationId(Constants.COD_OPER_AVANCE_PLAC.toString());
              this.setDateOperation(detailsOperationPlacement.getDatCompDopl());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);
              this.setTypeOperationCro("O");
              this.setCodTachTach(Constants.COD_TACHE_VALID_AVANC_PLAC);
              if (avancRembLiquid.getNumSeqArl()!=null)
              this.setCodRefcOmp(avancRembLiquid.getNumSeqArl().toString());
              this.setDatExecCro(new Date());

              this.setNumCinUser(user.getNumMatrUser());
              this.setCodTypUser(user.getMatriculeTyp());
              //this.setCodTypUser();  
              //this.setNumCinUser();
              
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
            StringBuffer cro=new StringBuffer("");
                
                // contratClient
            cro.append("numCptBna=");
            cro.append(StrHandler.lpad(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3)+StrHandler.lpad(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4)+StrHandler.lpad(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6)+";");               
                
            if (avancRembLiquid.getContratPlacement().getNumSeqCpla()!= null){
                    cro.append("CONTRAT_PLACEMENT.NUM_SEQ_CPLA=");
                    cro.append(avancRembLiquid.getContratPlacement().getNumSeqCpla() +";");
                }
            cro.append("AVANC_REMB_LIQUID.NUM_SEQ_ARL=");
            cro.append(avancRembLiquid.getNumSeqArl() +";");

            cro.append("AVANC_REMB_LIQUID.MONT_ARL_ARL=");
            cro.append(Math.round(avancRembLiquid.getMontArlArl()) +";");
            
            cro.append("AVANC_REMB_LIQUID.NUM_TAUI_ARL=");
            cro.append(avancRembLiquid.getNumTauiArl() +";");
            
            if (avancRembLiquid.getDatPrevArl()!=null){
                SimpleDateFormat formatage=new SimpleDateFormat("dd/MM/yyyy");
                cro.append("AVANC_REMB_LIQUID.DAT_PREV_ARL=");
                String datPrevArl = formatage.format(avancRembLiquid.getDatPrevArl());
                cro.append( datPrevArl+"; ");
            }
            if (avancRembLiquid.getContratPlacement().getNumBcCpla()!= null){
                cro.append("CONTRAT_PLACEMENT.NUM_BC_CPLA=");
                cro.append(avancRembLiquid.getContratPlacement().getNumBcCpla() +";");
            }
             
                this.setCroText(cro.toString());
        }   

}
