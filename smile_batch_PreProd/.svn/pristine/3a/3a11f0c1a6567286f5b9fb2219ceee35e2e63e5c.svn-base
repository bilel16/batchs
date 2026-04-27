package com.bna.smile.model.domainecommun.traitement;


import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.Personne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.List;

public class GetListCotitulairePersonneTrt extends Traitement{



    public GetListCotitulairePersonneTrt() {
    }

    /** Méthode  qui permet d'extraire les entités cootitulaires contenant une personne
     * @author Ramzi
     * @since  16/04/2007
     * @param VO:PersonneStrc contenant type piece, num piece
     * @return VO:Liste des cootitulaires
     */
    public IValueObject perform(IValueObject vo) {
        Listes listes = new Listes();
    try {
            this.setCroFlag(false);
            PersonneStrc personneStrc = (PersonneStrc)vo;
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteriaCotit = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            
            // Get personne :            
            GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
            Personne pers  = (Personne)getPersonneTrt.exec(personneStrc);
            List listEntCotitulaire;
            
            if (pers.getNumSeqPers()!=null){
                criteriaCotit.add(expression.eq("coTitulaireId.numSeqPers", 
                                                 pers.getNumSeqPers()));
            
               listEntCotitulaire=  searchEngine.find(CoTitulaire.class, criteriaCotit);           
               listes.setList(listEntCotitulaire);
           }
          
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetListCotitulairePersonneTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetListCotitulairePersonneTrt");
                listes.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);  
              
            }   
        return (listes);
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);       
    }
}
