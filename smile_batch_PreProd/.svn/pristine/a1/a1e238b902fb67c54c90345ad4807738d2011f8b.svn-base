package com.bna.smile.model.banqueAssurance.traitement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.model.Assureur;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.JourneeStructureId;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.GetJourneeStructureTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.banqueAssurance.service.AssuranceVieService;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetRibTrt;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe qui permet de prélver un tarif d'assurance vie pour chaque client ayant une adhesion (1 fois par 3 mois)
 * 
 * @author Y.BOUSSEN
 * @since 07/10/2010
 */

public class PrelevementAssuranceVieTrt extends Traitement {

	Logger logger = Logger.getLogger(PrelevementAssuranceVieTrt.class);
	Context context = ContextHandler.getContext();

	public PrelevementAssuranceVieTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		int nbrPrelevement = 0;

		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
		JourneeStructure journeeStructure = new JourneeStructure();

		// gestionStatistique(nbrPrelevement);
		// System.out.println(" *** "+ "fin fichier  ***");
		try {
			JourneeStructureId journeeStructureId = new JourneeStructureId();
			PlacementDAO plcDao = (PlacementDAO) context.getBean("placementDAO");
			AssuranceVieService assuranceVieService = (AssuranceVieService) context.getBean("assuranceVieService");

			Long lo = plcDao.isBatchExec();// /*** verifier si le batch est entrain de s'executer
			if (lo.intValue() == 0) {
				// /*** verouillage du batch
				journeeStructureId.setCodStrcStrc(Long.valueOf("900"));
				journeeStructureId.setDatJrnJrn(DateHandler.addJour(new Date(), 2));
				journeeStructure.setJourneeStructureId(journeeStructureId);
				journeeStructure.setCodStatJrn(Long.valueOf("0"));
				journeeStructure.setCodSoldJrn(Long.valueOf("2"));
				if (!journeeStructure.equals(null)) {
					crudService.create(journeeStructure);
				}

				// /*** garnir la liste des assureures+les dates comptable de leurs agences
				List listeAss = searchEngine.findAll(Assureur.class);
				List listeAssureurContrat = new ArrayList();
				if (listeAss != null && listeAss.size() > 0) {
					for (Iterator it2 = listeAss.iterator(); it2.hasNext();) {
						ParamAdhesion paramAdhesion = new ParamAdhesion();
						Assureur assureur = (Assureur) it2.next();
						// /*** la derniere journée ouverte pour l'agence de l'assureur
						JourneeStructure journeeStructure0 = new JourneeStructure();
						JourneeStructureId journeeStructureId0 = new JourneeStructureId();
						journeeStructureId0.setCodStrcStrc(assureur.getContratCpt().getStructure().getCodStrcStrc());
						journeeStructure0.setJourneeStructureId(journeeStructureId0);
						GetJourneeStructureTrt getJourneeStructureTrt = new GetJourneeStructureTrt();
						journeeStructure0 = (JourneeStructure) getJourneeStructureTrt.exec(journeeStructure0);
						paramAdhesion.setDateComptable(journeeStructure0.getJourneeStructureId().getDatJrnJrn());
						paramAdhesion.setNouveauCpt(assureur.getContratCpt());
						paramAdhesion.setAssureur(assureur);
						listeAssureurContrat.add(paramAdhesion);
					}
				}

				ICriteria criteria = searchEngine.createCriteria();
				IExpression expression = searchEngine.createExpression();

				Date toDay = new Date();
				Date D = DateHandler.addJour(getFirstDayOfQuarter(toDay), -1); // /* la premiere journee du trimestre
				// Date D = getFirstDayOfQuarter(toDay); ///* la premiere journee du trimestre

				System.out.println(" ****** la premiere journee du trimestre ***    : " + DateHandler.dateToStr(D)
						+ "   ------------------");
				criteria.add(expression.eq("assurance.codAssAss", 2000l)); // /*adhesion valide
				criteria.add(expression.eq("codEtatAdh", Constants.COD_ETA_VALID_ASSUR_VIE)); // /*adhesion valide
				criteria.add(expression.ge("dateEcheAdh", toDay));
				criteria.add(expression.le("dateSystAdh", D)); // /*adhesion faite avant le debut du trimestre
				criteria.add(expression.or(expression.le("datePrelAdh", D), expression.isNull("datePrelAdh"))); // /*derniere
																												// date
																												// de
																												// prelevement
																												// avant
																												// le
																												// debut
																												// du
																												// trimestre
																												// ou
																												// jamais
																												// prelevé
				// / criteria.add(expression.ge("numSeqAdh",Long.valueOf("16500")));
				// / criteria.add(expression.le("numSeqAdh",Long.valueOf("18000")));

				// criteria.add(expression.gt("numSeqAdh",Long.valueOf("20000")));

				/************ Ajouter by Hichem ****/
				Date firstDay = DateHandler.addJour(getFirstDayOfQuarter(DateHandler.addMonth(toDay, -3)), -1); // /* la
																												// premiere
																												// journee
																												// du
																												// trimestre
																												// precedent
				Date lastDay = DateHandler.addJour(getFirstDayOfQuarter(toDay), -1); // /* la derniere journee du
																					 // trimestre

				System.out.println(" * " + firstDay + " -- " + DateHandler.dateToStr(firstDay) + " - "
						+ DateHandler.dateToStr(lastDay));
				/************************************/
				int compteur = 1;
				Double quantieme = getQuantieme(new Date());
				List l = searchEngine.find(AdhesionAssVie.class, criteria);
				if (l != null && l.size() > 0) {
					System.out.println("Liste size : "+l.size() );
					for (Iterator it1 = l.iterator(); it1.hasNext();) {
						AdhesionAssVie adhesionAssVie = (AdhesionAssVie) it1.next();

						if (adhesionAssVie.getTarifAssVie().getMontPrmgTass().intValue() != 0) { // /*** verifier si le
																								 // client est FRANCO
																								 // (exhonéré de
																								 // prelevement)
							ParamAdhesion paramAdhesionEnv = new ParamAdhesion();
							if (listeAssureurContrat != null && listeAssureurContrat.size() > 0) {
								// /*** Récuperer la date comptable de l'agence de l'assureur
								for (Iterator it3 = listeAssureurContrat.iterator(); it3.hasNext();) {
									ParamAdhesion paramAdhesion0 = (ParamAdhesion) it3.next();
									if (adhesionAssVie.getTarifAssVie().getAssureur().getNumSeqAss().intValue() == paramAdhesion0
											.getAssureur().getNumSeqAss().intValue()) {
										paramAdhesionEnv = paramAdhesion0;
										paramAdhesionEnv.setAdhesionAssVie(adhesionAssVie);
										break;
									}
								}
							}
							// /*** génération de la reference intersiege
							if (paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc().intValue() != adhesionAssVie
									.getContratCpt().getStructure().getCodStrcStrc().intValue()) {
								String ag =
										StrHandler.lpad(adhesionAssVie.getContratCpt().getStructure().getCodStrcStrc()
												.toString(), '0', 3);
								paramAdhesionEnv.setInterSiege(ag
										+ StrHandler.lpad(String.valueOf(quantieme.intValue()), '0', 3) + "M"
										+ StrHandler.lpad(String.valueOf(compteur), '0', 2));
								compteur = (compteur + 1) % 99;
							}

							// /*** Execution du prelevement (Insertion d'une OMP + MAJ Solde + MAJ Adhesion +
							// Génération CRO)***///
							adhesionAssVie =
									(AdhesionAssVie) assuranceVieService
											.PrelevementAdhesionAssuranceVie(paramAdhesionEnv);
							nbrPrelevement = nbrPrelevement + 1;
						}

					}
				}
			} else {
				logger.info(" !!!  Le Batch (Prelevement Assurance Vie)  est en cours d execution. !!!");
			}
			// /*** gerer les statistiques
			gestionStatistique(nbrPrelevement);
			return (vo);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans PrelevementAssuranceVieTrt : ");
			e.printStackTrace();
			text.append(e.toString());
			erreur.setCode("200");
			erreur.setDescription(text.toString());
			erreur.setKey("PrelevementAssuranceVieTrt");
			logger.error("Exception : ", e);
			vo.addError(erreur);
			throw new RuntimeException(e);
			// return (vo);
		}

