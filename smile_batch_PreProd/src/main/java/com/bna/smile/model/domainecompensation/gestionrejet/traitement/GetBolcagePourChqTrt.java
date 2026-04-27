package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.BlocageCheque;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.model.BlocageChqVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetBolcagePourChqTrt extends Traitement {

	Context context = ContextHandler.getContext();

	public GetBolcagePourChqTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		ISearchEngine searchEngine = (ISearchEngine) context.getBean("searchEngine");

		ICriteria criteria = searchEngine.createCriteria();
		IExpression expression = searchEngine.createExpression();
		BlocageChqVo blocageChqVo = (BlocageChqVo) vo;
		ContratCpt contratCptTireur = blocageChqVo.getBlocageCheque().getContratCpt();
		logger.info("Get somme Bloc Cheque Trt..");
		List<BlocageCheque> l = new ArrayList<BlocageCheque>();
		try {
			if (contratCptTireur != null && contratCptTireur.getContratCptId() != null) {
				criteria.add(expression.eq("numChqChq", blocageChqVo.getBlocageCheque().getNumChqChq()));
				criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt",
						contratCptTireur.getContratCptId().getNumCcptCcpt()));
				criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd",
						contratCptTireur.getContratCptId().getCodPrdPrd()));
				criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc",
						contratCptTireur.getContratCptId().getCodStrcStrc()));
				criteria.add(expression.isNull("datFblocBloc"));
				if (blocageChqVo.getTypeBlocage() != null) {
					criteria.add(expression.eq("typeBlocBloc", blocageChqVo.getTypeBlocage()));
				}

				l = searchEngine.find(BlocageCheque.class, criteria);
			}
			logger.info("Liste Bloc Cheque :" + l.size());
			Long sommeBlocage = Long.valueOf(0);
			if (l != null && l.size() > 0) {
				for (Iterator it = l.iterator(); it.hasNext();) {
					BlocageCheque blocageCheque = (BlocageCheque) it.next();
					sommeBlocage = sommeBlocage + blocageCheque.getMntBlocBloc();
				}
			}
			// blocageChqVo.setListBlocageCheque(l);
			blocageChqVo.setSommeBlocage(sommeBlocage);
			return (blocageChqVo);

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GetBlocagePourChqTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GetBlocagePourChqTrt");
			logger.error("Exception : ", e);
			throw new RuntimeException(e);

		}

	}

	public void genCroText(ValueObject vo) {

	}

}
