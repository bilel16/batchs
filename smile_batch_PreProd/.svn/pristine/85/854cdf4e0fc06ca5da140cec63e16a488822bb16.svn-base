package com.bna.smile.batch.test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.Structure;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.batch.moulinette.MoulinetteAFB;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.AFBDAO;
import com.bna.smile.model.domainecommun.model.AFBVo;
import com.bna.smile.model.domainecommun.model.SocietesAFBView;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteAFBTest implements Runnable {

	private static final Logger logger = Logger.getLogger(MoulinetteAFBTest.class);
	private BatchAFBFrame frame = null;
	private static final SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	Date dateComptableFichier = new Date();
	String pathAFBTravail = "";
	String fileName = "";
	String repertoire = "";
	SimpleDateFormat formaterDate2 = new SimpleDateFormat("ddMMyyyy");
	String codeDevise = Constants.COD_DEV_DINAR.toString();
	File file = null;

	// *********************************//

	public BatchAFBFrame getFrame() {
		return frame;
	}

	public void setFrame(BatchAFBFrame frame) {
		this.frame = frame;
	}

	public MoulinetteAFBTest() {
		super();
	}

	public MoulinetteAFBTest(BatchAFBFrame frame) {
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

			System.out.println("TextDateDebut : " + frame.getTextDateDebut().getText());
			System.out.println("TextDateFin : " + frame.getTextDateFin().getText());
			System.out.println("Societe n° : " + frame.getTextNumSociete().getText());
			String strDateFichier = "";
			if (verifierFormatDate(frame.getTextDateDebut().getText())
					&& verifierFormatDate(frame.getTextDateFin().getText())) {
				Date dateDebut = null;
				Date dateFin = null;
				Long codeSociete = null;
				Date dateFichier = null;
				try {
					dateDebut = formaterDate.parse(frame.getTextDateDebut().getText());
					dateFin = formaterDate.parse(frame.getTextDateFin().getText());

					if (formaterDate2.format(dateDebut).equals(formaterDate2.format(dateFin))) {
						strDateFichier = "_" + formaterDate2.format(dateDebut);
					} else {
						strDateFichier = "_" + formaterDate2.format(dateDebut) + "-" + formaterDate2.format(dateFin);
					}
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (frame.getTextNumSociete().getText() != null && frame.getTextNumSociete().getText().length() > 0) {
					try {
						codeSociete = Long.valueOf(frame.getTextNumSociete().getText());

					} catch (NumberFormatException e1) {
						codeSociete = null;
						e1.printStackTrace();
					}
				}

				// Util.initDirectoriesTeleComp();

				AFBDAO AFBDAO = (AFBDAO) context.getBean("AFBDAO");
				List<SocietesAFBView> listeSocietesAFB = new ArrayList<SocietesAFBView>();
				listeSocietesAFB = AFBDAO.getListeSocietesAFBView(codeSociete);
				Structure agence = new Structure();
				if (codeSociete.equals(99L)) {
					dateFichier = AFBDAO.getDateFichier();
					System.out.println("dateFichier : " + dateFichier);
					strDateFichier = "_" + formaterDate2.format(dateFichier);
				}
				AFBVo AFBVo = new AFBVo();
				if (listeSocietesAFB != null && listeSocietesAFB.size() > 0) {

					for (SocietesAFBView societesAFBView : listeSocietesAFB) {

						logger.info("*** Debut AFB ***");

						// ***** Partie Creation Fichier *******//

						pathAFBTravail = File.separatorChar + Configuration.getParentPath() + File.separatorChar + "AFB"
								+ File.separatorChar + formaterDate2.format(dateComptableFichier) + File.separatorChar;
						if (societesAFBView.getNomSoctAFB() != null && societesAFBView.getNomSoctAFB().length() != 0) {
							fileName = pathAFBTravail + societesAFBView.getNomSoctAFB() + strDateFichier + ".txt";
						} else {
							fileName = pathAFBTravail + "FEMAILAFBNSI" + ".txt";
						}

						// ***** Ecriture dans le fichier *****//

						File theDir = new File(pathAFBTravail);
						if (!theDir.exists()) {
							try {
								theDir.mkdirs();
							} catch (SecurityException se) {
								logger.info("Erreur : " + se.getMessage());
							}
						}

						file = new File(fileName);
						logger.info("file : " + file.getAbsolutePath());

						boolean exists = file.exists();

						if (!exists) {
							file.createNewFile();

						} else {

							file.delete();
							file.createNewFile();
						}

						/*******************************************/

						AFBVo.setSocietesAFBView(societesAFBView);
						SwingInfoVo infoVo = new SwingInfoVo();
						if (codeSociete.equals(99L)) {
							logger.info("societesAFBView " + societesAFBView.getNomSoctAFB()
									+ "==> dateComptableAgence " + formaterDate.format(dateFichier));
							AFBVo.setDateComptable(dateFichier);
							AFBVo.setDateDebut(dateFichier);
							AFBVo.setDateFin(dateFichier);
							infoVo.setDateComptable(formaterDate.format(dateFichier));
						} else {
							logger.info("societesAFBView " + societesAFBView.getNomSoctAFB()
									+ "==> dateComptableAgence " + formaterDate.format(dateComptableFichier));
							AFBVo.setDateComptable(dateComptableFichier);
							AFBVo.setDateDebut(dateDebut);
							AFBVo.setDateFin(dateFin);
							infoVo.setDateComptable(formaterDate.format(dateComptableFichier));
						}
						AFBVo.setFile(file);

						infoVo.setNumSocieteAFB(societesAFBView.getNumSoctAFB());
						infoVo.setNomSocieteAFB(societesAFBView.getNomSoctAFB());
						infoVo.setEtat(Constants.STATUT_EN_COURS_INSERT);

						frame.addOrUpdateEtat(infoVo);

						// ********Batch *************//
						MoulinetteAFB moulinette = new MoulinetteAFB(AFBVo);
						moulinette.setMainFrame(frame);
						moulinette.perform();

					}
				}

				frame.getEnd_exec_label().setText("Moulinette génération fichier AFB terminée avec succès");
				frame.getBtnExcuter().setEnabled(true);
				logger.info("*** FIN MoulinetteAFB ***");
				System.out.println("JOBOK");
			} else {

				frame.getEnd_exec_label().setText("Verifiez les deux dates des périodes !");
				frame.getBtnExcuter().setEnabled(true);
			}
		} catch (		Exception e) {

			frame.getEnd_exec_label().setText(e.getMessage());
			frame.getBtnExcuter().setEnabled(true);

			e.printStackTrace();

			if (file != null && file.exists()) {

				file.delete();

			}
			System.out.println("JOBNOTOK");
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
