package com.bna.smile.batch.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;

import com.bna.commun.model.Structure;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.commande.RestoreChequeCmd;
import com.bna.smile.model.domainecompensation.gestionrejet.commande.RestoreEffetCmd;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.EditionRejetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.RemiseEffetVo;
import com.bna.smile.model.prelevement.commande.RestorePrelevementCmd;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteRestoreFilesTest implements Runnable {

	private static final Log LOGGER = LogFactory.getLog(MoulinetteRestoreFilesTest.class);
	public BatchFrameRestoreFile mainFrame;

	public MoulinetteRestoreFilesTest(BatchFrameRestoreFile mainFrame) {
		this.mainFrame = mainFrame;
	}

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
		mainFrame.updateInfo("");
		// trace Execution Lecture
		CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
		try {

			if (mainFrame.getTxt_strc().getText().isEmpty()) {
				mainFrame.updateInfo(" Veuillez entrer le code agence");
				mainFrame.getBtnExcuter().setEnabled(true);
			} else if (mainFrame.getTxt_dateComptable().getText().isEmpty()) {
				mainFrame.updateInfo(" Veuillez entrer la date comptable");
				mainFrame.getBtnExcuter().setEnabled(true);
			} else {
				Pattern p = Pattern.compile("[0-9]{1,3}");
				Matcher m = p.matcher(mainFrame.getTxt_strc().getText());
				if (!m.matches()) {
					mainFrame.updateInfo(" Code Agence invalide !");
					mainFrame.getBtnExcuter().setEnabled(true);
				} else {
					Structure strc = compensationDAO.findStructure(Long.valueOf(mainFrame.getTxt_strc().getText()));
					if (strc == null) {
						mainFrame.updateInfo(" Structure Introuvable !");
						mainFrame.getBtnExcuter().setEnabled(true);
					}
				}
				SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");

				try {
					formaterDate.parse(mainFrame.getTxt_dateComptable().getText());
				} catch (Exception ex) {
					mainFrame.updateInfo(" Date Comptable Invalide !");
					mainFrame.getBtnExcuter().setEnabled(true);
				}

				if (mainFrame.getEnd_exec_label().getText().isEmpty()) {

					// Initilisation de repertoire
					Util.initDirectoriesTeleComp();
					ParamAgence paramAgence = new ParamAgence();
					paramAgence.setCodStrcStrc(Long.valueOf(mainFrame.getTxt_strc().getText()));
					paramAgence.setDateComptable(mainFrame.getTxt_dateComptable().getText());

					RemiseEffetVo effetVo = new RemiseEffetVo();

					effetVo.setParamAgence(paramAgence);
					effetVo.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
					RestoreEffetCmd restoreEffetCmd = new RestoreEffetCmd();
					effetVo = (RemiseEffetVo) restoreEffetCmd.execute(effetVo);

					// Génération des Fichiers de rejets cheque ******* EditionRejetVo
					EditionRejetVo editionRejetVo = new EditionRejetVo();
					editionRejetVo.setParamAgence(paramAgence);
					editionRejetVo.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));

					RestoreChequeCmd cmd = new RestoreChequeCmd();
					editionRejetVo = (EditionRejetVo) cmd.execute(editionRejetVo);
					
					// Génération des Fichiers de rejets prelevements ******* 
					PrelevementVo prelevementVo = new PrelevementVo();
					prelevementVo.setCodeStructure(paramAgence.getCodStrcStrc());
					prelevementVo.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
					RestorePrelevementCmd restorePrelevementCmd = new RestorePrelevementCmd();
					prelevementVo =(PrelevementVo) restorePrelevementCmd.execute(prelevementVo);
					//*******************************************************
					mainFrame.updateInfo(" Fichiers Génerés avec succés !");
					mainFrame.getBtnExcuter().setEnabled(true);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mainFrame.getBtnExcuter().setEnabled(true);
			mainFrame.updateInfo(" Erreur de  Traitement");

		}

	}

	public void setMainFrame(BatchFrameRestoreFile mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

}