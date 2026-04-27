package com.bna.smile.model.clotureDomaine.traitement;

import java.util.List;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.model.ParamAvanRembLiq;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class DetailAvancRembLiqTrt extends Traitement {
    public DetailAvancRembLiqTrt() {
    }

    public ValueObject perform(IValueObject vo) {
        ParamAvanRembLiq paramAvanRembLiq = (ParamAvanRembLiq)vo;
        Listes listeContratPlacement = new Listes();


        try {

            ISearchEngine searchEngine = 
                (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            this.setCroFlag(false);


            if (paramAvanRembLiq.getCodEtatArl() != null)
                criteria.add(expression.eq("codEtatArl", 
                                           paramAvanRembLiq.getCodEtatArl()));

            if (paramAvanRembLiq.getCodToprtArl() != null)
                criteria.add(expression.eq("codToprArl", 
                                           paramAvanRembLiq.getCodToprtArl()));

           
               
            if (paramAvanRembLiq.getCodEtatArl().equalsIgnoreCase(Constants.ETAT_ARL_VALIDEE)) {
                if (paramAvanRembLiq.getCodToprtArl().equalsIgnoreCase(Constants.CODE_AVANCE)) {
                    criteria.add(expression.eq("datArlArl", 
                                               paramAvanRembLiq.getDateComptable()));
                    criteria.add(expression.isNull("datReelArl"));
                } else if (paramAvanRembLiq.getCodToprtArl().equalsIgnoreCase(Constants.CODE_REMBOURSEMENT_AVANCE)) {
                    criteria.add(expression.ge("datReelArl", 
                                               paramAvanRembLiq.getDateComptable()));
                    criteria.add(expression.le("datReelArl", 
                                               DateHandler.addJour(paramAvanRembLiq.getDateComptable(),1) ));
                }else{
                    criteria.add(expression.eq("datArlArl", 
                                               paramAvanRembLiq.getDateComptable()));
                }

            }else{
                criteria.add(expression.eq("datArlArl", 
                                           paramAvanRembLiq.getDateComptable()));
            }
            if (paramAvanRembLiq.getTypeLiquidation() != null)
                criteria.add(expression.eq("codTyplArl", 
                                           paramAvanRembLiq.getTypeLiquidation()));

            List l = searchEngine.find(AvancRembLiquid.class, criteria);
            if (l != null && l.size() > 0)
                listeContratPlacement.setList(l);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans DetailAvancRembLiqTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("DetailAvancRembLiqTrt");
            listeContratPlacement.addError(erreur);
            logger.error("Exception : ", e);
            throw new RuntimeException(e);

        }
        return (listeContratPlacement);
    }

    public void genCroText(ValueObject vo) {

    }
}
