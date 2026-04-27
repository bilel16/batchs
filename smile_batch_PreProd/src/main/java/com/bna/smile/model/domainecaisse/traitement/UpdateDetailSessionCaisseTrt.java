package com.bna.smile.model.domainecaisse.traitement;

import com.bna.commun.model.DetailSessionCaisse;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateDetailSessionCaisseTrt extends Traitement{

    public UpdateDetailSessionCaisseTrt() {
    }
    
    public IValueObject perform (IValueObject vo ) {     
     
        DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)vo;
        
        try{ 
             this.setCroFlag(false);   
               Context context = ContextHandler.getContext();
               CRUDservice crudService = (CRUDservice)context.getBean("crudservice"); 
               if(detailSessionCaisse!=null){
               crudService.update(detailSessionCaisse);    }            
           }
          catch (Exception e) {
                 com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                 erreur.setCode("Technique");
                 erreur.setDescription("UpdateDetailSessionCaisseTrt  "+e.getMessage());;
                 detailSessionCaisse.addError(erreur);
                 logger.error("Exception : ",e);   
                 throw new RuntimeException(e);
         } 
         return (detailSessionCaisse);
     
    }
    
    public void genCroText(ValueObject vo) {
            
    } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);
    }
    
    /* public IValueObject getNumeroDomaine(IValueObject vo){
       return null;
    }*/
}
