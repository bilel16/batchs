package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Groupe;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet Groupe
 * @author Mdimagh Med
 * @since 27/06/07
 */
public class GetGroupeCmd implements ICommande {
    public GetGroupeCmd() {
    }
    
    /**
     * executer la recherce de l'objet Groupe
     * @param vo  :Groupe
     * @return vO :Groupe
     */
    public IValueObject execute(IValueObject vo) {
        Groupe groupe = (Groupe)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        groupe = (Groupe)nomenclatureService.getGroupe(groupe);
        return (groupe);
    }
}
