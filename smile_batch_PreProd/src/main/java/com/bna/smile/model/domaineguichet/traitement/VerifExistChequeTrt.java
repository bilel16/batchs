package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.domainecommun.model.ContratCheque;

import com.bna.commun.vo.PrimitiveVO;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.Date;
import java.util.List;

public class VerifExistChequeTrt extends Traitement{
    public Context context = ContextHandler.getContext();

    public VerifExistChequeTrt() {
    }

    /**
     * Methode permet de verifier l'existance d'un cheque 
     * @param vo : ContratCheque
     * @return   : PrimitiveVO (boolean)
     */

     public IValueObject perform(IValueObject vo) { 
    
        this.setCroFlag(false);      
        ContratCheque contratCheque = new ContratCheque();
        
        PrimitiveVO primitiveVO = new PrimitiveVO();
        try{
            primitiveVO.setVBool(false);

            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
            ICriteria criteria         = searchEngine.createCriteria();
            IExpression expression     = searchEngine.createExpression();

            /* Rechercher d'une opposition sur ce moyen de payement */
           // criteria.add(expression.eq("oppositionMoyenPaiementId.numMoypOpmp", oppositionMoyenPaiementId.getNumMoypOpmp()));
            criteria.add(expression.ge("datRemiChqi",new Date()));
            criteria.add(expression.eq("codEtatOpmp","O"));
            

            List l = searchEngine.find(OppositionMoyenPaiement.class, criteria);

            if (l != null && l.size() > 0) {/* moyen de payement en opposition */
                primitiveVO.setVBool(true);
            }
        return (primitiveVO);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans VerifOppositionMoyPayTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("VerifOppositionMoyPayTrt");
              primitiveVO.addError(erreur);
              return (primitiveVO);
          }

    
    }
    
    
    
    
    public void genCroText(ValueObject vo) {
    }
    
    public String getNumeroTache(IValueObject vo){
        return Constants.CODE_RESSOURCE_GENERALE;   
    }

}
