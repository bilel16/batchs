package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.CategoriePersonne;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;


public class GetListCategoriesPersonneCmd implements ICommande{
    public GetListCategoriesPersonneCmd() {
    }

    /**
     * executer la recherce de l'objet CategoriePersonne
     * @param vo  : CategoriePersonne
     * @return vO : CategoriePersonne
     */
    public IValueObject execute(IValueObject vo) {
        Listes categoriesPersonne = (Listes)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        categoriesPersonne = (Listes)nomenclatureService.getListCategoriesPersonneService(categoriesPersonne);
        return (categoriesPersonne);
    }
}
