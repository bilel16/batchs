package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.EnvoiCtx;
import com.bna.commun.model.NomencElemtCondition;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.service.CommunService;
import com.bna.commun.traitements.InsertionOperationMoyPaySansCROTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.ContratCptSold;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.ExonerationTvaDAO;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecommun.traitement.GetRibTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.fwk.util.DateHandler;

public class CreationCROPositionVirementCompteVertTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
	boolean etatBenifTaxable = false;
	long montant_commissionRecue = 0;
	long montant_tva = 0;
	String dateValueRecu = "";
	String dateValueComRecu = "";
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");

	Operation operation = new Operation();
	Structure structureCtx = new Structure();
	long numCtxClt = 0;
	String codRefInter = "";

	public CreationCROPositionVirementCompteVertTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		OperationMoyPay operationMoyPay = new OperationMoyPay();

		Date dateComptable = new Date();
		VirementVo virementVoCB = new VirementVo();
		boolean boolMajContratBenfif = false;
		ContratCpt contratCptVert = new ContratCpt();
		ContratCpt contratCptDepot = new ContratCpt();
		contratCptVert = virementVo.getContratCptCompteVert();
		contratCptDepot = virementVo.getContratCptCompteDepot();
		try {

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");

			contratCptVert = (ContratCpt) searchEngine.get(ContratCpt.class, contratCptVert.getContratCptId());

			dateComptable = virementVo.getDateComptableAgence();

			if (contratCptVert.getClient() != null && contratCptVert.getClient().getNumCtxClt() != null
					&& contratCptVert.getClient().getNumCtxClt() != 0
					&& contratCptVert.getClient().getCodEtatClt().equalsIgnoreCase("C")) {

				numCtxClt = contratCptVert.getClient().getNumCtxClt();
				structureCtx = contratCptVert.getClient().getStructure();
			}

			if (contratCptVert.getClient() != null && contratCptVert.getClient().getPersonne() != null
					&& contratCptVert.getClient().getPersonne().getTypePiece() != null
					&& contratCptVert.getClient().getPersonne().getTypePiece().getCodTpceTpce() != null) {

				virementVoCB.setTypePiece(contratCptVert.getClient().getPersonne().getTypePiece().getCodTpceTpce());
				virementVoCB.setNumPcePers(contratCptVert.getClient().getPersonne().getNumPcePers());

			}
			// /-------------------- Condition de Banque Bénéficiaire ---------------------------///

			ArrayList<String> palierChar1 = new ArrayList<String>();
			boolean etatVirementMemeAgence = false;
			boolean etatVirementAutreAgence = false;
			boolean etatVirementAutreBanque = false;
			Operation operationCB = new Operation();
			operationCB.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT);
			operation.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT);
			if (contratCptVert.getContratCptId().getCodStrcStrc().longValue() == contratCptDepot.getContratCptId()
					.getCodStrcStrc().longValue()) {
				etatVirementMemeAgence = true;
				virementVo.setEtatBenifMemeAgence(true);

			} else {
				etatVirementAutreAgence = true;
			}
			if (etatVirementMemeAgence == true) {
				palierChar1.add("41");
				palierChar1.add("46");

			} else if (etatVirementAutreAgence == true) {
				palierChar1.add("42");
				palierChar1.add("46");

			} else if (etatVirementAutreBanque == true) {
				palierChar1.add("43");
				palierChar1.add("47");
			}
			virementVoCB.setListePalierCaractere(palierChar1);
			virementVoCB.setDateComptableAgence(dateComptable);
			virementVoCB.setOperation(operationCB);
			virementVoCB.setContratCpt(contratCptVert);
			virementVoCB.setMontant_virement(virementVo.getMontant_virement());
			virementVoCB = getConditionDeBanque(virementVoCB);
			montant_commissionRecue = new Long(StrHandler.strToMnt(virementVoCB.getCommissionRecue()));
			montant_tva = new Long(StrHandler.strToMnt(virementVoCB.getTvaRecue()));
			dateValueRecu = virementVoCB.getValueDateRecue();
			dateValueComRecu = virementVoCB.getValueDateComRecue();

			// *********Caracteristique Taxable du client ********//

			ExonerationTvaDAO exonerationTvaDAO = new ExonerationTvaDAO();
			ParamRechercheOpposition param = new ParamRechercheOpposition();
			param.setTypPceDemd(contratCptVert.getClient().getPersonne().getTypePiece().getCodTpceTpce());
			param.setNumPceDemd(contratCptVert.getClient().getPersonne().getNumPcePers());
			param.setDateDebutConsult(dateComptable);

			PrimitiveVO res = (PrimitiveVO) exonerationTvaDAO.isClientExonereTVA(param);

			if (res.isVBool() == true) {
				etatBenifTaxable = false;
			} else {
				etatBenifTaxable = true;
			}

			// / ------------------Operation Moyen de Paiement ---------------------------- ///

			// 00. setting obj operationMoyPay
			operationMoyPay.setLibObjOpOmp("POSITION VIREMENTS");

			// 01. setting personnel initiateur et valideur
			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			operationMoyPay.setPersonnelInitiateur(personnelInit);
			operationMoyPay.setPersonnelValideur(personnelInit);

			// 02. setting structure initiatrice
			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(contratCptDepot.getContratCptId().getCodStrcStrc());
			operationMoyPay.setStructureInitiatrice(structureInit);

			// 03. setting structure receptrice
			String codstructureReceptrice = "";
			codstructureReceptrice = contratCptVert.getContratCptId().getCodStrcStrc().toString();

			Structure structureRecep = new Structure();
			structureRecep.setCodStrcStrc(new Long(codstructureReceptrice));
			operationMoyPay.setStructureReceptrice(structureRecep);

			// 05. getting montant a retirer
			Long montVirCcpt = virementVo.getMontant_virement();

			// 06. getting provision
			Long montSoldThCcpt = contratCptVert.getMontSoldCcpt();

			// 07. setting devise et montant
			Devise devise = new Devise();

			// 07.2 Virement en dinar
			devise.setCodDevDev(Constants.COD_DEV_DINAR);

			operationMoyPay.setMontDinOmp(montVirCcpt);
			operationMoyPay.setMontApreOmp(contratCptVert.getMontSoldCcpt() + montVirCcpt);

			operationMoyPay.setMontSoldCcpt(contratCptVert.getMontSoldCcpt());
			operationMoyPay.setDevise(devise);

			// 08. setting contrat compte
			operationMoyPay.setContratCpt(contratCptVert);

			// 09. setting type demandeur (Titulaire, CoTitulaire, Mandataire)
			operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

			// 10.1 insertion du Donneur D'ordre

			TypePiece typePieceDemandeur = new TypePiece();
			typePieceDemandeur.setCodTpceTpce(Long.valueOf(contratCptVert.getClient().getPersonne().getTypePiece()
					.getCodTpceTpce()));
			operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
			operationMoyPay.setNumPcedOmp(contratCptVert.getClient().getPersonne().getNumPcePers());
			operationMoyPay.setNomNomdOmp(contratCptVert.getClient().getPersonne().getNomNomPers());
			operationMoyPay.setNomPrndOmp(contratCptVert.getClient().getPersonne().getNomPrnPers());

			// 11 Preparing data of Operation and tache
			Operation oper = new Operation();
			oper.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT);
			Tache tache = new Tache();
			tache.setOperation(oper);
			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT);

			// 11. 1 Cas de Virement Emis
			if (Constants.COD_OPER_POSITION_VIREMENT.equals(oper.getCodOperOper())) {

				operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);

				tacheId.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PONCTUEL);
				tache.setTacheId(tacheId);

			}
			// 12. setting tache
			operationMoyPay.setTache(tache);

			// 13. setting date operation moyen paiement
			Date dateOperOmp = dateComptable;

			operationMoyPay.setDatOperOmp(dateOperOmp);

			// 14. setting date valeur moyen paiement
			Date dateValOmp = null;
			if (dateValueRecu != null && dateValueRecu.length() > 0) {
				dateValOmp = formaterDate.parse(dateValueRecu);
			} else {
				dateValOmp = dateComptable;
			}
			operationMoyPay.setDatValOmp(dateValOmp);

			// 14.1 setting date system moyen paiement
			Date dateSysOmp = formaterDate.parse(formaterDate.format(new Date()));
			operationMoyPay.setDatSystOmp(dateSysOmp);

			// 14.2 setting date valeur Commission moyen paiement
			Date dateValComOmp = null;
			if (dateValueComRecu != null && dateValueComRecu.length() > 0) {
				dateValComOmp = formaterDate.parse(dateValueComRecu);
			} else {
				dateValComOmp = dateComptable;
			}
			operationMoyPay.setDateValeurCommission(dateValComOmp);

			// 15. setting sens operation
			operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);

			// 16. setting montant tva
			operationMoyPay.setMontTvaOmp((long) (virementVoCB.getTraitementConditionBanque().getMntTva()));

			// 17. Insertion dans la table DETAIL_OPERATION_MOY_PAIEMENT

			Set<DetailOperMoyPaiement> setDetOpm = new HashSet<DetailOperMoyPaiement>();
			NomencElemtCondition nomencElemtCondition = new NomencElemtCondition();
			DetailOperMoyPaiement detailOmpCommission = new DetailOperMoyPaiement();

			if (montant_commissionRecue != 0 && numCtxClt == 0) {

				// **** A verifier **** //
				nomencElemtCondition.setCodNecdNecd(Constants.COD_NECD_NECD_822 + "");

				detailOmpCommission.setNomencElemtCondition(nomencElemtCondition);
				detailOmpCommission.setCodTypDomp(Constants.COD_TYPE_COMMISSION);
				detailOmpCommission.setMontValDomp(new Long(montant_commissionRecue));
				detailOmpCommission.setDatValDomp(DateHandler.strToDate(virementVoCB.getValueDateComRecue()));
				detailOmpCommission.setOperationMoyPay(operationMoyPay);
				setDetOpm.add(detailOmpCommission);

				operationMoyPay.setDetailOperMoyPaiements(setDetOpm);

			}

			// 19. Insertion code ref client
			// champ obligtaoire dans la BD, valeur par defaut proposee par Chiraz CHELLY 999

			operationMoyPay.setCodRefcOmp(contratCptVert.getContratCptId().getCompteClient().replace(" ", ""));

			// 20. Insertion motif operation
			// valeur proposee par Chiraz CHELLY libelle operation
			operationMoyPay.setLibMotfOmp("POSITION VIREMENTS");

			// 02. insertion operation moyen paiement

			InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
					new InsertionOperationMoyPaySansCROTrt();
			operationMoyPay = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPay);

			logger.info("operationMoyPay :" + operationMoyPay);

			this.setCroFlag(true);
			virementVo.setEtatInsertionCro(true);

			// *************** Update Compte Bénéficiaire ***********//

			VirementVo objVirementVoContratBenif = new VirementVo();

			objVirementVoContratBenif.setContratCpt(contratCptVert);
			objVirementVoContratBenif.setStrRib("BENIF");

			// *************** Update Compte Bénéficiaire ***********//
			if (numCtxClt == 0) {

				long montantAjouterAuSolde = 0;
				if (etatBenifTaxable == true) {
					montantAjouterAuSolde = montVirCcpt - montant_commissionRecue - montant_tva;
				} else {
					montantAjouterAuSolde = montVirCcpt - montant_commissionRecue;
				}

				Context context = ContextHandler.getContext();
				CommunService communService = (CommunService) context.getBean("communService");
				ContratCptSold contratCptSold = new ContratCptSold();
				contratCptSold.setContratCpt(contratCptVert);
				contratCptSold.setSens(Constants.COD_SENS_CR);
				contratCptSold.setSolde(montantAjouterAuSolde);
				contratCptVert = (ContratCpt) communService.UpdateSoldOperationMoyPay(contratCptSold);

			}

			if (numCtxClt != 0) {
				long numSeqEnvoi = virementGlobalDAO.getSequenceEnvoiCTX();
				EnvoiCtx envoiCtx = new EnvoiCtx();
				envoiCtx.setNumSeqEctx(numSeqEnvoi);
				envoiCtx.setDatOperEctx(dateComptable);
				envoiCtx.setStructureCTX(structureCtx);
				envoiCtx.setNumCtxEctx(numCtxClt + "");
				envoiCtx.setNomNomEctx(contratCptVert.getNomIntiCcpt());
				envoiCtx.setMntOperEctx(montVirCcpt);
				envoiCtx.setOperation(operation);
				Structure structure = new Structure();
				structure.setCodStrcStrc(contratCptDepot.getContratCptId().getCodStrcStrc());
				envoiCtx.setStructureInitiatrice(structure);
				envoiCtx.setDatValEctx(formaterDate.parse(dateValueRecu));
				GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
				codRefInter = generateReferenceInterSiege.getRISWithUpdate(structure.getCodStrcStrc(), dateComptable);
				envoiCtx.setCodRefiEctx(codRefInter);
				envoiCtx.setNumOperOmp(operationMoyPay.getNumOperOmp());
				if (contratCptVert != null && contratCptVert.getContratCptId() != null) {
					envoiCtx.setContratCpt(contratCptVert);
				}

				crudService.create(envoiCtx);

			}

			virementVo.setOperationMoyPay(operationMoyPay);
			virementVo.setNumCtxClt(numCtxClt);
			virementVo.setStructureCTX(structureCtx);

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationCROPositionVirementTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationCROPositionVirementTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau CreationCROPositionVirementTrt : ", e);
			virementVo.setMessageValidation("Probléme dans CreationCROPositionVirementTrt");
			throw new RuntimeException();

		}
		return (virementVo);

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		logger.info("starting CreationCROPositionVirementTrt method ");

		VirementVo virementVo = (VirementVo) vo;
		OperationMoyPay operationMoyPay = new OperationMoyPay();
		operationMoyPay = virementVo.getOperationMoyPay();
		Operation operation = new Operation();
		operation.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT);
		ContratCpt contratCptVert = new ContratCpt();
		ContratCpt contratCptDepot = new ContratCpt();
		contratCptVert = virementVo.getContratCptCompteVert();
		contratCptDepot = virementVo.getContratCptCompteDepot();
		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_VIREMENT_PERMANENT);
		// produit = virementVo.getProduit();
		boolean etatBenifMemeAgence = virementVo.isEtatBenifMemeAgence();
		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */

		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		this.setLibRefCro("POSITION VIREMENTS");

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
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.oxia.security.abc.model.Personnel user = null;
		if (obj instanceof UserDetails) {
			user = (com.oxia.security.abc.model.Personnel) obj;
		}
		this.setNumCinUser("9999");
		this.setCodTypUser("M");
		this.setCodRefInter(codRefInter);
		/*
		 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
		 */

		StringBuffer cro = new StringBuffer("");

		// *** Montant brut du virement reçu par client ****//
		cro.append("MNT_BRUT_VIR_CLT=");
		cro.append(virementVo.getMontant_virement() + ";");

		// *** Montant de la commission sur virement reçu ****//
		cro.append("MNT_COM_VIR_CLT=");
		if (virementVo.getNumCtxClt() == 0) {
			cro.append(montant_commissionRecue + ";");
		} else {
			cro.append(new Long(0) + ";");
		}
		// *** Montant de la TVA ****//
		cro.append("MNT_TVA_VIR_CLT=");
		if (virementVo.getNumCtxClt() == 0) {
			cro.append(montant_tva + ";");
		} else {
			cro.append(new Long(0) + ";");
		}
		// *** Montant brut de la commission ****//
		cro.append("MNT_BRUT_COM=");
		long MNT_BRUT_COM = 0;
		if (virementVo.getNumCtxClt() == 0) {
			if (etatBenifTaxable == true) {
				MNT_BRUT_COM = virementVo.getMontant_virement() + montant_commissionRecue;
			} else {
				MNT_BRUT_COM = virementVo.getMontant_virement() + montant_commissionRecue + montant_tva;
			}
		} else {
			MNT_BRUT_COM = virementVo.getMontant_virement();
		}
		cro.append(MNT_BRUT_COM + ";");

		// *** Compte client bénéficiaire ****//
		cro.append("Numcptben=");
		String compte = "";

		if (contratCptVert != null) {
			if (contratCptVert.getContratCptId() != null) {

				compte = contratCptVert.getContratCptId().getCompteClient().replace(" ", "");
			}
		}
		cro.append(compte + ";");

		// *** Etat du compte bénéficiaire ****//
		cro.append("ETAT_CPT=");
		if (virementVo.getNumCtxClt() == 0) {
			cro.append(new Long(2) + ";");
		} else {
			cro.append(new Long(6) + ";");
		}
		// *** Type de compte ****//
		cro.append("TYPE_CPT=");
		cro.append(new Long(1) + ";");

		// *** Statu client taxable ****//
		cro.append("COD_TVA_CLT=");
		if (etatBenifTaxable == true) {
			cro.append(new Long(0) + ";");
		} else {
			cro.append(new Long(1) + ";");
		}

		// *** Montant Cours BBE brut du virement reçu ****//
		cro.append("NMNT_BRUT_VIR_CLI=");
		cro.append(new Long(0) + ";");

		// *** Code devise du compte ****//
		cro.append("COD_DEV_CPT=");
		cro.append(operationMoyPay.getDevise().getCodDevDev() + ";");

		// *** Taux variable BBE du jour ****//
		cro.append("TAUX_VAR_BBE=");
		cro.append(new Long(0) + ";");

		// *** Taux fixe BBE de l’année ****//
		cro.append("TAUX_FIX_BBE=");
		cro.append(new Long(0) + ";");

		// *** Montant du virement en devise ****//
		cro.append("MNT_DEV_VIR=");
		cro.append(new Long(0) + ";");

		// *** Référence Inter siège 9101 ****//
		cro.append("Refis9101=");
		cro.append(new Long(0) + ";");

		// *** Référence Inter siège 9102 ****//
		cro.append("Refis9102=");
		cro.append(new Long(0) + ";");

		// *** Nom donneur d’ordre ****//
		cro.append("donneur_ ordre=");
		cro.append(contratCptDepot.getNomIntiCcpt() + ";");

		// *** Rib Bénéficiaire****//
		cro.append("rib_benef=");
		PrimitiveVO primitiveVO = new PrimitiveVO();
		GetRibTrt getRibTrt = new GetRibTrt();
		primitiveVO = (PrimitiveVO) getRibTrt.exec(contratCptVert);
		cro.append(primitiveVO.getVString() + ";");

		// *** Rib Tireur ****//
		cro.append("rib_tireur=");
		primitiveVO = (PrimitiveVO) getRibTrt.exec(contratCptDepot);
		cro.append(primitiveVO.getVString() + ";");

		// Etat du bénéficiaire par rapport au donneur d'ordre (même agence ou agence différente)
		cro.append("ETAT_STRC_BENIF=");
		if (etatBenifMemeAgence == true) {

			cro.append(new Long(0) + ";");

		} else {

			cro.append(new Long(1) + ";");
		}

		// Code structure receptrice
		cro.append("cod_strc_recep=");
		if (virementVo.getNumCtxClt() != 0) {
			if (virementVo.getStructureCTX() != null && virementVo.getStructureCTX().getCodStrcStrc() != null) {
				cro.append(virementVo.getStructureCTX().getCodStrcStrc().longValue() + ";");
			}

		} else {
			cro.append(contratCptVert.getContratCptId().getCodStrcStrc() + ";");
		}

		this.setCroText(cro.toString());
	}

	public VirementVo getConditionDeBanque(VirementVo virementVoCB) {

		TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();

		ContratCpt contratCpt = new ContratCpt();
		contratCpt = virementVoCB.getContratCpt();
		// /-----------------------------////
		Operation operation = new Operation();
		operation.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT);

		String montant = null;
		montant = virementVoCB.getMontant_virement() + "";

		String tvaRecue = "";
		String tva = "";
		String commissionRecue = "";
		String valueDateRecue = "";
		String valueDateComRecue = "";
		// /----------------------------///

		List<String> listePalierCaractere = virementVoCB.getListePalierCaractere();
		// /----------------------------///

		try {

			if (listePalierCaractere != null && listePalierCaractere.size() > 0) {
				traitementConditionBanque.setPalierChar(new ArrayList<String>(listePalierCaractere));
			}

			traitementConditionBanque.setCodOperOper(operation.getCodOperOper().toString());
			traitementConditionBanque.setCodPrdPrd(Constants.COD_COMPTE_VERT + "");
			traitementConditionBanque.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(contratCpt.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt().toString());

			traitementConditionBanque.setMontant(montant);

			traitementConditionBanque.setDateReference(DateHandler.dateToStr(virementVoCB.getDateComptableAgence()));

			if (virementVoCB.getTypePiece() != null && virementVoCB.getNumPcePers() != null) {

				traitementConditionBanque.setCodTpceTpce(virementVoCB.getTypePiece() + "");
				traitementConditionBanque.setNumPcePers(virementVoCB.getNumPcePers());
			}

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
			virementVoCB.setTraitementConditionBanque(traitementConditionBanque);
			if (valueDateRecue != null && valueDateRecue.equals("NAN")) {
				valueDateRecue = null;
			}

			if (valueDateComRecue != null && valueDateComRecue.equals("NAN")) {
				valueDateComRecue = null;
			}
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