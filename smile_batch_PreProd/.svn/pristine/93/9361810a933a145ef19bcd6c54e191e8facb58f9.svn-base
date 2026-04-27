package com.bna.smile.model.banqueAssurance.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.banqueAssurance.service.AssuranceVieService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class ValidResiliationAssVieCmd implements ICommande {
    public ValidResiliationAssVieCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
    ParamAdhesion paramAdhesion = (ParamAdhesion)vo;
    Context context = ContextHandler.getContext();
    AssuranceVieService assuranceVieService = 
        (AssuranceVieService)context.getBean("assuranceVieService");
    ParamAdhesion paramAdhesionRet = 
        (ParamAdhesion)assuranceVieService.validResiliation(paramAdhesion);
    return (paramAdhesionRet);
    }
    
}
