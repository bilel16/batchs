package com.bna.smile.model.domainecommun.traitement;




import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;


import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.Error;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.List;

import org.springframework.context.ApplicationContext;

public class GetDetailContratTrt extends Traitement{    
    
   
    public GetDetailContratTrt() {

    }
    
    /**
     * methode permettant de trouver les informations sur un contrat donné    
     * @param vo : IdContratCpt
     * @return ContratCpt
     **/
    public IValueObject perform(IValueObject vo) {        
       
        this.setCroFlag(false);
        
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        //ISearchEngine searchEngine=(ISearchEngine)context.getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        ContratCptId contratCptId = (ContratCptId)vo;  
        ContratCpt contratCpt = new ContratCpt();
      try {  
       

        /* Rechercher du contrat */
        criteria.add(expression.eq("contratCptId.codPrdPrd", 
                                   contratCptId.getCodPrdPrd()));
        criteria.add(expression.eq("contratCptId.codStrcStrc", 
                                   contratCptId.getCodStrcStrc()));
        criteria.add(expression.eq("contratCptId.numCcptCcpt", 
                                   contratCptId.getNumCcptCcpt()));
        

        List l = searchEngine.find(ContratCpt.class, criteria);

        if (l != null && l.size() > 0) {
            contratCpt = (ContratCpt)l.get(0);            
        }
               
        } catch (Exception e) {
           com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
           StringBuffer text = 
               new StringBuffer("Erreur dans GetDetailContratTrt : ");
           text.append(e.toString());
           erreur.setCode("200");
           erreur.setDescription(text.toString());
           erreur.setKey("GetDetailContratTrt");
           contratCpt.addError(erreur);
           logger.error("Exception : ",e);   
           throw new RuntimeException(e);  
          
        }  
        return contratCpt ;

    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);     
    }


}


