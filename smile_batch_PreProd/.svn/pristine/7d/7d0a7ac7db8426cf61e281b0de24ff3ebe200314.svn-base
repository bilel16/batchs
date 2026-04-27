package com.bna.smile.model.domainechange.traitement;

import com.bna.commun.model.TracePariteOfficielle;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertTracePariteOffTrt extends Traitement {
    Context context = ContextHandler.getContext();

    public InsertTracePariteOffTrt() {
    }
    
   
   public  IValueObject perform(IValueObject vo) throws Exception {
      
        try {
            TracePariteOfficielle tracePariteOfficielle = (TracePariteOfficielle)vo;

            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
            crudService.create(tracePariteOfficielle);  
            
            return tracePariteOfficielle;
        } catch (Exception e) {
        e.printStackTrace();
        return null;
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
