package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Devise;
import com.bna.commun.model.GlobalVirement;
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
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.service.BatchService;
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

public class AlimentationCompteDepotTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	VirementService virementService = (VirementService) context.getBean("iVirementService");
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");

	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");

	public AlimentationCompteDepotTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		GlobalVirement globalVirement = (GlobalVirement) virementVo.getGlobalVirement();
		Date dateComptableAgence = new Date();
		dateComptableAgence = virementVo.getDateComptableAgence();
		Structure agence = virementVo.getStructure();

		ContratCpt cptCompteVert = new ContratCpt();
		ContratCpt cptCompteDepot = new ContratCpt();

		try {
			cptCompteVert = virementVo.getContratCptCompteVert();
			cptCompteDepot = virementVo.getContratCptCompteDepot();
			long mntAlimentation = virementVo.getMntAlimentationCompteDepot();

			cptCompteVert = (ContratCpt) searchEngine.get(ContratCpt.class, cptCompteVert.getContratCptId());

			cptCompteDepot = (ContratCpt) searchEngine.get(ContratCpt.class, cptCompteDepot.getContratCptId());

			// *************Condition de Banque *******************//

			VirementVo virementVoCB1205 = new VirementVo();
			VirementVo virementVoCB1206 = new VirementVo();
			Operation operationCB1205 = new Operation();
			Operation operationCB1206 = new Operation();

			operationCB1205.setCodOperOper(Constants.COD_OPER_ALIMENTATION_FAVEUR_COMPTE_DEPOT);
			operationCB1206.setCodOperOper(Constants.COD_OPER_ALIMENTATION_APARTIR_COMPTE_VERT);

			// **********1205************//

			virementVoCB1205.setContratCpt(cptCompteVert);
			virementVoCB1205.setOperation(operationCB1205);
			virementVoCB1205.setMontant_virement(mntAlimentation);

			virementVoCB1205.setDateComptableAgence(dateComptableAgence);

			virementVoCB1205 = getConditionDeBanque(virementVoCB1205);

			// **********1206************//
			ArrayList<String> palierChar = new ArrayList<String>();

			if (cptCompteDepot.getContratCptId().getCodStrcStrc()
					.equals(cptCompteVert.getContratCptId().getCodStrcStrc())) {
				palierChar.add("41");
			} else {
				palierChar.add("42");
			}

			virementVoCB1206.setContratCpt(cptCompteDepot);
			virementVoCB1206.setOperation(operationCB1206);
			virementVoCB1206.setMontant_virement(mntAlimentation);
			virementVoCB1206.setListePalierCaractere(palierChar);
			virementVoCB1206.setDateComptableAgence(dateComptableAgence);

			virementVoCB1206 = getConditionDeBanque(virementVoCB1206);

			// ************* Update Compte Vert *****************//

			ContratCptSold contratCptSoldVert = new ContratCptSold();
			Context context = ContextHandler.getContext();
			contratCptSoldVert.setContratCpt(cptCompteVert);
			contratCptSoldVert.setSolde(mntAlimentation);
			contratCptSoldVert.setSens("D");
			CommunService communService = (CommunService) context.getBean("communService");
			cptCompteVert = (ContratCpt) communService.UpdateSoldOperationMoyPay(contratCptSoldVert);

			// ************* Update Compte Depot *****************//

			ContratCptSold contratCptSoldDepot = new ContratCptSold();
			contratCptSoldDepot.setContratCpt(cptCompteDepot);
			contratCptSoldDepot.setSolde(mntAlimentation);
			contratCptSoldDepot.setSens("C");

			cptCompteDepot = (ContratCpt) communService.UpdateSoldOperationMoyPay(contratCptSoldDepot);

			// **************** Operation Moyen Payement 1206 ***************//

			OperationMoyPay operationMoyPayCompteVert = new OperationMoyPay();

			// 00. setting obj operationMoyPayCompteVert
			operationMoyPayCompteVert.setLibObjOpOmp("ALIMENTATION A PARTIR DU COMPTE VERT");

			// 01. setting personnel initiateur et valideur
			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			operationMoyPayCompteVert.setPersonnelInitiateur(personnelInit);
			operationMoyPayCompteVert.setPersonnelValideur(personnelInit);

			// 02. setting structure initiatrice
			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(cptCompteVert.getContratCptId().getCodStrcStrc());
			operationMoyPayCompteVert.setStructureInitiatrice(structureInit);

			// 03. setting structure receptrice
			String codstructureReceptrice = "";
			codstructureReceptrice = cptCompteDepot.getContratCptId().getCodStrcStrc().toString();

			Structure structureRecep = new Structure();
			structureRecep.setCodStrcStrc(new Long(codstructureReceptrice));
			operationMoyPayCompteVert.setStructureReceptrice(structureRecep);

			// 04. getting montant a retirer
			long montVirCcpt = mntAlimentation;

			// 06. getting provision
			long montSoldThCcpt = cptCompteDepot.getMontSoldCcpt();

			// 07. setting devise et montant
			Devise devise = new Devise();

			// 07.2 Virement en dinar

			devise.setCodDevDev(Constants.COD_DEV_DINAR);

			operationMoyPayCompteVert.setMontDinOmp(montVirCcpt);
			operationMoyPayCompteVert.setMontApreOmp(cptCompteDepot.getMontSoldCcpt() + montVirCcpt);

			operationMoyPayCompteVert.setMontSoldCcpt(cptCompteDepot.getMontSoldCcpt());
			operationMoyPayCompteVert.setDevise(devise);

			// 08. setting contrat compte
			operationMoyPayCompteVert.setContratCpt(cptCompteDepot);

			// 09. setting type demandeur (Titulaire, CoTitulaire, Mandataire)

			operationMoyPayCompteVert.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

			// 10. setting info Virement
			if (globalVirement != null && globalVirement.getNumSeqGvir() != null) {
				operationMoyPayCompteVert.setGlobalVirement(globalVirement);
			}

			// 10.1 insertion du Demandeur

			TypePiece typePieceDemandeur = new TypePiece();
			typePieceDemandeur.setCodTpceTpce(Long.valueOf(cptCompteVert.getClient().getPersonne().getTypePiece()
					.getCodTpceTpce()));
			operationMoyPayCompteVert.setTypePieceDemandeur(typePieceDemandeur);
			operationMoyPayCompteVert.setNumPcedOmp(cptCompteVert.getClient().getPersonne().getNumPcePers());
			operationMoyPayCompteVert.setNomNomdOmp(cptCompteVert.getClient().getPersonne().getNomNomPers());
			operationMoyPayCompteVert.setNomPrndOmp(cptCompteVert.getClient().getPersonne().getNomPrnPers());

			// 11 Preparing data of Operation and tache
			Operation oper1206 = new Operation();
			oper1206.setCodOperOper(Constants.COD_OPER_ALIMENTATION_APARTIR_COMPTE_VERT);
			Tache tache1206 = new Tache();
			TacheId tacheId1206 = new TacheId();
			tacheId1206.setCodTachTach(Constants.COD_TACH_ALIMENTATION_FAVEUR_COMPTE_DEPOT);
			tacheId1206.setCodOperOper(Constants.COD_OPER_ALIMENTATION_APARTIR_COMPTE_VERT);
			tache1206.setOperation(oper1206);
			tache1206.setTacheId(tacheId1206);

			// 11. 1 Etat Operation Moyen de Payement
			operationMoyPayCompteVert.setCodEtatOmp(Constants.COD_VALIDATION);

			// 12. setting tache
			operationMoyPayCompteVert.setTache(tache1206);

			// 13. setting date operation moyen paiement
			Date dateOperOmp = dateComptableAgence;

			operationMoyPayCompteVert.setDatOperOmp(dateOperOmp);

			// 14. setting date valeur moyen paiement
			String dateValueRecu = virementVoCB1206.getValueDateRecue();
			Date dateValOmp = null;
			if (dateValueRecu != null && dateValueRecu.length() > 0) {
				dateValOmp = formaterDate.parse(dateValueRecu);
			} else {
				dateValOmp = dateComptableAgence;
			}

			operationMoyPayCompteVert.setDatValOmp(dateValOmp);

			// 15. setting sens operation
			operationMoyPayCompteVert.setCodSensOmp(Constants.COD_SENS_CR);

			// 16. setting montant tva
			operationMoyPayCompteVert.setMontTvaOmp(new Long(0));

			// 19. Insertion code ref client
			// champ obligtaoire dans la BD, valeur par defaut proposee par Chiraz CHELLY 999

			operationMoyPayCompteVert.setCodRefcOmp(cptCompteDepot.getContratCptId().getCompteClient());

			// 20. Insertion motif operation
			// valeur proposee par Chiraz CHELLY libelle operation
			operationMoyPayCompteVert.setLibMotfOmp("ALIMENTATION A PARTIR DU COMPTE VERT");

			// 21. insertion operation moyen paiement

			InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
					new InsertionOperationMoyPaySansCROTrt();
			operationMoyPayCompteVert =
					(OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPayCompteVert);

			logger.info("operationMoyPayAlimentation :" + operationMoyPayCompteVert);

			// **************** Operation Moyen Payement 1205 ***************//

			OperationMoyPay operationMoyPayCompteDEPOT = new OperationMoyPay();

			// 00. setting obj operationMoyPayCompteVert
			operationMoyPayCompteDEPOT.setLibObjOpOmp("ALIMENTATION EN FAVEUR DU COMPTE DEPOT");

			// 01. setting personnel initiateur et valideur
			personnelInit.setNumMatrUser("9999");
			operationMoyPayCompteDEPOT.setPersonnelInitiateur(personnelInit);
			operationMoyPayCompteDEPOT.setPersonnelValideur(personnelInit);

			// 02. setting structure initiatrice
			structureInit.setCodStrcStrc(cptCompteVert.getContratCptId().getCodStrcStrc());
			operationMoyPayCompteDEPOT.setStructureInitiatrice(structureInit);

			// 03. setting structure receptrice
			codstructureReceptrice = cptCompteDepot.getContratCptId().getCodStrcStrc().toString();

			structureRecep.setCodStrcStrc(new Long(codstructureReceptrice));
			operationMoyPayCompteDEPOT.setStructureReceptrice(structureRecep);

			// 04. getting montant a retirer
			montVirCcpt = mntAlimentation;

			// 06. getting provision
			montSoldThCcpt = cptCompteVert.getMontSoldCcpt();

			// 07. setting devise et montant
			// 07.2 Virement en dinar

			devise.setCodDevDev(Constants.COD_DEV_DINAR);

			operationMoyPayCompteDEPOT.setMontDinOmp(montVirCcpt);
			operationMoyPayCompteDEPOT.setMontApreOmp(cptCompteVert.getMontSoldCcpt() - montVirCcpt);

			operationMoyPayCompteDEPOT.setMontSoldCcpt(cptCompteVert.getMontSoldCcpt());
			operationMoyPayCompteDEPOT.setDevise(devise);

			// 08. setting contrat compte
			operationMoyPayCompteDEPOT.setContratCpt(cptCompteVert);

			// 09. setting type demandeur (Titulaire, CoTitulaire, Mandataire)

			operationMoyPayCompteDEPOT.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

			// 10. setting info Virement

			operationMoyPayCompteDEPOT.setGlobalVirement(globalVirement);

			// 10.1 insertion du Demandeur

			typePieceDemandeur.setCodTpceTpce(Long.valueOf(cptCompteVert.getClient().getPersonne().getTypePiece()
					.getCodTpceTpce()));
			operationMoyPayCompteDEPOT.setTypePieceDemandeur(typePieceDemandeur);
			operationMoyPayCompteDEPOT.setNumPcedOmp(cptCompteVert.getClient().getPersonne().getNumPcePers());
			operationMoyPayCompteDEPOT.setNomNomdOmp(cptCompteVert.getClient().getPersonne().getNomNommPers());
			operationMoyPayCompteDEPOT.setNomPrndOmp(cptCompteVert.getClient().getPersonne().getNomPrnPers());

			// 11 Preparing data of Operation and tache
			Operation oper1205 = new Operation();
			Tache tache1205 = new Tache();
			TacheId tacheId1205 = new TacheId();
			oper1205.setCodOperOper(Constants.COD_OPER_ALIMENTATION_FAVEUR_COMPTE_DEPOT);
			tacheId1205.setCodTachTach(Constants.COD_TACH_ALIMENTATION_FAVEUR_COMPTE_DEPOT);
			tacheId1205.setCodOperOper(Constants.COD_OPER_ALIMENTATION_FAVEUR_COMPTE_DEPOT);
			tache1205.setOperation(oper1205);
			tache1205.setTacheId(tacheId1205);

			// 11. 1 Etat Operation Moyen de Payement
			operationMoyPayCompteDEPOT.setCodEtatOmp(Constants.COD_VALIDATION);

			// 12. setting tache
			operationMoyPayCompteDEPOT.setTache(tache1205);

			// 13. setting date operation moyen paiement
			dateOperOmp = dateComptableAgence;

			operationMoyPayCompteDEPOT.setDatOperOmp(dateOperOmp);

			// 14. setting date valeur moyen paiement

			String dateValueRecu1205 = virementVoCB1205.getValueDateRecue();
			Date dateValOmp1205 = null;
			if (dateValueRecu1205 != null && dateValueRecu1205.length() > 0) {
				dateValOmp1205 = formaterDate.parse(dateValueRecu1205);
			} else {
				dateValOmp1205 = dateComptableAgence;
			}
			operationMoyPayCompteDEPOT.setDatValOmp(dateValOmp1205);

			// 15. setting sens operation
			operationMoyPayCompteDEPOT.setCodSensOmp(Constants.COD_SENS_DB);

			// 16. setting montant tva
			operationMoyPayCompteDEPOT.setMontTvaOmp(new Long(0));

			// 19. Insertion code ref client
			// champ obligtaoire dans la BD, valeur par defaut proposee par Chiraz CHELLY 999

			operationMoyPayCompteDEPOT.setCodRefcOmp(cptCompteVert.getContratCptId().getCompteClient());

			// 20. Insertion motif operation
			// valeur proposee par Chiraz CHELLY libelle operation
			operationMoyPayCompteDEPOT.setLibMotfOmp("ALIMENTATION EN FAVEUR DU COMPTE DEPOT");

			// 21. insertion operation moyen paiement

			operationMoyPayCompteDEPOT =
					(OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPayCompteDEPOT);

			logger.info("operationMoyPayAlimentation :" + operationMoyPayCompteDEPOT);

			// **************** Creation CRO 1205 ***************//
			VirementVo virementVoCPTDEPOT = new VirementVo();
			Operation operation = new Operation();
			operation.setCodOperOper(Constants.COD_OPER_ALIMENTATION_FAVEUR_COMPTE_DEPOT);
			Produit produit = new Produit();
			produit.setCodPrdPrd(Constants.COD_PRD_PRD_VERT);
			virementVoCPTDEPOT.setOperation(operation);
			virementVoCPTDEPOT.setProduit(produit);
			virementVoCPTDEPOT.setDateComptableAgence(dateComptableAgence);
			virementVoCPTDEPOT.setOperationMoyPay(operationMoyPayCompteDEPOT);
			virementVoCPTDEPOT.setContratCptCompteVert(cptCompteVert);
			virementVoCPTDEPOT.setContratCptCompteDepot(cptCompteDepot);
			virementVoCPTDEPOT.setMntAlimentationCompteDepot(mntAlimentation);
			virementVoCPTDEPOT =
					(VirementVo) virementService.creationCROAlimentationFaveurCompteDepot(virementVoCPTDEPOT);

			// **************** Creation CRO 1206 ***************//

			VirementVo virementVoCPTVERT = new VirementVo();
			Operation operation2 = new Operation();
			operation2.setCodOperOper(Constants.COD_OPER_ALIMENTATION_APARTIR_COMPTE_VERT);
			Produit produit2 = new Produit();
			produit2.setCodPrdPrd(Constants.COD_COMPTE_CHEQUE);
			virementVoCPTVERT.setOperation(operation2);
			virementVoCPTVERT.setProduit(produit2);
			virementVoCPTVERT.setDateComptableAgence(dateComptableAgence);
			virementVoCPTVERT.setOperationMoyPay(operationMoyPayCompteVert);
			virementVoCPTVERT.setContratCptCompteDepot(cptCompteDepot);
			virementVoCPTVERT.setContratCptCompteVert(cptCompteVert);
			virementVoCPTVERT.setMntAlimentationCompteDepot(mntAlimentation);
			virementVoCPTVERT =
					(VirementVo) virementService.creationCROAlimentationAPartirCompteVert(virementVoCPTVERT);

			// *********************************************************//

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans AlimentationCompteDepotTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("AlimentationCompteDepotTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau AlimentationCompteDepotTrt : ", e);
			virementVo.setMessageValidation("Probléme dans AlimentationCompteDepotTrt");
			gestionException(dateComptableAgence, agence, e);
			throw new RuntimeException();

		}
		return (virementVo);
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	private void gestionException(Date dateComptable, Structure agence, Exception e) {

		BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();
		batchExeptionPlac.setDatSystBate(new Date());
		batchExeptionPlac.setDatCompBate(dateComptable);
		batchExeptionPlac.setStructure(agence);
		batchExeptionPlac.setLibTpbmBate("Exception Batch Virement");
		batchExeptionPlac.setLibExpBate(e.getMessage());
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
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

		// ParamAgence paramAgence = null;
		String tvaRecue = "";
		String tva = "";
		String commissionRecue = "";
		String valueDateRecue = "";
		String valueDateComRecue = "";
		List<String> listePalierCaractere = virementVoCB.getListePalierCaractere();
		// /----------------------------///

		try {

			if (listePalierCaractere != null && listePalierCaractere.size() > 0) {
				// ArrayList palierChar1 = new ArrayList();
				// palierChar1.add("26");
				traitementConditionBanque.setPalierChar(new ArrayList<String>(listePalierCaractere));
			}
			traitementConditionBanque.setCodOperOper(operation.getCodOperOper().toString());
			traitementConditionBanque.setCodPrdPrd(contratCpt.getContratCptId().getCodPrdPrd() + "");
			traitementConditionBanque.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(contratCpt.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt().toString());
			traitementConditionBanque.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
			traitementConditionBanque.setMontant(montant);
			traitementConditionBanque.setNbUnites("1");
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