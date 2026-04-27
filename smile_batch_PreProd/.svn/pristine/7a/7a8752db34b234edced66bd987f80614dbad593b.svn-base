package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.DetailsBc;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.MouvementInterne;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.commande.GetDetailBcCmd;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

public class InsertOperationMoyPayLiquidationPlacTrt extends Traitement{
    public InsertOperationMoyPayLiquidationPlacTrt() {
    }
    
    public IValueObject perform (IValueObject vo ) {
        
            DetailsOperationPlacement  detailsOperationPlacement  = (DetailsOperationPlacement )vo;
            OperationMoyPay  operationMoyPayInserer = new OperationMoyPay();
            
            this.setCroFlag(true); 
            
            try {           
                ///*** insertion dans la table Operation_Moy_Pay dans le cas des produit CAT en dinars et en dinars convertible
                InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt();
                
                // on insere l'operation moy pay que en cas des produits CAT CATDC BNAPLAC ou dans le cas d'une résiliation ( BC /BCDC /CAT/ CATDC)
                if(detailsOperationPlacement.getAvancRembLiquid().getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_CATDC_PLAC)
                   || detailsOperationPlacement.getAvancRembLiquid().getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_CAT_PLAC)
                   || detailsOperationPlacement.getAvancRembLiquid().getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)
                   || detailsOperationPlacement.getOperationMoyPay().getTache().getTacheId().getCodOperOper().equals(Constants.COD_OPER_RESILIATION)){                       
                       operationMoyPayInserer = (OperationMoyPay)insertOperationMoyPayTrt.exec(detailsOperationPlacement.getOperationMoyPay()); 
                }
                
                if(detailsOperationPlacement.getOperationMoyPay().getTache().getTacheId().getCodOperOper().equals(Constants.COD_OPER_RESILIATION)){ 
                    if(detailsOperationPlacement.getAvancRembLiquid().getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)                                   
                       || detailsOperationPlacement.getAvancRembLiquid().getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)){                       
                 // cas de la résiliation 
                         //récuperer automatiquement le BC
                          GetDetailBcCmd getDetailBcCmd = new GetDetailBcCmd();
                          DetailsBc detailBc  = new DetailsBc();
                          detailBc.setContratPlacement(detailsOperationPlacement.getAvancRembLiquid().getContratPlacement());
                          detailBc.setNumBcDbc(Long.valueOf(detailsOperationPlacement.getAvancRembLiquid().getContratPlacement().getNumBcCpla()));
                          detailBc = (DetailsBc)getDetailBcCmd.execute(detailBc);
                          
                        if(detailBc.getBonDeCaisse()!=null){
                            detailBc.setDateRecaBc((detailsOperationPlacement.getDatCompDopl()));
                            Context context = ContextHandler.getContext();
                            CRUDservice crudService = (CRUDservice)context.getBean("crudservice"); 
                            crudService.update(detailBc); 
                        }
                    }                 
                }
                
                
                
                 if(detailsOperationPlacement.getOperationMoyPay().getTache().getTacheId().getCodOperOper().equals(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE)){ 
                     if(detailsOperationPlacement.getAvancRembLiquid().getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)                                   
                        || detailsOperationPlacement.getAvancRembLiquid().getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)){                          
                    // insertion du mouvement interne en cas de la liq anticipée (309 ) d'un BC ou BCDC (pqque on n'insere pas dans la table operMoypay )
                     MouvementInterne mouvementInterne = new MouvementInterne();                 
                     mouvementInterne.setCodRefmMvti(detailsOperationPlacement.getAvancRembLiquid().getNumSeqArl().toString());
                     mouvementInterne.setDatOperMvti(detailsOperationPlacement.getDatCompDopl());
                     mouvementInterne.setDatSystMvti(new Date());
                     mouvementInterne.setDatValMvti(detailsOperationPlacement.getAvancRembLiquid().getDatValiArl());
                     mouvementInterne.setLibMotfMvti("Operation Liquidation Anticipee BC (309)");
                     Tache tache = new Tache();
                     TacheId tacheId = new TacheId();
                     tacheId.setCodTachTach(Long.valueOf("1"));                     
                     tacheId.setCodOperOper(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE);
                     
                      tache.setTacheId(tacheId);
                      Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                      com.oxia.security.abc.model.Personnel user = null;
                      if (obj instanceof UserDetails) {
                           user = (com.oxia.security.abc.model.Personnel)obj;
                      }
                     
                     mouvementInterne.setTache(tache);            
                     mouvementInterne.setMontMvtiMvti(detailsOperationPlacement.getAvancRembLiquid().getMontArlArl());
                     Structure strc = new Structure();
                     strc.setCodStrcStrc(detailsOperationPlacement.getAvancRembLiquid().getContratPlacement().getContratCpt().getStructure().getCodStrcStrc());
    
                     Personnel pers = new Personnel();
                     pers.setNumMatrUser(user.getNumMatrUser());
                     mouvementInterne.setPersonnel(pers);
                     mouvementInterne.setStructure(strc);
                     InsertMouvementInterneTrt insertMouvementInterneTrt = new InsertMouvementInterneTrt();
                     mouvementInterne = (MouvementInterne)insertMouvementInterneTrt.exec(mouvementInterne); 
                  }
                 }                 
                
                return operationMoyPayInserer;
            } 
            catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans InsertOperationMoyPayAvancePlacTrt:");
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
              
              if(detailsOperationPlacement.getOperationMoyPay().getNumOperOmp()!=null)
                this.setNumRefCro(Long.valueOf(detailsOperationPlacement.getOperationMoyPay().getNumOperOmp()));
              else this.setNumRefCro(avancRembLiquid.getContratPlacement().getNumBcCpla());
              
              this.setLibRefCro("SMILE.placement.Liquid");
              if(avancRembLiquid.getCodToprArl().equals(Constants.CODE_RESILIATION_PLAC)){
                  this.setLibRefCro("SMILE.placement.resil");
              }
              this.setDatValCro(detailsOperationPlacement.getOperationMoyPay().getDatValOmp());
              this.setCodeStructInitiatrice(avancRembLiquid.getContratPlacement().getContratCpt().getStructure().getCodStrcStrc().toString());              
              this.setCodStrcImpt(avancRembLiquid.getContratPlacement().getContratCpt().getStructure().getCodStrcStrc());
              this.setCodEtatCro(0);              
              this.setCodeProduit(avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().toString());
              if(avancRembLiquid.getCodToprArl().equals(Constants.CODE_LIQUIDATION_ANTICIPE)){              
                  
                  if(avancRembLiquid.getCodSbdvArl().equalsIgnoreCase("1")){
                      // liquidation anticipée...
                       if(avancRembLiquid.getCodTyplArl().equals("T"))
                          this.setOperationId(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_SBDV.toString());
                       else if(avancRembLiquid.getCodTyplArl().equals("P"))
                          this.setOperationId(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_PARTIELLE_SBDV.toString());                      
                  }else{
                      if(avancRembLiquid.getCodTyplArl().equals("T"))
                         this.setOperationId(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE.toString());
                      else if(avancRembLiquid.getCodTyplArl().equals("P"))
                         this.setOperationId(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_PARTIELLE.toString());
                  }
              }else if(avancRembLiquid.getCodToprArl().equals(Constants.CODE_LIQUIDATION_ECHEANCE)){
                  this.setOperationId(Constants.COD_OPER_LIQUID_AECH_PLAC.toString());
              }else if(avancRembLiquid.getCodToprArl().equals(Constants.CODE_RESILIATION_PLAC)){
                  this.setOperationId(Constants.COD_OPER_RESILIATION.toString());              
              }
              
              this.setDateOperation(detailsOperationPlacement.getDatCompDopl());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);
              this.setTypeOperationCro("O");
              this.setCodTachTach(Constants.COD_TACHE_VALIDATION_LIQUIDATION_ANTICIPE);
              if (avancRembLiquid.getNumSeqArl()!=null){
                this.setCodRefcOmp(avancRembLiquid.getNumSeqArl().toString());
              }else this.setCodRefcOmp("0");
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
            if(avancRembLiquid.getNumSeqArl() != null){
                cro.append("AVANC_REMB_LIQUID.NUM_SEQ_ARL=");
                cro.append(avancRembLiquid.getNumSeqArl() +";");
            }
            
            if(avancRembLiquid.getCodToprArl().equals(Constants.CODE_LIQUIDATION_ANTICIPE)){  
                if(avancRembLiquid.getCodTyplArl().equals("T")){
                // liquidation anticipée totale...
                  if(avancRembLiquid.getCodSbdvArl().equals("0"))
                    cro.append("CONTRAT_PLACEMENT.MONT_CAP_CPLA=");  // liquidation anticipée totale normale
                  else cro.append("CONTRAT_PLACEMENT.MONT_CAP_CPLA_641=");  // liquidation anticipée totale SBDV
                }else {
                  // liquidation anticipée partielle...
                   if(avancRembLiquid.getCodSbdvArl().equals("0"))
                      cro.append("CONTRAT_PLACEMENT.MONT_CAP_CPLA_625="); // liquidation anticipée partielle normale
                   else cro.append("CONTRAT_PLACEMENT.MONT_CAP_CPLA_645=");// liquidation anticipée partielle SBDV
                } 
            }else if(avancRembLiquid.getCodToprArl().equals(Constants.CODE_RESILIATION_PLAC) || avancRembLiquid.getCodToprArl().equals(Constants.CODE_LIQUIDATION_ECHEANCE) ){
                cro.append("CONTRAT_PLACEMENT.MONT_CAP_CPLA=");
            }            
             
            cro.append(avancRembLiquid.getMontArlArl() +";");
            
            cro.append("AVANC_REMB_LIQUID.NUM_TAUI_ARL=");
            cro.append(avancRembLiquid.getNumTauiArl() +";");
            
            
            if (avancRembLiquid.getContratPlacement().getNumBcCpla()!= null){
                cro.append("CONTRAT_PLACEMENT.NUM_BC_CPLA=");
                cro.append(avancRembLiquid.getContratPlacement().getNumBcCpla() +";");
            }
             
                this.setCroText(cro.toString());
        }   

}
