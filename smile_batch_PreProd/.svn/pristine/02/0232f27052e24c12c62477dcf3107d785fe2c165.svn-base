package com.bna.smile.model.domainechange.traitement;

import com.bna.commun.model.PariteOfficielle;
import com.bna.commun.model.PariteOfficielleId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GetCoursPariteOffTrt extends Traitement {
    Context context = ContextHandler.getContext();

    public GetCoursPariteOffTrt() {
    }
    
   
   public  IValueObject perform(IValueObject vo) throws Exception {
      
        try {
            PariteOfficielleId pariteOfficielleId = (PariteOfficielleId)vo;

            ISearchEngine searchEngine =
                (SearchEngine)context.getBean("searchEngine");
            ICriteria   criteria    = searchEngine.createCriteria();
            IExpression expression  = searchEngine.createExpression();
            PariteOfficielle pariteOfficielle = (PariteOfficielle) searchEngine.get(PariteOfficielle.class,pariteOfficielleId);
             
            return pariteOfficielle;
        } catch (Exception e) {
        e.printStackTrace();
        return null;
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
