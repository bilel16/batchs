package com.bna.smile.model.domainecaisse.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.service.CaisseService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * commande pour la création d'une caisse de vacation
 * @author BOUSSEN Youssef
 * @since 19/04/2011
 */

public class CreationCaisseVacationCmd {
    public CreationCaisseVacationCmd() {
    }
    
    /**
     * 
     * @param vo    :SessionJrnCaissePrVac
     * @return  vo  :SessionJrnCaissePrVac
     */
    public

    IValueObject execute(IValueObject vo) {
       
        Context context = ContextHandler.getContext();

        CaisseService caisseService = (CaisseService)context.getBean("caisseService");

        return (caisseService.creationCaisseVacation(vo));
    } 


}
