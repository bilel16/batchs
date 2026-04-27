package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.BlocageCheque;
import com.bna.commun.model.Cheque;
import com.bna.commun.model.Cheque31;
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
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class PositionCheque31Trt extends Traitement {

	public PositionCheque31Trt() {
	}

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	ICriteria criteria = searchEngine.createCriteria();
	ICriteria criteriaCpt = searchEngine.createCriteria();
	ICriteria criteriaChq = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();

	public IValueObject perform(IValueObject vo) {
		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
		CompensationVo compensationVo = (CompensationVo) vo;
		GetListChequesRecusTrt getListChequesRecusTrt = new GetListChequesRecusTrt();
		Long montGlobChq31 = new Long(0);
		Long nbrGlobChq31 = new Long(0);

		try {
			ListChequesRecusVo listChequesRecusVo = new ListChequesRecusVo();
			listChequesRecusVo.setDateComptable(compensationVo.getDateComptable());
			listChequesRecusVo.setTypeCheque(Long.valueOf(31));
			listChequesRecusVo.setStructure(compensationVo.getStrutcure().getCodStrcStrc().toString());
			getListChequesRecusTrt.exec(listChequesRecusVo);
			listChequesRecusVo = (ListChequesRecusVo) getListChequesRecusTrt.exec(listChequesRecusVo);
			boolean willBeToil;

			if (listChequesRecusVo.getListCheques31() != null && listChequesRecusVo.getListCheques31().size() > 0) {
				for (Iterator it = listChequesRecusVo.getListCheques31().iterator(); it.hasNext();) {
					Long mntBlocChq = new Long(0);
					Long mntReservationChq = new Long(0);
					Cheque31 chq31 = (Cheque31) it.next();
					montGlobChq31 = montGlobChq31 + chq31.getMntChq();
					nbrGlobChq31 = nbrGlobChq31 + 1;
					ContratCptId cptId = new ContratCptId();
					cptId.setCodStrcStrc(Long.valueOf(chq31.getRibTir().substring(5, 8)));
					cptId.setCodPrdPrd(Long.valueOf(chq31.getRibTir().substring(8, 12)));
					cptId.setNumCcptCcpt(Long.valueOf(chq31.getRibTir().substring(12, 18)));
					ContratCpt contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, cptId);
					willBeToil = false;

					criteria = searchEngine.createCriteria();
					criteriaCpt = searchEngine.createCriteria();
					criteriaChq = searchEngine.createCriteria();
					expression = searchEngine.createExpression();

					if (contratCpt != null) {

						/**********************************
						 * controle valeur deja regle ou rejete ************************FIN PROC
						 **********************/

						criteriaChq.add(expression.eq("chequeId.numChqChq", chq31.getNumChq()));
						criteriaChq.add(expression.eq("chequeId.ribTirChq", chq31.getRibTir()));
						criteriaChq.add(expression.eq("chequeId.ribBenChq", chq31.getRibBen()));
						List<Cheque> listChq = searchEngine.find(Cheque.class, criteriaChq);
						if ((listChq != null) && (listChq.size() > 0)) {
							for (Iterator itchq = listChq.iterator(); itchq.hasNext();) {
								Cheque cheque = (Cheque) itchq.next();
								if (cheque.getCodEtatChq().equalsIgnoreCase("P")) {
									chq31.setCodRej1("43");

								} else if (cheque.getCodRejChq() != null
										&& cheque.getCodRejChq().getCodValVal().toString().equals(Constants.COD_CNP)) {
									if (cheque.getCodFprocChq() != null && cheque.getCodFprocChq() == 1L) // deja rejete
																										  // + fin
																										  // procedure
																										  // => rejet
																										  // avec mme
																										  // motif
									{
										chq31.setCodRej1(cheque.getCodMrejChq().substring(0, 2));
										if (cheque.getCodMrejChq().length() >= 4) {
											chq31.setCodRej2(cheque.getCodMrejChq().substring(2, 4));
										}
									} else {

										String motifrej30 = cheque.getCodMrejChq().substring(0, 2);
										// si pas de vice de forme et montant reclame = montant bloqué : paiement
										// si probléme montant alors pas de paiement
										// si vf. alors toilettage
										if (motifrej30.equals("11")) {
											GetBolcagePourChqTrt getBolcagePourChqTrt = new GetBolcagePourChqTrt();
											BlocageCheque blocageCheque = new BlocageCheque();
											BlocageChqVo blocageChqVo = new BlocageChqVo();
											ContratCpt contratCptBloc = new ContratCpt();
											ContratCptId contratCptId = new ContratCptId();
											contratCptId.setCodStrcStrc(new Long(chq31.getRibTir().substring(5, 8)));
											contratCptId.setCodPrdPrd(new Long(chq31.getRibTir().substring(8, 12)));
											contratCptId.setNumCcptCcpt(new Long(chq31.getRibTir().substring(12, 18)));
											contratCptBloc.setContratCptId(contratCptId);
											blocageCheque.setContratCpt(contratCptBloc);
											blocageCheque.setNumChqChq(chq31.getNumChq());
											blocageChqVo.setBlocageCheque(blocageCheque);
											blocageChqVo = (BlocageChqVo) getBolcagePourChqTrt.exec(blocageChqVo);
											mntBlocChq = blocageChqVo.getSommeBlocage();

											/************ TEST MONTANT RESERVATION ****/
											CompensationDAO compensationDAO =
													(CompensationDAO) context.getBean("compensationDAO");
											List<ReservationChqVo> listeReservationChqVo = compensationDAO
													.getListReservationChq(chq31.getRibTir(), chq31.getNumChq() + "");

											if (listeReservationChqVo != null && listeReservationChqVo.size() != 0) {

												for (ReservationChqVo reservationChqVo : listeReservationChqVo) {

													mntReservationChq += reservationChqVo.getMontantRsv();
												}
											}

											if (chq31.getMntRec().longValue() != Long
													.valueOf(mntBlocChq + mntReservationChq).longValue()) {
												chq31.setCodRej1("16");
											} else { // si montant ok , verifier vice de forme
												if (isVf(cheque.getCodMrejChq())) {
													chq31.setCodEtatChq("P");
													willBeToil = true;
												}

											}

										} else { // motif != 11
											chq31.setCodRej1("36");
										}
									}
								} else {// motif different de CNP :
									chq31.setCodRej1("36");

								}

							}
						} else {
							chq31.setCodRej1("36"); // 31 sans enregistrement dans la talbe cheque : mal acheminé
						}

					} else {
						chq31.setCodRej1("39"); // contrat null : mal acheminé : ca doit pas arrive au niveau 31

					}
					if (willBeToil)
						continue;
					/************************* maj chq31 avec les motifs de rejet *******************************/

					if (chq31.getCodRej1() == null && chq31.getCodRej2() == null && chq31.getCodRej3() == null
							&& chq31.getCodRej4() == null) {
						criteria.add(expression.eq("chequeId.numChqChq", chq31.getNumChq()));
						criteria.add(expression.eq("chequeId.ribTirChq", chq31.getRibTir()));
						criteria.add(expression.eq("chequeId.ribBenChq", chq31.getRibBen()));

						List<Cheque> liste = (List<Cheque>) searchEngine.find(Cheque.class, criteria);
						Cheque cheque = liste.get(0);
						// crudService.update(cheque);
						// insertion dans la table trace cheque
						TraceChequeId traceChequeId = new TraceChequeId();
						traceChequeId.setDatOpeChq(cheque.getDatOpeChq());
						traceChequeId.setCodValVal(chq31.getCodVal());
						traceChequeId.setNumChqChq(cheque.getChequeId().getNumChqChq());
						traceChequeId.setRibBenChq(cheque.getChequeId().getRibBenChq());
						traceChequeId.setRibTirChq(cheque.getChequeId().getRibTirChq());
						TraceCheque traceCheque = new TraceCheque();
						traceCheque.setCheque(cheque);
						traceCheque.setTraceChequeId(traceChequeId);
						Valeur valeurr = new Valeur();
						valeurr.setCodValVal(chq31.getCodVal());
						traceCheque.setValeur(valeurr);
						traceCheque.setNumLotTch(cheque.getNumLotChq());
						traceCheque.setMntChqTch(cheque.getMntChqChq());
						traceCheque.setNomPrnTch(cheque.getNomPrnChq());
						traceCheque.setDatEmiTch(cheque.getDatEmiChq());
						traceCheque.setCodSenTch(2L);// code sens recu !!!!!!!
						traceCheque.setCodEnrTch(cheque.getCodEnrChq());
						traceCheque.setCodBanTch(cheque.getCodBadeChq());
						traceCheque.setCodAgedTch(cheque.getCodAgdeChq());
						traceCheque.setCodBandTch(cheque.getCodBaemChq());
						traceCheque.setCodLieuTch(cheque.getCodLemiChq());
						traceCheque.setCodSitTch(cheque.getCodSbenChq());
						traceCheque.setCodNatcTch(cheque.getCodNcptChq());
						traceCheque.setCodNateTch(cheque.getCodNateChq());
						traceCheque.setCodDevTch(cheque.getDevise().getCodDevDev().toString());
						traceCheque.setCodValTch(cheque.getValeur().getCodValVal());
						traceCheque.setRefFicTch(cheque.getRefFicChq());
						traceCheque.setRibTirRecTch(cheque.getRibTrecChq());
						traceCheque.setCodMotrTch(cheque.getCodMrejChq());
						traceCheque.setNumEvtEnvTch(cheque.getNumEvenChq());
						traceCheque.setNumEvtRcpTch(cheque.getNumEvrcpChq());
						traceCheque.setRjtRegTch(cheque.getRjtRegChq());
						traceCheque.setCodNatcTch(cheque.getCodNateChq());// !!!!!!!!!!!!!!!!

						crudService.create(traceCheque);

						// reglement cheque
						ReglementChequeVo reglementChequeVo = new ReglementChequeVo();
						contratCpt = UtilCtr.getContratCptByRIB(chq31.getRibTir());

						// ParamAgence
						ParamAgence paramAgence = new ParamAgence();
						paramAgence.setCodStrcStrc(Long.valueOf(listChequesRecusVo.getStructure()));
						paramAgence.setDateComptable(DateHandler.dateToStr(listChequesRecusVo.getDateComptable()));
						paramAgence.setNumMatrUser("9999");

						// maj cheque
						cheque.setDatOpeChq(DateHandler.strToDate(paramAgence.getDateComptable()));
						cheque.setValeur(new Valeur(31L));
						cheque.setDatRegChq(DateHandler.strToDate(paramAgence.getDateComptable()));
						crudService.update(cheque);

						reglementChequeVo.setCheque(cheque);
						reglementChequeVo.setMontantARegler(chq31.getMntRec());
						reglementChequeVo.setParamAgence(paramAgence);
						reglementChequeVo.setCodValVal(chq31.getCodVal());
						reglementChequeVo.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
						reglementChequeVo.setCodeOperation(Long.valueOf(809));
						reglementChequeVo.setSommeBlocage(mntBlocChq);
						reglementChequeVo.setSommeReserve(mntReservationChq);
						reglementChequeVo.setContratCpt(contratCpt);
						ReglementChequeTrt reglementChequeTrt = new ReglementChequeTrt();
						reglementChequeVo = (ReglementChequeVo) reglementChequeTrt.exec(reglementChequeVo);
						chq31.setCodEtatChq("R"); // régle

					} else {
						chq31.setCodEtatChq("T"); // toiletté
					}
					crudService.update(chq31);
				}

			}
			compensationVo.setMontGlobChq31(montGlobChq31);
			compensationVo.setNbrGlobChq31(nbrGlobChq31);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans PositionCheque31Trt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("PositionCheque33Trt");
			logger.error("Exception : ", e);
			compensationVo.addError(erreur);
			throw new RuntimeException(e);

		}
		return (compensationVo);
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);

	}

	public String[] getMotifAsTab(String motif) {
		int nbreMotif = motif.length() / 2;
		String[] returnValue = new String[nbreMotif];
		for (int i = 0; i <= motif.length() - 2; i = i + 2) {
			returnValue[i / 2] = motif.substring(i, i + 2);

		}
		return returnValue;
	}

	public boolean isVf(String motif) {
		String vfTab[] = new String[]{ "30", "32", "33", "36", "22", "23" }; // 1 ere presentation, le cheque 31 peut
																			 // arriver plusieurs fois
		String motifs[] = getMotifAsTab(motif);
		for (String m : vfTab) {
			if (Arrays.asList(motifs).contains(m))
				return true;
		}

		return false;
	}
}
