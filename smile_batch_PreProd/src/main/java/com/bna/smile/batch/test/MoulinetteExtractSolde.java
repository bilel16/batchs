package com.bna.smile.batch.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.DiscordanceSoldeDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteExtractSolde implements Runnable {

	private static final Log LOGGER = LogFactory
			.getLog(MoulinetteExtractSolde.class);
	public DiscordanceFrame mainFrame;
	public Long strc;

	public MoulinetteExtractSolde(DiscordanceFrame mainFrame, Long strc) {
		this.mainFrame = mainFrame;
		this.strc = strc;
	}

	@Override
	public void run() {
		if (ContextHandler.getContext() == null) {
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
					"./config/quartz-oxia-jobs.xml",
					"./config/quartz-oxia-listners.xml",
					"./config/quartz-oxia-triggers.xml" };

			ApplicationContext springContext = new FileSystemXmlApplicationContext(
					path);
			Context context = (Context) ContextFactory
					.initContext("./config/applicationContext-1Spring.xml");
			context.setSpringContext(springContext);
			ContextHandler.setContext(context);
		}
		DiscordanceSoldeDAO discordanceSoldeDAO = (DiscordanceSoldeDAO) ContextHandler
				.getContext().getBean("discordanceSoldeDAO");
		long start = System.currentTimeMillis();
		mainFrame.updateInfo("Extraction Solde En Cours  " + strc);
		discordanceSoldeDAO.initSoldeProcss(strc);
		mainFrame.updateInfo("Extraction  Solde  Terminée ");

	}

	public void setMainFrame(DiscordanceFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public DiscordanceFrame getMainFrame() {
		return mainFrame;
	}

}