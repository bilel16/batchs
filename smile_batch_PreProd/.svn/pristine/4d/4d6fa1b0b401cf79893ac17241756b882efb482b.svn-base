package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.model.Mandat;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetMandatCmd implements ICommande{
    public GetMandatCmd() {
    }


    /**
         * Methode execute
         * @param vo Objet : Mandat
         * @return   Objet : DetaiMandat
         */
    public IValueObject execute(IValueObject vo) {
        Mandat mandat = (Mandat)vo;
        Context context = ContextHandler.getContext();

        ProcurationService procurationService = 
            (ProcurationService)context.getBean("procurationService");
        Mandat mandatRetour = (Mandat)procurationService.getMandat(mandat);
        return (mandatRetour);

    }

}
