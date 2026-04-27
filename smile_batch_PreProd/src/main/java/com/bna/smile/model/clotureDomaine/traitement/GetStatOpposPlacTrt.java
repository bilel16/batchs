package com.bna.smile.model.clotureDomaine.traitement;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.clotureDomaine.dao.StatDomPlacementDAO;
import com.bna.smile.model.clotureDomaine.model.StatPlacement;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class GetStatOpposPlacTrt extends Traitement {
    public GetStatOpposPlacTrt() {
    }

    public IValueObject perform(IValueObject vo) throws SQLException, 
                                                        Exception {

        Context context = ContextHandler.getContext();
        StatPlacement statPlacement = (StatPlacement)vo;
        Listes listeretour = new Listes();
        List listtemp = new ArrayList();
        String remplie;

        try {
            ISearchEngine searchEngine = 
                (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            StatDomPlacementDAO statDomPlacementDAO = 
                (StatDomPlacementDAO)context.getBean("statDomPlacementDAO");
            List listeDemande = new ArrayList();


            return null;


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetStatOpposPlacTrt " + e.getMessage());
            statPlacement.addError(erreur);
            throw new Exception(e);
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
