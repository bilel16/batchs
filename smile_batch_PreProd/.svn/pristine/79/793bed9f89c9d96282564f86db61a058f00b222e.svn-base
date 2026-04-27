package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceVirement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class CreationCRORejetVirementTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	VirementService virementService = (VirementService) context.getBean("iVirementService");
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");

	public CreationCRORejetVirementTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		DetailVirement detailVirement = new DetailVirement();

		OperationMoyPay operationMoyPay = new OperationMoyPay();
		Operation operation = new Operation();
		Produit produit = new Produit();
		ContratCpt contratCptBenif = new ContratCpt();
		ContratCptId contratCptIdBenif = new ContratCptId();
		boolean boolMajContratBenfif = false;

		try {

			detailVirement = virementVo.getDetailVirement();
			Date dateComptable = virementVo.getDateComptableAgence();
			operation = virementVo.getOperation();
			// ************** Creation Enregistrement dans Trace Virement ***********//

			Long sequenceTraceVirement = virementGlobalDAO.getSequenceTraceVirement();
			TraceVirement traceVirement = new TraceVirement();

			traceVirement.setNumSeqTrace(sequenceTraceVirement);

			traceVirement.setDetailVirement(detailVirement);
			traceVirement.setOperation(operation);
			traceVirement.setDatOperTrace(dateComptable);
			traceVirement.setDatSysTrace(new Date());
			traceVirement.setTimeTrace(formaterHeure.format(new Date()));
			traceVirement.setSens(Constants.COD_SENS_EMIS);

			String codstructureRecep = detailVirement.getRibBenDetv().substring(5, 8);
			Structure structureRecp = new Structure();
			structureRecp.setCodStrcStrc(new Long(codstructureRecep));

			traceVirement.setStructureEmettrice(structureRecp);
			traceVirement.setStructureReceptrice(detailVirement.getGlobalVirement().getStructure());
			traceVirement.setMontVirTrace(detailVirement.getMntDetvDetv());
			crudService.create(traceVirement);

			this.setCroFlag(true);

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationCRORejetVirementTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationCRORejetVirementTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau CreationCRORejetVirementTrt : ", e);
			virementVo.setMessageValidation("Probléme dans CreationCRORejetVirementTrt");
			throw new RuntimeException();

		}
		return (virementVo);

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		logger.info("starting CreationCRORejetVirementTrt method ");

		VirementVo virementVo = (VirementVo) vo;

		OperationMoyPay operationMoyPay = new OperationMoyPay();
		operationMoyPay = virementVo.getOperationMoyPay();
		DetailVirement detailVirement = new DetailVirement();
		detailVirement = virementVo.getDetailVirement();
		Operation operation = new Operation();
		operation = virementVo.getOperation();

		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_VIREMENT_PERMANENT);
		// produit = virementVo.getProduit();

		boolean etatBenifMemeAgence = virementVo.isEtatBenifMemeAgence();
		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */
		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		this.setLibRefCro("REJET VIREMENT");

		if (operationMoyPay.getStructureInitiatrice().getCodStrcStrc() != null
				&& operationMoyPay.getStructureInitiatrice().getCodStrcStrc().longValue() != 0) {
			this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
		} else {
			this.setCodeStructInitiatrice("900");
		}
		this.setCodStrcImpt(Long.valueOf(detailVirement.getRibBenDetv().substring(5, 8)));
		this.setDatExecCro(new Date());
		this.setCodEtatCro(0);
		this.setCodeProduit(produit.getCodPrdPrd().toString());
		this.setOperationId(operation.getCodOperOper().toString());
		this.setDateOperation(operationMoyPay.getDatOperOmp());
		this.setDatValCro(null);
		this.setDatValCom(null);
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(operationMoyPay.getTache().getTacheId().getCodTachTach());
		this.setCodRefcOmp(operationMoyPay.getCodRefcOmp());
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.oxia.security.abc.model.Personnel user = null;
		if (obj instanceof UserDetails) {
			user = (com.oxia.security.abc.model.Personnel) obj;
		}
		this.setNumCinUser("9999");
		this.setCodTypUser("M");
		/*
		 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
		 */

		StringBuffer cro = new StringBuffer();

		// Montant brut du virement rejeté
		cro.append("MNT_BRT_RJV_REC=");
		cro.append(detailVirement.getMntDetvDetv() + ";");

		// Etat du bénéficiaire par rapport au donneur d'ordre (même agence ou agence différente)
		cro.append("ETAT_STRC_BENIF=");
		if (etatBenifMemeAgence == true) {

			cro.append(new Long(0) + ";");

		} else {

			cro.append(new Long(1) + ";");
		}

		// *** Rib Bénéficiaire****//
		cro.append("rib_benef=");
		cro.append(detailVirement.getRibBenDetv() + ";");

		// Numéro Remise Virement
		cro.append("COD_REM_VIR=");
		cro.append(detailVirement.getDetailVirementId().getNumSeqGvir() + ";");

		this.setCroText(cro.toString());
	}

}