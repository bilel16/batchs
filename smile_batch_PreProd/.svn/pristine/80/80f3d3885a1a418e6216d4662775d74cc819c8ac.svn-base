package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.MouvementInterne;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;
import com.bna.smile.model.domaineplacement.model.ParamInsertInteret;

import com.bna.smile.model.domaineplacement.model.ParamLiquidation;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.fwk.util.DateHandler;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import java.util.GregorianCalendar;

import java.util.Iterator;

import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

public class ExtourneAbonnInteretServiLiqTrt extends Traitement{
    public ExtourneAbonnInteretServiLiqTrt() {
    }
    public IValueObject perform (IValueObject vo ) {
        
            ParamLiquidation paramLiquidation = (ParamLiquidation)vo;  
            
          try {    
            MouvementInterne mouvementInterne = new MouvementInterne();
            
            mouvementInterne.setCodRefmMvti(paramLiquidation.getAvancRembLiquid().getNumSeqArl().toString());
            mouvementInterne.setDatOperMvti(paramLiquidation.getDateComptLiquidation());
            mouvementInterne.setDatSystMvti(new Date());
            mouvementInterne.setDatValMvti(paramLiquidation.getAvancRembLiquid().getDatValiArl());
            
            Tache tache = new Tache();
            TacheId tacheId = new TacheId();
            tacheId.setCodTachTach(Long.valueOf("1"));
            
            if(paramLiquidation.getAvancRembLiquid().getContratPlacement().getCodPintCpla().equalsIgnoreCase(Constants.PLACEMENT_PRECOMPTE)){
                tacheId.setCodOperOper(Constants.COD_OPER_ABONNE_INTERET_PLAC_PRECOMPTE);
                mouvementInterne.setLibMotfMvti("Extourne suite liquidation 620");
            }else {
               // Placement Postcompte
               tacheId.setCodOperOper(Constants.COD_OPER_ABONNE_EXTOURN_PLAC);
               mouvementInterne.setLibMotfMvti("Extourne suite liquidation 619");              
            }
             tache.setTacheId(tacheId);
             Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             com.oxia.security.abc.model.Personnel user = null;
             if (obj instanceof UserDetails) {
                  user = (com.oxia.security.abc.model.Personnel)obj;
             }
            
            mouvementInterne.setTache(tache);
            if(!paramLiquidation.getMntExtourne().equals(Double.valueOf(0))){
              mouvementInterne.setMontMvtiMvti(paramLiquidation.getMntExtourne().longValue());
            }else {
              mouvementInterne.setMontMvtiMvti(calculMntExtourne(paramLiquidation));
            }
            
            Structure strc = new Structure();
            strc.setCodStrcStrc(paramLiquidation.getAvancRembLiquid().getContratPlacement().getContratCpt().getStructure().getCodStrcStrc());

            Personnel pers = new Personnel();
            pers.setNumMatrUser(user.getNumMatrUser());
            mouvementInterne.setPersonnel(pers);
            mouvementInterne.setStructure(strc);
            InsertMouvementInterneTrt insertMouvementInterneTrt = new InsertMouvementInterneTrt();
            mouvementInterne = (MouvementInterne)insertMouvementInterneTrt.exec(mouvementInterne);
             
            this.setCroFlag(true);            
                   
             
            } 
            catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("contratPlacementTrt  "+e.getMessage());;
                paramLiquidation.addError(erreur);
                logger.error("Exception : ",e);   
                throw new   RuntimeException(e);
            }  
            return paramLiquidation;
        }
        
    public void genCroText(ValueObject vo) {
            
            ParamLiquidation paramLiquidation = (ParamLiquidation)vo;  
            AvancRembLiquid avancRembLiquid = paramLiquidation.getAvancRembLiquid();             
                /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

                 Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                          com.oxia.security.abc.model.Personnel user = null;
                          if (obj instanceof UserDetails) {
                              user = (com.oxia.security.abc.model.Personnel)obj;
                         }
                
                this.setNumRefCro(Long.valueOf(avancRembLiquid.getNumSeqArl()));
                this.setLibRefCro("SMILE.extourne.abonn.liq");
                this.setDatValCro(paramLiquidation.getDateComptLiquidation());
                this.setCodeStructInitiatrice(avancRembLiquid.getContratPlacement().getContratCpt().getStructure().getCodStrcStrc().toString());              
                this.setCodStrcImpt(avancRembLiquid.getContratPlacement().getContratCpt().getStructure().getCodStrcStrc());
                this.setCodEtatCro(0);              
                this.setCodeProduit(avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().toString());
                if(avancRembLiquid.getContratPlacement().getCodPintCpla().equalsIgnoreCase(Constants.PLACEMENT_PRECOMPTE)){
                  this.setOperationId(Constants.COD_OPER_ABONNE_INTERET_PLAC_PRECOMPTE.toString());                    
                    ParamAbonnementement paramAbonnementement = new ParamAbonnementement();
                    paramAbonnementement.setContratPlacement(avancRembLiquid.getContratPlacement());
                    updateAbonnementPlacement(paramAbonnementement);                  
                }else this.setOperationId(Constants.COD_OPER_ABONNE_EXTOURN_PLAC.toString());
                
                this.setDateOperation(paramLiquidation.getDateComptLiquidation());
                SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
                formater=new SimpleDateFormat("HH:mm:ss");
                String heureString = formater.format(new Date());
                this.setHeureOperation(heureString);
                this.setTypeOperationCro("O");
                this.setCodTachTach(1);
                if (avancRembLiquid.getNumSeqArl()!=null)
                this.setCodRefcOmp(avancRembLiquid.getNumSeqArl().toString());
                this.setDatExecCro(new Date());

                this.setNumCinUser(user.getNumMatrUser());
                this.setCodTypUser(user.getMatriculeTyp());
                //this.setCodTypUser();  
                //this.setNumCinUser();
             
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
              StringBuffer cro=new StringBuffer("");
              StringBuffer contratCPT =new StringBuffer("");
                 // contratClient
                  cro.append("numCptBna=");
                  cro.append(StrHandler.lpad(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3)+StrHandler.lpad(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4)+StrHandler.lpad(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6)+";");               
                 
                    
            //contrat placement 
            cro.append("CONTRAT_PLACEMENT.NUM_SEQ_CPLA=");
            cro.append(avancRembLiquid.getContratPlacement().getNumSeqCpla() +";");
            
            // BC
             if (avancRembLiquid.getContratPlacement().getNumBcCpla()!= null){
                    cro.append("CONTRAT_PLACEMENT.NUM_BC_CPLA=");
                    cro.append(avancRembLiquid.getContratPlacement().getNumBcCpla() +";");
             }                
            
            if(avancRembLiquid.getContratPlacement().getCodPintCpla().equalsIgnoreCase(Constants.PLACEMENT_PRECOMPTE)){
              cro.append("ABONNEMENT_PLACEMENT.MONT_ABPL_ABPL_620=");
            }else cro.append("ABONNEMENT_PLACEMENT.MONT_SINT_ABPL_619=");
            
            if(!paramLiquidation.getMntExtourne().equals(Double.valueOf(0)) && paramLiquidation.getAvancRembLiquid().getCodSbdvArl().equals("1")){
              cro.append(paramLiquidation.getMntExtourne().longValue()+";");     
            }else{
              cro.append(calculMntExtourne(paramLiquidation) +";");                       
            } 
            
            this.setCroText(cro.toString());
            
            //Mettre à jour à 'T' tous les abonnements d'un placement POSTCOMPTE...
             ParamAbonnementement paramAbonnementement = new ParamAbonnementement();
             paramAbonnementement.setContratPlacement(paramLiquidation.getAvancRembLiquid().getContratPlacement());
             updateAbonnementPlacement(paramAbonnementement);                  
        }   
        
        
        
        public Long calculMntExtourne(ParamLiquidation paramLiquidation){
            
            
            //dans le cas d'un contrat placement précompté, on fait l'extourne du montant total des abonnement passés = montant de l'intérêt servi
            // dans le cas d'un placement postcompté, on fait l'extourne du montant total des intérêt à servir - montant total des intérêt deja servi...
             Context context = ContextHandler.getContext();
             ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
             ICriteria critereCptPlacement1 = searchEngine.createCriteria();
             IExpression expression = searchEngine.createExpression();             
             
            AvancRembLiquid avancRembLiquid = paramLiquidation.getAvancRembLiquid();   
             if (avancRembLiquid.getContratPlacement().getNumSeqCpla()!=null)      
                 critereCptPlacement1.add(expression.eq("contratPlacement.numSeqCpla",avancRembLiquid.getContratPlacement().getNumSeqCpla()));
            
            Listes listeIntretServi = new Listes();
            listeIntretServi.setList(null);

             List l = searchEngine.find(InteretServi.class, critereCptPlacement1);
             if(l!=null && l.size()>0){
                 listeIntretServi.setList(l);
             }
             
              
             Long SommeMontServi = Long.valueOf("0");
             Date dateIntertMax = null;
             Long SommeAbonnExtourne = Long.valueOf("0");
             //GetListInteretServiTrt getListInteretServiTrt = new GetListInteretServiTrt();
            
             //listeIntretServi = (Listes)getListInteretServiTrt.exec(avancRembLiquid.getContratPlacement());
             
             if(listeIntretServi.getList() !=null  && listeIntretServi.getList().size() > 0 ){
                
                for (Iterator it =  listeIntretServi.getList().iterator();it.hasNext(); ) { 
                    InteretServi interetServis = (InteretServi)it.next();
                   
                    if(interetServis.getCodTypIsrv() != null &&  interetServis.getCodTypIsrv().equalsIgnoreCase("P")){
                    // versement partiel des intérets...
                        if(dateIntertMax != null){
                          if(interetServis.getDatIsrvIsrv().after(dateIntertMax))                           
                              dateIntertMax = interetServis.getDatIsrvIsrv();
                        }else dateIntertMax = interetServis.getDatIsrvIsrv();   
                    }
                
                }    
                 //System.out.println(DateHandler.dateToStr(dateIntertMax));
             }
                  PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
                  
                  if(avancRembLiquid.getContratPlacement().getCodPintCpla().equalsIgnoreCase(Constants.PLACEMENT_PRECOMPTE)){
                    // somme extourne 620 // extraire la dernière ligne de l'abonnement non encore traitée...
                     SommeAbonnExtourne = plcDao.getSommeAbonAnneeExtournePreCompte(avancRembLiquid.getContratPlacement().getNumSeqCpla().toString());                    
                  
                  
                  }else if(avancRembLiquid.getContratPlacement().getCodPintCpla().equalsIgnoreCase(Constants.PLACEMENT_POSTCOMPTE)){
                        
                         SommeAbonnExtourne = SommeMontServi ;              
                  
                        //traitement du cas Postcompté
                        //calcul du montant des intérets reste à servir ...
                         
                         
                         if(dateIntertMax == null){
                             // pas d'interets servi donc l'extourne est egal au total des interets servis...
                             SommeAbonnExtourne = plcDao.getSommeAbonAnneeExtourne(avancRembLiquid.getContratPlacement().getNumSeqCpla().toString());
                         }else{
                             // l'extourne est egal au montants des abonnement depuis le derniers interets servis...
                                 if(dateIntertMax != null){                                     
                                     
                                     SommeAbonnExtourne = plcDao.getSommeAbonAnneeExtourne(avancRembLiquid.getContratPlacement().getNumSeqCpla().toString(),DateHandler.dateToStr(dateIntertMax), DateHandler.dateToStr(paramLiquidation.getContratPlacement().getDatEcheCpla()));                                                                  
                                 }                             
                             
                         }                            
                    }
            
            return SommeAbonnExtourne;
        }
        
        
        
        
        public void updateAbonnementPlacement(ParamAbonnementement paramAbonnementement){
            GetListAbonnementsInteretsTrt getListAbonnementsInteretsTrt = new GetListAbonnementsInteretsTrt();
            UpdateAbonnementPlacementTrt updateAbonnementPlacementTrt = new UpdateAbonnementPlacementTrt();
            Listes listAbonnement = new Listes() ;            
            listAbonnement = (Listes)getListAbonnementsInteretsTrt.exec(paramAbonnementement);
            if(listAbonnement.getList() != null &&   listAbonnement.getList().size() > 0 ){
                for (Iterator it = listAbonnement.getList().iterator();it.hasNext();) { 
                    AbonnementPlacement abonnementPlacement = (AbonnementPlacement)it.next();
                    if (!abonnementPlacement.getCodEtatAbpl().equalsIgnoreCase("T")){  
                    // etat = 'T'
                     abonnementPlacement.setCodEtatAbpl("T");
                     abonnementPlacement= (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);
                    }
                }
            }
            
        }
        
}
