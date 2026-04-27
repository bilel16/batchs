package com.bna.smile.model.prelevement.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.DetailsPrelevements;
import com.bna.commun.model.Devise;
import com.bna.commun.model.MvtPrelevements;
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

public class CreationCRORejetPrelevementsRecusTrt extends Traitement {

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

	public CreationCRORejetPrelevementsRecusTrt() {
	}

	public IValueObject perform(IValueObject vo) {
		PrelevementVo prelevementVo = new PrelevementVo();
		prelevementVo = (PrelevementVo) vo;
		OperationMoyPay operationMoyPay = new OperationMoyPay();
		DetailsPrelevements detailsPrelevements = new DetailsPrelevements();
		detailsPrelevements = prelevementVo.getDetailsPrelevements();
		Date dateComptable = new Date();
		ContratCpt contratCptTireur = new ContratCpt();
		try {

			operation = prelevementVo.getOperation();
			dateComptable = prelevementVo.getDateComptable();
			contratCptTireur = prelevementVo.getContratCpt();
			Long codeRejet = prelevementVo.getCodeRejet();

			if (codeRejet != null
					&& (codeRejet.longValue() == Constants.COD_REJET_ABS_PROVISION.longValue() || codeRejet.longValue() == Constants.COD_REJET_INSUFF_PROVISION
							.longValue())) {

				// *************** Rechercher ContratCpt Bénéficiaire *********//

				ISearchEngine searchEngine =
						(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");

				contratCptTireur = (ContratCpt) searchEngine.get(ContratCpt.class, contratCptTireur.getContratCptId());

				// /-------------------- Condition de Banque Bénéficiaire ---------------------------///
				String tvaRecue = "";
				String tva = "";
				String commissionRecue = "";
				String valueDateRecue = "";
				String valueDateComRecue = "";

				TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
				traitementConditionBanque.setCodOperOper(operation.getCodOperOper().toString());
				// traitementConditionBanque.setCodPrdPrd(Constants.COD_PRODUIT_PRELEVEMENTS + "");
				traitementConditionBanque.setCodPrdPrd(contratCptTireur.getContratCptId().getCodPrdPrd() + "");
				traitementConditionBanque
						.setCodStrcStrc(contratCptTireur.getContratCptId().getCodStrcStrc().toString());
				traitementConditionBanque.setCodPrdCpt(contratCptTireur.getContratCptId().getCodPrdPrd().toString());
				traitementConditionBanque
						.setNumCcptCcpt(contratCptTireur.getContratCptId().getNumCcptCcpt().toString());
				traitementConditionBanque.setDateReference(DateHandler.dateToStr(detailsPrelevements
						.getDetailsPrelevementsId().getDatOpePrl()));
				traitementConditionBanque
						.setMontant(detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl() + "");

				if (contratCptTireur.getClient().getPersonne() != null
						&& contratCptTireur.getClient().getPersonne().getTypePiece() != null
						&& contratCptTireur.getClient().getPersonne().getNumPcePers() != null) {

					traitementConditionBanque.setCodTpceTpce(contratCptTireur.getClient().getPersonne().getTypePiece()
							.getCodTpceTpce()
							+ "");
					traitementConditionBanque.setNumPcePers(contratCptTireur.getClient().getPersonne().getNumPcePers());
				}

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
				prelevementVo.setValueDateRecu(valueDateRecue);
				

				/*************** TEST PRELEVEMENT DEPUIS ASSURANCE CAPI 18-12-2023 *********/
				// String ribBenif = detailsPrelevements.getDetailsPrelevementsId().getRibBenPrl();
				// if (ribBenif.equals("03045175011500408965")) {
				// montant_commissionRecue=0;
				// montant_tva=0;
				// }
				/*********************FIN TEST PRELEVEMENT DEPUIS ASSURANCE CAPI ***********/
				// *********Caracteristique Taxable du client ********//
				try {

					ExonerationTvaDAO exonerationTvaDAO = new ExonerationTvaDAO();
					ParamRechercheOpposition param = new ParamRechercheOpposition();
					param.setTypPceDemd(contratCptTireur.getClient().getPersonne().getTypePiece().getCodTpceTpce());
					param.setNumPceDemd(contratCptTireur.getClient().getPersonne().getNumPcePers());
					param.setDateDebutConsult(dateComptable);

					PrimitiveVO res = (PrimitiveVO) exonerationTvaDAO.isClientExonereTVA(param);

					if (res.isVBool() == true) {
						etatClientTaxable = false;
						montant_tva = Long.valueOf(0);
					} else {
						etatClientTaxable = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}

				// / ------------------Operation Moyen de Paiement ---------------------------- ///

				// 00. setting obj operationMoyPay
				operationMoyPay.setLibObjOpOmp("REJET DES PRELEVEMENTS");

				// 01. setting personnel initiateur et valideur
				Personnel personnelInit = new Personnel();
				personnelInit.setNumMatrUser("9999");
				operationMoyPay.setPersonnelInitiateur(personnelInit);
				operationMoyPay.setPersonnelValideur(personnelInit);

				// 02. setting structure initiatrice
				Structure structureInit = new Structure();
				structureInit.setCodStrcStrc(contratCptTireur.getContratCptId().getCodStrcStrc());
				operationMoyPay.setStructureInitiatrice(structureInit);

				// 03. setting structure receptrice
				String codstructureReceptrice = "";
				codstructureReceptrice = prelevementVo.getCodeStructureReceptrice() + "";
				Structure structureRecep = new Structure();
				structureRecep.setCodStrcStrc(new Long(codstructureReceptrice));
				operationMoyPay.setStructureReceptrice(structureRecep);

				// 05. getting montant a retirer
				long montPrelCcpt = 0;
				if (etatClientTaxable == true) {
					montPrelCcpt = montant_commissionRecue + montant_tva;
				} else {
					montPrelCcpt = montant_commissionRecue;
				}
				// 04. getting provision

				Long montSoldThCcpt = 0L;
				try {
					if (contratCptTireur.getDatEautCcpt() != null
							&& contratCptTireur.getDatEautCcpt().compareTo(dateComptable) >= 0) {

						montSoldThCcpt =
								(contratCptTireur.getMontAutCcpt() + contratCptTireur.getMontSoldCcpt() - contratCptTireur
										.getMontBlocCcpt());
					} else {

						montSoldThCcpt = (contratCptTireur.getMontSoldCcpt() - contratCptTireur.getMontBlocCcpt());

					}
				} catch (Exception e) {

					montSoldThCcpt = (contratCptTireur.getMontSoldCcpt() - contratCptTireur.getMontBlocCcpt());
				}
				// 05. setting devise et montant
				Devise devise = new Devise();

				// 05.1 Prelevement en dinar
				devise.setCodDevDev(Constants.COD_DEV_DINAR);
				if (contratCptTireur != null) {

					operationMoyPay.setMontDinOmp(Long.valueOf(montant_commissionRecue));
					operationMoyPay.setMontApreOmp(contratCptTireur.getMontSoldCcpt() - montPrelCcpt);
				}

				operationMoyPay.setMontSoldCcpt(contratCptTireur.getMontSoldCcpt());
				operationMoyPay.setDevise(devise);

				// 06. setting contrat compte
				operationMoyPay.setContratCpt(contratCptTireur);

				// 07. setting type demandeur (Titulaire, CoTitulaire, Mandataire)

				operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

				// 08. setting info Reference Operation
				operationMoyPay.setCodRefbOmp(detailsPrelevements.getDetailsPrelevementsId().getNumPrlPrl() + "");
				operationMoyPay.setCodRefcOmp(detailsPrelevements.getDetailsPrelevementsId().getNumPrlPrl() + "");

				// 09. insertion du Donneur D'ordre

				TypePiece typePieceDemandeur = new TypePiece();
				typePieceDemandeur.setCodTpceTpce(Long.valueOf(contratCptTireur.getClient().getPersonne()
						.getTypePiece().getCodTpceTpce()));
				operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
				operationMoyPay.setNumPcedOmp(contratCptTireur.getClient().getPersonne().getNumPcePers());
				operationMoyPay.setNomNomdOmp(contratCptTireur.getClient().getPersonne().getNomNomPers());
				operationMoyPay.setNomPrndOmp(contratCptTireur.getClient().getPersonne().getNomPrnPers());

				// 10 Preparing data of Operation and tache
				Operation oper = new Operation();
				oper.setCodOperOper(Constants.COD_OPER_REJET_PRELEVEMENT);
				Tache tache = new Tache();
				tache.setOperation(oper);
				TacheId tacheId = new TacheId();
				tacheId.setCodOperOper(Constants.COD_OPER_REJET_PRELEVEMENT);
				tacheId.setCodTachTach(Constants.COD_TACH_REJET_PRELEV_RECU);
				tache.setTacheId(tacheId);

				operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);

				operationMoyPay.setTache(tache);

				// 11. setting date operation moyen paiement
				Date dateOperOmp = detailsPrelevements.getDetailsPrelevementsId().getDatOpePrl();

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
					nomencElemtCondition.setCodNecdNecd(Constants.COD_NECD_NECD_819 + "");

					detailOmpCommission.setNomencElemtCondition(nomencElemtCondition);
					detailOmpCommission.setCodTypDomp(Constants.COD_TYPE_COMMISSION);
					detailOmpCommission.setMontValDomp(new Long(montant_commissionRecue));
					detailOmpCommission.setDatValDomp(DateHandler.strToDate(valueDateComRecue));
					detailOmpCommission.setOperationMoyPay(operationMoyPay);
					setDetOpm.add(detailOmpCommission);

					// operationMoyPay.setDetailOperMoyPaiements(setDetOpm);

				}

				// 20. Insertion motif operation
				operationMoyPay.setLibMotfOmp("REJET DES PRELEVEMENTS");

				operationMoyPay.setNumRibOmp(detailsPrelevements.getDetailsPrelevementsId().getRibBenPrl());
				// 02. insertion operation moyen paiement

				InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
						new InsertionOperationMoyPaySansCROTrt();
				operationMoyPay = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPay);

				logger.info("operationMoyPay :" + operationMoyPay);
				prelevementVo.setOperationMoyPay(operationMoyPay);

				// *************** Update Compte Bénéficiaire ***********//
				try {
					ContratCptSold contratCptSold = new ContratCptSold();
					UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
					contratCptSold.setContratCpt(contratCptTireur);
					contratCptSold.setSens(Constants.COD_SENS_DB);
					contratCptSold.setSolde(montPrelCcpt);
					contratCptTireur = (ContratCpt) updateSoldTrt.exec(contratCptSold);
				} catch (NullPointerException e) {
					logger.error(e.getMessage());
				}

			}

			// ********* CRO ***********//

			this.setCroFlag(true);

			// *******************************************************//

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationCRORejetPrelevementsRecusTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationCRORejetPrelevementsRecusTrt");
			prelevementVo.addError(erreur);
			logger.error("Erreur au niveau CreationCRORejetPrelevementsRecusTrt : ", e);
			throw new RuntimeException(e);

		}
		return (prelevementVo);

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		logger.info("starting CreationCRORejetPrelevementsRecusTrt method ");

