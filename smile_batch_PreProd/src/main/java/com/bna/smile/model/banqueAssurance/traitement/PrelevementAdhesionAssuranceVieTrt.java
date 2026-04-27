package com.bna.smile.model.banqueAssurance.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.log4j.Logger;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Devise;
import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.JourneeStructureId;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.GetJourneeStructureTrt;
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
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe qui permet de prélver un tarif d'assurance vie pour chaque client ayant une adhesion (1 fois par 3 mois)
 * 
 * @author Y.BOUSSEN
 * @since 15/10/2010
 */

public class PrelevementAdhesionAssuranceVieTrt extends Traitement {

	Logger logger = Logger.getLogger(PrelevementAdhesionAssuranceVieTrt.class);

	public PrelevementAdhesionAssuranceVieTrt() {
	}

	Context context = ContextHandler.getContext();

	public IValueObject perform(IValueObject vo) {

		ParamAdhesion paramAdhesionEnv = (ParamAdhesion) vo;
		AdhesionAssVie adhesionAssVie = paramAdhesionEnv.getAdhesionAssVie();

		try {
			OperationMoyPay operationMoyPay = new OperationMoyPay();

			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			Operation operation = new Operation();

			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(adhesionAssVie.getContratCpt().getContratCptId().getCodStrcStrc());
			Structure structureRecep = new Structure();
			structureRecep.setCodStrcStrc(adhesionAssVie.getContratCpt().getContratCptId().getCodStrcStrc());

			Devise devise = new Devise();
			devise.setCodDevDev(Constants.COD_DEV_DINAR);
			operationMoyPay.setDevise(devise);

			operationMoyPay.setContratCpt(adhesionAssVie.getContratCpt());

			operationMoyPay.setStructureInitiatrice(structureInit);
			operationMoyPay.setStructureReceptrice(structureRecep);

			operationMoyPay.setNumMoypOmp("00000");

			operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
			operationMoyPay.setPersonnelInitiateur(personnelInit);// / personne initiatrice seulement au cas de retrait
																  // ponctuel
			operationMoyPay.setPersonnelValideur(personnelInit);// / personnel initiatrice = personnel validateur

			operation.setCodOperOper(Constants.COD_OPER_PRELEV_ASSUR_VIE);

			Tache tache = new Tache();
			tache.setOperation(operation);
			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(operation.getCodOperOper());
			tacheId.setCodTachTach(Constants.COD_TACH_PRELEV_ASSUR_VIE);
			tache.setTacheId(tacheId);
			operationMoyPay.setTache(tache);
			Date d = new Date();
			// /*** la derniere journé ouverte pour cette agence
			JourneeStructure journeeStructure = new JourneeStructure();
			JourneeStructureId journeeStructureId = new JourneeStructureId();
			journeeStructureId.setCodStrcStrc(structureInit.getCodStrcStrc());
			journeeStructure.setJourneeStructureId(journeeStructureId);
			GetJourneeStructureTrt getJourneeStructureTrt = new GetJourneeStructureTrt();
			journeeStructure = (JourneeStructure) getJourneeStructureTrt.exec(journeeStructure);

			operationMoyPay.setDatOperOmp(journeeStructure.getJourneeStructureId().getDatJrnJrn());
			operationMoyPay.setDatSystOmp(new Date());
			operationMoyPay.setDatValOmp(getDateCompt(getFirstDayOfQuarter(d)));

			System.out.println(" *DatValOmp " + operationMoyPay.getDatValOmp() + " -- "
					+ DateHandler.dateToStr(operationMoyPay.getDatValOmp()));

			Produit produitAdhesionOmp = new Produit();

			operationMoyPay.setCodRefbOmp(adhesionAssVie.getNumSeqAdh().toString());
			operationMoyPay.setCodRefcOmp(adhesionAssVie.getNumSeqAdh().toString());
			produitAdhesionOmp.setCodPrdPrd(Constants.COD_PRD_ASSUR_VIE);
			operationMoyPay.setProduit(produitAdhesionOmp);

			TypePiece typePieceDem = adhesionAssVie.getClient().getPersonne().getTypePiece();
			operationMoyPay.setTypePieceDemandeur(typePieceDem);
			operationMoyPay.setNumPcedOmp(adhesionAssVie.getClient().getPersonne().getNumPcePers());
			operationMoyPay.setNomNomdOmp(adhesionAssVie.getClient().getPersonne().getNomNomPers());
			operationMoyPay.setNomPrndOmp(adhesionAssVie.getClient().getPersonne().getNomPrnPers());
			operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
			operationMoyPay.setMontDinOmp(adhesionAssVie.getTarifAssVie().getMontPrmgTass());
			operationMoyPay.setMontSoldCcpt(adhesionAssVie.getContratCpt().getMontSoldCcpt());
			operationMoyPay.setCodDemOmp("T"); // /*** type demandeur (Titulaire,CoTitul,Mandataire)
			operationMoyPay.setMontApreOmp(Long.valueOf(new Long(new Double((new Double(adhesionAssVie.getContratCpt()
					.getMontSoldCcpt()).doubleValue())
					- (new Double(adhesionAssVie.getTarifAssVie().getMontPrmgTass()))).longValue())));
			operationMoyPay.setLibMotfOmp("Prélévement de la prime d'assurance vie");
			InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt();
			operationMoyPay = (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPay);

			// /*** MAJ du montant actualisé dans le contrat compte
			ContratCptSold contratCptSold = new ContratCptSold();
			contratCptSold.setContratCpt(adhesionAssVie.getContratCpt());
			contratCptSold.setSolde(Long.valueOf(adhesionAssVie.getTarifAssVie().getMontPrmgTass()));
			contratCptSold.setSens("D");
			UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
			ContratCpt contratCpt = (ContratCpt) updateSoldTrt.exec(contratCptSold);

			// /*** MAJ de l'adhesionAssVie
			adhesionAssVie.setDatePrelAdh(journeeStructure.getJourneeStructureId().getDatJrnJrn());
			UpdateAdhesionAssVieTrt updateAdhesionAssVieTrt = new UpdateAdhesionAssVieTrt();
			adhesionAssVie = (AdhesionAssVie) updateAdhesionAssVieTrt.exec(adhesionAssVie);

			// /*** MAJ du montant dans le contrat compte MAGHREBIA(MAJ solde+CRO)
			ReglementAssureurTrt reglementAssureurTrt = new ReglementAssureurTrt();
			adhesionAssVie = (AdhesionAssVie) reglementAssureurTrt.exec(paramAdhesionEnv);

			this.setCroFlag(true);

		} catch (Exception e) {
			this.setCroFlag(false);
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans PrelevementAdhesionAssuranceVieTrt : ");
			text.append(e.toString());
			erreur.setCode("672");
			erreur.setDescription(text.toString());
			erreur.setKey("PrelevementAdhesionAssuranceVieTrt");
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
		batchExeptionPlac.setLibTpbmBate("Exception Batch Prelevement Assurance Vie : ");
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

		this.setLibRefCro("SMILE.Prelev.Assur.Vie");
		this.setDatValCro(getDateCompt(getFirstDayOfQuarter(new Date())));
		System.out.println(" * getFirstDayOfQuarter * " + getFirstDayOfQuarter(new Date()) + " -- "
				+ DateHandler.dateToStr(getFirstDayOfQuarter(new Date())));
		this.setCodeStructInitiatrice(adhesionAssVie.getContratCpt().getStructure().getCodStrcStrc().toString());
		this.setCodStrcImpt(adhesionAssVie.getContratCpt().getStructure().getCodStrcStrc());
		this.setCodEtatCro(0);
		this.setCodeProduit(Constants.COD_PRD_ASSUR_VIE.toString());
		this.setOperationId(Constants.COD_OPER_PRELEV_ASSUR_VIE.toString());
		this.setDateOperation(adhesionAssVie.getDatePrelAdh());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(Constants.COD_TACH_PRELEV_ASSUR_VIE);
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
						6) + ";");

