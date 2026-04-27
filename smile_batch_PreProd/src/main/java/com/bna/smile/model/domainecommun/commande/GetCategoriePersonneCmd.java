package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.CategoriePersonne;
import com.bna.commun.model.FormeJuridique;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetCategoriePersonneCmd implements ICommande{
    public GetCategoriePersonneCmd() {
    }

    /**
     * executer la recherce de l'objet CategoriePersonne
     * @param vo  : CategoriePersonne
     * @return vO : CategoriePersonne
     */
    public IValueObject execute(IValueObject vo) {
        CategoriePersonne categoriePersonne = (CategoriePersonne)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        categoriePersonne = 
                (CategoriePersonne)nomenclatureService.getCategoriePersonne(categoriePersonne);
        return (categoriePersonne);
    }
}
