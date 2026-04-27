package com.bna.smile.model.banqueAssurance.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.banqueAssurance.service.AssuranceVieService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class ValiderAdhesionAssVieCmd implements ICommande {
    public ValiderAdhesionAssVieCmd() {
    }
    /**
    * methode execute
    * @param  vo Objet : paramAdhesion
    * @return vo Objet : paramAdhesion
    * @author lamia
    */
    public IValueObject execute(IValueObject vo) {
    ParamAdhesion paramAdhesion = (ParamAdhesion)vo;
    Context context = ContextHandler.getContext();
    AssuranceVieService assuranceVieService = 
        (AssuranceVieService)context.getBean("assuranceVieService");
    ParamAdhesion paramAdhesionRetour = 
        (ParamAdhesion)assuranceVieService.validAdhesionAssVie(paramAdhesion);
    return (paramAdhesionRetour);
    }
    
}
