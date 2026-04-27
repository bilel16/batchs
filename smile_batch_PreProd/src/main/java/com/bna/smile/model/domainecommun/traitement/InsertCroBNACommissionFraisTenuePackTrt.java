package com.bna.smile.model.domainecommun.traitement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.NomencElemtCondition;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceFraisPack;
import com.bna.commun.model.TypePiece;
import com.bna.commun.service.CommunService;
import com.bna.commun.service.ICrudService;
import com.bna.commun.traitements.InsertionOperationMoyPaySansCROTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmilePlacementException;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.ContratCptSold;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.OperationCompteDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.traitement.AbstractValidator;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertCroBNACommissionFraisTenuePackTrt extends Traitement {

	OperationMoyPay operationMoyPay = new OperationMoyPay();
	boolean etatBenifTaxable = false;
	long montant_commissionRecue = 0;
	long montant_tva = 0;
	String dateValueRecu = "";
	String dateValueComRecu = "";
	String mois;
	String annee;
	long numMois = 0;
	long numTrimestre = 0;
	Date finMoisPrecedent;
	boolean etatExecutionTrimestre = false;

	/****************************/
	public InsertCroBNACommissionFraisTenuePackTrt() {
	}

	/**
	 * methode qui insère dans la table des CRO,les frais Abonnement pack ...
	 * 
	 * @param vo
	 *            : VirementVo
	 * @return : VirementVo
	 * @autor : SAYEB Hichem
	 * @date : 30/05/2022
	 */

	public IValueObject perform(IValueObject vo) throws SmilePlacementException {
		Context context = ContextHandler.getContext();
		VirementVo virementVo = (VirementVo) vo;
		String[] nomMois = { "", "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre",
				"Octobre", "Novembre", "Décembre" };
		try {

			logger.info("debut de traitement");
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat formaterMOIS = new SimpleDateFormat("MM");
			SimpleDateFormat formaterANNEE = new SimpleDateFormat("yyyy");
			Date dateComptable = new Date();

			dateComptable = virementVo.getDateComptableAgence();
			ContratCpt contratCpt = new ContratCpt();
			etatExecutionTrimestre = false;
			Operation oper = new Operation();
			oper.setCodOperOper(Constants.COD_OPER_FRAIS_ABONNEMENT_PACK);
			OperationCompteDAO operationCompteDAO = (OperationCompteDAO) context.getBean("operationCompteDAO");

			if (virementVo.getPeriodicite() != null && virementVo.getPeriodicite().equals("M")) {

				Calendar c = Calendar.getInstance();

				// on se place à la date utilisée comme base de calcul
				c.setTime(dateComptable);

				// on se place au premier jour du mois en cours
				c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
				Date debutMois = c.getTime();
				// premier jour du mois en cours moins un jour = dernier jour du mois précédent
				c.add(Calendar.DAY_OF_MONTH, -1);
				finMoisPrecedent = c.getTime();
				annee = formaterANNEE.format(finMoisPrecedent);

				logger.info(
						"Date Comptable : " + dateComptable + " ----> Date fin mois précedent " + finMoisPrecedent);

				mois = nomMois[Integer.valueOf(formaterMOIS.format(finMoisPrecedent))];
				numMois = Long.valueOf(formaterMOIS.format(finMoisPrecedent));
				annee = formaterANNEE.format(finMoisPrecedent);
			} else if (virementVo.getPeriodicite() != null && virementVo.getPeriodicite().equals("T")) {

				int moisCourant = Integer.valueOf(formaterMOIS.format(dateComptable));
				Calendar c = Calendar.getInstance();

				// on se place à la date utilisée comme base de calcul
				c.setTime(dateComptable);

				// on se place au premier jour du mois en cours
				c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
				Date debutMois = c.getTime();
				// premier jour du mois en cours moins un jour = dernier jour du mois précédent
				c.add(Calendar.DAY_OF_MONTH, -1);
				finMoisPrecedent = finMoisPrecedent = getLastDayOfQuarter(c.getTime());
				annee = formaterANNEE.format(finMoisPrecedent);
				switch (moisCourant) {
				case 1:
					numTrimestre = 4;
					numMois = 12;
					etatExecutionTrimestre = true;
					break;
				case 4:
					numTrimestre = 1;
					numMois = 3;
					etatExecutionTrimestre = true;
					break;
				case 7:
					numTrimestre = 2;
					numMois = 6;
					etatExecutionTrimestre = true;
					break;
				case 10:
					numTrimestre = 3;
					numMois = 9;
					etatExecutionTrimestre = true;
					break;
				default:

					etatExecutionTrimestre = false;
					break;
				}

			}
			// *******************Contrat_cpt *****************//
			ICrudService crudService = (ICrudService) context.getBean("CURService");
			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");

			contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, virementVo.getContratCpt().getContratCptId());

			if (contratCpt != null && contratCpt.getClient() != null && contratCpt.getClient().getNumSeqPers() != null
					&& contratCpt.getCodEtatCcpt().equals("V")) {

				Long codeProduitCpt = contratCpt.getContratCptId().getCodPrdPrd();

				/************** Verif existance perception déja faite pour le même mois et annee ********/

				boolean trouve = operationCompteDAO.verifierExistancePerceptionByCritere(contratCpt,
						virementVo.getProduit().getCodPrdPrd(), numMois, Long.valueOf(annee));

				if (trouve == false) {

					// **** Compte en Dinars Convertible et RIB DO est en Dinars Convertible ****//
					boolean boolCpteEnDinarsConvertible = false;
					boolean boolCpteEndevises = false;

					int i = 0;

					while (boolCpteEnDinarsConvertible == false
							&& i < Constants.listCompteEnDinarsConvertibles.length) {
						if (codeProduitCpt.longValue() == Constants.listCompteEnDinarsConvertibles[i].longValue()) {
							boolCpteEnDinarsConvertible = true;
						}
						i++;
					}

					i = 0;
					while (boolCpteEndevises == false && i < Constants.listCompteEnDevises.length) {
						if (new Long(codeProduitCpt).longValue() == Constants.listCompteEnDevises[i].longValue()) {
							boolCpteEndevises = true;
						}
						i++;
					}

					if (codeProduitCpt.equals(Constants.COD_PRD_PRD_EPS_CARTE) && etatExecutionTrimestre == true
							|| codeProduitCpt.equals(Constants.COD_PRD_PRD_EPS_CARTE) == false) {
						// *********Caracteristique Taxable du client ********//

						ExonerationTvaDAO exonerationTvaDAO = new ExonerationTvaDAO();
						ParamRechercheOpposition param = new ParamRechercheOpposition();
						param.setTypPceDemd(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce());
						param.setNumPceDemd(contratCpt.getClient().getPersonne().getNumPcePers());
						param.setDateDebutConsult(dateComptable);

						PrimitiveVO res = (PrimitiveVO) exonerationTvaDAO.isClientExonereTVA(param);

						if (res.isVBool() == true) {
							etatBenifTaxable = false;
						} else {
							etatBenifTaxable = true;
						}

						VirementVo virementVoCB =
								getConditionDeBanque(contratCpt, virementVo.getProduit().getCodPrdPrd(), dateComptable,
										contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce(),
										contratCpt.getClient().getPersonne().getNumPcePers());
						montant_commissionRecue = new Long(StrHandler.strToMnt(virementVoCB.getCommissionRecue()));
						montant_tva = new Long(StrHandler.strToMnt(virementVoCB.getTvaRecue()));
						dateValueRecu = virementVoCB.getValueDateRecue();
						dateValueComRecu = virementVoCB.getValueDateComRecue();

						if (etatBenifTaxable == false) {
							montant_tva = 0L;

						}

						// *********** AJOUTER OPERATION-MOY-PAY *****************//

						if (boolCpteEndevises == true) {

							// **************** Trace ***********************/

							TraceFraisPack traceFraisPack = new TraceFraisPack();
							traceFraisPack.setContratCpt(contratCpt);
							traceFraisPack.setOperation(oper);
							Calendar cal = Calendar.getInstance();
							traceFraisPack.setDatTimeTrcPack(cal.getTime());
							traceFraisPack.setCodPrdPack(virementVo.getProduit().getCodPrdPrd());
							Long codNatPack = operationCompteDAO.getNaturePack(virementVo.getProduit().getCodPrdPrd());
							traceFraisPack.setCodNatPack(codNatPack);
							traceFraisPack.setTypFactPack(virementVo.getPeriodicite());
							traceFraisPack.setMntComTrcPack(montant_commissionRecue);
							traceFraisPack.setMntTvaTrcPack(montant_tva);
							traceFraisPack.setMoisTrcPack(numMois);
							traceFraisPack.setAnneeTrcPack(Long.valueOf(annee));
							traceFraisPack.setEtatTrcPack("R");
							traceFraisPack.setDescTrcPack("Compte en Devise ");

							crudService.create(traceFraisPack);
							this.setCroFlag(false);
							virementVo.setEtatEnregistrement(true);
							virementVo.setTraceFraisPack(traceFraisPack);

						} else if (montant_commissionRecue != 0l) {

							if ((codeProduitCpt.equals(Constants.COD_PRD_PRD_EPS_CARTE)
									|| boolCpteEnDinarsConvertible == true) && contratCpt.getMontSoldCcpt() <= 0
									&& contratCpt.getMontSoldCcpt() < (montant_commissionRecue + montant_tva)) {

								// **************** Trace ***********************/

								TraceFraisPack traceFraisPack = new TraceFraisPack();
								traceFraisPack.setContratCpt(contratCpt);
								traceFraisPack.setOperation(oper);
								Calendar cal = Calendar.getInstance();
								traceFraisPack.setDatTimeTrcPack(cal.getTime());
								traceFraisPack.setCodPrdPack(virementVo.getProduit().getCodPrdPrd());
								Long codNatPack =
										operationCompteDAO.getNaturePack(virementVo.getProduit().getCodPrdPrd());
								traceFraisPack.setCodNatPack(codNatPack);
								traceFraisPack.setTypFactPack(virementVo.getPeriodicite());
								traceFraisPack.setMntComTrcPack(montant_commissionRecue);
								traceFraisPack.setMntTvaTrcPack(montant_tva);
								traceFraisPack.setMoisTrcPack(numMois);
								traceFraisPack.setAnneeTrcPack(Long.valueOf(annee));
								traceFraisPack.setEtatTrcPack("R");
								traceFraisPack.setDescTrcPack(
										"Manque provision==>Solde cpte = " + contratCpt.getMontSoldCcpt());

								crudService.create(traceFraisPack);
								this.setCroFlag(false);
								virementVo.setEtatEnregistrement(true);
								virementVo.setTraceFraisPack(traceFraisPack);

							} else {
								if (codeProduitCpt.equals(Constants.COD_PRD_PRD_EPS_CARTE)
										|| boolCpteEnDinarsConvertible == true) {

									if (contratCpt.getMontSoldCcpt() > 0
											&& contratCpt.getMontSoldCcpt() < (montant_commissionRecue + montant_tva)) {

										Long COMMISSIONTTC = contratCpt.getMontSoldCcpt();
										Long COMMISSION = Math.round(COMMISSIONTTC / 1.19);
										Long TVA = COMMISSIONTTC - COMMISSION;

										montant_commissionRecue = COMMISSION;
										montant_tva = TVA;
									}
								}
								logger.info("contratCpt.getContratCptId() :"
										+ contratCpt.getContratCptId().getCompteClient());
								// / ------------------Operation Moyen de Paiement ---------------------------- ///

								// 00. setting obj operationMoyPay
								operationMoyPay.setLibObjOpOmp("SMILE.FRAIS COMMISSION PACK");

								// 01. setting personnel initiateur et valideur
								Personnel personnelInit = new Personnel();
								personnelInit.setNumMatrUser("9999");
								operationMoyPay.setPersonnelInitiateur(personnelInit);
								operationMoyPay.setPersonnelValideur(personnelInit);

								// 02. setting structure initiatrice
								Structure structureInit = new Structure();
								structureInit.setCodStrcStrc(Long.valueOf(virementVo.getStructure().getCodStrcStrc()));
								operationMoyPay.setStructureInitiatrice(structureInit);

								// 03. setting structure receptrice
								Structure structureRecep = new Structure();
								structureRecep.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc());
								operationMoyPay.setStructureReceptrice(structureRecep);

								// 05. getting montant a retirer

								// 07. setting devise et montant
								Devise devise = new Devise();

								// 07.2 Virement en dinar
								devise.setCodDevDev(Constants.COD_DEV_DINAR);
								operationMoyPay.setMontDinOmp(0L);
								operationMoyPay.setMontApreOmp(
										contratCpt.getMontSoldCcpt() - (montant_commissionRecue + montant_tva));

								operationMoyPay.setMontSoldCcpt(contratCpt.getMontSoldCcpt());
								operationMoyPay.setDevise(devise);

								// 08. setting contrat compte
								operationMoyPay.setContratCpt(contratCpt);

								// 09. setting type demandeur (Titulaire, CoTitulaire, Mandataire)

								operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

								// 10.1 insertion du Donneur D'ordre

								TypePiece typePieceDemandeur = new TypePiece();
								typePieceDemandeur.setCodTpceTpce(Long
										.valueOf(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce()));
								operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
								operationMoyPay.setNumPcedOmp(contratCpt.getClient().getPersonne().getNumPcePers());
								operationMoyPay.setNomNomdOmp(contratCpt.getClient().getPersonne().getNomNomPers());
								operationMoyPay.setNomPrndOmp(contratCpt.getClient().getPersonne().getNomPrnPers());

								// 11 Preparing data of Operation and tache

								Tache tache = new Tache();
								tache.setOperation(oper);
								TacheId tacheId = new TacheId();
								tacheId.setCodOperOper(Constants.COD_OPER_FRAIS_ABONNEMENT_PACK);
								tacheId.setCodTachTach(1L);
								tache.setTacheId(tacheId);
								operationMoyPay.setTache(tache);

								// 12. setting date operation moyen paiement
								operationMoyPay.setDatOperOmp(dateComptable);

								// 13. setting date valeur moyen paiement
								Date dateValOmp = null;
								if (dateValueComRecu != null && dateValueComRecu.length() > 0) {
									dateValOmp = DateHandler.strToDate(dateValueComRecu);

								} else {
									dateValOmp = dateComptable;
								}
								operationMoyPay.setDatValOmp(dateValOmp);

								// 14.1 setting date system moyen paiement
								Date dateSysOmp = new Date();
								operationMoyPay.setDatSystOmp(dateSysOmp);

								// 14.2 setting date valeur Commission moyen paiement

								operationMoyPay.setDateValeurCommission(dateValOmp);

								// 15. setting sens operation
								operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);

								// 16. setting montant tva
								operationMoyPay.setMontTvaOmp(montant_tva);

								// 17. Insertion dans la table DETAIL_OPERATION_MOY_PAIEMENT

								Set<DetailOperMoyPaiement> setDetOpm = new HashSet<DetailOperMoyPaiement>();
								NomencElemtCondition nomencElemtCondition = new NomencElemtCondition();
								DetailOperMoyPaiement detailOmpCommission = new DetailOperMoyPaiement();

								if (montant_commissionRecue != 0) {
									if (virementVoCB.getTraitementConditionBanque() != null && virementVoCB
											.getTraitementConditionBanque().getCodCondCommission() != null) {
										nomencElemtCondition.setCodNecdNecd(
												virementVoCB.getTraitementConditionBanque().getCodCondCommission()
														+ "");
									}
									detailOmpCommission.setNomencElemtCondition(nomencElemtCondition);
									detailOmpCommission.setCodTypDomp(Constants.COD_TYPE_COMMISSION);
									detailOmpCommission.setMontValDomp(Long.valueOf(montant_commissionRecue));
									detailOmpCommission.setDatValDomp(dateValOmp);
									detailOmpCommission.setOperationMoyPay(operationMoyPay);
									setDetOpm.add(detailOmpCommission);

									operationMoyPay.setDetailOperMoyPaiements(setDetOpm);

								}

								// 19. Insertion code ref client

								if (codeProduitCpt.equals(Constants.COD_PRD_PRD_EPS_CARTE)) {
									annee = formaterANNEE.format(finMoisPrecedent);
									operationMoyPay.setCodRefcOmp("TRIMESTRE " + numTrimestre);
									operationMoyPay.setCodRefbOmp("TRIMESTRE " + numTrimestre);
									operationMoyPay.setLibMotfOmp(
											"FRAIS ABONNEMENT PACK TRIMESTRE " + numTrimestre + " " + annee);

								} else {
									mois = nomMois[Integer.valueOf(formaterMOIS.format(finMoisPrecedent))];
									numMois = Long.valueOf(formaterMOIS.format(finMoisPrecedent));
									annee = formaterANNEE.format(finMoisPrecedent);
									operationMoyPay.setCodRefcOmp("Mois " + mois);
									operationMoyPay.setCodRefbOmp("Mois " + mois);

									operationMoyPay.setLibMotfOmp("FRAIS ABONNEMENT PACK MOIS " + mois + " " + annee);
								}
								operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);

								// 02. insertion operation moyen paiement

								InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
										new InsertionOperationMoyPaySansCROTrt();
								operationMoyPay =
										(OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPay);
								virementVo.setOperationMoyPay(operationMoyPay);
								logger.info("operationMoyPay :" + operationMoyPay);

								// *************** Update Compte ***********//

								long montantAjouterAuSolde = montant_commissionRecue + montant_tva;

								CommunService communService = (CommunService) context.getBean("communService");
								ContratCptSold contratCptSold = new ContratCptSold();
								contratCptSold.setContratCpt(contratCpt);
								contratCptSold.setSens(Constants.COD_SENS_DB);
								contratCptSold.setSolde(montantAjouterAuSolde);
								contratCpt = (ContratCpt) communService.UpdateSoldOperationMoyPay(contratCptSold);

								// **************** Trace ***********************/

								TraceFraisPack traceFraisPack = new TraceFraisPack();
								traceFraisPack.setContratCpt(contratCpt);
								traceFraisPack.setOperation(oper);
								traceFraisPack.setOperationMoyPay(operationMoyPay);
								Calendar cal = Calendar.getInstance();
								traceFraisPack.setDatTimeTrcPack(cal.getTime());
								traceFraisPack.setCodPrdPack(virementVo.getProduit().getCodPrdPrd());
								Long codNatPack =
										operationCompteDAO.getNaturePack(virementVo.getProduit().getCodPrdPrd());
								traceFraisPack.setCodNatPack(codNatPack);
								traceFraisPack.setTypFactPack(virementVo.getPeriodicite());
								traceFraisPack.setMntComTrcPack(montant_commissionRecue);
								traceFraisPack.setMntTvaTrcPack(montant_tva);
								traceFraisPack.setMoisTrcPack(numMois);
								traceFraisPack.setAnneeTrcPack(Long.valueOf(annee));
								traceFraisPack.setEtatTrcPack("T");

								crudService.create(traceFraisPack);
								// **********************************************/
								this.setCroFlag(true);
								virementVo.setEtatEnregistrement(true);
								virementVo.setTraceFraisPack(traceFraisPack);

							}
						} else {

							// **************** Trace COMMISSION 0 *********************/
							mois = nomMois[Integer.valueOf(formaterMOIS.format(finMoisPrecedent))];
							numMois = Long.valueOf(formaterMOIS.format(finMoisPrecedent));
							annee = formaterANNEE.format(finMoisPrecedent);
							boolean trouveTrace = operationCompteDAO.verifierExistancePerceptionByCritere(contratCpt,
									virementVo.getProduit().getCodPrdPrd(), numMois, Long.valueOf(annee));

							if (trouveTrace == false) {
								TraceFraisPack traceFraisPack = new TraceFraisPack();
								traceFraisPack.setContratCpt(contratCpt);
								traceFraisPack.setOperation(oper);
								Calendar cal = Calendar.getInstance();
								traceFraisPack.setDatTimeTrcPack(cal.getTime());
								traceFraisPack.setCodPrdPack(virementVo.getProduit().getCodPrdPrd());
								Long codNatPack =
										operationCompteDAO.getNaturePack(virementVo.getProduit().getCodPrdPrd());
								traceFraisPack.setCodNatPack(codNatPack);
								traceFraisPack.setTypFactPack(virementVo.getPeriodicite());
								traceFraisPack.setMntComTrcPack(montant_commissionRecue);
								traceFraisPack.setMntTvaTrcPack(montant_tva);
								traceFraisPack.setMoisTrcPack(numMois);
								traceFraisPack.setAnneeTrcPack(Long.valueOf(annee));
								traceFraisPack.setEtatTrcPack("T");
								traceFraisPack.setDescTrcPack("Commission 0");

								crudService.create(traceFraisPack);
								this.setCroFlag(false);
								virementVo.setEtatEnregistrement(true);

								virementVo.setTraceFraisPack(traceFraisPack);
							}
						}
					} else if (codeProduitCpt.equals(Constants.COD_PRD_PRD_EPS_CARTE)
							&& etatExecutionTrimestre == false) {

						this.setCroFlag(false);

					}

				} else {
					virementVo.setEtatEnregistrement(true);
				}
			} else {

				// **************** Trace ***********************/

				TraceFraisPack traceFraisPack = new TraceFraisPack();
				traceFraisPack.setContratCpt(contratCpt);
				traceFraisPack.setOperation(oper);
				Calendar cal = Calendar.getInstance();
				traceFraisPack.setDatTimeTrcPack(cal.getTime());
				traceFraisPack.setCodPrdPack(virementVo.getProduit().getCodPrdPrd());
				Long codNatPack = operationCompteDAO.getNaturePack(virementVo.getProduit().getCodPrdPrd());
				traceFraisPack.setCodNatPack(codNatPack);
				traceFraisPack.setTypFactPack(virementVo.getPeriodicite());
				traceFraisPack.setMntComTrcPack(montant_commissionRecue);
				traceFraisPack.setMntTvaTrcPack(montant_tva);
				traceFraisPack.setMoisTrcPack(numMois);
				traceFraisPack.setAnneeTrcPack(Long.valueOf(annee));
				traceFraisPack.setEtatTrcPack("R");
				traceFraisPack.setDescTrcPack("Compte non valide (" + contratCpt.getCodEtatCcpt() + ")");

				crudService.create(traceFraisPack);
				this.setCroFlag(false);
				virementVo.setEtatEnregistrement(true);
				virementVo.setTraceFraisPack(traceFraisPack);
			}

			return (virementVo);
		}

		catch (

		Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur InsertCroBNACommissionFraisTenuePackTrt ");
			text.append(e.toString());
			erreur.setCode("400");
			erreur.setDescription(text.toString());
			erreur.setKey("InsertCroBNACommissionFraisTenuePackTrt");
			e.printStackTrace();
			virementVo.setEtatEnregistrement(false);
			throw new RuntimeException(e);
		}
	}

	public void genCroText(ValueObject vo) {
		VirementVo virementVo = (VirementVo) vo;
		/* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

		try {

			if (operationMoyPay != null && operationMoyPay.getNumOperOmp() != null) {
				this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
			}
			this.setLibRefCro("SMILE.FRAIS COMMISSION PACK");

			this.setDatValCro(operationMoyPay.getDatValOmp());
			this.setDatValCom(operationMoyPay.getDatValOmp());

			this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
			this.setCodStrcImpt(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc());
			this.setCodEtatCro(0);
			this.setCodeProduit(virementVo.getProduit().getCodPrdPrd() + "");
			this.setOperationId(operationMoyPay.getTache().getTacheId().getCodOperOper() + "");
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
			this.setDateOperation(operationMoyPay.getDatOperOmp());

			formater = new SimpleDateFormat("HH:mm:ss");
			String heureString = formater.format(new Date());
			this.setHeureOperation(heureString);
			this.setTypeOperationCro("O");
			this.setCodTachTach(1);
			if (operationMoyPay.getCodRefcOmp().length() > 16) {
				this.setCodRefcOmp(operationMoyPay.getCodRefcOmp().substring(0, 15));
			} else {
				this.setCodRefcOmp(operationMoyPay.getCodRefcOmp());
			}
			this.setDatExecCro(new Date());

			this.setNumCinUser("9999");
			this.setCodTypUser("M");

			/* ------------------Garniture de la partie VARIABLE du CRO---------------------------------- */

			StringBuffer cro = new StringBuffer("");

			cro.append("numCptBna=");
			cro.append(operationMoyPay.getContratCpt().getContratCptId().getCompteClient().replace(" ", "") + ";");
			if (operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd()
					.equals(Constants.COD_PRD_PRD_EPS_CARTE)) {
				cro.append("Trimestre=");
				cro.append(numTrimestre + ";");
			} else {
				cro.append("MOIS=");
				cro.append(mois + ";");
			}

			cro.append("ANNEE=");
			cro.append(annee + ";");
			// *** Statu client taxable ****//
			cro.append("COD_TVA_CLT=");
			if (etatBenifTaxable == true) {
				cro.append(new Long(0) + ";");
			} else {
				cro.append(new Long(1) + ";");
			}
			cro.append("FRAIS=");
			cro.append(montant_commissionRecue + ";");
			cro.append("MONT_TVA_OMP=");
			cro.append(montant_tva + ";");
			cro.append("FRAIS_TTC=");
			cro.append(Long.valueOf(montant_commissionRecue + montant_tva) + ";");

			this.setCroText(cro.toString());

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur InsertCroBNACommissionFraisTenuePackTrt ");
			text.append(e.toString());
			erreur.setCode("400");
			erreur.setDescription(text.toString());
			erreur.setKey("InsertCroBNACommissionFraisTenuePackTrt");
			virementVo.addError(erreur);
			logger.error(" *** Erreur lors de InsertCroBNACommissionFraisTenuePackTrt" + " : ", e);
			throw new RuntimeException(e);
		}
	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public VirementVo getConditionDeBanque(ContratCpt cpt, Long codeProduitPack, Date dateComptable, Long codePiece,
			String numPiece) {

		VirementVo virementVoCB = new VirementVo();
		TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
		// /-----------------------------////

		Operation operation = new Operation();
		operation.setCodOperOper(Constants.COD_OPER_FRAIS_ABONNEMENT_PACK);

		String montant = "0";
		String tvaRecue = "";
		String tva = "";
		String commissionRecue = "";
		String valueDateRecue = "";
		String valueDateComRecue = "";

		try {

			traitementConditionBanque.setCodOperOper(operation.getCodOperOper().toString());
			traitementConditionBanque.setCodPrdPrd(codeProduitPack + "");
			traitementConditionBanque.setCodStrcStrc(cpt.getContratCptId().getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(cpt.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(cpt.getContratCptId().getNumCcptCcpt().toString());
			traitementConditionBanque.setMontant(montant);
			traitementConditionBanque.setDateReference(DateHandler.dateToStr(dateComptable));
			traitementConditionBanque.setCodTpceTpce(codePiece + "");
			traitementConditionBanque.setNumPcePers(numPiece);
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
			logger.info("codCondCommission : " + traitementConditionBanque.getCodCondCommission());

		} catch (Exception e) {
			logger.error("Error occurred when trying to get bank conditions.", e);
		}

		return virementVoCB;

	}

	private static Date getFirstDayOfQuarter(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	private static Date getLastDayOfQuarter(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3 + 2);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
}
