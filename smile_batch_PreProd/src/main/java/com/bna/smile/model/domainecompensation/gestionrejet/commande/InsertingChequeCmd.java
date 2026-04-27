package com.bna.smile.model.domainecompensation.gestionrejet.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.domainecompensation.gestionrejet.service.GestionRejetService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
/**
 * 
 * @author nbdour
 *
 */
public class InsertingChequeCmd {
    public InsertingChequeCmd() {
    }

	public IValueObject execute(IValueObject vo) {

		Context context = ContextHandler.getContext();
		GestionRejetService gestionRejetService = (GestionRejetService) context.getBean("gestionRejetService");

		/**
		 * @since 11/03/2026 Refonte SNT - ACH
		 **/

		// CompensationVo compensationVo= (CompensationVo)
		// gestionRejetService.inserting(vo);
		CompensationVo compensationVo = (CompensationVo) gestionRejetService.insertingACH(vo);

		return (compensationVo);
	}
}
