package com.bna.smile.model.virement.traitement;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.SeqAgenceId;
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

public class GetNumVirSGMTTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();

	public GetNumVirSGMTTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		SeqAgence seqAgence = new SeqAgence();
		long numVirSgmt= 0;
		try {
			Long codStrcStrc = Constants.COD_DIR_TRESORERIE;
			SeqAgenceId seqAgenceId = new SeqAgenceId();
			seqAgenceId.setCodStrcStrc(codStrcStrc);
			seqAgenceId.setLibSeqSeqa(Constants.LIB_SEQUENCE_VIR_SGMT);

			HibernateTemplate hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
			// hibernateTemplate.flush();
			hibernateTemplate.evict(seqAgence);

			SeqAgence seqAgenceNew = (SeqAgence) searchEngine.loadForUpdate(SeqAgence.class, seqAgenceId);

			if (seqAgenceNew != null && seqAgenceNew.getNumValSeqa() != null) {

				numVirSgmt = seqAgenceNew.getNumValSeqa().longValue();
				virementVo.setNumVirSgmt(numVirSgmt);

				// /********* Incrementation ********//

				numVirSgmt++;
				seqAgenceNew.setNumValSeqa(numVirSgmt);

				hibernateTemplate.update(seqAgenceNew);
				hibernateTemplate.flush();

			} else {
				virementVo.setNumVirSgmt(200);
			}

			return (virementVo);

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GetNumVirSGMTTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GetNumVirSGMTTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau GetNumVirSGMTTrt : ", e);
			virementVo.setMessageValidation("Probléme dans GetNumVirSGMTTrt");
			return (virementVo);

		}

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {

	}

}