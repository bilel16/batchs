package com.bna.smile.model.domainecommun.traitement;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MailDocJoined {

	public static void SendMail(String to, String subject, String file) {
		try {
			// Create some properties and get the default Session;
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.planet.tn");
			props.put("mail.from", "sayeb.hichem@gmail.com");
			Session session = Session.getInstance(props, null);
			String from = "BNA";

			// Crée le message
			Message message = new MimeMessage(session);

			// On met les attributs d'entête ( sujet, adresse, expéditeur, destinataire)
			message.setSubject(subject);
			message.setFrom();
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Crée la partie message pour le contenu
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = "<H1>Bonjour</H1>";

			// Type et sous-type du message
			messageBodyPart.setContent(htmlText, "text/html");

			// Crée l'objet Multipart qui contiendra toutes les parties du message
			// on doit passer en paramètre "related", puisque les deux parties entres elles
			// sont reliées
			MimeMultipart multipart = new MimeMultipart("related");
			multipart.addBodyPart(messageBodyPart);

			// Crée l'autre partie du message qui contient l'image
			messageBodyPart = new MimeBodyPart();

			// Place l'image dans la partie
			DataSource fds = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(fds));

			// Ajoute la partie à l'objet Multipart
			multipart.addBodyPart(messageBodyPart);

			// Ajoute l'objet Multipart au message
			message.setContent(multipart);

			Transport.send(message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}