package com.bna.smile.model.banqueAssurance.traitement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Devise;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.validator.AbstractValidator;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe qui permet de regler l'assureur
 * 
 * @author Sayeb Hichem
 * @since 19/05/2022
 */

public class ReglementAssureurAssuranceVieDecouvertTrt extends Traitement {

	Logger logger = Logger.getLogger(PrelevementAdhesionAssuranceVieTrt.class);
	Context context = ContextHandler.getContext();
	OperationMoyPay operationMoyPay = new OperationMoyPay();
	OperationMoyPay operationMoyPayRS = new OperationMoyPay();

	public ReglementAssureurAssuranceVieDecouvertTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		ParamAdhesion paramAdhesionEnv = (ParamAdhesion) vo;
		AdhesionAssVie adhesionAssVie = paramAdhesionEnv.getAdhesionAssVie();
		SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
		try {
			ContratCpt contratCptAss = paramAdhesionEnv.getNouveauCpt();
			ISearchEngine searchEngine = (ISearchEngine) context.getBean("searchEngine");
			contratCptAss = (ContratCpt) searchEngine.get(ContratCpt.class, contratCptAss.getContratCptId());

			List<String> palierChar = new ArrayList<String>();
			/*********** Condition de banque ************/

			if (contratCptAss.getContratCptId().getCodStrcStrc().intValue() != adhesionAssVie.getContratCpt()
					.getContratCptId().getCodStrcStrc().intValue()) {
				palierChar.add("42");
			} else {
				palierChar.add("41");
			}

			VirementVo virementVo = getConditionDeBanque(contratCptAss, adhesionAssVie.getMontPrmaTass(),
					paramAdhesionEnv.getDateComptable(),
					contratCptAss.getClient().getPersonne().getTypePiece().getCodTpceTpce(),
					contratCptAss.getClient().getPersonne().getNumPcePers(), palierChar);

			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			Operation operation = new Operation();

			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(paramAdhesionEnv.getStructure().getCodStrcStrc());
			Structure structureRecep = new Structure();
			structureRecep.setCodStrcStrc(contratCptAss.getStructure().getCodStrcStrc());

			Devise devise = new Devise();
			devise.setCodDevDev(Constants.COD_DEV_DINAR);
			operationMoyPay.setDevise(devise);
			operationMoyPay.setContratCpt(contratCptAss);
			operationMoyPay.setStructureInitiatrice(structureInit);
			operationMoyPay.setStructureReceptrice(structureRecep);
			operationMoyPay.setNumMoypOmp("00000");
			operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
			operationMoyPay.setPersonnelInitiateur(personnelInit);
			operationMoyPay.setPersonnelValideur(personnelInit);
			operationMoyPay.setRefIns1Omp(paramAdhesionEnv.getInterSiege());
			
			operationMoyPayRS.setDevise(devise);
			operationMoyPayRS.setContratCpt(contratCptAss);
			operationMoyPayRS.setStructureInitiatrice(structureInit);
			operationMoyPayRS.setStructureReceptrice(structureRecep);
			operationMoyPayRS.setNumMoypOmp("00000");
			operationMoyPayRS.setCodEtatOmp(Constants.COD_VALIDATION);
			operationMoyPayRS.setPersonnelInitiateur(personnelInit);
			operationMoyPayRS.setPersonnelValideur(personnelInit);
			operation.setCodOperOper(Constants.COD_OPER_REGLEMENT_ASSUREUR_ASSUR_VIE);

			Tache tache = new Tache();
			tache.setOperation(operation);
			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(operation.getCodOperOper());
			tacheId.setCodTachTach(Constants.COD_TACH_REGLEMENT_ASSUR_VIE);
			tache.setTacheId(tacheId);
			operationMoyPay.setTache(tache);
			operationMoyPayRS.setTache(tache);
			// Date d = new Date();

			// /*** la derniere journé ouverte pour cette agence
			operationMoyPay.setDatOperOmp(paramAdhesionEnv.getDateComptable());
			operationMoyPay.setDatSystOmp(new Date());
			operationMoyPayRS.setDatOperOmp(paramAdhesionEnv.getDateComptable());
			operationMoyPayRS.setDatSystOmp(new Date());

			Date dateValOmp = null;
			dateValOmp = formaterDate.parse(virementVo.getValueDateRecue());
			operationMoyPay.setDatValOmp(dateValOmp);
			operationMoyPayRS.setDatValOmp(dateValOmp);

			Produit produitAdhesionOmp = new Produit();

			// /* ajouté le 18/01/2016
			operationMoyPay.setCodRefbOmp(" PRIME " + adhesionAssVie.getNumSeqAdh() + "");
			operationMoyPay.setCodRefcOmp(adhesionAssVie.getNumSeqAdh().toString());
			operationMoyPayRS.setCodRefbOmp(" RS " + adhesionAssVie.getNumSeqAdh() + "");
			operationMoyPayRS.setCodRefcOmp(adhesionAssVie.getNumSeqAdh().toString());
			produitAdhesionOmp.setCodPrdPrd(Constants.COD_PRD_ASSUR_VIE_DECOUVERT);
			operationMoyPay.setProduit(produitAdhesionOmp);
			operationMoyPayRS.setProduit(produitAdhesionOmp);

			TypePiece typePieceDem = adhesionAssVie.getClient().getPersonne().getTypePiece();
			operationMoyPay.setTypePieceDemandeur(typePieceDem);
			operationMoyPay.setNumPcedOmp(adhesionAssVie.getClient().getPersonne().getNumPcePers());
			operationMoyPay.setNomNomdOmp(adhesionAssVie.getClient().getPersonne().getNomNomPers());
			operationMoyPay.setNomPrndOmp(adhesionAssVie.getClient().getPersonne().getNomPrnPers());
			operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);

