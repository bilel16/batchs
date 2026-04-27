package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Personne;

import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.bna.smile.model.domainecommun.service.PersonneService;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.PersonneDAO;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UpdatePersonneTrt extends Traitement{
   
    public UpdatePersonneTrt() {
    }
    
   
    public IValueObject perform(IValueObject vo) {
        Personne personne = (Personne)vo;
        Context context = ContextHandler.getContext();
       
       try{
       if(this.checkClotureJournee()){
        this.setCroFlag(false);              
        CRUDservice crudservice = (CRUDservice)context.getBean("crudservice");
        
        crudservice.update(personne);
      
      }else{
                      com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                      StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                      erreur.setCode("100");
                      erreur.setDescription(text.toString());
                      erreur.setKey("UpdatePersonneTrt");
                      personne.addError(erreur);    
                      
        }     
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans UpdatePersonneTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("UpdatePersonneTrt");
            personne.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
            
        }  
        return (personne);
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);    
    }

    
}
