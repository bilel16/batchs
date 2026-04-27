package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
/**
 * Commande permet de recuperer l'objet Devise
 * @author Mdimagh Med
 * @since 17/10/07
 */
public class GetDeviseCmd implements ICommande{
    public GetDeviseCmd() {
    }
    
    /**
     * executer la recherce de l'objet Groupe
     * @param vo  :Devise
     * @return vO :Devise
     */
    public IValueObject execute(IValueObject vo) {
       
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
       
        return (nomenclatureService.getDevise(vo) );
    }   
    
}
