package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ClasActivite;
import com.bna.commun.model.GroupeProfession;
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

/** méthode d'extraction des Groupe profession d'une en prend en argument le critaire de recherche
 * @param   String : critaire de recherche 
 * @return  ValueObject : Listes des Groupe profession 
 */
public class GetListeGroupProfessionTrt extends Traitement{
    public GetListeGroupProfessionTrt() {
    }

    public  IValueObject perform(IValueObject vo) {
        Listes listes = new Listes();
        try {
            GroupeProfession groupeProfession = (GroupeProfession)vo;
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteriaPers = searchEngine.createCriteria();
            ICriteria criteriaCpt = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

            List listeGroupProf = new ArrayList();
            if (groupeProfession.getLibGproGpro() == null || 
                groupeProfession.getLibGproGpro().equals("")) {
                listeGroupProf = searchEngine.findAll(GroupeProfession.class);
            } else {
                criteriaPers.add(expression.like("libGproGpro", 
                                                 "%" + groupeProfession.getLibGproGpro() + 
                                                 "%"));
                listeGroupProf = 
                        searchEngine.find(GroupeProfession.class, criteriaPers);
            }
            
            listes.setList(listeGroupProf);
           
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetListeGroupProfessionTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetListeGroupProfessionTrt");
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
