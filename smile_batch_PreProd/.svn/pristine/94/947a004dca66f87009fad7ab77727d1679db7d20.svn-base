package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.Operation;
import com.bna.commun.model.Produit;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReceptionEffetVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class Cro825ReceptionLCNTrt extends Traitement {

	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	Context context = ContextHandler.getContext();

	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

	public Cro825ReceptionLCNTrt() {
	}

	public IValueObject perform(IValueObject vo) {
		logger.info("Cro d'operation recepetion LCN");
		CompensationEffetVo compensationEffetVo = (CompensationEffetVo) vo;
		this.setCroFlag(true);

		try {

		} catch (Exception e) {
			logger.info(e.getMessage());
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans Cro825ReceptionLCNTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("Cro825ReceptionLCNTrt");
			compensationEffetVo.addError(erreur);
			logger.error("Erreur au niveau Cro825ReceptionLCNTrt : ", e);
			throw new RuntimeException(e);

		}
		return compensationEffetVo;

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		try {
			CompensationEffetVo compensationEffetVo = (CompensationEffetVo) vo;
			Operation operation = new Operation();
			operation.setCodOperOper(Constants.COD_OPER_RECEP_COMP_EFFET);
			Produit produit = new Produit();
			produit.setCodPrdPrd(Constants.COD_PRODUIT_EFFET);

			/*
			 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
			 */

			GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();

			String quantieme =
					StrHandler.lpad(String.valueOf(new Double(generateReferenceInterSiege
							.getQuantieme(compensationEffetVo.getDateComptable())).intValue()), '0', 3);
			String refIntSg = ""+Constants.COD_DIR_TRESORERIE + quantieme + "2EF";
			this.setCodRefInter(refIntSg);
			this.setNumRefCro(Long.valueOf("9999"));
			this.setLibRefCro("Réception presentation Effet LCN");
			this.setCodeStructInitiatrice("" + Constants.COD_DIR_TRESORERIE);

			this.setCodStrcImpt(compensationEffetVo.getStructure().getCodStrcStrc());
			this.setDatExecCro(new Date());
			this.setCodEtatCro(0);
			this.setCodeProduit(produit.getCodPrdPrd().toString());
			this.setOperationId(operation.getCodOperOper().toString());
			this.setDateOperation(compensationEffetVo.getDateComptable());
			this.setDatValCro(compensationEffetVo.getDateComptable());
			this.setDatValCom(null);
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
			formater = new SimpleDateFormat("HH:mm:ss");
			String heureString = formater.format(new Date());
			this.setHeureOperation(heureString);
			this.setTypeOperationCro("O");
			this.setCodTachTach(Constants.COD_TACHE_EFFET);
			this.setCodRefcOmp("1");
			this.setNumCinUser("9999");
			this.setCodTypUser("M");

			// this.setCodRefInter(globalPrelevements.getRefLotGprl());

			/*
			 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
			 */

			StringBuffer cro = new StringBuffer("");

			// *** Montant global compensation reçu ****//
			cro.append("NBR_LOT_RECU_EFF=");
			cro.append(compensationEffetVo.getNbrGlobEffet() + ";");

			// *** Nombre global compensation reçu ****//
			cro.append("MNT_LOT_RECU_EFF=");
			cro.append(compensationEffetVo.getMontGlobEffet() + ";");

			// *** Code référence inter siège ****//
			cro.append("Code_ref_is=");
			cro.append(65 + ";");

			cro.append("cod_strc_recep=");
			cro.append(compensationEffetVo.getStructure().getCodStrcStrc());

			this.setCroText(cro.toString());

			logger.info("Cro generer avec succés");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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