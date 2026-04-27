package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamModifMandVo;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: ValidModifMandCmd.java
 * @version 1.0.0 du 28/03/2008
 * Copyright(c) 2008 BNA (www.bna.com.tn)
 * Classe: ValidModifMandCmd
 * package: com.bna.smile.model.domainecontratcompte.procuration.commande
 * @author : BOUSSEN Youssef 
 */
 public class ValidModifMandCmd implements ICommande{
    public ValidModifMandCmd() {
    }
    /**
         * Methode execute
         * @param vo Objet : ParamModifMandVo
         * @return   Objet : ParamModifMandVo
         */
    public IValueObject execute(IValueObject vo) {
        ParamModifMandVo paramModifMandVo = (ParamModifMandVo)vo;
        Context context = ContextHandler.getContext();

        ProcurationService procurationService = 
            (ProcurationService)context.getBean("procurationService");
        ParamModifMandVo paramModifMandVoRetour = (ParamModifMandVo)procurationService.validModifMand(paramModifMandVo);
        return (paramModifMandVoRetour);

    }
}
