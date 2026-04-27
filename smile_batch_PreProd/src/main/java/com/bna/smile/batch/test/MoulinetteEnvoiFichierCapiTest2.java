package com.bna.smile.batch.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.apache.log4j.Logger;

import com.bna.commun.model.AdhesionAssCapi;
import com.bna.commun.model.AssureCapi;
import com.bna.commun.model.BeneficiaireCapi;
import com.bna.commun.model.SouscripteurCapi;
import com.bna.commun.model.TraceAssuranceCapi;
import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.banqueAssurance.dao.AssuranceSereniteDAO;
import com.bna.smile.model.banqueAssurance.service.RedirectionUrlCallback;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.WebCredentials;

public class MoulinetteEnvoiFichierCapiTest2 extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteEnvoiFichierCapiTest2.class);
	private BatchEnvoiFichierCapiFrame mainFrame;
	private VirementVo virementVo;
	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	AssuranceSereniteDAO assuranceSereniteDAO = (AssuranceSereniteDAO) context.getBean("assuranceAmiSereniteDAO");

	private static final String SERVIDOR_SMTP = "bnatn.mail.protection.outlook.com";
	private static final int PORTA_SERVIDOR_SMTP = 25;
	private static final String CONTA_PADRAO = "";
	private static final String SENHA_CONTA_PADRAO = "";

	private final String from = "mail.assurance@bna.tn";
	// ************************//

	/**
	 * Consutructor
	 */
	public MoulinetteEnvoiFichierCapiTest2() {
		super();
	}

	public MoulinetteEnvoiFichierCapiTest2(VirementVo virementVo) {
		super();
		this.virementVo = virementVo;
	}

	public MoulinetteEnvoiFichierCapiTest2(VirementVo virementVo, BatchEnvoiFichierCapiFrame mainFrame) {
		super();
		this.virementVo = virementVo;
		this.mainFrame = mainFrame;
	}

	/**
	 * point d'entrée de la moulinette
	 */
	public void perform() {

		IValueObject vo = new ValueObject();
		try {

			SimpleDateFormat formatDateFile = new SimpleDateFormat("ddMMyyyy");
			SimpleDateFormat formatDateFileAFB = new SimpleDateFormat("dd/MM/yyyy");

			String jjmmyyyySys = formatDateFileAFB.format(DateHandler.strToDate("15/03/2024"));

			List<String> listeLignesAMI_Serenite = new ArrayList<String>();

			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();
			criteria.add(expression.ge("dateSystAdh", DateHandler.strToDate(jjmmyyyySys)));
			criteria.add(expression.lt("dateSystAdh", DateHandler.addJour(DateHandler.strToDate(jjmmyyyySys), +1)));
			criteria.add(expression.eq("codEtatAdh", "V"));
			List<AdhesionAssCapi> result_AMI_VOY = searchEngine.find(AdhesionAssCapi.class, criteria);
			logger.info("resultAMI_VOY " + result_AMI_VOY.size());

			Collections.sort(result_AMI_VOY, new Comparator<AdhesionAssCapi>() {

				public int compare(AdhesionAssCapi arg0, AdhesionAssCapi arg1) {
					return arg0.getDateSystAdh().compareTo(arg1.getDateSystAdh());
				}
			});

			if (result_AMI_VOY.size() > 0) {
				for (int i = 0; i < result_AMI_VOY.size(); i++) {
					AdhesionAssCapi adhesionAssCapi = result_AMI_VOY.get(i);
					SouscripteurCapi souscripteurCapi = new SouscripteurCapi();
					AssureCapi assureCapi = new AssureCapi();
					System.out.println(adhesionAssCapi.getAssuresCapi());

					for (Iterator it = adhesionAssCapi.getSouscripteursCapi().iterator(); it.hasNext();) {
						souscripteurCapi = (SouscripteurCapi) it.next();
					}
					for (Iterator it = adhesionAssCapi.getAssuresCapi().iterator(); it.hasNext();) {
						assureCapi = (AssureCapi) it.next();
					}

					for (TraceAssuranceCapi traceAssuranceCapi : adhesionAssCapi.getTraceAssuranceCapi()) {

						if (traceAssuranceCapi.getDateOperTrc().compareTo(DateHandler.strToDate(jjmmyyyySys)) > 0
								|| traceAssuranceCapi.getDateOperTrc()
										.compareTo(DateHandler.strToDate(jjmmyyyySys)) == 0) {
							String codeOperation = "";
							if (traceAssuranceCapi.getTache().getOperation().getCodOperOper()
									.equals(Long.valueOf("3660")))
								codeOperation = "S";
							if (traceAssuranceCapi.getTache().getOperation().getCodOperOper()
									.equals(Long.valueOf("3669")))
								codeOperation = StrHandler.rpad("V", ' ', 5);
							String dateAdh = formatDateFile.format(traceAssuranceCapi.getDateOperTrc());
							String annee;
							annee = dateAdh.substring(4, 8);
							String mntTaxeFga;
							long taxe = (long) assuranceSereniteDAO.getValeurFga(Long.valueOf(annee)).floatValue();
							String numAdh = "" + adhesionAssCapi.getNumSeqAdh();
							if (codeOperation.contains("S")) {
								String dateDebut = formatDateFile.format(adhesionAssCapi.getDateDebAdh());
								String dateFin = formatDateFile.format(adhesionAssCapi.getDateFinAdh());
								String cinSous = souscripteurCapi.getCinSouscripteur();
								String lieuDelivSous =
										StrHandler.rpad(souscripteurCapi.getLieuDelivSouscripteur(), ' ', 5);
								String dateDelivSous =
										formatDateFile.format(souscripteurCapi.getDateDelivSouscripteur());
								String nomPrnSous = StrHandler.rpad(souscripteurCapi.getNomSouscripteur().trim() + " "
										+ souscripteurCapi.getPrenomSouscripteur().trim(), ' ', 60);
								String dateSous = formatDateFile.format(souscripteurCapi.getDateNaisSouscripteur());
								String lieuNaisSous =
										StrHandler.rpad(souscripteurCapi.getLieuNaisSouscripteur(), ' ', 20);
								String adresseSous =
										StrHandler.rpad(souscripteurCapi.getAdresseSouscripteur(), ' ', 100);
								String profSous =
										StrHandler.rpad(souscripteurCapi.getProfessionSouscripteur().trim(), ' ', 100);
								String genreSous = StrHandler.rpad(souscripteurCapi.getGenreSouscripteur(), ' ', 1);
								String sitFamSous =
										StrHandler.rpad(souscripteurCapi.getCodSitFamSouscripteur(), ' ', 1);
								String typeSouscription = StrHandler.rpad(adhesionAssCapi.getTypeVersement(), ' ', 1);
								String periodicite = "";
								String datePrelevement = "";
								if (adhesionAssCapi.getPeriodicitePrelevement() != null)
									periodicite = adhesionAssCapi.getPeriodicitePrelevement();
								if (adhesionAssCapi.getDatePrelAdh() != null)
									datePrelevement = formatDateFile.format(adhesionAssCapi.getDatePrelAdh());
								String ribSous = StrHandler.rpad(souscripteurCapi.getRibSouscripteur(), ' ', 20);
								String mntVersement = StrHandler.lpad("", '0', 15);
								if (adhesionAssCapi.getTypeVersement().equals("R"))
									mntVersement =
											StrHandler.lpad(adhesionAssCapi.getMontPrmgTass().toString(), '0', 15);
								String duree = StrHandler.lpad(adhesionAssCapi.getDuree().toString(), '0', 2);
								String datePremierVers =
										formatDateFile.format(adhesionAssCapi.getDatePremierVersment());
								String mntPremierVersement =
										StrHandler.lpad(adhesionAssCapi.getMontPremierVersement().toString(), '0', 15);
								mntTaxeFga = StrHandler.lpad(Long.valueOf(taxe * 1000).toString(), '0', 15);
								String tauxIndexation =
										StrHandler.lpad(adhesionAssCapi.getTauxIndexationAdh().toString(), '0', 3);
								System.out.println(assureCapi.getNumSeqAssure());
								String cinAssure = StrHandler.rpad(assureCapi.getCinAssure(), ' ', 8);
								String lieuDelivAssure = StrHandler.rpad(assureCapi.getLieuDelivAssure(), ' ', 5);
								String dateDelivAssure = formatDateFile.format(assureCapi.getDateDelivAssure());
								String nomPrnAssure = StrHandler.rpad(
										assureCapi.getNomAssure().trim() + " " + assureCapi.getPrenomAssure().trim(),
										' ', 60);
								String dateAssure = formatDateFile.format(assureCapi.getDateNaisAssure());
								String lieuNaisAssure = StrHandler.rpad(assureCapi.getLieuNaisAssure(), ' ', 20);
								String adresseAssure = StrHandler.rpad(assureCapi.getAdresseAssure().trim(), ' ', 100);
								String profAssure = StrHandler.rpad(assureCapi.getProfessionAssure(), ' ', 100);
								String genreAssure = StrHandler.rpad(assureCapi.getGenreAssure(), ' ', 1);
								String sitFamAssure = StrHandler.rpad(assureCapi.getCodSitFamAssure(), ' ', 1);
								String ribAssure = StrHandler.rpad(assureCapi.getRibAssure(), ' ', 20);
								int b = 0;
								String ligneSous = "";
								String ligneAssur = "";

								if (traceAssuranceCapi.getTache().getOperation().getCodOperOper()
										.equals(Long.valueOf("3660"))
										&& traceAssuranceCapi.getTache().getTacheId().getCodTachTach()
												.equals(Long.valueOf(2))) {
									ligneSous = StrHandler.rpad(StrHandler.rpad(codeOperation, ' ', 5) + dateAdh
											+ numAdh + dateDebut + dateFin + cinSous + lieuDelivSous + dateDelivSous
											+ nomPrnSous + dateSous + lieuNaisSous + adresseSous + profSous + genreSous
											+ sitFamSous + typeSouscription + StrHandler.rpad(periodicite, ' ', 1)
											+ StrHandler.rpad(datePrelevement, ' ', 8) + ribSous + mntVersement + duree
											+ datePremierVers + mntPremierVersement + mntTaxeFga + tauxIndexation, ' ',
											524);
									listeLignesAMI_Serenite.add(ligneSous);

									ligneAssur = StrHandler.rpad(codeOperation + StrHandler.rpad("A", ' ', 4) + dateAdh
											+ numAdh + dateDebut + dateFin + cinAssure + lieuDelivAssure
											+ dateDelivAssure + nomPrnAssure + dateAssure + lieuNaisAssure
											+ adresseAssure + profAssure + genreAssure + sitFamAssure + typeSouscription
											+ StrHandler.rpad(periodicite, ' ', 1)
											+ StrHandler.rpad(datePrelevement, ' ', 8) + ribAssure + mntVersement
											+ duree + datePremierVers + mntPremierVersement + mntTaxeFga
											+ tauxIndexation, ' ', 524);

									listeLignesAMI_Serenite.add(ligneAssur);

									for (Iterator it = adhesionAssCapi.getBeneficiairesCapi().iterator(); it
											.hasNext();) {
										BeneficiaireCapi beneficiaireCapi = new BeneficiaireCapi();
										b++;
										beneficiaireCapi = (BeneficiaireCapi) it.next();

										String designationBenef = "B" + StrHandler.lpad(String.valueOf(b), '0', 2);
										String typeBenef = StrHandler.rpad(beneficiaireCapi.getTypeBenef(), ' ', 1);
										String lienBenef = "";
										if (beneficiaireCapi.getQualiteBenef().equals("11"))
											lienBenef = StrHandler.rpad(beneficiaireCapi.getAutreQlteBenef(), ' ', 80);
										else
											lienBenef = StrHandler.rpad(assuranceSereniteDAO.getLienParenteBenefeCapi(
													beneficiaireCapi.getQualiteBenef(),
													beneficiaireCapi.getNumSeqBenef()), ' ', 80);
										String numCinBenef = StrHandler.rpad("", ' ', 8);
										;
										if (beneficiaireCapi.getCinBenef() != null)
											numCinBenef = StrHandler.rpad(beneficiaireCapi.getCinBenef(), ' ', 8);
										String lieuDelivBenef = StrHandler.rpad("", ' ', 5);
										if (beneficiaireCapi.getLieuDelivBenef() != null)
											lieuDelivBenef =
													StrHandler.rpad(beneficiaireCapi.getLieuDelivBenef(), ' ', 5);
										String dateDelivBenef = StrHandler.rpad("", ' ', 8);
										;
										if (beneficiaireCapi.getDateDelivBenef() != null)
											dateDelivBenef =
													formatDateFile.format(beneficiaireCapi.getDateDelivBenef());
										String nomPrnBenef = StrHandler.rpad(beneficiaireCapi.getNomBenef().trim() + " "
												+ beneficiaireCapi.getPrenomBenef().trim(), ' ', 60);
										String dateNaisBenef =
												formatDateFile.format(beneficiaireCapi.getDateNaisBenef());
										String lieuNaisBenef =
												StrHandler.rpad(beneficiaireCapi.getLieuNaisBenef(), ' ', 20);
										String adresseBenef =
												StrHandler.rpad(beneficiaireCapi.getAdresseBenef(), ' ', 100);
										String profBenef = StrHandler.rpad("", ' ', 100);
										if (beneficiaireCapi.getProfessionBenef() != null)
											profBenef = StrHandler.rpad(beneficiaireCapi.getProfessionBenef().trim(),
													' ', 100);
										String genreBenef = StrHandler.rpad(beneficiaireCapi.getGenreBenef(), ' ', 1);
										String sitFamBenef =
												StrHandler.rpad(beneficiaireCapi.getCodSitFamBenef(), ' ', 1);
										String ribBenef = StrHandler.rpad("", ' ', 20);
										if (beneficiaireCapi.getRibBenef() != null)
											ribBenef = StrHandler.rpad(beneficiaireCapi.getRibBenef(), ' ', 20);
										String ligneBenef = "";
										if (beneficiaireCapi.getQualiteBenef().equals("11"))
											ligneBenef = StrHandler.rpad(codeOperation + designationBenef + typeBenef
													+ dateAdh + numAdh + dateDebut + dateFin + numCinBenef
													+ lieuDelivBenef + dateDelivBenef + nomPrnBenef + dateNaisBenef
													+ lieuNaisBenef + adresseBenef + profBenef + genreBenef
													+ sitFamBenef + typeSouscription
													+ StrHandler.rpad(periodicite, ' ', 1)
													+ StrHandler.rpad(datePrelevement, ' ', 8) + ribBenef + mntVersement
													+ duree + datePremierVers + mntPremierVersement + mntTaxeFga
													+ tauxIndexation + lienBenef, ' ', 524);
										else
											ligneBenef = StrHandler.rpad(codeOperation + designationBenef + typeBenef
													+ dateAdh + numAdh + dateDebut + dateFin + numCinBenef
													+ StrHandler.rpad("", ' ', 303) + typeSouscription
													+ StrHandler.rpad(periodicite, ' ', 1)
													+ StrHandler.rpad(datePrelevement, ' ', 8) + ribBenef + mntVersement
													+ duree + datePremierVers + mntPremierVersement + mntTaxeFga
													+ tauxIndexation + lienBenef, ' ', 524);
										listeLignesAMI_Serenite.add(ligneBenef);
									}
								}
							}
							if (traceAssuranceCapi.getTache().getOperation().getCodOperOper()
									.equals(Long.valueOf("3669"))) {
								String numCrt = adhesionAssCapi.getContratCpt().getContratCptId().getCompteClient()
										.replace(" ", "");
								String numeroRemise = StrHandler.lpad(traceAssuranceCapi.getNumSeqGvir(), '0', 15);
								String mntVirement =
										StrHandler.lpad(traceAssuranceCapi.getMontPrmgTass().toString(), '0', 15);
								String typePouvoir = traceAssuranceCapi.getTypePouvGvir();
								String ligneVirement = "";
								ligneVirement = StrHandler.rpad(codeOperation + dateAdh + numAdh + numCrt + numeroRemise
										+ mntVirement + typePouvoir + "03045175011500408965", ' ', 524);
								;
								listeLignesAMI_Serenite.add(ligneVirement);
							}

						}
					}
				}
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
			String dateFichierNom = dateFormat.format(DateHandler.strToDate("15/03/2024"));

			Map<String, File> mapExtraComptableFiles =
					writeToExtraComptabeFiles(listeLignesAMI_Serenite, dateFichierNom);
			sendFilesThroughtFTPandEmail(mapExtraComptableFiles);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// sendFilesThroughtFTPandEmail(mapExtraComptableFiles);

		logger.info("Exporation done..");

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

	public void setMainFrame(BatchEnvoiFichierCapiFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public BatchEnvoiFichierCapiFrame getMainFrame() {
		return mainFrame;
	}

	public void setVirementVo(VirementVo virementVo) {
		this.virementVo = virementVo;
	}

	public VirementVo getVirementVo() {
		return virementVo;
	}

	private void sendFilesThroughtFTPandEmail(Map<String, File> mapExtraComptableFiles) {
		try {
			File fileAMI = mapExtraComptableFiles.get("AMI_SERENITE");

			if (fileAMI.length() > 0) {
				// ********* Envoi FTP ******//
				String mail = assuranceSereniteDAO.getAdrMailAssurance(new Long(2006)).getAdrMailAss();
				String tab[] = mail.split(";");
				List<File> liste = new ArrayList<File>();
				boolean etatSendFile = Util.sendFileFTPCICS(fileAMI.getAbsolutePath(),
						Configuration.getLocalPathSendCICS() + fileAMI.getName());
				if (etatSendFile == true) {
					String filesAMI[] = { fileAMI.getName(), fileAMI.getPath() };

					liste.add(fileAMI);

					for (int i = 0; i < tab.length; i++) {

						this.sendExchangeMail(from, tab[i], null, "Fichiers CAPI", "Bonjour ; CAPI files .", liste);
					}

					// sendExchangeMail(tab, "Fichiers CAPI", "CAPI files", filesAMI);
					logger.info("Fichier : " + fileAMI.getName() + " , " + fileAMI.getName() + " envoyé avec succés ");
				} else {
					logger.error("Erreur d'envoie via FTP du fichier : " + fileAMI.getName());
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	private Map<String, File> writeToExtraComptabeFiles(List<String> listeLignesAMI_Serenite, String dateFichier)
			throws IOException {

		Map<String, File> mapFiles = new HashMap<String, File>();

		String tempDir = "D:\\AMI";

		String fichierAMI = "CAPI_" + dateFichier;

		try {

			//////////////////////////////////////////
			File file = new File(tempDir + File.separatorChar + fichierAMI);
			if (file.exists() && file.length() > 0) {
				file.delete();
			}
			if (!file.exists())
				file.createNewFile();
			for (String ligneFichier : listeLignesAMI_Serenite) {
				writeToFile(file, ligneFichier);
			}
			mapFiles.put("AMI_SERENITE", file);
		} catch (IOException e) {
			logger.error("erreur creation de fichier Assurance extra comptable ", e);
			throw e;
		}
		return mapFiles;
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

	public void sendExchangeMailOLD(String[] to, String subject, String body, String[] attachements) {
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

			for (String attachement : attachements)
				msg.getAttachments().addFileAttachment(attachement);

			logger.info("sending to : " + to[0] + " .....");
			msg.sendAndSaveCopy();
			logger.info("mail sended sucessfully to : " + to[0]);

		} catch (Exception e) {
			logger.error("fail to send mail to : " + to[0], e);
		}
	}

}
