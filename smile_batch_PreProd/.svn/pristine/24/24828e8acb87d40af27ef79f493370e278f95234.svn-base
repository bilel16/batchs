package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.InterditChq;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;


import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.List;

public class VerifInterditChequierTrt {

    public Context context = ContextHandler.getContext();

    public VerifInterditChequierTrt() {
    }

    /**
     * Methode permet de verifier si la personne est interdit de chéquier 
     * @param vo : PersonneStrc
     * @return   : PrimitiveVO (boolean)
     * @autor      EL ARBI HASSINE
     */
    public

    ValueObject execute(ValueObject vo) {

        PersonneStrc personneStrc = (PersonneStrc)vo;
        PrimitiveVO primitiveVO = new PrimitiveVO();
        try {
            
            primitiveVO.setVBool(false);

            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

            /* Rechercher de interdit de chequier */
            criteria.add(expression.eq("typePiece", 
                                       (personneStrc.getCodTpceTpce())));
            criteria.add(expression.eq("numPiece", 
                                       (personneStrc.getNumPcePers())));
                                       
            criteria.add(expression.isNotNull("dateInterdiction"));
            criteria.add(expression.isNull("dateLeveInter"));
            
            List l = searchEngine.find(InterditChq.class, criteria);

            if (l != null && l.size() > 0) { /* interdit de chequier */
                InterditChq interditChq = (InterditChq)l.get(0);
                if (interditChq.getStatus().equalsIgnoreCase("O"))
                    primitiveVO.setVBool(true);
            }
            return (primitiveVO);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans interditchequierTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("interditchequierTrt");
            primitiveVO.addError(erreur);
            return (primitiveVO);
        }

    }
    
    public String getNumeroTache(IValueObject vo){
        return Constants.CODE_RESSOURCE_GENERALE;   
    }

}
