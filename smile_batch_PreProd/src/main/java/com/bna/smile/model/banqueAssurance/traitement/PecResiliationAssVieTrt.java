package com.bna.smile.model.banqueAssurance.traitement;

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

public class PecResiliationAssVieTrt extends Traitement{
    public PecResiliationAssVieTrt() {
    }
    /**
     * Fonction qui permet la prise en charge d'un nouveau compte de facturation pour une adhésion
     * @Author : Jerbi lamia
     * @since 30/09/2010
     */
     public IValueObject perform (IValueObject vo ){  
         Listes listesAdhesionAssVie = new Listes();
         ParamAdhesion paramAdhesion = (ParamAdhesion)vo; 
         Context context = ContextHandler.getContext();
             DetailAdhesion detailAdhesion=new DetailAdhesion();
     try{
           
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
            //creation d'un nouveau detail
            detailAdhesion.setAdhesionAssVie(paramAdhesion.getAdhesionAssVie());
            detailAdhesion.setCodEtatDadh("R");
            detailAdhesion.setContratCpt(paramAdhesion.getAdhesionAssVie().getContratCpt());
            detailAdhesion.setDatDebDadh(paramAdhesion.getDateComptable());
            crudService.create(detailAdhesion);
            
         return (detailAdhesion); 
        
        }catch(Exception e){
           com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
           StringBuffer text = 
               new StringBuffer("Erreur dans PecResiliationAssVieTrt : ");
           text.append(e.toString());
           erreur.setCode("200");
           erreur.setDescription(text.toString());
           detailAdhesion.addError(erreur);
           return (listesAdhesionAssVie);  
        }
         }
         
         public void genCroText(ValueObject vo){
             }
         public String  getNumeroTache (IValueObject vo) {
               return (Constants.COD_OPER_RESIL_ASS_VIE.toString()+Constants.COD_TACH_PEC);     
           }
}
