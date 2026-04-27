package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertContratPlacementTrt extends Traitement{
    public InsertContratPlacementTrt() {
    }
    public IValueObject perform (IValueObject vo ){
     
        ///--------------------------------------------------------------
        ///----------- création contrat placement  ----------------------
        ///--------------------------------------------------------------
          
      ContratPlacement contratPlacement  = (ContratPlacement )vo;         
       try{ 
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
            
            if(!contratPlacement.equals(null)){
               crudService.create(contratPlacement);
             } 
          }catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("contratPlacementTrt  "+e.getMessage());;
                contratPlacement.addError(erreur);
                logger.error("Exception : ",e);   
                throw new   RuntimeException(e);
        } 
        return (contratPlacement);
    }
    
    public void genCroText(ValueObject vo) {
    
    }  
 }

