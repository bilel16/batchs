package com.bna.smile.model.domainecompensation.gestionrejet.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.EditionRejetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.RemiseEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.service.GestionRejetService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
/**
 * 
 * @author nbdour
 *
 */
public class RestoreChequeCmd {
    public RestoreChequeCmd() {
    }
    public IValueObject execute(IValueObject vo) {
       
        Context context = ContextHandler.getContext();
        GestionRejetService gestionRejetService = (GestionRejetService)context.getBean("gestionRejetService");
        EditionRejetVo editionRejetVo= (EditionRejetVo) gestionRejetService.restoreCheque(vo);
        return(editionRejetVo);
    }
}
