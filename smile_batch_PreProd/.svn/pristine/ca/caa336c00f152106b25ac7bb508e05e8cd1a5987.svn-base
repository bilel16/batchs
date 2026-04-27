package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ContratCpt;

import com.bna.commun.model.Mandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamCompteLie;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetNombreContratParClientTrt extends Traitement{
    public GetNombreContratParClientTrt() {
    }

    /**
      * Méhode de recherche de nombre des contrats d'un produit donné pour un client
      * @param ParamCompteLie     
      * @return ParamCompteLie
      */
     public IValueObject perform(IValueObject vo) {
        
        this.setCroFlag(false);        
        ParamCompteLie paramCompteLie = (ParamCompteLie)vo;
        Context context = ContextHandler.getContext(); 
         
          ISearchEngine searchEngine = (SearchEngine)context.getInstance().getSpringContext().getBean("searchEngine"); 
          ICriteria criteriaPers = searchEngine.createCriteria();
          ICriteria criteriaProduit = searchEngine.createCriteria();
          IExpression expression = searchEngine.createExpression();
          
           try {
               criteriaProduit.add(expression.eq("contratCptId.codPrdPrd", 
                                                  paramCompteLie.getCodeProduit()));
              
               criteriaProduit.add(expression.eq("client.numSeqPers", 
                                                  paramCompteLie.getContratCpt().getClient().getNumSeqPers()));
                                                  
             
             // pour le compte 195, il faut pas qu'il soit unique, donc pas de recherche sur som compte rattaché  
             if(!paramCompteLie.getCodeProduit().equals(Constants.COD_COMPTE_ECONOMIE_SUR_SALAIRE)){
                criteriaProduit.add(expression.eq("contratCpt.contratCptId.numCcptCcpt",paramCompteLie.getContratCpt().getContratCptId().getNumCcptCcpt()));        
                criteriaProduit.add(expression.eq("contratCpt.contratCptId.codStrcStrc",paramCompteLie.getContratCpt().getContratCptId().getCodStrcStrc()));
                criteriaProduit.add(expression.eq("contratCpt.contratCptId.codPrdPrd",paramCompteLie.getContratCpt().getContratCptId().getCodPrdPrd()));        
            }
             
            
             List listContrat = searchEngine.find(ContratCpt.class, criteriaProduit);              
             
             paramCompteLie.setNbreContrats(listContrat.size());
             return paramCompteLie;
            
         } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetNombreContratParClientTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("GetNombreContratParClientTrt");
            paramCompteLie.addError(erreur);
            logger.error("Exception : ",e);   
            return paramCompteLie ;
        }
     }

   public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);      
    }

}

