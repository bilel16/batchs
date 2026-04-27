package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.InteretServi;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
///import com.bna.smile.batch.moulinette.MoulAbonnementPlac;

import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;
import com.bna.smile.model.domaineplacement.traitement.GenererAbonnementTrt;

import com.bna.smile.model.domaineplacement.traitement.GetDemandeDecisionTrt;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;

import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class genererAbonnement {
    public genererAbonnement() {
    }
   
    public static void main(String[] args) {

        String[] path = 
        { "./config/spring.xml", "./config/applicationContext-DAO.xml", "./config/applicationContext-habilitation.xml", 
          "./config/applicationContext-resources.xml", "./config/applicationContext-service.xml", 
          "./config/applicationContext-serviceBatch.xml", 
          "./config/applicationContext-serviceHabil.xml", 
          "./config/applicationContext-traitements.xml", "./config/security.xml" };

        ApplicationContext springContext =  
            new FileSystemXmlApplicationContext(path);
        Context context= (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
        context.setSpringContext(springContext);
        ContextHandler.setContext(context);
       
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");
        ICriteria   criteria       = searchEngine.createCriteria();
        IExpression expression     = searchEngine.createExpression();
        Collection collect = new ArrayList();
        IExpression expressionAbn     = searchEngine.createExpression();
        ICriteria   criteriaAbon       = searchEngine.createCriteria();
      
     /*
        criteriaAbon.add(expressionAbn.eq("datFinAbpl",DateHandler.strToDate("31/10/2010")));
        criteriaAbon.add(expressionAbn.eq("datDebAbpl",DateHandler.strToDate("31/10/2010")));
        
        List<AbonnementPlacement> listAbnnmen = searchEngine.find(AbonnementPlacement.class, criteriaAbon);
        System.out.println("size  >>>>>  "+listAbnnmen.size());
        
        for(AbonnementPlacement abnnmenPlac : listAbnnmen){
            collect.add(abnnmenPlac.getContratPlacement().getNumSeqCpla());
        }
      */
   /*    collect.add(Long.valueOf("14200800073513"));
       
        collect.add(Long.valueOf("85200800087818"));
        
        collect.add(Long.valueOf("99200800038054"));
       
        collect.add(Long.valueOf("107200800041684"));
        
        collect.add(Long.valueOf("113200800082568"));
        
        collect.add(Long.valueOf("130200800064943"));
        
        collect.add(Long.valueOf("132200800072939"));
        
        collect.add(Long.valueOf("149200800058074"));
        
        collect.add(Long.valueOf("150200800058061"));
        
        collect.add(Long.valueOf("150200800058062"));
        
        collect.add(Long.valueOf("22200800043381"));
        
        collect.add(Long.valueOf("28200800089610"));
        
        collect.add(Long.valueOf("28200800089617"));
        
        collect.add(Long.valueOf("28200800089625"));

        collect.add(Long.valueOf("33200800087153"));
        

                collect.add(Long.valueOf("43200800087382"));
               
               collect.add(Long.valueOf("52200800044502"));
                
                collect.add(Long.valueOf("80200800062623"));
                
                collect.add(Long.valueOf("80200800062701"));*/
               
               /* collect.add(Long.valueOf(""));
                
                collect.add(Long.valueOf(""));
                
                collect.add(Long.valueOf(""));
                
                collect.add(Long.valueOf(""));
                
                collect.add(Long.valueOf(""));
                
                collect.add(Long.valueOf(""));
                
                collect.add(Long.valueOf(""));
                
                collect.add(Long.valueOf(""));*/
                
               
                collect.add(Long.valueOf("120201000008164"));
                
               
        criteria.add(expression.in("numSeqCpla",collect));
        
        List l = searchEngine.find(ContratPlacement.class, criteria);
        System.out.println("size llll >>>>>  "+l.size());
      //Date de modification de la base de calcul pour le produit BNA placement " de 360 à 365 jours " 30/06/2020
        String dateCalculBnaPlac ="30/06/2020";
        if(l!=null && l.size()>0){
            
            for (Iterator it1 = l.iterator(); it1.hasNext(); ){
                
                ContratPlacement cpla = (ContratPlacement)it1.next();
                ICriteria   criteriaAbonnement       = searchEngine.createCriteria();
                criteriaAbonnement.add(expressionAbn.eq("contratPlacement.numSeqCpla",cpla.getNumSeqCpla()));
                criteriaAbonnement.add(expressionAbn.eq("codToprAbpl",new String("S")));
                    
         //   if(cpla.getMontCapCpla().equals(cpla.getMontActuCpla())){
               CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
                List<AbonnementPlacement> listAbon = searchEngine.find(AbonnementPlacement.class, criteriaAbonnement);
                for(AbonnementPlacement abn : listAbon ){
                    crudService.remove(abn);
                }
               
           /*   }else {
                 System.out.println("---------->>>>>>>>> REMOVE *** Montant capital différent d l actuel  :  "+cpla.getNumSeqCpla().toString());
                    }*/
              List  listAbn = searchEngine.find(AbonnementPlacement.class, criteriaAbonnement);
                if(listAbn == null || listAbn.size() == 0){
            //  if(cpla.getMontCapCpla().equals(cpla.getMontActuCpla())){
                // génération abonnement
                 ParamAbonnementement paramAbonnementement = new ParamAbonnementement();
                    ICriteria   criteriaDemande       = searchEngine.createCriteria();
                    IExpression expressionDemande     = searchEngine.createExpression();
                    criteriaDemande.add(expressionDemande.eq("numRefdDemd",cpla.getNumSeqCpla()));   
                 List<DemandeDecision>  listDemande = searchEngine.find(DemandeDecision.class, criteriaDemande);  
                 paramAbonnementement.setDatDebAbpl(listDemande.get(0).getDatValDemd());
                 paramAbonnementement.setDatFinAbpl(cpla.getDatEcheCpla());
                 paramAbonnementement.setTypeOperation("S");///*** S:souscription, A:avance
                 paramAbonnementement.setNumSeqCpla(cpla.getNumSeqCpla());
                 paramAbonnementement.setTypeInteret(cpla.getCodFavCpla()); ///*** I:indexé
                 paramAbonnementement.setMontTotAbpl(cpla.getMontCapCpla()); ///*** montant placement
                 paramAbonnementement.setNumTauiCpla(cpla.getNumTauiCpla());
                 if(cpla.getContratPlacementByNumSqcrCpla() != null){
                    paramAbonnementement.setOpRenouvellemnt(true);
                 }else {
                     paramAbonnementement.setOpRenouvellemnt(false);
                 }
                Long codProduit = cpla.getProduitPlacement().getCodPrdPlc();
                double montInteret =0;
                Long montantInt = new Long(0);
                if(codProduit.equals(Constants.COD_PRD_BC_PLAC) || codProduit.equals(Constants.COD_PRD_BCDC_PLAC) || codProduit.equals(Constants.COD_PRD_CAT_PLAC) || codProduit.equals(Constants.COD_PRD_CATDC_PLAC)){
                    
                if(cpla.getCodPintCpla().equals("PRE")){
                        InteretServi interetServi = new InteretServi();
                        interetServi = getInteretServi(cpla, context);
                        
                        if(interetServi != null){
                        montantInt =interetServi.getMontBrutIsrv();
                          }else {
                              montInteret = Math.round(cpla.getMontCapCpla().doubleValue() * cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue()/(36500+ (cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue()))) ;
                              montantInt =  Long.valueOf(new Double(montInteret).longValue());
                          }
                    }else {
                     montantInt = Long.valueOf(new Double(Math.round(cpla.getMontCapCpla().doubleValue() * cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue()/36500)).longValue());
                    }
                }else if(codProduit.equals(Constants.COD_PRD_BNAPLC_PLAC) && cpla.getDatValCpla().after(DateHandler.strToDate(dateCalculBnaPlac))){
                        // cas du produit BNA placement
                        if(cpla.getCodPintCpla().equals("PRE")){
                                InteretServi interetServi = new InteretServi();
                                interetServi = getInteretServi(cpla, context);
                             if(interetServi != null){
                                      montantInt =interetServi.getMontBrutIsrv();
                                  }else {
                                      montInteret = Math.round(cpla.getMontCapCpla().doubleValue() * cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue()/(36500+ (cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue()))) ;
                                      montantInt =  Long.valueOf(new Double(montInteret).longValue());
                                  }
                            }else {
                             montantInt =  Long.valueOf(new Double(Math.round(cpla.getMontCapCpla().doubleValue() * cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue()/36500)).longValue());
                            }
                }else if(codProduit.equals(Constants.COD_PRD_BNAPLC_PLAC) && cpla.getDatValCpla().compareTo(DateHandler.strToDate(dateCalculBnaPlac))==0){
                    // cas du produit BNA placement
                    if(cpla.getCodPintCpla().equals("PRE")){
                            InteretServi interetServi = new InteretServi();
                            interetServi = getInteretServi(cpla, context);
                         if(interetServi != null){
                                  montantInt =interetServi.getMontBrutIsrv();
                              }else {
                                  montInteret = Math.round(cpla.getMontCapCpla().doubleValue() * cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue()/(36500+ (cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue()))) ;
                                  montantInt =  Long.valueOf(new Double(montInteret).longValue());
                              }
                        }else {
                         montantInt =  Long.valueOf(new Double(Math.round(cpla.getMontCapCpla().doubleValue() * cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue()/36500)).longValue());
                        }
                    
                }else if(codProduit.equals(Constants.COD_PRD_BNAPLC_PLAC) && cpla.getDatValCpla().before(DateHandler.strToDate(dateCalculBnaPlac))){
                    // cas du produit BNA placement
                	// Garder la base de calcul de 360 jours pour les anciens contrat BNA Placement
                    if(cpla.getCodPintCpla().equals("PRE")){
                            InteretServi interetServi = new InteretServi();
                            interetServi = getInteretServi(cpla, context);
                         if(interetServi != null){
                                  montantInt =interetServi.getMontBrutIsrv();
                              }else {
                                  montInteret = Math.round(cpla.getMontCapCpla().doubleValue() * cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue()/(36000+ (cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue()))) ;
                                  montantInt =  Long.valueOf(new Double(montInteret).longValue());
                              }
                        }else {
                         montantInt =  Long.valueOf(new Double(Math.round(cpla.getMontCapCpla().doubleValue() * cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue()/36000)).longValue());
                        }
                
                } else {
                        // cas autre produit que BC/BCDC CAT/CATDC BNA placement
                            montantInt =  Long.valueOf(new Double(Math.round(cpla.getMontCapCpla().doubleValue() * cpla.getNumNbrjCpla().doubleValue() * cpla.getNumTauiCpla().doubleValue() /36000)).longValue());
                    }
                
                paramAbonnementement.setMontItotAbpl(montantInt);
                paramAbonnementement.setDateCompAgence(new Date());
                paramAbonnementement.setContratPlacement(cpla);
                GenererAbonnementTrt genererAbonnementTrt = new GenererAbonnementTrt(); // type de faveur différen d'indexé au TMM
                genererAbonnementTrt.exec(paramAbonnementement);
                paramAbonnementement=null;
               
             /*   }else {
                    System.out.println("---------->>>>>>>>>  *** Montant capital différent d l actuel  :  "+cpla.getNumSeqCpla().toString());
                }*/
                }else {
                    System.out.println("------________------- Abonnement existe dejà  :  "+cpla.getNumSeqCpla().toString());
                    listAbn =null;
                }
              System.out.println("Fin");
            }
            
        }
    }
    public static InteretServi getInteretServi(ContratPlacement cpla, Context context){
        InteretServi interetServi = new InteretServi();
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");
        ICriteria   criteria       = searchEngine.createCriteria();
        IExpression expression     = searchEngine.createExpression();
        
        criteria.add(expression.eq("contratPlacement.numSeqCpla",cpla.getNumSeqCpla()));
        List l = searchEngine.find(InteretServi.class, criteria);
        if(l!=null && l.size()>0){
            interetServi = (InteretServi)l.get(0);   
        }else {
            interetServi = null;
        }
      return interetServi;
    }
}

/*
 * update abonnement_placement set cod_etat_abpl = 'T'
where trunc(dat_fin_abpl) <= trunc(to_date('28/02/2010','dd/mm/yyyy'))
and cod_strc_abpl = 62

select DISTINCT num_seq_cpla from abonnement_placement 
where cod_strc_abpl = 62

select num_seq_cpla from
contrat_placement where
cod_etat_cpla='V' 
and cod_strc_ccpt =62
order by num_seq_cpla
 */