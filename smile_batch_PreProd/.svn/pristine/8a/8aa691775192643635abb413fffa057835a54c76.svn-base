package com.bna.smile.model.banqueAssurance.traitement;

import com.bna.commun.model.DetailAssuranceVoyage;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class InsertDetailContratAssuranceVoyageTrt extends Traitement{
    public InsertDetailContratAssuranceVoyageTrt() {
    }
    public IValueObject perform(IValueObject vo) {
      
    DetailAssuranceVoyage detailAssuranceVoyage =(DetailAssuranceVoyage)vo;
         
       try{
         
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
            ISearchEngine searchEngine =(SearchEngine)context.getBean("searchEngine");
            crudService.create(detailAssuranceVoyage);
            this.setCroFlag(false);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans InsertDetailContratAssuranceVoyageTrt : ");
            text.append(e.toString());
            erreur.setCode("500");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertDetailContratAssuranceVoyageTrt");
            detailAssuranceVoyage.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
            
        }  
        return (detailAssuranceVoyage);
    }


    public void genCroText(ValueObject vo) {    
    
    }

}
