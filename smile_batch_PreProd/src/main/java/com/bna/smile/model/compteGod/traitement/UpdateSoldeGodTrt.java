package com.bna.smile.model.compteGod.traitement;

import java.util.Calendar;
import java.util.Date;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratGod;
import com.bna.commun.model.ContratGodId;
import com.bna.commun.model.Devise;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceOperationGod;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.compteGod.dao.GodDAO;
import com.bna.smile.model.compteGod.model.BlocageGodVo;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class UpdateSoldeGodTrt extends Traitement {

	public Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	GodDAO godDAO = (GodDAO) context.getBean("godDAO");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	// protected JdbcTemplate jt;
	Long soldeActuel = 0L;
	Long soldeActuelDevises = 0L;
	Long montantTND = 0L;
	Long montantDEV = 0L;

	/**
	 * Methode permettant la MAJ du Solde d'un contrat GOD donné
	 * 
	 * @param vo
	 *            : ContratCptSold
	 * @return : ContratGod
	 */

	public IValueObject perform(IValueObject vo) {

		BlocageGodVo blocageGodVo = (BlocageGodVo) vo;
		ContratCptSold contratCptSold = blocageGodVo.getContratCptSold();
		ContratGod contratGod = new ContratGod();
		ContratCpt contratCpt = new ContratCpt();
		ContratGodId contratGodId = new ContratGodId();
		ContratGod contratCptNew = new ContratGod();
		try {

			HibernateTemplate hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
			System.out.println("-- UpdateSoldeGodTrt ---");
			/* Mise à jour du ContratCpt dans la Base dans la BD */
			contratCpt = contratCptSold.getContratCpt();
			montantTND = contratCptSold.getSolde();
			if (contratCptSold.getSoldeDevise() != null && (!contratCptSold.getSoldeDevise().equals(0))) {
				montantDEV = contratCptSold.getSoldeDevise();

			}
			if (contratCpt != null && contratCpt.getContratCptId() != null) {
				contratGodId.setCodStrcGod(contratCpt.getContratCptId().getCodStrcStrc());

				if (contratCpt.getDevise() != null
						&& contratCpt.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
					contratGodId.setCodPrdGod(Long.valueOf(327));

				} else {

					contratGodId.setCodPrdGod(Long.valueOf(443));
				}

				contratGodId.setNumCcptGod(contratCpt.getContratCptId().getNumCcptCcpt());
				contratGod.setContratGodId(contratGodId);
				contratGod = (ContratGod) searchEngine.get(ContratGod.class, contratGodId);
			} else {
				// Cas de l'operation 2018
				contratGod = blocageGodVo.getContratGod();
				contratGodId = contratGod.getContratGodId();
				contratGod = (ContratGod) searchEngine.get(ContratGod.class, contratGod.getContratGodId());

			}
			if (contratGod != null && contratGod.getDevise() != null) {

				Long solde = contratGod.getMontSoldDinGod();
				Long codeOperation = blocageGodVo.getCodOper();
				String sens = "";

				if (codeOperation.equals(Constants.COD_OPER_VIR_SIEGE)) {

					sens = contratCptSold.getSens();

				} else if (codeOperation.equals(Constants.COD_OPER_BLOC_GOD)) {
					sens = "C";
				} else if (codeOperation.equals(Constants.COD_OPER_DBLOC_GOD)) {
					sens = "D";
				}

				// hibernateTemplate.flush();
				hibernateTemplate.evict(contratGod);

				// // Faire le LOCK par load on update--- Debut de la section critique ----
				// ContratCpt contratCptNew = (ContratCpt)searchEngine.loadForUpdate(ContratCpt.class,contratCptId);
				contratCptNew = (ContratGod) searchEngine.loadForUpdate(ContratGod.class, contratGodId);
				soldeActuel = contratCptNew.getMontSoldDinGod();
				soldeActuelDevises = contratCptNew.getMontSoldDevGod();
				if (sens.equalsIgnoreCase("C")) {
					contratCptNew.setMontSoldDinGod(soldeActuel + montantTND); // créditer le compte
					// since 13/10/2016
					if (soldeActuelDevises != null) {
						contratCptNew.setMontSoldDevGod(soldeActuelDevises + montantDEV);
					}
				} else {
					if (soldeActuel.intValue() >= solde.intValue()) {
						contratCptNew.setMontSoldDinGod(soldeActuel - montantTND); // débiter le compte
						// since 13/10/2016
						if (soldeActuelDevises != null) {
							contratCptNew.setMontSoldDevGod(soldeActuelDevises - montantDEV);
						}
					} else {
						StringBuffer text =
								new StringBuffer(
										"Opération impossible. Le Solde du compte a été modifié au cour de l'opération. Veuillez controler le nouveau solde du compte.");
						throw new RuntimeException(text.toString());
					}
				}
				contratCptNew.setDatMajGod(new Date());
				hibernateTemplate.update(contratCptNew);
				hibernateTemplate.flush();

			} else {

				contratCptNew.setContratGodId(contratGodId);
				if (contratCpt != null && contratCpt.getDevise() != null) {
					contratCptNew.setDevise(contratCpt.getDevise());
				} else if (contratGodId.getCodPrdGod().equals(327L)) {
					Devise devise = new Devise();
					devise.setCodDevDev(Constants.COD_DEV_DINAR);
					contratCptNew.setDevise(devise);
				} else if (contratGodId.getCodPrdGod().equals(443L) || contratGodId.getCodPrdGod().equals(507L)
						|| contratGodId.getCodPrdGod().equals(1035L)) {
					Devise devise = new Devise();
					devise.setCodDevDev(Constants.COD_DEV_EURO);
					contratCptNew.setDevise(devise);
				}
				if (contratCpt != null && contratCpt.getClient() != null) {
					contratCptNew.setPersonne(contratCpt.getClient().getPersonne());
				}
				contratCptNew.setMontSoldDinGod(contratCptSold.getSolde()); // créditer le compte
				contratCptNew.setMontSoldDevGod(contratCptSold.getSoldeDevise());
				if (contratCpt != null && contratCpt.getNomIntiCcpt() != null) {

					contratCptNew.setNomInitCptGod(contratCpt.getNomIntiCcpt());
				}
				contratCptNew.setDatMajGod(new Date());
				hibernateTemplate.save(contratCptNew);
				hibernateTemplate.flush();

			}

			/** Insertion trace **/
			blocageGodVo.setContratGod(contratCptNew);
			insererTraceOperationGod(blocageGodVo);

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans UpdateSoldeGodTrt : ");
			text.append(e.getMessage());
			erreur.setCode("200");
			erreur.setDescription(text.toString());
			erreur.setKey("UpdateSoldeGodTrt");
			blocageGodVo.addError(erreur);
			throw new RuntimeException(e.getMessage());

		}
		return (blocageGodVo);
	}

	public void insererTraceOperationGod(BlocageGodVo blocageGodVo) {
		try {
			logger.info("-- insererTraceOperationGod ---");
			TraceOperationGod traceOperationGod = new TraceOperationGod();
			traceOperationGod.setNumSeqTrgod(godDAO.getSequenceTraceOperGOD());
			traceOperationGod.setContratGod(blocageGodVo.getContratGod());
			traceOperationGod.setDateOperTrgod(blocageGodVo.getDateOperation());
			Tache tache = new Tache();
			TacheId tacheId = new TacheId();
			Long codeOper = blocageGodVo.getCodOper();
			tacheId.setCodOperOper(codeOper);
			if (codeOper.equals(Constants.COD_OPER_VIR_SIEGE)) {

				tacheId.setCodTachTach(1L);

			} else {
				tacheId.setCodTachTach(Constants.COD_TACH_VALID_BLOC_DEB_GOD);
			}
			tache.setTacheId(tacheId);
			traceOperationGod.setTache(tache);
			traceOperationGod.setMontSoldTrgod(soldeActuel);// //
			traceOperationGod.setMontSoldDevTrgod(soldeActuelDevises);
			traceOperationGod.setMontDinTrgod(montantTND);
			traceOperationGod.setMontDevTrgod(montantDEV);
			traceOperationGod.setMontAprDinGod(blocageGodVo.getContratGod().getMontSoldDinGod());
			traceOperationGod.setMontAprDevGod(blocageGodVo.getContratGod().getMontSoldDevGod());

			if (blocageGodVo.getContratGod() != null && blocageGodVo.getContratGod().getDevise() != null) {
				
				traceOperationGod.setDevise(blocageGodVo.getContratGod().getDevise());
		
			} else {

				if (blocageGodVo.getContratGod().getContratGodId().getCodPrdGod().equals(327L)) {
					Devise devise = new Devise();
					devise.setCodDevDev(Constants.COD_DEV_DINAR);
					traceOperationGod.setDevise(devise);
				} else if (blocageGodVo.getContratGod().getContratGodId().getCodPrdGod().equals(443L)
						|| blocageGodVo.getContratGod().getContratGodId().getCodPrdGod().equals(507L)
						|| blocageGodVo.getContratGod().getContratGodId().getCodPrdGod().equals(1035L)) {
					Devise devise = new Devise();
					devise.setCodDevDev(Constants.COD_DEV_EURO);
					traceOperationGod.setDevise(devise);
				}
			}
			String sens = "";

			if (codeOper.equals(Constants.COD_OPER_BLOC_GOD)) {
				sens = "C";
			} else if (codeOper.equals(Constants.COD_OPER_DBLOC_GOD)) {
				sens = "D";
			} else if (codeOper.equals(Constants.COD_OPER_VIR_SIEGE)) {

				sens = blocageGodVo.getContratCptSold().getSens();

			}
			traceOperationGod.setCodSensTrgod(sens);
			traceOperationGod.setNumMatrUser(9999L);

			if (blocageGodVo.getBlocageGod() != null && blocageGodVo.getBlocageGod().getNumBlocBloc() != 0) {
				System.out.println("blocageGodVo.getBlocageGod().getNumBlocBloc()  "
						+ blocageGodVo.getBlocageGod().getNumBlocBloc());
				traceOperationGod.setBlocageGod(blocageGodVo.getBlocageGod());
			}

			if (blocageGodVo.getDeblocageGod() != null && blocageGodVo.getDeblocageGod().getNumDeblcDeblc() != 0) {
				System.out.println("blocageGodVo.getDeblocageGod().getNumDeblcDeblc()   "
						+ blocageGodVo.getDeblocageGod().getNumDeblcDeblc());

				traceOperationGod.setDeblocageGod(blocageGodVo.getDeblocageGod());
			}

			if (blocageGodVo.getMotifOperation() != null) {

				traceOperationGod.setMotifOperTrgod(blocageGodVo.getMotifOperation());

			}
			Calendar cal = Calendar.getInstance();
			traceOperationGod.setDateTimeTrgod(cal.getTime());
			crudService.create(traceOperationGod);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {

	}

	public Long getSoldeActuel() {
		return soldeActuel;
	}

	public void setSoldeActuel(Long soldeActuel) {
		this.soldeActuel = soldeActuel;
	}

	public Long getSoldeActuelDevises() {
		return soldeActuelDevises;
	}

	public void setSoldeActuelDevises(Long soldeActuelDevises) {
		this.soldeActuelDevises = soldeActuelDevises;
	}

}
