package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.BlocageCheque;
import com.bna.commun.model.CertificationCheques;
import com.bna.commun.model.Cheque;
import com.bna.commun.model.Cheque33;
import com.bna.commun.model.ChequeId;
import com.bna.commun.model.Chequier;
import com.bna.commun.model.CompteInterne;
import com.bna.commun.model.CompteInterneId;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.BlocageChqVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementChequeVo;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetListOppositionTrt;
import com.bna.smile.model.traitementCompensationRecu.model.ListChequesRecusVo;
import com.bna.smile.model.traitementCompensationRecu.traitement.GetListChequesRecusTrt;
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
// TesterProvisionTrt, ReglementChequeTrt

public class PositionCheque33Trt extends Traitement {

	Context context = ContextHandler.getContext();
	CRUDservice crudService;
	ISearchEngine searchEngine;
	ICriteria criteria;
	ICriteria criteriaCpt;
	ICriteria criteriaChq;
	IExpression expression;
	boolean prbProvision;
	boolean mntChqInf20;
	ReglementChequeVo reglementChequeVo;
	HibernateTemplate hibernateTemplate;

	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");

	public PositionCheque33Trt() {
		context = ContextHandler.getContext();
		crudService = (CRUDservice) context.getBean("crudservice");
		searchEngine = (SearchEngine) context.getBean("searchEngine");
		criteria = searchEngine.createCriteria();
		criteriaCpt = searchEngine.createCriteria();
		criteriaChq = searchEngine.createCriteria();
		expression = searchEngine.createExpression();
		reglementChequeVo = new ReglementChequeVo();
		hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
	}

	public IValueObject perform(IValueObject vo) {
		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);

		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
		CompensationVo compensationVo = (CompensationVo) vo;

		GetListChequesRecusTrt getListChequesRecusTrt = new GetListChequesRecusTrt();

		Long montGlobchq33 = Long.valueOf(0);
		Long nbrGlobchq33 = Long.valueOf(0);

