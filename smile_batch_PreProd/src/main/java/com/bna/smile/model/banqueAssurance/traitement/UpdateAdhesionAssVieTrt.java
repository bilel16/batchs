package com.bna.smile.model.banqueAssurance.traitement;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class UpdateAdhesionAssVieTrt extends Traitement{
    public UpdateAdhesionAssVieTrt() {
    }
    
    /**
     * Fonction qui permet de MAJ d'une adhesion assurance vie 
     * @Author : Y.BOUSSEN
     * @since 16/09/2010
     */
    public IValueObject perform (IValueObject vo ){  
        
        Context context = ContextHandler.getContext();
        AdhesionAssVie adhesionAssVie= (AdhesionAssVie)vo;
    try{
      
        /* MAJ du adhesionAssVie dans la BD */
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.update(adhesionAssVie);
           
       
       }catch(Exception e){
          com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
          StringBuffer text = new StringBuffer("Erreur dans UpdateAdhesionAssVieTrt : ");
          text.append(e.toString());
          erreur.setCode("672");
          erreur.setDescription(text.toString());
          adhesionAssVie.addError(erreur);
       }
            return (adhesionAssVie);

    }
        
        public void genCroText(ValueObject vo){
            }
        public String  getNumeroTache (IValueObject vo) {
              return (Constants.CODE_RESSOURCE_GENERALE);     
          }
}
