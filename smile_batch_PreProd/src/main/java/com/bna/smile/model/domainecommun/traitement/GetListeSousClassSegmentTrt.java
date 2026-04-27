package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.SclasSegment;
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

/** méthode d'extraction des Sous Classe segment  en prend en argument le critaire de recherche et la code de la classe segment
 * @param   String : critaire de recherche et String : code classe segment
 * @return  ValueObject : Listes des sous classe segment
 */
public class GetListeSousClassSegmentTrt extends Traitement {
    public GetListeSousClassSegmentTrt() {
    }

    public IValueObject perform( IValueObject vo) {
        SclasSegment sclassSegment = (SclasSegment)vo;
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        Listes listes = new Listes();
        try {
            
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

            List listeSClassSeg = new ArrayList();
            if (sclassSegment.getLibSsegSseg() != null && 
                !sclassSegment.getLibSsegSseg().equals("")) {
                criteria.add(expression.like("libSsegSseg", 
                                                 "%" + sclassSegment.getLibSsegSseg() + 
                                                 "%"));
            }
            criteria.add(expression.eq("clasSegment.codCsegCseg", 
                                           sclassSegment.getClasSegment().getCodCsegCseg()));

            listeSClassSeg = searchEngine.find(SclasSegment.class, criteria);
           
            listes.setList(listeSClassSeg);
           
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetListeSousClassSegmentTrt : ");
                text.append(e.toString());
                erreur.setCode("metier");
                erreur.setDescription(text.toString());
                erreur.setKey("GetListeSousClassSegmentTrt");
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
