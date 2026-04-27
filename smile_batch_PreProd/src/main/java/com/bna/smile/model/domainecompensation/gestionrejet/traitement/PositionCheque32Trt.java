package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.BlocageCheque;
import com.bna.commun.model.Cheque;
import com.bna.commun.model.Cheque32;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.TraceCheque;
import com.bna.commun.model.TraceChequeId;
import com.bna.commun.model.Valeur;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.BlocageChqVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementChequeVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReservationChqVo;
import com.bna.smile.model.traitementCompensationRecu.model.ListChequesRecusVo;
import com.bna.smile.model.traitementCompensationRecu.traitement.GetListChequesRecusTrt;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.Error;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

// Referenced classes of package com.bna.smile.model.domainecompensation.gestionrejet.traitement:
// GetBolcagePourChqTrt, ReglementChequeTrt

public class PositionCheque32Trt extends Traitement {

	Context context;
	CRUDservice crudService;
	ISearchEngine searchEngine;
	ICriteria criteria;
	ICriteria criteriaCpt;
	ICriteria criteriaChq;
	IExpression expression;
	Long mntDispReservationArp = Long.valueOf(0L);

	public PositionCheque32Trt() {
		context = ContextHandler.getContext();
		crudService = (CRUDservice) context.getBean("crudservice");
		searchEngine = (SearchEngine) context.getBean("searchEngine");
		criteria = searchEngine.createCriteria();
		criteriaCpt = searchEngine.createCriteria();
		criteriaChq = searchEngine.createCriteria();
		expression = searchEngine.createExpression();
	}

