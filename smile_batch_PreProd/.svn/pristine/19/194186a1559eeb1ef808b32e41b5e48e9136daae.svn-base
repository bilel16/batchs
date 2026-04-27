package com.bna.smile.batch.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceBatch;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.moulinette.MoulinetteVirementsLieesComptesVerts;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.BatchVo;
import com.bna.smile.model.domainecommun.traitement.GetListTraceBatchTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteVirementsLieesComptesVertsTest implements Runnable {

	private static final Logger logger = Logger.getLogger(MoulinetteVirementsLieesComptesVertsTest.class);
	private BatchVirementCompteVertFrame frame = null;
	private static final SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");

	/***********************************/
	public MoulinetteVirementsLieesComptesVertsTest() {
		super();

	}

	public MoulinetteVirementsLieesComptesVertsTest(BatchVirementCompteVertFrame frame) {
		super();
		this.frame = frame;
	}

	public void setFrame(BatchVirementCompteVertFrame frame) {
		this.frame = frame;
	}

	public BatchVirementCompteVertFrame getFrame() {
		return frame;
	}

	/***************************************/

	@Override
	public void run() {

		String[] path =
				{ "./config/spring.xml", "./config/applicationContext-DAO.xml",
						"./config/applicationContext-habilitation.xml", "./config/applicationContext-resources.xml",
						"./config/applicationContext-service.xml", "./config/applicationContext-serviceBatch.xml",
						"./config/applicationContext-serviceHabil.xml", "./config/applicationContext-traitements.xml",
						"./config/quartz-oxia-calendars.xml", "./config/quartz-oxia-core.xml",
						"./config/quartz-oxia-jobs.xml", "./config/security.xml", "./config/quartz-oxia-listners.xml",
						"./config/quartz-oxia-triggers.xml" };

		ApplicationContext springContext = new FileSystemXmlApplicationContext(path);
		Context context = (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
		context.setSpringContext(springContext);
		ContextHandler.setContext(context);

		// *****Verification du l'existance d'un batch virement en cours ********//
		BatchVo batchVo = new BatchVo();
		GetListTraceBatchTrt getListTraceBatchTrt = new GetListTraceBatchTrt();
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_VIREMENT_LIEES_COMPTES_VERTS);
		batchVo.setBatchMetier(batchMetier);
		batchVo.setEtatTrace(Long.valueOf("0"));
		batchVo.setDateBatch(DateHandler.strToDate(DateHandler.dateToStr(new Date())));
		batchVo = (BatchVo) getListTraceBatchTrt.exec(batchVo);
		if (batchVo.getListeTraceBatch() != null && batchVo.getListeTraceBatch().size() > 0) {
			frame.getEnd_exec_label().setText("Une autre moulinette virement est en cours d'exécution");
			frame.getBtnExcuter().setEnabled(true);

		} else {
			/* Insertion trace **** */
			SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm:ss");

			BatchService batchService = (BatchService) context.getBean("batchService");
			TraceBatch traceBatch = new TraceBatch();
			BatchMetier batchMetierVirement = new BatchMetier();
			batchMetierVirement.setCodBatBmet(Constants.COD_BATCH_VIREMENT_LIEES_COMPTES_VERTS);
			traceBatch.setBatchMetier(batchMetierVirement);
			traceBatch.setCodeEtatBat(Long.valueOf("0"));
			traceBatch.setDateExecBat(new Date());
			traceBatch.setTimeDebBat(heureFormat.format(new Date()));
			traceBatch = (TraceBatch) batchService.InsertTraceBatch(traceBatch);

			if (traceBatch.getNumSeqTrc() != null) {

				VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
				List listAgences = virementGlobalDAO.getListAgencesVirement();
				Structure agence = new Structure();
				Date dateComptableAgence = null;
				VirementVo virementVo = new VirementVo();
				if (listAgences != null && listAgences.size() > 0) {
					logger.info("*** Debut Moulinette Virement Compte Vert ***");

					for (Object result : listAgences) {
						ListOrderedMap map = (ListOrderedMap) result;

						agence.setCodStrcStrc(Long.valueOf(map.get("COD_STRC_STRC") + ""));
						dateComptableAgence = DateHandler.strToDate(map.get("DAT_JRN_JRN").toString());

						logger.info("agence " + agence.getCodStrcStrc() + "==> dateComptableAgence "
								+ formaterDate.format(dateComptableAgence));
						virementVo.setStructure(agence);
						virementVo.setDateComptableAgence(dateComptableAgence);

						SwingInfoVo infoVo = new SwingInfoVo();
						infoVo.setStructure("" + agence.getCodStrcStrc());
						infoVo.setEtat(Constants.STATUT_EN_COURS_INSERT);
						infoVo.setDateComptable(formaterDate.format(dateComptableAgence));
						frame.addOrUpdateEtat(infoVo);

						// ********Batch *************//
						MoulinetteVirementsLieesComptesVerts moulinette =
								new MoulinetteVirementsLieesComptesVerts(virementVo);
						moulinette.setMainFrame(frame);
						moulinette.perform();

					}

				}

				TraceBatch traceBatch2 = new TraceBatch();
				traceBatch2.setNumSeqTrc(traceBatch.getNumSeqTrc());
				traceBatch2.setCodeEtatBat(Long.valueOf(1));
				traceBatch2.setTimeFinBat(heureFormat.format(new Date()));

				traceBatch2 = (TraceBatch) batchService.InsertTraceBatch(traceBatch2);

				frame.getEnd_exec_label().setText("Moulinette Virements comptes liées terminée avec succès");
				frame.getBtnExcuter().setEnabled(true);
				logger.info("*** FIN Moulinette Virements Comptes Verts ***");
			}
		}

	}

}
