package com.bna.smile.model.domainecompensation.gestionrejet.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.RemiseEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.service.GestionRejetService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
/**
 * 
 * @author nbdour
 *
 */
public class RestoreEffetCmd {
    public RestoreEffetCmd() {
    }
    public IValueObject execute(IValueObject vo) {
       
        Context context = ContextHandler.getContext();
        GestionRejetService gestionRejetService = (GestionRejetService)context.getBean("gestionRejetService");
        RemiseEffetVo remiseEffetVo= (RemiseEffetVo) gestionRejetService.restoreEffet(vo);
        return(remiseEffetVo);
    }
}