	public IValueObject perform(IValueObject vo) {
		setSecurityFlag(false);
		setVerifDomaine(false);
		setCroFlag(false);
		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
		CompensationVo compensationVo = (CompensationVo) vo;
		GetListChequesRecusTrt getListChequesRecusTrt = new GetListChequesRecusTrt();
		Long montGlobchq32 = Long.valueOf(0L);
		Long nbrGlobchq32 = Long.valueOf(0L);
		Long count = Long.valueOf(0L);
		Long total = Long.valueOf(0L);
		try {
			ListChequesRecusVo listChequesRecusVo = new ListChequesRecusVo();
			listChequesRecusVo.setDateComptable(compensationVo.getDateComptable());
			listChequesRecusVo.setTypeCheque(Long.valueOf(32L));
			listChequesRecusVo.setStructure(compensationVo.getStrutcure().getCodStrcStrc().toString());
			getListChequesRecusTrt.exec(listChequesRecusVo);
			listChequesRecusVo = (ListChequesRecusVo) getListChequesRecusTrt.exec(listChequesRecusVo);
			if (listChequesRecusVo.getListCheques32() != null && listChequesRecusVo.getListCheques32().size() > 0) {
				total = Long.valueOf(listChequesRecusVo.getListCheques32().size());
				Cheque32 chq32;
				for (Iterator it = listChequesRecusVo.getListCheques32().iterator(); it.hasNext(); crudService
						.update(chq32)) {
					criteria = searchEngine.createCriteria();
					criteriaCpt = searchEngine.createCriteria();
					criteriaChq = searchEngine.createCriteria();
					expression = searchEngine.createExpression();
					chq32 = (Cheque32) it.next();
					montGlobchq32 = Long.valueOf(montGlobchq32.longValue() + chq32.getMntRec().longValue());
					nbrGlobchq32 = Long.valueOf(nbrGlobchq32.longValue() + 1L);
					count = Long.valueOf(count.longValue() + 1L);
					compensationVo.getBatch().getMsgDetailChq()
							.setText((new StringBuilder("Cheque : ")).append(count).append("/").append(total)
									.append("  [Valeur :32,Numero :").append(chq32.getNumChq()).append("]").toString());
					ContratCptId cptId = new ContratCptId();
					cptId.setCodStrcStrc(Long.valueOf(chq32.getRibTir().substring(5, 8)));
					cptId.setCodPrdPrd(Long.valueOf(chq32.getRibTir().substring(8, 12)));
					cptId.setNumCcptCcpt(Long.valueOf(chq32.getRibTir().substring(12, 18)));
					ContratCpt contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, cptId);
					criteriaChq.add(expression.eq("chequeId.numChqChq", chq32.getNumChq()));
					criteriaChq.add(expression.eq("chequeId.ribTirChq", chq32.getRibTir()));
					List listChq = searchEngine.find(Cheque.class, criteriaChq);
					if ((listChq != null) && (listChq.size() > 0)) {
						Cheque cheque = (Cheque) listChq.get(0);
						if (isDejaPaye(cheque)) {
							chq32.setCodRej1(Constants.COD_MREJ_CHQ_DEJ_REGL);

						} else {
							if (cheque.getCodRejChq() != null && isArp(cheque) && cheque.getArp() != null) {
								Long mntIntRet = Long.valueOf(0L);
								Long mntDispArp = Long.valueOf(0L);
							
								GetBolcagePourChqTrt getBolcagePourChqTrt = new GetBolcagePourChqTrt();
								BlocageCheque blocageCheque = new BlocageCheque();
								BlocageChqVo blocageChqVo = new BlocageChqVo();
								ContratCpt contratCptBloc = new ContratCpt();
								ContratCptId contratCptId = new ContratCptId();
								contratCptId.setCodStrcStrc(new Long(chq32.getRibTir().substring(5, 8)));
								contratCptId.setCodPrdPrd(new Long(chq32.getRibTir().substring(8, 12)));
								contratCptId.setNumCcptCcpt(new Long(chq32.getRibTir().substring(12, 18)));
								contratCptBloc.setContratCptId(contratCptId);
								blocageCheque.setContratCpt(contratCptBloc);
								blocageCheque.setNumChqChq(chq32.getNumChq());
								blocageChqVo.setBlocageCheque(blocageCheque);
								blocageChqVo.setTypeBlocage("CHQ");
								blocageChqVo = (BlocageChqVo) getBolcagePourChqTrt.exec(blocageChqVo);
								mntDispArp = blocageChqVo.getSommeBlocage();
								// somme bloc for int
								blocageChqVo.setTypeBlocage("INR");
								blocageChqVo = (BlocageChqVo) getBolcagePourChqTrt.exec(blocageChqVo);
								mntDispArp = mntDispArp + blocageChqVo.getSommeBlocage();
								/************ TEST MONTANT RESERVATION ****/
								CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
								List<ReservationChqVo> listeReservationChqVo = compensationDAO
										.getListReservationChq(chq32.getRibTir(), chq32.getNumChq() + "");

								if (listeReservationChqVo != null && listeReservationChqVo.size() != 0) {

									for (ReservationChqVo reservationChqVo : listeReservationChqVo) {

										mntDispReservationArp += reservationChqVo.getMontantRsv();
									}
								}
								mntDispArp = mntDispArp + mntDispReservationArp;
								
								// mntDispArp= cheque.getDecompte().getMntRegChDec()+mntIntRet;
								if (chq32.getMntRec().longValue() != mntDispArp.longValue()) {
									chq32.setCodRej1(Constants.COD_MREJ_CHQ_MNT_REC_ERR);
								}

							} else {
								chq32.setCodRej1(Constants.COD_MREJ_CHQ_MAL_PRES);
							}
						}
					} else {
						chq32.setCodRej1(Constants.COD_MREJ_CHQ_MAL_PRES);
					}

					if (chq32.getCodRej1() == null && chq32.getCodRej2() == null && chq32.getCodRej3() == null
							&& chq32.getCodRej4() == null) {
						chq32 = reglement(chq32, contratCpt, listChequesRecusVo);
						chq32.setCodEtatChq(Constants.COD_ETAT_CHQ_TMP_REGLE);

					} else {
						chq32.setCodEtatChq(Constants.COD_ETAT_CHQ_TMP_POSITIONE);

					}

					crudService.update(chq32);
				}

			}
			compensationVo.setMontGlobChq32(montGlobchq32);
			compensationVo.setNbrGlobChq32(nbrGlobchq32);
		} catch (

		Exception e) {
			compensationVo.getBatch().getMsgDetailChq().setForeground(java.awt.Color.red);
			compensationVo.getBatch().getMsgDetailChq()
					.setText((new StringBuilder(String.valueOf(compensationVo.getBatch().getMsgDetailChq().getText())))
							.append(" Erreur !").toString());
			Error erreur = new Error();
			StringBuffer text = new StringBuffer("Erreur dans PositionCheque32Trt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("PositionCheque32Trt");
			logger.error("Exception : ", e);
			compensationVo.addError(erreur);
			throw new RuntimeException(e);
		}
		return compensationVo;
	}

	public void genCroText(ValueObject valueobject) {
	}

	public boolean isDejaPaye(Cheque chq) {
		return chq.getCodEtatChq().equalsIgnoreCase("P");
	}

	public boolean isArp(Cheque chq) {
		return chq.getCodRejChq().getCodValVal().toString().equals("83");
	}

