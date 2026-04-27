package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.TraceBatch;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class CreationTraceBatchTrt extends Traitement {

	public CreationTraceBatchTrt() {
	}

	public IValueObject perform(IValueObject vo) {
		TraceBatch traceBatch = (TraceBatch) vo;
		Context context = ContextHandler.getContext();

		try {

			this.setCroFlag(false);

			CRUDservice crudservice = (CRUDservice) context.getBean("crudservice");
			VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
			ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

			if (traceBatch != null && traceBatch.getNumSeqTrc() != null) {

				TraceBatch traceBatchNew = (TraceBatch) searchEngine.get(TraceBatch.class, traceBatch.getNumSeqTrc());
				traceBatchNew.setCodeEtatBat(traceBatch.getCodeEtatBat());
				traceBatchNew.setTimeFinBat(traceBatch.getTimeFinBat());
				crudservice.update(traceBatchNew);

			} else {

				Long numTrace = virementGlobalDAO.getSequenceTraceBatch();
				traceBatch.setNumSeqTrc(numTrace);
				crudservice.create(traceBatch);
			}

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationTraceBatchTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationTraceBatchTrt");
			traceBatch.addError(erreur);
			logger.error("Exception : ", e);
			throw new RuntimeException(e);

		}
		return (traceBatch);
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

}
