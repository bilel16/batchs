package com.bna.smile.model.traitementCompensationRecu.traitement;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bna.commun.model.Cheque;
import com.bna.commun.model.Cnp;
import com.bna.commun.model.CnpId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.traitementCompensationRecu.dao.RejetDAO;
import com.bna.smile.model.traitementCompensationRecu.model.CompensationRecuVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class CreateCnpTrt extends Traitement {

	/**
	 * 
	 */
	public Context context = ContextHandler.getContext();
	/**
	 * 
	 */
	private static final Log LOGGER = LogFactory.getLog(CreateCnpTrt.class);

	@Override
	protected void genCroText(ValueObject arg0) {

	}

	@Override
	protected IValueObject perform(IValueObject vo) throws Exception {
		LOGGER.debug("Beginning creating CNP : in function CreateCnpTrt.perform()... ");
		CompensationRecuVo compVo = (CompensationRecuVo) vo;
		Cheque cheque = compVo.getSelectedCheque();
		cheque.setCodEtatChq("CNP");

		Long numChqChq = cheque.getChequeId().getNumChqChq();
		String ribTirChq = cheque.getChequeId().getRibTirChq();
		//Date datOpeChq = cheque.getChequeId().getDatOpeChq();
		String ribBenChq = cheque.getChequeId().getRibBenChq();
		// Create CNP ID
		// Long ranChqCnp;: // TODO : To delete , attribute is not necessary
		Date datCnpCnp = compVo.getDateComptable();
		RejetDAO rejetDao = (RejetDAO) context.getBean("rejetDAO");
		Long numCnpCnp = rejetDao.getSequenceNumCnp();
		Long nbrEnrCom = null;
		String sigCheAge = null;
		Long refClePub = null;
		String refFic = cheque.getRefFicChq();
		String codMotRej = cheque.getCodMrejChq();
		Long ranChqCnp = null;
		CnpId cnpId = new CnpId(numChqChq, ribTirChq, ribBenChq);
		Cnp cnp = new Cnp(cheque, ranChqCnp, datCnpCnp, numCnpCnp, nbrEnrCom,
				sigCheAge, refClePub, refFic, codMotRej);

		// Create CNP
		try {
			CRUDservice crudService = (CRUDservice) context
					.getBean("crudservice");
			crudService.create(cnp);
			LOGGER.debug("Ending Creating Cheque ...");
			return (cheque);
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Error in CreateCnpTrt : ");
			text.append(e.toString());
			erreur.setCode("200");
			erreur.setDescription(text.toString());
			erreur.setKey("CreateCnpTrt");
			cheque.addError(erreur);
			throw new RuntimeException();

		}

	}
}
