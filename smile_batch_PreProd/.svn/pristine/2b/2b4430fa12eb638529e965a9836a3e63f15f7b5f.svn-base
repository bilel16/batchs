package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.service.ModificationDonneesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * Classe qui permet de faire les corrections des données client
 * @author Mdimagh
 * @since 23/07/2008
 */
public class CorrectionDonneesClientCmd {
    public CorrectionDonneesClientCmd() {
    }
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ModificationDonneesService modificationDonneesService = 
            (ModificationDonneesService)context.getBean("modificationDonneesService");

          return (modificationDonneesService.correctionDonneesClient(vo) );
    }
}
