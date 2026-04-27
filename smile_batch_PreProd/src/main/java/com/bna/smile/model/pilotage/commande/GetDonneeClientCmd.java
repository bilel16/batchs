package com.bna.smile.model.pilotage.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.pilotage.service.PilotageService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetDonneeClientCmd implements ICommande{
    public GetDonneeClientCmd() {
    }
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        PilotageService pilotageService = 
            (PilotageService)context.getBean("pilotageService");
        return (pilotageService.getDonnClient(vo));

    }
}
