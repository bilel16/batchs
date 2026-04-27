package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.constant.Constants;
import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.model.OperationEpargnes;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.PersonneDAO;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe pour l'insertion d'une nouvelle opération pour épargne
 * @author Mdimagh Med Lassaad
 * @since 31/10/2007
 */
public class AjoutOperationEpargneTrt extends Traitement {

    public Context context = ContextHandler.getContext();
    
    public AjoutOperationEpargneTrt() {
    }
    
    
    public IValueObject perform(IValueObject vo) {
        OperationEpargnes operationEpargnes = (OperationEpargnes)vo;
    try{


        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        
        SequenceDAO sequenceDAO = (SequenceDAO)context.getBean("sequenceDAO");
        operationEpargnes.setNumOpeOpe(sequenceDAO.getSequenceOperationEpargne().longValue());
        crudService.execute(operationEpargnes);
        
        crudService.create(operationEpargnes);

        return (operationEpargnes);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans AjoutOperationEpargneTrt : ");
              text.append(e.toString());
              erreur.setCode("300");
              erreur.setDescription(text.toString());
              erreur.setKey("AjoutOperationEpargneTrt");
              operationEpargnes.addError(erreur);
              return (operationEpargnes);
          }
    }
   
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
