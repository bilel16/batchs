package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ClasActivite;
import com.bna.commun.model.SclasActivite;
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

/** méthode d'extraction des Sous Classe activite  en prend en argument le critaire de recherche et la code de la classe d'activité
 * @param   String : critaire de recherche et String : code classe activité
 * @return  ValueObject : Listes des sous classe activité
 */
public class GetListeSousClassActiviteTrt extends Traitement {
    public GetListeSousClassActiviteTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        Listes listes = new Listes();
        try {
            SclasActivite sclassActivite = (SclasActivite)vo;
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteriaPers = searchEngine.createCriteria();
            ICriteria criteriaCpt = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

            List listeSClassAct = new ArrayList();
            if (sclassActivite.getLibSactSact() != null && 
                !sclassActivite.getLibSactSact().equals("")) {
                criteriaPers.add(expression.like("libSactSact", 
                                                 "%" + sclassActivite.getLibSactSact() + 
                                                 "%"));
            }
            criteriaPers.add(expression.eq("clasActivite.codCactCact", 
                                           sclassActivite.getClasActivite().getCodCactCact()));

            listeSClassAct = 
                    searchEngine.find(SclasActivite.class, criteriaPers);

           
            listes.setList(listeSClassAct);
          
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetListeSousClassActiviteTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetListeSousClassActiviteTrt");
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
