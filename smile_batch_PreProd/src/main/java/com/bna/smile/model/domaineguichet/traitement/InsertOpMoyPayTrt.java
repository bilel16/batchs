package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.constant.Constants;
import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.model.OperationEpargnes;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.PersonneDAO;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class InsertOpMoyPayTrt extends Traitement {

	public Context context = ContextHandler.getContext();

	public InsertOpMoyPayTrt() {
	}

	public IValueObject perform(IValueObject vo) {
		OperationMoyPay operationMoyPay = (OperationMoyPay) vo;

		this.setCroFlag(false);

		try {
			// /*** insertion dans la table Operation_Moy_Pay
			InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt();
			insertOperationMoyPayTrt.setVerifDomaine(false);
			operationMoyPay = (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPay);
			return operationMoyPay;
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJ NSI InsertOpMoyPayTrt : ");
			text.append(e.toString());
			erreur.setCode("300");
			erreur.setDescription(text.toString());
			erreur.setKey("InsertOpMoyPayTrt");
			operationMoyPay.addError(erreur);
			throw new RuntimeException();

		}

	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}
}
