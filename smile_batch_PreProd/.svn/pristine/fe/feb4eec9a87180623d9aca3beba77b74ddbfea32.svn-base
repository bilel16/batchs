package com.bna.smile.model.domainechange.commande;

import com.bna.commun.model.PariteOfficielle;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainechange.service.ChangeService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * Commande de recherche du cours de change par sonidentifiant 
 * @author EL arbi hassine
 * @since 12/2010
 */

public class UpdatePariteOffCmd {
    public UpdatePariteOffCmd() {
    }
    
    /**
     * executer de la recherche du cours change
     * @param vo  : PariteOfficielle
     * @return vO : PariteOfficielle
     */
    public

    IValueObject execute(IValueObject vo) {
        PariteOfficielle pariteOfficielle = (PariteOfficielle)vo;
        Context context = ContextHandler.getContext();

       ChangeService changeService = 
            (ChangeService)context.getBean("changeService");

        return (changeService.updatePariteOfficielle(vo));
    }
}
