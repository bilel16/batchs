package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Produit;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class CreationCROAlimentationFaveurCompteDepotTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();

	public CreationCROAlimentationFaveurCompteDepotTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		OperationMoyPay operationMoyPay = new OperationMoyPay();
		Operation operation = new Operation();

		try {
			operationMoyPay = virementVo.getOperationMoyPay();
			operation = virementVo.getOperation();
			this.setCroFlag(true);
			virementVo.setEtatInsertionCro(true);

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationCROAlimentationFaveurCompteDepotTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationCROAlimentationFaveurCompteDepotTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau CreationCROAlimentationFaveurCompteDepotTrt : ", e);
			virementVo.setMessageValidation("Probléme dans CreationCROAlimentationFaveurCompteDepotTrt");
			throw new RuntimeException();

		}
		return (virementVo);
	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		logger.info("starting CreationCROAlimentationFaveurCompteDepotTrt method ");

		VirementVo virementVo = (VirementVo) vo;

		OperationMoyPay operationMoyPay = new OperationMoyPay();
		operationMoyPay = virementVo.getOperationMoyPay();
		Operation operation = new Operation();
		operation = virementVo.getOperation();
		long mntAlimentation = virementVo.getMntAlimentationCompteDepot();
		Produit produit = new Produit();
		produit = virementVo.getProduit();
		ContratCpt contratCptVert = new ContratCpt();
		ContratCpt contratCptDepot = new ContratCpt();
		contratCptVert = virementVo.getContratCptCompteVert();
		contratCptDepot = virementVo.getContratCptCompteDepot();

		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */

		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		this.setLibRefCro("ALIMENTATION EN FAVEUR DU COMPTE DEPOT");

		this.setDatValCro(operationMoyPay.getDatValOmp());
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

		// Montant alimentation
		cro.append("MONT_DIN_OMP_VERT=");
		cro.append(mntAlimentation + ";");

		// Numéro du compte client
		cro.append("numCptBnaV=");

		String codeStructure = "";
		String codeProduit = "";
		String numCcpt = "";
		String compte = "";
		int tailleStrc = 0;
		int taillePrd = 0;
		int tailleCpt = 0;
		if (contratCptVert != null) {
			if (contratCptVert.getContratCptId() != null) {

				codeStructure = "" + contratCptVert.getContratCptId().getCodStrcStrc();
				codeProduit = "" + contratCptVert.getContratCptId().getCodPrdPrd();
				numCcpt = "" + contratCptVert.getContratCptId().getNumCcptCcpt();

				tailleStrc = codeStructure.length();
				for (int i = tailleStrc; i < 3; i++) {
					codeStructure = "0" + codeStructure;
				}

				taillePrd = codeProduit.length();
				for (int i = taillePrd; i < 4; i++) {
					codeProduit = "0" + codeProduit;
				}

				tailleCpt = numCcpt.length();
				for (int i = tailleCpt; i < 6; i++) {
					numCcpt = "0" + numCcpt;
				}

				compte = codeStructure + codeProduit + numCcpt;
			}
		}
		cro.append(compte + ";");

		// Code Structure Initiatrice
		cro.append("COD_STRI_STRC=");
		cro.append(contratCptVert.getContratCptId().getCodStrcStrc().toString() + ";");

		// Code Structure Receptrice
		cro.append("cod_strc_recep=");
		cro.append(contratCptDepot.getContratCptId().getCodStrcStrc().toString() + ";");

		this.setCroText(cro.toString());

	}

}