package com.bna.smile.model.domainecommun.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.ContratCompteService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet ContratCpt
 * @author Mdimagh Med
 * @since 16/08/07
 */
public class GetContratCptByIdCmd implements ICommande {
    public GetContratCptByIdCmd() {
    }
    
    /**
     * executer la recherce de l'objet ContratCpt
     * @param vo  : ContratCpt
     * @return vO : ContratCpt
     */
    public IValueObject execute(IValueObject vo) {
        
        Context context = ContextHandler.getContext();

        ContratCompteService contratCompteService = 
            (ContratCompteService)context.getBean("contratCompteService");
        return (contratCompteService.GetContratCptById(vo));
    }
}
