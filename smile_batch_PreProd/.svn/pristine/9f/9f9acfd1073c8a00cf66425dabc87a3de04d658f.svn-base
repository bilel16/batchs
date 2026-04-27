package com.bna.smile.model.domainecaisse.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.service.CaisseService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * commande pour la création d'une liste de mouvement
 * @author BOUSSEN Youssef
 * @since 02/05/2011
 */

public class InsertListMouvementSessionCaisseCmd {
    public InsertListMouvementSessionCaisseCmd() {
    }
    
    /**
     * 
     * @param vo    :Listes
     * @return  vo  :Listes
     */
    public

    IValueObject execute(IValueObject vo) {
       
        Context context = ContextHandler.getContext();

        CaisseService caisseService = (CaisseService)context.getBean("caisseService");

        return (caisseService.insertListMouvementSessionCaisse(vo));
    } 

}
