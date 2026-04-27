package com.bna.smile.model.virement.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class VirementsLieesCompteVertsByAgenceTrt extends Traitement {

	public VirementsLieesCompteVertsByAgenceTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	Set<ContratCpt> listeComptesFinales = new HashSet<ContratCpt>();
	Operation operation = new Operation();

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;
		Structure agence = virementVo.getStructure();
		Date dateComptable = virementVo.getDateComptableAgence();
		operation.setCodOperOper(Constants.COD_OPER_ALIMENTATION_FAVEUR_COMPTE_VERT);
		this.setCroFlag(false);

		try {

			// /*** recherche des contrat_cpt pour cette agence
			ICriteria criteria = searchEngine.createCriteria();

			criteria.add(expression.eq("boolCverCcpt", Long.valueOf(1)));
			criteria.add(expression.eq("codEtatCcpt", Constants.COD_ETAT_CPT_VALID));
			criteria.add(expression.eq("contratCptId.codStrcStrc", agence.getCodStrcStrc()));
			criteria.add(expression.eq("contratCptId.codPrdPrd", Constants.COD_COMPTE_CHEQUE));
			criteria.add(expression.gt("montSoldCcpt", Long.valueOf(Constants.SOLDE_MIN_COMPTE_DEPOT)));

			List<ContratCpt> listeContratCpts = searchEngine.find(ContratCpt.class, criteria);

			if (listeContratCpts != null && listeContratCpts.size() > 0) {

				for (ContratCpt contratCpt : listeContratCpts) {

					if (contratCpt.getMontSoldCcpt().longValue() > contratCpt.getMontSminCcpt().longValue()) {
						listeComptesFinales.add(contratCpt);

					}
				}

			}

			// /*** Parcour des contrat_cpt pour cette agence ********///
			long montantAtransferer = 0;
			long nbreTatalExecutees = 0;
			if (listeComptesFinales.size() > 0) {

				for (ContratCpt contratCpt : listeComptesFinales) {

					logger.info("contratCpt : " + contratCpt.getContratCptId().getCompteClient());

					ContratCpt contratCptVert = new ContratCpt();
					// ******Find Compte Vert *****************//

					ICriteria criteriaCPTV = searchEngine.createCriteria();
					IExpression expressionCPTV = searchEngine.createExpression();

					criteriaCPTV.add(expressionCPTV.eq("client.numSeqPers", contratCpt.getClient().getNumSeqPers()));
					criteriaCPTV.add(expressionCPTV.eq("contratCptId.codPrdPrd", new Long(165)));
					criteriaCPTV.add(expressionCPTV.eq("contratCptId.numCcptCcpt", contratCpt.getContratCptId()
							.getNumCcptCcpt()));
					criteriaCPTV.add(expressionCPTV.eq("contratCpt.contratCptId", contratCpt.getContratCptId()));
					criteriaCPTV.add(expressionCPTV.eq("codEtatCcpt", Constants.COD_ETAT_CPT_VALID));

					List<ContratCpt> l = searchEngine.find(ContratCpt.class, criteriaCPTV);

					if (l.size() > 0) {
						contratCptVert = (ContratCpt) l.get(0);
					}
					if (contratCpt.getMontSminCcpt() != null && contratCpt.getMontSminCcpt().longValue() != 0) {

						montantAtransferer =
								contratCpt.getMontSoldCcpt().longValue() - contratCpt.getMontSminCcpt().longValue();
					} else {
						montantAtransferer =
								contratCpt.getMontSoldCcpt().longValue() - Constants.SOLDE_MIN_COMPTE_DEPOT.longValue();
					}

					if (contratCptVert != null && contratCptVert.getContratCptId() != null) {

						// ******Execution virement ****************//

						long montantSoldeVert = 0;
						montantSoldeVert = contratCptVert.getMontSoldCcpt().longValue();

						if (montantSoldeVert < Constants.SOLDE_MAX_COMPTE_VERT.longValue()) {

							long montantCalculer = 0;
							montantCalculer = Constants.SOLDE_MAX_COMPTE_VERT.longValue() - montantSoldeVert;

							if (montantAtransferer > montantCalculer) {
								
								montantAtransferer = montantCalculer;
							}

							VirementVo virementVoExec = new VirementVo();
							virementVoExec.setDateComptableAgence(dateComptable);
							virementVoExec.setContratCptCompteDepot(contratCpt);
							virementVoExec.setContratCptCompteVert(contratCptVert);
							virementVoExec.setOperation(operation);
							virementVoExec.setMontant_virement(montantAtransferer);
							VirementService virementService = (VirementService) context.getBean("iVirementService");

							virementVoExec = (VirementVo) virementService.executerVirementCompteVert(virementVoExec);
							nbreTatalExecutees++;
						}
					}
				}

			}

			// ************ Gestion des Statistiques *****************//
			long nbreTatal = listeComptesFinales.size();

			gestionStatistique(dateComptable, agence, nbreTatal, nbreTatalExecutees);
			String messageStatistique = "Nombre total des virements liés aux comptes verts = " + nbreTatal + "  ; \n ";
			messageStatistique +=
					"Nombre des virements liés aux comptes verts exécuté avec sucées  = " + nbreTatalExecutees
							+ "  ; \n ";

			virementVo.setEtatEnregistrement(true);
			virementVo.setMessageValidation(messageStatistique);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans VirementsLieesCompteVertsByAgenceTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("VirementsLieesCompteVertsByAgenceTrt");
			logger.error("Exception : ", e);
			virementVo.setEtatEnregistrement(false);
			virementVo.setMessageValidation(e.getMessage());
			gestionException(dateComptable, agence, e);
			throw new RuntimeException(e);

		}
		return virementVo;
	}

	private void gestionStatistique(Date dateComptable, Structure agence, long nbrVirTotals, long nbreVirExecutees) {

		String messageStatistique = "";

		messageStatistique = "L’exécution a été effectuée avec sucées ! \n";
		messageStatistique += "Nombre total des virements liés aux comptes verts = " + nbrVirTotals + "  ; \n ";
		messageStatistique +=
				"Nombre des virements liés aux comptes verts exécuté avec sucées  = " + nbreVirExecutees + "  ; \n ";

		BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
		batchStatPlacement.setCodEtatBats("V");
		batchStatPlacement.setDatSystBats(new Date());
		batchStatPlacement.setDatCompBats(dateComptable);
		batchStatPlacement.setStructure(agence);
		batchStatPlacement.setLibExtrBats(messageStatistique);
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_VIREMENT_LIEES_COMPTES_VERTS);
		batchStatPlacement.setBatchMetier(batchMetier);
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);
	}

	private void gestionException(Date dateComptable, Structure agence, Exception e) {

		BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();
		batchExeptionPlac.setDatSystBate(new Date());
		batchExeptionPlac.setDatCompBate(dateComptable);
		batchExeptionPlac.setStructure(agence);
		batchExeptionPlac.setLibTpbmBate("Exception Batch Virement");
		batchExeptionPlac.setLibExpBate(e.getMessage());
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
	}

	public void genCroText(ValueObject vo) {

	}

}
