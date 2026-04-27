package com.bna.smile.model.traitementCompensationRecu.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * @author nbdour
 * 
 */
public class Configuration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static String pathFileCompensation;
	/**
     * 
     */
	private static String pathFileRejet;
	/**
     * 
     */
	private static String pathImagesCheques;
	/**
     * 
     */
	private static String periodeBatch;
	private static String userName;
	private static String userPassword;
	private static String serverPath;

	private static String localPath;
	private static String parentPath;
	private static String pathConcatLc;

	/**
	 * @param key
	 * @return
	 * @throws InvalidPropertiesFormatException
	 * @throws IOException
	 */
	public static String getPath(String key) throws InvalidPropertiesFormatException, IOException {
		String typeValeur;
		Properties props = new Properties();
		InputStream is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
		props.loadFromXML(is);
		typeValeur = props.getProperty(key);
		return typeValeur;
	}

	/**
	 * @return the pathFileRejet
	 */
	public static String getPathFileRejet() {
		try {
			pathFileRejet = getPath("pathRejet");
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pathFileRejet;
	}

	/**
	 * @return the pathFileCompensation
	 */
	public static String getPathFileCompensation() {
		try {
			pathFileCompensation = getPath("pathFileCompensation");
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pathFileCompensation;
	}

	/**
	 * @return the pathImagesCheques
	 */
	public static String getPathImagesCheques() {
		try {
			pathImagesCheques = getPath("pathImagesCheques");
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pathImagesCheques;
	}

	public static String getPeriodeBatch() {
		try {
			periodeBatch = getPath("periodeBatch");
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pathImagesCheques;
	}

	/**
	 * @param key
	 * @return
	 * @throws InvalidPropertiesFormatException
	 * @throws IOException
	 */
	// public static String getPath(String key) throws InvalidPropertiesFormatException,
	// IOException {
	// String typeValeur;
	// Properties props = new Properties();
	// InputStream is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
	// props.loadFromXML(is);
	// typeValeur = props.getProperty(key);
	// return typeValeur;
	// }

	/**
	 * @param key
	 * @return
	 * @throws InvalidPropertiesFormatException
	 * @throws IOException
	 */
	public static String getServerPath() {
		String typeValeur;
		Properties props = new Properties();
		InputStream is;
		try {
			is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
			props.loadFromXML(is);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		typeValeur = props.getProperty("serverPath");
		return typeValeur;
	}

	/**
	 * @param key
	 * @return
	 * @throws InvalidPropertiesFormatException
	 * @throws IOException
	 */
	public static String getUserName() throws InvalidPropertiesFormatException, IOException {
		String typeValeur;
		Properties props = new Properties();
		InputStream is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
		props.loadFromXML(is);
		typeValeur = props.getProperty("userName");
		return typeValeur;
	}

	/**
	 * @param key
	 * @return
	 * @throws InvalidPropertiesFormatException
	 * @throws IOException
	 */
	public static String getUserPassword() throws InvalidPropertiesFormatException, IOException {
		String typeValeur;
		Properties props = new Properties();
		InputStream is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
		props.loadFromXML(is);
		typeValeur = props.getProperty("userPassword");
		return typeValeur;
	}

	public static String getLocalPathLc() {

		String typeValeur;
		Properties props = new Properties();
		InputStream is;
		try {
			is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
			props.loadFromXML(is);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		typeValeur = props.getProperty("localPathLc");
		return typeValeur;
	}

	public static String getLocalPathCheque() {

		String typeValeur;
		Properties props = new Properties();
		InputStream is;
		try {
			is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
			props.loadFromXML(is);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		typeValeur = props.getProperty("localPathCheque");
		return typeValeur;
	}

	public static String getParentPath() {
		String typeValeur;
		Properties props = new Properties();
		InputStream is;
		try {
			is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
			props.loadFromXML(is);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		typeValeur = props.getProperty("parentPath");
		return typeValeur;
	}

	// **************** SEND FILE FTP ******************//

	public static String getUserNameSend() throws InvalidPropertiesFormatException, IOException {
		String typeValeur;
		Properties props = new Properties();
		InputStream is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
		props.loadFromXML(is);
		typeValeur = props.getProperty("userNameSend");
		return typeValeur;
	}

	public static String getUserPasswordSend() throws InvalidPropertiesFormatException, IOException {
		String typeValeur;
		Properties props = new Properties();
		InputStream is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
		props.loadFromXML(is);
		typeValeur = props.getProperty("userPasswordSend");
		return typeValeur;
	}

	public static String getLocalPathSend() {

		String typeValeur;
		Properties props = new Properties();
		InputStream is;
		try {
			is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
			props.loadFromXML(is);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		typeValeur = props.getProperty("localPathSend");
		return typeValeur;
	}

	public static String getServerPathSend() throws InvalidPropertiesFormatException, IOException {
		String typeValeur;
		Properties props = new Properties();
		InputStream is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
		props.loadFromXML(is);
		typeValeur = props.getProperty("serverPathSend");
		return typeValeur;
	}

	public static void setPathConcatLc(String pathConcatLc) {
		Configuration.pathConcatLc = pathConcatLc;
	}

	public static String getPathConcatLc() {

		String typeValeur;
		Properties props = new Properties();
		InputStream is;
		try {
			is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
			props.loadFromXML(is);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		typeValeur = props.getProperty("pathConcatLc");
		return typeValeur;
	}

	/**
	 * @param key
	 * @return
	 * @throws InvalidPropertiesFormatException
	 * @throws IOException
	 */
	public static String getUserNameLc() throws InvalidPropertiesFormatException, IOException {
		String typeValeur;
		Properties props = new Properties();
		InputStream is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
		props.loadFromXML(is);
		typeValeur = props.getProperty("userNameLc");
		return typeValeur;
	}

	/**
	 * @param key
	 * @return
	 * @throws InvalidPropertiesFormatException
	 * @throws IOException
	 */
	public static String getUserPasswordLc() throws InvalidPropertiesFormatException, IOException {
		String typeValeur;
		Properties props = new Properties();
		InputStream is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
		props.loadFromXML(is);
		typeValeur = props.getProperty("userPasswordLc");
		return typeValeur;
	}

	public static String getTresoreriePathSend() {

		String typeValeur;
		Properties props = new Properties();
		InputStream is;
		try {
			is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
			props.loadFromXML(is);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		typeValeur = props.getProperty("tresoreriePathSend");
		return typeValeur;
	}

	public static String getTresoreriePathManSend() {

		String typeValeur;
		Properties props = new Properties();
		InputStream is;
		try {
			is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
			props.loadFromXML(is);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		typeValeur = props.getProperty("tresoreriePathManSend");
		return typeValeur;
	}

	public static String getPfcPathSend() {

		String typeValeur;
		Properties props = new Properties();
		InputStream is;
		try {
			is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
			props.loadFromXML(is);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		typeValeur = props.getProperty("pfcPathSend");
		return typeValeur;
	}

	/**********************************************/
	public static String getUserNameSendCICS() throws InvalidPropertiesFormatException, IOException {
		String typeValeur;
		Properties props = new Properties();
		InputStream is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
		props.loadFromXML(is);
		typeValeur = props.getProperty("userNameSendCICS");
		return typeValeur;
	}

	public static String getUserPasswordSendCICS() throws InvalidPropertiesFormatException, IOException {
		String typeValeur;
		Properties props = new Properties();
		InputStream is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
		props.loadFromXML(is);
		typeValeur = props.getProperty("userPasswordSendCICS");
		return typeValeur;
	}

	public static String getServerPathSendCICS() throws InvalidPropertiesFormatException, IOException {
		String typeValeur;
		Properties props = new Properties();
		InputStream is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
		props.loadFromXML(is);
		typeValeur = props.getProperty("serverPathSendCICS");
		return typeValeur;
	}

	public static String getLocalPathSendCICS() {

		String typeValeur;
		Properties props = new Properties();
		InputStream is;
		try {
			is = new FileInputStream("./config/configuration-insertion-telecompensation.xml");
			props.loadFromXML(is);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		typeValeur = props.getProperty("localPathSendCICS");
		return typeValeur;
	}

}
