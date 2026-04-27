package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Pays;
import com.bna.commun.model.Tribunal;
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
 * classe pour la recherche d'un tribunal
 * @author el arbi hassine
 * @since 13/05/07
 */
public class GetTribunalTrt extends Traitement{
    public GetTribunalTrt() {
    }
    
    /**
     * methde d'execution de la recherche
     * @param vo : Tribunal
     * @return vo: Tribunal
     */
    public IValueObject perform(IValueObject vo) {


        Tribunal tribunal= (Tribunal)vo;
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");

        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        try {
            criteria.add(expression.eq("codTribTrib", tribunal.getCodTribTrib() ));

            List listTribunal = searchEngine.find(Tribunal.class, criteria);
            
            if (listTribunal != null && listTribunal.size() > 0) {
                tribunal = (Tribunal)listTribunal.get(0);
            }

            return (tribunal);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetTribunalTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
           tribunal.addError(erreur);
            return (tribunal);
        }
    }
    
    public void  genCroText (ValueObject vo) {
     
    }
    
    public String getNumeroTache  (ValueObject vo) {
     return  Constants.CODE_RESSOURCE_GENERALE;
    }
}
