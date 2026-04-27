package com.bna.smile.model.domaineplacement.traitement;


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

import java.util.List;

public class GetListInteretServiTrt extends Traitement{
    public GetListInteretServiTrt() {
    }
    
 /**
  * Charger la liste des intrets servi pour un contrat placement donné
  * pour une agence donnée.
  * @param ContratPlacement
  * @return listeAvancRembLiquid
  * 
  */
    public ValueObject perform(IValueObject vo ){
        Context context = ContextHandler.getContext();
        ContratPlacement contratPlacement = (ContratPlacement)vo;
        Listes listeIntretServi = new Listes();
        
    try{        
       
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        ICriteria critereCptPlacement = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        
        this.setCroFlag(false);

        if (contratPlacement.getNumSeqCpla()!=null)      
            critereCptPlacement.add(expression.eq("contratPlacement.numSeqCpla",contratPlacement.getNumSeqCpla()));
       

        List l = searchEngine.find(InteretServi.class, critereCptPlacement);
        if(l!=null && l.size()>0)
            listeIntretServi.setList(l);
         
        }catch(Exception e) {
                            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                            StringBuffer text = new StringBuffer("Erreur dans GetListInteretServiTrt : ");
                            text.append(e.toString());
                            erreur.setCode("100");
                            erreur.setDescription(text.toString());
                            erreur.setKey("GetListInteretServiTrt");
                            logger.error("Exception : ",e);   
                            listeIntretServi.addError(erreur);
                            throw new RuntimeException(e);
                           
            }
       return(listeIntretServi);
    }
    
    public void genCroText(ValueObject vo) {
    
    }  
}
