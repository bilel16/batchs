package com.bna.smile.batch.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceBatch;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.banqueAssurance.traitement.EnvoisFichierTrt2;
import com.bna.smile.model.banqueAssurance.vo.ContratAssuranceVo;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.AFBDAO;
import com.bna.smile.model.domainecommun.model.BatchVo;
import com.bna.smile.model.domainecommun.model.SocietesAFBView;
import com.bna.smile.model.domainecommun.traitement.GetListTraceBatchTrt;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteEnvoiFichierAssurance implements Runnable {

	String[] args;

	private static final Log LOGGER = LogFactory.getLog(MoulinetteEnvoiFichierAssurance.class.getSimpleName());

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
		args = new String[1];
		// args[1]="20/12/2022";

		BatchVo batchVo = new BatchVo();
		GetListTraceBatchTrt getListTraceBatchTrt = new GetListTraceBatchTrt();
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_ENVOI_FICHIER_AMI);
		batchVo.setBatchMetier(batchMetier);
		batchVo.setEtatTrace(Long.valueOf("0"));
		batchVo.setDateBatch(DateHandler.strToDate(DateHandler.dateToStr(new Date())));
		batchVo = (BatchVo) getListTraceBatchTrt.exec(batchVo);
		AFBDAO AFBDAO = (AFBDAO) context.getBean("AFBDAO");

		Date dateFichier = null;
		dateFichier = AFBDAO.getDateFichier();
		args[0] =DateHandler.dateToStr(dateFichier);
		
		if (batchVo.getListeTraceBatch() != null && batchVo.getListeTraceBatch().size() > 0) {
			LOGGER.error("Une autre moulinette envoi fichier Assurance est en cours d'exécution");
			return;
		} else {
			/* Insertion trace **** */
			SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm:ss");

			BatchService batchService = (BatchService) context.getBean("batchService");
			TraceBatch traceBatch = new TraceBatch();
			BatchMetier batchMetierVirement = new BatchMetier();
			batchMetierVirement.setCodBatBmet(Constants.COD_BATCH_ENVOI_FICHIER_AMI);
			traceBatch.setBatchMetier(batchMetierVirement);
			traceBatch.setCodeEtatBat(Long.valueOf("0"));
			traceBatch.setDateExecBat(new Date());
			traceBatch.setTimeDebBat(heureFormat.format(new Date()));
			traceBatch = (TraceBatch) batchService.InsertTraceBatch(traceBatch);

			if (traceBatch.getNumSeqTrc() != null) {
				ContratAssuranceVo contratAssuranceVo = new ContratAssuranceVo();
				EnvoisFichierTrt2 evoisFichierTrt = new EnvoisFichierTrt2();
				try {
					Date dateExtract = new Date();
					if (args.length == 0) {
						contratAssuranceVo.setDateExtraction(DateHandler.dateToStr(dateExtract));
						evoisFichierTrt.perform(contratAssuranceVo);
					} else if (args.length == 2) {
						List<String> listDates = listeDesDates(args[0], args[1]);
						for (String date : listDates) {
							dateExtract = DateHandler.strToDate(date);
							contratAssuranceVo.setDateExtraction(DateHandler.dateToStr(dateExtract));
							evoisFichierTrt.perform(contratAssuranceVo);
						}
					} else if (args.length == 1) {
						dateExtract = DateHandler.strToDate(args[0]);
						if (dateExtract == null) {
							LOGGER.error(
									"MoulinetteEnvoiFichierAssurance : mauvais format de la date passé en parametre");
							throw new InterruptedException();
						} else {
							contratAssuranceVo.setDateExtraction(DateHandler.dateToStr(dateExtract));
							evoisFichierTrt.perform(contratAssuranceVo);
						}
					} else {
						LOGGER.error("MoulinetteEnvoiFichierAssurance : probleme des parametres du batch ");
						throw new InterruptedException();
					}
				} catch (Exception e) {
					LOGGER.error("MoulinetteEnvoiFichierAssurance", e);
					return;
				}
				TraceBatch traceBatch2 = new TraceBatch();
				traceBatch2.setNumSeqTrc(traceBatch.getNumSeqTrc());
				traceBatch2.setCodeEtatBat(Long.valueOf(1));
				traceBatch2.setTimeFinBat(heureFormat.format(new Date()));
				traceBatch2 = (TraceBatch) batchService.InsertTraceBatch(traceBatch2);
				LOGGER.info("Moulinette envoi fichier Assurance terminée avec succès");
				LOGGER.info("*** FIN envoi fichier Assurance ***");
			}
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

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

}
