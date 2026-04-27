package com.bna.smile.batch.test;

import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.Structure;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.batch.moulinette.MoulinettePositionCompensationEffet;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.GetFilesEffetTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.MoulinetteInsertingEffetTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinettePositionEffetTest implements Runnable {

	private static final Log LOGGER = LogFactory.getLog(MoulinettePositionEffetTest.class);
	public BatchFrame mainFrame;

	public MoulinettePositionEffetTest(BatchFrame mainFrame) {
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
		CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");

		
		// Position Compensation
		new MoulinettePositionCompensationEffet(mainFrame).perform();

	}

	public void setMainFrame(BatchFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

}