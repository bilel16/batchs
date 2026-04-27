package com.bna.smile.model.SMS.traitement;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.TraceBatchEnvoiSms;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.SMS.dao.EnvoiSmsDAO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.EnvoiSMSVo;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GestionEnvoiSMSTrt extends Traitement {

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeureFichier = new SimpleDateFormat("HHmmss");
	String codeBanque = "";
	String ribDO = "";
	Date dateComptable = new Date();
	String numeroTel = "";
	String httpLogin;
	String httpPwd;
	String wbLogin;
	String wbPwd;
	String wbAccount;
	int type = 0;// 0 : latin ; 2 : arabic
	String date;// sending day ; format dd/mm/yyyy',
	String hour;// sending hour ; format HH
	String minute;// sending minute ; format MM
	String label; // source label
	String reference; // sending reference
	String application = null;// sending application
	String body = "";
	String testMsg = "";
	String codeRetourEnvoi = "";

	/*******************************/

	public GestionEnvoiSMSTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		EnvoiSMSVo envoiSMSVo = (EnvoiSMSVo) vo;
		ContratCpt contratCpt = envoiSMSVo.getContratCpt();
		dateComptable = envoiSMSVo.getDateComptable();

		try {
			httpLogin = ""; // not used in bnasms
			httpPwd = ""; // not used in bnasms
			wbLogin = "medyosn";
			wbPwd = "000000";
			wbAccount = "BNA";
			label = "BNASMS";
			reference = "";
			application = "SMILE"; // your application / version
			Date actuelle = new Date();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			date = dateFormat.format(actuelle);
			codeRetourEnvoi = "";
			EnvoiSmsDAO envoiSmsDAO = (EnvoiSmsDAO) context.getBean("envoiSmsDAO");

			numeroTel = envoiSmsDAO.getNumeroTelephone(contratCpt.getContratCptId().getCodStrcStrc(),
					contratCpt.getContratCptId().getCodPrdPrd(), contratCpt.getContratCptId().getNumCcptCcpt());

			if (numeroTel != null && numeroTel.length() >= 8 && numeroTel.equalsIgnoreCase("00000000") == false) {

				boolean etatnumTelephone = validateNumeroTelephone(numeroTel);

				// numeroTel = "40515824";
				if (etatnumTelephone == true) {
					List<EnvoiSMSVo> listeOperations = new ArrayList<EnvoiSMSVo>();
					listeOperations = envoiSmsDAO.getListeOperationsMoyenPayByCriteres(
							contratCpt.getContratCptId().getCodStrcStrc(), contratCpt.getContratCptId().getCodPrdPrd(),
							contratCpt.getContratCptId().getNumCcptCcpt(), dateComptable);

					if (listeOperations.size() > 0) {
						testMsg = "Le " + DateHandler.dateToStr(dateComptable) + ", cpte "
								+ contratCpt.getContratCptId().getCompteClient().replace(" ", "") + " a été";

						for (EnvoiSMSVo smsVo : listeOperations) {

							if (smsVo.getSens().equalsIgnoreCase("C")) {
								testMsg += " credité par une opération de " + smsVo.getLibOperation()
										+ " pour la somme du "
										+ StrHandler.formatMontant(smsVo.getMontantOperation(), Long.valueOf(3)) + ".";
							} else {
								testMsg += " débité par une opération de " + smsVo.getLibOperation()
										+ " pour la somme du "
										+ StrHandler.formatMontant(smsVo.getMontantOperation(), Long.valueOf(3)) + ".";
							}

						}

						/************ Envoi par SMS ***************/
						this.sendPost();
						/************* Insertion Trace **************/
						Calendar cal = Calendar.getInstance();

						TraceBatchEnvoiSms batchEnvoiSms = new TraceBatchEnvoiSms();
						batchEnvoiSms.setContratCpt(contratCpt);
						batchEnvoiSms.setNumTelPers(numeroTel);
						batchEnvoiSms.setDateOperBtch(dateComptable);
						batchEnvoiSms.setDateTimeTrc(cal.getTime());
						if (testMsg.length() > 300) {
							testMsg = testMsg.substring(0, 299);
						}
						batchEnvoiSms.setObjSmsTrc(testMsg);
						batchEnvoiSms.setCodRetSms(codeRetourEnvoi);
						crudService.create(batchEnvoiSms);
						envoiSMSVo.setNumTelephone(numeroTel);
						envoiSMSVo.setEtatEnregistrement(true);
						envoiSMSVo.setMessageValidation(testMsg);
					}
				} else {
					envoiSMSVo.setNumTelephone(numeroTel);
					envoiSMSVo.setEtatEnregistrement(true);
					envoiSMSVo.setMessageValidation("Numéro Télèphone incorrecte");
				}
			} else {
				envoiSMSVo.setNumTelephone(numeroTel);
				envoiSMSVo.setEtatEnregistrement(true);
				envoiSMSVo.setMessageValidation("Numéro Télèphone incorrecte");
			}

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GestionEnvoiSMSTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GestionEnvoiSMSTrt");
			envoiSMSVo.addError(erreur);
			logger.error("Erreur au niveau GestionEnvoiSMSTrt : ", e);
			envoiSMSVo.setMessageValidation("Probléme dans GestionEnvoiSMSTrt");

			envoiSMSVo.setEtatEnregistrement(false);
			throw new RuntimeException();

		}
		return (envoiSMSVo);

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

	public boolean validateNumeroTelephone(String numero) {
		boolean etatVerifNumberTelephone = false;
		try {
			if (!numero.trim().equals("") && numero.trim() != null) {

				String newNumber = numero.replace(".", "");
				newNumber = newNumber.replace(" ", "");
				Pattern p = Pattern.compile("[0-9]{1,8}");
				Matcher m = p.matcher(newNumber);

				if (newNumber.length() <= 8) {

					if (!m.matches()) {

						etatVerifNumberTelephone = false;
					} else {

						if (newNumber.substring(0, 1).equals("9") || newNumber.substring(0, 1).equals("5")
								|| newNumber.substring(0, 1).equals("4") || newNumber.substring(0, 1).equals("2")) {

							etatVerifNumberTelephone = true;
						} else {
							etatVerifNumberTelephone = false;
						}
					}
				} else {
					etatVerifNumberTelephone = false;
				}
			}
		} catch (Exception e) {
			etatVerifNumberTelephone = false;
		}
		return etatVerifNumberTelephone;
	}

	private void ssl() throws Exception {

		/************************************************ Avoid SSL ***************************************/

		TrustManager[] trustAllCerts = new TrustManager[]{ new X509TrustManager() {

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {

			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		/***************************************************************************************/

	}

	private void setHeader(HttpsURLConnection con, int i) throws Exception {
		// final String s = this.httpLogin+":"+this.httpPwd;
		// final byte[] authBytes = s.getBytes(StandardCharsets.UTF_8);
		// final String encoded = Base64.getEncoder().encodeToString(authBytes);
		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", this.application);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Length", "" + i);
		// con.setRequestProperty("Authorization", "Basic " + encoded);
	}

	// HTTP POST request
	private void sendPost() throws Exception {

		this.ssl();

		String urlParameters = "login=" + this.wbLogin + "&pass=" + this.wbPwd + "&compte=" + this.wbAccount + "&op=1"
				+ "&dest_num=" + this.numeroTel + "&msg=" + this.testMsg + "&type=" + this.type + "&dt=" + this.date
				+ "&hr=00" + "&mn=00" + "&label=" + this.label + "&ref=" + this.reference;

		String url = "http://10.1.8.1/wbmonitor/send/webapi/send_ack.php";

		URL obj = new URL(url);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// this.setHeader(con,urlParameters.length());

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\n Sending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		codeRetourEnvoi=responseCode+"";
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

	}
}