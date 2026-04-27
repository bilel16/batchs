package com.bna.smile.model.banqueAssurance.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.banqueAssurance.service.AssuranceVieService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class RejeterChangCptFactCmd implements ICommande{
    public RejeterChangCptFactCmd() {
    }
    /**
    * methode execute
    * @param  vo Objet : ParamAdhesion
    * @return vo Objet : ParamAdhesion
    * @author Kriaa hatem 24/09/2010
    */
    public IValueObject execute(IValueObject vo) {
    ParamAdhesion paramAdhesion = (ParamAdhesion)vo;
    Context context = ContextHandler.getContext();
    AssuranceVieService assuranceVieService = 
        (AssuranceVieService)context.getBean("assuranceVieService");
    ParamAdhesion paramAdhesionRet = 
        (ParamAdhesion)assuranceVieService.rejeterChangCptFact(paramAdhesion);
    return (paramAdhesionRet);
    }
}
