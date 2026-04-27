package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.Date;

import com.bna.commun.model.MandatOperation;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.procuration.dao.MandatDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertMandatOperationTrt extends Traitement{
   // public Context context = ContextHandler.getContext();
   // private static final Logger logger = Logger.getLogger(InsertMandatOperationTrt.class);

    public InsertMandatOperationTrt() {
    }

    public IValueObject perform(IValueObject vo) {

        Context context = ContextHandler.getContext();
        MandatOperation mandatOperation = (MandatOperation)vo;
    try{
        /* insertion du mandat Opération dans la Base dans la BD */
        /*MAJ de la clé*/
        MandatDAO mandatDAO = (MandatDAO)context.getBean("mandatDAO");
        Long numeroMandatOperation = mandatDAO.getSequenceMandatOperation();
        mandatOperation.getMandatOperationId().setNumMaopMaop(numeroMandatOperation);
        mandatOperation.setDatDebMaop(new Date());

        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.create(mandatOperation);

        return (mandatOperation);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans InsertMandatOperationTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("InsertMandatOperation");
              mandatOperation.addError(erreur);
              logger.error(" *** Erreur lors de InsertMandatOperationTrt concernant l'agence "+mandatOperation.getMandat().getCodStrcMand()+" : ", e);
              return (mandatOperation);
          }
    }
    
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
