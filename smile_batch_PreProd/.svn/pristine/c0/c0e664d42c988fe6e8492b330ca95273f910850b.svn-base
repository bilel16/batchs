package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.hibernate.Hibernate;

import com.bna.commun.model.ComplementEffetRecu;
import com.bna.commun.model.EffetId;
import com.bna.commun.model.EffetRecuTmp;
import com.bna.commun.model.EffetRecuTmpEsc;
import com.bna.commun.model.SuiviFileTeleCompensation;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.batch.test.BatchFrame;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * @author BNA
 * 
 */
public class MoulinetteInsertingEffetTrt extends Traitement {

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	ISearchEngine search = (SearchEngine) context.getBean("searchEngine");
	ICriteria criteria = search.createCriteria();
	IExpression expression = search.createExpression();
	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
	File imgCheques[] = null;
	CompensationEffetVo compensationVo;
	String ageDes = "";
	String banDes = "";
	Long mntTotal = 0L;
	Long nbrTotal = 0L;
	Long mntTotalInter = 0L;
	Long nbrTotalInter = 0L;
	Long mntTotalIntra = 0L;
	Long nbrTotalIntra = 0L;
	private BatchFrame mainFrame;
	Long mntGlobaleFichier = 0L;
	Long nbrGlobalFichier = 0L;

