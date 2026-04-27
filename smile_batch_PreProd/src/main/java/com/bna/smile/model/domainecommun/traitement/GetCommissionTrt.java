package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Commission;

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
 * classe pour la recherche d'une commission
 * @author el arbi hassine
 * @since 17/10/2007
 */
public class GetCommissionTrt extends Traitement{
    public GetCommissionTrt () {
    }

    /**
     * methde d'execution de la recherche
     * @param vo : Commission
     * @return vo Commission
     */
    public IValueObject perform(IValueObject vo) {

        Context context = ContextHandler.getContext();
        Commission commission = (Commission)vo;
        
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        try {
            criteria.add(expression.eq("codComCom", commission.getCodComCom()));

            List listCommission = searchEngine.find(Commission.class, criteria);
            /*si la commission existe*/
            if (listCommission  != null && listCommission.size() > 0) {
                commission= (Commission)listCommission.get(0);
            }

            return (commission);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetCommissionTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("GetCommissionTrt");
            commission.addError(erreur);
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
