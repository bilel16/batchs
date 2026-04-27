package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Activite;
import com.bna.commun.model.PieceAnnexe;
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

import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Order;

public class GetListeActiviteTrt extends Traitement{




    public GetListeActiviteTrt() {
    }

    /** méthode d'extraction des activités en prend en argument le critaire de recherche et la code du Sous Classe activité
     * @param   String : critaire de recherche et String : code sous classe activité
     * @return  ValueObject : Listes des sous classe activité
     */
    public  IValueObject perform(IValueObject vo) {
        Listes listes = new Listes();
        try {
            Activite activite = (Activite)vo;
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteriaPers = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

            List listeAct = new ArrayList();
            if (activite.getLibActAct() != null && 
                !activite.getLibActAct().equals("")) {
                criteriaPers.add(expression.like("libActAct", 
                                                 "%" + activite.getLibActAct() + 
                                                 "%"));
            }
            if(activite.getActiviteId()!=null){
                criteriaPers.add(expression.eq("activiteId.codSactSact", 
                                               activite.getActiviteId().getCodSactSact()));
                criteriaPers.add(expression.eq("activiteId.codCactCact", 
                                               activite.getActiviteId().getCodCactCact()));
            }
            criteriaPers.addOrder(Order.asc("libActAct"));
            listeAct = searchEngine.find(Activite.class, criteriaPers);

            
            listes.setList(listeAct);
           
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetListeActiviteTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetListeActiviteTrt");
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


