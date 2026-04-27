package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

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

public class Cro839PayementOCTrt extends Traitement {

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

	public Cro839PayementOCTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		ReglementEffetVo reglementEffetVo = (ReglementEffetVo) vo;
		OperationMoyPay operationMoyPay = new OperationMoyPay();
		ContratCpt contratCptTireur = reglementEffetVo.getContratCpt();

		String dateValeur = "";
		String dateValeurCom = "";

		try {

			// /-------------------- Condition de Banque Tireur
			// ---------------------------///
			TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
			traitementConditionBanque.setCodOperOper("" + Constants.COD_OPERATION_PAIEMENT_OC);
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

			montant_commissionRecue =new Long(new Double(new Double(StrHandler.strWithoutBlanck(String.valueOf(traitementConditionBanque.getValeurCommission()))).doubleValue()).longValue());
			montant_tva_Recu =new Long(new Double(new Double(StrHandler.strWithoutBlanck(String.valueOf(traitementConditionBanque.getMntTva()))).doubleValue()).longValue());
			montant_tva_comm = montant_commissionRecue + montant_tva_Recu;
			montant_tva = montant_tva_Recu;
			montant_commission = montant_commissionRecue;
			dateValeur = traitementConditionBanque.getDatevaleur();
			dateValeurCom = traitementConditionBanque.getDatevaleurComm();


			ExonerationTvaDAO exonerationTvaDAO = new ExonerationTvaDAO();
			ParamRechercheOpposition param = new ParamRechercheOpposition();
			logger.info(contratCptTireur.getClient().getPersonne().getTypePiece().getCodTpceTpce());
			logger.info(contratCptTireur.getClient().getPersonne().getNumPcePers());
			param.setTypPceDemd(contratCptTireur.getClient().getPersonne().getTypePiece().getCodTpceTpce());
			param.setNumPceDemd(contratCptTireur.getClient().getPersonne().getNumPcePers());
			param.setDateDebutConsult(reglementEffetVo.getDateComptable());

			PrimitiveVO res = (PrimitiveVO) exonerationTvaDAO.isClientExonereTVA(param);
			etatClientTaxable = res.isVBool() == true ?  true: false;

			operationMoyPay.setLibObjOpOmp("PAIEMENT OC ");

			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			operationMoyPay.setPersonnelInitiateur(personnelInit);
			operationMoyPay.setPersonnelValideur(personnelInit);

			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(contratCptTireur.getContratCptId().getCodStrcStrc());
			operationMoyPay.setStructureInitiatrice(structureInit);

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


			
			if (UtilCtr.isDinarConvertible(contratCptTireur)) {
				if (contratCptTireur.getProvision(reglementEffetVo.getDateComptable()) - montantDinars
						- montant_tva_comm < 0) {
					montant_commission = 0L;
					montant_tva = 0L;
					montant_tva_comm = 0L;

					commPercu = false;
				}
			}



			operationMoyPay.setMontDinOmp(montantDinars);
			operationMoyPay.setMontApreOmp(contratCptTireur.getMontSoldCcpt() - montant_tva_comm - montantDinars);
			operationMoyPay.setMontSoldCcpt(contratCptTireur.getMontSoldCcpt());
			operationMoyPay.setContratCpt(contratCptTireur);
			operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);
			operationMoyPay.setCodRefbOmp("0");
			operationMoyPay.setCodRefcOmp("0");
			TypePiece typePieceDemandeur = new TypePiece();
			typePieceDemandeur.setCodTpceTpce(Long.valueOf(contratCptTireur.getClient().getPersonne().getTypePiece().getCodTpceTpce()));
			operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
			operationMoyPay.setNumPcedOmp(contratCptTireur.getClient().getPersonne().getNumPcePers());
			operationMoyPay.setNomNomdOmp(contratCptTireur.getClient().getPersonne().getNomNomPers());
			operationMoyPay.setNomPrndOmp(contratCptTireur.getClient().getPersonne().getNomPrnPers());
			Operation oper = new Operation();
			oper.setCodOperOper(Constants.COD_OPERATION_PAIEMENT_OC);
			Tache tache = new Tache();
			tache.setOperation(oper);
			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(Constants.COD_OPERATION_PAIEMENT_OC);
			tacheId.setCodTachTach(Constants.COD_TACHE_EFFET);
			tache.setTacheId(tacheId);

			operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
			operationMoyPay.setTache(tache);
			operationMoyPay.setDatOperOmp(reglementEffetVo.getDateComptable());
			Date dateValOmp = null;
			if (dateValeur != null && dateValeur.length() > 0) {
				dateValOmp = formaterDate.parse(dateValeur);
			} else {
				dateValOmp = reglementEffetVo.getDateComptable();
			}		
			operationMoyPay.setDatValOmp(dateValOmp);
			
			Date dateValCom = null;
			if (dateValeurCom != null && dateValeurCom.length() > 0) {
				dateValCom = formaterDate.parse(dateValeurCom);
			} else {
				dateValCom = reglementEffetVo.getDateComptable();
			}
			operationMoyPay.setDateValeurCommission(dateValCom);
			operationMoyPay.setDatSystOmp(new Date());
			operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
			operationMoyPay.setMontTvaOmp(montant_tva_Recu);

			Set<DetailOperMoyPaiement> setDetOpm = new HashSet<DetailOperMoyPaiement>();
			NomencElemtCondition nomencElemtCondition = new NomencElemtCondition();
			DetailOperMoyPaiement detailOmpCommission = new DetailOperMoyPaiement();

