package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.bna.smile.model.domainecompensation.gestionrejet.model.BlocageChqVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.util.DateHandler;

public class BlocageProvisionTrt extends Traitement {
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");

	public BlocageProvisionTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
		BlocageChqVo blocageChqVo = (BlocageChqVo) vo;
		try {

			OperationMoyPay operationMoyPay = new OperationMoyPay();
			/*
			 * insertion du blocage
			 */

			crudService.create(blocageChqVo.getBlocageCheque());

			// /-------------------- Condition de Banque Tireur
			// ---------------------------///

			String valueDateRecue = "";

			TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
			traitementConditionBanque
					.setCodOperOper(Constants.COD_OPER_BLOC_PROV + "");
			traitementConditionBanque.setCodStrcStrc(blocageChqVo
					.getBlocageCheque().getContratCpt().getContratCptId()
					.getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(blocageChqVo
					.getBlocageCheque().getContratCpt().getContratCptId()
					.getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(blocageChqVo
					.getBlocageCheque().getContratCpt().getContratCptId()
					.getNumCcptCcpt().toString());
			traitementConditionBanque.setDateReference(DateHandler
					.dateToStr(blocageChqVo.getDateComptable()));

			traitementConditionBanque = getConditionDeBanque(traitementConditionBanque);

			valueDateRecue = traitementConditionBanque.getDatevaleur();

			if (valueDateRecue != null && valueDateRecue.equals("NAN")) {
				valueDateRecue = null;
			}

			// / ------------------Operation Moyen de Paiement
			// ---------------------------- ///

			operationMoyPay.setLibObjOpOmp("BLOCAGE PROVISION");
			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			operationMoyPay.setPersonnelInitiateur(personnelInit);
			operationMoyPay.setPersonnelValideur(personnelInit);
			// 02. setting structure initiatrice
			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(blocageChqVo.getBlocageCheque()
					.getContratCpt().getContratCptId().getCodStrcStrc());
			operationMoyPay.setStructureInitiatrice(structureInit);
			operationMoyPay.setStructureReceptrice(structureInit);
			// 07. setting devise et montant
			Devise devise = new Devise();
			devise.setCodDevDev(Constants.COD_DEV_DINAR);
			operationMoyPay.setMontDinOmp(blocageChqVo.getBlocageCheque()
					.getMntBlocBloc());
			operationMoyPay.setMontApreOmp(blocageChqVo.getBlocageCheque()
					.getContratCpt().getMontSoldCcpt()
					- blocageChqVo.getBlocageCheque().getMntBlocBloc());

			operationMoyPay.setMontSoldCcpt(blocageChqVo.getBlocageCheque()
					.getContratCpt().getMontSoldCcpt());
			operationMoyPay.setDevise(devise);

			// 08. setting contrat compte
			operationMoyPay.setContratCpt(blocageChqVo.getBlocageCheque()
					.getContratCpt());

			// 09. setting type demandeur (Titulaire, CoTitulaire, Mandataire)

			operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);
			// 10.1 insertion du Donneur D'ordre

			TypePiece typePieceDemandeur = new TypePiece();
			typePieceDemandeur.setCodTpceTpce(Long.valueOf(blocageChqVo
					.getBlocageCheque().getContratCpt().getClient()
					.getPersonne().getTypePiece().getCodTpceTpce()));
			operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
			operationMoyPay.setNumPcedOmp(blocageChqVo.getBlocageCheque()
					.getContratCpt().getClient().getPersonne().getNumPcePers());
			operationMoyPay
					.setNomNomdOmp(blocageChqVo.getBlocageCheque()
							.getContratCpt().getClient().getPersonne()
							.getNomNommPers());
			operationMoyPay.setNomPrndOmp(blocageChqVo.getBlocageCheque()
					.getContratCpt().getClient().getPersonne().getNomPrnPers());

			// 11 Preparing data of Operation and tache
			Operation oper = new Operation();
			oper.setCodOperOper(Constants.COD_OPER_BLOC_PROV);
			Tache tache = new Tache();
			tache.setOperation(oper);
			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(Constants.COD_OPER_BLOC_PROV);
			operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
			tacheId.setCodTachTach(Constants.COD_TACH_BLOC_PROV);
			tache.setTacheId(tacheId);
			operationMoyPay.setTache(tache);
			Date dateOperOmp = blocageChqVo.getDateComptable();
			operationMoyPay.setDatOperOmp(dateOperOmp);

			// 14. setting date valeur moyen paiement

			Date dateValOmp = null;
			if (valueDateRecue != null && valueDateRecue.length() > 0) {
				dateValOmp = formaterDate.parse(valueDateRecue);
			} else {
				dateValOmp = blocageChqVo.getDateComptable();
			}
			operationMoyPay.setDatValOmp(dateValOmp);

			// 14.1 setting date system moyen paiement
			Date dateSysOmp = formaterDate.parse(formaterDate
					.format(new Date()));
			operationMoyPay.setDatSystOmp(dateSysOmp);
			// 15. setting sens operation
			operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
			operationMoyPay.setCodRefcOmp("9999");
			operationMoyPay.setLibMotfOmp("BLOCAGE PROVISION");
			// 02. insertion operation moyen paiement
			InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt = new InsertionOperationMoyPaySansCROTrt();
			operationMoyPay = (OperationMoyPay) insertOperationMoyPaySansCROTrt
					.exec(operationMoyPay);
			/*
			 * maj solde
			 */
			UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
			ContratCptSold contratCptSold = new ContratCptSold();
			contratCptSold.setContratCpt(blocageChqVo.getBlocageCheque()
					.getContratCpt());
			contratCptSold.setSolde(blocageChqVo.getBlocageCheque()
					.getMntBlocBloc());
			contratCptSold.setSens(Constants.COD_SENS_DB);
			ContratCpt contratRet = (ContratCpt) updateSoldTrt
					.exec(contratCptSold);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer(
					"Erreur dans BlocageProvisionTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("BlocageProvisionTrt");
			logger.error("Exception : ", e);
			throw new RuntimeException(e);

		}
		return blocageChqVo;
	}

	public void genCroText(ValueObject vo) 
	{
     
	}

	public TraitementConditionBanque getConditionDeBanque(
			TraitementConditionBanque traitementConditionBanque) {
		try {

			traitementConditionBanque.getCB();

		} catch (Exception e) {
			logger.error("Error occurred when trying to get bank conditions.",
					e);
		}

		return traitementConditionBanque;

	}

}
