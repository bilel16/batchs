package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.CodePostal;
import com.bna.commun.model.TypeModification;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet Type de Modification
 * @author Mdimagh Med
 * @since 30/05/07
 */
public class GetTypeModificationCmd implements ICommande {
    public GetTypeModificationCmd() {
    }


    /**
     * executer la recherce de l'objet du Type de Modifcation
     * @param vo    : TypeModification
     * @return vO   : TypeModification
     */
    public

    IValueObject execute(IValueObject vo) {
        TypeModification typeModification = (TypeModification)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        typeModification = 
                (TypeModification)nomenclatureService.getTypeModification(typeModification);
        return (typeModification);
    }

}
