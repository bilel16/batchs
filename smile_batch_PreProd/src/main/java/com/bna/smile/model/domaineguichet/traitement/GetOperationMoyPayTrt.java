package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.DateHandler;
import com.bna.commun.vo.PrimitiveVO;

import com.bna.smile.model.domainecommun.model.Listes;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetOperationMoyPayTrt extends Traitement{

    public Context context = ContextHandler.getContext();

    public GetOperationMoyPayTrt() {
    }
    
    /**
     * Methode permet trouver les les OperationsMoyenPay par agence (en attente ou prévalidé)
     * @param vo : PrimitiveVO (cod agence , etat OperationMoyPay)
     * @return   : Listes :  liste des OperationsMoyenPay
     * @autor    : Youssef BOUSSEN 
     */

     public ValueObject perform(IValueObject vo) { 
    
        Listes listeOMP = new Listes();

        try{

            PrimitiveVO primitiveVO = (PrimitiveVO)vo;
            List listOperationMoyPay = new ArrayList();
            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
            ICriteria criteria         = searchEngine.createCriteria();
            IExpression expression     = searchEngine.createExpression();
            primitiveVO.setVDate(new Date());

            /* Rechercher de la liste des OperationsMoyenPay */
            if (primitiveVO.getVDouble().intValue()!=2){
                if (primitiveVO.getVDouble().intValue()==0){ /// OperationsMoyenPay dont l'agence receptrice est celle demandeur
                criteria.add(expression.eq("structureReceptrice.codStrcStrc",primitiveVO.getVLong())); /// structure
                }else criteria.add(expression.eq("structureInitiatrice.codStrcStrc",primitiveVO.getVLong())); /// OperationsMoyenPay dont l'agence initiatrice est celle demandeur
    
                if (primitiveVO.getVString().equalsIgnoreCase(Constants.COD_VALIDATION)){
                    if (primitiveVO.getVDouble().intValue()==3) // retrait deplacé < taux 
                    criteria.add(expression.eq("operation.codOperOper", Constants.COD_OPER_RETRAIT_DEPL_EMIS));
                    else
                    criteria.add(expression.eq("operation.codOperOper", Constants.COD_OPER_RETRAIT));/// code opération                 
                }else criteria.add(expression.eq("operation.codOperOper", Constants.COD_OPER_RETRAIT_DEPL_EMIS)); /// code opération                 
            
            }else {/// liste des retraits emis
                criteria.add(expression.eq("operation.codOperOper", Constants.COD_OPER_RETRAIT_DEPL_EMIS)); 
                criteria.add(expression.eq("structureInitiatrice.codStrcStrc",primitiveVO.getVLong())); /// OperationsMoyenPay dont l'agence initiatrice est celle demandante
            }
                criteria.add(expression.eq("codEtatOmp",primitiveVO.getVString())); /// etat 
                criteria.add(expression.ge("datOperOmp",DateHandler.addJour(primitiveVO.getVDate(),-1))); /// OperationsMoyenPay des dernieres 24 H
            
/*            if (primitiveVO.getVString().equalsIgnoreCase(Constants.COD_ATTENTE))
                criteria.add(expression.eq("operation.codOperOper",Constants.COD_OPER_RETRAIT_DEPL_EMIS)); /// code opération                 
            else if (primitiveVO.getVString().equalsIgnoreCase(Constants.COD_PREVALID))
                criteria.add(expression.eq("operation.codOperOper",Constants.COD_OPER_RETRAIT_DEPL_RECU)); /// code opération                 
*/                
            listOperationMoyPay = searchEngine.find(OperationMoyPay.class,criteria);

            listeOMP.setList(listOperationMoyPay);
        return (listeOMP);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans GetOperationMoyPayTrt : ");
              text.append(e.toString());
              erreur.setCode("500");
              erreur.setDescription(text.toString());
              erreur.setKey("GetOperationMoyPayTrt");
              listeOMP.addError(erreur);
              return (listeOMP);
          }

    
    }



    public void genCroText(ValueObject vo) {
    }
    
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
}
