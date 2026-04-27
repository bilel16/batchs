package com.bna.smile.model.domainecommun.traitement;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.WebCredentials;

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

public class GestionAFBTrt2 extends Traitement {

	Context context = ContextHandler.getContext();
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeureFichier = new SimpleDateFormat("HHmmss");
	String codeBanque = "";
	String ribDO = "";
	Date dateComptable = new Date();

	public GestionAFBTrt2() {
	}

	public IValueObject perform(IValueObject vo) {

		AFBVo AFBVo = (AFBVo) vo;
		SocietesAFBView societesAFBView = AFBVo.getSocietesAFBView();
		dateComptable = AFBVo.getDateComptable();

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
					subject =
							"Fichier AFB du " + dateFormat.format(afbVoFichier.getDateDebut()) + " au  "
									+ dateFormat.format(afbVoFichier.getDateFin());
				}
				/************** Envoi Fichier par email ******************/
				// MailDocJoined.SendMail("hichem.sayeb@gmail.com", subject, afbVoFichier.getFile().getAbsolutePath());
				byte[] data = FileUtils.readFileToByteArray(afbVoFichier.getFile());
//				this.sendEmail("nesrine.ghrairi@bna.tn", subject, null, data, afbVoFichier.getFile().getName());
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

	public boolean sendEmail(String mailDestinataire, String objet, String corps, byte[] attachement,
			String attachementName) throws Exception {

		Boolean flag = false;
		try {

			String passMail = "Bna40515824";
			String urlMail = "https://mail.bna.tn/owa";
			String mailEnvoi = "hichem.saieb@bna.tn";

			ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);

			ExchangeCredentials credentials = new WebCredentials("mailassurance", "bna+2019", "bna");
			service.setCredentials(credentials);
			service.setUrl(new URI("https://mail.bna.tn/owa"));
			service.autodiscoverUrl("mail.assurance@bna.tn", new RedirectionUrlCallback());

			// ExchangeCredentials credentials = new WebCredentials("4984", passMail, "bna");
			// service.setCredentials(credentials);
			// service.setUrl(new URI(urlMail));
			// service.autodiscoverUrl(mailEnvoi);

			EmailMessage msg = new EmailMessage(service);
			msg.setSubject(objet);
			msg.setBody(MessageBody.getMessageBodyFromText(corps));
			msg.getToRecipients().add(mailDestinataire);
			if (attachement != null) {
				msg.getAttachments().addFileAttachment(attachementName, attachement);
			}
			msg.send();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return flag;

	}
}

//class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
//    public boolean autodiscoverRedirectionUrlValidationCallback(
//            String redirectionUrl) {
//        return redirectionUrl.toLowerCase().startsWith("https://");
//    }
//}