package com.bna.smile.model.clotureDomaine.traitement;

import java.util.List;

import org.hibernate.criterion.Order;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.clotureDomaine.model.PramDetailPlacVo;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class DetailContratPlacementTrt extends Traitement {
    public DetailContratPlacementTrt() {
    }

    public ValueObject perform(IValueObject vo) {
        PramDetailPlacVo pramDetailPlacVo = (PramDetailPlacVo)vo;
        Listes listeContratPlacement = new Listes();


        try {

            ISearchEngine searchEngine = 
                (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            this.setCroFlag(false);


            if (pramDetailPlacVo.getEtat() != null) {
                criteria.add(expression.eq("codEtatCpla", 
                                           pramDetailPlacVo.getEtat()));
            }


            if (pramDetailPlacVo.getCodeProduit() != null) {
                criteria.add(expression.eq("produitPlacement.codPrdPlc", 
                                           pramDetailPlacVo.getCodeProduit()));
            }


            if (pramDetailPlacVo.getCodeStructure() != null) {
                criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                           pramDetailPlacVo.getCodeStructure()));
            }

            if (pramDetailPlacVo.getDatevalidation() != null) {
                criteria.add(expression.eq("datVldCpla", 
                                           pramDetailPlacVo.getDatevalidation()));
            }
            if (pramDetailPlacVo.getDateEcheance() != null) {
                criteria.add(expression.eq("datEcheCpla", 
                                           pramDetailPlacVo.getDateEcheance()));
            }
            if (pramDetailPlacVo.getDateCreation() != null) {
                criteria.add(expression.eq("datCreCpla", 
                                           pramDetailPlacVo.getDateCreation()));
            }
            if (pramDetailPlacVo.getDateLiquidation() != null) {
                criteria.add(expression.eq("datLiqCpla", 
                                           pramDetailPlacVo.getDateLiquidation()));
            }
            if (pramDetailPlacVo.getDateSup() != null) {
                criteria.add(expression.le("datEcheCpla", 
                                           pramDetailPlacVo.getDateSup()));
            }
            if (pramDetailPlacVo.getDateInfLiq() != null) {
                criteria.add(expression.ge("datLiqCpla", 
                                           pramDetailPlacVo.getDateInfLiq()));
            }
            if (pramDetailPlacVo.getDateSupLiq() != null) {
                criteria.add(expression.le("datLiqCpla", 
                                           pramDetailPlacVo.getDateSupLiq()));
            }
            if ((pramDetailPlacVo.getTypedem()!=null)&&(pramDetailPlacVo.getTypedem().equalsIgnoreCase("R"))) {
                criteria.add(expression.isNotNull("contratPlacementByNumSqcrCpla.numSeqCpla"));
            }

            criteria.addOrder(Order.desc("numSeqCpla"));
            List l = searchEngine.find(ContratPlacement.class, criteria);
            if (l != null && l.size() > 0)
                listeContratPlacement.setList(l);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans DetailContratPlacementTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("DetailContratPlacementTrt");
            listeContratPlacement.addError(erreur);
            logger.error("Exception : ", e);
            throw new RuntimeException(e);

        }
        return (listeContratPlacement);
    }

    public void genCroText(ValueObject vo) {

    }
}
