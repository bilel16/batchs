package com.bna.smile.model.clotureDomaine.traitement;

import java.util.List;

import org.hibernate.criterion.Order;

import com.bna.commun.model.DemandeDecision;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.clotureDomaine.model.PramDetailPlacVo;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class DetailSouscPlacementTrt extends Traitement {
    public DetailSouscPlacementTrt() {
    }

    public ValueObject perform(IValueObject vo) {
        PramDetailPlacVo pramDetailPlacVo = (PramDetailPlacVo)vo;
        Listes listeDemandeDecision = new Listes();


        try {

            ISearchEngine searchEngine = 
                (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            this.setCroFlag(false);


            if (pramDetailPlacVo.getEtat() != null) {
                criteria.add(expression.eq("codEtatDemd", 
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


            if (pramDetailPlacVo.getDateCreation() != null) {
                criteria.add(expression.eq("datCreDemd", 
                                           pramDetailPlacVo.getDateCreation()));
            }


            if (pramDetailPlacVo.getDatevalidation() != null) {
                criteria.add(expression.eq("datVldDemd", 
                                           pramDetailPlacVo.getDatevalidation()));
            }
            if (pramDetailPlacVo.getTypedem() != null) {
                criteria.add(expression.eq("codNdemDemd", 
                                           pramDetailPlacVo.getTypedem()));
            }
            if (pramDetailPlacVo.getCodTyprDemd() != null) {
                criteria.add(expression.eq("codTyprDemd", 
                                           pramDetailPlacVo.getCodTyprDemd()));
            }
            if (pramDetailPlacVo.getStructureValid() != null) {
                criteria.add(expression.eq("structure.codStrcStrc", 
                                           pramDetailPlacVo.getStructureValid()));
            }
            if (pramDetailPlacVo.getTypeCond() != null) {
                if (pramDetailPlacVo.getTypeCond().equalsIgnoreCase("G")) {
                    criteria.add(expression.eq("codFavDemd", 
                                               pramDetailPlacVo.getTypeCond()));
                } else {
                    criteria.add(expression.ne("codFavDemd", "G"));
                }
            }
            criteria.addOrder(Order.desc("numRefdDemd"));

            List l = searchEngine.find(DemandeDecision.class, criteria);
            if (l != null && l.size() > 0)
                listeDemandeDecision.setList(l);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans DetailSouscPlacementTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("DetailSouscPlacementTrt");
            listeDemandeDecision.addError(erreur);
            logger.error("Exception : ", e);
            throw new RuntimeException(e);

        }
        return (listeDemandeDecision);
    }

    public void genCroText(ValueObject vo) {

    }
}
