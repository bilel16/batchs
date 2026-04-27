package com.bna.smile.batch.moulinette;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.JourneeStructureBatchId;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceBatch;
import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.test.BatchFrame;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domainecompensation.gestionrejet.service.GestionRejetService;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinettePositionCompensationEffet extends AbstractJob {

	private static final Logger logger = Logger
			.getLogger(MoulinettePositionCompensationEffet.class);
	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	ICriteria criteria = searchEngine.createCriteria();
	ICriteria criteriaCpt = searchEngine.createCriteria();
	ICriteria criteriaChq = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();
	Date dateComptableAgence = null;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	GestionRejetService gestionRejetService = (GestionRejetService) context
			.getBean("gestionRejetService");
	public BatchFrame mainFrame;

	public MoulinettePositionCompensationEffet(BatchFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void perform() {
		Structure agence = new Structure();
		Structure strc = null;
		try {

			// trace Execution Lecture
			SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm:ss");

			BatchService batchService = (BatchService) context
					.getBean("batchService");
			TraceBatch traceBatch = new TraceBatch();
			BatchMetier batchMetierVirement = new BatchMetier();
			batchMetierVirement
					.setCodBatBmet(Constants.COD_BATCH_COMPENSATION_EFFET);
			traceBatch.setBatchMetier(batchMetierVirement);
			traceBatch.setCodeEtatBat(Long.valueOf("2"));
			traceBatch.setDateExecBat(new Date());
			traceBatch.setTimeDebBat(heureFormat.format(new Date()));
			traceBatch = (TraceBatch) batchService.InsertTraceBatch(traceBatch);

			fixerUser();
			IValueObject vo = new ValueObject();

			CompensationDAO compensationDAO = (CompensationDAO) context
					.getBean("compensationDAO");
			List listAgences = compensationDAO
					.getListAgencesCompensationPilote();
			ListOrderedMap ListAg = null;
			
			if (listAgences != null && listAgences.size() > 0) {
				for (Iterator it1 = listAgences.iterator(); it1.hasNext();) {
					ListAg = (ListOrderedMap) it1.next();

					if ((ListAg.getValue(0)).toString() != null) {
						agence.setCodStrcStrc(Long.valueOf(ListAg.getValue(0)
								.toString()));
					}
					if ((ListAg.getValue(1)).toString() != null) {
						ListAg.getValue(1);
						dateComptableAgence = DateHandler.strToDate(ListAg
								.getValue(1).toString());

					}

					 strc = compensationDAO.findStructure(new Long(
							(ListAg.getValue(0)).toString()));

					if (strc.getTypeStructure().getCodTstrTstr().equals(1L)
							|| strc.getTypeStructure().getCodTstrTstr()
									.equals(6L)) {
						System.out.println("Compensation Structure :"
								+ agence.getCodStrcStrc()
								+ " Date Compensation :"
								+ sdf.format(dateComptableAgence));

						// // tester JourneeStructureBatch
						JourneeStructureBatch journeeStructureBatch = new JourneeStructureBatch();
						JourneeStructureBatch journeeStructureBatchRetour = new JourneeStructureBatch();
						JourneeStructureBatchId journeeStructureBatchId = new JourneeStructureBatchId();
						journeeStructureBatchId.setCodStrcStrc(agence
								.getCodStrcStrc());
						journeeStructureBatchId
								.setDatJrnJrn(dateComptableAgence);
						journeeStructureBatchId
								.setCodBatBmet(Constants.COD_BATCH_COMPENSATION_EFFET);
						journeeStructureBatch
								.setJourneeStructureBatchId(journeeStructureBatchId);

						journeeStructureBatchRetour = (JourneeStructureBatch) batchService
								.getJourneeStructureBatch(journeeStructureBatch);

						if (journeeStructureBatchRetour == null) {

							journeeStructureBatchId.setCodStrcStrc(agence
									.getCodStrcStrc());
							journeeStructureBatchId
									.setDatJrnJrn(dateComptableAgence);
							journeeStructureBatchId
									.setCodBatBmet(Constants.COD_BATCH_COMPENSATION_EFFET);
							journeeStructureBatch
									.setJourneeStructureBatchId(journeeStructureBatchId);
							journeeStructureBatch.setCodStatJsb(0L);
							crudService.create(journeeStructureBatch);

						}
						journeeStructureBatchRetour = (JourneeStructureBatch) batchService
								.getJourneeStructureBatch(journeeStructureBatch);
						CompensationEffetVo compensationVo = new CompensationEffetVo();
						compensationVo.setStructure(agence);
						compensationVo.setDateComptable(dateComptableAgence);

						SwingInfoVo infoVo = new SwingInfoVo();
						infoVo.setStructure("" + strc.getCodStrcStrc());
						infoVo.setEtat(Constants.STATUT_EN_COURS_POS);
						infoVo.setDateComptable(sdf.format(dateComptableAgence));
						mainFrame.addOrUpdateEtat(infoVo);
						mainFrame.updateInfo("Agence "
								+ strc.getCodStrcStrc() + " "
								+ Constants.STATUT_EN_COURS_POS);
						
						compensationVo = (CompensationEffetVo) gestionRejetService
								.positionCompensationEffet(compensationVo);
						// MAJ JourneeStructureBatch
						journeeStructureBatch.setDatCloJsb(new Date());
						journeeStructureBatch.setCodStatJsb(Long.valueOf("1"));
						journeeStructureBatch = (JourneeStructureBatch) batchService
								.updateJourneeStructureBatch(journeeStructureBatch);
						infoVo = new SwingInfoVo();
						infoVo.setStructure("" + strc.getCodStrcStrc());
						infoVo.setEtat(Constants.STATUT_EN_TERMINE);
						infoVo.setDateComptable(sdf.format(dateComptableAgence));
						mainFrame.addOrUpdateEtat(infoVo);
						mainFrame.updateInfo("Agence "
								+ strc.getCodStrcStrc() + " Position "
								+ Constants.STATUT_EN_TERMINE+"e");
					}
				}
				logger.debug("FIN POSITION COMPENSATION EFFET:"
						+ agence.getCodStrcStrc());
				mainFrame.getBtnExcuter().setEnabled(true);
				mainFrame.getBtnPosition().setEnabled(true);
				mainFrame.getEnd_exec_label().setText(
						"Position Effet exécutée avec succès");

			} else {
				logger.debug("La liste des agences est vide.");
			}
			if (traceBatch.getNumSeqTrc() != null) {

				TraceBatch traceBatch2 = new TraceBatch();
				traceBatch2.setNumSeqTrc(traceBatch.getNumSeqTrc());
				traceBatch2.setCodeEtatBat(Long.valueOf(2));
				traceBatch2.setTimeFinBat(heureFormat.format(new Date()));

				traceBatch2 = (TraceBatch) batchService
						.InsertTraceBatch(traceBatch2);

				
			}
		} catch (Exception e) {
			logger.fatal("**** Exception *** MoulinettePositionCompensation *** : "
					+ this.getClass());
			e.printStackTrace();
			mainFrame.getBtnExcuter().setEnabled(false);
			mainFrame.getBtnPosition().setEnabled(false);
			mainFrame.getEnd_exec_label().setText(
					"Erreur Position Effet ");
			mainFrame.updateInfo("Agence " + strc.getCodStrcStrc()
					+ " Erreur de  Traitement");
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer(
					"Erreur dans MoulinettePositionCompensation : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MoulinettePositionCompensation");
			logger.error("Exception : ", e);
			gestionException(dateComptableAgence, agence, e);
			throw new RuntimeException(e);

		}
	}

	public void fixerUser() {
		ContextCROHandler.setContext(ContextHandler.getContext());

		Personnel user = new Personnel();
		UserManager usermanager = (UserManager) ContextHandler.getContext()
				.getBean("userManager");
		user = usermanager.getUser("9999");
		user.setPassword("9999");

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				user, user.getPassword(), user.getAuthorities());
		auth.setDetails(user);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private void gestionException(Date dateComptable, Structure agence,
			Exception e) {

		BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();
		batchExeptionPlac.setDatSystBate(new Date());
		batchExeptionPlac.setDatCompBate(dateComptable);
		batchExeptionPlac.setStructure(agence);
		batchExeptionPlac
				.setLibTpbmBate("Exception Batch Position compensation");
		batchExeptionPlac.setLibExpBate(e.getMessage());
		BatchService batchService = (BatchService) context
				.getBean("batchService");
		batchExeptionPlac = (BatchExeptionPlac) batchService
				.InsertBatchExeptionPlac(batchExeptionPlac);
	}

}
