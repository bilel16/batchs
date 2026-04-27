package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.MouvementCompensation;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecommun.traitement.GetOperationTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementChequeVo;
import com.bna.smile.model.traitementCompensationRecu.dao.RejetDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class CroReglementChequeTrt extends Traitement {

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

	public CroReglementChequeTrt() {
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

	public IValueObject perform(IValueObject vo) {
		reglementChequeVo = (ReglementChequeVo) vo;
		try {
			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser(reglementChequeVo.getParamAgence().getNumMatrUser());
			Operation operation = new Operation();
			operation.setCodOperOper(Long.valueOf(806L));
			Tache tache = new Tache();
			tache.setOperation(operation);
			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(reglementChequeVo.getCodeOperation());
			tacheId.setCodTachTach(Constants.COD_TACHE_REJET_CHEQUE_PREAVIS);
			tache.setTacheId(tacheId);
			MouvementCompensation mouvementCompensation = new MouvementCompensation();
			mouvementCompensation.setCheque(reglementChequeVo.getCheque());
			mouvementCompensation.setDatOpeMvtc(reglementChequeVo.getDateComptable());
			mouvementCompensation.setTache(tache);
			mouvementCompensation.setPersonnel(personnelInit);
			mouvementCompensation.setMntOpeMvtc(reglementChequeVo.getMontantARegler());
			mouvementCompensation.setNumSeqMvtc(rejetDao.getSequenceMvtCompensation());
			crudService.create(mouvementCompensation);
			setCroFlag(true);
		} catch (Exception e) {
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
		Operation operation = new Operation();
		operation.setCodOperOper(reglementChequeVo.getCodeOperation());
		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_CHEQUE);
		GetOperationTrt getOperationTrt = new GetOperationTrt();
		operation = (Operation) getOperationTrt.exec(operation);

		GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();

		String quantieme = StrHandler.lpad(String.valueOf(new Double(GenerateReferenceInterSiege
				.getQuantieme(DateHandler.strToDate(reglementChequeVo.getParamAgence().getDateComptable())))
						.intValue()),
				'0', 3);
		String refIntSg9101 = String.format("%03d", new Object[]{ reglementChequeVo.getParamAgence().getCodStrcStrc() })
				+ quantieme + "C01";
		String refIntSg9102 = String.format("%03d", new Object[]{ reglementChequeVo.getParamAgence().getCodStrcStrc() })
				+ quantieme + "C02";

		setCodRefInter(refIntSg9101);
		setNumRefCro(reglementChequeVo.getCheque().getChequeId().getNumChqChq().longValue());
		setLibRefCro(operation.getLibOperOper());
		setCodeStructInitiatrice(reglementChequeVo.getCheque().getChequeId().getRibTirChq().substring(5, 8));
		setCodStrcImpt(Long.valueOf(reglementChequeVo.getCheque().getChequeId().getRibTirChq().substring(5, 8)));
		setDatExecCro(new Date());
		setCodEtatCro(0);
		setCodeProduit(produit.getCodPrdPrd().toString());
		setOperationId(operation.getCodOperOper().toString());
		setDateOperation(reglementChequeVo.getCheque().getDatOpeChq());
		setDatValCro(reglementChequeVo.getCheque().getDatOpeChq());
		setDatValCom(null);
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		setHeureOperation(heureString);
		setTypeOperationCro("O");
		setCodTachTach(Constants.COD_TACHE_REJET_CHEQUE_PREAVIS.longValue());
		setCodRefcOmp(reglementChequeVo.getCheque().getChequeId().getNumChqChq() + "");
		setNumCinUser("9999");
		setCodTypUser("M");

		StringBuffer cro = new StringBuffer("");
		cro.append("cod_type_comp=1;");
		RejetDAO rejetDao = (RejetDAO) this.context.getBean("rejetDAO");
		cro.append("AGENT_ECONOMIQUE.COD_NAT_CLT=1;");
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
		cro.append("ETAT_CPT=");
		cro.append("0;");
		cro.append("TYPE_CPT=");
		cro.append("1;");
		cro.append("MNT_CHQ_DEV=0;");
		cro.append("TAUX_VAR_BBE=0;");
		cro.append("TAUX_FIX_BBE=0;");
		cro.append("MNT_CTR_CHQ_CFIX=0_$$_TND;");
		cro.append("COD_DEV_CPT=");
		cro.append("788;");
		cro.append("Refis9101=");
		cro.append(refIntSg9101 + ";");
		cro.append("Refis9102=");
		cro.append(refIntSg9102 + ";");
		cro.append("code_ref_910=67;");
		cro.append("MNT_CHQ_TOT_CLT=");
		cro.append(reglementChequeVo.getCheque().getMntChqChq().longValue() + "_$$_TND;");
		cro.append("MNT_INT_RET_CHQ=0_$$_TND;");
		cro.append("TYPE_INTERET=1;");
		cro.append("TYPE_PAIEMENT=0;");
		cro.append("COD_TVA_CLT=0;");
		cro.append("MONT_COM_BNA=0;");
		cro.append("MONT_TVA_OMP=0;");
		cro.append("MNT_RESV_CHQ_CLT=0;");
		
		setCroText(cro.toString());
		logger.info("Cro generer avec succés");
	}
}
