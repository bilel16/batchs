package com.bna.smile.model.telex.service;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.smile.model.telex.model.NotificationTelex;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.WebCredentials;

public class MailingService {

	public void sendExchangeMail(String[] to, String subject, String body, byte[] attachement, NotificationTelex NotificationTelex) {
		try {
			ExchangeService service = new ExchangeService();

			ExchangeCredentials credentials = new WebCredentials("5555", "Esprit09175496", "bna");
			service.setCredentials(credentials);
			service.setUrl(new URI("https://mail.bna.tn/owa"));
			service.autodiscoverUrl("mohamed.gharbi@bna.tn", new RedirectionUrlCallback());
			// props.put("mail.smtp.ssl.enable", "true");
			EmailMessage msg = new EmailMessage(service);

			msg.setSubject(subject);

			msg.setBody(MessageBody.getMessageBodyFromText(body));

			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
			String formated_date = dateFormat.format(date);
			
			msg.getAttachments().addFileAttachment("Telex_"+NotificationTelex.getNUM_TEL()+"_"+formated_date+".pdf", attachement);
			
			for (String receiver : to) {
				msg.getToRecipients().add(receiver);
			}

			// msg.send();
			msg.sendAndSaveCopy();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String editBodyMessage(String numTelex, String numDossier, String dateGeneration) {
		
		String num_telex  = "<span style='color:blue;'>"+numTelex+"</span>";
		String num_dossier = "<span style='color:blue;'>"+numDossier+"</span>";
		String date_generation = "<span style='color:blue;'>"+dateGeneration+"</span>";
		
		String Newligne = "<br />";
		
		String body = "Bonjour,"+Newligne+
					   Newligne+
					   "ci-joint le Telex Pdf."+Newligne+Newligne+
				
				      "Numéro Telex  : "+num_telex+Newligne+
				      "Numéro Dossier : "+num_dossier+Newligne+
				      "Date Generation : "+ date_generation+Newligne+
				      Newligne+		
				      Newligne;
		
		String footer = "<h5>Direction Des Transferts" + Newligne+	 
					  		"Division Des Transferts Reçus" + Newligne+	 
					  		"Service Des Transferts Reçus</h5>";
		
		body = body+footer;
		
		return body;
		
		
		
	}
	
	
}
