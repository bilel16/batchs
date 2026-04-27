package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.model.ParamBonCaisse;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/**
 * vérifier que le numero BC existe dans les carnets BC de l'agence, et vérifier que ce num. n'est pas affecté a un contrat placement
 * @author Lamia
 * @param ParamBonCaisse
 * @return ParamBonCaisse
 * @since 02/04/2009
 * 
 */
public class GetParamBonCaisseCmd implements ICommande{
    public GetParamBonCaisseCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamBonCaisse paramBonCaisse = (ParamBonCaisse)vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        ParamBonCaisse paramBonCaisseRetour = (ParamBonCaisse)placementService.verifNumBonCaisse(paramBonCaisse);
        return paramBonCaisseRetour;
    }
}