	public BatchFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(BatchFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	private EffetRecuTmpEsc createEsc(EffetRecuTmp tmp) {
		EffetRecuTmpEsc tmpEsc = new EffetRecuTmpEsc();
		tmpEsc.setEffetId(tmp.getEffetId());

		tmpEsc.setCodAge(tmp.getCodAge());
		tmpEsc.setCodBan(tmp.getCodBan());

		tmpEsc.setCodBanDes(tmp.getCodBanDes());
		tmpEsc.setCodAgeDes(tmp.getCodAgeDes());

		tmpEsc.setNumLot(tmp.getNumLot());
		tmpEsc.setCodEnr(tmp.getCodEnr());
		tmpEsc.setCodDev(tmp.getCodDev());
		tmpEsc.setMntEff(tmp.getMntEff());
		tmpEsc.setMntInt(tmp.getMntInt());
		// mnt Prot
		tmpEsc.setCodVal(tmp.getCodVal());

		tmpEsc.setMntFra(tmp.getMntFra());

		tmpEsc.setRibTir(tmp.getRibTir());
		tmpEsc.setRibTirIni(tmp.getRibTirIni());

		tmpEsc.setRibBen(tmp.getRibBen());
		tmpEsc.setNomBen(tmp.getNomBen());
		tmpEsc.setNomTir(tmp.getNomTir());
		tmpEsc.setDatEch(tmp.getDatEch());
		tmpEsc.setDatCre(tmp.getDatCre());

		tmpEsc.setCodSit(0L);
		tmpEsc.setCodNatEta(0L);
		tmpEsc.setRngDet(0L);

		tmpEsc.setMotRej(tmp.getMotRej());
		tmpEsc.setRefComTir(tmp.getRefComTir());

		tmpEsc.setCodRej1(tmp.getCodRej1());
		tmpEsc.setCodRej2(tmp.getCodRej2());
		tmpEsc.setCodRej3(tmp.getCodRej3());
		tmpEsc.setCodRej4(tmp.getCodRej4());

		tmpEsc.setRefFic(tmp.getRefFic());

		return tmpEsc;
	}

	@Override
	public IValueObject perform(IValueObject vo) {

		compensationVo = (CompensationEffetVo) vo;

		SimpleDateFormat formatDatePath = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatDateFile = new SimpleDateFormat("ddMMyyyy");

		String ageBct = StrHandler.lpad(compensationVo.getStructure().getCodBctStrc(), '0', 3);
		ageDes = ageBct;
		banDes = "03";
		String yyyymmjj = formatDatePath.format(compensationVo.getDateComptable());
		String jjmmyyyy = formatDateFile.format(compensationVo.getDateComptable());
		String jjmmyyyySys = formatDateFile.format(new Date());
		logger.info(yyyymmjj);
		logger.info(jjmmyyyy);
		logger.info(jjmmyyyySys);

		String pathAg = "AGENCE" + ageBct + File.separatorChar + jjmmyyyySys + File.separatorChar + "travail";
		String rootPath =
				File.separatorChar + Configuration.getParentPath() + File.separatorChar
						+ Configuration.getLocalPathCheque() + File.separatorChar + "reçu" + File.separatorChar
						+ "effet";
		String succesPath =
				rootPath + File.separatorChar + "AGENCE" + ageBct + File.separatorChar + jjmmyyyySys
						+ File.separatorChar + "traite";

		logger.info(rootPath + File.separatorChar + pathAg);
		File folder = new File(rootPath + File.separatorChar + pathAg);
		File[] listOfFiles = folder.listFiles();
		logger.info(listOfFiles.length);
		//System.out.println(jjmmyyyy);
		String fileName = "";
		try {
			/************************************************************************ [LCN] [41-21 et 41-22] *************************************************************************/
			for (int i = 0; i < listOfFiles.length; i++) {

				fileName = listOfFiles[i].getName();

				if (listOfFiles[i].isFile() && !fileName.startsWith("ERR-")) {
					logger.info("File Name:" + fileName);
					if (fileName.contains("-41-")) {
						if (!fileName.contains("-41-25-") && !fileName.contains("-CAG-") && fileName.contains(jjmmyyyy)
								&& fileName.endsWith(".RCP"))
							if (!SuivFileTrt.isTreated(fileName))
								importFromFile(listOfFiles[i].getAbsolutePath(), fileName, rootPath
										+ File.separatorChar + pathAg, yyyymmjj, succesPath);

						/************************************************************************ [LCN] [41-25] *************************************************************************/

						if (fileName.contains("-41-25-") && !fileName.contains("-CAG-") && fileName.contains(jjmmyyyy)
								&& fileName.endsWith(".RCP"))
							if (!SuivFileTrt.isTreated(fileName))
								importFromFileSort(listOfFiles[i].getAbsolutePath(), fileName, rootPath
										+ File.separatorChar + pathAg, yyyymmjj, succesPath);
					}
				}

				/************************************************************************ [LCR] [40-42- Bon_de_caisse] ***********************************************************/

				/**
				 * Valeur_40 21,22
				 */

				if (listOfFiles[i].isFile() && !listOfFiles[i].getName().startsWith("ERR-")) {
					fileName = listOfFiles[i].getName();
					logger.info("File Name:" + fileName);

					if (fileName.contains("-40-") || fileName.contains("-42-")) {
						if (!fileName.contains("-40-25-") && fileName.contains(jjmmyyyy) && fileName.endsWith(".RCP")
								&& !fileName.contains("-CAG-"))
							if (!SuivFileTrt.isTreated(fileName))
								importFromFile(listOfFiles[i].getAbsolutePath(), fileName, rootPath
										+ File.separatorChar + pathAg, yyyymmjj, succesPath);

						/**
						 * Valeur_4 25
						 */
						if (fileName.contains("-40-25-") && fileName.contains(jjmmyyyy) && fileName.endsWith(".RCP")
								&& !fileName.contains("-CAG-"))
							if (!SuivFileTrt.isTreated(fileName))
								importFromFileSort(listOfFiles[i].getAbsolutePath(), fileName, rootPath
										+ File.separatorChar + pathAg, yyyymmjj, succesPath);
					}

				}
			}

			/**
			 * Bon_De_Caisse
			 */

			// TODO : add treatment for Bon_De_Caisse
		} catch (Exception ex) {
			logger.info(Arrays.toString(ex.getStackTrace()));
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("");
			text.append(ex.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey(fileName);
			compensationVo.addError(erreur);
			logger.info("Importation done.. avec erreur");

			throw new RuntimeException(ex);
		}
		logger.info("Importation done..");

		return compensationVo;

	}

	public void ajouterFichierAvecMontant(String nomFichier, String codeStructure, Date datePECFichier,
			int codeTraitFichier, Long codValVal, long mntTotal, long nbreTotal, Long mntTotalInter,
			long nbreTotalInter, long nbreTotalIntra, Long mntTotalIntra, Long mntGlobTotFichier, Long nbrGlobTotFichier) {

		SuiviFileTeleCompensation suiviFileTeleCompensation = new SuiviFileTeleCompensation();
		suiviFileTeleCompensation = (SuiviFileTeleCompensation) search.get(SuiviFileTeleCompensation.class, nomFichier);

		if (suiviFileTeleCompensation == null) {
			// jt.update(
			// "INSERT INTO SUIVI_FILE_TELECOMPENSATION (NOM_ORIG_SFILE,COD_STRC_STRC ,DAT_OPER_SFILE,COD_TRAI_SFILE,COD_VAL_VAL,MNT_TOT_SFILE,NBR_TOT_SFILE,MNT_TOT_INTER,NBR_TOT_INTER,MNT_TOT_INTRA,NBR_TOT_INTRA)"
			// + "  VALUES(?,?,?,?,?,?,?,?,?,?,?)", new Object[]{ nomFichier,
			// codeStructure,
			// datePECFichier, codeTraitFichier, codValVal, mntTotal, nbreTotal,
			// mntTotalInter,
			// nbreTotalInter, mntTotalIntra, nbreTotalIntra });
			suiviFileTeleCompensation = new SuiviFileTeleCompensation();
			suiviFileTeleCompensation.setNomOrigSfile(nomFichier);
			suiviFileTeleCompensation.setCodStrcBct(codeStructure);
			suiviFileTeleCompensation.setDateOperSfile(datePECFichier);
			suiviFileTeleCompensation.setCodValVal(codValVal);
			suiviFileTeleCompensation.setMntTotSfile(mntTotal);
			suiviFileTeleCompensation.setNbrTotSfile(nbreTotal);
			suiviFileTeleCompensation.setMntTotInter(mntTotalInter);
			suiviFileTeleCompensation.setNbrTotInter(nbreTotalInter);
			suiviFileTeleCompensation.setMntTotIntra(mntTotalIntra);
			suiviFileTeleCompensation.setNbrTotIntra(nbreTotalIntra);
			suiviFileTeleCompensation.setMntGlobTot(mntGlobTotFichier);
			suiviFileTeleCompensation.setNbrGlobTot(nbrGlobTotFichier);
			suiviFileTeleCompensation.setCodTraitSfile(Long.valueOf(codeTraitFichier));
			crudService.create(suiviFileTeleCompensation);

		} else {
			// update nbdour 01122014 : en cas d'update , faut garnir les
			// montants !
			// jt.update("update SUIVI_FILE_TELECOMPENSATION set " +
			// "COD_TRAI_SFILE =" + codeTraitFichier + ","
			// + "MNT_TOT_SFILE=" + mntTotal + "," + "NBR_TOT_SFILE=" +
			// nbreTotal + "," + "MNT_TOT_INTER ="
			// + mntTotalInter + "," + "NBR_TOT_INTER=" + nbreTotalInter + "," +
			// "MNT_TOT_INTRA="
			// + mntTotalIntra + "," + "NBR_TOT_INTRA=" + nbreTotalIntra
			//
			// + " where NOM_ORIG_SFILE='" + nomFichier + "'");

			suiviFileTeleCompensation.setMntTotSfile(mntTotal);
			suiviFileTeleCompensation.setNbrTotSfile(nbreTotal);
			suiviFileTeleCompensation.setMntTotInter(mntTotalInter);
			suiviFileTeleCompensation.setNbrTotInter(nbreTotalInter);
			suiviFileTeleCompensation.setMntTotIntra(mntTotalIntra);
			suiviFileTeleCompensation.setNbrTotIntra(nbreTotalIntra);
			suiviFileTeleCompensation.setMntGlobTot(mntGlobTotFichier);
			suiviFileTeleCompensation.setNbrGlobTot(nbrGlobTotFichier);
			suiviFileTeleCompensation.setCodTraitSfile(Long.valueOf(codeTraitFichier));
			crudService.update(suiviFileTeleCompensation);

		}

	}

	public void importFromFile(String fichier, String fileName, String pathAg, String datComp, String pathSucces)
			throws IOException, ParseException, SQLException {
		// Begin Import
		mntGlobaleFichier = 0L;
		nbrGlobalFichier = 0L;
		mntTotal = 0L;
		nbrTotal = 0L;
		mntTotalInter = 0L;
		nbrTotalInter = 0L;
		mntTotalIntra = 0L;
		nbrTotalIntra = 0L;
		String strc = fileName.substring(3, 6);
		String codVal = fileName.substring(7, 9);
		logger.info(strc + "/" + codVal);
		// Begin Import

		logger.info("Importing :" + fichier);

		InputStream ips = new FileInputStream(fichier);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);
		String line;
		long numberLine = 2;
		line = br.readLine();
		// lecture global du fichier

		if (line != null && !line.isEmpty()) {
			mntGlobaleFichier = Long.valueOf(line.substring(28, 43));
			nbrGlobalFichier = Long.valueOf(line.substring(43, 53));
		}

		// null caracter to see !!!!
		boolean insertOk = true;
		while ((line = br.readLine()) != null && insertOk) {
			line = line.trim();
			if (line.length() > 0)
				if (line.startsWith("241") || line.startsWith("240") || line.startsWith("242")) {
					if (line.substring(26, 28).equals("00")) {
						createEffet(line, fileName, numberLine, pathAg, datComp);
					}
					if (!line.substring(26, 28).equals("00")) {
						createCompEffet(line, fileName, numberLine, pathAg, datComp);
					}
				}

			numberLine++;
		}

		logger.info("Importing lines done:" + numberLine);
		br.close();

		// 22 encaissment
		List<EffetRecuTmp> list2221 = new ArrayList<EffetRecuTmp>();
		expression = search.createExpression();
		ICriteria criteria1 = search.createCriteria();
		if (fileName.contains("-40-") && fileName.contains("-22-")) {
			criteria1.add(expression.eq("codVal", Long.valueOf(40))); // / Date
			criteria1.add(expression.eq("codEnr", Long.valueOf(22))); // / Date

			criteria1.add(expression.eq("effetId.datOpe", compensationVo.getDateComptable())); // / Date

			criteria1.add(expression.isNull("codEtatEff"));

			criteria1.add(expression.eq("codAgeDes",
					StrHandler.lpad(compensationVo.getStructure().getCodBctStrc(), '0', 3))); // /
																							  // Structure
			list2221 = search.find(EffetRecuTmp.class, criteria1);

		}
		if (fileName.contains("-41-") && fileName.contains("-22-")) {
			criteria1.add(expression.eq("codVal", Long.valueOf(41))); // / Date
			criteria1.add(expression.eq("codEnr", Long.valueOf(22))); // / Date

			criteria1.add(expression.eq("effetId.datOpe", compensationVo.getDateComptable())); // / Date

			criteria1.add(expression.isNull("codEtatEff"));

			criteria1.add(expression.eq("codAgeDes",
					StrHandler.lpad(compensationVo.getStructure().getCodBctStrc(), '0', 3))); // /
																							  // Structure
			list2221 = search.find(EffetRecuTmp.class, criteria1);

		}
		if (fileName.contains("-42-") && fileName.contains("-21-")) {
			criteria1.add(expression.eq("codVal", Long.valueOf(42))); // / Date
			criteria1.add(expression.eq("codEnr", Long.valueOf(21))); // / Date

			criteria1.add(expression.eq("effetId.datOpe", compensationVo.getDateComptable())); // / Date

			criteria1.add(expression.isNull("codEtatEff"));

			criteria1.add(expression.eq("codAgeDes",
					StrHandler.lpad(compensationVo.getStructure().getCodBctStrc(), '0', 3))); // /
																							  // Structure
			list2221 = search.find(EffetRecuTmp.class, criteria1);

		}

		if (fileName.contains("-41-") && fileName.contains("-21-")) {
			criteria1.add(expression.eq("codEnr", Long.valueOf(21))); // / Date
			criteria1.add(expression.eq("codVal", Long.valueOf(41))); // / Date

			criteria1.add(expression.eq("effetId.datOpe", compensationVo.getDateComptable())); // / Date

			criteria1.add(expression.isNull("codEtatEff"));

			criteria1.add(expression.eq("codAgeDes",
					StrHandler.lpad(compensationVo.getStructure().getCodBctStrc(), '0', 3))); // /
			list2221 = search.find(EffetRecuTmp.class, criteria1);

		}
		if (fileName.contains("-40-") && fileName.contains("-21-")) {
			criteria1.add(expression.eq("codEnr", Long.valueOf(21))); // / Date
			criteria1.add(expression.eq("codVal", Long.valueOf(40))); // / Date

			criteria1.add(expression.eq("effetId.datOpe", compensationVo.getDateComptable())); // / Date

			criteria1.add(expression.isNull("codEtatEff"));

			criteria1.add(expression.eq("codAgeDes",
					StrHandler.lpad(compensationVo.getStructure().getCodBctStrc(), '0', 3))); // /
			list2221 = search.find(EffetRecuTmp.class, criteria1);

		}

		for (int i = 0; i < list2221.size(); i++) {
			EffetRecuTmp tmp = list2221.get(i);
			if (tmp.getCodBan().equals(tmp.getCodBanDes())) {
				mntTotalIntra += tmp.getMntEff();
				nbrTotalIntra += 1;
			} else {
				mntTotalInter += tmp.getMntEff();
				nbrTotalInter += 1;
			}
		}

		// 22 escompte
		List<EffetRecuTmpEsc> list22Escompte = new ArrayList<EffetRecuTmpEsc>();

		expression = search.createExpression();
		criteria1 = search.createCriteria();
		if (fileName.contains("-40-") && fileName.contains("-22-")) {
			criteria1.add(expression.eq("codVal", Long.valueOf(40))); // / Date
			criteria1.add(expression.eq("codEnr", Long.valueOf(22))); // / Date

			criteria1.add(expression.eq("effetId.datOpe", compensationVo.getDateComptable())); // / Date

			criteria1.add(expression.isNull("codEtatEff"));

			criteria1.add(expression.eq("codAgeDes",
					StrHandler.lpad(compensationVo.getStructure().getCodBctStrc(), '0', 3))); // /
																							  // Structure
			list22Escompte = search.find(EffetRecuTmpEsc.class, criteria1);

		}
		if (fileName.contains("-41-") && fileName.contains("-22-")) {
			criteria1.add(expression.eq("codVal", Long.valueOf(41))); // / Date
			criteria1.add(expression.eq("codEnr", Long.valueOf(22))); // / Date

			criteria1.add(expression.eq("effetId.datOpe", compensationVo.getDateComptable())); // / Date

			criteria1.add(expression.isNull("codEtatEff"));

			criteria1.add(expression.eq("codAgeDes",
					StrHandler.lpad(compensationVo.getStructure().getCodBctStrc(), '0', 3))); // /
																							  // Structure
			list22Escompte = search.find(EffetRecuTmpEsc.class, criteria1);

		}
		for (int i = 0; i < list22Escompte.size(); i++) {
			EffetRecuTmpEsc tmp = list22Escompte.get(i);
			if (tmp.getCodBan().equals(tmp.getCodBanDes())) {
				mntTotalIntra += tmp.getMntEff();
				nbrTotalIntra += 1;
			} else {
				mntTotalInter += tmp.getMntEff();
				nbrTotalInter += 1;
			}
		}

		mntTotal = mntTotalInter + mntTotalIntra;
		nbrTotal = nbrTotalInter + nbrTotalIntra;
//		System.out.println("Recap insertion Inter:" + mntTotalInter + "/" + nbrTotalInter);
//		System.out.println("Recap insertion Intra:" + mntTotalIntra + "/" + nbrTotalIntra);
//		System.out.println("Recap insertion :" + mntTotal + "/" + nbrTotal);
		Util.copy(pathAg + File.separatorChar + fileName, pathSucces + File.separatorChar + fileName);
		// Util.deleteFile(pathAg+File.separatorChar+fileName);
		ajouterFichierAvecMontant(fileName, strc, compensationVo.getDateComptable(), 1, Long.valueOf(codVal), mntTotal,
				nbrTotal, mntTotalInter, nbrTotalInter, nbrTotalIntra, mntTotalIntra, mntGlobaleFichier,
				nbrGlobalFichier);

		// End Import

	}

	public void importFromFileSort(String fichier, String fileName, String pathAg, String datComp, String pathSucces)
			throws IOException, ParseException, SQLException {

		// Begin Import
		mntGlobaleFichier = 0L;
		nbrGlobalFichier = 0L;
		mntTotal = 0L;
		nbrTotal = 0L;
		mntTotalInter = 0L;
		nbrTotalInter = 0L;
		mntTotalIntra = 0L;
		nbrTotalIntra = 0L;
		String strc = fileName.substring(3, 6);
		String codVal = fileName.substring(7, 9);
		logger.info(strc + "/" + codVal);

		logger.info("Importing Sort:" + fichier);

		InputStream ips = new FileInputStream(fichier);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);
		String line;
		long numberLine = 2;
		line = br.readLine();
		if (line != null && !line.isEmpty()) {
			mntGlobaleFichier = Long.valueOf(line.substring(28, 43));
			nbrGlobalFichier = Long.valueOf(line.substring(43, 53));
		}
		// null caracter to see !!!!
		boolean insertOk = true;
		while ((line = br.readLine()) != null && insertOk) {
			line = line.trim();
			if (line.length() > 0)
				if (line.startsWith("241") || line.startsWith("240") || line.startsWith("242"))
					if (line.substring(26, 28).equals("00") && line.length() > 211)
						createSort(line, fileName, numberLine, pathAg, datComp);

			numberLine++;
		}
		br.close();
		// encaissment
		expression = search.createExpression();
		ICriteria criteria1 = search.createCriteria();
		if (fileName.contains("-40-")) {
			criteria1.add(expression.eq("codVal", Long.valueOf(40))); // / Date
		}
		if (fileName.contains("-41-")) {
			criteria1.add(expression.eq("codVal", Long.valueOf(41))); // / Date
		}
		criteria1.add(expression.eq("codEnr", Long.valueOf(25))); // / Date

		criteria1.add(expression.eq("effetId.datOpe", compensationVo.getDateComptable())); // / Date

		criteria1.add(expression.isNull("codEtatEff"));

		criteria1
				.add(expression.eq("codAgeDes", StrHandler.lpad(compensationVo.getStructure().getCodBctStrc(), '0', 3))); // /
																														  // Structure

		List<EffetRecuTmp> list25 = search.find(EffetRecuTmp.class, criteria1);
		for (int i = 0; i < list25.size(); i++) {
			EffetRecuTmp tmp = list25.get(i);
			if (tmp.getCodBan().equals(tmp.getCodBanDes())) {
				mntTotalIntra += tmp.getMntEff();
				nbrTotalIntra += 1;
			} else {
				mntTotalInter += tmp.getMntEff();
				nbrTotalInter += 1;
			}
		}
		// escompte
		expression = search.createExpression();
		criteria1 = search.createCriteria();
		if (fileName.contains("-40-")) {
			criteria1.add(expression.eq("codVal", Long.valueOf(40))); // / Date
		}
		if (fileName.contains("-41-")) {
			criteria1.add(expression.eq("codVal", Long.valueOf(41))); // / Date
		}
		criteria1.add(expression.eq("codEnr", Long.valueOf(25))); // / Date

		criteria1.add(expression.eq("effetId.datOpe", compensationVo.getDateComptable())); // / Date

		criteria1.add(expression.isNull("codEtatEff"));

		criteria1
				.add(expression.eq("codAgeDes", StrHandler.lpad(compensationVo.getStructure().getCodBctStrc(), '0', 3))); // /
																														  // Structure

		List<EffetRecuTmpEsc> list25Escompte = search.find(EffetRecuTmpEsc.class, criteria1);
		for (int i = 0; i < list25Escompte.size(); i++) {
			EffetRecuTmpEsc tmp = list25Escompte.get(i);
			if (tmp.getCodBan().equals(tmp.getCodBanDes())) {
				mntTotalIntra += tmp.getMntEff();
				nbrTotalIntra += 1;
			} else {
				mntTotalInter += tmp.getMntEff();
				nbrTotalInter += 1;
			}
		}
		mntTotal = mntTotalInter + mntTotalIntra;
		nbrTotal = nbrTotalInter + nbrTotalIntra;
		Util.copy(pathAg + File.separatorChar + fileName, pathSucces + File.separatorChar + fileName);
		ajouterFichierAvecMontant(fileName, strc, compensationVo.getDateComptable(), 1, Long.valueOf(codVal), mntTotal,
				nbrTotal, mntTotalInter, nbrTotalInter, nbrTotalIntra, mntTotalIntra, mntGlobaleFichier,
				nbrGlobalFichier);

	}

