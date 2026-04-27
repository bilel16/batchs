package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.service.ModificationDonneesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * classe de rechercher des personnes par leur nom, prenom, raison sociale et sigle 
 * @since 28/05/2008    
 * @author Mdimagh Lassaad 
 */

public class RecherchePersonneParNomCmd {
    public RecherchePersonneParNomCmd() {
    }
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ModificationDonneesService modificationDonneesService = 
            (ModificationDonneesService)context.getBean("modificationDonneesService");

          return (modificationDonneesService.RecherchePersonneParNom(vo) );
    }
    
}
