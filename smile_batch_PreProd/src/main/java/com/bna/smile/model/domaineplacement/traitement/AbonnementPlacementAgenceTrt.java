package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.HistTauxReference;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Structure;

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


import com.bna.smile.model.domaineplacement.service.PlacementService;

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


public class AbonnementPlacementAgenceTrt  extends Traitement{
    public AbonnementPlacementAgenceTrt() {
    }
        
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");  
        IExpression expression = searchEngine.createExpression();
        

    public IValueObject perform(IValueObject vo) {
     
       
       ParamInteretServi paramInteretServi = (ParamInteretServi) vo;
       Structure agence = paramInteretServi.getStructure();
       Date dateComptableAgence = paramInteretServi.getDateComptableAgence();
       
       Date dateDebutRecherche ;
       
       try{
    	   ///dateDebutRecherche =DateHandler.strToDate("01/08/2023")   ;
    	   dateDebutRecherche =DateHandler.addJour(dateComptableAgence, -90);
    	  // System.out.println("dateDebutRecherche : "+DateHandler.dateToStr(dateDebutRecherche));
                String[] etat={"A","S"};
                ///*** recherche des abonnement a echéance non encore traité pour cette agence
                ICriteria criteriaPlac = searchEngine.createCriteria();
                
                //abonnement pour une agence
                criteriaPlac.add(expression.eq("codStrcAbpl",agence.getCodStrcStrc()));
                //date comptable abonnement <= date comptable && non encore traité
               // criteriaPlac.add(expression.eq("datCompAbpl",dateComptableAgence));
                
                criteriaPlac.add(expression.ge("datCompAbpl",dateDebutRecherche));
                criteriaPlac.add(expression.le("datCompAbpl",dateComptableAgence));
                criteriaPlac.add(expression.eq("codEtatAbpl","A"));
                criteriaPlac.add(expression.in("codToprAbpl",etat));
                
                
                
                
                List listeAbonnement=searchEngine.find(AbonnementPlacement.class,criteriaPlac);
            
                int nbrAbonnemnet = 0;
                Double sommeAbonnemnet = Double.valueOf("0");
                if(listeAbonnement!=null&&listeAbonnement.size()>0) {
                    nbrAbonnemnet = 0;
                    sommeAbonnemnet = Double.valueOf("0");
                    for (Iterator it1 = listeAbonnement.iterator(); it1.hasNext(); ){
                        AbonnementPlacement abonnementPlacement=(AbonnementPlacement)it1.next();      
                        if(abonnementPlacement.getContratPlacement().getCodEtatCpla().equals(Constants.ETAT_CONTRAT_PLAC_VALIDE)){
                            //critère sur fin du mois pour assuré que l'enregistrement est un abonnement(fin du moi= fin abonnement)
                            GregorianCalendar calendar = new java.util.GregorianCalendar(); 
                            calendar.setTime(abonnementPlacement.getDatFinAbpl()); 
                            int lastDate = calendar.getActualMaximum(Calendar.DATE);
                            calendar.set(Calendar.DATE, lastDate); 
                            Date dateFinMoi=calendar.getTime();
                            
                            
                            if(DateHandler.dateToStr(dateFinMoi).equals(DateHandler.dateToStr(abonnementPlacement.getDatFinAbpl()))){
                                PlacementService placementService = (PlacementService)context.getBean("placementService");
                                abonnementPlacement = (AbonnementPlacement)placementService.traitementAbonPlacement(abonnementPlacement); 
                                if(abonnementPlacement!=null){
                                    nbrAbonnemnet = nbrAbonnemnet+1;
                                    sommeAbonnemnet =  sommeAbonnemnet+ Double.valueOf(abonnementPlacement.getMontAbplAbpl().toString());
                                    paramInteretServi.setFinBatchStructure(true);
                                }
                            }
                        }
                    }  

                }
                ///*** gerer les statistiques
               gestionStatistique(dateComptableAgence, agence, nbrAbonnemnet, sommeAbonnemnet);
               

            }catch (Exception e) {
                
                logger.error("Exception : ",e);   
                ///*** gerer une exception
                gestionException(dateComptableAgence, agence, e);
                throw new RuntimeException(e);
                                 
            }          
            return vo;
    }
    
    private void gestionStatistique(Date dateComptable, Structure agence, int nbrCptPlac, Double sommePlacement) {
    
        BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
        batchStatPlacement.setCodEtatBats("V");
        batchStatPlacement.setDatSystBats(new Date());
        batchStatPlacement.setDatCompBats(dateComptable);
        batchStatPlacement.setStructure(agence);
        batchStatPlacement.setLibExtrBats(nbrCptPlac+" Abonnement placement pour la somme de : "+(sommePlacement.longValue())+" Dinars");
        BatchMetier batchMetier = new BatchMetier();
        batchMetier.setCodBatBmet(Constants.COD_BATCH_ABONNEMENT_PLAC);
        batchStatPlacement.setBatchMetier(batchMetier);
        BatchService batchService= (BatchService) context.getBean("batchService");
        batchStatPlacement = (BatchStatPlacement)batchService.InsertBatchStatPlacement(batchStatPlacement);
    }

    private void gestionException(Date dateComptable, Structure agence, Exception e) {
    
        BatchExeptionPlac batchExeptionPlac  = new BatchExeptionPlac();
        batchExeptionPlac.setDatSystBate(new Date());
        batchExeptionPlac.setDatCompBate(dateComptable);
        batchExeptionPlac.setStructure(agence);
        batchExeptionPlac.setLibTpbmBate("Exception Batch Abonnement Placement");
        batchExeptionPlac.setLibExpBate(e.getMessage());
        BatchService batchService= (BatchService) context.getBean("batchService");
        batchExeptionPlac = (BatchExeptionPlac)batchService.InsertBatchExeptionPlac(batchExeptionPlac);
    }
    
    public void genCroText(ValueObject vo) {

    } 
   
}    
