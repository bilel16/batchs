package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Cro;
import com.bna.commun.model.Devise;
import com.bna.commun.model.MouvementCompensationEffet;
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
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.CroDAO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementEffetVo;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.fwk.util.DateHandler;

public class Cro2069EncaissementLcrPayeeTrt extends Traitement {

	Context context = ContextHandler.getContext();

	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	boolean etatClientTaxable = false;

	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");

	public Cro2069EncaissementLcrPayeeTrt() {
	}
	String dateValeur = "";
	public IValueObject perform(IValueObject vo) {

		ReglementEffetVo reglementEffetVo = (ReglementEffetVo) vo;
		try {
			OperationMoyPay operationMoyPay = new OperationMoyPay();
			ContratCpt contratCptBen = reglementEffetVo.getContratCpt();
			
			
			
			
			String dateValeurCom = "";



			// try {

			// /-------------------- Condition de Banque Tireur
			// ---------------------------///
			TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
			traitementConditionBanque.setCodOperOper("" + Constants.COD_OPERATION_ENCAISSEMENT_LCR_PAYEE);
			traitementConditionBanque.setCodStrcStrc("" + contratCptBen.getContratCptId().getCodStrcStrc());
			traitementConditionBanque.setCodPrdCpt("" + contratCptBen.getContratCptId().getCodPrdPrd());
			traitementConditionBanque.setNumCcptCcpt("" + contratCptBen.getContratCptId().getNumCcptCcpt());
			traitementConditionBanque.setDateReference(formaterDate.format(reglementEffetVo.getDateComptable()));
			traitementConditionBanque.setCodPrdPrd(contratCptBen.getContratCptId().getCodPrdPrd().toString());
			Personne pers = contratCptBen.getClient().getPersonne();
			traitementConditionBanque.setNumPcePers(pers.getNumPcePers());
			traitementConditionBanque.setCodTpceTpce("" + pers.getTypePiece().getCodTpceTpce());
			traitementConditionBanque = getConditionDeBanque(traitementConditionBanque);

			dateValeur = traitementConditionBanque.getDatevaleur();

			// / ------------------Operation Moyen de Paiement
			// ---------------------------- ///

			// 00. setting obj operationMoyPay
			operationMoyPay.setLibObjOpOmp(" ENCAISSEMENT LCR PAYEE");

			// 01. setting personnel initiateur et valideur
			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			operationMoyPay.setPersonnelInitiateur(personnelInit);
			operationMoyPay.setPersonnelValideur(personnelInit);

			// 02. setting structure initiatrice
			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(Long.valueOf(reglementEffetVo.getStructure()));
			operationMoyPay.setStructureInitiatrice(structureInit);

			// 03. setting structure receptrice
			Structure structureRecep = new Structure();
			structureRecep.setCodStrcStrc(Long.valueOf(reglementEffetVo.getStructure()));
			operationMoyPay.setStructureReceptrice(structureRecep);

			// 04. getting provision

			TypeMoyenPaiement typeMoyenPaiement = new TypeMoyenPaiement();
			typeMoyenPaiement.setCodMoypTmoy(Constants.COD_TYP_MOY_PAI_EFFET);
			operationMoyPay.setTypeMoyenPaiement(typeMoyenPaiement);

			operationMoyPay.setDevise(contratCptBen.getDevise());
			Long mnt = reglementEffetVo.getEffetRecu().getMntEff();
			if (reglementEffetVo.getEffetRecu().getMntInt() != null)
				mnt += reglementEffetVo.getEffetRecu().getMntInt();
			operationMoyPay.setMontDinOmp(mnt);

			operationMoyPay.setMontApreOmp(contratCptBen.getMontSoldCcpt() + mnt);

			operationMoyPay.setMontSoldCcpt(contratCptBen.getMontSoldCcpt());

			// 06. setting contrat compte
			operationMoyPay.setContratCpt(contratCptBen);

			// 07. setting type demandeur (Titulaire, CoTitulaire,
			// Mandataire)

			operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

			// 08. setting info Reference Operation //à completter
			operationMoyPay.setCodRefbOmp("n° " +reglementEffetVo.getEffetRecu().getEffetId().getNumEff());
			

			// 09. insertion du Donneur D'ordre

			TypePiece typePieceDemandeur = new TypePiece();
			typePieceDemandeur.setCodTpceTpce(Long.valueOf(contratCptBen.getClient().getPersonne().getTypePiece()
					.getCodTpceTpce()));
			operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
			operationMoyPay.setNumPcedOmp(contratCptBen.getClient().getPersonne().getNumPcePers());
			operationMoyPay.setNomNomdOmp(contratCptBen.getClient().getPersonne().getNomNomPers());
			operationMoyPay.setNomPrndOmp(contratCptBen.getClient().getPersonne().getNomPrnPers());

			// 10 Preparing data of Operation and tache
			// 10 Preparing data of Operation and tache
			Operation oper = new Operation();
			oper.setCodOperOper(Constants.COD_OPERATION_ENCAISSEMENT_LCR_PAYEE);
			Tache tache = new Tache();
			tache.setOperation(oper);
			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(Constants.COD_OPERATION_ENCAISSEMENT_LCR_PAYEE);
			tacheId.setCodTachTach(Constants.COD_TACHE_EFFET);
			tache.setTacheId(tacheId);

			operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);

			operationMoyPay.setTache(tache);



			// 11. setting date operation moyen paiement

			operationMoyPay.setDatOperOmp(reglementEffetVo.getDateComptable());

			// 12. setting date valeur moyen paiement
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

			// 13.1 setting date system moyen paiement
			// Date dateSysOmp = formaterDate.parse(formaterDate.format(new Date()));
			operationMoyPay.setDatSystOmp(new Date());

			// 13.2 setting date valeur Commission moyen paiement

			operationMoyPay.setDateValeurCommission(dateValOmp);

			// 14. setting sens operation

			operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);

