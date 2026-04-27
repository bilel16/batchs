package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Operation;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de traitement permet de rechercher une operation par son code
 * @author Mdimagh Lassaad 
 * @version 1.0.0 17/05/2007
 */
public class GetOperationTrt extends Traitement {
    public GetOperationTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        Operation operation = (Operation)vo;       
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();

        IExpression expression = searchEngine.createExpression();

        criteria.add(expression.eq("codOperOper", operation.getCodOperOper()));

        List l = searchEngine.find(Operation.class, criteria);

        if (l != null && l.size() > 0) {
            operation = (Operation)l.get(0);
        } else {
            operation = null;
        }

        return (operation);
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
    } 


}
