package com.bna.smile.model.prelevement.traitement;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;

import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.bna.smile.model.prelevement.dao.PrelevementDAO;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.bna.smile.model.prelevement.service.PrelevementBatchService;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class InsertionPrelevementsDomiciliationsTrt extends Traitement {

	public InsertionPrelevementsDomiciliationsTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	private SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	// à ne pas laisser en variable global
	ICriteria criteria = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();
	PrelevementBatchService prelevementBatchService = (PrelevementBatchService) context
			.getBean("iPrelevementBatchService");

	public IValueObject perform(IValueObject vo) throws FileNotFoundException {

		PrelevementVo prelevementVo = (PrelevementVo) vo;
		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		Date dateComptableAgence = null;

		try {
			SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyyyy");
			Structure agence = prelevementVo.getStructure();
			dateComptableAgence = prelevementVo.getDateComptable();
			PrelevementDAO prelevementDAO = (PrelevementDAO) context.getBean("prelevementDAO");

			logger.info("************** Debut importation des fichiers et insertion dans la base **********");

			Long codeStrcBCT = Long.valueOf(prelevementDAO.getCodeStructureBCT(agence.getCodStrcStrc()));
			Long codeStrcBNA = agence.getCodStrcStrc();

			logger.info("codetStrcBNA : " + codeStrcBNA + "----> codeAgenceBCT: " + codeStrcBCT);

			// *********** Importation file Prelevement & domiciliation ***********/

			String pathPrelevementTravail =
					File.separatorChar + Configuration.getParentPath() + File.separatorChar
							+ Configuration.getLocalPathCheque() + File.separatorChar + "reçu" + File.separatorChar
							+ "prelevement" + File.separatorChar + "agence" + StrHandler.lpad(codeStrcBCT + "", '0', 3)
							+ File.separatorChar + formatDate.format(new Date()) + File.separatorChar + "travail"
							+ File.separatorChar;

			String pathPrelevementTraitement =
					File.separatorChar + Configuration.getParentPath() + File.separatorChar
							+ Configuration.getLocalPathCheque() + File.separatorChar + "reçu" + File.separatorChar
							+ "prelevement" + File.separatorChar + "agence" + StrHandler.lpad(codeStrcBCT + "", '0', 3)
							+ File.separatorChar + formatDate.format(new Date()) + File.separatorChar + "traite"
							+ File.separatorChar;

			String pathPrelevementFTP =
					File.separatorChar + "AGENCE" + StrHandler.lpad(codeStrcBCT + "", '0', 3) + File.separatorChar
							+ "Out" + File.separatorChar;
			FTPClient FTPClient = Util.connectToFtp();
			Util.copyFtpFilesData(FTPClient, StrHandler.lpad(codeStrcBCT + "", '0', 3), dateComptableAgence,
					new String[]{ "80" }, pathPrelevementTravail, pathPrelevementFTP);

			PrelevementVo prelevementVoDom = new PrelevementVo();
			prelevementVoDom.setDateComptable(dateComptableAgence);
			prelevementVoDom.setCodeStructureBCT(codeStrcBCT);
			prelevementVoDom.setCodeStructure(codeStrcBNA);
			prelevementVoDom.setPathFichier(pathPrelevementTravail);
			prelevementVoDom.setPathFichierTraite(pathPrelevementTraitement);
			prelevementVoDom = (PrelevementVo) prelevementBatchService.saveLotsDomiciliations(prelevementVoDom);

			if (prelevementVoDom.isEtatEnregistrementPrelevement() == true) {
				logger.info("Fichier de domiciliation de l'agence " + codeStrcBNA + " est inséré avec succès");
			} else {
				logger.error(prelevementVoDom.getErreur());
			}

			Util.copyFtpFilesData(FTPClient, StrHandler.lpad(codeStrcBCT + "", '0', 3), dateComptableAgence,
					new String[]{ "20" }, pathPrelevementTravail, pathPrelevementFTP);

			PrelevementVo prelevementVoPrl = new PrelevementVo();
			prelevementVoPrl.setDateComptable(dateComptableAgence);
			prelevementVoPrl.setCodeStructureBCT(codeStrcBCT);
			prelevementVoPrl.setCodeStructure(codeStrcBNA);
			prelevementVoPrl.setPathFichier(pathPrelevementTravail);
			prelevementVoPrl.setPathFichierTraite(pathPrelevementTraitement);
			prelevementVoPrl = (PrelevementVo) prelevementBatchService.saveLotsPrelevements(prelevementVoPrl);

			if (prelevementVoPrl.isEtatEnregistrementPrelevement() == true) {
				logger.info("Fichier de prelevement de l'agence " + codeStrcBNA + " est inséré avec succès");
			} else {
				logger.error(prelevementVoPrl.getErreur());
			}

			logger.info("************** Fin importation des fichiers et insertion dans la base");

			return prelevementVo;

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans InsertionPrelevementsDomiciliationsTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("InsertionPrelevementsDomiciliationsTrt");
			logger.error("Exception : ", e);
			throw new RuntimeException(e);

		}
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

}
