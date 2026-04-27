package com.bna.smile.model.banqueAssurance.commande;

import com.bna.commun.model.DetailAdhesion;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.banqueAssurance.service.AssuranceVieService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class PecChangCptFactCmd implements ICommande{
    public PecChangCptFactCmd() {
    }
    /**
    * methode execute
    * @param  vo Objet : ParamAdhesion
    * @return vo Objet : ParamAdhesion
    * @author Kriaa hatem 15/09/2010
    */
    public IValueObject execute(IValueObject vo) {
    ParamAdhesion paramAdhesion = (ParamAdhesion)vo;
    Context context = ContextHandler.getContext();
    AssuranceVieService assuranceVieService = 
        (AssuranceVieService)context.getBean("assuranceVieService");
    DetailAdhesion detailAdhesion = 
        (DetailAdhesion)assuranceVieService.pecChangCptFact(paramAdhesion);
    return (detailAdhesion);
    }
    
}
