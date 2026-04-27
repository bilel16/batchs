package com.bna.smile.model.banqueAssurance.traitement;

import java.util.Iterator;

import com.bna.commun.model.DetailAdhesion;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ValidResiliationAssVieTrt extends Traitement{
    public ValidResiliationAssVieTrt() {
    }
    
    public IValueObject perform (IValueObject vo ){  
        ParamAdhesion paramAdhesion = (ParamAdhesion)vo; 
        Context context = ContextHandler.getContext();
           
            
    try{
           boolean resiliationdirect= true;
           CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
           //maj des detailAdesions
            if(paramAdhesion.getAdhesionAssVie().getDetailAdhesions()!=null){
            
            for (Iterator it = paramAdhesion.getAdhesionAssVie().getDetailAdhesions().iterator(); 
                 it.hasNext(); ) {
                 DetailAdhesion detailAdhesion = (DetailAdhesion)it.next();
                 if (detailAdhesion.getCodEtatDadh().equalsIgnoreCase("V")){
                     detailAdhesion.setCodEtatDadh("H");
                     detailAdhesion.setDatFinDadh(paramAdhesion.getDateComptable());
                     crudService.update(detailAdhesion);
                     ///resiliationdirect=false;
                 }else if (detailAdhesion.getCodEtatDadh().equalsIgnoreCase("R")){
                     detailAdhesion.setDatFinDadh(paramAdhesion.getDateComptable());
                     crudService.update(detailAdhesion);
                     resiliationdirect=false;
                 }
            }
            }
           if(resiliationdirect)   {   ///* si la resiliation est faite par batch (sans p.e.c)
               DetailAdhesion detailAdhesionNew=new DetailAdhesion();           
               detailAdhesionNew.setAdhesionAssVie(paramAdhesion.getAdhesionAssVie());
               detailAdhesionNew.setCodEtatDadh("R");
               detailAdhesionNew.setContratCpt(paramAdhesion.getAdhesionAssVie().getContratCpt());
               detailAdhesionNew.setDatDebDadh(paramAdhesion.getDateComptable());
               detailAdhesionNew.setDatFinDadh(paramAdhesion.getDateComptable());
               crudService.create(detailAdhesionNew);
           }
           paramAdhesion.getAdhesionAssVie().setCodEtatAdh("R");
           crudService.update(paramAdhesion.getAdhesionAssVie());
           
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
//         return (Constants.COD_OPER_RESIL_ASS_VIE.toString()+Constants.COD_TACH_VALID);     
        return Constants.CODE_RESSOURCE_GENERALE;
           
          }
}
