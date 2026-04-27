package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.Operation;
import com.bna.commun.model.TraceOperVirement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class NonApprovisionDoVirTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");

	public NonApprovisionDoVirTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		this.setCroFlag(false);

		VirementVo virementVo = (VirementVo) vo;

		GlobalVirement globalVirement = (GlobalVirement) virementVo.getGlobalVirement();
		Operation operation = new Operation();
		operation = virementVo.getOperation();
		Long codeTachTach = virementVo.getCodTachTach();
		Date dateComptable = new Date();
		dateComptable = virementVo.getDateComptableAgence();

		long nbre_rejet_detail = 0;
		long nbre_rejet_total = 0;

		long montant_rejet_detail = 0;
		long montant_rejet_global = 0;

		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");

		try {

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");

			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			// / --------- Cas d'un virement Permanent ----------------///
			if (globalVirement.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_PERMANENT.longValue()) {
				Long[] etatDetails =
						{ Constants.COD_ETAT_VIREMENT_ATTENTE, Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF,
								Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN };
				criteria.add(expression.eq("detailVirementId.numSeqGvir", globalVirement.getNumSeqGvir()));
				criteria.add(expression.in("etaDetvDetv", etatDetails));
				criteria.add(expression.le("datEchDetv", dateComptable));

				List<DetailVirement> l = searchEngine.find(DetailVirement.class, criteria);

				DetailVirement detailVirementObj = new DetailVirement();
				if (l != null && l.size() > 0) {
					detailVirementObj = (DetailVirement) l.get(0);

					if (dateComptable.compareTo(detailVirementObj.getDatEchDetv()) >= 0) {

						if (detailVirementObj.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_ATTENTE
								.longValue()) {

							detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN);
							detailVirementObj.setMotifRejetDetv("MANQUE D'APPROVISION J+1"); // / Motif de Rejet
							detailVirementObj.setDatExecDetv(dateComptable);
							// detailVirementObj.setDatEchDetv(CalanderHandler.GetNextWorkingDay(dateComptable));

							crudService.update(detailVirementObj);

						}

						else if (detailVirementObj.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN
								.longValue()) {

							if (formaterDate.parse(
									formaterDate.format(CalanderHandler.GetNextWorkingDay(detailVirementObj
											.getDatEchDetv()))).compareTo(
									formaterDate.parse(formaterDate.format(dateComptable))) == 0) {

								detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF);
								detailVirementObj.setMotifRejetDetv("MANQUE D'APPROVISION J+2"); // / Motif de Rejet
								detailVirementObj.setDatExecDetv(dateComptable);
								// detailVirementObj.setDatEchDetv(CalanderHandler.GetNextWorkingDay(dateComptable));

								crudService.update(detailVirementObj);
							} else if (formaterDate.parse(
									formaterDate.format(CalanderHandler.GetNextWorkingDay(detailVirementObj
											.getDatEchDetv()))).compareTo(
									formaterDate.parse(formaterDate.format(dateComptable))) < 0) {

								detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
								detailVirementObj.setMotifRejetDetv("MANQUE D'APPROVISION DEFINITIF");
								detailVirementObj.setDatExecDetv(dateComptable);
								// detailVirementObj.setDatEchDetv(CalanderHandler.GetNextWorkingDay(dateComptable));

								crudService.update(detailVirementObj);

								// /------------------- Global Virement --------------------------///

								nbre_rejet_detail = 0;
								nbre_rejet_total = 0;

								montant_rejet_detail = 0;
								montant_rejet_global = 0;

								// / Nbre de rejet Global
								if (detailVirementObj.getGlobalVirement().getNbrRejetGvir() == null) {
									nbre_rejet_total = 0;
								} else {
									nbre_rejet_total =
											detailVirementObj.getGlobalVirement().getNbrRejetGvir().longValue();
								}

								// / montant de rejet Global
								if (detailVirementObj.getGlobalVirement().getMntRejetGvir() == null) {
									montant_rejet_global = 0;
								} else {
									montant_rejet_global =
											detailVirementObj.getGlobalVirement().getMntRejetGvir().longValue();
								}

								nbre_rejet_detail = nbre_rejet_detail + 1;
								montant_rejet_detail =
										montant_rejet_detail + detailVirementObj.getMntDetvDetv().longValue();

								// / Update Global Virement
								nbre_rejet_total = nbre_rejet_total + nbre_rejet_detail;

								montant_rejet_global = montant_rejet_global + montant_rejet_detail;

								detailVirementObj.getGlobalVirement().setNbrRejetGvir(nbre_rejet_total);
								detailVirementObj.getGlobalVirement().setMntRejetGvir(montant_rejet_global);

								detailVirementObj.getGlobalVirement().setEtatGvirGvir(
										Constants.COD_ETAT_VIREMENT_ENCOUREXECUTION);

								crudService.update(detailVirementObj.getGlobalVirement());

								// /--------------------------------------------------------------///

							}

						}

						else if (detailVirementObj.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF
								.longValue()) {
							if (formaterDate.parse(
									formaterDate.format(CalanderHandler.GetNextWorkingDayAfterNdays(
											detailVirementObj.getDatEchDetv(), 2))).compareTo(
									formaterDate.parse(formaterDate.format(dateComptable))) <= 0) {

								detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
								detailVirementObj.setMotifRejetDetv("MANQUE D'APPROVISION DEFINITIF"); // / Motif de
								detailVirementObj.setDatExecDetv(dateComptable); // Rejet

								crudService.update(detailVirementObj);

								// /------------------- Global Virement --------------------------///

								nbre_rejet_detail = 0;
								nbre_rejet_total = 0;

								montant_rejet_detail = 0;
								montant_rejet_global = 0;

								// / Nbre de rejet Global
								if (detailVirementObj.getGlobalVirement().getNbrRejetGvir() == null) {
									nbre_rejet_total = 0;
								} else {
									nbre_rejet_total =
											detailVirementObj.getGlobalVirement().getNbrRejetGvir().longValue();
								}

								// / montant de rejet Global
								if (detailVirementObj.getGlobalVirement().getMntGvirGvir() == null) {
									montant_rejet_global = 0;
								} else {
									montant_rejet_global =
											detailVirementObj.getGlobalVirement().getMntGvirGvir().longValue();
								}

								nbre_rejet_detail = nbre_rejet_detail + 1;
								montant_rejet_detail =
										montant_rejet_detail + detailVirementObj.getMntDetvDetv().longValue();

								// / Update Global Virement
								nbre_rejet_total = nbre_rejet_total + nbre_rejet_detail;

								montant_rejet_global = montant_rejet_global + montant_rejet_detail;

								detailVirementObj.getGlobalVirement().setNbrRejetGvir(nbre_rejet_total);
								detailVirementObj.getGlobalVirement().setMntRejetGvir(montant_rejet_global);

								detailVirementObj.getGlobalVirement().setEtatGvirGvir(
										Constants.COD_ETAT_VIREMENT_ENCOUREXECUTION);

								crudService.update(detailVirementObj.getGlobalVirement());

								// /--------------------------------------------------------------///
							}
						}

						// /------------------- Trace Oper Virement --------------------------///

						// / Creation Trace Oper Virement
						TraceOperVirement traceOperVirement = new TraceOperVirement();
						Long numSeqTvir = new Long(0);
						numSeqTvir = virementGlobalDAO.getSequenceTraceOperVirement();

						traceOperVirement.setNumSeqTvir(numSeqTvir); // Num Seq Trace Vir
						traceOperVirement.setDetailVirement(detailVirementObj);

						if (detailVirementObj.getGlobalVirement().getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_PERMANENT
								.longValue()) {
							traceOperVirement.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PERMANENT);
							traceOperVirement.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PERMANENT);
						}

						traceOperVirement.setNumMatrUser("9999");

						SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");

						String heureString = formaterHeure.format(new Date());
						String dateString = formaterDate.format(new Date());

						traceOperVirement.setDatOperTvir(formaterDate.parse(dateString));
						traceOperVirement.setTimeOperTvir(heureString);

						traceOperVirement.setStructure(detailVirementObj.getGlobalVirement().getStructure());

						crudService.create(traceOperVirement);

						// /------------------------------------------------------------------///
					}

				}

			}
			// / --------- Cas d'un virement Ponctuel / Masse ----------------///

			else if (globalVirement.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_PONCTUEL.longValue()
					|| globalVirement.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_MASSE.longValue()) {

				long nbreRejets = 0;
				long mntRejets = 0;

				criteria.add(expression.eq("detailVirementId.numSeqGvir", globalVirement.getNumSeqGvir()));

				List<DetailVirement> listeDetails =
						new ArrayList<DetailVirement>(searchEngine.find(DetailVirement.class, criteria));

				if (listeDetails != null && listeDetails.size() > 0) {

					for (DetailVirement detailVirementObj : listeDetails) {

						if (detailVirementObj.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_ATTENTE
								.longValue()) {

							detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN);
							detailVirementObj.setMotifRejetDetv("MANQUE D'APPROVISION J+1"); // / Motif de Rejet
							detailVirementObj.setDatExecDetv(dateComptable);
							crudService.update(detailVirementObj);

						}

						else if (detailVirementObj.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN
								.longValue()) {
							if (formaterDate.parse(
									formaterDate.format(CalanderHandler.GetNextWorkingDay(detailVirementObj
											.getDatEchDetv()))).compareTo(
									formaterDate.parse(formaterDate.format(dateComptable))) == 0)

							{
								detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF);
								detailVirementObj.setMotifRejetDetv("MANQUE D'APPROVISION J+2"); // / Motif de Rejet
								detailVirementObj.setDatExecDetv(dateComptable);
								// detailVirementObj.setDatEchDetv(CalanderHandler.GetNextWorkingDay(dateComptable));

								crudService.update(detailVirementObj);

							} else if (formaterDate.parse(
									formaterDate.format(CalanderHandler.GetNextWorkingDay(detailVirementObj
											.getDatEchDetv()))).compareTo(
									formaterDate.parse(formaterDate.format(dateComptable))) < 0) {

								detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
								detailVirementObj.setMotifRejetDetv("MANQUE D'APPROVISION DEFINITIF");
								detailVirementObj.setDatExecDetv(dateComptable);
								// detailVirementObj.setDatEchDetv(CalanderHandler.GetNextWorkingDay(dateComptable));

								crudService.update(detailVirementObj);
							}
						}

						else if (detailVirementObj.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF
								.longValue()
								|| detailVirementObj.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN
										.longValue()) {
							if (formaterDate.parse(
									formaterDate.format(CalanderHandler.GetNextWorkingDayAfterNdays(
											detailVirementObj.getDatEchDetv(), 2))).compareTo(
									formaterDate.parse(formaterDate.format(dateComptable))) == 0) {

								detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
								detailVirementObj.setMotifRejetDetv("MANQUE D'APPROVISION DEFINITIF"); // / Motif de
								detailVirementObj.setDatExecDetv(dateComptable); // Rejet
								nbreRejets++;
								mntRejets += detailVirementObj.getMntDetvDetv().longValue();
								crudService.update(detailVirementObj);

							}
						}
					}
					// /-------------------Update Global Virement --------------------------///

					if (globalVirement.getEtatGvirGvir().longValue() == Constants.COD_ETAT_VIREMENT_ATTENTE.longValue()) {
						globalVirement.setEtatGvirGvir(Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN);

						crudService.update(globalVirement);

					} else if (globalVirement.getEtatGvirGvir().longValue() == Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN
							.longValue()) {
						if (formaterDate.parse(
								formaterDate.format(CalanderHandler.GetNextWorkingDay(globalVirement.getDatExeGvir())))
								.compareTo(formaterDate.parse(formaterDate.format(dateComptable))) == 0) {
							globalVirement.setEtatGvirGvir(Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF);

							crudService.update(globalVirement);
						} else if (formaterDate.parse(
								formaterDate.format(CalanderHandler.GetNextWorkingDay(globalVirement.getDatExeGvir())))
								.compareTo(formaterDate.parse(formaterDate.format(dateComptable))) < 0) {
							globalVirement.setEtatGvirGvir(Constants.COD_ETAT_VIREMENT_REJETER);

							crudService.update(globalVirement);
						}

					} else if (globalVirement.getEtatGvirGvir().longValue() == Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF
							.longValue()) {
						if (formaterDate.parse(
								formaterDate.format(CalanderHandler.GetNextWorkingDayAfterNdays(
										globalVirement.getDatExeGvir(), 2))).compareTo(
								formaterDate.parse(formaterDate.format(dateComptable))) <= 0) {

							nbre_rejet_total = 0;

							montant_rejet_global = 0;

							// / Nbre de rejet Global
							if (globalVirement.getNbrRejetGvir() == null) {
								nbre_rejet_total = 0;
							} else {
								nbre_rejet_total = globalVirement.getNbrRejetGvir().longValue();
							}

							// / montant de rejet Global
							if (globalVirement.getMntGvirGvir() == null) {
								montant_rejet_global = 0;
							} else {
								montant_rejet_global = globalVirement.getMntGvirGvir().longValue();
							}

							nbre_rejet_total = nbre_rejet_total + nbreRejets;

							montant_rejet_global = montant_rejet_global + mntRejets;

							globalVirement.setNbrRejetGvir(nbre_rejet_total);
							globalVirement.setMntRejetGvir(montant_rejet_global);

							globalVirement.setEtatGvirGvir(Constants.COD_ETAT_VIREMENT_REJETER);

							crudService.update(globalVirement);
						}
					}

					// /--------------------------------------------------------------///
					// /------------------- Trace Oper Virement --------------------------///

					// / Creation Trace Oper Virement
					TraceOperVirement traceOperVirement = new TraceOperVirement();
					Long numSeqTvir = new Long(0);
					numSeqTvir = virementGlobalDAO.getSequenceTraceOperVirement();

					traceOperVirement.setNumSeqTvir(numSeqTvir); // Num Seq Trace Vir
					traceOperVirement.setGlobalVirement(globalVirement);
					traceOperVirement.setCodOperOper(operation.getCodOperOper());
					traceOperVirement.setCodTachTach(codeTachTach);
					traceOperVirement.setNumMatrUser("9999");
					SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
					String heureString = formaterHeure.format(new Date());
					String dateString = formaterDate.format(new Date());

					traceOperVirement.setDatOperTvir(formaterDate.parse(dateString));
					traceOperVirement.setTimeOperTvir(heureString);

					traceOperVirement.setStructure(globalVirement.getStructure());

					crudService.create(traceOperVirement);

					// /------------------------------------------------------------------///

				}

			}

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans NonApprovisionDoVirPermanentTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("NonApprovisionDoVirPermanentTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau NonApprovisionDoVirPermanentTrt : ", e);
			virementVo.setMessageValidation("Probléme dans NonApprovisionDoVirPermanentTrt");
			throw new RuntimeException();

		}
		return (virementVo);

	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}
}