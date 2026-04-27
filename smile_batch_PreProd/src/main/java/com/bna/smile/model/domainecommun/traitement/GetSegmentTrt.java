package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.CodePostal;
import com.bna.commun.model.Segment;
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
 * classe pour la recherche d'un segment
 * @author Mdimagh Med
 * @since 14/06/07
 */
public class GetSegmentTrt extends Traitement {
    public GetSegmentTrt() {
    }
    
    /**
     * methde d'execution de la recherche
     * @param vo : Segment
     * @return vo :Segment
     */
    public IValueObject perform (IValueObject vo) {


        Segment segment = (Segment)vo;
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");


        try {
             segment =  (Segment)searchEngine.get(Segment.class, segment.getSegmentId());
            

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetSegmentTrt : ");
            text.append(e.toString());
            erreur.setCode("metier");
            erreur.setDescription(text.toString());
            erreur.setKey("GetSegmentTrt");
            segment.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);    
        }
        return (segment);
    }   
    
    public void genCroText(ValueObject vo) {
    
    }    
    
    public String getNumeroTache  (ValueObject vo) {
     return Constants.CODE_RESSOURCE_GENERALE;
    }
    
}
