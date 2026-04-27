package com.bna.smile.batch.telex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.telex.dao.TelexDAO;
import com.bna.smile.model.telex.model.CodPostal;
import com.bna.smile.model.telex.model.Devise;
import com.bna.smile.model.telex.model.Gouvernorat;
import com.bna.smile.model.telex.model.NotificationTelex;
import com.bna.smile.model.telex.service.MailingService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;

public class Telex  implements Runnable {

	String pathFileInRep;
	String[] args;
	//Parametres parametres = new Parametres();
	// public static Context context = ContextHandler.getContext();
	// public static TelexDAO telexDAO = (TelexDAO) context.getBean("TelexDAO");

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public static String absolute_file_name = ".\\jasper\\Telex.jasper";

	 @Override
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
	TelexDAO telexDAO = (TelexDAO) context.getBean("TelexDAO");
	MailingService mailingService = (MailingService) context.getBean("MailingService");

	List<NotificationTelex> list_notifications = telexDAO.getListeNotification(Constants.TELEX_NOT_SENDED);

	// try {
	// absolute_file_name = parametres.getTypeValeur(String.valueOf("Chemin
	// répertoire génération PDF"));
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	Map parameters = new HashMap();

	try {
		for (NotificationTelex notificationTelex : list_notifications) {
			String mail_agence = telexDAO.getStructure(notificationTelex.getCODE_AGENCE()).getLIB_MAIL_STRC();

			parameters = getTelexParams(notificationTelex);

			if (mail_agence != null) {

				printReport(absolute_file_name, parameters, null, notificationTelex, mail_agence);
			} else {
				// mail to admin
				String body = "mail not found for structure " + notificationTelex.getCODE_AGENCE();
				String subject_erreur = "mail not found for telex notification";
				mailingService.sendExchangeMail(Constants.ADMIN_MAILS, subject_erreur, body, null, null);
			}

			parameters.clear();
		}
	} catch (Exception e) {

		e.printStackTrace();
		// send to admin
		e.printStackTrace();
		String body = e.getCause().toString();
		String subject_erreur = "runtime Exception";
		mailingService.sendExchangeMail(Constants.ADMIN_MAILS, subject_erreur, body, null, null);

	}

	 }

	public static void main(String[] args) {

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
		TelexDAO telexDAO = (TelexDAO) context.getBean("TelexDAO");
		MailingService mailingService = (MailingService) context.getBean("MailingService");

		List<NotificationTelex> list_notifications = telexDAO.getListeNotification(Constants.TELEX_NOT_SENDED);

		// try {
		// absolute_file_name = parametres.getTypeValeur(String.valueOf("Chemin
		// répertoire génération PDF"));
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		Map parameters = new HashMap();

		try {
			for (NotificationTelex notificationTelex : list_notifications) {
				String mail_agence = telexDAO.getStructure(notificationTelex.getCODE_AGENCE()).getLIB_MAIL_STRC();

				parameters = getTelexParams(notificationTelex);

				if (mail_agence != null) {

					printReport(absolute_file_name, parameters, null, notificationTelex, mail_agence);
				} else {
					// mail to admin
					String body = "mail not found for structure " + notificationTelex.getCODE_AGENCE();
					String subject_erreur = "mail not found for telex notification";
					mailingService.sendExchangeMail(Constants.ADMIN_MAILS, subject_erreur, body, null, null);
				}

				parameters.clear();
			}
		} catch (Exception e) {

			e.printStackTrace();
			// send to admin
			e.printStackTrace();
			String body = e.getCause().toString();
			String subject_erreur = "runtime Exception";
			mailingService.sendExchangeMail(Constants.ADMIN_MAILS, subject_erreur, body, null, null);

		}

	}

	public static Map<String, String> getTelexParams(NotificationTelex notificationTelex) throws Exception {

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
		TelexDAO telexDAO = (TelexDAO) context.getBean("TelexDAO");
		MailingService mailingService = (MailingService) context.getBean("MailingService");
		try {

			Gouvernorat gouvernorat = null;
			long nombreChiffreApresVirgule = 0L;
			Long cod_postal = 0L;
			String ville = "";
			String montant = "";

			Devise devise = telexDAO.getDevise(notificationTelex.getCODE_DEVISE());

			if (devise != null) {
				nombreChiffreApresVirgule = devise.getNBR_DEC_DEV();
			} else {
				nombreChiffreApresVirgule = 2;
			}
			CodPostal codPostal = telexDAO.getVille(Integer.valueOf(notificationTelex.getCODE_POSTAL()));
			if (codPostal != null) {
				ville = codPostal.getLIB_CP_CP();
				gouvernorat = telexDAO.getGouvernorat(Integer.valueOf(codPostal.getCOD_GOUV_GOUV()));
			}

			Map parameters = new HashMap();

			String Date_generation = DateHandler.dateToStr(notificationTelex.getDATE_GENERATION());
			String Date_operation = DateHandler.dateToStr(notificationTelex.getDATE_OPERATION());

			if (nombreChiffreApresVirgule > 0) {
				montant = getFormatedMontant(notificationTelex.getMONTANT().toString(), nombreChiffreApresVirgule);
			}

			parameters.put("Date_generation", Date_generation);
			parameters.put("Relation", notificationTelex.getRELATION());

			if (notificationTelex.getADRESSE() == null || notificationTelex.getADRESSE().equals("")) {
				parameters.put("Adresse", "non specifiée");
			} else {
				parameters.put("Adresse", notificationTelex.getADRESSE());
			}
			if (notificationTelex.getCODE_POSTAL() == null || notificationTelex.getCODE_POSTAL().equals("")) {
				parameters.put("cod_postal", "non specifiée");
			} else {
				parameters.put("cod_postal", notificationTelex.getCODE_POSTAL());
			}
			if (ville.equals("")) {
				parameters.put("ville", "non specifiée");
			} else {
				parameters.put("ville", ville);
			}
			parameters.put("type_dossier", notificationTelex.getTYPE_DOSSIER());
			parameters.put("Num_dossier", notificationTelex.getNUM_DOSSIER().toString());
			parameters.put("Date_operation", Date_operation);
			parameters.put("sigle_devise", devise.getLIB_SIGL_DEV());
			parameters.put("montant", montant.toString());
			parameters.put("ordonateur", notificationTelex.getORDONNATEUR());
			parameters.put("commentaire", notificationTelex.getCOMMENTAIRE());
			parameters.put("compte", notificationTelex.getCOMPTE_CLIENT());

			return parameters;

		} catch (Exception e) {
			// send to admin
			e.printStackTrace();
			String body = e.getCause().toString();
			String subject_erreur = "runtime Exception Telex notification, num telex : "
					+ notificationTelex.getNUM_TEL() + ", num_dossier :" + notificationTelex.getNUM_DOSSIER();
			mailingService.sendExchangeMail(Constants.ADMIN_MAILS, subject_erreur, body, null, null);

			return null;
		}

	}

	public static String printReport(String absoluteFileName, Map<String, String> parameters, Connection connection,
			NotificationTelex notificationTelex, String mailAgence) {

		/**/
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
		MailingService mailingService = (MailingService) context.getBean("MailingService");
		TelexDAO telexDAO = (TelexDAO) context.getBean("TelexDAO");
		/**/

		String[] receiver = { "" };
		String Newligne = "<br />";
		// String body = "ci-joint telex pdf " + Newligne + "numero telex :" + num_telex
		// + Newligne + "numero dossier : "
		// + num_dossier;
		String subject = "Telex Notification";
		try {
			byte[] bytes = null;
			if (connection == null) {
				// bytes = JasperRunManager.runReportToPdf(absoluteFileName, parameters, new
				// JREmptyDataSource());
				bytes = JasperRunManager.runReportToPdf(absoluteFileName, parameters, new JREmptyDataSource());

				receiver[0] =  "mahdi.ayari@bna.tn";// mail_agence;

				String body = mailingService.editBodyMessage(notificationTelex.getNUM_TEL().toString(),
						notificationTelex.getNUM_DOSSIER().toString(),
						DateHandler.dateToStr(notificationTelex.getDATE_GENERATION()));
				mailingService.sendExchangeMail(receiver, subject, body, bytes, notificationTelex);
				telexDAO.updateEtatNotification(Long.valueOf(notificationTelex.getNUM_DOSSIER()),
						notificationTelex.getNUM_TEL(), Constants.TELEX_SENDED);

			} else {
				// bytes = JasperRunManager.runReportToPdf(absoluteFileName, parameters,
				// connection);
				// connection.close();
			}
			if (bytes != null) {
                 String dir = "D:\\telex";
//				return createFileFromByte(bytes, new File(absoluteFileName).getParentFile(),
//						notificationTelex.getNUM_TEL().toString());
                 return createFileFromByte(bytes, new File(dir),
 						notificationTelex.getNUM_TEL().toString());
                 
			}
		} catch (Exception e) {
			e.printStackTrace();
			String body = e.getCause().toString();
			String subject_erreur = "runtime Exception Telex notification, num telex : "
					+ notificationTelex.getNUM_TEL() + ", num_dossier :" + notificationTelex.getNUM_DOSSIER();
			mailingService.sendExchangeMail(Constants.ADMIN_MAILS, subject_erreur, body, null, null);

		}

		return null;

	}

	private static String createFileFromByte(byte[] bytes, File reportDir, String num_telex) {

		// 01. deleting existing files
		File[] files = reportDir.listFiles();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String prefix = sdf.format(new Date());
//		if (files != null && files.length > 0) {
//			for (File file : files) {
//				if (file.getName() != null && file.getName().endsWith(".pdf") && !file.getName().startsWith(prefix)) {
//					file.delete();
//				}
//			}
//		}

		// 02. generting file name
		UUID fileName = UUID.randomUUID();

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
		String formated_date = dateFormat.format(date);
		String pdf_name = "Telex_" + num_telex + "_" + formated_date + ".pdf";

		File[] old_files = reportDir.listFiles();
		for (File old_file : old_files) {
			if (old_file.getName().equals(pdf_name)) {
				old_file.delete();
			}
		}

		// File file = new File(reportDir, prefix + fileName.toString() + ".pdf");

		File file = new File(reportDir, pdf_name);

		FileOutputStream fos = null;
		try {
			// 03. creating file
			fos = new FileOutputStream(file);
			fos.write(bytes);
			System.out.println(file.getName());
			return file.getName();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String getFormatedMontant(String mnt, long nombreChiffreApresVirgule) {
		// return StrHandler.formatmnt(this.contratCpt.getMontAutCcpt());

		String montantFormatte = "";

		try {
			Long montant = Long.valueOf(mnt.trim());
			montantFormatte = formatMontant(montant, Long.valueOf(nombreChiffreApresVirgule));

		} catch (NumberFormatException e) {
			montantFormatte = mnt;
		} catch (Exception e) {
			montantFormatte = mnt;
		}
		return montantFormatte;
	}

	public static String formatMontant(long montant, long nombreChiffreApresVirgule) {
		String montantFormate = new String();
		montantFormate = String.valueOf(montant);
		if (montantFormate.length() < (((int) nombreChiffreApresVirgule) + 1)) {
			montantFormate = lpad(montantFormate, '0', ((int) nombreChiffreApresVirgule) + 1);
		}

		String avantVirgule = montantFormate.substring(0, (int) (montantFormate.length() - nombreChiffreApresVirgule));
		// -------------------------------------------//
		// ------ Faire le separateur d'espace -------//
		String decimal = "";
		String resultat = "";

		for (int i = avantVirgule.length(); i > 0;) {
			if (avantVirgule.length() > 3) {
				decimal = avantVirgule.substring(i - 3, i);
				avantVirgule = avantVirgule.substring(0, avantVirgule.length() - 3);
				resultat = decimal + " " + resultat;
				i = i - 3;
			} else {
				resultat = avantVirgule + " " + resultat;
				break;
			}
		}

		String apresVirgule = montantFormate.substring((int) (montantFormate.length() - nombreChiffreApresVirgule),
				montantFormate.length());

		montantFormate = resultat.substring(0, resultat.length() - 1) + "." + apresVirgule;
		return montantFormate;

	}

	public static String lpad(String item, char character, int size) {
		char[] array = new char[size];
		if (item.length() < size) {
			int len = size - item.length();

			for (int i = 0; i < len; i++)
				array[i] = character;

			item.getChars(0, item.length(), array, size - item.length());

			return String.valueOf(array);
		} else {
			return item;
		}

	}

}