		finally {
			if (journeeStructure.getCodSoldJrn() != null && journeeStructure.getCodSoldJrn().intValue() == 2) {
				ICriteria critereDdeDecision = searchEngine.createCriteria();
				IExpression expression = searchEngine.createExpression();
				critereDdeDecision.add(expression.eq("codSoldJrn", Long.valueOf("2")));
				List listJourneeStructure = searchEngine.find(JourneeStructure.class, critereDdeDecision);
				crudService.remove((JourneeStructure) listJourneeStructure.get(0));
			}
			System.out.println("  ");
			System.out.println("                       Nbr de Prelevement : " + nbrPrelevement);
			System.out.println("  /*************** Fin du Batch : Prelevement Assurance  *****************/");
			System.out.println("  ");
		}

	}

	public double getQuantieme(Date date) {

		int an = date.getYear();
		Date debutAnnee = new Date();
		debutAnnee.setDate(1);
		debutAnnee.setMonth(0);
		debutAnnee.setYear(an);
		return (DateHandler.getDaysBetween(debutAnnee, date) + 1);
	}

	private void gestionStatistique(int nbrPrelevement) {

		try {
			BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
			batchStatPlacement.setCodEtatBats("V");
			batchStatPlacement.setDatSystBats(new Date());
			batchStatPlacement.setDatCompBats(new Date());
			Structure s = new Structure();
			s.setCodStrcStrc(Long.valueOf("900"));
			batchStatPlacement.setStructure(s);
			BatchMetier batchMetier = new BatchMetier();
			batchMetier.setCodBatBmet(Constants.COD_BATCH_PRELEVEMENT_ASS_VIE);
			batchStatPlacement.setBatchMetier(batchMetier);
			batchStatPlacement.setLibExtrBats(nbrPrelevement + " Contrat(s) Prélevé(s)");
			BatchService batchService = (BatchService) context.getBean("batchService");
			// /** batchStatPlacement = (BatchStatPlacement)batchService.InsertBatchStatPlacement(batchStatPlacement);

			ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			Date toDay = new Date();
			Date firstDay = DateHandler.addJour(getFirstDayOfQuarter(DateHandler.addMonth(toDay, -3)), -1); // /* la
																											// premiere
																											// journee
																											// du
																											// trimestre
																											// precedent
			Date lastDay = DateHandler.addJour(getFirstDayOfQuarter(toDay), -1); // /* la derniere journee du trimestre
																				 // precedent

			criteria.add(expression.eq("codEtatAdh", Constants.COD_ETA_VALID_ASSUR_VIE)); // /*adhesion valide
			criteria.add(expression.le("dateSystAdh", lastDay)); // /*adhesion faite avant le debut du trimestre courant
			// criteria.add(expression.le("contratCp.t.contratCptId.codStrcStrc",Long.valueOf(50)));
			criteria.add(expression.or(expression.ge("datePrelAdh", lastDay),
					expression.eq("contratCpt.contratCptId.codPrdPrd", Constants.COD_COMPTE_CHEQUE_PERSONNEL))); // /*adhesion
			criteria.add(expression.eq("assurance.codAssAss", 2000l)); // /*adhesion valide																									 // faite
																												 // apres
																												 // le
																												 // debut
																												 // du
																												 // trimestre
																												 // precedent
			// criteria.add(expression.le("numSeqAdh",Long.valueOf("20000")));
			// criteria.add(expression.gt("numSeqAdh",Long.valueOf("20000")));

			// / System.out.println(" * "+firstDay+" -- "+
			// DateHandler.dateToStr(firstDay)+" - "+DateHandler.dateToStr(lastDay) );

			List l = searchEngine.find(AdhesionAssVie.class, criteria);
			if (l != null && l.size() > 0) {
				String f =
						"C:\\" + "MAGHREBIA_" + (toDay.getYear() + 1900) + "_"
								+ StrHandler.lpad(String.valueOf(toDay.getMonth() + 1), '0', 2) + ".txt";
				File fichier = new File(f);
				PrintWriter out;
				out = new PrintWriter(new FileWriter(fichier));
				// // il manque les totaux
				int totalNbrLignes = 0;
				int totalMontPrimAss = 0;
				int totalMontComm = 0;
				int totalMontPrimeComm = 0;
				int totalMontRetenue = 0;

				for (Iterator it1 = l.iterator(); it1.hasNext();) {
					AdhesionAssVie adhesionAssVie = (AdhesionAssVie) it1.next();
					String ligne = "";
					DateFormat myformat = new SimpleDateFormat("ddMMyyyy");
					String datNaisPers = myformat.format(adhesionAssVie.getClient().getPersonne().getDatNaisPers());
					String datEffetAdh = myformat.format(adhesionAssVie.getDateReelAdh());
					GetRibTrt getRibTrt = new GetRibTrt();
					PrimitiveVO primitiveVO = (PrimitiveVO) getRibTrt.exec(adhesionAssVie.getContratCpt());

					String nom = adhesionAssVie.getClient().getPersonne().getNomNomPers();
					String prenom = adhesionAssVie.getClient().getPersonne().getNomPrnPers();
					if (nom == null)
						nom = " ";
					if (prenom == null)
						prenom = " ";
					ligne =
							"11"
									+ StrHandler.rpad(nom, ' ', 30)
									+ StrHandler.rpad(prenom, ' ', 30)
									+ datNaisPers
									+ StrHandler.lpad(adhesionAssVie.getClient().getPersonne().getNumPcePers(), '0', 8)
									+ primitiveVO.getVString()
									+ datEffetAdh
									+ StrHandler.lpad(adhesionAssVie.getTarifAssVie().getMontPrmaTass().toString(),
											'0', 7)
									+ StrHandler.lpad(adhesionAssVie.getTarifAssVie().getMontComTass().toString(), '0',
											7)
									+ StrHandler.lpad(adhesionAssVie.getTarifAssVie().getMontPrmgTass().toString(),
											'0', 7);
					Long ll =
							Long.valueOf((Double.valueOf((Math.ceil((Double.valueOf(adhesionAssVie.getTarifAssVie()
									.getMontComTass()) * Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100)))))
									.longValue());
					ligne =
							ligne
									+ StrHandler.lpad(ll.toString(), '0', 7)
									+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getCodPrdPrd()
											.toString(), '0', 4);
					out.write(ligne);
					out.println(); // fais un retour à la ligne dans le fichier

					totalNbrLignes = totalNbrLignes + 1;
					totalMontPrimAss = totalMontPrimAss + adhesionAssVie.getTarifAssVie().getMontPrmaTass().intValue();
					totalMontComm = totalMontComm + adhesionAssVie.getTarifAssVie().getMontComTass().intValue();
					totalMontPrimeComm =
							totalMontPrimeComm + adhesionAssVie.getTarifAssVie().getMontPrmgTass().intValue();
					totalMontRetenue = totalMontRetenue + ll.intValue();

				}
				String ligneFinale =
						"99" + StrHandler.lpad(String.valueOf(totalNbrLignes), '0', 7)
								+ StrHandler.lpad(String.valueOf(totalMontPrimAss), '0', 15)
								+ StrHandler.lpad(String.valueOf(totalMontComm), '0', 15)
								+ StrHandler.lpad(String.valueOf(totalMontPrimeComm), '0', 15)
								+ StrHandler.lpad(String.valueOf(totalMontRetenue), '0', 15);
				out.write(ligneFinale);
				out.println();

				out.close(); // Ferme le fichier, sauvegardant ainsi les données.

			}
		} catch (IOException ie) {
			ie.printStackTrace();
			throw new RuntimeException(ie);

		}

	}

	/**
	 * methode qui retourne la premiere journee du trimestre courant
	 * 
	 * @param d
	 * @return d
	 */
	public Date getFirstDayOfQuarter(Date d) {
		Date dd = new Date();
		int mois = d.getMonth();
		if (mois >= 0 && mois < 3) {
			mois = 0;
		} else {
			if (mois > 2 && mois < 6) {
				mois = 3;
			} else {
				if (mois > 5 && mois < 9) {
					mois = 6;
				} else {
					mois = 9;
					// dd.setYear(dd.getYear()-1);
				}
			}
		}
		dd.setDate(1);
		dd.setMonth(mois);
		dd.setHours(23);
		return dd;
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return Constants.CODE_RESSOURCE_GENERALE;
	}

}
