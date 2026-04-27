package com.bna.smile.model.domaineguichet.traitement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineguichet.model.MAJNSIVo;
import com.bna.smile.model.domaineguichet.service.GuichetService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class MAJAutSmileTrt extends Traitement {

	public MAJAutSmileTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	BufferedWriter bufWriter = null;

	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
	int nbExcep = 0;
	String srcFile = "D:\\MAJNSISAUV\\fbrut";
	public IValueObject perform(IValueObject vo) {
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");

		
		String srcFileLog = "D:\\MAJNSISAUV\\fbrutLog" + formatter.format(new Date());
		String line = "";
		
		try {
			boolean etat = copyFileMAJNSI();
			if (etat) {
			FileWriter fileWriterResult = null;
			fileWriterResult = new FileWriter(srcFileLog, true);
			bufWriter = new BufferedWriter(fileWriterResult);

			InputStream ips = new FileInputStream(srcFile);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);

			while ((line = br.readLine()) != null && line.length()>=33  ) {

				MAJNSIVo mAJNSIVo = new MAJNSIVo();
				mAJNSIVo.setLigne(line);
				mAJNSIVo.setBufWriter(bufWriter);
				GuichetService guichetService = (GuichetService) context.getBean("guichetService");
				try {
					mAJNSIVo = (MAJNSIVo) guichetService.MAJNSIAut(mAJNSIVo);
				} catch (Exception e) {

					nbExcep++;
				}

			}
			br.close();

			System.out.println("  ");
			System.out.println("  *************** Fin *****************");
			System.out.println("  ");
			bufWriter.close();
			
			File file = new File("D:\\MAJNSISAUV\\fbrut");
			File file2 = new File("D:\\MAJNSISAUV\\fbrut" + formatter.format(new Date()));
			file.renameTo(file2);
			}
			else
			{
				System.out.println("  ");
				System.out.println("  *************** Aucun Fichier Autorisation sur le serveur *****************");
				System.out.println("  ");
			}
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJAutTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MAJAutSmileTrt");
			logger.error("Exception : ", e);
			vo.addError(erreur);

			try {
				bufWriter.write(e.toString() + " : " + line);
				bufWriter.newLine();
				bufWriter.close();
			} catch (Exception e2) {

			}
			// throw new RuntimeException(e);

		}
		return vo;
	}

	public void genCroText(ValueObject vo) {

	}

	public boolean copyFileMAJNSI() throws Exception {
		File localFile = new File(srcFile);
		FileOutputStream fout = new FileOutputStream(localFile);
		FTPClient ftpClient = connectToFtp("compta2", "compta2", "10.1.225.11");
		ftpClient.enterLocalPassiveMode();

		boolean success = ftpClient.retrieveFile("/home/bna/PUNCH/Fbrut", fout);
		if (success) {
			fout.flush();
			fout.close();
			logger.info(" End fbrut file copying ");
			
		} else {
			logger.error(" Error fbrut file copying ");
		}
		return success;
	}

	public FTPClient connectToFtp(String user, String pass, String server) {
		FTPClient ftpClient = new FTPClient();
		try {
			{

				ftpClient.connect(server);
				ftpClient.login(user, pass);
				ftpClient.enterLocalActiveMode();
				ftpClient.setUseEPSVwithIPv4(true);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			}

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ftpClient;
	}
	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

}
