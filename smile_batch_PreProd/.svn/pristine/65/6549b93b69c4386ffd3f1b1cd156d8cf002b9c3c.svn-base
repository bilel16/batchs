package com.bna.smile.model.banqueAssurance.traitement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bna.commun.model.ContratAssuranceAgricole;
import com.bna.commun.model.ContratAssuranceVoyage;
import com.bna.commun.model.DetailAssuranceVoyage;
import com.bna.commun.model.TarifAssuranceVoyage;
import com.bna.commun.model.TraceAssuranceVoyage;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.batch.test.MoulinetteEnvoiFichierAssurance;
import com.bna.smile.model.banqueAssurance.dao.AssuranceVoyageDAO;
import com.bna.smile.model.banqueAssurance.service.RedirectionUrlCallback;
import com.bna.smile.model.banqueAssurance.vo.ContratAssuranceVo;
import com.bna.smile.model.domainecommun.model.AFBVo;
import com.bna.smile.model.domainecommun.model.SocietesAFBView;
import com.bna.smile.model.domainecommun.traitement.GestionAFBTrt2;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.WebCredentials;

public class EnvoisFichierTrt2 extends Traitement {

	private static final Log LOGGER = LogFactory.getLog(MoulinetteEnvoiFichierAssurance.class.getSimpleName());

	Context context = ContextHandler.getContext();
	AssuranceVoyageDAO assuranceVoyageDAO = (AssuranceVoyageDAO) context.getBean("assuranceVoyageDAO");
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	CompensationEffetVo compensationVo;
	private static final String SERVIDOR_SMTP = "bnatn.mail.protection.outlook.com";
	private static final int PORTA_SERVIDOR_SMTP = 25;
	private static final String CONTA_PADRAO = "";
	private static final String SENHA_CONTA_PADRAO = "";

	private final String from = "mail.assurance@bna.tn";

