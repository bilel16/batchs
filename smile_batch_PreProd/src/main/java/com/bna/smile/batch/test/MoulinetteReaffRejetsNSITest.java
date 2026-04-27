package com.bna.smile.batch.test;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.batch.moulinette.MoulinetteReaffRejetsNSI;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteReaffRejetsNSITest implements Runnable {

	private static final Logger logger = Logger.getLogger(MoulinetteVirementsAecheanceTest.class);
	private BatchReaffRejetsNSIFrame frame = null;
	private static final SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");

	public BatchReaffRejetsNSIFrame getFrame() {
		return frame;
	}

	public void setFrame(BatchReaffRejetsNSIFrame frame) {
		this.frame = frame;
	}

	public MoulinetteReaffRejetsNSITest() {
		super();
	}

	public MoulinetteReaffRejetsNSITest(BatchReaffRejetsNSIFrame frame) {
		super();
		this.frame = frame;
	}

	public void run() {
		String[] path = { "./config/spring.xml", "./config/applicationContext-DAO.xml",
				"./config/applicationContext-habilitation.xml", "./config/applicationContext-resources.xml",
				"./config/applicationContext-service.xml", "./config/applicationContext-serviceBatch.xml",
				"./config/applicationContext-serviceHabil.xml", "./config/applicationContext-traitements.xml",
				"./config/quartz-oxia-calendars.xml", "./config/quartz-oxia-core.xml", "./config/quartz-oxia-jobs.xml",
				"./config/security.xml", "./config/quartz-oxia-listners.xml", "./config/quartz-oxia-triggers.xml" };

		ApplicationContext springContext = new FileSystemXmlApplicationContext(path);
		Context context = (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
		context.setSpringContext(springContext);
		ContextHandler.setContext(context);

		// Structure agence = new Structure();
		// Date dateComptableAgence = null;
		System.out.println("Agence : " + frame.getTextFieldAgence().getText());
		MoulinetteReaffRejetsNSI moulinette = new MoulinetteReaffRejetsNSI(frame);
		moulinette.perform();


		frame.getEnd_exec_label().setText("Moulinette Réaffectation rejets virements sièges terminée avec succès");
		frame.getBtnExcuter().setEnabled(true);
		logger.info("*** FIN Réaffectation virements sièges ***");

	}
}
