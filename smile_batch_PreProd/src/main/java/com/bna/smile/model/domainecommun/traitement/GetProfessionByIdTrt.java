package com.bna.smile.model.domainecommun.traitement;


import com.bna.commun.model.Profession;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;



public class GetProfessionByIdTrt  extends Traitement {
    public GetProfessionByIdTrt() {
    }
    
    /**
     * methde d'execution de la recherche
     * @param vo : Profession
     * @return vo :Profession
     */
    public IValueObject perform (IValueObject vo) {


        Profession profession = (Profession)vo;        
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        try {
          

          profession = (Profession) searchEngine.get(Profession.class, profession.getProfessionId());
          
          

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetProfessionByIdTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            profession.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);    
           
        }
        return (profession);
    }
    
    public void  genCroText (ValueObject vo) {
     
    }
    
    public String getNumeroTache  (ValueObject vo) {
     return Constants.CODE_RESSOURCE_GENERALE;
    }
}
