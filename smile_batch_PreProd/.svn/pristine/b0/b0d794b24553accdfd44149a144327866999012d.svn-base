package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.model.MandPersOperPlac;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/**
 * Inserer une MandPersOperPlac dans la base
 * @author BOUSSEN Youssef
 * @param  MandPersOperPlac
 * @return MandPersOperPlac
 * @since  11/02/2009
 * 
 */
public class InsertMandPersOperPlacCmd implements ICommande{
    public InsertMandPersOperPlacCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        MandPersOperPlac mandPersOperPlac = (MandPersOperPlac)vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        MandPersOperPlac mandPersOperPlacNew = (MandPersOperPlac)placementService.insertMandPersOperPlac(mandPersOperPlac);
        return mandPersOperPlacNew;
    }

}
