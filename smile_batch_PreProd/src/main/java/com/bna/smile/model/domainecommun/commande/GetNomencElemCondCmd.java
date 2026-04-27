package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.NomencElemtCondition;
import com.bna.commun.model.Pays;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.ContratCompteService;

import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet NomencElemtCondition
 * @author El arbi hassine
 * @since 01/11/07
 * @param NomencElemtCondition
 * @return NomencElemtCondition
 */
public class GetNomencElemCondCmd implements ICommande {
    public GetNomencElemCondCmd() {
    }

   
    public  IValueObject execute(IValueObject vo) {
        NomencElemtCondition nomencElemtCondition = (NomencElemtCondition)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        nomencElemtCondition = (NomencElemtCondition)nomenclatureService.getNomencElemCond(nomencElemtCondition);
        return (nomencElemtCondition);
    }
}
