package com.bna.smile.model.virement.traitement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceOperVirement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.ExonerationTvaDAO;
import com.bna.smile.model.domainecommun.traitement.GetContratCptByIdTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.SuivFileTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.fwk.util.DateHandler;

public class TraitementVirPermanentTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	// VirementService virementService = (VirementService) context.getBean("iVirementService");
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	long montant_commision = 0;
	long montant_tva = 0;
	boolean etatClientTaxable = false; // True : client taxable ; False :client non taxable
	GlobalVirement globalVirementObj = new GlobalVirement();
	boolean etatExecutionVirement = false;
	long numLotVirement = 0;

	public TraitementVirPermanentTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;

		globalVirementObj = (GlobalVirement) virementVo.getGlobalVirement();

		Date dateComptableAgence = new Date();
		dateComptableAgence = virementVo.getDateComptableAgence();
		Structure agence = virementVo.getStructure();

		List<DetailVirement> listeDetailVirementsFinal = new ArrayList<DetailVirement>();
		String heureString = formaterHeure.format(new Date());

		long montant_commissionRecue = 0;
		OperationMoyPay operationMoyPayCompteDO = new OperationMoyPay();
		this.setCroFlag(false);
		try {

			globalVirementObj =
					(GlobalVirement) searchEngine.get(GlobalVirement.class, globalVirementObj.getNumSeqGvir());

			// / ------------- Set List Detail Virement ------------------ ///

			Long[] etatsVirements =
					{ Constants.COD_ETAT_VIREMENT_ATTENTE, Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN,
							Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF };

			ICriteria criteriaDetail = searchEngine.createCriteria();
			IExpression expressionDetail = searchEngine.createExpression();

			criteriaDetail.add(expressionDetail.eq("detailVirementId.numSeqGvir", globalVirementObj.getNumSeqGvir()));
			criteriaDetail.add(expressionDetail.le("datEchDetv", dateComptableAgence));
			criteriaDetail.add(expressionDetail.in("etaDetvDetv", etatsVirements));

			listeDetailVirementsFinal =
					new ArrayList<DetailVirement>(searchEngine.find(DetailVirement.class, criteriaDetail));

			// / ---------------------------------------------------------- ///

			long MONT_SOLDE = 0;
			long MONT_AUT = 0;
			long MONT_DEBLC = 0;

			long MONT_TOTAL = 0;
			long MONT_VIR = 0;
			boolean etatMontantVirement = false;
			// boolean etatValiditeBenif = false;
			boolean etatExisteVirementSGMT = false;
			boolean etatEnvoiSGMT = virementVo.isEtatEnvoiSGMT();
			long mntAlimentation = 0;
			Long etatCptBenif = 0L;
			if (listeDetailVirementsFinal != null && listeDetailVirementsFinal.size() > 0) {

				// ********************** Existance Virement SGMT ******************//
				for (DetailVirement detailVirement1 : listeDetailVirementsFinal) {
					String codeBanque = detailVirement1.getRibBenDetv().substring(0, 2);
					if (!codeBanque.equals("03")) {
						if (detailVirement1.getMntDetvDetv().longValue() >= Constants.MONTANT_VIREMENT_SGMT
								.longValue()) {

							etatExisteVirementSGMT = true;

						}
					}
				}

				for (DetailVirement detailVirement : listeDetailVirementsFinal) {

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

					if (detailVirement.getMntDetvDetv() == null) {
						MONT_VIR = 0;
					} else {
						MONT_VIR = detailVirement.getMntDetvDetv().longValue();
					}

					if (MONT_TOTAL < MONT_VIR) {// / Manque d'approvision

						if (globalVirementObj.getContratCpt().getContratCptId().getCodPrdPrd()
								.equals(Constants.COD_COMPTE_CHEQUE)
								&& globalVirementObj.getContratCpt().getBoolCverCcpt() != null
								&& globalVirementObj.getContratCpt().getBoolCverCcpt().longValue() == 1) {

							// / ------------------ Verfier si Exist Compte Vert 165 ---------------------
							// ///
							VirementVo virementVo4 = new VirementVo();
							virementVo4.setMONT_TOTAL(MONT_TOTAL);
							virementVo4.setMONT_VIR(MONT_VIR);
							virementVo4.setContratCpt(globalVirementObj.getContratCpt());
							virementVo4.setDateComptableAgence(dateComptableAgence);
							VerifierProvisionCompteVertTrt verifierProvisionCompteVertDoVir =
									new VerifierProvisionCompteVertTrt();
							virementVo4 = (VirementVo) verifierProvisionCompteVertDoVir.exec(virementVo4);
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
								virementVoAlimentationCompteDepot
										.setContratCptCompteDepot(globalVirementObj.getContratCpt());
								virementVoAlimentationCompteDepot.setGlobalVirement(globalVirementObj);
								virementVoAlimentationCompteDepot.setStructure(agence);
								AlimentationCompteDepotTrt alimenterCompteDepot = new AlimentationCompteDepotTrt();
								virementVoAlimentationCompteDepot =
										(VirementVo) alimenterCompteDepot.exec(virementVoAlimentationCompteDepot);

								etatMontantVirement = true;
							} else {

								// / ------------------ Non Exist Compte Vert 165
								// ----------------------------
								// ///
								VirementVo virementVo3 = new VirementVo();
								Operation operation = new Operation();
								operation.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PERMANENT);
								virementVo3.setOperation(operation);
								virementVo3.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PERMANENT);
								virementVo3.setGlobalVirement(globalVirementObj);
								virementVo3.setDateComptableAgence(dateComptableAgence);
								NonApprovisionDoVirTrt nonApprovisionDoVir = new NonApprovisionDoVirTrt();
								virementVo3 = (VirementVo) nonApprovisionDoVir.exec(virementVo3);

								logger.info("\n  /***** VIREMENT TRAITER :  NON APPROVISION  ***/   \n");

								logger.info("\n  /***** NUM_GVIR = " + globalVirementObj.getNumSeqGvir() + "*****/ \n");

							}

						} else {

							// / ------------------ Produit Compte != 101 ----------------------------

							VirementVo virementVo3 = new VirementVo();
							Operation operation = new Operation();
							operation.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PERMANENT);
							virementVo3.setGlobalVirement(globalVirementObj);
							virementVo3.setDateComptableAgence(dateComptableAgence);
							virementVo3.setDetailVirement(detailVirement);
							virementVo3.setOperation(operation);
							virementVo3.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PERMANENT);
							NonApprovisionDoVirTrt nonApprovisionDoVir = new NonApprovisionDoVirTrt();
							virementVo3 = (VirementVo) nonApprovisionDoVir.exec(virementVo3);

							logger.info("\n  /***** VIREMENT TRAITER :  NON APPROVISION  ***/   \n");
							logger.info("\n  /***** NUM_GVIR = " + globalVirementObj.getNumSeqGvir() + "***/   \n");

						}

					} else {

						etatMontantVirement = true;

					}

					if ((etatMontantVirement == true && etatExisteVirementSGMT == false) || (etatMontantVirement == true
							&& etatExisteVirementSGMT == true && etatEnvoiSGMT == true)) {

						// while (iterateurDetailVirement.hasNext()) {
						// DetailVirement detailVirementObj = (DetailVirement) iterateur.next();

						// / ------------- Traitement Condition de banque -------- ///

						// ********* Condition de Banque ********//
						ArrayList<String> palierChar1 = new ArrayList<String>();
						boolean etatVirementMemeAgence = false; // MEME AGENCE DE COMPTE A COMPTE CLIENT DIFF
						boolean etatVirementAutreAgence = false;
						boolean etatVirementAutreBanque = false;
						boolean etatVirementCompteEpargneLiee = false; // MEME AGENCE DE COMPTE A COMPTE D'EPARGNE
						boolean etatVirementMemeAgenceMemePersonne = false; // MEME AGENCE DE COMPTE A COMPTE MEME
																			// CLIENT LIEE

						boolean trouveCompte = false;

						VirementVo virementVoCB = new VirementVo();
						Operation operationCB = new Operation();

						operationCB.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PERMANENT);
						if (detailVirement.getRibBenDetv().substring(0, 2).equals("03")) {
							ContratCpt contratCptBenif = new ContratCpt();
							ContratCptId contratCptId = new ContratCptId();
							contratCptId.setCodStrcStrc(new Long(detailVirement.getRibBenDetv().substring(5, 8)));
							contratCptId.setCodPrdPrd(new Long(detailVirement.getRibBenDetv().substring(8, 12)));
							contratCptId.setNumCcptCcpt(new Long(detailVirement.getRibBenDetv().substring(12, 18)));
							contratCptBenif.setContratCptId(contratCptId);

							GetContratCptByIdTrt contratCptByIdTrt = new GetContratCptByIdTrt();
							contratCptBenif = (ContratCpt) contratCptByIdTrt.exec(contratCptBenif);
							if (contratCptBenif != null && contratCptBenif.getClient() != null) {
								if (contratCptBenif.getClient().getNumSeqPers().longValue() == globalVirementObj
										.getContratCpt().getClient().getNumSeqPers().longValue()) {

									etatVirementMemeAgenceMemePersonne = true;

								}
							}
						}

						if (detailVirement.getRibBenDetv().substring(0, 2).equals("03")) {

							long codPrdPrd = new Long(detailVirement.getRibBenDetv().substring(8, 12)).longValue();

							for (int i = 0; i < Constants.produitCompteEpargneLiee.length; i++) {

								if (Constants.produitCompteEpargneLiee[i].longValue() == codPrdPrd) {
									trouveCompte = true;
								}
							}

							if (trouveCompte == true) {

								etatVirementCompteEpargneLiee = true;

							} else if (new Long(detailVirement.getRibBenDetv().substring(5, 8))
									.longValue() == globalVirementObj.getStructure().getCodStrcStrc().longValue()) {

								etatVirementMemeAgence = true;
								etatCptBenif = Long.valueOf(0);

							} else {
								etatVirementAutreAgence = true;
								etatCptBenif = Long.valueOf(1);
							}

						} else {
							etatVirementAutreBanque = true;

							if (detailVirement.getMntDetvDetv().compareTo(Constants.MONTANT_VIREMENT_SGMT) >= 0) {
								etatCptBenif = Long.valueOf(3);
							} else {
								etatCptBenif = Long.valueOf(2);
							}
						}

						if (etatVirementCompteEpargneLiee == true) {

							if (new Long(detailVirement.getRibBenDetv().substring(5, 8))
									.longValue() == globalVirementObj.getStructure().getCodStrcStrc().longValue()) {

								if (etatVirementMemeAgenceMemePersonne == true) {
									palierChar1.add("48");
								} else {
									palierChar1.add("106");
								}
								palierChar1.add("41");
								etatCptBenif = Long.valueOf(0);
							} else {
								palierChar1.add("42");
								etatCptBenif = Long.valueOf(1);
							}

							virementVoCB.setContratCpt(globalVirementObj.getContratCpt());
							virementVoCB.setOperation(operationCB);
							virementVoCB.setMontant_virement(detailVirement.getMntDetvDetv());
							virementVoCB.setGlobalVirement(globalVirementObj);
							virementVoCB.setDateComptableAgence(dateComptableAgence);
							virementVoCB.setListePalierCaractere(palierChar1);
							virementVoCB = getConditionDeBanqueComptesLiees(virementVoCB);

							if (virementVoCB.getCommissionRecue().isEmpty() == false) {
								montant_commissionRecue =
										new Long(StrHandler.strToMnt(virementVoCB.getCommissionRecue()));
							}
							if (virementVoCB.getTvaRecue().isEmpty() == false) {
								montant_tva = new Long(StrHandler.strToMnt(virementVoCB.getTvaRecue()));
							}

						} else {

							if (etatVirementMemeAgence == true) {
								if (etatVirementMemeAgenceMemePersonne == true) {

									palierChar1.add("48");
								} else {
									palierChar1.add("106");

								}
								palierChar1.add("41");
							} else if (etatVirementAutreAgence == true) {
								palierChar1.add("42");
							} else if (etatVirementAutreBanque == true) {
								palierChar1.add("43");
							}

							virementVoCB.setContratCpt(globalVirementObj.getContratCpt());
							virementVoCB.setOperation(operationCB);
							virementVoCB.setMontant_virement(detailVirement.getMntDetvDetv());
							virementVoCB.setGlobalVirement(globalVirementObj);
							virementVoCB.setDateComptableAgence(dateComptableAgence);
							virementVoCB.setListePalierCaractere(palierChar1);
							virementVoCB = getConditionDeBanque(virementVoCB);
							if (virementVoCB.getCommissionRecue().isEmpty() == false) {
								montant_commissionRecue =
										new Long(StrHandler.strToMnt(virementVoCB.getCommissionRecue()));
							}
							if (virementVoCB.getTvaRecue().isEmpty() == false) {
								montant_tva = new Long(StrHandler.strToMnt(virementVoCB.getTvaRecue()));
							}
						}

						// *********Caracteristique Taxable du client ********//

						ExonerationTvaDAO exonerationTvaDAO = new ExonerationTvaDAO();
						ParamRechercheOpposition param = new ParamRechercheOpposition();
						param.setTypPceDemd(globalVirementObj.getContratCpt().getClient().getPersonne().getTypePiece()
								.getCodTpceTpce());
						param.setNumPceDemd(
								globalVirementObj.getContratCpt().getClient().getPersonne().getNumPcePers());
						param.setDateDebutConsult(dateComptableAgence);

						PrimitiveVO res = (PrimitiveVO) exonerationTvaDAO.isClientExonereTVA(param);

						if (res.isVBool() == true) {
							etatClientTaxable = false;
						} else {
							etatClientTaxable = true;
						}

						// *************** Creation Cro // Operation Moyen de Payement**********//
						VirementVo objVirementVoCRO = new VirementVo();

						objVirementVoCRO.setGlobalVirement(globalVirementObj);
						objVirementVoCRO.setContratCpt(globalVirementObj.getContratCpt());
						objVirementVoCRO.setDetailVirement(detailVirement);
						objVirementVoCRO.setDateComptableAgence(dateComptableAgence);
						objVirementVoCRO.setMontant_commissionRecue(montant_commissionRecue);
						objVirementVoCRO.setTvaRecue(montant_tva + "");
						objVirementVoCRO.setEtatTaxableClient(etatClientTaxable);
						objVirementVoCRO.setValueDateRecue(virementVoCB.getValueDateRecue());
						objVirementVoCRO.setValueDateComRecue(virementVoCB.getValueDateComRecue());
						objVirementVoCRO.setNbreGlobalByCB(1);
						objVirementVoCRO.setEtatBenifVirementCB(etatCptBenif);// 0: meme agence; 1 :autre agence; 2:
																			  // autre banque ; 3:SGMT

						CreationCROExecutionVirementPermanentTrt creationCROExecutionVirementPermanent =
								new CreationCROExecutionVirementPermanentTrt();
						objVirementVoCRO = (VirementVo) creationCROExecutionVirementPermanent.exec(objVirementVoCRO);

						operationMoyPayCompteDO = objVirementVoCRO.getOperationMoyPay();

						virementVo.setOperationMoyPay(operationMoyPayCompteDO);

						// / ------------------ Creation Trace Oper Virement ----------------- ///
						TraceOperVirement traceOperVirement = new TraceOperVirement();
						Long numSeqTvir = new Long(0);
						numSeqTvir = virementGlobalDAO.getSequenceTraceOperVirement();

						traceOperVirement.setNumSeqTvir(numSeqTvir); // Num Seq Trace Vir
						traceOperVirement.setGlobalVirement(globalVirementObj);

						if (globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_PERMANENT
								.longValue()) {
							traceOperVirement.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PERMANENT);
							traceOperVirement.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PERMANENT);
						} else if (globalVirementObj.getCodPrdPrd()
								.longValue() == Constants.COD_PRODUIT_VIREMENT_PONCTUEL.longValue()) {
							traceOperVirement.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PONCTUEL);
							traceOperVirement.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PONCTUEL);

						} else if (globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_MASSE
								.longValue()) {
							traceOperVirement.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_MASSE);
							traceOperVirement.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_MASSE);

						}

						traceOperVirement.setNumMatrUser("9999");

						traceOperVirement.setDatOperTvir(dateComptableAgence);
						traceOperVirement.setTimeOperTvir(heureString);

						traceOperVirement.setStructure(globalVirementObj.getStructure());

						crudService.create(traceOperVirement);

						// ********************** Traitemts des Details Virements *******************//

						VirementVo virementVo4 = new VirementVo();

						virementVo4.setGlobalVirement(globalVirementObj);
						virementVo4.setDetailVirement(detailVirement);
						virementVo4.setDateComptableAgence(dateComptableAgence);
						virementVo4.setOperationMoyPay(operationMoyPayCompteDO);
						ExecutionDoVirPermanentTrt executionDoVirPermanent = new ExecutionDoVirPermanentTrt();
						virementVo4 = (VirementVo) executionDoVirPermanent.exec(virementVo4);

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

							if (virementVo.getListeFile() != null && virementVo.getListeFile().size() > 0) {

								for (File file : virementVo.getListeFile()) {
									String codeStrcBCT = virementGlobalDAO.getCodeStructureBCT(
											Long.valueOf(globalVirementObj.getStructure().getCodStrcStrc()));
									if (file.length() > 0) {
										// **** save dans SUIVI_FILE_TELECOMPENSATION ****//
										SuivFileTrt.ajouterFichier(file.getName(), codeStrcBCT, dateComptableAgence, 1,
												Constants.COD_ENREGISTREMENT_VIREMENT);

										// ********* Envoi FTP ******//
										boolean etatSendFile = Util.sendFileFTP(file.getAbsolutePath(),
												Configuration.getLocalPathSend() + file.getName());

										if (etatSendFile == true) {
											logger.info("Fichier : " + file.getName() + " envoyé avec succés ");

										} else {

											logger.info("Erreur d'envoie du fichier : " + file.getName());
										}

									}
								}
							}
						}
						// / ------------------ Update Global_Virement ----------------- ///
						if (etatExecutionVirement == true) {
							boolean trouveDetailAttente = false;

							ICriteria criteriaGlobal = searchEngine.createCriteria();
							criteriaGlobal.add(expression.idEq(globalVirementObj.getNumSeqGvir()));
							globalVirementObj = (GlobalVirement) (searchEngine.get(GlobalVirement.class,
									globalVirementObj.getNumSeqGvir()));

							if (numLotVirement != 0) {
								globalVirementObj.setNumLotGvir(numLotVirement);
							}

							if (globalVirementObj.getDatFinEch() != null) {

								if (globalVirementObj.getDatFinEch().compareTo(dateComptableAgence) > 0) {
									globalVirementObj.setEtatGvirGvir(Constants.COD_ETAT_VIREMENT_ENCOUREXECUTION);

								} else {
									ICriteria criteria = searchEngine.createCriteria();
									criteria.add(expression.eq("detailVirementId.numSeqGvir",
											globalVirementObj.getNumSeqGvir()));
									Set<DetailVirement> liste = new HashSet<DetailVirement>(
											searchEngine.find(DetailVirement.class, criteria));

									for (DetailVirement detailVir : liste) {
										if (detailVir.getEtaDetvDetv()
												.longValue() == Constants.COD_ETAT_VIREMENT_ATTENTE.longValue()) {
											trouveDetailAttente = true;
										}
									}
									if (trouveDetailAttente == true) {
										globalVirementObj.setEtatGvirGvir(Constants.COD_ETAT_VIREMENT_ENCOUREXECUTION);
									} else {
										globalVirementObj.setEtatGvirGvir(Constants.COD_ETAT_VIREMENT_EXECUTER);
									}

								}
							} else {
								globalVirementObj.setEtatGvirGvir(Constants.COD_ETAT_VIREMENT_ENCOUREXECUTION);
							}
						}

						crudService.update(globalVirementObj);

					} else if (etatMontantVirement == true && etatExisteVirementSGMT == true
							&& etatEnvoiSGMT == false) {

						if (detailVirement.getMntDetvDetv().longValue() >= Constants.MONTANT_VIREMENT_SGMT) {
							detailVirement.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
							detailVirement.setMotifRejetDetv(
									"La journée SGMT n’est pas encore ouverte ou elle est clôturée ");
							detailVirement.setDatExecDetv(dateComptableAgence);
							crudService.update(detailVirement);
						}

						// for (DetailVirement detailVirement2 : listeDetailVirementsFinal) {
						//
						// if (detailVirement2.getMntDetvDetv().longValue() >= Constants.MONTANT_VIREMENT_SGMT) {
						// detailVirement2.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
						// detailVirement2.setMotifRejetDetv("Absence Autorisation SGMT");
						// detailVirement2.setDatExecDetv(dateComptableAgence);
						// crudService.update(detailVirement2);
						// }
						// }

					}

				} // fin boucle for

			} // fin if

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans TraitementVirPermanentTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("TraitementVirPermanentTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau TraitementVirPermanentTrt : ", e);
			virementVo.setMessageValidation("Probléme dans TraitementVirPermanentTrt");
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

	public VirementVo getConditionDeBanque(VirementVo virementVoCB) {

		TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();

		// /-----------------------------////
		Operation operation = new Operation();
		operation = virementVoCB.getOperation();

		ContratCpt contratCpt = new ContratCpt();
		contratCpt = virementVoCB.getContratCpt();

		String montant = null;
		montant = virementVoCB.getMontant_virement() + "";

		String tvaRecue = "";
		String tva = "";
		String commissionRecue = "";
		String valueDateRecue = "";
		String valueDateComRecue = "";
		List<String> listePalierCaractere = virementVoCB.getListePalierCaractere();
		// /----------------------------///

		try {

			if (listePalierCaractere != null && listePalierCaractere.size() > 0) {
				// ArrayList palierChar1 = new ArrayList();
				// palierChar1.add("26");
				traitementConditionBanque.setPalierChar(new ArrayList<String>(listePalierCaractere));
			}
			traitementConditionBanque.setCodOperOper(operation.getCodOperOper().toString());
			traitementConditionBanque.setCodPrdPrd(contratCpt.getContratCptId().getCodPrdPrd() + "");
			traitementConditionBanque.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(contratCpt.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt().toString());
			if (contratCpt.getClient().getPersonne() != null
					&& contratCpt.getClient().getPersonne().getNumPcePers() != null) {
				traitementConditionBanque.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
			}
			if (contratCpt.getClient().getPersonne() != null
					&& contratCpt.getClient().getPersonne().getTypePiece() != null
					&& contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce() != null) {
				traitementConditionBanque
						.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce() + "");
			}
			traitementConditionBanque.setMontant(montant);
			traitementConditionBanque.setNbUnites("1");
			traitementConditionBanque.setDateReference(DateHandler.dateToStr(virementVoCB.getDateComptableAgence()));

			traitementConditionBanque.getCB();

			tvaRecue = String.valueOf(traitementConditionBanque.getMntTva() / 1000);
			if (tvaRecue == null || "".equals(tvaRecue.trim())) {
				tva = "0.0";
			}

			commissionRecue =
					AbstractValidator.formatMontant(traitementConditionBanque.getValeurCommission() / 1000 + "");
			if (commissionRecue == null || "".equals(commissionRecue.trim())) {
				commissionRecue = "0.0";
			}
			valueDateRecue = traitementConditionBanque.getDatevaleur();
			valueDateComRecue = traitementConditionBanque.getDatevaleurComm();
			virementVoCB.setTraitementConditionBanque(traitementConditionBanque);

			virementVoCB.setTva(tva);
			virementVoCB.setTvaRecue(tvaRecue);
			virementVoCB.setCommissionRecue(commissionRecue);
			virementVoCB.setValueDateRecue(valueDateRecue);
			virementVoCB.setValueDateComRecue(valueDateComRecue);
			virementVoCB.setOperation(operation);

		} catch (Exception e) {
			logger.error("Error occurred when trying to get bank conditions.", e);
		}

		return virementVoCB;

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

	public VirementVo getConditionDeBanqueComptesLiees(VirementVo virementVoCB) {

		TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();

		// /-----------------------------////
		Operation operation = new Operation();
		operation = virementVoCB.getOperation();

		ContratCpt contratCpt = new ContratCpt();
		contratCpt = virementVoCB.getContratCpt();

		String montant = null;
		montant = virementVoCB.getMontant_virement() + "";

		// ParamAgence paramAgence = null;
		String tvaRecue = "";
		String tva = "";
		String commissionRecue = "";
		String valueDateRecue = "";
		String valueDateComRecue = "";
		List<String> listePalierCaractere = virementVoCB.getListePalierCaractere();
		// /----------------------------///

		try {

			if (listePalierCaractere != null && listePalierCaractere.size() > 0) {
				// ArrayList palierChar1 = new ArrayList();
				// palierChar1.add("26");
				traitementConditionBanque.setPalierChar(new ArrayList<String>(listePalierCaractere));
			}
			traitementConditionBanque.setCodOperOper(operation.getCodOperOper().toString());
			traitementConditionBanque.setCodPrdPrd(Constants.COD_PRODUIT_VIREMENT_PERMANENT_LIES + "");
			traitementConditionBanque.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(contratCpt.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt().toString());
			if (contratCpt.getClient().getPersonne() != null
					&& contratCpt.getClient().getPersonne().getNumPcePers() != null) {
				traitementConditionBanque.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
			}
			if (contratCpt.getClient().getPersonne() != null
					&& contratCpt.getClient().getPersonne().getTypePiece() != null
					&& contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce() != null) {
				traitementConditionBanque
						.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce() + "");
			}

			traitementConditionBanque.setMontant(montant);
			traitementConditionBanque.setNbUnites("1");
			traitementConditionBanque.setDateReference(DateHandler.dateToStr(virementVoCB.getDateComptableAgence()));

			traitementConditionBanque.getCB();

			tvaRecue = String.valueOf(traitementConditionBanque.getMntTva() / 1000);
			if (tvaRecue == null || "".equals(tvaRecue.trim())) {
				tva = "0.0";
			}

			commissionRecue =
					AbstractValidator.formatMontant(traitementConditionBanque.getValeurCommission() / 1000 + "");
			if (commissionRecue == null || "".equals(commissionRecue.trim())) {
				commissionRecue = "0.0";
			}
			valueDateRecue = traitementConditionBanque.getDatevaleur();
			valueDateComRecue = traitementConditionBanque.getDatevaleurComm();
			virementVoCB.setTraitementConditionBanque(traitementConditionBanque);

			if (valueDateRecue != null && valueDateRecue.equals("NAN")) {
				valueDateRecue = null;
			}

			if (valueDateComRecue != null && valueDateComRecue.equals("NAN")) {
				valueDateComRecue = null;
			}

			virementVoCB.setTva(tva);
			virementVoCB.setTvaRecue(tvaRecue);
			virementVoCB.setCommissionRecue(commissionRecue);
			virementVoCB.setValueDateRecue(valueDateRecue);
			virementVoCB.setValueDateComRecue(valueDateComRecue);
			virementVoCB.setOperation(operation);

		} catch (Exception e) {
			logger.error("Error occurred when trying to get bank conditions.", e);
		}

		return virementVoCB;

	}
}