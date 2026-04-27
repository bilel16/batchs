package com.bna.smile.model.banqueAssurance.traitement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TarifAssVie;
import com.bna.commun.model.TraceAssuranceVieDecouvert;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.banqueAssurance.service.AssuranceVieService;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe qui permet de résilier automatiquement les adhesions concernat les comptes cloturés, les comptes transférés a
 * Cont, les comptes des personnes decédés et las personnes ayant>75 ans
 * 
 * @author SAYEB HICHEM
 * @since 06/12/2022
 */

public class ResiliationAutoRenouvellementAutoAssuranceVieDecouvertTrt extends Traitement {

	Logger logger = Logger.getLogger(ResiliationAutoRenouvellementAutoAssuranceVieDecouvertTrt.class);

	public ResiliationAutoRenouvellementAutoAssuranceVieDecouvertTrt() {
	}

	Context context = ContextHandler.getContext();

	public IValueObject perform(IValueObject vo) {

		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		int nbrResiliation = 0;
		int nbrRenouvellement = 0;
		int nbrTotalAgence = 0;
		Date dateComptable;
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
		JourneeStructure journeeStructure = new JourneeStructure();
		ParamAdhesion paramAdhesion = (ParamAdhesion) vo;
		try {

			AssuranceVieService assuranceVieService = (AssuranceVieService) context.getBean("assuranceVieService");
			/************ Recherche liste Ahesion **********/
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			// Date toDay = new Date();
			dateComptable = paramAdhesion.getDateComptable();
			criteria.add(expression.eq("codEtatAdh", Constants.COD_ETA_VALID_ASSUR_VIE)); // /*adhesion valide
			criteria.add(expression.eq("assurance.codAssAss", Constants.COD_ASS_VIE_AMIE_DECOUVERT)); // /*adhesion
																									  // valide
			criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc",
					paramAdhesion.getStructure().getCodStrcStrc()));
			criteria.add(expression.le("dateFinAdh", dateComptable));
			criteria.addOrder(Order.asc("numSeqAdh"));
			List<AdhesionAssVie> l = searchEngine.find(AdhesionAssVie.class, criteria);
			nbrTotalAgence = l.size();
			System.out.println(
					"Liste AdhesionAssVie Agence " + paramAdhesion.getStructure().getCodStrcStrc() + " : " + l.size());
			if (l != null && l.size() > 0) {
				List lres = new ArrayList();
				List lren = new ArrayList();
				int compteur = 1;
				for (Iterator it1 = l.iterator(); it1.hasNext();) {
					AdhesionAssVie adhesionAssVie = (AdhesionAssVie) it1.next();

					logger.info("AdhesionAssVie n° : " + adhesionAssVie.getNumSeqAdh());
					// /*** Execution de resiliation auto + renouvellement ***///
					int age = DateHandler.getMonthsBetween(adhesionAssVie.getClient().getPersonne().getDatNaisPers(),
							dateComptable) / 12;
					boolean vivant = (adhesionAssVie.getClient().getPersonne().getDatDecePers() == null);
					String etatCpt = adhesionAssVie.getContratCpt().getCodEtatCcpt();
					Long codeProduit = adhesionAssVie.getContratCpt().getContratCptId().getCodPrdPrd();
					if (!etatCpt.equalsIgnoreCase("V") || !vivant || (age >= 75 && codeProduit.longValue() != 103l)
							|| (age >= 80 && codeProduit.longValue() == 103l)) {// /* résiliation auto des comptes
						// cloturée,contentieux,deces,>70ans

						// /* Garniture du motif de resiliation
						adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_AUTRE);
						if (etatCpt.equalsIgnoreCase("C")) {
							adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_CLOT_CPT);
						}
						if (etatCpt.equalsIgnoreCase("T")) {
							adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_TRANSF_CONT);
						}
						if (etatCpt.equalsIgnoreCase("B")) {
							adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_DECES);
						}
						if (etatCpt.equalsIgnoreCase("S")) {
							adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_DECES);
						}
						if (etatCpt.equalsIgnoreCase("R")) {
							adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_CLOT_CPT);
						}
						if (!vivant) {
							adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_DECES);
						}
						if  (age >= 75 && codeProduit.longValue() != 103l) {
							adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_SUP_70);
						}
						
						if  (age >= 80 && codeProduit.longValue() == 103l) {
							adhesionAssVie.setCodMotfAdh(Constants.COD_MOTIF_RESIL_ASSUR_VIE_SUP_80);
						}
						paramAdhesion.setAdhesionAssVie(adhesionAssVie);

						/********************************/
						Operation operation = new Operation();
						operation.setCodOperOper(Constants.COD_OPER_RESILIATION_SOUSCRIPTION_ASSUR_VIE);
						// Tache de PEC Résiliation Assurance Vie sur Credit
						Tache tache = new Tache();
						TacheId tacheId = new TacheId();
						tacheId.setCodOperOper(operation.getCodOperOper());
						tacheId.setCodTachTach(Constants.TACHE_VALIDATION);
						tache.setTacheId(tacheId);
						Personnel personnelInit = new Personnel();
						personnelInit.setNumMatrUser("9999");
						TraceAssuranceVieDecouvert traceAssuranceVieDecouvert = new TraceAssuranceVieDecouvert();
						traceAssuranceVieDecouvert.setDateOperTrc(paramAdhesion.getDateComptable());
						traceAssuranceVieDecouvert.setAdhesionAssVie(adhesionAssVie);
						Calendar cal = Calendar.getInstance();
						traceAssuranceVieDecouvert.setDateTrcTrc(cal.getTime());
						traceAssuranceVieDecouvert.setTache(tache);
						traceAssuranceVieDecouvert.setPersonnel(personnelInit);
						traceAssuranceVieDecouvert.setMontPrmgTass(adhesionAssVie.getMontPrmgTass());
						traceAssuranceVieDecouvert.setMontPrmaTass(adhesionAssVie.getMontPrmaTass());
						traceAssuranceVieDecouvert.setMontComTass(adhesionAssVie.getMontComTass());
						Long mntRetSource = Math.round(new Double(adhesionAssVie.getMontComTass())
								* Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100);
						traceAssuranceVieDecouvert.setMontRetSrcTass(mntRetSource);
						paramAdhesion.setTraceAssuranceVieDecouvert(traceAssuranceVieDecouvert);

						// /* Execution de la resiliation
						ValidResiliationAssVieDecouvertAMITrt validResiliationAssVieDecouvertAMITrt =
								new ValidResiliationAssVieDecouvertAMITrt();
						paramAdhesion = (ParamAdhesion) validResiliationAssVieDecouvertAMITrt.exec(paramAdhesion);
						nbrResiliation = nbrResiliation + 1;

					} else { // /* si l'adhesion n'est pas resiliée
							 // /*** Renouvellement Auto ***///

						nbrRenouvellement = nbrRenouvellement + 1;

						Double quantieme = getQuantieme(new Date());
						ParamAdhesion paramAdhesionEnv = new ParamAdhesion();

						if (adhesionAssVie.getTarifAssVie() != null
								&& adhesionAssVie.getTarifAssVie().getNumSeqTass() != null
								&& adhesionAssVie.getTarifAssVie().getMontPrmgTass().intValue() != 0) {

							TarifAssVie tarifAssVie = (TarifAssVie) searchEngine.get(TarifAssVie.class,
									adhesionAssVie.getTarifAssVie().getNumSeqTass());

							if (tarifAssVie != null && tarifAssVie.getNumSeqTass() != null) {

								paramAdhesionEnv.setNouveauCpt(tarifAssVie.getAssureur().getContratCpt());
								paramAdhesionEnv.setAdhesionAssVie(adhesionAssVie);
								paramAdhesionEnv.setDateComptable(paramAdhesion.getDateComptable());
								paramAdhesionEnv.setStructure(paramAdhesion.getStructure());
								// /*** génération de la reference intersiege
								if (paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc()
										.intValue() != adhesionAssVie.getContratCpt().getStructure().getCodStrcStrc()
												.intValue()) {
									String ag = StrHandler.lpad(
											adhesionAssVie.getContratCpt().getStructure().getCodStrcStrc().toString(),
											'0', 3);
									paramAdhesionEnv.setInterSiege(
											ag + StrHandler.lpad(String.valueOf(quantieme.intValue()), '0', 3)
													+ StrHandler.lpad(String.valueOf(compteur), '0', 3));
									compteur = (compteur + 1) % 99;
								}

								/// *** Execution du prelevement ( OMP + MAJ Solde + MAJ Adhesion + Génération
								/// CRO)***///
								paramAdhesionEnv = (ParamAdhesion) assuranceVieService
										.preleverAdhesionAssuranceVieDecouvert(paramAdhesionEnv);
							}
						}

					}
				}
			}

			// /*** gerer les statistiques
			String msg = gestionStatistiqueResilRenouv(nbrResiliation, nbrRenouvellement, nbrTotalAgence);
			paramAdhesion.setMessageValidation(msg);
			paramAdhesion.setEtatValidation(true);

		} catch (

		Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			e.printStackTrace();
			StringBuffer text =
					new StringBuffer("Erreur dans ResiliationAutoRenouvellementAutoAssuranceVieDecouvertTrt : ");
			text.append(e.toString());
			erreur.setCode("200");
			erreur.setDescription(text.toString());
			erreur.setKey("ResiliationAutoRenouvellementAutoAssuranceVieDecouvertTrt");
			logger.error("Exception : ", e);
			paramAdhesion.addError(erreur);
			paramAdhesion.setMessageValidation(e.getMessage());
			paramAdhesion.setEtatValidation(false);

			throw new RuntimeException(e);
			// return (vo);
		}
		return (paramAdhesion);
	}

	public double getQuantieme(Date date) {

		int an = date.getYear();
		Date debutAnnee = new Date();
		debutAnnee.setDate(1);
		debutAnnee.setMonth(0);
		debutAnnee.setYear(an);
		return (DateHandler.getDaysBetween(debutAnnee, date) + 1);
	}

	private String gestionStatistiqueResilRenouv(int nbrResiliation, int nbrRenouvellement, int nbrTotal) {

		String message = "";
		try {
			BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
			batchStatPlacement.setCodEtatBats("V");
			batchStatPlacement.setDatSystBats(new Date());
			batchStatPlacement.setDatCompBats(new Date());
			Structure s = new Structure();
			s.setCodStrcStrc(Long.valueOf("900"));
			batchStatPlacement.setStructure(s);
			BatchMetier batchMetier = new BatchMetier();
			batchMetier.setCodBatBmet(Constants.COD_BATCH_RESIL_RENOUV_ASS_VIE);
			batchStatPlacement.setBatchMetier(batchMetier);
			message = "Total : " + nbrTotal + " ---> " + nbrResiliation + " Contrat(s) Résilié(s) et   "
					+ nbrRenouvellement + " Contrat(s) Renouvelé(s)";
			batchStatPlacement.setLibExtrBats(message);
			BatchService batchService = (BatchService) context.getBean("batchService");
			batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);

		} catch (Exception ie) {
			throw new RuntimeException(ie);
		}
		return message;
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return Constants.CODE_RESSOURCE_GENERALE;
	}

}