	@Override
	public IValueObject perform(IValueObject vo) throws ParseException, IOException {

		ContratAssuranceVo contratAss = (ContratAssuranceVo) vo;

		SimpleDateFormat formatDateFile = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat formatDateFileAFB = new SimpleDateFormat("dd/MM/yyyy");

		String jjmmyyyySys = formatDateFileAFB.format(DateHandler.strToDate(contratAss.getDateExtraction()));

		List<String> listeLignesAMI_VIE = new ArrayList<String>();
		List<String> listeLignesAMI_VOY = new ArrayList<String>();
		List<String> listeLignesMAG = new ArrayList<String>();
		List<String> listeLignesCTAM = new ArrayList<String>();
		List<String> listeLignesSTAR = new ArrayList<String>();

		List<ContratAssuranceVo> result_AMI_VIE =
				assuranceVoyageDAO.getVueContratAssurance(new Long("2004"), jjmmyyyySys);
		LOGGER.info("result_AMI_VIE " + result_AMI_VIE.size());

		List<ContratAssuranceVo> result_MAG = assuranceVoyageDAO.getVueContratAssurance(new Long("2000"), jjmmyyyySys);
		LOGGER.info("result_MAG " + result_MAG.size());

		List<ContratAssuranceVo> result_CTAM = assuranceVoyageDAO.getVueContratAssurance(new Long("2001"), jjmmyyyySys);
		LOGGER.info("result_CTAM " + result_CTAM.size());

		ICriteria criteria = searchEngine.createCriteria();
		IExpression expression = searchEngine.createExpression();
		criteria.add(expression.ge("dateCrtCassv", DateHandler.strToDate(jjmmyyyySys)));
		criteria.add(expression.lt("dateCrtCassv", DateHandler.addJour(DateHandler.strToDate(jjmmyyyySys), +1)));
		List<ContratAssuranceVoyage> result_AMI_VOY = searchEngine.find(ContratAssuranceVoyage.class, criteria);
		LOGGER.info("resultAMI_VOY " + result_AMI_VOY.size());

		ICriteria criteriaAgricole = searchEngine.createCriteria();
		IExpression expressionAgricole = searchEngine.createExpression();
		criteriaAgricole.add(expressionAgricole.eq("codEtatCaag", "V"));
		criteriaAgricole.add(expressionAgricole.ge("datCrtCaag", DateHandler.strToDate(jjmmyyyySys)));
		criteriaAgricole
				.add(expressionAgricole.lt("datCrtCaag", DateHandler.addJour(DateHandler.strToDate(jjmmyyyySys), +1)));
		List<ContratAssuranceAgricole> result_STAR =
				searchEngine.find(ContratAssuranceAgricole.class, criteriaAgricole);
		LOGGER.info("result_STAR " + result_STAR.size());

		Collections.sort(result_AMI_VOY, new Comparator<ContratAssuranceVoyage>() {

			public int compare(ContratAssuranceVoyage arg0, ContratAssuranceVoyage arg1) {
				return arg0.getDateCrtCassv().compareTo(arg1.getDateCrtCassv());
			}
		});

		if (result_AMI_VOY.size() > 0) {
			for (int i = 0; i < result_AMI_VOY.size(); i++) {
				ContratAssuranceVoyage contratAssuranceVoyage = result_AMI_VOY.get(i);
				for (TraceAssuranceVoyage traceAssuranceVoyage : contratAssuranceVoyage.getTraceAssuranceVoyages()) {

					if (traceAssuranceVoyage.getDateTracev().compareTo(DateHandler.strToDate(jjmmyyyySys)) > 0
							|| traceAssuranceVoyage.getDateTracev()
									.compareTo(DateHandler.strToDate(jjmmyyyySys)) == 0) {
						String codeOperation = "";
						String dateOperation = formatDateFile.format(traceAssuranceVoyage.getDateTracev());
						if (traceAssuranceVoyage.getOperation().getCodOperOper().equals(Long.valueOf("2317")))
							codeOperation = "S";
						if (traceAssuranceVoyage.getOperation().getCodOperOper().equals(Long.valueOf("2318")))
							codeOperation = "A";
						if (traceAssuranceVoyage.getOperation().getCodOperOper().equals(Long.valueOf("2320")))
							codeOperation = "M";
						String dateAdh = formatDateFile.format(contratAssuranceVoyage.getDateCrtCassv());
						String numAdh = contratAssuranceVoyage.getNumCrtCassv();
						TarifAssuranceVoyage tarif = new TarifAssuranceVoyage();
						tarif = (TarifAssuranceVoyage) searchEngine.get(TarifAssuranceVoyage.class,
								contratAssuranceVoyage.getTarifAssuranceVoyage().getCodTassTassv());
						String typeAdh = tarif.getTypeAdhTassv();
						String codeAgence = StrHandler.rpad("" + contratAssuranceVoyage.getCodStrCassv(), '0', 3);
						String dateDepart = formatDateFile.format(contratAssuranceVoyage.getDateDebCassv());
						String dateRetour = formatDateFile.format(contratAssuranceVoyage.getDateFinCassv());
						String destination = contratAssuranceVoyage.getPays().getCodPaysPays();
						String numPassport = null;
						if (contratAssuranceVoyage.getNumPasseportCassv().length() <= 10)
							numPassport = String.format("%1$-10s", contratAssuranceVoyage.getNumPasseportCassv());
						else
							numPassport = contratAssuranceVoyage.getNumPasseportCassv().substring(0, 11);
						String nomPrenom = String.format("%1$-60s", contratAssuranceVoyage.getNomBenfCassv() + " "
								+ contratAssuranceVoyage.getPrnBenfCassv());
						String dateNais = formatDateFile.format(contratAssuranceVoyage.getDateNaisCassv());
						TarifAssuranceVoyage tarifAssuranceVoyageNew = new TarifAssuranceVoyage();
						tarifAssuranceVoyageNew
								.setCodTassTassv(contratAssuranceVoyage.getTarifAssuranceVoyage().getCodTassTassv());
						ICriteria criteriaTarif = searchEngine.createCriteria();
						IExpression expressionTarif = searchEngine.createExpression();
						criteriaTarif
								.add(expressionTarif.eq("codTassTassv", tarifAssuranceVoyageNew.getCodTassTassv()));
						TarifAssuranceVoyage tt = new TarifAssuranceVoyage();
						List resTarif = searchEngine.find(TarifAssuranceVoyage.class, criteriaTarif);
						tt = (TarifAssuranceVoyage) resTarif.get(0);
						String montantPrimeComm = String.format("%015d", contratAssuranceVoyage.getMntPrcomCassv());

						String montantCommBanq = String.format("%015d", contratAssuranceVoyage.getComBanqueCassv());

						String montantPrimeTotaleAss = String.format("%015d",
								contratAssuranceVoyage.getMntPrcomCassv() - contratAssuranceVoyage.getComBanqueCassv());

						String montantRetenuSource = null;
						if (contratAssuranceVoyage.getRetSourceCassv() != null)
							montantRetenuSource = String.format("%015d", contratAssuranceVoyage.getRetSourceCassv());
						else
							montantRetenuSource = String.format("%015d", 0L);
						String adresse = String.format("%1$-100s", contratAssuranceVoyage.getAdrsBenfCassv());
						String numTel = String.format("%1$-15s", contratAssuranceVoyage.getNumTelpersCassv());
						String profession = String.format("%1$-100s", contratAssuranceVoyage.getProfBenfCassv());

						String ligne = "";
						if (typeAdh.equals("I"))
							ligne = codeOperation + dateOperation + dateAdh + numAdh + typeAdh + " " + dateDepart
									+ dateRetour + destination + numPassport + nomPrenom + dateNais + montantPrimeComm
									+ montantPrimeTotaleAss + montantCommBanq + montantRetenuSource + adresse + numTel
									+ profession;

						else
							ligne = codeOperation + dateOperation + dateAdh + numAdh + typeAdh + "1" + dateDepart
									+ dateRetour + destination + numPassport + nomPrenom + dateNais + montantPrimeComm
									+ montantPrimeTotaleAss + montantCommBanq + montantRetenuSource + adresse + numTel
									+ profession;

						listeLignesAMI_VOY.add(ligne);

						if (typeAdh.equals("F")) {
							List<DetailAssuranceVoyage> listeFamille = new ArrayList<DetailAssuranceVoyage>();
							listeFamille = assuranceVoyageDAO.getlisteFamille(contratAssuranceVoyage.getNumCrtCassv());
							int d = 2;
							for (DetailAssuranceVoyage detailAssuranceVoyage : listeFamille) {
								String ligneDetail = "";

								ligneDetail = codeOperation + dateOperation + dateAdh + numAdh + typeAdh
										+ String.valueOf(d++) + dateDepart + dateRetour + destination
										+ String.format("%1$-10s", detailAssuranceVoyage.getNumPasseportDassv())
										+ String.format("%1$-60s",
												detailAssuranceVoyage.getNomBenfDassv() + " "
														+ detailAssuranceVoyage.getPrnBenfDassv())
										+ formatDateFile.format(detailAssuranceVoyage.getDateNaisDassv())
										+ montantPrimeComm + montantPrimeTotaleAss + montantCommBanq
										+ montantRetenuSource + adresse + numTel + profession;
								listeLignesAMI_VOY.add(ligneDetail);

							}
						}

					}
				}
			}
		}

		if (result_AMI_VIE.size() > 0) {
			for (int i = 0; i < result_AMI_VIE.size(); i++) {
				ContratAssuranceVo contratAssuranceVo = result_AMI_VIE.get(i);
				String dateJour = contratAssuranceVo.getDateJour();
				String datePremiereEcheance = contratAssuranceVo.getDatePremiereEcheance();
				String franchise = contratAssuranceVo.getFranchise();
				String assure = StrHandler.rpad(contratAssuranceVo.getAssure(), ' ', 40);
				String adresse = contratAssuranceVo.getAdresse();
				String cin = contratAssuranceVo.getCin();
				String rib = contratAssuranceVo.getRib();
				String profession = contratAssuranceVo.getProfession();
				String dateNaissance = contratAssuranceVo.getDateNaissance();
				String capital = StrHandler.lpad(contratAssuranceVo.getCapital(), '0', 15);
				String capitalEnc = StrHandler.lpad(contratAssuranceVo.getCapitalEnc(), '0', 15);
				String tauxPrime = contratAssuranceVo.getTauxPrime();
				String tauxSprime = contratAssuranceVo.getTauxSprime();
				String mntPrimeComm = StrHandler.lpad(contratAssuranceVo.getMntPrimeComm(), '0', 15);
				String mntPrimeBNA = StrHandler.lpad(contratAssuranceVo.getMntPrimeBNA(), '0', 15);
				String mntRetenu = StrHandler.lpad(contratAssuranceVo.getMntRetenu(), '0', 15);
				String reponse = contratAssuranceVo.getReponse();
				String examen = contratAssuranceVo.getExamen();
				String honoraires = StrHandler.lpad(contratAssuranceVo.getHonoraires(), '0', 15);
				String duree = contratAssuranceVo.getDuree();
				String assurance = contratAssuranceVo.getAssurance();
				String codAgence = contratAssuranceVo.getCodAgence();
				String numAdhesion = contratAssuranceVo.getNumAdhesion();
				String quittance = contratAssuranceVo.getQuittance();
				String dateEffet = contratAssuranceVo.getDateEffet();

				String ligne = "";
				ligne = dateJour + codAgence + numAdhesion + quittance + dateEffet + datePremiereEcheance + franchise
						+ assure + adresse + cin + rib + profession + dateNaissance + capital + capitalEnc + tauxPrime
						+ tauxSprime + mntPrimeComm + mntPrimeBNA + mntRetenu + honoraires + duree;

				listeLignesAMI_VIE.add(ligne);

			}
		}

		if (result_STAR.size() > 0) {
			for (int i = 0; i < result_STAR.size(); i++) {
				ContratAssuranceAgricole contratAssuranceVo = result_STAR.get(i);
				String numQuiCaag = StrHandler.lpad(contratAssuranceVo.getNumQuiCaag(), '0', 30);
				String montRetsCaag = StrHandler.lpad(contratAssuranceVo.getMontRetsCaag() == null ? ""
						: contratAssuranceVo.getMontRetsCaag().toString(), '0', 15);
				String montComCaag = StrHandler.lpad(contratAssuranceVo.getMontComCaag() == null ? ""
						: contratAssuranceVo.getMontComCaag().toString(), '0', 15);
				String montTotCaag = StrHandler.lpad(contratAssuranceVo.getMontTotCaag() == null ? ""
						: contratAssuranceVo.getMontTotCaag().toString(), '0', 15);
				String codTpceAG = StrHandler.rpad(contratAssuranceVo.getCodTpceAG(), '0', 20);
				String nomPrnAg = StrHandler.rpad(
						contratAssuranceVo.getNomPrnAg() == null ? "" : contratAssuranceVo.getNomPrnAg().trim(), ' ',
						60);
				String nomNomAg = StrHandler.rpad(
						contratAssuranceVo.getNomNomAg() == null ? "" : contratAssuranceVo.getNomNomAg().trim(), ' ',
						60);
				String datCrtCaag = StrHandler.lpad(DateHandler.dateToStr(contratAssuranceVo.getDatCrtCaag()), ' ', 10);
				String codPackCaag = StrHandler.rpad(contratAssuranceVo.getCodPackCaag(), ' ', 20);
				String montCredCaag = StrHandler.lpad(contratAssuranceVo.getMontCredCaag() == null ? ""
						: contratAssuranceVo.getMontCredCaag().toString(), '0', 15);
				String numPceAg = StrHandler.lpad(contratAssuranceVo.getNumPceAg(), '0', 20);
				String codStrCaag = StrHandler.lpad(
						contratAssuranceVo.getCodStrCaag() == null ? "" : contratAssuranceVo.getCodStrCaag().toString(),
						'0', 19);
				String numDetDetagr = StrHandler.lpad(contratAssuranceVo.getNumDetDetagr(), '0', 2);
				String numDecDecagr = StrHandler.lpad(contratAssuranceVo.getNumDecDecagr(), '0', 11);
				String ligne = "";
				ligne = codStrCaag + ";" + numQuiCaag + ";" + montRetsCaag + ";" + montComCaag + ";" + ";" + montTotCaag
						+ ";" + codTpceAG + ";" + numPceAg + ";" + nomPrnAg + ";" + nomNomAg + ";" + datCrtCaag + ";"
						+ codPackCaag + ";" + montCredCaag + ";" + numDecDecagr + ";" + numDetDetagr;
				listeLignesSTAR.add(ligne);
			}
		}

		if (result_MAG.size() > 0) {
			for (int i = 0; i < result_MAG.size(); i++) {
				ContratAssuranceVo contratAssuranceVo = result_MAG.get(i);
				String dateJour = contratAssuranceVo.getDateJour();
				String datePremiereEcheance = contratAssuranceVo.getDatePremiereEcheance();
				String franchise = contratAssuranceVo.getFranchise();
				String assure = contratAssuranceVo.getAssure();
				String adresse = contratAssuranceVo.getAdresse();
				String cin = contratAssuranceVo.getCin();
				String rib = contratAssuranceVo.getRib();
				String profession = contratAssuranceVo.getProfession();
				String dateNaissance = contratAssuranceVo.getDateNaissance();
				String capital = contratAssuranceVo.getCapital();
				String capitalEnc = contratAssuranceVo.getCapitalEnc();
				String tauxPrime = contratAssuranceVo.getTauxPrime();
				String tauxSprime = contratAssuranceVo.getTauxSprime();
				String mntPrimeComm = contratAssuranceVo.getMntPrimeComm();
				String mntPrimeBNA = contratAssuranceVo.getMntPrimeBNA();
				String mntRetenu = contratAssuranceVo.getMntRetenu();
				String reponse = contratAssuranceVo.getReponse();
				String examen = contratAssuranceVo.getExamen();
				String honoraires = contratAssuranceVo.getHonoraires();
				String duree = contratAssuranceVo.getDuree();
				String tpx = contratAssuranceVo.getTpx();
				String assurance = contratAssuranceVo.getAssurance();
				String codAgence = contratAssuranceVo.getCodAgence();
				String numAdhesion = contratAssuranceVo.getNumAdhesion();
				String quittance = contratAssuranceVo.getQuittance();
				String dateEffet = contratAssuranceVo.getDateEffet();

				String ligne1 = "";
				ligne1 = "029" + dateJour + codAgence + numAdhesion + quittance + dateEffet + datePremiereEcheance
						+ franchise + assure + adresse;

				String ligne2 = "";
				ligne2 = "030" + cin + rib + profession + dateNaissance + capital + capitalEnc + tauxPrime + tauxSprime
						+ mntPrimeComm + mntPrimeBNA + mntRetenu + honoraires + tpx + duree;

				listeLignesMAG.add(ligne1);
				listeLignesMAG.add(ligne2);
			}
		}

		if (result_CTAM.size() > 0) {
			for (int i = 0; i < result_CTAM.size(); i++) {
				ContratAssuranceVo contratAssuranceVo = result_CTAM.get(i);
				String dateJour = contratAssuranceVo.getDateJour();
				String datePremiereEcheance = contratAssuranceVo.getDatePremiereEcheance();
				String franchise = contratAssuranceVo.getFranchise();
				String assure = contratAssuranceVo.getAssure();
				String adresse = contratAssuranceVo.getAdresse();
				String cin = contratAssuranceVo.getCin();
				String rib = contratAssuranceVo.getRib();
				String profession = contratAssuranceVo.getProfession();
				String dateNaissance = contratAssuranceVo.getDateNaissance();
				String capital = contratAssuranceVo.getCapital();
				String capitalEnc = contratAssuranceVo.getCapitalEnc();
				String tauxPrime = contratAssuranceVo.getTauxPrime();
				String tauxSprime = contratAssuranceVo.getTauxSprime();
				String mntPrimeComm = contratAssuranceVo.getMntPrimeComm();
				String mntPrimeBNA = contratAssuranceVo.getMntPrimeBNA();
				String mntRetenu = contratAssuranceVo.getMntRetenu();
				String reponse = contratAssuranceVo.getReponse();
				String examen = contratAssuranceVo.getExamen();
				String honoraires = contratAssuranceVo.getHonoraires();
				String duree = contratAssuranceVo.getDuree();
				String tpx = contratAssuranceVo.getTpx();
				String assurance = contratAssuranceVo.getAssurance();
				String codAgence = contratAssuranceVo.getCodAgence();
				String numAdhesion = contratAssuranceVo.getNumAdhesion();
				String quittance = contratAssuranceVo.getQuittance();
				String dateEffet = contratAssuranceVo.getDateEffet();

				String ligne1 = "";
				ligne1 = "027" + dateJour + codAgence + numAdhesion + quittance + dateEffet + datePremiereEcheance
						+ franchise + assure + adresse;

				String ligne2 = "";
				ligne2 = "028" + cin + rib + profession + dateNaissance + capital + capitalEnc + tauxPrime + tauxSprime
						+ mntPrimeComm + mntPrimeBNA + mntRetenu + honoraires + tpx + duree;

				listeLignesCTAM.add(ligne1);
				listeLignesCTAM.add(ligne2);
			}
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		String dateFichierNom = dateFormat.format(DateHandler.strToDate(contratAss.getDateExtraction()));

		Map<String, File> mapExtraComptableFiles = writeToExtraComptabeFiles(listeLignesAMI_VIE, listeLignesAMI_VOY,
				listeLignesMAG, listeLignesSTAR, listeLignesCTAM, dateFichierNom);
		Map<String, File> mapAFBComptableFiles =
				writeToAFBComptableFiles(dateFichierNom, contratAss.getDateExtraction());
		sendFilesThroughtFTPandEmail(mapExtraComptableFiles, mapAFBComptableFiles);

		LOGGER.info("Exporation done..");
		return compensationVo;

	}

	private void sendFilesThroughtFTPandEmail(Map<String, File> mapExtraComptableFiles,
			Map<String, File> mapAFBComptableFiles) {
		try {
			File fileAFBAMI = mapAFBComptableFiles.get("AFBAMI");
			File fileAFBMAG = mapAFBComptableFiles.get("AFBMAG");
			File fileAFBCTAM = mapAFBComptableFiles.get("AFBCTAM");
			File fileAFBSTAR = mapAFBComptableFiles.get("AFBSTAR");

			File file = mapExtraComptableFiles.get("AMI_VOY");
			File fileAMI = mapExtraComptableFiles.get("AMI_VIE");
			File fileMAG = mapExtraComptableFiles.get("MAG");
			File fileCTAM = mapExtraComptableFiles.get("CTAM");
			File fileSTAR = mapExtraComptableFiles.get("STAR");

			if (fileAFBAMI.length() > 0) {
				// ********* Envoi FTP ******//
				String mail = assuranceVoyageDAO.getAdrMailAssurance(new Long(2002)).getAdrMailAss();
				String tab[] = mail.split(";");
				List<File> liste = new ArrayList<File>();
				boolean etatSendFile = true;// Util.sendFileFTPCICS(fileAFBAMI.getAbsolutePath(),
				// Configuration.getLocalPathSendCICS() + fileAFBAMI.getName());
				if (etatSendFile == true) {
					String filesAMI[][] = { { file.getName(), file.getPath() },
							{ fileAMI.getName(), fileAMI.getPath() }, { fileAFBAMI.getName(), fileAFBAMI.getPath() } };
					liste.add(file);
					liste.add(fileAMI);
					liste.add(fileAFBAMI);
					for (int i = 0; i < tab.length; i++) {

						this.sendExchangeMail(from, tab[i], null, "Fichiers AMI", "Bonjour ; AMI files .", liste);
					}

					// sendExchangeMail(tab, "Fichiers AMI", "", filesAMI);

					LOGGER.info("Fichiers : " + fileAFBAMI.getName() + " , " + file.getName() + " , "
							+ fileAMI.getName() + " envoyé avec succés ");
				} else {
					LOGGER.error("Erreur d'envoie via FTP du fichier : " + fileAFBAMI.getName());

				}
			}

			if (fileAFBMAG.length() > 0) {
				// ********* Envoi FTP ******//
				String mail = assuranceVoyageDAO.getAdrMailAssurance(new Long(2000)).getAdrMailAss();
				String tab[] = mail.split(";");
				boolean etatSendFile = true; // Util.sendFileFTPCICS(fileAFBMAG.getAbsolutePath(),
				// Configuration.getLocalPathSendCICS() + fileAFBMAG.getName());
				List<File> liste = new ArrayList<File>();
				if (etatSendFile == true) {
					String filesMAG[][] = { { fileMAG.getName(), fileMAG.getPath() },
							{ fileAFBMAG.getName(), fileAFBMAG.getPath() } };

					liste.add(fileMAG);
					liste.add(fileAFBMAG);

					for (int i = 0; i < tab.length; i++) {

						this.sendExchangeMail(from, tab[i], null, "Fichiers MAGHEREBIA", "Bonjour ; MAGHEREBIA files .",
								liste);
					}

					// sendExchangeMail(tab, "Fichiers MAGHEREBIA", "MAGHEREBIA files .", filesMAG);

					LOGGER.info(
							"Fichiers : " + fileAFBMAG.getName() + " , " + fileMAG.getName() + " envoyé avec succés ");
				} else {
					LOGGER.error("Erreur d'envoie via FTP du fichier : " + fileAFBMAG.getName());

				}
			}

			if (fileAFBCTAM.length() > 0) {
				// ********* Envoi FTP ******//
				String mail = assuranceVoyageDAO.getAdrMailAssurance(new Long(2001)).getAdrMailAss();
				String tab[] = mail.split(";");
				boolean etatSendFile = true;
				// Util.sendFileFTPCICS(fileAFBCTAM.getAbsolutePath(),
				// Configuration.getLocalPathSendCICS() + fileAFBCTAM.getName());
				List<File> liste = new ArrayList<File>();
				if (etatSendFile == true) {
					String filesCTAM[][] = { { fileCTAM.getName(), fileCTAM.getPath() },
							{ fileAFBCTAM.getName(), fileAFBCTAM.getPath() } };

					liste.add(fileCTAM);
					liste.add(fileAFBCTAM);

					for (int i = 0; i < tab.length; i++) {

						this.sendExchangeMail(from, tab[i], null, "Fichiers CTAMA", "Bonjour ; CTAMA files .", liste);
					}

					// sendExchangeMail(tab, "Fichiers CTAMA", "CTAMA files", filesCTAM);
					LOGGER.info(
							"Fichier : " + fileAFBCTAM.getName() + " , " + fileCTAM.getName() + " envoyé avec succés ");
				} else {
					LOGGER.error("Erreur d'envoie via FTP du fichier : " + fileAFBCTAM.getName());

				}
			}

			if (fileAFBSTAR.length() > 0) {
				// ********* Envoi FTP ******//
				String mail = assuranceVoyageDAO.getAdrMailAssurance(new Long(2003)).getAdrMailAss();
				String tab[] = mail.split(";");
				boolean etatSendFile = true;
				// Util.sendFileFTPCICS(fileAFBSTAR.getAbsolutePath(),
				// Configuration.getLocalPathSendCICS() + fileAFBSTAR.getName());
				List<File> liste = new ArrayList<File>();
				if (etatSendFile == true) {
					String filesStar[][] = { { fileAFBSTAR.getName(), fileAFBSTAR.getPath() },
							{ fileSTAR.getName(), fileSTAR.getPath() } };

					liste.add(fileAFBSTAR);
					liste.add(fileSTAR);

					for (int i = 0; i < tab.length; i++) {

						this.sendExchangeMail(from, tab[i], null, "Fichiers STAR", "Bonjour ; STAR files .", liste);
					}

					// sendExchangeMail(tab, "Fichiers STAR", "STAR files", filesStar);
					LOGGER.info(
							"Fichier : " + fileAFBSTAR.getName() + " , " + fileSTAR.getName() + " envoyé avec succés ");
				} else {
					LOGGER.error("Erreur d'envoie via FTP du fichier : " + fileAFBSTAR.getName());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	private Map<String, File> writeToAFBComptableFiles(String dateFichierNom, String dateFichierComptable)
			throws IOException {

		Map<String, File> mapAFBComptableFiles = new HashMap<String, File>();
		Date dateFicherComptable = DateHandler.strToDate(dateFichierComptable);

		AFBVo AFBVoAMI = new AFBVo();
		AFBVo AFBVoCTAM = new AFBVo();
		AFBVo AFBVoMAG = new AFBVo();
		AFBVo AFBVoSTAR = new AFBVo();

		String fileNameAMI = "D:\\ASSURANCE AMI_" + dateFichierNom + ".txt";
		String fileNameMAG = "D:\\MAGHEREBIA_" + dateFichierNom + ".txt";
		String fileNameCTAM = "D:\\CTAMA_" + dateFichierNom + ".txt";
		String fileNameSTAR = "D:\\STAR_" + dateFichierNom + ".txt";

		try {
			///////////////////////////////////////
			File fileAFBAMI = new File(fileNameAMI);
			if (fileAFBAMI.exists() && fileAFBAMI.length() > 0) {
				fileAFBAMI.delete();
				fileAFBAMI.createNewFile();
			}
			if (!fileAFBAMI.exists())
				fileAFBAMI.createNewFile();

			SocietesAFBView societesAFBViewAMI = new SocietesAFBView();
			societesAFBViewAMI.setNumSoctAFB(Long.valueOf("138"));
			societesAFBViewAMI.setNomSoctAFB("ASSURANCE AMI");

			AFBVoAMI.setSocietesAFBView(societesAFBViewAMI);
			AFBVoAMI.setDateComptable(dateFicherComptable);
			AFBVoAMI.setDateDebut(dateFicherComptable);
			AFBVoAMI.setDateFin(dateFicherComptable);
			AFBVoAMI.setFile(fileAFBAMI);

			///////////////////////////////////////
			File fileAFBMAG = new File(fileNameMAG);
			if (fileAFBMAG.exists() && fileAFBMAG.length() > 0) {
				fileAFBMAG.delete();
				fileAFBMAG.createNewFile();
			}
			if (!fileAFBMAG.exists())
				fileAFBMAG.createNewFile();

			SocietesAFBView societesAFBViewMAG = new SocietesAFBView();
			societesAFBViewMAG.setNumSoctAFB(Long.valueOf("125"));
			societesAFBViewMAG.setNomSoctAFB("MAGHEREBIA");

			AFBVoMAG.setSocietesAFBView(societesAFBViewMAG);
			AFBVoMAG.setDateComptable(dateFicherComptable);
			AFBVoMAG.setDateDebut(dateFicherComptable);
			AFBVoMAG.setDateFin(dateFicherComptable);
			AFBVoMAG.setFile(fileAFBMAG);

			///////////////////////////////////////
			File fileAFBCTAM = new File(fileNameCTAM);
			if (fileAFBCTAM.exists() && fileAFBCTAM.length() > 0) {
				fileAFBCTAM.delete();
				fileAFBCTAM.createNewFile();
			}
			if (!fileAFBCTAM.exists())
				fileAFBCTAM.createNewFile();

			SocietesAFBView societesAFBViewCTAM = new SocietesAFBView();
			societesAFBViewCTAM.setNumSoctAFB(Long.valueOf("126"));
			societesAFBViewCTAM.setNomSoctAFB("CTAMA");

			AFBVoCTAM.setSocietesAFBView(societesAFBViewCTAM);
			AFBVoCTAM.setDateComptable(dateFicherComptable);
			AFBVoCTAM.setDateDebut(dateFicherComptable);
			AFBVoCTAM.setDateFin(dateFicherComptable);
			AFBVoCTAM.setFile(fileAFBCTAM);
			///////////////////////////////////////
			File fileAFBSTAR = new File(fileNameSTAR);
			if (fileAFBSTAR.exists() && fileAFBSTAR.length() > 0) {
				fileAFBSTAR.delete();
				fileAFBSTAR.createNewFile();
			}
			if (!fileAFBSTAR.exists())
				fileAFBSTAR.createNewFile();

			SocietesAFBView societesAFBViewSTAR = new SocietesAFBView();
			societesAFBViewSTAR.setNumSoctAFB(Long.valueOf("107"));
			societesAFBViewSTAR.setNomSoctAFB("STAR");

			AFBVoSTAR.setSocietesAFBView(societesAFBViewSTAR);
			AFBVoSTAR.setDateComptable(dateFicherComptable);
			AFBVoSTAR.setDateDebut(dateFicherComptable);
			AFBVoSTAR.setDateFin(dateFicherComptable);
			AFBVoSTAR.setFile(fileAFBSTAR);

			// ********Batch *************//
			fixerUser();

			// ******** LES TRAITEMENTS DOIVENT REMPLIR LES FICHIER *************//
			try {
				GestionAFBTrt2 gestionAFBTrt = new GestionAFBTrt2();
				AFBVoAMI = (AFBVo) gestionAFBTrt.exec(AFBVoAMI);
				AFBVoMAG = (AFBVo) gestionAFBTrt.exec(AFBVoMAG);
				AFBVoCTAM = (AFBVo) gestionAFBTrt.exec(AFBVoCTAM);
				AFBVoSTAR = (AFBVo) gestionAFBTrt.exec(AFBVoSTAR);

				mapAFBComptableFiles.put("AFBAMI", fileAFBAMI);
				mapAFBComptableFiles.put("AFBMAG", fileAFBMAG);
				mapAFBComptableFiles.put("AFBCTAM", fileAFBCTAM);
				mapAFBComptableFiles.put("AFBSTAR", fileAFBSTAR);

			} catch (RuntimeException e) {
				LOGGER.error(
						"erreur dans le traitement GestionAFBTrt2 , lors de l'ecriture dans les fichiers AFB comptable Assurance :",
						e);
				throw e;
			}
		} catch (IOException e) {
			LOGGER.error("erreur creation de fichier Assurance AFB comptable :", e);
			throw e;
		}

		return mapAFBComptableFiles;
	}

	private Map<String, File> writeToExtraComptabeFiles(List<String> listeLignesAMI_VIE,
			List<String> listeLignesAMI_VOY, List<String> listeLignesMAG, List<String> listeLignesSTAR,
			List<String> listeLignesCTAM, String dateFichier)
			throws IOException {

		Map<String, File> mapFiles = new HashMap<String, File>();

		String tempDirAMI = "D:\\AMI";
		String tempDirMAG = "D:\\MAG";
		String tempDirSTAR = "D:\\STAR";
		String tempDirCTAM = "D:\\CTAM";
		String tempDir = "D:\\AMI";

		String fichierAMI = "MVTAMI_" + dateFichier;
		String fichierCTAM = "CTAMASS" + dateFichier;
		String fichierSTAR = "STARASS" + dateFichier;
		String fichierMAG = "MAGASS" + dateFichier;
		String fichierMNG = "AMIASS" + dateFichier;

		try {
			//////////////////////////////////////////
			File fileAMI = new File(tempDirAMI + File.separatorChar + fichierMNG);
			if (fileAMI.exists() && fileAMI.length() > 0) {
				fileAMI.delete();
			}
			if (!fileAMI.exists())
				fileAMI.createNewFile();
			for (String ligneFichier : listeLignesAMI_VIE) {
				writeToFile(fileAMI, ligneFichier);
			}

			//////////////////////////////////////////
			File fileMAG = new File(tempDirMAG + File.separatorChar + fichierMAG);
			if (fileMAG.exists() && fileMAG.length() > 0) {
				fileMAG.delete();
			}
			if (!fileMAG.exists())
				fileMAG.createNewFile();
			for (String ligneFichier : listeLignesMAG) {
				writeToFile(fileMAG, ligneFichier);
			}

			//////////////////////////////////////////
			File fileSTAR = new File(tempDirSTAR + File.separatorChar + fichierSTAR);
			if (fileSTAR.exists() && fileSTAR.length() > 0) {
				fileSTAR.delete();
			}
			if (!fileSTAR.exists())
				fileSTAR.createNewFile();
			for (String ligneFichier : listeLignesSTAR) {
				writeToFile(fileSTAR, ligneFichier);
			}

			//////////////////////////////////////////
			File fileCTAM = new File(tempDirCTAM + File.separatorChar + fichierCTAM);
			if (fileCTAM.exists() && fileCTAM.length() > 0) {
				fileCTAM.delete();
			}
			if (!fileCTAM.exists())
				fileCTAM.createNewFile();
			for (String ligneFichier : listeLignesCTAM) {
				writeToFile(fileCTAM, ligneFichier);
			}
			//////////////////////////////////////////
			File file = new File(tempDir + File.separatorChar + fichierAMI);
			if (file.exists() && file.length() > 0) {
				file.delete();
			}
			if (!file.exists())
				file.createNewFile();
			for (String ligneFichier : listeLignesAMI_VOY) {
				writeToFile(file, ligneFichier);
			}
			mapFiles.put("AMI_VIE", fileAMI);
			mapFiles.put("MAG", fileMAG);
			mapFiles.put("STAR", fileSTAR);
			mapFiles.put("CTAM", fileCTAM);
			mapFiles.put("AMI_VOY", file);
		} catch (IOException e) {
			LOGGER.error("erreur creation de fichier Assurance extra comptable ", e);
			throw e;
		}
		return mapFiles;
	}

	@Override
	protected void genCroText(ValueObject arg0) {
		// TODO Auto-generated method stub

	}

	public static void writeToFile(File file, String text) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(text);
			bw.newLine();
			bw.close();
		} catch (Exception e) {
		}
	}

	public Properties getEmailProperties() {
		final Properties config = new Properties();
		config.put("mail.smtp.auth", "true");
		// config.put("mail.smtp.starttls.enable", "true");
		config.put("mail.smtp.host", SERVIDOR_SMTP);
		config.put("mail.smtp.port", PORTA_SERVIDOR_SMTP);
		config.put("mail.smtp.sendpartial", "true");
		return config;
	}

	public boolean sendExchangeMail(String from, String to, String[] toCc, String subject, String body,
			List<File> listeAttachements)
			throws MessagingException {
		final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(CONTA_PADRAO, SENHA_CONTA_PADRAO);
			}

		});

		try {

			Message message = new MimeMessage(session);

			List<InternetAddress> addressTo = new ArrayList<InternetAddress>();
			if (to != null && to.length() > 0) {

				// System.out.println("mail : " + to);
				addressTo.add(new InternetAddress(to));

			}
			if (toCc != null && toCc.length > 0) {
				List<InternetAddress> addressCC = new ArrayList<InternetAddress>();
				for (String cc : toCc) {
					addressCC.add(new InternetAddress(cc));
				}
				message.addRecipients(Message.RecipientType.CC, addressCC.toArray(new Address[0]));
			}

			message.addRecipients(Message.RecipientType.TO, addressTo.toArray(new Address[0]));

			message.setFrom(new InternetAddress(from));
			message.setSubject(subject);

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// set message body
			// messageBodyPart.setText(body);
			messageBodyPart.setContent(body, "text/html; charset=utf-8");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			if (listeAttachements != null && listeAttachements.size() != 0) {
				for (File attachements : listeAttachements) {
					if (attachements != null && attachements.isFile()) {

						MimeBodyPart attachPart = new MimeBodyPart();
						try {

							DataSource ds =
									new ByteArrayDataSource(FileUtils.readFileToByteArray(attachements), "text/plain");
							attachPart.setDataHandler(new DataHandler(ds));
							attachPart.setFileName(attachements.getName());
							attachPart.setDisposition(Part.ATTACHMENT);
							multipart.addBodyPart(attachPart);
						} catch (IOException ex) {
							ex.printStackTrace();
						}

					}
				}
			}

			message.setContent(multipart);
			message.setSentDate(new Date());

			logger.info("try to sendOfficeMail service");
			Transport.send(message);
			logger.info(" sendOfficeMail finish");

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();

			return false;
		}
	}

