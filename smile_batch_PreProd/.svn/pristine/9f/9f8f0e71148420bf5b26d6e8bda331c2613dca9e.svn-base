package com.bna.smile.batch.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceBatch;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecompensation.gestionrejet.commande.InsertingEffetCmd;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.GetFilesEffetTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.SuivFileTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteCompensationEffetTest implements Runnable {

	private static final Log LOGGER = LogFactory
			.getLog(MoulinetteCompensationEffetTest.class);
	public BatchFrame mainFrame;

	public MoulinetteCompensationEffetTest(BatchFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void run() {
		String[] path = { "./config/spring.xml",
				"./config/applicationContext-DAO.xml",
				"./config/applicationContext-habilitation.xml",
				"./config/applicationContext-resources.xml",
				"./config/applicationContext-service.xml",
				"./config/applicationContext-serviceBatch.xml",
				"./config/applicationContext-serviceHabil.xml",
				"./config/applicationContext-traitements.xml",
				"./config/quartz-oxia-calendars.xml",
				"./config/quartz-oxia-core.xml",
				"./config/quartz-oxia-jobs.xml", "./config/security.xml",
				"./config/quartz-oxia-listners.xml",
				"./config/quartz-oxia-triggers.xml" };

		ApplicationContext springContext = new FileSystemXmlApplicationContext(
				path);
		Context context = (Context) ContextFactory
				.initContext("./config/applicationContext-1Spring.xml");
		context.setSpringContext(springContext);
		
		ContextHandler.setContext(context);
		
		CompensationDAO compensationDAO = (CompensationDAO) context
				.getBean("compensationDAO");
		
		//trace Execution Lecture 
		SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm:ss");

		BatchService batchService = (BatchService) context.getBean("batchService");
		TraceBatch traceBatch = new TraceBatch();
		
		BatchMetier batchMetierVirement = new BatchMetier();
		batchMetierVirement.setCodBatBmet(Constants.COD_BATCH_COMPENSATION_EFFET);
		traceBatch.setBatchMetier(batchMetierVirement);
		traceBatch.setCodeEtatBat(Long.valueOf("1"));
		traceBatch.setDateExecBat(new Date());
		traceBatch.setTimeDebBat(heureFormat.format(new Date()));
		traceBatch = (TraceBatch) batchService.InsertTraceBatch(traceBatch);

		// Initilisation de repertoire
		Util.initDirectoriesTeleComp();
		
		// importaion de donnees fichiers
		// a supprimer get file ACH
		GetFilesEffetTrt geteffetTrt = new GetFilesEffetTrt();
		geteffetTrt.setMainFrame(mainFrame);
		geteffetTrt.exec(null);
		
		// isertion de donnees BD
		List listAgences = compensationDAO.getListAgencesCompensationPilote();
		
		ListOrderedMap ListAg = null;
		
		boolean isError = false;
		
		CompensationEffetVo compensationVo = new CompensationEffetVo();
		
		String dateCompStrc = "";
		Structure strc = null;
		try {
			if (listAgences != null && listAgences.size() > 0) {
				for (Iterator it1 = listAgences.iterator(); it1.hasNext();) {

					ListAg = (ListOrderedMap) it1.next();
					LOGGER.info(ListAg.getValue(0).toString());

					if ((ListAg.getValue(0)).toString() != null && (ListAg.getValue(1)).toString() != null) {

						dateCompStrc = ListAg.getValue(1).toString();
						strc = compensationDAO.findStructure(new Long((ListAg.getValue(0)).toString()));

						if (strc.getTypeStructure().getCodTstrTstr().equals(1L)
								|| strc.getTypeStructure().getCodTstrTstr().equals(6L)) {

							compensationVo.setDateComptable(DateHandler.strToDate(dateCompStrc));
							compensationVo.setStructure(strc);
							strc.setCodBctStrc(StrHandler.lpad(strc.getCodBctStrc(), '0', 3));

							// a verifier ACH
							SuivFileTrt.appurementSuiviFileEffet(dateCompStrc, strc.getCodBctStrc());

							InsertingEffetCmd insertingEffetCmd = new InsertingEffetCmd();
							
							SwingInfoVo infoVo = new SwingInfoVo();
							infoVo.setStructure("" + strc.getCodStrcStrc());
							infoVo.setEtat(Constants.STATUT_EN_COURS_INSERT);
							infoVo.setDateComptable(dateCompStrc);
							
							mainFrame.addOrUpdateEtat(infoVo);
							mainFrame.updateInfo(
									"Agence " + strc.getCodStrcStrc() + " " + Constants.STATUT_EN_COURS_INSERT);
							
							compensationVo = (CompensationEffetVo) insertingEffetCmd.execute(compensationVo);
							
							infoVo = new SwingInfoVo();
							infoVo.setStructure("" + strc.getCodStrcStrc());
							infoVo.setEtat(Constants.STATUT_EN_TERMINE);
							
							mainFrame.updateInfo("Agence " + strc.getCodStrcStrc() + " Fin Traitement");

							infoVo.setDateComptable(dateCompStrc);
							mainFrame.addOrUpdateEtat(infoVo);

						}

					}
				}

			}

			if (traceBatch.getNumSeqTrc() != null) {

				TraceBatch traceBatch2 = new TraceBatch();
				traceBatch2.setNumSeqTrc(traceBatch.getNumSeqTrc());
				traceBatch2.setCodeEtatBat(Long.valueOf(1));
				traceBatch2.setTimeFinBat(heureFormat.format(new Date()));

				traceBatch2 = (TraceBatch) batchService.InsertTraceBatch(traceBatch2);

			}
		} catch (Exception ex) {
			SwingInfoVo infoVo = new SwingInfoVo();
			
			infoVo = new SwingInfoVo();
			infoVo.setStructure("" + strc.getCodStrcStrc());
			infoVo.setEtat(
					Constants.STATUT_EN_ERRUR + " " + compensationVo.getErrors().get(0).getKey().substring(7, 12));
			
			isError = true;
			
			mainFrame.updateInfo("Agence " + strc.getCodStrcStrc() + " Erreur de  Traitement");
			
			listAgences = compensationDAO.getListAgencesCompensationPilote();
			
			ListAg = null;
			
			if (listAgences != null && listAgences.size() > 0) {
				for (Iterator it1 = listAgences.iterator(); it1.hasNext();) {
					ListAg = (ListOrderedMap) it1.next();

					if ((ListAg.getValue(0)).toString() != null && (ListAg.getValue(1)).toString() != null) {
						dateCompStrc = ListAg.getValue(1).toString();
						strc = compensationDAO.findStructure(new Long((ListAg.getValue(0)).toString()));
						if (strc.getTypeStructure().getCodTstrTstr().equals(1L)
								|| strc.getTypeStructure().getCodTstrTstr().equals(6L)) {

							SuivFileTrt.appurementSuiviFileEffet(dateCompStrc,
									StrHandler.lpad(strc.getCodBctStrc(), '0', 3));
						}
					}
				}
			}

			infoVo.setDateComptable(dateCompStrc);
			mainFrame.addOrUpdateEtat(infoVo);
		}
		mainFrame.getBtnExcuter().setEnabled(true);
		mainFrame.getBtnPosition().setEnabled(true);
		
		if (isError == false)
			mainFrame.getEnd_exec_label().setText(
					"Lecture Base Données effet exécutée avec succès");
		else {
			mainFrame.getEnd_exec_label().setText(
					"Erreur Lecture Base Données effet");
			mainFrame.getBtnPosition().setEnabled(false);

		}

	}

	public void setMainFrame(BatchFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

}