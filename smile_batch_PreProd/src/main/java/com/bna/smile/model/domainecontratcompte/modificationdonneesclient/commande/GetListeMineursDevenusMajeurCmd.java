package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.service.ModificationDonneesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * classe de rechercher les mineurs devenus majeur pour une structure
 * @since 23/05/2008    
 * @author Mdimagh Lassaad 
 */
 
public class GetListeMineursDevenusMajeurCmd {
    public GetListeMineursDevenusMajeurCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ModificationDonneesService modificationDonneesService = 
            (ModificationDonneesService)context.getBean("modificationDonneesService");

          return (modificationDonneesService.getListeMineursDevenusMajeurs(vo) );
    }
}
