package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.JourneeStructureBatchId;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.OperationCompteDAO;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.web.procuration.util.ContratCptView;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GestionPerceptionCommissionFraisPackTrt extends Traitement {

	public GestionPerceptionCommissionFraisPackTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	// à ne pas laisser en variable global
	ICriteria criteria = searchEngine.createCriteria();
	ICriteria criteriaAvanc = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();
	String messageStatistique;
	String messageException;

	public IValueObject perform(IValueObject vo) {

		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		long nombreCpteTraite = 0;
		long nombreCpteRejete = 0;
		long nombreCptTotal = 0;
		VirementVo virementVo = new VirementVo();
		virementVo = (VirementVo) vo;

		Date dateComptableAgence = null;
		try {

			Structure agence = new Structure();

			agence = virementVo.getStructure();
			dateComptableAgence = virementVo.getDateComptableAgence();

			// tester si la journée batch n'est pas dejà inserée

			/*************** Rechercher existance Journee Structure Batch *********/

			ICriteria criteriaJSB = searchEngine.createCriteria();
			IExpression expresJSB = searchEngine.createExpression();
			CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
			BatchService batchService = (BatchService) context.getBean("batchService");
			criteriaJSB.add(expresJSB.eq("journeeStructureBatchId.codStrcStrc", agence.getCodStrcStrc()));
			criteriaJSB.add(expresJSB.eq("journeeStructureBatchId.datJrnJrn", dateComptableAgence));
			criteriaJSB
					.add(expresJSB.eq("journeeStructureBatchId.codBatBmet", Constants.COD_BATCH_FRAIS_TENUE_CPTE_PACK));

			List<JourneeStructureBatch> liste_JourneeStructureBatch =
					new ArrayList<JourneeStructureBatch>(searchEngine.find(JourneeStructureBatch.class, criteriaJSB));

			if (liste_JourneeStructureBatch.size() > 0) {
				logger.debug("Journée batch dejà insérée pour l'agence " + agence.getCodStrcStrc());
				virementVo.setEtatEnregistrement(true);
				virementVo.setMessageValidation("Journée batch dejà traitée");

			} else {

				/********** Creation d'une Journee Structure Batch ***************/

				JourneeStructureBatch journeeStructureBatch = new JourneeStructureBatch();
				JourneeStructureBatchId journeeStructureBatchId = new JourneeStructureBatchId();
				journeeStructureBatchId.setCodBatBmet(Constants.COD_BATCH_FRAIS_TENUE_CPTE_PACK);
				journeeStructureBatchId.setCodStrcStrc(agence.getCodStrcStrc());
				journeeStructureBatchId.setDatJrnJrn(dateComptableAgence);
				journeeStructureBatch.setJourneeStructureBatchId(journeeStructureBatchId);
				journeeStructureBatch.setCodStatJsb(Long.valueOf(0));
				crudService.create(journeeStructureBatch);

				// //////////////////////////////////
				virementVo.setStructure(agence);
				virementVo.setDateComptableAgence(dateComptableAgence);
				List<ContratCptView> listeComptePack = new ArrayList<ContratCptView>();
				/***********************************************/

				Calendar c = Calendar.getInstance();

				// on se place à la date utilisée comme base de calcul
				c.setTime(dateComptableAgence);

				// on se place au premier jour du mois en cours
				c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
				Date debutMois = c.getTime();
				/************************************************/
				OperationCompteDAO operationCompteDAO = (OperationCompteDAO) context.getBean("operationCompteDAO");
				listeComptePack = operationCompteDAO
						.getListesDesComptesPacks(virementVo.getStructure().getCodStrcStrc(), debutMois);

				if (listeComptePack != null && listeComptePack.size() != 0) {
					nombreCptTotal = Long.valueOf(listeComptePack.size());
					for (ContratCptView contratCptView : listeComptePack) {
						virementVo.setContratCpt(contratCptView.getContratCpt());
						virementVo.setPeriodicite(contratCptView.getPeriodPrelevement());
						Produit produit = new Produit();
						produit.setCodPrdPrd(contratCptView.getCodePrdPack());
						virementVo.setProduit(produit);

						virementVo = (VirementVo) batchService.preleverCommissionFraisPack(virementVo);

						if (virementVo.isEtatEnregistrement() == true) {
							if (virementVo.getTraceFraisPack() != null
									&& virementVo.getTraceFraisPack().getEtatTrcPack() != null
									&& virementVo.getTraceFraisPack().getEtatTrcPack().equals("T")) {
								nombreCpteTraite = nombreCpteTraite + 1;
							} else if (virementVo.getTraceFraisPack() != null
									&& virementVo.getTraceFraisPack().getEtatTrcPack() != null
									&& virementVo.getTraceFraisPack().getEtatTrcPack().equals("R")) {
								nombreCpteRejete = nombreCpteRejete + 1;

							}
						}

					}
					messageStatistique = "Nombre Total des comptes : " + nombreCptTotal + " ==> Nombre traite : "
							+ nombreCpteTraite + " ==> Nombre rejet :" + nombreCpteRejete;
					virementVo.setEtatEnregistrement(virementVo.isEtatEnregistrement());
				} else {
					messageStatistique = "Pas de compte pour cette agence ";
					virementVo.setEtatEnregistrement(true);
				}
				/***************************************************/
				virementVo.setMessageValidation(messageStatistique);
				this.gestionStatistique(messageStatistique, virementVo.getStructure(),
						virementVo.getDateComptableAgence());

				// //////////////////////////////////

				// journée batch OK
				journeeStructureBatch.setDatCloJsb(new Date());
				journeeStructureBatch.setCodStatJsb(Long.valueOf("1"));
				journeeStructureBatch =
						(JourneeStructureBatch) batchService.updateJourneeStructureBatch(journeeStructureBatch);

			}

			logger.debug("--- FIN MOULINETTE COMMISSION PACK  ---");

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GestionPerceptionCommissionFraisPack : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GestionPerceptionCommissionFraisPack");
			logger.error("Exception : ", e);
			virementVo.addError(erreur);
			virementVo.setEtatEnregistrement(false);
			virementVo.setMessageValidation(e.getMessage());
			throw new RuntimeException(e);

		}
		return virementVo;
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	private void gestionStatistique(String message, Structure agence, Date dateComptable) {

		try {
			BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
			batchStatPlacement.setCodEtatBats("V");
			batchStatPlacement.setDatSystBats(new Date());
			batchStatPlacement.setDatCompBats(dateComptable);
			batchStatPlacement.setStructure(agence);
			BatchMetier batchMetier = new BatchMetier();
			batchMetier.setCodBatBmet(Constants.COD_BATCH_FRAIS_TENUE_CPTE_PACK);
			batchStatPlacement.setBatchMetier(batchMetier);

			batchStatPlacement.setLibExtrBats(message);
			BatchService batchService = (BatchService) context.getBean("batchService");
			batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);

		} catch (Exception ie) {
			ie.printStackTrace();
			message = ie.getMessage();

		}

	}
}
