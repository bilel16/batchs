package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Devise;

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

public class GetDeviseTrt extends Traitement {
    public GetDeviseTrt() {
    }
    
    /**
        * methde d'execution de la recherche
        * @param vo : Devise
        * @return vo :Devise
        */
       public IValueObject perform(IValueObject vo) {


           Devise devise = (Devise)vo;
           ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

           ICriteria criteria = searchEngine.createCriteria();
           IExpression expression = searchEngine.createExpression();

           try {
               criteria.add(expression.eq("codDevDev", devise.getCodDevDev() ));

               List listDevise = searchEngine.find(Devise.class, criteria);
               /*si la devise  existe*/
               if (listDevise != null && listDevise.size() > 0) {
                   devise = (Devise)listDevise.get(0);
               }

             
           } catch (Exception e) {
               com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
               StringBuffer text = 
                   new StringBuffer("Erreur dans GetDeviseTrt : ");
               text.append(e.toString());
               erreur.setCode("200");
               erreur.setDescription(text.toString());
               devise.addError(erreur);
               logger.error("Exception : ",e);   
               throw new RuntimeException(e);  
           }
           return (devise);
       }
       
    
     public void genCroText(ValueObject vo) {
     
     }

     public String getNumeroTache (ValueObject vo) {
      return  Constants.CODE_RESSOURCE_GENERALE;
     } 
     
}
