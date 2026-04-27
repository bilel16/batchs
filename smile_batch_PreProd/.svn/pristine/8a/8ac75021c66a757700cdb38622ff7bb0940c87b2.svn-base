package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Regime;
import com.bna.commun.model.RegleGestionContrat;
import com.bna.commun.model.RegleGestionContratId;
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
 * recherche d'une regle de gestion
 * @author Mdimagh Med lassaad 
 * @since 25/09/07
 */
public class GetRegleGestionContratTrt extends Traitement{
    public GetRegleGestionContratTrt() {
    }
    public

    IValueObject perform(IValueObject vo) {


       RegleGestionContratId regleGestionContratId = (RegleGestionContratId)vo;
       Context context = ContextHandler.getContext();
       ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        
        criteria.add(expression.eq("regleGestionContratId.produit.codPrdPrd",regleGestionContratId.getCodPrdPrd()));
        criteria.add(expression.eq("regleGestionContratId.operation.codOperOper",regleGestionContratId.getCodOperOper()));
        criteria.add(expression.eq("regleGestionContratId.typeRegleContrat.codTypTreg",regleGestionContratId.getCodTypTreg()));
        List ll = searchEngine.findAll(RegleGestionContrat.class);
        List l  = searchEngine.find(RegleGestionContrat.class, criteria);

        RegleGestionContrat regleGestionContrat = new RegleGestionContrat();
        try {
         RegleGestionContrat   regleGestionContratr = (RegleGestionContrat)  searchEngine.get (RegleGestionContrat.class,regleGestionContratId);
            return (regleGestionContrat);

         } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetRegleGestionContratTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetRegleGestionContratTrt");
                regleGestionContrat.addError(erreur);
                return (regleGestionContrat);
         }  
         
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);     
    }
}
