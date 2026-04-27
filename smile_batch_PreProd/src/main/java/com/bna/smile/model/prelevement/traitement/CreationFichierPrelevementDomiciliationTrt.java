package com.bna.smile.model.prelevement.traitement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.MvtPrelevements;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class CreationFichierPrelevementDomiciliationTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();

	public CreationFichierPrelevementDomiciliationTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		PrelevementVo prelevementVo = (PrelevementVo) vo;
		this.setCroFlag(false);

		Date dateComptable = prelevementVo.getDateComptable();
		String fileName = "";
		String entete = "";
		String detailFichier = "";
		SimpleDateFormat formaterDate = new SimpleDateFormat("yyyyMMdd");
		File file = prelevementVo.getFile();
		MvtPrelevements mvtPrelevements = new MvtPrelevements();
		mvtPrelevements = prelevementVo.getMvtPrelevements();

		// ********************************//

		try {
			// * Données strcuture Fichier *//
			fileName = file.getName();
			if (prelevementVo.getCodeValeur().longValue() == Constants.COD_ENREGISTREMENT_PRELEVEMENT.longValue()) {

				/* globals */

				String sens = "1";
				String codeValeur = prelevementVo.getCodeValeur() + "";
				String natureRemettant = "1";
				String codeRemettant = "03";
				// String codeCentreReg = lpadS(prelevementVo.getParamAgence().getCodStrcStrc() + "", "0", 3);
				String codeCentreReg = "   ";
				String dateOperation = formaterDate.format(dateComptable);
				String numeroLot = "0002";
				String codeEnregistrementGlobal = "12"; // 11: Presentation ; 12: Rejet
				String codeDevise = Constants.COD_DEV_DINAR.toString();
				String montantGlobal = lpadS(prelevementVo.getMntGlobalFichier() + "", "0", 15);
				String nbreTotal = lpadS(prelevementVo.getNbrGlobalFichier() + "", "0", 10);
				String reference = prelevementVo.getReferenceInterSiege();
				String zoneLibreGlobal = rpadS("", "0", 53);

				/* ***** Creation entête Fichier ****** */
				entete =
						sens + codeValeur + natureRemettant + codeRemettant + codeCentreReg + dateOperation + numeroLot
								+ codeEnregistrementGlobal + codeDevise + montantGlobal + nbreTotal + reference
								+ zoneLibreGlobal;

				logger.info(" entete Prel: " + entete.length());
				logger.info(" entete Prel: " + entete);

				/* details */
				if (mvtPrelevements != null && mvtPrelevements.getNumSeqMprl() != null) {
					String codeEnregistrementDetail = "22"; // 21: Presentation ; 22: Rejet
					String mntDetail = lpadS(mvtPrelevements.getMntPrlPrl() + "", "0", 15);
					String numDetail = lpadS(mvtPrelevements.getNumPrlPrl() + "", "0", 7);
					String ribPayeur = "";
					String ribCreancier = "";
					String emetteur = "";
					String codeInstDest = "";
					String codeAgeDest = "   ";
					String numRefDom = "";
					String descriptionPrel = "";
					String datCompInit = "";
					String motifRejet = "";
					String datEchPrel = "";
					String zoneLibre = "";

					if (mvtPrelevements.getRibBenPrl().length() == 20) {
						codeInstDest = mvtPrelevements.getRibBenPrl().substring(0, 2);
						// codeAgeDest = detailsPrelevements.getDetailsPrelevementsId().getRibBenPrl().substring(2, 5);
					}
					ribCreancier = lpadS(mvtPrelevements.getRibBenPrl(), "0", 20);
					ribPayeur = lpadS(mvtPrelevements.getRibTirPrl(), "0", 20);
					emetteur = lpadS(mvtPrelevements.getEmetteur().getCodEmtrEmtr() + "", "0", 6);
					numRefDom = lpadS(mvtPrelevements.getNumRefDom() + "", "0", 20);
					if (mvtPrelevements.getLibPrlPrl() != null) {
						descriptionPrel = rpadS(mvtPrelevements.getLibPrlPrl() + "", " ", 50);
					} else {
						descriptionPrel = rpadS("", " ", 50);
					}

					// descriptionPrel = UtilCtr.corrigerChaineCaractere(descriptionPrel);

					datCompInit = formaterDate.format(mvtPrelevements.getDatOpePrl());
					motifRejet = lpadS(mvtPrelevements.getMotifRejetPrelev().getCodMotrMrpr() + "", "0", 8);
					datEchPrel = formaterDate.format(mvtPrelevements.getDatEchPrl());
					zoneLibre = rpadS("", " ", 7);

					/* ***** Creation Detail Fichier ****** */

					detailFichier =
							sens + codeValeur + natureRemettant + codeRemettant + codeCentreReg + dateOperation
									+ numeroLot + codeEnregistrementDetail + codeDevise + mntDetail + numDetail
									+ ribPayeur + codeInstDest + codeAgeDest + ribCreancier + emetteur + numRefDom
									+ descriptionPrel + datCompInit + motifRejet + datEchPrel + zoneLibre;

					logger.info(" detailFichier Prel: " + detailFichier.length());
					logger.info(" detailFichier Prel: " + detailFichier);

				}

			}

			// ******** Find ligne in file || Ecriture sinon **************//
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = "";
			boolean existeGlobal = false;
			boolean existeDetail = false;

			if (entete.length() >= 100) {

				while ((strLine = br.readLine()) != null) {

					if (strLine.equals(entete)) {
						existeGlobal = true;
					}
				}
				if (existeGlobal == false) {
					writeToFile(file, entete);
				}

			}

			if (detailFichier.length() == 200) {

				while ((strLine = br.readLine()) != null) {

					if (strLine.equals(detailFichier)) {
						existeDetail = true;
					}
				}
				if (existeDetail == false) {
					writeToFile(file, detailFichier);
				}

			}

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationFichierPrelevementDomiciliationTrt: ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationFichierPrelevementDomiciliationTrt");
			prelevementVo.addError(erreur);
			logger.error("Erreur au niveau CreationFichierPrelevementDomiciliationTrt : ", e);
			prelevementVo.setMsgEnregistrement(e.getMessage());
			throw new RuntimeException();

		}
		return (prelevementVo);
	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {

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

	public static String lpadS(String valueToPad, String filler, int size) {
		StringBuilder builder = new StringBuilder();

		while (builder.length() + valueToPad.length() < size) {
			builder.append(filler);
		}
		builder.append(valueToPad);
		return builder.toString();
	}

	public static String rpadS(String valueToPad, String filler, int size) {
		StringBuilder builder = new StringBuilder();
		builder.append(valueToPad);

		while ((builder.length() + filler.length()) <= size) {
			builder.append(filler);
		}

		return builder.toString();
	}

	public static String lpad(String valueToPad, char filler, int size) {
		char[] array = new char[size];

		int len = size - valueToPad.length();

		for (int i = 0; i < len; i++)
			array[i] = filler;

		valueToPad.getChars(0, valueToPad.length(), array, size - valueToPad.length());

		return String.valueOf(array);
	}

	public static String rpad(String valueToPad, char filler, int size) {

		char[] array = new char[size];
		valueToPad.getChars(0, valueToPad.length(), array, 0);

		int len = size - valueToPad.length();

		for (int i = len; i < size; i++)
			array[i] = filler;

		return String.valueOf(array);
	}
}