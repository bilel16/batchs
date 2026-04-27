package com.bna.smile.model.traitementCompensationRecu.traitement;

import java.util.List;

import com.bna.commun.model.ComplementPapillon;
import com.bna.commun.model.Signataire;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class ListSignataireTrt extends Traitement {

	public Context context = ContextHandler.getContext();

	@Override
	protected void genCroText(ValueObject paramValueObject) {

	}

	@Override
	protected IValueObject perform(IValueObject vo) throws Exception {
		ComplementPapillon compPap = (ComplementPapillon) vo;
		Listes returnValue = new Listes();

		try {

			ISearchEngine searchEngine = (SearchEngine) context
					.getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			criteria.add(expression.eq("numChqChq", compPap.getPapillon()
					.getPapillonId().getNumChqChq()));
//			criteria.add(expression.eq("datOpeChq", compPap.getPapillon()
//					.getPapillonId().getDatOpeChq()));
			criteria.add(expression.eq("ribBenChq", compPap.getPapillon()
					.getPapillonId().getRibBenChq()));
			criteria.add(expression.eq("ribTirChq", compPap.getPapillon()
					.getPapillonId().getRibTirChq()));
			List<Signataire> signataires = searchEngine.find(Signataire.class,
					criteria);
			returnValue.setList(signataires);
			return (returnValue);
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer(
					"Erreur dans ListSignataireTrt : ");
			text.append(e.toString());
			erreur.setCode("500");
			erreur.setDescription(text.toString());
			erreur.setKey("ListSignataireTrt");
			returnValue.addError(erreur);
			throw new RuntimeException("Error in List Signataire treatement ");
		}
	}
}
