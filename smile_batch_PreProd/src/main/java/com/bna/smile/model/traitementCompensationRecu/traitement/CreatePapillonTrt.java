package com.bna.smile.model.traitementCompensationRecu.traitement;

import java.util.Date;

import com.bna.commun.model.Cheque;
import com.bna.commun.model.Papillon;
import com.bna.commun.model.PapillonId;
import com.bna.commun.model.Valeur;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.traitementCompensationRecu.dao.RejetDAO;
import com.bna.smile.model.traitementCompensationRecu.model.CompensationRecuVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class CreatePapillonTrt extends Traitement {

	/**
	 * 
	 */
	public Context context = ContextHandler.getContext();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bna.commun.traitements.Traitement#genCroText(com.oxia.fwk.core.
	 * ValueObject)
	 */
	@Override
	protected void genCroText(ValueObject paramValueObject) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.oxia.fwk.beans.traitement.AbstractTraitement#perform(com.oxia.fwk
	 * .core.IValueObject)
	 */
	@Override
	protected IValueObject perform(IValueObject vo) throws Exception {
		RejetDAO rejetDao = (RejetDAO) context.getBean("rejetDAO");
		CompensationRecuVo compVo = (CompensationRecuVo) vo;
		Cheque cheque = compVo.getSelectedCheque();
		// Create PapillonID
		Long numPapPap = rejetDao.getSequenceNumPapillon();
		long numChqChq = cheque.getChequeId().getNumChqChq();
		String ribTirChq = cheque.getChequeId().getRibTirChq();
		Date datOpeChq = cheque.getDatOpeChq();
		String ribBenChq = cheque.getChequeId().getRibBenChq();
		PapillonId papillonId = new PapillonId( numChqChq, ribBenChq,
				ribTirChq, numPapPap);
		Date datPapPap = compVo.getDateComptable();
		Long ranPapPap = null; // TODO : to delete this attribute . not necessary
		Long nbrEnrPap =Long.valueOf(0); // for test  
		String codMrejPap = cheque.getCodMrejChq();
		String refFicPap = cheque.getRefFicChq();
		Long valeurCodValVal = cheque.getValeur().getCodValVal();
		// Create Papillon
		Papillon papillon = new Papillon(papillonId, cheque, valeurCodValVal,
				datPapPap, ranPapPap, nbrEnrPap, codMrejPap, refFicPap);
		try {
			CRUDservice crudService = (CRUDservice) context
					.getBean("crudservice");
			crudService.create(papillon);
			return (papillon);
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer(
					"Error in CreatePapillon : ");
			text.append(e.toString());
			erreur.setCode("200");
			erreur.setDescription(text.toString());
			erreur.setKey("CreatePapillon");
			papillon.addError(erreur);
			throw new RuntimeException("Error in create Papillon treatement");

		}

	}

}
