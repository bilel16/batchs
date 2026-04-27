package com.bna.smile.batch.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.TraceBatch;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.moulinette.MoulinetteMAJNSIActelControlM;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.BatchVo;
import com.bna.smile.model.domainecommun.traitement.GetListTraceBatchTrt;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteMAJNSIActelControlMTest implements Runnable {

	private static final Logger logger = Logger.getLogger(MoulinetteVirementsAecheanceTest.class);
	
	private static final SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");

	

	public MoulinetteMAJNSIActelControlMTest() {
		super();
	}

	

	public void run() {
		String[] path =
				{ "SMILEBATCH2023/config/spring.xml", "SMILEBATCH2023/config/applicationContext-DAO.xml",
						"SMILEBATCH2023/config/applicationContext-habilitation.xml", "SMILEBATCH2023/config/applicationContext-resources.xml",
						"SMILEBATCH2023/config/applicationContext-service.xml", "SMILEBATCH2023/config/applicationContext-serviceBatch.xml",
						"SMILEBATCH2023/config/applicationContext-serviceHabil.xml", "SMILEBATCH2023/config/applicationContext-traitements.xml",
						"SMILEBATCH2023/config/quartz-oxia-calendars.xml", "SMILEBATCH2023/config/quartz-oxia-core.xml",
						"SMILEBATCH2023/config/quartz-oxia-jobs.xml", "SMILEBATCH2023/config/security.xml", "SMILEBATCH2023/config/quartz-oxia-listners.xml",
						"SMILEBATCH2023/config/quartz-oxia-triggers.xml" };

		ApplicationContext springContext = new FileSystemXmlApplicationContext(path);
		Context context = (Context) ContextFactory.initContext("SMILEBATCH2023/config/applicationContext-1Spring.xml");
		context.setSpringContext(springContext);
		ContextHandler.setContext(context);

		// *****Verification du l'existance d'un batch virement en cours ********//
		BatchVo batchVo = new BatchVo();
		GetListTraceBatchTrt getListTraceBatchTrt = new GetListTraceBatchTrt();
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_MAJNSI_Actel);
		batchVo.setBatchMetier(batchMetier);
		batchVo.setEtatTrace(Long.valueOf("0"));
		batchVo.setDateBatch(DateHandler.strToDate(DateHandler.dateToStr(new Date())));
		batchVo = (BatchVo) getListTraceBatchTrt.exec(batchVo);
		if (batchVo.getListeTraceBatch() != null && batchVo.getListeTraceBatch().size() > 0) {
			System.out.println("Une autre moulinette MAJ NSI  Actel est en cours d'exécution");
			
		} else {
			/* **** Insertion trace **** */
			SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm:ss");

			BatchService batchService = (BatchService) context.getBean("batchService");
			TraceBatch traceBatch = new TraceBatch();
			BatchMetier batchMetierVirement = new BatchMetier();
			batchMetierVirement.setCodBatBmet(Constants.COD_BATCH_MAJNSI_Actel);
			traceBatch.setBatchMetier(batchMetierVirement);
			traceBatch.setCodeEtatBat(Long.valueOf("0"));
			traceBatch.setDateExecBat(new Date());
			traceBatch.setTimeDebBat(heureFormat.format(new Date()));
			traceBatch = (TraceBatch) batchService.InsertTraceBatch(traceBatch);

			if (traceBatch.getNumSeqTrc() != null) {

				//Structure agence = new Structure();
				//Date dateComptableAgence = null;
				
				
				
				try {
					MoulinetteMAJNSIActelControlM moulinette = new MoulinetteMAJNSIActelControlM();
					moulinette.perform();
					TraceBatch traceBatch2 = new TraceBatch();
					traceBatch2.setNumSeqTrc(traceBatch.getNumSeqTrc());
					traceBatch2.setCodeEtatBat(Long.valueOf(1));
					traceBatch2.setTimeFinBat(heureFormat.format(new Date()));

					traceBatch2 = (TraceBatch) batchService.InsertTraceBatch(traceBatch2);

					System.out.println("Moulinette MAJ NSI Actel terminée avec succès");
				
					logger.info("*** FIN MAJ NSI Actel ***");
				
				} catch (Exception e) {
					TraceBatch traceBatch2 = new TraceBatch();
					traceBatch2.setNumSeqTrc(traceBatch.getNumSeqTrc());
					traceBatch2.setCodeEtatBat(Long.valueOf(1));
					traceBatch2.setTimeFinBat(heureFormat.format(new Date()));
					traceBatch2 = (TraceBatch) batchService.InsertTraceBatch(traceBatch2);
					 
				}
				
				
			}
		}
	}
}
