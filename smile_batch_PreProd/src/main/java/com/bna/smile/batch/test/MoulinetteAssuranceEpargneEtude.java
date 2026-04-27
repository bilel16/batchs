package com.bna.smile.batch.test;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.TraceBatch;
import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.assVieEpargneEtude.dao.AssVieEpargneDAO;
import com.bna.smile.model.assVieEpargneEtude.model.ContratEpargneEtudeVo;
import com.bna.smile.model.assVieEpargneEtude.model.ListFaiezResiduVo;
import com.bna.smile.model.assVieEpargneEtude.model.ListFaiezVo;
import com.bna.smile.model.assVieEpargneEtude.model.TraceAssuranceFaiezVo;
import com.bna.smile.model.assVieEpargneEtude.traitement.PrelevementDeuxiemeTrancheEpargneEtudeResiduTrt;
import com.bna.smile.model.assVieEpargneEtude.traitement.PrelevementDeuxiemeTrancheEpargneEtudeTrt;
import com.bna.smile.model.assVieEpargneEtude.traitement.PrelevementPremiereTrancheEpargneEtudeResiduTrt;
import com.bna.smile.model.assVieEpargneEtude.traitement.PrelevementPremiereTrancheEpargneEtudeTrt;
import com.bna.smile.model.assVieEpargneEtude.traitement.PrelevementTroisiemeTrancheEpargneEtudeResiduTrt;
import com.bna.smile.model.assVieEpargneEtude.traitement.PrelevementTroisiemeTrancheEpargneEtudeTrt;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.BatchVo;
import com.bna.smile.model.domainecommun.traitement.GetListTraceBatchTrt;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;

public class MoulinetteAssuranceEpargneEtude {

	private static final Log LOGGER = LogFactory.getLog(MoulinetteAssuranceEpargneEtude.class.getSimpleName());

