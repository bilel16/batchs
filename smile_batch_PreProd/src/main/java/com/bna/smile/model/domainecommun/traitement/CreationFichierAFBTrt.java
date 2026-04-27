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
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.AFBDAO;
import com.bna.smile.model.domainecommun.model.AFBView;
import com.bna.smile.model.domainecommun.model.AFBVo;
import com.bna.smile.model.domainecommun.model.ListeRIBSocietesAFBView;
import com.bna.smile.model.domainecommun.model.SocietesAFBView;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class CreationFichierAFBTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();

	public CreationFichierAFBTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		AFBVo AFBVo = (AFBVo) vo;
		this.setCroFlag(false);
		// Date dateComptable = AFBVo.getDateComptable();
		Date dateDebut = AFBVo.getDateDebut();
		Date dateFin = AFBVo.getDateFin();
		Date dateFinSolde = null;
		String debut = "";
		String milieu = "";
		String fin = "";
		String messageStastiques = "";
		long nbrTotalMvt = 0;
		SimpleDateFormat formaterDate = new SimpleDateFormat("ddMMyy");
		File file = AFBVo.getFile();
		AFBDAO AFBDAO = (AFBDAO) context.getBean("AFBDAO");
		SocietesAFBView societesAFBView = AFBVo.getSocietesAFBView();
		List<ListeRIBSocietesAFBView> listeRibsAFB = new ArrayList<ListeRIBSocietesAFBView>();
		long compteur = 0;
		long compteurExiste = 0;
		// ********************************//
		try {

			if (dateDebut.equals(dateFin)) {
				dateFinSolde = dateFin;
			} else {

				boolean etatJourfin = CalanderHandler.isJourFerier(dateFin);
				if (etatJourfin) {
					dateFinSolde = CalanderHandler.GetLastWorkingDay(dateFin);
					;
				} else {
					dateFinSolde = dateFin;
				}

			}

			dateFin = CalanderHandler.GetNextWorkingDay(dateFin);

			/***********************************************/
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

					String codStrc = ribSocietesAFBView.getRibSoctAFB().substring(5, 8);
					String codprd = ribSocietesAFBView.getRibSoctAFB().substring(8, 12);
					String numcpt = ribSocietesAFBView.getRibSoctAFB().substring(12, 18);
					cptId.setCodStrcStrc(Long.valueOf(codStrc));
					cptId.setCodPrdPrd(Long.valueOf(codprd));
					cptId.setNumCcptCcpt(Long.valueOf(numcpt));
					contratCpt.setContratCptId(cptId);

					contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, contratCpt.getContratCptId());

					if (contratCpt != null && contratCpt.getClient() != null && contratCpt.getNomIntiCcpt() != null) {

						String rib = ribSocietesAFBView.getRibSoctAFB();

						// * Données strcuture Fichier *//

						/* debut */

						String codeEnreg = "01";
						String codeBanque = "03";
						String codeAgenceBCT = rib.substring(2, 5);
						String vide1 = "    ";
						String codeAgenceBNA = rib.substring(5, 8);
						String codeCG = rib.substring(8, 10);

						String codeDevise = "   ";
						String nbrDecimal = " ";

						String vide2 = " ";
						String rib11_20 = rib.substring(10, 20);
						String vide3 = "   ";
						Date dateAncienSolde = CalanderHandler.GetLastWorkingDay(dateDebut);
						String strdateAncienSolde = formaterDate.format(dateAncienSolde);
						String vide4 = "                                                  ";
						String soldeDebut = "";
						String dernierCarcSolde = "";
						String vide5 = "               ";
						String finLigne = "*";

						List<AFBView> listMvts = new ArrayList<AFBView>();

						// listMvts =
						// AFBDAO.getListeOperationsMoyenPayByCriteres(cptId.getCodStrcStrc(), cptId.getCodPrdPrd(),
						// cptId.getNumCcptCcpt(), dateComptable);

						if (contratCpt != null && contratCpt.getDevise() != null) {

							if (contratCpt.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {

								codeDevise = "TND";
								nbrDecimal = "3";
								listMvts =
										AFBDAO.getListeOperationsMoyenPayByPeriode(cptId.getCodStrcStrc(),
												cptId.getCodPrdPrd(), cptId.getNumCcptCcpt(), dateDebut, dateFin);
							} else {

								codeDevise = contratCpt.getDevise().getLibSiglDev().toUpperCase();
								nbrDecimal = contratCpt.getDevise().getNbrDecDev() + "";
								listMvts =
										AFBDAO.getListeOperationsMoyenPayByPeriodeDevise(cptId.getCodStrcStrc(),
												cptId.getCodPrdPrd(), cptId.getNumCcptCcpt(), dateDebut, dateFin);
							}
						}
						nbrTotalMvt += listMvts.size();

						if (listMvts != null && listMvts.size() > 0) {

							Long soldeDepart = listMvts.get(0).getSoldeDeprt();
							Long soldeFinal = listMvts.get(listMvts.size() - 1).getSoldeApresOMP();

							soldeDebut = lpadS(Math.abs(soldeDepart) + "", "0", 13);

							if (soldeDepart >= 0) {

								dernierCarcSolde =
										AFBDAO.getLastChifreSolde(Long.valueOf(soldeDebut.substring(12)), true);
							} else {
								dernierCarcSolde =
										AFBDAO.getLastChifreSolde(Long.valueOf(soldeDebut.substring(12)), false);
							}
							soldeDebut = "0" + soldeDebut.substring(0, 12);

							debut =
									codeEnreg + codeBanque + codeAgenceBCT + vide1 + codeAgenceBNA + codeCG
											+ codeDevise + nbrDecimal + vide2 + rib11_20 + vide3 + strdateAncienSolde
											+ vide4 + soldeDebut + dernierCarcSolde + vide5 + finLigne;

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

							for (AFBView afbView : listMvts) {

								milieu = "";
								String codeEnregMilieu = "04";
								String codOper4 = afbView.getCodOperation4();
								String codOper2 = afbView.getCodOperation2();
								String dateOper = afbView.getDateOperation();
								String dateValeur = "";
								String videMilieu1 = "  ";
								String libOper = afbView.getLibOperation();
								String referOper = afbView.getRefOperation();
								String montant = "";
								String dernierCarcMontant = "";

								if (codOper4.equals("2018")) {

									libOper = afbView.getRefOperation();

								} else if (codOper4.equals("0822")) {

									libOper = afbView.getLibOperation() + " " + afbView.getDonneurDordre();

								} else if (codOper4.equals("2122") || codOper4.equals("2121")) {

									libOper = afbView.getLibOperation() + " " + afbView.getRefOperation();

								} else {
									
									libOper = afbView.getLibOperation() + " " + afbView.getRefOperation();
									
								}

								if (libOper.length() > 31) {

									libOper = libOper.substring(0, 31);

								} else {

									libOper = StrHandler.rpad(libOper, ' ', 31);

								}

								if (codOper4.equals("2018") == false) {

									if (referOper != null && referOper.length() > 7) {

										if (referOper.substring(0, 3).equalsIgnoreCase("n° ")
												&& referOper.length() >= 10) {

											referOper = referOper.substring(3, 10);
										}
										if (referOper.length() >= 11
												&& referOper.substring(0, 9).equalsIgnoreCase("Chèque N°")) {

											referOper = referOper.substring(9);

										} else {

											/********* Cas des Virements *****/

											referOper = referOper.substring(referOper.length() - 7, referOper.length());

											if (referOper.length() > 7) {
												referOper = referOper.substring(0, 7);

											}
										}

									} else if (referOper != null) {

										referOper = StrHandler.lpad(referOper, ' ', 7);

									} else {
										int lengthOmp = afbView.getNumOperOmp().length();
										referOper = afbView.getNumOperOmp().substring(lengthOmp - 7, lengthOmp);

									}
								} else {

									if (afbView.getLibMotfOmp() != null && afbView.getLibMotfOmp().length() > 7) {
										referOper = afbView.getLibMotfOmp().substring(0, 7);

									} else {
										referOper = "       ";
									}
								}

								if (afbView.getMontantOperation() != null
										&& afbView.getMontantOperation().length() == 13) {
									dateValeur = afbView.getDateValeur();
									montant = afbView.getMontantOperation();

									if (afbView.getCodSensOmp().equals("C")) {

										dernierCarcMontant =
												AFBDAO.getLastChifreSolde(Long.valueOf(montant.substring(12)), true);
									} else {
										dernierCarcMontant =
												AFBDAO.getLastChifreSolde(Long.valueOf(montant.substring(12)), false);
									}

									montant = "0" + montant.substring(0, 12);
									referOper = UtilCtr.corrigerChaineCaractere(referOper);

									libOper = UtilCtr.corrigerChaineCaractere(libOper);
									milieu =
											codeEnregMilieu + codeBanque + codeAgenceBCT + codOper4 + codeAgenceBNA
													+ codeCG + codeDevise + nbrDecimal + vide2 + rib11_20 + vide2
													+ codOper2 + dateOper + videMilieu1 + dateValeur + libOper
													+ videMilieu1 + referOper + videMilieu1 + montant
													+ dernierCarcMontant + vide5 + finLigne;

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

								if (afbView.getMontantCommission() != null
										&& afbView.getMontantCommission().longValue() != 0) {
									if (afbView.getDatValDomp() != null && afbView.getDatValDomp().length() >= 5) {
										dateValeur = afbView.getDatValDomp();
									} else {
										dateValeur = afbView.getDateValeur();
									}
									montant = StrHandler.lpad(afbView.getMontantCommission() + "", '0', 13);
									libOper = StrHandler.rpad("COMMISSION", ' ', 31);
									dernierCarcMontant =
											AFBDAO.getLastChifreSolde(Long.valueOf(montant.substring(12)), false);

									montant = "0" + montant.substring(0, 12);

									milieu =
											codeEnregMilieu + codeBanque + codeAgenceBCT + codOper4 + codeAgenceBNA
													+ codeCG + codeDevise + nbrDecimal + vide2 + rib11_20 + vide2
													+ codOper2 + dateOper + videMilieu1 + dateValeur + libOper
													+ videMilieu1 + referOper + videMilieu1 + montant
													+ dernierCarcMontant + vide5 + finLigne;

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
								if (afbView.getMontantTVA() != null && afbView.getMontantTVA().longValue() != 0) {

									if (afbView.getDatValDomp() != null && afbView.getDatValDomp().length() >= 5) {
										dateValeur = afbView.getDatValDomp();
									} else {
										dateValeur = afbView.getDateValeur();
									}
									montant = StrHandler.lpad(afbView.getMontantTVA() + "", '0', 13);
									libOper = StrHandler.rpad("TVA", ' ', 31);
									dernierCarcMontant =
											AFBDAO.getLastChifreSolde(Long.valueOf(montant.substring(12)), false);

									montant = "0" + montant.substring(0, 12);
									milieu =
											codeEnregMilieu + codeBanque + codeAgenceBCT + codOper4 + codeAgenceBNA
													+ codeCG + codeDevise + nbrDecimal + vide2 + rib11_20 + vide2
													+ codOper2 + dateOper + videMilieu1 + dateValeur + libOper
													+ videMilieu1 + referOper + videMilieu1 + montant
													+ dernierCarcMontant + vide5 + finLigne;

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

							}

							/* FIN */

							String codeEnregFin = "07";
							String strdateNouvelleSolde = formaterDate.format(dateFinSolde);
							// String strdateNouvelleSolde = "311215";
							String montantSoldeFin = StrHandler.lpad(Math.abs(soldeFinal) + "", '0', 13);
							String dernierCarcSoldeFin = "";

							if (soldeFinal >= 0) {

								dernierCarcSoldeFin =
										AFBDAO.getLastChifreSolde(Long.valueOf(montantSoldeFin.substring(12)), true);
							} else {
								dernierCarcSoldeFin =
										AFBDAO.getLastChifreSolde(Long.valueOf(montantSoldeFin.substring(12)), false);
							}

							montantSoldeFin = "0" + montantSoldeFin.substring(0, 12);
							fin =
									codeEnregFin + codeBanque + codeAgenceBCT + vide1 + codeAgenceBNA + codeCG
											+ codeDevise + nbrDecimal + vide2 + rib11_20 + vide3 + strdateNouvelleSolde
											+ vide4 + montantSoldeFin + dernierCarcSoldeFin + vide5 + finLigne;

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
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationFichierAFBTrt: ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationFichierAFBTrt");
			AFBVo.addError(erreur);
			logger.error("Erreur au niveau CreationFichierAFBTrt : ", e);
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