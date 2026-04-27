package com.bna.smile.batch.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.Structure;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.moulinette.MoulinettePrelevementsDomiciliationsACH;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.bna.smile.model.prelevement.dao.PrelevementDAO;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteInsertionPrelevementsDomiciliationTest implements Runnable {

	private static final Logger logger = Logger.getLogger(MoulinetteInsertionPrelevementsDomiciliationTest.class);
	private BatchFrame frame = null;
	private static final SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");

	public MoulinetteInsertionPrelevementsDomiciliationTest(BatchFrame frame) {
		super();
		this.frame = frame;
	}

	public BatchFrame getFrame() {
		return frame;
	}

	public void setFrame(BatchFrame frame) {
		this.frame = frame;
	}

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
		
		// ****************************//
		Util.initDirectoriesTeleComp();

		PrelevementDAO prelevementDAO = (PrelevementDAO) context.getBean("prelevementDAO");
		
		List listAgences = prelevementDAO.getListJourneesAgences();
		
		Structure agence = new Structure();
		Date dateComptableAgence = null;
		
		PrelevementVo prelevementVo = new PrelevementVo();
		if (listAgences != null && listAgences.size() > 0) {
			logger.info("*** Debut MoulinetteInsertionPrelevements ACH ***");

			for (Object result : listAgences) {
				ListOrderedMap map = (ListOrderedMap) result;

				agence.setCodStrcStrc(Long.valueOf(map.get("COD_STRC_STRC") + ""));
				dateComptableAgence = DateHandler.strToDate(map.get("DAT_JRN_JRN").toString());

				logger.info("agence " + agence.getCodStrcStrc() + "==> dateComptableAgence "
						+ formaterDate.format(dateComptableAgence));
				prelevementVo.setStructure(agence);
				prelevementVo.setDateComptable(dateComptableAgence);

				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setStructure("" + agence.getCodStrcStrc());
				infoVo.setEtat(Constants.STATUT_EN_COURS_INSERT);
				infoVo.setDateComptable(formaterDate.format(dateComptableAgence));
				frame.addOrUpdateEtat(infoVo);

				// ********Insertion dans la base *************//
				
				/**
				 * @since 11/03/2026 Refonte SNT - ACH
				 **/
				
				// MoulinettePrelevementsDomiciliations moulinettePrelevementsDomiciliations =
				// new MoulinettePrelevementsDomiciliations(prelevementVo);
				//
				MoulinettePrelevementsDomiciliationsACH moulinettePrelevementsDomiciliations
				= new MoulinettePrelevementsDomiciliationsACH(prelevementVo);
				moulinettePrelevementsDomiciliations.setMainFrame(frame);
				moulinettePrelevementsDomiciliations.perform();

				// *******************************************//

				infoVo = new SwingInfoVo();
				infoVo.setStructure("" + agence.getCodStrcStrc());
				infoVo.setEtat(Constants.STATUT_EN_TERMINE);
				infoVo.setDateComptable(formaterDate.format(dateComptableAgence));
				frame.addOrUpdateEtat(infoVo);
			}

		}
		frame.getEnd_exec_label().setText("La phase insertion est terminée avec succès");
		frame.getBtnExcuter().setEnabled(true);
		logger.info("*** FIN MoulinetteInsertionPrelevements ACH ***");

	}
}
