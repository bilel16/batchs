package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.DetailsOperationVirement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceVirement;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.InsertionOperationMoyPaySansCROTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetRibTrt;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.fwk.util.DateHandler;

public class CreationCROReaffectationRejetsVirementTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	VirementService virementService = (VirementService) context.getBean("iVirementService");
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");

	public CreationCROReaffectationRejetsVirementTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		DetailVirement detailVirementObj = new DetailVirement();

		OperationMoyPay operationMoyPay = new OperationMoyPay();
		Operation operation = new Operation();
		ContratCpt contratCptDO = new ContratCpt();
		Date dateComptable = new Date();
		boolean boolMajContratBenfif = false;

		String valueDateRecu = "";
		try {
			detailVirementObj = virementVo.getDetailVirement();
			operation = virementVo.getOperation();
			dateComptable = virementVo.getDateComptableAgence();
			contratCptDO = detailVirementObj.getGlobalVirement().getContratCpt();

			if (contratCptDO.getContratCptId() != null) {

				contratCptDO = (ContratCpt) searchEngine.get(ContratCpt.class, contratCptDO.getContratCptId());

			}
			// ************* Condition de Banque ************//
			VirementVo virementVoCB = new VirementVo();
			Operation operationCB = new Operation();
			ArrayList<String> palierChar = new ArrayList<String>();
			operationCB.setCodOperOper(Constants.COD_OPER_REAFFECTATION_REJETS_VIREMENT);
			palierChar.add("44");

			virementVoCB.setContratCpt(contratCptDO);
			virementVoCB.setOperation(operationCB);
			virementVoCB.setMontant_virement(detailVirementObj.getMntDetvDetv());
			virementVoCB.setDetailVirement(detailVirementObj);
			virementVoCB.setDateComptableAgence(dateComptable);
			virementVoCB.setListePalierCaractere(palierChar);
			virementVoCB = getConditionDeBanque(virementVoCB);

			valueDateRecu = virementVoCB.getValueDateRecue();

			PrimitiveVO primitiveVO = new PrimitiveVO();
			GetRibTrt getRibTrt = new GetRibTrt();
			primitiveVO = (PrimitiveVO) getRibTrt.exec(contratCptDO);

			String ribDO = primitiveVO.getVString();

			// / ------------------Operation Moyen de Paiement ---------------------------- ///

			operationMoyPay.setNumRibOmp(ribDO); // / Rib beneficaire

			// 00. setting num caisse and obj operationMoyPay
			// operationMoyPay.setNumeroCaisse(retraitVO.getNumSeqSjc().toString());
			operationMoyPay.setLibObjOpOmp("REAFFECTATION REJETS CLIENTS VIREMENTS");

			// 01. setting personnel initiateur et valideur
			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			operationMoyPay.setPersonnelInitiateur(personnelInit);
			operationMoyPay.setPersonnelValideur(personnelInit);

			// 02. setting structure initiatrice
			Structure structureInit = new Structure();
			//structureInit.setCodStrcStrc(new Long(detailVirementObj.getRibBenDetv().substring(5, 8)));
			structureInit.setCodStrcStrc(contratCptDO.getContratCptId().getCodStrcStrc());
			operationMoyPay.setStructureInitiatrice(structureInit);

			// 03. setting structure receptrice
			String codstructureReceptrice = "";

			codstructureReceptrice = contratCptDO.getContratCptId().getCodStrcStrc().toString();

			Structure structureRecep = new Structure();
			structureRecep.setCodStrcStrc(new Long(codstructureReceptrice));
			operationMoyPay.setStructureReceptrice(structureRecep);

			// 05. getting montant a retirer
			Long montVirCcpt = detailVirementObj.getMntDetvDetv();

			// 06. getting provision
			Long montSoldThCcpt = 0L;
			try {
				if (contratCptDO.getDatEautCcpt() != null
						&& contratCptDO.getDatEautCcpt().compareTo(dateComptable) >= 0) {

					montSoldThCcpt =
							(contratCptDO.getMontAutCcpt() + contratCptDO.getMontSoldCcpt() - contratCptDO
									.getMontBlocCcpt());
				} else {

					montSoldThCcpt = (contratCptDO.getMontSoldCcpt() - contratCptDO.getMontBlocCcpt());

				}
			} catch (Exception e) {

				montSoldThCcpt = (contratCptDO.getMontSoldCcpt() - contratCptDO.getMontBlocCcpt());
			}

			// 07. setting devise et montant
			Devise devise = new Devise();

			// 07.2 Virement en dinar
			devise.setCodDevDev(Constants.COD_DEV_DINAR);
			if (contratCptDO != null) {

				operationMoyPay.setMontDinOmp(montVirCcpt);
				operationMoyPay.setMontApreOmp(contratCptDO.getMontSoldCcpt() + montVirCcpt);
			}

			operationMoyPay.setMontSoldCcpt(contratCptDO.getMontSoldCcpt());
			operationMoyPay.setDevise(devise);

			// 08. setting contrat compte
			operationMoyPay.setContratCpt(contratCptDO);

			// 09. setting type demandeur (Titulaire, CoTitulaire, Mandataire)

			operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

			// 10. setting info Virement
			operationMoyPay.setDetailVirement(detailVirementObj);

			// 10.1 insertion du demandeur

			TypePiece typePieceDemandeur = new TypePiece();

			typePieceDemandeur.setCodTpceTpce(Long.valueOf(contratCptDO.getClient().getPersonne().getTypePiece()
					.getCodTpceTpce()));
			operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
			operationMoyPay.setNumPcedOmp(contratCptDO.getClient().getPersonne().getNumPcePers());
			operationMoyPay.setNomNomdOmp(contratCptDO.getClient().getPersonne().getNomNomPers());
			operationMoyPay.setNomPrndOmp(contratCptDO.getClient().getPersonne().getNomPrnPers());

			// 11 Preparing data of Operation and tache

			Tache tache = new Tache();
			tache.setOperation(operation);
			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(operation.getCodOperOper());

			// 11. 1 Cas de Virement Emis
			if (Constants.COD_OPER_REAFFECTATION_REJETS_VIREMENT.equals(operation.getCodOperOper())) {

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

			Date dateValOmp = DateHandler.strToDate(valueDateRecu);
			if (dateValOmp != null) {
				operationMoyPay.setDatValOmp(dateValOmp);
			} else {
				operationMoyPay.setDatValOmp(dateComptable);
			}
			// 15. setting sens operation
			operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);

			// 16. setting montant tva
			operationMoyPay.setMontTvaOmp(new Long(0));

			// 17. Insertion code ref client
			// champ obligtaoire dans la BD, valeur par defaut proposee par Chiraz CHELLY 999

			operationMoyPay.setCodRefcOmp(detailVirementObj.getDetailVirementId().getNumSeqGvir());
			operationMoyPay.setCodRefbOmp(detailVirementObj.getDetailVirementId().getNumSeqGvir());
			
			// 18. Insertion motif operation
			// valeur proposee par Chiraz CHELLY libelle operation
			operationMoyPay.setLibMotfOmp("REAFFECTATION REJETS CLIENTS VIREMENTS");

			// 19. insertion operation moyen paiement

			InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
					new InsertionOperationMoyPaySansCROTrt();
			operationMoyPay = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPay);

			logger.info("operationMoyPay :" + operationMoyPay);
			virementVo.setOperationMoyPay(operationMoyPay);
			this.setCroFlag(true);

			virementVo.setEtatInsertionCro(true);

			// *************** Update Compte Donneur d'Ordre ***********//

			VirementVo objVirementVoContratDO = new VirementVo();

			objVirementVoContratDO.setContratCpt(contratCptDO);
			objVirementVoContratDO.setStrRib("DO");

			long montantAjouterAuSolde = detailVirementObj.getMntDetvDetv().longValue();

			objVirementVoContratDO.setMontantMiseAjourSolde(montantAjouterAuSolde);
			objVirementVoContratDO.setCodeSens("CR");
			objVirementVoContratDO = (VirementVo) virementService.miseAjourSoldeContratCpt(objVirementVoContratDO);
			boolMajContratBenfif = objVirementVoContratDO.isBoolValiderContratCpt();

			if (boolMajContratBenfif == true) {

				logger.info("\n  /***** ----------- Solde Benif  MAJ  ------------------ ********/   \n");
			} else {

				logger.info("\n  /***** ----------- Solde Benif  NON MAJ  ------------------ ********/   \n");
			}
			// ************** Creation Enregistrement dans Trace Virement ***********//

			Long sequenceTraceVirement = virementGlobalDAO.getSequenceTraceVirement();
			TraceVirement traceVirement = new TraceVirement();

			traceVirement.setNumSeqTrace(sequenceTraceVirement);

			traceVirement.setDetailVirement(detailVirementObj);
			traceVirement.setOperation(operation);
			traceVirement.setDatOperTrace(dateComptable);
			traceVirement.setDatSysTrace(new Date());
			traceVirement.setTimeTrace(formaterHeure.format(new Date()));
			traceVirement.setSens(Constants.COD_SENS_RECU);
			String codStrc = detailVirementObj.getRibBenDetv().substring(5, 8);
			Structure structureEmet = new Structure();
			structureEmet.setCodStrcStrc(new Long(codStrc));
			traceVirement.setStructureEmettrice(structureEmet);
			traceVirement.setStructureReceptrice(detailVirementObj.getGlobalVirement().getStructure());
			traceVirement.setMontVirTrace(montVirCcpt);
			crudService.create(traceVirement);

			// ********** Creation Enregistrement dans Details_Operation_Virement *********//

			Long sequenceDetailsOperationVirement = virementGlobalDAO.getSequenceDetailsOperationVirement();

			DetailsOperationVirement detailsOperationVirement = new DetailsOperationVirement();

			detailsOperationVirement.setNumSeqDovi(sequenceDetailsOperationVirement);

			detailsOperationVirement.setDetailVirement(detailVirementObj);
			detailsOperationVirement.setDatOperDovi(dateComptable);

			detailsOperationVirement.setMontOpeDovi(montVirCcpt);
			detailsOperationVirement.setDatSysDovi(new Date());
			detailsOperationVirement.setNumMatrUser("9999");
			detailsOperationVirement.setCodStrcStrc(detailVirementObj.getGlobalVirement().getStructure()
					.getCodStrcStrc());
			detailsOperationVirement.setTache(tache);

			crudService.create(detailsOperationVirement);

			// ********** Fin Enregistrement dans Details_Operation_Virement *********//

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationCROReaffectationRejetsVirementTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationCROReaffectationRejetsVirementTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau CreationCROReaffectationRejetsVirementTrt : ", e);
			virementVo.setMessageValidation("Probléme dans CreationCROReaffectationRejetsVirementTrt");
			throw new RuntimeException();

		}
		return (virementVo);
	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		logger.info("starting CreationCROReaffectationRejetsVirementTrt method ");

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
		this.setLibRefCro("REAFFECTATION REJETS CLIENTS VIREMENTS");

		this.setDatValCro(operationMoyPay.getDatValOmp());
		if (operationMoyPay.getStructureInitiatrice().getCodStrcStrc() != null
				&& operationMoyPay.getStructureInitiatrice().getCodStrcStrc().longValue() != 0) {
			this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
		} else {
			this.setCodeStructInitiatrice("900");
		}
		if (operationMoyPay.getStructureReceptrice() != null
				&& operationMoyPay.getStructureReceptrice().getCodStrcStrc() != null) {
			this.setCodStrcImpt(operationMoyPay.getStructureReceptrice().getCodStrcStrc());
		} else {
			this.setCodStrcImpt(detailVirement.getGlobalVirement().getStructure().getCodStrcStrc());
		}
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

		// Montant brut de rejets virements reçus
		cro.append("MNT_BRUT_RJV_REC=");
		cro.append(detailVirement.getMntDetvDetv() + ";");

		// N° compte client émetteur
		cro.append("Numcptemt=");
		cro.append(detailVirement.getGlobalVirement().getCompteDO() + ";");

		// Numéro Remise Virement
		cro.append("COD_REM_VIR=");
		cro.append(detailVirement.getDetailVirementId().getNumSeqGvir() + ";");

		this.setCroText(cro.toString());
	}

	public VirementVo getConditionDeBanque(VirementVo virementVoCB) {

		TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();

		// /-----------------------------////
		Operation operation = new Operation();
		operation = virementVoCB.getOperation();

		ContratCpt contratCpt = new ContratCpt();
		contratCpt = virementVoCB.getContratCpt();

		String montant = null;
		montant = virementVoCB.getMontant_virement() + "";

		String tvaRecue = "";
		String tva = "";
		String commissionRecue = "";
		String valueDateRecue = "";
		String valueDateComRecue = "";
		List<String> listePalierCaractere = new ArrayList<String>(virementVoCB.getListePalierCaractere());
		// /----------------------------///

		try {

			if (listePalierCaractere != null && listePalierCaractere.size() > 0) {
				traitementConditionBanque.setPalierChar(new ArrayList<String>(listePalierCaractere));
			}
			traitementConditionBanque.setCodOperOper(operation.getCodOperOper().toString());
			traitementConditionBanque.setCodPrdPrd(contratCpt.getContratCptId().getCodPrdPrd() + "");
			traitementConditionBanque.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(contratCpt.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt().toString());
			traitementConditionBanque.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
			traitementConditionBanque.setMontant(montant);

			traitementConditionBanque.setDateReference(DateHandler.dateToStr(virementVoCB.getDateComptableAgence()));

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
			virementVoCB.setValueDateComRecue(valueDateComRecue);
			virementVoCB.setOperation(operation);

		} catch (Exception e) {
			logger.error("Error occurred when trying to get bank conditions.", e);
		}

		return virementVoCB;
	}

}