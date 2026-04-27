package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.DetailsOperationVirement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.MandantPersVirement;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.NomencElemtCondition;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.PersCotitVirement;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceOperVirement;
import com.bna.commun.model.TraceVirement;
import com.bna.commun.model.TypePiece;
import com.bna.commun.service.CommunService;
import com.bna.commun.traitements.InsertionOperationMoyPaySansCROTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.GetMandatPersonneByIdTrt;
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

public class CreationCROExecutionVirementPonctuelTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	VirementService virementService = (VirementService) context.getBean("iVirementService");
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
	long montant_commision = 0;
	long montant_tva = 0;
	long montantGlobalVirement = 0;
	String dateValueRecu = "";
	String dateValueComRecu = "";
	boolean etatClientTaxable = false;
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");

	public CreationCROExecutionVirementPonctuelTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		GlobalVirement globalVirement = new GlobalVirement();
		Date dateComptable = virementVo.getDateComptableAgence();
		Operation operation = new Operation();
		ContratCpt contratCptDO = new ContratCpt();

		try {
			montantGlobalVirement = virementVo.getMontantGlobalByCB();
			montant_commision = virementVo.getMontant_commissionRecue();
			montant_tva = new Long(virementVo.getTvaRecue());
			dateValueRecu = virementVo.getValueDateRecue();
			dateValueComRecu = virementVo.getValueDateComRecue();
			etatClientTaxable = virementVo.isEtatTaxableClient();
			globalVirement = virementVo.getGlobalVirement();
			operation.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PONCTUEL);
			contratCptDO = globalVirement.getContratCpt();

			// ********RE-FIND Compte donneur d'ordre ************//

			contratCptDO = (ContratCpt) searchEngine.get(ContratCpt.class, contratCptDO.getContratCptId());

			// ********* Operation Moyen Payement ********//

			OperationMoyPay operationMoyPayCompteDO = new OperationMoyPay();

			// 00. setting obj operationMoyPayCompteDO
			String motifEnvoi = "";
			if (virementVo.getEtatBenifVirementCB() != null
					&& virementVo.getEtatBenifVirementCB().equals(Long.valueOf(0))) {
				motifEnvoi = "MÊME AGENCE";
			} else if (virementVo.getEtatBenifVirementCB() != null
					&& virementVo.getEtatBenifVirementCB().equals(Long.valueOf(1))) {
				motifEnvoi = "AUTRE AGENCE";
			} else if (virementVo.getEtatBenifVirementCB() != null
					&& virementVo.getEtatBenifVirementCB().equals(Long.valueOf(2))) {
				motifEnvoi = "AUTRE BANQUE";
			} else if (virementVo.getEtatBenifVirementCB() != null
					&& virementVo.getEtatBenifVirementCB().equals(Long.valueOf(3))) {
				motifEnvoi = "SGMT";
			}
			operationMoyPayCompteDO.setLibObjOpOmp("EXECUTION VIREMENT PONCTUEL " + motifEnvoi);
			operationMoyPayCompteDO.setCodRefbOmp(motifEnvoi.toLowerCase());
			// 01. setting personnel initiateur et valideur
			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser("9999");
			operationMoyPayCompteDO.setPersonnelInitiateur(personnelInit);
			operationMoyPayCompteDO.setPersonnelValideur(personnelInit);

			// 02. setting structure initiatrice
			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(globalVirement.getStructure().getCodStrcStrc());
			operationMoyPayCompteDO.setStructureInitiatrice(structureInit);

			// 04. getting montant a retirer
			Long montVirCcpt = new Long(montantGlobalVirement);

			// 06. getting provision
			Long montSoldThCcpt = 0L;
			try {
				if (contratCptDO.getDatEautCcpt() != null
						&& contratCptDO.getDatEautCcpt().compareTo(dateComptable) >= 0) {

					montSoldThCcpt =
							(contratCptDO.getMontAutCcpt() + contratCptDO.getMontSoldCcpt() - contratCptDO
									.getMontBlocCcpt());
				} else {

					montSoldThCcpt = (contratCptDO.getMontSoldCcpt() - contratCptDO.getMontBlocCcpt());

				}
			} catch (Exception e) {

				montSoldThCcpt = (contratCptDO.getMontSoldCcpt() - contratCptDO.getMontBlocCcpt());
			}

			// 07. setting devise et montant
			Devise devise = new Devise();

			// 07.2 Virement en dinar
			devise.setCodDevDev(Constants.COD_DEV_DINAR);
			if (contratCptDO != null) {

				operationMoyPayCompteDO.setMontDinOmp(montVirCcpt);
				operationMoyPayCompteDO.setMontApreOmp(contratCptDO.getMontSoldCcpt()
						- (montVirCcpt + montant_commision + montant_tva));
			}

			operationMoyPayCompteDO.setMontSoldCcpt(contratCptDO.getMontSoldCcpt());
			operationMoyPayCompteDO.setDevise(devise);

			// 08. setting contrat compte
			operationMoyPayCompteDO.setContratCpt(contratCptDO);

			// 09. setting type demandeur (Titulaire, CoTitulaire, Mandataire)

			if (globalVirement.getTypePouvGvir() != null) {
				operationMoyPayCompteDO.setCodDemOmp(globalVirement.getTypePouvGvir());
			} else {
				operationMoyPayCompteDO.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);
			}

			// 10. setting info Virement /Remise

			operationMoyPayCompteDO.setGlobalVirement(globalVirement);

			// 10.1 insertion du Demandeur

			if (globalVirement.getTypePouvGvir().equals(Constants.COD_TYPE_POUVOIR_TITULAIRE)) {
				TypePiece typePieceDemandeur = new TypePiece();
				typePieceDemandeur.setCodTpceTpce(Long.valueOf(contratCptDO.getClient().getPersonne().getTypePiece()
						.getCodTpceTpce()));
				operationMoyPayCompteDO.setTypePieceDemandeur(typePieceDemandeur);
				operationMoyPayCompteDO.setNumPcedOmp(contratCptDO.getClient().getPersonne().getNumPcePers());
				operationMoyPayCompteDO.setNomNomdOmp(contratCptDO.getClient().getPersonne().getNomNomPers());
				operationMoyPayCompteDO.setNomPrndOmp(contratCptDO.getClient().getPersonne().getNomPrnPers());
			} else if (globalVirement.getTypePouvGvir().equals(Constants.COD_TYPE_POUVOIR_MANDATAIRE)) {
				if (globalVirement.getMandantPersVirements() != null
						&& globalVirement.getMandantPersVirements().size() > 0) {
					try {
						ICriteria criteria = searchEngine.createCriteria();
						IExpression expression = searchEngine.createExpression();
						criteria.add(expression.eq("mandantPersVirementId.numSeqGvir", globalVirement.getNumSeqGvir()));
						List<MandantPersVirement> list = searchEngine.find(MandantPersVirement.class, criteria);
						MandantPersVirement mandantPersVirement = list.get(0);

						MandatPersonne mandatPersonne = mandantPersVirement.getMandatPersonne();
						GetMandatPersonneByIdTrt getMandatPersonneByIdTrt = new GetMandatPersonneByIdTrt();
						mandatPersonne = (MandatPersonne) getMandatPersonneByIdTrt.exec(mandatPersonne);

						TypePiece typePieceDemandeur = new TypePiece();
						typePieceDemandeur.setCodTpceTpce(Long.valueOf(mandatPersonne.getPersonne().getTypePiece()
								.getCodTpceTpce()));
						operationMoyPayCompteDO.setTypePieceDemandeur(typePieceDemandeur);
						operationMoyPayCompteDO.setNumPcedOmp(mandatPersonne.getPersonne().getNumPcePers());
						operationMoyPayCompteDO.setNomNomdOmp(mandatPersonne.getPersonne().getNomNomPers());
						operationMoyPayCompteDO.setNomPrndOmp(mandatPersonne.getPersonne().getNomPrnPers());

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			} else if (globalVirement.getTypePouvGvir().equals(Constants.COD_TYPE_POUVOIR_COTITULAIRE)) {
				if (globalVirement.getPersCotitVirements() != null && globalVirement.getPersCotitVirements().size() > 0) {
					try {

						ICriteria criteria = searchEngine.createCriteria();
						IExpression expression = searchEngine.createExpression();
						criteria.add(expression.eq("persCotitVirementId.numSeqGvir", globalVirement.getNumSeqGvir()));
						List<PersCotitVirement> list = searchEngine.find(PersCotitVirement.class, criteria);
						PersCotitVirement persCotitVirement = list.get(0);
						Personne personne = persCotitVirement.getCoTitulaire().getPersonne();

						TypePiece typePieceDemandeur = new TypePiece();
						typePieceDemandeur.setCodTpceTpce(Long.valueOf(personne.getTypePiece().getCodTpceTpce()));
						operationMoyPayCompteDO.setTypePieceDemandeur(typePieceDemandeur);
						operationMoyPayCompteDO.setNumPcedOmp(personne.getNumPcePers());
						operationMoyPayCompteDO.setNomNomdOmp(personne.getNomNomPers());
						operationMoyPayCompteDO.setNomPrndOmp(personne.getNomPrnPers());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} else if (globalVirement.getTypePouvGvir().equals(Constants.COD_TYPE_POUVOIR_TIERS)) {
				try {

					ICriteria criteria = searchEngine.createCriteria();
					IExpression expression = searchEngine.createExpression();
					criteria.add(expression.eq("globalVirement.numSeqGvir", globalVirement.getNumSeqGvir()));
					criteria.add(expression.eq("codOperOper", Constants.COD_OPER_DEMANDE_VIREMENT));
					criteria.add(expression.eq("typPouvTvir", Constants.COD_TYPE_POUVOIR_TIERS));
					criteria.add(expression.isNotNull("personneDem.numSeqPers"));

					List<TraceOperVirement> list =
							(List<TraceOperVirement>) searchEngine.find(TraceOperVirement.class, criteria);

					if (list != null && list.size() > 0) {

						TraceOperVirement traceOperVirement = list.get(0);

						if (traceOperVirement.getPersonneDem() != null
								&& traceOperVirement.getPersonneDem().getNumSeqPers() != null) {

							TypePiece typePieceDemandeur = new TypePiece();
							typePieceDemandeur.setCodTpceTpce(Long.valueOf(traceOperVirement.getPersonneDem()
									.getTypePiece().getCodTpceTpce()));
							operationMoyPayCompteDO.setTypePieceDemandeur(typePieceDemandeur);
							operationMoyPayCompteDO.setNumPcedOmp(traceOperVirement.getPersonneDem().getNumPcePers());
							operationMoyPayCompteDO.setNomNomdOmp(traceOperVirement.getPersonneDem().getNomNomPers());
							operationMoyPayCompteDO.setNomPrndOmp(traceOperVirement.getPersonneDem().getNomPrnPers());

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// 11 Preparing data of Operation and tache

			Tache tache = new Tache();
			TacheId tacheId = new TacheId();
			tacheId.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PONCTUEL);
			tacheId.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PONCTUEL);
			tache.setOperation(operation);

			// 11. 1 Cas de Virement Emis
			if (Constants.COD_OPER_EXECUTION_VIREMENT_PONCTUEL.equals(operation.getCodOperOper())) {
				operationMoyPayCompteDO.setCodEtatOmp(Constants.COD_VALIDATION);
				tache.setTacheId(tacheId);

			}
			// 12. setting tache
			operationMoyPayCompteDO.setTache(tache);

			// 13. setting date operation moyen paiement
			Date dateOperOmp = dateComptable;

			operationMoyPayCompteDO.setDatOperOmp(dateOperOmp);

			// 14. setting date valeur moyen paiement
			Date dateValOmp = null;

			dateValOmp = formaterDate.parse(dateValueRecu);

			operationMoyPayCompteDO.setDatValOmp(dateValOmp);
			// 14.1 setting date system moyen paiement
			Date dateSysOmp = formaterDate.parse(formaterDate.format(new Date()));
			operationMoyPayCompteDO.setDatSystOmp(dateSysOmp);

			// 14.2 setting date valeur Commission moyen paiement
			Date dateValComOmp = null;
			dateValComOmp = formaterDate.parse(dateValueComRecu);

			operationMoyPayCompteDO.setDateValeurCommission(dateValComOmp);

			// 15. setting sens operation
			operationMoyPayCompteDO.setCodSensOmp(Constants.COD_SENS_DB);

			// 16. setting montant tva
			operationMoyPayCompteDO.setMontTvaOmp(montant_tva);

			// 17. Insertion dans la table DETAIL_OPERATION_MOY_PAIEMENT

			Set<DetailOperMoyPaiement> setDetOpm = new HashSet<DetailOperMoyPaiement>();
			NomencElemtCondition nomencElemtCondition = new NomencElemtCondition();
			DetailOperMoyPaiement detailOmpCommission = new DetailOperMoyPaiement();

			if (montant_commision != 0) {

				// **** A verifier **** //
				nomencElemtCondition.setCodNecdNecd(Constants.COD_NECD_NECD_715 + "");

				detailOmpCommission.setNomencElemtCondition(nomencElemtCondition);
				detailOmpCommission.setCodTypDomp(Constants.COD_TYPE_COMMISSION);
				detailOmpCommission.setMontValDomp(new Long(montant_commision));
				detailOmpCommission.setDatValDomp(DateHandler.strToDate(dateValueComRecu));
				detailOmpCommission.setOperationMoyPay(operationMoyPayCompteDO);
				setDetOpm.add(detailOmpCommission);

				operationMoyPayCompteDO.setDetailOperMoyPaiements(setDetOpm);

			}
			// 19. Insertion code ref client
			// champ obligtaoire dans la BD, valeur par defaut proposee par Chiraz CHELLY 999

			operationMoyPayCompteDO.setCodRefcOmp(globalVirement.getNumSeqGvir());
			operationMoyPayCompteDO.setCodRefbOmp(globalVirement.getNumSeqGvir());
			
			// 20. Insertion motif operation
			// valeur proposee par Chiraz CHELLY libelle operation
			operationMoyPayCompteDO.setLibMotfOmp("EXECUTION VIREMENT");

			// 02. insertion operation moyen paiement

			InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
					new InsertionOperationMoyPaySansCROTrt();
			operationMoyPayCompteDO = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPayCompteDO);

			logger.info("operationMoyPayCompteDO :" + operationMoyPayCompteDO);

			// ****************** Update Compte DO ***********//

			long montantAjouterAuSolde = 0;
			if (etatClientTaxable == true) {
				montantAjouterAuSolde = montVirCcpt + montant_commision + montant_tva;
			} else {
				montantAjouterAuSolde = montVirCcpt + montant_commision;
			}

			Context context = ContextHandler.getContext();
			CommunService communService = (CommunService) context.getBean("communService");
			ContratCptSold contratCptSold = new ContratCptSold();
			contratCptSold.setContratCpt(contratCptDO);
			contratCptSold.setSens(Constants.COD_SENS_DB);
			contratCptSold.setSolde(montantAjouterAuSolde);
			contratCptDO = (ContratCpt) communService.UpdateSoldOperationMoyPay(contratCptSold);

			// *************** Creation Cro **********//
			VirementVo objVirementVoCRO = new VirementVo();

			objVirementVoCRO.setGlobalVirement(globalVirement);
			objVirementVoCRO.setContratCpt(contratCptDO);
			objVirementVoCRO.setMontantGlobalByCB(montantGlobalVirement);
			objVirementVoCRO.setDateComptableAgence(dateComptable);
			objVirementVoCRO.setOperationMoyPay(operationMoyPayCompteDO);
			objVirementVoCRO.setEtatBenifVirementCB(virementVo.getEtatBenifVirementCB());

			// / -------------------------------------------------- ////

			// ************** Creation Enregistrement dans Trace Virement ***********//

			Long sequenceTraceVirement = virementGlobalDAO.getSequenceTraceVirement();
			TraceVirement traceVirement = new TraceVirement();

			traceVirement.setNumSeqTrace(sequenceTraceVirement);

			traceVirement.setGlobalVirement(globalVirement);
			traceVirement.setOperation(operation);
			traceVirement.setDatOperTrace(dateComptable);
			traceVirement.setDatSysTrace(new Date());
			traceVirement.setTimeTrace(formaterHeure.format(new Date()));
			traceVirement.setSens(Constants.COD_SENS_EMIS);
			traceVirement.setStructureEmettrice(globalVirement.getStructure());
			traceVirement.setMontVirTrace(montVirCcpt);
			crudService.create(traceVirement);

			// ************** FIN Creation Enregistrement dans Trace Virement ***********//

			// ********** Creation Enregistrement dans Details_Operation_Virement *********//

			Long sequenceDetailsOperationVirement = virementGlobalDAO.getSequenceDetailsOperationVirement();

			DetailsOperationVirement detailsOperationVirement = new DetailsOperationVirement();

			detailsOperationVirement.setNumSeqDovi(sequenceDetailsOperationVirement);

			detailsOperationVirement.setGlobalVirement(globalVirement);
			detailsOperationVirement.setDatOperDovi(dateComptable);

			Date datValDovi = null;
			if (dateValueRecu != null) {
				datValDovi = formaterDate.parse(dateValueRecu);
			} else {
				datValDovi = dateComptable;
			}
			detailsOperationVirement.setDatValDovi(datValDovi);

			detailsOperationVirement.setMontOpeDovi(montVirCcpt);
			detailsOperationVirement.setMontComDovi(montant_commision);
			detailsOperationVirement.setMontTvaDovi(montant_tva);
			if (dateValueComRecu != null) {
				detailsOperationVirement.setDatValcDovi(formaterDate.parse(dateValueComRecu));
			}
			detailsOperationVirement.setDatSysDovi(new Date());
			detailsOperationVirement.setNumMatrUser("9999");
			detailsOperationVirement.setCodStrcStrc(globalVirement.getStructure().getCodStrcStrc());
			detailsOperationVirement.setTache(tache);

			crudService.create(detailsOperationVirement);

			// ********** Fin Enregistrement dans Details_Operation_Virement *********//

			// **************************Update Table Heritiers en cas de succession ************//

			// **************************FIN Update Table Heritiers en cas de succession ************//

			this.setCroFlag(true);
			virementVo.setEtatInsertionCro(true);
			virementVo.setOperationMoyPay(operationMoyPayCompteDO);

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationCROExecutionVirementPonctuelTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationCROExecutionVirementPonctuelTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau CreationCROExecutionVirementPonctuelTrt : ", e);
			virementVo.setMessageValidation("Probléme dans CreationCROExecutionVirementPonctuelTrt");
			throw new RuntimeException();

		}
		return (virementVo);

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		logger.info("starting Creation CRO DO 715");

		VirementVo virementVo = (VirementVo) vo;
		GlobalVirement globalVirement = new GlobalVirement();
		OperationMoyPay operationMoyPay = new OperationMoyPay();
		operationMoyPay = virementVo.getOperationMoyPay();
		globalVirement = virementVo.getGlobalVirement();
		long montantGlobal = virementVo.getMontantGlobalByCB();

		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */

		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		String motifRefCro = "";
		if (virementVo.getEtatBenifVirementCB() != null && virementVo.getEtatBenifVirementCB().equals(Long.valueOf(0))) {
			motifRefCro = "MÊME AGENCE";
		} else if (virementVo.getEtatBenifVirementCB() != null
				&& virementVo.getEtatBenifVirementCB().equals(Long.valueOf(1))) {
			motifRefCro = "AUTRE AGENCE";
		} else if (virementVo.getEtatBenifVirementCB() != null
				&& virementVo.getEtatBenifVirementCB().equals(Long.valueOf(2))) {
			motifRefCro = "AUTRE BANQUE";
		} else if (virementVo.getEtatBenifVirementCB() != null
				&& virementVo.getEtatBenifVirementCB().equals(Long.valueOf(3))) {
			motifRefCro = "SGMT";
		}
		this.setLibRefCro("EXECUTION VIREMENT PONCTUEL " + motifRefCro);

		this.setDatValCro(operationMoyPay.getDatValOmp());
		if (operationMoyPay.getStructureInitiatrice().getCodStrcStrc() != null
				&& operationMoyPay.getStructureInitiatrice().getCodStrcStrc().longValue() != 0) {
			this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
		} else {
			this.setCodeStructInitiatrice("900");
		}
		this.setCodStrcImpt(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());
		this.setDatExecCro(new Date());
		this.setCodEtatCro(0);
		this.setCodeProduit(globalVirement.getCodPrdPrd().toString());
		this.setOperationId(operationMoyPay.getTache().getTacheId().getCodOperOper().toString());
		this.setDateOperation(operationMoyPay.getDatOperOmp());
		this.setDatValCom(operationMoyPay.getDateValeurCommission());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(operationMoyPay.getTache().getTacheId().getCodTachTach());
		this.setCodRefcOmp(operationMoyPay.getCodRefcOmp());
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.oxia.security.abc.model.Personnel user = null;
		if (obj instanceof UserDetails) {
			user = (com.oxia.security.abc.model.Personnel) obj;
		}
		this.setNumCinUser("9999");
		this.setCodTypUser("M");
		/*
		 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
		 */

		StringBuffer cro = new StringBuffer("");

		// contratClient
		cro.append("NumCptVir=");
		cro.append(globalVirement.getCompteDO() + ";");

		// Montant Global Remise
		cro.append("MNT_GLO_REM=");
		// cro.append(globalVirement.getMntGvirGvir() + ";");
		cro.append(montantGlobal + ";");

		// NBRE Global Remise
		cro.append("NBR_VIR_PALIER=");
		cro.append(Long.valueOf(virementVo.getNbreGlobalByCB()) + ";");

		// Montant Commission
		cro.append("267=");
		cro.append(montant_commision + ";");

		// Montant TVA
		cro.append("MNT_TVA_VEM=");
		cro.append(montant_tva + ";");

		// Statu Client (Taxable / Non Taxable)

		cro.append("COD_TVA_CLT=");

		if (etatClientTaxable == true) {
			cro.append(Long.valueOf(0) + ";");

		} else {
			cro.append(Long.valueOf(1) + ";");

		}
		// Numéro Remise Virement
		cro.append("COD_REM_VIR=");
		cro.append(globalVirement.getNumSeqGvir() + ";");

		this.setCroText(cro.toString());

	}

}