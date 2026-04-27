package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateAbonnementPlacementTrt extends Traitement{
    public UpdateAbonnementPlacementTrt() {
    }
    public IValueObject perform (IValueObject vo ){
     
          
      AbonnementPlacement abonnementPlacement  = (AbonnementPlacement)vo;         
       try{ 
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
            
            if(!abonnementPlacement.equals(null)){
               crudService.update(abonnementPlacement);
             } 
          }catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("UpdateAbonnementPlacementTrt  "+e.getMessage());;
                abonnementPlacement.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);
        } 
        return (abonnementPlacement);
    }
    
    public void genCroText(ValueObject vo) {
    
    }  

}
