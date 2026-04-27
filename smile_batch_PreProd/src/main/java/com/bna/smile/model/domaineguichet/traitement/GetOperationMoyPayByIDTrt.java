package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;

import com.bna.commun.vo.PrimitiveVO;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;


public class GetOperationMoyPayByIDTrt extends Traitement{
        
        public Context context = ContextHandler.getContext();

    public GetOperationMoyPayByIDTrt() {
    }
 

    /**
     * Methode permet trouver une OperationsMoyenPay par son identifiant
     * @param vo : PrimitiveVO (N° OperationMoyPay)
     * @return   : OperationMoyPay : OperationsMoyenPay
     * @autor    : Youssef BOUSSEN 
     */

     public ValueObject perform(IValueObject vo) { 
    
        OperationMoyPay operationMoyPay = new OperationMoyPay();

        try{

            PrimitiveVO primitiveVO = (PrimitiveVO)vo;
            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 

            /* Rechercher l' OperationsMoyenPay */
                
            operationMoyPay = (OperationMoyPay) searchEngine.get(OperationMoyPay.class,primitiveVO.getVString());

            return (operationMoyPay);
        }
        catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans GetOperationMoyPayByID : ");
              text.append(e.toString());
              erreur.setCode("800");
              erreur.setDescription(text.toString());
              erreur.setKey("GetOperationMoyPayByID");
              operationMoyPay.addError(erreur);
              return (operationMoyPay);
        }

    
    }



    public void genCroText(ValueObject vo) {
    }
    
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
}

