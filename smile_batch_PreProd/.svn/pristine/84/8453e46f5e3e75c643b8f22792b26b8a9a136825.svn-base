package com.bna.smile.model.domainecontratcompte.procuration.commande;


import com.bna.commun.model.MandatOperation;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: DebutDernierePeriodeCmd.java
  * @version 1.0.0 du 07/08/2007
  * Copyright(c) 2007 BNA (www.bna.com.tn)
  * Classe: DebutDernierePeriodeCmd
  * package: com.bna.smile.model.domainecontratcompte.procuration.commande
  * @author : BOUSSEN Youssef & KRIAA Hatem
  * Cette commande determine la date du debut de la derniere periode par rapport à la date du jour
  */

public class DebutDernierePeriodeCmd implements ICommande{
    Context context = ContextHandler.getContext();
    
    public DebutDernierePeriodeCmd() {
    }
    public IValueObject execute(IValueObject vo) {
        MandatOperation mandatOperation = (MandatOperation)vo;
        ProcurationService procurationService = 
            (ProcurationService)context.getBean("procurationService");
        PrimitiveVO primitiveVO = (PrimitiveVO)procurationService.CreationMandat(mandatOperation);
        return (primitiveVO);
    }
}
