package com.bna.smile.model.banqueAssurance.traitement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailAdhesion;
import com.bna.commun.model.Devise;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceAssuranceVieDecouvert;
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
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe qui permet de prélver un tarif d'assurance vie pour chaque client ayant une adhesion (1 fois par 3 mois)
 * 
 * @author SAYEB Hichem
 * @since 01/07/2022
 */

public class PrelevementAdhesionAssuranceVieDecouvertTrt extends Traitement {

	Logger logger = Logger.getLogger(PrelevementAdhesionAssuranceVieDecouvertTrt.class);
	OperationMoyPay operationMoyPay = new OperationMoyPay();

	public PrelevementAdhesionAssuranceVieDecouvertTrt() {
	}

	Context context = ContextHandler.getContext();

	public IValueObject perform(IValueObject vo) {

		ParamAdhesion paramAdhesionEnv = (ParamAdhesion) vo;
		AdhesionAssVie adhesionAssVie = paramAdhesionEnv.getAdhesionAssVie();
		SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formaterANNEE = new SimpleDateFormat("yyyy");
		SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
		try {

			ISearchEngine searchEngine = (ISearchEngine) context.getBean("searchEngine");
			CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

			ContratCpt contratCptDo =
					(ContratCpt) searchEngine.get(ContratCpt.class, adhesionAssVie.getContratCpt().getContratCptId());
			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			if (contratCptDo != null && contratCptDo.getContratCptId() != null
					&& contratCptDo.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)) {

				List<String> palierChar = new ArrayList<String>();
				/*********** Condition de banque ************/
				ContratCpt contratCptAss = paramAdhesionEnv.getNouveauCpt();
				if (contratCptAss.getContratCptId().getCodStrcStrc().intValue() != contratCptDo.getContratCptId()
						.getCodStrcStrc().intValue()) {
					palierChar.add("42");
				} else {
					palierChar.add("41");
				}
				// *************Condition Banque *********//

				VirementVo virementVo = getConditionDeBanque(contratCptDo, adhesionAssVie.getMontPrmgTass(),
						paramAdhesionEnv.getDateComptable(),
						contratCptDo.getClient().getPersonne().getTypePiece().getCodTpceTpce(),
						contratCptDo.getClient().getPersonne().getNumPcePers(), palierChar);

				Operation operation = new Operation();
				Structure structureInit = paramAdhesionEnv.getStructure();
				Structure structureRecep = new Structure();
				structureRecep.setCodStrcStrc(contratCptDo.getContratCptId().getCodStrcStrc());
				Devise devise = new Devise();
				devise.setCodDevDev(Constants.COD_DEV_DINAR);
				operationMoyPay.setDevise(devise);
				operationMoyPay.setContratCpt(contratCptDo);
				operationMoyPay.setStructureInitiatrice(structureInit);
				operationMoyPay.setStructureReceptrice(structureRecep);
				operationMoyPay.setNumMoypOmp("00000");
				operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
				operationMoyPay.setPersonnelInitiateur(personnelInit);
				operationMoyPay.setPersonnelValideur(personnelInit);
				operation.setCodOperOper(Constants.COD_OPER_RENOUVELLEMENT_SOUSCRIPTION_ASSUR_VIE);
				Tache tache = new Tache();
				tache.setOperation(operation);
				TacheId tacheId = new TacheId();
				tacheId.setCodOperOper(operation.getCodOperOper());
				tacheId.setCodTachTach(Constants.COD_TACH_PRELEV_ASSUR_VIE);
				tache.setTacheId(tacheId);
				operationMoyPay.setTache(tache);
				operationMoyPay.setDatOperOmp(paramAdhesionEnv.getDateComptable());
				operationMoyPay.setDatSystOmp(new Date());
				Date dateValOmp = formaterDate.parse(virementVo.getValueDateRecue());
				operationMoyPay.setDatValOmp(dateValOmp);
				operationMoyPay.setRefIns1Omp(paramAdhesionEnv.getInterSiege());
				Produit produitAdhesionOmp = new Produit();

				operationMoyPay.setCodRefbOmp(adhesionAssVie.getNumSeqAdh().toString());
				operationMoyPay.setCodRefcOmp(adhesionAssVie.getNumSeqAdh().toString());
				produitAdhesionOmp.setCodPrdPrd(Constants.COD_PRD_ASSUR_VIE_DECOUVERT);
				operationMoyPay.setProduit(produitAdhesionOmp);

				TypePiece typePieceDem = adhesionAssVie.getClient().getPersonne().getTypePiece();
				operationMoyPay.setTypePieceDemandeur(typePieceDem);
				operationMoyPay.setNumPcedOmp(contratCptDo.getClient().getPersonne().getNumPcePers());
				operationMoyPay.setNomNomdOmp(contratCptDo.getClient().getPersonne().getNomNomPers());
				operationMoyPay.setNomPrndOmp(contratCptDo.getClient().getPersonne().getNomPrnPers());
				operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
				operationMoyPay.setMontDinOmp(adhesionAssVie.getTarifAssVie().getMontPrmgTass());
				operationMoyPay.setMontSoldCcpt(contratCptDo.getMontSoldCcpt());
				operationMoyPay.setCodDemOmp("T"); // /*** type demandeur (Titulaire,CoTitul,Mandataire)
				operationMoyPay.setMontApreOmp(
						contratCptDo.getMontSoldCcpt() - adhesionAssVie.getTarifAssVie().getMontPrmgTass());
				operationMoyPay.setLibMotfOmp("Prélévement Renouvellement Assurance Vie Sur Découvert ANNEE "
						+ formaterANNEE.format(paramAdhesionEnv.getDateComptable()));
				operationMoyPay.setLibObjOpOmp("Renouvellement Assurance Vie  Découvert ANNEE "
						+ formaterANNEE.format(paramAdhesionEnv.getDateComptable()));
				InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt();
				operationMoyPay = (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPay);

				// /*** MAJ du montant actualisé dans le contrat compte
				ContratCptSold contratCptSold = new ContratCptSold();
				contratCptSold.setContratCpt(contratCptDo);
				contratCptSold.setSolde(Long.valueOf(adhesionAssVie.getTarifAssVie().getMontPrmgTass()));
				contratCptSold.setSens("D");
				// contratCptSold.setNewDateAutorisarion(
				// DateHandler.addJour(getDateFinAdhesion(paramAdhesionEnv.getDateComptable()), 7));
				UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
				ContratCpt contratCpt = (ContratCpt) updateSoldTrt.exec(contratCptSold);

				// /*** MAJ de l'adhesionAssVie
				adhesionAssVie.setDatePrelAdh(paramAdhesionEnv.getDateComptable());
				adhesionAssVie.setMontPrmgTass(adhesionAssVie.getTarifAssVie().getMontPrmgTass());
				adhesionAssVie.setMontPrmaTass(adhesionAssVie.getTarifAssVie().getMontPrmaTass());
				adhesionAssVie.setMontComTass(adhesionAssVie.getTarifAssVie().getMontComTass());
				adhesionAssVie.setDateEcheAdh(getDateFinAdhesion(paramAdhesionEnv.getDateComptable()));
				adhesionAssVie.setDateFinAdh(getDateFinAdhesion(paramAdhesionEnv.getDateComptable()));
				adhesionAssVie.setDateDebAdh(getDateDebutAdhesion(paramAdhesionEnv.getDateComptable()));
				adhesionAssVie.setDatRenAdh(paramAdhesionEnv.getDateComptable());
				UpdateAdhesionAssVieTrt updateAdhesionAssVieTrt = new UpdateAdhesionAssVieTrt();
				adhesionAssVie = (AdhesionAssVie) updateAdhesionAssVieTrt.exec(adhesionAssVie);

				// /*** MAJ du montant dans le contrat compte MAGHREBIA(MAJ solde+CRO)
				ReglementAssureurAssuranceVieDecouvertTrt reglementAssureurTrt =
						new ReglementAssureurAssuranceVieDecouvertTrt();
				paramAdhesionEnv = (ParamAdhesion) reglementAssureurTrt.exec(paramAdhesionEnv);
				// *************************************//

				TraceAssuranceVieDecouvert traceAssuranceVieDecouvert = new TraceAssuranceVieDecouvert();
				traceAssuranceVieDecouvert.setDateOperTrc(paramAdhesionEnv.getDateComptable());
				traceAssuranceVieDecouvert.setAdhesionAssVie(adhesionAssVie);
				Calendar cal = Calendar.getInstance();
				traceAssuranceVieDecouvert.setDateTrcTrc(cal.getTime());
				traceAssuranceVieDecouvert.setTache(tache);
				traceAssuranceVieDecouvert.setPersonnel(personnelInit);
				traceAssuranceVieDecouvert.setMontPrmgTass(adhesionAssVie.getMontPrmgTass());
				traceAssuranceVieDecouvert.setMontPrmaTass(adhesionAssVie.getMontPrmaTass());
				traceAssuranceVieDecouvert.setMontComTass(adhesionAssVie.getMontComTass());
				Long mntRetSource = Math.round(new Double(adhesionAssVie.getMontComTass())
						* Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100);
				traceAssuranceVieDecouvert.setMontRetSrcTass(mntRetSource);
				traceAssuranceVieDecouvert.setNumOperOmpClt(operationMoyPay.getNumOperOmp());
				if (paramAdhesionEnv.getOperationMoyPayAssureur() != null
						&& paramAdhesionEnv.getOperationMoyPayAssureur().getNumOperOmp() != null) {
					traceAssuranceVieDecouvert
							.setNumOperOmpAss(paramAdhesionEnv.getOperationMoyPayAssureur().getNumOperOmp());
				}
				if (paramAdhesionEnv.getOperationMoyPayRs() != null
						&& paramAdhesionEnv.getOperationMoyPayRs().getNumOperOmp() != null) {
					traceAssuranceVieDecouvert.setNumOperOmpRs(paramAdhesionEnv.getOperationMoyPayRs().getNumOperOmp());
				}
				crudService.create(traceAssuranceVieDecouvert);
				// *************************************//
				this.setCroFlag(true);

			}
			paramAdhesionEnv.setEtatValidation(true);
			paramAdhesionEnv.setAdhesionAssVie(adhesionAssVie);
		} catch (Exception e) {
			this.setCroFlag(false);
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans PrelevementAdhesionAssuranceVieDecouvertTrt : ");
			text.append(e.toString());
			erreur.setCode("672");
			erreur.setDescription(text.toString());
			erreur.setKey("PrelevementAdhesionAssuranceVieDecouvertTrt");
			// /*** gerer une exception
			gestionException(paramAdhesionEnv.getDateComptable(), paramAdhesionEnv.getStructure(), e);
			paramAdhesionEnv.setEtatValidation(false);
			paramAdhesionEnv.setMessageValidation(e.getMessage());
			vo.addError(erreur);
			throw new RuntimeException(e);

		}
		return (paramAdhesionEnv);

	}

	public Date getDateFinAdhesion(Date dateAgence) {
		Date dateFinAdhesion = null;
		SimpleDateFormat formaterAnnee = new SimpleDateFormat("yyyy");
		try {
			int year = Integer.valueOf(formaterAnnee.format(dateAgence));
			dateFinAdhesion = DateHandler.strToDate("31/12/" + year);
		} catch (Exception e) {
			dateFinAdhesion = null;
		}
		return dateFinAdhesion;
	}

	public Date getDateDebutAdhesion(Date dateAgence) {
		Date dateDebutAdhesion = null;
		SimpleDateFormat formaterAnnee = new SimpleDateFormat("yyyy");
		try {
			int year = Integer.valueOf(formaterAnnee.format(dateAgence));
			dateDebutAdhesion = DateHandler.strToDate("01/01/" + year);
		} catch (Exception e) {
			dateDebutAdhesion = null;
		}
		return dateDebutAdhesion;
	}

	private String gestionException(Date dateComptable, Structure agence, Exception e) {
		String messageErreur;
		try {
			BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();
			batchExeptionPlac.setDatSystBate(new Date());
			batchExeptionPlac.setDatCompBate(dateComptable);
			batchExeptionPlac.setStructure(agence);
			batchExeptionPlac.setLibTpbmBate("Exception Batch Renouvellement Prelevement Ass Vie Découvert");
			messageErreur = "Exception Batch Renouvellement : " + e.getMessage();
			batchExeptionPlac.setLibExpBate(e.getMessage());
			BatchService batchService = (BatchService) context.getBean("batchService");
			batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
		} catch (Exception ex) {
			messageErreur = "Exception Batch Renouvellement : " + ex.getMessage();
		}
		return messageErreur;
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

		if (operationMoyPay != null && operationMoyPay.getNumOperOmp() != null) {
			this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		} else {
			this.setNumRefCro(Long.valueOf(adhesionAssVie.getNumSeqAdh()));
		}

		this.setLibRefCro("SMILE.Renouvellement.Prelev.Assur.Vie.Decouvert");
		this.setDatValCro(operationMoyPay.getDatValOmp());
		this.setCodeStructInitiatrice(paramAdhesionEnv.getStructure().getCodStrcStrc().toString());
		this.setCodStrcImpt(adhesionAssVie.getContratCpt().getStructure().getCodStrcStrc());
		this.setCodEtatCro(0);
		this.setCodeProduit(Constants.COD_PRD_ASSUR_VIE_DECOUVERT.toString());
		this.setOperationId(Constants.COD_OPER_RENOUVELLEMENT_SOUSCRIPTION_ASSUR_VIE.toString());
		this.setDateOperation(operationMoyPay.getDatOperOmp());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(Constants.COD_TACH_PRELEV_ASSUR_VIE);
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
		cro.append(StrHandler.lpad(adhesionAssVie.getContratCpt().getStructure().getCodStrcStrc().toString(), '0', 3)
				+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getCodPrdPrd().toString(), '0', 4)
				+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), '0', 6)
				+ ";");

		cro.append("contratAssureur=");
		cro.append(StrHandler.lpad(paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc().toString(), '0', 3)
				+ StrHandler.lpad(paramAdhesionEnv.getNouveauCpt().getContratCptId().getCodPrdPrd().toString(), '0', 4)
				+ StrHandler.lpad(paramAdhesionEnv.getNouveauCpt().getContratCptId().getNumCcptCcpt().toString(), '0',
						6)
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

		cro.append("RefInterSiege=");
		if (paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc().intValue() != adhesionAssVie
				.getContratCpt().getStructure().getCodStrcStrc().intValue()) {
			cro.append(paramAdhesionEnv.getInterSiege() + ";");
			cro.append("IS=1;");
		} else {
			cro.append("0;");
			cro.append("IS=0;");
		}
		cro.append("COD_STRC_ASS=");
		cro.append(StrHandler.lpad(paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc().toString(), '0', 3)
				+ ";");

		cro.append("TARIF_ASS_VIE.MONT_PRMA_RTNS_TASS=");
		cro.append(Long.valueOf(Math
				.round(new Double(adhesionAssVie.getTarifAssVie().getMontComTass())
						* Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100)
				+ adhesionAssVie.getTarifAssVie().getMontPrmaTass()) + ";");

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
		operation.setCodOperOper(Constants.COD_OPER_RENOUVELLEMENT_SOUSCRIPTION_ASSUR_VIE);

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
