package com.bna.smile.batch.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.moulinette.MoulinetteEnvoiSMS;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.SMS.dao.EnvoiSmsDAO;
import com.bna.smile.model.domainecommun.model.EnvoiSMSVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteEnvoiSMSTest implements Runnable {

	private static final Logger logger = Logger.getLogger(MoulinetteEnvoiSMSTest.class);
	private BatchEnvoiSMSFrame frame = null;
	private static final SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	Date dateComptableFichier = new Date();
	String pathAFBTravail = "";
	String fileName = "";
	String repertoire = "";
	SimpleDateFormat formaterDate2 = new SimpleDateFormat("ddMMyyyy");
	String codeDevise = Constants.COD_DEV_DINAR.toString();

	// *********************************//

	public BatchEnvoiSMSFrame getFrame() {
		return frame;
	}

	public void setFrame(BatchEnvoiSMSFrame frame) {
		this.frame = frame;
	}

	public MoulinetteEnvoiSMSTest() {
		super();
	}

	public MoulinetteEnvoiSMSTest(BatchEnvoiSMSFrame frame) {
		super();
		this.frame = frame;
	}

	@Override
	public void run() {
		try {

			String[] path = { "./config/spring.xml", "./config/applicationContext-DAO.xml",
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

			System.out.println("TextDateRecherche : " + frame.getTextDate().getText());

			if (verifierFormatDate(frame.getTextDate().getText())) {
				Date dateDebut = null;

				try {
					dateDebut = formaterDate.parse(frame.getTextDate().getText());

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				EnvoiSmsDAO envoiSmsDAO = (EnvoiSmsDAO) context.getBean("envoiSmsDAO");
				List<ContratCpt> listeComptes = new ArrayList<ContratCpt>();
				listeComptes = envoiSmsDAO.getListeComptes(dateDebut);

				/*******************************************/
				if (listeComptes != null && listeComptes.size() > 0) {
					for (ContratCpt cpt : listeComptes) {

						logger.info("Compte n° " + cpt.getContratCptId().getCompteClient() + "==> dateRecherche : "
								+ formaterDate.format(dateDebut));
						EnvoiSMSVo smsVo = new EnvoiSMSVo();
						smsVo.setContratCpt(cpt);
						smsVo.setDateComptable(dateDebut);
					
						SwingInfoVo infoVo = new SwingInfoVo();
						infoVo.setCompteClient(cpt.getContratCptId().getCompteClient() );
						infoVo.setEtat(Constants.STATUT_EN_COURS_ENVOI);
						infoVo.setDateComptable(formaterDate.format(dateDebut));
						frame.addOrUpdateEtat(infoVo);

						// ********Batch *************//
						MoulinetteEnvoiSMS moulinette = new MoulinetteEnvoiSMS(smsVo);
						moulinette.setMainFrame(frame);
						moulinette.perform();

					}

					frame.getEnd_exec_label().setText("Moulinette Envoi SMS terminée avec succès");
					frame.getBtnExcuter().setEnabled(true);
					logger.info("*** FIN MoulinetteEnvoiSMS ***");
				} else {

					frame.getEnd_exec_label().setText("Pas des opérations sur des comptes epargnes pour la date du "
							+ DateHandler.dateToStr(dateDebut));
					frame.getBtnExcuter().setEnabled(true);
					logger.info("*** FIN MoulinetteEnvoiSMS ***");
				}
			} else {

				frame.getEnd_exec_label().setText("Verifiez les deux dates des périodes !");
				frame.getBtnExcuter().setEnabled(true);
			}
		} catch (Exception e) {

			frame.getEnd_exec_label().setText(e.getMessage());
			frame.getBtnExcuter().setEnabled(true);

			e.printStackTrace();

			

		}

	}

	public boolean verifierFormatDate(String strDate) {

		boolean stade = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = new Date();
		try {
			d = sdf.parse(strDate);
			String t = sdf.format(d);
			if (t.compareTo(strDate) != 0) {
				System.out.println("DATE  " + strDate + "  Non valide");
				stade = false;
			} else {
				stade = true;
			}
		} catch (Exception e) {
			System.out.println("Exception");
			stade = false;
		}
		return stade;
	}

}
