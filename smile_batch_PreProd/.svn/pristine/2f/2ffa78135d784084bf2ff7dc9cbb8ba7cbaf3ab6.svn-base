package com.bna.smile.model.domainechange.commande;

import com.bna.commun.model.PariteOfficielleId;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainechange.service.ChangeService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * Commande de recherche du cours de change par sonidentifiant 
 * @author El arbi hassine
 * @since 07/01/2011
 */

public class GetCoursPariteOffCmd {
    public GetCoursPariteOffCmd() {
    }
    
    /**
     * executer de la recherche du cours change
     * @param vo  : CoursChangeId
     * @return vO : CoursChange
     */
    public

    IValueObject execute(IValueObject vo) {
        PariteOfficielleId pariteOfficielleId = (PariteOfficielleId)vo;
        Context context = ContextHandler.getContext();

       ChangeService changeService = 
            (ChangeService)context.getBean("changeService");

        return (changeService.getCoursPariteOff(vo));
    }
}
