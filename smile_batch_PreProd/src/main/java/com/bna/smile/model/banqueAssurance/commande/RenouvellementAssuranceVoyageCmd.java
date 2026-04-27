package com.bna.smile.model.banqueAssurance.commande;

import com.bna.commun.model.ContratAssuranceVoyage;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.model.ParamAssuranceVoyage;
import com.bna.smile.model.banqueAssurance.service.AssuranceVoyageService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class RenouvellementAssuranceVoyageCmd implements ICommande {
    public RenouvellementAssuranceVoyageCmd() {
    }
    /**
    * methode execute
    * @param  vo : paramAssurance
    * @return vo : paramAssurance
    * @author GHRAIRI Nesrine
    */
    public IValueObject execute(IValueObject vo) {
    	ParamAssuranceVoyage paramAssuranceVoyage = (ParamAssuranceVoyage)vo;
    Context context = ContextHandler.getContext();
    AssuranceVoyageService assuranceVoyageService = (AssuranceVoyageService)context.getBean("assuranceVoyageService");
    paramAssuranceVoyage = (ParamAssuranceVoyage)assuranceVoyageService.renouvelerAssuranceVoyage(paramAssuranceVoyage);
    return (paramAssuranceVoyage);
    }

}
