package com.bna.smile.model.banqueAssurance.traitement;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailAdhesion;
import com.bna.commun.model.TraceAssuranceVieDecouvert;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ValidResiliationAssVieDecouvertAMITrt extends Traitement {

	public ValidResiliationAssVieDecouvertAMITrt() {
	}

	public IValueObject perform(IValueObject vo) {
		ParamAdhesion paramAdhesion = (ParamAdhesion) vo;
		Context context = ContextHandler.getContext();
		TraceAssuranceVieDecouvert traceAssuranceVieDecouvert = new TraceAssuranceVieDecouvert();
		DetailAdhesion detailAdhesion = new DetailAdhesion();
		CreatDetailAdhesionAssVieTrt creatDetailAdhesionAssVieTrt = new CreatDetailAdhesionAssVieTrt();
		AdhesionAssVie adhesionAssVie = paramAdhesion.getAdhesionAssVie();
		ISearchEngine searchEngine = (ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");
		ContratCpt contratCptDo = new ContratCpt();

		try {

			CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

			if (paramAdhesion.getAdhesionAssVie() != null) {
				paramAdhesion.getAdhesionAssVie().setCodEtatAdh("R");
				crudService.update(paramAdhesion.getAdhesionAssVie());

				contratCptDo = paramAdhesion.getAdhesionAssVie().getContratCpt();
				contratCptDo = (ContratCpt) searchEngine.get(ContratCpt.class, contratCptDo.getContratCptId());
				contratCptDo.setDatEautCcpt(paramAdhesion.getDateComptable());
				contratCptDo.setMontAutCcpt(Long.valueOf(0));
				crudService.update(contratCptDo);
				detailAdhesion.setContratCpt(contratCptDo);

				detailAdhesion.setAdhesionAssVie(paramAdhesion.getAdhesionAssVie());
				detailAdhesion.setCodEtatDadh("R");
				detailAdhesion.setDatDebDadh(paramAdhesion.getDateComptable());
				detailAdhesion.setDatFinDadh(paramAdhesion.getDateComptable());
				creatDetailAdhesionAssVieTrt.exec(detailAdhesion);

				if (paramAdhesion.getTraceAssuranceVieDecouvert() != null) {
					traceAssuranceVieDecouvert = paramAdhesion.getTraceAssuranceVieDecouvert();
					traceAssuranceVieDecouvert.setAdhesionAssVie(paramAdhesion.getAdhesionAssVie());
					crudService.create(traceAssuranceVieDecouvert);
				}
			}

			return (paramAdhesion);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans ValidResiliationAssVieDecouvertAMITrt : ");
			text.append(e.toString());
			erreur.setCode("200");
			erreur.setDescription(text.toString());
			paramAdhesion.addError(erreur);
			return (paramAdhesion);
		}
	}

	public void genCroText(ValueObject vo) {
	}

	public String getNumeroTache(IValueObject vo) {

		return Constants.CODE_RESSOURCE_GENERALE;

	}
}
