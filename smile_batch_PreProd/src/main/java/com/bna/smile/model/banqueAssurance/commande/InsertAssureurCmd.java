package com.bna.smile.model.banqueAssurance.commande;

import com.bna.commun.model.Assureur;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.service.AssuranceVieService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class InsertAssureurCmd implements ICommande{
    public InsertAssureurCmd() {
    }
    
    /**
    * methode execute
    * @param  vo Objet : Assureur
    * @return vo Objet : Assureur
    * @author Jerbi lamia 8/10/2010
    */
    public IValueObject execute(IValueObject vo) {
    Assureur assureur = (Assureur)vo;
    Context context = ContextHandler.getContext();
    AssuranceVieService assuranceVieService = 
        (AssuranceVieService)context.getBean("assuranceVieService");
    Assureur assureurRet = 
        (Assureur)assuranceVieService.insertAssureur(assureur);
    return (assureurRet);
    }
    
}
