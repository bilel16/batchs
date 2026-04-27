package com.bna.smile.model.virement.traitement;

import java.util.Date;

import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.JourneeStructureBatchId;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class VirementsAecheanceTrt extends Traitement {

	public VirementsAecheanceTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	// à ne pas laisser en variable global
	ICriteria criteria = searchEngine.createCriteria();
	ICriteria criteriaAvanc = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();

	public IValueObject perform(IValueObject vo) {

		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);

		VirementVo virementVo = new VirementVo();
		virementVo = (VirementVo) vo;

		Date dateComptableAgence = null;
		try {

			Structure agence = new Structure();

			agence = virementVo.getStructure();
			dateComptableAgence = virementVo.getDateComptableAgence();

			// tester si la journée batch n'est pas dejà inserée
			JourneeStructureBatch journeeStructureBatch = new JourneeStructureBatch();
			JourneeStructureBatch journeeStructureBatchRetour = new JourneeStructureBatch();

			JourneeStructureBatchId journeeStructureBatchId = new JourneeStructureBatchId();

			journeeStructureBatchId.setCodStrcStrc(agence.getCodStrcStrc());
			journeeStructureBatchId.setDatJrnJrn(dateComptableAgence);
			journeeStructureBatchId.setCodBatBmet(Constants.COD_BATCH_VIREMENT_AECHEANCE);

			journeeStructureBatch.setJourneeStructureBatchId(journeeStructureBatchId);

			// tester si la journée n'est pas dejà inserée
			BatchService batchService = (BatchService) context.getBean("batchService");
			journeeStructureBatchRetour =
					(JourneeStructureBatch) batchService.getJourneeStructureBatch(journeeStructureBatch);

			if (journeeStructureBatchRetour != null && journeeStructureBatchRetour.getCodStatJsb().intValue() == 0) {
				// structure non traitée

				// //////////////////////////////////
				virementVo.setStructure(agence);
				virementVo.setDateComptableAgence(dateComptableAgence);

				VirementService virementService = (VirementService) context.getBean("iVirementService");
				//virementVo = (VirementVo) virementService.virementAgence(virementVo);
				VirementAgenceTrt virementAgenceTrt = new VirementAgenceTrt();
				virementVo=(VirementVo) virementAgenceTrt.exec(virementVo);
				virementVo.setEtatEnregistrement(virementVo.isEtatEnregistrement());
				// //////////////////////////////////

				// journée batch OK
				journeeStructureBatch.setDatCloJsb(new Date());
				journeeStructureBatch.setCodStatJsb(Long.valueOf("1"));
				journeeStructureBatch =
						(JourneeStructureBatch) batchService.updateJourneeStructureBatch(journeeStructureBatch);

			} else {
				logger.debug("Journée batch dejà insérée pour l'agence " + agence.getCodStrcStrc());
				virementVo.setEtatEnregistrement(true);
				virementVo.setMessageValidation("Journée batch dejà insérée");
			}

			// }

			// } else {
			// logger.debug("La liste des agences est vide.");
			// }

			logger.debug("--- FIN MOULINETTE VIREMENTS A ECHEANCE ---");

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans VirementsAecheanceTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("VirementsAecheanceTrt");
			logger.error("Exception : ", e);
			virementVo.addError(erreur);
			virementVo.setEtatEnregistrement(false);
			virementVo.setMessageValidation(e.getMessage());
			throw new RuntimeException(e);

		}
		return virementVo;
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

}
