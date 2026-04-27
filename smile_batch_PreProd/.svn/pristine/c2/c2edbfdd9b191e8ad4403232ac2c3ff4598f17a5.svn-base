package com.bna.smile.model.clotureDomaine.traitement;

import java.util.List;

import com.bna.commun.model.ModificationDonnees;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamRechercheModificationDonneesVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class DetailRelationClientTrt extends Traitement {
    public DetailRelationClientTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        ParamRechercheModificationDonneesVo paramRechercheModificationDonneesVo = 
            (ParamRechercheModificationDonneesVo)vo;
        try {
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();


            if ((paramRechercheModificationDonneesVo.getDateDebut() != null)) {
                criteria.add(expression.eq("modificationDonneesId.datModModd", 
                                           paramRechercheModificationDonneesVo.getDateDebut()));
            }
            if ((paramRechercheModificationDonneesVo.getTypeModification() != 
                 null)) {
                criteria.add(expression.eq("typeModification.codCodModf", 
                                           paramRechercheModificationDonneesVo.getTypeModification().getCodCodModf()));
            }

            List listModifications = 
                searchEngine.find(ModificationDonnees.class, criteria);

            if (listModifications != null && listModifications.size() > 0) {
                paramRechercheModificationDonneesVo.setListeDesModifications(listModifications);
            }

            return (paramRechercheModificationDonneesVo);


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans DetailRelationClientTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("DetailRelationClientTrt");

            paramRechercheModificationDonneesVo.addError(erreur);
            return (paramRechercheModificationDonneesVo);
        }
    }

    public void genCroText(ValueObject vo) {

    }

    public String getNumeroTache(IValueObject vo) {
        return "70006";
    }
}
