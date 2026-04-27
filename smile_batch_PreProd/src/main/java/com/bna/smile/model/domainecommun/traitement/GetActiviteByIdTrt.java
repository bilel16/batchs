package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Activite;
import com.bna.commun.model.CodePostal;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * classe pour la recherche d'une activité par son identifiant
 * @author Mdimagh Med
 * @since 05/06/07
 */
public class GetActiviteByIdTrt  extends Traitement {
    public GetActiviteByIdTrt() {
    }
    /**    methde d'execution de la recherche
     * @param vo : CodePostal
     * @return vo :CodePostal
     */
    public IValueObject perform (IValueObject vo) {


        Activite activite = (Activite)vo;
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

          try {
              activite = (Activite)
                searchEngine.get(Activite.class, activite.getActiviteId());

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetActiviteByIdTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            activite.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);    
         
        }
        return (activite);
    }
    
    public void  genCroText (ValueObject vo) {
     
    }
    
    public String getNumeroTache  (ValueObject vo) {
     return Constants.CODE_RESSOURCE_GENERALE;
    }

}
