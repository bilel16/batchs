package com.bna.smile.model.domaineplacement.traitement;


import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;
import com.bna.smile.model.domaineplacement.model.ParamAvanRembLiq;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import com.oxia.fwk.searchengine.SearchEngine;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.Order;

public class GetListAbonnementsInteretsTrt extends Traitement{
    public GetListAbonnementsInteretsTrt() {
    }
    
 /**
  * Charger la liste des abonnemeents des intrets pour un contrat placement donné
  * 
  * @param ContratPlacement
  * @return liste
  * 
  */
    public ValueObject perform(IValueObject vo ){
        Context context = ContextHandler.getContext();
        ParamAbonnementement paramAbonnementement = (ParamAbonnementement)vo;
        Listes listeAbonnements = new Listes();
        
    try{        
       
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        ICriteria critereCptPlacement = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        
        this.setCroFlag(false);

        if (paramAbonnementement.getContratPlacement().getNumSeqCpla()!=null)      
            critereCptPlacement.add(expression.eq("contratPlacement.numSeqCpla",paramAbonnementement.getContratPlacement().getNumSeqCpla()));
        
       /* if (paramAbonnementement.getAvancRembLiquid().getNumSeqArl()!=null)      
                critereCptPlacement.add(expression.eq("avancRembLiquid.numSeqArl",paramAbonnementement.getAvancRembLiquid().getNumSeqArl()));
       */
       
        if (paramAbonnementement.getTypeOperation() != null ) {
            critereCptPlacement.add(expression.eq("codToprAbpl",paramAbonnementement.getTypeOperation()));
        }
            
        critereCptPlacement.addOrder(Order.asc("numSeqAbpl"));
        
        List l = searchEngine.find(AbonnementPlacement.class, critereCptPlacement);    

        if(l!=null && l.size()>0)  
            listeAbonnements.setList(l);
          
        
        }catch(Exception e) {
                            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                            StringBuffer text = new StringBuffer("Erreur dans GetListInteretServiTrt : ");
                            text.append(e.toString());
                            erreur.setCode("100");
                            erreur.setDescription(text.toString());
                            erreur.setKey("GetListAbonnementsInteretsTrt");
                            logger.error("Exception : ",e);   
                            listeAbonnements.addError(erreur);
                            throw new RuntimeException(e);
                           
            }
       return(listeAbonnements);
    }
    
    public void genCroText(ValueObject vo) {
    
    }  
}
