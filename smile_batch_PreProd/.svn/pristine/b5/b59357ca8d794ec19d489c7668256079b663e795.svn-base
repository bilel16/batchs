package com.bna.smile.model.domainechange.traitement;

import com.bna.commun.model.TraceCoursChange;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertTraceCoursChangeTrt extends Traitement {
    Context context = ContextHandler.getContext();

    public InsertTraceCoursChangeTrt() {
    }
    
   
   public  IValueObject perform(IValueObject vo) throws Exception {
      
        try {
            TraceCoursChange traceCoursChange = (TraceCoursChange)vo;

            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
            crudService.create(traceCoursChange);  
            
            return traceCoursChange;
        } catch (Exception e) {
        e.printStackTrace();
        return null;
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
