package com.bna.smile.model.prelevement.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratDomiciliations;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.NomencElemtCondition;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.InsertionOperationMoyPaySansCROTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.ContratCptSold;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.ExonerationTvaDAO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.bna.smile.model.virement.traitement.AbstractValidator;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.fwk.util.DateHandler;

public class CreationCROCreationContratDomiciliationTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	boolean etatClientTaxable = false;
	long montant_commissionRecue = 0;
	long montant_tva = 0;
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	Operation operation = new Operation();
	Structure structureCtx = new Structure();
	long numCtxClt = 0;

	public CreationCROCreationContratDomiciliationTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		PrelevementVo prelevementVo = (PrelevementVo) vo;
		OperationMoyPay operationMoyPay = new OperationMoyPay();
		ContratDomiciliations contratDomiciliations = new ContratDomiciliations();
		contratDomiciliations = prelevementVo.getContratDomiciliations();

		Date dateComptable = new Date();
		ContratCpt contratCptBenif = new ContratCpt();
		try {

			operation = prelevementVo.getOperation();
			dateComptable = prelevementVo.getDateComptable();
			contratCptBenif = prelevementVo.getContratCpt();
			// *************** Rechercher ContratCpt Bénéficiaire *********//

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");

			contratCptBenif = (ContratCpt) searchEngine.get(ContratCpt.class, contratCptBenif.getContratCptId());

			// ******************* Verifier etat contentieux du client ********************//

			if (contratCptBenif.getClient() != null && contratCptBenif.getClient().getNumCtxClt() != null
					&& contratCptBenif.getClient().getNumCtxClt() != 0) {

				numCtxClt = contratCptBenif.getClient().getNumCtxClt();
				structureCtx = contratCptBenif.getClient().getStructure();
			}

			// /-------------------- Condition de Banque Bénéficiaire ---------------------------///
			String tvaRecue = "";
			String tva = "";
			String commissionRecue = "";
			String valueDateRecue = "";
			String valueDateComRecue = "";

			TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
			traitementConditionBanque.setCodOperOper(operation.getCodOperOper().toString());
			traitementConditionBanque.setCodStrcStrc(contratCptBenif.getContratCptId().getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(contratCptBenif.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(contratCptBenif.getContratCptId().getNumCcptCcpt().toString());
			traitementConditionBanque.setDateReference(DateHandler.dateToStr(dateComptable));
			// traitementConditionBanque.setCodPrdPrd(Constants.COD_PRODUIT_PRELEVEMENTS + "");
			traitementConditionBanque.setCodPrdPrd(contratCptBenif.getContratCptId().getCodPrdPrd() + "");
			traitementConditionBanque = getConditionDeBanque(traitementConditionBanque);

			tvaRecue = String.valueOf(traitementConditionBanque.getMntTva() / 1000);
			if (tvaRecue == null || "".equals(tvaRecue.trim())) {
				tvaRecue = "0.0";
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
			montant_commissionRecue = Long.valueOf(StrHandler.strToMnt(commissionRecue));
			montant_tva = Long.valueOf(StrHandler.strToMnt(tvaRecue));

			/*************** TEST DOMICILIATION DEPUIS ASSURANCE CAPI 18-12-2023 *********/
			if (contratDomiciliations != null && contratDomiciliations.getEmetteur() != null
					&& contratDomiciliations.getEmetteur().getCodEmtrEmtr() != null
					&& contratDomiciliations.getEmetteur().getCodEmtrEmtr().longValue() == 101l) {
				
				montant_commissionRecue=0;
				montant_tva=0;

			}
			/********************* FIN TEST PRELEVEMENT DEPUIS ASSURANCE CAPI ***********/

			// *********Caracteristique Taxable du client ********//

			ExonerationTvaDAO exonerationTvaDAO = new ExonerationTvaDAO();
			ParamRechercheOpposition param = new ParamRechercheOpposition();
			param.setTypPceDemd(contratCptBenif.getClient().getPersonne().getTypePiece().getCodTpceTpce());
			param.setNumPceDemd(contratCptBenif.getClient().getPersonne().getNumPcePers());
			param.setDateDebutConsult(dateComptable);

			PrimitiveVO res = (PrimitiveVO) exonerationTvaDAO.isClientExonereTVA(param);

			if (res.isVBool() == true) {
				etatClientTaxable = false;
				montant_tva = Long.valueOf(0);
			} else {
				etatClientTaxable = true;
			}

			// / ------------------Operation Moyen de Paiement ---------------------------- ///

			// 00. setting obj operationMoyPay
			operationMoyPay.setLibObjOpOmp("RECEPTION CONTRATS DE DOMICILIATION");

			// 01. setting personnel initiateur et valideur
			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			operationMoyPay.setPersonnelInitiateur(personnelInit);
			operationMoyPay.setPersonnelValideur(personnelInit);

			// 02. setting structure initiatrice
			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(Constants.COD_DIR_TRESORERIE);
			operationMoyPay.setStructureInitiatrice(structureInit);

			// 03. setting structure receptrice
			String codstructureReceptrice = "";
			codstructureReceptrice = contratCptBenif.getContratCptId().getCodStrcStrc() + "";
			Structure structureRecep = new Structure();
			structureRecep.setCodStrcStrc(new Long(codstructureReceptrice));
			operationMoyPay.setStructureReceptrice(structureRecep);

			// 04. getting provision
			Long montSoldThCcpt = 0L;
			try {
				if (contratCptBenif.getDatEautCcpt() != null
						&& contratCptBenif.getDatEautCcpt().compareTo(dateComptable) >= 0) {

					montSoldThCcpt = (contratCptBenif.getMontAutCcpt() + contratCptBenif.getMontSoldCcpt()
							- contratCptBenif.getMontBlocCcpt());
				} else {

					montSoldThCcpt = (contratCptBenif.getMontSoldCcpt() - contratCptBenif.getMontBlocCcpt());

				}
			} catch (Exception e) {

				montSoldThCcpt = (contratCptBenif.getMontSoldCcpt() - contratCptBenif.getMontBlocCcpt());
			}
			// 05. setting devise et montant
			Devise devise = new Devise();

			// 05.1 Prelevement en dinar
			devise.setCodDevDev(Constants.COD_DEV_DINAR);
			long mntComTva = montant_commissionRecue + montant_tva;

			operationMoyPay.setMontDinOmp(Long.valueOf(montant_commissionRecue));
			operationMoyPay.setMontApreOmp(contratCptBenif.getMontSoldCcpt() - mntComTva);

			operationMoyPay.setMontSoldCcpt(contratCptBenif.getMontSoldCcpt());
			operationMoyPay.setDevise(devise);

			// 06. setting contrat compte
			operationMoyPay.setContratCpt(contratCptBenif);

			// 07. setting type demandeur (Titulaire, CoTitulaire, Mandataire)

			operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

			// 08. setting info Reference Operation
			operationMoyPay.setCodRefbOmp(contratDomiciliations.getNumContCdom() + "");
			operationMoyPay.setCodRefcOmp(contratDomiciliations.getNumContCdom() + "");

			// 09. insertion du Donneur D'ordre

			TypePiece typePieceDemandeur = new TypePiece();
			typePieceDemandeur.setCodTpceTpce(
					Long.valueOf(contratCptBenif.getClient().getPersonne().getTypePiece().getCodTpceTpce()));
			operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
			operationMoyPay.setNumPcedOmp(contratCptBenif.getClient().getPersonne().getNumPcePers());
			operationMoyPay.setNomNomdOmp(contratCptBenif.getClient().getPersonne().getNomNomPers());
			operationMoyPay.setNomPrndOmp(contratCptBenif.getClient().getPersonne().getNomPrnPers());

			// 10 Preparing data of Operation and tache
			Operation oper = new Operation();
			oper.setCodOperOper(Constants.COD_OPER_RECEP_LOT_CONTRAT_DOMICILIATION);
			Tache tache = new Tache();
			tache.setOperation(oper);
			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(Constants.COD_OPER_RECEP_LOT_CONTRAT_DOMICILIATION);
			tacheId.setCodTachTach(Constants.COD_TACH_RECEP_CONTRAT_DOMICILIATION);
			tache.setTacheId(tacheId);

			operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);

			operationMoyPay.setTache(tache);

			// 11. setting date operation moyen paiement
			Date dateOperOmp = dateComptable;

			operationMoyPay.setDatOperOmp(dateOperOmp);

			// 12. setting date valeur moyen paiement
			Date dateValOmp = null;
			if (valueDateRecue != null && valueDateRecue.length() > 0) {
				dateValOmp = formaterDate.parse(valueDateRecue);
			} else {
				dateValOmp = dateComptable;
			}
			operationMoyPay.setDatValOmp(dateValOmp);

			// 13.1 setting date system moyen paiement
			Date dateSysOmp = formaterDate.parse(formaterDate.format(new Date()));
			operationMoyPay.setDatSystOmp(dateSysOmp);

			// 13.2 setting date valeur Commission moyen paiement
			Date dateValComOmp = null;
			if (valueDateComRecue != null && valueDateComRecue.length() > 0) {
				dateValComOmp = formaterDate.parse(valueDateComRecue);
			} else {
				dateValComOmp = dateComptable;
			}
			operationMoyPay.setDateValeurCommission(dateValComOmp);

			// 14. setting sens operation
			operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);

			// 15. setting montant tva
			operationMoyPay.setMontTvaOmp(montant_tva);

			// 16. Insertion dans la table DETAIL_OPERATION_MOY_PAIEMENT

			Set<DetailOperMoyPaiement> setDetOpm = new HashSet<DetailOperMoyPaiement>();
			NomencElemtCondition nomencElemtCondition = new NomencElemtCondition();
			DetailOperMoyPaiement detailOmpCommission = new DetailOperMoyPaiement();

			if (montant_commissionRecue != 0) {

				// **** A verifier **** //
				nomencElemtCondition.setCodNecdNecd(Constants.COD_NECD_NECD_817 + "");

				detailOmpCommission.setNomencElemtCondition(nomencElemtCondition);
				detailOmpCommission.setCodTypDomp(Constants.COD_TYPE_COMMISSION);
				detailOmpCommission.setMontValDomp(new Long(montant_commissionRecue));
				detailOmpCommission.setDatValDomp(DateHandler.strToDate(valueDateComRecue));
				detailOmpCommission.setOperationMoyPay(operationMoyPay);
				setDetOpm.add(detailOmpCommission);

				// operationMoyPay.setDetailOperMoyPaiements(setDetOpm);

			}

			// 17. Insertion code ref client
			operationMoyPay.setCodRefcOmp("9999");

			// 20. Insertion motif operation
			operationMoyPay.setLibMotfOmp("RECEPTION CONTRATS DE DOMICILIATION");

			// 02. insertion operation moyen paiement

			InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
					new InsertionOperationMoyPaySansCROTrt();
			operationMoyPay = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPay);

			logger.info("operationMoyPay :" + operationMoyPay);
			prelevementVo.setOperationMoyPay(operationMoyPay);

			this.setCroFlag(true);

			// *************** Update Compte Bénéficiaire ***********//
			ContratCptSold contratCptSold = new ContratCptSold();
			UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
			contratCptSold.setContratCpt(contratCptBenif);
			contratCptSold.setSens(Constants.COD_SENS_DB);
			contratCptSold.setSolde(montant_commissionRecue + montant_tva);
			contratCptBenif = (ContratCpt) updateSoldTrt.exec(contratCptSold);
			// *******************************************************//

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationCROCreationContratDomiciliationTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationCROCreationContratDomiciliationTrt");
			prelevementVo.addError(erreur);
			logger.error("Erreur au niveau CreationCROCreationContratDomiciliationTrt : ", e);

			throw new RuntimeException(e);

		}

		return (prelevementVo);
	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		logger.info("starting CreationCROCreationContratDomiciliationTrt method ");

		PrelevementVo prelevementVo = (PrelevementVo) vo;

		OperationMoyPay operationMoyPay = new OperationMoyPay();
		operationMoyPay = prelevementVo.getOperationMoyPay();
		Operation operation = new Operation();
		operation = prelevementVo.getOperation();
		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_PRELEVEMENTS);
		ContratDomiciliations contratDomiciliations = new ContratDomiciliations();
		contratDomiciliations = prelevementVo.getContratDomiciliations();
		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */

		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		this.setLibRefCro("RECEPTION CONTRATS DE DOMICILIATION");

		if (operationMoyPay.getStructureInitiatrice().getCodStrcStrc() != null
				&& operationMoyPay.getStructureInitiatrice().getCodStrcStrc().longValue() != 0) {
			this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
		} else {
			this.setCodeStructInitiatrice("900");
		}
		this.setCodStrcImpt(operationMoyPay.getStructureReceptrice().getCodStrcStrc());
		this.setDatExecCro(new Date());
		this.setCodEtatCro(0);
		this.setCodeProduit(produit.getCodPrdPrd().toString());
		this.setOperationId(operation.getCodOperOper().toString());
		this.setDateOperation(operationMoyPay.getDatOperOmp());
		this.setDatValCro(operationMoyPay.getDatValOmp());
		this.setDatValCom(operationMoyPay.getDateValeurCommission());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(operationMoyPay.getTache().getTacheId().getCodTachTach());
		this.setCodRefcOmp(operationMoyPay.getCodRefcOmp());
		try {
			Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			com.oxia.security.abc.model.Personnel user = null;
			if (obj instanceof UserDetails) {
				user = (com.oxia.security.abc.model.Personnel) obj;
			}
		} catch (NullPointerException e) {
			logger.error(e.getMessage());
		}
		this.setNumCinUser("9999");
		this.setCodTypUser("M");
		/*
		 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
		 */

		StringBuffer cro = new StringBuffer("");

		// *** Numéro du compte client ****//
		cro.append("numCptBna=");
		cro.append(contratDomiciliations.getCompteClient() + ";");

		// *** Montant de la commission sur contrat de domiciliation ***//
		cro.append("323=");
		cro.append(montant_commissionRecue + ";");

		// *** Montant TVA sur contrat de domiciliation ***//
		cro.append("MNT_TVA_CDOM=");
		cro.append(montant_tva + ";");

		// *** Statu client taxable ****//
		cro.append("COD_TVA_CLT=");
		if (etatClientTaxable == true) {
			cro.append(new Long(0) + ";");
		} else {
			cro.append(new Long(1) + ";");
		}

		// *** Numéro du contrat de domiciliation ***//
		cro.append("NUM_CON_DOM=");
		cro.append(contratDomiciliations.getNumContCdom() + ";");

		// *** Référence dossier domiciliation ***//
		cro.append("NUM_REF_CDOM=");
		cro.append(contratDomiciliations.getNumRefCdom() + ";");

		// *** Numéro de lot de la réception ***//
		cro.append("Numlotreception=");
		cro.append(contratDomiciliations.getNumLotCdom() + ";");

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