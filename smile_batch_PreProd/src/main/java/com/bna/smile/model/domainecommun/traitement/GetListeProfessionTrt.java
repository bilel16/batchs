package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ClasActivite;
import com.bna.commun.model.GroupeProfession;
import com.bna.commun.model.Profession;
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

import org.hibernate.criterion.Order;

/** méthode d'extraction des professions qui prend en argument le critère de recherche et le code du Groupe profession
 * @param   String : critaire de recherche et String : code Groupe profession
 * @return  ValueObject : Listes des Profession
 */
public class GetListeProfessionTrt extends Traitement{
    public GetListeProfessionTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        
        Listes listes = new Listes();
        try {
            Profession profession = (Profession)vo;
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteriaPers = searchEngine.createCriteria();
            ICriteria criteriaCpt = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

            List listeProf = new ArrayList();
            if (profession.getLibProfProf() != null && 
                !profession.getLibProfProf().equals("")) {
                criteriaPers.add(expression.like("libProfProf", 
                                                 "%" + profession.getLibProfProf() + 
                                                 "%"));
            }
            
            if(profession.getGroupeProfession()!=null) {          
                criteriaPers.add(expression.eq("groupeProfession.codGproGpro", 
                                           profession.getGroupeProfession().getCodGproGpro()));
            }
            criteriaPers.addOrder(Order.asc("libProfProf"));
            listeProf = searchEngine.find(Profession.class, criteriaPers);

            
            listes.setList(listeProf);
            
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetListeProfessionTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetListeProfessionTrt");
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