	public Cheque32 reglement(Cheque32 chq32, ContratCpt contratCpt, ListChequesRecusVo listChequesRecusVo) {
		criteria = searchEngine.createCriteria();
		criteriaCpt = searchEngine.createCriteria();
		criteriaChq = searchEngine.createCriteria();
		expression = searchEngine.createExpression();
		criteria.add(expression.eq("chequeId.numChqChq", chq32.getNumChq()));
		criteria.add(expression.eq("chequeId.ribTirChq", chq32.getRibTir()));
		List liste = searchEngine.find(Cheque.class, criteria);
		Cheque cheque = (Cheque) liste.get(0);
		Valeur valeur = new Valeur(Long.valueOf(32L));
		cheque.setValeur(valeur);
		cheque.setDatOpeChq(chq32.getDatOpe());
		cheque.setCodEtatChq("P");
		cheque.setDatRegChq(chq32.getDatOpe());
		crudService.update(cheque);
		TraceChequeId traceChequeId = new TraceChequeId();
		traceChequeId.setDatOpeChq(chq32.getDatOpe());
		traceChequeId.setCodValVal(chq32.getCodVal());
		traceChequeId.setNumChqChq(cheque.getChequeId().getNumChqChq().longValue());
		traceChequeId.setRibBenChq(cheque.getChequeId().getRibBenChq());
		traceChequeId.setRibTirChq(cheque.getChequeId().getRibTirChq());
		TraceCheque traceCheque = new TraceCheque();
		traceCheque.setCheque(cheque);
		traceCheque.setTraceChequeId(traceChequeId);
		Valeur valeurr = new Valeur();
		valeurr.setCodValVal(chq32.getCodVal());
		traceCheque.setValeur(valeurr);
		traceCheque.setNumLotTch(cheque.getNumLotChq());
		traceCheque.setMntChqTch(cheque.getMntChqChq());
		traceCheque.setNomPrnTch(cheque.getNomPrnChq());
		traceCheque.setDatEmiTch(cheque.getDatEmiChq());
		traceCheque.setCodSenTch(Long.valueOf(2L));
		traceCheque.setCodEnrTch(cheque.getCodEnrChq());
		traceCheque.setCodBanTch(cheque.getCodBadeChq());
		traceCheque.setCodAgedTch(cheque.getCodAgdeChq());
		traceCheque.setCodBandTch(cheque.getCodBaemChq());
		traceCheque.setCodLieuTch(cheque.getCodLemiChq());
		traceCheque.setCodSitTch(cheque.getCodSbenChq());
		traceCheque.setCodNatcTch(cheque.getCodNcptChq());
		traceCheque.setCodNateTch(cheque.getCodNateChq());
		traceCheque.setCodDevTch(cheque.getDevise().getCodDevDev().toString());
		traceCheque.setCodValTch(null);
		traceCheque.setRefFicTch(cheque.getRefFicChq());
		traceCheque.setRibTirRecTch(cheque.getRibTrecChq());
		traceCheque.setCodMotrTch(null);
		traceCheque.setNumEvtEnvTch(cheque.getNumEvenChq());
		traceCheque.setNumEvtRcpTch(cheque.getNumEvrcpChq());
		traceCheque.setRjtRegTch(cheque.getRjtRegChq());
		traceCheque.setCodNatcTch(cheque.getCodNateChq());
		crudService.create(traceCheque);
		ReglementChequeVo reglementChequeVo = new ReglementChequeVo();
		contratCpt = UtilCtr.getContratCptByRIB(chq32.getRibTir());
		ParamAgence paramAgence = new ParamAgence();
		paramAgence.setCodStrcStrc(Long.valueOf(listChequesRecusVo.getStructure()));
		paramAgence.setDateComptable(DateHandler.dateToStr(listChequesRecusVo.getDateComptable()));
		paramAgence.setNumMatrUser("9999");
		GetBolcagePourChqTrt getBolcagePourChqTrt = new GetBolcagePourChqTrt();
		BlocageCheque blocageCheque = new BlocageCheque();
		BlocageChqVo blocageChqVo = new BlocageChqVo();
		ContratCpt contratCptBloc = new ContratCpt();
		ContratCptId contratCptId = new ContratCptId();
		contratCptId.setCodStrcStrc(new Long(chq32.getRibTir().substring(5, 8)));
		contratCptId.setCodPrdPrd(new Long(chq32.getRibTir().substring(8, 12)));
		contratCptId.setNumCcptCcpt(new Long(chq32.getRibTir().substring(12, 18)));
		contratCptBloc.setContratCptId(contratCptId);
		blocageCheque.setContratCpt(contratCptBloc);
		blocageCheque.setNumChqChq(chq32.getNumChq());
		blocageChqVo.setBlocageCheque(blocageCheque);
		blocageChqVo.setTypeBlocage("CHQ");
		blocageChqVo = (BlocageChqVo) getBolcagePourChqTrt.exec(blocageChqVo);
		reglementChequeVo.setSommeBlocage(blocageChqVo.getSommeBlocage());
		reglementChequeVo.setSommeReserve(mntDispReservationArp);
		reglementChequeVo.setCheque(cheque);
		reglementChequeVo.setMontantARegler(blocageChqVo.getSommeBlocage());
		reglementChequeVo.setParamAgence(paramAgence);
		reglementChequeVo.setCodValVal(chq32.getCodVal());
		reglementChequeVo.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
		reglementChequeVo.setCodeOperation(Long.valueOf(806L));
		reglementChequeVo.setContratCpt(contratCpt);
		ReglementChequeTrt reglementChequeTrt = new ReglementChequeTrt();
		reglementChequeVo = (ReglementChequeVo) reglementChequeTrt.exec(reglementChequeVo);
		return chq32;
	}
}
