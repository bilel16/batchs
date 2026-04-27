package com.bna.smile.model.domainecommun.traitement;


import com.bna.commun.model.ClasSegment;
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

import java.util.ArrayList;
import java.util.List;

/** méthode d'extraction des Classe segment d'une en prend en argument le critaire de recherche
 * @param   String : critaire de recherche
 * @return  ValueObject : Listes des classe segment
 */
public class GetListeClassSegmentTrt extends Traitement  {
    public GetListeClassSegmentTrt() {
    }

    public IValueObject perform(IValueObject vo) {
       
        Listes listes = new Listes();
        ClasSegment clasSegment = (ClasSegment)vo;
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        try {

          

            List listeClassSeg = new ArrayList();
            if (clasSegment.getLibCsegCseg() == null || 
                clasSegment.getLibCsegCseg().equals("")) {
                listeClassSeg = searchEngine.findAll(ClasSegment.class);
            } else {
                criteria.add(expression.like("libCsegCseg", 
                                                 "%" + clasSegment.getLibCsegCseg() + 
                                                 "%"));
                listeClassSeg = 
                        searchEngine.find(ClasSegment.class, criteria);
            }
           
            listes.setList(listeClassSeg);
           
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetListeClassSegmentTrt : ");
                text.append(e.toString());
                erreur.setCode("metier");
                erreur.setDescription(text.toString());
                erreur.setKey("GetListeClassSegmentTrt");
                listes.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);    
            }
        return (listes);


    }
    
      public void genCroText(ValueObject vo) {
      
      }

      public String getNumeroTache (ValueObject vo) {
       return  Constants.CODE_RESSOURCE_GENERALE;
      }
}
