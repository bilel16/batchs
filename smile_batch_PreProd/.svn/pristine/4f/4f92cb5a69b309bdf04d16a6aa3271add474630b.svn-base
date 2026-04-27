package com.bna.smile.batch.test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Structure;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.batch.moulinette.MoulinettePositionCompensationEffet;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ListEditBatchVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.GetFilesEffetTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.GetListEditBatchTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.MoulinetteInsertingEffetTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class EditionGlobal implements Runnable {

	private static final Log LOGGER = LogFactory.getLog(EditionGlobal.class);
	public Consultation consultation;

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	public EditionGlobal(Consultation consultation) {
		this.consultation = consultation;
	}

	@Override
	public void run() {
		if (ContextHandler.getContext() == null) {
			String[] path =
					{ "./config/spring.xml", "./config/applicationContext-DAO.xml",
							"./config/applicationContext-habilitation.xml",
							"./config/applicationContext-resources.xml", "./config/applicationContext-service.xml",
							"./config/applicationContext-serviceBatch.xml",
							"./config/applicationContext-serviceHabil.xml",
							"./config/applicationContext-traitements.xml", "./config/quartz-oxia-calendars.xml",
							"./config/quartz-oxia-core.xml", "./config/quartz-oxia-jobs.xml",
							"./config/quartz-oxia-listners.xml", "./config/quartz-oxia-triggers.xml" };

			ApplicationContext springContext = new FileSystemXmlApplicationContext(path);
			Context context = (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
			context.setSpringContext(springContext);
			ContextHandler.setContext(context);
		}
		GetListEditBatchTrt trt = new GetListEditBatchTrt();
		ListEditBatchVo edit = consultation.getEditBatchVo();
		edit.setGlobal(true);
		edit = (ListEditBatchVo) trt.exec(edit);

		consultation.getButton_rechercher().setEnabled(true);
		consultation.getButton_edition().setEnabled(true);
		try {
			if (!edit.getListe().isEmpty())
				consultation.printEtatGlobal(edit.getListe(), edit.getValeur());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}