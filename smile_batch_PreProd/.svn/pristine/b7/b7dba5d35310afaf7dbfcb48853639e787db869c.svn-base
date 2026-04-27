package com.bna.smile.model.virement.traitement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.bna.commun.model.FluxComptVirement;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetListFluxComptableVirementByNumSeqGvirTrt extends Traitement {

	public GetListFluxComptableVirementByNumSeqGvirTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;

		this.setCroFlag(false);

		try {

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			criteria.add(expression.eq("numSeqGvir", virementVo.getGlobalVirement().getNumSeqGvir()));

			Set<FluxComptVirement> listFluxComptVirement =
					new HashSet<FluxComptVirement>(searchEngine.find(FluxComptVirement.class, criteria));

			virementVo.setListFluxComptVirement(new ArrayList<FluxComptVirement>(listFluxComptVirement));

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GetListFluxComptableVirementByNumSeqGvirTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GetListDetailVirementByNumSeqGvirTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau GetListFluxComptableVirementByNumSeqGvirTrt : ", e);
			virementVo.setMessageValidation("Probléme lors GetListFluxComptableVirementByNumSeqGvirTrt");

			throw new RuntimeException();

		}
		return (virementVo);

	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

}