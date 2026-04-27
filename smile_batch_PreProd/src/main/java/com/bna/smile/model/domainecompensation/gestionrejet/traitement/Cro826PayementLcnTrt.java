package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

/**
 * @author Ayari haythem
 * @since 05/03/2013
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.MouvementCompensationEffet;
import com.bna.commun.model.NomencElemtCondition;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypeMoyenPaiement;
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
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementEffetVo;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;

import com.bna.smile.web.commun.controller.UtilCtr;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.fwk.util.DateHandler;

public class Cro826PayementLcnTrt extends Traitement {

	Context context = ContextHandler.getContext();

	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	boolean etatClientTaxable = false;

	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	long montant_commission = 0;
	long montant_tva_Recu = 0;
	long montant_commissionRecue = 0;
	long montant_tva = 0;
	long montant_tva_comm = 0;
	long montantConverti = 0;
	long montantDevise = 0;
	long montantDinars = 0;
	double coursFixe = 0;
	double coursAchat = 0;
	boolean commPercu = true;

	public Cro826PayementLcnTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		ReglementEffetVo reglementEffetVo = (ReglementEffetVo) vo;

		OperationMoyPay operationMoyPay = new OperationMoyPay();
		ContratCpt contratCptTireur = reglementEffetVo.getContratCpt();

		String valueDateRecue = "";
		String valueDateComRecue = "";

		try {

			// /-------------------- Condition de Banque Tireur
			// ---------------------------///
			TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
			traitementConditionBanque.setCodOperOper("" + Constants.COD_OPERATION_PAIEMENT_EFFET);
			traitementConditionBanque.setCodStrcStrc("" + contratCptTireur.getContratCptId().getCodStrcStrc());
			traitementConditionBanque.setCodPrdCpt("" + contratCptTireur.getContratCptId().getCodPrdPrd());
			traitementConditionBanque.setNumCcptCcpt("" + contratCptTireur.getContratCptId().getNumCcptCcpt());
			traitementConditionBanque.setDateReference(formaterDate.format(reglementEffetVo.getDateComptable()));
			traitementConditionBanque.setCodPrdPrd(contratCptTireur.getContratCptId().getCodPrdPrd().toString());
			Personne pers = contratCptTireur.getClient().getPersonne();
			traitementConditionBanque.setNbUnites("1");

			traitementConditionBanque.setNumPcePers(pers.getNumPcePers());
			traitementConditionBanque.setCodTpceTpce("" + pers.getTypePiece().getCodTpceTpce());
			traitementConditionBanque = getConditionDeBanque(traitementConditionBanque);

			montant_commissionRecue =
					new Long(new Double(new Double(StrHandler.strWithoutBlanck(String.valueOf(traitementConditionBanque
							.getValeurCommission()))).doubleValue()).longValue());
			montant_tva_Recu =
					new Long(new Double(new Double(StrHandler.strWithoutBlanck(String.valueOf(traitementConditionBanque
							.getMntTva()))).doubleValue()).longValue());
			montant_tva_comm = montant_commissionRecue + montant_tva_Recu;
			montant_tva = montant_tva_Recu;
			montant_commission = montant_commissionRecue;
			valueDateRecue = traitementConditionBanque.getDatevaleur();
			valueDateComRecue = traitementConditionBanque.getDatevaleurComm();

			// *********Caracteristique Taxable du client ********//
			try {
				ExonerationTvaDAO exonerationTvaDAO = new ExonerationTvaDAO();
				ParamRechercheOpposition param = new ParamRechercheOpposition();
				logger.info(contratCptTireur.getClient().getPersonne().getTypePiece().getCodTpceTpce());
				logger.info(contratCptTireur.getClient().getPersonne().getNumPcePers());
				param.setTypPceDemd(contratCptTireur.getClient().getPersonne().getTypePiece().getCodTpceTpce());
				param.setNumPceDemd(contratCptTireur.getClient().getPersonne().getNumPcePers());
				param.setDateDebutConsult(reglementEffetVo.getDateComptable());

				PrimitiveVO res = (PrimitiveVO) exonerationTvaDAO.isClientExonereTVA(param);

				if (res.isVBool() == true) {
					etatClientTaxable = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}

			// / ------------------Operation Moyen de Paiement
			// ---------------------------- ///

			// 00. setting obj operationMoyPay
			operationMoyPay.setLibObjOpOmp("PAIEMENT EFFET ");

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
			Structure structureRecep = new Structure();
			structureRecep.setCodStrcStrc(contratCptTireur.getContratCptId().getCodStrcStrc());
			operationMoyPay.setStructureReceptrice(structureRecep);

			TypeMoyenPaiement typeMoyenPaiement = new TypeMoyenPaiement();
			typeMoyenPaiement.setCodMoypTmoy(Constants.COD_TYP_MOY_PAI_EFFET);
			operationMoyPay.setTypeMoyenPaiement(typeMoyenPaiement);

			operationMoyPay.setDevise(contratCptTireur.getDevise());
			montantDinars = reglementEffetVo.getEffetRecuTmp().getMntEff();
			if (reglementEffetVo.getEffetRecuTmp().getMntInt() != null)
				montantDinars += reglementEffetVo.getEffetRecuTmp().getMntInt();

			// verification compte dinars convertible
			if (UtilCtr.isDinarConvertible(contratCptTireur)) {

				if (contratCptTireur.getProvision(reglementEffetVo.getDateComptable()) - montantDinars
						- montant_tva_comm < 0) {
					montant_commission = 0L;
					montant_tva = 0L;
					montant_tva_comm = 0L;

					commPercu = false;
				}
			}

			// 05. setting devise et montant

			if (!contratCptTireur.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
				coursAchat = UtilCtr.getCoursAchatBna("" + contratCptTireur.getDevise().getCodDevDev());

				montantDevise =
						UtilCtr.changeTNDToDevise(montantDinars, contratCptTireur.getDevise().getNbrDecDev(),
								contratCptTireur.getDevise().getNbrUnitDev(), coursAchat);

				montant_commission = 0L;
				montant_tva = 0L;
				montant_tva_comm = 0L;

				commPercu = false;

				// compte en devise
				coursFixe =
						UtilCtr.getCoursFixe(reglementEffetVo.getDateComptable(), contratCptTireur.getDevise()
								.getCodDevDev());

				montantConverti =
						UtilCtr.changeDeviseToTND(montantDevise, contratCptTireur.getDevise().getNbrDecDev(),
								contratCptTireur.getDevise().getNbrUnitDev(), coursFixe);

				operationMoyPay.setMontSoldCcpt(contratCptTireur.getMontSoldCcpt());
				operationMoyPay.setMontSdevCcpt(contratCptTireur.getMontSdevCcpt());

				operationMoyPay.setMontCourPaof(coursFixe);
				operationMoyPay.setMontCourOmp(coursAchat);

				operationMoyPay.setMontCvalOmp(montantConverti);
				operationMoyPay.setMontDevOmp(montantDevise);

				operationMoyPay.setMontDinOmp(montantConverti);
				operationMoyPay.setMontDevApreOmp(contratCptTireur.getMontSdevCcpt() - montantDevise);
				operationMoyPay.setMontApreOmp(contratCptTireur.getMontSoldCcpt() - montantConverti);

			}

			else {

				operationMoyPay.setMontDinOmp(montantDinars);

				operationMoyPay.setMontApreOmp(contratCptTireur.getMontSoldCcpt() - montant_tva_comm - montantDinars);

				operationMoyPay.setMontSoldCcpt(contratCptTireur.getMontSoldCcpt());

			}

			// 06. setting contrat compte
			operationMoyPay.setContratCpt(contratCptTireur);

			// 07. setting type demandeur (Titulaire, CoTitulaire,
			// Mandataire)

			operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

			// 08. setting info Reference Operation //à completter
			operationMoyPay.setCodRefbOmp("0");
			operationMoyPay.setCodRefcOmp("0");

			// 09. insertion du Donneur D'ordre

			TypePiece typePieceDemandeur = new TypePiece();
			typePieceDemandeur.setCodTpceTpce(Long.valueOf(contratCptTireur.getClient().getPersonne().getTypePiece()
					.getCodTpceTpce()));
			operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
			operationMoyPay.setNumPcedOmp(contratCptTireur.getClient().getPersonne().getNumPcePers());
			operationMoyPay.setNomNomdOmp(contratCptTireur.getClient().getPersonne().getNomNomPers());
			operationMoyPay.setNomPrndOmp(contratCptTireur.getClient().getPersonne().getNomPrnPers());

			// 10 Preparing data of Operation and tache
			Operation oper = new Operation();
			oper.setCodOperOper(Constants.COD_OPERATION_PAIEMENT_EFFET);
			Tache tache = new Tache();
			tache.setOperation(oper);
			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(Constants.COD_OPERATION_PAIEMENT_EFFET);
			tacheId.setCodTachTach(Constants.COD_TACHE_EFFET);
			tache.setTacheId(tacheId);

			operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);

			operationMoyPay.setTache(tache);

			// 11. setting date operation moyen paiement

			operationMoyPay.setDatOperOmp(reglementEffetVo.getDateComptable());

			// 12. setting date valeur moyen paiement

			operationMoyPay.setDatValOmp(DateHandler.strToDate(valueDateRecue));

			// 13.1 setting date system moyen paiement
			operationMoyPay.setDatSystOmp(new Date());

			// 13.2 setting date valeur Commission moyen paiement
			operationMoyPay.setDateValeurCommission(DateHandler.strToDate(valueDateComRecue));

			// 14. setting sens operation

			operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);

			// 15. setting montant tva
			operationMoyPay.setMontTvaOmp(montant_tva_Recu);

			// 16. Insertion dans la table DETAIL_OPERATION_MOY_PAIEMENT

			Set<DetailOperMoyPaiement> setDetOpm = new HashSet<DetailOperMoyPaiement>();
			NomencElemtCondition nomencElemtCondition = new NomencElemtCondition();
			DetailOperMoyPaiement detailOmpCommission = new DetailOperMoyPaiement();

			if (montant_commission != 0) {

				// **** A verifier **** //
				nomencElemtCondition.setCodNecdNecd(Constants.COD_NECD_NECD_PAIEMENT_EFFET + "");
				detailOmpCommission.setNomencElemtCondition(nomencElemtCondition);
				detailOmpCommission.setCodTypDomp(Constants.COD_TYPE_COMMISSION);
				detailOmpCommission.setMontValDomp(new Long(montant_commission));
				detailOmpCommission.setDatValDomp(DateHandler.strToDate(valueDateComRecue));
				detailOmpCommission.setOperationMoyPay(operationMoyPay);
				setDetOpm.add(detailOmpCommission);

				operationMoyPay.setDetailOperMoyPaiements(setDetOpm);

			}

			// 17. Insertion code ref client
			// operationMoyPay.setCodRefcOmp("9999");

			// 20. Insertion motif operation
			operationMoyPay.setLibMotfOmp("PAIEMENT EFFET");
			operationMoyPay.setCodRefbOmp("n° " +reglementEffetVo.getEffetRecu().getEffetId().getNumEff());
			// 02. insertion operation moyen paiement

			InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
					new InsertionOperationMoyPaySansCROTrt();
			operationMoyPay = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPay);

			logger.info("Operation Mo Pay done success:" + operationMoyPay.getNumOperOmp());

			reglementEffetVo.setOperationMoyPay(operationMoyPay);
			this.setCroFlag(true);
			// ***************** Insertion Mouvement
			// Compensation******************//
			// Mouvement Copensation Effet//
			//
			CompensationDAO effetDAO = (CompensationDAO) context.getBean("compensationDAO");
			MouvementCompensationEffet mouvementCompensationEffet = new MouvementCompensationEffet();
			mouvementCompensationEffet.setNumEffet(reglementEffetVo.getEffetRecu().getEffetId().getNumEff());
			mouvementCompensationEffet.setDatOpeMvtc(reglementEffetVo.getDateComptable());
			if (!contratCptTireur.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR))
				mouvementCompensationEffet.setMntOpeMvtc(montantConverti);
			else
				mouvementCompensationEffet.setMntOpeMvtc(montantDinars + montant_tva_comm);
			mouvementCompensationEffet.setCommissionPecu(commPercu);
			mouvementCompensationEffet.setMntCommission(montant_commissionRecue + montant_tva_Recu);
			mouvementCompensationEffet.setContratCpt(contratCptTireur);
			mouvementCompensationEffet.setOperationMoyPay(operationMoyPay);
			mouvementCompensationEffet.setTache(tache);
			mouvementCompensationEffet.setPersonnel(personnelInit);
			mouvementCompensationEffet.setNumSeqMvtc(effetDAO.getSequenceMvtCompensationEffet());
			crudService.create(mouvementCompensationEffet);
			/*********** Mise à jour solde ************/

			ContratCptSold contratCptSold = new ContratCptSold();
			UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
			contratCptSold.setContratCpt(contratCptTireur);

			contratCptSold.setSens(Constants.COD_SENS_DB);
			// Checking Devise
			if (!contratCptTireur.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
				contratCptSold.setSoldeDevise(montantDevise);
				contratCptSold.setSolde(montantConverti);

			} else {

				contratCptSold.setSolde(montantDinars + montant_tva_comm);

			}
			contratCptTireur = (ContratCpt) updateSoldTrt.exec(contratCptSold);

			HibernateTemplate hibernateTemplate = (HibernateTemplate) this.context.getBean("hibernateTemplate");
			hibernateTemplate.getSessionFactory().getCurrentSession().evict(contratCptTireur);
			contratCptTireur = (ContratCpt) hibernateTemplate.get(ContratCpt.class, contratCptTireur.getContratCptId());
			reglementEffetVo.setContratCpt(contratCptTireur);

		} catch (Exception e) {
			logger.info(e.getMessage());
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans Cro826PayementLcnTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("Cro826PayementLcnTrt");
			reglementEffetVo.addError(erreur);
			logger.error("Erreur au niveau Cro826PayementLcnTrt : ", e);
			throw new RuntimeException(e);

		}
		return reglementEffetVo;

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {

		ReglementEffetVo reglementEffetVo = (ReglementEffetVo) vo;
		OperationMoyPay operationMoyPay = reglementEffetVo.getOperationMoyPay();
		ContratCpt contratCptTire = reglementEffetVo.getContratCpt();

		Operation operation = new Operation();

		operation.setCodOperOper(Constants.COD_OPERATION_PAIEMENT_EFFET);
		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_EFFET);

		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */
		GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();

		String refIntSg1 =

				generateReferenceInterSiege.getRISWithUpdate(Long.valueOf(reglementEffetVo.getStructure()),
						reglementEffetVo.getDateComptable());

		String refIntSg2 =

				generateReferenceInterSiege.getRISWithUpdate(Long.valueOf(reglementEffetVo.getStructure()),
						reglementEffetVo.getDateComptable());
		String refIntSg3 =

				generateReferenceInterSiege.getRISWithUpdate(Long.valueOf(reglementEffetVo.getStructure()),
						reglementEffetVo.getDateComptable());
		System.out.println(refIntSg1 + "/" + refIntSg2);
		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		this.setLibRefCro("PAIEMENT EFFET");
		this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc() + "");
		this.setCodRefInter(refIntSg1);
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
		this.setCodTachTach(Constants.COD_TACHE_EFFET);
		this.setCodRefcOmp(reglementEffetVo.getEffetRecu().getEffetId().getNumEff());

		this.setNumCinUser("9999");
		this.setCodTypUser("M");

		/*
		 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
		 */

		StringBuffer cro = new StringBuffer("");

		// *** Montant effet ****//
		cro.append("MNT_EFF_EFF=");
		cro.append(montantDinars + ";");

		// *** Numéro du compte client ****//
		cro.append("numCptBna=");
		cro.append(StrHandler.lpad(contratCptTire.getContratCptId().getCodStrcStrc().toString(), '0', 3));
		// cro.append("COD_PRD_PRD=");
		cro.append(StrHandler.lpad(contratCptTire.getContratCptId().getCodPrdPrd().toString(), '0', 4));
		// cro.append("NUM_CCPT_CCPT=");
		cro.append(StrHandler.lpad(contratCptTire.getContratCptId().getNumCcptCcpt().toString(), '0', 6) + ";");

		// *** statut client taxable ****//
		cro.append("COD_TVA_CLT=");
		if (etatClientTaxable == true) {
			cro.append(Long.valueOf(0) + ";");

		} else {
			cro.append(Long.valueOf(1) + ";");

		}

		if (!contratCptTire.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
			// *** Montant Commission ****//
			cro.append("197" + "=");
			cro.append(0 + ";");

			// *** Montant Tva Commission ****//
			cro.append("MNT_TVA_EFF=");
			cro.append(0 + ";");
			// *** Montant Tva +Commission ****//
			cro.append("MONT_COM_TVA_EFF_826=");
			cro.append(0 + ";");

			// ***MNT EFF DEVISE EN DINARS****//
			cro.append("MNT_DEV_EFF=");
			cro.append(montantDevise + ";");
			// ***cours fixe ****//
			cro.append("TAUX_FIX_BBE=");
			cro.append(coursFixe + ";");
			// ***montant converti cours fixe ****//
			cro.append("MNT_CTR_CFIX=");
			cro.append(montantConverti + ";");

		} else {
			// *** Montant Commission ****//
			cro.append("197" + "=");
			cro.append(montant_commission + ";");

			// *** Montant Tva Commission ****//
			cro.append("MNT_TVA_EFF=");
			cro.append(montant_tva + ";");
			// *** Montant Tva +Commission ****//
			cro.append("MONT_COM_TVA_EFF_826=");
			cro.append(montant_tva_comm + ";");

		}

		// ***ref 1 ****//
		cro.append("REF_IS1=");
		cro.append(refIntSg1 + ";");
		// ***ref 2 ****//
		cro.append("REF_IS2=");
		cro.append(refIntSg2 + ";");
		// ***ref 3 ****//
		cro.append("REF_IS3=");
		cro.append(refIntSg3 + ";");
		// ***code Ref IS 65 ****//
		cro.append("code_ref_is=");
		cro.append(65 + ";");
		// *** Numero effet ****//
		cro.append("NUM_EFF_EFF=");
		cro.append(reglementEffetVo.getEffetRecu().getEffetId().getNumEff() + ";");
		// ***Code devise****//
		cro.append("COD_DEV_DEV=");
		cro.append(contratCptTire.getDevise().getCodDevDev() + ";");
		if (!contratCptTire.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
			// ***type compte****//
			cro.append("TYPE_CPT=");
			cro.append(3 + ";");
		} else {
			cro.append("TYPE_CPT=");
			cro.append(2 + ";");
		}
		// *** ETAT CPT ****//
		cro.append("ETAT_CPT=");
		cro.append(0 + ";");
		

		this.setCroText(cro.toString());
		logger.info("Cro generer avec succés");

	}

	public TraitementConditionBanque getConditionDeBanque(TraitementConditionBanque traitementConditionBanque) {

		traitementConditionBanque.getCB();

		return traitementConditionBanque;

	}

}