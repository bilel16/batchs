package com.bna.smile.model.domaineplacement.traitement;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.Devise;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypeMoyenPaiement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.ContratCptSold;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamDates;
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



public class InteretServiAgenceTrt  extends Traitement{
    public InteretServiAgenceTrt() {
    }
        
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
       
       // à ne pas laisser en variable global
        ICriteria criteria = searchEngine.createCriteria();
        ICriteria criteriaAvanc = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        

    public IValueObject perform(IValueObject vo) {
        
       
       ParamInteretServi paramInteretServi = (ParamInteretServi) vo;
       Structure agence = paramInteretServi.getStructure();
       Date dateComptableAgence = paramInteretServi.getDateComptableAgence();
       
       try{
                //appel CB pour extraire Date valeur
                logger.info("La date Comtable de l'Agence "+ agence.getCodStrcStrc()+" est "+DateHandler.dateToStr(dateComptableAgence));
                
                PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
                ///*** recherche des placement a echéance interet servi pour cette agence
                ICriteria criteriaPlac = searchEngine.createCriteria();
                criteriaPlac.add(expression.eq("codEtatCpla","V"));
                criteriaPlac.add(expression.eq("contratCpt.contratCptId.codStrcStrc",agence.getCodStrcStrc()));
                //contrat placement non encore échu
                criteriaPlac.add(expression.gt("datEcheCpla",dateComptableAgence));
                //date prochene echéance interet <= date comptable
                criteriaPlac.add(expression.le("datPintCpla",dateComptableAgence));
                // type intret postcompté: POST
                criteriaPlac.add(expression.eq("codPintCpla","POST"));
                // date prochaine echeance intret servi is not  null et != date echeance placement
                criteriaPlac.add(expression.isNotNull("datPintCpla"));
               
               // System.out.println(paramLiquidation.getDateComptLiquidation());
                List listePlacement=searchEngine.find(ContratPlacement.class,criteriaPlac);
                int nbrCptPlac = 0;
                Double sommePlacement = Double.valueOf("0");
                if(listePlacement!=null&&listePlacement.size()>0) {
                    nbrCptPlac = 0;
                    sommePlacement = Double.valueOf("0");
                    for (Iterator it1 = listePlacement.iterator(); it1.hasNext(); ){
                        ContratPlacement contratPlacement=(ContratPlacement)it1.next();
                        System.out.println(contratPlacement.getNumSeqCpla());
                        ///recheerche date prochain interet servi 
                        GregorianCalendar calendar = new java.util.GregorianCalendar(); 
                        // Initialisé à la date et l'heure courrante. 
                        calendar.setTime(contratPlacement.getDatPintCpla()); 
                        
                        ///*** Traitement intret servi***
                        InteretServi interetServi = new InteretServi();
                        //calcule date comptable qui correspend à la date de l'interet servi
                        //Date dateComptableInteret =getDateComptable(contratPlacement.getDatPintCpla());
                       
                        Long montantInteret = null;
                        //  recalcul Taux interet si interet indexé
                        double tauxInteret = contratPlacement.getNumTauiCpla().doubleValue();
                        if(contratPlacement.getCodFavCpla().equals("I")){
                            ParamDates paramDates = new ParamDates();
                            
                            ///Calcule de la date du premier jour de calcul d'interet
                            //dateAvant=dateinteret -365
                            calendar.add(Calendar.DATE, -365);
                            Date dateAvant=calendar.getTime();
                            Date dateDebut;
                            //si cas du premier interet servi (prem année) date inter-365 <= date dern interet servi alors date debut=date creation
                            //sinon date debut=dateAvant(cad -365)
                            if(dateAvant.compareTo(contratPlacement.getDatValCpla())<=0){
                                ////si BNaPlacement ou renouvellement dateDeb=dateVal sinn datedeb=DateVal+1
                                 if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC) || contratPlacement.getContratPlacementByNumSqcrCpla()!=null)
                                 {
                                	 dateDebut=contratPlacement.getDatValCpla();
                                 }
                                 else
                                 {		
                                	 GregorianCalendar calendarDeb = new java.util.GregorianCalendar();
                                	 calendarDeb.setTime(contratPlacement.getDatValCpla());
                                	 calendarDeb.add(Calendar.DATE,1);
                                	 dateDebut=calendarDeb.getTime();
                                 }
                            }else{
                                //ajout d'un jour
                                calendar.add(Calendar.DATE, 1);
                                dateDebut=calendar.getTime();
                            }
                              
                            paramDates.setDateDebut(dateDebut);
                            //on enleve le jour du versement d'interet pour qu'il ne soit pas compté ds le calcul
                            paramDates.setDateFin(DateHandler.addJour(contratPlacement.getDatPintCpla(), -1));
                            
                            paramDates.setInterval("J");
                            GetAvgTMMbetweenDatesTrt getAvgTMMbetweenDatesTrt = new GetAvgTMMbetweenDatesTrt();
                            PrimitiveVO primitiveVO = (PrimitiveVO)getAvgTMMbetweenDatesTrt.exec(paramDates);
                            Double tauxInteretMoyen = primitiveVO.getVDouble();
                            if(contratPlacement.getCodMargCpla().equals("+")){
                                tauxInteret =  tauxInteretMoyen.doubleValue() + contratPlacement.getNumMargCpla();
                            }else if(contratPlacement.getCodMargCpla().equals("-")){
                                tauxInteret = tauxInteretMoyen.doubleValue() - contratPlacement.getNumMargCpla();
                            }
                        }
                        
                        
                        //Date de modification de la base de calcul pour le produit BNA placement " de 360 à 365 jours " 30/06/2020
                        String dateCalculBnaPlac ="30/06/2020";
                        if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC) && contratPlacement.getDatValCpla().after(DateHandler.strToDate(dateCalculBnaPlac))){
                            montantInteret = (long)(contratPlacement.getMontActuCpla() * tauxInteret * 365/36500);  
                        }else if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC) && contratPlacement.getDatValCpla().compareTo(DateHandler.strToDate(dateCalculBnaPlac))==0){
                            montantInteret = (long)(contratPlacement.getMontActuCpla() * tauxInteret * 365/36500);  
                        
                        }else if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC) && contratPlacement.getDatValCpla().before(DateHandler.strToDate(dateCalculBnaPlac))){
                            // Garder la base de calcul de 360 jours pour les anciens contrat BNA Placement
                        	montantInteret = (long)(contratPlacement.getMontActuCpla() * tauxInteret * 365/36000);      
                        }else{
                            montantInteret = (long)(contratPlacement.getMontCapCpla() * tauxInteret * 365/36500);
                        }
                        interetServi.setNumIsrvIsrv(plcDao.getSequenceInteretSerrvi());
                        
                        interetServi.setDatIsrvIsrv(contratPlacement.getDatPintCpla());
                        
                        if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
                                || contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_CATDC_PLAC)){
                                    interetServi.setMontIrcIsrv(Long.valueOf(0));  
                                }else{
                                    interetServi.setMontIrcIsrv(montantInteret*20/100);  
                            }
                       
                        
                        interetServi.setMontBrutIsrv(montantInteret);  
                        interetServi.setMontIsrvIsrv(montantInteret-interetServi.getMontIrcIsrv());
                        interetServi.setContratPlacement(contratPlacement);
                        // c'est la date comptable et non date valeur comme son nom :)
                        interetServi.setDatValIsrv(dateComptableAgence); 
                        interetServi.setCodTypIsrv("P"); 
                        
                        InsertInteretServiTrt insertInteretServiTrt = new InsertInteretServiTrt();
                        insertInteretServiTrt.exec(interetServi);
                        
                        //update date prochain interet servi datPintCpla
                        Context context = ContextHandler.getContext();
                        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");  
                        calendar.setTime(contratPlacement.getDatPintCpla()); 
                        // c'est 365 jour à partir de dat_pint_cpla
                        calendar.add(Calendar.DATE, 365);
                        contratPlacement.setDatPintCpla(calendar.getTime());
                        crudService.update(contratPlacement);
                        
                        ///*** MAJ du montant actualisé dans le contrat compte                    
                        ContratCptId contratCptId =contratPlacement.getContratCpt().getContratCptId();
                        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
                        /* Charger le ContratCpt existante */
                        ContratCpt contratCpt =(ContratCpt) searchEngine.get(ContratCpt.class,contratCptId);
                        ContratCptSold contratCptSold = new ContratCptSold();
                        contratCptSold.setContratCpt(contratCpt);
                        contratCptSold.setSolde(interetServi.getMontIsrvIsrv());
                        contratCptSold.setSens("C");
                        UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
                        contratCpt = (ContratCpt)updateSoldTrt.exec(contratCptSold); 
                        
                        ///----------- création opération Moyen de payement (interet) ----------------------           
                        OperationMoyPay operationMoyPay =affecterDonneesOperationMoyenPaiement (contratPlacement, interetServi, dateComptableAgence);
                         
                        ParamInsertInteret paramInsertInteret = new ParamInsertInteret();
                        paramInsertInteret.setOperationMoyPay(operationMoyPay);
                        paramInsertInteret.setInteretServi(interetServi);
                         
                        InsertOperationInteretServiTrt insertOpMoyPayInteretSouscPlacTrt = new InsertOperationInteretServiTrt();
                        insertOpMoyPayInteretSouscPlacTrt.setVerifDomaine(false);
                        operationMoyPay = (OperationMoyPay)insertOpMoyPayInteretSouscPlacTrt.exec(paramInsertInteret);
                         
                        
                        nbrCptPlac = nbrCptPlac+1;
                        sommePlacement =  sommePlacement+ Double.valueOf(montantInteret.toString());
                        paramInteretServi.setFinBatchStructure(true);
                    }  

                }
                ///*** gerer les statistiques
               gestionStatistique(dateComptableAgence, agence, nbrCptPlac, sommePlacement);
               

            }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = new StringBuffer("Erreur dans interetServiAgence : ");
                text.append(e.getMessage());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("interetServiAgence");
                logger.error("Exception : ",e);   
                ///*** gerer une exception
                gestionException(dateComptableAgence, agence, e);
                throw new RuntimeException(e);
                                 
            }          
            return vo;
    }
    
    public  Date getDateComptable(Date d)  {

        try{
          if(CalanderHandler.isJourFerier(d)){
            return(CalanderHandler.GetNextWorkingDay(d));
          }else
          {
            return(d);
          }
        
        }catch(Exception e){
            logger.error(" Erreur dans GetDateComptable.execute : " , e);
            return (d);
        }
    }

    public TraitementConditionBanque getCB(ContratPlacement contratPlacement,Long codOper,Date dateComptableInteret) {
    

        TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();

        try {
            ContratCpt contratCpt = contratPlacement.getContratCpt();
            traitementConditionBanque.setCodOperOper(codOper.toString());
            if (contratPlacement!=null){
                traitementConditionBanque.setCodPrdPrd(contratPlacement.getProduitPlacement().getCodPrdPlc().toString());
                traitementConditionBanque.setCodTpceTpce(contratPlacement.getPersonne().getTypePiece().getCodTpceTpce().toString());
                traitementConditionBanque.setNumPcePers(contratPlacement.getPersonne().getNumPcePers());
                traitementConditionBanque.setIdContrat(contratPlacement.getNumSeqCpla().toString());
                traitementConditionBanque.setMontant(contratPlacement.getMontCapCpla().toString());
                traitementConditionBanque.setNbUnites(contratPlacement.getNumNbrjCpla().toString());
            }
            if (contratCpt!=null){
                traitementConditionBanque.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt().toString());
                traitementConditionBanque.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc().toString());
                traitementConditionBanque.setCodPrdCpt(contratCpt.getContratCptId().getCodPrdPrd().toString());
            }
            traitementConditionBanque.setDateReference(DateHandler.dateToStr(dateComptableInteret));
            
            traitementConditionBanque.getCB();
            
           
        } catch (Exception e) {
                throw new RuntimeException(e);
        }
        return traitementConditionBanque;   
    } 
    public OperationMoyPay affecterDonneesOperationMoyenPaiement (ContratPlacement contratPlacement, InteretServi interetServi, Date dateComptableAgence){
    
        
        OperationMoyPay operationMoyPay = new OperationMoyPay();          
        
        Personnel personnelInit = new Personnel();
        personnelInit.setNumMatrUser("9999");
        Operation operation = new Operation();    
        
        Structure structure = new Structure();    
        structure.setCodStrcStrc(Long.valueOf(contratPlacement.getContratCpt().getContratCptId().getCodStrcStrc()));
        operationMoyPay.setStructureInitiatrice(structure);
        operationMoyPay.setStructureReceptrice(structure);
        
        Devise devise = new Devise();
        devise.setCodDevDev(Constants.COD_DEV_DINAR);
        operationMoyPay.setDevise(devise);
         
        ContratCpt contratCpt = contratPlacement.getContratCpt();
        operationMoyPay.setContratCpt(contratCpt);

        if (contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC) ||
            contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC) )
          {
          if(contratPlacement.getNumBcCpla() != null){
            operationMoyPay.setNumMoypOmp(contratPlacement.getNumBcCpla().toString());
          }else {
              logger.error("Le numéro BC est vide");
          }
        }
         
        operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
        operationMoyPay.setPersonnelInitiateur(personnelInit);/// personne initiatrice seulement au cas de retrait ponctuel
        operationMoyPay.setPersonnelValideur(personnelInit);/// personnel initiatrice = personnel validateur
        
        operation.setCodOperOper(Constants.COD_OPER_VERSEMENT_INTERET_PLAC_POST);
        
        Tache tache = new Tache();
        tache.setOperation(operation);
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(operation.getCodOperOper());
        tacheId.setCodTachTach(Long.valueOf("1"));
        
        tache.setTacheId(tacheId);
        operationMoyPay.setTache(tache);
      //  operationMoyPay.setCodRefbOmp(creationContratPlacementForm.getInitialisationView().getCodeOperation());
        String str = contratPlacement.getNumSeqCpla().toString();
        if(contratPlacement.getNumBcaCpla()!= null){
            str = str +" | "+contratPlacement.getNumBcaCpla().toString();   
        } 
        operationMoyPay.setCodRefbOmp(str);
        operationMoyPay.setDatOperOmp(dateComptableAgence);
        operationMoyPay.setDatSystOmp(new Date()); // avec le time ok
        
        //calcul date valeur de la date comptable
        TraitementConditionBanque traitementConditionBanque = getCB(contratPlacement,Constants.COD_OPER_VERSEMENT_INTERET_PLAC_POST,dateComptableAgence); 
        operationMoyPay.setDatValOmp(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));
       
        DemandeDecision demdRetour = contratPlacement.getDemandeDecision();
        if(demdRetour != null && demdRetour.getNumRefdDemd() != null){
                 operationMoyPay.setTypePieceDemandeur(demdRetour.getTypePiece());
                 operationMoyPay.setNumPcedOmp(demdRetour.getNumNpceDemd());
                 operationMoyPay.setNomNomdOmp(demdRetour.getNomNomDemd());
                 operationMoyPay.setNomPrndOmp(demdRetour.getNomPrnDemd());
         }else {
             logger.error("Le contrat placement n'est pas affecté à aucune demande -- Set des demandes vides");
         }
        operationMoyPay.setMontDinOmp(interetServi.getMontIsrvIsrv());
       
        operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);
     // solde avant  -- montsoldccpt / champ, non pa foreign key
        operationMoyPay.setMontSoldCcpt(contratCpt.getMontSoldCcpt());
        operationMoyPay.setMontApreOmp(contratCpt.getMontSoldCcpt()+interetServi.getMontIsrvIsrv());
        
        operationMoyPay.setCodRefcOmp(operationMoyPay.getNumOperOmp()); 
        str = null;
        //operationMoyPay.setMontApreOmp(contratCpt.getMontSoldCcpt());
     
        Produit prd = new Produit();
        prd.setCodPrdPrd(contratPlacement.getProduitPlacement().getCodPrdPlc());
        operationMoyPay.setProduit(prd); // COD_PRD_OMP rempli avec le code produit placement
        prd=null;
        TypeMoyenPaiement typMoyPay = new TypeMoyenPaiement();
        typMoyPay.setCodMoypTmoy(Constants.COD_TMOY_ESPECE);
        operationMoyPay.setTypeMoyenPaiement(typMoyPay);
        typMoyPay =null;
        //operation effectuée par Tiers (Batch)
        operationMoyPay.setCodDemOmp("TR");
    
      
            
    return operationMoyPay;
    } 
    private void gestionStatistique(Date dateComptable, Structure agence, int nbrCptPlac, Double sommePlacement) {
    
        BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
        batchStatPlacement.setCodEtatBats("V");
        batchStatPlacement.setDatSystBats(new Date());
        batchStatPlacement.setDatCompBats(dateComptable);
        batchStatPlacement.setStructure(agence);
        BatchMetier batchMetier = new BatchMetier();
        batchMetier.setCodBatBmet(Constants.COD_BATCH_INTERET_SERVI);
        batchStatPlacement.setBatchMetier(batchMetier);
        batchStatPlacement.setLibExtrBats(nbrCptPlac+" Interet Servi pour la somme de : "+(sommePlacement.longValue())+" Dinars");
        BatchService batchService= (BatchService) context.getBean("batchService");
        batchStatPlacement = (BatchStatPlacement)batchService.InsertBatchStatPlacement(batchStatPlacement);
    }

    private void gestionException(Date dateComptable, Structure agence, Exception e) {
    
        BatchExeptionPlac batchExeptionPlac  = new BatchExeptionPlac();
        batchExeptionPlac.setDatSystBate(new Date());
        batchExeptionPlac.setDatCompBate(dateComptable);
        batchExeptionPlac.setStructure(agence);
        batchExeptionPlac.setLibTpbmBate("Exception Batch Interet Servi");
        batchExeptionPlac.setLibExpBate(e.getMessage());
        BatchService batchService= (BatchService) context.getBean("batchService");
        batchExeptionPlac = (BatchExeptionPlac)batchService.InsertBatchExeptionPlac(batchExeptionPlac);
    }
    public void genCroText(ValueObject vo) {
    
    } 
}    
