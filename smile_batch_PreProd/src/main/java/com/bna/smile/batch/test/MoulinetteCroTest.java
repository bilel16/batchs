package com.bna.smile.batch.test;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.commande.InsertingCroFromOmpCmd;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;

public class MoulinetteCroTest implements Runnable {

	private static final Log LOGGER = LogFactory.getLog(MoulinetteCroTest.class);
	public BatchFrame mainFrame;

	public MoulinetteCroTest(BatchFrame mainFrame) {
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

		try {

			ISearchEngine searchEngine = (SearchEngine) ContextHandler.getContext().getBean("searchEngine");

			OperationMoyPay operationMoyPay =
					(OperationMoyPay) searchEngine.get(OperationMoyPay.class, "1201606006468031");
			System.out.println(operationMoyPay.getNumOperOmp());
			operationMoyPay.setNumeroCaisse("1");

			InsertingCroFromOmpCmd cmd = new InsertingCroFromOmpCmd();
			operationMoyPay = (OperationMoyPay) cmd.execute(operationMoyPay);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void setMainFrame(BatchFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

}