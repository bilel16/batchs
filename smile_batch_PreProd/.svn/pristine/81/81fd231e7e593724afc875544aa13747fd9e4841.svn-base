package com.bna.smile.model.prelevement.traitement;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.prelevement.dao.PrelevementDAO;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.bna.smile.model.prelevement.service.PrelevementBatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * @author 5556
 * @since 11/03/2026 Refonte SNT - ACH
 **/
public class InsertionPrelevementsDomiciliationsACHTrt extends Traitement {

	Context context = ContextHandler.getContext();

	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	ICriteria criteria = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();

	PrelevementBatchService prelevementBatchService = (PrelevementBatchService) context
			.getBean("iPrelevementBatchService");

	public InsertionPrelevementsDomiciliationsACHTrt() {
	}

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

			logger.info("************** Debut insertion dans la base **********");

			Long codeStrcBCT = Long.valueOf(prelevementDAO.getCodeStructureBCT(agence.getCodStrcStrc()));
			Long codeStrcBNA = agence.getCodStrcStrc();

			logger.info("codetStrcBNA : " + codeStrcBNA + "----> codeAgenceBCT: " + codeStrcBCT);

			// *********** Importation Prelevement & domiciliation ***********/

			/**
			 * MAJ ACH REFONTE SNT 11/03/2026
			 **/

			PrelevementVo prelevementVoDom = new PrelevementVo();
			prelevementVoDom.setDateComptable(dateComptableAgence);
			prelevementVoDom.setCodeStructureBCT(codeStrcBCT);
			prelevementVoDom.setCodeStructure(codeStrcBNA);
			// prelevementVoDom.setPathFichier(pathPrelevementTravail);
			// prelevementVoDom.setPathFichierTraite(pathPrelevementTraitement);

			/**
			 * MAJ ACH REFONTE SNT 11/03/2026
			 **/
			prelevementVoDom = (PrelevementVo) prelevementBatchService.saveLotsDomiciliationsACH(prelevementVoDom);

			if (prelevementVoDom.isEtatEnregistrementPrelevement() == true) {
				logger.info("Fichier de domiciliation de l'agence " + codeStrcBNA + " est inséré avec succès");
			} else {
				logger.error(prelevementVoDom.getErreur());
			}

			/**
			 * MAJ ACH REFONTE SNT 11/03/2026
			 **/

			PrelevementVo prelevementVoPrl = new PrelevementVo();
			prelevementVoPrl.setDateComptable(dateComptableAgence);
			prelevementVoPrl.setCodeStructureBCT(codeStrcBCT);
			prelevementVoPrl.setCodeStructure(codeStrcBNA);
			// prelevementVoPrl.setPathFichier(pathPrelevementTravail);
			// prelevementVoPrl.setPathFichierTraite(pathPrelevementTraitement);
			prelevementVoPrl = (PrelevementVo) prelevementBatchService.saveLotsPrelevementsACH(prelevementVoPrl);

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
			StringBuffer text = new StringBuffer("Erreur dans InsertionPrelevementsDomiciliationsACHTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("InsertionPrelevementsDomiciliationsACHTrt");
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