			operationMoyPayRS.setTypePieceDemandeur(typePieceDem);
			operationMoyPayRS.setNumPcedOmp(adhesionAssVie.getClient().getPersonne().getNumPcePers());
			operationMoyPayRS.setNomNomdOmp(adhesionAssVie.getClient().getPersonne().getNomNomPers());
			operationMoyPayRS.setNomPrndOmp(adhesionAssVie.getClient().getPersonne().getNomPrnPers());
			operationMoyPayRS.setCodSensOmp(Constants.COD_SENS_CR);

			Long mntTotal = adhesionAssVie.getTarifAssVie().getMontPrmaTass()
					+ (adhesionAssVie.getTarifAssVie().getMontComTass() * Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE
							/ 100);
			operationMoyPay.setMontDinOmp(adhesionAssVie.getTarifAssVie().getMontPrmaTass());
			operationMoyPay.setMontSoldCcpt(contratCptAss.getMontSoldCcpt());
			operationMoyPay.setCodDemOmp("T");
			operationMoyPay.setMontApreOmp(
					contratCptAss.getMontSoldCcpt() + adhesionAssVie.getTarifAssVie().getMontPrmaTass());

			operationMoyPayRS.setMontDinOmp(adhesionAssVie.getTarifAssVie().getMontComTass()
					* Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100);
			operationMoyPayRS.setMontSoldCcpt(
					contratCptAss.getMontSoldCcpt() + adhesionAssVie.getTarifAssVie().getMontPrmaTass());
			operationMoyPayRS.setCodDemOmp("T");
			operationMoyPayRS.setMontApreOmp(contratCptAss.getMontSoldCcpt() + mntTotal);

			operationMoyPay.setLibMotfOmp("Réglement Assureur prime d'assurance vie ");
			operationMoyPay.setLibObjOpOmp("Réglement Assureur PRIME");
			operationMoyPayRS.setLibMotfOmp("Réglement Assureur Retenue à la source d'assurance vie ");
			operationMoyPayRS.setLibObjOpOmp("Réglement Assureur RS");

			InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt();
			operationMoyPay = (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPay);

