package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateJourneeStructureBatchTrt extends Traitement{
    public UpdateJourneeStructureBatchTrt() {
    }
    
    
    public IValueObject perform (IValueObject vo ){
        ///----------- création journée structure batch  ----------------------                    
      JourneeStructureBatch journeeStructureBatch  = (JourneeStructureBatch )vo;         
       try{ 
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
            
            if(!journeeStructureBatch.equals(null)){
               crudService.update(journeeStructureBatch);    
            } 
          }catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("UpdateJourneeStructureBatchTrt  "+e.getMessage());;
                journeeStructureBatch.addError(erreur);
                logger.error("Exception : ",e);   
                throw new   RuntimeException(e);
        } 
        return (journeeStructureBatch);
    }
    
    public void genCroText(ValueObject vo) {
    
    } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
    }  
}
