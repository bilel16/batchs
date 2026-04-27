package com.bna.smile.batch.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.batch.moulinette.MoulinetteEnvoiSMSPowerCard;
import com.bna.smile.model.SMS.dao.EnvoiSmsDAO;
import com.bna.smile.model.SMS.model.PowerCardSMS;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.EnvoiSMSVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteEnvoiSMSPowerCardTest implements Runnable {

	private static final Logger logger = Logger.getLogger(MoulinetteEnvoiSMSPowerCardTest.class);
	private BatchEnvoiSmsPowerCardFrame frame = null;
	private static final SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	Date dateComptableFichier = new Date();
	String pathAFBTravail = "";
	String fileName = "";
	String repertoire = "";
	SimpleDateFormat formaterDate2 = new SimpleDateFormat("ddMMyyyy");
	String codeDevise = Constants.COD_DEV_DINAR.toString();

	// *********************************//

	public BatchEnvoiSmsPowerCardFrame getFrame() {
		return frame;
	}

	public void setFrame(BatchEnvoiSmsPowerCardFrame frame) {
		this.frame = frame;
	}

	public MoulinetteEnvoiSMSPowerCardTest() {
		super();
	}

	public MoulinetteEnvoiSMSPowerCardTest(BatchEnvoiSmsPowerCardFrame frame) {
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

			EnvoiSmsDAO envoiSmsDAO = (EnvoiSmsDAO) context.getBean("envoiSmsDAO");
			List<PowerCardSMS> listeSmsPowerCard = new ArrayList<PowerCardSMS>();
			listeSmsPowerCard = envoiSmsDAO.getListeSMSPowerCard();

			/*******************************************/
			if (listeSmsPowerCard != null && listeSmsPowerCard.size() > 0) {
				for (PowerCardSMS smsPowerCard : listeSmsPowerCard) {

					logger.info("Telephone n° " + smsPowerCard.getNumTelSal());
					EnvoiSMSVo smsVo = new EnvoiSMSVo();
					smsVo.setNumTelephone(smsPowerCard.getNumTelSal());
					smsVo.setTextSms(smsPowerCard.getTextSmsSal());
					smsVo.setPowerCardSMS(smsPowerCard);

					SwingInfoVo infoVo = new SwingInfoVo();
					infoVo.setNumeroTelephone(smsPowerCard.getNumTelSal());
					infoVo.setInfo(smsPowerCard.getTextSmsSal());
					infoVo.setEtat(Constants.STATUT_EN_COURS_ENVOI);
					frame.addOrUpdateEtat(infoVo);

					// ********Batch *************//
					MoulinetteEnvoiSMSPowerCard moulinette = new MoulinetteEnvoiSMSPowerCard(smsVo);
					moulinette.setMainFrame(frame);
					moulinette.perform();

				}

				frame.getEnd_exec_label().setText("Moulinette Envoi SMS PowerCard terminée avec succès");
				frame.getBtnExcuter().setEnabled(true);
				logger.info("*** FIN MoulinetteEnvoiSMS ***");
			} else {

				frame.getEnd_exec_label().setText("Pas d'opérations recharges PowerCard à envoyer ");
				frame.getBtnExcuter().setEnabled(true);
				logger.info("*** FIN MoulinetteEnvoiSMSPowerCard ***");
			}

		} catch (Exception e) {
			e.printStackTrace();
			frame.getEnd_exec_label().setText(e.getMessage());
			frame.getBtnExcuter().setEnabled(true);

			e.printStackTrace();

		}

	}

}
