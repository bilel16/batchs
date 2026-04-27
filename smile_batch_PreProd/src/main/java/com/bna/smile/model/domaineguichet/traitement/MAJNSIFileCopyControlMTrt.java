package com.bna.smile.model.domaineguichet.traitement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchVirFileCpy;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TmpBatchVirNSI;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineguichet.dao.GuichetDAO;
import com.bna.smile.model.domaineguichet.model.MAJNSIVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class MAJNSIFileCopyControlMTrt extends Traitement {

	public MAJNSIFileCopyControlMTrt() {
	}

	Context context = ContextHandler.getContext();

	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
	String srcFile = "D:\\MAJNSISAUV\\MAJNSI";
	Date dateModif;
	Long fileSize;
	GuichetDAO guichetDao;
	BatchVirFileCpy batchVirFileCpy;
	MAJNSIVo mAJNSIVo;
	BatchMetier batch=new BatchMetier();

	public IValueObject perform(IValueObject vo) {
		mAJNSIVo = (MAJNSIVo) vo;
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		srcFile += formatter.format(new Date());
		String line = "";
		batch.setCodBatBmet(Constants.COD_BATCH_MAJNSI);
		try {
			this.setVerifDomaine(false);
			this.setCroFlag(false);
			guichetDao = (GuichetDAO) context.getBean("guichetDAO");

			File f = new File(srcFile);
			if (!f.exists()) {
				List<BatchVirFileCpy> listBatchVirFileCpy = guichetDao.getMaxDateCreFileNSI(Constants.COD_BATCH_MAJNSI.toString());
				if (listBatchVirFileCpy != null && listBatchVirFileCpy.size() > 0)
					batchVirFileCpy = listBatchVirFileCpy.get(0);

				logger.info(" Start copying the file MAJNSI ");
			

				boolean etat = copyFileMAJNSI();
				if (etat) {
					if (batchVirFileCpy == null
							|| (!(batchVirFileCpy.getdateFileCre().getTime() == dateModif.getTime() || batchVirFileCpy
									.getfileSize().equals(fileSize)))) {
						InputStream ips = new FileInputStream(srcFile);
						InputStreamReader ipsr = new InputStreamReader(ips);
						BufferedReader br = new BufferedReader(ipsr);
						long nbLine = 0L;
						while ((line = br.readLine()) != null) {
							Structure str = new Structure();
							str.setCodStrcStrc(new Long(line.trim().substring(0, 3)));
							if(line.length()>13 && line.trim().substring(3, 13).equals("0135299999"))
							{
								TmpBatchVirNSI tmpBatchVirNSI = new TmpBatchVirNSI();
								tmpBatchVirNSI.setdataVir(line);
								tmpBatchVirNSI.setdatVir(new Date());
								tmpBatchVirNSI.setboolIns(7L);
								tmpBatchVirNSI.setStructure(str);
								CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
								crudService.create(tmpBatchVirNSI);
								nbLine++;
							}
							else if (mAJNSIVo.getListAgencesMAJNSIStrc().contains(str.getCodStrcStrc())) {

								TmpBatchVirNSI tmpBatchVirNSI = new TmpBatchVirNSI();
								tmpBatchVirNSI.setdataVir(line);
								tmpBatchVirNSI.setdatVir(new Date());
								tmpBatchVirNSI.setboolIns(0L);
								tmpBatchVirNSI.setStructure(str);
								CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
								crudService.create(tmpBatchVirNSI);
								nbLine++;

							}

						}
						br.close();
						
						
						

						BatchVirFileCpy batchVirFileCpyed = new BatchVirFileCpy();
						batchVirFileCpyed.setdateFileCre(dateModif);
						batchVirFileCpyed.setfileSize(fileSize);
						batchVirFileCpyed.setdateBatchCpy(formatDate.parse(formatDate.format(new Date())));
						SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
						batchVirFileCpyed.settimeExec(sdfDate.format(new Date()));
						batchVirFileCpyed.setnbLineCpy(nbLine);
						batchVirFileCpyed.setBatchMetier(batch);
						CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
						crudService.create(batchVirFileCpyed);
					} else {
						logger.info(" File MAJNSI alrady copied");
						
					}

				}

			} else {
				logger.info(" File MAJNSI exists");
				
			}
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJNSIFileCopyTrt-perf : ");
			text.append(e.getMessage());
			erreur.setCode("100");
			erreur.setDescription(text.toString() + " on line : " + line);
			erreur.setKey("MAJNSIFileCopyTrt-perf");
			logger.error("Exception : ", e);
			mAJNSIVo.addError(erreur);
			// /*** gerer une exception
			throw new RuntimeException(e);
		}
		return mAJNSIVo;
	}

	public void genCroText(ValueObject vo) {

	}

	public boolean copyFileMAJNSI() throws Exception {
		File localFile = new File(srcFile);
		FileOutputStream fout = new FileOutputStream(localFile);
		FTPClient ftpClient = connectToFtp("compta2", "compta2", "10.1.225.11");
		ftpClient.enterLocalPassiveMode();

		boolean success = ftpClient.retrieveFile("/home/bna/PUNCH/TESTMAJNSI", fout);
		if (success) {
			fout.flush();
			fout.close();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateModifFile = ftpClient.getModificationTime("/home/bna/PUNCH/TESTMAJNSI");
			dateModifFile = dateModifFile.substring(dateModifFile.indexOf(" ") + 1);
			dateModif = dateFormat.parse(dateModifFile);
			fileSize = localFile.length();
			logger.info(" End MAJNSI file copying ");
			

		} else {
			logger.error(" Error MAJNSI file copying ");
			
		}
		return success;
	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
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
}
