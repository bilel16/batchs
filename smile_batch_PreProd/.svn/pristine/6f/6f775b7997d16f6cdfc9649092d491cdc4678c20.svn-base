package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.MandPersOperPlac;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.ReajustAvrembliq;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmilePlacementException;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.commande.GetListInteretServiCmd;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;

import com.bna.smile.model.domaineplacement.model.ParamLiquidation;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

public class ReajusterAbonnementLiquidationTrt extends Traitement{
    public ReajusterAbonnementLiquidationTrt() {
    }
    
    /**
     * methode qui réajuste et genere le tableau des abonnements suite à la liquidation anticipé  d'un placement donné 
     * @param vo : ParamAbonnementement
     * @return   : ParamAbonnementement
     * @autor    : EL ARBI HASSINE  
     * @date     : 12/08/2009
     */
    
    public IValueObject perform(IValueObject vo) throws  SmilePlacementException
 {
        Context context = ContextHandler.getContext();
        //ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");

        ParamAbonnementement paramAbonnementement = (ParamAbonnementement)vo;

       
        try{
              
               // recherche des abonnements de souscription d'un placement donné (typeOperaion = "S")
                GetListAbonnementsInteretsTrt getListAbonnementsInteretsTrt = new GetListAbonnementsInteretsTrt();
                UpdateAbonnementPlacementTrt updateAbonnementPlacementTrt = new UpdateAbonnementPlacementTrt();
                Listes listAbonnement = new Listes() ;
                paramAbonnementement.setTypeOperation("S"); // parametre pour extraire les abonnements de la souscription
                listAbonnement = (Listes)getListAbonnementsInteretsTrt.exec(paramAbonnementement);
                
                Double nbrJourAnne = Double.valueOf("0"); 
                Double nbrJourAnnePrecedente = Double.valueOf("0"); 
                Double nbrJourAnneCourante = Double.valueOf("0"); 
                Double nbrJourAnneAbApresVP = Double.valueOf("0"); 
                Double nbrJourAnnulee       = Double.valueOf("0");
                Long nbrJourMoisExtourne  = Long.valueOf("0");
                Double montInteretsAnnulle = Double.valueOf("0"); 
                Double montInteretMoisLiq  = Double.valueOf("0"); 
                Double montInteretCorrecAnneesPrec = Double.valueOf("0"); 
                Double montInteretDiffAnneesCourante = Double.valueOf("0"); 
                Double montInteretServis = Double.valueOf("0"); 
                Double montInteretAbonnPostCompte = Double.valueOf("0"); 
                Double montAVerserClt = Double.valueOf("0"); 
                Double montAPercevoirClt = Double.valueOf("0"); 
                Double montInteretAvdernLigne = Double.valueOf("0"); 
                
                Double montRetenu = Double.valueOf("0"); 
                Double montExtourne = Double.valueOf("0"); 
                                
                montRetenu = paramAbonnementement.getMontRetenu(); 
                
                Date dateDernierIntServi = null;
                // préciser le dénominateur
              //Date de modification de la base de calcul pour le produit BNA placement " de 360 à 365 jours " 30/06/2020
                String dateCalculBnaPlac ="30/06/2020";
                if(paramAbonnementement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC) && paramAbonnementement.getContratPlacement().getDatValCpla().after(DateHandler.strToDate(dateCalculBnaPlac))){   
                    nbrJourAnne  = Constants.NBR_JOURS_BC_CAT.doubleValue();
                }else if(paramAbonnementement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC) && paramAbonnementement.getContratPlacement().getDatValCpla().compareTo(DateHandler.strToDate(dateCalculBnaPlac))==0){   
                    nbrJourAnne  = Constants.NBR_JOURS_BC_CAT.doubleValue();
                }else if(paramAbonnementement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC) && paramAbonnementement.getContratPlacement().getDatValCpla().before(DateHandler.strToDate(dateCalculBnaPlac))){   
                	 // Garder la base de calcul de 360 jours pour les anciens contrat BNA Placement
                	nbrJourAnne  = Constants.NBR_JOURS_BNAPLC.doubleValue();
                
                }else {
                    nbrJourAnne  = Constants.NBR_JOURS_BC_CAT.doubleValue();
                }                  
               
                Double mont = Double.valueOf("0");  
                String cas = "";
                
                
                InsertAbonnementPlacementTrt insertAbonnementPlacementTrt =new InsertAbonnementPlacementTrt();
                
                // determiner l'année de l'exercice courant : 
                int anneeCourante = DateHandler.GetYearFromDate(paramAbonnementement.getDateLiquidationAnticipe());
                
                
                if(listAbonnement.getList() != null && listAbonnement.getList().size()> 0){
                
                //####################### ETUDE DE CAS DE PLACEMENTS PRECOMPTE  #############################################
                   if(paramAbonnementement.getContratPlacement().getCodPintCpla().equals(Constants.PLACEMENT_PRECOMPTE)){   
                     
                     for (Iterator it = listAbonnement.getList().iterator();it.hasNext();) { 
                         AbonnementPlacement abonnementPlacement = (AbonnementPlacement)it.next();
                        //las abonnements qui précèdent la date de la liquidation...                       
                         if (abonnementPlacement.getDatFinAbpl().before(paramAbonnementement.getDateLiquidationAnticipe())){                       
                               // ajuster les abonnements inferieurs à la date de liquidation anticipée...
                            abonnementPlacement.setCodEtatAbpl("J");
                                // insertion des nouveaux abonnements avec la date reele de placement et le nouveau taux pénalisé 
                                                        
                              mont=(paramAbonnementement.getMontTotAbpl() * abonnementPlacement.getNumNbrjAbpl() * paramAbonnementement.getNumTauiCpla()/(nbrJourAnne + (paramAbonnementement.getDureeReelPlc() * paramAbonnementement.getNumTauiCpla()) ));
                              
                              abonnementPlacement.setMontReajAbpl(Math.rint(mont));
                           
                              abonnementPlacement.setAvancRembLiquid(paramAbonnementement.getAvancRembLiquid());
                              abonnementPlacement.setContratPlacement(paramAbonnementement.getContratPlacement());
                              abonnementPlacement.setCodTypAbpl(paramAbonnementement.getAvancRembLiquid().getCodTypiArl());                              
                              
                              abonnementPlacement.setCodToprAbpl("L");
                              if(paramAbonnementement.getAvancRembLiquid().getCodToprArl().equals(Constants.CODE_RESILIATION_PLAC)){
                                  // cas de la resiliation...
                                  abonnementPlacement.setCodToprAbpl("R");
                              }                              
                              abonnementPlacement= (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);
                             
                              // determiner l'année de l'abonnement et la comparer avec l'année courante :                              
                             if(anneeCourante > DateHandler.GetYearFromDate(abonnementPlacement.getDatCompAbpl())){
                                 // calculer le nombre de jours des abonnements de l'année précédente                                 
                                 nbrJourAnnePrecedente = nbrJourAnnePrecedente + abonnementPlacement.getNumNbrjAbpl();
                             }else if(anneeCourante == DateHandler.GetYearFromDate(abonnementPlacement.getDatCompAbpl())){
                                 // le nombre de jour des abonnement durant l'exercice courant
                                  nbrJourAnneCourante = nbrJourAnneCourante + abonnementPlacement.getNumNbrjAbpl();
                             }
                             // la somme des montants des abonnements jusqu'à la dernière ligne d'abonnement( la ligne de liquidation n'est pas comprise)
                             montInteretAvdernLigne = montInteretAvdernLigne + mont;
                             
                         }else if(abonnementPlacement.getDatFinAbpl().after(paramAbonnementement.getDateLiquidationAnticipe()) && abonnementPlacement.getDatDebAbpl().before(paramAbonnementement.getDateLiquidationAnticipe())){
                              // traiter la ligne de l'abonnement de la date de liquidation
                               // annuler la ligne où la date de liquidation existe entre la date début et date fin de l'abonnmenet
                               // et crééer une autre ligne d'abonnement qui a comme date début = date de début de cet abonnement el la date fin = date de liquidation
                               
                             abonnementPlacement.setCodEtatAbpl("J"); 
                             // insertion d'un nouvel abonnement... 
                             AbonnementPlacement newAbonnementPlacement  = new AbonnementPlacement();
                             newAbonnementPlacement.setDatDebAbpl(abonnementPlacement.getDatDebAbpl());
                             newAbonnementPlacement.setDatFinAbpl(paramAbonnementement.getDateLiquidationAnticipe());
                             newAbonnementPlacement.setDatCompAbpl(getDateComptabilisation(newAbonnementPlacement.getDatFinAbpl()));
                             newAbonnementPlacement.setDatValAbpl(CalanderHandler.GetNextWorkingDay(paramAbonnementement.getDateLiquidationAnticipe()));///??? J+1
                             newAbonnementPlacement.setNumNbrjAbpl(Long.valueOf((Double.valueOf(Math.rint(DateHandler.getDaysBetween(newAbonnementPlacement.getDatDebAbpl(),newAbonnementPlacement.getDatFinAbpl())))).longValue()));                             
                                                         
                             mont =  paramAbonnementement.getInteretsReelPlc() - Math.rint(montInteretAvdernLigne);
                             
                             newAbonnementPlacement.setMontReajAbpl(mont);
                             newAbonnementPlacement.setMontAbplAbpl(mont);                             
                             newAbonnementPlacement.setAvancRembLiquid(paramAbonnementement.getAvancRembLiquid());
                             newAbonnementPlacement.setContratPlacement(paramAbonnementement.getContratPlacement());
                             newAbonnementPlacement.setCodTypAbpl(abonnementPlacement.getCodTypAbpl());
                             newAbonnementPlacement.setCodEtatAbpl("T");///*** Traitée
                             newAbonnementPlacement.setCodToprAbpl("L");  
                             if(paramAbonnementement.getAvancRembLiquid().getCodToprArl().equals(Constants.CODE_RESILIATION_PLAC)){
                                 // cas de la resiliation...
                                 abonnementPlacement.setCodToprAbpl("R");
                             }
                             
                             montInteretMoisLiq = mont;
                             newAbonnementPlacement= (AbonnementPlacement)insertAbonnementPlacementTrt.exec(newAbonnementPlacement);
                             paramAbonnementement.setAbonnementPlacement(newAbonnementPlacement);    
                             
                                                         
                             // mise à jour de l'abonnement ancien
                             abonnementPlacement.setCodEtatAbpl("N"); 
                             abonnementPlacement.setCodToprAbpl("L");
                             nbrJourAnnulee = nbrJourAnnulee + abonnementPlacement.getNumNbrjAbpl();

                             //montInteretsAnnulle = Math.rint(montInteretsAnnulle + abonnementPlacement.getMontAbplAbpl());
                             abonnementPlacement = (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);
                                
                         }else if (abonnementPlacement.getDatFinAbpl().after(paramAbonnementement.getDateLiquidationAnticipe())){                
                                // annuler les abonnementes dont la date est superieure à la date de liquidation                                    
                               abonnementPlacement.setCodEtatAbpl("N"); 
                               abonnementPlacement.setCodToprAbpl("L");
                                 if(paramAbonnementement.getAvancRembLiquid().getCodToprArl().equals(Constants.CODE_RESILIATION_PLAC)){
                                     // cas de la resiliation...
                                     abonnementPlacement.setCodToprAbpl("R");
                                 }
                               nbrJourAnnulee = nbrJourAnnulee + abonnementPlacement.getNumNbrjAbpl();
                               abonnementPlacement = (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);
                               
                             }
                      
                       } // fin for
                
                       // la différence d'intéret durant l'année précédente (se base sur le nombre de jours de l'année précédente : nbrJourAnnePrecedente)
                       montInteretCorrecAnneesPrec = (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnnePrecedente * paramAbonnementement.getContratPlacement().getNumTauiCpla()/ (nbrJourAnne + (paramAbonnementement.getContratPlacement().getNumNbrjCpla() * paramAbonnementement.getContratPlacement().getNumTauiCpla()))) -
                        (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnnePrecedente * paramAbonnementement.getNumTauiCpla()/(nbrJourAnne + (paramAbonnementement.getDureeReelPlc() * paramAbonnementement.getNumTauiCpla())));
                        
                       // la différence d'intéret durant l'année courante (se base sur le nombre de jours de l'année courante : nbrJourAnneCourante)
                        montInteretDiffAnneesCourante = 
                        (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnneCourante * paramAbonnementement.getContratPlacement().getNumTauiCpla()/(nbrJourAnne + (paramAbonnementement.getContratPlacement().getNumNbrjCpla() * paramAbonnementement.getContratPlacement().getNumTauiCpla()))) -
                        (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnneCourante * paramAbonnementement.getNumTauiCpla()/(nbrJourAnne + (paramAbonnementement.getDureeReelPlc() * paramAbonnementement.getNumTauiCpla())));
                       
                       // la somme des intérêts annulés... 
                        montInteretsAnnulle = 
                        (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnnulee * paramAbonnementement.getContratPlacement().getNumTauiCpla()/(nbrJourAnne + (paramAbonnementement.getContratPlacement().getNumNbrjCpla() * paramAbonnementement.getContratPlacement().getNumTauiCpla())));
                                  
                
                        paramAbonnementement.setMontDiffeInteretLiq(paramAbonnementement.getAvancRembLiquid().getMontInetArl());
                        paramAbonnementement.setMontInteretsAnnulle(Math.rint(montInteretsAnnulle));
                        paramAbonnementement.setMontInteretMoisLiq(montInteretMoisLiq);
                        paramAbonnementement.setMontInteretCorrecAnneesPrec(Math.rint(montInteretCorrecAnneesPrec));
                        paramAbonnementement.setMontInteretDiffAnneesCourante(Math.rint(montInteretDiffAnneesCourante));                        
                        
                     // fin PRECOMPTE
                   }else if(paramAbonnementement.getContratPlacement().getCodPintCpla().equals(Constants.PLACEMENT_POSTCOMPTE)){   
                       // traitement des cas POSTCOMPTE
                       // verifier les interets servis partiels pour ce placement : deux cas pour ce traitement : 
                       // 1:) si les interets partiels versés sont superieurs au montant reels des interets réels devant être servis, on traite l'opération 331 (ristourne d'interets)
                       // 2:) si les interets partiels versés sont inferieurs au montant reels des interets réels devant être servis, on traite l'opération 321 (versement d'interets)
                       
                       // calculer les interets servis en se référant à la table interets servis:
                       // calculer la dernière date de l'intéret servi
                       paramAbonnementement = calculerDateDernierInteretsServis(paramAbonnementement.getContratPlacement(),paramAbonnementement);
                       montInteretServis = paramAbonnementement.getMontInteretServis();
                       dateDernierIntServi = paramAbonnementement.getDateDernierIntServi();                      
                     
                                              
                        if(paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_RISTOURNE_INTERET_LIQUID_ANTICIPE)){
                            cas = "ristourne"; //Op 331 : on perçoit  
                        }else{
                            cas = "versement"; //Op 321 : on verse 
                        }
                             
                             
                             if(cas.equalsIgnoreCase("versement")){                                 
                                 montAVerserClt = paramAbonnementement.getMontInteretAVerserPercevoir();
                             }else  montAPercevoirClt = paramAbonnementement.getMontInteretAVerserPercevoir();
                             
                             
                             for (Iterator it = listAbonnement.getList().iterator();it.hasNext();) { 
                                 AbonnementPlacement abonnementPlacement = (AbonnementPlacement)it.next();
                                   int comp =abonnementPlacement.getDatFinAbpl().compareTo(paramAbonnementement.getDateLiquidationAnticipe());
                                
                                 if (abonnementPlacement.getDatFinAbpl().before(paramAbonnementement.getDateLiquidationAnticipe()) || comp ==0){                       
                                       // ajuster les abonnements inferieurs ou égale à la date de liquidation anticipée...
                                    abonnementPlacement.setCodEtatAbpl("J");                                                                     
                                    
                                    // calculer le montant d'abonnement avec le nouveau taux de liquidation
                                    mont= paramAbonnementement.getMontTotAbpl().doubleValue() * abonnementPlacement.getNumNbrjAbpl().doubleValue() * paramAbonnementement.getNumTauiCpla()/nbrJourAnne;                                   
                                    // calculer la somme des montants d'abonnements  avec le taux de liquidation jusqu'à la date de liquidation // montInteretAvdernLigne
                                    montInteretAvdernLigne = montInteretAvdernLigne + mont;
                                    
                                    //garnir le champs de réajustement montant dans cette table ()
                                    abonnementPlacement.setMontReajAbpl(Math.rint(mont));
                                    
                                     abonnementPlacement.setAvancRembLiquid(paramAbonnementement.getAvancRembLiquid());
                                     abonnementPlacement.setContratPlacement(paramAbonnementement.getContratPlacement());
                                     abonnementPlacement.setCodTypAbpl(paramAbonnementement.getAvancRembLiquid().getCodTypiArl());                              
                                     abonnementPlacement.setCodToprAbpl("L");
                                     if(paramAbonnementement.getAvancRembLiquid().getCodToprArl().equals(Constants.CODE_RESILIATION_PLAC)){
                                         // cas de la resiliation...
                                         abonnementPlacement.setCodToprAbpl("R");
                                      }                                      
                                      // mettre à jour la ligne d'abonnement
                                      abonnementPlacement= (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);
                                      
                                     
                                     if(anneeCourante > DateHandler.GetYearFromDate(abonnementPlacement.getDatCompAbpl())){
                                         // calculer le nombre de jour abonnée de l'annee précédente 
                                         nbrJourAnnePrecedente = nbrJourAnnePrecedente + abonnementPlacement.getNumNbrjAbpl();                                         
                                          
                                     }else if(anneeCourante == DateHandler.GetYearFromDate(abonnementPlacement.getDatCompAbpl())){
                                        // calculer le nombre de jour abonnés de l'exercice courant...
                                         nbrJourAnneCourante = nbrJourAnneCourante + abonnementPlacement.getNumNbrjAbpl();                                         
                                     }
                                     
                                     if(dateDernierIntServi != null){
                                         if(abonnementPlacement.getDatFinAbpl().after(dateDernierIntServi) && abonnementPlacement.getDatFinAbpl().before(paramAbonnementement.getDateLiquidationAnticipe())){
                                             // calculer le nombre de jours abonnées depuis la dernière date de versement d'interet partiel...
                                            nbrJourAnneAbApresVP = nbrJourAnneAbApresVP + abonnementPlacement.getNumNbrjAbpl();                                            
                                         }
                                     }else{
                                         // pas de versement d'interets servis...
                                         nbrJourAnneAbApresVP = nbrJourAnneAbApresVP + abonnementPlacement.getNumNbrjAbpl();                                                                                  
                                     }
                                     
                                                                          
                                 }else if(abonnementPlacement.getDatFinAbpl().after(paramAbonnementement.getDateLiquidationAnticipe()) && abonnementPlacement.getDatDebAbpl().before(paramAbonnementement.getDateLiquidationAnticipe())){
                                      // traiter la ligne de l'abonnement de la date de liquidation
                                     abonnementPlacement.setCodEtatAbpl("J"); 
                                     // insertion d'un nouvel abonnement 
                                     nbrJourMoisExtourne = abonnementPlacement.getNumNbrjAbpl();
                                     AbonnementPlacement newAbonnementPlacement  = new AbonnementPlacement();
                                     newAbonnementPlacement.setDatDebAbpl(abonnementPlacement.getDatDebAbpl());
                                     newAbonnementPlacement.setDatFinAbpl(paramAbonnementement.getDateLiquidationAnticipe());
                                     newAbonnementPlacement.setDatCompAbpl(getDateComptabilisation(newAbonnementPlacement.getDatFinAbpl()));
                                     newAbonnementPlacement.setDatValAbpl(CalanderHandler.GetNextWorkingDay(paramAbonnementement.getDateLiquidationAnticipe()));///??? J+1
                                     // ne pas ajouter + 1 à la duree pour penaliser le client...
                                     newAbonnementPlacement.setNumNbrjAbpl(Long.valueOf((Double.valueOf(Math.rint(DateHandler.getDaysBetween(newAbonnementPlacement.getDatDebAbpl(),newAbonnementPlacement.getDatFinAbpl())))).longValue()));
                                     
                                     if(dateDernierIntServi == null){
                                     //si pas de versmement d'interet partiel alors le montant du mois de liquidation est egal au montant d'interet total - la somme d'interet des abonnements qui précedent la ligne d'abonnement courante...
                                       mont =  paramAbonnementement.getInteretsReelPlc() - Math.rint(montInteretAvdernLigne);
                                     }else{
                                       //le montant d'interet du mois du liquidation est calculé sur la base du nombre de jours dans ce mois 
                                       
                                         mont=Math.rint(paramAbonnementement.getMontTotAbpl() * newAbonnementPlacement.getNumNbrjAbpl() * paramAbonnementement.getNumTauiCpla()/nbrJourAnne); 
                                     }
                                     newAbonnementPlacement.setMontReajAbpl(mont);
                                     newAbonnementPlacement.setMontAbplAbpl(mont);
                                     //montSreaAbpl = montSreaAbpl + newAbonnementPlacement.getMontAbplAbpl().doubleValue();
                                    // newAbonnementPlacement.setMontSintAbpl(montSintAbpl);
                                     
                                     newAbonnementPlacement.setAvancRembLiquid(paramAbonnementement.getAvancRembLiquid());
                                     newAbonnementPlacement.setContratPlacement(paramAbonnementement.getContratPlacement());
                                     newAbonnementPlacement.setCodTypAbpl(abonnementPlacement.getCodTypAbpl());
                                     newAbonnementPlacement.setCodEtatAbpl("T");///*** Traitée
                                     newAbonnementPlacement.setCodToprAbpl("L");   
                                     if(paramAbonnementement.getAvancRembLiquid().getCodToprArl().equals(Constants.CODE_RESILIATION_PLAC)){
                                         // cas de la resiliation...
                                         newAbonnementPlacement.setCodToprAbpl("R");
                                     }
                                     
                                     montInteretMoisLiq = mont;
                                     newAbonnementPlacement= (AbonnementPlacement)insertAbonnementPlacementTrt.exec(newAbonnementPlacement);
                                     paramAbonnementement.setAbonnementPlacement(newAbonnementPlacement);    
                                     
                                     // mise à jour de l'abonnement ancien
                                     abonnementPlacement.setCodEtatAbpl("N"); 
                                     abonnementPlacement.setCodToprAbpl("L");      
                                     if(paramAbonnementement.getAvancRembLiquid().getCodToprArl().equals(Constants.CODE_RESILIATION_PLAC)){
                                         // cas de la resiliation...
                                         abonnementPlacement.setCodToprAbpl("R");
                                     }
                                     abonnementPlacement = (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);
                                        
                                 }else if (abonnementPlacement.getDatFinAbpl().after(paramAbonnementement.getDateLiquidationAnticipe())){                
                                        // annuler les abonnementes dont la date est superieures à la date de liquidation                                    
                                       abonnementPlacement.setCodEtatAbpl("N"); 
                                       abonnementPlacement.setCodToprAbpl("L");  
                                       if(paramAbonnementement.getAvancRembLiquid().getCodToprArl().equals(Constants.CODE_RESILIATION_PLAC)){
                                             // cas de la resiliation...
                                          abonnementPlacement.setCodToprAbpl("R");
                                       }
                                       abonnementPlacement = (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);
                                       
                                     }
                              
                               } // fin for
                                    
                                    //la difference d'interet est calculé sur la base du taux d'interêt du placement (paramAbonnementement.getTauxInteretPlacement()/ taux d'interet de la liquidation (paramAbonnementement.getNumTauiCpla())
                                    
                                    // calculer le montant de correction des annees précedentes 
                                    montInteretCorrecAnneesPrec = 
                                    (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnnePrecedente * paramAbonnementement.getTauxInteretPlacement()/nbrJourAnne) -
                                    (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnnePrecedente * paramAbonnementement.getNumTauiCpla()/nbrJourAnne);
                                    
                                    // calculer le montant de correction de l'année courante 
                                    montInteretDiffAnneesCourante = 
                                    (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnneCourante * paramAbonnementement.getTauxInteretPlacement()/nbrJourAnne) -
                                    (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnneCourante * paramAbonnementement.getNumTauiCpla()/nbrJourAnne);
                                    
                                    // calculer le montant des intêrets de début du placement jusqu'au dernier mois avant le mois qui contient la date de liquuidation 
                                    // sinon depuis la date du dernier intêret partiel servi jusqu'au dernier mois avant le mois qui contient la date de liquuidation 
                                    montInteretAbonnPostCompte = 
                                    (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnneAbApresVP * paramAbonnementement.getTauxInteretPlacement()/nbrJourAnne);
                               
                                          
                                paramAbonnementement.setMontInteretMoisLiq(montInteretMoisLiq);
                                paramAbonnementement.setMontAverserClt(montAVerserClt);
                                paramAbonnementement.setMontAPercevoirClt(montAPercevoirClt);
                               // paramAbonnementement.setMontRetenu(montRetenu);
                                paramAbonnementement.setMontInteretAbonnPostCompte(Math.rint(montInteretAbonnPostCompte));
                                paramAbonnementement.setMontInteretCorrecAnneesPrec(Math.rint(montInteretCorrecAnneesPrec));
                                paramAbonnementement.setMontInteretDiffAnneesCourante(Math.rint(montInteretDiffAnneesCourante));                                
                                
                   }
                   
                    if(!paramAbonnementement.getTauxInteretPlacement().equals(paramAbonnementement.getNumTauiCpla())){
                      // le taux de placement est different du taux de la liquidation....
                        if(paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_VERSEMENT_INTERET_LIQUID_ANTICIPE) || paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_VERS_INTERET_SUITE_RESILIATION)){                      
                        // op 321  / 631
                          if(!nbrJourAnnePrecedente.equals(Double.valueOf("0"))){
                            paramAbonnementement.setMontInteretCorrecAnneesPrec(Math.abs(paramAbonnementement.getMontAverserClt()+  paramAbonnementement.getMontRetenu() +  paramAbonnementement.getMontInteretDiffAnneesCourante()  - paramAbonnementement.getMontInteretMoisLiq() - paramAbonnementement.getMontInteretAbonnPostCompte()));
                          }else  paramAbonnementement.setMontInteretDiffAnneesCourante(Math.abs(paramAbonnementement.getMontAverserClt()+  paramAbonnementement.getMontRetenu() - paramAbonnementement.getMontInteretMoisLiq() - paramAbonnementement.getMontInteretAbonnPostCompte()));
                          
                        }else if(paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_RISTOURNE_INTERET_LIQUID_ANTICIPE)){
                           // op 331
                            if(!nbrJourAnnePrecedente.equals(Double.valueOf("0"))){
                              paramAbonnementement.setMontInteretCorrecAnneesPrec(Math.abs(paramAbonnementement.getMontInteretDiffAnneesCourante()  -  paramAbonnementement.getMontAPercevoirClt() -   paramAbonnementement.getMontInteretMoisLiq()  -  paramAbonnementement.getMontInteretAbonnPostCompte()));
                            }else paramAbonnementement.setMontInteretDiffAnneesCourante(Math.abs(paramAbonnementement.getMontAPercevoirClt() +   paramAbonnementement.getMontInteretMoisLiq()+ paramAbonnementement.getMontInteretAbonnPostCompte()));

                        }else if(paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_RESTITUTION_INTERET_LIQUID_ANTICIPE) || paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_RISTOURNE_INTERET_SUITE_RESILIATION)){
                           // op 322  / 630
                            if(!nbrJourAnnePrecedente.equals(Double.valueOf("0"))){
                               paramAbonnementement.setMontInteretCorrecAnneesPrec(Math.abs(paramAbonnementement.getMontInteretDiffAnneesCourante() + paramAbonnementement.getMontInteretsAnnulle()  -  paramAbonnementement.getMontDiffeInteretLiq() -   paramAbonnementement.getMontInteretMoisLiq()));
                            }else paramAbonnementement.setMontInteretDiffAnneesCourante(Math.abs( paramAbonnementement.getMontDiffeInteretLiq() +  paramAbonnementement.getMontInteretMoisLiq() - paramAbonnementement.getMontInteretsAnnulle() ));
                        }
                    }else{
                        // le taux de la liquidation est egal au taux du placement...
                         
                         if(paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_VERSEMENT_INTERET_LIQUID_ANTICIPE) || paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_VERS_INTERET_SUITE_RESILIATION)){                      
                         // op 321  / 631
                           paramAbonnementement.setMontInteretAbonnPostCompte(Math.abs(paramAbonnementement.getMontAverserClt()+  paramAbonnementement.getMontRetenu()  - paramAbonnementement.getMontInteretMoisLiq()));
                           
                        // 331 cas ne peux pas exister 
                        
                         }else if(paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_RESTITUTION_INTERET_LIQUID_ANTICIPE) || paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_RISTOURNE_INTERET_SUITE_RESILIATION)){
                            // op 322  / 630
                             paramAbonnementement.setMontInteretsAnnulle(Math.abs( paramAbonnementement.getMontDiffeInteretLiq() +   paramAbonnementement.getMontInteretMoisLiq()));                         
                         }                        
                        
                    }
                   
                   // insertion dans la table ReajustAvrembliq 
                    ReajustAvrembliq reajustAvrembliq = new ReajustAvrembliq();
                    
                    reajustAvrembliq.setMontIdifReaj(paramAbonnementement.getMontDiffeInteretLiq());
                    reajustAvrembliq.setMontIannReaj(paramAbonnementement.getMontInteretsAnnulle());
                    
                    reajustAvrembliq.setAvancRembLiquid(paramAbonnementement.getAvancRembLiquid());
                    reajustAvrembliq.setMontImoiReaj(paramAbonnementement.getMontInteretMoisLiq());
                    reajustAvrembliq.setMontVersReaj(paramAbonnementement.getMontAverserClt());
                    reajustAvrembliq.setMontPercReaj(paramAbonnementement.getMontAPercevoirClt());
                    reajustAvrembliq.setMontIrcReaj(paramAbonnementement.getMontRetenu());
                    reajustAvrembliq.setMontCaprReaj(paramAbonnementement.getMontInteretCorrecAnneesPrec());
                    reajustAvrembliq.setMontDexcReaj(paramAbonnementement.getMontInteretDiffAnneesCourante());
                    reajustAvrembliq.setMontIvprReaj(paramAbonnementement.getMontInteretAbonnPostCompte());
                    
                                        
                    InsertReajustAvRembLiqTrt insertReajustAvRembLiqTrt =new InsertReajustAvRembLiqTrt();
                    reajustAvrembliq = (ReajustAvrembliq)insertReajustAvRembLiqTrt.exec(reajustAvrembliq);                    
                   
                }else{ 
                    // si pas d'abbonnement de souscription
                    //throw new SmilePlacementException("Les abonnements de la souscription placement ne sont pas garnies au niveau de la base de données; Veuillez contacter l'administrateur du système... ")    ;                           
                     paramAbonnementement.setMontDiffeInteretLiq(Double.valueOf("0"));
                     paramAbonnementement.setMontInteretsAnnulle(Double.valueOf("0"));
                     paramAbonnementement.setMontInteretMoisLiq(Double.valueOf("0"));
                     paramAbonnementement.setMontAverserClt(Double.valueOf("0"));
                     paramAbonnementement.setMontAPercevoirClt(Double.valueOf("0"));
                     paramAbonnementement.setMontRetenu(Double.valueOf("0"));
                     paramAbonnementement.setMontInteretCorrecAnneesPrec(Double.valueOf("0"));
                     paramAbonnementement.setMontInteretDiffAnneesCourante(Double.valueOf("0"));
                     paramAbonnementement.setMontInteretAbonnPostCompte(Double.valueOf("0"));
                }
                this.setCroFlag(true); 
                // generation du CRO du l'abonnement....
                //en cas de liquidation sous bonne date valeur, verifier s'il ya un chevauchement de mois entre la date comptable et la date valeur 
                //de liquidation : generer le CRo de l'extourne du mois précedent : Opération 619...
                 if(paramAbonnementement.getAvancRembLiquid().getCodSbdvArl().equals("1")){
                 String moisCompt= DateHandler.dateToStr(paramAbonnementement.getDateCompAgence()).substring(3,5);
                 String moisValeur=DateHandler.dateToStr(paramAbonnementement.getDateLiquidationAnticipe()).substring(3,5);
                 if(Long.valueOf(moisValeur).intValue() != Long.valueOf(moisCompt).intValue()){
                     ParamLiquidation paramLiquidation = new ParamLiquidation();                     
                     paramLiquidation.setAvancRembLiquid(paramAbonnementement.getAvancRembLiquid());
                     paramLiquidation.setDateComptLiquidation(paramAbonnementement.getAvancRembLiquid().getDatArlArl());
                     montExtourne=Math.rint(paramAbonnementement.getMontTotAbpl() * nbrJourMoisExtourne *  paramAbonnementement.getTauxInteretPlacement()/nbrJourAnne); 
                     paramLiquidation.setMntExtourne(montExtourne);
                     ExtourneAbonnInteretServiLiqTrt extourneAbonnInteretServiLiqTrt = new ExtourneAbonnInteretServiLiqTrt();
                     paramLiquidation = (ParamLiquidation)extourneAbonnInteretServiLiqTrt.exec(paramLiquidation);  
                                  
                 }
                 }
            
        return (paramAbonnementement);
        }    
       
        
        catch (Exception e) {       
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur ReajusterAbonnementTrt ");
                text.append(e.toString());
                erreur.setCode("400");
                erreur.setDescription(text.toString());
                erreur.setKey("GenererAbonnementTrt");
                paramAbonnementement.addError(erreur);
                logger.error(" *** Erreur lors de ReajusterAbonnementTrt" /*concernant l'agence "+avancRembLiquid.get().getCodStrcMand()*/+" : ", e);
                throw new RuntimeException(e);
            }
    }
    
    public Date getLastDayOfMonth(Date date){
    
    Date d=new Date();
    int year = date.getYear()+1900;
    int month = date.getMonth()+1;
    d.setYear(date.getYear());
    d.setMonth(date.getMonth());
    d.setHours(date.getHours());
    
        if (month == 4 || month == 6 || month == 9 || month == 11)  
        {
            d.setDate(30);
        }
        else {
            if (month == 2){
                if ((year % 4 == 0) && ((year % 100 != 0) ||  (year % 400 == 0))){
                    d.setDate(29);
                }
                else  d.setDate(28);
            }
            else d.setDate(31);
        }
        return d;
    }
    public  Date getDateComptabilisation(Date d)  {

        try{
         Date DateReturn=d;
          while(CalanderHandler.isJourFerier(d)){
            DateReturn = DateHandler.addJour(d,-1);
            d = DateHandler.addJour(d,-1);
            System.out.print("-d- *** "+d);
          }
            return(DateReturn);
        
        }catch(Exception e){
            logger.error(" Erreur dans GetDateComptable.execute : " , e);
            return (d);
        }
    }    

    public void genCroText(ValueObject vo) {
                ParamAbonnementement paramAbonnementement = (ParamAbonnementement)vo;
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

               Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        com.oxia.security.abc.model.Personnel user = null;
                        if (obj instanceof UserDetails) {
                            user = (com.oxia.security.abc.model.Personnel)obj;
                       }
              
              this.setNumRefCro(Long.valueOf((paramAbonnementement.getAvancRembLiquid().getNumSeqArl())));              
              this.setLibRefCro("SMILE.PLC.ABON.LIQ");
              if(paramAbonnementement.getAvancRembLiquid().getCodToprArl().equals(Constants.CODE_RESILIATION_PLAC)){
                  this.setLibRefCro("SMILE.PLC.ABON.RES");
              }
              
              this.setDatValCro(paramAbonnementement.getDateValeurLiquidation());
              this.setCodeStructInitiatrice(user.getStructure().getCodStrcStrc().toString());              
              this.setCodStrcImpt(user.getStructure().getCodStrcStrc());
              this.setCodEtatCro(0);              
              this.setCodeProduit(paramAbonnementement.getContratPlacement().getProduitPlacement().getCodPrdPlc().toString());
               
              this.setOperationId(String.valueOf(paramAbonnementement.getCodeOperation()));                     
              
              this.setDateOperation(paramAbonnementement.getDateCompAgence());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);
              this.setTypeOperationCro("O");
              this.setCodTachTach(1);
              
              this.setCodRefcOmp(paramAbonnementement.getAvancRembLiquid().getNumSeqArl().toString());
              this.setDatExecCro(new Date());

              this.setNumCinUser(user.getNumMatrUser());
              this.setCodTypUser(user.getMatriculeTyp());
              
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
              StringBuffer cro=new StringBuffer("");
              
              cro.append("numCptBna=");
              cro.append(StrHandler.lpad(paramAbonnementement.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3)+StrHandler.lpad(paramAbonnementement.getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4)+StrHandler.lpad(paramAbonnementement.getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6)+";");               
                    
               
              cro.append("ABONNEMENT_PLACEMENT.NUM_SEQ_CPLA=");
              cro.append(paramAbonnementement.getContratPlacement().getNumSeqCpla() +";");
              
                            
              if(paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_RESTITUTION_INTERET_LIQUID_ANTICIPE)  
                  || paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_RISTOURNE_INTERET_SUITE_RESILIATION)){
              // 322  // 630
                  cro.append("REAJUST_AVREMBLIQ.MONT_IDIF_REAJ=");
                  cro.append(Math.round(paramAbonnementement.getMontDiffeInteretLiq()) +";");              
            
                  cro.append("REAJUST_AVREMBLIQ.MONT_IANN_REAJ=");
                  cro.append(Math.round(paramAbonnementement.getMontInteretsAnnulle()) +";");
                  
                  cro.append("REAJUST_AVREMBLIQ.MONT_IFRC_REAJ="); // fraction du mois
                  cro.append(Math.round(paramAbonnementement.getMontInteretMoisLiq()) +";");   
              
              }else { 
                   if(paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_VERSEMENT_INTERET_LIQUID_ANTICIPE)
                      || paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_VERS_INTERET_SUITE_RESILIATION)){
                     //321   // 631
                       cro.append("REAJUST_AVREMBLIQ.MONT_VERS_REAJ=");
                       cro.append(Math.round(paramAbonnementement.getMontAverserClt()) +";");              
                   }else if(paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_RISTOURNE_INTERET_LIQUID_ANTICIPE)){
                       //331
                        cro.append("REAJUST_AVREMBLIQ.MONT_PERC_REAJ=");
                        cro.append(Math.round(paramAbonnementement.getMontAPercevoirClt()) +";");                             
                   }
                   
                   
                  cro.append("REAJUST_AVREMBLIQ.MONT_IRC_REAJ=");
                  cro.append(Math.round(paramAbonnementement.getMontRetenu()) +";");
                   
                   
                  cro.append("REAJUST_AVREMBLIQ.MONT_IMOI_REAJ=");
                  cro.append(Math.round(paramAbonnementement.getMontInteretMoisLiq()) +";");              
                  
                  cro.append("REAJUST_AVREMBLIQ.MONT_IVPR_REAJ=");
                  cro.append(Math.round(paramAbonnementement.getMontInteretAbonnPostCompte()) +";");    
                                   
              }      
                  
              cro.append("REAJUST_AVREMBLIQ.MONT_CAPR_REAJ=");
              cro.append(Math.round(paramAbonnementement.getMontInteretCorrecAnneesPrec()) +";");
                  // reste à calculer le montant 
                  
              cro.append("REAJUST_AVREMBLIQ.MONT_DEXC_REAJ=");
              cro.append(Math.round(paramAbonnementement.getMontInteretDiffAnneesCourante()) +";");
             
            
             
            this.setCroText(cro.toString());
            }  
         
        
   
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);
    }
    
    public void calculerMontantInteretsServis(ContratPlacement comptePlc, Double montIntertsServis, Date dateDernierInteretServi){
        
        GetListInteretServiCmd getListInteretServiCmd = new GetListInteretServiCmd();
        Listes listInt = (Listes)getListInteretServiCmd.execute(comptePlc);
        montIntertsServis = Double.valueOf(0);        
        if(listInt != null &&  listInt.getList() != null &&  listInt.getList().size()>0){
                for (Iterator it = listInt.getList().iterator(); it.hasNext(); ) {
                        InteretServi interetServi = (InteretServi)it.next();                    
                       
                        if(dateDernierInteretServi != null){
                          if(interetServi.getDatIsrvIsrv().after(dateDernierInteretServi))
                              dateDernierInteretServi = interetServi.getDatIsrvIsrv();
                        }else dateDernierInteretServi = interetServi.getDatIsrvIsrv();
                        
                        montIntertsServis =Math.abs(interetServi.getMontIsrvIsrv().doubleValue()) + montIntertsServis;                               
                } 
        }
    }
    
    
    public  ParamAbonnementement calculerMontantInteretsServisAbonn(Listes listAbonnement,ParamAbonnementement paramAbonnementement){        
       
        
       
        Date dateDernierInteretS = null;
        if(listAbonnement.getList() != null &&  listAbonnement.getList() != null &&  listAbonnement.getList().size()>0){
                for (Iterator it = listAbonnement.getList().iterator(); it.hasNext(); ) {
                    AbonnementPlacement abonnementPlacement = (AbonnementPlacement)it.next();              
                     if(abonnementPlacement.getCodPartAbpl() != null &&  abonnementPlacement.getCodPartAbpl().equalsIgnoreCase("P") && abonnementPlacement.getCodEtatAbpl().equalsIgnoreCase("T")) { 
                        if(dateDernierInteretS != null){
                          if(abonnementPlacement.getDatFinAbpl().after(dateDernierInteretS))
                              dateDernierInteretS = abonnementPlacement.getDatFinAbpl();
                        }else dateDernierInteretS = abonnementPlacement.getDatFinAbpl();
                        
                        //montIntertsS =Math.abs(abonnementPlacement.getMontSintAbpl().doubleValue()) + montIntertsS; 
                     }
                } 
        }
        paramAbonnementement.setDateDernierIntServi(dateDernierInteretS);
        //paramAbonnementement.setMontInteretServis(montIntertsS);
        return paramAbonnementement;
        
    }
    
    
    public ParamAbonnementement calculerDateDernierInteretsServis(ContratPlacement comptePlc,ParamAbonnementement paramAbonnementement){
        
        GetListInteretServiCmd getListInteretServiCmd = new GetListInteretServiCmd();
        Listes listInt = (Listes)getListInteretServiCmd.execute(comptePlc);
        Date dateDernierInteretS = null;
        if(listInt != null &&  listInt.getList() != null &&  listInt.getList().size()>0){
                for (Iterator it = listInt.getList().iterator(); it.hasNext(); ) {
                        InteretServi interetServi = (InteretServi)it.next();                    
                        if(interetServi.getCodTypIsrv() != null &&  interetServi.getCodTypIsrv().equalsIgnoreCase("P")){
                            if(dateDernierInteretS != null){
                              if(interetServi.getDatIsrvIsrv().after(dateDernierInteretS))
                                  dateDernierInteretS = interetServi.getDatIsrvIsrv();
                            }else dateDernierInteretS = interetServi.getDatIsrvIsrv();
                        }                                                    
                } 
        }
        paramAbonnementement.setDateDernierIntServi(dateDernierInteretS);
        return paramAbonnementement;
    }
    
}
