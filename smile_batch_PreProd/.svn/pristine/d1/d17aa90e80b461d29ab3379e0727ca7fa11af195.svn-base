package com.bna.smile.model.domainecommun.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.ProduitService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet regle de gestion
 * @author Mdimagh Med lassaad
 * @since 25/09/07
 */
public class GetRegleGestionContratCmd  implements ICommande {
    public GetRegleGestionContratCmd() {
    }
    
    /**
     * executer la recherce de l'objet GetRegleGestionContrat
     * @param vo  :RegleGestionContratId
     * @return vO :RegleGestionContrat
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ProduitService produitService = (ProduitService)context.getBean("produitService");
        return (produitService.getRegleGestionContrat(vo));
    }
}
