package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.CoursChange;
import com.bna.commun.model.CoursChangeId;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.domainecommun.service.OperationService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetCoursDevCmd implements ICommande{
    public GetCoursDevCmd() {
    }
    
    /**
     * executer la recherce de l'objet Activite
     * @param vo  :CoursChangeId
     * @return vO :CoursChange
     */
    public

    IValueObject execute(IValueObject vo) {
        CoursChangeId coursChangeId = (CoursChangeId)vo;
        Context context = ContextHandler.getContext();

        OperationService operationService = (OperationService)context.getBean("operationService");
        CoursChange coursChange = (CoursChange)operationService.GetCoursDev(coursChangeId);
        return (coursChange);
    }

}
