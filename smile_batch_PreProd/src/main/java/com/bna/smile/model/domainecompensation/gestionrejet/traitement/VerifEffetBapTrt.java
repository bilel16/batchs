package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bna.commun.model.EffetRecu;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class VerifEffetBapTrt extends Traitement {

	private static final Log LOGGER = LogFactory.getLog(VerifEffetBapTrt.class);

	public VerifEffetBapTrt() {
	}

	public IValueObject perform(IValueObject vo) throws Exception {

		PrimitiveVO primitiveVO = (PrimitiveVO) vo;
		try {
			Context context = ContextHandler.getContext();
			ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			criteria.add(expression.eq("effetId.numEff", primitiveVO.getVString()));
			criteria.add(expression.isNotNull("datBap"));

			List<EffetRecu> liste = searchEngine.find(EffetRecu.class, criteria);
			if (!liste.isEmpty())

				primitiveVO.setVBool(true);

			else {
				primitiveVO.setVBool(false);

			}

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans VerifEffetBapTrt: ");
			text.append(e.toString());
			erreur.setCode("200");
			erreur.setDescription(text.toString());
			primitiveVO.addError(erreur);
			logger.error("Exception VerifEffetBapTrt : ", e);
			throw new RuntimeException(e);

		}
		return primitiveVO;
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return Constants.CODE_RESSOURCE_GENERALE;
	}

}
