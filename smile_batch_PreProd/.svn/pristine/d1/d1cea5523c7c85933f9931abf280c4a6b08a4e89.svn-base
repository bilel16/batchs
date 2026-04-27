package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.Date;

import com.bna.commun.model.TraceMandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertTraceMandatTrt extends Traitement{
   // public Context context = ContextHandler.getContext();
   // private static final Logger logger = Logger.getLogger(InsertTraceMandatTrt.class);

    public InsertTraceMandatTrt() {
    }

    /**
     * Methode permettant d'inserer un TraceMandat dans la BD
     * @param vo : TraceMandat
     * @return TraceMandat
     * @autors BOUSSEN Youssef & KRIAA Hatem
     */
    public IValueObject perform(IValueObject vo) {
    
        Context context = ContextHandler.getContext();
        TraceMandat traceMandat = (TraceMandat)vo;
    try{
        traceMandat.setCodEtatTrm("V");
        traceMandat.setDatOperTrm(new Date());

        /* insertion du TraceMandat dans la BD */
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.create(traceMandat);

        return (traceMandat);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = new StringBuffer("Erreur dans InsertTraceMandatTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("InsertTraceMandat");
              traceMandat.addError(erreur);
              logger.error("*** Exception lors de InsertTraceMandat concernant l'agence"+traceMandat.getMandat().getCodStrcMand()+" : ",e);
              return (traceMandat);
          }
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
    
}
