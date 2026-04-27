package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertDetailsOpPlacementTrt extends Traitement{
    public InsertDetailsOpPlacementTrt() {
    }
    public IValueObject perform (IValueObject vo ){
     
         ///----------- création détails opération  ----------------------      
         
      DetailsOperationPlacement detailsOperationPlacement  = (DetailsOperationPlacement )vo;     
        DetailsOperationPlacement detailsOpPlac = new DetailsOperationPlacement(); 
       try{ 
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
            
            if(!detailsOperationPlacement.equals(null)){
               crudService.create(detailsOperationPlacement);    
            } 
          }catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("InsertDetailsOpPlacementTrt  "+e.getMessage());;
                detailsOperationPlacement.addError(erreur);
                logger.error("Exception : ",e);   
                throw new   RuntimeException(e);
        } 
        return (detailsOperationPlacement);
    }
    
    public void genCroText(ValueObject vo) {
    
    }  
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);
    }
}
