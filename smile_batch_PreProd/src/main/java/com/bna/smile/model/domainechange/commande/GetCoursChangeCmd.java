package com.bna.smile.model.domainechange.commande;

import com.bna.commun.model.CoursChangeId;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainechange.service.ChangeService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * Commande de recherche du cours de change par sonidentifiant 
 * @author Mdimagh Med lassaad
 * @since 02/10/07
 */

public class GetCoursChangeCmd {
    public GetCoursChangeCmd() {
    }
    
    /**
     * executer de la recherche du cours change
     * @param vo  : CoursChangeId
     * @return vO : CoursChange
     */
    public

    IValueObject execute(IValueObject vo) {
        CoursChangeId coursChangeId = (CoursChangeId)vo;
        Context context = ContextHandler.getContext();

       ChangeService changeService = 
            (ChangeService)context.getBean("changeService");

        return (changeService.getCoursChange(vo));
    }
}
