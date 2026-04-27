package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.model.MandatOperation;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class ConsultEnveloppeRestanteCmd implements ICommande{
    /** Fichier: ConsultEnveloppeRestanteCmd.java
      * @version 1.0.0 du 06/11/2007
      * Copyright(c) 2007 BNA (www.bna.com.tn)
      * Classe: ConsultEnveloppeRestanteCmd
      * package: com.bna.smile.model.domainecontratcompte.procuration.commande
      * @author : Boussen Youssef 
      * Commande de consultation de l'enveloppe restante
      */
     Context context = ContextHandler.getContext();

    public ConsultEnveloppeRestanteCmd()  {
    }
    
    public IValueObject execute(IValueObject vo) {
        MandatOperation mandatOperation = (MandatOperation)vo;
        ProcurationService procurationService = (ProcurationService)context.getBean("procurationService");
        PrimitiveVO primitiveVO = (PrimitiveVO)procurationService.ConsultEnveloppeRestante(mandatOperation);
        return (primitiveVO);
    }
    }
