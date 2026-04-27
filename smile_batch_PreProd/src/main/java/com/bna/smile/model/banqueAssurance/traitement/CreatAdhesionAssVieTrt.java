package com.bna.smile.model.banqueAssurance.traitement;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class CreatAdhesionAssVieTrt  extends Traitement{

   
    public CreatAdhesionAssVieTrt() {
    }
    public IValueObject perform(IValueObject vo) {
      
        AdhesionAssVie adhesionAssVie = (AdhesionAssVie)vo;
         
       try{
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
            crudService.create(adhesionAssVie);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans CreatAdhesionAssVieTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("CreatAdhesionAssVieTrt");
            adhesionAssVie.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
            
        }  
        return (adhesionAssVie);
    }


    public void genCroText(ValueObject vo) {    
    
    }
    

    public String  getNumeroTache (IValueObject vo) {
          return(Constants.COD_OPER_ADH_ASSUR_VIE.toString()+Constants.COD_TACH_PEC); 
      }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        AdhesionAssVie adhesionAssVie = (AdhesionAssVie)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
        structureDomaine.setCodStrcStrc(adhesionAssVie.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }

}
