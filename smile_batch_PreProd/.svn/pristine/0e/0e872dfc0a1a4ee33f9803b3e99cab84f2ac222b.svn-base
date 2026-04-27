package com.bna.smile.model.domaineplacement.traitement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.JourneeStructureBatchId;
import com.bna.commun.model.JourneeStructureId;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceBlocageMontantContrat;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.commande.GetAvgTMMbetweenDatesCmd;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamDates;
import com.bna.smile.model.domaineplacement.model.ParamLiquidation;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class LiquidationAEcheanceTrt extends Traitement {

	int nbrCptPlac = 0;
	Double sommePlacement = Double.valueOf("0");
	int nbrCptPlacTot = 0;
	Double sommePlacementTot = Double.valueOf("0");
	Structure agence = new Structure();
	Date dateDebutInteret = new Date();

	public LiquidationAEcheanceTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	// à ne pas laisser en variable global
	ICriteria criteria = searchEngine.createCriteria();
	ICriteria criteriaAvanc = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();

	public IValueObject perform(IValueObject vo) {
		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
		JourneeStructure journeeStructure = new JourneeStructure();
		JourneeStructureId journeeStructureId = new JourneeStructureId();
		ParamLiquidation paramLiquidation = (ParamLiquidation) vo;

		try {

			PlacementDAO plcDao = (PlacementDAO) context.getBean("placementDAO");

			Long l = plcDao.isBatchExec();
			if (l.intValue() == 0) {
				/// *** verouillage du batch
				journeeStructureId.setCodStrcStrc(Long.valueOf("900"));
				journeeStructureId.setDatJrnJrn(DateHandler.addJour(new Date(), 2));
				journeeStructure.setJourneeStructureId(journeeStructureId);
				journeeStructure.setCodStatJrn(Long.valueOf("0"));
				journeeStructure.setCodSoldJrn(Long.valueOf("2"));
				if (!journeeStructure.equals(null)) {
					crudService.create(journeeStructure);
				}
				/// *** traitement Batch
				List listAgencesPlacementLiq = plcDao.getListAgencesPlacement();
				ListOrderedMap ListAgPlc = null;
				if (listAgencesPlacementLiq != null && listAgencesPlacementLiq.size() > 0) {
					for (Iterator it1 = listAgencesPlacementLiq.iterator(); it1.hasNext();) {
						ListAgPlc = (ListOrderedMap) it1.next();

						if ((ListAgPlc.getValue(0)).toString() != null) {
							agence.setCodStrcStrc(Long.valueOf(ListAgPlc.getValue(0).toString()));
						}
						if ((ListAgPlc.getValue(1)).toString() != null) {
							ListAgPlc.getValue(1);
							paramLiquidation
									.setDateComptLiquidation(DateHandler.strToDate(ListAgPlc.getValue(1).toString()));
							paramLiquidation
									.setDateComptableAg(DateHandler.strToDate(ListAgPlc.getValue(1).toString()));
						}
						/// * tester si la journée batch n'est pas dejà inserée
						JourneeStructureBatch journeeStructureBatch = new JourneeStructureBatch();
						JourneeStructureBatch journeeStructureBatchRetour = new JourneeStructureBatch();
						JourneeStructureBatchId journeeStructureBatchId = new JourneeStructureBatchId();
						journeeStructureBatchId.setCodStrcStrc(agence.getCodStrcStrc());
						journeeStructureBatchId.setDatJrnJrn(paramLiquidation.getDateComptLiquidation());
						journeeStructureBatchId.setCodBatBmet(Constants.COD_BATCH_LQUIDATION);
						journeeStructureBatch.setJourneeStructureBatchId(journeeStructureBatchId);
						/// * tester si la journée n'est pas dejà inserée
						BatchService batchService = (BatchService) context.getBean("batchService");
						journeeStructureBatchRetour =
								(JourneeStructureBatch) batchService.getJourneeStructureBatch(journeeStructureBatch);
						if (journeeStructureBatchRetour != null
								&& journeeStructureBatchRetour.getCodStatJsb().intValue() == 0) { /// * structure non
																								  /// traitée
							this.setCroFlag(true);
							paramLiquidation = (ParamLiquidation) perf(paramLiquidation, agence);
							journeeStructureBatch.setDatCloJsb(new Date());
							journeeStructureBatch.setCodStatJsb(Long.valueOf("1"));
							journeeStructureBatch = (JourneeStructureBatch) batchService
									.updateJourneeStructureBatch(journeeStructureBatch);
						} else {
							logger.debug("Journée batch dejà insérée pour l'agence : "
									+ journeeStructureBatch.getJourneeStructureBatchId().getCodStrcStrc().toString());
						}
					}
				} else {
					logger.debug("La liste des agences est vide.");
				}
			} else {
				logger.info(" !!!  Le Batch Liquidation est en cours d execution. !!!");
			}
			return paramLiquidation;
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans LiquidationAEcheanceTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("LiquidationAEcheanceTrt");
			logger.error("Exception : ", e);
			paramLiquidation.addError(erreur);
			throw new RuntimeException(e);

		} finally {
			if (journeeStructure.getCodSoldJrn() != null && journeeStructure.getCodSoldJrn().intValue() == 2) {
				ICriteria critereDdeDecision = searchEngine.createCriteria();
				IExpression expression = searchEngine.createExpression();
				critereDdeDecision.add(expression.eq("codSoldJrn", Long.valueOf("2")));
				List listJourneeStructure = searchEngine.find(JourneeStructure.class, critereDdeDecision);
				crudService.remove((JourneeStructure) listJourneeStructure.get(0));
			}
		}
	}

	private IValueObject perf(ParamLiquidation paramLiquidation, Structure agence) {

		try {
			/// *** recherche des placement a liquider pour cette agence
			ICriteria criteriaPlac = searchEngine.createCriteria();
			criteriaPlac.add(expression.eq("codEtatCpla", "V"));
			criteriaPlac.add(expression.eq("contratCpt.contratCptId.codStrcStrc", agence.getCodStrcStrc()));
			criteriaPlac.add(expression.lt("datEcheCpla", paramLiquidation.getDateComptLiquidation()));
			/// System.out.println(" - DateComptLiquidation : "+ paramLiquidation.getDateComptLiquidation());
			nbrCptPlac = 0;
			sommePlacement = Double.valueOf("0");
			List listePlacement = searchEngine.find(ContratPlacement.class, criteriaPlac);
			if (listePlacement != null && listePlacement.size() > 0) {
				for (Iterator it1 = listePlacement.iterator(); it1.hasNext();) {
					ContratPlacement contratPlacement = (ContratPlacement) it1.next();
					paramLiquidation.setContratPlacement(contratPlacement);
					paramLiquidation.setDateComptableAg(getDateComptable(contratPlacement.getDatEcheCpla()));/// ***
																											 /// DATE
																											 /// POUR
																											 /// cond bq
					paramLiquidation.setDateEcheanceContrat(contratPlacement.getDatEcheCpla());/// *** DATE POUR cond bq
																							   /// 311
					paramLiquidation.setDateOperationLiq(
							getDateComptable(DateHandler.addJour(contratPlacement.getDatEcheCpla(), 1)));/// *** DATE
																										 /// POUR cond
																										 /// bq 613

					//// ****paramLiquidation.setDateComptLiquidation(getDateComptable(contratPlacement.getDatEcheCpla()));
					//// ///*** datecomptable pour chaque placement
					PlacementService placementService = (PlacementService) context.getBean("placementService");
					AvancRembLiquid avancRembLiquid =
							(AvancRembLiquid) placementService.LiquidationAvancesPlacement(paramLiquidation);

					/// *** Appel Traitement Liqudation
					traiterLiquidation(avancRembLiquid, paramLiquidation);

					nbrCptPlac = nbrCptPlac + 1;
					sommePlacement = sommePlacement + Double.valueOf(contratPlacement.getMontCapCpla().toString());

					nbrCptPlacTot = nbrCptPlacTot + 1;
					sommePlacementTot =
							sommePlacementTot + Double.valueOf(contratPlacement.getMontCapCpla().toString());

				}

			}
			/// *** gerer les statistiques
			gestionStatistique(paramLiquidation, agence, nbrCptPlac, sommePlacement);
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans Liquidation-perf : ");
			text.append(e.getMessage());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("Liquidation-perf");
			logger.error("Exception : ", e);
			paramLiquidation.addError(erreur);
			/// *** gerer une exception
			gestionException(paramLiquidation, agence, e);
			throw new RuntimeException(e);

		}
		return paramLiquidation;

	}

	private void gestionStatistique(ParamLiquidation paramLiquidation, Structure agence, int nbrCptPlac,
			Double sommePlacement) {

		BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
		batchStatPlacement.setCodEtatBats("V");
		batchStatPlacement.setDatSystBats(new Date());
		batchStatPlacement.setDatCompBats(paramLiquidation.getDateComptLiquidation());
		batchStatPlacement.setStructure(agence);
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_LQUIDATION);
		batchStatPlacement.setBatchMetier(batchMetier);
		batchStatPlacement.setLibExtrBats(
				nbrCptPlac + " Contrats liquidés, pour la somme de : " + (sommePlacement.longValue()) + " Dinars");
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);
	}

	private void gestionException(ParamLiquidation paramLiquidation, Structure agence, Exception e) {

		BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();
		batchExeptionPlac.setDatSystBate(new Date());
		batchExeptionPlac.setDatCompBate(paramLiquidation.getDateComptLiquidation());
		batchExeptionPlac.setStructure(agence);
		batchExeptionPlac.setLibTpbmBate("Exception Batch Liquidation a échéance");
		batchExeptionPlac.setLibExpBate(e.getMessage());
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
	}

	public void traiterLiquidation(AvancRembLiquid avancRembLiquid, ParamLiquidation paramLiquidation) {

		try {
			GetContratPlacementTrt getContratPlacementTrt = new GetContratPlacementTrt();
			avancRembLiquid.setContratPlacement(
					(ContratPlacement) getContratPlacementTrt.exec(paramLiquidation.getContratPlacement()));
			avancRembLiquid.getContratPlacement().setCodEtatCpla("L");
			avancRembLiquid.getContratPlacement().setContratCpt(paramLiquidation.getContratCpt());

			LiquidationAvancesPlacementTrt liquidationAvancesPlacementTrt = new LiquidationAvancesPlacementTrt();
			TraitementConditionBanque traitementConditionBanqueLiq = liquidationAvancesPlacementTrt.getCB(
					avancRembLiquid.getContratPlacement(), Constants.COD_OPER_LIQUID_AECH_PLAC, paramLiquidation);

			/// *** cas du post compté
			if (avancRembLiquid.getContratPlacement().getCodPintCpla().equalsIgnoreCase("POST")) {
				TraitementConditionBanque traitementConditionBanqueInteretServiLiq =
						liquidationAvancesPlacementTrt.getCB(avancRembLiquid.getContratPlacement(),
								Constants.OPER_INT_POST_SOUSC_PLAC, paramLiquidation);
				avancRembLiquid
						.setDatValiArl(DateHandler.strToDate(traitementConditionBanqueInteretServiLiq.getDatevaleur()));
			} else
				avancRembLiquid.setDatValiArl(paramLiquidation.getDateComptableAg());

			/*------------------------------------------- Garnir  AvancRembLiquid ----------------------------------------------  */
			avancRembLiquid.setNumSeqArl(null);
			avancRembLiquid.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
			avancRembLiquid.setCodToprArl(Constants.CODE_LIQUIDATION_ECHEANCE);
			/// avancRembLiquid.getContratPlacement().setMontActuCpla(Long.valueOf("0")); ///*** MAJ du montant actuel
			/// du placement
			//// avancRembLiquid.setDatValiArl(DateHandler.strToDate(traitementConditionBanqueInteretServiLiq.getDatevaleur()));
			/// *** calcul montant remboursement
			if (avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc()
					.equals(Constants.COD_PRD_BNAPLC_PLAC))
				avancRembLiquid.setMontArlArl(avancRembLiquid.getContratPlacement().getMontActuCpla());
			else
				avancRembLiquid.setMontArlArl(avancRembLiquid.getContratPlacement().getMontCapCpla());

			avancRembLiquid.setDatArlArl(paramLiquidation.getDateComptableAg());

			/// *** appel calcul interet liquid (hassine)
			avancRembLiquid = calculerInteretLiquidationEchance(avancRembLiquid, paramLiquidation);
			/*------------------------------------------- Garnir  DetailsOperationPlacement ----------------------------------------------  */

			DetailsOperationPlacement detailsOperationPlacement = new DetailsOperationPlacement();

			for (Iterator it = avancRembLiquid.getDetailsOperationPlacements().iterator(); it.hasNext();) {
				detailsOperationPlacement = (DetailsOperationPlacement) it.next();
				break;
			}
			detailsOperationPlacement.setDatOperDopl(new Date());
			Tache tache = new Tache();
			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(Constants.COD_OPER_LIQUID_AECH_PLAC);
			tacheId.setCodTachTach(Constants.COD_TACHE_VALID_LIQ_ECHEANCE);
			tache.setTacheId(tacheId);
			detailsOperationPlacement.setTache(tache);
			Personnel personnel = new Personnel();
			personnel.setNumMatrUser("9999");
			detailsOperationPlacement.setPersonnel(personnel);
			Structure structure = new Structure();
			structure.setCodStrcStrc(paramLiquidation.getContratCpt().getContratCptId().getCodStrcStrc());
			detailsOperationPlacement.setStructure(structure);
			detailsOperationPlacement
					.setDatValDopl(DateHandler.strToDate(traitementConditionBanqueLiq.getDatevaleur()));
			detailsOperationPlacement.setMontDopDopl(avancRembLiquid.getMontArlArl());
			detailsOperationPlacement.setDatCompDopl(paramLiquidation.getDateComptLiquidation());
			detailsOperationPlacement.setNumSeqDopl(null);
			detailsOperationPlacement
					.setTypePieceByCodTpssTpce(paramLiquidation.getContratPlacement().getPersonne().getTypePiece());
			detailsOperationPlacement
					.setNumNpssDopl(paramLiquidation.getContratPlacement().getPersonne().getNumPcePers());

			/*------------------------------------------- Garnir  DetailsOperationPlacement ----------------------------------------------  */

			OperationMoyPay operationMoyPay = new OperationMoyPay();

			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(paramLiquidation.getContratCpt().getContratCptId().getCodStrcStrc());
			Structure structureRecep = new Structure();
			structureRecep.setCodStrcStrc(paramLiquidation.getContratCpt().getContratCptId().getCodStrcStrc());

			Devise devise = new Devise();
			//// devise.setCodDevDev(Constants.COD_DEV_DINAR);
			devise.setCodDevDev(avancRembLiquid.getContratPlacement().getContratCpt().getDevise().getCodDevDev());
			operationMoyPay.setDevise(devise);

			operationMoyPay.setContratCpt(paramLiquidation.getContratCpt());

			operationMoyPay.setStructureInitiatrice(structureInit);
			operationMoyPay.setStructureReceptrice(structureRecep);

			if (avancRembLiquid.getContratPlacement().getNumBcCpla() != null)
				operationMoyPay.setNumMoypOmp(avancRembLiquid.getContratPlacement().getNumBcCpla().toString());

			operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
			operationMoyPay.setPersonnelInitiateur(personnel);/// personne initiatrice seulement au cas de retrait
															  /// ponctuel
			operationMoyPay.setPersonnelValideur(personnel);/// personnel initiatrice = personnel validateur
			operationMoyPay.setTache(tache);
			operationMoyPay.setDatOperOmp(paramLiquidation.getDateComptLiquidation());
			operationMoyPay.setDatSystOmp(new Date());
			operationMoyPay.setDatValOmp(DateHandler.strToDate(traitementConditionBanqueLiq.getDatevaleur()));
			Produit produitPlacOmp = new Produit();
			/// operationMoyPay.setCodRefbOmp("N°
			/// "+(StrHandler.lpad(avancRembLiquid.getContratPlacement().getNumSeqCpla().toString(),'0',15)).substring(7,15));
			operationMoyPay.setCodRefbOmp(avancRembLiquid.getContratPlacement().getNumSeqCpla().toString());
			// operationMoyPay.setCodRefcOmp((StrHandler.lpad(avancRembLiquid.getContratPlacement().getNumSeqCpla().toString(),'0',15)).substring(7,15));
			// operationMoyPay.setCodRefmOmp(avancRembLiquid.getNumSeqArl().toString());
			operationMoyPay.setCodRefcOmp("");
			produitPlacOmp.setCodPrdPrd(avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc());
			operationMoyPay.setProduit(produitPlacOmp);
			TypePiece typePieceDem = avancRembLiquid.getContratPlacement().getPersonne().getTypePiece();
			operationMoyPay.setTypePieceDemandeur(typePieceDem);
			operationMoyPay.setNumPcedOmp(avancRembLiquid.getContratPlacement().getPersonne().getNumPcePers());
			operationMoyPay.setNomNomdOmp(avancRembLiquid.getContratPlacement().getPersonne().getNomNomPers());
			operationMoyPay.setNomPrndOmp(avancRembLiquid.getContratPlacement().getPersonne().getNomPrnPers());
			operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);
			operationMoyPay.setMontDinOmp(avancRembLiquid.getMontArlArl());
			operationMoyPay.setCodDemOmp("T"); /// *** type demandeur (Titulaire,CoTitul,Mandataire)
			if (paramLiquidation.getSoldeCptAvant() != null) {
				operationMoyPay.setMontSoldCcpt(paramLiquidation.getSoldeCptAvant());
			} else {
				operationMoyPay.setMontSoldCcpt(paramLiquidation.getContratCpt().getMontSoldCcpt());
			}
			operationMoyPay.setMontApreOmp(
					Long.valueOf(new Long(new Double((new Double(operationMoyPay.getMontSoldCcpt()).doubleValue())
							+ (new Double(avancRembLiquid.getMontArlArl()))).longValue())));
			operationMoyPay.setLibMotfOmp("Liquidation a écheance");

			detailsOperationPlacement.setOperationMoyPay(operationMoyPay);
			avancRembLiquid.getDetailsOperationPlacements().add(detailsOperationPlacement);

			/***************** CAS d'un depot affectée ***********/
			if (avancRembLiquid.getContratPlacement().getMontBlocDaff() != null
					&& avancRembLiquid.getContratPlacement().getMontBlocDaff().longValue() != 0) {

				TraceBlocageMontantContrat traceBlocageMontantContrat = new TraceBlocageMontantContrat();
				traceBlocageMontantContrat.setContratCpt(paramLiquidation.getContratCpt());
				traceBlocageMontantContrat.setMntBlocBloc(avancRembLiquid.getContratPlacement().getMontBlocDaff());
				traceBlocageMontantContrat.setMntBlocDinBloc(avancRembLiquid.getContratPlacement().getMontBlocDaff());
				traceBlocageMontantContrat.setDatDebBloc(paramLiquidation.getDateComptLiquidation());
				traceBlocageMontantContrat.setDatTimeOp(new Date());
				traceBlocageMontantContrat.setPersonnelBlocage(personnel);
				traceBlocageMontantContrat.setCanalBlocBloc("BATCH_SMILE");
				traceBlocageMontantContrat.setDevise(avancRembLiquid.getContratPlacement().getContratCpt().getDevise());
				traceBlocageMontantContrat.setDescription("BLOCAGE MONTANT SUITE UN DEPOT AFFECTE SUR UN PLACEMENT N° "
						+ avancRembLiquid.getContratPlacement().getNumSeqCpla() + "  ");
				traceBlocageMontantContrat.setMotifBlocage("BLOCAGE MONTANT SUITE UN DEPOT AFFECTE SUR UN PLACEMENT N° "
						+ avancRembLiquid.getContratPlacement().getNumSeqCpla() + "  ");

				paramLiquidation.setTraceBlocageMontantContrat(traceBlocageMontantContrat);
			}

			HibernateTemplate hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
			hibernateTemplate.evict(avancRembLiquid);

			PlacementService placementService = (PlacementService) context.getBean("placementService");
			paramLiquidation.setAvancRembLiquid(avancRembLiquid);
			AvancRembLiquid avancRembLiquidretour =
					(AvancRembLiquid) placementService.validerLiquidationAnticipeePlacementLAE(paramLiquidation);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans Liquidation-rembourserAvance : ");
			text.append(e.getMessage());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("Liquidation a echeance-traiterLiquidation");
			logger.error("Exception : ", e);
			throw new RuntimeException(e);

		}
	}

	public AvancRembLiquid calculerInteretLiquidationEchance(AvancRembLiquid avancRembLiquid,
			ParamLiquidation paramLiquidation) {

		try {

			// l'opération versement interets liquidation
			// paramLiquidation.setCodOperOper(Constants.COD_OPER_VERSEMENT_INTERET_LIQUID_ANTICIPE);

			// TraitementConditionBanque traitementConditionBanque =
			// getConditionBanque(avancRembLiquid,paramLiquidation);

			Double tauxVariable = Double.valueOf("0");

			if (avancRembLiquid.getContratPlacement().getCodFavCpla().equalsIgnoreCase("G")
					|| avancRembLiquid.getContratPlacement().getCodFavCpla().equalsIgnoreCase("F")
					|| avancRembLiquid.getContratPlacement().getCodFavCpla().equalsIgnoreCase("P")) {/// *** Taux
																									 /// général
																									 // si la condition
																									 /// est ponctuelle,
																									 /// le taux est
																									 /// fixe, on le
																									 /// récupére a
																									 /// partir de
																									 /// ValMarge/ modif
																									 /// 20.04.2010
				avancRembLiquid.setNumTauiArl(Double.valueOf(avancRembLiquid.getContratPlacement().getNumTauiCpla()));
			} else {/// *** Taux variable

				if (avancRembLiquid.getContratPlacement().getCodMargCpla().equalsIgnoreCase("+"))
					tauxVariable = Math.abs(calculerMoyenneTauxPlacement(avancRembLiquid)
							+ avancRembLiquid.getContratPlacement().getNumMargCpla());
				else
					tauxVariable = Math.abs(calculerMoyenneTauxPlacement(avancRembLiquid)
							- avancRembLiquid.getContratPlacement().getNumMargCpla());

				avancRembLiquid.setNumTauiArl(tauxVariable);
			}

			avancRembLiquid.setMontArlArl(new Long(
					new Double(new Double(avancRembLiquid.getContratPlacement().getMontCapCpla()).doubleValue())
							.longValue()));
			avancRembLiquid.setCodTyplArl("T");
			avancRembLiquid.setCodSbdvArl("0");
			avancRembLiquid.setCodToprArl(Constants.CODE_LIQUIDATION_ECHEANCE);
			avancRembLiquid.setCodEtatArl("V");
			avancRembLiquid.setDatArlArl(paramLiquidation.getDateComptableAg());
			// avancRembLiquid.setDatValiArl(paramLiquidation.getDateComptLiquidation());

			Double duree = avancRembLiquid.getContratPlacement().getNumNbrjCpla().doubleValue();
			Double montInteretPlacement = Double.valueOf(0);
			if (avancRembLiquid.getContratPlacement().getCodPintCpla().equals(Constants.PLACEMENT_PRECOMPTE)) {
				// placement PRECOMPTE Montant des intéret à servir est null;
				avancRembLiquid.setMontInetArl(Double.valueOf("0"));
				avancRembLiquid.setCodTypiArl("N");
			} else {

				// Placement POSTCOMPTE: Le client encaisse le capital placé et les ineterets pour la periode totale du
				// placement

				// interet = C*T*N/36500
				// verifier le cas de placement BNA PLACEMENT (Possibilité de liquidation anticipée partielle : donc on
				// liquide à echeance le montant actualisé)

				// calculer les interêts à servir

				// Date de modification de la base de calcul pour le produit BNA placement " de 360 à 365 jours "
				// 30/06/2020
				String dateCalculBnaPlac = "30/06/2020";

				if (avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc()
						.equals(Constants.COD_PRD_BNAPLC_PLAC)
						&& avancRembLiquid.getContratPlacement().getDatValCpla()
								.after(DateHandler.strToDate(dateCalculBnaPlac))) {
					avancRembLiquid.setMontArlArl(new Long(new Double(
							new Double(avancRembLiquid.getContratPlacement().getMontActuCpla()).doubleValue())
									.longValue()));
					if (avancRembLiquid.getContratPlacement().getMontActuCpla()
							.equals(avancRembLiquid.getContratPlacement().getMontCapCpla()))//// il'n'y a pas de
																							//// liquidation anticp on
																							//// calcul les interets
																							//// pour la periode totale
																							//// du placement
					{
						montInteretPlacement = Double.valueOf(avancRembLiquid.getContratPlacement().getMontActuCpla()
								* avancRembLiquid.getNumTauiArl() * duree) / 36500;
					}
				} else if (avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc()
						.equals(Constants.COD_PRD_BNAPLC_PLAC)
						&& avancRembLiquid.getContratPlacement().getDatValCpla()
								.compareTo(DateHandler.strToDate(dateCalculBnaPlac)) == 0) {
					avancRembLiquid.setMontArlArl(new Long(new Double(
							new Double(avancRembLiquid.getContratPlacement().getMontActuCpla()).doubleValue())
									.longValue()));
					if (avancRembLiquid.getContratPlacement().getMontActuCpla()
							.equals(avancRembLiquid.getContratPlacement().getMontCapCpla()))//// il'n'y a pas de
																							//// liquidation anticp on
																							//// calcul les interets
																							//// pour la periode totale
																							//// du placement
					{
						montInteretPlacement = Double.valueOf(avancRembLiquid.getContratPlacement().getMontActuCpla()
								* avancRembLiquid.getNumTauiArl() * duree) / 36500;
					}
				} else if (avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc()
						.equals(Constants.COD_PRD_BNAPLC_PLAC)
						&& avancRembLiquid.getContratPlacement().getDatValCpla()
								.before(DateHandler.strToDate(dateCalculBnaPlac))) {
					avancRembLiquid.setMontArlArl(new Long(new Double(
							new Double(avancRembLiquid.getContratPlacement().getMontActuCpla()).doubleValue())
									.longValue()));
					if (avancRembLiquid.getContratPlacement().getMontActuCpla()
							.equals(avancRembLiquid.getContratPlacement().getMontCapCpla()))//// il'n'y a pas de
																							//// liquidation anticp on
																							//// calcul les interets
																							//// pour la periode totale
																							//// du placement
					{
						// Garder la base de calcul de 360 jours pour les anciens contrat BNA Placement
						montInteretPlacement = Double.valueOf(avancRembLiquid.getContratPlacement().getMontActuCpla()
								* avancRembLiquid.getNumTauiArl() * duree) / 36000;
					}

				} else {
					montInteretPlacement = (avancRembLiquid.getContratPlacement().getMontCapCpla().doubleValue()
							* avancRembLiquid.getNumTauiArl() * duree) / 36500;
				}
				// determination de la duree reele du placement // vérifier les interets servis pour les placements
				// postcomptés qui chevauchent entre 1 et n années...

				List listIntView = new ArrayList();
				Date dateIntertMax = null;
				GetListInteretServiTrt getListInteretServiTrt = new GetListInteretServiTrt();
				Listes listInt = (Listes) getListInteretServiTrt.exec(avancRembLiquid.getContratPlacement());
				Double montInteretServis = Double.valueOf(0);
				if (listInt != null && listInt.getList() != null) {
					if (listInt.getList().size() > 0) {
						for (Iterator it = listInt.getList().iterator(); it.hasNext();) {
							InteretServi interetServi = (InteretServi) it.next();
							if (interetServi.getCodTypIsrv() != null
									&& interetServi.getCodTypIsrv().equalsIgnoreCase("P")) {
								if (dateIntertMax != null) {
									if (interetServi.getDatIsrvIsrv().after(dateIntertMax))
										dateIntertMax = interetServi.getDatIsrvIsrv();
								} else
									dateIntertMax = interetServi.getDatIsrvIsrv();

								montInteretServis =
										Math.abs(interetServi.getMontBrutIsrv().doubleValue()) + montInteretServis;
							}
						}
						// recalculer la durée réele de clacul des intérets ( date de la liquidation - date dernier
						// versement intrete partiel):
						if (dateIntertMax != null)
							duree = DateHandler.getDaysBetween(dateIntertMax,
									avancRembLiquid.getContratPlacement().getDatEcheCpla()) + 1; //// a enlever +1
						// ajout de la journee du dernier versement d'interet
					}
				}
				Double intretBrutAServir = Double.valueOf(0);
				// Date de modification de la base de calcul pour le produit BNA placement " de 360 à 365 jours "
				// 30/06/2020
				if (avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc()
						.equals(Constants.COD_PRD_BNAPLC_PLAC)
						&& avancRembLiquid.getContratPlacement().getDatValCpla()
								.after(DateHandler.strToDate(dateCalculBnaPlac))) {
					if (avancRembLiquid.getContratPlacement().getMontActuCpla()
							.equals(avancRembLiquid.getContratPlacement().getMontCapCpla())) {
						//// il'n'y a pas de liquidation anticp on calcul les interets pour la periode totale du
						//// placement - les interets servis
						intretBrutAServir = Math.max((montInteretPlacement - montInteretServis), 0);
					} else {
						intretBrutAServir = Double.valueOf(avancRembLiquid.getContratPlacement().getMontActuCpla()
								* avancRembLiquid.getNumTauiArl() * duree) / 36500;
					}
				} else if (avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc()
						.equals(Constants.COD_PRD_BNAPLC_PLAC)
						&& avancRembLiquid.getContratPlacement().getDatValCpla()
								.compareTo(DateHandler.strToDate(dateCalculBnaPlac)) == 0) {
					if (avancRembLiquid.getContratPlacement().getMontActuCpla()
							.equals(avancRembLiquid.getContratPlacement().getMontCapCpla())) {
						//// il'n'y a pas de liquidation anticp on calcul les interets pour la periode totale du
						//// placement - les interets servis
						intretBrutAServir = Math.max((montInteretPlacement - montInteretServis), 0);
					} else {
						intretBrutAServir = Double.valueOf(avancRembLiquid.getContratPlacement().getMontActuCpla()
								* avancRembLiquid.getNumTauiArl() * duree) / 36500;
					}

				} else if (avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc()
						.equals(Constants.COD_PRD_BNAPLC_PLAC)
						&& avancRembLiquid.getContratPlacement().getDatValCpla()
								.before(DateHandler.strToDate(dateCalculBnaPlac))) {
					if (avancRembLiquid.getContratPlacement().getMontActuCpla()
							.equals(avancRembLiquid.getContratPlacement().getMontCapCpla())) {
						//// il'n'y a pas de liquidation anticp on calcul les interets pour la periode totale du
						//// placement - les interets servis
						intretBrutAServir = Math.max((montInteretPlacement - montInteretServis), 0);
					} else {
						// Garder la base de calcul de 360 jours pour les anciens contrat BNA Placement
						intretBrutAServir = Double.valueOf(avancRembLiquid.getContratPlacement().getMontActuCpla()
								* avancRembLiquid.getNumTauiArl() * duree) / 36000;
					}

				} else {
					intretBrutAServir = Math.max((montInteretPlacement - montInteretServis), 0);
				}
				Double montantIRC = Double.valueOf(0);
				long numTircCpla = 20;
				if (avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc()
						.equals(Constants.COD_PRD_BC_PLAC)
						|| avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc()
								.equals(Constants.COD_PRD_CAT_PLAC)
						|| avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc()
								.equals(Constants.COD_PRD_BNAPLC_PLAC)) {

					montantIRC = Double.valueOf(intretBrutAServir * numTircCpla / 100);
				} else {
					montantIRC = Double
							.valueOf(intretBrutAServir * avancRembLiquid.getContratPlacement().getNumTircCpla() / 100);
				}

				Double intretNetAServir = Double.valueOf(Math.round(intretBrutAServir) - Math.round(montantIRC));
				avancRembLiquid.setMontInetArl(intretNetAServir);
				avancRembLiquid.setMontBrutArl(Double.valueOf(Math.round(intretBrutAServir)));
				avancRembLiquid.setMontIrcArl(Double.valueOf(Math.round(montantIRC)));
				avancRembLiquid.setCodTypiArl("S");

			}

			// Mise à jour du contratPlacement
			avancRembLiquid.getContratPlacement().setMontActuCpla(Long.valueOf("0"));
			avancRembLiquid.getContratPlacement().setCodEtatCpla(Constants.ETAT_CPT_PLACEMENT_LIQUIDE);
			avancRembLiquid.getContratPlacement().setDatLiqCpla(paramLiquidation.getDateComptLiquidation());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return avancRembLiquid;
	}

	public Date getDateComptable(Date d) {

		try {
			if (CalanderHandler.isJourFerier(d)) {
				return (CalanderHandler.GetNextWorkingDay(d));
			} else {
				return (d);
			}

		} catch (Exception e) {
			logger.error(" Erreur dans GetDateComptable.execute : ", e);
			return (d);
		}
	}

	public void genCroText(ValueObject vo) {

		ParamLiquidation paramLiquidation = (ParamLiquidation) vo;

		/* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.oxia.security.abc.model.Personnel user = null;
		if (obj instanceof UserDetails) {
			user = (com.oxia.security.abc.model.Personnel) obj;
		}

		this.setNumRefCro(Long.valueOf("99999"));
		/// *this.setNumRefCro(Long.valueOf(paramLiquidation.getAvancRembLiquid().getNumSeqArl()));
		this.setLibRefCro("SMILE.Placement.BatLiqEch");
		this.setDatValCro(new Date());
		/// *this.setDatValCro(paramLiquidation.getDateValInteretServLiq());
		this.setCodeStructInitiatrice("900");
		this.setCodStrcImpt(Long.valueOf("900"));
		this.setCodEtatCro(0);
		this.setCodeProduit(Constants.COD_DOM_PLACEMENT.toString());
		this.setOperationId(String.valueOf(Constants.COD_OPERATION_FIN_BATCH));
		this.setDateOperation(new Date());
		/// *this.setDateOperation(paramLiquidation.getDateComptLiquidation());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("B");
		this.setCodTachTach(Constants.COD_TACHE_VALID_AVANC_PLAC);
		/// if (avancRembLiquid.getNumSeqArl()!=null)
		this.setCodRefcOmp(" ");
		this.setDatExecCro(new Date());

		this.setNumCinUser(user.getNumMatrUser());
		this.setCodTypUser(user.getMatriculeTyp());
		// this.setCodTypUser();
		// this.setNumCinUser();

		/* ------------------Garniture de la partie VARIABLE du CRO---------------------------------- */
		StringBuffer cro = new StringBuffer("");

		// contratClient
		cro.append("NombreContratsLiquides=");
		cro.append(nbrCptPlacTot + ";");

		cro.append("SommeMontantPlacement=");
		cro.append(sommePlacementTot.longValue() + ";");

		this.setCroText(cro.toString());

		System.out.println("  ");
		System.out.println("  /*************** Fin de la Liquidation a echeance *****************/");
		System.out.println("  ");

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public Double calculerMoyenneTauxPlacement(AvancRembLiquid avancRembLiquid) {

		double taux = Double.valueOf(0);
		try {
			ParamDates paramDates = new ParamDates();
			paramDates.setDateDebut(DateHandler.addJour(avancRembLiquid.getContratPlacement().getDatValCpla(), 1));//// ajout
																												   //// d'un
																												   //// jour
																												   //// lors
																												   //// du
																												   //// calcul
																												   //// +
																												   //// calcule
																												   //// du
																												   //// date
																												   //// debut
																												   //// (moyenne
																												   //// de
																												   //// toute
																												   //// la
																												   //// periode)
																												   //// pour
																												   //// tt
																												   //// les
																												   //// prd
																												   //// sauf
																												   //// BNAPlac
																												   //// 1004
			if (avancRembLiquid.getContratPlacement().getContratPlacementByNumSqcrCpla() != null) {
				paramDates.setDateDebut(avancRembLiquid.getContratPlacement().getDatValCpla());
			}
			//// calcule du date debut des interets servis dans le cas du BNAPLC (moyenne de la derinner periode)
			if (avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc()
					.equals(Constants.COD_PRD_BNAPLC_PLAC)) {
				paramDates.setDateDebut(avancRembLiquid.getContratPlacement().getDatValCpla());//// calcule du date
																							   //// debut pour BNAPLC
				if (!avancRembLiquid.getContratPlacement().getMontActuCpla()
						.equals(avancRembLiquid.getContratPlacement().getMontCapCpla())) {//// il y a des liquidations
																						  //// anticp taux d'interet
																						  //// apartir du dernier
																						  //// interet servi
					List listIntView = new ArrayList();
					Date dateIntertMax = null;
					GetListInteretServiTrt getListInteretServiTrt = new GetListInteretServiTrt();
					Listes listInt = (Listes) getListInteretServiTrt.exec(avancRembLiquid.getContratPlacement());
					Double montInteretServis = Double.valueOf(0);
					if (listInt != null && listInt.getList() != null) {
						if (listInt.getList().size() > 0) {
							for (Iterator it = listInt.getList().iterator(); it.hasNext();) {
								InteretServi interetServi = (InteretServi) it.next();
								if (interetServi.getCodTypIsrv() != null
										&& interetServi.getCodTypIsrv().equalsIgnoreCase("P")) {
									if (dateIntertMax != null) {
										if (interetServi.getDatIsrvIsrv().after(dateIntertMax))
											dateIntertMax = interetServi.getDatIsrvIsrv();
									} else
										dateIntertMax = interetServi.getDatIsrvIsrv();

									paramDates.setDateDebut(dateIntertMax);

								}
							}

						}
					}
				}
			}

			paramDates.setDateFin(avancRembLiquid.getContratPlacement().getDatEcheCpla());
			paramDates.setInterval(Constants.INTERVAL_TMM_JOURNALIER);
			GetAvgTMMbetweenDatesCmd getAvgTMMbetweenDates = new GetAvgTMMbetweenDatesCmd();
			PrimitiveVO primitiveVO = (PrimitiveVO) getAvgTMMbetweenDates.execute(paramDates);
			taux = (primitiveVO.getVDouble().doubleValue());

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("");
			text.append(e.getMessage());
			erreur.setCode("200");
			erreur.setDescription(text.toString());
		}
		return (taux);

	}

}
