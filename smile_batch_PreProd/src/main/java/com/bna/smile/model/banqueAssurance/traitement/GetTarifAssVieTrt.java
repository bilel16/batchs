package com.bna.smile.model.banqueAssurance.traitement;

import java.util.List;

import com.bna.commun.model.TarifAssVie;
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

public class GetTarifAssVieTrt extends Traitement{
    public GetTarifAssVieTrt() {
    }
    
    public IValueObject perform (IValueObject vo ){  
            TarifAssVie tarifAssVie = (TarifAssVie)vo;
        try{
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            if (tarifAssVie.getProduit() != null) { 
                criteria.add(expression.eq("produit.codPrdPrd", 
                                                      tarifAssVie.getProduit().getCodPrdPrd()));
                }else {
                     logger.debug("---------- produit vide");
                 }
            if (tarifAssVie.getAssureur() != null) { 
                criteria.add(expression.eq("assureur.numSeqAss", 
                                                      tarifAssVie.getAssureur().getNumSeqAss()));
                }else {
                     logger.debug("---------- code assureur vide");
                 }     
            List l = searchEngine.find(TarifAssVie.class, criteria);
            if(l != null && l.size() != 0){
             return (TarifAssVie)l.get(0);
            }else {
                return tarifAssVie;
            }
        }catch(Exception e){
           com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
           StringBuffer text = 
               new StringBuffer("Erreur dans GetTarifAssVieTrt : ");
           text.append(e.toString());
           erreur.setCode("200");
           erreur.setDescription(text.toString());
           erreur.setKey("GetTarifAssVieTrt");
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

