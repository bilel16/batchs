package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.MandPersOperPlac;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertMandPersOperPlacTrt extends Traitement{
    public InsertMandPersOperPlacTrt() {
    }
    /**
     * Methode permettant l'insertion d'une MandPersOperPlac
     * @param vo : MandPersOperPlac
     * @return MandPersOperPlac
     */
    public IValueObject perform(IValueObject vo) {
    
        Context context = ContextHandler.getContext();
        MandPersOperPlac mandPersOperPlac = (MandPersOperPlac)vo;
    try{

        /* Insertion du MandPersOperPlac dans la BD */
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.create(mandPersOperPlac);

        return (mandPersOperPlac);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans InsertMandPersOperPlacTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("InsertMandPersOperPlacTrt");
              mandPersOperPlac.addError(erreur);
              logger.error(" *** Erreur lors de InsertMandPersOperPlacTrt concernant l'agence "+mandPersOperPlac.getDetailsOperationPlacement().getStructure().getCodStrcStrc()+" : ", e);
              return (mandPersOperPlac);
          }
    }


    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