		PrelevementVo prelevementVo = (PrelevementVo) vo;

		Operation operation = new Operation();
		operation = prelevementVo.getOperation();
		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_PRELEVEMENTS);
		DetailsPrelevements detailsPrelevements = new DetailsPrelevements();
		detailsPrelevements = prelevementVo.getDetailsPrelevements();
		OperationMoyPay operationMoyPay = new OperationMoyPay();
		operationMoyPay = prelevementVo.getOperationMoyPay();
		MvtPrelevements mvtPrelevements = new MvtPrelevements();
		mvtPrelevements = prelevementVo.getMvtPrelevements();

		Long codeRejet = prelevementVo.getCodeRejet();

		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */

		this.setLibRefCro("REJET DES PRELEVEMENTS");

		if (codeRejet != null && (codeRejet.longValue() == 10 || codeRejet.longValue() == 11)) {
			this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
			this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc() + "");
			this.setDateOperation(operationMoyPay.getDatOperOmp());
			this.setDatValCro(operationMoyPay.getDatValOmp());
			this.setDatValCom(operationMoyPay.getDateValeurCommission());
			this.setCodRefcOmp(operationMoyPay.getCodRefcOmp());
		} else {
			this.setNumRefCro(detailsPrelevements.getDetailsPrelevementsId().getNumPrlPrl());
			this.setCodeStructInitiatrice(prelevementVo.getCodeStructureReceptrice()+"");
			this.setDateOperation(mvtPrelevements.getDatOpePrl());
			this.setDatValCro(null);
			this.setDatValCom(null);
			this.setCodRefcOmp(detailsPrelevements.getDetailsPrelevementsId().getNumPrlPrl() + "");
		}

		this.setCodStrcImpt(prelevementVo.getCodeStructureReceptrice());
		this.setDatExecCro(new Date());
		this.setCodEtatCro(0);
		this.setCodeProduit(produit.getCodPrdPrd().toString());
		this.setOperationId(operation.getCodOperOper().toString());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(Constants.COD_TACH_REJET_PRELEV_RECU);
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

		StringBuffer cro = new StringBuffer("");

		// *** Numéro du compte client ****//
		cro.append("numCptBna=");
		String compteDO = detailsPrelevements.getDetailsPrelevementsId().getRibTirPrl().substring(5, 18);
		cro.append(compteDO + ";");

		// *** Numéro du prélèvement ****//
		cro.append("NUM_PRL_PRL=");
		cro.append(detailsPrelevements.getDetailsPrelevementsId().getNumPrlPrl() + ";");

		// *** Montant rejet du prélèvement ****//
		cro.append("MNT_REJ_PRL=");
		cro.append(detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl() + ";");

		// *** Montant de la commission du prélèvement rejeté ****//
		cro.append("283=");
		cro.append(montant_commissionRecue + ";");

		// *** Montant de la TVA sur commission du prélèvement rejeté ****//
		cro.append("MNT_TVA_REJ_PRL=");
		cro.append(montant_tva + ";");

		// *** Statu client taxable ****//
		cro.append("COD_TVA_CLT=");
		if (etatClientTaxable == true) {
			cro.append(new Long(0) + ";");
		} else {
			cro.append(new Long(1) + ";");
		}
		// *** Code motif rejet ****//
		cro.append("COD_MOT_REJ_PRL=");
		cro.append(prelevementVo.getCodeRejet() + ";");

		// *** Référence dossier domiciliation ****//
		cro.append("NUM_REF_CDOM=");
		cro.append(detailsPrelevements.getNumRefDom() + ";");

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