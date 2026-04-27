package com.bna.smile.model.virement.traitement;

import java.util.Date;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class VerifierValiditerRibDoTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();

	public VerifierValiditerRibDoTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;

		DetailVirement detailVirement = (DetailVirement) virementVo.getDetailVirement();
		GlobalVirement globalVirement = (GlobalVirement) virementVo.getGlobalVirement();

		Date dateComptableAgence = new Date();
		dateComptableAgence = virementVo.getDateComptableAgence();

		this.setCroFlag(false);

		boolean boolVerfierRibDO = false;

		VirementService virementService = (VirementService) context.getBean("iVirementService");

		try {

			// ////////////// Verfier Validiter DO /////////////////////////////////////

			VirementVo objVirementVoContratCpt = new VirementVo();

			objVirementVoContratCpt.setContratCpt(globalVirement.getContratCpt());
			objVirementVoContratCpt.setGlobalVirement(globalVirement);
			objVirementVoContratCpt.setDetailVirement(null);
			objVirementVoContratCpt.setDateComptableAgence(dateComptableAgence);
			objVirementVoContratCpt.setStrRib("");
			// / false : Contrat Invalde ; True Contrat valide

			objVirementVoContratCpt = (VirementVo) virementService.verfierContratCpt(objVirementVoContratCpt);
			boolVerfierRibDO = objVirementVoContratCpt.isBoolValiderContratCpt();

			if (boolVerfierRibDO == false) {
				// System.out.println(" RIB DO VALIDE ");
				// logger.info("\n  /***** RIB DO NON VALIDE ***/   \n" );

				// / Rejeter Detail Virement
				objVirementVoContratCpt = (VirementVo) virementService.rejeterGlobalVirement(objVirementVoContratCpt);

			} else {
				// System.out.println(" RIB DO VALIDE ");
				// logger.info("\n  /***** RIB DO  VALIDE ***/   \n" );
			}

			// /////////////////////////////////////////////////////////////////////////

			// / False : Invalide /// true : valide
			virementVo.setBoolValiderContratCptDO(boolVerfierRibDO);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans VerifierValiditerRibDoTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("VerifierValiditerRibDoTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau VerifierValiditerRibDoTrt : ", e);
			virementVo.setMessageValidation("Probléme dans VerifierValiditerRibDoTrt");
			throw new RuntimeException();

		}
		return (virementVo);

	}

	private void gestionException(Date dateComptable, Structure agence, Exception e) {

		BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();
		batchExeptionPlac.setDatSystBate(new Date());
		batchExeptionPlac.setDatCompBate(dateComptable);
		batchExeptionPlac.setStructure(agence);
		batchExeptionPlac.setLibTpbmBate("Exception Batch Virement");
		batchExeptionPlac.setLibExpBate(e.getMessage());
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}
}