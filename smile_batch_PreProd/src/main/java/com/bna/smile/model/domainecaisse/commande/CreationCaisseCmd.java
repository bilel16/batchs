package com.bna.smile.model.domainecaisse.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.service.CaisseService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/**
 * commande pour la création d'une caisse 
 * @author Mdimagh Med Lassaad
 * @since 17/12/2007
 */
public class CreationCaisseCmd implements ICommande{
    public CreationCaisseCmd() {
    }
    
    /**
     * 
     * @param vo :AffectationCaisseStructure
     * @return  vo :AffectationCaisseStructure
     */
    public

    IValueObject execute(IValueObject vo) {
       
        Context context = ContextHandler.getContext();

       CaisseService caisseService = 
            (CaisseService)context.getBean("caisseService");

        return (caisseService.creationCaisse(vo));
    } 
    
}
