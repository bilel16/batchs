package com.bna.smile.model.virement.traitement;

import java.util.List;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ModifierDetailVirementTrt extends Traitement {

	public ModifierDetailVirementTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;

		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		this.setCroFlag(false);

		try {

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			DetailVirement detailVirement = new DetailVirement();

			criteria.add(expression.eq("detailVirementId.numSeqDetv", virementVo.getDetailVirement()
					.getDetailVirementId().getNumSeqDetv()));
			criteria.add(expression.eq("detailVirementId.numSeqGvir", virementVo.getDetailVirement()
					.getDetailVirementId().getNumSeqGvir()));

			List l = searchEngine.find(DetailVirement.class, criteria);

			long nbre_rejet = 0;
			long montant_rejet_detail = 0;
			long montant_rejet_global = 0;

			if (l != null && l.size() > 0) {

				// / Update Detail Virement
				detailVirement = (DetailVirement) l.get(0);

				detailVirement.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
				detailVirement.setMotifRejetDetv(virementVo.getMessageValidation()); // / Motif de Rejet
				crudService.update(detailVirement);

				// / Update Global Virement

				if (detailVirement.getGlobalVirement().getNbrRejetGvir() == null)
					nbre_rejet = 0;
				else
					nbre_rejet = detailVirement.getGlobalVirement().getNbrRejetGvir().longValue();

				nbre_rejet = nbre_rejet + 1;

				if (detailVirement.getMntDetvDetv() == null)
					montant_rejet_detail = 0;
				else
					montant_rejet_detail = detailVirement.getMntDetvDetv().longValue();

				montant_rejet_global = montant_rejet_global + montant_rejet_detail;

				detailVirement.getGlobalVirement().setNbrRejetGvir(nbre_rejet);
				detailVirement.getGlobalVirement().setMntRejetGvir(montant_rejet_global);

				crudService.update(detailVirement.getGlobalVirement());

			}

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans ModifierDetailVirementTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("ModifierDetailVirementTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau ModifierDetailVirementTrt : ", e);
			virementVo.setMessageValidation("Probléme lors de ModifierDetailVirementTrt");
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