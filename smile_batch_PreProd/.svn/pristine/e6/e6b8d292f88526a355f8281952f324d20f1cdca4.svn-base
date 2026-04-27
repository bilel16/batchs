package com.bna.smile.model.virement.traitement;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.SeqAgence;
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
import com.oxia.fwk.searchengine.SearchEngine;

public class GetNumLotAgenceTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();

	public GetNumLotAgenceTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		SeqAgence seqAgence = new SeqAgence();
		long numLot = 0;
		try {
			Long codStrcStrc = virementVo.getStructure().getCodStrcStrc();

			ICriteria criteria = searchEngine.createCriteria();

			criteria.add(expression.eq("seqAgenceId.codStrcStrc", codStrcStrc));
			criteria.add(expression.eq("seqAgenceId.libSeqSeqa", Constants.LIB_SEQUENCE_LOT_AGENCE));

			List<SeqAgence> l = new ArrayList<SeqAgence>(searchEngine.find(SeqAgence.class, criteria));
			if (l != null && l.size() > 0) {
				seqAgence = l.get(0);
				numLot = seqAgence.getNumValSeqa().longValue();
				virementVo.setNumLot(numLot);

				// /********* Incrementation ********//

				numLot++;
				seqAgence.setNumValSeqa(numLot);

				crudService.update(seqAgence);

			} else {
				virementVo.setNumLot(1);
			}

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GetNumLotAgenceTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GetNumLotAgenceTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau GetNumLotAgenceTrt : ", e);
			virementVo.setMessageValidation("Probléme dans GetNumLotAgenceTrt");
			throw new RuntimeException();

		}
		return (virementVo);

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {

	}

}