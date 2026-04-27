package com.bna.smile.model.domainechange.commande;

import com.bna.commun.model.TracePariteOfficielle;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainechange.service.ChangeService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * Commande de recherche du cours de change par son identifiant 
 * @author El Arbi Hassine
 * @since 15/12/2010
 */

public class InsertTracePariteOffCmd {
    public InsertTracePariteOffCmd() {
    }
    
    /**
     * 
     * @param vo  : TraceCoursChange
     * @return vO : TraceCoursChange
     */
    public

    IValueObject execute(IValueObject vo) {
        TracePariteOfficielle tracePariteOfficielle = (TracePariteOfficielle)vo;
        Context context = ContextHandler.getContext();

       ChangeService changeService = 
            (ChangeService)context.getBean("changeService");

        return (changeService.insertTracePariteOff(vo));
    }
}
