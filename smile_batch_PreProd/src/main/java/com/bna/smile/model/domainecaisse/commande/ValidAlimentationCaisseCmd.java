package com.bna.smile.model.domainecaisse.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.model.ParamMvtCaisse;
import com.bna.smile.model.domainecaisse.service.CaisseService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;


public class ValidAlimentationCaisseCmd  implements ICommande{
    public ValidAlimentationCaisseCmd() {
    }
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamMvtCaisse paramMvtCaisse  = (ParamMvtCaisse)vo;
        CaisseService caisseService =  (CaisseService)context.getBean("caisseService");
        paramMvtCaisse = (ParamMvtCaisse)caisseService.validAlimInterCaisse(paramMvtCaisse);
        return paramMvtCaisse;
    }
    
}
