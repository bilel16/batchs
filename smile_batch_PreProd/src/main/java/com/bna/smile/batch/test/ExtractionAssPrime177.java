package com.bna.smile.batch.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.assVieEpargneEtude.dao.AssVieEpargneDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class ExtractionAssPrime177 {

	private static final Log LOGGER = LogFactory.getLog(ExtractionAssPrime177.class);
	public Consultation consultation;

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	public ExtractionAssPrime177(Consultation consultation) {
		this.consultation = consultation;
	}

	public ExtractionAssPrime177() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String... args) {
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

			AssVieEpargneDAO assVieEpargneEtude = (AssVieEpargneDAO) context.getBean("assVieEpargneDAO");

			//assVieEpargneEtude.loadNewContart(2013L);
			
			//assVieEpargneEtude.createFilePrime(2013L);
			/*
			 * try { assVieEpargneEtude.insertCategorie("d:/categorie.csv"); } catch (FileNotFoundException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); }
			 */
/*
			try {
				assVieEpargneEtude.insertHistTrach("d:/LST177_2013", 2013L);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

		}

	}
}