package com.bna.smile.model.virement.traitement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.JourneeSGMT;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.ParametreBNA;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class VirementAgenceTrt extends Traitement {

	public VirementAgenceTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	VirementService virementService = (VirementService) context.getBean("iVirementService");
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	List<File> listeFiles = new ArrayList<File>();
	SimpleDateFormat formaterHeureSGMT = new SimpleDateFormat("HHmm");
	SimpleDateFormat formaterDateSGMT = new SimpleDateFormat("dd/MM/yy");

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		Structure agence = virementVo.getStructure();
		Date dateComptableAgence = virementVo.getDateComptableAgence();

		logger.info(" Agence = " + agence.getCodStrcStrc() + " ==> dateComptableAgence = "
				+ formaterDate.format(dateComptableAgence));
		listeFiles.clear();
		this.setCroFlag(false);
		ISearchEngine searchEngine = (ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");

		try {
			List<String> listVirementEcheances = new ArrayList<String>();
			 /*** recherche des virements a echéance non encore traité pour cette agence  */
			 listVirementEcheances.addAll(virementGlobalDAO.getListeVirementsEcheances(agence.getCodStrcStrc(),
			 dateComptableAgence));

			// /*** recherche des virements cash
			//listVirementEcheances.addAll(virementGlobalDAO.getListeVirementsCash(agence.getCodStrcStrc(),
			//		dateComptableAgence));

			Set<GlobalVirement> listeGlobalVirementsFinal = new HashSet<GlobalVirement>();

			logger.info("listVirementEcheances.size =  " + listVirementEcheances.size());

			boolean trouver = false;

			if (listVirementEcheances != null && listVirementEcheances.size() > 0) {

				for (String numSeqGvir : listVirementEcheances) {

					Long[] etatsVirements =
							{ Constants.COD_ETAT_VIREMENT_ATTENTE, Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN,
									Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF };

					ICriteria criteria = searchEngine.createCriteria();
					IExpression expression = searchEngine.createExpression();

					criteria.add(expression.eq("detailVirementId.numSeqGvir", numSeqGvir));
					criteria.add(expression.le("datEchDetv", dateComptableAgence));
					criteria.add(expression.in("etaDetvDetv", etatsVirements));

					List<DetailVirement> list_Details =
							new ArrayList<DetailVirement>(searchEngine.find(DetailVirement.class, criteria));

					if (list_Details.size() > 0) {

						GlobalVirement globalVirement = new GlobalVirement();
						globalVirement.setNumSeqGvir(numSeqGvir);
						listeGlobalVirementsFinal.add(globalVirement);

					}

				}

				// / --------------Fin Set List Global Virement A Executer -------------------------///

			}

			// / --------------Debut Traiter Global Virement A Executer -------------------------------///

			logger.info(" listeGlobalVirementsFinal.size = " + listeGlobalVirementsFinal.size());

			if (listeGlobalVirementsFinal != null && listeGlobalVirementsFinal.size() > 0) {

				// ********************** Validite du journee SGMT ******************//
				boolean etatEnvoiSGMT = false;
				String heureSGMT = formaterHeureSGMT.format(new Date());
				String dateSGMT = formaterDate.format(dateComptableAgence);
				String heureDebutSGMT;
				String heureFinSGMT;
				logger.info("heureSGMT : " + heureSGMT);

				List<ParametreBNA> list_ParametreBNA =
						new ArrayList<ParametreBNA>(virementGlobalDAO.getParametreSGMT());

				if (list_ParametreBNA.size() > 0) {

					ParametreBNA parametreBNA = list_ParametreBNA.get(0);
					heureDebutSGMT = StrHandler.lpad(parametreBNA.getNumHdjJrn() + "", '0', 4);
					heureFinSGMT = StrHandler.lpad(parametreBNA.getNumHfjJrn() + "", '0', 4);

					if ((new Short(heureSGMT).shortValue() >= new Short(heureDebutSGMT).shortValue())
							&& (new Short(heureSGMT).shortValue() < new Short(heureFinSGMT).shortValue())) {

						// ********************** Validite du journee SGMT ******************//

						try {
							ICriteria criteriaSGMT = searchEngine.createCriteria();

							criteriaSGMT.add(expression.eq("datDatJrn", formaterDate.parse(dateSGMT)));

							List<JourneeSGMT> list_JourneeSGMT =
									new ArrayList<JourneeSGMT>(searchEngine.find(JourneeSGMT.class, criteriaSGMT));

							if (list_JourneeSGMT.size() > 0) {

								JourneeSGMT journeeSGMT = list_JourneeSGMT.get(0);
								if ((new Short(heureSGMT).shortValue() >= new Short(journeeSGMT.getNumHdjJrn())
										.shortValue())
										&& (new Short(heureSGMT).shortValue() < new Short(journeeSGMT.getNumHfjJrn())
												.shortValue())) {

									etatEnvoiSGMT = true;

								}
							}
						} catch (DataAccessException e) {
							etatEnvoiSGMT = false;
							logger.error(e.getMessage());
						}

					}

				}

				for (GlobalVirement globalVirementObj : listeGlobalVirementsFinal) {

					globalVirementObj =
							(GlobalVirement) searchEngine.get(GlobalVirement.class, globalVirementObj.getNumSeqGvir());

					logger.info("\n  /***** Num Seq GVIR = " + globalVirementObj.getNumSeqGvir() + "***/   \n");

					// / Verfiier la validité du RIB DO

					VirementVo virementVo2 = new VirementVo();

					virementVo2.setBoolValiderContratCptDO(true);
					virementVo2.setStructure(agence);
					virementVo2.setDateComptableAgence(dateComptableAgence);
					virementVo2.setDetailVirement(null);
					virementVo2.setGlobalVirement(globalVirementObj);
					virementVo2.setStrRib("");
					VerifierValiditerRibDoTrt verifierValiditerRibDo = new VerifierValiditerRibDoTrt();
					virementVo2 = (VirementVo) verifierValiditerRibDo.exec(virementVo2);

					// / False : Invalide /// true : valide
					// virementVo.setBoolValiderContratCptDO(boolVerfierRibDO);

					if (virementVo2.isBoolValiderContratCptDO() == true) {
						logger.info("\n  /***** RIB DO Valide  ***/   \n");

						// / ----------------- Traitement Batch ----------------------- ///

						// / ------------------ Virement Permanent --------------------- ///

						if (globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_PERMANENT
								.longValue()) {
							virementVo2.setEtatEnvoiSGMT(etatEnvoiSGMT);
							virementVo2 = (VirementVo) virementService.traitementVirPermanent(virementVo2);

							// **** Recupuration du fichier ****//

							listeFiles.addAll(virementVo2.getListeFile());

						}

						// / ------------------ Virement Ponctuel / Masse --------------------- ///

						else if ((globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_PONCTUEL
								.longValue() || globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_MASSE
								.longValue())) {
							virementVo2.setEtatEnvoiSGMT(etatEnvoiSGMT);
							virementVo2 = (VirementVo) virementService.traitementVirPonctuelMasse(virementVo2);

							// **** Recupuration du fichier ****//

							listeFiles.addAll(virementVo2.getListeFile());
						}

						// / ----------------------------------------------------------- ///

					} else {
						logger.info("\n  /***** RIB DO  Non Valide ***/   \n");

						// /-------------------- Rejet Du Virement Global -------------- ///

						virementVo2 = (VirementVo) virementService.rejeterGlobalVirement(virementVo2);

						// /-------------------------------------------------------------- ///

					}

				}
				// ****************** Gestion des Statitiques *************************///

				long nbreVirementExecutes = 0;
				long nbreVirementRejetes = 0;
				long nbreVirementdecales = 0;
				long nbreVirementdecalesDefinitif = 0;

				for (GlobalVirement globalVirement : listeGlobalVirementsFinal) {

					Set<DetailVirement> listeVirements = new HashSet<DetailVirement>();

					// ************* Refind Liste Details ************//

					ICriteria criteriaDetails = searchEngine.createCriteria();

					criteriaDetails.add(expression.eq("detailVirementId.numSeqGvir", globalVirement.getNumSeqGvir()));
					criteriaDetails.add(expression.le("datEchDetv", dateComptableAgence));
					listeVirements.addAll(searchEngine.find(DetailVirement.class, criteriaDetails));

					if (listeVirements.size() > 0) {
						for (DetailVirement detailVirement : listeVirements) {

							if ((detailVirement.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_EXECUTER)
									&& (detailVirement.getDatEchDetv() != null)
									&& (detailVirement.getDatEchDetv().compareTo(dateComptableAgence) <= 0
											&& (detailVirement.getDatExecDetv() != null) && (detailVirement
											.getDatExecDetv().compareTo(dateComptableAgence) == 0))) {

								nbreVirementExecutes++;

							} else if ((detailVirement.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_REJETER)
									&& (detailVirement.getDatEchDetv() != null)
									&& (detailVirement.getDatEchDetv().compareTo(dateComptableAgence) <= 0
											&& (detailVirement.getDatExecDetv() != null) && (detailVirement
											.getDatExecDetv().compareTo(dateComptableAgence) == 0))) {

								nbreVirementRejetes++;
							} else if ((detailVirement.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN)
									&& (detailVirement.getDatEchDetv() != null)
									&& (detailVirement.getDatEchDetv().compareTo(dateComptableAgence) <= 0)
									&& (detailVirement.getDatExecDetv() != null)
									&& (detailVirement.getDatExecDetv().compareTo(dateComptableAgence) == 0)) {

								nbreVirementdecales++;

							} else if ((detailVirement.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF)
									&& (detailVirement.getDatEchDetv() != null)
									&& (detailVirement.getDatEchDetv().compareTo(dateComptableAgence) <= 0)
									&& (detailVirement.getDatExecDetv() != null)
									&& (detailVirement.getDatExecDetv().compareTo(dateComptableAgence) == 0)) {

								nbreVirementdecalesDefinitif++;
							}

						}
					}

				}

				gestionStatistique(dateComptableAgence, agence, nbreVirementExecutes, nbreVirementRejetes,
						nbreVirementdecales, nbreVirementdecalesDefinitif);
				String messageStatistique = "";
				messageStatistique += "Nbre des virements exécutés avec sucées : " + nbreVirementExecutes + "  ; \n ";
				if (nbreVirementdecales > 0) {
					messageStatistique += "décalés : " + nbreVirementdecales + "  ; \n ";
				}
				if (nbreVirementdecalesDefinitif > 0) {
					messageStatistique += "décalés auto definitif : " + nbreVirementdecalesDefinitif + "  ; \n";
				}
				if (nbreVirementRejetes > 0) {
					messageStatistique += "rejetés : " + nbreVirementRejetes + "\n";
				}
				virementVo.setEtatEnregistrement(true);
				virementVo.setMessageValidation(messageStatistique);
				// /////////////////////////////////////////

			} else {

				virementVo.setEtatEnregistrement(true);
				virementVo.setMessageValidation("Aucun virement à exécuter pour cette agence");
			}

			// --------------Fin Traiter Global Virement A Executer -------------------------------///

		} catch (NullPointerException e) {
			if (listeFiles.size() > 0) {

				for (File file : listeFiles) {

					if (file.exists() == true) {
						file.delete();
						logger.info("file.delete() :" + file.delete());
					}

				}

			}
			logger.error("Exception : ", e);
			gestionException(dateComptableAgence, agence, e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.info("listeFiles :" + listeFiles.size());
			if (listeFiles.size() > 0) {

				for (File file : listeFiles) {

					if (file.exists() == true) {
						file.delete();
						logger.info("file.delete() :" + file.delete());
					}

				}

			}
			logger.error("Exception : ", e);
			gestionException(dateComptableAgence, agence, e);
			virementVo.setEtatEnregistrement(false);
			throw new RuntimeException(e);

		}
		return vo;
	}

	private void gestionStatistique(Date dateComptable, Structure agence, long nbrVirExecutes, long nbrVirRejetes,
			long nbreVirDecales, long nbreVirDecalesDef) {

		String messageStatistique = "";

		messageStatistique = "L’exécution a été effectuée avec sucées ! \n";
		messageStatistique += "Nombre  des virements exécutés  avec sucées  = " + nbrVirExecutes + "  ; \n ";

		if (nbreVirDecales > 0) {
			messageStatistique += "Nombre  des virements décalés  = " + nbreVirDecales + "  ; \n ";
		}

		if (nbreVirDecalesDef > 0) {
			messageStatistique += "Nombre  des virements décalés auto definitif  =  " + nbreVirDecalesDef + "  ; \n";
		}

		if (nbrVirRejetes > 0) {
			messageStatistique += "Nombre  des virements rejetés  =  " + nbrVirRejetes + "\n";
		}

		BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
		batchStatPlacement.setCodEtatBats("V");
		batchStatPlacement.setDatSystBats(new Date());
		batchStatPlacement.setDatCompBats(dateComptable);
		batchStatPlacement.setStructure(agence);
		batchStatPlacement.setLibExtrBats(messageStatistique);
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_VIREMENT_AECHEANCE);
		batchStatPlacement.setBatchMetier(batchMetier);
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);
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

}
