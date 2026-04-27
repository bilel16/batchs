package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.CatSocProf;
import com.bna.commun.model.Groupe;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet categorie socioprofessionel
 * @author Mdimagh Med
 * @since 10/07/07
 */
public class GetCatSocProfCmd implements ICommande{
    public GetCatSocProfCmd() {
    }

    /**
     * executer la recherce de l'objet Groupe
     * @param vo  :CatSocProf
     * @return vO :CatSocProf
     */
    public IValueObject execute(IValueObject vo) {

        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");

        return (nomenclatureService.getCatSocProf(vo));
    }

}
