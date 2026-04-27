package com.bna.smile.model.prelevement.traitement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.bna.commun.model.MvtPrelevements;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GetListeRejetsPrelevementsByStructureTrt extends Traitement {

	public GetListeRejetsPrelevementsByStructureTrt() {
		super();
	}

	@Override
	public IValueObject perform(IValueObject vo) {

		PrelevementVo prelevementVo = (PrelevementVo) vo;

		try {

			ISearchEngine searchEngine =
					(SearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			criteria.add(expression.eq("structure.codStrcStrc", prelevementVo.getCodeStructure()));
			criteria.add(expression.eq("datOperMprl", prelevementVo.getDateComptable()));
			criteria.add(expression.eq("operation.codOperOper", Constants.COD_OPER_REJET_PRELEVEMENT));
			criteria.add(expression.isNull("boolReafPrl"));

			Set<MvtPrelevements> listePrelevements =
					new HashSet<MvtPrelevements>(searchEngine.find(MvtPrelevements.class, criteria));
			prelevementVo.setListeMvtsPrelevements(new ArrayList<MvtPrelevements>(listePrelevements));

			return (prelevementVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GetListeRejetsPrelevementsByStructureTrt : ");
			text.append(e.toString());
			erreur.setCode("500");
			erreur.setDescription(text.toString());
			erreur.setKey("GetListeRejetsPrelevementsByStructureTrt");
			prelevementVo.addError(erreur);
			return (prelevementVo);
		}

	}

	@Override
	protected void genCroText(ValueObject valueobject) {
		// TODO Auto-generated method stub

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

}