		cro.append("TARIF_ASS_VIE.MONT_PRMA_TASS=");
		cro.append(adhesionAssVie.getTarifAssVie().getMontPrmaTass() + ";");

		cro.append("TARIF_ASS_VIE.MONT_COM_TASS=");
		cro.append(adhesionAssVie.getTarifAssVie().getMontComTass() + ";");

		cro.append("TARIF_ASS_VIE.MONT_PRMG_TASS=");
		cro.append(adhesionAssVie.getTarifAssVie().getMontPrmgTass() + ";");

		cro.append("TARIF_ASS_VIE.MONT_RTNS_TASS=");
		cro.append(Math.round(new Double(adhesionAssVie.getTarifAssVie().getMontComTass())
				* Constants.TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE / 100)
				+ ";");

		cro.append("RefInterSiege=");
		if (paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc().intValue() != adhesionAssVie
				.getContratCpt().getStructure().getCodStrcStrc().intValue()) {
			cro.append(paramAdhesionEnv.getInterSiege() + ";");
			cro.append("IS=1;");
		} else {
			cro.append("0;");
			cro.append("IS=0;");
		}
		cro.append("COD_STRC_ASS=");
		cro.append(StrHandler.lpad(paramAdhesionEnv.getNouveauCpt().getStructure().getCodStrcStrc().toString(), '0', 3)
				+ ";");

		this.setCroText(cro.toString());
	}

	public String getNumeroTache(ValueObject vo) {
		return Constants.CODE_RESSOURCE_GENERALE;
	}

}
