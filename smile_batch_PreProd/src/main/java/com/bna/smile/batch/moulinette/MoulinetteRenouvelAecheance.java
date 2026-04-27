package com.bna.smile.batch.moulinette;


import java.util.Date;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.JourneeStructureId;
import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.domaineplacement.traitement.RenouvellementAEcheanceTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;


public class MoulinetteRenouvelAecheance extends AbstractJob {
    private static final Logger logger =   Logger.getLogger(MoulinetteRenouvelAecheance.class);
    public MoulinetteRenouvelAecheance() {
    }
    public void perform() {
       
        ParamContratPlacement paramContratPlacement = new ParamContratPlacement();
        JourneeStructureId journeeStructureId = new JourneeStructureId();
        JourneeStructure journeeStructure = new JourneeStructure();
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice"); 
        
        try {
        
        PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
                 Long l = plcDao.isBatchExec();
                 if (l.intValue()==0){
                     ///*** verouillage du batch
                     journeeStructureId.setCodStrcStrc(Long.valueOf("900"));
                     journeeStructureId.setDatJrnJrn(DateHandler.addJour(new Date(),2));
                     journeeStructure.setJourneeStructureId(journeeStructureId);
                     journeeStructure.setCodStatJrn(Long.valueOf("0"));
                     journeeStructure.setCodSoldJrn(Long.valueOf("2"));
                     if(!journeeStructure.equals(null)){
                         crudService.create(journeeStructure);
                     } 
            fixerUser();
          //  BatchService batchService = (BatchService)context.getBean("batchService");
            RenouvellementAEcheanceTrt renouvellementAEcheanceTrt = new RenouvellementAEcheanceTrt();
            paramContratPlacement = (ParamContratPlacement)renouvellementAEcheanceTrt.exec(paramContratPlacement);
          
          }else{
                         logger.info(" !!!  Le Batch Renouvellement est en cours d execution. !!!");
                         System.out.println("  /***************  !!!  Le Batch Renouvellement est en cours d execution. !!! *****************/");
                     }
             } catch (Exception e) {
         
            if(paramContratPlacement.getAgence() != null){
            BatchExeptionPlac batchExeptionPlac  = new BatchExeptionPlac();
            batchExeptionPlac.setDatSystBate(new Date());
            if(paramContratPlacement.getDateComptRenouvel() != null){
            batchExeptionPlac.setDatCompBate(paramContratPlacement.getDateComptRenouvel());
            }else {
                batchExeptionPlac.setDatCompBate(DateHandler.strToDate("11/11/1111")); 
            }
            batchExeptionPlac.setStructure(paramContratPlacement.getAgence());
            batchExeptionPlac.setLibTpbmBate("Exception Batch Renouvellement à échéance");
            batchExeptionPlac.setLibExpBate(e.getMessage());
            BatchService batchService = (BatchService)context.getBean("batchService");
            batchExeptionPlac = (BatchExeptionPlac)batchService.InsertBatchExeptionPlac(batchExeptionPlac);
            }
            logger.fatal("**** exception *** : " + this.getClass() + "------"+ e.getMessage());
            System.out.println("  /*************** Fin du batch Renouvellement placement avec des ERREURS *****************/");
        }
        finally{ 
            if (journeeStructure.getCodSoldJrn() != null && journeeStructure.getCodSoldJrn().intValue()==2 ){
                     ICriteria critereDdeDecision = searchEngine.createCriteria();
                     IExpression expression = searchEngine.createExpression();
                     critereDdeDecision.add(expression.eq("codSoldJrn",Long.valueOf("2")));
                     List listJourneeStructure = searchEngine.find(JourneeStructure.class, critereDdeDecision);
                     crudService.remove((JourneeStructure)listJourneeStructure.get(0));
                    System.out.println("  /*************** Fin du batch Renouvellement placement *****************/");
                }
        }
    }
    public void fixerUser() {
            ContextCROHandler.setContext(ContextHandler.getContext());
            
        Personnel user = new Personnel();
        UserManager usermanager = (UserManager)ContextHandler.getContext().getBean("userManager");
        user = usermanager.getUser("9999");
        
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        auth.setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    

}
