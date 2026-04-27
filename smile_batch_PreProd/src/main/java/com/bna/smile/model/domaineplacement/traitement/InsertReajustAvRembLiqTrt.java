package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.ReajustAvrembliq;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertReajustAvRembLiqTrt extends Traitement{
    public InsertReajustAvRembLiqTrt() {
    }
    
    public IValueObject perform (IValueObject vo ){
     
         ///----------- création reajustAvrembliq  ----------------------      
         
        ReajustAvrembliq reajustAvrembliq = (ReajustAvrembliq)vo;
       try{ 
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
            
            if(!reajustAvrembliq.equals(null)){
               crudService.create(reajustAvrembliq);    
            } 
          }catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("InsertReajustAvRembLiqTrt  "+e.getMessage());;
                reajustAvrembliq.addError(erreur);
                logger.error("Exception : ",e);   
                throw new   RuntimeException(e);
        } 
        return (reajustAvrembliq);
    }
    
    public void genCroText(ValueObject vo) {
    
    }  
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);
    }

}
