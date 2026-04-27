package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet NiveauInstruction
 * @author Mdimagh Med
 * @since 18/06/07
 */
public class GetNiveauInstructionCmd implements ICommande{
    public GetNiveauInstructionCmd() {
    }

    /**
     * executer la recherce de l'objet NiveauInstruction
     * @param vo  :NiveauInstruction
     * @return vo :NiveauInstruction
     */
    public

    IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        return (nomenclatureService.getNiveauInstruction(vo));

    }
}
