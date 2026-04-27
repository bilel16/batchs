package com.bna.smile.model.prelevement.traitement;

/**
 * @author Sayeb Hichem
 * @since 19/04/2017
 */
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.MvtPrelevements;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.prelevement.dao.PrelevementDAO;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ExtractionFichierPrelevementTrt extends Traitement {

	Context context = ContextHandler.getContext();

	public ExtractionFichierPrelevementTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		PrelevementVo prelevementVo = (PrelevementVo) vo;
		PrelevementDAO prelevementDAO = (PrelevementDAO) context.getBean("prelevementDAO");
		SimpleDateFormat formaterDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formaterDateFile = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat formaterDateFileMan = new SimpleDateFormat("ddMMyy");
		SimpleDateFormat timeFileExtractFormat = new SimpleDateFormat("HHmmss");
		String timeFileExtract =
				formaterDateFile.format(prelevementVo.getDateComptable()) + "-"
						+ timeFileExtractFormat.format(new Date()) + "-";
		try {

			Structure strc = prelevementDAO.findStructure(prelevementVo.getCodeStructure());
			String structureBct = StrHandler.lpad(strc.getCodBctStrc(), '0', 3);
			String structureBna = StrHandler.lpad("" + strc.getCodStrcStrc(), '0', 3);

			/************* Fichier Rejtes Prelevements **************************/
			long mntGlobRejetsPrelev = 0;
			long nbrGlobRejetsPrelev = 0;

			/*
			 * *** Reference Inter siege ********
			 */
			GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
			String referenceInterSiege =
					generateReferenceInterSiege.getRISWithUpdate(strc.getCodStrcStrc(),
							prelevementVo.getDateComptable());

			String codRefInter = referenceInterSiege.substring(0, 6) + "RPR";
			Date dateComptableFichier = new Date();
			// String fileNameCTX = "";
			String numLotPrelevement = StrHandler.lpad("2", '0', 4);
			String codeDevise = Constants.COD_DEV_DINAR + "";

			// remote path : tresorerie
			String remotePathTresorMan = Configuration.getTresoreriePathManSend();

			File directoryFiles = new File(remotePathTresorMan + File.separatorChar + structureBna);
			if (!directoryFiles.exists())
				directoryFiles.mkdir();

			String fileNamePrelevement =
					remotePathTresorMan + "03" + "-" + structureBct + "-" + Constants.COD_ENREGISTREMENT_PRELEVEMENT
							+ "-" + "22" + "-" + numLotPrelevement + "-"
							+ formaterDateFile.format(prelevementVo.getDateComptable()) + "-"
							+ timeFileExtractFormat.format(new Date()) + "-" + codeDevise + ".ENV";
			File filePrel = null;
			filePrel = new File(fileNamePrelevement);
			logger.info("file : " + filePrel.getAbsolutePath());

			boolean exists = filePrel.exists();

			if (!exists) {
				filePrel.createNewFile();

			}

			GetListeRejetsPrelevementsByStructureTrt getListeRejetsPrelevementsByStructureTrt =
					new GetListeRejetsPrelevementsByStructureTrt();

			prelevementVo = (PrelevementVo) getListeRejetsPrelevementsByStructureTrt.exec(prelevementVo);

			if (prelevementVo.getListeMvtsPrelevements() != null && prelevementVo.getListeMvtsPrelevements().size() > 0) {

				for (MvtPrelevements mvtPrelevements : prelevementVo.getListeMvtsPrelevements()) {

					nbrGlobRejetsPrelev++;
					mntGlobRejetsPrelev += mvtPrelevements.getMntPrlPrl();

				}

				for (MvtPrelevements mvtPrelevements : prelevementVo.getListeMvtsPrelevements()) {

					PrelevementVo prelevementVoCreationFile = new PrelevementVo();
					prelevementVoCreationFile.setFile(filePrel);
					prelevementVoCreationFile.setDateComptable(prelevementVo.getDateComptable());
					prelevementVoCreationFile.setMvtPrelevements(mvtPrelevements);
					prelevementVoCreationFile.setMntGlobalFichier(mntGlobRejetsPrelev);
					prelevementVoCreationFile.setNbrGlobalFichier(nbrGlobRejetsPrelev);
					// prelevementVoCreationFile.setParamAgence(journStrucDomVo.getParamAgence());
					prelevementVoCreationFile.setCodeValeur(Constants.COD_ENREGISTREMENT_PRELEVEMENT);
					prelevementVoCreationFile.setReferenceInterSiege(codRefInter);
					CreationFichierPrelevementDomiciliationTrt creationFichierPrelevementDomiciliationTrt =
							new CreationFichierPrelevementDomiciliationTrt();
					prelevementVoCreationFile =
							(PrelevementVo) creationFichierPrelevementDomiciliationTrt.exec(prelevementVoCreationFile);
					if (prelevementVoCreationFile.getMsgEnregistrement() != null
							&& prelevementVoCreationFile.getMsgEnregistrement().length() > 0) {
						logger.info("Message validation : " + prelevementVoCreationFile.getMsgEnregistrement());
					}

				}

			} else {

				PrelevementVo prelevementVoCreationFile = new PrelevementVo();
				prelevementVoCreationFile.setFile(filePrel);
				prelevementVoCreationFile.setDateComptable(prelevementVo.getDateComptable());
				prelevementVoCreationFile.setMvtPrelevements(null);
				prelevementVoCreationFile.setMntGlobalFichier(0);
				prelevementVoCreationFile.setNbrGlobalFichier(0);
				prelevementVoCreationFile.setCodeValeur(Constants.COD_ENREGISTREMENT_PRELEVEMENT);
				prelevementVoCreationFile.setReferenceInterSiege(codRefInter);
				CreationFichierPrelevementDomiciliationTrt creationFichierPrelevementDomiciliationTrt =
						new CreationFichierPrelevementDomiciliationTrt();
				prelevementVoCreationFile =
						(PrelevementVo) creationFichierPrelevementDomiciliationTrt.exec(prelevementVoCreationFile);
				if (prelevementVoCreationFile.getMsgEnregistrement() != null
						&& prelevementVoCreationFile.getMsgEnregistrement().length() > 0) {
					logger.info("Message validation : " + prelevementVoCreationFile.getMsgEnregistrement());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans ExtractionFichierPrelevementTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("ExtractionFichierTrt");
			prelevementVo.addError(erreur);
			logger.error("Erreur au niveau ExtractionFichierPrelevementTrt : ", e);
			throw new RuntimeException(e);

		}
		return prelevementVo;
	}

	public void genCroText(ValueObject vo) {

	}

}