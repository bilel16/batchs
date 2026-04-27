package com.bna.smile.batch.test;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.EnvoisFichierTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteEnvoisMoneyGramTest implements Runnable {

	private static final Log LOGGER = LogFactory.getLog(MoulinetteEnvoisMoneyGramTest.class);
	public BatchFrameMoneyGram mainFrame;

	public MoulinetteEnvoisMoneyGramTest(BatchFrameMoneyGram mainFrame) {
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

		// Position Compensation
		
		CompensationEffetVo compensationVo = new CompensationEffetVo();
		EnvoisFichierTrt evoisFichierTrt=new EnvoisFichierTrt();
		evoisFichierTrt.setMainFrame(mainFrame);
		evoisFichierTrt.perform(compensationVo);

	}

	public void setMainFrame(BatchFrameMoneyGram mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

}