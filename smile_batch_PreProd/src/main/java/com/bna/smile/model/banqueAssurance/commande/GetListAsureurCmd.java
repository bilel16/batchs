package com.bna.smile.model.banqueAssurance.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.service.AssuranceVieService;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetListAsureurCmd implements ICommande{
    public GetListAsureurCmd() {
    }
    /**
    * methode execute
    * @param  vo Objet : Listes
    * @return vo Objet : Listes des assureur
    * @author lamia
    */
    public IValueObject execute(IValueObject vo) {
    Listes listes = (Listes)vo;
    Context context = ContextHandler.getContext();
    AssuranceVieService assuranceVieService = 
        (AssuranceVieService)context.getBean("assuranceVieService");
    Listes listeAssureurs = 
        (Listes)assuranceVieService.getListAssureur(listes);
    return (listeAssureurs);
    }
    
}
