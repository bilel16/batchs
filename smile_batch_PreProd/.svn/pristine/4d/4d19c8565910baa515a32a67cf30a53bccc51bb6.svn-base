package com.bna.smile.model.prelevement.traitement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.bna.commun.model.AdPrelevement;
import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.DetailDomiciliationTemp;
import com.bna.commun.model.DetailDomiciliationTempId;
import com.bna.commun.model.Emetteur;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.SuivFileTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.prelevement.dao.PrelevementDAO;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class SaveLotsDomiciliationsTrt extends Traitement {

	public SaveLotsDomiciliationsTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	PrelevementDAO prelevementDAO = (PrelevementDAO) context.getBean("prelevementDAO");
	// à ne pas laisser en variable global
	ICriteria criteria = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();
	private SimpleDateFormat formaterDate = new SimpleDateFormat("ddMMyyyy");
	private SimpleDateFormat formaterDate2 = new SimpleDateFormat("yyyyMMdd");

	public IValueObject perform(IValueObject vo) {

		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);

		PrelevementVo prelevementVo = new PrelevementVo();
		Structure structureException = new Structure();
		try {
			structureException.setCodStrcStrc(prelevementVo.getCodeStructure());
			long compteur = 0;
			prelevementVo = (PrelevementVo) vo;

			String wmmjj = null;
			String ageBct = null;
			ageBct = StrHandler.lpad(prelevementVo.getCodeStructureBCT() + "", '0', 3);
			wmmjj = formaterDate.format(prelevementVo.getDateComptable());

			String pathTravail = prelevementVo.getPathFichier();

			String srcFileDom =
					pathTravail + "03-" + ageBct + "-" + Constants.COD_ENREGISTREMENT_DOMICILIATION + "-"
							+ Constants.COD_ENREGISTREMENT_DETAIL_PRESENTATION + "-" + wmmjj + "-788.RCP";

			File srcFile = new File(srcFileDom);

			boolean etatSaveFichier = importFromFile(srcFile, "" + Constants.COD_ENREGISTREMENT_DOMICILIATION);
			Long valeurEnrFichier = Long.valueOf(0);
			if (etatSaveFichier == true) {

				valeurEnrFichier = Long.valueOf(1);

				SuivFileTrt.ajouterFichier("03-" + ageBct + "-" + Constants.COD_ENREGISTREMENT_DOMICILIATION + "-"
						+ Constants.COD_ENREGISTREMENT_DETAIL_PRESENTATION + "-" + wmmjj + "-788.RCP", ageBct,
						prelevementVo.getDateComptable(), valeurEnrFichier.intValue(),
						Constants.COD_ENREGISTREMENT_DOMICILIATION);

				// ********* Copier fichier dans traite ************//
				try {
					Util.copy(srcFileDom, prelevementVo.getPathFichierTraite() + srcFile.getName());

				} catch (Exception e) {
					logger.error(e.getMessage());
				}

			}

			prelevementVo.setEtatEnregistrementPrelevement(etatSaveFichier);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans SaveLotsDomiciliationsTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("SaveLotsDomiciliationsTrt");
			logger.error("Exception : ", e);
			gestionException(prelevementVo.getDateComptable(), structureException, e);
			prelevementVo.setEtatEnregistrementPrelevement(false);
			prelevementVo.setErreur(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);

		}
		return prelevementVo;
	}

	static final Comparator<AdPrelevement> AdPrelevement_ORDER = new Comparator<AdPrelevement>() {

		public int compare(AdPrelevement a1, AdPrelevement a2) {
			try {
				if (Long.valueOf(a1.getAdPrelevementId().getCodAge()) > Long.valueOf(a2.getAdPrelevementId()
						.getCodAge()))
					return 1;
				else if (Long.valueOf(a1.getAdPrelevementId().getCodAge()) < Long.valueOf(a2.getAdPrelevementId()
						.getCodAge()))
					return -1;
				else
					return 0;
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return 0;

			}
		}
	};

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	private void gestionException(Date dateComptable, Structure agence, Exception e) {

		BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();
		batchExeptionPlac.setDatSystBate(new Date());
		batchExeptionPlac.setDatCompBate(dateComptable);
		batchExeptionPlac.setStructure(agence);
		batchExeptionPlac.setLibTpbmBate("Exception Batch Prelevement");
		batchExeptionPlac.setLibExpBate(e.getMessage());
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
	}

	private void gestionStatistique(Date dateComptable, Structure agence, String message) {

		BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
		batchStatPlacement.setCodEtatBats("V");
		batchStatPlacement.setDatSystBats(new Date());
		batchStatPlacement.setDatCompBats(dateComptable);
		batchStatPlacement.setStructure(agence);
		batchStatPlacement.setLibExtrBats(message);
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_DOMICILIATION);
		batchStatPlacement.setBatchMetier(batchMetier);
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);
	}

	public boolean importFromFile(File fichier, String codEnregistrement) {

		// Begin Import
		try {
			BufferedWriter bufWriter = null;
			FileWriter fileWriter = null;
			InputStream ips = new FileInputStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			long numberLine = 0;

			while ((line = br.readLine()) != null && line.length() > 30) {

				if (codEnregistrement.equals("" + Constants.COD_ENREGISTREMENT_DOMICILIATION)
						&& line.substring(21, 23).equals("" + Constants.COD_ENREGISTREMENT_DETAIL_PRESENTATION)) {

					createDetailDomiciliationTemp(line, fichier.getName(),numberLine);
					numberLine++;
				}
			}
			br.close();

			logger.info("Nombre de ligne enregistree  :" + numberLine);
			return true;
		} catch (FileNotFoundException e) {

			logger.error("Le Fichier de domiciliation " + fichier.getName() + " n'existe pas dans le repertoire ");
			return false;
		} catch (Exception e) {

			logger.error(e.getMessage());
			return false;
		}
	}

	public DetailDomiciliationTemp createDetailDomiciliationTemp(String line, String nomFichier,long compteur) throws ParseException {

		DetailDomiciliationTemp detailDomiciliationTemp = new DetailDomiciliationTemp();
		DetailDomiciliationTempId newDetailDomiciliationTempId = new DetailDomiciliationTempId();

		try {
			logger.info("Traitement of line : " + line);

			newDetailDomiciliationTempId.setCodSenDom(new Long(line.substring(0, 1)));

			detailDomiciliationTemp.setCodValDom(new Long(line.substring(1, 3)));
			detailDomiciliationTemp.setCodNatDom(Long.valueOf(1));
			newDetailDomiciliationTempId.setRefFicDom(nomFichier);
			newDetailDomiciliationTempId.setCodBanDom(Long.valueOf(line.substring(4, 6)));
			try {
				newDetailDomiciliationTempId.setCodAgeDom(Long.valueOf(line.substring(6, 9)));
			} catch (NumberFormatException e) {
				newDetailDomiciliationTempId.setCodAgeDom(Long.valueOf(0));
			}
			String strDatOp = line.substring(9, 17);
			newDetailDomiciliationTempId.setDatOpeDom(formaterDate2.parse(strDatOp));
			newDetailDomiciliationTempId.setNumLotDom(new Long(line.substring(17, 21)));

			detailDomiciliationTemp.setCodEnrDom(new Long(line.substring(21, 23)));
			detailDomiciliationTemp.setCodDevDev(Long.valueOf(line.substring(23, 26)));
			newDetailDomiciliationTempId.setNumDomDom(new Long(line.substring(26, 33)));
			newDetailDomiciliationTempId.setRibTirDom(line.substring(33, 53));
			detailDomiciliationTemp.setCodBanDes(Long.valueOf(line.substring(53, 55)));
			detailDomiciliationTemp.setCodAgeDes(Long.valueOf(line.substring(33, 53).substring(2, 5)));
			Emetteur emetteur = new Emetteur(Long.valueOf(line.substring(58, 64)));
			detailDomiciliationTemp.setEmetteur(emetteur);
			detailDomiciliationTemp.setNumRefDom(line.substring(64, 84));
			detailDomiciliationTemp.setCodPayDom(Long.valueOf(line.substring(84, 85)));
			detailDomiciliationTemp.setCodMajDom(line.substring(85, 86));
			String strDatMaj = line.substring(86, 94);
			try {
				detailDomiciliationTemp.setDatMajDom(formaterDate2.parse(strDatMaj));
			} catch (Exception e) {
				detailDomiciliationTemp.setDatMajDom(null);
			}
			newDetailDomiciliationTempId.setNumDomDom(newDetailDomiciliationTempId.getNumDomDom()+compteur);
			detailDomiciliationTemp.setDetailDomiciliationTempId(newDetailDomiciliationTempId);
			detailDomiciliationTemp.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_ATTENTE);
			DetailDomiciliationTemp detailDomiciliationTempBase =
					rechercherDetailDomiciliationTemp(detailDomiciliationTemp);
			
			logger.info("detailDomiciliationTemp .r"+detailDomiciliationTemp.getNumRefDom() +"==>"+newDetailDomiciliationTempId .getNumDomDom());
			if (detailDomiciliationTempBase != null
					&& detailDomiciliationTempBase.getDetailDomiciliationTempId() != null) {
				logger.info("Un même detailDomiciliationTemp existe dans la base ");
			} else {
				
				crudService.create(detailDomiciliationTemp);
			}

		} catch (DataIntegrityViolationException e) {
			logger.error("DataIntegrityViolationException : " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return detailDomiciliationTemp;

	}

	public DetailDomiciliationTemp rechercherDetailDomiciliationTemp(
			DetailDomiciliationTemp detailDomiciliationTempRechercher) {

		try {

			ICriteria criteriaDOM = searchEngine.createCriteria();
			IExpression expressionDOM = searchEngine.createExpression();

			criteriaDOM.add(expressionDOM.eq("detailDomiciliationTempId.datOpeDom", detailDomiciliationTempRechercher
					.getDetailDomiciliationTempId().getDatOpeDom()));
			criteriaDOM.add(expressionDOM.eq("detailDomiciliationTempId.codBanDom", detailDomiciliationTempRechercher
					.getDetailDomiciliationTempId().getCodBanDom()));
			criteriaDOM.add(expressionDOM.eq("detailDomiciliationTempId.numDomDom", detailDomiciliationTempRechercher
					.getDetailDomiciliationTempId().getNumDomDom()));
			criteriaDOM.add(expressionDOM.eq("detailDomiciliationTempId.ribTirDom", detailDomiciliationTempRechercher
					.getDetailDomiciliationTempId().getRibTirDom()));
			criteriaDOM.add(expressionDOM.eq("emetteur.codEmtrEmtr", detailDomiciliationTempRechercher.getEmetteur()
					.getCodEmtrEmtr()));
			criteriaDOM.add(expressionDOM.eq("codBanDes", detailDomiciliationTempRechercher.getCodBanDes()));
			criteriaDOM.add(expressionDOM.eq("codAgeDes", detailDomiciliationTempRechercher.getCodAgeDes()));
			criteriaDOM.add(expressionDOM.eq("numRefDom", detailDomiciliationTempRechercher.getNumRefDom()));

			List<DetailDomiciliationTemp> liste_DetailDomiciliationTemp =
					new ArrayList<DetailDomiciliationTemp>(
							searchEngine.find(DetailDomiciliationTemp.class, criteriaDOM));

			if (liste_DetailDomiciliationTemp != null && liste_DetailDomiciliationTemp.size() > 0) {
				return liste_DetailDomiciliationTemp.get(0);

			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}

	}
}
