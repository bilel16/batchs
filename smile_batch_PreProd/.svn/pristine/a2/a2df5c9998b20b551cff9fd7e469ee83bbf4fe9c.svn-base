package com.bna.smile.model.prelevement.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.JourneeStructureBatchId;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.prelevement.dao.PrelevementDAO;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.bna.smile.model.prelevement.service.PrelevementBatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GestionPrelevementsDomiciliationsTrt extends Traitement {

	public GestionPrelevementsDomiciliationsTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	private SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	// à ne pas laisser en variable global
	ICriteria criteria = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();
	PrelevementBatchService prelevementBatchService = (PrelevementBatchService) context
			.getBean("iPrelevementBatchService");

	public IValueObject perform(IValueObject vo) {

		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);

		PrelevementVo prelevementVo = (PrelevementVo) vo;

		Date dateComptableAgence = prelevementVo.getDateComptable();

		try {

			Structure agence = prelevementVo.getStructure();
			PrelevementDAO prelevementDAO = (PrelevementDAO) context.getBean("prelevementDAO");

			Long codeStrcBCT = Long.valueOf(prelevementDAO.getCodeStructureBCT(agence.getCodStrcStrc()));
			Long codeStrcBNA = agence.getCodStrcStrc();
			System.out.println("Agence = " + codeStrcBNA + "  --> BCT = " + codeStrcBCT);
			logger.info("codetStrcBNA : " + codeStrcBNA + "----> codeAgenceBCT: " + codeStrcBCT);

			// **************** Tester Si Agence a des prelevements ou des domiciliations *******************//

			long nbreLotsPrelevements = 0;
			long nbreLotsDomiciations = 0;

			nbreLotsPrelevements =
					prelevementDAO.getNombresDesLotsPrelevements(Constants.COD_ENREGISTREMENT_PRELEVEMENT, codeStrcBCT);

			nbreLotsDomiciations =
					prelevementDAO.getNombresDesLotsDomiciliations(Constants.COD_ENREGISTREMENT_DOMICILIATION,
							codeStrcBCT);

			// ******************Cas du Contrat Domiciliations **************//

			if (nbreLotsDomiciations > 0) {
				try {
					PrelevementVo prelevementVoDom = new PrelevementVo();
					prelevementVoDom.setDateComptable(dateComptableAgence);
					prelevementVoDom.setCodeDevise(Constants.COD_DEV_DINAR);
					prelevementVoDom.setCodeValeur(Constants.COD_ENREGISTREMENT_DOMICILIATION);

					/*************** Rechercher existance Journee Structure *********/

					ICriteria criteriaJS = searchEngine.createCriteria();
					IExpression expresJS = searchEngine.createExpression();

					criteriaJS.add(expresJS.eq("journeeStructureId.codStrcStrc", codeStrcBNA));
					criteriaJS.add(expresJS.eq("journeeStructureId.datJrnJrn", dateComptableAgence));

					Set<JourneeStructure> liste_JourneeStructure =
							new HashSet<JourneeStructure>(searchEngine.find(JourneeStructure.class, criteriaJS));

					if (liste_JourneeStructure != null && liste_JourneeStructure.size() == 0) {

						logger.info("Journée Structure non existante pour la structure " + codeStrcBNA);

					} else if (liste_JourneeStructure != null && liste_JourneeStructure.size() > 0) {

						/*************** Rechercher existance Journee Structure Batch *********/

						ICriteria criteriaJSB = searchEngine.createCriteria();
						IExpression expresJSB = searchEngine.createExpression();

						criteriaJSB.add(expresJSB.eq("journeeStructureBatchId.codStrcStrc", codeStrcBNA));
						criteriaJSB.add(expresJSB.eq("journeeStructureBatchId.datJrnJrn", dateComptableAgence));
						criteriaJSB.add(expresJSB.eq("journeeStructureBatchId.codBatBmet",
								Constants.COD_BATCH_DOMICILIATION));

						Set<JourneeStructureBatch> liste_JourneeStructureBatch =
								new HashSet<JourneeStructureBatch>(searchEngine.find(JourneeStructureBatch.class,
										criteriaJSB));

						if (liste_JourneeStructureBatch.size() > 0) {
							logger.info("Journée Structure Batch Prelevement existe pour la structure " + codeStrcBNA);

						} else {

							/********** Creation d'une Journee Structure Batch ***************/

							JourneeStructureBatch journeeStructureBatch = new JourneeStructureBatch();
							JourneeStructureBatchId journeeStructureBatchId = new JourneeStructureBatchId();
							journeeStructureBatchId.setCodBatBmet(Constants.COD_BATCH_DOMICILIATION);
							journeeStructureBatchId.setCodStrcStrc(codeStrcBNA);
							journeeStructureBatchId.setDatJrnJrn(dateComptableAgence);
							journeeStructureBatch.setJourneeStructureBatchId(journeeStructureBatchId);
							journeeStructureBatch.setCodStatJsb(Long.valueOf(0));
							crudService.create(journeeStructureBatch);

							PrelevementVo prelevementVoGestionCDOM = new PrelevementVo();
							prelevementVoGestionCDOM.setDateComptable(dateComptableAgence);
							prelevementVoGestionCDOM.setCodeStructureBCT(codeStrcBCT);
							prelevementVoGestionCDOM.setCodeStructureBNA(codeStrcBNA);
							prelevementVoGestionCDOM.setCodeDevise(Constants.COD_DEV_DINAR);
							prelevementVoGestionCDOM.setCodeValeur(Constants.COD_ENREGISTREMENT_DOMICILIATION);
							prelevementVoGestionCDOM =
									(PrelevementVo) prelevementBatchService
											.traiterDomiciliationsRecus(prelevementVoGestionCDOM);

							if (prelevementVoGestionCDOM.isEtatEnregistrementPrelevement() == true) {

								/****************** Fin Batch Journee Structure ****************/

								journeeStructureBatch.setCodStatJsb(Long.valueOf(1));
								journeeStructureBatch.setDatCloJsb(formaterDate.parse(formaterDate.format(new Date())));
								crudService.update(journeeStructureBatch);

							}

						}
					}

					logger.info("************ FIN CDOM POUR L'AGENCE " + codeStrcBNA + " *****************");
				} catch (Exception e) {
					logger.error(e.getMessage());
					com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
					StringBuffer text = new StringBuffer(e.getMessage());
					text.append(e.toString());
					erreur.setCode("100");
					erreur.setDescription(text.toString());
					prelevementVo.addError(erreur);
					throw new RuntimeException(e);
				}

			}

			// ******************Cas du Prelevements **************//
			if (nbreLotsPrelevements > 0) {

				prelevementVo.setDateComptable(dateComptableAgence);
				prelevementVo.setCodeDevise(Constants.COD_DEV_DINAR);
				prelevementVo.setCodeValeur(Constants.COD_ENREGISTREMENT_PRELEVEMENT);

				// ******************Gestion des Prelevements Reçus **************//
				try {

					PrelevementVo prelevementVoGestionPrel = new PrelevementVo();
					prelevementVoGestionPrel.setDateComptable(dateComptableAgence);
					prelevementVoGestionPrel.setCodeStructureBCT(codeStrcBCT);
					prelevementVoGestionPrel.setCodeStructureBNA(codeStrcBNA);
					prelevementVoGestionPrel =
							(PrelevementVo) prelevementBatchService.traiterPrelevementsRecus(prelevementVoGestionPrel);

					logger.info("************ FIN Prelevement " + codeStrcBNA + " *****************");
				} catch (Exception e) {
					logger.error(e.getMessage());
					com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
					StringBuffer text = new StringBuffer(e.getMessage());
					text.append(e.toString());
					erreur.setCode("100");
					erreur.setDescription(text.toString());
					prelevementVo.addError(erreur);
					throw new RuntimeException(e);
				}
			}

			prelevementVo.setEtatEnregistrementPrelevement(true);

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GestionPrelevementsDomiciliationsTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GestionPrelevementsDomiciliationsTrt");
			logger.error("Exception : ", e);
			prelevementVo.addError(erreur);
			prelevementVo.setEtatEnregistrementPrelevement(false);
			throw new RuntimeException(e);

		}
		return prelevementVo;
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

}
