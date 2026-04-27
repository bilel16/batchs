package com.bna.smile.model.pilotage.service;

import com.bna.smile.model.pilotage.traitement.GetDonnClientTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;

public class PilotageService extends BasicService{
    public PilotageService() {
    }
    public IValueObject getDonnClient(IValueObject vo) {

        GetDonnClientTrt getDonnClientTrt = new GetDonnClientTrt();
        return (getDonnClientTrt.exec(vo));

    }
}
