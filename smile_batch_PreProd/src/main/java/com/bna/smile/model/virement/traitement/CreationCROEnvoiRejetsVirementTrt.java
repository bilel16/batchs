package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

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
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class CreationCROEnvoiRejetsVirementTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");

	public CreationCROEnvoiRejetsVirementTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		OperationMoyPay operationMoyPay = new OperationMoyPay();
		SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
		Operation operation = new Operation();
		DetailVirement detailVirement = new DetailVirement();

		try {
			operationMoyPay = virementVo.getOperationMoyPay();
			operation = virementVo.getOperation();
			detailVirement = virementVo.getDetailVirement();
			Date dateComptable = virementVo.getDateComptableAgence();
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
			virementVo.setEtatInsertionCro(true);
			

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationCROEnvoiRejetsVirementTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationCROEnvoiRejetsVirementTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau CreationCROEnvoiRejetsVirementTrt : ", e);
			virementVo.setMessageValidation("Probléme dans CreationCROEnvoiRejetsVirementTrt");
			throw new RuntimeException();
			

		}
		return (virementVo);
	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		logger.info("starting CreationCROEnvoiRejetsVirementTrt method ");

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
		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */

		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		this.setLibRefCro("ENVOI REJETS VIREMENT AGENCE");

		
		if (operationMoyPay.getStructureInitiatrice().getCodStrcStrc() != null
				&& operationMoyPay.getStructureInitiatrice().getCodStrcStrc().longValue() != 0) {
			this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
		} else {
			this.setCodeStructInitiatrice("900");
		}
		this.setCodStrcImpt(new Long(detailVirement.getRibBenDetv().substring(5, 8)));
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
		
		// *** Rib Bénéficiaire****//
		cro.append("rib_benef=");
		cro.append(detailVirement.getRibBenDetv() + ";");
		
		// Montant global brut des virements rejetés
		cro.append("MNT_GBVR_RJV_AG=");
		cro.append(detailVirement.getMntDetvDetv() + ";");

		// Code référence inter siège
		cro.append("code_ref_is=");
		cro.append(new Long(43) + ";");

		// Numéro Remise Virement
		cro.append("COD_REM_VIR=");
		cro.append(detailVirement.getDetailVirementId().getNumSeqGvir() + ";");

		// Code structure receptrice
		cro.append("cod_strc_recep=");
		cro.append(operationMoyPay.getStructureInitiatrice().getCodStrcStrc() + ";");

		this.setCroText(cro.toString());
	}

}