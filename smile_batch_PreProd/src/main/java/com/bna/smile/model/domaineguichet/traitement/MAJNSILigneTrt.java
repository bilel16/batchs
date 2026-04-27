package com.bna.smile.model.domaineguichet.traitement;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.BatchRejetVirNSI;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratGod;
import com.bna.commun.model.ContratGodId;
import com.bna.commun.model.DepPersonnel;
import com.bna.commun.model.Devise;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.service.CURService;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.compteGod.model.BlocageGodVo;
import com.bna.smile.model.compteGod.traitement.UpdateSoldeGodTrt;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratCptByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetContratEtatCmd;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineguichet.dao.GuichetDAO;
import com.bna.smile.model.domaineguichet.model.AgencesMAJNSIVo;
import com.bna.smile.model.domaineguichet.model.Const;
import com.bna.smile.model.domaineguichet.model.MAJNSIVo;
import com.bna.smile.model.domaineguichet.model.MvtDevise;
import com.bna.smile.model.domaineguichet.service.GuichetService;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class MAJNSILigneTrt extends Traitement {

	public MAJNSILigneTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	// à ne pas laisser en variable global
	ICriteria criteria = searchEngine.createCriteria();
	ICriteria criteriaAvanc = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();
	List<AgencesMAJNSIVo> listAgencesMAJNSI = null;
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	GuichetDAO guichetDao;
	BufferedWriter bufWriter;
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

	public IValueObject perform(IValueObject vo) {
		MAJNSIVo mAJNSIVo = (MAJNSIVo) vo;
		String ligne = "";
		boolean is135 = false;
		boolean isGOD = false;
		Date dateCpt = new Date();
		Date dateSys = new Date();
		Date dateVal = new Date();
		Structure strc = new Structure();
		strc.setCodStrcStrc(949L);
		String codePrdGOD = "";
		try {
			bufWriter = mAJNSIVo.getBufWriter();
			this.setVerifDomaine(false);
			this.setCroFlag(false);
			ligne = mAJNSIVo.getLigne();
			listAgencesMAJNSI = mAJNSIVo.getListAgencesMAJNSI();
			dateCpt = DateHandler.strToDate(formatDate.format(new Date()));
			dateSys = DateHandler.strToDate(formatDate.format(new Date()));
			dateVal = DateHandler.strToDate(formatDate.format(new Date()));
			Long mnt = 0L;
			Long mntDev = 0L;
			String tmpMntDev = "";
			String motifOperation = ""; // Ajouté by Hichem pour extrait

			guichetDao = (GuichetDAO) context.getBean("guichetDAO");
			ContratCptId contratCptId = new ContratCptId();
			contratCptId.setCodStrcStrc(new Long(ligne.trim().substring(0, 3)));
			strc.setCodStrcStrc(contratCptId.getCodStrcStrc());
			int[] res = getIndexStrc(strc.getCodStrcStrc());
			int indexStrc = res[1];
			int stateStrc = res[0];

			codePrdGOD = ligne.trim().substring(3, 7);

			if (codePrdGOD.equals("0327") || codePrdGOD.equals("0443") || codePrdGOD.equals("0507")
					|| codePrdGOD.equals("1035")) {
				isGOD = true;
			}

			if (isGOD == false) {

				if (ligne != null && ligne.length() > 13 && ligne.trim().substring(3, 13).equals("0135299999")) {
					is135 = true;
				}
				if (stateStrc >= 0 || is135) {
					if (stateStrc > 0 && !is135) {
						listAgencesMAJNSI.get(indexStrc).setNbTotalOperations(
								listAgencesMAJNSI.get(indexStrc).getNbTotalOperations() + 1);
					}
					if (stateStrc >= 0) {
						dateCpt =
								listAgencesMAJNSI.get(indexStrc).getJourneeStructureBatch()
										.getJourneeStructureBatchId().getDatJrnJrn();
						dateSys =
								listAgencesMAJNSI.get(indexStrc).getJourneeStructureBatch()
										.getJourneeStructureBatchId().getDatJrnJrn();
					}
					contratCptId.setCodPrdPrd(new Long(ligne.trim().substring(3, 7)));
					contratCptId.setNumCcptCcpt(new Long(ligne.trim().substring(7, 13)));
					GetContratCptByIdCmd getContratCptByIdCmd = new GetContratCptByIdCmd();
					ContratCpt cptPret = new ContratCpt();
					cptPret.setContratCptId(contratCptId);
					cptPret = (ContratCpt) getContratCptByIdCmd.execute(cptPret);

					if (contratCptId.getCodPrdPrd().equals(1109l)
							|| contratCptId.getCodPrdPrd().equals(110l)
							|| contratCptId.getCodPrdPrd().equals(135l)
							|| contratCptId.getCodPrdPrd().equals(138l)
							|| Arrays.asList(Constants.listCompteEnDinars).contains(
									(Integer) (contratCptId.getCodPrdPrd().intValue()))
							|| Arrays.asList(Constants.listCompteEnDinarsConvertibles).contains(
									(Integer) (contratCptId.getCodPrdPrd().intValue()))
							|| Arrays.asList(Constants.listCompteSpeciauxEnDinars).contains(
									(Integer) (contratCptId.getCodPrdPrd().intValue()))
							|| Arrays.asList(Constants.listCompteEnDevises).contains(
									(Integer) (contratCptId.getCodPrdPrd().intValue()))) {
						if (cptPret == null) {
							// throw new contratCptNotFoundException();

							String sens = "C";
							if (ligne.trim().substring(43, 44).equals("-"))
								sens = Constants.COD_SENS_CR;
							else
								sens = Constants.COD_SENS_DB;

							gestionRejet(stateStrc, indexStrc, DateHandler.strToDate(formatDate.format(new Date())),
									cptPret, strc, Long.valueOf(ligne.trim().substring(32, 43)), sens,
									" Compte inexistant ", ligne);
							crudService.remove(mAJNSIVo.getTmpBatchVirNSI());

						} else {
							if (stateStrc > 0 || is135) {
								if (cptPret.getCodEtatCcpt().equals("V")) {
									SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
									dateVal = formatter.parse(ligne.trim().substring(25, 31));
									dateCpt = formatter.parse(ligne.trim().substring(17, 23));
									mnt = Long.valueOf(ligne.trim().substring(32, 43));
									motifOperation = ligne.trim().substring(44);

									OperationMoyPay operationMoyPay = new OperationMoyPay();
									Structure structureInit = new Structure();
									structureInit.setCodStrcStrc(contratCptId.getCodStrcStrc());
									Structure structureRecep = new Structure();
									structureRecep.setCodStrcStrc(contratCptId.getCodStrcStrc());

									Devise devise = new Devise();
									devise.setCodDevDev(cptPret.getDevise().getCodDevDev());
									operationMoyPay.setDevise(devise);
									operationMoyPay.setContratCpt(cptPret);
									operationMoyPay.setStructureInitiatrice(structureInit);
									operationMoyPay.setStructureReceptrice(structureRecep);

									operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
									Personnel personnel = new Personnel();
									personnel.setNumMatrUser("9999");
									operationMoyPay.setPersonnelInitiateur(personnel);// / personne initiatrice
																					  // seulement au
																					  // cas
																					  // de

									operationMoyPay.setPersonnelValideur(personnel);// / personnel initiatrice =
																					// personnel
																					// validateur
									Tache tache = new Tache();
									TacheId tacheId = new TacheId();
									tacheId.setCodOperOper(Constants.COD_OPER_VIR_SIEGE);
									tacheId.setCodTachTach(1L);
									tache.setTacheId(tacheId);
									operationMoyPay.setTache(tache);
									operationMoyPay.setDatOperOmp(dateCpt);
									operationMoyPay.setDatSystOmp(dateSys);
									operationMoyPay.setDatValOmp(dateVal);
									if (cptPret.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
										String libOp = Const.getLibOp(Integer.parseInt(ligne.trim().substring(23, 25)));
										String libFichier = "";
										if (ligne.trim().length() > 53) {
											libFichier = ligne.trim().substring(53);
										}

										/*******************************************/

										// operationMoyPay.setCodRefbOmp(getLib(libOp, libFichier));
										operationMoyPay.setRefIns1Omp(ligne.trim().substring(44,
												Math.min(53, ligne.trim().length())));

										/********** Ajouté pour extrait *********/

										if ((ligne.trim().substring(23, 25).equals("98") || ligne.trim()
												.substring(23, 25).equals("82"))
												&& motifOperation != null && motifOperation.length() > 9) {

											motifOperation = motifOperation.substring(9);
											if (operationMoyPay.getRefIns1Omp().startsWith("000"))
												motifOperation =
														operationMoyPay.getRefIns1Omp().substring(3)
																.replaceAll(" ", "")
																+ " " + motifOperation;
											else
												motifOperation =
														operationMoyPay.getRefIns1Omp().replaceAll(" ", "") + " "
																+ motifOperation;
										}
										String codRefbOmp = libOp + " " + motifOperation;
										if (codRefbOmp.length() > 30) {
											codRefbOmp = codRefbOmp.substring(0, 29);
										}
										operationMoyPay.setCodRefbOmp(codRefbOmp);

									} else {
										tmpMntDev = ligne.trim().substring(44).replaceAll(" ", "");
										String libOp = Const.getLibOp(Integer.parseInt(ligne.trim().substring(23, 25)));
										String libFichier = "";
										if (ligne.trim().length() > 63) {
											libFichier = ligne.trim().substring(63);
										}
										if (ligne.trim().substring(44).startsWith("      "))
											operationMoyPay.setCodRefbOmp(getLib(libOp, ""));
										else
											operationMoyPay.setCodRefbOmp(getLib(libOp, libFichier));
										operationMoyPay.setRefIns1Omp(tmpMntDev.trim().substring(10,
												Math.min(19, tmpMntDev.trim().length())));
									}
									operationMoyPay.setCodRefcOmp(ligne.trim().substring(13, 17)
											+ ligne.trim().substring(23, 25));
									TypePiece typePieceDem = cptPret.getClient().getPersonne().getTypePiece();
									operationMoyPay.setTypePieceDemandeur(typePieceDem);
									operationMoyPay.setNumPcedOmp(cptPret.getClient().getPersonne().getNumPcePers());
									if (cptPret.getClient().getPersonne() != null
											&& cptPret.getClient().getPersonne().getNomRsPers() != null
											&& cptPret.getClient().getPersonne().getNomRsPers().length() > 0) {
										operationMoyPay.setNomNomdOmp(cptPret
												.getClient()
												.getPersonne()
												.getNomRsPers()
												.substring(
														0,
														Math.min(60, cptPret.getClient().getPersonne().getNomRsPers()
																.trim().length())));
									} else {
										if (cptPret.getClient().getPersonne().getNomNomPers() != null)
											operationMoyPay.setNomNomdOmp(cptPret
													.getClient()
													.getPersonne()
													.getNomNomPers()
													.substring(
															0,
															Math.min(60, cptPret.getClient().getPersonne()
																	.getNomNomPers().trim().length())));
										if (cptPret.getClient().getPersonne().getNomPrnPers() != null)
											operationMoyPay.setNomPrndOmp(cptPret
													.getClient()
													.getPersonne()
													.getNomPrnPers()
													.substring(
															0,
															Math.min(60, cptPret.getClient().getPersonne()
																	.getNomPrnPers().trim().length())));
									}
									if (ligne.trim().substring(43, 44).equals("-"))
										operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);
									else
										operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);

									if (cptPret.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
										operationMoyPay.setMontSoldCcpt(cptPret.getMontSoldCcpt());
										operationMoyPay.setMontDinOmp(mnt);
										if (operationMoyPay.getCodSensOmp().equals(Constants.COD_SENS_DB))
											operationMoyPay.setMontApreOmp(cptPret.getMontSoldCcpt()
													- operationMoyPay.getMontDinOmp());
										else
											operationMoyPay.setMontApreOmp(cptPret.getMontSoldCcpt()
													+ operationMoyPay.getMontDinOmp());
									} else {

										mntDev = Long.valueOf(tmpMntDev.trim().substring(0, 10));
										operationMoyPay.setMontSoldCcpt(cptPret.getMontSoldCcpt());
										operationMoyPay.setMontSdevCcpt(cptPret.getMontSdevCcpt());
										operationMoyPay.setMontDinOmp(mnt);
										operationMoyPay.setMontDevOmp(mntDev);
										if (operationMoyPay.getCodSensOmp().equals(Constants.COD_SENS_DB)) {
											operationMoyPay.setMontApreOmp(cptPret.getMontSoldCcpt()
													- operationMoyPay.getMontDinOmp());
											operationMoyPay.setMontDevApreOmp(cptPret.getMontSdevCcpt() - mntDev);
										} else {
											operationMoyPay.setMontApreOmp(cptPret.getMontSoldCcpt()
													+ operationMoyPay.getMontDinOmp());
											operationMoyPay.setMontDevApreOmp(mntDev + cptPret.getMontSdevCcpt());
										}

										MvtDevise mvt = new MvtDevise();
										mvt.setMNT_DEV_DEV(operationMoyPay.getMontDevOmp());
										mvt.setCOD_SENS_OPER(operationMoyPay.getCodSensOmp());
										mvt.setREF_INTER_SIEGE(operationMoyPay.getRefIns1Omp());
										for (Iterator iterator = mAJNSIVo.getMvtDevises().iterator(); iterator
												.hasNext();) {
											MvtDevise mvtDevDev = (MvtDevise) iterator.next();
											if (mvtDevDev.equals(mvt)) {
												operationMoyPay.setCodRefbOmp(mvtDevDev.getLIB_OPER().substring(0,
														Math.min(30, mvtDevDev.getLIB_OPER().length())));
												break;
											}
										}

									}
									operationMoyPay.setCodDemOmp("T"); // /*** type demandeur
																	   // (Titulaire,CoTitul,Mandataire)
									operationMoyPay.setLibMotfOmp(ligne.trim().substring(44));
									if (((!cptPret.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR))
											&& operationMoyPay.getCodSensOmp().equals(Constants.COD_SENS_DB) && cptPret
											.getMontSdevCcpt() < operationMoyPay.getMontDevOmp())
											|| ((Arrays.asList(Constants.listCompteEnDinarsConvertibles)
													.contains((Integer) (contratCptId.getCodPrdPrd().intValue())))
													&& operationMoyPay.getCodSensOmp().equals(Constants.COD_SENS_DB) && cptPret
													.getMontSoldCcpt() < operationMoyPay.getMontDinOmp())) {
										String sens = "C";
										if (ligne.trim().substring(43, 44).equals("-"))
											sens = Constants.COD_SENS_CR;
										else
											sens = Constants.COD_SENS_DB;
										String etat = "Manque de provision";

										gestionRejet(stateStrc, indexStrc,
												DateHandler.strToDate(formatDate.format(new Date())), cptPret, strc,
												Long.valueOf(ligne.trim().substring(32, 43)), sens, etat, ligne);
										crudService.remove(mAJNSIVo.getTmpBatchVirNSI());

									} else {

										GuichetService guichetService =
												(GuichetService) context.getBean("guichetService");
										operationMoyPay =
												(OperationMoyPay) guichetService.ajoutOpMoyPay(operationMoyPay);
										if (stateStrc >= 0)
											UtilCtr.updateSoldeDevDin(operationMoyPay.getContratCpt(),
													operationMoyPay.getCodSensOmp(), mnt, mntDev);

										crudService.remove(mAJNSIVo.getTmpBatchVirNSI());
										if (stateStrc >= 0) {
											listAgencesMAJNSI.get(indexStrc).setNbOperationsPosi(
													listAgencesMAJNSI.get(indexStrc).getNbOperationsPosi() + 1);
											if (operationMoyPay.getCodSensOmp().equals("C")) {
												listAgencesMAJNSI.get(indexStrc).setSommeOp(
														listAgencesMAJNSI.get(indexStrc).getSommeOp()
																+ operationMoyPay.getMontDinOmp());
											} else {
												listAgencesMAJNSI.get(indexStrc).setSommeOpDebit(
														listAgencesMAJNSI.get(indexStrc).getSommeOpDebit()
																+ operationMoyPay.getMontDinOmp());
											}
										} else {
											mAJNSIVo.getAgNonNsi().setNbOperationsPosi(
													mAJNSIVo.getAgNonNsi().getNbOperationsPosi() + 1);
											if (operationMoyPay.getCodSensOmp().equals("C")) {
												mAJNSIVo.getAgNonNsi().setSommeOp(
														mAJNSIVo.getAgNonNsi().getSommeOp()
																+ operationMoyPay.getMontDinOmp());
											} else {
												mAJNSIVo.getAgNonNsi().setSommeOpDebit(
														mAJNSIVo.getAgNonNsi().getSommeOpDebit()
																+ operationMoyPay.getMontDinOmp());
											}
										}
									}
								} else {
									String sens = "C";
									if (ligne.trim().substring(43, 44).equals("-"))
										sens = Constants.COD_SENS_CR;
									else
										sens = Constants.COD_SENS_DB;
									String etat = cptPret.get_codEtatCcpt();

									GetContratEtatCmd getContratEtatCmd = new GetContratEtatCmd();
									ValueObject retObj =
											(ValueObject) getContratEtatCmd.execute(cptPret.getContratCptId());

									if (!vo.hasError()) {
										ContratCptMandat contratCptMandat = (ContratCptMandat) retObj;
										etat = " " + contratCptMandat.getMessageEtat();
									}
									gestionRejet(stateStrc, indexStrc,
											DateHandler.strToDate(formatDate.format(new Date())), cptPret, strc,
											Long.valueOf(ligne.trim().substring(32, 43)), sens, etat, ligne);
									crudService.remove(mAJNSIVo.getTmpBatchVirNSI());
								}
							} else {
								logger.debug("Journée batch dejà insérée pour l'agence : " + strc.getCodStrcStrc());
							}
						}
					} else {
						crudService.remove(mAJNSIVo.getTmpBatchVirNSI());
						if (stateStrc >= 0) {
							listAgencesMAJNSI.get(indexStrc).setNbOperationsInter(
									listAgencesMAJNSI.get(indexStrc).getNbOperationsInter() + 1);
						}
					}
				}
			} else {

				/************* Cas de GOD 327 ,443,507 *********/
				System.out.println("ligne : " + ligne);
				ContratGod contratGod = new ContratGod();
				ContratGodId contratGodId = new ContratGodId();
				contratGodId.setCodStrcGod(new Long(ligne.trim().substring(0, 3)));
				contratGodId.setCodPrdGod(new Long(ligne.trim().substring(3, 7)));
				contratGodId.setNumCcptGod(new Long(ligne.trim().substring(7, 13)));
				contratGod.setContratGodId(contratGodId);

				BlocageGodVo blocageGodVo = new BlocageGodVo();
				blocageGodVo.setContratGod(contratGod);
				String motifOper = ligne.trim().substring(44);
				blocageGodVo.setMotifOperation(motifOper);
				ContratCptSold contratCptSold = new ContratCptSold();
				if (contratGodId.getCodPrdGod().equals(443L) || contratGodId.getCodPrdGod().equals(507L)
						|| contratGodId.getCodPrdGod().equals(1035L)) {

					tmpMntDev = ligne.trim().substring(44).replaceAll(" ", "");
					mntDev = Long.valueOf(tmpMntDev.trim().substring(0, 10));
					contratCptSold.setSoldeDevise(mntDev);

				}
				if (ligne.trim().substring(43, 44).equals("-")) {
					contratCptSold.setSens(Constants.COD_SENS_CR);
				} else {
					contratCptSold.setSens(Constants.COD_SENS_DB);
				}
				mnt = Long.valueOf(ligne.trim().substring(32, 43));
				contratCptSold.setSolde(mnt);
				blocageGodVo.setContratCptSold(contratCptSold);
				blocageGodVo.setCodOper(Constants.COD_OPER_VIR_SIEGE);
				SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
				dateCpt = formatter.parse(ligne.trim().substring(17, 23));
				blocageGodVo.setDateOperation(dateCpt);
				UpdateSoldeGodTrt updateSoldeGodTrt = new UpdateSoldeGodTrt();
				blocageGodVo = (BlocageGodVo) updateSoldeGodTrt.exec(blocageGodVo);
				crudService.remove(mAJNSIVo.getTmpBatchVirNSI());
			}
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJNSI-perf : ");
			text.append(e.getMessage());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MAJNSILigneTrt-perf");
			logger.error("Exception : ", e);
			gestionException(dateCpt, strc, e, ligne);
			mAJNSIVo.addError(erreur);
			// /*** gerer une exception
			throw new RuntimeException(e);
		}
		return mAJNSIVo;
	}

	private void gestionException(Date dateOper, Structure agence, Exception e, String donnee) {

		try {
			bufWriter.write(agence.getCodStrcStrc() + " " + DateHandler.dateToStr(dateOper) + " " + e.toString()
					+ " ** " + donnee);
			bufWriter.newLine();
		} catch (IOException e1) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJNSI-perf : ");
			text.append(e1.getMessage());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MAJNSI-perf_fileLogExcep");
			logger.error("Exception : ", e1);
		}

	}

	int[] getIndexStrc(Long codStrc) {
		int[] res = new int[2];
		res[0] = -1;
		res[1] = -1;
		if (listAgencesMAJNSI != null)
			for (int i = 0; i < listAgencesMAJNSI.size(); i++) {
				if ((listAgencesMAJNSI.get(i).getJourneeStructureBatch().getJourneeStructureBatchId() != null)
						&& (listAgencesMAJNSI.get(i).getJourneeStructureBatch().getJourneeStructureBatchId()
								.get_codStrcStrc().equals(codStrc)))
					if ((listAgencesMAJNSI.get(i).getOldCodStatJsb() == null)
							|| listAgencesMAJNSI.get(i).getOldCodStatJsb().equals(0L)) {
						res[0] = 1;
						res[1] = i;
						return res;
					} else {
						res[0] = 0;
						res[1] = i;
						return res;
					}

			}
		return res;
	}

	private void gestionRejet(int stateStrc, int indexStrc, Date dateOp, ContratCpt contratCpt, Structure structure,
			Long montVir, String codSens, String motif, String ligne) {

		BatchRejetVirNSI batchRejetVirNSI = new BatchRejetVirNSI();
		batchRejetVirNSI.setCodEtatBatr("A");
		batchRejetVirNSI.setDatSystBatr(new Date());
		batchRejetVirNSI.setDatCompBatr(dateOp);
		batchRejetVirNSI.setContratCpt(contratCpt);
		batchRejetVirNSI.setMontVirBatr(montVir);
		batchRejetVirNSI.setCodSensBatr(codSens);
		batchRejetVirNSI.setMotifRejBatr(motif);
		batchRejetVirNSI.setDonneBatr(ligne);
		batchRejetVirNSI.setStructure(structure);
		CURService crudService = (CURService) context.getBean("CURService");
		crudService.create(batchRejetVirNSI);
		if (stateStrc >= 0) {
			listAgencesMAJNSI.get(indexStrc).setNbOperationsRej(
					listAgencesMAJNSI.get(indexStrc).getNbOperationsRej() + 1);
		}
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public String getLib(String libOp, String libFichier) {
		String res = "";
		if (libFichier != null && libFichier.length() >= 15) {
			res = libOp.substring(0, Math.min(13, libOp.length())) + ". " + libFichier;
		} else if (libFichier != null) {
			if (libOp.length() <= (13 + (15 - libFichier.length())))
				res = libOp.substring(0, Math.min(13 + (15 - libFichier.length()), libOp.length())) + " " + libFichier;
			else
				res = libOp.substring(0, Math.min(13 + (15 - libFichier.length()), libOp.length())) + ". " + libFichier;
		} else
			res = libOp;

		return res.substring(0, Math.min(30, res.length()));
	}

	public Date getDateEch(String month, String year) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date convertedDate = dateFormat.parse(month + "/01/" + year);
		Calendar c = Calendar.getInstance();
		c.setTime(convertedDate);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	public void depPerso(OperationMoyPay operationMoyPay, String month, String year) throws Exception {
		DepPersonnel depPersonnel = new DepPersonnel();
		String codeAgence = operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc().toString();
		// creer crédit : 000 + 00 + 00000
		// CodStruct + annee + num_seq

		List lmaxNum = guichetDao.getMaxNumCreditCG(codeAgence);
		String numDem = "0000000";
		for (Iterator it = lmaxNum.iterator(); it.hasNext();) {
			ListOrderedMap ob = (ListOrderedMap) it.next();
			for (int i1 = 0; i1 < ob.size(); i1++) {
				if (ob.getValue(i1) != null)
					numDem = ob.getValue(i1).toString();
				i1 = i1 + 1;
			}
		} // / Num Seq
		String DateString = DateHandler.dateToStr(new Date());
		String anneeEncours = DateString.substring(8, 10);
		String annee = numDem.substring(0, 2);
		if (anneeEncours.equals(annee)) {
			long IdDemandeLong = new Long(numDem);
			IdDemandeLong = IdDemandeLong + 1;
			numDem = "" + IdDemandeLong;
			for (int j = numDem.length() + 1; j <= 7; j = j + 1)
				numDem = "0" + numDem;
			numDem = codeAgence + numDem;
		} else {
			numDem = codeAgence + anneeEncours + "00001";
		}
		depPersonnel.setNumCredCredeps(new Long(numDem));
		depPersonnel.setMontCredCredeps(operationMoyPay.getMontDinOmp());
		depPersonnel.setMontAutCredeps(operationMoyPay.getMontDinOmp());
		depPersonnel.setDatEchCredeps(getDateEch(month, year));
		depPersonnel.setDatRealCredeps(new Date());
		depPersonnel.setCodPrdPrd(Constants.COD_PRD_FACIL_CAISSE);
		depPersonnel.setCodDetatCredps("6");
		String cpt =
				StrHandler.lpad("" + operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc(), '0', 3)
						+ StrHandler
								.lpad("" + operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd(), '0', 4)
						+ StrHandler.lpad("" + operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt(), '0',
								6);
		depPersonnel.setNumCptCredeps(cpt);
		depPersonnel.setCodStrcStrc(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc());
		depPersonnel.setNumSeqPers(operationMoyPay.getContratCpt().getClient().getPersonne().getNumSeqPers());

		// Long da = d.getMontDaDetcg(); //000
		// Long ga = d.getMontGarDetcg(); //000
		CURService crudService = (CURService) context.getBean("CURService");
		crudService.create(depPersonnel);

		// pb il faut modifier le sold avant la date echance sionn la modification ne sera pas executé
		UtilCtr.updateSoldeDevDin(operationMoyPay.getContratCpt(), operationMoyPay.getCodSensOmp(),
				operationMoyPay.getMontDinOmp(), null);

		ContratCpt contratCpt =
				(ContratCpt) searchEngine.loadForUpdate(ContratCpt.class, operationMoyPay.getContratCpt()
						.getContratCptId());
		contratCpt.setDatEautCcpt(depPersonnel.getDatEchCredeps());
		contratCpt.setMontAutCcpt(operationMoyPay.getMontDinOmp());
		crudService.update(contratCpt);

	}

	public static int getQuantieme(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(GregorianCalendar.DAY_OF_YEAR);
	}
}
