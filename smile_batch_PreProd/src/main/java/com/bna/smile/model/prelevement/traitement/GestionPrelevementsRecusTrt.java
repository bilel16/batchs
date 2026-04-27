package com.bna.smile.model.prelevement.traitement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratDomiciliations;
import com.bna.commun.model.DetailEtatContrat;
import com.bna.commun.model.DetailsPrelevements;
import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.JourneeStructureBatchId;
import com.bna.commun.model.MotifRejetPrelev;
import com.bna.commun.model.MvtPrelevements;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OppositionPrelevements;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecommun.traitement.GetRibTrt;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.prelevement.dao.PrelevementDAO;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.traitement.AlimentationCompteDepotTrt;
import com.bna.smile.model.virement.traitement.VerifierProvisionCompteVertTrt;
import com.bna.smile.model.virement.traitement.VerifierRibTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GestionPrelevementsRecusTrt extends Traitement {

	public GestionPrelevementsRecusTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	private SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formaterAnnee = new SimpleDateFormat("yyyy");
	PrelevementDAO prelevementDAO = (PrelevementDAO) context.getBean("prelevementDAO");
	// à ne pas laisser en variable global
	ICriteria criteria = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();

	public IValueObject perform(IValueObject vo) {

		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);

		PrelevementVo prelevementVo = new PrelevementVo();
		prelevementVo = (PrelevementVo) vo;

		Produit produit = new Produit();
		Operation operation = new Operation();
		Structure structureException = new Structure();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_PRELEVEMENTS);
		Date dateComptable = prelevementVo.getDateComptable();

		try {
			Long codeStrcBNA = prelevementVo.getCodeStructureBNA();
			Long codeAgenceBCT = prelevementVo.getCodeStructureBCT();

			/*************** Rechercher existance Journee Structure *********/

			ICriteria criteriaJS = searchEngine.createCriteria();
			IExpression expresJS = searchEngine.createExpression();

			criteriaJS.add(expresJS.eq("journeeStructureId.codStrcStrc", codeStrcBNA));
			criteriaJS.add(expresJS.eq("journeeStructureId.datJrnJrn", dateComptable));

			Set<JourneeStructure> liste_JourneeStructure =
					new HashSet<JourneeStructure>(searchEngine.find(JourneeStructure.class, criteriaJS));

			if (liste_JourneeStructure != null && liste_JourneeStructure.size() == 0) {

				logger.info("Journée Structure non existante pour la structure " + codeStrcBNA);

			} else if (liste_JourneeStructure != null && liste_JourneeStructure.size() > 0) {

				/*************** Rechercher existance Journee Structure Batch *********/

				ICriteria criteriaJSB = searchEngine.createCriteria();
				IExpression expresJSB = searchEngine.createExpression();

				criteriaJSB.add(expresJSB.eq("journeeStructureBatchId.codStrcStrc", codeStrcBNA));
				criteriaJSB.add(expresJSB.eq("journeeStructureBatchId.datJrnJrn", dateComptable));
				criteriaJSB.add(
						expresJSB.eq("journeeStructureBatchId.codBatBmet", Constants.COD_BATCH_PRELEVEMENT_AECHEANCE));

				Set<JourneeStructureBatch> liste_JourneeStructureBatch =
						new HashSet<JourneeStructureBatch>(searchEngine.find(JourneeStructureBatch.class, criteriaJSB));

				if (liste_JourneeStructureBatch.size() > 0) {
					logger.info("Journée Structure Batch Prelevement existe pour la structure " + codeStrcBNA);

				} else {

					/********** Creation d'une Journee Structure Batch ***************/

					JourneeStructureBatch journeeStructureBatch = new JourneeStructureBatch();
					JourneeStructureBatchId journeeStructureBatchId = new JourneeStructureBatchId();
					journeeStructureBatchId.setCodBatBmet(Constants.COD_BATCH_PRELEVEMENT_AECHEANCE);
					journeeStructureBatchId.setCodStrcStrc(codeStrcBNA);
					journeeStructureBatchId.setDatJrnJrn(dateComptable);
					journeeStructureBatch.setJourneeStructureBatchId(journeeStructureBatchId);
					journeeStructureBatch.setCodStatJsb(Long.valueOf(0));
					crudService.create(journeeStructureBatch);

					structureException.setCodStrcStrc(codeStrcBNA);
					// *********** Recherche Liste Global_Prelevements du jour ********************//

					long mntTotauxPrl = 0;
					long nbrTotauxPrl = 0;
					long numeroLOT = 0;
					List listeSommeTotBySTRC = prelevementDAO.getSommePrelevements(dateComptable, codeAgenceBCT);

					ListOrderedMap ListSommeTotBySTRC = null;

					if (listeSommeTotBySTRC.size() > 0) {
						for (Iterator it1 = listeSommeTotBySTRC.iterator(); it1.hasNext();) {
							ListSommeTotBySTRC = (ListOrderedMap) it1.next();

							if ((ListSommeTotBySTRC.getValue(0)).toString() != null) {
								mntTotauxPrl = Long.valueOf(ListSommeTotBySTRC.getValue(0) + "");
							}
							if ((ListSommeTotBySTRC.getValue(1)).toString() != null) {
								nbrTotauxPrl = Long.valueOf(ListSommeTotBySTRC.getValue(1) + "");
							}

							if ((ListSommeTotBySTRC.getValue(2)).toString() != null) {
								numeroLOT = Long.valueOf(ListSommeTotBySTRC.getValue(2) + "");
							}

						}

						// ************** Reference Inter siege *********//
						GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
						String codRefInter = generateReferenceInterSiege.getRISWithUpdate(Constants.COD_DIR_TRESORERIE,
								dateComptable);

						String referenceInterSiege = codRefInter.substring(0, 6) + "2PR";

						// ***********************CRO 781 ****************************//
						Operation operation781 = new Operation();
						operation781.setCodOperOper(Constants.COD_OPER_ENVOI_PRELEVEMENT_COMPENSATION);
						PrelevementVo prelevementVoCro781 = new PrelevementVo();
						prelevementVoCro781.setMNT_GLB_RCP_PRE_AGE(mntTotauxPrl);
						prelevementVoCro781.setNBR_GLB_RCP_PRE_AGE(nbrTotauxPrl);
						prelevementVoCro781.setCodeStructureBNA(codeStrcBNA);
						prelevementVoCro781.setProduit(produit);
						prelevementVoCro781.setOperation(operation781);
						prelevementVoCro781.setDateComptable(dateComptable);
						prelevementVoCro781.setNumeroLot(numeroLOT);
						prelevementVoCro781.setReferenceInterSiege(referenceInterSiege);
						CreationCROEnvoiPrelevementsAgenceTrt creationCROEnvoiPrelevementsAgenceTrt =
								new CreationCROEnvoiPrelevementsAgenceTrt();
						// prelevementVoCro781 =
						// (PrelevementVo) creationCROEnvoiPrelevementsAgenceTrt.exec(prelevementVoCro781);

						System.out.println("Agence = " + codeStrcBNA + "  --> BCT = " + codeAgenceBCT);
						// ********************* CRO 816 ******************************//
						operation.setCodOperOper(Constants.COD_OPER_RECEP_PRELEV_RECU);
						PrelevementVo prelevementVoCro816 = new PrelevementVo();
						prelevementVoCro816.setMNT_GLB_RCP_PRE_AGE(mntTotauxPrl);
						prelevementVoCro816.setNBR_GLB_RCP_PRE_AGE(nbrTotauxPrl);
						prelevementVoCro816.setCodeStructureBNA(codeStrcBNA);
						prelevementVoCro816.setProduit(produit);
						prelevementVoCro816.setOperation(operation);
						prelevementVoCro816.setDateComptable(dateComptable);
						prelevementVoCro816.setNumeroLot(numeroLOT);
						prelevementVoCro816.setReferenceInterSiege(referenceInterSiege);
						CreationCROReceptionPrelevementsRecusTrt creationCROReceptionPrelevementsRecusTrt =
								new CreationCROReceptionPrelevementsRecusTrt();
						prelevementVoCro816 =
								(PrelevementVo) creationCROReceptionPrelevementsRecusTrt.exec(prelevementVoCro816);
					}

					// *********** Recherche Liste Details_Prelevements du jour ********************//
					ICriteria criteriaPrel = searchEngine.createCriteria();
					IExpression expresPrel = searchEngine.createExpression();

					criteriaPrel.add(expresPrel.eq("codValPrl", Constants.COD_ENREGISTREMENT_PRELEVEMENT));
					criteriaPrel.add(expresPrel.eq("codEtatPrl", Constants.COD_ETAT_PRELEVEMENT_ATTENTE));
					criteriaPrel.add(expresPrel.eq("codAgePrl", lpadS(codeAgenceBCT.toString(), "0", 3)));

					Set<DetailsPrelevements> liste_DetailsPrelevements = new HashSet<DetailsPrelevements>(
							searchEngine.find(DetailsPrelevements.class, criteriaPrel));
					int compteur = 0;
					if (liste_DetailsPrelevements.size() > 0) {

						long nbrePrelevementsValides = 0;
						long nbrePrelevementsRejetes = 0;
						long montPrelevementsValides = 0;
						long montPrelevementsRejetes = 0;

						for (DetailsPrelevements detailsPrelevements : liste_DetailsPrelevements) {

							// /************ verification RIB tireur**************//
							String ribTireur = detailsPrelevements.getDetailsPrelevementsId().getRibTirPrl();

							boolean trouveRejet = false;
							MotifRejetPrelev motifRejetPrelev = new MotifRejetPrelev();

							ContratCpt contratCpt = new ContratCpt();
							ContratCptId contratCptId = new ContratCptId();

							String petitRib = ribTireur.substring(0, 18);
							String cleRib = ribTireur.substring(18, 20);
							String codBanqueBCT = ribTireur.substring(0, 2);
							String codAgenceBCT = ribTireur.substring(2, 5);

							VirementVo virementVoRib = new VirementVo();
							// /virementVoRib.setStrCle(cleRib);
							virementVoRib.setStrCle(null);
							virementVoRib.setStrCodAgenceBanque(codAgenceBCT);
							virementVoRib.setStrCodbanque(codBanqueBCT);
							virementVoRib.setStrPetitRib(petitRib);

							VerifierRibTrt verifierRibTrt = new VerifierRibTrt();
							virementVoRib = (VirementVo) verifierRibTrt.exec(virementVoRib);

							if (virementVoRib.isVerifier() == false) {

								motifRejetPrelev.setCodMotrMrpr(Constants.COD_REJET_VALEUR_MAL_ACHEMINEE);
								logger.info("Rib Tireur Erronné");
								trouveRejet = true;

							} else if (virementVoRib.isVerifier() == true) {

								// /************ verification compte tireur**************//

								if (ribTireur.substring(0, 2).equals("03")) {

									contratCptId.setCodStrcStrc(Long.valueOf(ribTireur.substring(5, 8)));
									contratCptId.setCodPrdPrd(Long.valueOf(ribTireur.substring(8, 12)));
									contratCptId.setNumCcptCcpt(Long.valueOf(ribTireur.substring(12, 18)));

									contratCpt.setContratCptId(contratCptId);

									prelevementVo = verifierComptePrelevement(contratCpt);
									boolean etatRibCorrecte = false;

									if (ribTireur.equals(prelevementVo.getRibCalculer())) {
										etatRibCorrecte = true;
									} else {
										etatRibCorrecte = false;
									}

									contratCpt = prelevementVo.getContratCpt();
									if (prelevementVo.isEtatComptePrelevement() == true && etatRibCorrecte == true) {
										/*********************** Compte Valide *******************/

										/***** Verification Contrat Domiciliation *****/
										List<String> listeEtatCDOM = new ArrayList<String>();
										listeEtatCDOM.add(Constants.COD_ETAT_DOMICILIATION_VALIDE);
										listeEtatCDOM.add(Constants.COD_ETAT_DOMICILIATION_RESILIE);

										ICriteria criteriaCD = searchEngine.createCriteria();
										IExpression expresCD = searchEngine.createExpression();
										criteriaCD.add(expresCD.eq("contratCpt.contratCptId.codStrcStrc",
												contratCpt.getContratCptId().getCodStrcStrc()));
										criteriaCD.add(expresCD.eq("contratCpt.contratCptId.codPrdPrd",
												contratCpt.getContratCptId().getCodPrdPrd()));
										criteriaCD.add(expresCD.eq("contratCpt.contratCptId.numCcptCcpt",
												contratCpt.getContratCptId().getNumCcptCcpt()));
										criteriaCD.add(expresCD.eq("emetteur.codEmtrEmtr",
												detailsPrelevements.getEmetteur().getCodEmtrEmtr()));
										criteriaCD.add(expresCD.eq("numRefCdom", detailsPrelevements.getNumRefDom()));
										criteriaCD.add(expresCD.in("codEtatCdom", listeEtatCDOM));

										List<ContratDomiciliations> liste_ContratDomiciliations =
												new ArrayList<ContratDomiciliations>(
														searchEngine.find(ContratDomiciliations.class, criteriaCD));

										/**** Pas de Contrat Domiciliation ********/

										if (liste_ContratDomiciliations.size() <= 0) {
											motifRejetPrelev.setCodMotrMrpr(Constants.COD_REJET_ABSENCE_CON_DOMI);
											trouveRejet = true;

										} else {

											/**** Contrat Domiciliation existe ********/

											ContratDomiciliations contratDomiciliations = new ContratDomiciliations();
											contratDomiciliations = liste_ContratDomiciliations.get(0);
											if (contratDomiciliations.getCodEtatCdom()
													.equals(Constants.COD_ETAT_DOMICILIATION_RESILIE)) {

												if (detailsPrelevements.getDatEchPrl()
														.compareTo(contratDomiciliations.getDatResCdom()) > 0) {

													motifRejetPrelev
															.setCodMotrMrpr(Constants.COD_REJET_ABSENCE_CON_DOMI);
													trouveRejet = true;
												}

											} else if ((contratDomiciliations.getCodEtatCdom().equals(
													Constants.COD_ETAT_DOMICILIATION_RESILIE) && trouveRejet == false)
													|| (contratDomiciliations.getCodEtatCdom()
															.equals(Constants.COD_ETAT_DOMICILIATION_VALIDE))) {

												/***** Verification Opposition *****/
												ICriteria criteriaOpp = searchEngine.createCriteria();
												IExpression expresOpp = searchEngine.createExpression();
												criteriaOpp.add(expresOpp.eq("contratCpt.contratCptId.codStrcStrc",
														contratCpt.getContratCptId().getCodStrcStrc()));
												criteriaOpp.add(expresOpp.eq("contratCpt.contratCptId.codPrdPrd",
														contratCpt.getContratCptId().getCodPrdPrd()));
												criteriaOpp.add(expresOpp.eq("contratCpt.contratCptId.numCcptCcpt",
														contratCpt.getContratCptId().getNumCcptCcpt()));
												criteriaOpp.add(expresOpp.eq("emetteur.codEmtrEmtr",
														detailsPrelevements.getEmetteur().getCodEmtrEmtr()));
												criteriaOpp.add(expresOpp.eq("moisEchOprl", Long
														.valueOf(detailsPrelevements.getDatEchPrl().getMonth() + 1)));
												criteriaOpp.add(expresOpp.eq("anneEchOprl", Long.valueOf(
														formaterAnnee.format(detailsPrelevements.getDatEchPrl()))));
												criteriaOpp.add(expresOpp.eq("numRefDomOprl",
														detailsPrelevements.getNumRefDom()));
												criteriaOpp.add(expresOpp.eq("codEtatOprl",
														Constants.COD_ETAT_OPPOSITION_PRELEVEMENT));

												Set<OppositionPrelevements> liste_OppositionPrelevements =
														new HashSet<OppositionPrelevements>(searchEngine
																.find(OppositionPrelevements.class, criteriaOpp));

												if (liste_OppositionPrelevements.size() > 0) {
													motifRejetPrelev
															.setCodMotrMrpr(Constants.COD_REJET_OPPO_AUTRES_MOTIF);
													trouveRejet = true;

												} else {

													/***** Prelevement deja régle *****/
													List<Long> listOper = new ArrayList<Long>();
													listOper.add(Constants.COD_OPER_REGLEMENT_PRELEVEMENT);
													// listOper.add(Constants.COD_OPER_REJET_PRELEVEMENT);
													ICriteria criteriaPRE = searchEngine.createCriteria();
													IExpression epressionPER = searchEngine.createExpression();
													criteriaPRE.add(epressionPER.eq("ribTirPrl", detailsPrelevements
															.getDetailsPrelevementsId().getRibTirPrl()));
													criteriaPRE.add(epressionPER.eq("ribBenPrl", detailsPrelevements
															.getDetailsPrelevementsId().getRibBenPrl()));
													criteriaPRE.add(epressionPER.eq("emetteur.codEmtrEmtr",
															detailsPrelevements.getEmetteur().getCodEmtrEmtr()));
													criteriaPRE.add(epressionPER.eq("datEchPrl",
															detailsPrelevements.getDatEchPrl()));
													criteriaPRE.add(epressionPER.eq("numRefDom",
															detailsPrelevements.getNumRefDom()));
													criteriaPRE.add(epressionPER.eq("mntPrlPrl", detailsPrelevements
															.getDetailsPrelevementsId().getMntPrlPrl()));
													criteriaPRE.add(epressionPER.lt("datOpePrl", detailsPrelevements
															.getDetailsPrelevementsId().getDatOpePrl()));
													criteriaPRE.add(epressionPER.in("operation.codOperOper", listOper));

													Set<MvtPrelevements> liste_MvtPrelevements =
															new HashSet<MvtPrelevements>(searchEngine
																	.find(MvtPrelevements.class, criteriaPRE));

													if (liste_MvtPrelevements != null
															&& liste_MvtPrelevements.size() > 0) {

														for (MvtPrelevements mvtPrelevements : liste_MvtPrelevements) {

															if (mvtPrelevements.getOperation().getCodOperOper()
																	.longValue() == Constants.COD_OPER_REGLEMENT_PRELEVEMENT
																			.longValue()) {

																motifRejetPrelev.setCodMotrMrpr(
																		Constants.COD_REJET_PREL_DEJA_REGLE);
																trouveRejet = true;

															}
														}

													}
													/******************* Fin Test Prelevement deja reglé *******/
												}
												/******************* Fin Test Opposition ******/

											}
										}
										/******************* Fin Test Domicilation existe ******/

										if (trouveRejet == false) {

											/***** Verification provision *****/

											long montantProvision = contratCpt.getProvision(dateComptable);
											long montantPrelevement =
													detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl();

											// *********** Verification Compte vert ************//
											if (contratCpt.getContratCptId().getCodPrdPrd().equals(Long.valueOf(101))
													&& contratCpt.getBoolCverCcpt() != null
													&& contratCpt.getBoolCverCcpt().equals(Long.valueOf(1))
													&& (montantPrelevement > montantProvision)) {

												VirementVo virementVo = new VirementVo();
												virementVo.setMONT_VIR(montantPrelevement);
												virementVo.setContratCpt(contratCpt);

												VerifierProvisionCompteVertTrt verifierProvisionCompteVertTrt =
														new VerifierProvisionCompteVertTrt();

												virementVo =
														(VirementVo) verifierProvisionCompteVertTrt.exec(virementVo);

												ContratCpt contratCptCompteVert = virementVo.getContratCptCompteVert();
												// -- True : Exist Provision ---- False : Non Provision
												// --//

												if (virementVo.isBoolProvisionCompteVert() == true) {

													// / ------ Execution A partir Compte Vert 165 pour
													// Alimenter Compte 101

													long mntAlimentation = new Long(montantPrelevement
															+ Constants.SOLDE_MIN_COMPTE_DAV.longValue()
															- contratCpt.getMontSoldCcpt());

													VirementVo virementVoAlimentationCompteDepot = new VirementVo();
													virementVoAlimentationCompteDepot
															.setDateComptableAgence(dateComptable);
													virementVoAlimentationCompteDepot
															.setContratCptCompteVert(contratCptCompteVert);
													virementVoAlimentationCompteDepot
															.setMntAlimentationCompteDepot(mntAlimentation);
													virementVoAlimentationCompteDepot
															.setContratCptCompteDepot(contratCpt);
													Structure structure = new Structure();
													structure.setCodStrcStrc(codeStrcBNA);
													virementVoAlimentationCompteDepot.setStructure(structure);
													AlimentationCompteDepotTrt alimentationCompteDepotTrt =
															new AlimentationCompteDepotTrt();
													virementVoAlimentationCompteDepot =
															(VirementVo) alimentationCompteDepotTrt
																	.exec(virementVoAlimentationCompteDepot);

												} else {

													motifRejetPrelev.setCodMotrMrpr(Constants.COD_REJET_ABS_PROVISION);
													trouveRejet = true;
												}

											} else {

												if (montantProvision <= 0) {
													motifRejetPrelev.setCodMotrMrpr(Constants.COD_REJET_ABS_PROVISION);
													trouveRejet = true;
												} else if (montantPrelevement > montantProvision) {
													motifRejetPrelev
															.setCodMotrMrpr(Constants.COD_REJET_INSUFF_PROVISION);
													trouveRejet = true;

												}
											}
											/******************* Fin Test Provision *******/

										}
										/******* FIN TEST TROUVE ==FALSE ***********/

									} else {

										/*********** Compte Invalide **************/
										if (etatRibCorrecte == false) {
											motifRejetPrelev.setCodMotrMrpr(Constants.COD_REJET_VALEUR_MAL_ACHEMINEE);
											logger.info("Rib Tireur Erronné");
											trouveRejet = true;
										} else {
											motifRejetPrelev.setCodMotrMrpr(prelevementVo.getCodeRejet());
											trouveRejet = true;
										}
									}
								}
							}
							if (trouveRejet == true) {

								/***** Prelevement deja rejeté *****/
								List<Long> listOper = new ArrayList<Long>();

								listOper.add(Constants.COD_OPER_REJET_PRELEVEMENT);
								ICriteria criteriaPRE = searchEngine.createCriteria();
								IExpression epressionPER = searchEngine.createExpression();
								criteriaPRE.add(epressionPER.eq("ribTirPrl",
										detailsPrelevements.getDetailsPrelevementsId().getRibTirPrl()));
								criteriaPRE.add(epressionPER.eq("ribBenPrl",
										detailsPrelevements.getDetailsPrelevementsId().getRibBenPrl()));
								criteriaPRE.add(epressionPER.eq("emetteur.codEmtrEmtr",
										detailsPrelevements.getEmetteur().getCodEmtrEmtr()));
								criteriaPRE.add(epressionPER.eq("datEchPrl", detailsPrelevements.getDatEchPrl()));
								criteriaPRE.add(epressionPER.eq("numRefDom", detailsPrelevements.getNumRefDom()));
								criteriaPRE.add(epressionPER.eq("mntPrlPrl",
										detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl()));
								criteriaPRE.add(epressionPER.lt("datOpePrl",
										detailsPrelevements.getDetailsPrelevementsId().getDatOpePrl()));
								criteriaPRE.add(epressionPER.in("operation.codOperOper", listOper));

								Set<MvtPrelevements> liste_MvtPrelevements = new HashSet<MvtPrelevements>(
										searchEngine.find(MvtPrelevements.class, criteriaPRE));

								if (liste_MvtPrelevements != null && liste_MvtPrelevements.size() > 0) {

									for (MvtPrelevements mvtPrelevements : liste_MvtPrelevements) {

										if (motifRejetPrelev.getCodMotrMrpr()
												.equals(mvtPrelevements.getMotifRejetPrelev().getCodMotrMrpr())) {

											motifRejetPrelev.setCodMotrMrpr(Constants.COD_REJET_PREL_DEJA_REJETE);

										}

									}

								}

								nbrePrelevementsRejetes++;
								montPrelevementsRejetes +=
										detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl();
								/*********** Update Etat Prelevement **************/
								detailsPrelevements.setCodEtatPrl(Constants.COD_ETAT_PRELEVEMENT_REJETE);
								detailsPrelevements.setMotifRejetPrelev(motifRejetPrelev);
								crudService.update(detailsPrelevements);

								/*********** Creation Trace dans Mvt_Prelevements **************/
								long numSeqMprl = prelevementDAO.getSequenceMvtPrelevements();

								MvtPrelevements mvtPrelevements = new MvtPrelevements();
								Operation operationRejet = new Operation();
								operationRejet.setCodOperOper(Constants.COD_OPER_REJET_PRELEVEMENT);
								Structure structure = new Structure();
								structure.setCodStrcStrc(codeStrcBNA);

								mvtPrelevements.setNumSeqMprl(numSeqMprl);
								mvtPrelevements.setDatOperMprl(formaterDate.parse(formaterDate.format(new Date())));
								mvtPrelevements
										.setDatOpePrl(detailsPrelevements.getDetailsPrelevementsId().getDatOpePrl());
								mvtPrelevements
										.setRibTirPrl(detailsPrelevements.getDetailsPrelevementsId().getRibTirPrl());
								mvtPrelevements
										.setRibBenPrl(detailsPrelevements.getDetailsPrelevementsId().getRibBenPrl());
								mvtPrelevements
										.setMntPrlPrl(detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl());
								mvtPrelevements
										.setNumLotPrl(detailsPrelevements.getDetailsPrelevementsId().getNumLotPrl());
								mvtPrelevements.setNumMatrUser("9999");
								mvtPrelevements.setOperation(operationRejet);
								mvtPrelevements.setMotifRejetPrelev(motifRejetPrelev);
								if (structure.getCodStrcStrc() != null && structure.getCodStrcStrc().longValue() != 0) {
									mvtPrelevements.setStructure(structure);
								} else {
									structure.setCodStrcStrc(codeStrcBNA);
									mvtPrelevements.setStructure(structure);
								}
								mvtPrelevements.setEmetteur(detailsPrelevements.getEmetteur());
								mvtPrelevements.setNumRefDom(detailsPrelevements.getNumRefDom());
								mvtPrelevements
										.setNumPrlPrl(detailsPrelevements.getDetailsPrelevementsId().getNumPrlPrl());
								if (contratCpt != null && contratCpt.getContratCptId() != null
										&& contratCpt.getCodEtatCcpt() != null && motifRejetPrelev.getCodMotrMrpr()
												.longValue() != Constants.COD_REJET_VALEUR_MAL_ACHEMINEE.longValue()) {
									mvtPrelevements.setContratCpt(contratCpt);
								}

								mvtPrelevements.setLibPrlPrl(detailsPrelevements.getLibPrlPrl());
								mvtPrelevements.setDatEchPrl(detailsPrelevements.getDatEchPrl());

								/************ Creation CRO Rejet prelevement *****************/

								PrelevementVo prelevementVoRejetPrelev = new PrelevementVo();
								prelevementVoRejetPrelev.setOperation(operationRejet);
								prelevementVoRejetPrelev.setProduit(produit);
								prelevementVoRejetPrelev.setDetailsPrelevements(detailsPrelevements);
								prelevementVoRejetPrelev.setContratCpt(contratCpt);
								prelevementVoRejetPrelev.setDateComptable(dateComptable);
								prelevementVoRejetPrelev.setCodeRejet(motifRejetPrelev.getCodMotrMrpr());
								prelevementVoRejetPrelev.setMvtPrelevements(mvtPrelevements);
								prelevementVoRejetPrelev.setCodeStructureReceptrice(codeStrcBNA);
								prelevementVoRejetPrelev.setCodeRejet(motifRejetPrelev.getCodMotrMrpr());

								CreationCRORejetPrelevementsRecusTrt creationCRORejetPrelevementsRecusTrt =
										new CreationCRORejetPrelevementsRecusTrt();
								prelevementVoRejetPrelev = (PrelevementVo) creationCRORejetPrelevementsRecusTrt
										.exec(prelevementVoRejetPrelev);
								/********************* suite mvtPrelevement ******************/

								try {
									if (prelevementVo.getValueDateRecu() != null
											&& prelevementVo.getValueDateRecu().length() > 0) {
										mvtPrelevements
												.setDatValMprl(formaterDate.parse(prelevementVo.getValueDateRecu()));

									}
								} catch (ParseException e) {

								}

								try {
									crudService.create(mvtPrelevements);
								} catch (DataIntegrityViolationException e) {
									logger.error("DataIntegrityViolationException : " + e.getMessage());
								} catch (ConstraintViolationException e) {
									logger.error("ConstraintViolationException : " + e.getMessage());
								}

							} else {

								nbrePrelevementsValides++;
								montPrelevementsValides +=
										detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl();
								// ******Cas d'un prelevement non rejeté ***********//
								Operation operationReglement = new Operation();
								operationReglement.setCodOperOper(Constants.COD_OPER_REGLEMENT_PRELEVEMENT);
								PrelevementVo prelevementVoReglPrelev = new PrelevementVo();
								prelevementVoReglPrelev.setOperation(operationReglement);
								prelevementVoReglPrelev.setProduit(produit);
								prelevementVoReglPrelev.setDetailsPrelevements(detailsPrelevements);
								prelevementVoReglPrelev.setContratCpt(contratCpt);
								prelevementVoReglPrelev
										.setCodeStructureReceptrice(contratCpt.getContratCptId().getCodStrcStrc());

								CreationCROPositionPrelevementsRecusTrt creationCROPositionPrelevementsRecusTrt =
										new CreationCROPositionPrelevementsRecusTrt();
								prelevementVoReglPrelev = (PrelevementVo) creationCROPositionPrelevementsRecusTrt
										.exec(prelevementVoReglPrelev);

								/*********** Creation Trace dans Mvt_Prelevements **************/

								MvtPrelevements mvtPrelevements = new MvtPrelevements();

								long numSeqMprl = prelevementDAO.getSequenceMvtPrelevements();
								Structure structure = new Structure();
								structure.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc());
								mvtPrelevements.setNumSeqMprl(numSeqMprl);
								mvtPrelevements.setDatOperMprl(formaterDate.parse(formaterDate.format(new Date())));
								mvtPrelevements
										.setDatOpePrl(detailsPrelevements.getDetailsPrelevementsId().getDatOpePrl());
								mvtPrelevements
										.setRibTirPrl(detailsPrelevements.getDetailsPrelevementsId().getRibTirPrl());
								mvtPrelevements
										.setRibBenPrl(detailsPrelevements.getDetailsPrelevementsId().getRibBenPrl());
								mvtPrelevements
										.setMntPrlPrl(detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl());
								mvtPrelevements
										.setNumLotPrl(detailsPrelevements.getDetailsPrelevementsId().getNumLotPrl());
								mvtPrelevements.setNumMatrUser("9999");
								mvtPrelevements.setOperation(operationReglement);
								mvtPrelevements.setOperationMoyPay(prelevementVoReglPrelev.getOperationMoyPay());
								if (structure.getCodStrcStrc() != null && structure.getCodStrcStrc().longValue() != 0) {
									mvtPrelevements.setStructure(structure);
								} else {
									structure.setCodStrcStrc(codeStrcBNA);
									mvtPrelevements.setStructure(structure);
								}
								mvtPrelevements.setEmetteur(detailsPrelevements.getEmetteur());
								mvtPrelevements.setNumRefDom(detailsPrelevements.getNumRefDom());
								mvtPrelevements
										.setNumPrlPrl(detailsPrelevements.getDetailsPrelevementsId().getNumPrlPrl());
								mvtPrelevements.setContratCpt(contratCpt);

								try {
									if (prelevementVoReglPrelev.getValueDateRecu() != null
											&& prelevementVoReglPrelev.getValueDateRecu().length() > 0) {
										mvtPrelevements.setDatValMprl(
												formaterDate.parse(prelevementVoReglPrelev.getValueDateRecu()));
									}
								} catch (ParseException e) {
									e.printStackTrace();
								}

								mvtPrelevements.setLibPrlPrl(detailsPrelevements.getLibPrlPrl());
								mvtPrelevements.setDatEchPrl(detailsPrelevements.getDatEchPrl());
								logger.info("Strcuture : " + structure.getCodStrcStrc()
										+ " -----> mvtPrelevements structure  : "
										+ mvtPrelevements.getStructure().getCodStrcStrc());
								try {
									crudService.create(mvtPrelevements);
								} catch (DataIntegrityViolationException e) {
									logger.error("DataIntegrityViolationException : " + e.getMessage());
								} catch (ConstraintViolationException e) {
									logger.error("ConstraintViolationException : " + e.getMessage());
								}

								/*********** Update Etat Prelevement **************/
								detailsPrelevements.setCodEtatPrl(Constants.COD_ETAT_PRELEVEMENT_EXECUTE);
								crudService.update(detailsPrelevements);

							}

							// ***************** Incrementation Compteur **************//
							compteur++;
						}

						if (compteur == liste_DetailsPrelevements.size()) {

							logger.info("Tous les détails prélèvements de la struture sont exécutés avec succès");
						}
						/****************** Statistique ****************/
						String messageStatistique = "";

						messageStatistique = "Sucées de l’exécution : \n";

						if (liste_DetailsPrelevements.size() > 0) {
							messageStatistique +=
									"Nombre total des prélèvements = " + liste_DetailsPrelevements.size() + "  ; \n ";
						}
						if (nbrePrelevementsValides > 0) {
							messageStatistique +=
									"Nombre des prélèvements validés  = " + nbrePrelevementsValides + "  ; \n ";
							messageStatistique += "pour la somme de : " + montPrelevementsValides + "  ; \n ";

						}

						if (nbrePrelevementsRejetes > 0) {
							messageStatistique +=
									"Nombre des prélèvements rejetés  = " + nbrePrelevementsRejetes + "  ; \n ";
							messageStatistique += "pour la somme de : " + montPrelevementsRejetes + "  ; \n ";
						}

						Structure structure = new Structure();
						structure.setCodStrcStrc(journeeStructureBatch.getJourneeStructureBatchId().getCodStrcStrc());
						gestionStatistique(journeeStructureBatch.getJourneeStructureBatchId().getDatJrnJrn(), structure,
								messageStatistique);

						prelevementVo.setMsgEnregistrement(messageStatistique);
					}
					/****************** Fin Batch Journee ****************/

					journeeStructureBatch.setCodStatJsb(Long.valueOf(1));
					journeeStructureBatch.setDatCloJsb(formaterDate.parse(formaterDate.format(new Date())));
					crudService.update(journeeStructureBatch);
					prelevementVo.setEtatEnregistrementPrelevement(true);
				}

				/*********** Update Etat Global Prelevement **************/
			}

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GestionPrelevementsRecusTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GestionPrelevementsRecusTrt");
			logger.error("Exception : ", e);
			if (structureException.getCodStrcStrc() != null && structureException.getCodStrcStrc().longValue() != 0) {
				gestionException(dateComptable, structureException, e);
			} else {
				structureException.setCodStrcStrc(Long.valueOf(120));
				gestionException(dateComptable, structureException, e);
			}
			prelevementVo.setMsgEnregistrement(e.getMessage());
			throw new RuntimeException(e);

		}
		return prelevementVo;
	}

	public PrelevementVo verifierComptePrelevement(ContratCpt contratCpt) {
		PrelevementVo prelVo = new PrelevementVo();

		boolean exist = false; // / false : Contrat Invalde ; True Contrat valide
		boolean etatDecedes = false;
		boolean etatIndispProvision = false;
		String msg = "";
		this.setCroFlag(false);

		try {
			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");

			ContratCpt compteBase = (ContratCpt) searchEngine.get(ContratCpt.class, contratCpt.getContratCptId());

			if (compteBase != null && compteBase.getClient() != null) {

				ICriteria criteria2 = searchEngine.createCriteria();
				IExpression expression2 = searchEngine.createExpression();

				criteria2.add(expression2.eq("contratCpt.contratCptId.codPrdPrd",
						compteBase.getContratCptId().getCodPrdPrd()));
				criteria2.add(expression2.eq("contratCpt.contratCptId.codStrcStrc",
						compteBase.getContratCptId().getCodStrcStrc()));
				criteria2.add(expression2.eq("contratCpt.contratCptId.numCcptCcpt",
						compteBase.getContratCptId().getNumCcptCcpt()));
				criteria2.add(expression2.isNull("datFinDetc"));

				List<DetailEtatContrat> l2 = searchEngine.find(DetailEtatContrat.class, criteria2);

				if (l2 != null && l2.size() > 0) {
					DetailEtatContrat detailEtatContrat = (DetailEtatContrat) l2.get(0);

					if (detailEtatContrat.getMotifEtat().getMotifEtatId().getCodMotfMeta().longValue() == 3
							&& (detailEtatContrat.getMotifEtat().getMotifEtatId().getCodEtatEcon().equals("S")
									|| detailEtatContrat.getMotifEtat().getMotifEtatId().getCodEtatEcon()
											.equals("B"))) {
						etatDecedes = true;
					} else if ((detailEtatContrat.getMotifEtat().getMotifEtatId().getCodMotfMeta().longValue() == 5
							|| detailEtatContrat.getMotifEtat().getMotifEtatId().getCodMotfMeta().longValue() == 6
							|| detailEtatContrat.getMotifEtat().getMotifEtatId().getCodMotfMeta().longValue() == 4)
							&& detailEtatContrat.getMotifEtat().getMotifEtatId().getCodEtatEcon().equals("B")) {
						etatIndispProvision = true;
					}
				}

				// *********Calculer RIB*****************//

				PrimitiveVO primitiveVO = new PrimitiveVO();
				GetRibTrt getRibTrt = new GetRibTrt();
				primitiveVO = (PrimitiveVO) getRibTrt.exec(compteBase);
				String rib_tireur = primitiveVO.getVString();

				prelVo.setRibCalculer(rib_tireur);
				// **************************************//

				if (compteBase.getCodEtatCcpt().equals("V")) {
					boolean trouve = false;

					for (int i = 0; i < Constants.produitPrelevementsEligible.length; i++) {

						if (Constants.produitPrelevementsEligible[i].longValue() == compteBase.getContratCptId()
								.getCodPrdPrd().longValue()) {
							trouve = true;
						}
					}

					if (trouve == false) {
						exist = false;
						msg = " Contrat Cpt non éligible";
						prelVo.setCodeRejet(Constants.COD_REJET_OPERATION_NON_AUTORISEE);
					} else {
						exist = true;

					}

				} else if (etatDecedes == true) {
					exist = false;
					msg = "Titulaire du compte décédé";
					prelVo.setCodeRejet(Constants.COD_REJET_TITULAIRE_DECEDE);
				} else if (etatIndispProvision == true) {
					exist = false;
					msg = "Indisponibilité de provision";
					prelVo.setCodeRejet(Constants.COD_REJET_INDISPO_PROVISION);
				} else {
					exist = false;
					msg = " Contrat Cpt Invalide";
					prelVo.setCodeRejet(Constants.COD_REJET_COMPTE_CLOTURE);
				}

			} else {
				exist = false;
				msg = " Contrat Cpt Inexistant";
				prelVo.setCodeRejet(Constants.COD_REJET_VALEUR_MAL_ACHEMINEE);
			}
			prelVo.setContratCpt(compteBase);
			prelVo.setEtatComptePrelevement(exist);
			prelVo.setMsgEtatComptePrelevement(msg);
		} catch (Exception e) {
			logger.error("Exception : ", e);
		}
		return prelVo;
	}

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
		batchMetier.setCodBatBmet(Constants.COD_BATCH_PRELEVEMENT_AECHEANCE);
		batchStatPlacement.setBatchMetier(batchMetier);
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);
	}

	public static String lpadS(String valueToPad, String filler, int size) {
		StringBuilder builder = new StringBuilder();

		while (builder.length() + valueToPad.length() < size) {
			builder.append(filler);
		}
		builder.append(valueToPad);
		return builder.toString();
	}
}
