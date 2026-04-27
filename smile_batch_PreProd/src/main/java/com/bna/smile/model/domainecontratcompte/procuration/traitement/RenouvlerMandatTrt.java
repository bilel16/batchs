package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import org.apache.log4j.Logger;

import com.bna.commun.model.Mandat;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

public class RenouvlerMandatTrt {
    
    private static final Logger logger = Logger.getLogger(RenouvlerMandatTrt.class);

    public RenouvlerMandatTrt() {
    }

    /**
     * Methode permettant de renouvler un Mandat dans la BD
     * @param vo : Mandat
     * @return Mandat
     */
    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        Mandat mandat = (Mandat)vo;
    try{
        /* Annulation du mandat */
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.update(mandat);
        return (mandat);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans RenouvlerMandatTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("RenouvlerMandatTrt");
              mandat.addError(erreur);
              logger.error(" *** Erreur lors de  RenouvlerMandatTrt concernant l'agence "+mandat.getCodStrcMand()+" : ", e);
              return (mandat);
          }
    }
}
