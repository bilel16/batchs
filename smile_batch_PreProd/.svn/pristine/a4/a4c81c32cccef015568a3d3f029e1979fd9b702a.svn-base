package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.MandPersOperPlac;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.Iterator;

import org.springframework.orm.hibernate3.HibernateTemplate;


/**
 * validation d'une avance sur capital .
 * @param ParamContratPlacement
 * @return AvancRembLiquid
 * 
 */
public class RejeterLiquidationAnticipeTrt extends Traitement{
    public RejeterLiquidationAnticipeTrt() {
    }
        
    public IValueObject perform (IValueObject vo ) {     
     
    Context context = ContextHandler.getContext();
    ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;             
             
       try{ 
           ///------------------------------------------------------------------------------------------------
           ///----------- Mise à jour de la table avanc_remb_liq et la table placement... ----------------------
           ///------------------------------------------------------------------------------------------------
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice"); 
            
            if(!paramContratPlacement.getAvancRembLiquid().equals(null)){
                crudService.update(paramContratPlacement.getAvancRembLiquid());  
                
                crudService.update(paramContratPlacement.getAvancRembLiquid().getContratPlacement());
                
            }
               
                  
           }
         catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("RejeterLiquidationAnticipeTrt  "+e.getMessage());
                paramContratPlacement.getAvancRembLiquid().addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);
        } 
        return (paramContratPlacement.getAvancRembLiquid());
    }
    
    public void genCroText(ValueObject vo) {
            
        }   

    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;             
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        structureDomaine.setCodStrcStrc(paramContratPlacement.getAvancRembLiquid().getContratPlacement().getContratCpt().getStructure().getCodStrcStrc());
        return structureDomaine;
    }
    

}
