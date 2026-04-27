package com.bna.smile.model.virement.traitement;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class VerfierContratCptTrt extends Traitement {

	public VerfierContratCptTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;

		boolean exist = false; // / false : Contrat Invalde ; True Contrat valide
		String msg = "";
		this.setCroFlag(false);

		try {
			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			criteria.add(expression.eq("contratCptId.codStrcStrc", virementVo.getContratCpt().getContratCptId()
					.getCodStrcStrc()));

			criteria.add(expression.eq("contratCptId.codPrdPrd", virementVo.getContratCpt().getContratCptId()
					.getCodPrdPrd()));
			criteria.add(expression.eq("contratCptId.numCcptCcpt", virementVo.getContratCpt().getContratCptId()
					.getNumCcptCcpt()));

			List<ContratCpt> l = new ArrayList<ContratCpt>(searchEngine.find(ContratCpt.class, criteria));
			ContratCpt contratCpt = new ContratCpt();
			if (l != null && l.size() > 0) {
				contratCpt = (ContratCpt) l.get(0);

				if (virementVo.getStrRib() != null && virementVo.getStrRib().equals("BENIF")) {

					if (contratCpt.get_codEtatCcpt().equals("V") || contratCpt.get_codEtatCcpt().equals("B")
							|| contratCpt.get_codEtatCcpt().equals("T") || contratCpt.get_codEtatCcpt().equals("S")) {
						exist = true;
					} else {
						exist = false;
						msg = " Contrat Cpt Invalide";
					}
				} else {
					if (virementVo.getGlobalVirement().getBoolSuccGvir() != null
							&& virementVo.getGlobalVirement().getBoolSuccGvir().longValue() == 1) {

						if (!(contratCpt.get_codEtatCcpt().equals("B") || contratCpt.get_codEtatCcpt().equals("S"))) {
							exist = false;
							msg = " Contrat Cpt Invalide pour succession";
						} else {
							exist = true;

						}
					} else {

						if (!contratCpt.get_codEtatCcpt().equals("V")) {
							exist = false;
							msg = " Contrat Cpt Invalide";
						} else {
							exist = true;

						}
					}
				}
			} else {
				exist = false;
				msg = " Contrat Cpt Inexistant";
			}

			virementVo.setBoolValiderContratCpt(exist);
			virementVo.setMessageValidation(msg);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans VerfierContratCptTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("VerfierContratCptTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau VerfierContratCptTrt : ", e);
			virementVo.setMessageValidation("Probléme lors de VerfierContratCptTrt");
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