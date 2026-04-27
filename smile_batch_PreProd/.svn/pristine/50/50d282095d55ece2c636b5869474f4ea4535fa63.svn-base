package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.BlocageEffet;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.EffetId;
import com.bna.commun.model.EffetRecu;
import com.bna.commun.model.EffetRecuTmp;
import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceEffetRecu;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratEtatCmd;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.model.BlocageEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReceptionEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementEffetVo;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetListOppositionTrt;
import com.bna.smile.model.traitementCompensationRecu.model.ListEffetsRecusVo;
import com.bna.smile.model.traitementCompensationRecu.traitement.GetListEffetsRecusTrt;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class PositionEffet21Trt extends Traitement {

	public PositionEffet21Trt() {
	}

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	IExpression expression = searchEngine.createExpression();
	ICriteria criteria = searchEngine.createCriteria();
	ReglementEffetVo reglementEffetVo;

	public IValueObject perform(IValueObject vo) {
		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		CompensationEffetVo compensationEffetVo = (CompensationEffetVo) vo;
		logger.info("Start compensation Effet  ......");
		Long montGlobEffetLCN = Long.valueOf(0);
		Long nbrGlobEffetLCN = Long.valueOf(0);
		
		Long montGlobEffetLCR = Long.valueOf(0);
		Long nbrGlobEffetLCR = Long.valueOf(0);
		
		Long montGlobEffetOC = Long.valueOf(0);
		Long nbrGlobEffetOC = Long.valueOf(0);
		
		Long montGlobEffetBC = Long.valueOf(0);
		Long nbrGlobEffetBC = Long.valueOf(0);



		
		
		Long montGlobReclamationEffet = Long.valueOf(0);
		Long nbrTotReclamationEffet = Long.valueOf(0);
		boolean motifRejetAuto = false;
		boolean prbProvision = false;
		boolean requVerifProv = true;
		GetListEffetsRecusTrt getListEffetsRecusTrt = new GetListEffetsRecusTrt();

		try {

			Structure strc =
					(Structure) searchEngine.get(Structure.class, compensationEffetVo.getStructure().getCodStrcStrc());

			ListEffetsRecusVo listEffetsRecusVo = new ListEffetsRecusVo();
			listEffetsRecusVo.setDateComptable(compensationEffetVo.getDateComptable());
			listEffetsRecusVo.setCodEnr(Long.valueOf(21));
			listEffetsRecusVo.setStructure(strc);
			listEffetsRecusVo.setEtat(null);

			listEffetsRecusVo = (ListEffetsRecusVo) getListEffetsRecusTrt.exec(listEffetsRecusVo);

			ReceptionEffetVo receptionEffetVo = new ReceptionEffetVo();
			receptionEffetVo.setDateComptable(compensationEffetVo.getDateComptable());
			receptionEffetVo.setStructure(StrHandler.lpad(compensationEffetVo.getStructure().getCodStrcStrc()
					.toString(), '0', 3));

			logger.info("Traitement de Lot 21 Structure  :" + compensationEffetVo.getStructure().getCodStrcStrc() + ":"	+ listEffetsRecusVo.getListEffet21().size());

			if (listEffetsRecusVo.getListEffet21() != null && listEffetsRecusVo.getListEffet21().size() > 0) {
				for (Iterator it = listEffetsRecusVo.getListEffet21().iterator(); it.hasNext();) {

					motifRejetAuto = false;
					prbProvision = false;
					requVerifProv = true;

					expression = searchEngine.createExpression();
					criteria = searchEngine.createCriteria();
					EffetRecuTmp eff = new EffetRecuTmp();
					eff = (EffetRecuTmp) it.next();
					logger.info("ENRG:" + eff.getCodEnr());
					if(isLCR(eff)) {
						montGlobEffetLCR = montGlobEffetLCR + eff.getMntEff();
						nbrGlobEffetLCR = nbrGlobEffetLCR + 1;
					}
					if(isLCN(eff)) {
						montGlobEffetLCN = montGlobEffetLCN + eff.getMntEff();
						nbrGlobEffetLCN = nbrGlobEffetLCN + 1;
					}	
					if(isOC(eff)) {
						montGlobEffetOC = montGlobEffetOC + eff.getMntEff();
						nbrGlobEffetOC = nbrGlobEffetOC + 1;
					}							

					if(isBC(eff)){
						montGlobEffetBC = montGlobEffetBC + eff.getMntEff();
						nbrGlobEffetBC = nbrGlobEffetBC + 1;
					}
					ContratCptId cptId = new ContratCptId();
					cptId.setCodStrcStrc(Long.valueOf(eff.getRibTir().substring(5, 8)));
					cptId.setCodPrdPrd(Long.valueOf(eff.getRibTir().substring(8, 12)));
					cptId.setNumCcptCcpt(Long.valueOf(eff.getRibTir().substring(12, 18)));
					ContratCpt contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, cptId);
					contratCpt = UtilCtr.getContratCptByRIB(eff.getRibTir());

					reglementEffetVo = new ReglementEffetVo();
					reglementEffetVo.setDateComptable(compensationEffetVo.getDateComptable());
					reglementEffetVo.setStructure("" + compensationEffetVo.getStructure().getCodStrcStrc());
					reglementEffetVo.setContratCpt(contratCpt);
					reglementEffetVo.setEffetRecuTmp(eff);

					if (contratCpt != null && cptId.getCodStrcStrc().equals(compensationEffetVo.getStructure().getCodStrcStrc())) {

						// 41 , 40 ,42,43
						if ((eff.getCodVal().equals(Constants.COD_LCR))||(eff.getCodVal().equals(Constants.COD_LCN))) {

							/********************************* controle deja regle ****************************************************/
							criteria.add(expression.eq("effetId.numEff", eff.getEffetId().getNumEff()));
							criteria.add(expression.eq("ribBen", eff.getRibBen()));
							criteria.add(expression.eq("ribTir", eff.getRibTir()));
							criteria.add(expression.eq("datEch", eff.getDatEch()));//numero manuel se repette



							List<EffetRecu> listEff = searchEngine.find(EffetRecu.class, criteria);
							if (!listEff.isEmpty()) {
								EffetRecu effet = listEff.get(0);
								if (effet.getCodEtatEff().equals(Constants.COD_ETAT_EFF_REG)
										&& (effet.getCodEnr().equals(21L) || effet.getCodEnr().equals(25L))) {
									eff = setCodRej(eff, Constants.COD_MREJ_EFF_VAL_DEJ_REG);
									motifRejetAuto = true;
									requVerifProv=false;

								}
							}

							/********************************* deces personne physique ****************************************************/

							if (contratCpt.getClient().getPersonne() != null
									&& contratCpt.getClient().getPersonne().getDatDecePers() != null) {
								if (contratCpt.getClient().getPersonne().getDatDecePers().compareTo(eff.getDatCre()) < 0) {
									eff = setCodRej(eff, Constants.COD_MREJ_EFF_COMPT_DECES);
									motifRejetAuto = true;
									requVerifProv = false;
								}

							}



							/********************************* controle opposition ****************************************************/

							String rejOpp = "";
							if (!(rejOpp = getRejOpposition(eff, contratCpt)).equals("")) {
								eff = setCodRej(eff, rejOpp);
								motifRejetAuto = true;
								requVerifProv = false;

							}

							/********************************* controle compte cloture ****************************************************/

							if (contratCpt.getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_RESILIE)) {
								prbProvision = true;
								motifRejetAuto = true;
								eff = setCodRej(eff, Constants.COD_MREJ_EFF_COMPT_CLOTURE);
								eff = setCodRej(eff, Constants.COD_MREJ_CHQ_ABS_PROV);
								requVerifProv = false;
							}

							/************************************* controle compte transfere a ctx *******************************/

							if (contratCpt.getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_TCONTENTIEU)) {
								prbProvision = true;
								motifRejetAuto = true;
								eff = setCodRej(eff, Constants.COD_MREJ_EFF_COMPT_CLOTURE);
								eff = setCodRej(eff, Constants.COD_MREJ_CHQ_ABS_PROV);
								requVerifProv = false;
							}

							/**************************************** controle compte bloque ou semi actif **************************/

							if ((contratCpt.getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_SEMIACTIF))
									|| (contratCpt.getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_BLOQUE))) {
								prbProvision = true;
								motifRejetAuto = true;
								eff = setCodRej(eff, Constants.COD_MREJ_EFF_COMPT_CLOTURE);
								eff = setCodRej(eff, Constants.COD_MREJ_CHQ_ABS_PROV);
								requVerifProv = false;

							}

							/********************************* controle compte elligible ****************************************************/

							Long produit = contratCpt.getContratCptId().getCodPrdPrd();
							if (!Arrays.asList(Constants.produitEligibleEncaissementEffet).contains(produit)) {
								eff = setCodRej(eff, Constants.COD_MREJ_EFF_OPER_NON_AUTO);
								motifRejetAuto = true;
								requVerifProv = false;
							}
							// ----------------------------------- Traitement Effet en DEVISE
							// -------------------------------------------

							if (!motifRejetAuto
									&& !contratCpt.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
								logger.info("Compte en devise :" + contratCpt.getDevise().getCodDevDev() + " "
										+ eff.getCodVal());
								positionEffetDevise(eff, receptionEffetVo, compensationEffetVo);
								continue;
							} else {

								// ----------------------------------- Traitement Effet en DINAR
								// -------------------------------------------

								// ---------------------Verification Effet AVAL------------------------------//

								System.out.println(eff.getCodEnd());
								System.out.println(isEffBap(eff));
								System.out.println(eff.getEffetId().getNumEff());
								if (!motifRejetAuto && eff.getCodEnd().equals(Constants.COD_EFF_AVAL)
										|| eff.getCodEnd().equals(Constants.COD_EFF_AVAL_END)) {

									requVerifProv = false;
								}
								// ---------------------Verification Effet BAP------------------------------//

								/** controle provision **/

								if (requVerifProv) {
									Long mntInt = Long.valueOf(0);
									Long mntBlocEffet = Long.valueOf(0);
									Long provision = Long.valueOf(0);

									if (eff.getMntInt() != null)
										mntInt = eff.getMntInt();

									mntBlocEffet = getBlocageEffet(contratCpt, eff.getEffetId().getNumEff());
									provision = getProvision(contratCpt, compensationEffetVo.getDateComptable());
									if (provision < 0)
										provision = 0L;
									Long diff = (provision + mntBlocEffet) - (eff.getMntEff() + mntInt);

									if ((eff.getMntEff() + mntInt) - (provision - getMntCptVert(contratCpt)) > 0) {
										reglementEffetVo.setMntCptVert((eff.getMntEff() + mntInt)
												- (provision - getMntCptVert(contratCpt)));
									} else {
										reglementEffetVo.setMntCptVert(null);
									}

									if (diff < 0) {
										if (provision > 0) {
											eff = setCodRej(eff, Constants.COD_MREJ_EFF_PROV_INSUFF);
										} else {
											eff = setCodRej(eff, Constants.COD_MREJ_EFF_PROV_ABS);
										}
									}

								}
							}

							if (motifRejetAuto) {
								eff.setCodEtatEff(Constants.COD_ETAT_EFF_TMP_FIN);
								createEffetRecu(eff, receptionEffetVo, compensationEffetVo,
										Constants.COD_ETAT_EFF_TMP_FIN, Constants.COD_ETAT_EFF_REJ, false);
							} else {
								if (isEffBap(eff) || eff.getCodEnd().equals(Constants.COD_EFF_AVAL)
										|| eff.getCodEnd().equals(Constants.COD_EFF_AVAL_END) && isNotRejet(eff)) {
									eff.setCodEtatEff(Constants.COD_ETAT_EFF_TMP_POS);
									if (isEffBap(eff)) {
                                        //ne pas modifier les données recu
										//eff.setCodAcc(Constants.COD_EFF_BAP);

										// eff.setAutInf("P");

									}
									eff.setCodEtatEff(Constants.COD_ETAT_EFF_TMP_POS);
									crudService.update(eff);

								} else {
									eff.setCodEtatEff(Constants.COD_ETAT_EFF_TMP_POS);
									crudService.update(eff);
									continue;
								}

							}

						} else {
							// 42
							if (eff.getCodVal().equals(Constants.COD_OC)) {
								// reglement 42
								eff.setCodEtatEff(Constants.COD_ETAT_EFF_TMP_FIN);
								crudService.update(eff);
								createEffetRecu(eff, receptionEffetVo, compensationEffetVo,	Constants.COD_ETAT_EFF_TMP_FIN, Constants.COD_ETAT_EFF_REG, true);
								continue;
							}
							if (eff.getCodVal().equals(Constants.COD_BC)) {
								// reglement 43
								eff.setCodEtatEff(Constants.COD_ETAT_EFF_TMP_FIN);
								crudService.update(eff);
								createEffetRecu(eff, receptionEffetVo, compensationEffetVo,	Constants.COD_ETAT_EFF_TMP_FIN, Constants.COD_ETAT_EFF_REG, true);
								continue;
							}
							

						}

					} else {
						eff.setCodRej1(Constants.COD_MREJ_EFF_VAL_MAL_ACHEMINEE);
						createEffetRecu(eff, receptionEffetVo, compensationEffetVo, Constants.COD_ETAT_EFF_TMP_FIN,
								Constants.COD_ETAT_EFF_REJ, false);

					}

				}
			}
			
			// gen cro recep LCN  825
			compensationEffetVo.setMontGlobEffet(montGlobEffetLCN);
			compensationEffetVo.setNbrGlobEffet(nbrGlobEffetLCN);
			Cro825ReceptionLCNTrt  recepLCN = new Cro825ReceptionLCNTrt();
			recepLCN.exec(compensationEffetVo) ;
			// gen cro recep LCR  832
			compensationEffetVo.setMontGlobEffet(montGlobEffetLCR);
			compensationEffetVo.setNbrGlobEffet(nbrGlobEffetLCR);
			Cro832ReceptionLCRTrt  recepLCR = new Cro832ReceptionLCRTrt();
			recepLCR.exec(compensationEffetVo) ;
			
			// gen cro recep OC  838
			compensationEffetVo.setMontGlobEffet(montGlobEffetOC);
			compensationEffetVo.setNbrGlobEffet(nbrGlobEffetOC);
			Cro838ReceptionOCTrt  recepOC = new Cro838ReceptionOCTrt();
			recepOC.exec(compensationEffetVo) ;
			
			
			// gen cro recep OC  841
			compensationEffetVo.setMontGlobEffet(montGlobEffetBC);
			compensationEffetVo.setNbrGlobEffet(nbrGlobEffetBC);
			Cro841ReceptionBCTrt  recepBC = new Cro841ReceptionBCTrt();
			recepBC.exec(compensationEffetVo) ;
			

			
			
			
			//compensationEffetVo.setMontGlobEffet(montGlobEffet);
			//compensationEffetVo.setNbrGlobEffet(nbrGlobEffet);

			this.setCroFlag(false);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans PositionEffet21Trt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("PositionEffet21Trt");
			logger.error("Exception : ", e);
			compensationEffetVo.addError(erreur);
			throw new RuntimeException(e);

		}
		return (compensationEffetVo);
	}

	public void genCroText(ValueObject vo) {}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);

	}

	private void create_or_updateEffetRecu(EffetRecu effet) {
		criteria = searchEngine.createCriteria();
		HibernateTemplate hibernateTemplate = (HibernateTemplate) this.context.getBean("hibernateTemplate");
		criteria.add(expression.eq("effetId.numEff", effet.getEffetId().getNumEff()));
		criteria.add(expression.eq("ribBen", effet.getRibBen()));
		criteria.add(expression.eq("ribTir", effet.getRibTir()));
		criteria.add(expression.eq("effetId.datOpe", effet.getEffetId().getDatOpe()));
		List res = searchEngine.find(EffetRecu.class, criteria);
		if (!res.isEmpty()) {
			EffetRecu recu = (EffetRecu) res.get(0);
			if (!recu.getCodEtatEff().equals(Constants.COD_ETAT_EFF_REG)) {
				recu.setCodRej1(effet.getCodRej1());
				recu.setCodRej2(effet.getCodRej2());
				recu.setCodRej3(effet.getCodRej3());
				recu.setCodRej4(effet.getCodRej4());
				recu.setCodEtatEff(effet.getCodEtatEff());
				crudService.update(recu);

			}
			// hibernateTemplate.clear();
			// hibernateTemplate.update(effetrecu);
			// crudService.update(effetrecu);
		} else {
			hibernateTemplate.save(effet);
		}
		createTraceEffetRecu(effet);

	}

	/**
	 * @param cpt
	 * @param numEffet
	 * @return
	 */
	private Long getBlocageEffet(ContratCpt cpt, String numEffet) {
		GetBolcagePourEffetTrt getBolcagePourEffetTrt = new GetBolcagePourEffetTrt();
		BlocageEffetVo blocageEffetVo = new BlocageEffetVo();
		BlocageEffet blocageEffet = new BlocageEffet();
		blocageEffet.setContratCpt(cpt);
		blocageEffet.setNumEffEff(numEffet);
		blocageEffetVo.setBlocageEffet(blocageEffet);
		blocageEffetVo = (BlocageEffetVo) getBolcagePourEffetTrt.exec(blocageEffetVo);
		if (blocageEffetVo == null)
			return 0L;
		if (!cpt.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR))
			return blocageEffetVo.getSommeMnEffet();
		return blocageEffetVo.getSommeBlocage();
	}

	/**
	 * @author nbdour
	 * @param effTmp
	 * @param motRej
	 * @return
	 */
	public EffetRecuTmp setCodRej(EffetRecuTmp effTmp, String motRej) {
		if (effTmp.getCodRej1() == null) {
			effTmp.setCodRej1(motRej);
		} else {
			if (effTmp.getCodRej2() == null) {
				effTmp.setCodRej2(motRej);

			} else {
				// codrej1 et codrej2 != null
				if (effTmp.getCodRej3() == null) {
					effTmp.setCodRej3(motRej);

				} else {
					// codrej1 et codrej2 et codrej3 != null
					if (effTmp.getCodRej4() == null) {
						effTmp.setCodRej4(motRej);
					}
				}
			}
		}

		return effTmp;
	}

	/**
	 * @param eff
	 * @param receptionEffetVo
	 * @param compensationEffetVo
	 * @param etatEffTmp
	 * @param etatEffRecu
	 * @return
	 */
	public ReglementEffetVo createEffetRecu(EffetRecuTmp eff, ReceptionEffetVo receptionEffetVo,
			CompensationEffetVo compensationEffetVo, String etatEffTmp, String etatEffRecu) {
		eff.setCodEtatEff(etatEffTmp);

		EffetRecu nouveauEffetRecu = new EffetRecu();
		EffetId effetId = new EffetId();
		effetId.setDatOpe(receptionEffetVo.getDateComptable());
		effetId.setNumEff(eff.getEffetId().getNumEff());
		nouveauEffetRecu.setEffetId(effetId);
		nouveauEffetRecu.setCodDev(eff.getCodDev());
		nouveauEffetRecu.setMntEff(eff.getMntEff());
		nouveauEffetRecu.setNomBen(eff.getNomBen());
		nouveauEffetRecu.setDatCre(eff.getDatCre());
		nouveauEffetRecu.setNomTir(eff.getNomTir());
		nouveauEffetRecu.setCodEnr(eff.getCodEnr());
		nouveauEffetRecu.setCodBan(eff.getCodBan());
		nouveauEffetRecu.setCodAge(eff.getCodAge());
		nouveauEffetRecu.setCodBanDes(eff.getCodBanDes());
		nouveauEffetRecu.setCodAgeDes(eff.getCodAgeDes());
		nouveauEffetRecu.setLieCre(eff.getLieCre());
		nouveauEffetRecu.setCodProt(eff.getCodProt());
		nouveauEffetRecu.setCodOrd(eff.getCodOrd());
		nouveauEffetRecu.setCodAcc(eff.getCodAcc());
		nouveauEffetRecu.setCodVal(eff.getCodVal());
		nouveauEffetRecu.setCodNatCpt(eff.getCodNatCpt());
		nouveauEffetRecu.setNumLot(eff.getNumLot());
		nouveauEffetRecu.setMntFra(eff.getMntFra());
		nouveauEffetRecu.setMntInt(eff.getMntInt());
		nouveauEffetRecu.setMntIntIni(eff.getMntIntIni());
		nouveauEffetRecu.setNumEffIni(eff.getNumEffIni());
		nouveauEffetRecu.setCodEnd(eff.getCodEnd());
		nouveauEffetRecu.setRefComBen(eff.getRefComBen());
		nouveauEffetRecu.setRefComTir(eff.getRefComTir());
		nouveauEffetRecu.setRefFic(eff.getRefFic());
		nouveauEffetRecu.setRibBen(eff.getRibBen());
		nouveauEffetRecu.setRibTir(eff.getRibTir());
		nouveauEffetRecu.setNomTir(eff.getNomTir());
		nouveauEffetRecu.setCodRisBct(eff.getCodRisBct());
		nouveauEffetRecu.setRibTirIni(eff.getRibTirIni());
		nouveauEffetRecu.setAutInf(eff.getAutInf());
		nouveauEffetRecu.setDatCre(eff.getDatCre());
		nouveauEffetRecu.setDatEch(eff.getDatEch());
		nouveauEffetRecu.setDatEchIni(eff.getDatEchIni());
		nouveauEffetRecu.setDatCmp(eff.getDatCmp());
		nouveauEffetRecu.setCodSit(eff.isCodSit());
		nouveauEffetRecu.setCodNatEta(eff.isCodNatEta());
		nouveauEffetRecu.setCodProt(eff.getCodProt());
		nouveauEffetRecu.setRngDet(eff.getRngDet());


		nouveauEffetRecu.setCodEtatEff(etatEffRecu);
		nouveauEffetRecu.setCodRej1(eff.getCodRej1());
		nouveauEffetRecu.setCodRej2(eff.getCodRej2());
		nouveauEffetRecu.setCodRej3(eff.getCodRej3());
		nouveauEffetRecu.setCodRej4(eff.getCodRej4());
		nouveauEffetRecu.setCodRisBct(eff.getCodRisBct());
		create_or_updateEffetRecu(nouveauEffetRecu);
		reglementEffetVo.setEffetRecu(nouveauEffetRecu);

		return reglementEffetVo;
	}

	/**
	 * @param eff
	 * @param receptionEffetVo
	 * @param compensationEffetVo
	 * @param etatEffTmp
	 * @param etatEffRecu
	 * @param reglement
	 * @return
	 */
	public boolean createEffetRecu(EffetRecuTmp eff, ReceptionEffetVo receptionEffetVo,
			CompensationEffetVo compensationEffetVo, String etatEffTmp, String etatEffRecu, boolean reglement) {
		System.out.println(eff.getEffetId().getNumEff());

		if (reglement) {
			eff.setAutInf("P");
				reglementEffetVo = createEffetRecu(eff, receptionEffetVo, compensationEffetVo, etatEffTmp, etatEffRecu);
//				if(isLCR(eff)) {
//					Cro833PayementLcrTrt cro833PayementLcrTrt = new Cro833PayementLcrTrt();
//					reglementEffetVo = (ReglementEffetVo) cro833PayementLcrTrt.exec(reglementEffetVo);
//
//				}
//				if(isLCN(eff)){
//					Cro826PayementLcnTrt cro826PayementLcnTrt = new Cro826PayementLcnTrt();
//					reglementEffetVo = (ReglementEffetVo) cro826PayementLcnTrt.exec(reglementEffetVo);
//
//				}
				if(isOC(eff)){
					Cro839PayementOCTrt ro839PayementOCTrt = new Cro839PayementOCTrt();
					reglementEffetVo = (ReglementEffetVo) ro839PayementOCTrt.exec(reglementEffetVo);

				}
				if(isBC(eff)){
					Cro842PayementBCTrt cro842PayementBCTrt = new Cro842PayementBCTrt();
					reglementEffetVo = (ReglementEffetVo) cro842PayementBCTrt.exec(reglementEffetVo);

				}
				
				
			
		} else {
			reglementEffetVo = createEffetRecu(eff, receptionEffetVo, compensationEffetVo, etatEffTmp, etatEffRecu);
			System.out.println(UtilCtr.getContratCptByRIB(eff.getRibTir()));
			eff.setAutInf("R");
			if(isLCR(eff)){
				Cro834RejetEffetAutoTrt cro834RejetEffetAutoTrt = new Cro834RejetEffetAutoTrt();
				// On execute pas un _CRO avec des montant (0)
				// 
				reglementEffetVo = (ReglementEffetVo) cro834RejetEffetAutoTrt.exec(reglementEffetVo);

			}else{
				Cro827RejetEffetAutoTrt cro827RejetEffetAutoTrt = new Cro827RejetEffetAutoTrt();
				reglementEffetVo = (ReglementEffetVo) cro827RejetEffetAutoTrt.exec(reglementEffetVo);

			}
			// }
		}
		crudService.update(eff);
		return true;
	}

	/**
	 * @param chq30
	 * @param listChequesRecusVo
	 */
	public void positionEffetDevise(EffetRecuTmp eff, ReceptionEffetVo receptionEffetVo,
			CompensationEffetVo compensationEffetVo) {

		// tester provision : si disponible payer puis toilettage, sinon rejeter auto
		Long mnt = eff.getMntEff();
		if (eff.getMntInt() != null)
			mnt += eff.getMntInt();
		ContratCpt cpt = UtilCtr.getContratCptByRIB(eff.getRibTir());
		// set Code devise
		eff.setCodDev("" + cpt.getDevise().getCodDevDev());

		Double coursAchat = UtilCtr.getCoursAchatBna(cpt.getDevise().getCodDevDev().toString());
		Long mntDev =
				UtilCtr.changeTNDToDevise(mnt, cpt.getDevise().getNbrDecDev(), cpt.getDevise().getNbrUnitDev(),
						coursAchat);

		if (!isEffBap(eff) && mntDev > cpt.getMontSdevCcpt()) // OK provision
		{

			eff = setCodRej(eff, "11"); // probleme provision

		}
		if (isEffBap(eff)) {
			eff.setCodAcc(Constants.COD_EFF_BAP);
			eff.setAutInf("P");
		}
		eff.setCodEtatEff(Constants.COD_ETAT_EFF_TMP_POS);
		crudService.update(eff);
	}

	/**
	 * @param eff
	 * @return
	 */
	public boolean isEffBap(EffetRecuTmp eff) {

		PrimitiveVO primitiveVO = new PrimitiveVO();
		primitiveVO.setVString(eff.getEffetId().getNumEff());
		VerifEffetBapTrt effetBapTrt = new VerifEffetBapTrt();
		primitiveVO = (PrimitiveVO) effetBapTrt.exec(primitiveVO);
		return (primitiveVO.isVBool());

	}

	/**
	 * @param eff
	 * @return
	 */
	public boolean isEffReclame(EffetRecuTmp eff) {
		VerifEffetReclameTrt effetReclameTrt = new VerifEffetReclameTrt();
		PrimitiveVO primitiveVO = new PrimitiveVO();
		primitiveVO.setVString(eff.getEffetId().getNumEff());
		primitiveVO = (PrimitiveVO) effetReclameTrt.exec(primitiveVO);
		return (primitiveVO.isVBool());

	}

	public Long getMntCptVert(ContratCpt contratCpt) {
		Long mntSoldeVert = Long.valueOf(0);
		if (contratCpt.getBoolCverCcpt() != null && contratCpt.getBoolCverCcpt().equals(Long.valueOf("1"))) {
			GetContratEtatCmd getContratEtatCmd = new GetContratEtatCmd();
			ContratCpt contratCptV = new ContratCpt();
			ContratCptId contratCptId = new ContratCptId();
			contratCptId.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc());
			contratCptId.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt());
			contratCptId.setCodPrdPrd(Constants.COD_PRD_PRD_VERT);
			ValueObject vo1 = (ValueObject) getContratEtatCmd.execute(contratCptId);
			if (!vo1.hasError()) { // / retour de l'habilitation
				ContratCptMandat contratCptMandatV = (ContratCptMandat) vo1;
				contratCptV = contratCptMandatV.getContratCpt();
				if (contratCptV.getMontSoldCcpt() != null) {
					mntSoldeVert = contratCptV.getMontSoldCcpt() - Constants.MNT_SEUIL_CPT_VERT;

				}
			}
		}
		return mntSoldeVert;

	}

	public Long getProvision(ContratCpt contratCpt, Date dateComptable) {
		Long provision = Long.valueOf(0);
		Long mntAut = Long.valueOf(0);
		Long mntBlocCpt = Long.valueOf(0);
		Long mntSoldCpt = Long.valueOf(0);

		if (contratCpt.getMontBlocCcpt() != null) {
			mntBlocCpt = contratCpt.getMontBlocCcpt();
		}
		if (contratCpt.getMontSoldCcpt() != null) {
			mntSoldCpt = contratCpt.getMontSoldCcpt();
		}
		if (contratCpt.getMontAutCcpt() != null && contratCpt.getDatEautCcpt() != null
				&& contratCpt.getDatEautCcpt().compareTo(dateComptable) >= 0) {
			mntAut = contratCpt.getMontAutCcpt();
		}
		provision = mntSoldCpt + mntAut + getMntCptVert(contratCpt) - mntBlocCpt;
		return provision;
	}

	public String getRejOpposition(EffetRecuTmp eff, ContratCpt contratCpt) {
		ContratCptId cptId = contratCpt.getContratCptId();
		ParamRechercheOpposition paramRechercheOpposition = new ParamRechercheOpposition();
		GetListOppositionTrt getListOppositionTrt = new GetListOppositionTrt();
		String rejOpp = "";
		paramRechercheOpposition.setCodPrdPrd(cptId.getCodPrdPrd());
		paramRechercheOpposition.setCodStrcStrc(cptId.getCodStrcStrc());
		paramRechercheOpposition.setNumCcptCcpt(cptId.getNumCcptCcpt());
		paramRechercheOpposition.setNumMoypTmoy(eff.getEffetId().getNumEff());
		paramRechercheOpposition.setTypeMoyPaie(Constants.COD_MOYP_TMOY_EFFET);
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
				if (oppositionMoyenPaiement.getCodMotfOpmp().equalsIgnoreCase(Constants.COD_ETAT_OPP_CHQ_PERT)) {
					rejOpp = Constants.COD_MREJ_EFF_OPP_PERTE;
				} else if (oppositionMoyenPaiement.getCodMotfOpmp().equalsIgnoreCase(Constants.COD_ETAT_OPP_CHQ_VOL)) {
					rejOpp = Constants.COD_MREJ_EFF_OPP_VOL;
				} else if (oppositionMoyenPaiement.getCodMotfOpmp().equalsIgnoreCase(Constants.COD_ETAT_OPP_CHQ_FAIL)) {
					rejOpp = Constants.COD_MREJ_EFF_OPP_FAILLITE;
				} else if (oppositionMoyenPaiement.getCodMotfOpmp().equalsIgnoreCase(Constants.COD_ETAT_OPP_CHQ_AUT)) {
					rejOpp = Constants.COD_MREJ_EFF_OPP_AUTRE;
				}
			}

		}
		return rejOpp;
	}

	public void createTraceEffetRecu(EffetRecu eff) {
		TraceEffetRecu nouveauEffetRecu = new TraceEffetRecu();
		EffetId effetId = new EffetId();
		effetId.setNumEff(eff.getEffetId().getNumEff()); // TODO : to be updated
		effetId.setDatOpe(eff.getEffetId().getDatOpe()); // TODO : to be updated
		nouveauEffetRecu.setEffetId(effetId);
		nouveauEffetRecu.setCodDev(eff.getCodDev());
		nouveauEffetRecu.setNomTir(eff.getNomTir());
		nouveauEffetRecu.setMntEff(eff.getMntEff());
		nouveauEffetRecu.setNomBen(eff.getNomBen());
		nouveauEffetRecu.setDatCre(eff.getDatCre());
		nouveauEffetRecu.setCodEnr(eff.getCodEnr());
		nouveauEffetRecu.setCodBan(eff.getCodBan());
		nouveauEffetRecu.setCodAge(eff.getCodAge());
		nouveauEffetRecu.setCodBanDes(eff.getCodBanDes());
		nouveauEffetRecu.setCodAgeDes(eff.getCodAgeDes());
		nouveauEffetRecu.setLieCre(eff.getLieCre());
		nouveauEffetRecu.setCodAcc(eff.getCodAcc());
		nouveauEffetRecu.setCodNatCpt(eff.getCodNatCpt());
		nouveauEffetRecu.setNumLot(eff.getNumLot());
		nouveauEffetRecu.setMntFra(eff.getMntFra());
		nouveauEffetRecu.setMntInt(eff.getMntInt());
		nouveauEffetRecu.setCodEnd(eff.getCodEnd());
		nouveauEffetRecu.setRefComBen(eff.getRefComBen());
		nouveauEffetRecu.setRefComTir(eff.getRefComTir());
		nouveauEffetRecu.setRefFic(eff.getRefFic());
		nouveauEffetRecu.setRibBen(eff.getRibBen());
		nouveauEffetRecu.setCodVal(eff.getCodVal());
		nouveauEffetRecu.setRibTir(eff.getRibTir());
		nouveauEffetRecu.setRibTirIni(eff.getRibTirIni());
		nouveauEffetRecu.setAutInf(eff.getAutInf());
		nouveauEffetRecu.setDatCre(eff.getDatCre());
		nouveauEffetRecu.setCodProt(eff.getCodProt());
		nouveauEffetRecu.setDatEch(eff.getDatEch());
		nouveauEffetRecu.setCodRisBct(eff.getCodRisBct());
		nouveauEffetRecu.setDatEchIni(eff.getDatEchIni());
		nouveauEffetRecu.setDatCmp(eff.getDatCmp());
		nouveauEffetRecu.setCodSit(eff.getCodSit());
		nouveauEffetRecu.setCodNatEta(eff.getCodNatEta());

		// nouveauEffetRecu.setCodBap(eff.getCodOrd());

		nouveauEffetRecu.setRngDet(eff.getRngDet());
		nouveauEffetRecu.setRjtReg(eff.getRjtReg());

		nouveauEffetRecu.setCodEtatEff(eff.getCodEtatEff());
		nouveauEffetRecu.setCodRej1(eff.getCodRej1());
		nouveauEffetRecu.setCodRej2(eff.getCodRej2());
		nouveauEffetRecu.setCodRej3(eff.getCodRej3());
		nouveauEffetRecu.setCodRej4(eff.getCodRej4());
		crudService.create(nouveauEffetRecu);
	}

	public boolean isNotRejet(EffetRecuTmp tmp) {
		return (tmp.getCodRej1() == null && tmp.getCodRej2() == null && tmp.getCodRej3() == null && tmp.getCodRej4() == null);
	}
	public boolean isLCN(EffetRecuTmp eff) {
		return eff.getCodVal().equals(Constants.COD_LCN);
	}
	public boolean isLCR(EffetRecuTmp eff) {
		return eff.getCodVal().equals(Constants.COD_LCR);
	}

	public boolean isOC(EffetRecuTmp eff) {
		return eff.getCodVal().equals(Constants.COD_OC);
	}
	public boolean isBC(EffetRecuTmp eff) {
		return eff.getCodVal().equals(Constants.COD_BC);
	}


}