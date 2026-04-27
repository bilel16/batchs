//package com.bna.smile.model.banqueAssurance.traitement;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//
//import com.bna.commun.model.AdhesionAssVie;
//import com.bna.commun.model.BatchMetier;
//import com.bna.commun.model.BatchStatPlacement;
//import com.bna.commun.model.DetailAdhesion;
//import com.bna.commun.model.JourneeStructure;
//import com.bna.commun.model.JourneeStructureId;
//import com.bna.commun.model.Structure;
//import com.bna.commun.traitements.Traitement;
//import com.bna.commun.util.ContextHandler;
//import com.bna.commun.util.DateHandler;
//import com.bna.commun.util.StrHandler;
//import com.bna.commun.vo.PrimitiveVO;
//import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
//import com.bna.smile.model.constant.Constants;
//import com.bna.smile.model.domainecommun.service.CRUDservice;
//import com.bna.smile.model.domainecommun.traitement.GetRibTrt;
//import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
//import com.bna.smile.model.domaineplacement.service.BatchService;
//import com.oxia.fwk.context.Context;
//import com.oxia.fwk.core.ICriteria;
//import com.oxia.fwk.core.IExpression;
//import com.oxia.fwk.core.ISearchEngine;
//import com.oxia.fwk.core.IValueObject;
//import com.oxia.fwk.core.ValueObject;
//import com.oxia.fwk.searchengine.SearchEngine;
//
//public class ResiliationAutoRenouvellementAutoAssVoyageTrt extends Traitement {
//
//	Logger logger = Logger.getLogger(ResiliationAutoRenouvellementAutoAssVoyageTrt.class);
//
//	public ResiliationAutoRenouvellementAutoAssVoyageTrt() {
//	}
//
//	Context context = ContextHandler.getContext();
//
//	public IValueObject perform(IValueObject vo) {
//
//		this.setSecurityFlag(false);
//		this.setVerifDomaine(false);
//		this.setCroFlag(false);
//		int nbrResiliation = 0;
//		int nbrRenouvellement = 0;
//
//		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
//		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
//		JourneeStructure journeeStructure = new JourneeStructure();
//
//		try {
//			JourneeStructureId journeeStructureId = new JourneeStructureId();
//			PlacementDAO plcDao = (PlacementDAO) context.getBean("placementDAO");
//			// / AssuranceVieService assuranceVieService = (AssuranceVieService)context.getBean("assuranceVieService");
//
//			Long lo = plcDao.isBatchExec();// /*** verifier si le batch est entrain de s'executer
//			if (lo.intValue() == 0) {
//				// /*** verouillage du batch
//				journeeStructureId.setCodStrcStrc(Long.valueOf("900"));
//				journeeStructureId.setDatJrnJrn(DateHandler.addJour(new Date(), 2));
//				journeeStructure.setJourneeStructureId(journeeStructureId);
//				journeeStructure.setCodStatJrn(Long.valueOf("0"));
//				journeeStructure.setCodSoldJrn(Long.valueOf("2"));
//				if (!journeeStructure.equals(null)) {
//					// / crudService.create(journeeStructure);
//				}
//
//				ICriteria criteria = searchEngine.createCriteria();
//				IExpression expression = searchEngine.createExpression();
//
//				Date toDay = new Date();
//				// Date D = DateHandler.addJour(getFirstDayOfQuarter(toDay),-1); ///* la premiere journee du trimestre
//				// Date D = getFirstDayOfQuarter(toDay); ///* la premiere journee du trimestre
//
//				criteria.add(expression.eq("codEtatAdh", Constants.COD_ETA_VALID_ASSUR_VIE)); // /*adhesion valide
//				// criteria.add(expression.ge("dateSystAdh",DateHandler.addJour(toDay,-200)));
//				criteria.add(expression.ge("numSeqAdh", Long.valueOf("30500")));
//				criteria.add(expression.le("numSeqAdh", Long.valueOf("32000")));
//
//				List l = searchEngine.find(AdhesionAssVie.class, criteria);
//				if (l != null && l.size() > 0) {
//					List lres = new ArrayList();
//					List lren = new ArrayList();
//					for (Iterator it1 = l.iterator(); it1.hasNext();) {
//						AdhesionAssVie adhesionAssVie = (AdhesionAssVie) it1.next();
//
//						// /*** Execution de resiliation auto + renouvellement ***///
//						int age =
//								DateHandler.getMonthsBetween(adhesionAssVie.getClient().getPersonne().getDatNaisPers(),
//										toDay) / 12;
//						boolean vivant = (adhesionAssVie.getClient().getPersonne().getDatDecePers() == null);
//						String etatCpt = adhesionAssVie.getContratCpt().getCodEtatCcpt();
//						if (!etatCpt.equalsIgnoreCase("V") || !vivant || age >= 70) {// /* résiliation auto des comptes
//																					 // cloturée,contentieux,deces,>70ans
//							ParamAdhesion paramAdhesion = new ParamAdhesion();
//							paramAdhesion.setDateComptable(toDay);
//							// /* Garniture du motif de resiliation
//							adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_AUTRE);
//							if (etatCpt.equalsIgnoreCase("C")) {
//								adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_CLOT_CPT);
//							}
//							if (etatCpt.equalsIgnoreCase("T")) {
//								adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_TRANSF_CONT);
//							}
//							if (!vivant) {
//								adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_DECES);
//							}
//							if (age >= 70) {
//								adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_SUP_70);
//							}
//							paramAdhesion.setAdhesionAssVie(adhesionAssVie);
//							// /* Execution de la resiliation
//							ValidResiliationAssVieTrt validResiliationAssVieTrt = new ValidResiliationAssVieTrt();
//							paramAdhesion = (ParamAdhesion) validResiliationAssVieTrt.exec(paramAdhesion);
//							nbrResiliation = nbrResiliation + 1;
//
//							// /* generation du fichier de Résiliation
//							// / lres.add(adhesionAssVie);
//
//						} else { // /* si l'adhesion n'est pas resiliée
//								 // /*** Renouvellement Auto ***///
//								 // modification date fin adhésion:: ajout COD_NBR_MOIS_RENOUV_ASSUR_VIE à la date fin
//								 // et à la date échéance (04.02.2011)
//							if (adhesionAssVie.getDateFinAdh().before(toDay)) { // /* verification de la date d'echeance
//								adhesionAssVie.setDateEcheAdh(DateHandler.addMonth(adhesionAssVie.getDateEcheAdh(),
//										Constants.COD_NBR_MOIS_RENOUV_ASSUR_VIE));
//								// modification date fin adhésion
//								adhesionAssVie.setDateFinAdh(DateHandler.addMonth(adhesionAssVie.getDateFinAdh(),
//										Constants.COD_NBR_MOIS_RENOUV_ASSUR_VIE));
//								adhesionAssVie.setDatRenAdh(toDay);
//								UpdateAdhesionAssVieTrt updateAdhesionAssVieTrt = new UpdateAdhesionAssVieTrt();
//								adhesionAssVie = (AdhesionAssVie) updateAdhesionAssVieTrt.exec(adhesionAssVie);
//								nbrRenouvellement = nbrRenouvellement + 1;
//								// /* generation du fichier de Résiliation
//								lren.add(adhesionAssVie);
//							}
//						}
//					}
//					// /if (nbrRenouvellement+nbrResiliation>0){///*** verifier s'il n'y a ni renouvellement ni
//					// resiliation (pas de generation de fichier)
//					genererFichier(lren, lres);
//					// /}
//
//				}
//			} else {
//				logger.info(" !!!  Le Batch (Resiliation + Renouvellement Automatique)  est en cours d execution. !!!");
//			}
//			// /*** gerer les statistiques et les fichiers
//			gestionStatistiqueResilRenouv(nbrResiliation, nbrRenouvellement);
//			return (vo);
//
//		} catch (Exception e) {
//			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
//			e.printStackTrace();
//			StringBuffer text = new StringBuffer("Erreur dans ResiliationAutoRenouvellementAutoAssVoyageTrt : ");
//			text.append(e.toString());
//			erreur.setCode("200");
//			erreur.setDescription(text.toString());
//			erreur.setKey("ResiliationAutoRenouvellementAutoTrt");
//			logger.error("Exception : ", e);
//			vo.addError(erreur);
//			throw new RuntimeException(e);
//			// return (vo);
//		}
//
//		finally {
//			if (journeeStructure.getCodSoldJrn() != null && journeeStructure.getCodSoldJrn().intValue() == 2) {
//				ICriteria critereDdeDecision = searchEngine.createCriteria();
//				IExpression expression = searchEngine.createExpression();
//				critereDdeDecision.add(expression.eq("codSoldJrn", Long.valueOf("2")));
//				List listJourneeStructure = searchEngine.find(JourneeStructure.class, critereDdeDecision);
//				// crudService.remove((JourneeStructure)listJourneeStructure.get(0));
//			}
//			System.out.println("  ");
//			System.out.println("                       Nbr de Resiliation : " + nbrResiliation
//					+ "  -  Nbr de Renouvellement : " + nbrRenouvellement);
//			System.out
//					.println("  /*************** Fin du Batch : ResiliationAutoRenouvellementAutoAssVoyageTrt (Assurance voyage)  *****************/");
//			System.out.println("  ");
//		}
//
//	}
//
//	private void genererFichier(List lren, List lres) throws IOException {
//
//		try {
//			Date toDay = new Date();
//			String f =
//					"C:\\" + "AMI_RENOUV_RESIL" + (toDay.getYear() + 1900) + "_"
//							+ StrHandler.lpad(String.valueOf(toDay.getMonth() + 1), '0', 2) + ".txt";
//			File fichier = new File(f);
//
//			PrintWriter out;
//			out = new PrintWriter(new FileWriter(fichier));
//
//			int totalNbrLignes = 0;
//			int totalMontPrimAss = 0;
//			int totalMontComm = 0;
//			int totalMontPrimeComm = 0;
//			int totalMontRetenue = 0;
//			int totalNbrLignesResil = 0;
//
//			ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
//			ICriteria criteria0 = searchEngine.createCriteria();
//			IExpression expression = searchEngine.createExpression();
//			criteria0.add(expression.ge("datFinDadh", DateHandler.addMonth(toDay, -6))); // /*adhesion résiliées ce mois
//			criteria0.add(expression.eq("codEtatDadh", Constants.COD_ETA_RESIL_ASSUR_VIE)); // /*adhesion résiliées ce
//																							// mois
//
//			lres = searchEngine.find(DetailAdhesion.class, criteria0);
//
//			if (lren != null && lren.size() > 0) { // /*** les renouvellements
//				for (Iterator it1 = lren.iterator(); it1.hasNext();) {
//					AdhesionAssVie adhesionAssVie = (AdhesionAssVie) it1.next();
//					String ligne = "";
//					DateFormat myformat = new SimpleDateFormat("ddMMyyyy");
//					String datNaisPers = myformat.format(adhesionAssVie.getClient().getPersonne().getDatNaisPers());
//					String datEffetAdh = myformat.format(toDay);
//					GetRibTrt getRibTrt = new GetRibTrt();
//					PrimitiveVO primitiveVO = (PrimitiveVO) getRibTrt.exec(adhesionAssVie.getContratCpt());
//
//					ligne =
//							"11"
//									+ StrHandler
//											.rpad(adhesionAssVie.getClient().getPersonne().getNomNomPers(), ' ', 30)
//									+ StrHandler
//											.rpad(adhesionAssVie.getClient().getPersonne().getNomPrnPers(), ' ', 30)
//									+ datNaisPers
//									+ StrHandler.lpad(adhesionAssVie.getClient().getPersonne().getNumPcePers(), '0', 8)
//									+ primitiveVO.getVString()
//									+ datEffetAdh
//									+ StrHandler.lpad(adhesionAssVie.getTarifAssVie().getMontPrmaTass().toString(),
//											'0', 7)
//									+ StrHandler.lpad(adhesionAssVie.getTarifAssVie().getMontComTass().toString(), '0',
//											7)
//									+ StrHandler.lpad(adhesionAssVie.getTarifAssVie().getMontPrmgTass().toString(),
//											'0', 7);
//					Long ll =
//							Long.valueOf(Math.round(new Double(adhesionAssVie.getTarifAssVie().getMontComTass())
//									.intValue() * Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100));
//					ligne =
//							ligne
//									+ StrHandler.lpad(ll.toString(), '0', 7)
//									+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getCodPrdPrd()
//											.toString(), '0', 4);
//					out.write(ligne);
//					out.println(); // fais un retour à la ligne dans le fichier
//
//					totalNbrLignes = totalNbrLignes + 1;
//					totalMontPrimAss = totalMontPrimAss + adhesionAssVie.getTarifAssVie().getMontPrmaTass().intValue();
//					totalMontComm = totalMontComm + adhesionAssVie.getTarifAssVie().getMontComTass().intValue();
//					totalMontPrimeComm =
//							totalMontPrimeComm + adhesionAssVie.getTarifAssVie().getMontPrmgTass().intValue();
//					totalMontRetenue = totalMontRetenue + ll.intValue();
//				}
//			}
//
//			if (lres != null && lres.size() > 0) { // /*** les résiliations
//				for (Iterator it1 = lres.iterator(); it1.hasNext();) {
//					DetailAdhesion detailAdhesion = (DetailAdhesion) it1.next();
//					AdhesionAssVie adhesionAssVie =
//							(AdhesionAssVie) searchEngine.get(AdhesionAssVie.class, detailAdhesion.getAdhesionAssVie()
//									.getNumSeqAdh());
//
//					String ligne = "";
//					DateFormat myformat = new SimpleDateFormat("ddMMyyyy");
//					String datNaisPers = myformat.format(adhesionAssVie.getClient().getPersonne().getDatNaisPers());
//					String datEffetAdh = myformat.format(toDay);
//					GetRibTrt getRibTrt = new GetRibTrt();
//					PrimitiveVO primitiveVO = (PrimitiveVO) getRibTrt.exec(adhesionAssVie.getContratCpt());
//
//					ligne =
//							"11"
//									+ StrHandler
//											.rpad(adhesionAssVie.getClient().getPersonne().getNomNomPers(), ' ', 30)
//									+ StrHandler
//											.rpad(adhesionAssVie.getClient().getPersonne().getNomPrnPers(), ' ', 30)
//									+ datNaisPers
//									+ StrHandler.lpad(adhesionAssVie.getClient().getPersonne().getNumPcePers(), '0', 8)
//									+ primitiveVO.getVString()
//									+ datEffetAdh
//									+ StrHandler.lpad(adhesionAssVie.getTarifAssVie().getMontPrmaTass().toString(),
//											'0', 7)
//									+ StrHandler.lpad(adhesionAssVie.getTarifAssVie().getMontComTass().toString(), '0',
//											7)
//									+ StrHandler.lpad(adhesionAssVie.getTarifAssVie().getMontPrmgTass().toString(),
//											'0', 7);
//					Long ll =
//							Long.valueOf(Math.round(new Double(adhesionAssVie.getTarifAssVie().getMontComTass())
//									.intValue() * Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100));
//					ligne =
//							ligne
//									+ StrHandler.lpad(ll.toString(), '0', 7)
//									+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getCodPrdPrd()
//											.toString(), '0', 4);
//					ligne = ligne + "Résilié" + adhesionAssVie.getCodMotfAdh();
//
//					out.write(ligne);
//					out.println(); // fais un retour à la ligne dans le fichier
//
//					totalNbrLignesResil = totalNbrLignesResil + 1;
//					totalNbrLignes = totalNbrLignes + 1;
//					/*
//					 * totalMontPrimAss = totalMontPrimAss -
//					 * adhesionAssVie.getTarifAssVie().getMontPrmaTass().intValue(); totalMontComm = totalMontComm -
//					 * adhesionAssVie.getTarifAssVie().getMontComTass().intValue(); totalMontPrimeComm =
//					 * totalMontPrimeComm - adhesionAssVie.getTarifAssVie().getMontPrmgTass().intValue();
//					 * totalMontRetenue = totalMontRetenue - ll.intValue();
//					 */
//				}
//			}
//			String ligneFinale =
//					"99" + StrHandler.lpad(String.valueOf(totalNbrLignes), '0', 7)
//							+ StrHandler.lpad(String.valueOf(totalMontPrimAss), '0', 15)
//							+ StrHandler.lpad(String.valueOf(totalMontComm), '0', 15)
//							+ StrHandler.lpad(String.valueOf(totalMontPrimeComm), '0', 15)
//							+ StrHandler.lpad(String.valueOf(totalMontRetenue), '0', 15)
//							+ StrHandler.lpad(String.valueOf(totalNbrLignesResil), '0', 7);
//			out.write(ligneFinale);
//			out.println();
//
//			out.close(); // /Ferme le fichier, sauvegardant ainsi les données.
//
//		} catch (IOException ie) {
//			throw new RuntimeException(ie);
//		}
//
//	}
//
//	private void gestionStatistiqueResilRenouv(int nbrResiliation, int nbrRenouvellement) {
//
//		try {
//			BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
//			batchStatPlacement.setCodEtatBats("V");
//			batchStatPlacement.setDatSystBats(new Date());
//			batchStatPlacement.setDatCompBats(new Date());
//			Structure s = new Structure();
//			s.setCodStrcStrc(Long.valueOf("900"));
//			batchStatPlacement.setStructure(s);
//			BatchMetier batchMetier = new BatchMetier();
//			batchMetier.setCodBatBmet(Constants.COD_BATCH_RESIL_RENOUV_ASS_VIE);
//			batchStatPlacement.setBatchMetier(batchMetier);
//			batchStatPlacement.setLibExtrBats(nbrResiliation + " Contrat(s) Résilié(s) et   " + nbrRenouvellement
//					+ " Contrat(s) Renouvelé(s)");
//			BatchService batchService = (BatchService) context.getBean("batchService");
//			batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);
//
//		} catch (Exception ie) {
//			throw new RuntimeException(ie);
//		}
//
//	}
//
//	public void genCroText(ValueObject vo) {
//
//	}
//
//	public String getNumeroTache(ValueObject vo) {
//		return Constants.CODE_RESSOURCE_GENERALE;
//	}
//
//
//}
