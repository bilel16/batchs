package com.bna.smile.model.domaineplacement.traitement;


import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.model.Listes;
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

public class GetListAbonnementsInteretsByArlTrt extends Traitement{
    public GetListAbonnementsInteretsByArlTrt() {
    }
    
 /**
  * Charger la liste des abonnemeents des intrets pour une avance donnée
  * @param AvanRembLiquid
  * @return liste
  * @Auteur BOUSSEN Youssef
  */
    public ValueObject perform(IValueObject vo ){
        Context context = ContextHandler.getContext();
        AvancRembLiquid avancRembLiquid = (AvancRembLiquid)vo;
        Listes listeAbonnements = new Listes();
        
    try{        
       
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        ICriteria critereArl = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        
        this.setCroFlag(false);

        if (avancRembLiquid.getNumSeqArl()!=null)      
            critereArl.add(expression.eq("avancRembLiquid.numSeqArl",avancRembLiquid.getNumSeqArl()));
       
        List l = searchEngine.find(AbonnementPlacement.class, critereArl);    
        Collections.sort(l);

        if(l!=null && l.size()>0)  
            listeAbonnements.setList(l);
            
        }catch(Exception e) {
                            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                            StringBuffer text = new StringBuffer("Erreur dans GetListAbonnementsInteretsByArlTrt : ");
                            text.append(e.toString());
                            erreur.setCode("100");
                            erreur.setDescription(text.toString());
                            erreur.setKey("GetListAbonnementsInteretsByArlTrt");
                            logger.error("Exception : ",e);   
                            listeAbonnements.addError(erreur);
                            throw new RuntimeException(e);
                           
            }
       return(listeAbonnements);
    }
    
    public void genCroText(ValueObject vo) {
    
    }  
}
