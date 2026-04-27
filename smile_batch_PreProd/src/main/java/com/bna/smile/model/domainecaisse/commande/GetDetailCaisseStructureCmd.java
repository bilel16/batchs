package com.bna.smile.model.domainecaisse.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.service.CaisseService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * commande pour la recherche d'un détail caisse structure
 * @author Mdimagh Med Lassaad
 * @since 01/04/2008
 */
public class GetDetailCaisseStructureCmd {
    public GetDetailCaisseStructureCmd() {
    }
    
    public

    IValueObject execute(IValueObject vo) {
       
        Context context = ContextHandler.getContext();

       CaisseService caisseService = 
            (CaisseService)context.getBean("caisseService");

        return (caisseService.getDetailCaisseStructure(vo));
    } 
}
