package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertCompte519Cmd implements ICommande{
 
    public IValueObject execute(IValueObject vo) {
            
            Context context = ContextHandler.getContext();
            SouscriptionContratCompteService souscriptionContratCompteService = 
                (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
            ValueObject voo = (ValueObject)souscriptionContratCompteService.insertClientContCompte519(vo);
            return (voo);
        }
}
