package com.bna.smile.model.reporting.traitement;


import java.util.List;

import org.hibernate.criterion.Order;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.reporting.model.ParamMoyPayVo;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class GetListOperMoyPayExtraitTrt  extends Traitement {
    public GetListOperMoyPayExtraitTrt() {
    }
    /**
     * Fonction qui permet de determiner la liste des opération sur un contrat par période (extrait d compte)
     * @Author : lamia jerbi
     * @since 29/01/2009
     */

    public IValueObject perform(IValueObject vo)  throws Exception{
        
        Listes listOperationMoyPay =  new Listes();
        ParamMoyPayVo paramMoyPayVo = (ParamMoyPayVo)vo;
        try
        {
            ICriteria criteria = getSearchEngine().createCriteria();
            IExpression expression = getSearchEngine().createExpression();
            paramMoyPayVo= (ParamMoyPayVo)vo;
            if (paramMoyPayVo.getDateDeb()!=null && paramMoyPayVo.getDateFin()!=null && paramMoyPayVo.getIdContrat()!=null) 
            {
                criteria.add(expression.eq("contratCpt.contratCptId", paramMoyPayVo.getIdContrat())); 
                        //----------------------------------------------recherche par période
               if (paramMoyPayVo.getDateDeb() != null) {
                    criteria.add(expression.ge("datOperOmp", 
                                               paramMoyPayVo.getDateDeb()));
                }
                if (paramMoyPayVo.getDateFin() != null) {
                    criteria.add(expression.le("datOperOmp", 
                                               paramMoyPayVo.getDateFin()));
                }
            
               criteria.addOrder(Order.desc("datOperOmp"));
               criteria.addOrder(Order.desc("numOperOmp"));
               
               List liste = getSearchEngine().find(OperationMoyPay.class,criteria);
               if(liste != null || liste.size()!=0 ){
                   listOperationMoyPay.setList(liste);
                   }else{
                       logger.debug("liste null");
                   }
           }
          return (listOperationMoyPay); 
        }
         catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListOperMoyPayExtraitTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            listOperationMoyPay.addError(erreur);
            return (listOperationMoyPay);  
        }   
       
        
    }
    public void genCroText(ValueObject vo) {
            ;
        } 
}
