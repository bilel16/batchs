package com.bna.smile.model.virement.traitement;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.FluxComptVirement;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Produit;
import com.bna.commun.model.VirSgmt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecommun.traitement.GetRibTrt;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.fwk.util.DateHandler;

public class ExecutionDoVirMasseTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
	IExpression expression = searchEngine.createExpression();
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeureFichier = new SimpleDateFormat("HHmmss");
	String codeBanque = "";
	String fileName = "";
	// SimpleDateFormat formaterDate1 = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat formaterDate2 = new SimpleDateFormat("ddMMyyyy");
	String codeDevise = Constants.COD_DEV_DINAR.toString();
	File file = null;
	boolean etatClientTaxable = false;
	ContratCpt contratCptDO = new ContratCpt();
	Date dateComptable = new Date();
	Date dateComptableFichier;
	String codStrcStrc = "";
	String pathVirementTravail = "";
	Produit produit = new Produit();

	public ExecutionDoVirMasseTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;

		GlobalVirement globalVirement = (GlobalVirement) virementVo.getGlobalVirement();
		// OperationMoyPay operationMoyPayCompteDO = new OperationMoyPay();
		OperationMoyPay operationMoyPayCompteDOMemeAgence = new OperationMoyPay();
		OperationMoyPay operationMoyPayCompteDOAutreAgence = new OperationMoyPay();
		OperationMoyPay operationMoyPayCompteDOAutreBanque = new OperationMoyPay();
		OperationMoyPay operationMoyPayCompteDOAutreBanqueSGMT = new OperationMoyPay();

		dateComptable = virementVo.getDateComptableAgence();
		dateComptableFichier = new Date();// virementVo.getDateComptableAgence();
		boolean etatValiditeBenif = false;
		boolean etatBenifMemeAgence = false;
		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		VirementService virementService = (VirementService) context.getBean("iVirementService");
		Long[] etatsVirements =
				{ Constants.COD_ETAT_VIREMENT_ATTENTE, Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN,
						Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF };

		String heureString = formaterHeure.format(new Date());

		long montant_Rejets = 0;
		int nbreRejets = 0;

		long numLotAgence = 0;
		String numLot = "";

		codStrcStrc = globalVirement.getStructure().getCodStrcStrc().toString();

		long mntVirementADT = 0;
		long nbrVirementADT = 0;
		long numeroLigneADT = 0;

		produit.setCodPrdPrd(Constants.COD_PRODUIT_VIREMENT_MASSE);
		long nbreVirementNormal = 0; // / mont virement<100 000 D
		long nbreVirementSGMT = 0; // / mont virement>100 000 D
		long commissionTotal = 0; // Commission Totale
		long tvaTotal = 0; // TVA Totale
		String dateValeur = ""; // Date Valeur de l'operation
		String dateValeurCommission = ""; // Date Valeur de la commission

		/*********** New CB *******************/

		long nbreVirementMemeAgence = 0;
		long nbreVirementAutreAgence = 0;
		long nbreVirementAutreBanque = 0;
		long nbreVirementAutreBanqueSGMT = 0;

		long nbreVirementMemeAgenceNormal = 0;
		long nbreVirementAutreAgenceNormal = 0;

		long nbreVirementMemeAgenceSGMT = 0;
		long nbreVirementAutreAgenceSGMT = 0;

		long mntCommissionVirementMemeAgence = 0;
		long mntCommissionVirementAutreAgence = 0;
		long mntCommissionVirementAutreBanque = 0;
		long mntCommissionVirementAutreBanqueSGMT = 0;

		long montant_tvaVirementMemeAgence = 0;
		long montant_tvaVirementAutreAgence = 0;
		long montant_tvaVirementAutreBanque = 0;
		long montant_tvaVirementAutreBanqueSGMT = 0;

		String dateValeurMemeAgence = ""; // Date Valeur de l'operation
		String dateValeurAutreAgence = "";
		String dateValeurAutreBanque = "";
		String dateValeurAutreBanqueSGMT = "";

		long mntVirementMemeAgence = 0;
		long mntVirementAutreAgence = 0;
		long mntVirementAutreBanque = 0;
		long mntVirementAutreBanqueSGMT = 0;

		long mntCommissionVirementPetitMnt = 0;
		long mntCommissionVirementGrandMnt = 0;

		long montant_tvaVirementPetitMnt = 0;
		long montant_tvaVirementGrandMnt = 0;
		String codRefcVir = "";

		try {
			this.setCroFlag(false);

			contratCptDO = globalVirement.getContratCpt();
			// operationMoyPayCompteDO = virementVo.getOperationMoyPay();

			// *********** Find Liste details for global virement *******************//

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			criteria.add(expression.eq("detailVirementId.numSeqGvir", globalVirement.getNumSeqGvir()));
			criteria.add(expression.in("etaDetvDetv", etatsVirements));

			List<DetailVirement> list_Details =
					new ArrayList<DetailVirement>(searchEngine.find(DetailVirement.class, criteria));

			etatClientTaxable = virementVo.isEtatTaxableClient();
			// *********** Parcours liste details one by one *******************//

			if (list_Details != null && list_Details.size() > 0) {

				// /************** Determiner le nbre et le montant d'un virement ADT ********//
				for (DetailVirement detailVirement : list_Details) {
					codeBanque = detailVirement.getRibBenDetv().substring(0, 2);
					// if (detailVirement.getMntDetvDetv().longValue() < Constants.MONTANT_VIREMENT_SGMT.longValue()) {
					//
					// nbreVirementNormal++;
					// } else {
					// nbreVirementSGMT++;
					// }
					//
					// if (codeBanque.equals("03") == false) {
					// if (detailVirement.getMntDetvDetv().longValue() < Constants.MONTANT_VIREMENT_SGMT.longValue()) {
					// mntVirementADT += detailVirement.getMntDetvDetv();
					// nbrVirementADT++;
					// }
					// }

					if (codeBanque.equals("03")) {

						if (new Long(detailVirement.getRibBenDetv().substring(5, 8)).longValue() == globalVirement
								.getStructure().getCodStrcStrc().longValue()) {

							mntVirementMemeAgence += detailVirement.getMntDetvDetv();
							nbreVirementMemeAgence++;

							if (detailVirement.getMntDetvDetv().longValue() < Constants.MONTANT_VIREMENT_SGMT
									.longValue()) {

								nbreVirementMemeAgenceNormal++;
							} else {
								nbreVirementMemeAgenceSGMT++;
							}

						} else {

							mntVirementAutreAgence += detailVirement.getMntDetvDetv();
							nbreVirementAutreAgence++;

							if (detailVirement.getMntDetvDetv().longValue() < Constants.MONTANT_VIREMENT_SGMT
									.longValue()) {

								nbreVirementAutreAgenceNormal++;
							} else {
								nbreVirementAutreAgenceSGMT++;
							}
						}

					} else {

						if (detailVirement.getMntDetvDetv().longValue() < Constants.MONTANT_VIREMENT_SGMT.longValue()) {

							mntVirementADT += detailVirement.getMntDetvDetv();
							nbrVirementADT++;
							mntVirementAutreBanque += detailVirement.getMntDetvDetv();
							nbreVirementAutreBanque++;

						} else {
							mntVirementAutreBanqueSGMT += detailVirement.getMntDetvDetv();
							nbreVirementAutreBanqueSGMT++;
						}

					}

				}

				// / ------------- Traitement Condition de banque -------- ///

				VirementVo virementVoCBMemeAgence = new VirementVo();
				VirementVo virementVoCBAutreAgence = new VirementVo();
				VirementVo virementVoCBAutreBanque = new VirementVo();
				VirementVo virementVoCBAutreBanqueSGMT = new VirementVo();
				ArrayList<String> palierCharMemeAgence = new ArrayList<String>();
				ArrayList<String> palierCharAutreAgence = new ArrayList<String>();
				ArrayList<String> palierCharAutreBanque = new ArrayList<String>();
				ArrayList<String> palierCharAutreBanqueSGMT = new ArrayList<String>();

				Operation operationCB = new Operation();
				Operation oper = new Operation();

				if (globalVirement.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_MASSE.longValue()) {

					operationCB.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_MASSE);
					oper.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_MASSE);
				}

				if (nbreVirementMemeAgence > 0) {

					palierCharMemeAgence.add("41");

					virementVoCBMemeAgence.setContratCpt(globalVirement.getContratCpt());
					virementVoCBMemeAgence.setOperation(operationCB);
					virementVoCBMemeAgence.setMontant_virement(new Long("10000"));
					virementVoCBMemeAgence.setGlobalVirement(globalVirement);
					virementVoCBMemeAgence.setDateComptableAgence(dateComptable);
					virementVoCBMemeAgence.setListePalierCaractere(palierCharMemeAgence);
					virementVoCBMemeAgence = getConditionDeBanque(virementVoCBMemeAgence);
					dateValeurMemeAgence = virementVoCBMemeAgence.getValueDateRecue();
					dateValeurCommission = virementVoCBMemeAgence.getValueDateComRecue();
				}
				if (nbreVirementAutreAgence > 0) {

					palierCharAutreAgence.add("42");

					virementVoCBAutreAgence.setContratCpt(globalVirement.getContratCpt());
					virementVoCBAutreAgence.setOperation(operationCB);
					virementVoCBAutreAgence.setMontant_virement(new Long("10000"));
					virementVoCBAutreAgence.setGlobalVirement(globalVirement);
					virementVoCBAutreAgence.setDateComptableAgence(dateComptable);
					virementVoCBAutreAgence.setListePalierCaractere(palierCharAutreAgence);
					virementVoCBAutreAgence = getConditionDeBanque(virementVoCBAutreAgence);
					dateValeurAutreAgence = virementVoCBAutreAgence.getValueDateRecue();
					dateValeurCommission = virementVoCBAutreAgence.getValueDateComRecue();
				}
				if (nbreVirementAutreBanque > 0) {

					palierCharAutreBanque.add("43");

					virementVoCBAutreBanque.setContratCpt(globalVirement.getContratCpt());
					virementVoCBAutreBanque.setOperation(operationCB);
					virementVoCBAutreBanque.setMontant_virement(new Long("10000"));
					virementVoCBAutreBanque.setGlobalVirement(globalVirement);
					virementVoCBAutreBanque.setDateComptableAgence(dateComptable);
					virementVoCBAutreBanque.setListePalierCaractere(palierCharAutreBanque);
					virementVoCBAutreBanque = getConditionDeBanque(virementVoCBAutreBanque);
					dateValeurAutreBanque = virementVoCBAutreBanque.getValueDateRecue();
					dateValeurCommission = virementVoCBAutreBanque.getValueDateComRecue();
				}
				if (nbreVirementAutreBanqueSGMT > 0) {

					palierCharAutreBanqueSGMT.add("43");

					virementVoCBAutreBanqueSGMT.setContratCpt(globalVirement.getContratCpt());
					virementVoCBAutreBanqueSGMT.setOperation(operationCB);
					virementVoCBAutreBanqueSGMT.setMontant_virement(Constants.MONTANT_VIREMENT_SGMT);
					virementVoCBAutreBanqueSGMT.setGlobalVirement(globalVirement);
					virementVoCBAutreBanqueSGMT.setDateComptableAgence(dateComptable);
					virementVoCBAutreBanqueSGMT.setListePalierCaractere(palierCharAutreBanqueSGMT);
					virementVoCBAutreBanqueSGMT = getConditionDeBanque(virementVoCBAutreBanqueSGMT);
					dateValeurAutreBanqueSGMT = virementVoCBAutreBanqueSGMT.getValueDateRecue();
					dateValeurCommission = virementVoCBAutreBanqueSGMT.getValueDateComRecue();
				}

				// *********** Lecture des Commissions CB ******************//

				VirementVo virementVoCBPetitMNT = new VirementVo();
				VirementVo virementVoCBGrandMNT = new VirementVo();
				palierCharAutreBanque.add("41");
				virementVoCBPetitMNT.setContratCpt(globalVirement.getContratCpt());
				virementVoCBPetitMNT.setOperation(operationCB);
				virementVoCBPetitMNT.setMontant_virement(new Long("10000"));
				virementVoCBPetitMNT.setGlobalVirement(globalVirement);
				virementVoCBPetitMNT.setDateComptableAgence(dateComptable);
				virementVoCBPetitMNT.setListePalierCaractere(palierCharAutreBanque);
				virementVoCBPetitMNT = getConditionDeBanque(virementVoCBPetitMNT);
				if (virementVoCBPetitMNT.getCommissionRecue().isEmpty() == false) {
					mntCommissionVirementPetitMnt =
							new Long(StrHandler.strToMnt(virementVoCBPetitMNT.getCommissionRecue()));
				}
				if (virementVoCBPetitMNT.getTvaRecue().isEmpty() == false) {
					montant_tvaVirementPetitMnt = new Long(StrHandler.strToMnt(virementVoCBPetitMNT.getTvaRecue()));
				}

				virementVoCBGrandMNT.setContratCpt(globalVirement.getContratCpt());
				virementVoCBGrandMNT.setOperation(operationCB);
				virementVoCBGrandMNT.setMontant_virement(Constants.MONTANT_VIREMENT_SGMT);
				virementVoCBGrandMNT.setGlobalVirement(globalVirement);
				virementVoCBGrandMNT.setDateComptableAgence(dateComptable);
				virementVoCBGrandMNT.setListePalierCaractere(palierCharAutreBanque);
				virementVoCBGrandMNT = getConditionDeBanque(virementVoCBGrandMNT);
				if (virementVoCBGrandMNT.getCommissionRecue().isEmpty() == false) {
					mntCommissionVirementGrandMnt =
							new Long(StrHandler.strToMnt(virementVoCBGrandMNT.getCommissionRecue()));
				}
				if (virementVoCBGrandMNT.getTvaRecue().isEmpty() == false) {
					montant_tvaVirementGrandMnt = new Long(StrHandler.strToMnt(virementVoCBGrandMNT.getTvaRecue()));
				}

				// ****** Commission Totale / TVA Totale ***********//

				mntCommissionVirementMemeAgence =
						(nbreVirementMemeAgenceNormal * mntCommissionVirementPetitMnt)
								+ (nbreVirementMemeAgenceSGMT * mntCommissionVirementGrandMnt);

				mntCommissionVirementAutreAgence =
						(nbreVirementAutreAgenceNormal * mntCommissionVirementPetitMnt)
								+ (nbreVirementAutreAgenceSGMT * mntCommissionVirementGrandMnt);

				mntCommissionVirementAutreBanque = (nbreVirementAutreBanque * mntCommissionVirementPetitMnt);
				mntCommissionVirementAutreBanqueSGMT = (nbreVirementAutreBanqueSGMT * mntCommissionVirementGrandMnt);

				montant_tvaVirementMemeAgence =
						(nbreVirementMemeAgenceNormal * montant_tvaVirementPetitMnt)
								+ (nbreVirementMemeAgenceSGMT * montant_tvaVirementGrandMnt);

				montant_tvaVirementAutreAgence =
						(nbreVirementAutreAgenceNormal * montant_tvaVirementPetitMnt)
								+ (nbreVirementAutreAgenceSGMT * montant_tvaVirementGrandMnt);

				montant_tvaVirementAutreBanque = (nbreVirementAutreBanque * montant_tvaVirementPetitMnt);
				montant_tvaVirementAutreBanqueSGMT = (nbreVirementAutreBanqueSGMT * montant_tvaVirementGrandMnt);

				commissionTotal =
						mntCommissionVirementMemeAgence + mntCommissionVirementMemeAgence
								+ mntCommissionVirementAutreBanque + mntCommissionVirementAutreBanqueSGMT;

				if (commissionTotal < Constants.MIN_COMMISSION) {
					boolean etatCommissionPercu = false;
					if (mntVirementMemeAgence > 0 && etatCommissionPercu == false) {
						mntCommissionVirementMemeAgence = Constants.MIN_COMMISSION.longValue();
						montant_tvaVirementMemeAgence =
								Long.valueOf((long) (Constants.MIN_COMMISSION.longValue() * 0.19));
						mntCommissionVirementAutreAgence = 0;
						montant_tvaVirementAutreAgence = 0;
						mntCommissionVirementAutreBanque = 0;
						mntCommissionVirementAutreBanqueSGMT = 0;
						montant_tvaVirementAutreBanque = 0;
						montant_tvaVirementAutreBanqueSGMT = 0;
						etatCommissionPercu = true;

					} else if (mntVirementAutreAgence > 0 && etatCommissionPercu == false) {

						mntCommissionVirementMemeAgence = 0;
						montant_tvaVirementMemeAgence = 0;
						mntCommissionVirementAutreAgence = Constants.MIN_COMMISSION.longValue();
						montant_tvaVirementAutreAgence =
								Long.valueOf((long) (Constants.MIN_COMMISSION.longValue() * 0.19));
						mntCommissionVirementAutreBanque = 0;
						mntCommissionVirementAutreBanqueSGMT = 0;
						montant_tvaVirementAutreBanque = 0;
						montant_tvaVirementAutreBanqueSGMT = 0;
						etatCommissionPercu = true;

					} else if (mntVirementAutreBanque > 0 && etatCommissionPercu == false) {

						mntCommissionVirementMemeAgence = 0;
						montant_tvaVirementMemeAgence = 0;
						mntCommissionVirementAutreAgence = 0;
						montant_tvaVirementAutreAgence = 0;
						mntCommissionVirementAutreBanque = Constants.MIN_COMMISSION.longValue();
						montant_tvaVirementAutreBanque =
								Long.valueOf((long) (Constants.MIN_COMMISSION.longValue() * 0.19));
						mntCommissionVirementAutreBanqueSGMT = 0;
						montant_tvaVirementAutreBanqueSGMT = 0;
						etatCommissionPercu = true;
					} else if (mntVirementAutreBanqueSGMT > 0 && etatCommissionPercu == false) {

						mntCommissionVirementMemeAgence = 0;
						montant_tvaVirementMemeAgence = 0;
						mntCommissionVirementAutreAgence = 0;
						montant_tvaVirementAutreAgence = 0;
						mntCommissionVirementAutreBanque = 0;
						montant_tvaVirementAutreBanque = 0;
						mntCommissionVirementAutreBanqueSGMT = Constants.MIN_COMMISSION.longValue();
						montant_tvaVirementAutreBanqueSGMT =
								Long.valueOf((long) (Constants.MIN_COMMISSION.longValue() * 0.19));
						etatCommissionPercu = true;
					}

				}

				// ********************* Insertion dans Flux Comptable Virement ******//
				boolean etatMemeAgence = false;
				String codeAgence = "";
				long codAgenceInerSgmt = 900;
				long nbreVirAgence = 0;
				long mntVirAgence = 0;

				long nbreVirAgenceInter = 0;
				long mntVirAgenceInter = 0;
				long nbreVirAgenceSgmt = 0;
				long mntVirAgenceSgmt = 0;
				boolean trouveADT = false;
				boolean trouveSGMT = false;

				List<Long> listAgenceByVirement =
						virementGlobalDAO.getListeStructuresByVirement(globalVirement.getNumSeqGvir());

				if (listAgenceByVirement != null && listAgenceByVirement.size() > 0) {
					for (Long i : listAgenceByVirement) {
						nbreVirAgence = 0;
						mntVirAgence = 0;
						nbreVirAgenceInter = 0;
						mntVirAgenceInter = 0;
						nbreVirAgenceSgmt = 0;
						mntVirAgenceSgmt = 0;
						boolean trouveAgenceBNA = false;

						if (globalVirement.getStructure().getCodStrcStrc().longValue() == new Long(i).longValue()) {
							etatMemeAgence = true;
						} else {
							etatMemeAgence = false;
						}

						for (DetailVirement detailVir : list_Details) {

							codeBanque = detailVir.getRibBenDetv().substring(0, 2);
							codeAgence = detailVir.getRibBenDetv().substring(5, 8);

							// *************BNA/BNA****************//
							if (codeBanque.equals("03") == true && etatMemeAgence == false) {

								if (Long.valueOf(i).equals(Long.valueOf(codeAgence))) {

									trouveAgenceBNA = true;
									nbreVirAgence++;

									mntVirAgence += detailVir.getMntDetvDetv().longValue();
								}

							}
						}

						// *******Ajout dans la table FLUX_COMPT_VIREMENT BNA/BNA **********//

						if (nbreVirAgence != 0 && mntVirAgence != 0 && trouveAgenceBNA == true) {
							FluxComptVirement fluxComptVirement = new FluxComptVirement();
							Long numSeqFlux = virementGlobalDAO.getSequenceFluxComptableVirement();
							fluxComptVirement.setNumSeqFlux(numSeqFlux);
							fluxComptVirement.setCodStrcStrc(new Long(i));
							fluxComptVirement.setMntVirStrc(mntVirAgence);
							fluxComptVirement.setNbreVirStrc(nbreVirAgence);
							fluxComptVirement.setTypeFluxVir(new Long(0));
							fluxComptVirement.setDatOperFlux(dateComptable);
							fluxComptVirement.setDatSysFlux(new Date());
							fluxComptVirement.setNumSeqGvir(globalVirement.getNumSeqGvir());

							crudService.create(fluxComptVirement);
						}

					}
				}
				// *************BNA/Autre Banque****************//
				for (DetailVirement detailVir : list_Details) {

					codeBanque = detailVir.getRibBenDetv().substring(0, 2);
					codeAgence = detailVir.getRibBenDetv().substring(5, 8);
					if (codeBanque.equals("03") == false) {

						if (detailVir.getMntDetvDetv().longValue() < Constants.MONTANT_VIREMENT_SGMT) {
							trouveADT = true;
							nbreVirAgenceInter++;
							mntVirAgenceInter += detailVir.getMntDetvDetv().longValue();

						} else {
							trouveSGMT = true;
							nbreVirAgenceSgmt++;
							mntVirAgenceSgmt += detailVir.getMntDetvDetv().longValue();

						}

					}

				}
				// *************Ajout dans la table FLUX_COMPT_VIREMENT BNA/Autres Banques****************//

				if (nbreVirAgenceInter != 0 && mntVirAgenceInter != 0 && trouveADT == true) {

					FluxComptVirement fluxComptVirement = new FluxComptVirement();
					Long numSeqFlux = virementGlobalDAO.getSequenceFluxComptableVirement();
					fluxComptVirement.setNumSeqFlux(numSeqFlux);
					fluxComptVirement.setCodStrcStrc(new Long(codAgenceInerSgmt));
					fluxComptVirement.setMntVirStrc(mntVirAgenceInter);
					fluxComptVirement.setNbreVirStrc(nbreVirAgenceInter);
					fluxComptVirement.setTypeFluxVir(new Long(0));
					fluxComptVirement.setDatOperFlux(dateComptable);
					fluxComptVirement.setDatSysFlux(new Date());
					fluxComptVirement.setNumSeqGvir(globalVirement.getNumSeqGvir());

					crudService.create(fluxComptVirement);
					trouveADT = false;

					// ************** Recupuration du Numero de Lot **************** //
					VirementVo virementVoLot = new VirementVo();

					virementVoLot.setStructure(globalVirement.getStructure());

					virementVoLot = (VirementVo) virementService.getNumLotAgence(virementVoLot);

					numLotAgence = virementVoLot.getNumLot();

					numLot += numLotAgence;

					for (int i = new String(numLotAgence + "").length(); i < 4; i++) {

						numLot = "0" + numLot;

					}

					logger.info("numLot : " + numLot);

					// ***** Partie Creation Fichier *******//

					String codeStrcBCT = virementGlobalDAO.getCodeStructureBCT(Long.valueOf(codStrcStrc));

					pathVirementTravail =
							File.separatorChar + Configuration.getParentPath() + File.separatorChar
									+ Configuration.getLocalPathCheque() + File.separatorChar + "emis"
									+ File.separatorChar + "virement" + File.separatorChar + "agence" + codeStrcBCT
									+ File.separatorChar + formaterDate2.format(dateComptableFichier)
									+ File.separatorChar + "travail" + File.separatorChar;

					fileName =
							pathVirementTravail + "03" + "-" + codeStrcBCT + "-" + "10" + "-" + "21" + "-" + numLot
									+ "-" + formaterDate2.format(dateComptable) + "-"
									+ formaterHeureFichier.format(new Date()) + "-" + codeDevise + ".ENV";

					// *****************************************************************//
				}
				if (nbreVirAgenceSgmt != 0 && mntVirAgenceSgmt != 0 && trouveSGMT == true) {

					FluxComptVirement fluxComptVirement = new FluxComptVirement();
					Long numSeqFlux = virementGlobalDAO.getSequenceFluxComptableVirement();
					fluxComptVirement.setNumSeqFlux(numSeqFlux);
					fluxComptVirement.setCodStrcStrc(new Long(codAgenceInerSgmt));
					fluxComptVirement.setMntVirStrc(mntVirAgenceSgmt);
					fluxComptVirement.setNbreVirStrc(nbreVirAgenceSgmt);
					fluxComptVirement.setTypeFluxVir(new Long(1));
					fluxComptVirement.setDatOperFlux(dateComptable);
					fluxComptVirement.setDatSysFlux(new Date());
					fluxComptVirement.setNumSeqGvir(globalVirement.getNumSeqGvir());
					crudService.create(fluxComptVirement);

					trouveSGMT = false;
				}

				// /********** Creation CRO 718 // Operation Moyen Payement // Update Compte Client ******//

				if (mntVirementMemeAgence > 0) {
					VirementVo virementVoCreationCroExeVir = new VirementVo();
					virementVoCreationCroExeVir.setEtatTaxableClient(etatClientTaxable);
					virementVoCreationCroExeVir.setGlobalVirement(globalVirement);
					virementVoCreationCroExeVir.setDateComptableAgence(dateComptable);
					virementVoCreationCroExeVir.setValueDateRecue(dateValeurMemeAgence);
					virementVoCreationCroExeVir.setMontant_commissionRecue(mntCommissionVirementMemeAgence);
					virementVoCreationCroExeVir.setTvaRecue(montant_tvaVirementMemeAgence + "");
					virementVoCreationCroExeVir.setValueDateComRecue(dateValeurCommission);
					virementVoCreationCroExeVir.setMontantGlobalByCB(mntVirementMemeAgence);
					virementVoCreationCroExeVir.setNbreGlobalByCB(nbreVirementMemeAgence);
					virementVoCreationCroExeVir.setEtatBenifVirementCB(Long.valueOf(0));// 0: meme agence; 1 :autre
																						// agence; 2: autre banque ;
																						// 3:SGMT

					virementVoCreationCroExeVir =
							(VirementVo) virementService.creationCROExecutionVirementMasse(virementVoCreationCroExeVir);

					operationMoyPayCompteDOMemeAgence = virementVoCreationCroExeVir.getOperationMoyPay();
				}
				if (mntVirementAutreAgence > 0) {

					VirementVo virementVoCreationCroExeVir = new VirementVo();
					virementVoCreationCroExeVir.setEtatTaxableClient(etatClientTaxable);
					virementVoCreationCroExeVir.setGlobalVirement(globalVirement);
					virementVoCreationCroExeVir.setDateComptableAgence(dateComptable);
					virementVoCreationCroExeVir.setValueDateRecue(dateValeurAutreAgence);
					virementVoCreationCroExeVir.setMontant_commissionRecue(mntCommissionVirementAutreAgence);
					virementVoCreationCroExeVir.setTvaRecue(montant_tvaVirementAutreAgence + "");
					virementVoCreationCroExeVir.setValueDateComRecue(dateValeurCommission);
					virementVoCreationCroExeVir.setMontantGlobalByCB(mntVirementAutreAgence);
					virementVoCreationCroExeVir.setNbreGlobalByCB(nbreVirementAutreAgence);
					virementVoCreationCroExeVir.setEtatBenifVirementCB(Long.valueOf(1));// 0: meme agence; 1 :autre
																						// agence; 2: autre banque ;
																						// 3:SGMT
					virementVoCreationCroExeVir =
							(VirementVo) virementService.creationCROExecutionVirementMasse(virementVoCreationCroExeVir);

					operationMoyPayCompteDOAutreAgence = virementVoCreationCroExeVir.getOperationMoyPay();

				}
				if (mntVirementAutreBanque > 0) {

					VirementVo virementVoCreationCroExeVir = new VirementVo();
					virementVoCreationCroExeVir.setEtatTaxableClient(etatClientTaxable);
					virementVoCreationCroExeVir.setGlobalVirement(globalVirement);
					virementVoCreationCroExeVir.setDateComptableAgence(dateComptable);
					virementVoCreationCroExeVir.setValueDateRecue(dateValeurAutreBanque);
					virementVoCreationCroExeVir.setMontant_commissionRecue(mntCommissionVirementAutreBanque);
					virementVoCreationCroExeVir.setTvaRecue(montant_tvaVirementAutreBanque + "");
					virementVoCreationCroExeVir.setValueDateComRecue(dateValeurCommission);
					virementVoCreationCroExeVir.setMontantGlobalByCB(mntVirementAutreBanque);
					virementVoCreationCroExeVir.setNbreGlobalByCB(nbreVirementAutreBanque);
					virementVoCreationCroExeVir.setEtatBenifVirementCB(Long.valueOf(2));// 0: meme agence; 1 :autre
																						// agence; 2: autre banque ;
																						// 3:SGMT
					virementVoCreationCroExeVir =
							(VirementVo) virementService.creationCROExecutionVirementMasse(virementVoCreationCroExeVir);

					operationMoyPayCompteDOAutreBanque = virementVoCreationCroExeVir.getOperationMoyPay();

				}
				if (mntVirementAutreBanqueSGMT > 0) {

					VirementVo virementVoCreationCroExeVir = new VirementVo();
					virementVoCreationCroExeVir.setEtatTaxableClient(etatClientTaxable);
					virementVoCreationCroExeVir.setGlobalVirement(globalVirement);
					virementVoCreationCroExeVir.setDateComptableAgence(dateComptable);
					virementVoCreationCroExeVir.setValueDateRecue(dateValeurAutreBanqueSGMT);
					virementVoCreationCroExeVir.setMontant_commissionRecue(mntCommissionVirementAutreBanqueSGMT);
					virementVoCreationCroExeVir.setTvaRecue(montant_tvaVirementAutreBanqueSGMT + "");
					virementVoCreationCroExeVir.setValueDateComRecue(dateValeurCommission);
					virementVoCreationCroExeVir.setMontantGlobalByCB(mntVirementAutreBanqueSGMT);
					virementVoCreationCroExeVir.setNbreGlobalByCB(nbreVirementAutreBanqueSGMT);
					virementVoCreationCroExeVir.setEtatBenifVirementCB(Long.valueOf(3));// 0: meme agence; 1 :autre
																						// agence; 2: autre banque ;
																						// 3:SGMT
					virementVoCreationCroExeVir =
							(VirementVo) virementService.creationCROExecutionVirementMasse(virementVoCreationCroExeVir);

					operationMoyPayCompteDOAutreBanqueSGMT = virementVoCreationCroExeVir.getOperationMoyPay();
				}

				// VirementVo virementVoCreationCroExeVir = new VirementVo();
				// virementVoCreationCroExeVir.setEtatTaxableClient(etatClientTaxable);
				// virementVoCreationCroExeVir.setGlobalVirement(globalVirement);
				// virementVoCreationCroExeVir.setDateComptableAgence(dateComptable);
				// virementVoCreationCroExeVir.setValueDateRecue(dateValeur);
				// virementVoCreationCroExeVir.setMontant_commissionRecue(commissionTotal);
				// virementVoCreationCroExeVir.setTvaRecue(tvaTotal + "");
				// virementVoCreationCroExeVir.setValueDateComRecue(dateValeurCommission);
				//
				// virementVoCreationCroExeVir =
				// (VirementVo) virementService.creationCROExecutionVirementMasse(virementVoCreationCroExeVir);
				//
				// operationMoyPayCompteDO = virementVoCreationCroExeVir.getOperationMoyPay();

				// ********** Creation Cro 721/821/1203 *****************//

				/* * Recupuration liste Flux comptable */

				List<FluxComptVirement> fluxComptVirements = new ArrayList<FluxComptVirement>();
				GetListFluxComptableVirementByNumSeqGvirTrt getListFluxComptableVirementByNumSeqGvirTrt =
						new GetListFluxComptableVirementByNumSeqGvirTrt();
				VirementVo virementVoFlux = new VirementVo();
				virementVoFlux.setGlobalVirement(globalVirement);

				virementVoFlux = (VirementVo) getListFluxComptableVirementByNumSeqGvirTrt.exec(virementVoFlux);

				fluxComptVirements = virementVoFlux.getListFluxComptVirement();

				if (fluxComptVirements.size() > 0) {

					for (FluxComptVirement fluxComptVirement : fluxComptVirements) {

						// ************** Reference Inter siege *********//
						GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
						String codRefInter =
								generateReferenceInterSiege.getRISWithUpdate(globalVirement.getStructure()
										.getCodStrcStrc(), dateComptable);
						String codRefInterFichier = codRefInter.substring(0, 6) + lpadS(numLotAgence + "", "0", 3);
						if (fluxComptVirement.getCodStrcStrc().longValue() != new Long(900).longValue()) {

							// ***** Envoi Cro 721 *****//
							VirementVo virementVo3 = new VirementVo();
							Operation operation2 = new Operation();
							operation2.setCodOperOper(Constants.COD_OPER_ENVOI_VIREMENT);
							virementVo3.setOperationMoyPay(operationMoyPayCompteDOAutreAgence);
							virementVo3.setOperation(operation2);
							virementVo3.setProduit(produit);
							virementVo3.setDateComptableAgence(dateComptable);
							virementVo3.setGlobalVirement(globalVirement);
							virementVo3.setCodStrcRecp(fluxComptVirement.getCodStrcStrc());
							virementVo3.setMontantGlobalByStructure(fluxComptVirement.getMntVirStrc().longValue());
							virementVo3.setCodRefInter(codRefInter);
							virementVo3 = (VirementVo) virementService.createCroEnvoiVirement(virementVo3);

							// ***** Envoi Cro 821 *****//
							VirementVo virementVo4 = new VirementVo();
							Operation operation3 = new Operation();
							operation3.setCodOperOper(Constants.COD_OPER_RECEPTION_VIREMENT_AGENCE);
							virementVo4.setOperationMoyPay(operationMoyPayCompteDOAutreAgence);
							virementVo4.setOperation(operation3);
							virementVo4.setProduit(produit);
							virementVo4.setDateComptableAgence(dateComptable);
							virementVo4.setGlobalVirement(globalVirement);
							virementVo4.setCodStrcRecp(fluxComptVirement.getCodStrcStrc());
							virementVo4.setMontantGlobalByStructure(fluxComptVirement.getMntVirStrc().longValue());
							virementVo4.setCodRefInter(codRefInter);
							virementVo4 = (VirementVo) virementService.createCroReceptionVirementParAgence(virementVo4);

						} else if (fluxComptVirement.getCodStrcStrc().longValue() == new Long(900).longValue()) {
							if (fluxComptVirement.getTypeFluxVir().longValue() == 0) {
								// ***** Envoi Cro 721 *****//
								VirementVo virementVo3 = new VirementVo();
								Operation operation2 = new Operation();
								operation2.setCodOperOper(Constants.COD_OPER_ENVOI_VIREMENT);
								virementVo3.setOperationMoyPay(operationMoyPayCompteDOAutreBanque);
								virementVo3.setOperation(operation2);
								virementVo3.setProduit(produit);
								virementVo3.setDateComptableAgence(dateComptable);
								virementVo3.setGlobalVirement(globalVirement);
								virementVo3.setCodStrcRecp(fluxComptVirement.getCodStrcStrc());
								virementVo3.setMontantGlobalByStructure(fluxComptVirement.getMntVirStrc().longValue());
								// virementVo3.setCodRefInter(codRefInter);
								virementVo3.setCodRefInter(codRefInterFichier);
								virementVo3 = (VirementVo) virementService.createCroEnvoiVirement(virementVo3);

							} else if (fluxComptVirement.getTypeFluxVir().longValue() == 1) {

								// ***** Envoi Cro 721 *****//
								VirementVo virementVo3 = new VirementVo();
								Operation operation2 = new Operation();
								operation2.setCodOperOper(Constants.COD_OPER_ENVOI_VIREMENT);
								virementVo3.setOperationMoyPay(operationMoyPayCompteDOAutreBanqueSGMT);
								virementVo3.setOperation(operation2);
								virementVo3.setProduit(produit);
								virementVo3.setDateComptableAgence(dateComptable);
								virementVo3.setGlobalVirement(globalVirement);
								virementVo3.setCodStrcRecp(fluxComptVirement.getCodStrcStrc());
								virementVo3.setMontantGlobalByStructure(fluxComptVirement.getMntVirStrc().longValue());
								virementVo3.setCodRefInter(codRefInter);
								virementVo3 = (VirementVo) virementService.createCroEnvoiVirement(virementVo3);

								// ***** Envoi Cro 1203 *****//

								VirementVo virementVoSGMT = new VirementVo();
								Operation operationSGMT = new Operation();
								operationSGMT.setCodOperOper(Constants.COD_OPER_RECEPTION_VIREMENT_SGMT);
								virementVoSGMT.setOperationMoyPay(operationMoyPayCompteDOAutreBanqueSGMT);
								virementVoSGMT.setOperation(operationSGMT);
								virementVoSGMT.setProduit(produit);
								virementVoSGMT.setDateComptableAgence(dateComptable);
								virementVoSGMT.setGlobalVirement(globalVirement);
								virementVoSGMT.setCodStrcRecp(fluxComptVirement.getCodStrcStrc());
								virementVoSGMT.setMontantGlobalByStructure(fluxComptVirement.getMntVirStrc()
										.longValue());
								virementVoSGMT.setCodRefInter(codRefInter);

								virementVoSGMT =
										(VirementVo) virementService.creationCROReceptionVirementSGMT(virementVoSGMT);

								codRefcVir = codRefInter;

							}
						}
					}
				}

				// ********** Fin Creation Cro 721/821/1203 *****************//

				for (DetailVirement detailVirementObj : list_Details) {

					String codeAgenceCompteBenif = "";

					codeBanque = detailVirementObj.getRibBenDetv().substring(0, 2);

					// ***** Bénéficiaire BNA *****//
					if (codeBanque.equals("03")) {

						// *****Verifier Benif *****//
						VirementVo virementVoBenif = new VirementVo();

						virementVoBenif.setGlobalVirement(globalVirement);
						virementVoBenif.setDetailVirement(detailVirementObj);
						virementVoBenif.setDateComptableAgence(dateComptable);
						virementVoBenif.setStrRib("BENIF");
						virementVoBenif.setBoolValiderContratCptBENIF(false);

						virementVoBenif = (VirementVo) virementService.verifierValiditerRibBenif(virementVoBenif);
						String msgValidator = virementVoBenif.getMessageVerificationRib();
						logger.info("\n  /***** virementVo.isBoolValiderContratCptBENIF() ="
								+ virementVoBenif.isBoolValiderContratCptBENIF() + " ***/   \n");

						// / True : Valide ----- False : Invalide ---------------- ///
						// / virementVo.setBoolValiderContratCptBENIF(boolVerfierRibBenif);

						if (virementVoBenif.isBoolValiderContratCptBENIF() == false) {

							etatValiditeBenif = false;

							logger.info("\n  /***** RIB BENIF Non Valide  ***/   \n");
						} else {
							etatValiditeBenif = true;
							logger.info("\n  /***** RIB BENIF Valide  ***/   \n");
						}

						// **************** Virement même BNA/BNA même Agence *************//
						codeAgenceCompteBenif = detailVirementObj.getRibBenDetv().substring(5, 8);
						if (new Long(codeAgenceCompteBenif).longValue() == globalVirement.getStructure()
								.getCodStrcStrc().longValue()) {

							etatBenifMemeAgence = true;
						} else {
							etatBenifMemeAgence = false;

						}

						// **** RIB Bénéficiaire en Dinars Convertible et RIB DO est en Dinars Convertible ****//
						boolean boolRibBenfiEnDinarsConvertible = false;
						boolean boolRibDOEnDinarsConv = false;
						boolean verifierLiaisonCompte = false;
						String CodeProduitCpteBenif = detailVirementObj.getRibBenDetv().substring(8, 12);
						int i = 0;

						while (boolRibDOEnDinarsConv == false && i < Constants.listCompteEnDinarsConvertibles.length) {
							if (globalVirement.getContratCpt().getContratCptId().getCodPrdPrd().longValue() == Constants.listCompteEnDinarsConvertibles[i]
									.longValue()) {
								boolRibDOEnDinarsConv = true;
							}
							i++;
						}

						i = 0;
						while (boolRibBenfiEnDinarsConvertible == false
								&& i < Constants.listCompteEnDinarsConvertibles.length) {
							if (new Long(CodeProduitCpteBenif).longValue() == Constants.listCompteEnDinarsConvertibles[i]
									.longValue()) {
								boolRibBenfiEnDinarsConvertible = true;
							}
							i++;
						}
						if (boolRibBenfiEnDinarsConvertible == true && boolRibDOEnDinarsConv == true) {

							verifierLiaisonCompte = true;
						}

						// ********************************************************************//

						if (verifierLiaisonCompte == true) {
							// ***** Envoi Cro 1064 *****//
							VirementVo virementVo1064 = new VirementVo();
							Operation operation1064 = new Operation();

							operation1064.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT_COMPTE_DEVISES);
							virementVo1064.setOperation(operation1064);
							virementVo1064.setProduit(produit);
							virementVo1064.setGlobalVirement(globalVirement);
							virementVo1064.setDetailVirement(detailVirementObj);
							virementVo1064.setDateComptableAgence(dateComptable);
							virementVo1064.setBoolValiderContratCptBENIF(etatValiditeBenif);
							virementVo1064.setEtatBenifMemeAgence(etatBenifMemeAgence);
							virementVo1064 =
									(VirementVo) virementService
											.creationCROPositionVirementCompteDevises(virementVo1064);

						} else {
							// ***** Envoi Cro 822 *****//

							VirementVo virementVo2 = new VirementVo();
							Operation operation = new Operation();

							operation.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT);
							virementVo2.setOperation(operation);
							virementVo2.setProduit(produit);
							virementVo2.setGlobalVirement(globalVirement);
							virementVo2.setDetailVirement(detailVirementObj);
							virementVo2.setDateComptableAgence(dateComptable);
							virementVo2.setBoolValiderContratCptBENIF(etatValiditeBenif);
							virementVo2.setEtatBenifMemeAgence(etatBenifMemeAgence);
							virementVo2 = (VirementVo) virementService.createCroPositionVirement(virementVo2);
						}
						if (new Long(codeAgenceCompteBenif).longValue() == globalVirement.getStructure()
								.getCodStrcStrc().longValue()) {

							etatBenifMemeAgence = true;

							if (etatValiditeBenif == false) {

								// ***** Envoi Cro 823 *****//
								VirementVo virementVoRejet = new VirementVo();
								Operation operationRejet = new Operation();
								operationRejet.setCodOperOper(Constants.COD_OPER_REJET_VIREMENT);
								virementVoRejet.setOperation(operationRejet);
								virementVoRejet.setProduit(produit);
								virementVoRejet.setDetailVirement(detailVirementObj);
								virementVoRejet.setDateComptableAgence(dateComptable);
								virementVoRejet.setOperationMoyPay(operationMoyPayCompteDOMemeAgence);
								virementVoRejet.setEtatBenifMemeAgence(etatBenifMemeAgence);
								virementVoRejet = (VirementVo) virementService.createCroRejetVirement(virementVoRejet);

								// ***** Envoi Cro 948 *****//
								VirementVo virementVoReaf = new VirementVo();
								Operation operationReaf = new Operation();
								operationReaf.setCodOperOper(Constants.COD_OPER_REAFFECTATION_REJETS_VIREMENT);
								virementVoReaf.setOperation(operationReaf);
								virementVoReaf.setProduit(produit);
								virementVoReaf.setDetailVirement(detailVirementObj);
								virementVoReaf.setDateComptableAgence(dateComptable);
								virementVoReaf =
										(VirementVo) virementService
												.createCroReaffectationRejetsVirement(virementVoReaf);
								montant_Rejets += detailVirementObj.getMntDetvDetv();
								nbreRejets++;
								detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
								detailVirementObj.setMotifRejetDetv(msgValidator);
							} else {
								detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_EXECUTER);
							}

						} else if (new Long(codeAgenceCompteBenif).longValue() != globalVirement.getStructure()
								.getCodStrcStrc().longValue()) {

							etatBenifMemeAgence = false;

							if (etatValiditeBenif == false) {

								// ***** Envoi Cro 823 *****//
								VirementVo virementVoRejet = new VirementVo();
								Operation operationRejet = new Operation();
								operationRejet.setCodOperOper(Constants.COD_OPER_REJET_VIREMENT);
								virementVoRejet.setOperation(operationRejet);
								virementVoRejet.setProduit(produit);
								virementVoRejet.setDetailVirement(detailVirementObj);
								virementVoRejet.setDateComptableAgence(dateComptable);
								virementVoRejet.setOperationMoyPay(operationMoyPayCompteDOAutreAgence);
								virementVoRejet.setEtatBenifMemeAgence(etatBenifMemeAgence);
								virementVoRejet = (VirementVo) virementService.createCroRejetVirement(virementVoRejet);

								// ***** Envoi Cro 948 *****//
								VirementVo virementVoReaf = new VirementVo();
								Operation operationReaf = new Operation();
								operationReaf.setCodOperOper(Constants.COD_OPER_REAFFECTATION_REJETS_VIREMENT);
								virementVoReaf.setOperation(operationReaf);
								virementVoReaf.setProduit(produit);
								virementVoReaf.setDetailVirement(detailVirementObj);
								virementVoReaf.setDateComptableAgence(dateComptable);
								virementVoReaf =
										(VirementVo) virementService
												.createCroReaffectationRejetsVirement(virementVoReaf);

								// ***** Envoi Cro 824 *****//

								VirementVo virementVoEnvoiRejets = new VirementVo();
								Operation operationEnvoiRejets = new Operation();
								operationEnvoiRejets.setCodOperOper(Constants.COD_OPER_ENVOI_REJETS_VIREMENT);
								virementVoEnvoiRejets.setOperation(operationEnvoiRejets);
								virementVoEnvoiRejets.setProduit(produit);
								virementVoEnvoiRejets.setDetailVirement(detailVirementObj);
								virementVoEnvoiRejets.setDateComptableAgence(dateComptable);
								virementVoEnvoiRejets.setOperationMoyPay(operationMoyPayCompteDOAutreAgence);
								virementVoEnvoiRejets =
										(VirementVo) virementService
												.createCroEnvoiRejetsVirement(virementVoEnvoiRejets);

								// ***** Envoi Cro 947 *****//
								VirementVo virementVoRecptRejets = new VirementVo();
								Operation operationRecptRejets = new Operation();
								operationRecptRejets.setCodOperOper(Constants.COD_OPER_RECEPTION_REJETS_VIREMENT);
								virementVoRecptRejets.setOperation(operationRecptRejets);
								virementVoRecptRejets.setProduit(produit);
								virementVoRecptRejets.setDetailVirement(detailVirementObj);
								virementVoRecptRejets.setDateComptableAgence(dateComptable);
								virementVoRecptRejets.setOperationMoyPay(operationMoyPayCompteDOAutreAgence);
								virementVoRecptRejets =
										(VirementVo) virementService
												.creationCROReceptionRejetsVirementParAgence(virementVoRecptRejets);

								// *************************//

								montant_Rejets += detailVirementObj.getMntDetvDetv();
								nbreRejets++;
								detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
								detailVirementObj.setMotifRejetDetv(msgValidator);
							} else {
								detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_EXECUTER);
							}

						}

						// *********** Update Detail ***********//

						detailVirementObj.setDatExecDetv(dateComptable);
						detailVirementObj.setDatSysDetv(new Date());
						detailVirementObj.setTimeExecDetv(heureString);
						detailVirementObj.setCodFlagDetv(Constants.COD_FLAG_FICHIER_VIREMENT_NON_TRAITER);
						crudService.update(detailVirementObj);

					}

					// ***** Bénéficiaire non BNA *****//

					else {
						etatBenifMemeAgence = false;
						// **** Virement autre Banque SIBTEL ****//

						if (detailVirementObj.getMntDetvDetv().longValue() < Constants.MONTANT_VIREMENT_SGMT
								.longValue()) {

							// ***** Ecriture dans le fichier *****//

							file = new File(fileName);
							logger.info("file : " + file.getAbsolutePath());

							boolean exists = file.exists();

							if (!exists) {
								file.createNewFile();

							}

							numeroLigneADT++;
							VirementVo virementVoCreationFile = new VirementVo();

							virementVoCreationFile.setFile(file);
							virementVoCreationFile.setGlobalVirement(globalVirement);
							virementVoCreationFile.setDetailVirement(detailVirementObj);
							virementVoCreationFile.setDateComptableAgence(dateComptable);
							virementVoCreationFile.setMntVirementADT(mntVirementADT);
							virementVoCreationFile.setNbrVirementADT(nbrVirementADT);
							virementVoCreationFile.setNumeroLigneADT(numeroLigneADT);
							virementVoCreationFile.setNumeroLot(numLot);

							virementVoCreationFile =
									(VirementVo) virementService.creerFichierVirement(virementVoCreationFile);

							// **** update Detail ****//
							detailVirementObj.setCodFlagDetv(Constants.COD_FLAG_FICHIER_VIREMENT_TRAITER);
							detailVirementObj.setRefFichDetv(file.getName());

							// **** Recupuration du fichier ****//
							virementVo.getListeFile().add(file);
							virementVo.getListeFilesNames().add(file.getName());

						}
						// **** Virement autre Banque SGMT****//

						else {

							// ***** Insertion dans la table SGMT *****//

							// ***** Recupuration du Numero de Vir *** //

							VirementVo virementVoSGMT = new VirementVo();

							GetNumVirSGMTTrt getNumVirSGMTTrt = new GetNumVirSGMTTrt();

							virementVoSGMT = (VirementVo) getNumVirSGMTTrt.exec(virementVoSGMT);

							Long numVirSgmt = virementVoSGMT.getNumVirSgmt();

							logger.info("numVirSgmt : " + numVirSgmt);

							VirSgmt virSgmt = new VirSgmt();
							SimpleDateFormat formaterDateddMMyy = new SimpleDateFormat("ddMMyy");
							String codCodVir = formaterDateddMMyy.format(dateComptable) + numVirSgmt;
							virSgmt.setCodCodVir(codCodVir);
							virSgmt.setDatOperVir(formaterDate.parse(formaterDate.format(detailVirementObj
									.getDatEchDetv())));
							virSgmt.setDatVirVir(formaterDate.parse(formaterDate.format(dateComptable)));
							virSgmt.setCodDevDev(new Short(codeDevise));
							virSgmt.setNumVirVir(new Integer(detailVirementObj.getDetailVirementId().getNumSeqDetv()));
							virSgmt.setCodRefVir(globalVirement.getNumSeqGvir());
							virSgmt.setCodSensVir("1");
							PrimitiveVO primitiveVO = new PrimitiveVO();
							GetRibTrt getRibTrt = new GetRibTrt();
							primitiveVO = (PrimitiveVO) getRibTrt.exec(globalVirement.getContratCpt());
							String ribDO = primitiveVO.getVString();
							if (ribDO.length() == 20) {
								virSgmt.setNumRibdVir(new BigDecimal(ribDO));

							}
							virSgmt.setCodBdBank(new Short("3"));

							String nomPre_RsDO = globalVirement.getContratCpt().getNomIntiCcpt();
							nomPre_RsDO = UtilCtr.corrigerChaineCaractere(nomPre_RsDO);

							if (nomPre_RsDO.length() > 30) {
								virSgmt.setNomNomdVir(nomPre_RsDO.substring(0, 30));
							} else {
								virSgmt.setNomNomdVir(nomPre_RsDO);
							}
							virSgmt.setNumRibbVir(new BigDecimal(detailVirementObj.getRibBenDetv()));
							String codeBanqueBenif = detailVirementObj.getRibBenDetv().substring(0, 2);
							String codeAgenceBenif = detailVirementObj.getRibBenDetv().substring(2, 5);

							if (new Short(codeBanqueBenif).shortValue() == 0) // ****** Bank Centrale
							{
								if (new Short(codeAgenceBenif).shortValue() == 999) {

									virSgmt.setCodOperOpgm("VBSC");

								} else if (detailVirementObj.getRibBenDetv().equals("00038000401001700004")) {

									virSgmt.setCodOperOpgm("VBO");

								} else if (detailVirementObj.getRibBenDetv().substring(0, 12).equals("000380004012")) {

									virSgmt.setCodOperOpgm("VBB");

								} else {

									virSgmt.setCodOperOpgm("VBTC");
								}
							} else if (new Short(codeBanqueBenif).shortValue() == 17) {

								virSgmt.setCodOperOpgm("VBCP");

							} else {
								virSgmt.setCodOperOpgm("VBP");
							}

							virSgmt.setCodBbBank(new Short(codeBanqueBenif));

							String nomPre_RsBenif = detailVirementObj.getNomBenDetv();
							nomPre_RsBenif = UtilCtr.corrigerChaineCaractere(nomPre_RsBenif);
							if (nomPre_RsBenif.length() > 30) {
								virSgmt.setNomNombVir(nomPre_RsBenif.substring(0, 30));
							} else {
								virSgmt.setNomNombVir(nomPre_RsBenif);
							}

							virSgmt.setMntVirVir(detailVirementObj.getMntDetvDetv());

							String motifOperation = detailVirementObj.getMotiDetvDetv();
							motifOperation = UtilCtr.corrigerChaineCaractere(motifOperation);

							if (motifOperation.length() > 45) {
								virSgmt.setLibMotifVir(motifOperation.substring(0, 45));
							} else {
								virSgmt.setLibMotifVir(motifOperation);
							}

							virSgmt.setDatEnvVir(formaterDate.parse("11/11/1111"));
							virSgmt.setCodAgAg(globalVirement.getStructure().getCodStrcStrc().shortValue());

							virSgmt.setCodSitdVir(new Short("0"));
							virSgmt.setCodTypcVir(new Short("1"));
							virSgmt.setCodNatcVir("0");
							virSgmt.setCodVldVir(new Short("2")); // A verifier
							virSgmt.setCodUserUser("9999");
							virSgmt.setCodResVir(new Short("0"));
							virSgmt.setCodRefcVir(codRefcVir);
							// virSgmt.setCodUsrvUser("9999");
							virSgmt.setDatDatJrn(formaterDate.parse(formaterDate.format(dateComptable)));

							crudService.create(virSgmt);

							detailVirementObj.setRefFichDetv("SGMT");
							detailVirementObj.setCodFlagDetv(Constants.COD_FLAG_FICHIER_VIREMENT_TRAITER);

						}

						// *********** Update Detail ***********//
						detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_EXECUTER);
						detailVirementObj.setDatExecDetv(dateComptable);
						detailVirementObj.setDatSysDetv(new Date());
						detailVirementObj.setTimeExecDetv(heureString);
						crudService.update(detailVirementObj);

					}

				}

			}

			virementVo.setStadeEnregistrement(true);
			// / ------------------------------------------------------------------ ///

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans ExecutionDoVirMasseTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("ExecutionDoVirMasseTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau ExecutionDoVirMasseTrt : ", e);
			virementVo.setMessageValidation("Probléme dans ExecutionDoVirMasseTrt");
			if (file != null && file.exists()) {

				file.delete();

			}
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

	public void genCroText(ValueObject vo) {
	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public static String lpadS(String valueToPad, String filler, int size) {
		StringBuilder builder = new StringBuilder();

		while (builder.length() + valueToPad.length() < size) {
			builder.append(filler);
		}
		builder.append(valueToPad);
		return builder.toString();
	}

}