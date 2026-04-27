package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.CoursChange;
import com.bna.commun.model.CoursChangeId;
import com.bna.commun.model.Devise;
import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.ContextHandler;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.Date;
import java.util.List;


public class GetCoursDevTrt {

    public Context context = ContextHandler.getContext();
    public GetCoursDevTrt() {
    }
    
    /**
     * Methode permet de retourner la valeur du cours d'une devise à une date donnée  
     * @param vo : CoursChangeId
     * @return   : CoursChange
     */
    public ValueObject execute(ValueObject vo) {
   
        CoursChangeId coursChangeId = (CoursChangeId)vo;
        CoursChange coursChange = new CoursChange();
   
    try{
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
            
            //coursChange = (CoursChange)searchEngine.get(CoursChange.class, coursChangeId);
             ICriteria criteria         = searchEngine.createCriteria();
             IExpression expression     = searchEngine.createExpression();

             /* Rechercher le cours de la devise d'aujourdhui */
             criteria.add(expression.eq("coursChangeId.codDevDev", coursChangeId.getCodDevDev()));
             criteria.add(expression.between("coursChangeId.datJourCchn",DateHandler.addJour(coursChangeId.getDatJourCchn(), -1),coursChangeId.getDatJourCchn()));
             

             List l = searchEngine.find(CoursChange.class, criteria);

             if (l != null && l.size() > 0) {/* le cours d'aujoudhui */
              coursChange = (CoursChange)l.get(0);
             }
             
       /*     Devise devise = new Devise();
            devise.setNbrUnitDev(Long.valueOf(1000));
            coursChange.setDevise(devise);
            coursChange.setMontCabaCchn(Long.valueOf(1700));
        */
        return (coursChange);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = new StringBuffer("Erreur dans GetCoursDevTrt : ");
              text.append(e.toString());
              erreur.setCode("GCD");
              erreur.setDescription(text.toString());
              erreur.setKey("GetCoursDevTrt");
              coursChange.addError(erreur);
              return (coursChange);
          }
    }
}
