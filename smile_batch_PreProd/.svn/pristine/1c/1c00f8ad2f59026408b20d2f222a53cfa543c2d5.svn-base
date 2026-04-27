package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ClasActivite;
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

/** méthode d'extraction des Classe activite d'une en prend en argument le critaire de recherche
 * @param   String : critaire de recherche
 * @return  ValueObject : Listes des classe activité
 */
public class GetListeClassActiviteTrt extends Traitement {
    public GetListeClassActiviteTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        
        Listes listes = new Listes();
        try {

            ClasActivite classActivite = (ClasActivite)vo;
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteriaPers = searchEngine.createCriteria();
            ICriteria criteriaCpt = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

            List listeClassAct = new ArrayList();
            if (classActivite.getLibCactCact() == null || 
                classActivite.getLibCactCact().equals("")) {
                listeClassAct = searchEngine.findAll(ClasActivite.class);
            } else {
                criteriaPers.add(expression.like("libCactCact", 
                                                 "%" + classActivite.getLibCactCact() + 
                                                 "%"));
                listeClassAct = 
                        searchEngine.find(ClasActivite.class, criteriaPers);
            }
           
            listes.setList(listeClassAct);
            
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetListeClassActiviteTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetListeClassActiviteTrt");
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
