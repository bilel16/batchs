package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.MouvementInterne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertMouvementInterneTrt extends Traitement{
    public InsertMouvementInterneTrt() {
    }
    public IValueObject perform (IValueObject vo ){
        ///----------- création des mouvement internes  ----------------------                    
      MouvementInterne mouvementInterne  = (MouvementInterne )vo;         
       try{ 
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
            
            if(!mouvementInterne.equals(null)){
               crudService.create(mouvementInterne);    
            } 
          }catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("contratPlacementTrt  "+e.getMessage());;
                mouvementInterne.addError(erreur);
                logger.error("Exception : ",e);   
                throw new   RuntimeException(e);
        } 
        return (mouvementInterne);
    }
    
    public void genCroText(ValueObject vo) {
    
    }  
}
