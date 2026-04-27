package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Segment;
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

public class GetListeSegmentTrt extends Traitement  {
    public GetListeSegmentTrt() {
    }

    /** méthode d'extraction des segments en prend en argument le critaire de recherche et la code du Sous Classe segment
     * @param   String : critaire de recherche et String : code sous classe segment
     * @return  ValueObject : Listes des sous classe segment
     */
    public


    IValueObject perform(IValueObject vo) {
        Listes listes = new Listes();
        Segment segment = (Segment)vo;
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        try {
            
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

            List liste = new ArrayList();
            if (segment.getLibSegSeg() != null && 
                !segment.getLibSegSeg().equals("")) {
                criteria.add(expression.like("libSegSeg", 
                                                 "%" + segment.getLibSegSeg() + 
                                                 "%"));
            }
            criteria.add(expression.eq("segmentId.codSsegSseg", 
                                           segment.getSegmentId().getCodSsegSseg()));
            criteria.add(expression.eq("segmentId.codCsegCseg", 
                                           segment.getSegmentId().getCodCsegCseg()));
            liste = searchEngine.find(Segment.class, criteria);

            
            listes.setList(liste);
           
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetListeSegmentTrt : ");
                text.append(e.toString());
                erreur.setCode("metier");
                erreur.setDescription(text.toString());
                erreur.setKey("GetListeSegmentTrt");
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


