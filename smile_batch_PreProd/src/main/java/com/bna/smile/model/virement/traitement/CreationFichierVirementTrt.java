package com.bna.smile.model.virement.traitement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecommun.traitement.GetRibTrt;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class CreationFichierVirementTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();

	public CreationFichierVirementTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		this.setCroFlag(false);
		GlobalVirement globalVirement = new GlobalVirement();
		DetailVirement detailVirement = new DetailVirement();
		Date dateComptable = virementVo.getDateComptableAgence();
		String fileName = "";
		String entete = "";
		String detailFichier = "";
		SimpleDateFormat formaterDate = new SimpleDateFormat("yyyyMMdd");
		File file = virementVo.getFile();

		// ********************************//

		// * Données strcuture Fichier *//

		/* globals */

		String sens = "1";
		String codeValeur = "10";
		String natureRemettant = "1";
		String codeRemettant = "03";
		String codeCentreReg = "   ";
		String dateOperation = formaterDate.format(dateComptable);
		String numeroLot = "";
		String codeEnregistrement = "11";
		String codeDevise = Constants.COD_DEV_DINAR.toString();
		String rang = "00";
		String montantVirementGlobal = "";
		String nbreToatalVirement = "";
		String zoneLibreGlobal = "";
		String referenceIS = "";
		/* details */

		String mntVirDetail = "";
		String numVirDetail = "";
		String ribDO = "";
		String nomPre_RsDO = "";
		String codeInstDest = "";
		String ribBenif = "";
		String nomPre_RsBenif = "";
		String refDossier = "00000000000000000000";
		String codEnregCompl = "0";
		String nbreEnregCompl = "00";
		String motifOperation = "";
		String dateCompensation = "00000000";
		String motifRejet = "00000000";
		String situationDO = "0"; // 0 ==> Resident 1==> Non Resident
		String typeCompteDO = "1"; // 1 ==> Compte Dinars 2==> Compte Dinars convertible 3==> Compte Devises
		String natureCompteDO = " "; // 0 ==> Compte Professionnel 1==> Compte Spécial
		String existanceDossierChange = " "; // ==> 1 : oui
		String zoneLibreDetail = " ";

		try {
			fileName = file.getName();
			globalVirement = virementVo.getGlobalVirement();
			detailVirement = virementVo.getDetailVirement();

			if (virementVo.getNumeroLot() != null) {
				numeroLot = lpadS(virementVo.getNumeroLot(), "0", 4);

			} else {
				numeroLot = "0001";
			}

			/* ***** Creation entête Fichier ****** */

			montantVirementGlobal = lpadS(virementVo.getMntVirementADT() + "", "0", 15);

			// ******** Find ligne in file || Ecriture sinon **************//
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = "";
			boolean existeGlobal = false;
			boolean existeDetail = false;

			nbreToatalVirement = lpadS(virementVo.getNbrVirementADT() + "", "0", 10);

			zoneLibreGlobal = rpadS(zoneLibreGlobal, " ", 80);
			// ************** Reference Inter siege *********//
			GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
			String referenceInterSiege =
					generateReferenceInterSiege.getRISWithUpdate(globalVirement.getStructure().getCodStrcStrc(),
							dateComptable);
			long nbrLot = 0;
			try {
				if (virementVo.getNumeroLot() != null && Long.valueOf(virementVo.getNumeroLot()).longValue() <= 999) {

					nbrLot = Long.valueOf(virementVo.getNumeroLot()).longValue();

				} else {
					nbrLot = 1;
				}
			} catch (Exception e) {
				nbrLot = 1;
			}

			// referenceIS = referenceInterSiege.substring(0, 6) + "1V" + nbrLot;
			referenceIS = referenceInterSiege.substring(0, 6) + lpadS(nbrLot + "", "0", 3);
			entete =
					sens + codeValeur + natureRemettant + codeRemettant + codeCentreReg + dateOperation + numeroLot
							+ codeEnregistrement + codeDevise + rang + montantVirementGlobal + nbreToatalVirement
							+ referenceIS + zoneLibreGlobal;

			if (entete.length() >= 62) {

				while ((strLine = br.readLine()) != null) {

					if (strLine.equals(entete)) {
						existeGlobal = true;
					}
				}
				if (existeGlobal == false) {
					writeToFile(file, entete);
				}

			}

			mntVirDetail = lpadS(detailVirement.getMntDetvDetv() + "", "0", 15);
			numVirDetail = lpadS(virementVo.getNumeroLigneADT() + "", "0", 7);
			PrimitiveVO primitiveVO = new PrimitiveVO();
			GetRibTrt getRibTrt = new GetRibTrt();
			primitiveVO = (PrimitiveVO) getRibTrt.exec(globalVirement.getContratCpt());
			ribDO = primitiveVO.getVString();

			nomPre_RsDO = globalVirement.getContratCpt().getNomIntiCcpt();
			nomPre_RsDO = UtilCtr.corrigerChaineCaractere(nomPre_RsDO);
			nomPre_RsDO = rpadS(nomPre_RsDO, " ", 30);

			if (nomPre_RsDO.length() > 30) {

				nomPre_RsDO = nomPre_RsDO.substring(0, 30);

			}

			ribBenif = detailVirement.getRibBenDetv();
			codeInstDest = ribBenif.substring(0, 2);

			nomPre_RsBenif = detailVirement.getNomBenDetv();
			nomPre_RsBenif = UtilCtr.corrigerChaineCaractere(nomPre_RsBenif);
			nomPre_RsBenif = rpadS(nomPre_RsBenif, " ", 30);

			if (nomPre_RsBenif.length() > 30) {

				nomPre_RsBenif = nomPre_RsBenif.substring(0, 30);
			}

			motifOperation = detailVirement.getMotiDetvDetv();
			motifOperation = UtilCtr.corrigerChaineCaractere(motifOperation);

			motifOperation = rpadS(motifOperation, " ", 45);

			if (motifOperation.length() > 45) {
				motifOperation = motifOperation.substring(0, 45);
			}
			codeEnregistrement = "21";
			// zoneLibreDetail = rpadS(zoneLibreDetail, " ", 37);

			boolean trouveDC = false;
			boolean trouveDEV = false;
			Long codeProduitCompteDO = globalVirement.getContratCpt().getContratCptId().getCodPrdPrd();

			for (int i = 0; i < Constants.listCompteEnDinarsConvertibles.length; i++) {
				if (Long.valueOf(codeProduitCompteDO).longValue() == Constants.listCompteEnDinarsConvertibles[i]
						.longValue()) {
					trouveDC = true;
				}
			}

			if (trouveDC == true) {

				typeCompteDO = "2";
				natureCompteDO = "0";
			}

			for (int i = 0; i < Constants.listCompteEnDevises.length; i++) {
				if (Long.valueOf(codeProduitCompteDO).longValue() == Constants.listCompteEnDevises[i].longValue()) {
					trouveDEV = true;
				}
			}

			if (trouveDEV == true) {

				typeCompteDO = "3";
				natureCompteDO = "0";
			}
			
			
			
			detailFichier =
					sens + codeValeur + natureRemettant + codeRemettant + codeCentreReg + dateOperation + numeroLot
							+ codeEnregistrement + codeDevise + rang + mntVirDetail + numVirDetail + ribDO
							+ nomPre_RsDO + codeInstDest + codeCentreReg + ribBenif + nomPre_RsBenif + refDossier
							+ codEnregCompl + nbreEnregCompl + motifOperation + dateCompensation + motifRejet
							+ situationDO + typeCompteDO + natureCompteDO + existanceDossierChange + zoneLibreDetail;

			logger.info(" detailFichier : " + detailFichier.length());
			logger.info(" detailFichier : " + detailFichier);
			if (detailFichier.length() >= 244) {

				while ((strLine = br.readLine()) != null) {

					if (strLine.equals(detailFichier)) {
						existeDetail = true;
					}
				}
				if (existeDetail == false) {
					writeToFile(file, detailFichier);
				}

			}
			detailVirement.setCodFlagDetv(Constants.COD_FLAG_FICHIER_VIREMENT_TRAITER);
			detailVirement.setRefFichDetv(fileName);

			crudService.update(detailVirement);

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationFichierVirementTrt: ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationFichierVirementTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau CreationFichierVirementTrt : ", e);
			virementVo.setMessageValidation("Probléme dans CreationFichierVirementTrt");
			throw new RuntimeException(e);

		}
		return (virementVo);
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