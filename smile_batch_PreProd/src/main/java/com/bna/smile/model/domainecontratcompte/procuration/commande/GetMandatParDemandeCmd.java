package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.model.Mandat;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.procuration.model.DossierMandat;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetMandatParDemandeCmd implements ICommande{
    public GetMandatParDemandeCmd() {
    }
    
    
    /**
     * Methode execute
     * @param vo Objet : DossierMandat
     * @return   Objet : Mandat
     */
    public IValueObject execute(IValueObject vo) {
        DossierMandat dossierMandat = (DossierMandat)vo;
        Context context = ContextHandler.getContext();
                 
        ProcurationService procurationService = (ProcurationService)context.getBean("procurationService");
        Mandat mandat = (Mandat)procurationService.getMandatParDemande(dossierMandat);
        return(mandat);
    }
}
