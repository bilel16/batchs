package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.DetailsOperationVirement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.EnvoiCtx;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.NomencElemtCondition;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.PersClient;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceVirement;
import com.bna.commun.model.TypePiece;
import com.bna.commun.service.CommunService;
import com.bna.commun.traitements.InsertionOperationMoyPaySansCROTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.ContratCptSold;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.ExonerationTvaDAO;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecommun.traitement.GetRibTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
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

public class CreationCROPositionVirementCompteDevisesTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	VirementService virementService = (VirementService) context.getBean("iVirementService");
	VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
	boolean etatBenifTaxable = false;
	long montant_commissionRecue = 0;
	long montant_tva = 0;
	String dateValueRecu = "";
	String dateValueComRecu = "";
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	Operation operation = new Operation();
	Structure structureCtx = new Structure();
	long numCtxClt = 0;
	String codRefInter = "";
	String rib_tireur = "";

	public CreationCROPositionVirementCompteDevisesTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		OperationMoyPay operationMoyPay = new OperationMoyPay();
		DetailVirement detailVirementObj = new DetailVirement();
		GlobalVirement globalVirement = new GlobalVirement();
		globalVirement = virementVo.getGlobalVirement();
		String codeBanque = "";
		Date dateComptable = new Date();
		VirementVo virementVoCB = new VirementVo();
		boolean boolValiderBenif = virementVo.isBoolValiderContratCptBENIF();
		ContratCpt contratCptBenif = new ContratCpt();
		try {

			operation = virementVo.getOperation();
			detailVirementObj = virementVo.getDetailVirement();
			dateComptable = virementVo.getDateComptableAgence();
			// *************** Rechercher ContratCpt Bénéficiaire *********//
			if (boolValiderBenif == true) {

				ContratCptId contratCptId = new ContratCptId();

				if (detailVirementObj.getRibBenDetv().substring(0, 2).equals("03")) {
					String codStrcCpt = detailVirementObj.getRibBenDetv().substring(5, 8);
					String codPrdCpt = detailVirementObj.getRibBenDetv().substring(8, 12);
					String numCptCpt = detailVirementObj.getRibBenDetv().substring(12, 18);

					contratCptId.setCodStrcStrc(new Long(codStrcCpt));
					contratCptId.setCodPrdPrd(new Long(codPrdCpt));
					contratCptId.setNumCcptCcpt(new Long(numCptCpt));

					contratCptBenif.setContratCptId(contratCptId);
				}

				ISearchEngine searchEngine =
						(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");

				contratCptBenif = (ContratCpt) searchEngine.get(ContratCpt.class, contratCptBenif.getContratCptId());

				// ******************* Verifier etat contentieux du client ********************//

				if (contratCptBenif.getClient() != null && contratCptBenif.getClient().getNumCtxClt() != null
						&& contratCptBenif.getClient().getNumCtxClt() != 0
						&& contratCptBenif.getClient().getCodEtatClt().equalsIgnoreCase("C")) {

					numCtxClt = contratCptBenif.getClient().getNumCtxClt();
					structureCtx = contratCptBenif.getClient().getStructure();
				}

				if (contratCptBenif.getClient() != null && contratCptBenif.getClient().getPersonne() != null
						&& contratCptBenif.getClient().getPersonne().getTypePiece() != null
						&& contratCptBenif.getClient().getPersonne().getTypePiece().getCodTpceTpce() != null) {

					virementVoCB
							.setTypePiece(contratCptBenif.getClient().getPersonne().getTypePiece().getCodTpceTpce());
					virementVoCB.setNumPcePers(contratCptBenif.getClient().getPersonne().getNumPcePers());

				}
				// /-------------------- Condition de Banque Bénéficiaire ---------------------------///

				ArrayList<String> palierChar1 = new ArrayList<String>();
				boolean etatVirementMemeAgence = false;
				boolean etatVirementAutreAgence = false;
				boolean etatVirementAutreBanque = false;
				Operation operationCB = new Operation();
				operationCB.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT_COMPTE_DEVISES);

				ContratCpt contratCptDO = new ContratCpt();
				if (globalVirement != null && globalVirement.getContratCpt() != null
						&& globalVirement.getContratCpt().getContratCptId() != null) {
					contratCptDO =
							(ContratCpt) searchEngine.get(ContratCpt.class, globalVirement.getContratCpt()
									.getContratCptId());

					if (contratCptDO != null && contratCptDO.getClient() != null
							&& contratCptDO.getClient().getNumSeqPers() != null) {

						if (contratCptDO.getClient().getNumSeqPers()
								.equals(contratCptBenif.getClient().getNumSeqPers())) {
							palierChar1.add("46");
						} else {
							palierChar1.add("47");
						}
					}

				}

				if (detailVirementObj.getRibBenDetv().substring(0, 2).equals("03")) {
					if (new Long(detailVirementObj.getRibBenDetv().substring(5, 8)).longValue() == detailVirementObj
							.getGlobalVirement().getStructure().getCodStrcStrc().longValue()) {

						etatVirementMemeAgence = true;

					} else {
						etatVirementAutreAgence = true;
					}

				} else {
					etatVirementAutreBanque = true;
				}

				if (etatVirementMemeAgence == true) {
					palierChar1.add("41");
				} else if (etatVirementAutreAgence == true) {
					palierChar1.add("42");
				} else if (etatVirementAutreBanque == true) {
					palierChar1.add("43");
					palierChar1.add("47");
				}
				virementVoCB.setListePalierCaractere(palierChar1);
				virementVoCB.setDateComptableAgence(dateComptable);
				virementVoCB.setDetailVirement(detailVirementObj);
				virementVoCB.setOperation(operationCB);
				virementVoCB.setMontant_virement(detailVirementObj.getMntDetvDetv());
				virementVoCB = getConditionDeBanque(virementVoCB);
				montant_commissionRecue = new Long(StrHandler.strToMnt(virementVoCB.getCommissionRecue()));
				montant_tva = new Long(StrHandler.strToMnt(virementVoCB.getTvaRecue()));
				dateValueRecu = virementVoCB.getValueDateRecue();
				dateValueComRecu = virementVoCB.getValueDateComRecue();

				// *********Caracteristique Taxable du client ********//

				ExonerationTvaDAO exonerationTvaDAO = new ExonerationTvaDAO();
				ParamRechercheOpposition param = new ParamRechercheOpposition();
				param.setTypPceDemd(contratCptBenif.getClient().getPersonne().getTypePiece().getCodTpceTpce());
				param.setNumPceDemd(contratCptBenif.getClient().getPersonne().getNumPcePers());
				param.setDateDebutConsult(dateComptable);

				PrimitiveVO res = (PrimitiveVO) exonerationTvaDAO.isClientExonereTVA(param);

				if (res.isVBool() == true) {
					etatBenifTaxable = false;
				} else {
					etatBenifTaxable = true;
				}

				if (etatBenifTaxable == false) {
					montant_tva = 0L;

				}
				// / ------------------Operation Moyen de Paiement ---------------------------- ///

				operationMoyPay.setNumRibOmp(detailVirementObj.getRibBenDetv()); // / Rib beneficaire

				// 00. setting obj operationMoyPay
				operationMoyPay.setLibObjOpOmp("POSITION VIR SUR CPTE DEVISES OU DINARS CONVERT");

				// 01. setting personnel initiateur et valideur
				Personnel personnelInit = new Personnel();
				personnelInit.setNumMatrUser("9999");
				operationMoyPay.setPersonnelInitiateur(personnelInit);
				operationMoyPay.setPersonnelValideur(personnelInit);

				// 02. setting structure initiatrice
				Structure structureInit = new Structure();
				structureInit.setCodStrcStrc(detailVirementObj.getGlobalVirement().getStructure().getCodStrcStrc());
				operationMoyPay.setStructureInitiatrice(structureInit);

				// 03. setting structure receptrice
				String codstructureReceptrice = "";

				codeBanque = detailVirementObj.getRibBenDetv().substring(0, 2);
				if (codeBanque.equals("03")) {
					codstructureReceptrice = detailVirementObj.getRibBenDetv().substring(5, 8);
				} else {
					codstructureReceptrice =
							detailVirementObj.getGlobalVirement().getStructure().getCodStrcStrc().toString();
				}

				Structure structureRecep = new Structure();
				structureRecep.setCodStrcStrc(new Long(codstructureReceptrice));
				operationMoyPay.setStructureReceptrice(structureRecep);

				// 05. getting montant a retirer
				Long montVirCcpt = detailVirementObj.getMntDetvDetv();

				// 06. getting provision
				Long montSoldThCcpt = 0L;
				try {
					if (contratCptBenif.getDatEautCcpt() != null
							&& contratCptBenif.getDatEautCcpt().compareTo(dateComptable) >= 0) {

						montSoldThCcpt =
								(contratCptBenif.getMontAutCcpt() + contratCptBenif.getMontSoldCcpt() - contratCptBenif
										.getMontBlocCcpt());
					} else {

						montSoldThCcpt = (contratCptBenif.getMontSoldCcpt() - contratCptBenif.getMontBlocCcpt());

					}
				} catch (Exception e) {

					montSoldThCcpt = (contratCptBenif.getMontSoldCcpt() - contratCptBenif.getMontBlocCcpt());
				}

				// 07. setting devise et montant
				Devise devise = new Devise();

				// 07.2 Virement en dinar
				devise.setCodDevDev(Constants.COD_DEV_DINAR);

				if (contratCptBenif != null) {
					if (numCtxClt == 0) {
						operationMoyPay.setMontDinOmp(montVirCcpt);
						operationMoyPay.setMontApreOmp(contratCptBenif.getMontSoldCcpt()
								+ (montVirCcpt - montant_commissionRecue - montant_tva));
					} else {
						// ***Cas de client CTX *************//

						operationMoyPay.setMontDinOmp(null);
						operationMoyPay.setMontApreOmp(contratCptBenif.getMontSoldCcpt());
						montant_tva = 0L;

					}
				}

				operationMoyPay.setMontSoldCcpt(contratCptBenif.getMontSoldCcpt());
				operationMoyPay.setDevise(devise);

				// 08. setting contrat compte
				operationMoyPay.setContratCpt(contratCptBenif);

				// 09. setting type demandeur (Titulaire, CoTitulaire, Mandataire)

				operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

				// 10. setting info Virement
				operationMoyPay.setDetailVirement(detailVirementObj);

				// 10.1 insertion du Donneur D'ordre

				TypePiece typePieceDemandeur = new TypePiece();
				typePieceDemandeur.setCodTpceTpce(Long.valueOf(detailVirementObj.getGlobalVirement().getContratCpt()
						.getClient().getPersonne().getTypePiece().getCodTpceTpce()));
				operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
				operationMoyPay.setNumPcedOmp(detailVirementObj.getGlobalVirement().getContratCpt().getClient()
						.getPersonne().getNumPcePers());
				operationMoyPay.setNomNomdOmp(detailVirementObj.getGlobalVirement().getContratCpt().getClient()
						.getPersonne().getNomNomPers());
				operationMoyPay.setNomPrndOmp(detailVirementObj.getGlobalVirement().getContratCpt().getClient()
						.getPersonne().getNomPrnPers());

				PrimitiveVO primitiveVO = new PrimitiveVO();
				GetRibTrt getRibTrt = new GetRibTrt();

				primitiveVO = (PrimitiveVO) getRibTrt.exec(detailVirementObj.getGlobalVirement().getContratCpt());
				rib_tireur = primitiveVO.getVString();
				operationMoyPay.setNumRibOmp(rib_tireur);

				// 10.2 insertion du Benificiaire
				operationMoyPay.setNomNombOmp(detailVirementObj.getNomBenDetv());

				// 11 Preparing data of Operation and tache
				Operation oper = new Operation();
				oper.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT_COMPTE_DEVISES);
				Tache tache = new Tache();
				tache.setOperation(oper);
				TacheId tacheId = new TacheId();
				tacheId.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT_COMPTE_DEVISES);

				// 11. 1 Cas de Virement Emis
				if (Constants.COD_OPER_POSITION_VIREMENT_COMPTE_DEVISES.equals(oper.getCodOperOper())) {

					operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);

					tacheId.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PONCTUEL);
					tache.setTacheId(tacheId);

				}
				// 12. setting tache
				operationMoyPay.setTache(tache);

				// 13. setting date operation moyen paiement
				Date dateOperOmp = dateComptable;

				operationMoyPay.setDatOperOmp(dateOperOmp);

				// 14. setting date valeur moyen paiement
				Date dateValOmp = null;
				if (dateValueRecu != null && dateValueRecu.length() > 0) {
					dateValOmp = formaterDate.parse(dateValueRecu);
				} else {
					dateValOmp = dateComptable;
				}
				operationMoyPay.setDatValOmp(dateValOmp);

				// 14.1 setting date system moyen paiement
				Date dateSysOmp = formaterDate.parse(formaterDate.format(new Date()));
				operationMoyPay.setDatSystOmp(dateSysOmp);

				// 14.2 setting date valeur Commission moyen paiement
				Date dateValComOmp = null;
				if (dateValueComRecu != null && dateValueComRecu.length() > 0) {
					dateValComOmp = formaterDate.parse(dateValueComRecu);
				} else {
					dateValComOmp = dateComptable;
				}
				operationMoyPay.setDateValeurCommission(dateValComOmp);

				// 15. setting sens operation
				operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);

				// 16. setting montant tva
				operationMoyPay.setMontTvaOmp(montant_tva);

				// 17. Insertion dans la table DETAIL_OPERATION_MOY_PAIEMENT

				Set<DetailOperMoyPaiement> setDetOpm = new HashSet<DetailOperMoyPaiement>();
				NomencElemtCondition nomencElemtCondition = new NomencElemtCondition();
				DetailOperMoyPaiement detailOmpCommission = new DetailOperMoyPaiement();

				if (montant_commissionRecue != 0 && numCtxClt == 0) {

					// **** A verifier **** //
					nomencElemtCondition.setCodNecdNecd(Constants.COD_NECD_NECD_1064 + "");

					detailOmpCommission.setNomencElemtCondition(nomencElemtCondition);
					detailOmpCommission.setCodTypDomp(Constants.COD_TYPE_COMMISSION);
					detailOmpCommission.setMontValDomp(new Long(montant_commissionRecue));
					detailOmpCommission.setDatValDomp(DateHandler.strToDate(virementVoCB.getValueDateComRecue()));
					detailOmpCommission.setOperationMoyPay(operationMoyPay);
					setDetOpm.add(detailOmpCommission);

					operationMoyPay.setDetailOperMoyPaiements(setDetOpm);

				}

				// 19. Insertion code ref client
				// champ obligtaoire dans la BD, valeur par defaut proposee par Chiraz CHELLY 999

				operationMoyPay.setCodRefcOmp(detailVirementObj.getDetailVirementId().getNumSeqGvir());
				operationMoyPay.setCodRefbOmp(detailVirementObj.getDetailVirementId().getNumSeqGvir());

				// 20. Insertion motif operation

				String libObjOpOmp = "VIREMENTS DEPUIS " + contratCptDO.getClient().getPersonne().getRelation();

				if (libObjOpOmp.length() > 50) {
					libObjOpOmp = libObjOpOmp.substring(0, 49);
				}
				operationMoyPay.setLibObjOpOmp(libObjOpOmp);

				// 02. insertion operation moyen paiement

				InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
						new InsertionOperationMoyPaySansCROTrt();
				operationMoyPay = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPay);

				logger.info("operationMoyPay :" + operationMoyPay);

				// *************** Update Compte Bénéficiaire ***********//
				if (numCtxClt == 0) {
					long montantAjouterAuSolde = 0;
					if (etatBenifTaxable == true) {
						montantAjouterAuSolde = montVirCcpt - montant_commissionRecue - montant_tva;
					} else {
						montantAjouterAuSolde = montVirCcpt - montant_commissionRecue;
					}

					Context context = ContextHandler.getContext();
					CommunService communService = (CommunService) context.getBean("communService");
					ContratCptSold contratCptSold = new ContratCptSold();
					contratCptSold.setContratCpt(contratCptBenif);
					contratCptSold.setSens(Constants.COD_SENS_CR);
					contratCptSold.setSolde(montantAjouterAuSolde);
					contratCptBenif = (ContratCpt) communService.UpdateSoldOperationMoyPay(contratCptSold);

				}
				virementVo.setOperationMoyPay(operationMoyPay);
				virementVo.setNumCtxClt(numCtxClt);
				virementVo.setStructureCTX(structureCtx);

				// ********** Creation Enregistrement dans Details_Operation_Virement *********//

				Long sequenceDetailsOperationVirement = virementGlobalDAO.getSequenceDetailsOperationVirement();

				DetailsOperationVirement detailsOperationVirement = new DetailsOperationVirement();

				detailsOperationVirement.setNumSeqDovi(sequenceDetailsOperationVirement);

				detailsOperationVirement.setDetailVirement(detailVirementObj);
				detailsOperationVirement.setDatOperDovi(dateComptable);

				Date datValDovi = null;
				if (dateValueRecu != null) {
					datValDovi = formaterDate.parse(dateValueRecu);
				} else {
					datValDovi = dateComptable;
				}
				detailsOperationVirement.setDatValDovi(datValDovi);

				detailsOperationVirement.setMontOpeDovi(montVirCcpt);
				detailsOperationVirement.setMontComDovi(montant_commissionRecue);
				detailsOperationVirement.setMontTvaDovi(montant_tva);
				detailsOperationVirement.setDatValcDovi(formaterDate.parse(dateValueComRecu));
				detailsOperationVirement.setDatSysDovi(new Date());
				detailsOperationVirement.setNumMatrUser("9999");
				detailsOperationVirement.setCodStrcStrc(detailVirementObj.getGlobalVirement().getStructure()
						.getCodStrcStrc());
				detailsOperationVirement.setTache(tache);

				crudService.create(detailsOperationVirement);

				// ********** Fin Enregistrement dans Details_Operation_Virement *********//

				// ************** Creation Enregistrement dans Trace Virement ***********//

				Long sequenceTraceVirement = virementGlobalDAO.getSequenceTraceVirement();
				TraceVirement traceVirement = new TraceVirement();

				traceVirement.setNumSeqTrace(sequenceTraceVirement);

				traceVirement.setDetailVirement(detailVirementObj);
				traceVirement.setOperation(operation);
				traceVirement.setDatOperTrace(dateComptable);
				traceVirement.setDatSysTrace(new Date());
				traceVirement.setTimeTrace(formaterHeure.format(new Date()));
				traceVirement.setSens(Constants.COD_SENS_EMIS);
				traceVirement.setStructureEmettrice(detailVirementObj.getGlobalVirement().getStructure());
				String codstructureRecep = detailVirementObj.getRibBenDetv().substring(5, 8);
				Structure structureRecp = new Structure();
				structureRecp.setCodStrcStrc(new Long(codstructureRecep));
				traceVirement.setStructureReceptrice(structureRecp);
				traceVirement.setMontVirTrace(montVirCcpt);
				crudService.create(traceVirement);

				// **************************Update Table Heritiers en cas de succession ************//
				if (globalVirement != null && globalVirement.getNumSeqGvir() != null
						&& globalVirement.getBoolSuccGvir() != null
						&& globalVirement.getBoolSuccGvir().longValue() == 1) {

					PersClient heritiers = new PersClient();

					PersonneStrc personneStrc = new PersonneStrc();
					if (detailVirementObj.getTypePiece() != null
							&& detailVirementObj.getTypePiece().getCodTpceTpce() != null) {
						personneStrc.setCodTpceTpce(detailVirementObj.getTypePiece().getCodTpceTpce());
					}
					personneStrc.setNumPcePers(detailVirementObj.getNumPcePers());
					GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
					Personne personne = new Personne();
					personne = (Personne) getPersonneCmd.execute(personneStrc);
					if (personne != null && personne.getNumSeqPers() != null) {
						ICriteria criteriaHer = searchEngine.createCriteria();
						IExpression expressionHer = searchEngine.createExpression();

						criteriaHer.add(expressionHer.eq("persClientId.numSeqCli", globalVirement.getContratCpt()
								.getClient().getNumSeqPers()));
						criteriaHer.add(expressionHer.eq("persClientId.numSeqPers", personne.getNumSeqPers()));
						criteriaHer.add(expressionHer.eq("persClientId.codQualQual",
								Long.valueOf(Constants.COD_QUAL_HERITIER)));

						List<PersClient> listHeritiers = searchEngine.find(PersClient.class, criteriaHer);

						if (listHeritiers != null && listHeritiers.size() > 0) {
							heritiers = (PersClient) listHeritiers.get(0);

							heritiers.setNumOperOmp(operationMoyPay.getNumOperOmp());

							crudService.update(heritiers);
						}

					}
				}
				// **************************FIN Update Table Heritiers en cas de succession ************//

				// **************************Insertion Dans la Table Envoi_CTX en cas du contentieux ************//

				if (numCtxClt != 0) {
					long numSeqEnvoi = virementGlobalDAO.getSequenceEnvoiCTX();
					EnvoiCtx envoiCtx = new EnvoiCtx();
					envoiCtx.setNumSeqEctx(numSeqEnvoi);
					envoiCtx.setDatOperEctx(dateComptable);
					envoiCtx.setStructureCTX(structureCtx);
					envoiCtx.setNumCtxEctx(numCtxClt + "");
					envoiCtx.setNomNomEctx(detailVirementObj.getNomBenDetv());
					envoiCtx.setMntOperEctx(detailVirementObj.getMntDetvDetv());
					envoiCtx.setOperation(operation);
					envoiCtx.setStructureInitiatrice(globalVirement.getStructure());
					envoiCtx.setDatValEctx(formaterDate.parse(dateValueRecu));
					GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
					codRefInter =
							generateReferenceInterSiege.getRISWithUpdate(
									globalVirement.getStructure().getCodStrcStrc(), dateComptable);
					envoiCtx.setCodRefiEctx(codRefInter);
					envoiCtx.setNumOperOmp(operationMoyPay.getNumOperOmp());
					if (contratCptBenif != null && contratCptBenif.getContratCptId() != null) {
						envoiCtx.setContratCpt(contratCptBenif);
					}
					if (globalVirement != null && globalVirement.getNumSeqGvir() != null) {
						envoiCtx.setGlobalVirement(globalVirement);
					}
					crudService.create(envoiCtx);

				}

				// **************************Fin Insertion Dans la Table Envoi_CTX en cas du contentieux************//
				this.setCroFlag(true);
				virementVo.setEtatInsertionCro(true);
			}

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreationCROPositionVirementCompteDevisesTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("CreationCROPositionVirementCompteDevisesTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau CreationCROPositionVirementCompteDevisesTrt : ", e);
			virementVo.setMessageValidation("Probléme dans CreationCROPositionVirementCompteDevisesTrt");
			throw new RuntimeException();

		}
		return (virementVo);

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		logger.info("starting CreationCROPositionVirementCompteDevisesTrt method ");

		VirementVo virementVo = (VirementVo) vo;
		DetailVirement detailVirementObj = new DetailVirement();
		detailVirementObj = virementVo.getDetailVirement();
		OperationMoyPay operationMoyPay = new OperationMoyPay();
		operationMoyPay = virementVo.getOperationMoyPay();
		Operation operation = new Operation();
		operation = virementVo.getOperation();

		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_VIREMENT_ADT);
		// produit = virementVo.getProduit();
		boolean etatBenifMemeAgence = virementVo.isEtatBenifMemeAgence();
		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */

		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		this.setLibRefCro("POSITION VIREMENTS SUR COMPTE EN DEVISES OU EN DINARS CONVERTIBLES");

		if (operationMoyPay.getStructureInitiatrice().getCodStrcStrc() != null
				&& operationMoyPay.getStructureInitiatrice().getCodStrcStrc().longValue() != 0) {
			this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
		} else {
			this.setCodeStructInitiatrice("900");
		}
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
		this.setCodTachTach(operationMoyPay.getTache().getTacheId().getCodTachTach());
		this.setCodRefcOmp(operationMoyPay.getCodRefcOmp());
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.oxia.security.abc.model.Personnel user = null;
		if (obj instanceof UserDetails) {
			user = (com.oxia.security.abc.model.Personnel) obj;
		}
		this.setNumCinUser("9999");
		this.setCodTypUser("M");
		this.setCodRefInter(codRefInter);
		/*
		 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
		 */

		StringBuffer cro = new StringBuffer("");

		// *** Montant brut du virement reçu par client ****//
		cro.append("MNT_BRUT_VIR_CLT=");
		cro.append(detailVirementObj.getMntDetvDetv() + ";");

		// *** Montant de la commission sur virement reçu ****//
		cro.append("MNT_COM_VIR_CLT=");
		if (virementVo.getNumCtxClt() == 0) {
			cro.append(montant_commissionRecue + ";");
		} else {
			cro.append(new Long(0) + ";");
		}
		// *** Montant de la TVA ****//
		cro.append("MNT_TVA_VIR_CLT=");
		if (virementVo.getNumCtxClt() == 0) {
			cro.append(montant_tva + ";");
		} else {
			cro.append(new Long(0) + ";");
		}
		// *** Montant brut de la commission ****//
		cro.append("MNT_BRUT_COM=");
		long MNT_BRUT_COM = 0;
		if (virementVo.getNumCtxClt() == 0) {
			if (etatBenifTaxable == true) {
				MNT_BRUT_COM = detailVirementObj.getMntDetvDetv().longValue() + montant_commissionRecue;
			} else {
				MNT_BRUT_COM = detailVirementObj.getMntDetvDetv().longValue() + montant_commissionRecue + montant_tva;
			}
		} else {
			MNT_BRUT_COM = detailVirementObj.getMntDetvDetv().longValue();
		}
		cro.append(MNT_BRUT_COM + ";");

		// *** Compte client bénéficiaire ****//
		cro.append("Numcptben=");
		String numCptBenif = detailVirementObj.getRibBenDetv().substring(5, 18);
		cro.append(numCptBenif + ";");

		// *** Etat du compte bénéficiaire ****//
		cro.append("ETAT_CPT=");
		if (virementVo.getNumCtxClt() == 0) {
			cro.append(new Long(2) + ";");
		} else {
			cro.append(new Long(6) + ";");
		}
		// *** Type de compte ****//
		cro.append("TYPE_CPT=");
		boolean trouveDinarsConvertibles = false;
		boolean trouveDevises = false;
		for (int i = 0; i < Constants.listCompteEnDinarsConvertibles.length; i++) {
			if (Long.valueOf(Constants.listCompteEnDinarsConvertibles[i]).equals(
					Long.valueOf(detailVirementObj.getRibBenDetv().substring(8, 12)))) {
				trouveDinarsConvertibles = true;
			}
		}

		for (int i = 0; i < Constants.listCompteEnDevises.length; i++) {
			if (Long.valueOf(Constants.listCompteEnDevises[i]).equals(
					Long.valueOf(detailVirementObj.getRibBenDetv().substring(8, 12)))) {
				trouveDevises = true;
			}
		}
		if (trouveDinarsConvertibles == true) {
			cro.append(new Long(2) + ";");
		} else if (trouveDevises == true) {
			cro.append(new Long(3) + ";");
		} else {
			cro.append(new Long(1) + ";");
		}

		// *** Statu client taxable ****//
		cro.append("COD_TVA_CLT=");
		if (etatBenifTaxable == true) {
			cro.append(new Long(0) + ";");
		} else {
			cro.append(new Long(1) + ";");
		}

		// *** Montant Cours BBE brut du virement reçu ****//
		cro.append("NMNT_BRUT_VIR_CLI=");
		cro.append(new Long(0) + ";");

		// *** Code devise du compte ****//
		cro.append("COD_DEV_CPT=");
		cro.append(operationMoyPay.getDevise().getCodDevDev() + ";");

		// *** Taux variable BBE du jour ****//
		cro.append("TAUX_VAR_BBE=");
		cro.append(new Long(0) + ";");

		// *** Taux fixe BBE de l’année ****//
		cro.append("TAUX_FIX_BBE=");
		cro.append(new Long(0) + ";");

		// *** Montant du virement en devise ****//
		cro.append("MNT_DEV_VIR=");
		cro.append(new Long(0) + ";");

		// *** Référence Inter siège 9101 ****//
		cro.append("Refis9101=");
		cro.append(new Long(0) + ";");

		// *** Référence Inter siège 9102 ****//
		cro.append("Refis9102=");
		cro.append(new Long(0) + ";");

		// *** Nom donneur d’ordre ****//
		cro.append("donneur_ordre=");
		cro.append(detailVirementObj.getGlobalVirement().getContratCpt().getNomIntiCcpt() + ";");

		// *** Numéro virement****//
		cro.append("num_vir=");
		cro.append(detailVirementObj.getDetailVirementId().getNumSeqGvir() + ";");

		// *** Rib Bénéficiaire****//
		cro.append("rib_benef=");
		cro.append(detailVirementObj.getRibBenDetv() + ";");

		// *** Rib Tireur ****//
		cro.append("rib_tireur=");
		cro.append(rib_tireur + ";");

		// *** Motif virement ****//
		cro.append("motif_vir=");
		cro.append(detailVirementObj.getMotiDetvDetv() + ";");

		// Etat du bénéficiaire par rapport au donneur d'ordre (même agence ou agence différente)
		cro.append("ETAT_STRC_BENIF=");
		if (etatBenifMemeAgence == true) {

			cro.append(new Long(0) + ";");

		} else {

			cro.append(new Long(1) + ";");
		}

		// Code structure receptrice
		cro.append("cod_strc_recep=");
		if (virementVo.getNumCtxClt() != 0) {
			if (virementVo.getStructureCTX() != null && virementVo.getStructureCTX().getCodStrcStrc() != null) {
				cro.append(virementVo.getStructureCTX().getCodStrcStrc().longValue() + ";");
			}

		} else {
			cro.append(new Long(detailVirementObj.getRibBenDetv().substring(5, 8)) + ";");
		}

		// Numéro Remise Virement
		cro.append("COD_REM_VIR=");
		cro.append(detailVirementObj.getDetailVirementId().getNumSeqGvir() + ";");

		this.setCroText(cro.toString());
	}

	public VirementVo getConditionDeBanque(VirementVo virementVoCB) {

		TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
		DetailVirement detailVirement = new DetailVirement();

		detailVirement = virementVoCB.getDetailVirement();
		// /-----------------------------////
		Operation operation = new Operation();
		operation.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT_COMPTE_DEVISES);

		ContratCpt contratCpt = new ContratCpt();

		ContratCptId contratCptId = new ContratCptId();

		if (detailVirement.getRibBenDetv().substring(0, 2).equals("03")) {
			String codStrcCpt = detailVirement.getRibBenDetv().substring(5, 8);
			String codPrdCpt = detailVirement.getRibBenDetv().substring(8, 12);
			String numCptCpt = detailVirement.getRibBenDetv().substring(12, 18);

			contratCptId.setCodStrcStrc(new Long(codStrcCpt));
			contratCptId.setCodPrdPrd(new Long(codPrdCpt));
			contratCptId.setNumCcptCcpt(new Long(numCptCpt));

			contratCpt.setContratCptId(contratCptId);
		}

		String montant = null;
		montant = detailVirement.getMntDetvDetv().longValue() + "";

		// ParamAgence paramAgence = null;
		String tvaRecue = "";
		String tva = "";
		String commissionRecue = "";
		String valueDateRecue = "";
		String valueDateComRecue = "";
		// /----------------------------///

		List<String> listePalierCaractere = virementVoCB.getListePalierCaractere();
		// /----------------------------///

		try {

			if (listePalierCaractere != null && listePalierCaractere.size() > 0) {
				// ArrayList palierChar1 = new ArrayList();
				// palierChar1.add("26");
				traitementConditionBanque.setPalierChar(new ArrayList<String>(listePalierCaractere));
			}

			traitementConditionBanque.setCodOperOper(operation.getCodOperOper().toString());
			traitementConditionBanque.setCodPrdPrd(contratCpt.getContratCptId().getCodPrdPrd() + "");
			traitementConditionBanque.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(contratCpt.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt().toString());

			traitementConditionBanque.setMontant(montant);

			traitementConditionBanque.setDateReference(DateHandler.dateToStr(virementVoCB.getDateComptableAgence()));

			if (virementVoCB.getTypePiece() != null && virementVoCB.getNumPcePers() != null) {

				traitementConditionBanque.setCodTpceTpce(virementVoCB.getTypePiece() + "");
				traitementConditionBanque.setNumPcePers(virementVoCB.getNumPcePers());
			}

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

}