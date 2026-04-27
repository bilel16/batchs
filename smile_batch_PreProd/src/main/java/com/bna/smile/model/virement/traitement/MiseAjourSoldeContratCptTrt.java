package com.bna.smile.model.virement.traitement;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class MiseAjourSoldeContratCptTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	GlobalVirement globalVirement = new GlobalVirement();

	public MiseAjourSoldeContratCptTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		boolean executer = false; // / false : Contrat NOn MAJ ; True Contrat MAJ
		String msg = "";
		this.setCroFlag(false);

		try {

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");

			ContratCpt contratCpt =
					(ContratCpt) searchEngine.loadForUpdate(ContratCpt.class, virementVo.getContratCpt()
							.getContratCptId());
			long montant_solde = 0;

			if (contratCpt != null && contratCpt.getContratCptId() != null) {

				montant_solde = contratCpt.getMontSoldCcpt().longValue();
				logger.info("(virementVo.getMontantMiseAjourSolde()) : " + (virementVo.getMontantMiseAjourSolde()));
				logger.info("virementVo.getCodeSens() : " + (virementVo.getCodeSens()));
				if (virementVo.getStrRib().equals("DO")) {

					if (virementVo.getCodeSens().equals("DB")) {

						contratCpt.setMontSoldCcpt(montant_solde - (virementVo.getMontantMiseAjourSolde()));
					} else if (virementVo.getCodeSens().equals("CR")) {

						contratCpt.setMontSoldCcpt(montant_solde + (virementVo.getMontantMiseAjourSolde()));
					}
				} else if (virementVo.getStrRib().equals("BENIF")) {
					if (virementVo.getCodeSens().equals("DB")) {

						contratCpt.setMontSoldCcpt(montant_solde - (virementVo.getMontantMiseAjourSolde()));
					} else if (virementVo.getCodeSens().equals("CR")) {

						contratCpt.setMontSoldCcpt(montant_solde + (virementVo.getMontantMiseAjourSolde()));
					}
				} else {

				}

				crudService.update(contratCpt);
				executer = true;

			} else {
				executer = false;
				msg = " Contrat Cpt Inexistant";
			}
			virementVo.setBoolValiderContratCpt(executer);
			// / false : Contrat NOn MAJ ; True Contrat MAJ
			virementVo.setMessageValidation(msg);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MiseAjourSoldeContratCptTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MiseAjourSoldeContratCptTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau MiseAjourSoldeContratCptTrt : ", e);
			virementVo.setMessageValidation("Probléme lors de MiseAjourSoldeContratCptTrt");
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