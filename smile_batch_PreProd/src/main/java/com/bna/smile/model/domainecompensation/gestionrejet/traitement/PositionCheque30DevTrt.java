package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.Cheque;
import com.bna.commun.model.Cheque30;
import com.bna.commun.model.ChequeId;
import com.bna.commun.model.Chequier;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Devise;
import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.model.TraceCheque;
import com.bna.commun.model.TraceChequeId;
import com.bna.commun.model.Valeur;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementChequeVo;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetListOppositionTrt;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * @author nbdour
 *
 */
public class PositionCheque30DevTrt extends Traitement {

	public PositionCheque30DevTrt() {
	}

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");

	boolean mntChqInf20 = false;

	@SuppressWarnings("rawtypes")
	public IValueObject perform(IValueObject vo) {
		Context context = ContextHandler.getContext();
		Cheque30 chq30 = (Cheque30) vo;
		mntChqInf20 = (chq30.getMntChq() <= 20000);
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		try {

			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(Long.valueOf(chq30.getRibTir().substring(5, 8)));
			cptId.setCodPrdPrd(Long.valueOf(chq30.getRibTir().substring(8, 12)));
			cptId.setNumCcptCcpt(Long.valueOf(chq30.getRibTir().substring(12, 18)));
			ContratCpt contratCpt = null;
			contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, cptId);

			if (contratCpt != null) {

				CheckCertifCheqTrt checkTrt = new CheckCertifCheqTrt();
				PrimitiveVO primitive = new PrimitiveVO();
				primitive = (PrimitiveVO) checkTrt.exec(chq30);

				/*** DEBUT TEST CHEQUIER PLAFOND/VALIDITE / EXISTANCE SANS CERTIF ****/
				if (!primitive.isVBool()) { // non certifié
					List<Chequier> listeChequier =
							compensationDAO.getInfoChequierByCriteres(contratCpt, chq30.getNumChq());
					if (listeChequier != null && listeChequier.size() != 0) {

						Chequier chequier = listeChequier.get(0);

						/****** TEST CHEQUE NOUVEAU FORMAT ***********/
						if (chequier != null && chequier.getChequierId() != null && chequier.getCleSecPeccChqi() != null
								&& chequier.getCleSecPeccChqi().length() != 0 && chequier.getMntPlafChqi() != null) {

							if (chequier.getMntPlafChqi() < chq30.getMntChq()) {

								chq30 = this.setCodRej(chq30, "61");
							}

							if (CalanderHandler.GetNextWorkingDayAfterNdays(chequier.getDatExpChqi(), 8)
									.compareTo(chq30.getDatOpe()) < 0) {

								chq30 = this.setCodRej(chq30, "62");

							}

						} else {
							/****** CHEQUE ANCIEN FORMAT ***********/
							chq30 = this.setCodRej(chq30, "64");
						}
					} else {
						chq30 = this.setCodRej(chq30, "64");
					}
				}
				/*** FIN TEST CHEQUIER PLAFOND/VALIDITE / EXISTANCE ****/

				boolean prbProvision = false;

				/**********************************
				 * controle valeur deja regle ou rejete 
				 **********************/
				IExpression expression = searchEngine.createExpression();
				ICriteria criteriaChq = searchEngine.createCriteria();

				criteriaChq.add(expression.eq("chequeId.numChqChq", chq30.getNumChq()));
				criteriaChq.add(expression.eq("chequeId.ribTirChq", chq30.getRibTir()));
				criteriaChq.add(expression.eq("chequeId.ribBenChq", chq30.getRibBen()));
				List<Cheque> listChq = searchEngine.find(Cheque.class, criteriaChq);
				if ((listChq != null) && (listChq.size() > 0)) {

					Cheque cheque = listChq.get(0);
					if (cheque.getCodEtatChq().equalsIgnoreCase(Constants.COD_ETAT_CHQ_PAYE)) // deja regle (43)
					{
						chq30 = setCodRej(chq30, Constants.COD_MREJ_CHQ_DEJ_REGL);

					} else // deja rejete (44)
					{
						chq30 = setCodRej(chq30, Constants.COD_MREJ_CHQ_DEJ_REJ);
					}

				}

				/*********************************************
				 * controle deces en cas personne physique ******************** FIN PROC
				 *****************/

				if ((contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE))
						&& (contratCpt.getClient().getPersonne().getDatDecePers() != null)) {
					if (contratCpt.getClient().getPersonne().getDatDecePers().compareTo(chq30.getDatEmi()) < 0) {
						chq30 = setCodRej(chq30, Constants.COD_MREJ_CHQ_DECE_PERS);
					}
				}

				/*********************************************
				 * controle valeur prescrite ********************************* FIN PROC
				 ***************/

				Long nbrjourPre = Double.valueOf(DateHandler.getDaysBetween(chq30.getDatEmi(), new Date())).longValue();
				if (nbrjourPre > 1103) {
					chq30 = setCodRej(chq30, Constants.COD_MREJ_CHQ_PERSCRITE);
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
				paramRechercheOpposition.setNumMoypTmoy(chq30.getNumChq().toString());
				paramRechercheOpposition.setTypeMoyPaie(Constants.COD_MOYP_TMOY_Cheque);
				paramRechercheOpposition.setTypeOper(Constants.COD_ETAT_OPMP_Opposition);
				Listes listeOppositionsMoyPaie = (Listes) getListOppositionTrt.exec(paramRechercheOpposition);
				List<OppositionMoyenPaiement> listOppositionMoyenPaiement = new ArrayList<OppositionMoyenPaiement>();
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

					if (!rejOpp.equals("")) {
						chq30 = setCodRej(chq30, rejOpp);
					}
				}

				/*********************************
				 * controle compte cloture
				 ****************************************************/
				// ne tester pas si montant < 20 et compte en dinar
				if (!mntChqInf20) {
					if (contratCpt.getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_RESILIE)) {
						prbProvision = true;
						chq30 = setCodRej(chq30, Constants.COD_MREJ_CHQ_COMP_CLOT);
						chq30 = setCodRej(chq30, Constants.COD_MREJ_CHQ_ABS_PROV);
					}

					if (!(contratCpt.getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_RESILIE))
							&& !(contratCpt.getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_VALID))) {
						prbProvision = true;
						chq30 = setCodRej(chq30, Constants.COD_MREJ_CHQ_COMP_CLOT);
						chq30 = setCodRej(chq30, Constants.COD_MREJ_CHQ_INDISP_PROV);
					}

				}

				/*****************************************
				 * ****si cheque certifié on ne controle pas la provision
				 ***************************************************/
				// CheckCertifCheqTrt checkTrt = new CheckCertifCheqTrt();
				// PrimitiveVO primitive = new PrimitiveVO();
				// primitive = (PrimitiveVO) checkTrt.exec(chq30);
				if (!primitive.isVBool()) { // non certifié
					/******************* controle provision **************************/
					chq30 = ctrlProvisionDevise(chq30, prbProvision, contratCpt);
				}
			}

			else {
				chq30.setCodRej1(Constants.COD_MREJ_CHQ_MAL_ACHEMINE);
			} // end if cpt is null}

			if (chq30.getCodRej1() == null && chq30.getCodRej2() == null && chq30.getCodRej3() == null
					&& chq30.getCodRej4() == null) {
				reglementCheque(chq30);
			} else {
				createCheque(chq30, "R");
			}
			chq30.setCodEtatChq("P");
			crudService.update(chq30);

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans PositionCheque30Trt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("PositionCheque33Trt");
			logger.error("Exception : ", e);
			throw new RuntimeException(e);

		}
		return chq30;
	}

	public void genCroText(ValueObject vo) {
	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public Cheque30 setCodRej(Cheque30 v30, String motRej) {
		if (v30.getCodRej1() == null) {
			v30.setCodRej1(motRej);
		} else {
			if (v30.getCodRej2() == null) {
				v30.setCodRej2(motRej);

			} else {
				// codrej1 et codrej2 != null
				if (v30.getCodRej3() == null) {
					v30.setCodRej3(motRej);

				} else {
					// codrej1 et codrej2 et codrej3 != null
					if (v30.getCodRej4() == null) {
						v30.setCodRej4(motRej);
					}
				}
			}
		}

		return v30;
	}

	/**
	 * @param chq30
	 * @param listChequesRecusVo
	 */
	public Cheque30 ctrlProvisionDevise(Cheque30 chq30, boolean prbProvision, ContratCpt contratCpt) {

		Long montantChequeEnDevise = Long.valueOf(0);
		Long montantChequeEnDinar = chq30.getMntChq();
		double coursAchat = UtilCtr.getCoursAchatBna("" + contratCpt.getDevise().getCodDevDev());
		montantChequeEnDevise = UtilCtr.changeTNDToDevise(montantChequeEnDinar, contratCpt.getDevise().getNbrDecDev(),
				contratCpt.getDevise().getNbrUnitDev(), coursAchat);
		if (montantChequeEnDevise > contratCpt.getMontSdevCcpt()) {
			if (mntChqInf20) {
				chq30 = setCodRej(chq30, "14");
				return chq30;

			} else {
				if (contratCpt.getMontSdevCcpt() == 0) {
					if (!prbProvision)
						chq30 = setCodRej(chq30, Constants.COD_MREJ_CHQ_ABS_PROV);
				} else if (montantChequeEnDevise > contratCpt.getMontSdevCcpt()) {
					// pas absence , un compte en devise censé entre toujours positif
					if (!prbProvision)
						chq30 = setCodRej(chq30, Constants.COD_MREJ_CHQ_INSUFF_PROV);
				}

			}

		}
		return chq30;
	}

	/**
	 * @param chq30
	 * @param listChequesRecusVo
	 */
	public void reglementCheque(Cheque30 chq30) {

		System.out.println("******************************************reglement cheque [" + chq30.getNumChq() + "]");

		Cheque nouveauCheque = createCheque(chq30, "P");
		ReglementChequeVo reglementChequeVo = new ReglementChequeVo();
		ContratCpt contratCpt = UtilCtr.getContratCptByRIB(chq30.getRibTir());
		reglementChequeVo.setCheque(nouveauCheque);
		ParamAgence paramAgence = new ParamAgence();
		paramAgence.setCodStrcStrc(Long.valueOf(chq30.getCodUgOpe()));
		paramAgence.setDateComptable(DateHandler.dateToStr(chq30.getDatOpe()));
		paramAgence.setNumMatrUser("9999");
		reglementChequeVo.setMontantARegler(chq30.getMntChq());
		reglementChequeVo.setParamAgence(paramAgence);
		reglementChequeVo.setCodValVal(chq30.getCodVal());
		reglementChequeVo.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
		reglementChequeVo.setCodeOperation(Constants.COD_OPERATION_REGLEMENT_CHEQUE);
		// reglementChequeVo.setReglementDevise(true);
		reglementChequeVo.setContratCpt(contratCpt);
		ReglementChequeTrt reglementChequeTrt = new ReglementChequeTrt();
		reglementChequeVo = (ReglementChequeVo) reglementChequeTrt.exec(reglementChequeVo);
		chq30.setCodEtatChq(Constants.COD_ETAT_CHQ_TMP_REGLE);
	}

	public Cheque createCheque(Cheque30 chq30, String codEtat) {
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
		devise.setCodDevDev(Long.valueOf(Constants.COD_DEV_DINAR));
		nouveauCheque.setDevise(devise);
		nouveauCheque.setDatOpeChq(chq30.getDatOpe());
		nouveauCheque.setMntChqChq(chq30.getMntChq());
		nouveauCheque.setNomPrnChq(chq30.getNomBen());
		nouveauCheque.setDatEmiChq(chq30.getDatEmi());
		nouveauCheque.setCodNateChq(chq30.getNatRem().toString());
		if (chq30.getNatRem() == null) {
			logger.info("Attention codnatechq null default value : A"); // ??????
			nouveauCheque.setCodNateChq("A");
		}
		nouveauCheque.setCodEnrChq(chq30.getCodEnr());
		if (codEtat.equals("P"))
			nouveauCheque.setDatRegChq(chq30.getDatOpe());
		nouveauCheque.setCodBaemChq(chq30.getCodInsDes());
		nouveauCheque.setCodAgemChq(chq30.getCodCenReg());
		nouveauCheque.setCodBadeChq(chq30.getCodInsDes());
		nouveauCheque.setCodAgdeChq(chq30.getCodCenRegDes());
		nouveauCheque.setCodLemiChq(chq30.getLieEmi());
		nouveauCheque.setCodSbenChq(chq30.getSitBen());
		nouveauCheque.setCodNcptChq(chq30.getNatCpt());
		nouveauCheque.setNumLotChq(chq30.getNumLot());
		nouveauCheque.setCodEtatChq(codEtat);
		nouveauCheque.setRefFicChq("N");
		nouveauCheque.setNumEvenChq(0L);
		nouveauCheque.setNumEvrcpChq(0L);
		nouveauCheque.setRibTrecChq("N");
		nouveauCheque.setRjtRegChq("N");
		nouveauCheque.setCodNateChq("D"); // D: pour dire devise non toilette ( d: devise toilette )
		crudService.create(nouveauCheque);
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
		valeurr.setCodValVal(Long.valueOf(30));
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
		traceCheque.setCodValTch(null); // payé pas de cor rej chq
		traceCheque.setRefFicTch(nouveauCheque.getRefFicChq());
		traceCheque.setRibTirRecTch(nouveauCheque.getRibTrecChq());
		traceCheque.setCodMotrTch(nouveauCheque.getCodMrejChq());
		traceCheque.setNumEvtEnvTch(nouveauCheque.getNumEvenChq());
		traceCheque.setNumEvtRcpTch(nouveauCheque.getNumEvrcpChq());
		traceCheque.setRjtRegTch(nouveauCheque.getRjtRegChq());
		traceCheque.setCodNatcTch("D");
		crudService.create(traceCheque);

		return nouveauCheque;

	}

}
