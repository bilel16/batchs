package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.HistTauxReference;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.MouvementInterne;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;

import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;

import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;
import com.bna.smile.model.domaineplacement.model.ParamInsertInteret;
import com.bna.smile.model.domaineplacement.model.ParamInteretServi;
import com.bna.smile.model.domaineplacement.service.BatchService;



import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;


public class TraitementAbonPlacementTrt  extends Traitement{
    public TraitementAbonPlacementTrt() {
    }
        
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");  
        IExpression expression = searchEngine.createExpression();
        

    public IValueObject perform(IValueObject vo) {
     
       this.setCroFlag(true); 
       
       AbonnementPlacement abonnementPlacement = (AbonnementPlacement) vo;
       
       try{             
                ///reload abonnment placement on select for update pour test etat abonPlac   
                abonnementPlacement = (AbonnementPlacement)searchEngine.loadForUpdate(AbonnementPlacement.class,abonnementPlacement.getNumSeqAbpl());
                if(abonnementPlacement.getCodEtatAbpl().equals("T")){
                    return null;
                }
                
                // cas de TMM indexé calcule du montant de l'abonnement
                if(abonnementPlacement.getMontAbplAbpl().intValue()==0){
                        majAbonnement(abonnementPlacement);
                }   
                abonnementPlacement.setCodEtatAbpl("T");   
                abonnementPlacement.setDatValAbpl(new Date()); 
                crudService.update(abonnementPlacement);  
             
                //insertion dans la table mouvement interne pour historique
                MouvementInterne mouvementInterne = new MouvementInterne();
                
                mouvementInterne.setCodRefmMvti(abonnementPlacement.getNumSeqAbpl().toString());
                mouvementInterne.setDatOperMvti(abonnementPlacement.getDatCompAbpl());
                mouvementInterne.setDatSystMvti(new Date());
                mouvementInterne.setDatValMvti(abonnementPlacement.getDatValAbpl());
                mouvementInterne.setMontMvtiMvti(abonnementPlacement.getMontAbplAbpl().longValue());
                Structure strc = new Structure();
                strc.setCodStrcStrc(abonnementPlacement.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc());
                Tache tache = new Tache();
                TacheId tacheId = new TacheId();
                /////////Effectation code opeartion selon cas 
                ////---------CAS DE TYPE OPEATION ABONNEMENT SUR AVANCE : COD_TOPR_ABPL='A' -----------
                if(abonnementPlacement.getCodToprAbpl().equals("A")){
                    //---cas abonnement sur interet avance non encore échu
                    if(abonnementPlacement.getAvancRembLiquid().getDatPrevArl()!= null && abonnementPlacement.getAvancRembLiquid().getDatPrevArl().compareTo(abonnementPlacement.getDatCompAbpl())>=0 ){
                        ///partie fixe
                        mouvementInterne.setLibMotfMvti("smile.placement.abonn.avanc");
                        tacheId.setCodTachTach(Long.valueOf("1"));
                        tacheId.setCodOperOper(Constants.COD_OPER_ABON_INTERET_REMB_AVANCE_PLAC);
                    
                    //---cas abonnement sur interet avance  échu
                    }else{
                        ///partie fixe
                        mouvementInterne.setLibMotfMvti("smile.placement.abonn.avanc.echu");
                        tacheId.setCodTachTach(Long.valueOf("1"));
                        tacheId.setCodOperOper(Constants.COD_OPER_ABONNE_AVANC_ECHU_PLAC);
  
                    }
                ////---------CAS DE TYPE OPEATION ABONNEMENT SUR SOUSCRIPTION : COD_TOPR_ABPL='S' -----------
                }else if(abonnementPlacement.getCodToprAbpl().equals("S")){
                    //---cas abonnement sur interet servi precompté
                   if(abonnementPlacement.getContratPlacement().getCodPintCpla().equals("POST") ){
                        ///partie fixe
                        mouvementInterne.setLibMotfMvti("smile.placement.abonn.souscription.postcompte");
                        tacheId.setCodTachTach(Long.valueOf("1"));
                        tacheId.setCodOperOper(Constants.COD_OPER_ABONNE_INTERET_PLAC_POSTCOMPTE);
                    
                   }else if(abonnementPlacement.getContratPlacement().getCodPintCpla().equals("PRE") ){
                       ///partie fixe
                       mouvementInterne.setLibMotfMvti("smile.placement.abonn.souscription.precompte");
                       tacheId.setCodTachTach(Long.valueOf("1"));
                       tacheId.setCodOperOper(Constants.COD_OPER_ABONNE_INTERET_PLAC_PRECOMPTE);    
                   }
                }

                tache.setTacheId(tacheId);
                Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                com.oxia.security.abc.model.Personnel user = null;
                if (obj instanceof UserDetails) {
                     user = (com.oxia.security.abc.model.Personnel)obj;
                }
                mouvementInterne.setTache(tache);
                Personnel pers = new Personnel();
                pers.setNumMatrUser(user.getNumMatrUser());
                mouvementInterne.setPersonnel(pers);
                mouvementInterne.setStructure(strc);
                InsertMouvementInterneTrt insertMouvementInterneTrt = new InsertMouvementInterneTrt();
                mouvementInterne = (MouvementInterne)insertMouvementInterneTrt.exec(mouvementInterne);

            }catch (Exception e) {           
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);
                                 
            }          
            return vo;
    }
    
    public void majAbonnement(AbonnementPlacement abonnementPlacement){
        Double mont = new Double("0"); 
        Long montSintAbpl = new Long("0");  
        ////calculke du TMM du moi
        //construction du premier jour du moi
        String datePremJour = "01/"+DateHandler.dateToStr(abonnementPlacement.getDatCompAbpl()).substring(3);
        Double TMM=getTmmJour(DateHandler.strToDate(datePremJour));
      //Date de modification de la base de calcul pour le produit BNA placement " de 360 à 365 jours " 30/06/2020
        String dateCalculBnaPlac ="30/06/2020";
        if(abonnementPlacement.getContratPlacement().getCodMargCpla() != null){
            if(abonnementPlacement.getContratPlacement().getCodMargCpla().equals("+")){
                TMM =  TMM.doubleValue() + abonnementPlacement.getContratPlacement().getNumMargCpla();
            }else{
                TMM = TMM.doubleValue() - abonnementPlacement.getContratPlacement().getNumMargCpla();
            }
        }else{
            TMM=abonnementPlacement.getContratPlacement().getNumTauiCpla();
             
        }
        
        /////////////////////////////////////abonnementPlacement.setNumTauiAbpl(TMM);
        //si autre que BNA placement
        if(!abonnementPlacement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)){
             if(abonnementPlacement.getContratPlacement().getCodPintCpla().equals("PRE")){
                mont=Math.rint(Double.valueOf(abonnementPlacement.getContratPlacement().getMontCapCpla() * abonnementPlacement.getNumNbrjAbpl() * TMM/(Constants.NBR_JOURS_BC_CAT.doubleValue()+ abonnementPlacement.getNumNbrjAbpl() * TMM))); 
            }else {
                mont=Math.rint(Double.valueOf(abonnementPlacement.getContratPlacement().getMontCapCpla() * abonnementPlacement.getNumNbrjAbpl() * TMM/(Constants.NBR_JOURS_BC_CAT.doubleValue())));
            }
       //si  BNA placement
        }else if(abonnementPlacement.getContratPlacement().getDatValCpla().after(DateHandler.strToDate(dateCalculBnaPlac))){
            if(abonnementPlacement.getContratPlacement().getCodPintCpla().equals("PRE")){
                   mont=Math.rint(Double.valueOf(abonnementPlacement.getContratPlacement().getMontCapCpla() * abonnementPlacement.getNumNbrjAbpl() * TMM/(Constants.NBR_JOURS_BC_CAT.doubleValue()+ abonnementPlacement.getNumNbrjAbpl() * TMM))); 
            }else {
                   mont=Math.rint(Double.valueOf(abonnementPlacement.getContratPlacement().getMontCapCpla() * abonnementPlacement.getNumNbrjAbpl() * TMM/(Constants.NBR_JOURS_BC_CAT.doubleValue())));
            } 
        
        }else if(abonnementPlacement.getContratPlacement().getDatValCpla().compareTo(DateHandler.strToDate(dateCalculBnaPlac))==0){
            if(abonnementPlacement.getContratPlacement().getCodPintCpla().equals("PRE")){
                mont=Math.rint(Double.valueOf(abonnementPlacement.getContratPlacement().getMontCapCpla() * abonnementPlacement.getNumNbrjAbpl() * TMM/(Constants.NBR_JOURS_BC_CAT.doubleValue()+ abonnementPlacement.getNumNbrjAbpl() * TMM))); 
         }else {
                mont=Math.rint(Double.valueOf(abonnementPlacement.getContratPlacement().getMontCapCpla() * abonnementPlacement.getNumNbrjAbpl() * TMM/(Constants.NBR_JOURS_BC_CAT.doubleValue())));
         } 
    
        }else if(abonnementPlacement.getContratPlacement().getDatValCpla().before(DateHandler.strToDate(dateCalculBnaPlac))){
        	// Garder la base de calcul de 360 jours pour les anciens contrat BNA Placement
            if(abonnementPlacement.getContratPlacement().getCodPintCpla().equals("PRE")){
                   mont=Math.rint(Double.valueOf(abonnementPlacement.getContratPlacement().getMontCapCpla() * abonnementPlacement.getNumNbrjAbpl() * TMM/(Constants.NBR_JOURS_BNAPLC.doubleValue()+ abonnementPlacement.getNumNbrjAbpl() * TMM))); 
            }else {
                   mont=Math.rint(Double.valueOf(abonnementPlacement.getContratPlacement().getMontCapCpla() * abonnementPlacement.getNumNbrjAbpl() * TMM/(Constants.NBR_JOURS_BNAPLC.doubleValue())));
            } 
      }   
      abonnementPlacement.setNumTauiAbpl(TMM);
      abonnementPlacement.setMontAbplAbpl(mont);
      //calcul du montant annuel montSintAbpl si le cas
      if(abonnementPlacement.getCodPartAbpl() !=null && abonnementPlacement.getCodPartAbpl().equals("P")){
          PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
          //recherche de la date du dernier date interet servi 
           
           GregorianCalendar calendar = new java.util.GregorianCalendar(); 
           // recalcule de la derniere date de l' interet servir 
           calendar.setTime(abonnementPlacement.getContratPlacement().getDatPintCpla()); 
           
          ///Calcule de la date du premier jour de calcul d'interet
          //dateAvant=dateinteret -365
          calendar.add(Calendar.DATE, -365);
          Date dateAvant=calendar.getTime();
          Date dateDebut;
          //si cas du premier interet servi (prem année) date inter-365 <= date dern interet servi alors date debut=date creation
          //sinon date debut=dateAvant(cad -365)
          if(dateAvant.compareTo(abonnementPlacement.getContratPlacement().getDatCreCpla())<=0){
               dateDebut=abonnementPlacement.getContratPlacement().getDatCreCpla();
          }else{
              //ajout d'un jour
              calendar.add(Calendar.DATE, 1);
              dateDebut=calendar.getTime();
          }
           
          montSintAbpl = plcDao.getSommeAbonDate(abonnementPlacement.getContratPlacement().getNumSeqCpla().toString(),DateHandler.dateToStr(dateDebut),DateHandler.dateToStr(abonnementPlacement.getDatDebAbpl()));
          abonnementPlacement.setMontSintAbpl(montSintAbpl.doubleValue() + mont);
      }
    }
    
    
    public Double getTmmJour(Date dateComptable){
    
        ICriteria criteria         = searchEngine.createCriteria();      
        criteria.add(expression.eq("histTauxReferenceId.datDebHtre",dateComptable));
        criteria.add(expression.eq("histTauxReferenceId.codTrefHtre",Constants.Taux_Ref_TMM));
        
        List list=searchEngine.find(HistTauxReference.class,criteria);
        HistTauxReference histTauxReference = (HistTauxReference)list.get(0);
        return histTauxReference.getHistTauxReferenceId().getValTrefTref();
        
    }
    public void genCroText(ValueObject vo) {

    
      AbonnementPlacement  abonnementPlacement =  (AbonnementPlacement)vo;
     /* ////si enregistrement pour la somme des abonn par mois ne rien faire
      if(abonnementPlacement.getCodPartAbpl()!=null && abonnementPlacement.getCodPartAbpl().equals("P")){
              ;
      }else{*/
          /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

          Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          com.oxia.security.abc.model.Personnel user = null;
          if (obj instanceof UserDetails) {
            user = (com.oxia.security.abc.model.Personnel)obj;
          }
          
          this.setNumRefCro(abonnementPlacement.getNumSeqAbpl());
          /////////this.setLibRefCro("smile.placement.abonn.avanc");
          this.setDatValCro(abonnementPlacement.getDatValAbpl());
          this.setCodeStructInitiatrice(abonnementPlacement.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString());              
          this.setCodStrcImpt(abonnementPlacement.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc());
          this.setCodEtatCro(0);              
          this.setCodeProduit(abonnementPlacement.getContratPlacement().getProduitPlacement().getCodPrdPlc().toString());  
          //////this.setOperationId(operationMoyPay.getTache().getTacheId().getCodOperOper().toString());
          this.setDateOperation(abonnementPlacement.getDatCompAbpl());
          SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
          formater=new SimpleDateFormat("HH:mm:ss");
          String heureString = formater.format(new Date());
          this.setHeureOperation(heureString);
          this.setDatExecCro(new Date()); // date system
          this.setTypeOperationCro("O");
          /////this.setCodTachTach(operationMoyPay.getTache().getTacheId().getCodTachTach().longValue());
          this.setCodRefcOmp(abonnementPlacement.getNumSeqAbpl().toString());
          this.setNumCinUser(user.getNumMatrUser());
          this.setCodTypUser(user.getMatriculeTyp());
         
             /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
          StringBuffer cro=new StringBuffer("");
          StringBuffer contratCPT =new StringBuffer("");
        // contratClient
          contratCPT.append(StrHandler.lpad(abonnementPlacement.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3));
          contratCPT.append(StrHandler.lpad(abonnementPlacement.getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4));
          contratCPT.append(StrHandler.lpad(abonnementPlacement.getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6));
          contratCPT.append(";");
          cro.append("numCptBna=");
          cro.append(contratCPT.toString());
        // contrat placement
          cro.append("CONTRAT_PLACEMENT.NUM_SEQ_CPLA=");
          cro.append(abonnementPlacement.getContratPlacement().getNumSeqCpla() +";");
        // numero BC si le cas
          if (abonnementPlacement.getContratPlacement().getNumBcCpla()!=null){
                // categorie personne cas du BC/CAT
                cro.append("CONTRAT_PLACEMENT.NUM_BC_CPLA=");
                cro.append(abonnementPlacement.getContratPlacement().getNumBcCpla() +";");
          }
    
    
        ////---------CAS DE TYPE OPEATION ABONNEMENT SUR AVANCE : COD_TOPR_ABPL='A' -----------
        if(abonnementPlacement.getCodToprAbpl().equals("A")){
            //---cas abonnement sur interet avance non encore échu
            if(abonnementPlacement.getAvancRembLiquid().getDatPrevArl()!= null && abonnementPlacement.getAvancRembLiquid().getDatPrevArl().compareTo(abonnementPlacement.getDatCompAbpl())>=0 ){
                ///partie fixe
                this.setLibRefCro("smile.placement.abonn.avanc");
                this.setOperationId(Constants.COD_OPER_ABON_INTERET_REMB_AVANCE_PLAC.toString());
                this.setCodTachTach(1);
                ///partie variable
                
                cro.append("ABONNEMENT_PLACEMENT.MONT_ABPL_ABPL_304=");
                cro.append(abonnementPlacement.getMontAbplAbpl().intValue() +";");
                 
                cro.append("AVANC_REMB_LIQUID.NUM_SEQ_ARL_304=");
                cro.append(abonnementPlacement.getAvancRembLiquid().getNumSeqArl() +";");
            
            //---cas abonnement sur interet avance  échu
            }else{
                ///partie fixe
                this.setLibRefCro("smile.placement.abonn.avanc.echu");
                this.setOperationId(Constants.COD_OPER_ABONNE_AVANC_ECHU_PLAC.toString());
                this.setCodTachTach(1);
                ///partie variable
                
                cro.append("ABONNEMENT_PLACEMENT.MONT_ABPL_ABPL_615=");
                cro.append(abonnementPlacement.getMontAbplAbpl().intValue() +";");
                 
                cro.append("AVANC_REMB_LIQUID.NUM_SEQ_ARL_615=");
                cro.append(abonnementPlacement.getAvancRembLiquid().getNumSeqArl() +";");
                
            }
        ////---------CAS DE TYPE OPEATION ABONNEMENT SUR SOUSCRIPTION : COD_TOPR_ABPL='S' -----------
        }else if(abonnementPlacement.getCodToprAbpl().equals("S")){
            //---cas abonnement sur interet servi precompté
           if(abonnementPlacement.getContratPlacement().getCodPintCpla().equals("POST") ){
                ///partie fixe
                this.setLibRefCro("smile.placement.abonn.souscription.postcompte");
                this.setOperationId(Constants.COD_OPER_ABONNE_INTERET_PLAC_POSTCOMPTE.toString());
                this.setCodTachTach(1);
                ///partie variable
                
                cro.append("ABONNEMENT_PLACEMENT.MONT_ABPL_ABPL_618=");
                cro.append(abonnementPlacement.getMontAbplAbpl().intValue() +";");
            
           }else if(abonnementPlacement.getContratPlacement().getCodPintCpla().equals("PRE") ){
               ///partie fixe
               this.setLibRefCro("smile.placement.abonn.souscription.precompte");
               this.setOperationId(Constants.COD_OPER_ABONNE_INTERET_PLAC_PRECOMPTE.toString());
               this.setCodTachTach(1);
               ///partie variable
               
               cro.append("ABONNEMENT_PLACEMENT.MONT_ABPL_ABPL_620=");
               cro.append(abonnementPlacement.getMontAbplAbpl().intValue() +";");
               
           }
        }
          
           this.setCroText(cro.toString());
     // }
    } 
}    
