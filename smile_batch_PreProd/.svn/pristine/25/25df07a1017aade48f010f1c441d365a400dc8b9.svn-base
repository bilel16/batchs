package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Pays;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.ContratCompteService;

import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet pays
 * @author Mdimagh Med
 * @since 30/05/07
 */
public class GetPaysCmd implements ICommande{
    public GetPaysCmd() {
    }

    /**
     * executer la recherce de l'objet pays
     * @param vo :Pays
     * @return vO : pays
     */
    public IValueObject execute(IValueObject vo) {
        Pays pays = (Pays)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        pays = (Pays)nomenclatureService.getPays(pays);
        return (pays);
    }
}
