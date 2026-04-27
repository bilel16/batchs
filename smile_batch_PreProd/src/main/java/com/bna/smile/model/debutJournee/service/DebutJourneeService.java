package com.bna.smile.model.debutJournee.service;



import com.bna.smile.model.debutJournee.traitement.GetDonneeDebJourneeTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;

public class DebutJourneeService extends BasicService{
    public DebutJourneeService() {
    }
    public IValueObject getdonneeDebJournee(IValueObject vo){
    
        GetDonneeDebJourneeTrt getDonneeDebJourneeTrt=new GetDonneeDebJourneeTrt();
        return(getDonneeDebJourneeTrt.exec(vo));
    
    }
}
