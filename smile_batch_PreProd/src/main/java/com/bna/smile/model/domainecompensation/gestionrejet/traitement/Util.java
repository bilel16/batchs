package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.log4j.Logger;

import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.oxia.fwk.context.Context;

public class Util implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Context context = ContextHandler.getContext();
	DataSource dataSource = (DataSource) context.getBean("dataSource");
	Connection co = null;
	public static final Logger logger = Logger.getLogger(Util.class);

	/**
	 * Methode permettant de lister un répertoire
	 * 
	 * @param cheminRepSource
	 *            :String
	 * @return Tableau de type String[]
	 */
	public static String[] listerRepertoire(String cheminRepSource) {

		File repSource = new File(cheminRepSource);
		String[] list = repSource.list(); // on crée une liste des noms des
										  // fichiers d'un répertoire

		return list;
	}

	/**
	 * Methode permettant de copier un fichier dans un autre répertoire
	 * 
	 * @param SourceFile
	 *            :String , NewDestFile:String
	 */
	public static int copy(String SourceFile, String NewDestFile) {

		Scanner inputFile = null;
		PrintWriter outputFile = null;
		File nomFichier = null;
		int ret = 0;
		try {

			nomFichier = new File(SourceFile); // je met SourceFile dans
											   // nomFichier
			inputFile = new Scanner(nomFichier); // je met nomFichier dans
												 // inputfile

			/*
			 * inputfile est initialisé,il est lié au fichier externe SourceFile et est ouvert en lecture
			 */

			outputFile = new PrintWriter(NewDestFile); // ouvre le fichier
													   // NewDestFile

			// écrit dans le fichier nexDestFile le contenu du fichier source

			while (inputFile.hasNext()) // regarde si la ligne suivante existe
			{
				outputFile.println(inputFile.nextLine()); // inputfile.nextline()
														  // voir scanner et
														  // file au debut de
														  // la méthode
			}
			ret = 1;

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			outputFile.close(); // ferme le fichier en écriture
			inputFile.close(); // inputfile est fermé
			return ret;
		}
	}

	/**
	 * Methode permettant de supprimer un répertoire
	 * 
	 * @param file
	 *            :File
	 * @return boolean
	 */
	public static boolean deleteDirectory(File file) {

		boolean resultat = true;

		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					resultat &= deleteDirectory(files[i]);
				} else {
					resultat &= files[i].delete();
				}
			}
		}
		resultat &= file.delete();
		return (resultat);
	}

	/**
	 * Methode permettant de donner un format de temps de type hhmmss
	 * 
	 * @param
	 * @return String
	 */
	public static String timeForNameFile() {

		Calendar calendar = Calendar.getInstance();
		String hours = StrHandler.lpad(String.valueOf(calendar.getTime().getHours()), '0', 2);
		String minutes = StrHandler.lpad(String.valueOf(calendar.getTime().getMinutes()), '0', 2);
		String seconds = StrHandler.lpad(String.valueOf(calendar.getTime().getSeconds()), '0', 2);

		return hours + minutes + seconds;
	}

	/**
	 * Methode permettant de donner un format de temps de type hh:mm:ss
	 * 
	 * @param
	 * @return String
	 */
	public static String timeForMsg() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		return sdf.format(calendar.getTime());
	}

	/**
	 * Methode permettant de tester la validité d'une date
	 * 
	 * @param annee
	 *            :int ,mois:int et jour:int
	 * @return boolean
	 */
	public static boolean testDateValide(int annee, int mois, int jour) {
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		c.set(annee, mois, jour);
		try {
			c.getTime();
		} catch (IllegalArgumentException iAE) {
			iAE.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Methode permettant d'afficher un fichier PDF à l'écran
	 * 
	 * @param pathFile
	 *            :String
	 * @return
	 */
	public static void ShowPDF(String pathFile) {

		Process p;
		try {
			p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pathFile);
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param pathReport
	 * @param pathFileGen
	 * @param map
	 */
	public void editJasper(String pathReport, String pathFileGen, Map map) {
		JasperReport jasperReport;
		JasperPrint jasperPrint;

		try {
			co = dataSource.getConnection();
			jasperReport = JasperCompileManager.compileReport(pathReport);
			jasperPrint = JasperFillManager.fillReport(jasperReport, map, co);
			JasperExportManager.exportReportToPdfFile(jasperPrint, pathFileGen);
		} catch (SQLException e) {
			// e.printStackTrace();

		} catch (JRException e) {
			// e.printStackTrace();

		}
	}

	/**
	 * @param directoryName
	 * @return
	 * @throws IOException
	 */
	public static String createDirectory(String directoryName) throws IOException {

		// TODO : This path must be getted from config file
		File dir = new File(directoryName);
		if (!dir.exists()) {
			if (dir.mkdir()) {
				return dir.getPath();
			} else {
				return null;
			}
		}
		return dir.getPath();
	}

	/**
	 * @author nbdour
	 * @param codBct
	 * @param dateComp
	 * @param remotePath
	 * @param codVal
	 * @param img
	 * @return
	 */
	public static FTPFile[] getFilesFtp(FTPClient ftpClient, String codBct, final Date dateComp, String remotePath,
			final String[] codVal, boolean img) {

		// Max of value 4 ( 30,31,32,33) (80,20) (10,VP) ....

		if (ftpClient == null || !ftpClient.isConnected())
			ftpClient = connectToFtp();

		final String val1 = codVal[0];
		final SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		final String val2 = codVal.length >= 2 ? codVal[1] : "";
		final String val3 = codVal.length >= 3 ? codVal[2] : "";
		final String val4 = codVal.length >= 4 ? codVal[3] : "";
		final String dateComptable = formatter.format(dateComp);
		FTPFile[] names;

		try {
			FTPFileFilter filter = new FTPFileFilter() {

				public boolean accept(FTPFile ftpFile) {

					return (ftpFile.isFile()
							&& ftpFile.getName().contains(dateComptable)
							&& (ftpFile.getName().contains("-" + val1 + "-")
									|| ftpFile.getName().contains("-" + val2 + "-")
									|| ftpFile.getName().contains("-" + val3 + "-") || ftpFile.getName().contains(
									"-" + val4 + "-")) && ftpFile.getName().endsWith(".RCP"));

				}
			};
			if (img) {
				names = ftpClient.listFiles(remotePath);

			} else {
				names = ftpClient.listFiles(remotePath, filter);
				logger.info("FTP FILES :" + names.length);
			}
			return names;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * @author nbdour
	 * @return
	 */
	public static FTPClient connectToFtp() {
		FTPClient ftpClient = new FTPClient();
		String user = null;
		String pass = null;
		String server = null;

		try {
			{
				user = Configuration.getUserName();
				pass = Configuration.getUserPassword();
				server = Configuration.getServerPath();
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

	/**
	 * @author nbdour
	 * @param ftpClient
	 */
	private static void disconnectFtp(FTPClient ftpClient) {
		try {
			ftpClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author nbdour
	 * @param ageBct
	 * @param dateComptable
	 * @param codVals
	 * @param localPathTravail
	 * @param remotePath
	 * @param img
	 */
	private static void copyFtpFiles(FTPClient ftpClient, String ageBct, Date dateComptable, String[] codVals,
			String localPathTravail, String remotePath, boolean img) {
		if (ftpClient == null || !ftpClient.isConnected())
			ftpClient = connectToFtp();

		FTPFile[] names = Util.getFilesFtp(ftpClient, ageBct, dateComptable, remotePath, codVals, img);
		try {
			for (int i = 0; i < names.length; i++) {

				File localFile = new File(localPathTravail + names[i].getName());
				FileOutputStream fout = new FileOutputStream(localFile);
				boolean success = ftpClient.retrieveFile(remotePath + names[i].getName(), fout);
				if (success) {
					fout.flush();
					fout.close();
				} else {
					System.out.println(remotePath + names[i].getName() + " :" + ftpClient.getReplyString());
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			disconnectFtp(ftpClient);

		}
	}

	/**
	 * @author nbdour
	 * @param pathTravail
	 * @param pathRemote
	 * @param fileName
	 */
	public static void copyFtpFilesImgEffet(String pathTravail, String pathRemote, String fileName) {
		FTPClient ftpClient = Util.connectToFtp();
		try {

			File localFile = new File(pathTravail + File.separatorChar + fileName);
			FileOutputStream fout = new FileOutputStream(localFile);
			// ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			boolean success = ftpClient.retrieveFile(pathRemote + File.separatorChar + fileName, fout);
			if (success) {
				fout.flush();
				fout.close();
			} else {
				System.out.println(fileName + " :" + ftpClient.getReplyString());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			disconnectFtp(ftpClient);
		}

	}

	/**
	 * @author nbdour
	 * @param ageBct
	 * @param dateComptable
	 * @param codVals
	 * @param localPathTravail
	 * @param remotePath
	 */
	public static void copyFtpFilesImg(FTPClient ftpclient, String ageBct, Date dateComptable, String[] codVals,
			String localPathTravail, String remotePath) {
		copyFtpFiles(ftpclient, ageBct, dateComptable, codVals, localPathTravail, remotePath, true);

	}

	/**
	 * @author nbdour
	 * @param ageBct
	 * @param dateComptable
	 * @param codVals
	 * @param localPathTravail
	 * @param remotePath
	 */
	public static void copyFtpFilesData(FTPClient ftpclient, String ageBct, Date dateComptable, String[] codVals,
			String localPathTravail, String remotePath) {
		copyFtpFiles(ftpclient, ageBct, dateComptable, codVals, localPathTravail, remotePath, false);

	}

	/**
	 * @author nbdour
	 */
	public static void initDirectoriesTeleComp() {

		System.out.println("**************Init Directory****************");
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String dateSys = formatter.format(new Date());
		String parentPath = File.separatorChar + Configuration.getParentPath() + File.separatorChar;// Configuration.getParentPath();
		String telecomp = null;
		String recepPathCheque = null;
		String emiPathCheque = null;

		String valuesTelecomp[] = new String[]{ "cheque", "effet", "virement", "prelevement" };

		try {
			for (String value : valuesTelecomp) {

				telecomp = createDirectory(parentPath + "telecompensation");
				recepPathCheque = createDirectory(telecomp + File.separatorChar + "reçu");
				emiPathCheque = createDirectory(telecomp + File.separatorChar + "emis");
				recepPathCheque = createDirectory(recepPathCheque + File.separatorChar + value);
				emiPathCheque = createDirectory(emiPathCheque + File.separatorChar + value);

				List<String> listBctAges = SuivFileTrt.getAllBctAge();
				String tempDir = "";
				for (String agBct : listBctAges) {
					tempDir = createDirectory(recepPathCheque + File.separatorChar + "agence" + agBct);
					tempDir = createDirectory(tempDir + File.separatorChar + dateSys);
					createDirectory(tempDir + File.separatorChar + "traite");
					tempDir = createDirectory(tempDir + File.separatorChar + "travail");
					createDirectory(tempDir + File.separatorChar + "Images");

					tempDir = createDirectory(emiPathCheque + File.separatorChar + "agence" + agBct);
					tempDir = createDirectory(tempDir + File.separatorChar + dateSys);
					createDirectory(tempDir + File.separatorChar + "traite");
					createDirectory(tempDir + File.separatorChar + "travail");

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author nbdour
	 * @param bctAge
	 * @return
	 */
	public static FTPFileFilter filter(final String bctAge) {

		return new FTPFileFilter() {

			public boolean accept(FTPFile ftpFile) {

				return (ftpFile.isFile() && ftpFile.getName().substring(7).startsWith("03" + bctAge) && ftpFile
						.getName().endsWith(".JPG"));

			}
		};
	}

	public static void deleteFile(String SourceFile) {
		try {

			File file = new File(SourceFile);

			if (file.delete()) {
				;// logger.info(file.getName() + " is deleted!");
			} else {
				logger.info("Delete operation is failed.");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public static boolean sendFileFTP(String localFile, String remoteFile) {
		FTPClient ftpClient = new FTPClient();
		String user = null;
		String pass = null;
		String server = null;

		try {
			user = Configuration.getUserNameSend();
			pass = Configuration.getUserPasswordSend();
			server = Configuration.getServerPathSend();
			ftpClient.connect(server);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileInputStream fis = null;

		try {

			fis = new FileInputStream(localFile);

			ftpClient.storeFile(remoteFile, fis);

			fis.close();
			ftpClient.logout();
			logger.info("File #1 has been uploaded successfully.");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @author nbdour
	 * @param bctageEm
	 * @param bctAgeRecp
	 * @param datComp
	 * @param codValChq
	 * @param fileName
	 * @param init
	 * @return
	 */
	public static File getFilesFtpIntraImg(String bctageEm, String bctAgeRecp, Date datComp, String codValChq,
			String fileName, boolean init) {
		final SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyyyy");

		String pathChequeTravail =
				File.separatorChar + Configuration.getParentPath() + File.separatorChar
						+ Configuration.getLocalPathCheque() + File.separatorChar + "reçu" + File.separatorChar
						+ "cheque" + File.separatorChar + "agence" + bctAgeRecp + File.separatorChar
						+ formatDate.format(new Date()) + File.separatorChar + "travail" + File.separatorChar
						+ "Images" + File.separatorChar;
		FTPClient ftpClient = connectToFtp();
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		File localFile = null;

		try {

			String dat = formatter.format(datComp);
			String remotePath =
					"agence" + bctageEm + File.separatorChar + "Emis" + File.separatorChar + dat + File.separatorChar
							+ codValChq + File.separatorChar + "Defalc" + File.separatorChar + "Images"
							+ File.separatorChar;
			localFile = new File(pathChequeTravail + fileName);
			FileOutputStream fout = new FileOutputStream(localFile);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			boolean success = ftpClient.retrieveFile(remotePath + fileName, fout);
			if (success) {
				fout.flush();
				fout.close();
			} else { // si l'image n'est trouve dans la dossier (datecomptable), on essaie de chercher dans le dossier
					 // (date -1 : comptable)
				if (init) { // only one time (recur)
					Date datComp_1 = CalanderHandler.getDateOuvrableBeforeNDays(datComp, -1);
					getFilesFtpIntraImg(bctageEm, bctAgeRecp, datComp_1, codValChq, fileName, false);
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			disconnectFtp(ftpClient);
		}

		return localFile;

	}

	/**
	 * @author nbdour
	 * @param codBct
	 * @param dateComp
	 * @param remotePath
	 * @param codVal
	 * @param img
	 * @return
	 * @throws IOException
	 */
	public static FTPFile[] getFilesFtpLC(FTPClient ftpClient, String codBct, String remotePath) throws IOException {

		if (ftpClient == null || !ftpClient.isConnected())
			ftpClient = connectToFtp();

		FTPFile[] names;

		// try {
		names = ftpClient.listFiles(remotePath);
		logger.info("FTP FILES :" + names.length);
		System.out.println("Nombre de fichiers dans in  : " + names.length);

		return names;
		// } catch (IOException ex) {
		// ex.printStackTrace();
		// }
		// return null;
	}

	/**
	 * @author nbdour
	 * @return
	 */
	public static FTPClient connectToFtpLc() {
		FTPClient ftpClient = new FTPClient();
		String user = null;
		String pass = null;
		String server = null;

		try {
			{
				user = Configuration.getUserNameLc();
				pass = Configuration.getUserPasswordLc();
				server = Configuration.getServerPath();
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

	public static boolean sendFileFTPCICS(String localFile, String remoteFile) {
		FTPClient ftpClient = new FTPClient();
		String user = null;
		String pass = null;
		String server = null;

		try {
			user = Configuration.getUserNameSendCICS();
			pass = Configuration.getUserPasswordSendCICS();
			server = Configuration.getServerPathSendCICS();
			ftpClient.connect(server);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			//ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileInputStream fis = null;

		try {

			fis = new FileInputStream(localFile);

			ftpClient.storeFile(remoteFile, fis);

			fis.close();
			ftpClient.logout();
			logger.info("File #1 has been uploaded successfully.");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}