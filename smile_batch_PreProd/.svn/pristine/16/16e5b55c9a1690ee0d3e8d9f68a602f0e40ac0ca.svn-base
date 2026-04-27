package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.CodePostal;
import com.bna.commun.model.FormeJuridique;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet Forme juridique
 * @author Mdimagh Med
 * @since 20/06/07
 */
public class GetFormeJuridiqueCmd implements ICommande{
    public GetFormeJuridiqueCmd() {
    }
    /**
     * executer la recherce de l'objet CodePostal
     * @param vo  : FormeJuridique
     * @return vO : FormeJuridique
     */
    public

    IValueObject execute(IValueObject vo) {
        FormeJuridique formeJuridique = (FormeJuridique)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        formeJuridique = (FormeJuridique)nomenclatureService.getFomeJuridique(formeJuridique);
        return (formeJuridique);
    }
}
