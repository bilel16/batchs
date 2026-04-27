package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;

import com.bna.commun.vo.PrimitiveVO;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.List;

public class GetMontantMADByIdTrt extends Traitement{
        public Context context = ContextHandler.getContext();

    public GetMontantMADByIdTrt() {
    }
    
    /**
     * Methode permet trouver les montants mis a disposition en cours 
     * @param vo : PrimitiveVO
     * @return   : MontantMiseDiposition : montant mise à disposition
     * @autor    : Youssef BOUSSEN 
     */

     public ValueObject perform(IValueObject vo) { 
    
        MontantMiseDiposition montantMiseDiposition= new MontantMiseDiposition();

        try{

            PrimitiveVO primitiveVO = (PrimitiveVO)vo;
            List listMAD = new ArrayList();
            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
            ICriteria criteria         = searchEngine.createCriteria();
            IExpression expression     = searchEngine.createExpression();

            /* Rechercher de la liste des MAD en cours*/
            criteria.add(expression.eq("montantMiseDipositionId.numMmadMmad",primitiveVO.getVString()));
            criteria.add(expression.isNull("datRetMmad"));

            listMAD = searchEngine.find(MontantMiseDiposition.class,criteria);

            if (listMAD != null && listMAD.size() > 0) {/* mises a disposition */
                 montantMiseDiposition =(MontantMiseDiposition) listMAD.get(0);
            }
        return (montantMiseDiposition);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans GetMontantMADById : ");
              text.append(e.toString());
              erreur.setCode("500");
              erreur.setDescription(text.toString());
              erreur.setKey("GetMontantMADById");
              montantMiseDiposition.addError(erreur);
              return (montantMiseDiposition);
          }

    
    }

    public void genCroText(ValueObject vo) {
    }
    
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
}
