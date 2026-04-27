package com.bna.smile.model.domainecommun.traitement;

import java.text.SimpleDateFormat;
import java.util.List;

import com.bna.commun.model.TraceBatch;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.BatchVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GetListTraceBatchTrt extends Traitement {

	public GetListTraceBatchTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		this.setCroFlag(false);
		BatchVo batchVo = (BatchVo) vo;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Context context = ContextHandler.getContext();

		try {
			ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			if (batchVo.getBatchMetier() != null && batchVo.getBatchMetier().getCodBatBmet() != null) {
				criteria.add(expression.eq("batchMetier.codBatBmet", batchVo.getBatchMetier().getCodBatBmet()));
			}

			if (batchVo.getDateBatch() != null) {

				criteria.add(expression.ge("dateExecBat", batchVo.getDateBatch()));

			}

			if (batchVo.getEtatTrace() != null) {
				criteria.add(expression.eq("codeEtatBat", batchVo.getEtatTrace()));
			}

			List<TraceBatch> listTraceBatch = searchEngine.find(TraceBatch.class, criteria);

			batchVo.setListeTraceBatch(listTraceBatch);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GetListTraceBatchTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GetListTraceBatchTrt");
			batchVo.addError(erreur);
			logger.error("Exception : ", e);
			throw new RuntimeException(e);

		}
		return (batchVo);

	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

}
