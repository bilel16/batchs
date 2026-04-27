package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.CodePostal;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet CodePostal
 * @author Mdimagh Med
 * @since 30/05/07
 */
public class GetCodePostalCmd implements ICommande{
    public GetCodePostalCmd() {
    }

    /**
     * executer la recherce de l'objet CodePostal
     * @param vo :CodePostal
     * @return vO : CodePostal
     */
    public

    IValueObject execute(IValueObject vo) {
        CodePostal codePostal = (CodePostal)vo;
        Context context = ContextHandler.getContext();
        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        codePostal = (CodePostal)nomenclatureService.getCodePostal(codePostal);
        return (codePostal);
    }
}
