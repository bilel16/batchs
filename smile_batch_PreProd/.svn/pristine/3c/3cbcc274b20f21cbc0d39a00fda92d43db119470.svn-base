package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.ReajustAvrembliq;
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
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;


public class ReajusterAbonnementLiquidationPartielleTrt extends Traitement{
    
    public ReajusterAbonnementLiquidationPartielleTrt() {
    }
    
    /**
     * methode qui réajuste et genere le tableau des abonnements suite à la liquidation anticipé Partielle  d'un placement donné 
     * @param vo : ParamAbonnementement
     * @return   : ParamAbonnementement
     * @autor    :  EL ARBI HASSINE   
     * @date     : 12/08/2009
     */
    
    public IValueObject perform(IValueObject vo) throws  SmilePlacementException
 {
        Context context = ContextHandler.getContext();
        //ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");

        ParamAbonnementement paramAbonnementement = (ParamAbonnementement)vo;

       
        try{
               
               // recherche et annulation des abonnements des intérets...
                GetListAbonnementsInteretsTrt getListAbonnementsInteretsTrt = new GetListAbonnementsInteretsTrt();
                UpdateAbonnementPlacementTrt updateAbonnementPlacementTrt = new UpdateAbonnementPlacementTrt();
                Listes listAbonnement = new Listes() ;
                paramAbonnementement.setTypeOperation("S"); // parametre pour extraire les abonnements de la souscription
                listAbonnement = (Listes)getListAbonnementsInteretsTrt.exec(paramAbonnementement);
                
                Double nbrJourAnne = Double.valueOf("0"); 
                
                Double nbrJourAnnePrecedente = Double.valueOf("0"); 
                Double nbrJourAnneCourante = Double.valueOf("0"); 
                Double nbrJourAnneAbApresVP = Double.valueOf("0"); 
                Long nbrJourMoisExtourne  = Long.valueOf("0");               
                Double montInteretsAnnulle = Double.valueOf("0"); 
                Double montInteretMoisLiq  = Double.valueOf("0"); 
                Double montInteretCorrecAnneesPrec = Double.valueOf("0"); 
                Double montInteretDiffAnneesCourante = Double.valueOf("0"); 
                Double montInteretServis = Double.valueOf("0"); 
                Double montInteretAbonnPostCompte = Double.valueOf("0"); 
                Double montAVerserClt = Double.valueOf("0"); 
                Double montAPercevoirClt = Double.valueOf("0");                 
                Double montExtourne = Double.valueOf("0"); 
                Double montRetenu = Double.valueOf("0"); 
                montRetenu = paramAbonnementement.getMontRetenu();
                Date dateDernierIntServi = null;
                          
                String dateCalculBnaPlac ="30/06/2020";
                if(paramAbonnementement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)){
                	//Date de modification de la base de calcul pour le produit BNA placement " de 360 à 365 jours " 30/06/2020
                    if(paramAbonnementement.getContratPlacement().getDatValCpla().after(DateHandler.strToDate(dateCalculBnaPlac))){
                    	nbrJourAnne  = Constants.NBR_JOURS_BC_CAT.doubleValue();
                    }else if(paramAbonnementement.getContratPlacement().getDatValCpla().compareTo(DateHandler.strToDate(dateCalculBnaPlac))==0){
                    	nbrJourAnne  = Constants.NBR_JOURS_BC_CAT.doubleValue();
                    }else if(paramAbonnementement.getContratPlacement().getDatValCpla().before(DateHandler.strToDate(dateCalculBnaPlac))){
                        // Garder la base de calcul de 360 jours pour les anciens contrat BNA Placement
                    	nbrJourAnne  = Constants.NBR_JOURS_BNAPLC.doubleValue();      
                    }   
                }else {
                    nbrJourAnne  = Constants.NBR_JOURS_BC_CAT.doubleValue();
                }              
                
                Double montSreaAbpl = Double.valueOf("0");
                Double mont = Double.valueOf("0");  
                String cas = "";
                Double montPartiel = Double.valueOf("0");  
                
                
                InsertAbonnementPlacementTrt insertAbonnementPlacementTrt =new InsertAbonnementPlacementTrt();
                
                // determiner l'année de l'exercice courant : 
                int anneeCourante = DateHandler.GetYearFromDate(paramAbonnementement.getDateLiquidationAnticipe());
                
                
                   if(listAbonnement.getList() != null && listAbonnement.getList().size()> 0){
                
                 if(paramAbonnementement.getContratPlacement().getCodPintCpla().equals(Constants.PLACEMENT_POSTCOMPTE)){   
                       // traitement des cas POSTCOMPTE
                       // verifier les interets servis partiels pour ce placement : deux cas pour ce traitement : 
                       // 1:) si les interets partiels versés sont superieurs au montant reels des interets réels devant être servis, on traite l'opération 331 (ristourne d'interets)
                       // 2:) si les interets partiels versés sont inferieurs au montant reels des interets réels devant être servis, on traite l'opération 321 (versement d'interets)
                       
                       // calculer les interets servis en se référant à la table interets servis : 
                       //calculerMontantInteretsServis(paramAbonnementement.getContratPlacement(),montInteretServis,dateDernierIntServi);
                       // calculer les interets servis en se référant à la table Abonnement: 
                       paramAbonnementement = calculerDateDernierInteretsServis(paramAbonnementement.getContratPlacement(),paramAbonnementement);
                       
                       montInteretServis = paramAbonnementement.getMontInteretServis();
                      // montInteretServiAuProratas = (paramAbonnementement.getAvancRembLiquid().getMontArlArl() * paramAbonnementement.getContratPlacement().getNumTauiCpla() * )/
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
                                 
                                 if (abonnementPlacement.getDatFinAbpl().before(paramAbonnementement.getDateLiquidationAnticipe()) || comp ==0 ){                       
                                       // ajuster les abonnements inferieurs à la date de liquidation anticipée...
                                    abonnementPlacement.setCodEtatAbpl("J");
                                        // insertion des nouveaux abonnements avec la date reele de placement et le nouveau taux pénalisé
                                                                        
                                     mont=(paramAbonnementement.getMontTotAbpl().doubleValue() * abonnementPlacement.getNumNbrjAbpl().doubleValue() * paramAbonnementement.getNumTauiCpla()/nbrJourAnne);
                                     montPartiel = (paramAbonnementement.getMontTotAbpl().doubleValue() * abonnementPlacement.getNumNbrjAbpl().doubleValue() * paramAbonnementement.getTauxInteretPlacement()/nbrJourAnne);
                                   
                                     
                                     abonnementPlacement.setMontReajAbpl(mont);
                                     abonnementPlacement.setMontPartAbpl(montPartiel);
                                     
                                     abonnementPlacement.setAvancRembLiquid(paramAbonnementement.getAvancRembLiquid());
                                     abonnementPlacement.setContratPlacement(paramAbonnementement.getContratPlacement());
                                     abonnementPlacement.setCodTypAbpl(paramAbonnementement.getAvancRembLiquid().getCodTypiArl());                              
                                     abonnementPlacement.setCodToprAbpl("P");                                                           
                                      
                                      abonnementPlacement= (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);
                                      
                                     
                                     if(anneeCourante > DateHandler.GetYearFromDate(abonnementPlacement.getDatCompAbpl())){
                                         // calculer la difference d'interet abonnés entre la date de souscription jusqu à la date fin du dernier exercice precedent...
                                          nbrJourAnnePrecedente = nbrJourAnnePrecedente + abonnementPlacement.getNumNbrjAbpl();
                                                                                 
                                     }else if(anneeCourante == DateHandler.GetYearFromDate(abonnementPlacement.getDatCompAbpl())){
                                        //calculer la difference d'interet abonnés de l'exercice courant...
                                         nbrJourAnneCourante = nbrJourAnneCourante + abonnementPlacement.getNumNbrjAbpl();
                                                                                  
                                     }
                                     
                                     if(dateDernierIntServi != null){
                                         if(abonnementPlacement.getDatFinAbpl().after(dateDernierIntServi) && abonnementPlacement.getDatFinAbpl().before(paramAbonnementement.getDateLiquidationAnticipe())){
                                             // calculer le montant des interets abonnés depuis la dernière date de versement d'interet partiel...
                                              nbrJourAnneAbApresVP = nbrJourAnneAbApresVP + abonnementPlacement.getNumNbrjAbpl();
                                                                                     
                                         }
                                     }else{
                                         // pas encore de versement d'interets servis...
                                         nbrJourAnneAbApresVP = nbrJourAnneAbApresVP + abonnementPlacement.getNumNbrjAbpl();
                                    
                                       }                                     
                                    
                                     
                                 }else if(abonnementPlacement.getDatFinAbpl().after(paramAbonnementement.getDateLiquidationAnticipe()) && abonnementPlacement.getDatDebAbpl().before(paramAbonnementement.getDateLiquidationAnticipe())){
                                      // traiter la ligne de l'abonnement de la date de liquidation
                                     nbrJourMoisExtourne = abonnementPlacement.getNumNbrjAbpl();
                                     abonnementPlacement.setCodEtatAbpl("J"); 
                                     // insertion de l'abonnement 
                                     AbonnementPlacement newAbonnementPlacement  = new AbonnementPlacement();
                                     newAbonnementPlacement.setDatDebAbpl(abonnementPlacement.getDatDebAbpl());
                                     newAbonnementPlacement.setDatFinAbpl(paramAbonnementement.getDateLiquidationAnticipe());
                                     newAbonnementPlacement.setDatCompAbpl(getDateComptabilisation(newAbonnementPlacement.getDatFinAbpl()));
                                     newAbonnementPlacement.setDatValAbpl(CalanderHandler.GetNextWorkingDay(paramAbonnementement.getDateLiquidationAnticipe()));///??? J+1
                                     // ne pas ajouter + 1 à la duree pour penaliser le client...
                                     newAbonnementPlacement.setNumNbrjAbpl(Long.valueOf((Double.valueOf(Math.rint(DateHandler.getDaysBetween(newAbonnementPlacement.getDatDebAbpl(),newAbonnementPlacement.getDatFinAbpl())))).longValue()));
                                     
                                     mont=(paramAbonnementement.getMontTotAbpl().doubleValue() * newAbonnementPlacement.getNumNbrjAbpl().doubleValue() * paramAbonnementement.getNumTauiCpla()/nbrJourAnne);
                                     montPartiel = (paramAbonnementement.getMontTotAbpl().doubleValue() * newAbonnementPlacement.getNumNbrjAbpl().doubleValue() * paramAbonnementement.getTauxInteretPlacement()/nbrJourAnne);
                                     newAbonnementPlacement.setMontReajAbpl(mont);
                                     newAbonnementPlacement.setMontPartAbpl(montPartiel);
                                     newAbonnementPlacement.setMontAbplAbpl(mont);
                                     //montSreaAbpl = montSreaAbpl + newAbonnementPlacement.getMontAbplAbpl().doubleValue();
                                     // newAbonnementPlacement.setMontSintAbpl(montSintAbpl);
                                     
                                     newAbonnementPlacement.setAvancRembLiquid(paramAbonnementement.getAvancRembLiquid());
                                     newAbonnementPlacement.setContratPlacement(paramAbonnementement.getContratPlacement());
                                     newAbonnementPlacement.setCodTypAbpl(abonnementPlacement.getCodTypAbpl());
                                     newAbonnementPlacement.setCodEtatAbpl("T");///*** Traitée
                                     newAbonnementPlacement.setCodToprAbpl("P");       /// P : liquidation partielle...                       
                                     
                                     montInteretMoisLiq = mont;
                                     //newAbonnementPlacement= (AbonnementPlacement)insertAbonnementPlacementTrt.exec(newAbonnementPlacement);
                                     //paramAbonnementement.setAbonnementPlacement(newAbonnementPlacement);    
                                     
                                     // mise à jour de l'abonnement ancien
                                     abonnementPlacement.setCodEtatAbpl("N"); 
                                     abonnementPlacement.setCodToprAbpl("P");                                     
                                     abonnementPlacement = (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);
                                        
                                 }else if (abonnementPlacement.getDatFinAbpl().equals(paramAbonnementement.getDateLiquidationAnticipe())){                
                                      // la liquidation coincide avec la date fin de l'abonnement :
                                      
                                     }
                              
                               } // fin for
                               
                               
                                montInteretCorrecAnneesPrec = 
                                    (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnnePrecedente * paramAbonnementement.getTauxInteretPlacement()/nbrJourAnne) -
                                    (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnnePrecedente * paramAbonnementement.getNumTauiCpla()/nbrJourAnne);
                                    
                                montInteretDiffAnneesCourante = 
                                    (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnneCourante * paramAbonnementement.getTauxInteretPlacement()/nbrJourAnne) -
                                    (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnneCourante * paramAbonnementement.getNumTauiCpla()/nbrJourAnne);
    
                                montInteretAbonnPostCompte = 
                                    (paramAbonnementement.getMontTotAbpl().doubleValue() * nbrJourAnneAbApresVP * paramAbonnementement.getTauxInteretPlacement()/nbrJourAnne);
                                                                
                                paramAbonnementement.setMontInteretMoisLiq(Math.rint(montInteretMoisLiq));
                                paramAbonnementement.setMontAverserClt(montAVerserClt);
                                paramAbonnementement.setMontAPercevoirClt(montAPercevoirClt);
                               // paramAbonnementement.setMontRetenu(montRetenu);
                                paramAbonnementement.setMontInteretAbonnPostCompte(Math.rint(montInteretAbonnPostCompte));
                                paramAbonnementement.setMontInteretCorrecAnneesPrec(Math.rint(montInteretCorrecAnneesPrec));
                                paramAbonnementement.setMontInteretDiffAnneesCourante(Math.rint(montInteretDiffAnneesCourante)); 
                                
                               
                          if(!paramAbonnementement.getTauxInteretPlacement().equals(paramAbonnementement.getNumTauiCpla())){
                              // le taux de placement est different du taux de la liquidation....
                               if(cas.equals("versement")){                               
                               // op 321
                                if(!nbrJourAnnePrecedente.equals(Double.valueOf("0"))){
                                  paramAbonnementement.setMontInteretCorrecAnneesPrec(Math.abs(paramAbonnementement.getMontAverserClt()+  paramAbonnementement.getMontRetenu() +  paramAbonnementement.getMontInteretDiffAnneesCourante()  - paramAbonnementement.getMontInteretMoisLiq() - paramAbonnementement.getMontInteretAbonnPostCompte()));
                                }else  paramAbonnementement.setMontInteretDiffAnneesCourante(Math.abs(paramAbonnementement.getMontAverserClt()+  paramAbonnementement.getMontRetenu() - paramAbonnementement.getMontInteretMoisLiq() - paramAbonnementement.getMontInteretAbonnPostCompte()));
                                
                               }else if(cas.equals("ristourne")){ 
                                  // op 331
                                   if(!nbrJourAnnePrecedente.equals(Double.valueOf("0"))){
                                     paramAbonnementement.setMontInteretCorrecAnneesPrec(Math.abs(paramAbonnementement.getMontInteretDiffAnneesCourante()  -  paramAbonnementement.getMontAPercevoirClt() -   paramAbonnementement.getMontInteretMoisLiq()  -  paramAbonnementement.getMontInteretAbonnPostCompte()));
                                   }else paramAbonnementement.setMontInteretDiffAnneesCourante(Math.abs(paramAbonnementement.getMontAPercevoirClt() +   paramAbonnementement.getMontInteretMoisLiq()+ paramAbonnementement.getMontInteretAbonnPostCompte()));

                               }
                          }else{
                              // le taux de la liquidation est egal au taux du placement...
                               if(cas.equals("versement")){  
                                   paramAbonnementement.setMontInteretAbonnPostCompte(Math.abs(paramAbonnementement.getMontAverserClt()+  paramAbonnementement.getMontRetenu()  - paramAbonnementement.getMontInteretMoisLiq()));                               
                               }
                               
                              // le cas 331 n'exsite pas ( ristourne)                              
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
                
                
                  // supprimer les abonnements de ce contrat placement et générer les nouveaux abonnement avec le nouveau capital                  
                   DeleteAbonnementTrt deleteAbonnementTrt = new DeleteAbonnementTrt();
                   for (Iterator it = listAbonnement.getList().iterator();it.hasNext();) { 
                       AbonnementPlacement abonnementPlacement = (AbonnementPlacement)it.next();
                       abonnementPlacement = (AbonnementPlacement)deleteAbonnementTrt.exec(abonnementPlacement);
                   }                       
                  
                  ParamAbonnementement paramAbonnementementNew = new ParamAbonnementement();
                  paramAbonnementementNew.setDatDebAbpl(paramAbonnementement.getContratPlacement().getDatValCpla());
                  paramAbonnementementNew.setDatFinAbpl(paramAbonnementement.getContratPlacement().getDatEcheCpla());
                  paramAbonnementementNew.setTypeOperation("S");///*** S:souscription, A:avance
                  
                  paramAbonnementementNew.setNumSeqCpla(paramAbonnementement.getContratPlacement().getNumSeqCpla());
                  paramAbonnementementNew.setTypeInteret(paramAbonnementement.getContratPlacement().getCodFavCpla()); ///*** I:indexé
                  paramAbonnementementNew.setMontTotAbpl(paramAbonnementement.getContratPlacement().getMontActuCpla()); ///*** montant placement
                  paramAbonnementementNew.setNumTauiCpla(paramAbonnementement.getTauxInteretPlacement());
                  Double montInteretTotal = Math.rint(paramAbonnementement.getContratPlacement().getMontActuCpla() * paramAbonnementement.getContratPlacement().getNumNbrjCpla() * paramAbonnementement.getContratPlacement().getNumTauiCpla()/nbrJourAnne);
                  paramAbonnementementNew.setMontItotAbpl(montInteretTotal.longValue());
                  paramAbonnementementNew.setContratPlacement(paramAbonnementement.getContratPlacement());
                  
                  
                  GenererAbonnementTrt genererAbonnementTrt = new GenererAbonnementTrt(); 
                  genererAbonnementTrt.exec(paramAbonnementementNew);
                  
                  //mettre à jour le nouveau tableau d'abonnement ( mettre l'etat à traité pour les abonnement déja passé...);                   
                  listAbonnement = (Listes)getListAbonnementsInteretsTrt.exec(paramAbonnementementNew);
                  for (Iterator it = listAbonnement.getList().iterator();it.hasNext();) { 
                        AbonnementPlacement abonnementPlacement = (AbonnementPlacement)it.next();
                      if (abonnementPlacement.getDatFinAbpl().before(paramAbonnementement.getDateLiquidationAnticipe())){  
                      // etat = 'T'
                       abonnementPlacement.setCodEtatAbpl("T");
                       abonnementPlacement= (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);
                      }
                  }                       
                  
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
              
              this.setNumRefCro(Long.valueOf(paramAbonnementement.getAvancRembLiquid().getNumSeqArl()));              
              this.setLibRefCro("SMILE.PLC.ABON.LIQ");
              
              
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
              
                            
              if(paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_RESTITUTION_INTERET_LIQUID_ANTICIPE)){
              // 322
                  cro.append("REAJUST_AVREMBLIQ.MONT_IDIF_REAJ=");
                  cro.append(Math.round(paramAbonnementement.getMontDiffeInteretLiq()) +";");              
            
                  cro.append("REAJUST_AVREMBLIQ.MONT_IANN_REAJ=");
                  cro.append(Math.round(paramAbonnementement.getMontInteretsAnnulle()) +";");
                  
                  cro.append("REAJUST_AVREMBLIQ.MONT_IFRC_REAJ="); // fraction du mois
                  cro.append(Math.round(paramAbonnementement.getMontInteretMoisLiq()) +";");   
              
              }else { 
                   if(paramAbonnementement.getCodeOperation().equals(Constants.COD_OPER_VERSEMENT_INTERET_LIQUID_ANTICIPE)){
                  //321
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
