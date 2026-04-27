package com.bna.smile.model.domainechange.traitement;

import com.bna.commun.model.CoursChange;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertCoursChangeTrt extends Traitement {
    Context context = ContextHandler.getContext();

    public InsertCoursChangeTrt() {
    }
    
   
   public  IValueObject perform(IValueObject vo) throws Exception {
      
        try {
            CoursChange coursChange = (CoursChange)vo;

            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
            crudService.create(coursChange);  
            
            return coursChange;
        } catch (Exception e) {
        e.printStackTrace();
        return null;
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
