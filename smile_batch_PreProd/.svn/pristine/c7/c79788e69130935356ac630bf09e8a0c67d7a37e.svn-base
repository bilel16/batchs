package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;

import com.bna.commun.traitements.Traitement;

import com.bna.commun.util.StrHandler;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;

import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.security.abc.model.Personnel;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;


public class InsertOperationMoyPayRembAvancPlacTrt extends Traitement{
    public InsertOperationMoyPayRembAvancPlacTrt() {
    }
    
    public IValueObject perform (IValueObject vo ) {
        
            ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;         
            OperationMoyPay  operationMoyPayInserer   = new OperationMoyPay();
            
            this.setCroFlag(true); 
            
            try {           
                ///*** insertion dans la table Operation_Moy_Pay 
                InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt(); 
                operationMoyPayInserer = (OperationMoyPay)insertOperationMoyPayTrt.exec(paramContratPlacement.getDetailsOperationPlacement().getOperationMoyPay()); 
                return operationMoyPayInserer;
            } 
            catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans InsertOperationMoyPayRembAvancPlacTrt : ");
                text.append(e.getMessage());
                erreur.setCode("301");
                erreur.setDescription(text.toString());
                erreur.setKey("InsertOperationMoyPayRembAvancPlacTrt");
                operationMoyPayInserer.addError(erreur);
                throw new RuntimeException();
            }   
        }

    public void genCroText(ValueObject vo) {
            
            ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;         
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

               Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        Personnel user = null;
                        if (obj instanceof UserDetails) {
                            user = (Personnel)obj;
                       }
              
              this.setNumRefCro(Long.valueOf(paramContratPlacement.getDetailsOperationPlacement().getOperationMoyPay().getNumOperOmp()));
              this.setLibRefCro("SMILE.placement.RembAvanc");
              this.setDatValCro(paramContratPlacement.getDetailsOperationPlacement().getDatValDopl());
              this.setCodeStructInitiatrice(paramContratPlacement.getDetailsOperationPlacement().getStructure().getCodStrcStrc().toString());              
              this.setCodStrcImpt(paramContratPlacement.getDetailsOperationPlacement().getStructure().getCodStrcStrc());
              this.setCodEtatCro(0);              
              this.setCodeProduit(paramContratPlacement.getAvancRembLiquid().getContratPlacement().getProduitPlacement().getCodPrdPlc().toString());
              this.setOperationId(paramContratPlacement.getDetailsOperationPlacement().getTache().getTacheId().getCodOperOper().toString());
              this.setDateOperation(paramContratPlacement.getDetailsOperationPlacement().getDatCompDopl());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);
              this.setTypeOperationCro("O");
              this.setCodTachTach(paramContratPlacement.getDetailsOperationPlacement().getTache().getTacheId().getCodTachTach());
              this.setCodRefcOmp(paramContratPlacement.getAvancRembLiquid().getNumSeqArl().toString());
              this.setDatExecCro(new Date());

              this.setNumCinUser(user.getNumMatrUser());
              this.setCodTypUser(user.getMatriculeTyp());
              //this.setCodTypUser();  
              //this.setNumCinUser();
              
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
            StringBuffer cro=new StringBuffer("");
                
                // contratClient
            cro.append("numCptBna=");
            cro.append(StrHandler.lpad(paramContratPlacement.getDetailsOperationPlacement().getStructure().getCodStrcStrc().toString(),'0',3)+StrHandler.lpad(paramContratPlacement.getAvancRembLiquid().getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4)+StrHandler.lpad(paramContratPlacement.getAvancRembLiquid().getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6)+";");               
                
            if (paramContratPlacement.getAvancRembLiquid().getContratPlacement().getNumSeqCpla()!= null){
                    cro.append("CONTRAT_PLACEMENT.NUM_SEQ_CPLA=");
                    cro.append(paramContratPlacement.getAvancRembLiquid().getContratPlacement().getNumSeqCpla() +";");
                }
            cro.append("AVANC_REMB_LIQUID.NUM_SEQ_ARL=");
            cro.append(paramContratPlacement.getAvancRembLiquid().getNumSeqArl() +";");

            cro.append("AVANC_REMB_LIQUID.MONT_ARL_ARL=");
            cro.append(paramContratPlacement.getAvancRembLiquid().getMontArlArl() +";");

            cro.append("AVANC_REMB_LIQUID.NUM_TAUI_ARL=");
            cro.append(paramContratPlacement.getAvancRembLiquid().getNumTauiArl() +";");

            cro.append("AVANC_REMB_LIQUID.COD_TYPI_ARL=");
            cro.append(paramContratPlacement.getAvancRembLiquid().getCodTypiArl() +";");
            
            if (paramContratPlacement.getAvancRembLiquid().getContratPlacement().getNumBcCpla()!= null){
                cro.append("CONTRAT_PLACEMENT.NUM_BC_CPLA=");
                cro.append(paramContratPlacement.getAvancRembLiquid().getContratPlacement().getNumBcCpla() +";");
            }
                
             
            this.setCroText(cro.toString());
        }   


}