			// 17. Insertion code ref client
			operationMoyPay.setCodRefcOmp("9999");

			// 20. Insertion motif operation
			operationMoyPay.setLibMotfOmp("ENCAISSEMENT LCR ");

			// 02. insertion operation moyen paiement

			InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
					new InsertionOperationMoyPaySansCROTrt();
			operationMoyPay = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPay);

			logger.info("Operation Mo Pay done success:" + operationMoyPay.getNumOperOmp());

			reglementEffetVo.setOperationMoyPay(operationMoyPay);

			// ***************** Insertion Mouvement
			// Compensation******************//
			// Mouvement Copensation Effet//
			//
			CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
			MouvementCompensationEffet mouvementCompensationEffet = new MouvementCompensationEffet();
			mouvementCompensationEffet.setNumEffet(reglementEffetVo.getEffetRecu().getEffetId().getNumEff());
			mouvementCompensationEffet.setDatOpeMvtc(reglementEffetVo.getDateComptable());
			mouvementCompensationEffet.setMntOpeMvtc(mnt);
			mouvementCompensationEffet.setCommissionPecu(true);
			mouvementCompensationEffet.setMntCommission(0L);
			mouvementCompensationEffet.setContratCpt(contratCptBen);
			mouvementCompensationEffet.setOperationMoyPay(operationMoyPay);
			mouvementCompensationEffet.setTache(tache);
			mouvementCompensationEffet.setPersonnel(personnelInit);
			mouvementCompensationEffet.setNumSeqMvtc(compensationDAO.getSequenceMvtCompensationEffet());
			crudService.create(mouvementCompensationEffet);
			/*********** Mise à jour solde ************/

			contratCptBen =
					UtilCtr.updateSolde(contratCptBen, Constants.COD_SENS_CR, mnt, reglementEffetVo.getDateComptable());

			HibernateTemplate hibernateTemplate = (HibernateTemplate) this.context.getBean("hibernateTemplate");
			hibernateTemplate.evict(contratCptBen);
			contratCptBen = (ContratCpt) hibernateTemplate.get(ContratCpt.class, contratCptBen.getContratCptId());
			reglementEffetVo.setContratCpt(contratCptBen);

			this.setCroFlag(true);
		} catch (Exception e) {
			throw new RuntimeException(e);

		}

		return (reglementEffetVo);


	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		Operation operation = new Operation();
		ReglementEffetVo reglementEffetVo = (ReglementEffetVo) vo;
		
		Date dateValOmp = null;
		if (dateValeur != null && dateValeur.length() > 0) {
			dateValOmp = DateHandler.strToDate(dateValeur);
		} else {
			dateValOmp = reglementEffetVo.getDateComptable();
		}
		ContratCpt contratCptBen = reglementEffetVo.getContratCpt();
		operation.setCodOperOper(Constants.COD_OPERATION_ENCAISSEMENT_LCR_PAYEE);
		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_EFFET);
		this.setNumRefCro(Long.valueOf(reglementEffetVo.getOperationMoyPay().getNumOperOmp()));
		this.setLibRefCro("ENCAISSEMENT LCR");
		this.setCodeStructInitiatrice("" + contratCptBen.getContratCptId().getCodStrcStrc());
		this.setCodStrcImpt(contratCptBen.getContratCptId().getCodStrcStrc());
		this.setDatExecCro(new Date());
		this.setCodEtatCro(0);

		this.setCodeProduit(produit.getCodPrdPrd().toString());
		this.setOperationId(operation.getCodOperOper().toString());
		this.setDateOperation(reglementEffetVo.getDateComptable());
		this.setDatValCro(dateValOmp);
		this.setDatValCom(null);
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(Constants.COD_TACHE_EFFET);
		this.setCodRefcOmp(reglementEffetVo.getEffetRecu().getEffetId().getNumEff());

		this.setNumCinUser("9999");
		this.setCodTypUser("M");
		this.setCodRefInter(null);
		StringBuffer croD = new StringBuffer("");

		// *** Montant effet ****//
		croD.append("MNT_EFF_EFF=");
		Long mnt = reglementEffetVo.getEffetRecu().getMntEff();
		if (reglementEffetVo.getEffetRecu().getMntInt() != null)
			mnt += reglementEffetVo.getEffetRecu().getMntInt();
		croD.append(mnt + ";");
		// *** Numero effet ****//
		croD.append("NUM_EFF_EFF=");
		croD.append(reglementEffetVo.getEffetRecu().getEffetId().getNumEff() + ";");
		
		// *** Numero effet ****//
		croD.append("COD_VAL_EFF=");
		croD.append("40;");


		// *** Numéro du compte client ****//
		croD.append("numCptBna=");
		croD.append(StrHandler.lpad(contratCptBen.getContratCptId().getCodStrcStrc().toString(), '0', 3));
		// cro.append("COD_PRD_PRD=");
		croD.append(StrHandler.lpad(contratCptBen.getContratCptId().getCodPrdPrd().toString(), '0', 4));
		// cro.append("NUM_CCPT_CCPT=");
		croD.append(StrHandler.lpad(contratCptBen.getContratCptId().getNumCcptCcpt().toString(), '0', 6) + ";");

		this.setCroText(croD.toString());

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