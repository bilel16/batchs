package com.bna.smile.model.domainecommun.service;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.traitement.InsertCroBNASmsTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

public class AbonnementService extends BasicService {

	public Context context = ContextHandler.getContext();
	private InsertCroBNASmsTrt insertCroBNASmsTrt;

	public AbonnementService() {
	}

	/**
	 * cette methode permet de retourner la liste des personnes qui sont en relationa avec un client pour une qualité
	 * donnée
	 * 
	 * @param vo
	 *            ParamListQualiteClientVo
	 * @return ParamListQualiteClientVo
	 */
	public IValueObject insertCroBNASmst(IValueObject vo) {
		insertCroBNASmsTrt = new InsertCroBNASmsTrt();
		return (insertCroBNASmsTrt.exec(vo));
	}

	public void setInsertCroBNASmsTrt(InsertCroBNASmsTrt insertCroBNASmsTrt) {
		this.insertCroBNASmsTrt = insertCroBNASmsTrt;
	}

	public InsertCroBNASmsTrt getInsertCroBNASmsTrt() {
		return this.insertCroBNASmsTrt;
	}

}
