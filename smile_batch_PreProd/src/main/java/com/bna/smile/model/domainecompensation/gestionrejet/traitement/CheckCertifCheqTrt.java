package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.List;

import com.bna.commun.model.CertificationCheques;
import com.bna.commun.model.Cheque30;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.Error;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;
/**
 * 
 * @author Nabil bdour
 * 
 * Traitment that return if cheque is certificated or no
 *
 */
public class CheckCertifCheqTrt extends Traitement {

	@Override
	protected void genCroText(ValueObject arg0) {
	}
	@SuppressWarnings("rawtypes")
	@Override
	protected IValueObject perform(IValueObject vo) throws Exception {

		Cheque30 chq30 = (Cheque30) vo;
		PrimitiveVO primitive= new PrimitiveVO();
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		IExpression expression = searchEngine.createExpression();
		ICriteria criteria = searchEngine.createCriteria();
		
		//  compte client 
		ContratCpt contratCpt = UtilCtr.getContratCptByRIB(chq30.getRibTir());
		criteria.add(expression.eq("contratCpt", contratCpt));
		// cheque
		criteria.add(expression.eq("numChqCchq", chq30.getNumChq()));
		// etat certif  valide
		//criteria.add(expression.eq("codEtatCchq", Constants.ETAT_CERT_VALIDE));
		criteria.add(expression.eq("codEtatCchq",Long.valueOf(1)));
		// etat certif cheque non paye 
		//criteria.add(expression.eq("codPayCchq", Constants.ETAT_CERT_NON_PAYE));
		criteria.add(expression.eq("codPayCchq",Long.valueOf(0)));

		try {
			List l = searchEngine.find(CertificationCheques.class, criteria);
			if (l.isEmpty()) {
				primitive.setVBool(false);
			}else{
				primitive.setVBool(true);
			}
			return primitive;
		} catch (Exception e) {
			e.printStackTrace();
			return primitive;
		}
	}

}
