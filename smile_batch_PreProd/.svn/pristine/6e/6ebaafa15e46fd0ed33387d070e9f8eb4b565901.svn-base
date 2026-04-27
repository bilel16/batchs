package com.bna.smile.model.domaineplacement.traitement;


import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.JourneeStructureBatchId;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;
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

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import org.apache.commons.collections.map.ListOrderedMap;

public class IntretServiTrt  extends Traitement{
    public IntretServiTrt() {
    }
        
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
       
       // à ne pas laisser en variable global
        ICriteria criteria = searchEngine.createCriteria();
        ICriteria criteriaAvanc = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        

    public IValueObject perform(IValueObject vo) {
        this.setCroFlag(true); 
        
        Date dateComptableAgence = null;
        try{


        Structure agence = new Structure();
        PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
        List listAgencesPlacement = plcDao.getListAgencesPlacement();
        ListOrderedMap ListAgPlc = null;
            if(listAgencesPlacement!=null && listAgencesPlacement.size()>0) {
                for (Iterator it1 = listAgencesPlacement.iterator(); it1.hasNext(); ){
                    ListAgPlc = (ListOrderedMap)it1.next();
                    
                    if ((ListAgPlc.getValue(0)).toString() != null) {
                        agence.setCodStrcStrc(Long.valueOf(ListAgPlc.getValue(0).toString()));
                    }
                    if ((ListAgPlc.getValue(1)).toString() != null) {
                        ListAgPlc.getValue(1);
                        dateComptableAgence=DateHandler.strToDate(ListAgPlc.getValue(1).toString());
                    }
                    
                    // tester si la journée batch n'est pas dejà inserée
                     JourneeStructureBatch journeeStructureBatch = new JourneeStructureBatch();
                     JourneeStructureBatch journeeStructureBatchRetour = new JourneeStructureBatch();
                     JourneeStructureBatchId journeeStructureBatchId = new JourneeStructureBatchId();
                     journeeStructureBatchId.setCodStrcStrc(agence.getCodStrcStrc());
                     journeeStructureBatchId.setDatJrnJrn(dateComptableAgence);
                     journeeStructureBatchId.setCodBatBmet(Constants.COD_BATCH_INTERET_SERVI);
                     journeeStructureBatch.setJourneeStructureBatchId(journeeStructureBatchId);
                    // tester si la journée n'est pas dejà inserée
                     BatchService batchService= (BatchService) context.getBean("batchService");
                    journeeStructureBatchRetour = (JourneeStructureBatch)batchService.getJourneeStructureBatch(journeeStructureBatch);  
                    if( journeeStructureBatchRetour != null && journeeStructureBatchRetour.getCodStatJsb().intValue() == 0){ // structure non traitée
                         PlacementService placementService = (PlacementService)context.getBean("placementService");
                         ParamInteretServi paramInteretServi = new ParamInteretServi();
                         paramInteretServi.setStructure(agence);
                         paramInteretServi.setDateComptableAgence(dateComptableAgence);
                         paramInteretServi = (ParamInteretServi) placementService.interetServiAgence(paramInteretServi);
                             // journée batch OK
                              journeeStructureBatch.setDatCloJsb(new Date());
                              journeeStructureBatch.setCodStatJsb(Long.valueOf("1"));
                              journeeStructureBatch = (JourneeStructureBatch)batchService.updateJourneeStructureBatch(journeeStructureBatch);   
                            
                     }else {
                      logger.error("\n*******************Journée batch dejà insérée pour l'agence "+agence.getCodStrcStrc()+"\n******************");
                    }
                    
                    
                    
                    
                    
                   
                    

                }
            }

        return null;
        }catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans IntretServiTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("IntretServiTrt");
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);
                
        }
    }


    public void genCroText(ValueObject vo) {
    

          /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

           Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    com.oxia.security.abc.model.Personnel user = null;
                    if (obj instanceof UserDetails) {
                        user = (com.oxia.security.abc.model.Personnel)obj;
                   }
          
          this.setNumRefCro(Long.valueOf("99999"));
          this.setLibRefCro("SMILE.Placement.BatchInteretServi");
          this.setDatValCro(new Date());
          this.setCodeStructInitiatrice("900");              
          this.setCodStrcImpt(Long.valueOf("900"));
          this.setCodEtatCro(0);              
          this.setCodeProduit(Constants.COD_DOM_PLACEMENT.toString());
          this.setOperationId(String.valueOf(Constants.COD_OPERATION_FIN_BATCH));
          this.setDateOperation(new Date());
          SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
          formater=new SimpleDateFormat("HH:mm:ss");
          String heureString = formater.format(new Date());
          this.setHeureOperation(heureString);
          this.setTypeOperationCro("0");
          this.setCodTachTach(1);
         /// if (avancRembLiquid.getNumSeqArl()!=null)
          this.setCodRefcOmp(" ");
          this.setDatExecCro(new Date());

          this.setNumCinUser(user.getNumMatrUser());
          this.setCodTypUser(user.getMatriculeTyp());
          //this.setCodTypUser();  
          //this.setNumCinUser();
          
             /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
        StringBuffer cro=new StringBuffer("");
            
            // code operation batch 
        cro.append("codeOperationBatch=");
        cro.append(Constants.COD_OPER_VERSEMENT_INTERET_PLAC_POST.toString()+";");               

        this.setCroText(cro.toString());
        logger.error("\n  /************************************************************/   \n"+
        "\n  /*************** Fin du Batch Interet Servi *****************/   \n"+
        "\n  /************************************************************/   \n");

    } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
    }  
    
   
}    
