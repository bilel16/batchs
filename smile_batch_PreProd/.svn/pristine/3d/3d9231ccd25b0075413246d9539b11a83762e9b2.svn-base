package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import com.bna.commun.model.CertificationCheques;
import com.bna.commun.model.Cheque;
import com.bna.commun.model.Cheque30;
import com.bna.commun.model.ChequeId;
import com.bna.commun.model.Chequier;
import com.bna.commun.model.CompteInterne;
import com.bna.commun.model.CompteInterneId;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Devise;
import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceCheque;
import com.bna.commun.model.TraceChequeId;
import com.bna.commun.model.Valeur;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementChequeVo;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
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
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class PositionCheque30Trt extends Traitement {

	Context context = ContextHandler.getContext();
	CRUDservice crudService;
	ISearchEngine searchEngine;
	boolean mntChqInf20;
	ReglementChequeVo reglementChequeVo;
	HibernateTemplate hibernateTemplate;
	CompensationVo compensationVo = null;

	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");

	public PositionCheque30Trt() {
		this.crudService = (CRUDservice) this.context.getBean("crudservice");
		this.searchEngine = (SearchEngine) this.context.getBean("searchEngine");
		this.mntChqInf20 = false;
		this.reglementChequeVo = new ReglementChequeVo();
		this.hibernateTemplate = (HibernateTemplate) this.context.getBean("hibernateTemplate");
	}

	public IValueObject perform(IValueObject vo) {
		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
		this.compensationVo = (CompensationVo) vo;
		GetListChequesRecusTrt getListChequesRecusTrt = new GetListChequesRecusTrt();
		Long montGlobChq30 = 0L;
		Long nbrGlobChq30 = 0L;
		boolean prbProvision = false;
		Long count = 0L;
		Long total = 0L;
		Long nbreChqDev = 1L;

		try {
			ListChequesRecusVo listChequesRecusVo = new ListChequesRecusVo();
			listChequesRecusVo.setDateComptable(this.compensationVo.getDateComptable());
			listChequesRecusVo.setTypeCheque(30L);
			listChequesRecusVo.setStructure(
					StrHandler.lpad(this.compensationVo.getStrutcure().getCodStrcStrc().toString(), '0', 3));
			getListChequesRecusTrt.exec(listChequesRecusVo);
			listChequesRecusVo = (ListChequesRecusVo) getListChequesRecusTrt.exec(listChequesRecusVo);
			if (listChequesRecusVo.getListCheques30() != null && listChequesRecusVo.getListCheques30().size() > 0) {
				total = (long) listChequesRecusVo.getListCheques30().size();
				System.out.println("AGENCE : " + this.compensationVo.getStrutcure().getCodStrcStrc()
						+ " ---> TOTAL CHEQUE 30 :=>" + total);
			}

			// Iterator it = listChequesRecusVo.getListCheques30().iterator();
			// while (it.hasNext() {
			for (Cheque30 chq30 : listChequesRecusVo.getListCheques30()) {
				count = count + 1L;
				prbProvision = false;

				this.mntChqInf20 = chq30.getMntChq() <= 20000L;
				this.compensationVo.getBatch().getMsgDetailChq().setText(
						"Cheque : " + count + "/" + total + "  [Valeur :30,Numero :" + chq30.getNumChq() + "]");
				montGlobChq30 = montGlobChq30 + chq30.getMntChq();
				nbrGlobChq30 = nbrGlobChq30 + 1L;
				System.out.println("position cheque [" + chq30.getNumChq() + "]");
				ContratCptId cptId = new ContratCptId();
				if (chq30.getCheque30Id().getNum().toString().substring(0, 3).equals("141")) {
					CompteInterneId compteInterneId =
							new CompteInterneId(0L, this.compensationVo.getStrutcure().getCodStrcStrc(), 141L);
					CompteInterne compteInterne = new CompteInterne();
					compteInterne.setCompteInterneId(compteInterneId);

					// IExpression expressionCertif = this.searchEngine.createExpression();
					// ICriteria criteriaCertif = this.searchEngine.createCriteria();
					// criteriaCertif.add(expressionCertif.eq("compteInterne", compteInterne));
					// criteriaCertif.add(expressionCertif.eq("numChqCchq", chq30.getNumChq()));
					// criteriaCertif.add(expressionCertif.eq("codEtatCchq", 1L));
					// criteriaCertif.add(expressionCertif.eq("montCertCchq", chq30.getMntChq()));
					// criteriaCertif.add(expressionCertif.eq("codPayCchq", 0L));
					// List l = this.searchEngine.find(CertificationCheques.class, criteriaCertif);
					List<CertificationCheques> listeChequeCertif = compensationDAO.listeChequeCertifiesAgence(
							this.compensationVo.getStrutcure().getCodStrcStrc(), chq30.getNumChq(), chq30.getMntChq());
					if (!listeChequeCertif.isEmpty()) {
						this.reglementCheque(chq30, listChequesRecusVo, true);
						CertificationCheques certification = (CertificationCheques) listeChequeCertif.get(0);
						certification.setCodPayCchq(1L);
						certification.setCodEtatCchq(4L);
						certification.setDatPayCchq(listChequesRecusVo.getDateComptable());
						crudService.update(certification);
					} else {
						chq30.setCodEtatChq("B");
						chq30 = this.setcodEtatChq(chq30, true);
						chq30.setCodRej1("39");
						crudService.update(chq30);
					}
				} else {

					/******** Produit !=141 ***************/

					cptId.setCodStrcStrc(Long.valueOf(chq30.getRibTir().substring(5, 8)));
					cptId.setCodPrdPrd(Long.valueOf(chq30.getRibTir().substring(8, 12)));
					cptId.setNumCcptCcpt(Long.valueOf(chq30.getRibTir().substring(12, 18)));
					ContratCpt contratCpt = (ContratCpt) this.searchEngine.get(ContratCpt.class, cptId);
					this.reglementChequeVo = new ReglementChequeVo();
					this.reglementChequeVo.setContratCpt(contratCpt);
					this.reglementChequeVo.setDateComptable(listChequesRecusVo.getDateComptable());
					ChequeId chequeId = new ChequeId(chq30.getNumChq(), chq30.getRibTir(), chq30.getRibBen());
					Cheque chq = new Cheque();
					chq.setChequeId(chequeId);
					chq.setMntChqChq(chq30.getMntChq());
					this.reglementChequeVo.setCheque(chq);
					this.reglementChequeVo.setMontantARegler(chq30.getMntChq());
					if (contratCpt == null) {
						chq30.setCodEtatChq("B");
						chq30 = this.setcodEtatChq(chq30, true);
						chq30.setCodRej1("39");
						this.update(chq30);
					} else {

						this.reglementChequeVo = this.testerProvision(this.reglementChequeVo);
						if (!contratCpt.getDevise().getCodDevDev().toString().equals("" + Constants.COD_DEV_DINAR)) {

							/*********** Compte En DEVISE ************/

							chq30.setNbreChqDev(nbreChqDev);
							System.out.println("position cheque devise  [" + chq30.getNumChq() + "] , Devise["
									+ contratCpt.getDevise().getCodDevDev().toString() + "]");
							PositionCheque30DevTrt posDev = new PositionCheque30DevTrt();
							posDev.exec(chq30);
							nbreChqDev = nbreChqDev + 4L;
						} else {
							CompteInterneId compteInterneId =
									new CompteInterneId(0L, this.compensationVo.getStrutcure().getCodStrcStrc(), 141L);
							CompteInterne compteInterne = new CompteInterne();
							compteInterne.setCompteInterneId(compteInterneId);
							// IExpression expressionCertif = this.searchEngine.createExpression();
							// ICriteria criteriaCertif = this.searchEngine.createCriteria();
							// criteriaCertif.add(expressionCertif.eq("compteInterne", compteInterne));
							// criteriaCertif.add(expressionCertif.eq("numChqCchq", chq30.getNumChq()));
							// criteriaCertif.add(expressionCertif.eq("codEtatCchq", 1L));
							// criteriaCertif.add(expressionCertif.eq("montCertCchq", chq30.getMntChq()));
							// criteriaCertif.add(expressionCertif.eq("codPayCchq", 0L));
							// List listCertificationClient =
							// this.searchEngine.find(CertificationCheques.class, criteriaCertif);

							List<CertificationCheques> listeChequeCertifClt = compensationDAO
									.listeChequeCertifies(chq30.getNumChq(), chq30.getMntChq(), contratCpt);
							/*** DEBUT TEST CHEQUIER PLAFOND/VALIDITE / EXISTANCE SANS CERTIF ****/
							if (listeChequeCertifClt == null || listeChequeCertifClt.size() == 0) {
								List<Chequier> listeChequier =
										compensationDAO.getInfoChequierByCriteres(contratCpt, chq30.getNumChq());
								if (listeChequier != null && listeChequier.size() != 0) {

									Chequier chequier = listeChequier.get(0);

									/****** TEST CHEQUE NOUVEAU FORMAT ***********/
									if (chequier != null && chequier.getChequierId() != null
											&& chequier.getCleSecPeccChqi() != null
											&& chequier.getCleSecPeccChqi().length() != 0
											&& chequier.getMntPlafChqi() != null) {

										if (chequier.getMntPlafChqi() < chq30.getMntChq()) {

											chq30 = this.setCodRej(chq30, "61");
											chq30 = this.setcodEtatChq(chq30, false);
											// this.update(chq30);
										}

										if (CalanderHandler.GetNextWorkingDayAfterNdays(chequier.getDatExpChqi(), 8)
												.compareTo(chq30.getDatOpe()) < 0) {

											chq30 = this.setCodRej(chq30, "62");
											chq30 = this.setcodEtatChq(chq30, false);
											// this.update(chq30);

										}

									} else {
										/****** CHEQUE ANCIEN FORMAT ***********/

										chq30 = this.setCodRej(chq30, "64");
										chq30 = this.setcodEtatChq(chq30, false);
										this.update(chq30);
										continue;

									}
								} else {

									chq30 = this.setCodRej(chq30, "64");
									chq30 = this.setcodEtatChq(chq30, false);
									this.update(chq30);
									continue;

								}
							}
							/*** FIN TEST CHEQUIER PLAFOND/VALIDITE / EXISTANCE ****/

							/*********** Compte En dinars ************/
							IExpression expression = this.searchEngine.createExpression();
							ICriteria criteriaChq = this.searchEngine.createCriteria();
							criteriaChq.add(expression.eq("chequeId.numChqChq", chq30.getNumChq()));
							criteriaChq.add(expression.eq("chequeId.ribTirChq", chq30.getRibTir()));
							List listChq = this.searchEngine.find(Cheque.class, criteriaChq);
							if (listChq != null && listChq.size() > 0) {
								Cheque cheque = (Cheque) listChq.get(0);
								if (cheque.getCodEtatChq().equalsIgnoreCase("P")) {
									chq30 = this.setCodRej(chq30, "43");
								} else {
									chq30 = this.setCodRej(chq30, "44");
								}

								chq30 = this.setcodEtatChq(chq30, true);
								this.update(chq30);
							} else if (contratCpt.getClient().getTypePers().getCodTperTper().equals("1")
									&& contratCpt.getClient().getPersonne().getDatDecePers() != null
									&& (contratCpt.getClient().getPersonne().getDatDecePers()
											.compareTo(chq30.getDatEmi()) < 0
											|| !this.reglementChequeVo.isProvisionDisponible())) {

								/*********** Client decede ************/
								chq30 = this.setCodRej(chq30, "20");
								chq30 = this.setcodEtatChq(chq30, true);
								this.update(chq30);
							} else {

								/*********** client actif ************/
								Long nbrjourPre = Double
										.valueOf(DateHandler.getDaysBetween(chq30.getDatEmi(), new Date())).longValue();
								if ((!chq30.getLieEmi().equals("T") || nbrjourPre <= 1103L)
										&& (!chq30.getLieEmi().equals("E") || nbrjourPre <= 1155L)) {
									/*********** Cheque non prescrit ************/

									/*********** Test Opposition ************/

									String rejOpp = "";
									List<OppositionMoyenPaiement> listOppositionMoyenPaiement =
											compensationDAO.listeOppositionMoyenPaiement(chq30.getNumChq().toString(),
													Constants.COD_MOYP_TMOY_Cheque, cptId);

									if (listOppositionMoyenPaiement != null && listOppositionMoyenPaiement.size() > 0) {

										OppositionMoyenPaiement oppositionMoyenPaiement1 =
												(OppositionMoyenPaiement) listOppositionMoyenPaiement.get(0);
										if (oppositionMoyenPaiement1.getCodMotfOpmp() != null
												&& oppositionMoyenPaiement1.getCodEtatOpmp().equals("O")) {
											if (oppositionMoyenPaiement1.getCodMotfOpmp().equalsIgnoreCase("P")) {
												rejOpp = "01";
											} else if (oppositionMoyenPaiement1.getCodMotfOpmp()
													.equalsIgnoreCase("V")) {
												rejOpp = "02";
											} else if (oppositionMoyenPaiement1.getCodMotfOpmp()
													.equalsIgnoreCase("F")) {
												rejOpp = "03";
											} else if (oppositionMoyenPaiement1.getCodMotfOpmp()
													.equalsIgnoreCase("A")) {
												rejOpp = "04";
											}
										}

										if (!rejOpp.equals("")) {
											chq30 = this.setCodRej(chq30, rejOpp);
										}
									}

									/*********** Fin Test Opposition ************/
									if (!this.mntChqInf20) {

										/*********** Test ETAT COMPTE CLOTURE ************/
										if (contratCpt.getCodEtatCcpt().equalsIgnoreCase("R")) {
											prbProvision = true;
											chq30 = this.setCodRej(chq30, "13");
											chq30 = this.setCodRej(chq30, "10");
										}
										/*********** FIN Test ETAT COMPTE CLOTURE ************/

										/*********** Test ETAT COMPTE BLOQUE/SEMI ACTIF/ CTX ************/
										if (!contratCpt.getCodEtatCcpt().equalsIgnoreCase("R")
												&& !contratCpt.getCodEtatCcpt().equalsIgnoreCase("V")) {
											prbProvision = true;
											chq30 = this.setCodRej(chq30, "13");
											chq30 = this.setCodRej(chq30, "12");
										}

										/*********** FIN Test ETAT COMPTE BLOQUE/SEMI ACTIF/ CTX ************/
									}

									if (listeChequeCertifClt.isEmpty()) {

										/*********** Test CHEQUE NON CERTIFIEE ************/
										if (!this.reglementChequeVo.isProvisionDisponible()) {
											if (this.mntChqInf20 && UtilCtr.isDinarConvertible(contratCpt)) {

												/*********** Test CHEQUE <20 Dinars avec Compte DCV ************/
												chq30 = this.setCodRej(chq30, "14");
											} else if (this.reglementChequeVo.getProvision() > 0L) {
												if (!prbProvision) {
													chq30 = this.setCodRej(chq30, "11");
												}
											} else if (!prbProvision) {
												chq30 = this.setCodRej(chq30, "10");
											}
										}
									} else {

										List<String> motifIgnoree = new ArrayList<String>();
										motifIgnoree.add("61");
										motifIgnoree.add("62");
										motifIgnoree.add("63");
										motifIgnoree.add("64");
										/******** Cheque Certifie **********/
										if ((chq30.getCodRej1() == null && chq30.getCodRej2() == null
												&& chq30.getCodRej3() == null && chq30.getCodRej4() == null)
												|| (motifIgnoree.contains(chq30.getCodRej1())
														|| motifIgnoree.contains(chq30.getCodRej2())
														|| motifIgnoree.contains(chq30.getCodRej3())
														|| motifIgnoree.contains(chq30.getCodRej4()))) {

											/*********** Test CHEQUE CERTIFIEE ************/
											this.reglementCheque(chq30, listChequesRecusVo, true);
											CertificationCheques certification =
													(CertificationCheques) listeChequeCertifClt.get(0);
											certification.setCodPayCchq(1L);
											certification.setCodEtatCchq(4L);
											certification.setDatPayCchq(listChequesRecusVo.getDateComptable());
											crudService.update(certification);
											continue;
											/*********** FIN Test CHEQUE CERTIFIEE ************/
										}
									}
									chq30 = this.setcodEtatChq(chq30, false);
									crudService.update(chq30);
								} else {

									/*********** Cheque prescrit ************/
									chq30 = this.setCodRej(chq30, "31");
									chq30 = this.setcodEtatChq(chq30, true);
									this.update(chq30);
								}
							}
						}
					}
				}
			}

			this.compensationVo.setMontGlobChq30(montGlobChq30);
			this.compensationVo.setNbrGlobChq30(nbrGlobChq30);
			return this.compensationVo;
		} catch (Exception var33) {
			this.compensationVo.getBatch().getMsgDetailChq().setForeground(Color.red);
			this.compensationVo.getBatch().getMsgDetailChq()
					.setText(this.compensationVo.getBatch().getMsgDetailChq().getText() + " Erreur !");
			var33.printStackTrace();
			Error erreur = new Error();
			StringBuffer text = new StringBuffer("Erreur dans PositionCheque30Trt : ");
			text.append(var33.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("PositionCheque33Trt");
			logger.error("Exception : ", var33);
			this.compensationVo.addError(erreur);
			throw new RuntimeException(var33);
		}
	}

	public void genCroText(ValueObject valueobject1) {
	}

	public String getNumeroTache(ValueObject vo) {
		return "120";
	}

	public Cheque30 setCodRej(Cheque30 v30, String motRej) {
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

	public void reglementCheque(Cheque30 chq30, ListChequesRecusVo listChequesRecusVo, boolean isCertifie) {
		if (isCertifie && chq30.getCheque30Id().getNum().toString().substring(0, 3).equals("141")) {
			this.compensationVo.setStrutcure(this.findStructure(this.compensationVo.getStrutcure().getCodStrcStrc()));
			chq30.setRibTir("03" + StrHandler.lpad(this.compensationVo.getStrutcure().getCodBctStrc(), '0', 3)
					+ StrHandler.lpad(listChequesRecusVo.getStructure(), '0', 3)
					+ StrHandler.rpad("0" + chq30.getCheque30Id().getNum().toString(), '0', 12));
		}

		System.out.println("******************************************reglement cheque [" + chq30.getNumChq() + "]");
		ParamAgence paramAgence = new ParamAgence();
		paramAgence.setCodStrcStrc(Long.valueOf(listChequesRecusVo.getStructure()));
		paramAgence.setDateComptable(DateHandler.dateToStr(listChequesRecusVo.getDateComptable()));
		ChequeId chequeId = new ChequeId();
		Cheque nouveauCheque = new Cheque();
		chequeId.setNumChqChq(chq30.getNumChq());
		chequeId.setRibBenChq(chq30.getRibBen());
		chequeId.setRibTirChq(chq30.getRibTir());
		nouveauCheque.setChequeId(chequeId);
		Valeur valeur = new Valeur();
		valeur.setCodValVal(30L);
		nouveauCheque.setValeur(valeur);
		Devise devise = new Devise();
		devise.setCodDevDev(Constants.COD_DEV_DINAR);
		nouveauCheque.setDevise(devise);
		nouveauCheque.setDatOpeChq(chq30.getDatOpe());
		nouveauCheque.setMntChqChq(chq30.getMntChq());
		nouveauCheque.setNomPrnChq(chq30.getNomBen());
		nouveauCheque.setDatEmiChq(chq30.getDatEmi());
		nouveauCheque.setCodNateChq(chq30.getNatRem().toString());
		if (chq30.getNatRem() == null) {
			logger.info("Attention codnatechq null default value : A");
			nouveauCheque.setCodNateChq("A");
		}

		nouveauCheque.setCodEnrChq(chq30.getCodEnr());
		nouveauCheque.setCodBaemChq(chq30.getCodInsDes());
		nouveauCheque.setCodAgemChq(chq30.getCodCenReg());
		nouveauCheque.setCodBadeChq("03");
		nouveauCheque.setCodAgdeChq(chq30.getCodCenRegDes());
		nouveauCheque.setCodLemiChq(chq30.getLieEmi());
		nouveauCheque.setCodSbenChq(chq30.getSitBen());
		nouveauCheque.setCodNcptChq(chq30.getNatCpt());
		nouveauCheque.setNumLotChq(chq30.getNumLot());
		nouveauCheque.setCodEtatChq("P");
		nouveauCheque.setRefFicChq("N");
		nouveauCheque.setNumEvenChq(0L);
		nouveauCheque.setNumEvrcpChq(0L);
		nouveauCheque.setRibTrecChq("N");
		nouveauCheque.setRjtRegChq("N");
		nouveauCheque.setDatRegChq(DateHandler.strToDate(paramAgence.getDateComptable()));
		this.crudService.create(nouveauCheque);
		TraceChequeId traceChequeId = new TraceChequeId();
		traceChequeId.setDatOpeChq(nouveauCheque.getDatOpeChq());
		traceChequeId.setCodValVal(30L);
		traceChequeId.setNumChqChq(nouveauCheque.getChequeId().getNumChqChq());
		traceChequeId.setRibBenChq(nouveauCheque.getChequeId().getRibBenChq());
		traceChequeId.setRibTirChq(nouveauCheque.getChequeId().getRibTirChq());
		TraceCheque traceCheque = new TraceCheque();
		traceCheque.setCheque(nouveauCheque);
		traceCheque.setTraceChequeId(traceChequeId);
		Valeur valeurr = new Valeur();
		valeurr.setCodValVal(30L);
		traceCheque.setValeur(valeurr);
		traceCheque.setNumLotTch(nouveauCheque.getNumLotChq());
		traceCheque.setMntChqTch(nouveauCheque.getMntChqChq());
		traceCheque.setNomPrnTch(nouveauCheque.getNomPrnChq());
		traceCheque.setDatEmiTch(nouveauCheque.getDatEmiChq());
		traceCheque.setCodSenTch(2L);
		traceCheque.setCodEnrTch(nouveauCheque.getCodEnrChq());
		traceCheque.setCodBanTch(nouveauCheque.getCodBadeChq());
		traceCheque.setCodAgedTch(nouveauCheque.getCodAgdeChq());
		traceCheque.setCodBandTch(nouveauCheque.getCodBaemChq());
		traceCheque.setCodLieuTch(nouveauCheque.getCodLemiChq());
		traceCheque.setCodSitTch(nouveauCheque.getCodSbenChq());
		traceCheque.setCodNatcTch(nouveauCheque.getCodNcptChq());
		traceCheque.setCodNateTch(nouveauCheque.getCodNateChq());
		traceCheque.setCodDevTch(nouveauCheque.getDevise().getCodDevDev().toString());
		traceCheque.setCodValTch(nouveauCheque.getValeur().getCodValVal());
		traceCheque.setRefFicTch(nouveauCheque.getRefFicChq());
		traceCheque.setRibTirRecTch(nouveauCheque.getRibTrecChq());
		traceCheque.setCodMotrTch(nouveauCheque.getCodMrejChq());
		traceCheque.setNumEvtEnvTch(nouveauCheque.getNumEvenChq());
		traceCheque.setNumEvtRcpTch(nouveauCheque.getNumEvrcpChq());
		traceCheque.setRjtRegTch(nouveauCheque.getRjtRegChq());
		traceCheque.setCodNatcTch("d");
		this.crudService.create(traceCheque);
		ReglementChequeVo reglementChequeVo = new ReglementChequeVo();
		ContratCpt contratCpt = UtilCtr.getContratCptByRIB(chq30.getRibTir());
		reglementChequeVo.setCheque(nouveauCheque);
		paramAgence.setNumMatrUser("9999");
		reglementChequeVo.setParamAgence(paramAgence);
		reglementChequeVo.setCodValVal(chq30.getCodVal());
		reglementChequeVo.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
		reglementChequeVo.setCodeOperation(Constants.COD_OPERATION_REGLEMENT_CHEQUE);
		reglementChequeVo.setMontantARegler(chq30.getMntChq());
		reglementChequeVo.setContratCpt(contratCpt);
		ReglementChequeTrt reglementChequeTrt = new ReglementChequeTrt();
		CroReglementChequeTrt croReglementChequeTrt = new CroReglementChequeTrt();
		if (isCertifie) {
			reglementChequeVo = (ReglementChequeVo) croReglementChequeTrt.exec(reglementChequeVo);
		} else {
			reglementChequeVo = (ReglementChequeVo) reglementChequeTrt.exec(reglementChequeVo);
		}

		chq30.setCodEtatChq("R");
		this.crudService.update(chq30);
	}

	public ReglementChequeVo testerProvision(ReglementChequeVo reglementVo) {
		TesterProvisionTrt trt = new TesterProvisionTrt();
		reglementVo = (ReglementChequeVo) trt.exec(reglementVo);
		return reglementVo;
	}

	public Cheque30 setcodEtatChq(Cheque30 chq30, boolean finProc) {
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

	public ContratCptId getContratCptIdFromCertif(Cheque30 chq30) {
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		IExpression expression = searchEngine.createExpression();
		ICriteria criteria = searchEngine.createCriteria();
		criteria.add(expression.eq("numChqCchq", chq30.getNumChq()));
		criteria.add(expression.eq("codEtatCchq", 1L));
		criteria.add(expression.eq("codPayCchq", 0L));
		criteria.add(expression.eq("codTypcCchq", "A"));
		List l = searchEngine.find(CertificationCheques.class, criteria);
		if (!l.isEmpty()) {
			Iterator iterator = l.iterator();

			while (iterator.hasNext()) {
				CertificationCheques cchq = (CertificationCheques) iterator.next();
				if (cchq.getMontCertCchq().toString().equals(chq30.getMntChq().toString())) {
					new ContratCptId();
					ContratCptId id = cchq.getContratCpt().getContratCptId();
					return id;
				}
			}
		}

		return null;
	}

	public Cheque30 update(Cheque30 chq30) {
		this.hibernateTemplate.evict(chq30);
		Cheque30 cheque30New = (Cheque30) this.searchEngine.loadForUpdate(Cheque30.class, chq30.getCheque30Id());
		if (chq30.getCodRej1() != null) {
			cheque30New = this.updateRejCheque30(cheque30New, chq30.getCodRej1());
		}

		if (chq30.getCodRej2() != null) {
			cheque30New = this.updateRejCheque30(cheque30New, chq30.getCodRej2());
		}

		if (chq30.getCodRej3() != null) {
			cheque30New = this.updateRejCheque30(cheque30New, chq30.getCodRej3());
		}

		if (chq30.getCodRej4() != null) {
			cheque30New = this.updateRejCheque30(cheque30New, chq30.getCodRej4());
		}

		if (chq30.getCodEtatChq() != null && chq30.getCodEtatChq().equals("R")) {
			cheque30New.setCodEtatChq("R");
		} else if (cheque30New.getCodEtatChq() == null) {
			cheque30New.setCodEtatChq("P");
		} else if (cheque30New.getCodEtatChq().equals("T")) {
			cheque30New.setCodEtatChq("B");
		}

		cheque30New.setMntLettres(chq30.getMntLettres());
		this.hibernateTemplate.update(cheque30New);
		this.hibernateTemplate.flush();
		return cheque30New;
	}

	public Cheque30 updateRejCheque30(Cheque30 v30, String motRej) {
		String[] motExits = new String[]{ v30.getCodRej1(), v30.getCodRej2(), v30.getCodRej3(), v30.getCodRej4() };
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

	public Structure findStructure(Long codStructure) {
		new Listes();
		new Structure();
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		ICriteria criteria = searchEngine.createCriteria();
		IExpression expression = searchEngine.createExpression();
		criteria.add(expression.eq("codStrcStrc", codStructure));
		List res = searchEngine.find(Structure.class, criteria);
		Structure structure = null;
		if (res != null && !res.isEmpty()) {
			structure = (Structure) res.get(0);
			if (structure.getCodBctStrc() != null) {
				structure.setCodBctStrc(StrHandler.lpad(structure.getCodBctStrc(), '0', 3));
			}
		}

		return structure;
	}
}