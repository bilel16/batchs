package com.bna.smile.model.banqueAssurance.traitement;

import java.util.Iterator;

import com.bna.commun.model.DetailAdhesion;
import com.bna.commun.model.Produit;
import com.bna.commun.model.TarifAssVie;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ValidChangCptFactTrt extends Traitement {
    public ValidChangCptFactTrt() {
    }

    /**
     * Fonction qui permet de valider la prise en charge d'un changement de facturation pour une adh�sion
     * @Author : Kriaa hatem
     * @since 15/09/2010
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
                  if (detailAdhesion.getCodEtatDadh().equalsIgnoreCase("V")){
                      detailAdhesion.setCodEtatDadh("H");
                      detailAdhesion.setDatFinDadh(paramAdhesion.getDateComptable());
                      crudService.update(detailAdhesion);
                  }else if (detailAdhesion.getCodEtatDadh().equalsIgnoreCase("A")){
                      detailAdhesion.setCodEtatDadh("V");
                      detailAdhesion.setDatDebDadh(paramAdhesion.getDateComptable());
                      crudService.update(detailAdhesion);
                      //maj adhesion
                      paramAdhesion.getAdhesionAssVie().setContratCpt(detailAdhesion.getContratCpt());
                      
                      GetTarifAssVieTrt getTarifAssVieTrt = new GetTarifAssVieTrt();
                      TarifAssVie tarifAssVie = new  TarifAssVie();
                      Produit prd = new Produit();
                      prd.setCodPrdPrd(detailAdhesion.getContratCpt().getContratCptId().getCodPrdPrd());
                      tarifAssVie.setProduit(prd);
                      tarifAssVie.setAssureur(paramAdhesion.getAdhesionAssVie().getTarifAssVie().getAssureur());
                      tarifAssVie = (TarifAssVie)getTarifAssVieTrt.exec(tarifAssVie);
                      paramAdhesion.getAdhesionAssVie().setTarifAssVie(tarifAssVie);
                      crudService.update(paramAdhesion.getAdhesionAssVie());
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
               return(Constants.COD_OPER_CHG_CPT_ASSUR_VIE.toString()+Constants.COD_TACH_VALID);
           }
}