		try {
			ListChequesRecusVo listChequesRecusVo = new ListChequesRecusVo();
			listChequesRecusVo.setDateComptable(compensationVo.getDateComptable());
			listChequesRecusVo.setTypeCheque(Long.valueOf(33));
			listChequesRecusVo.setStructure(compensationVo.getStrutcure().getCodStrcStrc().toString());

			// getListChequesRecusTrt.exec(listChequesRecusVo);

			listChequesRecusVo = (ListChequesRecusVo) getListChequesRecusTrt.exec(listChequesRecusVo);

			if (listChequesRecusVo.getListCheques33() != null && listChequesRecusVo.getListCheques33().size() > 0) {
				for (Iterator it = listChequesRecusVo.getListCheques33().iterator(); it.hasNext();) {
					prbProvision = false;

					Cheque33 chq33 = (Cheque33) it.next();

					mntChqInf20 = (chq33.getMntChq() <= 20000L);

					montGlobchq33 = montGlobchq33 + chq33.getMntChq();
					nbrGlobchq33 = nbrGlobchq33 + 1;

					ContratCptId cptId = new ContratCptId();
					cptId.setCodStrcStrc(Long.valueOf(chq33.getRibTir().substring(5, 8)));
					cptId.setCodPrdPrd(Long.valueOf(chq33.getRibTir().substring(8, 12)));
					cptId.setNumCcptCcpt(Long.valueOf(chq33.getRibTir().substring(12, 18)));
					ContratCpt contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, cptId);
					System.out.println("COMPTE = "+cptId.getCompteClient());
					// if (contratCpt != null) {

					/**********************************
					 * Test if cheque possede un Blocage
					 **********************/

					ContratCpt contratCptBloc = new ContratCpt();
					contratCptBloc.setContratCptId(cptId);

					BlocageCheque blocageCheque = new BlocageCheque();

					blocageCheque.setContratCpt(contratCptBloc);
					blocageCheque.setNumChqChq(chq33.getNumChq());

					BlocageChqVo blocageChqVo = new BlocageChqVo();
					blocageChqVo.setBlocageCheque(blocageCheque);
					blocageChqVo.setTypeBlocage("CHQ");

					GetBolcagePourChqTrt getBolcagePourChqTrt = new GetBolcagePourChqTrt();
					blocageChqVo = (BlocageChqVo) getBolcagePourChqTrt.exec(blocageChqVo);

					/**********************************
					 * Test if cheque certifier
					 **********************************/
					CompteInterneId compteInterneId = new CompteInterneId(0L, cptId.getCodStrcStrc(), 141L);
					CompteInterne compteInterne = new CompteInterne();
					compteInterne.setCompteInterneId(compteInterneId);
					IExpression expressionCertif = this.searchEngine.createExpression();
					ICriteria criteriaCertif = this.searchEngine.createCriteria();
					criteriaCertif.add(expressionCertif.eq("compteInterne", compteInterne));
					criteriaCertif.add(expressionCertif.eq("numChqCchq", chq33.getNumChq()));
					criteriaCertif.add(expressionCertif.eq("codEtatCchq", 1L));
					criteriaCertif.add(expressionCertif.eq("montCertCchq", chq33.getMntChq()));
					criteriaCertif.add(expressionCertif.eq("codPayCchq", 0L));
					List listCertificationClient = this.searchEngine.find(CertificationCheques.class, criteriaCertif);

					/**********************************
					 * controle valeur deja regle ou rejete
					 **********************/
					IExpression expression = searchEngine.createExpression();
					ICriteria criteriaChq = searchEngine.createCriteria();
					criteriaChq.add(expression.eq("chequeId.numChqChq", chq33.getNumChq()));
					criteriaChq.add(expression.eq("chequeId.ribTirChq", chq33.getRibTir()));
					// criteriaChq.add(expression.eq("chequeId.ribBenChq", chq33.getRibBen()));
					List<Cheque> listChq = searchEngine.find(Cheque.class, criteriaChq);

					ChequeId chequeId = new ChequeId(chq33.getNumChq(), chq33.getRibTir(), chq33.getRibBen());

					Cheque chq = new Cheque();
					chq.setChequeId(chequeId);
					chq.setMntChqChq(chq33.getMntChq());

					reglementChequeVo = new ReglementChequeVo();
					reglementChequeVo.setCheque(chq);
					reglementChequeVo.setContratCpt(contratCpt);
					reglementChequeVo.setDateComptable(listChequesRecusVo.getDateComptable());
					reglementChequeVo.setCheque(chq);
					reglementChequeVo.setMontantARegler(chq33.getMntChq());
					reglementChequeVo = testerProvision(reglementChequeVo);

					/*** DEBUT TEST CHEQUIER PLAFOND/VALIDITE / EXISTANCE SANS CERTIF && SANS Blocage ****/
					if ((listCertificationClient == null || listCertificationClient.size() == 0)
							&& blocageChqVo.getSommeBlocage() == 0L) {
						List<Chequier> listeChequier = new ArrayList<Chequier>();
						if (contratCpt != null && contratCpt.getContratCptId() != null) {

							listeChequier = compensationDAO.getInfoChequierByCriteres(contratCpt, chq33.getNumChq());
						}
						if (listeChequier != null && listeChequier.size() != 0) {

							Chequier chequier = listeChequier.get(0);

							/****** TEST CHEQUE NOUVEAU FORMAT ***********/
							if (chequier != null && chequier.getChequierId() != null
									&& chequier.getCleSecPeccChqi() != null
									&& chequier.getCleSecPeccChqi().length() != 0
									&& chequier.getMntPlafChqi() != null) {

								if (chequier.getMntPlafChqi() < chq33.getMntChq()) {

									chq33 = this.setCodRej(chq33, "61");
									chq33 = this.setcodEtatChq(chq33, false);
									// this.update(chq30);
								}

								if (CalanderHandler.GetNextWorkingDayAfterNdays(chequier.getDatExpChqi(), 8)
										.compareTo(chq33.getDatOpe()) < 0) {

									chq33 = this.setCodRej(chq33, "62");
									chq33 = this.setcodEtatChq(chq33, false);
									// this.update(chq30);

								}

							} else {
								/****** CHEQUE ANCIEN FORMAT ***********/

								chq33 = this.setCodRej(chq33, "64");
								chq33 = setcodEtatChq(chq33, false);
								this.update(chq33);
								continue;
							}
						} else {

							chq33 = this.setCodRej(chq33, "64");
							chq33 = setcodEtatChq(chq33, false);
							this.update(chq33);
							continue;

						}
					}
					/*** FIN TEST CHEQUIER PLAFOND/VALIDITE / EXISTANCE ****/

					if ((listChq != null) && (listChq.size() > 0)) {

						Cheque cheque = (Cheque) listChq.get(0);
						if (isDejaPaye(cheque)) {
							chq33 = setCodRej(chq33, Constants.COD_MREJ_CHQ_DEJ_REGL);
							// chq33.setCodEtatChq(Constants.COD_ETAT_CHQ_TMP_POSITIONE);
							chq33 = setcodEtatChq(chq33, true);
							// crudService.update(chq33);
							update(chq33);
							continue;

						} else {
							if (cheque.getCodRejChq() != null && isPap(cheque)) {
								if (cheque.getCodFprocChq() != null
										&& cheque.getCodFprocChq().equals(Long.valueOf(1))) {

									chq33 = setCodRej(chq33, Constants.COD_MREJ_CHQ_DEJ_REJ);
									// chq33.setCodEtatChq(Constants.COD_ETAT_CHQ_TMP_POSITIONE);
									chq33 = setcodEtatChq(chq33, false);
									// crudService.update(chq33);
									update(chq33);
									continue;

								}
							} else { /** cod_rej !=84 **/
								chq33 = setCodRej(chq33, Constants.COD_MREJ_CHQ_MAL_PRES);

							}
						}

					} else {/** premiere presentation avec 33 ! **/
						chq33 = setCodRej(chq33, Constants.COD_MREJ_CHQ_MAL_PRES);
					}

					/*********************************************
					 * controle deces en cas personne physique
					 *********************************************/

					if ((contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE))
							&& (contratCpt.getClient().getPersonne().getDatDecePers() != null)) {
						if (contratCpt.getClient().getPersonne().getDatDecePers().compareTo(chq33.getDatEmi()) < 0) {
							chq33 = setCodRej(chq33, Constants.COD_MREJ_CHQ_DECE_PERS);
							// chq33.setCodEtatChq(Constants.COD_ETAT_CHQ_TMP_POSITIONE);
							chq33 = setcodEtatChq(chq33, true);
							// crudService.update(chq33);
							update(chq33);
							continue;

						}
					}

					/*********************************************
					 * controle valeur prescrite
					 *********************************************/

					Long nbrjourPre =
							Double.valueOf(DateHandler.getDaysBetween(chq33.getDatEmi(), new Date())).longValue();
					if (nbrjourPre > 1103) {
						chq33 = setCodRej(chq33, Constants.COD_MREJ_CHQ_PERSCRITE);
						// chq33.setCodEtatChq(Constants.COD_ETAT_CHQ_TMP_POSITIONE);
						chq33 = setcodEtatChq(chq33, true);
						// crudService.update(chq33);
						update(chq33);
						continue;

					}

					/***********************************************
					 * controle opposition
					 ******************************************/

					ParamRechercheOpposition paramRechercheOpposition = new ParamRechercheOpposition();
					GetListOppositionTrt getListOppositionTrt = new GetListOppositionTrt();
					String rejOpp = "";
					paramRechercheOpposition.setCodPrdPrd(cptId.getCodPrdPrd());
					paramRechercheOpposition.setCodStrcStrc(cptId.getCodStrcStrc());
					paramRechercheOpposition.setNumCcptCcpt(cptId.getNumCcptCcpt());
					paramRechercheOpposition.setNumMoypTmoy(chq33.getNumChq().toString());
					paramRechercheOpposition.setTypeMoyPaie(Constants.COD_MOYP_TMOY_Cheque);
					// paramRechercheOpposition.setTypeOper(Constants.COD_ETAT_OPMP_Opposition);
					Listes listeOppositionsMoyPaie = (Listes) getListOppositionTrt.exec(paramRechercheOpposition);
					List<OppositionMoyenPaiement> listOppositionMoyenPaiement =
							new ArrayList<OppositionMoyenPaiement>();
					if (listeOppositionsMoyPaie.getList() != null && listeOppositionsMoyPaie.getList().size() > 0) {
						for (Iterator iterator = listeOppositionsMoyPaie.getList().iterator(); iterator.hasNext();) {
							OppositionMoyenPaiement oppositionMoyenPaiement = (OppositionMoyenPaiement) iterator.next();
							listOppositionMoyenPaiement.add(oppositionMoyenPaiement);
						}
						Collections.sort(listOppositionMoyenPaiement, new Comparator<OppositionMoyenPaiement>() {

							public int compare(OppositionMoyenPaiement o1, OppositionMoyenPaiement o2) {
								return o2.getOppositionMoyenPaiementId().getDatOperOpmp()
										.compareTo(o1.getOppositionMoyenPaiementId().getDatOperOpmp());
							}
						});

						OppositionMoyenPaiement oppositionMoyenPaiement = listOppositionMoyenPaiement.get(0);
						if (oppositionMoyenPaiement.getCodMotfOpmp() != null) {
							if (oppositionMoyenPaiement.getCodEtatOpmp().equals(Constants.COD_ETAT_OPMP_Opposition)) {

								if (oppositionMoyenPaiement.getCodMotfOpmp()
										.equalsIgnoreCase(Constants.COD_ETAT_OPP_CHQ_PERT)) {
									rejOpp = Constants.COD_MREJ_CHQ_OPP_PERT;
								} else if (oppositionMoyenPaiement.getCodMotfOpmp()
										.equalsIgnoreCase(Constants.COD_ETAT_OPP_CHQ_VOL)) {
									rejOpp = Constants.COD_MREJ_CHQ_OPP_VOL;
								} else if (oppositionMoyenPaiement.getCodMotfOpmp()
										.equalsIgnoreCase(Constants.COD_ETAT_OPP_CHQ_FAIL)) {
									rejOpp = Constants.COD_MREJ_CHQ_OPP_FAIL;
								} else if (oppositionMoyenPaiement.getCodMotfOpmp()
										.equalsIgnoreCase(Constants.COD_ETAT_OPP_CHQ_AUT)) {
									rejOpp = Constants.COD_MREJ_CHQ_OPP_AUT;
								}
							}
						}

						if (!rejOpp.equals("")) {
							chq33 = setCodRej(chq33, rejOpp);
						}
					}

					// ne tester pas si montant < 20 et compte en dinar
					if (!mntChqInf20) {
						/****************************************************
						 * controle compte cloture
						 ****************************************************/

						if (contratCpt.getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_RESILIE)) {
							prbProvision = true;
							chq33 = setCodRej(chq33, Constants.COD_MREJ_CHQ_COMP_CLOT);
							chq33 = setCodRej(chq33, Constants.COD_MREJ_CHQ_ABS_PROV);
						}

						// CTX
						if (!(contratCpt.getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_RESILIE))
								&& !(contratCpt.getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_VALID))) {
							prbProvision = true;
							chq33 = setCodRej(chq33, Constants.COD_MREJ_CHQ_COMP_CLOT);
							chq33 = setCodRej(chq33, Constants.COD_MREJ_CHQ_INDISP_PROV);
						}

					}

					/********************************************************
					 * controle provision
					 ********************************************************/

					if (!reglementChequeVo.isProvisionDisponible()) {
						// si pas de provision
						if (mntChqInf20 && UtilCtr.isDinarConvertible(contratCpt)) {
							chq33 = setCodRej(chq33, "14");
						}
						if (reglementChequeVo.getProvision() > 0) {
							if (!prbProvision) {
								chq33 = setCodRej(chq33, Constants.COD_MREJ_CHQ_INSUFF_PROV);
							}
						} else {
							if (!prbProvision) {

								chq33 = setCodRej(chq33, Constants.COD_MREJ_CHQ_ABS_PROV);
							}
						}
					}

					// if(chq33.getCodRej1()==null&&chq33.getCodRej2()==null&&chq33.getCodRej3()==null&&chq33.getCodRej4()==null
					// && chq33.getCodEtatChq()!=null &&chq33.getCodEtatChq().equals("T"))
					// {
					// reglement(chq33, contratCpt, listChequesRecusVo);
					// chq33.setCodEtatChq("R");
					// }

					// {
					// chq33.setCodEtatChq(Constants.COD_ETAT_CHQ_TMP_POSITIONE);

					// }
					chq33 = setcodEtatChq(chq33, false);
					// crudService.update(chq33);
					update(chq33);
				}

			}
			compensationVo.setMontGlobChq33(montGlobchq33);
			compensationVo.setNbrGlobChq33(nbrGlobchq33);
		} catch (Exception e) {
			Error erreur = new Error();
			StringBuffer text = new StringBuffer("Erreur dans PositionCheque33Trt : ");
			text.append(e.toString());
			erreur.setCode("500");
			erreur.setDescription(text.toString());
			erreur.setKey("PositionCheque33Trt");
			logger.error("Exception : ", e);
			compensationVo.addError(erreur);
			throw new RuntimeException(e);
		}
		return compensationVo;
	}

	public void genCroText(ValueObject valueobject) {
	}

	public String getNumeroTache(ValueObject vo) {
		return "120";
	}

	public Cheque33 setCodRej(Cheque33 v30, String motRej) {
		if (v30.getCodRej1() == null) {
			v30.setCodRej1(motRej);
		} else if (v30.getCodRej2() == null) {
			v30.setCodRej2(motRej);
		} else if (v30.getCodRej3() == null) {
			v30.setCodRej3(motRej);
		} else if (v30.getCodRej4() == null) {
			v30.setCodRej4(motRej);
		}
		return v30;
	}

	public boolean isDejaPaye(Cheque chq) {
		return chq.getCodEtatChq().equalsIgnoreCase("P");
	}

	public boolean isPap(Cheque chq) {
		return chq.getCodRejChq().getCodValVal().toString().equals("84");
	}

	public ReglementChequeVo testerProvision(ReglementChequeVo reglementVo) {
		TesterProvisionTrt trt = new TesterProvisionTrt();
		reglementVo = (ReglementChequeVo) trt.exec(reglementVo);
		return reglementVo;
	}

	// public Cheque33 reglement(Cheque33 chq33, ContratCpt contratCpt, ListChequesRecusVo listChequesRecusVo) {
	// criteria = searchEngine.createCriteria();
	// criteriaCpt = searchEngine.createCriteria();
	// criteriaChq = searchEngine.createCriteria();
	// expression = searchEngine.createExpression();
	// criteria.add(expression.eq("chequeId.numChqChq", chq33.getNumChq()));
	// criteria.add(expression.eq("chequeId.ribTirChq", chq33.getRibTir()));
	// criteria.add(expression.eq("chequeId.ribBenChq", chq33.getRibBen()));
	// List liste = searchEngine.find(Cheque.class, criteria);
	// Cheque cheque = (Cheque) liste.get(0);
	// Valeur valeur = new Valeur(Long.valueOf(33L));
	// cheque.setValeur(valeur);
	// cheque.setDatOpeChq(chq33.getDatOpe());
	// cheque.setCodEtatChq("P");
	// cheque.setDatRegChq(chq33.getDatOpe());
	// crudService.update(cheque);
	// TraceChequeId traceChequeId = new TraceChequeId();
	// traceChequeId.setDatOpeChq(chq33.getDatOpe());
	// traceChequeId.setCodValVal(chq33.getCodVal());
	// traceChequeId.setNumChqChq(cheque.getChequeId().getNumChqChq().longValue());
	// traceChequeId.setRibBenChq(cheque.getChequeId().getRibBenChq());
	// traceChequeId.setRibTirChq(cheque.getChequeId().getRibTirChq());
	// TraceCheque traceCheque = new TraceCheque();
	// traceCheque.setCheque(cheque);
	// traceCheque.setTraceChequeId(traceChequeId);
	// Valeur valeurr = new Valeur();
	// valeurr.setCodValVal(chq33.getCodVal());
	// traceCheque.setValeur(valeurr);
	// traceCheque.setNumLotTch(cheque.getNumLotChq());
	// traceCheque.setMntChqTch(cheque.getMntChqChq());
	// traceCheque.setNomPrnTch(cheque.getNomPrnChq());
	// traceCheque.setDatEmiTch(cheque.getDatEmiChq());
	// traceCheque.setCodSenTch(Long.valueOf(2L));
	// traceCheque.setCodEnrTch(cheque.getCodEnrChq());
	// traceCheque.setCodBanTch(cheque.getCodBadeChq());
	// traceCheque.setCodAgedTch(cheque.getCodAgdeChq());
	// traceCheque.setCodBandTch(cheque.getCodBaemChq());
	// traceCheque.setCodLieuTch(cheque.getCodLemiChq());
	// traceCheque.setCodSitTch(cheque.getCodSbenChq());
	// traceCheque.setCodNatcTch(cheque.getCodNcptChq());
	// traceCheque.setCodNateTch(cheque.getCodNateChq());
	// traceCheque.setCodDevTch(cheque.getDevise().getCodDevDev().toString());
	// traceCheque.setCodValTch(null);
	// traceCheque.setRefFicTch(cheque.getRefFicChq());
	// traceCheque.setRibTirRecTch(cheque.getRibTrecChq());
	// traceCheque.setCodMotrTch(null);
	// traceCheque.setNumEvtEnvTch(cheque.getNumEvenChq());
	// traceCheque.setNumEvtRcpTch(cheque.getNumEvrcpChq());
	// traceCheque.setRjtRegTch(cheque.getRjtRegChq());
	// traceCheque.setCodNatcTch(cheque.getCodNateChq());
	// crudService.create(traceCheque);
	// contratCpt = UtilCtr.getContratCptByRIB(chq33.getRibTir());
	// ParamAgence paramAgence = new ParamAgence();
	// paramAgence.setCodStrcStrc(Long.valueOf(listChequesRecusVo.getStructure()));
	// paramAgence.setDateComptable(DateHandler.dateToStr(listChequesRecusVo.getDateComptable()));
	// paramAgence.setNumMatrUser("9999");
	// reglementChequeVo.setParamAgence(paramAgence);
	// reglementChequeVo.setCodValVal(chq33.getCodVal());
	// reglementChequeVo.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
	// reglementChequeVo.setCodeOperation(Long.valueOf(806L));
	// reglementChequeVo.setContratCpt(contratCpt);
	// ReglementChequeTrt reglementChequeTrt = new ReglementChequeTrt();
	// reglementChequeVo = (ReglementChequeVo) reglementChequeTrt.exec(reglementChequeVo);
	// return chq33;
	// }
	//
	public Cheque33 setcodEtatChq(Cheque33 chq30, boolean finProc) {
		if (finProc) {
			chq30.setCodEtatChq("B");
		}
		if (chq30.getCodEtatChq() == null) {
			chq30.setCodEtatChq("P");
		} else if (chq30.getCodEtatChq().equals("T")) {
			chq30.setCodEtatChq("B");
		}
		return chq30;
	}

	public Cheque33 update(Cheque33 chq30) {
		Cheque33 cheque30 = chq30;
		hibernateTemplate.evict(cheque30);
		Cheque33 cheque30New = (Cheque33) searchEngine.loadForUpdate(Cheque33.class, cheque30.getCheque33Id());
		if (cheque30.getCodRej1() != null) {
			cheque30New = updateRejCheque33(cheque30New, cheque30.getCodRej1());
		}
		if (cheque30.getCodRej2() != null) {
			cheque30New = updateRejCheque33(cheque30New, cheque30.getCodRej2());
		}
		if (cheque30.getCodRej3() != null) {
			cheque30New = updateRejCheque33(cheque30New, cheque30.getCodRej3());
		}
		if (cheque30.getCodRej4() != null) {
			cheque30New = updateRejCheque33(cheque30New, cheque30.getCodRej4());
		}
		if (cheque30.getCodEtatChq() != null && cheque30.getCodEtatChq().equals("R")) {
			cheque30New.setCodEtatChq("R");
		} else if (cheque30New.getCodEtatChq() == null) {
			cheque30New.setCodEtatChq("P");
		} else if (cheque30New.getCodEtatChq().equals("T")) {
			cheque30New.setCodEtatChq("B");
		}
		cheque30New.setMntLettres(cheque30.getMntLettres());
		hibernateTemplate.update(cheque30New);
		hibernateTemplate.flush();
		return cheque30New;
	}

	public Cheque33 updateRejCheque33(Cheque33 v30, String motRej) {
		String motExits[] = { v30.getCodRej1(), v30.getCodRej2(), v30.getCodRej3(), v30.getCodRej4() };
		if (!Arrays.asList(motExits).contains(motRej)) {
			if (v30.getCodRej1() == null) {
				v30.setCodRej1(motRej);
			} else if (v30.getCodRej2() == null) {
				v30.setCodRej2(motRej);
			} else if (v30.getCodRej3() == null) {
				v30.setCodRej3(motRej);
			} else if (v30.getCodRej4() == null) {
				v30.setCodRej4(motRej);
			}
		}
		return v30;
	}
}
