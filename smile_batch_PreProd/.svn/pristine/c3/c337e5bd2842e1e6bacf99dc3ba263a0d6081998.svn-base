package com.bna.smile.model.domainecommun.traitement;


import com.bna.commun.model.CategoriePersonne;
import com.bna.commun.model.FormeJuridique;
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

import java.util.List;

/** méthode pour la recherche d'une catégorie de personne
 * @param  ValueObject : CategoriePersonne 
 * @return ValueObject : CategoriePersonne 
 */

 public class GetListCategoriesPersonneTrt  extends Traitement{
     public GetListCategoriesPersonneTrt() {
     }

    
    public   IValueObject perform(IValueObject vo) {

         //CategoriePersonne categoriePersonne = (CategoriePersonne)vo;
         ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
         ICriteria criteria = searchEngine.createCriteria();
         IExpression expression = searchEngine.createExpression();
         Listes listCategoriePersonne = new Listes();
            try {

                 this.setCroFlag(false);
                List categoriePersonne = searchEngine.findAll(CategoriePersonne.class);
                if (categoriePersonne != null && categoriePersonne.size() > 0) {
                     
                     listCategoriePersonne.setList(categoriePersonne);
                 }else{
                     com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                     StringBuffer text = 
                         new StringBuffer("La liste de catégories de personne est vide. ");
                     erreur.setCode("200");
                     erreur.setDescription(text.toString());
                     listCategoriePersonne=null;
                 }
             } catch (Exception e) {
                 com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                 StringBuffer text = 
                     new StringBuffer("Erreur dans GetCategoriePersonneTrt : ");
                 text.append(e.toString());
                 erreur.setCode("200");
                 erreur.setDescription(text.toString());
                  listCategoriePersonne=null;
                 logger.error("Exception : ",e);   
                 throw new RuntimeException(e);  
             }
         return (listCategoriePersonne);
     } 
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);    
    }
  
}
