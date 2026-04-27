package com.bna.smile.model.prelevement.traitement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

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
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.prelevement.dao.PrelevementDAO;
import com.bna.smile.model.prelevement.model.ADDetailDomiciliationVo;
import com.bna.smile.model.prelevement.model.PrelevementVo;
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
public class SaveLotsDomiciliationsACHTrt extends Traitement {

	Context context = ContextHandler.getContext();

	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

	ICriteria criteria = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();

	PrelevementDAO prelevementDAO = (PrelevementDAO) context.getBean("prelevementDAO");

	private SimpleDateFormat formaterDate = new SimpleDateFormat("ddMMyyyy");
	private SimpleDateFormat formaterDate2 = new SimpleDateFormat("yyyyMMdd");

	public SaveLotsDomiciliationsACHTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);

		PrelevementVo prelevementVo = new PrelevementVo();

		Structure structureException = new Structure();

		try {
			prelevementVo = (PrelevementVo) vo;

			structureException.setCodStrcStrc(prelevementVo.getCodeStructure());

			long compteur = 0;

			String ageBct = StrHandler.lpad(prelevementVo.getCodeStructureBCT() + "", '0', 3);
			String wmmjj = formaterDate.format(prelevementVo.getDateComptable());

			boolean etatSaveFichier = importFromDataBase(prelevementVo.getDateComptable(),
					prelevementVo.getCodeStructureBCT(), Constants.COD_ENREGISTREMENT_DOMICILIATION);
			Long valeurEnrFichier = Long.valueOf(0);

			prelevementVo.setEtatEnregistrementPrelevement(etatSaveFichier);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans SaveLotsDomiciliationsACHTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("SaveLotsDomiciliationsACHTrt");
			logger.error("Exception : ", e);
			gestionException(prelevementVo.getDateComptable(), structureException, e);
			prelevementVo.setEtatEnregistrementPrelevement(false);
			prelevementVo.setErreur(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);

		}
		return prelevementVo;
	}

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
		batchExeptionPlac.setLibTpbmBate("Exception Batch Prelevement Domi ACH");
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

	public boolean importFromDataBase(Date dateComptable, Long codBct, Long codEnregistrement) {

		// Begin Import
		try {

			List<ADDetailDomiciliationVo> listDomiciliationsACH = new ArrayList<ADDetailDomiciliationVo>();

			listDomiciliationsACH = prelevementDAO.getListDomiciliationsACHAgence(dateComptable, codBct);
			long numberLine = 0;

			for (ADDetailDomiciliationVo detailDomiciliationVo : listDomiciliationsACH) {

				createDetailDomiciliationTemp(detailDomiciliationVo, numberLine);
				numberLine++;
			}

			logger.info("Nombre de ligne enregistree  :" + numberLine);
			System.out.println("Nombre de ligne enregistree  :" + numberLine);
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public DetailDomiciliationTemp createDetailDomiciliationTemp(ADDetailDomiciliationVo detailDomiciliationVo,
			long compteur)
			throws ParseException {

		DetailDomiciliationTemp detailDomiciliationTemp = new DetailDomiciliationTemp();
		DetailDomiciliationTempId newDetailDomiciliationTempId = new DetailDomiciliationTempId();

		try {

			newDetailDomiciliationTempId.setCodSenDom(detailDomiciliationVo.getCodSen());
			newDetailDomiciliationTempId.setRefFicDom(detailDomiciliationVo.getRefFic());
			newDetailDomiciliationTempId.setCodBanDom(Long.valueOf(detailDomiciliationVo.getCodBan()));
			try {
				newDetailDomiciliationTempId.setCodAgeDom(Long.valueOf(detailDomiciliationVo.getCodAge()));
			} catch (NumberFormatException e) {
				newDetailDomiciliationTempId.setCodAgeDom(Long.valueOf(0));
			}

			newDetailDomiciliationTempId.setDatOpeDom(detailDomiciliationVo.getDatOpe());
			newDetailDomiciliationTempId.setNumLotDom(detailDomiciliationVo.getNumLot());
			newDetailDomiciliationTempId.setNumDomDom(detailDomiciliationVo.getNumDom());
			newDetailDomiciliationTempId.setRibTirDom(lpadS(detailDomiciliationVo.getRibTir() + "", "0", 20));

			newDetailDomiciliationTempId.setNumDomDom(newDetailDomiciliationTempId.getNumDomDom() + compteur);

			detailDomiciliationTemp.setCodValDom(detailDomiciliationVo.getCodVal());
			detailDomiciliationTemp.setCodNatDom(detailDomiciliationVo.getCodNatEta());

			detailDomiciliationTemp.setCodEnrDom(detailDomiciliationVo.getCodEnr());
			detailDomiciliationTemp.setCodDevDev(Long.valueOf(detailDomiciliationVo.getCodDev()));

			detailDomiciliationTemp.setCodBanDes(Long.valueOf(detailDomiciliationVo.getCodBanDes()));
			detailDomiciliationTemp.setCodAgeDes(Long.valueOf(detailDomiciliationVo.getCodAgeDes()));

			Emetteur emetteur = new Emetteur(detailDomiciliationVo.getCodEmePrl());
			detailDomiciliationTemp.setEmetteur(emetteur);

			detailDomiciliationTemp.setNumRefDom(detailDomiciliationVo.getNumRefDom());
			detailDomiciliationTemp.setCodPayDom(detailDomiciliationVo.getCodPay());
			detailDomiciliationTemp.setCodMajDom(detailDomiciliationVo.getCodMaj());

			detailDomiciliationTemp.setDatMajDom(detailDomiciliationVo.getDatMaj());

			detailDomiciliationTemp.setDetailDomiciliationTempId(newDetailDomiciliationTempId);
			detailDomiciliationTemp.setCodEtatDom(Constants.COD_ETAT_DETAIL_DOM_TEMP_ATTENTE);

			DetailDomiciliationTemp detailDomiciliationTempBase =
					rechercherDetailDomiciliationTemp(detailDomiciliationTemp);

			logger.info("detailDomiciliationTemp .r" + detailDomiciliationTemp.getNumRefDom() + "==>"
					+ newDetailDomiciliationTempId.getNumDomDom());
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

			criteriaDOM.add(expressionDOM.eq("detailDomiciliationTempId.datOpeDom",
					detailDomiciliationTempRechercher.getDetailDomiciliationTempId().getDatOpeDom()));
			criteriaDOM.add(expressionDOM.eq("detailDomiciliationTempId.codBanDom",
					detailDomiciliationTempRechercher.getDetailDomiciliationTempId().getCodBanDom()));
			criteriaDOM.add(expressionDOM.eq("detailDomiciliationTempId.numDomDom",
					detailDomiciliationTempRechercher.getDetailDomiciliationTempId().getNumDomDom()));
			criteriaDOM.add(expressionDOM.eq("detailDomiciliationTempId.ribTirDom",
					detailDomiciliationTempRechercher.getDetailDomiciliationTempId().getRibTirDom()));
			criteriaDOM.add(expressionDOM.eq("emetteur.codEmtrEmtr",
					detailDomiciliationTempRechercher.getEmetteur().getCodEmtrEmtr()));
			criteriaDOM.add(expressionDOM.eq("codBanDes", detailDomiciliationTempRechercher.getCodBanDes()));
			criteriaDOM.add(expressionDOM.eq("codAgeDes", detailDomiciliationTempRechercher.getCodAgeDes()));
			criteriaDOM.add(expressionDOM.eq("numRefDom", detailDomiciliationTempRechercher.getNumRefDom()));

			List<DetailDomiciliationTemp> liste_DetailDomiciliationTemp = new ArrayList<DetailDomiciliationTemp>(
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
}
