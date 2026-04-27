package com.bna.smile.model.banqueAssurance.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Devise;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe qui permet de regler l'assureur
 * 
 * @author Y.BOUSSEN
 * @since 23/10/2010
 */

public class ReglementAssureurTrt extends Traitement {

	Logger logger = Logger.getLogger(PrelevementAdhesionAssuranceVieTrt.class);
	Context context = ContextHandler.getContext();

	public ReglementAssureurTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		ParamAdhesion paramAdhesionEnv = (ParamAdhesion) vo;
		AdhesionAssVie adhesionAssVie = paramAdhesionEnv.getAdhesionAssVie();

		try {
			ContratCpt contratCptAss = paramAdhesionEnv.getNouveauCpt();

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");

			contratCptAss = (ContratCpt) searchEngine.get(ContratCpt.class, contratCptAss.getContratCptId());

			OperationMoyPay operationMoyPay = new OperationMoyPay();
			OperationMoyPay operationMoyPayRetenuS = new OperationMoyPay();
			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			Operation operation = new Operation();

			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(adhesionAssVie.getContratCpt().getContratCptId().getCodStrcStrc());
			Structure structureRecep = new Structure();
			structureRecep.setCodStrcStrc(contratCptAss.getStructure().getCodStrcStrc());

			Devise devise = new Devise();
			devise.setCodDevDev(Constants.COD_DEV_DINAR);
			operationMoyPay.setDevise(devise);
			operationMoyPayRetenuS.setDevise(devise);
			operationMoyPay.setContratCpt(contratCptAss);
			operationMoyPayRetenuS.setContratCpt(contratCptAss);
			operationMoyPay.setStructureInitiatrice(structureInit);
			operationMoyPayRetenuS.setStructureInitiatrice(structureInit);
			operationMoyPay.setStructureReceptrice(structureRecep);
			operationMoyPayRetenuS.setStructureReceptrice(structureRecep);
			operationMoyPay.setNumMoypOmp("00000");
			operationMoyPayRetenuS.setNumMoypOmp("00000");
			operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
			operationMoyPayRetenuS.setCodEtatOmp(Constants.COD_VALIDATION);
			operationMoyPay.setPersonnelInitiateur(personnelInit);// / personne initiatrice
			operationMoyPay.setPersonnelValideur(personnelInit);// / personnel initiatrice = personnel validateur
			operationMoyPayRetenuS.setPersonnelInitiateur(personnelInit);// / personne initiatrice // ponctuel
			operationMoyPayRetenuS.setPersonnelValideur(personnelInit);// / personnel initiatrice = personnel validateur

			operation.setCodOperOper(Constants.COD_OPER_REGLEMENT_ASSUR_VIE);

			Tache tache = new Tache();
			tache.setOperation(operation);
			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(operation.getCodOperOper());
			tacheId.setCodTachTach(Constants.COD_TACH_REGLEMENT_ASSUR_VIE);
			tache.setTacheId(tacheId);
			operationMoyPay.setTache(tache);
			operationMoyPayRetenuS.setTache(tache);
			Date d = new Date();

			// /*** la derniere journé ouverte pour cette agence
			operationMoyPay.setDatOperOmp(paramAdhesionEnv.getDateComptable());
			operationMoyPay.setDatSystOmp(new Date());
			operationMoyPay.setDatValOmp(getDateCompt(getFirstDayOfQuarter(d)));

			operationMoyPayRetenuS.setDatOperOmp(paramAdhesionEnv.getDateComptable());
			operationMoyPayRetenuS.setDatSystOmp(new Date());
			operationMoyPayRetenuS.setDatValOmp(getDateCompt(getFirstDayOfQuarter(d)));
			Produit produitAdhesionOmp = new Produit();

			// /* ajouté le 18/01/2016
			operationMoyPay.setCodRefbOmp(" Cpt "
					+ StrHandler.lpad(adhesionAssVie.getContratCpt().getStructure().getCodStrcStrc().toString(), '0', 3)
					+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getCodPrdPrd().toString(), '0',
							4)
					+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), '0',
							6));
			// /operationMoyPay.setCodRefbOmp(adhesionAssVie.getNumSeqAdh().toString());
			operationMoyPay.setCodRefcOmp(adhesionAssVie.getNumSeqAdh().toString());
			operationMoyPayRetenuS.setCodRefcOmp(adhesionAssVie.getNumSeqAdh().toString());
			produitAdhesionOmp.setCodPrdPrd(Constants.COD_PRD_ASSUR_VIE);
			operationMoyPay.setProduit(produitAdhesionOmp);
			operationMoyPayRetenuS.setProduit(produitAdhesionOmp);

			TypePiece typePieceDem = adhesionAssVie.getClient().getPersonne().getTypePiece();
			operationMoyPay.setTypePieceDemandeur(typePieceDem);
			operationMoyPay.setNumPcedOmp(adhesionAssVie.getClient().getPersonne().getNumPcePers());
			operationMoyPay.setNomNomdOmp(adhesionAssVie.getClient().getPersonne().getNomNomPers());
			operationMoyPay.setNomPrndOmp(adhesionAssVie.getClient().getPersonne().getNomPrnPers());
			operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);
			operationMoyPay.setCodDemOmp("T"); // /*** type demandeur (Titulaire,CoTitul,Mandataire)
			operationMoyPayRetenuS.setTypePieceDemandeur(typePieceDem);
			operationMoyPayRetenuS.setNumPcedOmp(adhesionAssVie.getClient().getPersonne().getNumPcePers());
			operationMoyPayRetenuS.setNomNomdOmp(adhesionAssVie.getClient().getPersonne().getNomNomPers());
			operationMoyPayRetenuS.setNomPrndOmp(adhesionAssVie.getClient().getPersonne().getNomPrnPers());
			operationMoyPayRetenuS.setCodSensOmp(Constants.COD_SENS_CR);
			operationMoyPayRetenuS.setCodDemOmp("T"); // /*** type demandeur (Titulaire,CoTitul,Mandataire)
			operationMoyPay.setMontDinOmp(adhesionAssVie.getTarifAssVie().getMontPrmaTass());
			operationMoyPay.setMontSoldCcpt(contratCptAss.getMontSoldCcpt());
		
			operationMoyPay.setMontApreOmp(
					contratCptAss.getMontSoldCcpt() + adhesionAssVie.getTarifAssVie().getMontPrmaTass());

			operationMoyPay.setLibMotfOmp("Réglement Assureur (prime d'assurance vie)");
			InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt();
			operationMoyPay = (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPay);

			// /*** MAJ du montant actualisé dans le contrat compte de L'Assureur
			ContratCptSold contratCptSold = new ContratCptSold();
			contratCptSold.setContratCpt(contratCptAss);
			// contratCptSold.setSolde(Long.valueOf(adhesionAssVie.getTarifAssVie().getMontPrmaTass()));
			// / *** maj suite a l'operation Operation Retenu a la source le 25/01/2016
			contratCptSold.setSolde(Long.valueOf(
					new Long(new Double((new Double(adhesionAssVie.getTarifAssVie().getMontPrmaTass()).doubleValue())
							+ (Double.valueOf(adhesionAssVie.getTarifAssVie().getMontComTass())
									* Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100)).longValue())));

			contratCptSold.setSens("C");
			UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
			ContratCpt contratCpt = (ContratCpt) updateSoldTrt.exec(contratCptSold);

			// /*** Insertion Operation Retenu a la source le 25/01/2016

			HibernateTemplate hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
			hibernateTemplate.evict(operationMoyPay);

			operationMoyPayRetenuS.setCodRefbOmp(" Ret Src "
					+ StrHandler.lpad(adhesionAssVie.getContratCpt().getStructure().getCodStrcStrc().toString(), '0', 3)
					+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getCodPrdPrd().toString(), '0',
							4)
					+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), '0',
							6));
			
			operationMoyPayRetenuS.setMontDinOmp(Long.valueOf(
					(Double.valueOf((Math.ceil((Double.valueOf(adhesionAssVie.getTarifAssVie().getMontComTass())
							* Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100))))).longValue()));

			System.out.println(" ***  RTC :  *** " + (Double.valueOf(adhesionAssVie.getTarifAssVie().getMontComTass())
					* Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100));
			// /System.out.println(" *** Mont RTC : *** "+operationMoyPay.getMontDinOmp());///*** ajouté le 30/06/2016

			operationMoyPayRetenuS.setMontSoldCcpt(operationMoyPay.getMontApreOmp());
			operationMoyPayRetenuS
					.setMontApreOmp(
							Long.valueOf(Long.valueOf(Double
									.valueOf((Double.valueOf(operationMoyPay.getMontApreOmp())
											.doubleValue())
											+ Math.ceil(
													(Double.valueOf(adhesionAssVie.getTarifAssVie().getMontComTass())
															* Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100)))
									.longValue())));
			operationMoyPayRetenuS.setLibMotfOmp("Prime d'assurance vie (Retenue a la source)");
			// InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt();
			operationMoyPayRetenuS = (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPayRetenuS);

			if (paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc().intValue() != adhesionAssVie
					.getContratCpt().getStructure().getCodStrcStrc().intValue()) {
				this.setCroFlag(true);
			} else {
				this.setCroFlag(false);
			}

		} catch (Exception e) {
			this.setCroFlag(false);
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans ReglementAssureurTrt : ");
			text.append(e.toString());
			erreur.setCode("703");
			erreur.setDescription(text.toString());
			erreur.setKey("ReglementAssureurTrt");
			// /*** gerer une exception
			gestionException(adhesionAssVie, e);
			vo.addError(erreur);

		}
		return (adhesionAssVie);

	}

	private void gestionException(AdhesionAssVie adhesionAssVie, Exception e) {

		BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();
		batchExeptionPlac.setDatSystBate(new Date());
		batchExeptionPlac.setDatCompBate(adhesionAssVie.getDatePrelAdh());
		batchExeptionPlac.setStructure(adhesionAssVie.getContratCpt().getStructure());
		batchExeptionPlac.setLibTpbmBate("Exception Batch Prelevement Assurance Vie - Reglement Ass : ");
		batchExeptionPlac.setLibExpBate(e.getMessage());
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);

	}

	public Date getDateCompt(Date d) {

		try {
			Date DateReturn = DateHandler.addJour(d, -1);
			while (CalanderHandler.isJourFerier(d)) {
				DateReturn = DateHandler.addJour(d, -1);
				d = DateHandler.addJour(d, -1);
			}
			return (DateHandler.strToDate(DateHandler.dateToStr(DateReturn)));

		} catch (Exception e) {
			logger.error(" Erreur dans getDateCompt.execute : ", e);
			return (d);
		}
	}

	/**
	 * methode qui retourne la premiere journee du trimestre courant
	 * 
	 * @param d
	 * @return d
	 */
	public Date getFirstDayOfQuarter(Date d) {

		int mois = d.getMonth();
		if (mois >= 0 && mois < 3) {
			mois = 0;
		} else {
			if (mois > 2 && mois < 6) {
				mois = 3;
			} else {
				if (mois > 5 && mois < 9) {
					mois = 6;
				} else {
					mois = 9;
				}
			}
		}
		d.setDate(1);
		d.setMonth(mois);

		return d;
	}

	public void genCroText(ValueObject vo) {
		ParamAdhesion paramAdhesionEnv = (ParamAdhesion) vo;
		AdhesionAssVie adhesionAssVie = paramAdhesionEnv.getAdhesionAssVie();
		/* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.oxia.security.abc.model.Personnel user = null;
		if (obj instanceof UserDetails) {
			user = (com.oxia.security.abc.model.Personnel) obj;
		}

		this.setNumRefCro(Long.valueOf(adhesionAssVie.getNumSeqAdh()));

		this.setLibRefCro("SMILE.Reglement.Assureur.Vie");
		this.setDatValCro(getDateCompt(getFirstDayOfQuarter(new Date())));
		this.setCodeStructInitiatrice(paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc().toString());
		this.setCodStrcImpt(paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc());
		this.setCodEtatCro(0);
		this.setCodeProduit(Constants.COD_PRD_ASSUR_VIE.toString());
		this.setOperationId(Constants.COD_OPER_REGLEMENT_ASSUR_VIE.toString());
		this.setDateOperation(paramAdhesionEnv.getDateComptable());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(Constants.COD_TACH_REGLEMENT_ASSUR_VIE);
		if (adhesionAssVie.getNumSeqAdh() != null)
			this.setCodRefcOmp(adhesionAssVie.getNumSeqAdh().toString());
		this.setDatExecCro(new Date());

		this.setNumCinUser(user.getNumMatrUser());
		this.setCodTypUser(user.getMatriculeTyp());

		/* ------------------Garniture de la partie VARIABLE du CRO---------------------------------- */
		StringBuffer cro = new StringBuffer("");

		// /*** contratClient
		cro.append("numCptBna=");
		cro.append(StrHandler.lpad(adhesionAssVie.getContratCpt().getStructure().getCodStrcStrc().toString(), '0', 3)
				+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getCodPrdPrd().toString(), '0', 4)
				+ StrHandler.lpad(adhesionAssVie.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), '0', 6)
				+ ";");

		cro.append("contratAssureur=");
		cro.append(StrHandler.lpad(paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc().toString(), '0', 3)
				+ StrHandler.lpad(paramAdhesionEnv.getNouveauCpt().getContratCptId().getCodPrdPrd().toString(), '0', 4)
				+ StrHandler.lpad(paramAdhesionEnv.getNouveauCpt().getContratCptId().getNumCcptCcpt().toString(), '0',
						6)
				+ ";");

		cro.append("TARIF_ASS_VIE.MONT_PRMA_TASS=");
		cro.append(adhesionAssVie.getTarifAssVie().getMontPrmaTass() + ";");

		cro.append("TARIF_ASS_VIE.MONT_COM_TASS=");
		cro.append(adhesionAssVie.getTarifAssVie().getMontComTass() + ";");

		cro.append("TARIF_ASS_VIE.MONT_PRMG_TASS=");
		cro.append(adhesionAssVie.getTarifAssVie().getMontPrmgTass() + ";");

		cro.append("TARIF_ASS_VIE.MONT_RTNS_TASS=");
		cro.append(Math.round(new Double(adhesionAssVie.getTarifAssVie().getMontComTass())
				* Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100) + ";");

		if (paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc().intValue() != adhesionAssVie
				.getContratCpt().getStructure().getCodStrcStrc().intValue()) {
			cro.append("RefInterSiege=");
			cro.append(paramAdhesionEnv.getInterSiege() + ";");
			cro.append("IS=1;");
		}

		this.setCroText(cro.toString());
	}

	public String getNumeroTache(ValueObject vo) {
		return Constants.CODE_RESSOURCE_GENERALE;
	}

}
