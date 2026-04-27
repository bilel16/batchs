package com.bna.smile.model.domaineplacement.traitement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.JourneeStructureBatchId;
import com.bna.commun.model.JourneeStructureId;
import com.bna.commun.model.Personne;
import com.bna.commun.model.ProduitPlacement;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetContratCptByIdTrt;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;


public class RenouvellementAEcheanceTrt extends Traitement{
    public RenouvellementAEcheanceTrt() {
    }
    Context context = ContextHandler.getContext();
    ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
    CRUDservice crudService = (CRUDservice)context.getBean("crudservice"); 
    ICriteria criteria = searchEngine.createCriteria();
    private int nbrCptPlac = 0;
    private Double sommePlacement = Double.valueOf("0");
    private Date dateComptRenouvel;
    JourneeStructureId journeeStructureId = new JourneeStructureId();
    JourneeStructure journeeStructure = new JourneeStructure();
    
    public IValueObject perform(IValueObject vo) {
        this.setSecurityFlag(false);
        this.setVerifDomaine(false);
        this.setCroFlag(true); 
        
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        ParamContratPlacement paramRenouvellement = (ParamContratPlacement)vo;
        Structure agence = new Structure();
        try{
          
            PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
             ///*** traitement Batch 
            List listAgencesPlacementLiq = plcDao.getListAgencesPlacement();
            ListOrderedMap ListAgPlc = null;
                if(listAgencesPlacementLiq!=null && listAgencesPlacementLiq.size()>0) {
                    for (Iterator it1 = listAgencesPlacementLiq.iterator(); it1.hasNext(); ){
                        ListAgPlc = (ListOrderedMap)it1.next();
                        paramRenouvellement.setFinBatchStructure(true); 
                        if ((ListAgPlc.getValue(0)).toString() != null) {
                            agence.setCodStrcStrc(Long.valueOf(ListAgPlc.getValue(0).toString()));
                            paramRenouvellement.setAgence(agence);
                        }
                        if ((ListAgPlc.getValue(1)).toString() != null) {
                            ListAgPlc.getValue(1);
                            paramRenouvellement.setDateComptRenouvel(DateHandler.strToDate(ListAgPlc.getValue(1).toString()));
                            this.dateComptRenouvel = paramRenouvellement.getDateComptRenouvel();
                        }
                        // tester si la journée batch n'est pas dejà inserée
                         JourneeStructureBatch journeeStructureBatch = new JourneeStructureBatch();
                         JourneeStructureBatch journeeStructureBatchRetour = new JourneeStructureBatch();
                         JourneeStructureBatchId journeeStructureBatchId = new JourneeStructureBatchId();
                         journeeStructureBatchId.setCodStrcStrc(agence.getCodStrcStrc());
                         journeeStructureBatchId.setDatJrnJrn(paramRenouvellement.getDateComptRenouvel());
                         System.out.println(DateHandler.dateToStr(journeeStructureBatchId.getDatJrnJrn()));
                         journeeStructureBatchId.setCodBatBmet(Constants.COD_BATCH_RENOUVEL);
                         journeeStructureBatch.setJourneeStructureBatchId(journeeStructureBatchId);
                        // tester si la journée n'est pas dejà inserée
                         BatchService batchService= (BatchService) context.getBean("batchService");
                        journeeStructureBatchRetour = (JourneeStructureBatch)batchService.getJourneeStructureBatch(journeeStructureBatch);  
                        if( journeeStructureBatchRetour != null && journeeStructureBatchRetour.getCodStatJsb().intValue() == 0){// structure non traitée
                      
                         paramRenouvellement = (ParamContratPlacement)perf(paramRenouvellement, agence);
                         
                             if(paramRenouvellement.isFinBatchStructure()){
                                 // journée batch OK
                                  journeeStructureBatch.setDatCloJsb(new Date());
                                  journeeStructureBatch.setCodStatJsb(Long.valueOf("1"));
                                  journeeStructureBatch = (JourneeStructureBatch)batchService.updateJourneeStructureBatch(journeeStructureBatch);  
                                  gestionStatistique(paramRenouvellement, agence.getCodStrcStrc());
                                  paramRenouvellement.setNbrCptPlacRenouvelable(0);
                                 }else {
                                    // le batch est fini, mais avec des erreurs, insertion statistiques 
                                     gestionStatistique(paramRenouvellement, agence.getCodStrcStrc());
                                     paramRenouvellement.setNbrCptPlacRenouvelable(0);
                                      if(paramRenouvellement.hasError()){
                                       if(paramRenouvellement.getAgence() != null){
                                        List listErreur = paramRenouvellement.getErrors();
                                        for (Iterator it = listErreur.iterator(); it.hasNext();) {
                                            com.oxia.fwk.core.Error error = 
                                                (com.oxia.fwk.core.Error)it.next();
                                            gestionException(paramRenouvellement.getDateComptRenouvel(),agence,error.getDescription());    
                                            }
                                         }
                                       }
                                      paramRenouvellement.setErrors(new ArrayList()); 
                                  }
                            
                         }else {
                          logger.debug("Journée batch dejà insérée ou inexistante pour l'agence :: " + agence.getCodStrcStrc().toString());
                          System.out.println("Journée batch dejà insérée ou inexistante pour l'agence :: "+ agence.getCodStrcStrc().toString());
                        }
                    }
                }else {
                    logger.debug("La liste des agences est vide.");
                    System.out.println("La liste des agences est vide.");
                    }
            
        }catch (Exception e) {
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);
        }
        finally{ 
         // créer une boucle pr tt les erreurs !
        if(paramRenouvellement.hasError()){
         if(paramRenouvellement.getAgence() != null){
          List listErreur = paramRenouvellement.getErrors();
          for (Iterator it = listErreur.iterator(); it.hasNext();) {
              com.oxia.fwk.core.Error error = 
                  (com.oxia.fwk.core.Error)it.next();
              gestionException(paramRenouvellement.getDateComptRenouvel(),agence,error.getDescription());    
              }
           }
         }
       }
        return paramRenouvellement;
    }
    private IValueObject perf(ParamContratPlacement paramRenouvellement, Structure agence) {
        GetContratPlacementTrt getContratPlacementTrt = new GetContratPlacementTrt();
        
        ContratPlacement ancienContratPlacement = new ContratPlacement();
        
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        try{
        ///*** recherche des placement a renouveler pour cette agence, a partir des demandes avant échéance (codTypr_demd = Type du renouvellement ( 1: avant échéance, 2: aprés échéance ))
        ICriteria criteriaPlac = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        criteriaPlac.add(expression.eq("codTyprDemd",Long.valueOf("1")));
        criteriaPlac.add(expression.eq("codNdemDemd","R"));
        criteriaPlac.add(expression.eq("codEtatDemd","V"));
        criteriaPlac.add(expression.le("datValDemd",paramRenouvellement.getDateComptRenouvel()));
        criteriaPlac.add(expression.eq("contratCpt.contratCptId.codStrcStrc",agence.getCodStrcStrc()));
        criteriaPlac.add(expression.ne("produitPlacement.codPrdPlc",Constants.COD_PRD_BC_PLAC));
        criteriaPlac.add(expression.ne("produitPlacement.codPrdPlc",Constants.COD_PRD_BCDC_PLAC));  
        
        List listeDemandeRenouvel =searchEngine.find(DemandeDecision.class,criteriaPlac);
            int nbrCptPlac = 0;
            Double sommePlacement = Double.valueOf("0");
            boolean finBatch = true;
            paramRenouvellement.setOperationForce(false);       
     if (listeDemandeRenouvel != null && listeDemandeRenouvel.size() != 0){  
         paramRenouvellement.setNbrCptPlacRenouvelable(listeDemandeRenouvel.size());
        for (Iterator it1 = listeDemandeRenouvel.iterator(); it1.hasNext(); ){
            DemandeDecision demandeRenouvel = (DemandeDecision)it1.next();
            paramRenouvellement.setDemandeDecision(demandeRenouvel);
        // vérifier que l'ancien contrat placement est liquidé
         if(demandeRenouvel.getContratPlacement() != null){
            ancienContratPlacement.setNumSeqCpla(demandeRenouvel.getContratPlacement().getNumSeqCpla());
                 getContratPlacementTrt.setVerifDomaine(false);
                 ancienContratPlacement = (ContratPlacement)getContratPlacementTrt.exec(ancienContratPlacement);
                 if(ancienContratPlacement.getCodEtatCpla().equals(Constants.ETAT_CPT_PLACEMENT_LIQUIDE) || ancienContratPlacement.getCodEtatCpla().equals(Constants.ETAT_CONTRAT_PLAC_ECHULIQ)){
                      paramRenouvellement.setContratPlacement(ancienContratPlacement);  
                     // pour chaque demande créer le nouveau contrat placement approprié
                      paramRenouvellement = renouvelerContratPlacement(demandeRenouvel, paramRenouvellement);// créer InteretServi
                   if(!paramRenouvellement.hasError()){
                     PlacementService placementService = (PlacementService)context.getBean("placementService");
                     paramRenouvellement = (ParamContratPlacement)placementService.validerRenouvellement(paramRenouvellement);
                      if(paramRenouvellement.getContratPlacement()!=null ){
                        if(paramRenouvellement.getContratPlacement().getNumSeqCpla()!=null ){
                         nbrCptPlac = nbrCptPlac+1;
                         sommePlacement =  sommePlacement+ Double.valueOf(paramRenouvellement.getContratPlacement().getMontCapCpla().toString());
                       } // si le contrat placement a été inseré (not null) -- contratPlacement.getNumSeqCpla().equals(null)
                      }
                     }else {
                     // le paramRenouvellement has error (retour de renouvelerContratPlacement() )
                       finBatch =false;
                       if(paramRenouvellement.getAgence() != null){
                        List listErreur = paramRenouvellement.getErrors();
                        for (Iterator it = listErreur.iterator(); it.hasNext();) {
                            com.oxia.fwk.core.Error error = 
                                (com.oxia.fwk.core.Error)it.next();
                            gestionException(paramRenouvellement.getDateComptRenouvel(),agence,error.getDescription());    
                            }
                         }
                        paramRenouvellement.setErrors(new ArrayList()); 
                       }
                    
                     }else {
                         logger.error("Pour l' agence ::"+agence.getCodStrcStrc().toString() +", le contrat de placement à renouveler n°" +ancienContratPlacement.getNumSeqCpla()+ " n'est pas liquidé. Veuillez vérifier son état.");
                         erreur.setDescription("Pour l' agence ::"+agence.getCodStrcStrc().toString()+", l'ancien contrat de placement à renouveler n°" +ancienContratPlacement.getNumSeqCpla()+ " n'est pas liquidé. Veuillez vérifier son état.");
                         erreur.setKey("Renouvellement-perf");
                        // paramRenouvellement.addError(erreur); 
                         finBatch =false;
                         gestionException(paramRenouvellement.getDateComptRenouvel(),agence,erreur.getDescription());    
                     }
             }else {
                 StringBuffer descriptionError =new StringBuffer("");
                 descriptionError.append("Pour l'agence :: "); descriptionError.append(agence.getCodStrcStrc().toString());
                 descriptionError.append(" Aucun contrat de placement affecté à la demande de renouvellement ");
                 descriptionError.append(demandeRenouvel.getNumRefdDemd().toString());
                 logger.error(descriptionError.toString());
                 erreur.setDescription(descriptionError.toString());
                 System.out.println(descriptionError.toString());
                 erreur.setKey("Renouvellement-perf");
                 //paramRenouvellement.addError(erreur);
                 finBatch =false;
                 gestionException(paramRenouvellement.getDateComptRenouvel(),agence,erreur.getDescription());    
             }
        
       }// fin de la boucle sur la liste des demandes à renouveler
      // fin du batch pour une structure donnée, test sur paramRenouvellemen san erreur !!
   /*    if(!paramRenouvellement.hasError()){
           finBatch = true;
               }else {
                   finBatch =false;
               }*/
     }else {
         // liste des demandes vides
          logger.debug("La liste des demandes de renouvellement est vide pour l' agence ::"+agence.getCodStrcStrc().toString());
          System.out.println("La liste des demandes de renouvellement est vide pour l' agence ::"+agence.getCodStrcStrc().toString());
          finBatch = true;
     }
     
       ///*** gerer les statistiques et journée structure batch
        paramRenouvellement.setNbrCptPlac(nbrCptPlac);
        paramRenouvellement.setSommePlacement(sommePlacement);
        paramRenouvellement.setFinBatchStructure(finBatch);
        this.nbrCptPlac = nbrCptPlac;
        this.sommePlacement = sommePlacement;
        }catch (Exception e) {
            
            StringBuffer text = new StringBuffer("Erreur dans Renouvellement-perf : ");
            text.append(e.getMessage());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            System.out.println(text.toString());
            erreur.setKey("Renouvellement-perf");
            logger.error("Exception : ",e);   
      //      gestionException(paramRenouvellement.getDateComptRenouvel(),agence,e.getMessage()); 
            paramRenouvellement.addError(erreur);
            throw new RuntimeException(e);
        }    
    return paramRenouvellement;
    }
    public void genCroText(ValueObject vo) {
        ParamContratPlacement paramRenouvellement = (ParamContratPlacement)vo;
                  /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

                   Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                            com.oxia.security.abc.model.Personnel user = null;
                            if (obj instanceof UserDetails) {
                                user = (com.oxia.security.abc.model.Personnel)obj;
                           }
                  
                  this.setNumRefCro(Long.valueOf("99999"));
                  this.setLibRefCro("SMILE.Placement.BatRenouv");
                  this.setDatValCro(dateComptRenouvel);
                  this.setCodeStructInitiatrice(Constants.COD_STRUCT_INIT_BATCH_PLACEMENT);              
                  this.setCodStrcImpt(Long.valueOf(Constants.COD_STRUCT_INIT_BATCH_PLACEMENT));
                  this.setCodEtatCro(0);              
                  this.setCodeProduit(Constants.COD_DOM_PLACEMENT.toString());
                  this.setOperationId(String.valueOf(Constants.COD_OPERATION_FIN_BATCH));
                  this.setDateOperation(new Date());
                  SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
                  formater=new SimpleDateFormat("HH:mm:ss");
                  String heureString = formater.format(new Date());
                  this.setHeureOperation(heureString);
                  this.setTypeOperationCro("B");
                  this.setCodTachTach(Constants.COD_TACHE_VALID_PLAC);
                  this.setDatExecCro(new Date());
                  this.setCodRefcOmp("Fin Renouv");
                  this.setNumCinUser(user.getNumMatrUser());
                  this.setCodTypUser(user.getMatriculeTyp());
                  
                     /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
                StringBuffer cro=new StringBuffer("");
                    
                cro.append("NombreContratsRenouveles=");
                cro.append(nbrCptPlac+";");               
                    
                cro.append("SommeMontantPlacement=");
                cro.append(sommePlacement.longValue() +";");

                 
                this.setCroText(cro.toString());

                System.out.println("  ");
                System.out.println("  /*************** insert CRO renouvellement placement *****************/");
                System.out.println("  ");

    } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
    }   
    private void gestionStatistique(ParamContratPlacement paramRenouvellement, Long codeAgence) {
    
        BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
        Structure agence =new Structure();
        agence.setCodStrcStrc(codeAgence);
        batchStatPlacement.setCodEtatBats("V");
        batchStatPlacement.setDatSystBats(new Date());
        batchStatPlacement.setDatCompBats(paramRenouvellement.getDateComptRenouvel());
        batchStatPlacement.setStructure(agence);
        BatchMetier batchMetier = new BatchMetier();
        batchMetier.setCodBatBmet(Constants.COD_BATCH_RENOUVEL);
        batchStatPlacement.setBatchMetier(batchMetier);
        batchStatPlacement.setLibExtrBats(paramRenouvellement.getNbrCptPlac()+" Contrats renouvelés/"+paramRenouvellement.getNbrCptPlacRenouvelable()+" Contrats renouvelables, et celà pour la somme de : "+(paramRenouvellement.getSommePlacement().longValue())+" Dinars");
        BatchService batchService= (BatchService) context.getBean("batchService");
        batchStatPlacement = (BatchStatPlacement)batchService.InsertBatchStatPlacement(batchStatPlacement);
    } 
    private void gestionException(Date dateComptable, Structure agence, String description) {
    
        BatchExeptionPlac batchExeptionPlac  = new BatchExeptionPlac();
        batchExeptionPlac.setDatSystBate(new Date());
        batchExeptionPlac.setDatCompBate(dateComptable);
        batchExeptionPlac.setStructure(agence);
        batchExeptionPlac.setLibTpbmBate("Exception Batch Renouvellement à échéance");
        batchExeptionPlac.setLibExpBate(description);
        BatchService batchService= (BatchService) context.getBean("batchService");
        batchExeptionPlac = (BatchExeptionPlac)batchService.InsertBatchExeptionPlac(batchExeptionPlac);
    }  
  private ParamContratPlacement renouvelerContratPlacement(DemandeDecision demandeRenouvel, ParamContratPlacement paramRenouvellement){
      ContratPlacement nouvContratPlacement =new ContratPlacement();
      com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
      TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
      try{
          Personne personne = new Personne();
          Client client = new Client();
          ContratCpt contratCpt = demandeRenouvel.getContratCpt();
          
          GetContratCptByIdTrt getContratCptByIdTrt =new GetContratCptByIdTrt();
          ContratCptId ccptId = new ContratCptId();
          ccptId.setCodPrdPrd(contratCpt.getContratCptId().getCodPrdPrd());
          ccptId.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc());
          ccptId.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt());
          ContratCpt contratCompte = new ContratCpt();
          contratCompte.setContratCptId(ccptId);
          getContratCptByIdTrt.setVerifDomaine(false);
          paramRenouvellement.setTypeOperation("");
          ContratCpt ccpt = (ContratCpt)getContratCptByIdTrt.exec(contratCompte);
          contratCompte =null;
          ccptId =null;
          if(ccpt != null){
          // Verifier Si l etat du contrat compte est toujours valide et vérifier la provision (le solde)
          if(ccpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)){
             if(demandeRenouvel.getMontPlaDemd() > ccpt.getProvision(paramRenouvellement.getDateComptRenouvel())){
                    StringBuffer descriptionError =new StringBuffer("");
                    descriptionError.append(" La provision du compte de la demande ");
                    descriptionError.append(demandeRenouvel.getNumRefdDemd());
                    descriptionError.append(" ne permet pas de poursuivre le renouvellement : Provision =  ");
                    if(ccpt.getProvision(paramRenouvellement.getDateComptRenouvel()) < 0){ descriptionError.append("- ");
                     }else {descriptionError.append("+ ");}
                    descriptionError.append(StrHandler.formatmnt(Double.valueOf(ccpt.getProvision(paramRenouvellement.getDateComptRenouvel()).toString())));
                    descriptionError.append(" ; Montant placement = ");descriptionError.append(StrHandler.formatmnt(Double.valueOf(demandeRenouvel.getMontPlaDemd().toString())));
                    logger.error(descriptionError.toString());
               /*     erreur.setDescription(descriptionError.toString());
                    paramRenouvellement.addError(erreur);*/
                if(ccpt.getProduit().getCodTprdPrd().equals("DCV")
                    || ccpt.getContratCptId().getCodPrdPrd().equals(Long.valueOf(103))
                    || ccpt.getContratCptId().getCodPrdPrd().equals(Long.valueOf(121))
                    ){
                        // compte 103, 121 et dinars convertibles, pas de forçage rejet demande
                         paramRenouvellement.setTypeOperation("REJET");
                         demandeRenouvel.setLibRmqDemd(descriptionError.toString());
                         paramRenouvellement.setDemandeDecision(demandeRenouvel);
                         paramRenouvellement.setOperationForce(false);
                    }else {
                        // autre compte
                      // OPERATION FORCE
                      paramRenouvellement.setOperationForce(true);
                      paramRenouvellement.setTypeOperation("");
                    }
                }else {
                    paramRenouvellement.setOperationForce(false);
                    paramRenouvellement.setTypeOperation("");
                    nouvContratPlacement.setNumSeqCpla(demandeRenouvel.getNumRefdDemd());
                    nouvContratPlacement.setContratCpt(ccpt);
                    client = ccpt.getClient();
                    personne.setNumSeqPers(client.getNumSeqPers());
                    nouvContratPlacement.setPersonne(personne);
                    nouvContratPlacement.setDemandeDecision(demandeRenouvel);
                    nouvContratPlacement.setDatCreCpla(paramRenouvellement.getDateComptRenouvel());
                    nouvContratPlacement.setDatVldCpla(paramRenouvellement.getDateComptRenouvel());
                    ProduitPlacement prdPlac = demandeRenouvel.getProduitPlacement();
                    if(prdPlac.getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
                           || prdPlac.getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
                            ){
                              nouvContratPlacement.setNumBcCpla(demandeRenouvel.getNumBcDemd());
                               }
                    nouvContratPlacement.setProduitPlacement(prdPlac);
                       int nbreJour= Integer.parseInt(demandeRenouvel.getNumDureDemd().toString());
                        Date dateSousc = demandeRenouvel.getDatValDemd();
                        nouvContratPlacement.setDatEcheCpla(DateHandler.addJour(dateSousc,nbreJour -1 ));
                        nouvContratPlacement.setNumNbrjCpla(demandeRenouvel.getNumDureDemd());
                        nouvContratPlacement.setMontCapCpla(demandeRenouvel.getMontPlaDemd());
                        nouvContratPlacement.setMontActuCpla(demandeRenouvel.getMontPlaDemd());
                        nouvContratPlacement.setCodPintCpla(demandeRenouvel.getCodPintDemd());
                        nouvContratPlacement.setNumTircCpla(demandeRenouvel.getNumTircDemd());
                       // taux d interet selon les conditions de banque
                        if(demandeRenouvel.getCodFavDemd().equals(Constants.COD_FAV_GENERAL)){
                             // demande général
                               nouvContratPlacement.setCodFavCpla(Constants.COD_FAV_GENERAL);
                                 
                                   if(demandeRenouvel.getProduitPlacement() != null){
                                    String codPrd = demandeRenouvel.getProduitPlacement().getCodPrdPlc().toString();
                                    String dureeGen=demandeRenouvel.getNumDureDemd().toString();
                                    
									 // appel conditions de banque general opération renouvellement
                                         traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_RENOUVEL_PLAC_AVAN.toString() ,DateHandler.dateToStr(paramRenouvellement.getContratPlacement().getDatEcheCpla()),dureeGen);
                                         paramRenouvellement.setDateValeur(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));
                                  
                                   if(demandeRenouvel.getCodPintDemd().equals("PRE")){
                                   // appel conditions de banque general opération versement interet pré compté (320)
                                    traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() ,DateHandler.dateToStr(demandeRenouvel.getDatValDemd()),dureeGen);
                                    if(!traitementConditionBanque.getTauxInteret().equals("NAN")){
                                    nouvContratPlacement.setNumTauiCpla(Double.valueOf(traitementConditionBanque.getTauxInteret()));
                                    nouvContratPlacement.setCodMargCpla(traitementConditionBanque.getSigneMarge());
                                    nouvContratPlacement.setNumMargCpla(Double.valueOf(traitementConditionBanque.getValeurMarge()));
                                    paramRenouvellement.setInteretServi(affecterDonneesInteretServi(nouvContratPlacement,DateHandler.strToDate(traitementConditionBanque.getDatevaleur()),paramRenouvellement.getDateComptRenouvel()));
                                    }else {
                                        StringBuffer descriptionError =new StringBuffer("");
                                        descriptionError.append("Agence : ");descriptionError.append(ccpt.getStructure().getCodStrcStrc().toString()); descriptionError.append(ccpt.getStructure().getLibStrcStrc());
                                        descriptionError.append(" Aucune condition de banque générale à appliquer pour l'opération 320 et le produit ");descriptionError.append(codPrd);
                                        logger.error(descriptionError.toString());
                                        erreur.setDescription(descriptionError.toString());
                                        System.out.println(descriptionError.toString());
                                        paramRenouvellement.addError(erreur);
                                    }
                                      }else{
                                           // appel conditions de banque general opération versement interet post compté (613)
                                            traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , DateHandler.dateToStr(demandeRenouvel.getDatValDemd()),dureeGen);
                                           if(!traitementConditionBanque.getTauxInteret().equals("NAN")){
                                           nouvContratPlacement.setNumTauiCpla(Double.valueOf(traitementConditionBanque.getTauxInteret()));
                                           nouvContratPlacement.setCodMargCpla(traitementConditionBanque.getSigneMarge());
                                           nouvContratPlacement.setNumMargCpla(Double.valueOf(traitementConditionBanque.getValeurMarge()));
                                           paramRenouvellement.setInteretServi(affecterDonneesInteretServi(nouvContratPlacement,DateHandler.strToDate(traitementConditionBanque.getDatevaleur()),paramRenouvellement.getDateComptRenouvel()));
                                           }else {
                                               StringBuffer descriptionError =new StringBuffer("");
                                               descriptionError.append("Agence : ");descriptionError.append(ccpt.getStructure().getCodStrcStrc().toString()); descriptionError.append(ccpt.getStructure().getLibStrcStrc());
                                               descriptionError.append(" Verifier la date de référence. Aucune condition de banque générale à appliquer pour l'opération 613 et le produit ");descriptionError.append(codPrd);
                                               logger.error(descriptionError.toString());
                                               erreur.setDescription(descriptionError.toString());
                                               System.out.println(descriptionError.toString());
                                               paramRenouvellement.addError(erreur);
                                           }
                                       }
                                    codPrd= null;
                                    }
                                }else if(demandeRenouvel.getCodFavDemd().equals(Constants.COD_FAV_FAVEUR)){ //faveur (taux fixe)
                                   nouvContratPlacement.setCodFavCpla(Constants.COD_FAV_FAVEUR);
                                         // appel conditions de banque opération renouvellement
                                         traitementConditionBanque = appelConditionBanque(demandeRenouvel, Constants.OPER_RENOUVEL_PLAC_AVAN.toString(),paramRenouvellement.getContratPlacement().getDatEcheCpla());
                                         paramRenouvellement.setDateValeur(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));
                                             if(demandeRenouvel.getCodPintDemd().equals("PRE")){
                                         // appel conditions de banque opération versement interet pré compté (320)
                                          traitementConditionBanque = appelConditionBanque(demandeRenouvel, Constants.OPER_INT_PRE_SOUSC_PLAC.toString(),demandeRenouvel.getDatValDemd());
                                         if(!traitementConditionBanque.getTauxInteret().equals("NAN")){
                                          nouvContratPlacement.setNumTauiCpla(Double.valueOf(traitementConditionBanque.getTauxInteret()));
                                                     paramRenouvellement.setInteretServi(affecterDonneesInteretServi(nouvContratPlacement,DateHandler.strToDate(traitementConditionBanque.getDatevaleur()),paramRenouvellement.getDateComptRenouvel()));
                                                 }else {
                                                     StringBuffer descriptionError =new StringBuffer("");
                                                     descriptionError.append("Agence : ");descriptionError.append(ccpt.getStructure().getCodStrcStrc().toString()); descriptionError.append(ccpt.getStructure().getLibStrcStrc());
                                                     descriptionError.append(" Verifier la date de référence. Aucune condition de banque de faveur (taux fixe) à appliquer pour l'opération 320 et la demande ");descriptionError.append(demandeRenouvel.getNumRefdDemd().toString());
                                                     logger.error(descriptionError.toString());
                                                     erreur.setDescription(descriptionError.toString());
                                                     paramRenouvellement.addError(erreur);
                                                 } 
                                             }else{
                                                 // appel conditions de banque opération versement interet post compté (613) :: aucune insertion des inetrets au niveau d la base
                                                 traitementConditionBanque = appelConditionBanque(demandeRenouvel, Constants.OPER_INT_POST_SOUSC_PLAC.toString(),demandeRenouvel.getDatValDemd());
                                                    if(!traitementConditionBanque.getTauxInteret().equals("NAN")){
                                                     nouvContratPlacement.setNumTauiCpla(Double.valueOf(traitementConditionBanque.getTauxInteret()));
                                                        paramRenouvellement.setInteretServi(affecterDonneesInteretServi(nouvContratPlacement,DateHandler.strToDate(traitementConditionBanque.getDatevaleur()),paramRenouvellement.getDateComptRenouvel()));
                                                    }else {
                                                        StringBuffer descriptionError =new StringBuffer("");
                                                        descriptionError.append("Agence : ");descriptionError.append(ccpt.getStructure().getCodStrcStrc().toString()); descriptionError.append(ccpt.getStructure().getLibStrcStrc());
                                                        descriptionError.append(" Verifier la date de référence.Aucune condition de banque de faveur (taux fixe) à appliquer pour l'opération 613 et la demande ");descriptionError.append(demandeRenouvel.getNumRefdDemd().toString());
                                                        logger.error(descriptionError.toString());
                                                        erreur.setDescription(descriptionError.toString());
                                                        paramRenouvellement.addError(erreur);
                                                    } 
                                                }
                                    }else if(demandeRenouvel.getCodFavDemd().equals(Constants.COD_FAV_INDEXE)) {
                                             // demande avec taux indexé au TMM
                                         nouvContratPlacement.setCodFavCpla(Constants.COD_FAV_INDEXE); 
                                             // appel conditions de banque opération renouvellement
                                              traitementConditionBanque = appelConditionBanque(demandeRenouvel, Constants.OPER_RENOUVEL_PLAC_AVAN.toString(),paramRenouvellement.getContratPlacement().getDatEcheCpla());
                                              paramRenouvellement.setDateValeur(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));
                                              if(demandeRenouvel.getCodPintDemd().equals("PRE")){
                                                 // appel conditions de banque opération versement interet pré compté (320)
                                                  traitementConditionBanque = appelConditionBanque(demandeRenouvel, Constants.OPER_INT_PRE_SOUSC_PLAC.toString(),demandeRenouvel.getDatValDemd());
                                                         if(!traitementConditionBanque.getTauxInteret().equals("NAN")){
                                                         nouvContratPlacement.setNumTauiCpla(Double.valueOf(traitementConditionBanque.getTauxInteret()));
                                                         nouvContratPlacement.setCodMargCpla(traitementConditionBanque.getSigneMarge());
                                                         nouvContratPlacement.setNumMargCpla(Double.valueOf(traitementConditionBanque.getValeurMarge()));
                                                         paramRenouvellement.setInteretServi(affecterDonneesInteretServi(nouvContratPlacement,DateHandler.strToDate(traitementConditionBanque.getDatevaleur()),paramRenouvellement.getDateComptRenouvel())); }else {
                                                             StringBuffer descriptionError =new StringBuffer("");
                                                             descriptionError.append("Agence : ");descriptionError.append(ccpt.getStructure().getCodStrcStrc().toString()); descriptionError.append(ccpt.getStructure().getLibStrcStrc());
                                                             descriptionError.append("Verifier la date de référence. Aucune condition de banque pour le taux indexé au TMM, à appliquer pour l'opération 320 et la demande ");descriptionError.append(demandeRenouvel.getNumRefdDemd().toString());
                                                             logger.error(descriptionError.toString());
                                                             erreur.setDescription(descriptionError.toString());
                                                             paramRenouvellement.addError(erreur);
                                                         } 
                                                     }else{
                                                        // appel conditions de banque opération versement interet post compté (613) :: aucune insertion des inetrets au niveau d la base
                                                          traitementConditionBanque = appelConditionBanque(demandeRenouvel, Constants.OPER_INT_POST_SOUSC_PLAC.toString(),demandeRenouvel.getDatValDemd());
                                                        if(!traitementConditionBanque.getTauxInteret().equals("NAN")){
                                                          nouvContratPlacement.setNumTauiCpla(Double.valueOf(traitementConditionBanque.getTauxInteret()));
                                                          // ajout sign e marge !!
                                                          nouvContratPlacement.setCodMargCpla(traitementConditionBanque.getSigneMarge());
                                                          nouvContratPlacement.setNumMargCpla(Double.valueOf(traitementConditionBanque.getValeurMarge()));
                                                          paramRenouvellement.setInteretServi(affecterDonneesInteretServi(nouvContratPlacement,DateHandler.strToDate(traitementConditionBanque.getDatevaleur()),paramRenouvellement.getDateComptRenouvel()));}else {
                                                            StringBuffer descriptionError =new StringBuffer("");
                                                            descriptionError.append("Agence : ");descriptionError.append(ccpt.getStructure().getCodStrcStrc().toString()); descriptionError.append(ccpt.getStructure().getLibStrcStrc());
                                                            descriptionError.append("Verifier la date de référence. Aucune condition de banque pour le taux indexé au TMM, à appliquer pour l'opération 613 et la demande ");descriptionError.append(demandeRenouvel.getNumRefdDemd().toString());
                                                            logger.error(descriptionError.toString());
                                                            erreur.setDescription(descriptionError.toString());
                                                            paramRenouvellement.addError(erreur);
                                                        } 
                                                    }
                                            
                                          }else if(demandeRenouvel.getCodFavDemd().equals(Constants.COD_FAV_PREFERENTIEL)) {
                                             // demande avec taux préférentiel
                                            nouvContratPlacement.setCodFavCpla(Constants.COD_FAV_PREFERENTIEL); 
                                             // appel conditions de banque opération renouvellement
                                              traitementConditionBanque = appelConditionBanque(demandeRenouvel, Constants.OPER_RENOUVEL_PLAC_AVAN.toString(),paramRenouvellement.getContratPlacement().getDatEcheCpla());
                                              paramRenouvellement.setDateValeur(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));
                                              if(demandeRenouvel.getCodPintDemd().equals("PRE")){
                                                 // appel conditions de banque opération versement interet pré compté (320)
                                                  traitementConditionBanque = appelConditionBanque(demandeRenouvel, Constants.OPER_INT_PRE_SOUSC_PLAC.toString(),demandeRenouvel.getDatValDemd());
                                                       if(!traitementConditionBanque.getTauxInteret().equals("NAN")){
                                                  nouvContratPlacement.setNumTauiCpla(Double.valueOf(traitementConditionBanque.getTauxInteret()));
                                                  nouvContratPlacement.setCodMargCpla(traitementConditionBanque.getSigneMarge());
                                                  nouvContratPlacement.setNumMargCpla(Double.valueOf(traitementConditionBanque.getValeurMarge()));
                                                  paramRenouvellement.setInteretServi(affecterDonneesInteretServi(nouvContratPlacement,DateHandler.strToDate(traitementConditionBanque.getDatevaleur()),paramRenouvellement.getDateComptRenouvel())); 
                                                  }else {
                                                           StringBuffer descriptionError =new StringBuffer("");
                                                           descriptionError.append("Agence : ");descriptionError.append(ccpt.getStructure().getCodStrcStrc().toString()); descriptionError.append(ccpt.getStructure().getLibStrcStrc());
                                                           descriptionError.append("Verifier la date de référence.Aucune condition de banque préférentielle à appliquer pour l'opération 320 et la demande ");descriptionError.append(demandeRenouvel.getNumRefdDemd().toString());
                                                           logger.error(descriptionError.toString());
                                                           erreur.setDescription(descriptionError.toString());
                                                           paramRenouvellement.addError(erreur);
                                                       } 
                                                   }else{
                                                         // appel conditions de banque opération versement interet post compté (613) :: aucune insertion des inetrets au niveau d la base
                                                          traitementConditionBanque = appelConditionBanque(demandeRenouvel, Constants.OPER_INT_POST_SOUSC_PLAC.toString(),demandeRenouvel.getDatValDemd());
                                                        if(!traitementConditionBanque.getTauxInteret().equals("NAN")){
                                                         nouvContratPlacement.setNumTauiCpla(Double.valueOf(traitementConditionBanque.getTauxInteret()));
                                                         nouvContratPlacement.setCodMargCpla(traitementConditionBanque.getSigneMarge());
                                                         nouvContratPlacement.setNumMargCpla(Double.valueOf(traitementConditionBanque.getValeurMarge()));
                                                         paramRenouvellement.setInteretServi(affecterDonneesInteretServi(nouvContratPlacement,DateHandler.strToDate(traitementConditionBanque.getDatevaleur()),paramRenouvellement.getDateComptRenouvel()));}else {
                                                            StringBuffer descriptionError =new StringBuffer("");
                                                            descriptionError.append("Agence : ");descriptionError.append(ccpt.getStructure().getCodStrcStrc().toString()); descriptionError.append(ccpt.getStructure().getLibStrcStrc());
                                                            descriptionError.append("Verifier la date de référence.Aucune condition de banque préférentielle à appliquer pour l'opération 613 et la demande ");descriptionError.append(demandeRenouvel.getNumRefdDemd().toString());
                                                            logger.error(descriptionError.toString());
                                                            erreur.setDescription(descriptionError.toString());
                                                            paramRenouvellement.addError(erreur);
                                                        } 
                                                    }
                                            
                                          }else {
                                             StringBuffer descriptionError =new StringBuffer("");
                                             descriptionError.append("Agence : ");descriptionError.append(ccpt.getStructure().getCodStrcStrc().toString()); descriptionError.append(ccpt.getStructure().getLibStrcStrc());
                                             descriptionError.append("Le type de faveur est vide, pour la demande ");descriptionError.append(demandeRenouvel.getNumRefdDemd().toString());
                                             logger.error(descriptionError.toString());
                                             erreur.setDescription(descriptionError.toString());
                                             paramRenouvellement.addError(erreur);
                                         }
            //    nouvContratPlacement.setDatValCpla(paramRenouvellement.getDateValeur());
             nouvContratPlacement.setDatValCpla(demandeRenouvel.getDatValDemd());
              
                    nouvContratPlacement.setCodEtatCpla(Constants.ETAT_CONTRAT_PLAC_VALIDE); 
                    nouvContratPlacement.setCodErenCpla(Long.valueOf("0"));
                    
                  if(demandeRenouvel.getCodPintDemd().equals("POST")){
                      // tester si la durée dépasse une année, remplir la prochaine date d pay d int
                       GregorianCalendar calendrier = new GregorianCalendar();
                       calendrier.setTime(demandeRenouvel.getDatValDemd());
                       if(!demandeRenouvel.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)){
                           calendrier.add(GregorianCalendar.DATE,365);
                       }else {
                           calendrier.add(GregorianCalendar.DATE,364); // cas BNA placement ou contrat renouvelé
                       }
                      
                      if(nouvContratPlacement.getDatEcheCpla().after(calendrier.getTime())){
                          nouvContratPlacement.setDatPintCpla(DateHandler.strToDate(DateHandler.dateToStr(calendrier.getTime())));
                       }
                      }                    // liaison avec l ancien contrat placement renouvelable
                    ContratPlacement contratPlacementRenouvele = new ContratPlacement();
                    contratPlacementRenouvele.setNumSeqCpla(demandeRenouvel.getContratPlacement().getNumSeqCpla());
                    GetContratPlacementTrt getContratPlacementTrt =new GetContratPlacementTrt();
                    getContratPlacementTrt.setVerifDomaine(false);
                    contratPlacementRenouvele = (ContratPlacement)getContratPlacementTrt.exec(contratPlacementRenouvele);
                    nouvContratPlacement.setContratPlacementByNumSqcrCpla(contratPlacementRenouvele);
                    contratPlacementRenouvele.setCodErenCpla(Long.valueOf("2"));
                    UpdateContratPlacementTrt updateContratPlacementTrt = new UpdateContratPlacementTrt();
                updateContratPlacementTrt.setVerifDomaine(false);
                    updateContratPlacementTrt.exec(contratPlacementRenouvele);
                    contratPlacementRenouvele = null;
              }
            
            // autre compte
            }else {
                StringBuffer descriptionError =new StringBuffer("");
                descriptionError.append("Pour l'agence :: ");
                descriptionError.append(ccpt.getStructure().getCodStrcStrc().toString());
                descriptionError.append(" L'état du compte de la demande");descriptionError.append(demandeRenouvel.getNumRefdDemd());
                descriptionError.append(" ne permet pas de poursuivre le renouvellement : Etat du compte = ");descriptionError.append(ccpt.getCodEtatCcpt());
                logger.error(descriptionError.toString());
                erreur.setDescription(descriptionError.toString());
                paramRenouvellement.addError(erreur);
            }
           }else {
               
           }
        paramRenouvellement.setContratPlacement(null);
        paramRenouvellement.setContratPlacement(nouvContratPlacement);
        return paramRenouvellement;
      }catch (Exception e) {
      logger.error("Exception méthode renouvelerContratPlacement : ", e );
          throw new RuntimeException(e);
      }
  }
    private InteretServi affecterDonneesInteretServi(ContratPlacement contratPlacement , Date dateValeur, Date dateComptRenouvel) {

        InteretServi interetServi = new InteretServi();
     
        try{
        interetServi.setDatIsrvIsrv(dateComptRenouvel);
        interetServi.setDatValIsrv(dateValeur);
        // date valeur a partir CB !!
        Long montantInt = new Long(0);
        Long codProduit = contratPlacement.getProduitPlacement().getCodPrdPlc();
            double montInteret =0;
        if(codProduit.equals(Constants.COD_PRD_BC_PLAC) || codProduit.equals(Constants.COD_PRD_BCDC_PLAC) || codProduit.equals(Constants.COD_PRD_CAT_PLAC) || codProduit.equals(Constants.COD_PRD_CATDC_PLAC)){
            
        if(contratPlacement.getCodPintCpla().equals("PRE")){
             montInteret = Math.round(contratPlacement.getMontCapCpla().doubleValue() * contratPlacement.getNumNbrjCpla().doubleValue() * contratPlacement.getNumTauiCpla().doubleValue()/(36500+ (contratPlacement.getNumNbrjCpla().doubleValue() * contratPlacement.getNumTauiCpla().doubleValue()))) ;
              montantInt =  Long.valueOf(new Double(montInteret).longValue());
            }else {
             montantInt =  Long.valueOf(new Double(Math.round(contratPlacement.getMontCapCpla().doubleValue() * contratPlacement.getNumNbrjCpla().doubleValue() * contratPlacement.getNumTauiCpla().doubleValue() /36500)).longValue());
            }
        }else if(codProduit.equals(Constants.COD_PRD_BNAPLC_PLAC)){
                // cas du produit BNA placement
        	    //Modification de la base de calcul pour le produit BNA placement " de 360 à 365 jours " 30/06/2020
                if(contratPlacement.getCodPintCpla().equals("PRE")){
                    montInteret = Math.round(contratPlacement.getMontCapCpla().doubleValue() * contratPlacement.getNumNbrjCpla().doubleValue() * contratPlacement.getNumTauiCpla().doubleValue()/(36500+ (contratPlacement.getNumNbrjCpla().doubleValue() * contratPlacement.getNumTauiCpla().doubleValue()))) ;
                    montantInt =  Long.valueOf(new Double(montInteret).longValue());
                    }else {
                     montantInt =  Long.valueOf(new Double(Math.round(contratPlacement.getMontCapCpla().doubleValue() * contratPlacement.getNumNbrjCpla().doubleValue() * contratPlacement.getNumTauiCpla().doubleValue() /36500)).longValue());
                    }
            } else {
                // cas autre produit que BC/BCDC CAT/CATDC BNA placement
                    montantInt =  Long.valueOf(new Double(Math.round(contratPlacement.getMontCapCpla().doubleValue() * contratPlacement.getNumNbrjCpla().doubleValue() * contratPlacement.getNumTauiCpla().doubleValue()/36000)).longValue());
            }
        interetServi.setMontBrutIsrv(montantInt);
        interetServi.setMontIrcIsrv(Long.valueOf(new Double(montInteret * (contratPlacement.getNumTircCpla().doubleValue() / 100)).longValue()));
        
        interetServi.setMontIsrvIsrv(montantInt - interetServi.getMontIrcIsrv());
       
    //    paramRenouvellement.setInteretServi(interetServi);
        
        } catch (Exception e) {
               logger.error("Exception Methode : affecterDonnéesInteretServi:  ",e);  
               throw new RuntimeException(e);               
        } 
        return interetServi;
    }
     private TraitementConditionBanque appelConditionBanqueGeneral(String codeProduit , String codeOperation , String dateReference,String duree){
         
         TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
         
         StringBuffer str = new StringBuffer();
         str.append("Exception Methode : appelConditionBanqueGeneral: ");
       try{  
        
             traitementConditionBanque.setCodPrdPrd(codeProduit);
             traitementConditionBanque.setCodOperOper(codeOperation);
         
             traitementConditionBanque.setNumCcptCcpt("");
             traitementConditionBanque.setCodStrcStrc("");
             traitementConditionBanque.setCodPrdCpt("");
             traitementConditionBanque.setCodTpceTpce("");
             traitementConditionBanque.setNumPcePers("");
             traitementConditionBanque.setIdContrat("");
             traitementConditionBanque.setMontant("");
             traitementConditionBanque.setNbUnites(duree);
             traitementConditionBanque.setDateReference(dateReference);
               
         traitementConditionBanque.getCB();
         } catch (Exception e) {
                    logger.error(str.toString(),e);  
                    throw new RuntimeException(e);               
             } 
         return traitementConditionBanque;
     }
     
    private TraitementConditionBanque appelConditionBanque(DemandeDecision demandeRenouvel, String codeOp, Date dateRef){
        
        TraitementConditionBanque traitementConditionBanque= new TraitementConditionBanque();
        
        DemandeDecision dmdeRenouvel = demandeRenouvel;
        StringBuffer str = new StringBuffer();
        str.append("Exception Methode : appelConditionBanque: ");
      try{  
            ContratCpt contratCpt = demandeRenouvel.getContratCpt();
            
            GetContratCptByIdTrt getContratCptByIdTrt =new GetContratCptByIdTrt();
            ContratCptId ccptId = new ContratCptId();
            ccptId.setCodPrdPrd(contratCpt.getContratCptId().getCodPrdPrd());
            ccptId.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc());
            ccptId.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt());
            ContratCpt contratCompte = new ContratCpt();
            contratCompte.setContratCptId(ccptId);
            getContratCptByIdTrt.setVerifDomaine(false);
            ContratCpt ccpt = (ContratCpt)getContratCptByIdTrt.exec(contratCompte);
            contratCompte =null;
            ccptId =null;
            contratCpt = null;
        traitementConditionBanque.setCodPrdPrd(dmdeRenouvel.getProduitPlacement().getCodPrdPlc().toString());
        traitementConditionBanque.setCodOperOper(codeOp);
        traitementConditionBanque.setNumCcptCcpt(ccpt.getContratCptId().getNumCcptCcpt().toString());
        traitementConditionBanque.setCodStrcStrc(ccpt.getContratCptId().getCodStrcStrc().toString());
        traitementConditionBanque.setCodPrdCpt(ccpt.getContratCptId().getCodPrdPrd().toString());
        traitementConditionBanque.setCodTpceTpce(ccpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
        traitementConditionBanque.setNumPcePers(ccpt.getClient().getPersonne().getNumPcePers());
        traitementConditionBanque.setIdContrat(dmdeRenouvel.getNumRefdDemd().toString());
        traitementConditionBanque.setMontant(dmdeRenouvel.getMontPlaDemd().toString());
        traitementConditionBanque.setNbUnites(dmdeRenouvel.getNumDureDemd().toString());
        traitementConditionBanque.setDateReference(DateHandler.dateToStr(dateRef));
          
        traitementConditionBanque.getCB();
        } catch (Exception e) {
                   logger.error(str.toString(),e);  
                   throw new RuntimeException(e);               
            } 
        return traitementConditionBanque;
    }

    

}
