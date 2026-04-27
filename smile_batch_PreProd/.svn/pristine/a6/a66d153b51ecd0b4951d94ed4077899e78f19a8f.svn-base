package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.Activite;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet Gouvernorat
 * @author Mdimagh Med
 * @since 13/06/07
 */
public class GetGouvernoratCmd implements ICommande {
    public GetGouvernoratCmd() {
    }
    /**
     * executer la recherce de l'objet Gouvernorat
     * @param vo  :Gouvernorat
     * @return vO :Gouvernorat
     */
    public

    IValueObject execute(IValueObject vo) {
        Gouvernorat gouvernorat = (Gouvernorat)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        gouvernorat = (Gouvernorat)nomenclatureService.getGouvernorat(gouvernorat);
        return (gouvernorat);
    }
}
