package com.bna.smile.model.domainecommun.traitement;


import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.OperationCompte;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;


import com.bna.commun.util.DateHandler;
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
import java.util.Iterator;
import java.util.List;

public class GetDateDerniereOperationTrt extends Traitement{
    public Context context = ContextHandler.getContext();

    public GetDateDerniereOperationTrt() {
    }
    
    
    public IValueObject perform(IValueObject vo) {
    
        MandatOperation mandatOperation = (MandatOperation)vo;
        PrimitiveVO primitiveVO = new PrimitiveVO();
        OperationMoyPay operationMoyPay=new OperationMoyPay();
    try{
            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
            ICriteria criteria         = searchEngine.createCriteria();
            IExpression expression     = searchEngine.createExpression();

            /* Rechercher de l'Opeartion_Compte */
            criteria.add(expression.eq("mandatOperation.mandatOperationId.numMaopMaop", mandatOperation.getMandatOperationId().getNumMaopMaop()));

            List l = searchEngine.find(OperationMoyPay.class, criteria);

            if (l != null && l.size() > 0) {
                operationMoyPay.setDatOperOmp((DateHandler.addJour(new Date(),-400)));

                for (Iterator it =l.iterator(); it.hasNext();){
                    OperationMoyPay operationMoyPay0 = (OperationMoyPay)it.next();
                    
                    if (operationMoyPay0.getDatOperOmp().after(operationMoyPay.getDatOperOmp()))
                        operationMoyPay.setDatOperOmp(operationMoyPay0.getDatOperOmp());
                }
            
             primitiveVO.setVDate(operationMoyPay.getDatOperOmp());    
            
            }else { 
                primitiveVO.setVDate(DateHandler.addJour(new Date(),-400));
                }
            
            
            
        return (primitiveVO);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans GetDateDerniereOperationTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("GetDateDerniereOperationTrt");
              primitiveVO.addError(erreur);
              return (primitiveVO);
          }
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
