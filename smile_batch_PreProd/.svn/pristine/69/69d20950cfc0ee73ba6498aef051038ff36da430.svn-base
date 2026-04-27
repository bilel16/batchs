package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ExonerationCltTva;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TraceExoTva;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertTraceExoTvaTrt extends Traitement{

    public InsertTraceExoTvaTrt() {
    }
    public IValueObject perform(IValueObject vo) {
      
        TraceExoTva traceExoTva = (TraceExoTva)vo;
         
       try{
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
            crudService.create(traceExoTva);

        } catch (Exception e) {
           logger.error("Exception : ",e);   
           throw new RuntimeException(e);  
        }  
        return (traceExoTva);
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);    
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        TraceExoTva traceExoTva = (TraceExoTva)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
        structureDomaine.setCodStrcStrc(traceExoTva.getPersonnel().getStructure().getCodStrcStrc());
        return structureDomaine;
    }
    
}