			operationMoyPayRS = (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPayRS);
			paramAdhesionEnv.setOperationMoyPayAssureur(operationMoyPay);
			paramAdhesionEnv.setOperationMoyPayRs(operationMoyPayRS);
			// /*** MAJ du montant actualisé dans le contrat compte de L'Assureur
			ContratCptSold contratCptSold = new ContratCptSold();
			contratCptSold.setContratCpt(contratCptAss);
			// contratCptSold.setSolde(Long.valueOf(adhesionAssVie.getTarifAssVie().getMontPrmaTass()));
			// / *** maj suite a l'operation Operation Retenu a la source le 25/01/2016
			contratCptSold.setSolde(mntTotal);
			contratCptSold.setSens("C");
			UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
			ContratCpt contratCpt = (ContratCpt) updateSoldTrt.exec(contratCptSold);
			this.setCroFlag(true);

		} catch (Exception e) {
			this.setCroFlag(false);
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans ReglementAssureurAssuranceVieDecouvertTrt : ");
			text.append(e.toString());
			erreur.setCode("703");
			erreur.setDescription(text.toString());
			erreur.setKey("ReglementAssureurAssuranceVieDecouvertTrt");
			// /*** gerer une exception
			gestionException(paramAdhesionEnv.getDateComptable(), paramAdhesionEnv.getStructure(), e);
			vo.addError(erreur);
			paramAdhesionEnv.setMessageValidation(e.getMessage());
		}
		return (paramAdhesionEnv);

	}

	private void gestionException(Date dateComptable, Structure agence, Exception e) {

		try {
			BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();

			batchExeptionPlac.setDatSystBate(new Date());
			batchExeptionPlac.setDatCompBate(dateComptable);
			batchExeptionPlac.setStructure(agence);
			batchExeptionPlac.setLibTpbmBate("Exception Batch Prelevement ASS Vie Decouvert-Reglement Ass");
			batchExeptionPlac.setLibExpBate(e.getMessage());
			BatchService batchService = (BatchService) context.getBean("batchService");
			batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Date getDateCompt(Date d) {

		try {
			Date DateReturn = DateHandler.addJour(d, -1);
			while (CalanderHandler.isJourFerier(d)) {
				DateReturn = DateHandler.addJour(d, -1);
				d = DateHandler.addJour(d, -1);
			}
			return (DateHandler.strToDate(DateHandler.dateToStr(DateReturn)));

		} catch (Exception e) {
			logger.error(" Erreur dans getDateCompt.execute : ", e);
			return (d);
		}
	}

	/**
	 * methode qui retourne la premiere journee du trimestre courant
	 * 
	 * @param d
	 * @return d
	 */
	public Date getFirstDayOfQuarter(Date d) {

		int mois = d.getMonth();
		if (mois >= 0 && mois < 3) {
			mois = 0;
		} else {
			if (mois > 2 && mois < 6) {
				mois = 3;
			} else {
				if (mois > 5 && mois < 9) {
					mois = 6;
				} else {
					mois = 9;
				}
			}
		}
		d.setDate(1);
		d.setMonth(mois);

		return d;
	}

	public void genCroText(ValueObject vo) {
		ParamAdhesion paramAdhesionEnv = (ParamAdhesion) vo;
		AdhesionAssVie adhesionAssVie = paramAdhesionEnv.getAdhesionAssVie();
		/* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));

		this.setLibRefCro("SMILE.Reglement.Assureur.Vie.Decouvert");
		this.setDatValCro(operationMoyPay.getDatValOmp());
		this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc() + "");
		this.setCodStrcImpt(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc());
		this.setCodEtatCro(0);
		this.setCodeProduit(Constants.COD_PRD_ASSUR_VIE_DECOUVERT.toString());
		this.setOperationId(Constants.COD_OPER_REGLEMENT_ASSUREUR_ASSUR_VIE.toString());
		this.setDateOperation(operationMoyPay.getDatOperOmp());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(Constants.COD_TACH_REGLEMENT_ASSUR_VIE);
		if (adhesionAssVie.getNumSeqAdh() != null)
			this.setCodRefcOmp(adhesionAssVie.getNumSeqAdh().toString());
		this.setDatExecCro(new Date());
		this.setCodRefInter(paramAdhesionEnv.getInterSiege());
		this.setNumCinUser("9999");
		this.setCodTypUser("M");

		/* ------------------Garniture de la partie VARIABLE du CRO---------------------------------- */
		StringBuffer cro = new StringBuffer("");

		// /*** contratClient
		cro.append("numCptBna=");
		cro.append(StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getCodStrcStrc().toString(), '0', 3)
				+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getCodPrdPrd().toString(), '0', 4)
				+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), '0', 6)
				+ ";");

		cro.append("contratAssureur=");
		cro.append(
				StrHandler.lpad(paramAdhesionEnv.getNouveauCpt().getContratCptId().getCodStrcStrc().toString(), '0', 3)
						+ StrHandler.lpad(paramAdhesionEnv.getNouveauCpt().getContratCptId().getCodPrdPrd().toString(),
								'0', 4)
						+ StrHandler.lpad(
								paramAdhesionEnv.getNouveauCpt().getContratCptId().getNumCcptCcpt().toString(), '0', 6)
						+ ";");

		cro.append("TARIF_ASS_VIE.MONT_PRMA_TASS=");
		cro.append(adhesionAssVie.getTarifAssVie().getMontPrmaTass() + ";");

		cro.append("TARIF_ASS_VIE.MONT_COM_TASS=");
		cro.append(adhesionAssVie.getTarifAssVie().getMontComTass() + ";");

		cro.append("TARIF_ASS_VIE.MONT_PRMG_TASS=");
		cro.append(adhesionAssVie.getTarifAssVie().getMontPrmgTass() + ";");

		cro.append("TARIF_ASS_VIE.MONT_RTNS_TASS=");
		cro.append(Math.round(new Double(adhesionAssVie.getTarifAssVie().getMontComTass())
				* Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100) + ";");

		if (paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc().intValue() != adhesionAssVie
				.getContratCpt().getStructure().getCodStrcStrc().intValue()) {
			cro.append("RefInterSiege=");
			cro.append(paramAdhesionEnv.getInterSiege() + ";");
			cro.append("IS=1;");
		} else {
			
			cro.append("RefInterSiege=0;");
			cro.append("IS=0;");
		}
		cro.append("TARIF_ASS_VIE.MONT_PRMA_RTNS_TASS=");
		cro.append(Long.valueOf(Math
				.round(new Double(adhesionAssVie.getTarifAssVie().getMontComTass())
						* Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100)
				+ adhesionAssVie.getTarifAssVie().getMontPrmaTass()) + ";");

		cro.append("COD_STRC_ASS=");
		cro.append(
				StrHandler.lpad(paramAdhesionEnv.getNouveauCpt().getContratCptId().getCodStrcStrc().toString(), '0', 3)
						+ ";");

		cro.append("COD_STRC_INIT=");
		cro.append(adhesionAssVie.getContratCpt().getContratCptId().getCodStrcStrc().toString() + ";");

		this.setCroText(cro.toString());
	}

	public String getNumeroTache(ValueObject vo) {
		return Constants.CODE_RESSOURCE_GENERALE;
	}

	public VirementVo getConditionDeBanque(ContratCpt compte, Long montant, Date dateComptable, Long codePiece,
			String numPiece, List<String> palierChar) {

		TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
		VirementVo virementVoCB = new VirementVo();
		// /-----------------------------////
		Operation operation = new Operation();
		operation.setCodOperOper(Constants.COD_OPER_REGLEMENT_ASSUREUR_ASSUR_VIE);

		String tvaRecue = "";
		String tva = "";
		String commissionRecue = "";
		String valueDateRecue = "";
		String valueDateComRecue = "";

		try {

			traitementConditionBanque.setCodOperOper(operation.getCodOperOper().toString());
			traitementConditionBanque.setCodPrdPrd(compte.getContratCptId().getCodPrdPrd() + "");

			traitementConditionBanque.setCodStrcStrc(compte.getContratCptId().getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(compte.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(compte.getContratCptId().getNumCcptCcpt().toString());

			traitementConditionBanque.setMontant(montant + "");

			traitementConditionBanque.setDateReference(DateHandler.dateToStr(dateComptable));

			traitementConditionBanque.setCodTpceTpce(codePiece + "");
			traitementConditionBanque.setNumPcePers(numPiece);
			traitementConditionBanque.setPalierChar(new ArrayList(palierChar));

			traitementConditionBanque.getCB();

			tvaRecue = String.valueOf(traitementConditionBanque.getMntTva() / 1000);
			if (tvaRecue == null || "".equals(tvaRecue.trim())) {
				tva = "0.0";
			}

			commissionRecue =
					AbstractValidator.formatMontant(traitementConditionBanque.getValeurCommission() / 1000 + "");
			if (commissionRecue == null || "".equals(commissionRecue.trim())) {
				commissionRecue = "0.0";
			}
			valueDateRecue = traitementConditionBanque.getDatevaleur();
			valueDateComRecue = traitementConditionBanque.getDatevaleurComm();
			if (valueDateRecue != null && valueDateRecue.equals("NAN")) {
				valueDateRecue = null;
			}

			if (valueDateComRecue != null && valueDateComRecue.equals("NAN")) {
				valueDateComRecue = null;
			}

			virementVoCB.setNumCondition(traitementConditionBanque.getNumSeqCond());
			virementVoCB.setTypeCondition(traitementConditionBanque.getCodTcndTcnd());
			virementVoCB.setTva(tva);
			virementVoCB.setTvaRecue(tvaRecue);
			virementVoCB.setCommissionRecue(commissionRecue);
			virementVoCB.setValueDateRecue(valueDateRecue);
			virementVoCB.setOperation(operation);
			virementVoCB.setValueDateComRecue(valueDateComRecue);

		} catch (Exception e) {
			logger.error("Error occurred when trying to get bank conditions.", e);
		}

		return virementVoCB;

	}

}