	public void createEffet(String line, String file, long compteur, String pathAg, String dateCom)
			throws ParseException, IOException, SQLException {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");

		EffetRecuTmp tmp = new EffetRecuTmp();
		String strc = file.substring(3, 6);
		String codVal = file.substring(7, 9);
		logger.info(strc + "/" + codVal);

		Long codEnr = Long.valueOf(line.substring(21, 23));
		/**
		 * Creation Valeur_22
		 */
		if (codEnr.equals(Long.valueOf(22))) {
			// System.out.println(line.length());
			if (line.length() >= 375) {
				EffetId effetId = new EffetId();
				effetId.setNumEff(line.substring(73, 85));
				effetId.setDatOpe(formatDate.parse(dateCom));
				tmp.setEffetId(effetId);
				tmp.setCodAge(line.substring(87, 90));
				tmp.setCodBan(line.substring(85, 87));
				tmp.setCodBanDes(line.substring(130, 132));
				tmp.setCodAgeDes(line.substring(132, 135));
				tmp.setNumLot(Long.valueOf(line.substring(17, 21)));
				tmp.setCodEnr(Long.valueOf(line.substring(21, 23)));
				tmp.setCodDev(line.substring(23, 26));
				tmp.setMntEff(Long.valueOf(line.substring(28, 43)));
				tmp.setMntInt(Long.valueOf(line.substring(43, 58)));
				// mnt Prot
				tmp.setCodVal(Long.valueOf(line.substring(1, 3)));
				Long mntProt = Long.valueOf(line.substring(58, 73));
				if (!mntProt.equals(0L))
					tmp.setCodProt(1L);
				tmp.setMntFra(mntProt);

				tmp.setRibTir(line.substring(85, 105));
				tmp.setRibTirIni(line.substring(105, 125));

				tmp.setRibBen(line.substring(130, 150));
				tmp.setNomBen(line.substring(150, 180));
				tmp.setNomTir(line.substring(180, 210));
				try {
					tmp.setRefComTir(line.substring(210, 240));
				} catch (Exception ex) {

				}
				try {
					tmp.setCodAcc(Long.valueOf(line.substring(240, 241)));
				} catch (Exception ex) {
					tmp.setCodAcc(0L);
				}
				try {
					tmp.setCodEnd(Long.valueOf(line.substring(241, 242)));
				} catch (Exception ex) {
					tmp.setCodEnd(0L);
				}
				tmp.setDatEch(formatDate.parse(line.substring(242, 250)));
				tmp.setDatEchIni(formatDate.parse(line.substring(250, 258)));
				tmp.setDatCre(formatDate.parse(line.substring(258, 266)));
				tmp.setLieCre(line.substring(266, 296));

				tmp.setRefComBen(line.substring(296, 326));
				try {
					tmp.setCodOrd(Long.valueOf(line.substring(326, 327)));
				} catch (Exception ex) {
					tmp.setCodOrd(0L);
				}
				try {
					tmp.setCodSit(Long.valueOf(line.substring(327, 328)));
				} catch (Exception ex) {
					tmp.setCodSit(0L);
				}
				try {
					tmp.setCodNatCpt(Long.valueOf(line.substring(328, 329)));
				} catch (Exception ex) {
					tmp.setCodNatCpt(0L);
				}
				try {
					tmp.setDatCmp(formatDate.parse(line.substring(329, 337)));
				} catch (ParseException ex) {
					tmp.setDatCmp(tmp.getEffetId().getDatOpe());
				}
				tmp.setMotRej(line.substring(337, 345));
				String motifs = line.substring(337, 345);
				int chunkSize = 2;

				List<String> result = new ArrayList<String>();

				int length = motifs.length();
				for (int i = 0; i < length; i += chunkSize) {
					String seq = motifs.substring(i, Math.min(length, i + chunkSize));
					result.add(seq);

				}
				logger.info(motifs + "/" + result.size());
				if (!result.get(0).equals("00"))
					tmp.setCodRej1(result.get(0));
				if (!result.get(1).equals("00"))
					tmp.setCodRej2(result.get(1));
				if (!result.get(2).equals("00"))
					tmp.setCodRej3(result.get(2));
				if (!result.get(3).equals("00"))
					tmp.setCodRej4(result.get(3));
				logger.info("Motif:" + tmp.getCodRej1() + "/" + tmp.getCodRej2() + "/" + tmp.getCodRej3() + "/"
						+ tmp.getCodRej4());
				try {
					tmp.setCodRisBct(line.substring(345, 351));
				} catch (Exception ex) {
					tmp.setCodRisBct("000000");
				}
				tmp.setRefFic(file);
				// unused field File.separatorChar
				tmp.setCodNatEta(0L);
				tmp.setRngDet(0L);
			} else if (line.length() == 218) {
				EffetId effetId = new EffetId();
				effetId.setNumEff(line.substring(73, 85));
				effetId.setDatOpe(formatDate.parse(dateCom));
				tmp.setEffetId(effetId);

				tmp.setCodAge(line.substring(87, 90));
				tmp.setCodBan(line.substring(85, 87));
				tmp.setCodBanDes(line.substring(130, 132));
				tmp.setCodAgeDes(line.substring(132, 135));

				tmp.setNumLot(Long.valueOf(line.substring(17, 21)));
				tmp.setCodEnr(Long.valueOf(line.substring(21, 23)));
				tmp.setCodDev(line.substring(23, 26));
				tmp.setMntEff(Long.valueOf(line.substring(28, 43)));
				tmp.setMntInt(Long.valueOf(line.substring(43, 58)));
				// mnt Prot
				tmp.setCodVal(Long.valueOf(line.substring(1, 3)));
				Long mntProt = Long.valueOf(line.substring(58, 73));
				if (!mntProt.equals(0L))
					tmp.setCodProt(1L);
				tmp.setMntFra(mntProt);

				tmp.setRibTir(line.substring(85, 105));
				tmp.setRibTirIni(line.substring(105, 125));

				tmp.setRibBen(line.substring(130, 150));

				tmp.setNomBen(line.substring(150, 180));
				tmp.setNomTir(line.substring(180, 210));

				tmp.setDatEch(formatDate.parse(dateCom));
				tmp.setDatCre(formatDate.parse(dateCom));
				tmp.setCodSit(0L);
				tmp.setCodNatEta(0L);
				tmp.setRngDet(0L);
				String motifs = line.substring(210, 218);
				tmp.setMotRej(motifs);
				int chunkSize = 2;

				List<String> result = new ArrayList<String>();

				int length = motifs.length();
				for (int i = 0; i < length; i += chunkSize) {
					String seq = motifs.substring(i, Math.min(length, i + chunkSize));
					result.add(seq);

				}
				logger.info(motifs + "/" + result.size());
				if (!result.get(0).equals("00"))
					tmp.setCodRej1(result.get(0));
				if (!result.get(1).equals("00"))
					tmp.setCodRej2(result.get(1));
				if (!result.get(2).equals("00"))
					tmp.setCodRej3(result.get(2));
				if (!result.get(3).equals("00"))
					tmp.setCodRej4(result.get(3));
				logger.info("Motif:" + tmp.getCodRej1() + "/" + tmp.getCodRej2() + "/" + tmp.getCodRej3() + "/"
						+ tmp.getCodRej4());
				tmp.setRefFic(file);

			} else if (line.length() == 345) {
				EffetId effetId = new EffetId();
				effetId.setNumEff(line.substring(73, 85));
				effetId.setDatOpe(formatDate.parse(dateCom));
				tmp.setEffetId(effetId);

				tmp.setCodAge(line.substring(87, 90));
				tmp.setCodBan(line.substring(85, 87));
				tmp.setCodBanDes(line.substring(130, 132));
				tmp.setCodAgeDes(line.substring(132, 135));

				tmp.setNumLot(Long.valueOf(line.substring(17, 21)));
				tmp.setCodEnr(Long.valueOf(line.substring(21, 23)));
				tmp.setCodDev(line.substring(23, 26));
				tmp.setMntEff(Long.valueOf(line.substring(28, 43)));
				tmp.setMntInt(Long.valueOf(line.substring(43, 58)));
				// mnt Prot
				tmp.setCodVal(Long.valueOf(line.substring(1, 3)));
				Long mntProt = Long.valueOf(line.substring(58, 73));
				if (!mntProt.equals(0L))
					tmp.setCodProt(1L);
				tmp.setMntFra(mntProt);

				tmp.setRibTir(line.substring(85, 105));
				tmp.setRibTirIni(line.substring(105, 125));

				tmp.setRibBen(line.substring(130, 150));
				tmp.setNomBen(line.substring(150, 180));
				tmp.setNomTir(line.substring(180, 210));
				tmp.setDatEch(formatDate.parse(dateCom));
				tmp.setDatCre(formatDate.parse(dateCom));

				tmp.setCodSit(0L);
				tmp.setCodNatEta(0L);
				tmp.setRngDet(0L);

				String motifs = line.substring(337, 345);
				tmp.setMotRej(motifs);
				int chunkSize = 2;

				List<String> result = new ArrayList<String>();

				int length = motifs.length();
				for (int i = 0; i < length; i += chunkSize) {
					String seq = motifs.substring(i, Math.min(length, i + chunkSize));
					result.add(seq);

				}
				logger.info(motifs + "/" + result.size());
				if (!result.get(0).equals("00"))
					tmp.setCodRej1(result.get(0));
				if (!result.get(1).equals("00"))
					tmp.setCodRej2(result.get(1));
				if (!result.get(2).equals("00"))
					tmp.setCodRej3(result.get(2));
				if (!result.get(3).equals("00"))
					tmp.setCodRej4(result.get(3));
				logger.info("Motif:" + tmp.getCodRej1() + "/" + tmp.getCodRej2() + "/" + tmp.getCodRej3() + "/"
						+ tmp.getCodRej4());

				tmp.setRefFic(file);

			}

			// regeneration du cle effet 40-42
			if (tmp.getCodVal().equals(40L)) {

				if (!verifierNumEffet(tmp.getEffetId().getNumEff())) {
					String numeffet =
							tmp.getCodAgeDes() + tmp.getCodVal() + tmp.getEffetId().getNumEff().substring(5, 12);
					tmp.setNumEffIni(tmp.getEffetId().getNumEff());
					tmp.getEffetId().setNumEff(numeffet);
				}

			}

			logger.info(tmp.toString());
			if (tmp.getRefComTir() == null || (tmp.getRefComTir() != null && !tmp.getRefComTir().startsWith("6"))) {// ne
																													// pas
																													// traiter
																													// les
				if (!compensationDAO.isEffetInserted(tmp.getEffetId().getNumEff(),
						DateHandler.dateToStr(tmp.getEffetId().getDatOpe()))) // escomptes"6"
				{
					crudService.create(tmp);
				}
			} else {
				if (tmp.getRefComTir() != null && tmp.getRefComTir().startsWith("6")) {
					EffetRecuTmpEsc tmpEsc = createEsc(tmp);
					if (!compensationDAO.isEffetEscInserted(tmp.getEffetId().getNumEff(),
							DateHandler.dateToStr(tmp.getEffetId().getDatOpe()))) {

						crudService.create(tmpEsc);
					}
				}
			}

		} else {
			/**
			 * Creation Valeur_21
			 */

			EffetId effetId = new EffetId();
			effetId.setNumEff(line.substring(73, 85));
			effetId.setDatOpe(formatDate.parse(line.substring(9, 17)));
			tmp.setEffetId(effetId);

			tmp.setCodAgeDes(ageDes);
			tmp.setCodBanDes(banDes);
			tmp.setCodBan(line.substring(130, 132));
			tmp.setCodAge(line.substring(132, 135));

			logger.info("Saving Effet:" + line.substring(73, 85) + ":" + Long.valueOf(line.substring(21, 23)));

			tmp.setCodVal(Long.valueOf(line.substring(1, 3)));

			tmp.setNumLot(Long.valueOf(line.substring(17, 21)));
			tmp.setCodEnr(Long.valueOf(line.substring(21, 23)));
			tmp.setCodDev(line.substring(23, 26));
			tmp.setMntEff(Long.valueOf(line.substring(28, 43)));
			// pas de traietemet d'interet-reclamation trezor 03-06-2016
			tmp.setMntIntIni(Long.valueOf(line.substring(43, 58)));
			tmp.setMntInt(Long.valueOf(0));
			// mnt Prot

			Long mntProt = Long.valueOf(line.substring(58, 73));
			if (!mntProt.equals(0L))
				tmp.setCodProt(1L);
			tmp.setMntFra(mntProt);

			tmp.setRibTir(line.substring(85, 105));
			tmp.setRibTirIni(line.substring(105, 125));

			tmp.setRibBen(line.substring(130, 150));
			tmp.setNomBen(line.substring(150, 180).trim());
			tmp.setNomTir(line.substring(180, 210).trim());
			try {
				tmp.setRefComTir(line.substring(210, 240));
			} catch (Exception ex) {

			}
			try {
				tmp.setCodAcc(Long.valueOf(line.substring(240, 241)));
			} catch (Exception ex) {
				tmp.setCodAcc(0L);
			}
			try {
				tmp.setCodEnd(Long.valueOf(line.substring(241, 242)));
			} catch (Exception ex) {
				tmp.setCodEnd(0L);
			}
			try {
				tmp.setDatEch(formatDate.parse(line.substring(242, 250)));
			} catch (Exception ex) {
				tmp.setDatEch(formatDate.parse(dateCom));
			}
			try {
				tmp.setDatEchIni(formatDate.parse(line.substring(250, 258)));
			} catch (Exception ex) {

			}
			try {
				tmp.setDatCre(formatDate.parse(line.substring(258, 266)));
			} catch (Exception ex) {
				tmp.setDatCre(formatDate.parse(dateCom));
			}
			try {

				tmp.setLieCre(line.substring(266, 296));
			} catch (Exception ex) {

			}
			try {
				tmp.setRefComBen(line.substring(296, 326));
			} catch (Exception ex) {

			}
			try {
				tmp.setCodOrd(Long.valueOf(line.substring(326, 327)));
			} catch (Exception ex) {
				tmp.setCodOrd(0L);
			}
			try {
				tmp.setCodSit(Long.valueOf(line.substring(327, 328)));
			} catch (Exception ex) {
				tmp.setCodSit(0L);
			}
			// a revoir valeur
			try {
				tmp.setCodNatCpt(Long.valueOf(line.substring(328, 329)));
			} catch (Exception ex) {
				tmp.setCodNatCpt(null);
			}
			try {
				tmp.setDatCmp(formatDate.parse(line.substring(329, 337)));
			} catch (ParseException ex) {
				tmp.setDatCmp(formatDate.parse(dateCom));
			}
			tmp.setMotRej(line.substring(337, 345));
			String motifs = line.substring(337, 345);
			int chunkSize = 2;

			List<String> result = new ArrayList<String>();

			int length = motifs.length();
			for (int i = 0; i < length; i += chunkSize) {
				String seq = motifs.substring(i, Math.min(length, i + chunkSize));
				result.add(seq);

			}
			logger.info(motifs + "/" + result.size());
			if (!result.get(0).equals("00"))
				tmp.setCodRej1(result.get(0));
			if (!result.get(1).equals("00"))
				tmp.setCodRej2(result.get(1));
			if (!result.get(2).equals("00"))
				tmp.setCodRej3(result.get(2));
			if (!result.get(3).equals("00"))
				tmp.setCodRej4(result.get(3));
			logger.info("Motif:" + tmp.getCodRej1() + "/" + tmp.getCodRej2() + "/" + tmp.getCodRej3() + "/"
					+ tmp.getCodRej4());
			try {
				tmp.setCodRisBct(line.substring(345, 351));
			} catch (Exception ex) {
				tmp.setCodRisBct("000000");
			}
			tmp.setRefFic(file);
			// unused field File.separatorChar
			tmp.setCodNatEta(0L);
			tmp.setRngDet(0L);
			// test Fonctionel
			// dateCom="20140407";
			logger.info(tmp.toString());
			boolean emis = false;
			if (tmp.getCodVal().equals(41L) && tmp.getCodEnr().equals(21L)) {
				if (tmp.getCodBan().equals(tmp.getCodBanDes()))
					emis = true;
				tmp.setImgRec(getImg(strc, tmp.getEffetId().getNumEff(), tmp.getCodBan(), "R", dateCom, emis));
				tmp.setImgVes(getImg(strc, tmp.getEffetId().getNumEff(), tmp.getCodBan(), "V", dateCom, emis));
			}

			// regeneration du cle effet 40-42
			if (tmp.getCodVal().equals(40L) || tmp.getCodVal().equals(42L)) {
				if (!verifierNumEffet(tmp.getEffetId().getNumEff())) {
					String numeffet =
							tmp.getCodAgeDes() + tmp.getCodVal() + tmp.getEffetId().getNumEff().substring(5, 12);
					tmp.setNumEffIni(tmp.getEffetId().getNumEff());
					tmp.getEffetId().setNumEff(numeffet);
				}

			}

			if (!compensationDAO.isEffetInserted(tmp.getEffetId().getNumEff(),
					DateHandler.dateToStr(tmp.getEffetId().getDatOpe()))) {

				crudService.create(tmp);
			}

			logger.info("fill temp done");
		}

		// boolean isEscompte = false;
		// if (tmp.getCodEnr() != 21 && tmp.getRefComTir() != null &&
		// tmp.getRefComTir().startsWith("6"))
		// isEscompte = true;
		// if (!isEscompte) {
		// if (tmp.getCodBan().equals(tmp.getCodBanDes())) {
		// mntTotalIntra += tmp.getMntEff();
		// nbrTotalIntra += 1;
		// } else {
		// mntTotalInter += tmp.getMntEff();
		// nbrTotalInter += 1;
		// }
		// }

	}

