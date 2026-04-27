package com.bna.smile.model.domainecommun.traitement;


import java.util.List;

import com.bna.commun.model.Personne;
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
/**
 * classe pour la recherche d'une personne à travers son numero de piece
 * @author nbdour
 * @since 17/10/14
 */
public class GetPersonneByNumPieTrt extends Traitement{
    public GetPersonneByNumPieTrt() {
    }

    public IValueObject perform(IValueObject vo) {

        this.setCroFlag(false);
        Personne personne = (Personne)vo;
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");

        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        try {
            criteria.add(expression.eq("numPcePers", personne.getNumPcePers() ));

            List listPersonnel = searchEngine.find(Personne.class, criteria);
            /*si le personnel existe*/
            if (listPersonnel != null && listPersonnel.size() > 0) {
                personne = (Personne)listPersonnel.get(0);
            }

            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetPersonnelByCinTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetPersonnelByCinTrt");
                personne.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);  
              
            }
            
        return (personne);
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);    
    }
}
