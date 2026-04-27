package com.bna.smile.batch.test;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.DiscordanceCompEffetDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class DiscordanceCompEffet {

	private static final Log LOGGER = LogFactory.getLog(DiscordanceCompEffet.class);
	public Consultation consultation;

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	public DiscordanceCompEffet(Consultation consultation) {
		this.consultation = consultation;
	}

	public DiscordanceCompEffet() {
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

			DiscordanceCompEffetDAO discordanceCompEffetDAO =
					(DiscordanceCompEffetDAO) context.getBean("discordanceCompEffetDAO");

			String dateComptable = "30/01/2017";
			/***** reception 21 ********/

			//discordanceCompEffetDAO.verifCro813(dateComptable,116L);

			List<Long> strcs = discordanceCompEffetDAO.getAgPilolte();

			for (int i = 0; i < strcs.size(); i++) {
				
				discordanceCompEffetDAO.verifCro813(dateComptable,strcs.get(i));
			}

			System.out.println("FIIIIIIIIIIIIIIIIIIN");

		}

	}
}