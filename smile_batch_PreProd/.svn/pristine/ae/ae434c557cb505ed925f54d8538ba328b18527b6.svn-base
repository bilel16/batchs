package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Produit;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetContratCptByIdTrt;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.fwk.util.DateHandler;

public class ExecutionDoVirDeviseTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
	IExpression expression = searchEngine.createExpression();
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	String codeBanque = "";
	String codeAgence = "";
	SimpleDateFormat formaterDate2 = new SimpleDateFormat("ddMMyyyy");
	boolean etatClientTaxable = false;
	Produit produit = new Produit();
	Date dateComptable = new Date();

	public ExecutionDoVirDeviseTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;

		GlobalVirement globalVirement = (GlobalVirement) virementVo.getGlobalVirement();

		OperationMoyPay operationMoyPayCompteDOMemeAgence = new OperationMoyPay();
		dateComptable = virementVo.getDateComptableAgence();
		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		VirementService virementService = (VirementService) context.getBean("iVirementService");
		Long[] etatsVirements =
				{ Constants.COD_ETAT_VIREMENT_ATTENTE, Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN,
						Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF };

		String heureString = formaterHeure.format(new Date());

		try {

			this.setCroFlag(false);

			etatClientTaxable = virementVo.isEtatTaxableClient();
			produit.setCodPrdPrd(Constants.COD_PRODUIT_VIREMENT_PONCTUEL);
			long nbreVirementMemeAgence = 0;
			long mntCommissionVirementMemeAgence = 0;
			long montant_tvaVirementMemeAgence = 0;
			long mntVirementMemeAgence = 0;
			String dateValeurMemeAgence = ""; // Date Valeur de l'operation
			String dateValeurCommission = ""; // Date Valeur de la commission

			// *********** Find Liste details for global virement *******************//

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			criteria.add(expression.eq("detailVirementId.numSeqGvir", globalVirement.getNumSeqGvir()));
			criteria.add(expression.in("etaDetvDetv", etatsVirements));

			List<DetailVirement> list_Details =
					new ArrayList<DetailVirement>(searchEngine.find(DetailVirement.class, criteria));

			// *********** Parcours liste details one by one *******************//

			if (list_Details != null && list_Details.size() > 0) {

				// /************** Determiner le nbre et le montant d'un virement ADT ********//

				for (DetailVirement detailVirement : list_Details) {
					codeBanque = detailVirement.getRibBenDetv().substring(0, 2);
					if (codeBanque.equals("03")) {

						if (new Long(detailVirement.getRibBenDetv().substring(5, 8)).longValue() == globalVirement
								.getStructure().getCodStrcStrc().longValue()) {

							ContratCpt contratCptBenif = new ContratCpt();
							ContratCptId contratCptId = new ContratCptId();
							contratCptId.setCodStrcStrc(new Long(detailVirement.getRibBenDetv().substring(5, 8)));
							contratCptId.setCodPrdPrd(new Long(detailVirement.getRibBenDetv().substring(8, 12)));
							contratCptId.setNumCcptCcpt(new Long(detailVirement.getRibBenDetv().substring(12, 18)));
							contratCptBenif.setContratCptId(contratCptId);

							GetContratCptByIdTrt contratCptByIdTrt = new GetContratCptByIdTrt();
							contratCptBenif = (ContratCpt) contratCptByIdTrt.exec(contratCptBenif);
							if (contratCptBenif != null && contratCptBenif.getClient() != null) {
								if (contratCptBenif.getClient().getNumSeqPers().longValue() == globalVirement
										.getContratCpt().getClient().getNumSeqPers().longValue()) {

									mntVirementMemeAgence += detailVirement.getMntDetvDetv();

								} else {
									mntVirementMemeAgence += detailVirement.getMntDetvDetv();
									nbreVirementMemeAgence++;
								}
							}

						}

					}
				}

				// / ------------- Traitement Condition de banque -------- ///

				VirementVo virementVoCBMemeAgence = new VirementVo();
				ArrayList<String> palierCharMemeAgence = new ArrayList<String>();
				Operation operationCB = new Operation();
				Operation oper = new Operation();

				operationCB.setCodOperOper(Constants.COD_OPER_VIREMENT_EMIS_DEVISE_MEME_AG);
				oper.setCodOperOper(Constants.COD_OPER_VIREMENT_EMIS_DEVISE_MEME_AG);

				if (nbreVirementMemeAgence > 0) {

					palierCharMemeAgence.add("41");

					virementVoCBMemeAgence.setContratCpt(globalVirement.getContratCpt());
					virementVoCBMemeAgence.setOperation(operationCB);
					virementVoCBMemeAgence.setMontant_virement(new Long("10000"));
					virementVoCBMemeAgence.setGlobalVirement(globalVirement);
					virementVoCBMemeAgence.setDateComptableAgence(dateComptable);
					virementVoCBMemeAgence.setListePalierCaractere(palierCharMemeAgence);
					virementVoCBMemeAgence = getConditionDeBanque(virementVoCBMemeAgence);
					if (virementVoCBMemeAgence.getCommissionRecue().isEmpty() == false) {
						mntCommissionVirementMemeAgence =
								new Long(StrHandler.strToMnt(virementVoCBMemeAgence.getCommissionRecue()));
					}
					if (virementVoCBMemeAgence.getTvaRecue().isEmpty() == false) {
						montant_tvaVirementMemeAgence =
								new Long(StrHandler.strToMnt(virementVoCBMemeAgence.getTvaRecue()));
					}
					dateValeurMemeAgence = virementVoCBMemeAgence.getValueDateRecue();
					dateValeurCommission = virementVoCBMemeAgence.getValueDateComRecue();
				}

				if (mntVirementMemeAgence > 0) {
					// /********** Creation CRO 2086 // Operation Moyen Payement // Update Compte Client ******//

					VirementVo virementVoCreationCroExeVir = new VirementVo();
					virementVoCreationCroExeVir.setEtatTaxableClient(etatClientTaxable);
					virementVoCreationCroExeVir.setGlobalVirement(globalVirement);
					virementVoCreationCroExeVir.setDateComptableAgence(dateComptable);
					virementVoCreationCroExeVir.setValueDateRecue(DateHandler.dateToStr(dateComptable));
					virementVoCreationCroExeVir.setMontant_commissionRecue(mntCommissionVirementMemeAgence);
					virementVoCreationCroExeVir.setTvaRecue(montant_tvaVirementMemeAgence + "");
					virementVoCreationCroExeVir.setValueDateComRecue(DateHandler.dateToStr(dateComptable));
					virementVoCreationCroExeVir.setNbreGlobalByCB(Long.valueOf(nbreVirementMemeAgence));
					virementVoCreationCroExeVir.setEtatBenifVirementCB(Long.valueOf(0));// 0: meme agence; 1 :autre
					// agence; 2: autre banque ;
					// 3:SGMT

					CreationCROVirementEmisDeviseTrt creationCROVirementEmisDeviseTrt =
							new CreationCROVirementEmisDeviseTrt();

					virementVoCreationCroExeVir =
							(VirementVo) creationCROVirementEmisDeviseTrt.exec(virementVoCreationCroExeVir);

					operationMoyPayCompteDOMemeAgence = virementVoCreationCroExeVir.getOperationMoyPay();

					// /********** Creation CRO 2087 // Operation Moyen Payement // Update Compte Client ******//

					// ************ Execution des Virement one by one **********//

					for (DetailVirement detailVirementObj : list_Details) {
						boolean boolVerfierRibBenif = false;
						codeBanque = detailVirementObj.getRibBenDetv().substring(0, 2);
						Produit produit = new Produit();
						produit.setCodPrdPrd(Constants.COD_PRODUIT_VIREMENT_PONCTUEL);

						// ***** Bénéficiaire BNA *****//

						String RibBenif = "";

						RibBenif = detailVirementObj.getRibBenDetv();

						VirementVo objVirementVoContratCptBenif = new VirementVo();
						ContratCpt contratCptObj = new ContratCpt();
						ContratCptId contratCptIdObj = new ContratCptId();

						if (RibBenif.length() == 20) {

							contratCptIdObj.setCodStrcStrc(new Long(RibBenif.substring(5, 8)));
							contratCptIdObj.setCodPrdPrd(new Long(RibBenif.substring(8, 12)));
							contratCptIdObj.setNumCcptCcpt(new Long(RibBenif.substring(12, 18)));
							contratCptObj.setContratCptId(contratCptIdObj);

							objVirementVoContratCptBenif.setContratCpt(contratCptObj);
							objVirementVoContratCptBenif.setGlobalVirement(globalVirement);
							objVirementVoContratCptBenif.setDetailVirement(detailVirementObj);
							objVirementVoContratCptBenif.setDateComptableAgence(dateComptable);
							objVirementVoContratCptBenif.setStrRib("BENIF");

							// virementVo.getStrRib().equals("BENIF")
							// / false : Contrat Invalde ; True Contrat valide

							VerfierContratCptTrt verfierContratCptTrt = new VerfierContratCptTrt();
							objVirementVoContratCptBenif =
									(VirementVo) verfierContratCptTrt.exec(objVirementVoContratCptBenif);
							boolVerfierRibBenif = objVirementVoContratCptBenif.isBoolValiderContratCpt();

							if (boolVerfierRibBenif == false) {
								// (" RIB DO NON VALIDE ");

								objVirementVoContratCptBenif =
										(VirementVo) virementService
												.rejeterDetailVirement(objVirementVoContratCptBenif);
								boolVerfierRibBenif = false;

							}

						}

						if (boolVerfierRibBenif == true) {

							// ***** Envoi Cro 2087 *****//

							VirementVo virementVo2087 = new VirementVo();
							Operation operation = new Operation();

							operation.setCodOperOper(Constants.COD_OPER_VIREMENT_RECU_DEVISE_MEME_AG);
							virementVo2087.setOperation(operation);
							virementVo2087.setProduit(produit);
							virementVo2087.setGlobalVirement(globalVirement);
							virementVo2087.setDetailVirement(detailVirementObj);
							virementVo2087.setDateComptableAgence(dateComptable);
							virementVo2087.setBoolValiderContratCptBENIF(boolVerfierRibBenif);
							CreationCROVirementRecuDeviseTrt creationCROVirementRecuDeviseTrt =
									new CreationCROVirementRecuDeviseTrt();
							virementVo2087 = (VirementVo) creationCROVirementRecuDeviseTrt.exec(virementVo2087);

							// *********** Update Detail ***********//
							detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_EXECUTER);
							detailVirementObj.setDatExecDetv(dateComptable);
							detailVirementObj.setDatSysDetv(new Date());
							detailVirementObj.setTimeExecDetv(heureString);
							crudService.update(detailVirementObj);
						}
					}

				}
			}

			// / ------------------------------------------------------------------ ///
			virementVo.setStadeEnregistrement(true);

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans ExecutionDoVirDeviseTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("ExecutionDoVirDeviseTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau ExecutionDoVirDeviseTrt : ", e);

			virementVo.setStadeEnregistrement(false);
			throw new RuntimeException(e);

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
				traitementConditionBanque.setPalierChar(new ArrayList(listePalierCaractere));
			}
			traitementConditionBanque.setCodOperOper(operation.getCodOperOper().toString());
			traitementConditionBanque.setCodPrdPrd(contratCpt.getContratCptId().getCodPrdPrd() + "");
			traitementConditionBanque.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(contratCpt.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt().toString());
			traitementConditionBanque.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece()
					.getCodTpceTpce().toString());
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
			virementVoCB.setOperation(operation);
			virementVoCB.setValueDateComRecue(valueDateComRecue);

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