package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetTraceMandatCmd implements ICommande{
    public GetTraceMandatCmd() {
    }
    
    /**
         * Methode execute
         * @param vo Objet : ParamMandOper
         * @return   Objet : ParamMandOper
         */
        public IValueObject execute(IValueObject vo){
           Context context = ContextHandler.getContext();
            
           ProcurationService procurationService = (ProcurationService)context.getBean("procurationService");
            ValueObject vo2 = (ValueObject)procurationService.getTraceMandat(vo);
           return(vo2);
            
        }
}
