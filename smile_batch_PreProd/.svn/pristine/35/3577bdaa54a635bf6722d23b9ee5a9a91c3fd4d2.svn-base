package com.bna.smile.model.virement.traitement;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.JourneeStructureBatchId;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class VirementsLieesComptesVertsTrt extends Traitement {

	public VirementsLieesComptesVertsTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

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
			logger.info("********** Debut Moulinette Virements Comptes Verts **************");

			Structure agence = new Structure();

			agence = virementVo.getStructure();
			dateComptableAgence = virementVo.getDateComptableAgence();

			/*************** Rechercher existance Journee Structure Batch *********/

			ICriteria criteriaJSB = searchEngine.createCriteria();
			IExpression expresJSB = searchEngine.createExpression();

			criteriaJSB.add(expresJSB.eq("journeeStructureBatchId.codStrcStrc", agence.getCodStrcStrc()));
			criteriaJSB.add(expresJSB.eq("journeeStructureBatchId.datJrnJrn", dateComptableAgence));
			criteriaJSB.add(expresJSB.eq("journeeStructureBatchId.codBatBmet",
					Constants.COD_BATCH_VIREMENT_LIEES_COMPTES_VERTS));

			Set<JourneeStructureBatch> liste_JourneeStructureBatch =
					new HashSet<JourneeStructureBatch>(searchEngine.find(JourneeStructureBatch.class, criteriaJSB));

			if (liste_JourneeStructureBatch.size() > 0) {

				logger.debug("Journée batch dejà insérée pour l'agence " + agence.getCodStrcStrc());
				virementVo.setEtatEnregistrement(true);
				virementVo.setMessageValidation("Journée batch dejà insérée");

			} else {

				/********** Creation d'une Journee Structure Batch ***************/

				JourneeStructureBatch journeeStructureBatch = new JourneeStructureBatch();
				JourneeStructureBatchId journeeStructureBatchId = new JourneeStructureBatchId();
				journeeStructureBatchId.setCodBatBmet(Constants.COD_BATCH_VIREMENT_LIEES_COMPTES_VERTS);
				journeeStructureBatchId.setCodStrcStrc(agence.getCodStrcStrc());
				journeeStructureBatchId.setDatJrnJrn(dateComptableAgence);
				journeeStructureBatch.setJourneeStructureBatchId(journeeStructureBatchId);
				journeeStructureBatch.setCodStatJsb(Long.valueOf(0));
				crudService.create(journeeStructureBatch);

				// //////////////////////////////////
				virementVo.setStructure(agence);
				virementVo.setDateComptableAgence(dateComptableAgence);

				VirementService virementService = (VirementService) context.getBean("iVirementService");
				// virementVo = (VirementVo) virementService.virementAgence(virementVo);
				VirementsLieesCompteVertsByAgenceTrt virementsLieesCompteVertsByAgenceTrt =
						new VirementsLieesCompteVertsByAgenceTrt();
				virementVo = (VirementVo) virementsLieesCompteVertsByAgenceTrt.exec(virementVo);
				virementVo.setEtatEnregistrement(virementVo.isEtatEnregistrement());
				virementVo.setMessageValidation(virementVo.getMessageValidation());
				// //////////////////////////////////

				// journée batch OK
				journeeStructureBatch.setDatCloJsb(new Date());
				journeeStructureBatch.setCodStatJsb(Long.valueOf("1"));
				crudService.update(journeeStructureBatch);

			}

			logger.info("********** Fin Moulinette Virements Comptes Verts **************");

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans VirementsLieesComptesVertsTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("VirementsLieesComptesVertsTrt");
			logger.error("Exception : ", e);
			virementVo.setEtatEnregistrement(false);
			virementVo.setMessageValidation(text.toString());
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
