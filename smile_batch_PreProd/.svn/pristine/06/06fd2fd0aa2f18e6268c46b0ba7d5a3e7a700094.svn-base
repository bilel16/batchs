package com.bna.smile.model.domainecompensation.gestionrejet.commande;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.service.GestionRejetService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
/**
 * 
 * @author nbdour
 *
 */
public class InsertingCroFromOmpCmd {
    public InsertingCroFromOmpCmd() {
    }
    public IValueObject execute(IValueObject vo) {
       
        Context context = ContextHandler.getContext();
        GestionRejetService gestionRejetService = (GestionRejetService)context.getBean("gestionRejetService");
      System.out.println(gestionRejetService);
        OperationMoyPay compensationVo= (OperationMoyPay) gestionRejetService.insertingCroFromOmp(vo);
        return(compensationVo);
    }
}