			if (montant_commission != 0) {

				// **** A verifier **** //
				nomencElemtCondition.setCodNecdNecd(Constants.COD_NECD_NECD_PAIEMENT_EFFET + "");
				detailOmpCommission.setNomencElemtCondition(nomencElemtCondition);
				detailOmpCommission.setCodTypDomp(Constants.COD_TYPE_COMMISSION);
				detailOmpCommission.setMontValDomp(new Long(montant_commission));
				detailOmpCommission.setDatValDomp(dateValCom);
				detailOmpCommission.setOperationMoyPay(operationMoyPay);
				setDetOpm.add(detailOmpCommission);
				operationMoyPay.setDetailOperMoyPaiements(setDetOpm);

			}

			operationMoyPay.setLibMotfOmp("PAIEMENT OC");
			operationMoyPay.setCodRefbOmp("n° " +reglementEffetVo.getEffetRecu().getEffetId().getNumEff());

			InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =new InsertionOperationMoyPaySansCROTrt();
			operationMoyPay = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPay);


			reglementEffetVo.setOperationMoyPay(operationMoyPay);
			this.setCroFlag(true);
			CompensationDAO effetDAO = (CompensationDAO) context.getBean("compensationDAO");
			MouvementCompensationEffet mouvementCompensationEffet = new MouvementCompensationEffet();
			mouvementCompensationEffet.setNumEffet(reglementEffetVo.getEffetRecu().getEffetId().getNumEff());
			mouvementCompensationEffet.setDatOpeMvtc(reglementEffetVo.getDateComptable());
//			if (!contratCptTireur.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR))
//				mouvementCompensationEffet.setMntOpeMvtc(montantConverti);
//			else
			mouvementCompensationEffet.setMntOpeMvtc(montantDinars + montant_tva_comm);
			mouvementCompensationEffet.setCommissionPecu(commPercu);
			mouvementCompensationEffet.setMntCommission(montant_commissionRecue + montant_tva_Recu);
			mouvementCompensationEffet.setContratCpt(contratCptTireur);
			mouvementCompensationEffet.setOperationMoyPay(operationMoyPay);
			mouvementCompensationEffet.setTache(tache);
			mouvementCompensationEffet.setPersonnel(personnelInit);
			mouvementCompensationEffet.setNumSeqMvtc(effetDAO.getSequenceMvtCompensationEffet());
			crudService.create(mouvementCompensationEffet);

			
			ContratCptSold contratCptSold = new ContratCptSold();
			UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
			contratCptSold.setContratCpt(contratCptTireur);

			contratCptSold.setSens(Constants.COD_SENS_DB);
//			// Checking Devise
//			if (!contratCptTireur.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
//				contratCptSold.setSoldeDevise(montantDevise);
//				contratCptSold.setSolde(montantConverti);
//
//			} else {

			contratCptSold.setSolde(montantDinars + montant_tva_comm);
			//}
			contratCptTireur = (ContratCpt) updateSoldTrt.exec(contratCptSold);

			HibernateTemplate hibernateTemplate = (HibernateTemplate) this.context.getBean("hibernateTemplate");
			hibernateTemplate.getSessionFactory().getCurrentSession().evict(contratCptTireur);
			contratCptTireur = (ContratCpt) hibernateTemplate.get(ContratCpt.class, contratCptTireur.getContratCptId());
			reglementEffetVo.setContratCpt(contratCptTireur);

		} catch (Exception e) {
			logger.info(e.getMessage());
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans Cro839PayementOCTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("Cro839PayementOCTrt");
			reglementEffetVo.addError(erreur);
			logger.error("Erreur au niveau Cro839PayementOCTrt : ", e);
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

		operation.setCodOperOper(Constants.COD_OPERATION_PAIEMENT_OC);
		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_EFFET_OC);

		
		//  _Cro _Fixe .
		
		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		this.setLibRefCro("PAIEMENT OC");
		this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc() + "");
		this.setCodRefInter(null);
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

		
		// _Cro _Variable  .		
		
		StringBuffer cro = new StringBuffer("");
		cro.append("MNT_OC_OC=");
		cro.append(montantDinars + ";");
		cro.append("numCptBna=");
		cro.append(StrHandler.lpad(contratCptTire.getContratCptId().getCodStrcStrc().toString(), '0', 3));
		cro.append(StrHandler.lpad(contratCptTire.getContratCptId().getCodPrdPrd().toString(), '0', 4));
		cro.append(StrHandler.lpad(contratCptTire.getContratCptId().getNumCcptCcpt().toString(), '0', 6) + ";");
		cro.append("COD_TVA_CLT=");
		String cod_tva="0";
		cod_tva=etatClientTaxable == true?"1":"0";
		cro.append(cod_tva + ";");
		cro.append("MNT_TVA_EFF=");
		cro.append(montant_tva + ";");
		// TODO : Check this 
		cro.append("MNT_PDL_RECUP_EFF=");
		cro.append(0L + ";");
		cro.append("MNT_COM_OC" + "=");
		cro.append(montant_commission + ";");
		this.setCroText(cro.toString());
		logger.info("Cro generer avec succés");

	}

	public TraitementConditionBanque getConditionDeBanque(TraitementConditionBanque traitementConditionBanque) {

		traitementConditionBanque.getCB();
		return traitementConditionBanque;

	}

}