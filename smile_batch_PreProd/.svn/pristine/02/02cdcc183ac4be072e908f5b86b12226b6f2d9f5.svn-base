package com.bna.smile.model.domaineguichet.traitement;

import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;

import com.bna.commun.model.BatchRejetVirNSI;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.test.BatchReaffRejetsNSIFrame;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domaineguichet.dao.GuichetDAO;
import com.bna.smile.model.domaineguichet.model.AgencesMAJNSIVo;
import com.bna.smile.model.domaineguichet.model.MvtDevise;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class ReaffecterRejtesTrt extends Traitement {

	private BatchReaffRejetsNSIFrame mainFrame;

	/**
	 * Consutructor
	 */
	public ReaffecterRejtesTrt() {
		super();
	}

	public ReaffecterRejtesTrt(BatchReaffRejetsNSIFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	// à ne pas laisser en variable global

	List<Long> listAgencesMAJNSIStrc = null;
	List<MvtDevise> mvtDevises = null;
	List<AgencesMAJNSIVo> listAgencesMAJNSIStrcBtch = null;
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	BufferedWriter bufWriter = null;
	GuichetDAO guichetDao;
	int nbExcep = 0;
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

	public IValueObject perform(IValueObject vo) {
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		Context context = ContextHandler.getContext();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");

		try {
			guichetDao = (GuichetDAO) context.getBean("guichetDAO");

			// /*** traitement Batch
			Long CodeAgence = null;
			if (mainFrame.getTextFieldAgence() != null && mainFrame.getTextFieldAgence().getText() != null
					&& mainFrame.getTextFieldAgence().getText().length()>0) {
				CodeAgence = Long.valueOf(mainFrame.getTextFieldAgence().getText());
			}
			listAgencesMAJNSIStrc = guichetDao.getListAgencesRejetNsi(CodeAgence);

			for (Long codeAgence : listAgencesMAJNSIStrc) {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setStructure("" + codeAgence);
				infoVo.setEtat(Constants.STATUT_EN_COURS_INSERT);
				infoVo.setDateComptable(formatDate.format(new Date()));
				mainFrame.addOrUpdateEtat(infoVo);

				BatchService batchService = (BatchService) context.getBean("batchService");

				ICriteria criteriaAgVirNsi = searchEngine.createCriteria();
				IExpression expressionVirNsi = searchEngine.createExpression();
				criteriaAgVirNsi.add(expressionVirNsi.eq("structure.codStrcStrc", codeAgence));
				criteriaAgVirNsi.add(expressionVirNsi.isNotNull("contratCpt"));
				//criteriaAgVirNsi.add(expressionVirNsi.ge("contratCpt.contratCptId.codPrdPrd", 121L));
				criteriaAgVirNsi.add(expressionVirNsi.eq("codEtatBatr", "A"));
				criteriaAgVirNsi.add(expressionVirNsi.ge("datCompBatr", DateHandler.strToDate("01/01/2021")));
				criteriaAgVirNsi.addOrder(Order.asc("numSeqBatr"));

				List<BatchRejetVirNSI> listBatchRejetVirNSI =
						searchEngine.find(BatchRejetVirNSI.class, criteriaAgVirNsi);

				if (listBatchRejetVirNSI != null && listBatchRejetVirNSI.size() > 0) {

					Long nombreTotal = Long.valueOf(listBatchRejetVirNSI.size());
					Long nombreTraite = 0l;
					Long nombreRejets = 0l;
					for (BatchRejetVirNSI batchRejetVirNSI : listBatchRejetVirNSI) {

						VirementVo virementVo = new VirementVo();
						ParamAgence paramAgence = new ParamAgence();
						paramAgence.setNumMatrUser("9999");
						paramAgence.setDateComptable(formatDate.format(new Date()));
						virementVo.setParamAgence(paramAgence);
						virementVo.setCodStrcStrc(codeAgence);
						batchRejetVirNSI.setCodEtatBatr("R");
						virementVo.setBatchRejetVirNSI(batchRejetVirNSI);
						virementVo.setDateComptableAgence(new Date());
						VirementService virementService = (VirementService) context.getBean("iVirementService");
						virementVo = (VirementVo) virementService.reaffecterRejetVirSiege(virementVo);

						if (virementVo.isEtatEnregistrement() == true) {
							nombreTraite++;
						} else {
							nombreRejets++;

						}

					}

					infoVo.setEtat(Constants.STATUT_EN_TERMINE);
					infoVo.setDateComptable(formatDate.format(new Date()));
					String info = " Nombre total des opérations = " + nombreTotal
							+ " ; Nombre des opérations positionnées  = " + nombreTraite + " ; Nombre des rejets = "
							+ nombreRejets;
					infoVo.setInfo(info);
					mainFrame.getBtnExcuter().setEnabled(false);
					mainFrame.addOrUpdateEtat(infoVo);

				} else {

					infoVo.setEtat(Constants.STATUT_EN_TERMINE);
					infoVo.setDateComptable(formatDate.format(new Date()));
					String info = " Nombre total des opérations = " + 0;
					infoVo.setInfo(info);
					mainFrame.getBtnExcuter().setEnabled(false);
					mainFrame.addOrUpdateEtat(infoVo);
				}
			}

			System.out.println("  *************** Fin *****************");
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans ReaffecterRejtesTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("ReaffecterRejtesTrt");
			logger.error("Exception : ", e);
			vo.addError(erreur);

		}
		return vo;
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

}
