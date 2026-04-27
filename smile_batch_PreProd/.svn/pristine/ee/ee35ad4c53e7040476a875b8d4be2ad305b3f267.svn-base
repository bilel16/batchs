package com.bna.smile.model.domainecommun.traitement;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.banqueAssurance.service.RedirectionUrlCallback;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.AFBVo;
import com.bna.smile.model.domainecommun.model.SocietesAFBView;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import javax.activation.*;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.WebCredentials;

public class GestionAFBTrt extends Traitement {

	Context context = ContextHandler.getContext();
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeureFichier = new SimpleDateFormat("HHmmss");
	String codeBanque = "";
	String ribDO = "";
	Date dateComptable = new Date();
	String tab[] = {};

	public GestionAFBTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		AFBVo AFBVo = (AFBVo) vo;
		SocietesAFBView societesAFBView = AFBVo.getSocietesAFBView();
		dateComptable = AFBVo.getDateComptable();
		String body = "Bonjour;";
		try {

			// ***** Partie Ecriture dans le Fichier *******//

			AFBVo afbVoFichier = new AFBVo();
			afbVoFichier.setFile(AFBVo.getFile());
			afbVoFichier.setDateComptable(dateComptable);
			afbVoFichier.setDateDebut(AFBVo.getDateDebut());
			afbVoFichier.setDateFin(AFBVo.getDateFin());

			afbVoFichier.setSocietesAFBView(societesAFBView);
			CreationFichierAFBTrt creationFichierAFBTrt = new CreationFichierAFBTrt();
			afbVoFichier = (AFBVo) creationFichierAFBTrt.exec(afbVoFichier);

			// ***** Partie Ecriture dans le Fichier mvts *******//

			// AFBVo afbVoFichier = new AFBVo();
			// afbVoFichier.setFile(file);
			// afbVoFichier.setDateComptable(dateComptable);
			// afbVoFichier.setSocietesAFBView(societesAFBView);
			// CreationFichierAFBMVTTrt creationFichierAFBMVTTrt = new CreationFichierAFBMVTTrt();
			// afbVoFichier = (AFBVo) creationFichierAFBMVTTrt.exec(afbVoFichier);

			if (afbVoFichier.isEtatEnregistrement() == true) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

				String subject = "";

				if (afbVoFichier.getDateDebut().equals(afbVoFichier.getDateFin())) {
					subject = "Fichier AFB du " + dateFormat.format(afbVoFichier.getDateDebut());
				} else {
					subject = "Fichier AFB du " + dateFormat.format(afbVoFichier.getDateDebut()) + " au  "
							+ dateFormat.format(afbVoFichier.getDateFin());
				}
				/************** Envoi Fichier par email ******************/
				if (societesAFBView.getEmailSoctAFB() != null) {
					String tab[] = societesAFBView.getEmailSoctAFB().split(";");
					String tabEmail[] = { "" };
					byte[] data = FileUtils.readFileToByteArray(afbVoFichier.getFile());
					String[] receivers = new String[1];
					receivers[0] = "hichem.saieb@bna.tn";
					// this.sendExchangeMail(receivers, subject,body, data, afbVoFichier.getFile().getName());
					String filesSEND[][] = { { afbVoFichier.getFile().getName(), afbVoFichier.getFile().getPath() } };

					for (int i = 0; i < tab.length; i++) {
						tabEmail[0] = tab[i];
						sendExchangeMail(tabEmail, subject, "Bonjour ;", filesSEND);
						System.out.println(subject + " envoyé avec succés ");
					}
				}
			}

			AFBVo.setEtatEnregistrement(afbVoFichier.isEtatEnregistrement());
			AFBVo.setMessageValidation(afbVoFichier.getMessageValidation());

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GestionAFBTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GestionAFBTrt");
			AFBVo.addError(erreur);
			logger.error("Erreur au niveau GestionAFBTrt : ", e);
			AFBVo.setMessageValidation("Probléme dans GestionAFBTrt");

			AFBVo.setEtatEnregistrement(false);
			throw new RuntimeException();

		}
		return (AFBVo);

	}

	public void genCroText(ValueObject vo) {
	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public static String lpadS(String valueToPad, String filler, int size) {
		StringBuilder builder = new StringBuilder();

		while (builder.length() + valueToPad.length() < size) {
			builder.append(filler);
		}
		builder.append(valueToPad);
		return builder.toString();
	}

	public void sendExchangeMail(String[] to, String subject, String body, String[][] attachements) {
		try {
			 ExchangeService service = new ExchangeService();
			
			 ExchangeCredentials credentials = new WebCredentials("Releve_compte_bna", "BNAbna2018", "bna");
			 service.setCredentials(credentials);
			 //service.setUrl(new URI("https://mail.bna.tn/owa"));
			 service.setUrl(new URI("https://outlook.office365.com/"));
			 service.autodiscoverUrl("releves.comptesBna@bna.tn", new RedirectionUrlCallback());
			
			 EmailMessage msg = new EmailMessage(service);
			 msg.setSubject(subject);
			 msg.setBody(MessageBody.getMessageBodyFromText(body));
			
			 for (String[] attachement : attachements) {
			 msg.getAttachments().addFileAttachment(attachement[0], attachement[1]);
			 }
			
			 msg.getToRecipients().add(to[0]);
			 System.out.println("sending to : " + to[0] + " .....");
			 msg.send();
			 System.out.println("mail sended sucessfully to : " + to[0]);
/*
			final String username = "hichem.saieb@bna.tn";
			final String password = "2019Rayhan***";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.bna.tn");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {

				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("hichem.saieb@bna.tn"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to[0]));
			message.setSubject(subject);
			message.setText(body);
			Multipart multipart = new MimeMultipart();
			// Part two is attachment
			for (String[] attachement : attachements) {

				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(attachement[0]);
				// DataSource source = new FileDataSource(attachement[0]);
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setFileName(attachement[0]);
				multipart.addBodyPart(messageBodyPart);
			}
			message.setContent(multipart);
			Transport.send(message);

			System.out.println("Done");
*/
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("fail to send mail to : " + to[0], e);
		}
	}

}