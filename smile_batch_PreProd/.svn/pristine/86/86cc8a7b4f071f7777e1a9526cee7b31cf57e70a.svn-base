package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Produit;
import com.bna.commun.model.TraceVirement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class CreationCROEnvoiVirementTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");

	public CreationCROEnvoiVirementTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		OperationMoyPay operationMoyPay = new OperationMoyPay();
		Operation operation = new Operation();
		GlobalVirement globalVirement = new GlobalVirement();

		try {

			operationMoyPay = virementVo.getOperationMoyPay();
			operation = virementVo.getOperation();
			globalVirement = virementVo.getGlobalVirement();
			Date dateComptable = virementVo.getDateComptableAgence();

			if (globalVirement != null && globalVirement.getNumSeqGvir() != null) {

				globalVirement.getStructure().getCodStrcStrc();

				// ************** Creation Enregistrement dans Trace Virement ***********//

				Long sequenceTraceVirement = virementGlobalDAO.getSequenceTraceVirement();
				TraceVirement traceVirement = new TraceVirement();

				traceVirement.setNumSeqTrace(sequenceTraceVirement);

				traceVirement.setGlobalVirement(globalVirement);
				traceVirement.setOperation(operation);
				traceVirement.setDatOperTrace(dateComptable);
				traceVirement.setDatSysTrace(new Date());
				traceVirement.setTimeTrace(formaterHeure.format(new Date()));
				traceVirement.setSens(Constants.COD_SENS_EMIS);
				traceVirement.setStructureEmettrice(globalVirement.getStructure());
				traceVirement.setMontVirTrace(virementVo.getMontantGlobalByStructure());
				crudService.create(traceVirement);
			}
			this.setCroFlag(true);
			virementVo.setEtatInsertionCro(true);

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationCROEnvoiVirementTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationCROEnvoiVirementTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau CreationCROEnvoiVirementTrt : ", e);
			virementVo.setMessageValidation("Probléme dans CreationCROEnvoiVirementTrt");
			throw new RuntimeException(e);

		}
		return (virementVo);

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		logger.info("starting CreationCROEnvoiVirementTrt method ");

		VirementVo virementVo = (VirementVo) vo;

		OperationMoyPay operationMoyPay = new OperationMoyPay();
		operationMoyPay = virementVo.getOperationMoyPay();
		GlobalVirement globalVirement = new GlobalVirement();
		Operation operation = new Operation();
		operation = virementVo.getOperation();
		long montantGlobal = virementVo.getMontantGlobalByStructure();
		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_VIREMENT_PERMANENT) ;
		// produit = virementVo.getProduit();
		globalVirement = virementVo.getGlobalVirement();
		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */

		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		this.setLibRefCro("ENVOI PRESENTATION VIREMENT AGENCE");

		if (operationMoyPay.getStructureInitiatrice().getCodStrcStrc() != null
				&& operationMoyPay.getStructureInitiatrice().getCodStrcStrc().longValue() != 0) {
			this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
		} else {
			this.setCodeStructInitiatrice("900");
		}
		this.setCodStrcImpt(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());
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

		this.setCodRefInter(virementVo.getCodRefInter());
		/*
		 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
		 */

		StringBuffer cro = new StringBuffer();

		// Montant global des virements émis par structure
		cro.append("MNT_GLO_VEM=");
		cro.append(montantGlobal + ";");

		// Numéro Remise Virement
		if (globalVirement != null && globalVirement.getNumSeqGvir() != null) {
			cro.append("COD_REM_VIR=");
			cro.append(globalVirement.getNumSeqGvir() + ";");
		}

		// Code référence inter siège
		cro.append("code_ref_is=");
		cro.append(new Long(43) + ";");

		// Code structure réceptrice du virement
		cro.append("cod_strc_recep=");
		cro.append(virementVo.getCodStrcRecp() + ";");

		this.setCroText(cro.toString());
	}
}