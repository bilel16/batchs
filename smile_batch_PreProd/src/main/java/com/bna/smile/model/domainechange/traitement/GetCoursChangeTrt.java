package com.bna.smile.model.domainechange.traitement;

import com.bna.commun.model.CoursChange;
import com.bna.commun.model.CoursChangeId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GetCoursChangeTrt extends Traitement {
    Context context = ContextHandler.getContext();

    public GetCoursChangeTrt() {
    }
    
    public  IValueObject execute(IValueObject vo) {
       
         try {
             CoursChangeId  coursChangeId = (CoursChangeId)vo;

             ISearchEngine searchEngine =
                 (SearchEngine)context.getBean("searchEngine");
             ICriteria   criteria    = searchEngine.createCriteria();
             IExpression expression  = searchEngine.createExpression();
             CoursChange coursChange = (CoursChange) searchEngine.get(CoursChange.class,coursChangeId);
              
             return coursChange;
         } catch (Exception e) {
         e.printStackTrace();
         return null;
         }

     }
   public  IValueObject perform(IValueObject vo) throws Exception {
      
        try {
            CoursChangeId  coursChangeId = (CoursChangeId)vo;

            ISearchEngine searchEngine =
                (SearchEngine)context.getBean("searchEngine");
            ICriteria   criteria    = searchEngine.createCriteria();
            IExpression expression  = searchEngine.createExpression();
            CoursChange coursChange = (CoursChange) searchEngine.get(CoursChange.class,coursChangeId);
             
            return coursChange;
        } catch (Exception e) {
        e.printStackTrace();
        return null;
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
