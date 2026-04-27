package com.bna.smile.model.prelevement.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.Operation;
import com.bna.commun.model.Produit;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class CreationCROReceptionPrelevementsRecusTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDate1 = new SimpleDateFormat("ddMMyyyy");

	public CreationCROReceptionPrelevementsRecusTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		PrelevementVo prelevementVo = (PrelevementVo) vo;

		try {
			this.setCroFlag(true);

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationCROReceptionPrelevementsRecusTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationCROReceptionPrelevementsRecusTrt");
			prelevementVo.addError(erreur);
			logger.error("Erreur au niveau CreationCROReceptionPrelevementsRecusTrt : ", e);
			throw new RuntimeException(e);

		}
		return (prelevementVo);
	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		logger.info("starting CreationCROReceptionPrelevementsRecusTrt method ");

		PrelevementVo prelevementVo = (PrelevementVo) vo;

		Operation operation = new Operation();
		operation = prelevementVo.getOperation();
		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_PRELEVEMENTS);

		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */
		this.setNumRefCro(Long.valueOf("9999999"));
		this.setLibRefCro("RECEPTION DES  PRELEVEMENTS RECUS");
		this.setCodeStructInitiatrice(Constants.COD_DIR_TRESORERIE + "");

		this.setCodStrcImpt(prelevementVo.getCodeStructureBNA());
		this.setDatExecCro(new Date());
		this.setCodEtatCro(0);
		this.setCodeProduit(produit.getCodPrdPrd().toString());
		this.setOperationId(operation.getCodOperOper().toString());
		this.setDateOperation(prelevementVo.getDateComptable());
		this.setDatValCro(prelevementVo.getDateComptable());
		this.setDatValCom(null);
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(Constants.COD_TACH_RECEP_PRELEV_RECU);
		this.setCodRefcOmp("9999999");
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.oxia.security.abc.model.Personnel user = null;
		if (obj instanceof UserDetails) {
			user = (com.oxia.security.abc.model.Personnel) obj;
		}
		this.setNumCinUser("9999");
		this.setCodTypUser("M");

		this.setCodRefInter(prelevementVo.getReferenceInterSiege());

		/*
		 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
		 */

		StringBuffer cro = new StringBuffer("");

		// *** Montant global par agence des prélèvements reçus ****//
		cro.append("MNT_GLB_RCP_PRE_AGE=");
		cro.append(prelevementVo.getMNT_GLB_RCP_PRE_AGE() + ";");

		// *** Nombre global par agence des prélèvements reçus ****//
		cro.append("NBR_GLB_RCP_PRE_AGE=");
		cro.append(prelevementVo.getNBR_GLB_RCP_PRE_AGE() + ";");

		// *** Code référence inter siège ****//
		cro.append("code_ref_is=");
		cro.append(43 + ";");

		// *** Structure receptrice ****//
		cro.append("vir24.cod_strc_recep=");
		cro.append(prelevementVo.getCodeStructureBNA() + ";");

		// *** Numéro de lot de la réception ****//
		cro.append("Numlotreception=");
		cro.append(prelevementVo.getNumeroLot() + ";");

		this.setCroText(cro.toString());
	}

	public TraitementConditionBanque getConditionDeBanque(TraitementConditionBanque traitementConditionBanque) {
		try {

			traitementConditionBanque.getCB();

		} catch (Exception e) {
			logger.error("Error occurred when trying to get bank conditions.", e);
		}

		return traitementConditionBanque;

	}

}