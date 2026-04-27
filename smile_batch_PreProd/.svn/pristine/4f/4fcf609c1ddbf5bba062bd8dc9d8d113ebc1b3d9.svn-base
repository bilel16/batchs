package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.BlocageEffet;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.model.BlocageEffetVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetBolcagePourEffetTrt extends Traitement {

	Context context = ContextHandler.getContext();

	public GetBolcagePourEffetTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		ISearchEngine searchEngine = (ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");

		ICriteria criteria = searchEngine.createCriteria();
		IExpression expression = searchEngine.createExpression();
		BlocageEffetVo blocageEffetVo = (BlocageEffetVo) vo;

		try {
			criteria.add(expression.eq("numEffEff", blocageEffetVo.getBlocageEffet().getNumEffEff()));
			criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", blocageEffetVo.getBlocageEffet()
					.getContratCpt().getContratCptId().getCodStrcStrc()));
			criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd", blocageEffetVo.getBlocageEffet()
					.getContratCpt().getContratCptId().getCodPrdPrd()));
			criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", blocageEffetVo.getBlocageEffet()
					.getContratCpt().getContratCptId().getNumCcptCcpt()));
			criteria.add(expression.isNull("datFblocBloc"));
			List l = searchEngine.find(BlocageEffet.class, criteria);
			Long sommeBlocage = Long.valueOf(0);
			Long sommeDev = Long.valueOf(0);
			Long sommeEff = Long.valueOf(0);
			if (l != null && l.size() > 0) {
				blocageEffetVo.setListeBlocage(l);
				for (Iterator it = l.iterator(); it.hasNext();) {
					BlocageEffet blocageEffet = (BlocageEffet) it.next();
					sommeBlocage = sommeBlocage + blocageEffet.getMntBlocBloc();
					sommeDev += blocageEffet.getMntDevBloc();
					sommeEff += blocageEffet.getMntEffBloc();

				}
			}
			if (l.isEmpty())
				return null;
			blocageEffetVo.setSommeDevMnEffet(sommeDev);
			blocageEffetVo.setSommeMnEffet(sommeEff);
			blocageEffetVo.setSommeBlocage(sommeBlocage);
			return (blocageEffetVo);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GetBolcagePourEffetTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GetBolcagePourEffetTrt");
			logger.error("Exception : ", e);
			throw new RuntimeException(e);

		}

	}

	public void genCroText(ValueObject vo) {

	}

}
