package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetMandatReserveCmd implements ICommande{
    public GetMandatReserveCmd() {
    }
    public IValueObject execute(IValueObject vo) {
        MandatRecherche mandatRecherche = (MandatRecherche)vo;
        Context context = ContextHandler.getContext();
        ProcurationService procurationService = (ProcurationService)context.getBean("procurationService");
        ContratCptMandat contratCptMandat = 
            (ContratCptMandat)procurationService.getMandatReserve(mandatRecherche);
        return (contratCptMandat);
    }
}
