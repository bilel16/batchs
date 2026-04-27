package com.bna.smile.model.domainecommun.traitement;


import com.bna.commun.model.CompteInterne;
import com.bna.commun.model.CompteInterneId;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;


import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.Error;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.List;

import org.springframework.context.ApplicationContext;

public class GetDetailCompteInterneTrt extends Traitement{
  
    public GetDetailCompteInterneTrt() {

    }
    
    /**
     * methode permettant de trouver les informations sur un contrat interne donné    
     * @param vo : CompteInterneId
     * @return CompteInterne
     **/
    public IValueObject perform(IValueObject vo) {
         
        this.setCroFlag(false);

        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
     
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        CompteInterneId compteInterneId = (CompteInterneId)vo;       
        
        CompteInterne compteInterne = new CompteInterne();
  try{
        /* Rechercher du contrat */
        criteria.add(expression.eq("compteInterneId.codPrdPrd", 
                                   compteInterneId.getCodPrdPrd()));
        criteria.add(expression.eq("compteInterneId.codStrcStrc", 
                                   compteInterneId.getCodStrcStrc()));
        criteria.add(expression.eq("compteInterneId.numCptiCpti", 
                                   compteInterneId.getNumCptiCpti()));
        

        List l = searchEngine.find(CompteInterne.class, criteria);

        if (l != null && l.size() > 0) {
            compteInterne = (CompteInterne)l.get(0);            
        }
        
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetDetailCompteInterneTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetDetailCompteInterneTrt");
            compteInterne.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
            
        }
        return compteInterne;

    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);     
    }


}


