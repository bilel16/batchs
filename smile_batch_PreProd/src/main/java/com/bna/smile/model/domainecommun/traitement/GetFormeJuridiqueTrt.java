package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.FormeJuridique;
import com.bna.commun.model.NiveauInstruction;
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

public class GetFormeJuridiqueTrt extends Traitement{
    public GetFormeJuridiqueTrt() {
    }
    /** méthode pour la recherche d'une forme juridique
     * @param  ValueObject : FormeJuridique 
     * @return ValueObject : FormeJuridique 
     */
     
    public   IValueObject perform(IValueObject vo) {
         
         this.setCroFlag(false);
         FormeJuridique formeJuridique = (FormeJuridique)vo;
         ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
         ICriteria criteria = searchEngine.createCriteria();
         IExpression expression = searchEngine.createExpression();

             try {
                 criteria.add(expression.eq("codFjFj", formeJuridique.getCodFjFj()));

                 List listFormeJuridique = searchEngine.find(FormeJuridique.class, criteria);
                 /*si le RegimeMatrimonial existe*/
                 if (listFormeJuridique != null && listFormeJuridique.size() > 0) {
                     formeJuridique = (FormeJuridique)listFormeJuridique.get(0);
                 }else{
                     com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                     StringBuffer text = 
                         new StringBuffer("La forme juridique n'existe pas. ");
                     erreur.setCode("200");
                     erreur.setDescription(text.toString());
                     formeJuridique.addError(erreur);
                 }

                

             } catch (Exception e) {
                 com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                 StringBuffer text = 
                     new StringBuffer("Erreur dans GetFormeJuridiqueTrt : ");
                 text.append(e.toString());
                 erreur.setCode("200");
                 erreur.setDescription(text.toString());
                 formeJuridique.addError(erreur);
                 logger.error("Exception : ",e);   
                 throw new RuntimeException(e);    
             }
         return (formeJuridique);
     }  


    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);      
    } 
    
}
