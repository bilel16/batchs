package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.RegimeMatrimonial;
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

public class GetRegimeMatrimonialTrt  extends Traitement  {
    public GetRegimeMatrimonialTrt() {
    }

    /** méthode pour la recherche du regime Matrimonial
     * @param  ValueObject : RegimeMatrimonal 
     * @return ValueObject : RegimeMatrimonal 
     */
    public

    IValueObject perform (IValueObject vo) {

        RegimeMatrimonial regimeMatrimonial = (RegimeMatrimonial)vo;
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        try {
            criteria.add(expression.eq("codRmatRmat", 
                                       regimeMatrimonial.getCodRmatRmat()));

            List listRegime = 
                searchEngine.find(RegimeMatrimonial.class, criteria);
            /*si le RegimeMatrimonial existe*/
            if (listRegime != null && listRegime.size() > 0) {
                regimeMatrimonial = (RegimeMatrimonial)listRegime.get(0);
            }

          

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetRegimeMatrimonialTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            regimeMatrimonial.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);    
           
        }
        return (regimeMatrimonial);
    }
    
    public void genCroText(ValueObject vo) {
    
    }    
    
    public String getNumeroTache  (ValueObject vo) {
     return Constants.CODE_RESSOURCE_GENERALE;
    }
    
}
