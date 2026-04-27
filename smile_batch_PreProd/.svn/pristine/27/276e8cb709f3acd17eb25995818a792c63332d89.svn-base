package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.Activite;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet profession
 * @author Mdimagh Med
 * @since 05/06/07
 */
public class GetActiviteByIdCmd implements ICommande{
    public GetActiviteByIdCmd() {
    }

    /**
     * executer la recherce de l'objet Activite
     * @param vo  :Activite
     * @return vO :Activite
     */
    public  IValueObject execute(IValueObject vo) {
        Activite activite = (Activite)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        activite = (Activite)nomenclatureService.getActiviteById(activite);
        return (activite);
    }
}
