package com.bna.smile.model.domainetraitementfichier;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Parametres implements Serializable {

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * Paramètres utiles pour le traitement du Batch *
	 * 
	 * @author JAOUALI Yossri *
	 * @since 28/10/2010 * * * * * * * * * * * * * * * * * * * * * * * *
	 **/

	private long compteur;
	private long periode;
	private String cheminRepertoireSource;
	private String cheminRepertoireDestination;
	private String cheminRepertoireDestinationLocale;
	private String cheminRepertoireTraitement;
	private String cheminRepertoireTravail;
	private String cheminRepertoireTest;
	private String cheminRepertoireDestination2;

	/**
	 * Methode permettant de retenir la valeur de compteur à partir d'un fichier xml
	 * 
	 * @param
	 * @return long
	 */
	public long getCompteur() {
		try {
			compteur = Long.parseLong(getTypeValeur("compteur batch traitement fichier"));
		} catch (InvalidPropertiesFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return compteur;
	}

	/**
	 * Methode permettant de retenir la valeur de la période de traitement du batch à partir d'un fichier xml
	 * 
	 * @param
	 * @return long
	 */
	public long getPeriode() {
		try {
			periode = Long.parseLong(getTypeValeur("période batch traitement fichier"));
		} catch (InvalidPropertiesFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return periode;
	}

	/**
	 * Methode permettant de retenir le chemin de la répertoire source à partir d'un fichier xml
	 * 
	 * @param
	 * @return String
	 */
	public String getCheminRepertoireSource() {
		try {
			cheminRepertoireSource = getTypeValeur("Chemin répertoire source");
		} catch (InvalidPropertiesFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return cheminRepertoireSource;
	}

	/**
	 * Methode permettant de retenir le chemin de la répertoire ADT à partir d'un fichier xml
	 * 
	 * @param
	 * @return String
	 */

	public String getCheminRepertoireDestination() {
		try {
			cheminRepertoireDestination = getTypeValeur("Chemin répertoire destination");
		} catch (InvalidPropertiesFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return cheminRepertoireDestination;
	}

	/**
	 * Methode permettant de retenir le chemin de la répertoire destination locale à partir d'un fichier xml
	 * 
	 * @param
	 * @return String
	 */
	public String getCheminRepertoireDestinationLocale() {
		try {
			cheminRepertoireDestinationLocale = getTypeValeur("Chemin répertoire destination locale");
		} catch (InvalidPropertiesFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return cheminRepertoireDestinationLocale;
	}

	/**
	 * Methode permettant de retenir le chemin de la répertoire traite à partir d'un fichier xml
	 * 
	 * @param
	 * @return String
	 */

	public String getCheminRepertoireTraitement() {
		try {
			cheminRepertoireTraitement = getTypeValeur("Chemin répertoire traitement");
		} catch (InvalidPropertiesFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return cheminRepertoireTraitement;
	}

	/**
	 * Methode permettant de retenir le chemin de la répertoire travail à partir d'un fichier xml
	 * 
	 * @param
	 * @return String
	 */
	public String getCheminRepertoireTravail() {

		try {
			cheminRepertoireTravail = getTypeValeur("Chemin répertoire travail");
		} catch (InvalidPropertiesFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return cheminRepertoireTravail;

	}

	/**
	 * Methode permettant de retenir le chemin de la répertoire du test à partir d'un fichier xml
	 * 
	 * @param
	 * @return String
	 */
	public String getCheminRepertoireTest() {

		try {
			cheminRepertoireTest = getTypeValeur("Chemin répertoire test");
		} catch (InvalidPropertiesFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return cheminRepertoireTest;

	}

	/**
	 * Methode permettant de retenir le chemin du répertoire des rejets LCN et LCR à partir d'un fichier xml
	 * 
	 * @param
	 * @return String
	 */
	public String getCheminRepertoireDestination2() {

		try {
			cheminRepertoireDestination2 = getTypeValeur("Chemin répertoire destination2");
		} catch (InvalidPropertiesFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return cheminRepertoireDestination2;

	}

	/**
	 * Methode permettant de retenir une valeur localisé dans un fichier xml à partir de son clé
	 * 
	 * @param key
	 *            :String
	 * @return String
	 */
	public String getTypeValeur(String key) throws InvalidPropertiesFormatException, IOException {
		String typeValeur;
		Properties props = new Properties();
		InputStream is = new FileInputStream("./config/configuration-telecompensation.xml");
		props.loadFromXML(is);
		typeValeur = props.getProperty(key);
		return typeValeur;
	}

}