	public void createCompEffet(String line, String file, long compteur, String pathAg, String dateCom) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");

		ComplementEffetRecu comp = new ComplementEffetRecu();
		String strc = file.substring(3, 6);
		String codVal = file.substring(7, 9);
		logger.info(strc + "/" + codVal);
		try {
			Long codEnr = Long.valueOf(line.substring(21, 23));

			/**
			 * Creation Valeur_21
			 */
			if (codEnr.equals(21L)) {
				EffetId effetId = new EffetId();
				effetId.setNumEff(line.substring(28, 40));
				effetId.setDatOpe(formatDate.parse(line.substring(9, 17)));
				effetId.setNumOrdre(Long.valueOf(line.substring(26, 28)));
				comp.setEffetId(effetId);

				comp.setDonnComplement(line.substring(26));
				comp.setCodAgeDes(strc);
				crudService.create(comp);

				logger.info("fill comp  temp done");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public boolean verifierNumEffet(String num) {
		if (num.length() == 12) {
			if (calculerCleEffet(num.substring(0, 10)).equals(num.substring(10, 12))) {
				try {
					String numSerie = num.substring(0, 3);
					// System.out.println(numSerie);
					if (Long.valueOf(numSerie).longValue() > 0 && Long.valueOf(num.substring(10, 12)).longValue() > 0)
						return true;
					else
						return false;

				} catch (Exception ex) {
					return false;
				}
			} else

				return false;
		} else
			return false;
	}

	public String calculerCleEffet(String numEffet) {
		String resultat = "";
		if (numEffet.length() == 10) {

			String RI = numEffet;
			BigInteger rr = new BigInteger(RI.concat("00"));
			int rest = rr.mod(new BigInteger("97")).intValue();
			int nb = 97 - rest;
			String nbr = "" + nb;
			if (nbr.length() == 1)
				resultat = "0" + nbr;
			else
				resultat = nbr;
		}
		// System.out.println(resultat);
		return resultat;
	}

	public void createSort(String line, String file, long compteur, String pathAg, String dateCom)
			throws ParseException, IOException, SQLException {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
		EffetRecuTmp tmp = new EffetRecuTmp();
		String strc = file.substring(3, 6);
		String codVal = file.substring(7, 9);
		//System.out.println(line);
		logger.info(strc + "/" + codVal);

		tmp.setCodAge(line.substring(87, 90));
		tmp.setCodBan(line.substring(85, 87));
		tmp.setCodBanDes(line.substring(130, 132));
		tmp.setCodAgeDes(line.substring(132, 135));

		EffetId effetId = new EffetId();
		effetId.setNumEff(line.substring(73, 85));
		effetId.setDatOpe(formatDate.parse(line.substring(9, 17)));
		tmp.setEffetId(effetId);
		logger.info("Saving Sort: File :" + file + File.separatorChar + line.substring(73, 85) + ":"
				+ Long.valueOf(line.substring(21, 23)));

		tmp.setCodVal(Long.valueOf(line.substring(1, 3)));

		tmp.setNumLot(Long.valueOf(line.substring(17, 21)));
		tmp.setCodEnr(Long.valueOf(line.substring(21, 23)));
		tmp.setCodDev(line.substring(23, 26));
		tmp.setMntEff(Long.valueOf(line.substring(28, 43)));
		// pas de traietemet d'interet-reclamation trezor 03-06-2016
		tmp.setMntIntIni(Long.valueOf(line.substring(43, 58)));
		tmp.setMntInt(Long.valueOf(0));

		tmp.setRibTir(line.substring(85, 105));

		tmp.setRibBen(line.substring(130, 150));
		tmp.setNomBen(line.substring(150, 180).trim());
		tmp.setNomTir(line.substring(180, 210).trim());

		tmp.setDatEch(formatDate.parse(line.substring(242, 250)));

		tmp.setDatCre(formatDate.parse(line.substring(258, 266)));

		tmp.setRefFic(file);
		// unused field File.separatorChar
		tmp.setCodNatEta(0L);
		tmp.setRngDet(0L);
		tmp.setCodSit(0L);
		tmp.setCodEnd(0L);
		tmp.setMntFra(0L);
		tmp.setCodOrd(0L);
		try {
			tmp.setRefComTir(line.substring(210, 211));
		} catch (Exception ex) {
			tmp.setRefComTir(null);
		}
		// regeneration du cle effet 40-42
		if (tmp.getCodVal().equals(40L)) {

			if (!verifierNumEffet(tmp.getEffetId().getNumEff())) {
				String numeffet = tmp.getCodAgeDes() + tmp.getCodVal() + tmp.getEffetId().getNumEff().substring(5, 12);
				tmp.setNumEffIni(tmp.getEffetId().getNumEff());
				tmp.getEffetId().setNumEff(numeffet);
			}

		}
		logger.info(tmp.toString());
		boolean isEscompte = false;
		if (tmp.getRefComTir() != null && tmp.getRefComTir().startsWith("6"))
			isEscompte = true;
		if (!isEscompte) {
			if (!compensationDAO.isEffetInserted(tmp.getEffetId().getNumEff(),
					DateHandler.dateToStr(tmp.getEffetId().getDatOpe()))) {

				crudService.create(tmp);
			}

		} else {
			EffetRecuTmpEsc tmpEsc = createEsc(tmp);
			if (!compensationDAO.isEffetEscInserted(tmp.getEffetId().getNumEff(),
					DateHandler.dateToStr(tmp.getEffetId().getDatOpe()))) {

				crudService.create(tmpEsc);
			}

		}
		// if (tmp.getCodBan().equals(tmp.getCodBanDes())) {
		// mntTotalIntra += tmp.getMntEff();
		// nbrTotalIntra += 1;
		// } else {
		// mntTotalInter += tmp.getMntEff();
		// nbrTotalInter += 1;
		// }
		logger.info("fill temp Sort done");

	}

	/********************************************************* Util. Functions ************************************************************************/

	/**
	 * @author nbdour
	 * @param directoryPath
	 * @param fileFilter
	 * @return
	 */

	public File[] findFiles(String directoryPath, FileFilter fileFilter) {
		File directory = new File(directoryPath);
		File[] subfiles = null;
		if (!directory.exists()) {
			logger.info("Le fichier/répertoire '" + directoryPath + "' n'existe pas");
		} else if (!directory.isDirectory()) {
			logger.info("Le chemin '" + directoryPath + "' correspond à un fichier et non à un répertoire");
		} else {
			subfiles = directory.listFiles(fileFilter);
		}
		return subfiles;
	}

	/**
	 * @param numEff
	 * @param banqueEm
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	private Blob getImg(String agBct, String numEffet, String banqueEm, String RV, String dateCom, boolean emis)
			throws IOException, SQLException {

		SimpleDateFormat formatDateFile = new SimpleDateFormat("ddMMyyyy");
		String jjmmyyyySys = formatDateFile.format(new Date());

		FileInputStream fis = null;

		String pathTravailIm =
				File.separatorChar + Configuration.getParentPath() + File.separatorChar
						+ Configuration.getLocalPathCheque() + File.separatorChar + "reçu" + File.separatorChar
						+ "effet" + File.separatorChar + "AGENCE" + agBct + File.separatorChar + jjmmyyyySys
						+ File.separatorChar + "travail" + File.separatorChar + "Images";
		String remotePathImInter =
				File.separatorChar + "PFC" + File.separatorChar + "Recu" + File.separatorChar + dateCom
						+ File.separatorChar + "41" + File.separatorChar + "Images";
		String remotePathImIntra =
				File.separatorChar + "PFC" + File.separatorChar + "Emis" + File.separatorChar + dateCom
						+ File.separatorChar + "41" + File.separatorChar + "DEFALC" + File.separatorChar + "Images";
		logger.info("Path Image :" + pathTravailIm);
		logger.info("Path Image :" + remotePathImInter);
		logger.info("Path Image :" + remotePathImIntra);
		logger.info("EMIS :" + emis);
		FTPClient ftpClient = Util.connectToFtp();
		if (emis)
			Util.copyFtpFilesImgEffet(pathTravailIm, remotePathImIntra, numEffet + RV + ".JPG");
		else
			Util.copyFtpFilesImgEffet(pathTravailIm, remotePathImInter, numEffet + banqueEm + RV + ".JPG");
		try {
			File image = null;
			if (emis)
				image = new File(pathTravailIm + File.separatorChar + numEffet + RV + ".JPG");
			else
				image = new File(pathTravailIm + File.separatorChar + numEffet + banqueEm + RV + ".JPG");
			if (image.exists()) {
				if (emis)
					fis = new FileInputStream(pathTravailIm + File.separatorChar + numEffet + RV + ".JPG");
				else
					fis = new FileInputStream(pathTravailIm + File.separatorChar + numEffet + banqueEm + RV + ".JPG");
				byte[] bFile = new byte[(int) image.length()];
				fis.read(bFile);
				Blob blob = Hibernate.createBlob(bFile);
				return blob;
			} else
				logger.info("Image Not found :" + pathTravailIm + File.separatorChar + numEffet + RV + ".JPG");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			SwingInfoVo infoVo = new SwingInfoVo();
			infoVo.setStructure("" + compensationVo.getStructure().getCodStrcStrc());
			infoVo.setEtat(Constants.STATUT_EN_ERRUR);
			infoVo.setDateComptable(dateCom);
			mainFrame.getBtnExcuter().setEnabled(true);
			mainFrame.addOrUpdateEtat(infoVo);
		}
		return null;
	}

	@Override
	protected void genCroText(ValueObject arg0) {
		// TODO Auto-generated method stub

	}

}