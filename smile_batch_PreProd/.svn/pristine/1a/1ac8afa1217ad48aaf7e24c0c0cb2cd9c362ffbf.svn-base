package com.bna.smile.model.domainecommun.traitement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.AFBDAO;
import com.bna.smile.model.domainecommun.model.AFBMvt;
import com.bna.smile.model.domainecommun.model.AFBVo;
import com.bna.smile.model.domainecommun.model.ListeRIBSocietesAFBView;
import com.bna.smile.model.domainecommun.model.SocietesAFBView;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class CreationFichierAFBMVTTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();

	public CreationFichierAFBMVTTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		AFBVo AFBVo = (AFBVo) vo;
		this.setCroFlag(false);
		// Date dateComptable = AFBVo.getDateComptable();
		Date dateComptable = DateHandler.strToDate("02/01/2015");
		String debut = "";
		String milieu = "";
		String fin = "";
		String messageStastiques = "";
		long nbrTotalMvt = 0;
		SimpleDateFormat formaterDate = new SimpleDateFormat("ddMMyy");
		SimpleDateFormat formaterDateDDMMYYYY = new SimpleDateFormat("ddMMyyyy");
		File file = AFBVo.getFile();
		AFBDAO AFBDAO = (AFBDAO) context.getBean("AFBDAO");
		SocietesAFBView societesAFBView = AFBVo.getSocietesAFBView();
		List<ListeRIBSocietesAFBView> listeRibsAFB = new ArrayList<ListeRIBSocietesAFBView>();
		long compteur = 0;
		long compteurExiste = 0;
		// ********************************//
		try {

			listeRibsAFB = AFBDAO.getListeRibAFBByCriteres(societesAFBView.getNumSoctAFB());

			// ******** Find ligne in file || Ecriture sinon **************//
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = "";
			boolean existeDebut = false;
			boolean existeMilieu = false;
			boolean existeFin = false;

			if (listeRibsAFB != null && listeRibsAFB.size() > 0) {

				for (ListeRIBSocietesAFBView ribSocietesAFBView : listeRibsAFB) {

					ContratCpt contratCpt = new ContratCpt();
					ContratCptId cptId = new ContratCptId();
					cptId.setCodStrcStrc(120L);
					String codprd = "01" + ribSocietesAFBView.getRibSoctAFB().substring(3, 5);
					String numcpt = ribSocietesAFBView.getRibSoctAFB().substring(5, 11);
					cptId.setCodPrdPrd(Long.valueOf(codprd));
					cptId.setNumCcptCcpt(Long.valueOf(numcpt));
					contratCpt.setContratCptId(cptId);

					contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, contratCpt.getContratCptId());
					PrimitiveVO primitiveVO = new PrimitiveVO();
					GetRibTrt getRibTrt = new GetRibTrt();
					primitiveVO = (PrimitiveVO) getRibTrt.exec(contratCpt);
					String rib = primitiveVO.getVString();

					// * Données strcuture Fichier *//

					/* debut */

					String codeEnreg = "01";
					String codeBanque = "03";
					String codeAgenceBCT = rib.substring(2, 5);
					String vide1 = "    ";
					String codeAgenceBNA = rib.substring(5, 8);
					String codeCG = rib.substring(8, 10);
					String codeDevise = "TND";
					String nbrDecimal = "3";
					String vide2 = " ";
					String rib11_20 = rib.substring(10, 20);
					String vide3 = "   ";
					Date dateAncienSolde = CalanderHandler.GetLastWorkingDay(dateComptable);
					String strdateAncienSolde = formaterDate.format(dateAncienSolde);
					String vide4 = "                                                  ";
					String soldeDebut = "";
					String dernierCarcSolde = "";
					String vide5 = "               ";
					String finLigne = "*";

					List<AFBMvt> listMvts = new ArrayList<AFBMvt>();

					listMvts = AFBDAO.getListeMvtsByCriteres(cptId.getCompteClient().replace(" ", ""));

					nbrTotalMvt += listMvts.size();

					if (listMvts != null && listMvts.size() > 0) {

						System.out.println("listMvts.size() : " + listMvts.size());

						Long soldeDepart = 0L;
						Long soldeFinal = 0L;

						soldeDebut = lpadS(Math.abs(soldeDepart) + "", "0", 13);

						if (soldeDepart >= 0) {

							dernierCarcSolde = AFBDAO.getLastChifreSolde(Long.valueOf(soldeDebut.substring(12)), true);
						} else {
							dernierCarcSolde = AFBDAO.getLastChifreSolde(Long.valueOf(soldeDebut.substring(12)), false);
						}

						debut =
								codeEnreg + codeBanque + codeAgenceBCT + vide1 + codeAgenceBNA + codeCG + codeDevise
										+ nbrDecimal + vide2 + rib11_20 + vide3 + strdateAncienSolde + vide4
										+ soldeDebut + dernierCarcSolde + vide5 + finLigne;

						if (debut.length() >= 120) {

							while ((strLine = br.readLine()) != null) {

								if (strLine.equals(debut)) {
									existeDebut = true;
								}
							}
							if (existeDebut == false) {
								writeToFile(file, debut);
							}

						}

						/* Milieu */

						for (AFBMvt afbMvt : listMvts) {

							milieu = "";
							String codeEnregMilieu = "04";
							String codOper4 = "0000";
							String codOper2 = afbMvt.getCODE_OPERATION().trim();
							String dateOper =
									formaterDate.format(formaterDateDDMMYYYY.parse(afbMvt.getDATE_OPERATION()));
							String dateValeur = "";
							String videMilieu1 = "  ";
							String libOper = afbMvt.getLIBELLE();
							String referOper = afbMvt.getREFERENCE();
							String montant = "";
							String dernierCarcMontant = "";

							if (codOper2.equals("33")) {

								if (afbMvt.getLIB() != null) {
									libOper = afbMvt.getLIB();
								} else {
									libOper = afbMvt.getLIBELLE();
								}

							}

							if (libOper.length() > 31) {

								libOper = libOper.substring(0, 31);

							} else {

								libOper = StrHandler.rpad(libOper, ' ', 31);

							}

							if (referOper != null && referOper.length() > 7) {

								if (referOper.substring(0, 3).equalsIgnoreCase("n° ") && referOper.length() >= 10) {

									referOper = referOper.substring(3, 10);
								}
								if (referOper.length() >= 11 && referOper.substring(0, 9).equalsIgnoreCase("Chèque N°")) {

									referOper = referOper.substring(9);

									referOper = StrHandler.lpad(referOper, ' ', 7);
								} else {

									referOper = referOper.substring(0, 7);
								}

							} else if (referOper != null) {

								referOper = StrHandler.lpad(referOper, ' ', 7);

							} else {
								referOper = "       ";
							}

							if (afbMvt.getMVTS_CREDITEURS() != null && afbMvt.getMVTS_CREDITEURS().length() == 15
									&& afbMvt.getMVTS_CREDITEURS().equals("000000000000000") == false) {

								dateValeur = formaterDate.format(formaterDateDDMMYYYY.parse(afbMvt.getDATE_VALEUR()));
								montant = afbMvt.getMVTS_CREDITEURS().substring(2);
								dernierCarcMontant =
										AFBDAO.getLastChifreSolde(Long.valueOf(montant.substring(12)), true);

							} else if (afbMvt.getMVTS_DEBITEURS() != null && afbMvt.getMVTS_CREDITEURS().length() == 15
									&& afbMvt.getMVTS_DEBITEURS().equals("000000000000000") == false) {
								dateValeur = formaterDate.format(formaterDateDDMMYYYY.parse(afbMvt.getDATE_VALEUR()));
								montant = afbMvt.getMVTS_DEBITEURS().substring(2);
								dernierCarcMontant =
										AFBDAO.getLastChifreSolde(Long.valueOf(montant.substring(12)), false);
							}

							milieu =
									codeEnregMilieu + codeBanque + codeAgenceBCT + codOper4 + codeAgenceBNA + codeCG
											+ codeDevise + nbrDecimal + vide2 + rib11_20 + vide2 + codOper2 + dateOper
											+ videMilieu1 + dateValeur + libOper + videMilieu1 + referOper
											+ videMilieu1 + montant + dernierCarcMontant + vide5 + finLigne;

							if (milieu.length() >= 120) {

								existeMilieu = false;

								if (existeMilieu == false) {
									writeToFile(file, milieu);
									compteur++;
								} else {
									System.out.println("milieu : " + milieu);
									compteurExiste++;
								}

							}
						}

						/* FIN */

						String codeEnregFin = "07";
						String strdateNouvelleSolde = "300615";
						String montantSoldeFin = StrHandler.lpad(Math.abs(soldeFinal) + "", '0', 13);
						String dernierCarcSoldeFin = "";

						if (soldeFinal >= 0) {

							dernierCarcSoldeFin =
									AFBDAO.getLastChifreSolde(Long.valueOf(montantSoldeFin.substring(12)), true);
						} else {
							dernierCarcSoldeFin =
									AFBDAO.getLastChifreSolde(Long.valueOf(montantSoldeFin.substring(12)), false);
						}

						fin =
								codeEnregFin + codeBanque + codeAgenceBCT + vide1 + codeAgenceBNA + codeCG + codeDevise
										+ nbrDecimal + vide2 + rib11_20 + vide3 + strdateNouvelleSolde + vide4
										+ montantSoldeFin + dernierCarcSoldeFin + vide5 + finLigne;

						if (fin.length() >= 120) {

							while ((strLine = br.readLine()) != null) {

								if (strLine.equals(fin)) {
									existeFin = true;
								}
							}
							if (existeFin == false) {
								writeToFile(file, fin);
							}

						}

					}
				}

			}
			System.out.println("compteurExiste : " + compteurExiste);
			if (nbrTotalMvt != 0) {
				messageStastiques =
						"Nombre total des mouvements inscrits " + compteur + " pour un total de " + nbrTotalMvt;
			} else {
				messageStastiques = "Pas de mouvements ";
			}

			AFBVo.setEtatEnregistrement(true);
			AFBVo.setMessageValidation(messageStastiques);
		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationFichierAFBMVTTrt: ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationFichierAFBMVTTrt");
			AFBVo.addError(erreur);
			logger.error("Erreur au niveau CreationFichierAFBMVTTrt : ", e);
			AFBVo.setMessageValidation("Probléme dans la génération du fichier pour la société "
					+ AFBVo.getSocietesAFBView().getNomSoctAFB());
			AFBVo.setEtatEnregistrement(false);
			throw new RuntimeException(e);

		}
		return (AFBVo);
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