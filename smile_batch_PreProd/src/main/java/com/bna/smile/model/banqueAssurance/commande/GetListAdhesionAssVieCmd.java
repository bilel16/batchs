package com.bna.smile.model.banqueAssurance.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.service.AssuranceVieService;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetListAdhesionAssVieCmd implements ICommande {
    public GetListAdhesionAssVieCmd() {
    }
        /**
     * methode execute
     * @param  vo Objet : ParamRechercheOpposition
     * @return vo Objet : Listes des adhesion assurance vie
     * @author lamia
     */
    public IValueObject execute(IValueObject vo) {
        ParamRechercheOpposition paramRecherche = (ParamRechercheOpposition)vo;
        Context context = ContextHandler.getContext();
        AssuranceVieService assuranceVieService = 
            (AssuranceVieService)context.getBean("assuranceVieService");
        Listes listeAssuranceVie = 
            (Listes)assuranceVieService.getListAdhesionAssVie(paramRecherche);
        return (listeAssuranceVie);
    }
   

}
