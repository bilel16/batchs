package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

public class GetMontantMADByIdPersCmd {
    public GetMontantMADByIdPersCmd() {
    }
    /**
     * methode execute
     * @param  vo Objet : PersonneStrc
     * @return vo Objet : Listes
     */
    public ValueObject execute(ValueObject vo) {
        PersonneStrc personneStrc = (PersonneStrc)vo;
        Context context = ContextHandler.getContext();

        GuichetService guichetService = (GuichetService)context.getBean("guichetService");
        Listes listes = (Listes)guichetService.GetMontantMADByIdPers(personneStrc);
        return (listes);
    
    }
    
}
