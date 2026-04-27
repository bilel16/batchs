package com.bna.smile.model.virement.traitement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceOperVirement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.ExonerationTvaDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.SuivFileTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class TraitementVirPonctuelMasseTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	VirementService virementService = (VirementService) context.getBean("iVirementService");
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	long montant_commision = 0;
	long montant_tva = 0;
	boolean etatClientTaxable = false;
	boolean etatExecutionVirement = false;
	long numLotVirement = 0;

	public TraitementVirPonctuelMasseTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		GlobalVirement globalVirementObj = (GlobalVirement) virementVo.getGlobalVirement();
		Date dateComptableAgence = new Date();
		dateComptableAgence = virementVo.getDateComptableAgence();
		Structure agence = virementVo.getStructure();
		this.setCroFlag(false);

		List<DetailVirement> listeDetailVirementsFinal = new ArrayList<DetailVirement>();
		String heureString = formaterHeure.format(new Date());

		TraceOperVirement traceOperVirement = new TraceOperVirement();
		try {
			ICriteria iCriteria = searchEngine.createCriteria();
			iCriteria.add(expression.idEq(virementVo.getGlobalVirement().getNumSeqGvir()));

			List<GlobalVirement> liste_GlobalVirements =
					new ArrayList<GlobalVirement>(searchEngine.find(GlobalVirement.class, iCriteria));

			globalVirementObj = liste_GlobalVirements.get(0);

			// / ------------- Set List Detail Virement ------------------ ///

			// itérateur
			Iterator<DetailVirement> iterateur = globalVirementObj.getDetailVirements().iterator();

			while (iterateur.hasNext()) {
				DetailVirement detailVirementObj = (DetailVirement) iterateur.next();

				if (detailVirementObj.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_ATTENTE.longValue()
						|| detailVirementObj.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN
								.longValue()
						|| detailVirementObj.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF
								.longValue()) {
					if (dateComptableAgence.compareTo(detailVirementObj.getDatEchDetv()) >= 0) {
						listeDetailVirementsFinal.add(detailVirementObj);
					}
				}
			}

			// / ---------------------------------------------------------- ///

			long MONT_SOLDE = 0;
			long MONT_AUT = 0;
			long MONT_DEBLC = 0;

			long MONT_TOTAL = 0;
			long MONT_VIR = 0;

			boolean etatMontantVirement = false;
			boolean etatExisteVirementSGMT = false;
			boolean etatEnvoiSGMT = virementVo.isEtatEnvoiSGMT();

			long mntAlimentation = 0;

			if (listeDetailVirementsFinal != null && listeDetailVirementsFinal.size() > 0) {

				// ********************** Existance Virement SGMT ******************//
				for (DetailVirement detailVirement : listeDetailVirementsFinal) {
					String codeBanque = detailVirement.getRibBenDetv().substring(0, 2);
					if (!codeBanque.equals("03")) {
						if (detailVirement.getMntDetvDetv().longValue() >= Constants.MONTANT_VIREMENT_SGMT.longValue()) {

							etatExisteVirementSGMT = true;

						}
					}
				}

				// / ------------- Verifier Approvision Solde Donneur D'ordre ------------- ////

				MONT_SOLDE = 0;
				MONT_AUT = 0;
				MONT_DEBLC = 0;

				MONT_TOTAL = 0;
				MONT_VIR = 0;

				if (globalVirementObj.getContratCpt().getMontSoldCcpt() == null) {
					MONT_SOLDE = 0;
				} else {
					MONT_SOLDE = globalVirementObj.getContratCpt().getMontSoldCcpt().longValue();
				}

				if (globalVirementObj.getContratCpt().getMontAutCcpt() == null) {
					MONT_AUT = 0;
				} else {
					long diff = -1;
					if (globalVirementObj.getContratCpt().getDatEautCcpt() != null)
						diff = dateComptableAgence.compareTo(globalVirementObj.getContratCpt().getDatEautCcpt());
					if (diff > 0) {
						MONT_AUT = 0;
					} else {

						MONT_AUT = globalVirementObj.getContratCpt().getMontAutCcpt().longValue();
					}
				}

				if (globalVirementObj.getContratCpt().getMontBlocCcpt() == null) {
					MONT_DEBLC = 0;
				} else {
					MONT_DEBLC = globalVirementObj.getContratCpt().getMontBlocCcpt().longValue();
				}

				MONT_TOTAL = MONT_SOLDE + MONT_AUT - MONT_DEBLC;

				if (globalVirementObj.getMntGvirGvir() == null) {
					MONT_VIR = 0;
				} else {
					MONT_VIR = globalVirementObj.getMntGvirGvir().longValue();
				}

				if (MONT_TOTAL < MONT_VIR) {// / Manque d'approvision

					if (globalVirementObj.getContratCpt().getContratCptId().getCodPrdPrd()
							.equals(Constants.COD_COMPTE_CHEQUE)
							&& globalVirementObj.getContratCpt().getBoolCverCcpt() != null
							&& globalVirementObj.getContratCpt().getBoolCverCcpt().longValue() == 1) {

						// / ------------------ Verfier si Exist Compte Vert 165 ---------------------
						// ///
						VirementVo virementVo4 = new VirementVo();
						virementVo4.setGlobalVirement(globalVirementObj);
						// virementVo4.setDetailVirement(detailVirementObj);
						virementVo4.setDateComptableAgence(dateComptableAgence);
						virementVo4.setMONT_TOTAL(MONT_TOTAL);
						virementVo4.setMONT_VIR(MONT_VIR);
						virementVo4.setContratCpt(globalVirementObj.getContratCpt());

						virementVo4 = (VirementVo) virementService.verifierProvisionCompteVertDoVir(virementVo4);
						ContratCpt contratCptCompteVert = virementVo4.getContratCptCompteVert();
						// / True : Exist Provision ---- False : Non Provision ----- ////
						// virementVo.setBoolProvisionCompteVert(boolProvisionCompteVert);
						if (virementVo4.isBoolProvisionCompteVert() == true) {

							// / ------ Execution A partir Compte Vert 165 pour Alimenter Compte 101

							mntAlimentation =
									new Long(MONT_VIR + Constants.SOLDE_MIN_COMPTE_DAV.longValue() - MONT_SOLDE);

							VirementVo virementVoAlimentationCompteDepot = new VirementVo();
							virementVoAlimentationCompteDepot.setDateComptableAgence(dateComptableAgence);
							virementVoAlimentationCompteDepot.setContratCptCompteVert(contratCptCompteVert);
							virementVoAlimentationCompteDepot.setMntAlimentationCompteDepot(mntAlimentation);
							virementVoAlimentationCompteDepot.setContratCptCompteDepot(globalVirementObj
									.getContratCpt());
							virementVoAlimentationCompteDepot.setGlobalVirement(globalVirementObj);
							virementVoAlimentationCompteDepot.setStructure(agence);
							virementVoAlimentationCompteDepot =
									(VirementVo) virementService
											.alimenterCompteDepot(virementVoAlimentationCompteDepot);

							etatMontantVirement = true;
						} else {

							// / ------------------ Non Exist Compte Vert 165
							// ----------------------------
							// ///
							VirementVo virementVo3 = new VirementVo();
							Operation operation = new Operation();
							if (globalVirementObj.getCodPrdPrd() != null
									&& globalVirementObj.getCodPrdPrd().equals(Constants.COD_PRODUIT_VIREMENT_PONCTUEL)) {
								operation.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PONCTUEL);
								virementVo3.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PONCTUEL);

							} else if (globalVirementObj.getCodPrdPrd() != null
									&& globalVirementObj.getCodPrdPrd().equals(Constants.COD_PRODUIT_VIREMENT_MASSE)) {
								operation.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_MASSE);
								virementVo3.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_MASSE);
							}

							virementVo3.setOperation(operation);
							virementVo3.setGlobalVirement(globalVirementObj);
							// virementVo3.setDetailVirement(detailVirementObj);
							virementVo3.setDateComptableAgence(dateComptableAgence);

							virementVo3 = (VirementVo) virementService.nonApprovisionDoVir(virementVo3);

							logger.info("\n  /***** VIREMENT TRAITER :  NON APPROVISION  ***/   \n");

							logger.info("\n  /***** NUM_GVIR = " + globalVirementObj.getNumSeqGvir() + "*****/ \n");

						}

					} else {

						// / ------------------ Produit Compte != 101 ----------------------------

						VirementVo virementVo3 = new VirementVo();
						Operation operation = new Operation();
						operation.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PONCTUEL);
						virementVo3.setGlobalVirement(globalVirementObj);
						// virementVo3.setDetailVirement(detailVirementObj);
						virementVo3.setDateComptableAgence(dateComptableAgence);
						virementVo3.setOperation(operation);
						virementVo3.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PONCTUEL);
						VirementService virementService3 = (VirementService) context.getBean("iVirementService");
						virementVo3 = (VirementVo) virementService3.nonApprovisionDoVir(virementVo3);

						logger.info("\n  /***** VIREMENT TRAITER :  NON APPROVISION  ***/   \n");
						logger.info("\n  /***** NUM_GVIR = " + globalVirementObj.getNumSeqGvir() + "***/   \n");

					}

				} else {

					etatMontantVirement = true;

				}

				if ((etatMontantVirement == true && etatExisteVirementSGMT == false)
						|| (etatMontantVirement == true && etatExisteVirementSGMT == true && etatEnvoiSGMT == true)) {

					// *********Caracteristique Taxable du client ********//

					ExonerationTvaDAO exonerationTvaDAO = new ExonerationTvaDAO();
					ParamRechercheOpposition param = new ParamRechercheOpposition();
					param.setTypPceDemd(globalVirementObj.getContratCpt().getClient().getPersonne().getTypePiece()
							.getCodTpceTpce());
					param.setNumPceDemd(globalVirementObj.getContratCpt().getClient().getPersonne().getNumPcePers());
					param.setDateDebutConsult(dateComptableAgence);

					PrimitiveVO res = (PrimitiveVO) exonerationTvaDAO.isClientExonereTVA(param);

					if (res.isVBool() == true) {
						etatClientTaxable = false;
					} else {
						etatClientTaxable = true;
					}

					// ********************** Traitemts des Details Virements *******************//

					if (globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_PONCTUEL
							.longValue()) {

						traceOperVirement.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PONCTUEL);
						traceOperVirement.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PONCTUEL);
						VirementService virementService4 = (VirementService) context.getBean("iVirementService");

						if (globalVirementObj.getDevise() != null
								&& globalVirementObj.getDevise().getCodDevDev() != null
								&& globalVirementObj.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR) == false) {

							/************ Virement Devise ***********/

							VirementVo virementDeviseVo = new VirementVo();
							virementDeviseVo.setGlobalVirement(globalVirementObj);
							virementDeviseVo.setDateComptableAgence(dateComptableAgence);
							virementDeviseVo.setEtatTaxableClient(etatClientTaxable);

							virementDeviseVo = (VirementVo) virementService4.executerVirementDevise(virementDeviseVo);

							etatExecutionVirement = virementDeviseVo.isStadeEnregistrement();

						} else {

							/************ Virement Dinars ***********/
							VirementVo virementVo4 = new VirementVo();
							virementVo4.setGlobalVirement(globalVirementObj);
							virementVo4.setDateComptableAgence(dateComptableAgence);
							virementVo4.setEtatTaxableClient(etatClientTaxable);

							virementVo4 = (VirementVo) virementService4.executionDoVirPonctuel(virementVo4);

							etatExecutionVirement = virementVo4.isStadeEnregistrement();
							if (etatExecutionVirement == true) {
								// **** Recupuration du fichier ****//

								try {
									if (virementVo4.getNumeroLot() != null && virementVo4.getNumeroLot().length() > 0) {
										numLotVirement = Long.valueOf(virementVo4.getNumeroLot());
									}
								} catch (NumberFormatException e) {
									numLotVirement = 0;
								}

								virementVo.getListeFile().addAll(virementVo4.getListeFile());

								virementVo.getListeFilesNames().addAll(virementVo4.getListeFilesNames());

								if (virementVo.getListeFile() != null && virementVo.getListeFile().size() > 0) {

									for (File file : virementVo.getListeFile()) {
										String codeStrcBCT =
												virementGlobalDAO.getCodeStructureBCT(Long.valueOf(globalVirementObj
														.getStructure().getCodStrcStrc()));
										// **** save dans SUIVI_FILE_TELECOMPENSATION ****//
										SuivFileTrt.ajouterFichier(file.getName(), codeStrcBCT, dateComptableAgence, 1,
												Constants.COD_ENREGISTREMENT_VIREMENT);

										// ********* Envoi FTP ******//
										boolean etatSendFile =
												Util.sendFileFTP(file.getAbsolutePath(),
														Configuration.getLocalPathSend() + file.getName());

										if (etatSendFile == true) {
											logger.info("Fichier : " + file.getName() + " envoyé avec succés ");

										} else {

											logger.info("Erreur d'envoie du fichier : " + file.getName());
										}
									}
								}
								if (virementVo.getListeFilesNames().size() > 0) {
									for (String fileName : virementVo.getListeFilesNames()) {
										System.out.println("fileName: " + fileName);
										ICriteria iCriteriaFile = searchEngine.createCriteria();
										iCriteriaFile.add(expression.eq("detailVirementId.numSeqGvir",
												globalVirementObj.getNumSeqGvir()));
										iCriteriaFile.add(expression.eq("refFichDetv", fileName));

										Set<DetailVirement> liste_Virements =
												new HashSet<DetailVirement>(searchEngine.find(DetailVirement.class,
														iCriteriaFile));

										System.out.println("liste_Virements: " + liste_Virements.size());
									}

								}
							}
						}

					} else if (globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_MASSE
							.longValue()) {

						traceOperVirement.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_MASSE);
						traceOperVirement.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_MASSE);

						Long nbreVirementMasseBNABNA =
								virementGlobalDAO.getNbreVirementMassesBNABNA(globalVirementObj.getNumSeqGvir(),
										globalVirementObj.getStructure().getCodStrcStrc());

						Long paramVirementBNABNA = virementGlobalDAO.getNbreMinVirementMasses();

						VirementVo virementVo5 = new VirementVo();
						virementVo5.setGlobalVirement(globalVirementObj);
						virementVo5.setDateComptableAgence(dateComptableAgence);
						virementVo5.setEtatTaxableClient(etatClientTaxable);
						VirementService virementService4 = (VirementService) context.getBean("iVirementService");
						logger.info(" nbreVirementMasseBNABNA  : " + nbreVirementMasseBNABNA
								+ " ---> paramVirementBNABNA : " + paramVirementBNABNA);
						if (nbreVirementMasseBNABNA.longValue() >= paramVirementBNABNA.longValue()) {
							logger.info("---- Virement Salaires ----");

							virementVo5 = (VirementVo) virementService4.executerDoVirMasseSalaire(virementVo5);

						} else {
							logger.info("---- Virement Masse Petit ----");
							virementVo5 = (VirementVo) virementService4.executionDoVirMasse(virementVo5);
						}

						etatExecutionVirement = virementVo5.isStadeEnregistrement();
						if (etatExecutionVirement == true) {
							// **** Recupuration du fichier ****//

							try {
								if (virementVo5.getNumeroLot() != null && virementVo5.getNumeroLot().length() > 0) {
									numLotVirement = Long.valueOf(virementVo5.getNumeroLot());
								}
							} catch (NumberFormatException e) {
								numLotVirement = 0;
							}

							virementVo.getListeFile().addAll(virementVo5.getListeFile());
							virementVo.getListeFilesNames().addAll(virementVo5.getListeFilesNames());

							if (virementVo.getListeFile() != null && virementVo.getListeFile().size() > 0) {

								for (File file : virementVo.getListeFile()) {
									String codeStrcBCT =
											virementGlobalDAO.getCodeStructureBCT(Long.valueOf(globalVirementObj
													.getStructure().getCodStrcStrc()));
									if (file.length() > 0) {

										// **** save dans SUIVI_FILE_TELECOMPENSATION ****//
										SuivFileTrt.ajouterFichier(file.getName(), codeStrcBCT, dateComptableAgence, 1,
												Constants.COD_ENREGISTREMENT_VIREMENT);

										// ********* Envoi FTP ******//
										boolean etatSendFile =
												Util.sendFileFTP(file.getAbsolutePath(),
														Configuration.getLocalPathSend() + file.getName());

										if (etatSendFile == true) {
											logger.info("Fichier : " + file.getName() + " envoyé avec succés ");

										} else {

											logger.info("Erreur d'envoie du fichier : " + file.getName());
										}
									}
								}
							}

							if (virementVo.getListeFilesNames().size() > 0) {
								for (String fileName : virementVo.getListeFilesNames()) {
									System.out.println("fileName: " + fileName);
									ICriteria iCriteriaFile = searchEngine.createCriteria();
									iCriteriaFile.add(expression.eq("detailVirementId.numSeqGvir",
											globalVirementObj.getNumSeqGvir()));
									iCriteriaFile.add(expression.eq("refFichDetv", fileName));

									Set<DetailVirement> liste_Virements =
											new HashSet<DetailVirement>(searchEngine.find(DetailVirement.class,
													iCriteriaFile));

									System.out.println("liste_Virements: " + liste_Virements.size());
								}

							}
						}
					}

					// / ------------------ Creation Trace Oper Virement ----------------- ///
					if (etatExecutionVirement == true) {
						Long numSeqTvir = new Long(0);
						numSeqTvir = virementGlobalDAO.getSequenceTraceOperVirement();
						traceOperVirement.setNumSeqTvir(numSeqTvir); // Num Seq Trace Vir
						traceOperVirement.setGlobalVirement(globalVirementObj);
						traceOperVirement.setNumMatrUser("9999");
						traceOperVirement.setDatOperTvir(dateComptableAgence);
						traceOperVirement.setTimeOperTvir(heureString);
						traceOperVirement.setStructure(globalVirementObj.getStructure());

						crudService.create(traceOperVirement);

						// / ------------------ Update Global_Virement ----------------- ///

						if (numLotVirement != 0) {
							globalVirementObj.setNumLotGvir(numLotVirement);
						}

						globalVirementObj.setEtatGvirGvir(Constants.COD_ETAT_VIREMENT_EXECUTER);
					}
				}

				else if (etatMontantVirement == true && etatExisteVirementSGMT == true && etatEnvoiSGMT == false) {

					for (DetailVirement detailVirement2 : listeDetailVirementsFinal) {

						if (detailVirement2.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_ATTENTE) {

							detailVirement2.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
							detailVirement2.setMotifRejetDetv("La journée SGMT n’est pas encore ouverte ou elle est clôturée ");
							detailVirement2.setDatExecDetv(dateComptableAgence);
							detailVirement2.setDatExecDetv(dateComptableAgence);
							crudService.update(detailVirement2);
						}
					}

					globalVirementObj.setEtatGvirGvir(Constants.COD_ETAT_VIREMENT_REJETER);

				}
			}
			crudService.update(globalVirementObj);

			// / ---------------------------------------------------------- ///

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans TraitementVirPonctuelMasseTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("TraitementVirPonctuelMasseTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau TraitementVirPonctuelMasseTrt : ", e);
			virementVo.setMessageValidation("Probléme dans TraitementVirPonctuelMasseTrt");
			gestionException(dateComptableAgence, agence, e);
			throw new RuntimeException();

		}
		return (virementVo);

	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
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
}