package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.List;

public class GetEntiteCotitByContratTrt extends Traitement{
    public GetEntiteCotitByContratTrt() {
    }


    /**
     * methode permettant de trouver l'entité cotitulaire
     * suite à un contrat donné    
     * @param vo : ContratCpt
     * @return CoTitulaire
     */
   
     
    public IValueObject perform(IValueObject vo) {
                
        this.setCroFlag(false);
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        CoTitulaire coTitulaire = new CoTitulaire();
        ContratCpt contratCpt = (ContratCpt)vo;
   try{
        /* Rechercher de l'entité cotitulaire*/
        criteria.add(expression.eq("coTitulaireId.numSeqCli", 
                                   contratCpt.getClient().getNumSeqPers()));
        

        List l = searchEngine.find(CoTitulaire.class, criteria);

        if (l != null && l.size() > 0) {
            coTitulaire = (CoTitulaire)l.get(0);            
        }
        return coTitulaire;
        
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetEntiteCotitByContratTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetEntiteCotitByContratTrt");
            coTitulaire.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
            
        }

    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);       
    }

}