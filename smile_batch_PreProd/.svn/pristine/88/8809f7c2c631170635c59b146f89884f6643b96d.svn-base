package com.bna.smile.model.prelevement.traitement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bna.commun.model.AdPrelevement;
import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratDomiciliations;
import com.bna.commun.model.DetailDomiciliationTemp;
import com.bna.commun.model.Emetteur;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceContDomiciliations;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetContratCptByIdTrt;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.prelevement.dao.PrelevementDAO;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GestionLotsDomiciliationsTrt extends Traitement {

	public GestionLotsDomiciliationsTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	PrelevementDAO prelevementDAO = (PrelevementDAO) context.getBean("prelevementDAO");
	// à ne pas laisser en variable global
	ICriteria criteria = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();

	public IValueObject perform(IValueObject vo) {

		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);

		PrelevementVo prelevementVo = new PrelevementVo();
		Structure structureException = new Structure();
		try {

			prelevementVo = (PrelevementVo) vo;
			// *********** Recherche ADT DOMICILIATION *******//

			ICriteria criteriaCDOM = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();
			criteriaCDOM.add(expression.eq("detailDomiciliationTempId.datOpeDom", prelevementVo.getDateComptable()));
			criteriaCDOM.add(expression.eq("detailDomiciliationTempId.codSenDom", Constants.COD_SENS_RECU));
			criteriaCDOM.add(expression.eq("codValDom", prelevementVo.getCodeValeur()));
			criteriaCDOM.add(expression.eq("codDevDev", prelevementVo.getCodeDevise()));
			criteriaCDOM.add(expression.eq("codEnrDom", Constants.COD_ENREGISTREMENT_DETAIL_PRESENTATION));
			criteriaCDOM.add(expression.eq("codAgeDes", prelevementVo.getCodeStructureBCT()));
			criteriaCDOM.add(expression.eq("codEtatDom", Constants.COD_ETAT_DETAIL_DOM_TEMP_ATTENTE));

			Set<DetailDomiciliationTemp> liste_Domiciliations =
					new HashSet<DetailDomiciliationTemp>(searchEngine.find(DetailDomiciliationTemp.class, criteriaCDOM));

			logger.info("liste_Domiciliations Agence " + prelevementVo.getCodeStructureBNA() + " : "
					+ liste_Domiciliations.size());

			if (liste_Domiciliations.size() > 0) {

				long nbreDomiciliationAjout = 0;
				long nbreDomiciliationMod = 0;
				long nbreDomiciliationSupp = 0;
				long nbreDomiciliationResil = 0;
				long nbreDomiciliationRejetes = 0;
				long compteurDomiciliation = 0;

				long codStrcBNA = prelevementVo.getCodeStructureBNA();
				Structure structure = new Structure();
				structure.setCodStrcStrc(codStrcBNA);
				structureException.setCodStrcStrc(codStrcBNA);
				for (DetailDomiciliationTemp detailDomiciliationTemp : liste_Domiciliations) {

					/********* Insertion dans la table Contrat_Domiciliation *********************/

					logger.info("compteurDomiciliation : " + compteurDomiciliation++);

					if (detailDomiciliationTemp.getCodMajDom().equals(Constants.COD_NATURE_AJOUT_TRACE_DOMICILIATION)) {

						String ribTireur = detailDomiciliationTemp.getDetailDomiciliationTempId().getRibTirDom();
						if (ribTireur.length() == 19) {
							ribTireur = "0" + ribTireur;
						}
						ContratCpt contratCpt = new ContratCpt();
						ContratCptId contratCptId = new ContratCptId();
						contratCptId.setCodStrcStrc(Long.valueOf(ribTireur.substring(5, 8)));
						contratCptId.setCodPrdPrd(Long.valueOf(ribTireur.substring(8, 12)));
						contratCptId.setNumCcptCcpt(Long.valueOf(ribTireur.substring(12, 18)));
						contratCpt.setContratCptId(contratCptId);

						GetContratCptByIdTrt contratCptByIdTrt = new GetContratCptByIdTrt();

						contratCpt = (ContratCpt) contratCptByIdTrt.exec(contratCpt);

						boolean produitEligible = false;

						if (contratCpt != null && contratCpt.getContratCptId() != null) {

							for (Long prd : Constants.produitPrelevementsEligible) {

								if (prd.equals(contratCpt.getContratCptId().getCodPrdPrd())) {
									produitEligible = true;
								}
							}

							if (produitEligible == true && contratCpt.getClient().getNumSeqPers() != null) {

								/********** Verif existence Cdom ************************/

								ICriteria criteria = searchEngine.createCriteria();
								IExpression expres = searchEngine.createExpression();
								List<String> listeEtatCDOM = new ArrayList<String>();
								listeEtatCDOM.add(Constants.COD_ETAT_DOMICILIATION_VALIDE);
								listeEtatCDOM.add(Constants.COD_ETAT_DOMICILIATION_RESILIE);

								// criteria.add(expres.eq("numContCdom", detailDomiciliationTemp
								// .getDetailDomiciliationTempId().getNumDomDom()));
								criteria.add(expres.eq("contratCpt.contratCptId.codStrcStrc",
										Long.valueOf(ribTireur.substring(5, 8))));
								criteria.add(expres.eq("contratCpt.contratCptId.codPrdPrd",
										Long.valueOf(ribTireur.substring(8, 12))));
								criteria.add(expres.eq("contratCpt.contratCptId.numCcptCcpt",
										Long.valueOf(ribTireur.substring(12, 18))));
								criteria.add(expres.eq("emetteur.codEmtrEmtr", detailDomiciliationTemp.getEmetteur()
										.getCodEmtrEmtr()));
								criteria.add(expres.eq("numRefCdom", detailDomiciliationTemp.getNumRefDom()));
								criteria.add(expres.in("codEtatCdom", listeEtatCDOM));

								Set<ContratDomiciliations> liste_ContratsDomiciliations =
										new HashSet<ContratDomiciliations>(searchEngine.find(
												ContratDomiciliations.class, criteria));

								if (liste_ContratsDomiciliations != null && liste_ContratsDomiciliations.size() > 0) {

									nbreDomiciliationRejetes++;

									detailDomiciliationTemp.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_REJETE);
									crudService.update(detailDomiciliationTemp);

								} else {

									if (contratCpt.getCodEtatCcpt() != null
											&& contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)) {

										ContratDomiciliations contratDomiciliations = new ContratDomiciliations();

										long numSeqCdom = prelevementDAO.getSequenceContratDomiciliation();
										contratDomiciliations.setNumSeqCdom(numSeqCdom);
										contratDomiciliations.setDatCreCdom(detailDomiciliationTemp
												.getDetailDomiciliationTempId().getDatOpeDom());
										contratDomiciliations.setNumContCdom(detailDomiciliationTemp
												.getDetailDomiciliationTempId().getNumDomDom());
										Emetteur emetteur = new Emetteur();
										emetteur.setCodEmtrEmtr(detailDomiciliationTemp.getEmetteur().getCodEmtrEmtr());
										contratDomiciliations.setEmetteur(emetteur);

										contratDomiciliations.setContratCpt(contratCpt);
										contratDomiciliations.setCodEtatCdom(Constants.COD_ETAT_DOMICILIATION_VALIDE);
										contratDomiciliations.setNumRefCdom(detailDomiciliationTemp.getNumRefDom());
										contratDomiciliations.setNumLotCdom(detailDomiciliationTemp
												.getDetailDomiciliationTempId().getNumLotDom());

										crudService.create(contratDomiciliations);

										// ************ Creation Cro 817 ******************//
										PrelevementVo prelevementVoCRO = new PrelevementVo();
										Operation operation = new Operation();
										operation.setCodOperOper(Constants.COD_OPER_RECEP_LOT_CONTRAT_DOMICILIATION);
										Produit produit = new Produit();
										produit.setCodPrdPrd(Constants.COD_PRODUIT_PRELEVEMENTS);

										prelevementVoCRO.setOperation(operation);
										prelevementVoCRO.setProduit(produit);
										prelevementVoCRO.setContratDomiciliations(contratDomiciliations);
										prelevementVoCRO.setDateComptable(contratDomiciliations.getDatCreCdom());
										prelevementVoCRO.setContratCpt(contratCpt);

										CreationCROCreationContratDomiciliationTrt creationCROCreationContratDomiciliationTrt =
												new CreationCROCreationContratDomiciliationTrt();

										prelevementVoCRO =
												(PrelevementVo) creationCROCreationContratDomiciliationTrt
														.exec(prelevementVoCRO);

										// ***********Creation dans Trace_Cont_domiciliation ****************//

										TraceContDomiciliations traceContDomiciliations = new TraceContDomiciliations();
										long numSeqTdom = prelevementDAO.getSequenceTraceContratDomiciliation();

										traceContDomiciliations.setStructure(structure);
										traceContDomiciliations.setNumSeqTdom(numSeqTdom);
										traceContDomiciliations.setDatTrcTdom(contratDomiciliations.getDatCreCdom());
										traceContDomiciliations.setNumContTdom(contratDomiciliations.getNumContCdom());
										traceContDomiciliations
												.setNatTrcTdom(Constants.COD_NATURE_AJOUT_TRACE_DOMICILIATION);
										traceContDomiciliations.setContratDomiciliations(contratDomiciliations);
										traceContDomiciliations.setNumMatrUser("9999");
										traceContDomiciliations.setMontTrcTdom(prelevementVoCRO.getOperationMoyPay()
												.getMontDinOmp());

										crudService.create(traceContDomiciliations);
										nbreDomiciliationAjout++;

										// ************* Update detailDomiciliationTemp *****************************//

										detailDomiciliationTemp
												.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_VALIDE);
										crudService.update(detailDomiciliationTemp);
									} else {
										nbreDomiciliationRejetes++;

										detailDomiciliationTemp
												.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_REJETE);
										crudService.update(detailDomiciliationTemp);
									}
								}
							} else {
								nbreDomiciliationRejetes++;

								detailDomiciliationTemp.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_REJETE);
								crudService.update(detailDomiciliationTemp);

							}
						}
					} else if (detailDomiciliationTemp.getCodMajDom().equals(
							Constants.COD_NATURE_MODIFICATION_TRACE_DOMICILIATION)) {

						String ribTireur = detailDomiciliationTemp.getDetailDomiciliationTempId().getRibTirDom();
						if (ribTireur.length() == 19) {
							ribTireur = "0" + ribTireur;
						}
						ICriteria criteria = searchEngine.createCriteria();
						IExpression expres = searchEngine.createExpression();

						criteria.add(expres.eq("contratCpt.contratCptId.codStrcStrc",
								Long.valueOf(ribTireur.substring(5, 8))));
						criteria.add(expres.eq("contratCpt.contratCptId.codPrdPrd",
								Long.valueOf(ribTireur.substring(8, 12))));
						criteria.add(expres.eq("contratCpt.contratCptId.numCcptCcpt",
								Long.valueOf(ribTireur.substring(12, 18))));
						criteria.add(expres.eq("emetteur.codEmtrEmtr", detailDomiciliationTemp.getEmetteur()
								.getCodEmtrEmtr()));
						criteria.add(expres.eq("numRefCdom", detailDomiciliationTemp.getNumRefDom()));

						Set liste_ContratsDomiciliations =
								new HashSet<ContratDomiciliations>(searchEngine.find(ContratDomiciliations.class,
										criteria));

						if (liste_ContratsDomiciliations.size() > 0) {

							ContratDomiciliations contratDomiciliations =
									(ContratDomiciliations) liste_ContratsDomiciliations.iterator().next();

							contratDomiciliations.setDatMajCdom(detailDomiciliationTemp.getDetailDomiciliationTempId()
									.getDatOpeDom());
							ContratCpt contratCpt = new ContratCpt();
							ContratCpt contratCptAncien = new ContratCpt();
							ContratCptId contratCptId = new ContratCptId();
							contratCptId.setCodStrcStrc(Long.valueOf(ribTireur.substring(5, 8)));
							contratCptId.setCodPrdPrd(Long.valueOf(ribTireur.substring(8, 12)));
							contratCptId.setNumCcptCcpt(Long.valueOf(ribTireur.substring(12, 18)));
							contratCpt.setContratCptId(contratCptId);
							contratCptAncien = contratDomiciliations.getContratCpt();
							contratDomiciliations.setContratCpt(contratCpt);

							crudService.update(contratDomiciliations);

							// ***********Creation dans Trace_Cont_domiciliation ****************//

							TraceContDomiciliations traceContDomiciliations = new TraceContDomiciliations();
							long numSeqTdom = prelevementDAO.getSequenceTraceContratDomiciliation();

							traceContDomiciliations.setStructure(structure);
							traceContDomiciliations.setNumSeqTdom(numSeqTdom);
							traceContDomiciliations.setDatTrcTdom(detailDomiciliationTemp
									.getDetailDomiciliationTempId().getDatOpeDom());
							traceContDomiciliations.setNumContTdom(contratDomiciliations.getNumContCdom());
							traceContDomiciliations
									.setNatTrcTdom(Constants.COD_NATURE_MODIFICATION_TRACE_DOMICILIATION);
							traceContDomiciliations.setContratDomiciliations(contratDomiciliations);
							traceContDomiciliations.setNumMatrUser("9999");
							traceContDomiciliations.setCodStrcTdom(contratCptAncien.getContratCptId().getCodStrcStrc());
							traceContDomiciliations.setCodPrdTdom(contratCptAncien.getContratCptId().getCodPrdPrd());
							traceContDomiciliations.setNumCcptTdom(contratCptAncien.getContratCptId().getNumCcptCcpt());

							crudService.create(traceContDomiciliations);

							nbreDomiciliationMod++;

							// ************* Update detailDomiciliationTemp *****************************//

							detailDomiciliationTemp.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_VALIDE);
							crudService.update(detailDomiciliationTemp);

						} else {

							/************* Creation domiciliation si n'existe pas ********/

							ContratCpt contratCpt = new ContratCpt();
							ContratCptId contratCptId = new ContratCptId();
							contratCptId.setCodStrcStrc(Long.valueOf(ribTireur.substring(5, 8)));
							contratCptId.setCodPrdPrd(Long.valueOf(ribTireur.substring(8, 12)));
							contratCptId.setNumCcptCcpt(Long.valueOf(ribTireur.substring(12, 18)));
							contratCpt.setContratCptId(contratCptId);

							GetContratCptByIdTrt contratCptByIdTrt = new GetContratCptByIdTrt();

							contratCpt = (ContratCpt) contratCptByIdTrt.exec(contratCpt);

							boolean produitEligible = false;

							if (contratCpt != null && contratCpt.getContratCptId() != null) {

								for (Long prd : Constants.produitPrelevementsEligible) {

									if (prd.equals(contratCpt.getContratCptId().getCodPrdPrd())) {
										produitEligible = true;
									}
								}

								if (produitEligible == true && contratCpt.getClient().getNumSeqPers() != null) {

									if (contratCpt.getCodEtatCcpt() != null
											&& contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)) {

										ContratDomiciliations contratDomiciliations = new ContratDomiciliations();

										long numSeqCdom = prelevementDAO.getSequenceContratDomiciliation();
										contratDomiciliations.setNumSeqCdom(numSeqCdom);
										contratDomiciliations.setDatCreCdom(detailDomiciliationTemp
												.getDetailDomiciliationTempId().getDatOpeDom());
										contratDomiciliations.setNumContCdom(detailDomiciliationTemp
												.getDetailDomiciliationTempId().getNumDomDom());
										Emetteur emetteur = new Emetteur();
										emetteur.setCodEmtrEmtr(detailDomiciliationTemp.getEmetteur().getCodEmtrEmtr());
										contratDomiciliations.setEmetteur(emetteur);

										contratDomiciliations.setContratCpt(contratCpt);
										contratDomiciliations.setCodEtatCdom(Constants.COD_ETAT_DOMICILIATION_VALIDE);
										contratDomiciliations.setNumRefCdom(detailDomiciliationTemp.getNumRefDom());
										contratDomiciliations.setNumLotCdom(detailDomiciliationTemp
												.getDetailDomiciliationTempId().getNumLotDom());

										crudService.create(contratDomiciliations);

										// ************ Creation Cro 817 ******************//
										PrelevementVo prelevementVoCRO = new PrelevementVo();
										Operation operation = new Operation();
										operation.setCodOperOper(Constants.COD_OPER_RECEP_LOT_CONTRAT_DOMICILIATION);
										Produit produit = new Produit();
										produit.setCodPrdPrd(Constants.COD_PRODUIT_PRELEVEMENTS);

										prelevementVoCRO.setOperation(operation);
										prelevementVoCRO.setProduit(produit);
										prelevementVoCRO.setContratDomiciliations(contratDomiciliations);
										prelevementVoCRO.setDateComptable(contratDomiciliations.getDatCreCdom());
										prelevementVoCRO.setContratCpt(contratCpt);

										CreationCROCreationContratDomiciliationTrt creationCROCreationContratDomiciliationTrt =
												new CreationCROCreationContratDomiciliationTrt();

										prelevementVoCRO =
												(PrelevementVo) creationCROCreationContratDomiciliationTrt
														.exec(prelevementVoCRO);

										// ***********Creation dans Trace_Cont_domiciliation ****************//

										TraceContDomiciliations traceContDomiciliations = new TraceContDomiciliations();
										long numSeqTdom = prelevementDAO.getSequenceTraceContratDomiciliation();

										traceContDomiciliations.setStructure(structure);
										traceContDomiciliations.setNumSeqTdom(numSeqTdom);
										traceContDomiciliations.setDatTrcTdom(contratDomiciliations.getDatCreCdom());
										traceContDomiciliations.setNumContTdom(contratDomiciliations.getNumContCdom());
										traceContDomiciliations
												.setNatTrcTdom(Constants.COD_NATURE_AJOUT_TRACE_DOMICILIATION);
										traceContDomiciliations.setContratDomiciliations(contratDomiciliations);
										traceContDomiciliations.setNumMatrUser("9999");
										traceContDomiciliations.setMontTrcTdom(prelevementVoCRO.getOperationMoyPay()
												.getMontDinOmp());

										crudService.create(traceContDomiciliations);
										nbreDomiciliationAjout++;

										// ************* Update detailDomiciliationTemp *****************************//

										detailDomiciliationTemp
												.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_VALIDE);
										crudService.update(detailDomiciliationTemp);
									} else {
										nbreDomiciliationRejetes++;

										detailDomiciliationTemp
												.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_REJETE);
										crudService.update(detailDomiciliationTemp);
									}
								} else {

									nbreDomiciliationRejetes++;

									detailDomiciliationTemp.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_REJETE);
									crudService.update(detailDomiciliationTemp);
								}
							} else {

								nbreDomiciliationRejetes++;

								detailDomiciliationTemp.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_REJETE);
								crudService.update(detailDomiciliationTemp);
							}
						}

					} else if (detailDomiciliationTemp.getCodMajDom().equals(
							Constants.COD_NATURE_SUPPRESSION_TRACE_DOMICILIATION)) {

						String ribTireur = detailDomiciliationTemp.getDetailDomiciliationTempId().getRibTirDom();
						if (ribTireur.length() == 19) {
							ribTireur = "0" + ribTireur;
						}

						ICriteria criteria = searchEngine.createCriteria();
						IExpression expres = searchEngine.createExpression();

						criteria.add(expres.eq("contratCpt.contratCptId.codStrcStrc",
								Long.valueOf(ribTireur.substring(5, 8))));
						criteria.add(expres.eq("contratCpt.contratCptId.codPrdPrd",
								Long.valueOf(ribTireur.substring(8, 12))));
						criteria.add(expres.eq("contratCpt.contratCptId.numCcptCcpt",
								Long.valueOf(ribTireur.substring(12, 18))));
						criteria.add(expres.eq("emetteur.codEmtrEmtr", detailDomiciliationTemp.getEmetteur()
								.getCodEmtrEmtr()));
						criteria.add(expres.eq("numRefCdom", detailDomiciliationTemp.getNumRefDom()));

						Set liste_ContratsDomiciliations =
								new HashSet<ContratDomiciliations>(searchEngine.find(ContratDomiciliations.class,
										criteria));

						if (liste_ContratsDomiciliations.size() > 0) {

							ContratDomiciliations contratDomiciliations =
									(ContratDomiciliations) liste_ContratsDomiciliations.iterator().next();

							contratDomiciliations.setDatMajCdom(detailDomiciliationTemp.getDetailDomiciliationTempId()
									.getDatOpeDom());
							contratDomiciliations.setCodEtatCdom(Constants.COD_ETAT_DOMICILIATION_ANNULE);
							crudService.update(contratDomiciliations);

							// ***********Creation dans Trace_Cont_domiciliation ****************//

							TraceContDomiciliations traceContDomiciliations = new TraceContDomiciliations();
							long numSeqTdom = prelevementDAO.getSequenceTraceContratDomiciliation();

							traceContDomiciliations.setStructure(structure);
							traceContDomiciliations.setNumSeqTdom(numSeqTdom);
							traceContDomiciliations.setDatTrcTdom(detailDomiciliationTemp
									.getDetailDomiciliationTempId().getDatOpeDom());
							traceContDomiciliations.setNumContTdom(contratDomiciliations.getNumContCdom());
							traceContDomiciliations.setNatTrcTdom(Constants.COD_NATURE_SUPPRESSION_TRACE_DOMICILIATION);
							traceContDomiciliations.setContratDomiciliations(contratDomiciliations);
							traceContDomiciliations.setNumMatrUser("9999");
							crudService.create(traceContDomiciliations);

							nbreDomiciliationSupp++;

							// ************* Update detailDomiciliationTemp *****************************//

							detailDomiciliationTemp.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_VALIDE);
							crudService.update(detailDomiciliationTemp);

						} else {
							nbreDomiciliationRejetes++;

							detailDomiciliationTemp.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_REJETE);
							crudService.update(detailDomiciliationTemp);
						}
					} else if (detailDomiciliationTemp.getCodMajDom().equals(
							Constants.COD_NATURE_RESILIATION_TRACE_DOMICILIATION)) {

						String ribTireur = detailDomiciliationTemp.getDetailDomiciliationTempId().getRibTirDom();
						if (ribTireur.length() == 19) {
							ribTireur = "0" + ribTireur;
						}

						ICriteria criteria = searchEngine.createCriteria();
						IExpression expres = searchEngine.createExpression();

						criteria.add(expres.eq("contratCpt.contratCptId.codStrcStrc",
								Long.valueOf(ribTireur.substring(5, 8))));
						criteria.add(expres.eq("contratCpt.contratCptId.codPrdPrd",
								Long.valueOf(ribTireur.substring(8, 12))));
						criteria.add(expres.eq("contratCpt.contratCptId.numCcptCcpt",
								Long.valueOf(ribTireur.substring(12, 18))));
						criteria.add(expres.eq("emetteur.codEmtrEmtr", detailDomiciliationTemp.getEmetteur()
								.getCodEmtrEmtr()));
						criteria.add(expres.eq("numRefCdom", detailDomiciliationTemp.getNumRefDom()));

						Set<ContratDomiciliations> liste_ContratsDomiciliations =
								new HashSet<ContratDomiciliations>(searchEngine.find(ContratDomiciliations.class,
										criteria));

						if (liste_ContratsDomiciliations.size() > 0) {

							ContratDomiciliations contratDomiciliations =
									(ContratDomiciliations) liste_ContratsDomiciliations.iterator().next();

							contratDomiciliations.setDatResCdom(detailDomiciliationTemp.getDetailDomiciliationTempId()
									.getDatOpeDom());
							contratDomiciliations.setCodEtatCdom(Constants.COD_ETAT_DOMICILIATION_RESILIE);
							crudService.update(contratDomiciliations);

							// ***********Creation dans Trace_Cont_domiciliation ****************//

							TraceContDomiciliations traceContDomiciliations = new TraceContDomiciliations();
							long numSeqTdom = prelevementDAO.getSequenceTraceContratDomiciliation();

							traceContDomiciliations.setStructure(structure);
							traceContDomiciliations.setNumSeqTdom(numSeqTdom);
							traceContDomiciliations.setDatTrcTdom(detailDomiciliationTemp
									.getDetailDomiciliationTempId().getDatOpeDom());
							traceContDomiciliations.setNumContTdom(contratDomiciliations.getNumContCdom());
							traceContDomiciliations.setNatTrcTdom(Constants.COD_NATURE_RESILIATION_TRACE_DOMICILIATION);
							traceContDomiciliations.setContratDomiciliations(contratDomiciliations);
							traceContDomiciliations.setNumMatrUser("9999");
							crudService.create(traceContDomiciliations);

							nbreDomiciliationResil++;

							// ************* Update detailDomiciliationTemp *****************************//

							detailDomiciliationTemp.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_VALIDE);
							crudService.update(detailDomiciliationTemp);

						} else {
							nbreDomiciliationRejetes++;

							detailDomiciliationTemp.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_REJETE);
							crudService.update(detailDomiciliationTemp);
						}
					}
				}
				/****************** Statistique ****************/
				String messageStatistique = "";

				messageStatistique = "Sucées de l’exécution :\n";
				if (liste_Domiciliations.size() > 0) {
					messageStatistique +=
							"Nombre total des domiciliations = " + liste_Domiciliations.size() + "  ; \n ";
				}
				if (nbreDomiciliationAjout > 0) {
					messageStatistique += "Nombre des domiciliations créés = " + nbreDomiciliationAjout + "  ; \n ";
				}

				if (nbreDomiciliationMod > 0) {
					messageStatistique += "Nombre des domiciliations modifiés = " + nbreDomiciliationMod + "  ; \n ";
				}
				if (nbreDomiciliationSupp > 0) {
					messageStatistique += "Nombre des domiciliations supprimés = " + nbreDomiciliationSupp + "  ; \n ";
				}
				if (nbreDomiciliationResil > 0) {
					messageStatistique += "Nombre des domiciliations résiliés = " + nbreDomiciliationResil + "  ; \n ";
				}
				if (nbreDomiciliationRejetes > 0) {
					messageStatistique += "Nombre des domiciliations rejetés = " + nbreDomiciliationRejetes + "  ; \n ";
				}

				gestionStatistique(prelevementVo.getDateComptable(), structure, messageStatistique);

				prelevementVo.setMsgEnregistrement(messageStatistique);
			}

			// ****************** Fin Insertion ******************//
			prelevementVo.setEtatEnregistrementPrelevement(true);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GestionLotsDomiciliationsTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GestionLotsDomiciliationsTrt");
			logger.error("Exception : ", e);
			gestionException(prelevementVo.getDateComptable(), structureException, e);
			prelevementVo.setMsgEnregistrement(e.getMessage());
			throw new RuntimeException(e);

		}
		return prelevementVo;
	}

	static final Comparator<AdPrelevement> AdPrelevement_ORDER = new Comparator<AdPrelevement>() {

		public int compare(AdPrelevement a1, AdPrelevement a2) {
			try {
				if (Long.valueOf(a1.getAdPrelevementId().getCodAge()) > Long.valueOf(a2.getAdPrelevementId()
						.getCodAge()))
					return 1;
				else if (Long.valueOf(a1.getAdPrelevementId().getCodAge()) < Long.valueOf(a2.getAdPrelevementId()
						.getCodAge()))
					return -1;
				else
					return 0;
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return 0;

			}
		}
	};

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	private void gestionException(Date dateComptable, Structure agence, Exception e) {

		BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();
		batchExeptionPlac.setDatSystBate(new Date());
		batchExeptionPlac.setDatCompBate(dateComptable);
		batchExeptionPlac.setStructure(agence);
		batchExeptionPlac.setLibTpbmBate("Exception Batch Prelevement");
		batchExeptionPlac.setLibExpBate(e.getMessage());
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
	}

	private void gestionStatistique(Date dateComptable, Structure agence, String message) {

		BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
		batchStatPlacement.setCodEtatBats("V");
		batchStatPlacement.setDatSystBats(new Date());
		batchStatPlacement.setDatCompBats(dateComptable);
		batchStatPlacement.setStructure(agence);
		batchStatPlacement.setLibExtrBats(message);
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_DOMICILIATION);
		batchStatPlacement.setBatchMetier(batchMetier);
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);
	}

}
