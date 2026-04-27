package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import com.bna.commun.model.PersClient;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertPersonneClientTrt extends Traitement{
    

    public InsertPersonneClientTrt() {
    }

    /**
     * méthode permettant d'ajouter une relation entre un client et une personne
     * @param vo (PersClient)
     * @return ValueObject
     */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        PersClient persClient = (PersClient)vo;
       try{ 
        this.setCroFlag(false);  
        
        /* insertion du PersonneClient dans la BD */
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.create(persClient);

        return (persClient);
        
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans InsertPersonneClientTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertPersonneClientTrt");
            persClient.addError(erreur);
            logger.error("Exception : ",e);   
            return (persClient);
        }
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
