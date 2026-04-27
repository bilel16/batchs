package com.bna.smile.model.banqueAssurance.traitement;

import java.util.List;

import com.bna.commun.model.Assureur;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GetListAssureurTrt  extends Traitement{
    public GetListAssureurTrt() {
    }
    /**
     * Fonction qui permet de determiner la liste assureurs
     * @Author : kriaa hatem
     * @since 24/09/2010
     */
    public IValueObject perform (IValueObject vo ){  
       
        Listes listeAssureurs = (Listes)vo; 
    try{
      
            
           Context context = ContextHandler.getContext();
           ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
           ICriteria criteria = searchEngine.createCriteria();
           IExpression expression = searchEngine.createExpression();
           criteria.add(expression.isNull("dateFinAss"));
           
           List l =  searchEngine.find(Assureur.class,criteria);
           if (l != null && l.size() > 0) {
               listeAssureurs.setList(l);
           }
           
           return listeAssureurs;
       }catch(Exception e){
          com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
          StringBuffer text = 
              new StringBuffer("Erreur dans GetListAssureurTrt : ");
          text.append(e.toString());
          erreur.setCode("200");
          erreur.setDescription(text.toString());
          erreur.setKey("GetListAssureurTrt");
          logger.error("Exception : ",e);  
          throw new RuntimeException(e);
        }
     }
        
        public void genCroText(ValueObject vo){
            }
        public String  getNumeroTache (IValueObject vo) {
              return (Constants.CODE_RESSOURCE_GENERALE);     
          }
}
