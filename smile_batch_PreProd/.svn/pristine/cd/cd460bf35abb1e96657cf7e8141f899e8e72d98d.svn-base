package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.Cheque;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Devise;
import com.bna.commun.model.MouvementCompensation;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.InsertionOperationMoyPaySansCROTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecommun.traitement.GetOperationTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementChequeVo;
import com.bna.smile.model.traitementCompensationRecu.dao.RejetDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.Error;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

// Referenced classes of package com.bna.smile.model.domainecompensation.gestionrejet.traitement:
// DeblocageChequeTrt

public class ReglementChequeTrt extends Traitement {

	Context context;
	ISearchEngine searchEngine;
	CRUDservice crudService;
	IExpression expression;
	ICriteria criteria;
	boolean etatClientTaxable;
	private ReglementChequeVo reglementChequeVo;
	SimpleDateFormat formaterDate;
	SimpleDateFormat formaterHeure;
	RejetDAO rejetDao;
	SimpleDateFormat formaterDateCro;

	public ReglementChequeTrt() {
		context = ContextHandler.getContext();
		searchEngine = (SearchEngine) context.getBean("searchEngine");
		crudService = (CRUDservice) context.getBean("crudservice");
		expression = searchEngine.createExpression();
		criteria = searchEngine.createCriteria();
		etatClientTaxable = false;
		reglementChequeVo = new ReglementChequeVo();
		formaterDate = new SimpleDateFormat("dd/MM/yyyy");
		formaterHeure = new SimpleDateFormat("HH:mm:ss");
		rejetDao = (RejetDAO) context.getBean("rejetDAO");
		formaterDateCro = new SimpleDateFormat("yyyyMMdd");
	}

