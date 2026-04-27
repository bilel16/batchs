package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.Pays;
import com.bna.commun.model.Profession;
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
public class GetProfessionByIdCmd implements ICommande {
    public GetProfessionByIdCmd() {
    }

    /**
     * executer la recherce de l'objet pays
     * @param vo :Profession
     * @return vO :Profession
     */
    public IValueObject execute(IValueObject vo) {
        Profession profession = (Profession)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        profession = 
                (Profession)nomenclatureService.getProfessionById(profession);
        return (profession);
    }
}
