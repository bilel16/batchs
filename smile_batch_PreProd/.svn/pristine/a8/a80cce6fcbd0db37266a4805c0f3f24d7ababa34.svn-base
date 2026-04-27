package com.bna.smile.model.prelevement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.bna.smile.model.prelevement.service.PrelevementBatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * 
 * @author Sayeb Hichem
 * 
 */
public class RestorePrelevementCmd {

	public RestorePrelevementCmd() {
	}

	public IValueObject execute(IValueObject vo) {

		Context context = ContextHandler.getContext();
		PrelevementBatchService prelevementBatchService =
				(PrelevementBatchService) context.getBean("iPrelevementBatchService");
		PrelevementVo prelevementVo = (PrelevementVo) prelevementBatchService.genererFichierRejtesPrelevements(vo);
		return (prelevementVo);
	}
}
