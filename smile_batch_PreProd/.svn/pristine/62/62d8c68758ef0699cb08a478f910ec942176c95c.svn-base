package com.bna.smile.model.reporting.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.reporting.model.ParamMoyPayVo;
import com.bna.smile.model.reporting.service.ExtraitService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetListOperMoyPayExtraitCmd implements ICommande{
    public GetListOperMoyPayExtraitCmd() {
    }
    
    public IValueObject execute (IValueObject vo){
        ParamMoyPayVo paramMoyPayVo = (ParamMoyPayVo)vo;
        Context context = ContextHandler.getContext();
        ExtraitService extraitService = 
            (ExtraitService)context.getBean("extraitService");
        Listes listeOperationMoyPayRetour = (Listes)extraitService.getListOperationExtrait(paramMoyPayVo);
        return(listeOperationMoyPayRetour);
    }
}