	public void perform() {
		ContextCROHandler.setContext(ContextHandler.getContext());
		Context context = ContextCROHandler.getContext();

		BatchVo batchVo = new BatchVo();
		GetListTraceBatchTrt getListTraceBatchTrt = new GetListTraceBatchTrt();
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_PAIEMENT_ASS_FAIEZ);
		batchVo.setBatchMetier(batchMetier);
		batchVo.setEtatTrace(Long.valueOf("0"));
		batchVo.setDateBatch(DateHandler.strToDate(DateHandler.dateToStr(new Date())));
		batchVo = (BatchVo) getListTraceBatchTrt.exec(batchVo);
		if (batchVo.getListeTraceBatch() != null && batchVo.getListeTraceBatch().size() > 0) {
			System.out.println("Une autre moulinette prélèvement Assurance Epargne Etude FAIEZ est en cours d'exécution");
			LOGGER.error("Une autre moulinette prélèvement Assurance Epargne Etude FAIEZ est en cours d'exécution");
			return;
		} else {
			/* Insertion trace */
			SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm:ss");

			BatchService batchService = (BatchService) context.getBean("batchService");
			TraceBatch traceBatch = new TraceBatch();
			BatchMetier batchMetierAssFaiez = new BatchMetier();
			batchMetierAssFaiez.setCodBatBmet(Constants.COD_BATCH_PAIEMENT_ASS_FAIEZ);
			traceBatch.setBatchMetier(batchMetierAssFaiez);
			traceBatch.setCodeEtatBat(Long.valueOf("0"));
			traceBatch.setDateExecBat(new Date());
			traceBatch.setTimeDebBat(heureFormat.format(new Date()));
			traceBatch = (TraceBatch) batchService.InsertTraceBatch(traceBatch);

			if (traceBatch.getNumSeqTrc() != null) {
				System.out.println("Moulinette prélèvement Assurance Epargne Etude FAIEZ en cours de traitement");
				LOGGER.info("Moulinette prélèvement Assurance Epargne Etude FAIEZ en cours de traitement");
				AssVieEpargneDAO assVieEpargneDAO = (AssVieEpargneDAO) context.getBean("assVieEpargneDAO");
				List<TraceAssuranceFaiezVo> listTranche1Residue = new ArrayList<TraceAssuranceFaiezVo>();
				List<ContratEpargneEtudeVo> listTranche1APayer = new ArrayList<ContratEpargneEtudeVo>();
				List<ContratEpargneEtudeVo> listTranche1Impaye = new ArrayList<ContratEpargneEtudeVo>();
				List<TraceAssuranceFaiezVo> listTranche2Residue = new ArrayList<TraceAssuranceFaiezVo>();
				List<ContratEpargneEtudeVo> listTranche2APayer = new ArrayList<ContratEpargneEtudeVo>();
				List<ContratEpargneEtudeVo> listTranche2Impaye = new ArrayList<ContratEpargneEtudeVo>();
				List<TraceAssuranceFaiezVo> listTranche3Residue = new ArrayList<TraceAssuranceFaiezVo>();
				List<ContratEpargneEtudeVo> listTranche3APayer = new ArrayList<ContratEpargneEtudeVo>();
				List<ContratEpargneEtudeVo> listTranche3Impaye = new ArrayList<ContratEpargneEtudeVo>();
				ListFaiezVo listPaiementTranche1 = new ListFaiezVo();
				ListFaiezVo listPaiementTranche2 = new ListFaiezVo();
				ListFaiezVo listPaiementTranche3 = new ListFaiezVo();
				ListFaiezResiduVo listPaiementResiduTranche1 = new ListFaiezResiduVo();
				ListFaiezResiduVo listPaiementResiduTranche2 = new ListFaiezResiduVo();
				ListFaiezResiduVo listPaiementResiduTranche3 = new ListFaiezResiduVo();
				PrelevementPremiereTrancheEpargneEtudeTrt prelevementPremiereTrancheEpargneEtudeTrt = new PrelevementPremiereTrancheEpargneEtudeTrt();
				PrelevementDeuxiemeTrancheEpargneEtudeTrt prelevementDeuxiemeTrancheEpargneEtudeTrt = new PrelevementDeuxiemeTrancheEpargneEtudeTrt();
				PrelevementTroisiemeTrancheEpargneEtudeTrt prelevementTroisiemeTrancheEpargneEtudeTrt = new PrelevementTroisiemeTrancheEpargneEtudeTrt();
				PrelevementPremiereTrancheEpargneEtudeResiduTrt prelevementPremiereTrancheEpargneEtudeResiduTrt = new PrelevementPremiereTrancheEpargneEtudeResiduTrt();
				PrelevementDeuxiemeTrancheEpargneEtudeResiduTrt prelevementDeuxiemeTrancheEpargneEtudeResiduTrt = new PrelevementDeuxiemeTrancheEpargneEtudeResiduTrt();
				PrelevementTroisiemeTrancheEpargneEtudeResiduTrt prelevementTroisiemeTrancheEpargneEtudeResiduTrt = new PrelevementTroisiemeTrancheEpargneEtudeResiduTrt();
				try {
					listTranche1Residue = assVieEpargneDAO.getListeTrancheResidueFaiez(1);
					System.out.println("+++ Liste première tranche résidue pour paiement :"+listTranche1Residue.size());
					LOGGER.info("+++ Liste première tranche résidue pour paiement :"+listTranche1Residue.size());
					if(listTranche1Residue.size() > 0) {
						listPaiementResiduTranche1.setList(listTranche1Residue);
						prelevementPremiereTrancheEpargneEtudeResiduTrt.perform(listPaiementResiduTranche1);
					}
					listTranche2Residue = assVieEpargneDAO.getListeTrancheResidueFaiez(2);
					System.out.println("+++ Liste deuxième tranche résidue pour paiement :"+listTranche2Residue.size());
					LOGGER.info("+++ Liste deuxième tranche résidue pour paiement :"+listTranche2Residue.size());
					if(listTranche2Residue.size() > 0) {
						listPaiementResiduTranche2.setList(listTranche2Residue);
						prelevementDeuxiemeTrancheEpargneEtudeResiduTrt.perform(listPaiementResiduTranche2);
					}
					listTranche3Residue = assVieEpargneDAO.getListeTrancheResidueFaiez(3);
					System.out.println("+++ Liste troisième tranche résidue pour paiement :"+listTranche3Residue.size());
					LOGGER.info("+++ Liste troisième tranche résidue pour paiement :"+listTranche3Residue.size());
					if(listTranche3Residue.size() > 0) {
						listPaiementResiduTranche3.setList(listTranche3Residue);
						prelevementTroisiemeTrancheEpargneEtudeResiduTrt.perform(listPaiementResiduTranche3);
					}
					listTranche1APayer = assVieEpargneDAO.getListeContratEpargneEtudePaye(1, getDateDebut(1), getDateFin(1));
					System.out.println("+++ Liste première tranche à payer :"+listTranche1APayer.size());
					LOGGER.info("+++ Liste première tranche à payer :"+listTranche1APayer.size());
					if(listTranche1APayer.size() > 0) {
						listPaiementTranche1.setList(listTranche1APayer);
						prelevementPremiereTrancheEpargneEtudeTrt.perform(listPaiementTranche1);
					}
					listTranche2APayer = assVieEpargneDAO.getListeContratEpargneEtudePaye(2, getDateDebut(2), getDateFin(2));
					System.out.println("+++ Liste deuxième tranche à payer :"+listTranche2APayer.size());
					LOGGER.info("+++ Liste deuxième tranche à payer :"+listTranche2APayer.size());
					if(listTranche2APayer.size() > 0) {
						listPaiementTranche2.setList(listTranche2APayer);
						prelevementDeuxiemeTrancheEpargneEtudeTrt.perform(listPaiementTranche2);
					}
					listTranche3APayer = assVieEpargneDAO.getListeContratEpargneEtudePaye(3, getDateDebut(3), getDateFin(3));
					System.out.println("+++ Liste troisième tranche à payer :"+listTranche3APayer.size());
					LOGGER.info("+++ Liste troisième tranche à payer :"+listTranche3APayer.size());
					if(listTranche3APayer.size() > 0) {
						listPaiementTranche3.setList(listTranche3APayer);
						prelevementTroisiemeTrancheEpargneEtudeTrt.perform(listPaiementTranche3);
					}
					listTranche1Impaye = assVieEpargneDAO.getListeContratEpargneEtudeImpaye(1, getDateDebut(1), getDateFin(1));
					listTranche2Impaye = assVieEpargneDAO.getListeContratEpargneEtudeImpaye(2, getDateDebut(2), getDateFin(2));
					listTranche3Impaye = assVieEpargneDAO.getListeContratEpargneEtudeImpaye(3, getDateDebut(3), getDateFin(3));
					System.out.println("--- Liste première tranche impayé :"+listTranche1Impaye.size());
					LOGGER.info("--- Liste première tranche impayé :"+listTranche1Impaye.size());
					if(listTranche1Impaye.size() > 0) {
						for (ContratEpargneEtudeVo contratFaiez1Vo : listTranche1Impaye) {
							assVieEpargneDAO.InsertTraceTrancheFaiezImpayé(contratFaiez1Vo, 1);
						}
					}
					System.out.println("--- Liste deuxième tranche impayé :"+listTranche2Impaye.size());
					LOGGER.info("--- Liste deuxième tranche impayé :"+listTranche2Impaye.size());
					if(listTranche2Impaye.size() > 0) {
						for (ContratEpargneEtudeVo contratFaiez2Vo : listTranche2Impaye) {
							assVieEpargneDAO.InsertTraceTrancheFaiezImpayé(contratFaiez2Vo, 2);
						}
					}
					System.out.println("--- Liste troisième tranche impayé :"+listTranche3Impaye.size());
					LOGGER.info("--- Liste troisième tranche impayé :"+listTranche3Impaye.size());
					if(listTranche3Impaye.size() > 0) {
						for (ContratEpargneEtudeVo contratFaiez3Vo : listTranche3Impaye) {
							assVieEpargneDAO.InsertTraceTrancheFaiezImpayé(contratFaiez3Vo, 3);
						}
					}
				} catch (Exception e) {
					LOGGER.error("MoulinetteAssuranceEpargneEtude", e);
					return;
				}
				TraceBatch traceBatch2 = new TraceBatch();
				traceBatch2.setNumSeqTrc(traceBatch.getNumSeqTrc());
				traceBatch2.setCodeEtatBat(Long.valueOf(1));
				traceBatch2.setTimeFinBat(heureFormat.format(new Date()));
				traceBatch2 = (TraceBatch) batchService.InsertTraceBatch(traceBatch2);
				System.out.println("Moulinette prélèvement Assurance Epargne Etude FAIEZ terminée avec succès");
				System.out.println("*** FIN ***");
				LOGGER.info("Moulinette prélèvement Assurance Epargne Etude FAIEZ terminée avec succès");
				LOGGER.info("*** FIN ***");
			}
		}

	}

	public String getDateDebut(int tranche) {

		Calendar dateDebut = Calendar.getInstance();
		dateDebut.set(Calendar.DAY_OF_MONTH, 1);
		dateDebut.set(Calendar.MONTH, Calendar.JULY);
		String dateD = null;
		SimpleDateFormat formatDateFile = new SimpleDateFormat("dd/MM/yyyy");

		if(tranche == 1) {
			dateDebut.add(Calendar.YEAR, -2);
			dateD = formatDateFile.format(dateDebut.getTime());
		} else if(tranche == 2) {
			dateDebut.add(Calendar.YEAR, -3);
			dateD = formatDateFile.format(dateDebut.getTime());
		} else if(tranche == 3) {
			dateDebut.add(Calendar.YEAR, -4);
			dateD = formatDateFile.format(dateDebut.getTime());
		}
		return dateD;
	}
	
	public String getDateFin(int tranche) {

		Calendar dateFin = Calendar.getInstance();
		dateFin.set(Calendar.DAY_OF_MONTH, 30);
		dateFin.set(Calendar.MONTH, Calendar.JUNE);
		String dateF = null;
		SimpleDateFormat formatDateFile = new SimpleDateFormat("dd/MM/yyyy");

		if(tranche == 1) {
			dateFin.add(Calendar.YEAR, -1);
			dateF = formatDateFile.format(dateFin.getTime());
		} else if(tranche == 2) {
			dateFin.add(Calendar.YEAR, -2);
			dateF = formatDateFile.format(dateFin.getTime());
		} else if(tranche == 3) {
			dateFin.add(Calendar.YEAR, -3);
			dateF = formatDateFile.format(dateFin.getTime());
		}
		return dateF;
	}

}
