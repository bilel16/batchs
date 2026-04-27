package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Pays;
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

/**
 * classe pour la recherche d'un gouvernorat
 * @author Mdimagh Med
 * @since 13/05/07
 */
public class GetGouvernoratTrt  extends Traitement{
    public GetGouvernoratTrt() {
    }
    
    /**
     * methde d'execution de la recherche
     * @param vo : Gouvernorat
     * @return vo :Gouvernorat
     */
    public IValueObject perform (IValueObject vo)  {


        Gouvernorat gouvernorat = (Gouvernorat)vo;
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");


        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        try {
            criteria.add(expression.eq("codGouvGouv", gouvernorat.getCodGouvGouv() ));

            List listGouvernorat = searchEngine.find(Gouvernorat.class, criteria);
            /*si le pays existe*/
            if (listGouvernorat != null && listGouvernorat.size() > 0) {
                gouvernorat = (Gouvernorat)listGouvernorat.get(0);
            }

            

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetGouvernoratTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            gouvernorat.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);    
        }
        return (gouvernorat);
    }
    
    public void  genCroText (ValueObject vo) {
     
    }
    
    public String getNumeroTache  (ValueObject vo) {
     return  Constants.CODE_RESSOURCE_GENERALE;
    }
    
}
