package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateContratCptTrt extends Traitement{
   
  
    
    public UpdateContratCptTrt() {
    }
    
    public IValueObject perform(IValueObject vo) {
         Context context = ContextHandler.getContext();
        ContratCpt contratCpt = (ContratCpt)vo;
    try{
        this.setCroFlag(false);
        /* Mise à jour du ContratCpt dans la Base dans la BD */
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.update(contratCpt);

        return (contratCpt);
    }
        catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans UpdateContratCptTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("UpdateContratCpt");
            contratCpt.addError(erreur);     
            logger.error("Exception : ",e);  
            return (contratCpt);
        }
    }
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
