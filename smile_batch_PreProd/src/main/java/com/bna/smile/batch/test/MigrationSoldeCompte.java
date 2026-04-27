package com.bna.smile.batch.test;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.histSoldePascale.dao.MigrationSoldeCompteDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MigrationSoldeCompte {

	private static final Log LOGGER = LogFactory.getLog(MigrationSoldeCompte.class);
	public Consultation consultation;

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	public MigrationSoldeCompte(Consultation consultation) {
		this.consultation = consultation;
	}

	public MigrationSoldeCompte() {
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

			MigrationSoldeCompteDAO migrationSoldeCompteDAO =
					(MigrationSoldeCompteDAO) context.getBean("migrationSoldeCompteDAO");
			// *******Migration solde agence pascal****** *******/
			/* 1-chargement solde dinars */

			List<Long> strcs = migrationSoldeCompteDAO.getAgPilolte();
			for (int i = 0; i < strcs.size(); i++) {
				Long codStructure = strcs.get(i);
				migrationSoldeCompteDAO.insertCompteMig(
						"D:/migration_solde/migration/TRSOLDE." + StrHandler.lpad("" + codStructure, '0', 3),
						StrHandler.lpad("" + codStructure, '0', 3));

			}
			System.out.println("   FIN 1");

			/* 2-mise à jour contrat solde compte dinars+ dinars covertile+devise */
			// migrationSoldeCompteDAO.updateSoldeContratCompte(codStructure);
			System.out.println("FIN 2");

			/* 3-migration blocage compte+ dep affecté */
			// migrationSoldeCompteDAO.insertMigBlocageCompte("d:/TRCBLOC." + StrHandler.lpad("" + codStructure, '0',
			// 3),
			// codStructure);
			System.out.println("FIN 3");

			/* 4-mise à jour contrat blocage + depot aff compte */

			// migrationSoldeCompteDAO.updateCompteBlocage(codStructure);

			System.out.println("FIIIIIIIIIIIIIIIIIIN");

			/****************************************/

			/* 5-insert trace blocage Cumule */
			// migrationSoldeCompteDAO.migBlocageCompte("d:/TSMBLOC.004", 4L);

			/*********** Migration etat de compte NSI et OC ************/
			// migrationSoldeCompteDAO.insertConfrontationEtatCptOC_NSI("d:/ETATCPT07-03-16");
			// migrationSoldeCompteDAO.createFichierCPTNotInOC();
			// migrationSoldeCompteDAO.updateCPTResilieInOC();
		}

	}
}