	public void sendExchangeMailOLD(String[] to, String subject, String body, String[][] attachements) {
		try {
			ExchangeService service = new ExchangeService();

			ExchangeCredentials credentials = new WebCredentials("mailassurance", "bna+2019", "bna");
			service.setCredentials(credentials);
			service.setUrl(new URI("https://mail.bna.tn/owa"));
			service.autodiscoverUrl("mail.assurance@bna.tn", new RedirectionUrlCallback());

			EmailMessage msg = new EmailMessage(service);
			msg.setSubject(subject);

			msg.setBody(MessageBody.getMessageBodyFromText(body));

			msg.getToRecipients().add(to[0]);

			for (String[] attachement : attachements)
				msg.getAttachments().addFileAttachment(attachement[0], attachement[1]);

			LOGGER.info("sending to : " + to[0] + " .....");
			msg.sendAndSaveCopy();
			LOGGER.info("mail sended sucessfully to : " + to[0]);

		} catch (Exception e) {
			LOGGER.error("fail to send mail to : " + to[0], e);
		}
	}

	public void fixerUser() {
		ContextCROHandler.setContext(ContextHandler.getContext());

		Personnel user = new Personnel();
		UserManager usermanager = (UserManager) ContextHandler.getContext().getBean("userManager");
		user = usermanager.getUser("9999");

		UsernamePasswordAuthenticationToken auth =
				new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		auth.setDetails(user);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

}
