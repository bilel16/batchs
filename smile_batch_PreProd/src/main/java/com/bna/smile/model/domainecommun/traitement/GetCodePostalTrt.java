package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.CodePostal;

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
 * classe pour la recherche d'un codePostal
 * @author Mdimagh Med
 * @since 30/05/07
 */
public class GetCodePostalTrt   extends Traitement  {
    public GetCodePostalTrt() {
    }

    /**
     * methde d'execution de la recherche
     * @param vo : CodePostal
     * @return vo :CodePostal
     */
    public IValueObject perform (IValueObject vo)  {


        CodePostal codePostal = (CodePostal)vo;
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");


        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        try {
            criteria.add(expression.eq("codCpCp", codePostal.getCodCpCp()));

            List listCodePostal = 
                searchEngine.find(CodePostal.class, criteria);
            /*si le pays existe*/
            if (listCodePostal != null && listCodePostal.size() > 0) {
                codePostal = (CodePostal)listCodePostal.get(0);
            }

          

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetCodePostalTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("GetCodePostalTrt");
            codePostal.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
        }
        return (codePostal);
    }
    
    public void genCroText(ValueObject vo) {
    
    }

    public String getNumeroTache (ValueObject vo) {
     return  Constants.CODE_RESSOURCE_GENERALE;
    }
}
