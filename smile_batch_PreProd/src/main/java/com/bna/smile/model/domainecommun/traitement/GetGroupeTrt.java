package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Groupe;
import com.bna.commun.model.Segment;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * classe pour la recherche d'un groupe
 * @author Mdimagh Med
 * @since 27/06/07
 */
public class GetGroupeTrt extends Traitement {
    public GetGroupeTrt() {
    }
    /**
     * methode permettant la recherche d'un groupe
     * @param vo :Objet : Groupe 
     * @return   :Objet : Groupe
     */
     public IValueObject perform (IValueObject vo)  {


         Groupe groupe = (Groupe)vo;
        
         ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

         try {
              groupe =  (Groupe)searchEngine.get(Groupe.class, groupe.getCodGrpGrp());
             

         } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                         StringBuffer text = 
                         new StringBuffer("Erreur dans GetGroupeTrt : ");
                         text.append(e.toString());
                         erreur.setCode("200");
                         erreur.setDescription(text.toString());
                         groupe.addError(erreur);
                        logger.error("Exception : ",e);   
                        throw new RuntimeException(e);  
         }
         return (groupe);
     }   
   
    public void genCroText(ValueObject vo) {
    
    }

    public String getNumeroTache (ValueObject vo) {
     return  Constants.CODE_RESSOURCE_GENERALE;
    }   
}
