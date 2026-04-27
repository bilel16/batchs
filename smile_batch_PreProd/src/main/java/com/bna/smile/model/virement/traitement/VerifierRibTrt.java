package com.bna.smile.model.virement.traitement;

import java.math.BigInteger;
import java.util.List;

import com.bna.commun.model.AgenceBct;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class VerifierRibTrt extends Traitement {

	public VerifierRibTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;

		this.setCroFlag(false);

		boolean exist = false;
		String cleCalculer = "";
		String cle = virementVo.getStrCle();

		try {

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			AgenceBct agenceBct = new AgenceBct();

			criteria.add(expression.eq("agenceBctId.codBankBank", new Long(virementVo.getStrCodbanque())));
			criteria.add(expression.eq("agenceBctId.codAgbcAgbc", new Long(virementVo.getStrCodAgenceBanque())));

			List<AgenceBct> l = searchEngine.find(AgenceBct.class, criteria);

			// / Verifier Code Banque et Code Agence Banque
			if (l != null && l.size() > 0) {
				agenceBct = (AgenceBct) l.get(0);

				// / Verifier Cle Rib
				cleCalculer = calculerRIB(virementVo.getStrPetitRib());
				if (cle != null && cle.length() > 0) {
					if (cle.equals(cleCalculer)) {
						exist = true;
					} else {
						exist = false;
						virementVo.setMessageVerificationRib(" Clé RIB erronée !");
					}
				} else {
					/********** Cas des prelevements ***********/
					exist = true;
				}
			} else {
				exist = false;
				virementVo.setMessageVerificationRib(" Code Banque et Code Agence erroné!");

			}

			virementVo.setVerifier(exist);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans VerifierRibTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("VerifierRibTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau VerifierRibTrt : ", e);
			virementVo.setMessageValidation("Probléme lors de VerifierRibTrt");
			throw new RuntimeException();

		}
		return (virementVo);
	}

	public String calculerRIB(String RIB) {
		String cle = "";
		String resultat = "";
		if (RIB.length() == 18) {

			String RI = RIB;
			BigInteger rr = new BigInteger(RI.concat("00"));
			int rest = rr.mod(new BigInteger("97")).intValue();
			int nb = 97 - rest;
			String nbr = "" + nb;
			if (nbr.length() == 1)
				resultat = "0" + nbr;
			else
				resultat = nbr;
		}
		return resultat;
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}
}