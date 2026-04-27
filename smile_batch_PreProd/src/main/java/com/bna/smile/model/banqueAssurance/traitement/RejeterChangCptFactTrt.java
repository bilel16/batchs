package com.bna.smile.model.banqueAssurance.traitement;

import java.util.Iterator;

import com.bna.commun.model.DetailAdhesion;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class RejeterChangCptFactTrt extends Traitement{
    public RejeterChangCptFactTrt() {
    }
    /**
     * Fonction qui permet de rejeter la prise en charge d'un changement de facturation pour une adhésion
     * @Author : Kriaa hatem
     * @since 24/09/2010
     */
     public IValueObject perform (IValueObject vo ){  
         Listes listesAdhesionAssVie = new Listes();
         ParamAdhesion paramAdhesion = (ParamAdhesion)vo; 
         Context context = ContextHandler.getContext();
            
             
     try{
           
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
            //maj des detailAdesions
             for (Iterator it = paramAdhesion.getAdhesionAssVie().getDetailAdhesions().iterator(); 
                  it.hasNext(); ) {
                  DetailAdhesion detailAdhesion = (DetailAdhesion)it.next();
                   if (detailAdhesion.getCodEtatDadh().equalsIgnoreCase("A")){
                      crudService.remove(detailAdhesion);
                  }
                  
                  }
           
            
         return (paramAdhesion); 
        
        }catch(Exception e){
           com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
           StringBuffer text = 
               new StringBuffer("Erreur dans PecChangCptFactTrt : ");
           text.append(e.toString());
           erreur.setCode("200");
           erreur.setDescription(text.toString());
           paramAdhesion.addError(erreur);
           return (paramAdhesion);  
        }
         }
         
         public void genCroText(ValueObject vo){
             }
         public String  getNumeroTache (IValueObject vo) {
               return (Constants.CODE_RESSOURCE_GENERALE);     
           }
}
