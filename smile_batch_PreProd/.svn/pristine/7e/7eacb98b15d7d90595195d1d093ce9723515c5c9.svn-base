package com.bna.smile.model.domainecommun.traitement;


import com.bna.commun.model.Personne;
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
 * classe pour la recherche d'une personne par son numero sequentiel
 * @author Mdimagh Med
 * @since 07/06/07
 */
public class GetPersonneByNumSeqPersTrt extends Traitement {
    public GetPersonneByNumSeqPersTrt() {
    }
    
    /**
     * methde d'execution de la recherche
     * @param vo : Personne
     * @return vo :Personne
     */
    public IValueObject perform (IValueObject vo) {
        Personne personne = (Personne)vo;
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");

        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        try {
            criteria.add(expression.eq("numSeqPers", personne.getNumSeqPers()));

            List listPersonne = searchEngine.find(Personne.class, criteria);
            /*si le pays existe*/
            if (listPersonne != null && listPersonne.size() > 0) {
                personne = (Personne)listPersonne.get(0);
            }else {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                erreur.setCode("1");
                erreur.setDescription("Personne inexistant " );
                erreur.setKey("RecherchePersonne.NumSeqPers");
                personne.addError(erreur);   
            }

        

        } catch (Exception e) {
        e.printStackTrace();
      /*     com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("200");
            erreur.setDescription("Erreur dans GetPersonneByNumSeqPersTrt ");
            erreur.setKey("RecherchePersonne.NumSeqPers");
            personne.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  */
          
        }
        return personne;
    }
    
    public void genCroText (ValueObject vo) {
    
    }
    
    public String getNumeroTache (ValueObject vo) {
      return  Constants.CODE_RESSOURCE_GENERALE;
    }
}   
