package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.List;

import com.bna.commun.model.Chequier;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.Paramchequiers;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetChequiersTrt extends Traitement{
    public GetChequiersTrt() {
    }


    public IValueObject perform(IValueObject vo) {
        Paramchequiers paramchequiers = (Paramchequiers)vo;
        Listes listeChequiers = new Listes();
        try {
            this.setCroFlag(false);
           
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                       paramchequiers.getContratCptId().getCodStrcStrc()));
            criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                       paramchequiers.getContratCptId().getCodPrdPrd()));
            criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                       paramchequiers.getContratCptId().getNumCcptCcpt()));

            List list = searchEngine.find(Chequier.class, criteria);
            if (list != null && list.size() > 0) {
                listeChequiers.setList(list);
            }
            
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetChequiersTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetChequiersTrt");
                logger.error("Exception : ",e); 
                listeChequiers.addError(erreur);
                return (listeChequiers);
            }
        return listeChequiers;
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }  
}
