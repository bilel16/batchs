package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.ExonerationCltTva;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ParamExonerationCltTva;
import com.bna.smile.model.domainecommun.service.ExonerationTVAService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class UpdateExonerationTvaCmd implements ICommande{
    public UpdateExonerationTvaCmd() {
    }
    /**
     * methode execute
     * @param  vo Objet : ParamExonerationCltTva
     * @return vo Objet : ParamExonerationCltTva
     * @author lamia
     */
    public IValueObject execute(IValueObject iValueObject) {
        ParamExonerationCltTva paramExonerationCltTva = (ParamExonerationCltTva)iValueObject;
        Context context = ContextHandler.getContext();
        ExonerationTVAService exonerationTVAService = 
            (ExonerationTVAService)context.getBean("exonerationTVAService");
        paramExonerationCltTva = (ParamExonerationCltTva)
         exonerationTVAService.updateExonerationTva(paramExonerationCltTva);  
        return paramExonerationCltTva;
    }
}
