package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.GregorianCalendar;

public class GenererAbonnementTrt extends Traitement{
    public GenererAbonnementTrt() {
    }
    
    /**
     * methode qui genere le tableau des abonnements pour
     * un placement donné (souscription ou avance)
     * @param vo : ParamAbonnementement
     * @return   : ParamAbonnementement
     * @autor    : Youssef BOUSSEN 
     */
    
    public IValueObject perform(IValueObject vo) {

        ParamAbonnementement paramAbonnementement = (ParamAbonnementement)vo;

        this.setCroFlag(false);
        try{
            Double montSintAbpl=Double.valueOf("0");
            Date dateDeb = paramAbonnementement.getDatDebAbpl();
           
        if (paramAbonnementement.getTypeOperation().equalsIgnoreCase("S")){ ///*** cas souscription
          
            Date endYearDate = new Date();
            Double restTotInt = paramAbonnementement.getMontItotAbpl().doubleValue();
            Double restParAnnee = Double.valueOf("0");
            Date datEcheance = paramAbonnementement.getDatFinAbpl();
            Date subscriptionDate =   paramAbonnementement.getDatDebAbpl();
            
            
            GregorianCalendar calendrier = new GregorianCalendar();
            calendrier.setTime(dateDeb);
            if(!paramAbonnementement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)
              && !paramAbonnementement.isOpRenouvellemnt()
                ){
                calendrier.add(GregorianCalendar.DATE,365);
                   }else {
                       calendrier.add(GregorianCalendar.DATE,364); // cas BNA placement et contrat renouvelé
                   }
           
            // calendrier.add(GregorianCalendar.DATE,365);
             if(datEcheance.after(calendrier.getTime())){ // la date d'échéance dépasse une année
               endYearDate = calendrier.getTime(); // initialisation de la fin d'année 
              }else {
                      endYearDate = datEcheance;
                     }
            // si la date fin abonnement dépasse la date de fin d'année, créer une ligne de fin d'exercice comptable    
             // créer Abonnement pr chaque année
        if(paramAbonnementement.getContratPlacement().getCodPintCpla().equals("POST")){  
            
             while(datEcheance.after(endYearDate)){
                  
                    restParAnnee = insertAbonnementPlacement(dateDeb,endYearDate,paramAbonnementement,datEcheance,subscriptionDate,true,restTotInt);
                    dateDeb = DateHandler.addJour(endYearDate,1);
                    calendrier.setTime(endYearDate);
                    calendrier.add(GregorianCalendar.DATE,365);
                    if(datEcheance.after(calendrier.getTime())){ // la date d'échéance dépasse la date fin d'année
                       endYearDate = calendrier.getTime(); 
                      }else {
                         endYearDate = datEcheance;
                       }
                    restTotInt = restParAnnee;
                }
                
             // créer abonnement jusqu'à la date d'échéance 
        /*   System.out.println("  date dateDeb "+DateHandler.dateToStr(dateDeb));
             System.out.println("  date éche "+DateHandler.dateToStr(datEcheance));
           */
          
            insertAbonnementPlacement(dateDeb,datEcheance,paramAbonnementement,datEcheance,subscriptionDate,false,restTotInt);
         
        }else {
            insertAbonnementPlacement(dateDeb,datEcheance,paramAbonnementement,datEcheance,subscriptionDate,false,restTotInt);
        }///-----------------------------------------------------------------------------------------------------------------------
           }else{ ///*** cas avance
              montSintAbpl = Double.valueOf("0");
            boolean b=true;
             for (montSintAbpl=Double.valueOf("0");dateDeb.before(paramAbonnementement.getDatPrevAbpl());dateDeb=(getLastDayOfMonth(dateDeb))){
                 
///System.out.println("  ***  dateDeb : "+DateHandler.dateToStr(dateDeb));
///System.out.println("  *** DatDebAbpl  : "+DateHandler.dateToStr(paramAbonnementement.getDatDebAbpl()));
///System.out.println("  *** DatPrevAbpl  : "+DateHandler.dateToStr(paramAbonnementement.getDatPrevAbpl()));

                 AbonnementPlacement abonnementPlacement=new AbonnementPlacement();
                 if (DateHandler.getDaysBetween(paramAbonnementement.getDatDebAbpl(),dateDeb)<1 && b){
                     dateDeb=DateHandler.addJour(dateDeb,-1);///
                    ///***abonnementPlacement.setDatDebAbpl(dateDeb);
                     abonnementPlacement.setDatDebAbpl(DateHandler.addJour(dateDeb,1));///
                     b=false;
                 }else{
                    abonnementPlacement.setDatDebAbpl(DateHandler.addJour(dateDeb,1));                     
                 }
                 Date df = getLastDayOfMonth(DateHandler.addJour(dateDeb,1));
                 if (df.after(paramAbonnementement.getDatPrevAbpl())){
                     df = paramAbonnementement.getDatPrevAbpl();
                 }
///System.out.println("  ***  dateDeb 1 : "+DateHandler.dateToStr(dateDeb));

///System.out.println("  *** df      : "+DateHandler.dateToStr(df));

                 abonnementPlacement.setDatFinAbpl(df);
                 abonnementPlacement.setDatCompAbpl(getDateComptabilisation(abonnementPlacement.getDatFinAbpl()));
                 abonnementPlacement.setDatValAbpl(getDateComptabilisation(abonnementPlacement.getDatFinAbpl()));///???
///System.out.println("** dateDeb : "+dateDeb);///
                 abonnementPlacement.setNumNbrjAbpl(Long.valueOf((Double.valueOf(Math.rint(DateHandler.getDaysBetween(dateDeb,abonnementPlacement.getDatFinAbpl())))).longValue()));
                 Double mont = new Double("0");
                 if (DateHandler.getDaysBetween(df,paramAbonnementement.getDatPrevAbpl())>=1){
                    mont = Math.rint(paramAbonnementement.getMontTotAbpl() * abonnementPlacement.getNumNbrjAbpl() * paramAbonnementement.getNumTauiCpla()/Constants.NBR_JOURS_BC_CAT.doubleValue());
                    montSintAbpl = montSintAbpl + mont;
                 }else {///*** la dernière mensualité (tenir compte des arrondis)
                    mont = Math.rint(paramAbonnementement.getMontItotAbpl()-montSintAbpl); 
                    montSintAbpl = paramAbonnementement.getMontItotAbpl().doubleValue();
                    abonnementPlacement.setCodPartAbpl("A");///*** derniere mensualité (fin de la periode de l'avance)
                 }
                 abonnementPlacement.setMontAbplAbpl(mont);
                 abonnementPlacement.setMontSintAbpl(montSintAbpl);
                 AvancRembLiquid avancRembLiquid =new AvancRembLiquid();
                 avancRembLiquid.setNumSeqArl(paramAbonnementement.getNumSeqArl());
                 abonnementPlacement.setAvancRembLiquid(avancRembLiquid);
                 abonnementPlacement.setContratPlacement(paramAbonnementement.getContratPlacement());/// a verifier
                 abonnementPlacement.setCodTypAbpl("P"); ///*** percu dans le cas de l'avance
                 abonnementPlacement.setCodEtatAbpl("A");///*** en attente
                 abonnementPlacement.setCodToprAbpl("A");///***Type operation (S: souscription, A: avance ... )
                 abonnementPlacement.setCodStrcAbpl(paramAbonnementement.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc());
                 
                 InsertAbonnementPlacementTrt insertAbonnementPlacementTrt =new InsertAbonnementPlacementTrt();
                 abonnementPlacement= (AbonnementPlacement)insertAbonnementPlacementTrt.exec(abonnementPlacement);

///System.out.println("** abonnementPlacement.getDatDebAbpl : "+abonnementPlacement.getDatDebAbpl());///
///System.out.println("** abonnementPlacement.FetDatFinAbpl : "+abonnementPlacement.getDatFinAbpl());///
//                 if (DateHandler.getDaysBetween(paramAbonnementement.getDatDebAbpl(),DateHandler.addJour(dateDeb,1))<1){
//                    dateDeb=DateHandler.addJour(dateDeb,1);
//                 }
///System.out.println("** dateDeb 2 : "+DateHandler.dateToStr(dateDeb));///
                 dateDeb=DateHandler.addJour(dateDeb,1);
///System.out.println("** dateDeb 3 : "+DateHandler.dateToStr(dateDeb));///
             }  
             
             
            dateDeb=paramAbonnementement.getDatPrevAbpl();
            for (montSintAbpl=montSintAbpl;dateDeb.before(paramAbonnementement.getDatFinAbpl());dateDeb=(getLastDayOfMonth(dateDeb))){
                AbonnementPlacement abonnementPlacement=new AbonnementPlacement();
                if (DateHandler.getDaysBetween(paramAbonnementement.getDatPrevAbpl(),dateDeb)<1){
                   abonnementPlacement.setDatDebAbpl(dateDeb);
                }else{
                   abonnementPlacement.setDatDebAbpl(DateHandler.addJour(dateDeb,1));                     
                }
                Date df = getLastDayOfMonth(DateHandler.addJour(dateDeb,1));
                if (df.after(paramAbonnementement.getDatFinAbpl())){
                    df = paramAbonnementement.getDatFinAbpl();
                }
                abonnementPlacement.setDatFinAbpl(df);
            
                abonnementPlacement.setDatCompAbpl(getDateComptabilisation(abonnementPlacement.getDatFinAbpl()));
                abonnementPlacement.setDatValAbpl(getDateComptabilisation(DateHandler.addJour(dateDeb,1)));///???
                abonnementPlacement.setNumNbrjAbpl(Long.valueOf((Double.valueOf(Math.rint(DateHandler.getDaysBetween(dateDeb,abonnementPlacement.getDatFinAbpl())))).longValue()));
                Double mont = new Double("0");
                mont = Math.rint(paramAbonnementement.getMontTotAbpl() * abonnementPlacement.getNumNbrjAbpl() * paramAbonnementement.getNumTauiCpla()/Constants.NBR_JOURS_BC_CAT.doubleValue());
                montSintAbpl = montSintAbpl + mont;
                abonnementPlacement.setMontAbplAbpl(mont);
                abonnementPlacement.setMontSintAbpl(montSintAbpl);
                
                AvancRembLiquid avancRembLiquid =new AvancRembLiquid();
                avancRembLiquid.setNumSeqArl(paramAbonnementement.getNumSeqArl());
                abonnementPlacement.setAvancRembLiquid(avancRembLiquid);
                abonnementPlacement.setContratPlacement(paramAbonnementement.getContratPlacement());/// a verifier
                abonnementPlacement.setCodTypAbpl("P"); ///*** percu dans le cas de l'avance
                abonnementPlacement.setCodEtatAbpl("A");///*** en attente
                abonnementPlacement.setCodToprAbpl("A");///***Type operation (S: souscription, A: avance ... )
                abonnementPlacement.setCodStrcAbpl(paramAbonnementement.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc());

                InsertAbonnementPlacementTrt insertAbonnementPlacementTrt =new InsertAbonnementPlacementTrt();
                abonnementPlacement= (AbonnementPlacement)insertAbonnementPlacementTrt.exec(abonnementPlacement);
            
                dateDeb=DateHandler.addJour(dateDeb,1);
            }    
        }
         
        return (paramAbonnementement);
        }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur GenererAbonnementTrt ");
                text.append(e.toString());
                erreur.setCode("400");
                erreur.setDescription(text.toString());
                erreur.setKey("GenererAbonnementTrt");
                paramAbonnementement.addError(erreur);
                logger.error(" *** Erreur lors de GenererAbonnementTrt" /*concernant l'agence "+avancRembLiquid.get().getCodStrcMand()*/+" : ", e);
                throw new RuntimeException(e);
            }
    }
    
    public Double insertAbonnementPlacement(Date dateDeb, Date dateFin,ParamAbonnementement paramAbonnement, Date datEcheance,Date subscriptionDate, boolean isEndYear, Double restTotInt){
    try{
        Double montSintAbpl = Double.valueOf("0");
        Double restParAnnee = Double.valueOf("0");
        Date lastDayMonth = new Date();
         double duree ;
       //Date de modification de la base de calcul pour le produit BNA placement " de 360 à 365 jours " 30/06/2020
         String dateCalculBnaPlac ="30/06/2020";
         if(paramAbonnement.isOpRenouvellemnt() || paramAbonnement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)){
           duree = Math.rint(DateHandler.getDaysBetween(paramAbonnement.getDatDebAbpl(),paramAbonnement.getDatFinAbpl())+1) ;
         }else {
           duree = Math.rint(DateHandler.getDaysBetween(paramAbonnement.getDatDebAbpl(),paramAbonnement.getDatFinAbpl())) ;
         }
         while(DateHandler.strToDate(DateHandler.dateToStr(dateDeb)).compareTo(DateHandler.strToDate(DateHandler.dateToStr(dateFin))) <= 0 ){
        lastDayMonth = (getLastDayOfMonth(dateDeb));
        AbonnementPlacement abonnementPlacement = new AbonnementPlacement();
            abonnementPlacement.setDatDebAbpl(dateDeb);
            Date df = getLastDayOfMonth(dateDeb);
            if (df.after(dateFin)){
                df = dateFin;
              }
            abonnementPlacement.setDatFinAbpl(df);
            abonnementPlacement.setDatCompAbpl(getDateComptabilisation(abonnementPlacement.getDatFinAbpl()));
 
            if (DateHandler.getDaysBetween(subscriptionDate,dateDeb)<1){
            if(paramAbonnement.isOpRenouvellemnt()){
                abonnementPlacement.setNumNbrjAbpl(Long.valueOf((Double.valueOf(Math.rint(DateHandler.getDaysBetween(abonnementPlacement.getDatDebAbpl(),abonnementPlacement.getDatFinAbpl())))).longValue()+1)); 
            }else{
                if(!paramAbonnement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)){
                    abonnementPlacement.setNumNbrjAbpl(Long.valueOf((Double.valueOf(Math.rint(DateHandler.getDaysBetween(abonnementPlacement.getDatDebAbpl(),abonnementPlacement.getDatFinAbpl())))).longValue()));
                }else {
                    abonnementPlacement.setNumNbrjAbpl(Long.valueOf((Double.valueOf(Math.rint(DateHandler.getDaysBetween(abonnementPlacement.getDatDebAbpl(),abonnementPlacement.getDatFinAbpl())))).longValue()+1)); 
                }
              }
             }else{
                abonnementPlacement.setNumNbrjAbpl(Long.valueOf((Double.valueOf(Math.rint(DateHandler.getDaysBetween(abonnementPlacement.getDatDebAbpl(),abonnementPlacement.getDatFinAbpl())))).longValue()+1));                 
            }
            abonnementPlacement.setDatValAbpl(null);/// date d'exécution
            Double mont = new Double("0"); 
         
          if(DateHandler.strToDate(DateHandler.dateToStr(df)).compareTo(DateHandler.strToDate(DateHandler.dateToStr(datEcheance))) < 0 ){
       //     if (DateHandler.getDaysBetween(DateHandler.strToDate(DateHandler.dateToStr(df)),DateHandler.strToDate(DateHandler.dateToStr(datEcheance)))>1){
               if(paramAbonnement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
                   || paramAbonnement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_CAT_PLAC)
                   || paramAbonnement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
                   || paramAbonnement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_CATDC_PLAC)
                ){
                    if(paramAbonnement.getContratPlacement().getCodPintCpla().equals("PRE")){
                       mont=Math.rint(paramAbonnement.getMontTotAbpl() * abonnementPlacement.getNumNbrjAbpl() * paramAbonnement.getNumTauiCpla()/(Constants.NBR_JOURS_BC_CAT.doubleValue()+ duree * paramAbonnement.getNumTauiCpla())); 
                            }else {
                                mont=Math.rint(paramAbonnement.getMontTotAbpl() * abonnementPlacement.getNumNbrjAbpl() * paramAbonnement.getNumTauiCpla()/(Constants.NBR_JOURS_BC_CAT.doubleValue()));
                            }
                  }else if(paramAbonnement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC) && paramAbonnement.getContratPlacement().getDatValCpla().after(DateHandler.strToDate(dateCalculBnaPlac))){
                       if(paramAbonnement.getContratPlacement().getCodPintCpla().equals("PRE")){
                          mont=Math.rint(paramAbonnement.getMontTotAbpl() * abonnementPlacement.getNumNbrjAbpl() * paramAbonnement.getNumTauiCpla()/(Constants.NBR_JOURS_BC_CAT.doubleValue()+ duree * paramAbonnement.getNumTauiCpla())); 
                           }else {
                               mont=Math.rint(paramAbonnement.getMontTotAbpl() * abonnementPlacement.getNumNbrjAbpl() * paramAbonnement.getNumTauiCpla()/(Constants.NBR_JOURS_BC_CAT.doubleValue()));
                           } 
                    }else if(paramAbonnement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC) && paramAbonnement.getContratPlacement().getDatValCpla().compareTo(DateHandler.strToDate(dateCalculBnaPlac))==0){
                        if(paramAbonnement.getContratPlacement().getCodPintCpla().equals("PRE")){
                            mont=Math.rint(paramAbonnement.getMontTotAbpl() * abonnementPlacement.getNumNbrjAbpl() * paramAbonnement.getNumTauiCpla()/(Constants.NBR_JOURS_BC_CAT.doubleValue()+ duree * paramAbonnement.getNumTauiCpla())); 
                             }else {
                                 mont=Math.rint(paramAbonnement.getMontTotAbpl() * abonnementPlacement.getNumNbrjAbpl() * paramAbonnement.getNumTauiCpla()/(Constants.NBR_JOURS_BC_CAT.doubleValue()));
                             } 
                    }else if(paramAbonnement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC) && paramAbonnement.getContratPlacement().getDatValCpla().before(DateHandler.strToDate(dateCalculBnaPlac))){
                    	// Garder la base de calcul de 360 jours pour les anciens contrat BNA Placement
                    	if(paramAbonnement.getContratPlacement().getCodPintCpla().equals("PRE")){
                           mont=Math.rint(paramAbonnement.getMontTotAbpl() * abonnementPlacement.getNumNbrjAbpl() * paramAbonnement.getNumTauiCpla()/(Constants.NBR_JOURS_BNAPLC.doubleValue()+ duree * paramAbonnement.getNumTauiCpla())); 
                            }else {
                                mont=Math.rint(paramAbonnement.getMontTotAbpl() * abonnementPlacement.getNumNbrjAbpl() * paramAbonnement.getNumTauiCpla()/(Constants.NBR_JOURS_BNAPLC.doubleValue()));
                            } 
                     }
                 montSintAbpl = montSintAbpl + mont;
             }else {///*** la dernière mensualité (tenir compte des arrondis)
              if(paramAbonnement.getContratPlacement().getCodPintCpla().equals("POST")){
                 mont = Math.rint(restTotInt.doubleValue() - montSintAbpl.doubleValue()); 
                 montSintAbpl = Math.rint(restTotInt.doubleValue());
                  }else{
                    mont = Math.rint(paramAbonnement.getMontItotAbpl()-montSintAbpl);
                    montSintAbpl = paramAbonnement.getMontItotAbpl().doubleValue();
                  }
             }
              if(!paramAbonnement.getContratPlacement().getCodFavCpla().equals(Constants.COD_FAV_INDEXE)){
                 abonnementPlacement.setMontAbplAbpl(mont);
                 abonnementPlacement.setMontSintAbpl(montSintAbpl);
              }else {
                  if(paramAbonnement.getContratPlacement().getCodPintCpla().equals("PRE")){
                      abonnementPlacement.setMontAbplAbpl(mont);
                      abonnementPlacement.setMontSintAbpl(montSintAbpl);
                  }else {
                      abonnementPlacement.setMontAbplAbpl(Double.valueOf("0"));
                      abonnementPlacement.setMontSintAbpl(Double.valueOf("0"));
                      }
              }
        if(paramAbonnement.getDateCompAgence() != null){
          int anneeDateCompt = DateHandler.GetYearFromDate(paramAbonnement.getDateCompAgence());
          int anneeDateSousc = DateHandler.GetYearFromDate(subscriptionDate);
          
          if (DateHandler.getDaysBetween(subscriptionDate,dateDeb)<1){
            if(anneeDateCompt != anneeDateSousc){
                  paramAbonnement.setMontInteretCorrecAnneesPrec(mont); 
                  paramAbonnement.setMontIntCorrectAbonnMois(Double.valueOf("0"));
               }else{
                  if(subscriptionDate.getMonth() != paramAbonnement.getDateCompAgence().getMonth()){
                      paramAbonnement.setMontInteretCorrecAnneesPrec(Double.valueOf("0"));
                      paramAbonnement.setMontIntCorrectAbonnMois(mont); 
                  }else {
                      paramAbonnement.setMontInteretCorrecAnneesPrec(Double.valueOf("0"));
                      paramAbonnement.setMontIntCorrectAbonnMois(Double.valueOf("0")); 
                  }
              }
          }
        }
             ContratPlacement contratPlacement =new ContratPlacement();
             contratPlacement.setNumSeqCpla(paramAbonnement.getNumSeqCpla());
             abonnementPlacement.setContratPlacement(contratPlacement);
             abonnementPlacement.setCodTypAbpl("S");///*** servi dans le cas de la souscription
              if (DateHandler.getDaysBetween(subscriptionDate,dateDeb)<1){
                  abonnementPlacement.setCodEtatAbpl("T");///*** 
              }else {
                  abonnementPlacement.setCodEtatAbpl("A");///*** en attente
              }
           
             abonnementPlacement.setCodToprAbpl("S"); //Type operation (S: souscription, A: avance ... )
             if(paramAbonnement.getContratPlacement().getCodPintCpla().equals("POST")){
                 if(isEndYear && 
                     DateHandler.strToDate(DateHandler.dateToStr(df)).compareTo(DateHandler.strToDate(DateHandler.dateToStr(dateFin))) == 0
                 ){ 
                      abonnementPlacement.setCodPartAbpl("P"); //Flag la ligne contenant la date exacte de la fin de l annee pour  le versement partiel d'interets
                    }
              }
             abonnementPlacement.setCodStrcAbpl(paramAbonnement.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc());
             InsertAbonnementPlacementTrt insertAbonnementPlacementTrt =new InsertAbonnementPlacementTrt();
             abonnementPlacement= (AbonnementPlacement)insertAbonnementPlacementTrt.exec(abonnementPlacement); 
             
        dateDeb=DateHandler.addJour(lastDayMonth,1);
      }
      if(paramAbonnement.getContratPlacement().getCodPintCpla().equals("POST")){
             restParAnnee = restTotInt.doubleValue() - montSintAbpl.doubleValue();
             }
    return restParAnnee;
     }catch (Exception e) {
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
          }
          
          return( DateHandler.strToDate(DateHandler.dateToStr(DateReturn)));
        
        }catch(Exception e){
            logger.error(" Erreur dans GetDateComptable.execute : " , e);
            return (d);
        }
    }
    public  boolean equalsDate(Date d1, Date d2)  {

        try{
        
          return true;
        
        }catch(Exception e){
            logger.error(" Erreur dans GetDateComptable.execute : " , e);
            return false;
        }
    }
/*    public  Long calculMontAvance(Long nbrJour, Long montant, Double taux){
    
    
    try{
        
        Math.rint(montant * nbrJour * taux/Constants.NBR_JOURS_BC_CAT.doubleValue());
        
                                         
    } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("veuillez transmettre ce message à l'équipe informatique: ");
                text.append(". Exception calculMontAvance : "); text.append(e.getMessage());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
            }

    }
    */
    public void genCroText(ValueObject vo) {
          
         
        } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
        
        
    }
}