	private void operationReglement(Long montant) throws IllegalAccessException, InvocationTargetException {
		OperationMoyPay operationMoyPay = new OperationMoyPay();
		OperationMoyPay operCredit = null;
		OperationMoyPay operDebit = null;
		ContratCpt contratCptTireur = reglementChequeVo.getContratCpt();
		contratCptTireur =
				(ContratCpt) searchEngine.loadForUpdate(ContratCpt.class, contratCptTireur.getContratCptId());
		String valueDateRecue = "";
		String valueDateComRecue = "";
		
		TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
		ArrayList palierChar = new ArrayList();

		 palierChar.add(104);
		traitementConditionBanque.setPalierChar(palierChar);
		traitementConditionBanque
				.setCodOperOper((new StringBuilder()).append(reglementChequeVo.getCodeOperation()).toString());
		traitementConditionBanque.setCodStrcStrc(
				(new StringBuilder()).append(contratCptTireur.getContratCptId().getCodStrcStrc()).toString());
		traitementConditionBanque.setCodPrdCpt(
				(new StringBuilder()).append(contratCptTireur.getContratCptId().getCodPrdPrd()).toString());
		traitementConditionBanque.setCodPrdPrd(
				(new StringBuilder()).append(contratCptTireur.getContratCptId().getCodPrdPrd()).toString());
		traitementConditionBanque.setNumCcptCcpt(
				(new StringBuilder()).append(contratCptTireur.getContratCptId().getNumCcptCcpt()).toString());
		traitementConditionBanque.setDateReference(formaterDate.format(reglementChequeVo.getDateComptable()));
		traitementConditionBanque.getCB();
		valueDateRecue = traitementConditionBanque.getDatevaleur();
		valueDateComRecue = traitementConditionBanque.getDatevaleurComm();
		if (valueDateRecue != null && valueDateRecue.equals("NAN")) {
			valueDateRecue = reglementChequeVo.getParamAgence().getDateComptable();
		}
		if (valueDateComRecue != null && valueDateComRecue.equals("NAN")) {
			valueDateComRecue = null;
		}
		if (reglementChequeVo.getMontVertUtil() != null && reglementChequeVo.getMontVertUtil().longValue() > 0L) {
			System.out.println((new StringBuilder("Paiement à partir du compte vert:"))
					.append(reglementChequeVo.getMontVertUtil()).toString());
			criteria = searchEngine.createCriteria();
			expression = searchEngine.createExpression();
			VirementService virementService = (VirementService) context.getBean("iVirementService");
			Structure strc =
					(Structure) searchEngine.get(Structure.class, reglementChequeVo.getParamAgence().getCodStrcStrc());
			criteria.add(expression.eq("contratCptId.numCcptCcpt",
					reglementChequeVo.getContratCpt().getContratCptId().getNumCcptCcpt()));
			criteria.add(expression.eq("contratCptId.codStrcStrc",
					reglementChequeVo.getContratCpt().getContratCptId().getCodStrcStrc()));
			criteria.add(expression.eq("contratCptId.codPrdPrd", Constants.COD_COMPTE_VERT));
			criteria.add(expression.eq("codEtatCcpt", "V"));
			List listeVert = searchEngine.find(ContratCpt.class, criteria);
			ContratCpt cptV = (ContratCpt) listeVert.get(0);
			VirementVo virementVoAlimentationCompteDepot = new VirementVo();
			virementVoAlimentationCompteDepot.setContratCptCompteVert(cptV);
			virementVoAlimentationCompteDepot
					.setMntAlimentationCompteDepot(reglementChequeVo.getMontVertUtil().longValue());
			virementVoAlimentationCompteDepot.setContratCptCompteDepot(reglementChequeVo.getContratCpt());
			virementVoAlimentationCompteDepot.setGlobalVirement(null);
			virementVoAlimentationCompteDepot.setStructure(strc);
			virementVoAlimentationCompteDepot.setParamAgence(reglementChequeVo.getParamAgence());
			virementVoAlimentationCompteDepot =
					(VirementVo) virementService.alimenterCompteDepot(virementVoAlimentationCompteDepot);
			HibernateTemplate hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
			hibernateTemplate.getSessionFactory().getCurrentSession().evict(reglementChequeVo.getContratCpt());
			contratCptTireur = (ContratCpt) hibernateTemplate.get(ContratCpt.class,
					reglementChequeVo.getContratCpt().getContratCptId());
			criteria = searchEngine.createCriteria();
			expression = searchEngine.createExpression();
		}
		operationMoyPay.setLibObjOpOmp("REGLEMENT CHEQUE");
		Personnel personnelInit = new Personnel();
		personnelInit.setNumMatrUser(reglementChequeVo.getParamAgence().getNumMatrUser());
		operationMoyPay.setPersonnelInitiateur(personnelInit);
		operationMoyPay.setPersonnelValideur(personnelInit);
		Structure structureInit = new Structure();
		structureInit.setCodStrcStrc(contratCptTireur.getContratCptId().getCodStrcStrc());
		operationMoyPay.setStructureInitiatrice(structureInit);
		Structure structureRecep = new Structure();
		structureRecep.setCodStrcStrc(contratCptTireur.getContratCptId().getCodStrcStrc());
		operationMoyPay.setStructureReceptrice(structureRecep);
		operationMoyPay.setDevise(contratCptTireur.getDevise());
		operationMoyPay.setContratCpt(contratCptTireur);
		operationMoyPay.setCodDemOmp("T");
		operationMoyPay.setCodRefcOmp("");
		operationMoyPay.setCodRefbOmp(
				(new StringBuilder()).append(reglementChequeVo.getCheque().getChequeId().getNumChqChq()).toString());
		TypePiece typePieceDemandeur = new TypePiece();
		typePieceDemandeur.setCodTpceTpce(
				Long.valueOf(contratCptTireur.getClient().getPersonne().getTypePiece().getCodTpceTpce().longValue()));
		operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
		operationMoyPay.setNumPcedOmp(contratCptTireur.getClient().getPersonne().getNumPcePers());
		operationMoyPay.setNomNomdOmp(contratCptTireur.getClient().getPersonne().getNomNommPers());
		operationMoyPay.setNomPrndOmp(contratCptTireur.getClient().getPersonne().getNomPrnPers());
		Operation oper = new Operation();
		oper.setCodOperOper(reglementChequeVo.getCodeOperation());
		Tache tache = new Tache();
		tache.setOperation(oper);
		TacheId tacheId = new TacheId();
		tacheId.setCodOperOper(reglementChequeVo.getCodeOperation());
		tacheId.setCodTachTach(Constants.COD_TACHE_REJET_CHEQUE_PREAVIS);
		tache.setTacheId(tacheId);
		operationMoyPay.setCodEtatOmp("V");
		operationMoyPay.setTache(tache);
		operationMoyPay.setDatOperOmp(reglementChequeVo.getDateComptable());
		operationMoyPay.setDatValOmp(DateHandler.strToDate(valueDateRecue));
		operationMoyPay.setDatSystOmp(new Date());
		operationMoyPay.setDateValeurCommission(DateHandler.strToDate(valueDateComRecue));
		operationMoyPay.setCodSensOmp("D");
		operationMoyPay.setMontTvaOmp(Long.valueOf(0L));
		operationMoyPay.setCodRefcOmp(reglementChequeVo.getParamAgence().getNumMatrUser());
		operationMoyPay.setLibMotfOmp("Reglement Cheque");
		InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt = new InsertionOperationMoyPaySansCROTrt();
		logger.info("Operation Mo Pay done success");
		logger.info((new StringBuilder("operationMoyPay :")).append(operationMoyPay).toString());
		reglementChequeVo.setOperationMoyPay(operationMoyPay);
		if (!contratCptTireur.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
			Double tauxCoursAchat = UtilCtr.getCoursAchatBna(contratCptTireur.getDevise().getCodDevDev().toString());
			Long mntDev = UtilCtr.changeTNDToDevise(montant, contratCptTireur.getDevise().getNbrDecDev(),
					contratCptTireur.getDevise().getNbrUnitDev(), tauxCoursAchat);
			Double tauxFixe = UtilCtr.getCoursFixe(reglementChequeVo.getDateComptable(),
					contratCptTireur.getDevise().getCodDevDev());
			Long mntDinar = UtilCtr.changeDeviseToTND(mntDev, contratCptTireur.getDevise().getNbrDecDev(),
					contratCptTireur.getDevise().getNbrUnitDev(), tauxFixe);
			Long mntSold = contratCptTireur.getMontSoldCcpt();
			operCredit = new OperationMoyPay();
			BeanUtils.copyProperties(operCredit, operationMoyPay);
			operCredit.setCodSensOmp("C");
			operCredit.setMontSoldCcpt(mntSold);
			operCredit.setMontDinOmp(montant);
			operCredit.setMontApreOmp(Long.valueOf(mntSold.longValue() + montant.longValue()));
			operCredit.setCodSensOmp("C");
			operCredit.setMontSdevCcpt(contratCptTireur.getMontSdevCcpt());
			operCredit.setMontDevOmp(mntDev);
			operCredit.setMontDevApreOmp(
					Long.valueOf(contratCptTireur.getMontSdevCcpt().longValue() + mntDev.longValue()));
			operDebit = new OperationMoyPay();
			BeanUtils.copyProperties(operDebit, operationMoyPay);
			operDebit.setCodSensOmp("D");
			operDebit.setMontSoldCcpt(operCredit.getMontApreOmp());
			operDebit.setMontDinOmp(montant);
			GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
			String quantieme = StrHandler.lpad(
					String.valueOf((new Double(GenerateReferenceInterSiege.getQuantieme(
							DateHandler.strToDate(reglementChequeVo.getParamAgence().getDateComptable())))).intValue()),
					'0', 3);
			String refIntSg9101="";
			String refIntSg9102="";
			if (reglementChequeVo.getNbreChqDev() != null) {
				 refIntSg9101 = (new StringBuilder(String.valueOf(
						String.format("%03d", new Object[]{ reglementChequeVo.getParamAgence().getCodStrcStrc() }))))
								.append(quantieme).append("A")
								.append(String.format("%02d", new Object[]{ reglementChequeVo.getNbreChqDev() }))
								.toString();
				 refIntSg9102 = (new StringBuilder(String.valueOf(
							String.format("%03d", new Object[]{ reglementChequeVo.getParamAgence().getCodStrcStrc() }))))
									.append(quantieme).append("A")
									.append(String.format("%02d",
											new Object[]{ Long.valueOf(reglementChequeVo.getNbreChqDev().longValue() + 2L) }))
									.toString();
			} else {
				
				refIntSg9101 = (new StringBuilder(String.valueOf(
							String.format("%03d", new Object[]{ reglementChequeVo.getParamAgence().getCodStrcStrc() }))))
									.append(quantieme).append("A")
									.append(String.format("%02d", new Object[]{ Long.valueOf(1L) }))
									.toString();
				
				 refIntSg9102 = (new StringBuilder(String.valueOf(
							String.format("%03d", new Object[]{ reglementChequeVo.getParamAgence().getCodStrcStrc() }))))
									.append(quantieme).append("A")
									.append(String.format("%02d",
											new Object[]{ Long.valueOf(2L) }))
									.toString();
			}
			
			operDebit.setMontApreOmp(Long.valueOf(operCredit.getMontApreOmp().longValue() - montant.longValue()));
			operDebit.setCodSensOmp("D");
			operDebit.setMontSdevCcpt(operCredit.getMontDevApreOmp());
			operDebit.setMontDevOmp(mntDev);
			operDebit.setMontDevApreOmp(Long.valueOf(operCredit.getMontDevApreOmp().longValue() - mntDev.longValue()));
			operationMoyPay.setMontSoldCcpt(operDebit.getMontApreOmp());
			operationMoyPay.setMontDinOmp(mntDinar);
			operationMoyPay.setMontApreOmp(Long.valueOf(operDebit.getMontApreOmp().longValue() - mntDinar.longValue()));
			operationMoyPay.setMontSdevCcpt(operDebit.getMontDevApreOmp());
			operationMoyPay.setMontDevOmp(mntDev);
			operationMoyPay
					.setMontDevApreOmp(Long.valueOf(operDebit.getMontDevApreOmp().longValue() - mntDev.longValue()));
			operationMoyPay.setRefIns1Omp(refIntSg9101);
			operationMoyPay.setRefIns2Omp(refIntSg9102);
			operationMoyPay.setMontCvalOmp(montant);
			operationMoyPay.setCodRefcOmp(operDebit.getCodRefbOmp());
			operDebit.setCodRefcOmp(operDebit.getCodRefbOmp());
		} else {
			operationMoyPay.setMontDinOmp(montant);
			operationMoyPay
					.setMontApreOmp(Long.valueOf(contratCptTireur.getMontSoldCcpt().longValue() - montant.longValue()));
			operationMoyPay.setMontSoldCcpt(contratCptTireur.getMontSoldCcpt());
		}
		if (UtilCtr.isContratValide(contratCptTireur) && !UtilCtr.isChqCertif(reglementChequeVo.getCheque())) {
			if (operCredit != null) {
				insertOperationMoyPaySansCROTrt.exec(operCredit);
				insertOperationMoyPaySansCROTrt.exec(operDebit);
			}
			operationMoyPay = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPay);
			contratCptTireur =
					UtilCtr.updateSolde(contratCptTireur, "D", montant, reglementChequeVo.getDateComptable());
			HibernateTemplate hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
			hibernateTemplate.getSessionFactory().getCurrentSession().evict(contratCptTireur);
			contratCptTireur = (ContratCpt) hibernateTemplate.get(ContratCpt.class, contratCptTireur.getContratCptId());
			reglementChequeVo.setContratCpt(contratCptTireur);
		}
		MouvementCompensation mouvementCompensation = new MouvementCompensation();
		mouvementCompensation.setCheque(reglementChequeVo.getCheque());
		mouvementCompensation.setDatOpeMvtc(reglementChequeVo.getDateComptable());
		if (operationMoyPay.getNumMoypOmp() != null) {
			mouvementCompensation.setOperationMoyPay(operationMoyPay);
		}
		mouvementCompensation.setTache(tache);
		mouvementCompensation.setPersonnel(personnelInit);
		mouvementCompensation.setMntOpeMvtc(reglementChequeVo.getMontantARegler());
		mouvementCompensation.setNumSeqMvtc(rejetDao.getSequenceMvtCompensation());
		crudService.create(mouvementCompensation);
		setCroFlag(true);
	}

	public IValueObject perform(IValueObject vo) {
		reglementChequeVo = (ReglementChequeVo) vo;
		try {
			Long mntPpart = Long.valueOf(0L);
			if (reglementChequeVo.getCheque().getMntPPartChq() != null) {
				mntPpart = reglementChequeVo.getCheque().getMntPPartChq();
			}
			if (reglementChequeVo.getSommeBlocage().longValue() != 0L) {
				DeblocageChequeTrt deblocageChequeTrt = new DeblocageChequeTrt();
				reglementChequeVo = (ReglementChequeVo) deblocageChequeTrt.exec(reglementChequeVo);
			}
			if (reglementChequeVo.getSommeReserve().longValue() != 0L) {
				DeReservationChequeTrt deReservationChequeTrt = new DeReservationChequeTrt();
				reglementChequeVo = (ReglementChequeVo) deReservationChequeTrt.exec(reglementChequeVo);
			}

			if (reglementChequeVo.getCodValVal().equals(Constants.COD_CHEQUE_PREMIERE_PRESENTATION)
					|| reglementChequeVo.getCodValVal().equals(Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON)) {
				operationReglement(reglementChequeVo.getCheque().getMntChqChq());
			}
			if (reglementChequeVo.getCodValVal().equals(Constants.COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL)) {
				Cheque cheque = reglementChequeVo.getCheque();
				cheque.setMntPPartChq(
						Long.valueOf(reglementChequeVo.getMontantARegler().longValue() + mntPpart.longValue()));
				crudService.update(cheque);
				reglementChequeVo.setCheque(cheque);
				reglementChequeVo.setCodeOperation(Long.valueOf(809L));
				operationReglement(reglementChequeVo.getMontantARegler());
			}
			if (reglementChequeVo.getCodValVal().equals(Constants.COD_CHEQUE_REPRESENTATION_SUITE_ARP)) {
				Cheque cheque = reglementChequeVo.getCheque();
				reglementChequeVo.setCheque(cheque);
				operationReglement(reglementChequeVo.getMontantARegler());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Error erreur = new Error();
			StringBuffer text = new StringBuffer("Erreur dans ReglementChequeTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("ReglementChequeTrt");
			reglementChequeVo.addError(erreur);
			logger.error("Erreur au niveau ReglementChequeTrt : ", e);
			throw new RuntimeException(e);
		}
		return reglementChequeVo;
	}

	public String getNumeroTache(IValueObject vo) {
		return "120";
	}

	public boolean verifierEligibiliteProduit(Integer dt[], Integer codeProduit) {
		Integer ainteger[];
		int j = (ainteger = dt).length;
		for (int i = 0; i < j; i++) {
			Integer produit = ainteger[i];
			if (produit.equals(codeProduit)) {
				return true;
			}
		}

		return false;
	}

	public void genCroText(ValueObject vo) {
		ReglementChequeVo reglementChequeVo = (ReglementChequeVo) vo;
		OperationMoyPay operationMoyPay = reglementChequeVo.getOperationMoyPay();
		ContratCpt contratCptTireur = reglementChequeVo.getContratCpt();
		Operation operation = new Operation();
		operation.setCodOperOper(reglementChequeVo.getCodeOperation());
		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_CHEQUE);
		GetOperationTrt getOperationTrt = new GetOperationTrt();
		operation = (Operation) getOperationTrt.exec(operation);

		logger.info("starting ReglementChequeTrt method ");
		GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
		String quantieme = StrHandler.lpad(String.valueOf(new Double(GenerateReferenceInterSiege
				.getQuantieme(DateHandler.strToDate(reglementChequeVo.getParamAgence().getDateComptable())))
						.intValue()),
				'0', 3);
		String refIntSg9101 = "";
		String refIntSg9102 = "";
		if (contratCptTireur.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
			refIntSg9101 = String
					.valueOf(String.format("%03d", new Object[]{ reglementChequeVo.getParamAgence().getCodStrcStrc() }))
					+ quantieme + "C01";
			refIntSg9102 = String
					.valueOf(String.format("%03d", new Object[]{ reglementChequeVo.getParamAgence().getCodStrcStrc() }))
					+ quantieme + "C02";
		} else {
			if (reglementChequeVo.getNbreChqDev() != null) {
				 refIntSg9101 = (new StringBuilder(String.valueOf(
						String.format("%03d", new Object[]{ reglementChequeVo.getParamAgence().getCodStrcStrc() }))))
								.append(quantieme).append("A")
								.append(String.format("%02d", new Object[]{ reglementChequeVo.getNbreChqDev() }))
								.toString();
				 refIntSg9102 = (new StringBuilder(String.valueOf(
							String.format("%03d", new Object[]{ reglementChequeVo.getParamAgence().getCodStrcStrc() }))))
									.append(quantieme).append("A")
									.append(String.format("%02d",
											new Object[]{ Long.valueOf(reglementChequeVo.getNbreChqDev().longValue() + 2L) }))
									.toString();
			} else {
				
				refIntSg9101 = (new StringBuilder(String.valueOf(
							String.format("%03d", new Object[]{ reglementChequeVo.getParamAgence().getCodStrcStrc() }))))
									.append(quantieme).append("A")
									.append(String.format("%02d", new Object[]{ Long.valueOf(1L) }))
									.toString();
				
				 refIntSg9102 = (new StringBuilder(String.valueOf(
							String.format("%03d", new Object[]{ reglementChequeVo.getParamAgence().getCodStrcStrc() }))))
									.append(quantieme).append("A")
									.append(String.format("%02d",
											new Object[]{ Long.valueOf(2L) }))
									.toString();
			}
			
		}

		setCodRefInter(refIntSg9101);

		if (operationMoyPay.getNumOperOmp() == null) {
			setNumRefCro(reglementChequeVo.getCheque().getChequeId().getNumChqChq().longValue());
		} else {
			setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()).longValue());
		}

		setLibRefCro(operation.getLibOperOper());
		setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc() + "");
		setCodStrcImpt(operationMoyPay.getStructureReceptrice().getCodStrcStrc());
		setDatExecCro(new Date());
		setCodEtatCro(0);
		setCodeProduit(produit.getCodPrdPrd().toString());
		setOperationId(operation.getCodOperOper().toString());
		setDateOperation(operationMoyPay.getDatOperOmp());
		setDatValCro(operationMoyPay.getDatValOmp());
		setDatValCom(null);
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		setHeureOperation(heureString);
		setTypeOperationCro("O");
		setCodTachTach(Constants.COD_TACHE_REJET_CHEQUE_PREAVIS.longValue());
		setCodRefcOmp(operationMoyPay.getCodRefcOmp());
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.oxia.security.abc.model.Personnel user = null;
		if ((obj instanceof UserDetails)) {
			user = (com.oxia.security.abc.model.Personnel) obj;
		}
		setNumCinUser(user.getNumMatrUser());
		setCodTypUser(user.getMatriculeTyp());

		StringBuffer cro = new StringBuffer("");

		cro.append("cod_type_comp=1;");

		RejetDAO rejetDao = (RejetDAO) this.context.getBean("rejetDAO");
		String codAgentEco = rejetDao.getAgentEconomique(contratCptTireur.getClient().getPersonne());

		cro.append("AGENT_ECONOMIQUE.COD_NAT_CLT=" + codAgentEco + ";");

		cro.append("numcheque=");
		cro.append(reglementChequeVo.getCheque().getChequeId().getNumChqChq() + ";");

		cro.append("rib_benef=");
		cro.append(reglementChequeVo.getCheque().getChequeId().getRibBenChq() + ";");

		cro.append("rib_tireur=");
		cro.append(reglementChequeVo.getCheque().getChequeId().getRibTirChq() + ";");

		cro.append("Dat_comp=");
		cro.append(this.formaterDateCro.format(reglementChequeVo.getDateComptable()) + ";");

		cro.append("codval=");
		cro.append(reglementChequeVo.getCodValVal() + ";");

		cro.append("Numcptben=" + reglementChequeVo.getCheque().getChequeId().getRibTirChq().substring(5, 18) + ";");
		cro.append("MNT_CHQ_CLT=");
		cro.append(reglementChequeVo.getMontantARegler() + "_$$_TND;");

		if (operation.getCodOperOper().toString().equals("809")) {
			cro.append("MNT_PAY_PAR_CHQ=");
			cro.append(reglementChequeVo.getMontantARegler() + "_$$_TND;");
		}

		cro.append("ETAT_CPT=");
		if (contratCptTireur.getCodEtatCcpt().equals("V")) {
			cro.append("0;");
			cro.append("MNT_RESV_CHQ_CLT=0;");
		} else if ((contratCptTireur.getCodEtatCcpt().equals("T")) || (contratCptTireur.getCodEtatCcpt().equals("R"))) {
			cro.append("6;");
			cro.append("MNT_RESV_CHQ_CLT=" + reglementChequeVo.getSommeReserve() + ";");
		}

		cro.append("TYPE_CPT=");

		if (verifierEligibiliteProduit(Constants.listCompteEnDinarsConvertibles,
				new Integer(contratCptTireur.getContratCptId().getCodPrdPrd().toString()))) {
			cro.append("2;");
		} else if (verifierEligibiliteProduit(Constants.listCompteEnDevises,
				new Integer(contratCptTireur.getContratCptId().getCodPrdPrd().toString())))
			cro.append("3;");
		else if (contratCptTireur.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
			cro.append("1;");
		}

		if (!contratCptTireur.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
			Devise devise = (Devise) searchEngine.get(Devise.class, contratCptTireur.getDevise().getCodDevDev());

			Double tauxCoursAchat = UtilCtr.getCoursAchatBna(contratCptTireur.getDevise().getCodDevDev().toString());
			Long mntDev = UtilCtr.changeTNDToDevise(reglementChequeVo.getCheque().getMntChqChq(),
					contratCptTireur.getDevise().getNbrDecDev(), contratCptTireur.getDevise().getNbrUnitDev(),
					tauxCoursAchat);

			cro.append("MNT_CHQ_DEV=");
			if (devise != null && devise.getLibSiglDev() != null) {
				cro.append(mntDev + "_$$_" + devise.getLibSiglDev() + ";");
			} else {
				cro.append(mntDev + ";");
			}
			cro.append("TAUX_VAR_BBE=");
			cro.append(tauxCoursAchat + ";");
			Double tauxFixe = UtilCtr.getCoursFixe(reglementChequeVo.getDateComptable(),
					contratCptTireur.getDevise().getCodDevDev());
			cro.append("TAUX_FIX_BBE=");
			cro.append(tauxFixe + ";");
			cro.append("MNT_CTR_CHQ_CFIX=");
			cro.append(UtilCtr.changeDeviseToTND(mntDev, contratCptTireur.getDevise().getNbrDecDev(),
					contratCptTireur.getDevise().getNbrUnitDev(), tauxFixe) + "_$$_TND;");
		} else {
			cro.append("MNT_CHQ_DEV=0;");

			cro.append("TAUX_VAR_BBE=0;");

			cro.append("TAUX_FIX_BBE=0;");
			cro.append("MNT_CTR_CHQ_CFIX=0_$$_TND;");
		}

		cro.append("COD_DEV_CPT=");
		cro.append(contratCptTireur.getDevise().getCodDevDev() + ";");

		cro.append("Refis9101=");
		cro.append(refIntSg9101 + ";");

		cro.append("Refis9102=");
		cro.append(refIntSg9102 + ";");

		cro.append("code_ref_910=67;");

		if ((reglementChequeVo.getCheque().getDecompte() != null)
				&& (reglementChequeVo.getCheque().getDecompte().getMntIntDec() != null)) {
			cro.append("MNT_CHQ_TOT_CLT=");
			cro.append(Long.valueOf(reglementChequeVo.getCheque().getMntChqChq().longValue()
					+ reglementChequeVo.getCheque().getDecompte().getMntIntDec().longValue()) + "_$$_TND;");
			cro.append("MNT_INT_RET_CHQ=");
			cro.append(reglementChequeVo.getCheque().getDecompte().getMntIntDec() + "_$$_TND;");
			cro.append("TYPE_INTERET=0;");
		} else {
			cro.append("MNT_CHQ_TOT_CLT=");

			cro.append(reglementChequeVo.getCheque().getMntChqChq().longValue() + "_$$_TND;");
			cro.append("MNT_INT_RET_CHQ=0_$$_TND;");
			cro.append("TYPE_INTERET=1;");
		}

		if (UtilCtr.isChqCertif(reglementChequeVo.getCheque()))
			cro.append("TYPE_PAIEMENT=0;");
		else {
			cro.append("TYPE_PAIEMENT=1;");
		}

		if (UtilCtr.exoneration(contratCptTireur.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString(),
				contratCptTireur.getClient().getPersonne().getNumPcePers(), reglementChequeVo.getDateComptable()))
			cro.append("COD_TVA_CLT=1;");
		else {
			cro.append("COD_TVA_CLT=0;");
		}
		cro.append("MONT_COM_BNA=0_$$_TND;");
		cro.append("MONT_TVA_OMP=0_$$_TND;");

		setCroText(cro.toString());
		logger.info("Cro generer avec succés");
	}
}