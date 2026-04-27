package com.bna.smile.model.domainecommun.traitement;


import com.bna.commun.model.Personnel;
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
 * classe pour la recherche d'un personnel à travers son CIN
 * @author El Arbi Hassine
 * @since 20/02/08
 */
public class GetPersonnelByCinTrt extends Traitement{
    public GetPersonnelByCinTrt() {
    }
    
    /**
     * methde d'execution de la recherche
     * @param vo : Personnel
     * @return vo :Personnel
     */
    public IValueObject perform(IValueObject vo) {

        this.setCroFlag(false);
        Personnel personnel = (Personnel)vo;
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");

        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        try {
            criteria.add(expression.eq("numCinUser", personnel.getNumCinUser() ));

            List listPersonnel = searchEngine.find(Personnel.class, criteria);
            /*si le personnel existe*/
            if (listPersonnel != null && listPersonnel.size() > 0) {
                personnel = (Personnel)listPersonnel.get(0);
            }

            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetPersonnelByCinTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetPersonnelByCinTrt");
                personnel.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);  
              
            }
            
        return (personnel);
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);    
    }
}
