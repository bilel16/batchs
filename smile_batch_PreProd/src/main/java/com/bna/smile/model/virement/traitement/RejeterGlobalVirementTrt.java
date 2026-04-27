package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.TraceOperVirement;
import com.bna.commun.traitements.Traitement;
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

public class RejeterGlobalVirementTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");

	public RejeterGlobalVirementTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;

		Date dateComptable = new Date();
		dateComptable = virementVo.getDateComptableAgence();

		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");

		this.setCroFlag(false);

		try {

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			GlobalVirement globalVirementObj = new GlobalVirement();

			criteria.add(expression.eq("numSeqGvir", virementVo.getGlobalVirement().getNumSeqGvir()));

			List l = searchEngine.find(GlobalVirement.class, criteria);

			long nbre_rejet_detail = 0;
			long nbre_rejet_total = 0;

			long montant_rejet_detail = 0;
			long montant_rejet_global = 0;

			String heureString = formaterHeure.format(new Date());
			String dateString = formaterDate.format(new Date());

			if (l != null && l.size() > 0) {
				globalVirementObj = (GlobalVirement) l.get(0);

				// / --------- Cas d'un virement Ponctuel & de Masse ----------------///
				if (globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_PONCTUEL.longValue()
						|| globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_MASSE
								.longValue()) {
					nbre_rejet_detail = 0;
					nbre_rejet_total = 0;

					montant_rejet_detail = 0;
					montant_rejet_global = 0;

					// / Nbre de rejet Global
					if (globalVirementObj.getNbrRejetGvir() == null) {
						nbre_rejet_total = 0;
					} else {
						nbre_rejet_total = globalVirementObj.getNbrRejetGvir().longValue();
					}

					// / montant de rejet Global
					if (globalVirementObj.getMntRejetGvir() == null) {
						montant_rejet_global = 0;
					} else {
						montant_rejet_global = globalVirementObj.getMntRejetGvir().longValue();
					}

					// itérateur
					Iterator iterateur = globalVirementObj.getDetailVirements().iterator();
					while (iterateur.hasNext()) {
						DetailVirement detailVirementObj = (DetailVirement) iterateur.next();

						if (detailVirementObj.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_ATTENTE
								.longValue()) {
							detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
							detailVirementObj.setMotifRejetDetv(virementVo.getMessageValidation()); // / Motif de Rejet
							detailVirementObj.setDatExecDetv(dateComptable);
							detailVirementObj.setDatSysDetv(formaterDate.parse(dateString));
							detailVirementObj.setTimeExecDetv(heureString);
							nbre_rejet_detail = nbre_rejet_detail + 1;

							montant_rejet_detail =
									montant_rejet_detail + detailVirementObj.getMntDetvDetv().longValue();

							crudService.update(detailVirementObj);

						}

					}

					// / Update Global Virement
					nbre_rejet_total = nbre_rejet_total + nbre_rejet_detail;

					montant_rejet_global = montant_rejet_global + montant_rejet_detail;

					globalVirementObj.setNbrRejetGvir(nbre_rejet_total);
					globalVirementObj.setMntRejetGvir(montant_rejet_global);
					globalVirementObj.setEtatGvirGvir(Constants.COD_ETAT_VIREMENT_REJETER);

					crudService.update(globalVirementObj);

					// /------------------- Trace Oper Virement --------------------------///

					// / Creation Trace Oper Virement
					TraceOperVirement traceOperVirement = new TraceOperVirement();
					Long numSeqTvir = new Long(0);
					numSeqTvir = virementGlobalDAO.getSequenceTraceOperVirement();

					traceOperVirement.setNumSeqTvir(numSeqTvir); // Num Seq Trace Vir
					traceOperVirement.setGlobalVirement(globalVirementObj); // Global Virement

					if (globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_PONCTUEL
							.longValue()) {
						traceOperVirement.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PONCTUEL);
						traceOperVirement.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PONCTUEL);
					}

					if (globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_MASSE
							.longValue()) {
						traceOperVirement.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_MASSE);
						traceOperVirement.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_MASSE);
					}

					traceOperVirement.setNumMatrUser("9999");

					SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
					SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");

					traceOperVirement.setDatOperTvir(formaterDate.parse(dateString));
					traceOperVirement.setTimeOperTvir(heureString);

					traceOperVirement.setStructure(globalVirementObj.getStructure());

					crudService.create(traceOperVirement);

					// /------------------------------------------------------------------///

				}

				// / --------- Cas d'un virement Permanent ----------------///

				if (globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_PERMANENT
						.longValue()) {

					nbre_rejet_detail = 0;
					nbre_rejet_total = 0;

					montant_rejet_detail = 0;
					montant_rejet_global = 0;

					// / Nbre de rejet Global
					if (globalVirementObj.getNbrRejetGvir() == null) {
						nbre_rejet_total = 0;
					} else {
						nbre_rejet_total = globalVirementObj.getNbrRejetGvir().longValue();
					}

					// / montant de rejet Global
					if (globalVirementObj.getMntGvirGvir() == null) {
						montant_rejet_global = 0;
					} else {
						montant_rejet_global = globalVirementObj.getMntGvirGvir().longValue();
					}

					// itérateur
					Iterator iterateur = globalVirementObj.getDetailVirements().iterator();
					while (iterateur.hasNext()) {
						DetailVirement detailVirementObj = (DetailVirement) iterateur.next();

						if (dateComptable.compareTo(detailVirementObj.getDatEchDetv()) == 0
								|| dateComptable.compareTo(detailVirementObj.getDatEchDetv()) == 1) {
							if (detailVirementObj.getEtaDetvDetv().longValue() == Constants.COD_ETAT_VIREMENT_ATTENTE
									.longValue()) {
								detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
								detailVirementObj.setMotifRejetDetv(virementVo.getMessageValidation()); // / Motif de
																										// Rejet

								nbre_rejet_detail = nbre_rejet_detail + 1;
								montant_rejet_detail =
										montant_rejet_detail + detailVirementObj.getMntDetvDetv().longValue();

								crudService.update(detailVirementObj);

								// /------------------- Trace Oper Virement --------------------------///

								// / Creation Trace Oper Virement
								TraceOperVirement traceOperVirement = new TraceOperVirement();
								Long numSeqTvir = new Long(0);
								numSeqTvir = virementGlobalDAO.getSequenceTraceOperVirement();

								traceOperVirement.setNumSeqTvir(numSeqTvir); // Num Seq Trace Vir
								traceOperVirement.setDetailVirement(detailVirementObj);

								if (globalVirementObj.getCodPrdPrd().longValue() == Constants.COD_PRODUIT_VIREMENT_PERMANENT
										.longValue()) {
									traceOperVirement.setCodOperOper(Constants.COD_OPER_EXECUTION_VIREMENT_PERMANENT);
									traceOperVirement.setCodTachTach(Constants.COD_TACH_EXECUTION_VIREMENT_PERMANENT);
								}

								traceOperVirement.setNumMatrUser("9999");

								SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
								SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");

								traceOperVirement.setDatOperTvir(formaterDate.parse(dateString));
								traceOperVirement.setTimeOperTvir(heureString);

								traceOperVirement.setStructure(globalVirementObj.getStructure());

								crudService.create(traceOperVirement);

								// /------------------------------------------------------------------///

							}
						}

					}
					// / Update Global Virement
					nbre_rejet_total = nbre_rejet_total + nbre_rejet_detail;

					montant_rejet_global = montant_rejet_global + montant_rejet_detail;

					globalVirementObj.setNbrRejetGvir(nbre_rejet_total);
					globalVirementObj.setMntRejetGvir(montant_rejet_global);

					/************ A verifier ***********/
					// globalVirementObj.setEtatGvirGvir(Constants.COD_ETAT_VIREMENT_REJETER);

					crudService.update(globalVirementObj);

				}

				// / ------------------------------------------------------///
			}

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans RejeterGlobalVirementTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("RejeterGlobalVirementTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau RejeterGlobalVirementTrt : ", e);
			virementVo.setMessageValidation("Probléme lors de RejeterGlobalVirementTrt");
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