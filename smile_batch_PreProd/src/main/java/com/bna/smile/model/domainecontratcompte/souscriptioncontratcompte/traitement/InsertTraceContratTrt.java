package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.Date;

import com.bna.commun.model.TraceContrat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertTraceContratTrt extends Traitement{
  

    public InsertTraceContratTrt() {
    }

    /**
     * Methode permettant d'inserer un TraceMandat dans la BD
     * @param vo : TraceMandat
     * @return TraceMandat
     */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        TraceContrat traceContrat = (TraceContrat)vo;
    try{       
        this.setCroFlag(false);
        traceContrat.setDatOperTrc(new Date());
        
        /* insertion du traceContrat dans la BD */
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.create(traceContrat);
        
        return (traceContrat);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans InsertTraceContratTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("InsertTraceContrat");
              traceContrat.addError(erreur);
              logger.error("Erreur au niveau de l'agence <<" + traceContrat.getContratCpt().getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);           
              return (traceContrat);
          }
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
