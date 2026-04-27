package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.util.ContextHandler;

import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.model.ContratCheque;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.Date;
import java.util.List;

public class VerifCompteVertTrt {

    public Context context = ContextHandler.getContext();
    public VerifCompteVertTrt() {
    }
    
    
    /**
     * Methode permet de verifier si un compte est lié à  
     * @param vo : ContratCheque
     * @return   : PrimitiveVO (boolean)
     */

     public ValueObject execute(ValueObject vo) { 
    
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
    }


