package com.bna.smile.model.clotureDomaine.traitement;


import java.util.List;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.clotureDomaine.model.OperMoyPayVo;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class DetailOperMoyPayTrt extends Traitement {
    public DetailOperMoyPayTrt() {
    }

    public ValueObject perform(IValueObject vo) {
        OperMoyPayVo operMoyPayVo = (OperMoyPayVo)vo;
        Listes listeOperMoyPay = new Listes();


        try {

            ISearchEngine searchEngine = 
                (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            this.setCroFlag(false);


            if (operMoyPayVo.getCodStrcStrc() != null) {
                criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                           operMoyPayVo.getCodStrcStrc()));
            }

            if (operMoyPayVo.getCodOperOpm() != null) {
                criteria.add(expression.eq("tache.tacheId.codOperOper", 
                                           operMoyPayVo.getCodOperOpm()));
            }

            if (operMoyPayVo.getDateOperOpm() != null) {
                criteria.add(expression.eq("datOperOmp", 
                                           operMoyPayVo.getDateOperOpm()));
            }
            if (operMoyPayVo.getDateValOpm() != null) {
                criteria.add(expression.eq("datValOmp", 
                                           operMoyPayVo.getDateValOpm()));
            }


            List l = searchEngine.find(OperationMoyPay.class, criteria);
            if (l != null && l.size() > 0)
                listeOperMoyPay.setList(l);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans DetailOperMoyPayTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("DetailOperMoyPayTrt");
            listeOperMoyPay.addError(erreur);
            logger.error("Exception : ", e);
            throw new RuntimeException(e);

        }
        return (listeOperMoyPay);
    }

    public void genCroText(ValueObject vo) {

    }
}
