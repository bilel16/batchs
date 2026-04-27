package com.bna.smile.model.virement.traitement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Devise;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.InsertionOperationMoyPaySansCROTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.ContratCptSold;
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
import com.oxia.fwk.util.DateHandler;

public class ExecutionVirementCompteVertTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
	IExpression expression = searchEngine.createExpression();
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeureFichier = new SimpleDateFormat("HHmmss");
	ContratCpt contratCptCompteDepot = new ContratCpt();
	ContratCpt contratCptCompteVert = new ContratCpt();
	Date dateComptable = new Date();

	public ExecutionVirementCompteVertTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		contratCptCompteDepot = virementVo.getContratCptCompteDepot();
		contratCptCompteVert = virementVo.getContratCptCompteVert();
		String dateValeur = null;
		try {

			// *********** Condition de Banque **********//
			ArrayList<String> palier = new ArrayList<String>();
			contratCptCompteDepot =
					(ContratCpt) searchEngine.get(ContratCpt.class, contratCptCompteDepot.getContratCptId());

			if (contratCptCompteDepot.getContratCptId() != null && contratCptCompteVert.getContratCptId() != null) {
				if (contratCptCompteDepot.getContratCptId().getCodStrcStrc().longValue() == contratCptCompteVert
						.getContratCptId().getCodStrcStrc().longValue()) {
					palier.add("41");

				} else {
					palier.add("42");
				}

			}

			VirementVo virementVoCB = new VirementVo();
			virementVoCB.setDateComptableAgence(dateComptable);
			virementVoCB.setContratCptCompteDepot(contratCptCompteDepot);
			virementVoCB.setContratCptCompteVert(contratCptCompteVert);
			virementVoCB.setOperation(virementVo.getOperation());
			virementVoCB.setMontant_virement(virementVo.getMontant_virement());
			virementVoCB.setListePalierCaractere(palier);
			virementVoCB = getConditionDeBanque(virementVoCB);
			dateValeur = virementVoCB.getValueDateRecue();

			// ******** Operation moyen Payement ********//

			// ********* Operation Moyen Payement ********//

			OperationMoyPay operationMoyPayCompteDO = new OperationMoyPay();

			// 00. setting obj operationMoyPayCompteDO

			operationMoyPayCompteDO.setLibObjOpOmp("Alimentation en faveur du compte vert");

			// 01. setting personnel initiateur et valideur
			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			operationMoyPayCompteDO.setPersonnelInitiateur(personnelInit);
			operationMoyPayCompteDO.setPersonnelValideur(personnelInit);

			// 02. setting structure initiatrice
			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(contratCptCompteDepot.getContratCptId().getCodStrcStrc());
			operationMoyPayCompteDO.setStructureInitiatrice(structureInit);

			// // 03. setting structure receptrice
			String codstructureReceptrice = "";
			codstructureReceptrice = contratCptCompteVert.getContratCptId().getCodStrcStrc().toString();
			Structure structureRecep = new Structure();
			structureRecep.setCodStrcStrc(new Long(codstructureReceptrice));
			operationMoyPayCompteDO.setStructureReceptrice(structureRecep);

			// 04. getting montant a retirer
			Long montVirCcpt = new Long(virementVo.getMontant_virement());

			// 06. getting provision
			Long montSoldThCcpt = 0L;
			try {
				if (contratCptCompteDepot.getDatEautCcpt() != null
						&& contratCptCompteDepot.getDatEautCcpt().compareTo(dateComptable) >= 0) {

					montSoldThCcpt =
							(contratCptCompteDepot.getMontAutCcpt() + contratCptCompteDepot.getMontSoldCcpt() - contratCptCompteDepot
									.getMontBlocCcpt());
				} else {

					montSoldThCcpt =
							(contratCptCompteDepot.getMontSoldCcpt() - contratCptCompteDepot.getMontBlocCcpt());

				}
			} catch (Exception e) {

				montSoldThCcpt = (contratCptCompteDepot.getMontSoldCcpt() - contratCptCompteDepot.getMontBlocCcpt());
			}

			// 07. setting devise et montant
			Devise devise = new Devise();

			// 07.2 Virement en dinar
			devise.setCodDevDev(Constants.COD_DEV_DINAR);
			if (contratCptCompteDepot != null) {

				operationMoyPayCompteDO.setMontDinOmp(montVirCcpt);
				operationMoyPayCompteDO.setMontApreOmp(contratCptCompteDepot.getMontSoldCcpt() - montVirCcpt);
			}

			operationMoyPayCompteDO.setMontSoldCcpt(contratCptCompteDepot.getMontSoldCcpt());
			operationMoyPayCompteDO.setDevise(devise);

			// 08. setting contrat compte
			operationMoyPayCompteDO.setContratCpt(contratCptCompteDepot);

			// 09. setting type demandeur (Titulaire, CoTitulaire, Mandataire)

			operationMoyPayCompteDO.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

			// 10.1 insertion du Demandeur

			TypePiece typePieceDemandeur = new TypePiece();
			typePieceDemandeur.setCodTpceTpce(Long.valueOf(contratCptCompteDepot.getClient().getPersonne()
					.getTypePiece().getCodTpceTpce()));
			operationMoyPayCompteDO.setTypePieceDemandeur(typePieceDemandeur);
			operationMoyPayCompteDO.setNumPcedOmp(contratCptCompteDepot.getClient().getPersonne().getNumPcePers());
			operationMoyPayCompteDO.setNomNomdOmp(contratCptCompteDepot.getClient().getPersonne().getNomNommPers());
			operationMoyPayCompteDO.setNomPrndOmp(contratCptCompteDepot.getClient().getPersonne().getNomPrnPers());

			// 11 Preparing data of Operation and tache

			Tache tache = new Tache();
			TacheId tacheId = new TacheId();
			tacheId.setCodTachTach(Constants.COD_TACH_ALIMENTATION_FAVEUR_COMPTE_DEPOT);
			tacheId.setCodOperOper(Constants.COD_OPER_ALIMENTATION_FAVEUR_COMPTE_VERT);
			tache.setOperation(virementVo.getOperation());

			// 11. 1 Cas de Virement Emis

			operationMoyPayCompteDO.setCodEtatOmp(Constants.COD_VALIDATION);
			tache.setTacheId(tacheId);

			// 12. setting tache
			operationMoyPayCompteDO.setTache(tache);

			// 13. setting date operation moyen paiement
			Date dateOperOmp = dateComptable;

			operationMoyPayCompteDO.setDatOperOmp(dateOperOmp);

			// 14. setting date valeur moyen paiement
			Date dateValOmp = null;
			try {
				if (dateValOmp != null) {
					dateValOmp = formaterDate.parse(dateValeur);
				} else {
					dateValOmp = dateComptable;
				}
			} catch (ParseException e) {
				dateValOmp = dateComptable;
			}
			operationMoyPayCompteDO.setDatValOmp(dateValOmp);
			// 14.1 setting date system moyen paiement
			Date dateSysOmp = formaterDate.parse(formaterDate.format(new Date()));
			operationMoyPayCompteDO.setDatSystOmp(dateSysOmp);

			// 15. setting sens operation
			operationMoyPayCompteDO.setCodSensOmp(Constants.COD_SENS_DB);

			// 16. Insertion code ref client
			// champ obligtaoire dans la BD, valeur par defaut proposee par Chiraz CHELLY 999

			operationMoyPayCompteDO.setCodRefcOmp("9999");

			// 17. Insertion motif operation
			// valeur proposee par Chiraz CHELLY libelle operation
			operationMoyPayCompteDO.setLibMotfOmp("Alimentation en faveur du compte vert");

			// 19. insertion operation moyen paiement

			InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
					new InsertionOperationMoyPaySansCROTrt();
			operationMoyPayCompteDO = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPayCompteDO);

			logger.info("operationMoyPayCompteDO :" + operationMoyPayCompteDO);

			// ************** Creation Cro 1204 *****************************//

			VirementVo virementVoCreationCROALimentation = new VirementVo();
			virementVoCreationCROALimentation.setDateComptableAgence(dateComptable);
			virementVoCreationCROALimentation.setContratCptCompteDepot(contratCptCompteDepot);
			virementVoCreationCROALimentation.setContratCptCompteVert(contratCptCompteVert);
			virementVoCreationCROALimentation.setOperation(virementVo.getOperation());
			virementVoCreationCROALimentation.setMontant_virement(virementVo.getMontant_virement());
			virementVoCreationCROALimentation.setValueDateRecue(dateValeur);
			virementVoCreationCROALimentation.setOperationMoyPay(operationMoyPayCompteDO);

			CreationCROAlimentationFaveurCompteVertTrt creationCROAlimentationFaveurCompteVertTrt =
					new CreationCROAlimentationFaveurCompteVertTrt();

			virementVoCreationCROALimentation =
					(VirementVo) creationCROAlimentationFaveurCompteVertTrt.exec(virementVoCreationCROALimentation);

			// ************** Creation Cro 822 *****************************//

			VirementVo virementVoCreationCROPositionVirement = new VirementVo();
			virementVoCreationCROPositionVirement.setDateComptableAgence(dateComptable);
			virementVoCreationCROPositionVirement.setContratCptCompteDepot(contratCptCompteDepot);
			virementVoCreationCROPositionVirement.setContratCptCompteVert(contratCptCompteVert);
			virementVoCreationCROPositionVirement.setOperation(virementVo.getOperation());
			virementVoCreationCROPositionVirement.setMontant_virement(virementVo.getMontant_virement());
			virementVoCreationCROPositionVirement.setValueDateRecue(dateValeur);

			CreationCROPositionVirementCompteVertTrt creationCROPositionVirementCompteVertTrt =
					new CreationCROPositionVirementCompteVertTrt();

			virementVoCreationCROPositionVirement =
					(VirementVo) creationCROPositionVirementCompteVertTrt.exec(virementVoCreationCROPositionVirement);

			// ************** Update Compte Depot ***************************//
			long montantAjouterAuSolde = 0;
			montantAjouterAuSolde = virementVo.getMontant_virement();

			UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
			ContratCptSold contratCptSold = new ContratCptSold();

			contratCptSold.setContratCpt(contratCptCompteDepot);
			contratCptSold.setSens(Constants.COD_SENS_DB);
			contratCptSold.setSolde(montantAjouterAuSolde);
			contratCptCompteDepot = (ContratCpt) updateSoldTrt.exec(contratCptSold);

			logger.info("\n  /***** ----------- Solde DO  MAJ  ------------------ ********/   \n");

			// ***************************************************************//

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans ExecutionVirementCompteVertTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("ExecutionVirementCompteVertTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau ExecutionVirementCompteVertTrt : ", e);
			virementVo.setMessageValidation("Probléme dans ExecutionVirementCompteVertTrt");
			virementVo.setStadeEnregistrement(false);
			throw new RuntimeException();

		}

		return (virementVo);

	}

	public VirementVo getConditionDeBanque(VirementVo virementVoCB) {

		TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();

		// /-----------------------------////
		Operation operation = new Operation();
		operation = virementVoCB.getOperation();

		ContratCpt contratCpt = new ContratCpt();
		contratCpt = virementVoCB.getContratCptCompteDepot();

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
				// ArrayList palierChar1 = new ArrayList();
				// palierChar1.add("26");
				traitementConditionBanque.setPalierChar(new ArrayList<String>(listePalierCaractere));
			}
			traitementConditionBanque.setCodOperOper(operation.getCodOperOper().toString());
			traitementConditionBanque.setCodPrdPrd(Constants.COD_COMPTE_VERT + "");
			traitementConditionBanque.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(contratCpt.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt().toString());
			traitementConditionBanque.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
			traitementConditionBanque.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece()
					.getCodTpceTpce()+"");
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
			if (traitementConditionBanque.getDatevaleur() != null
					&& traitementConditionBanque.getDatevaleur().equals("NAN") == false) {
				valueDateRecue = traitementConditionBanque.getDatevaleur();
			} else {
				valueDateRecue = null;
			}

			valueDateComRecue = traitementConditionBanque.getDatevaleurComm();
			virementVoCB.setTraitementConditionBanque(traitementConditionBanque);

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

	public void genCroText(ValueObject vo) {
	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

}