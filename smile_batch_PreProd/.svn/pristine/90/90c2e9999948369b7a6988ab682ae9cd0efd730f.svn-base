package com.bna.smile.batch.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.TraceBatch;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.moulinette.MoulinetteMAJNSIControlM;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.BatchVo;
import com.bna.smile.model.domainecommun.traitement.GetListTraceBatchTrt;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteMAJNSIControlMTest implements Runnable {

	private static final Logger logger = Logger.getLogger(MAJNSILauncher.class);
	private static final SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");

	public void run() {
		String[] path = 
	        { "./config/spring.xml", "./config/applicationContext-DAO.xml", "./config/applicationContext-habilitation.xml", 
	          "./config/applicationContext-resources.xml", "./config/applicationContext-service.xml", 
	          "./config/applicationContext-serviceBatch.xml", 
	          "./config/applicationContext-serviceHabil.xml", 
	          "./config/applicationContext-traitements.xml", "./config/quartz-oxia-calendars.xml", 
	          "./config/quartz-oxia-core.xml", "./config/quartz-oxia-jobs.xml", "./config/security.xml",
	          "./config/quartz-oxia-listners.xml", "./config/quartz-oxia-triggers.xml" };

	        ApplicationContext springContext =  
	            new FileSystemXmlApplicationContext(path);
	        Context context= (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
	        context.setSpringContext(springContext);
	        ContextHandler.setContext(context);
		try {
			// *****Verification du l'existance d'un batch virement en cours ********//
			BatchVo batchVo = new BatchVo();
			GetListTraceBatchTrt getListTraceBatchTrt = new GetListTraceBatchTrt();
			BatchMetier batchMetier = new BatchMetier();
			batchMetier.setCodBatBmet(Constants.COD_BATCH_MAJNSI);
			batchVo.setBatchMetier(batchMetier);
			batchVo.setEtatTrace(Long.valueOf("0"));
			batchVo.setDateBatch(DateHandler.strToDate(DateHandler.dateToStr(new Date())));
			batchVo = (BatchVo) getListTraceBatchTrt.exec(batchVo);
			if (batchVo.getListeTraceBatch() != null && batchVo.getListeTraceBatch().size() > 0) {
				System.out.println("Une autre moulinette MAJ NSI virements sièges est en cours d'exécution");
				System.out.println("JOBNOTOK");

			} else {
				/* Insertion trace **** */
				SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm:ss");

				BatchService batchService = (BatchService) context.getBean("batchService");
				TraceBatch traceBatch = new TraceBatch();
				BatchMetier batchMetierVirement = new BatchMetier();
				batchMetierVirement.setCodBatBmet(Constants.COD_BATCH_MAJNSI);
				traceBatch.setBatchMetier(batchMetierVirement);
				traceBatch.setCodeEtatBat(Long.valueOf("0"));
				traceBatch.setDateExecBat(new Date());
				traceBatch.setTimeDebBat(heureFormat.format(new Date()));
				traceBatch = (TraceBatch) batchService.InsertTraceBatch(traceBatch);

				if (traceBatch.getNumSeqTrc() != null) {

					// Structure agence = new Structure();
					// Date dateComptableAgence = null;

					MoulinetteMAJNSIControlM moulinette = new MoulinetteMAJNSIControlM();
					moulinette.perform();

					TraceBatch traceBatch2 = new TraceBatch();
					traceBatch2.setNumSeqTrc(traceBatch.getNumSeqTrc());
					traceBatch2.setCodeEtatBat(Long.valueOf(1));
					traceBatch2.setTimeFinBat(heureFormat.format(new Date()));

					traceBatch2 = (TraceBatch) batchService.InsertTraceBatch(traceBatch2);

					System.out.println("Moulinette MAJ NSI virements sièges terminée avec succès");
					logger.info("*** FIN MAJ NSI virements sièges ***");
					System.out.println("JOBOK");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("JOBNOTOK");

		}

	}

	public static List<String> listeDesDates(String strDateDebut, String strDateFin) {

		Date DateDebut = DateHandler.strToDate(strDateDebut);
		Date DateFin = DateHandler.addJour(DateHandler.strToDate(strDateFin), 1);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(DateDebut);
		List<String> listeDates = new ArrayList<String>();
		SimpleDateFormat formatDateFile = new SimpleDateFormat("dd/MM/yyyy");

		while (DateDebut.before(DateFin)) {
			listeDates.add(formatDateFile.format(DateDebut));
			DateDebut = DateHandler.addJour(DateDebut, 1);
		}
		return listeDates;
	}

	

}
