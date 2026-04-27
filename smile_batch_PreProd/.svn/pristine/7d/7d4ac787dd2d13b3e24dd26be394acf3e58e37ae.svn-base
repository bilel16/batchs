package com.bna.smile.model.virement.traitement;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.FluxComptVirement;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Produit;
import com.bna.commun.model.VirSgmt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecommun.traitement.GetRibTrt;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class ExecutionDoVirPermanentTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeureFichier = new SimpleDateFormat("HHmmss");
	String codeBanque = "";
	String fileName = "";
	String repertoire = "";
	SimpleDateFormat formaterDate2 = new SimpleDateFormat("ddMMyyyy");
	String codeDevise = Constants.COD_DEV_DINAR.toString();
	File file = null;
	String ribDO = "";
	Date dateComptable = new Date();
	Date dateComptableFichier;
	String pathVirementTravail = "";

	public ExecutionDoVirPermanentTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;

		GlobalVirement globalVirement = (GlobalVirement) virementVo.getGlobalVirement();
		DetailVirement detailVirementObj = (DetailVirement) virementVo.getDetailVirement();
		OperationMoyPay operationMoyPayCompteDO = new OperationMoyPay();
		dateComptableFichier = new Date();// virementVo.getDateComptableAgence();
		dateComptable = virementVo.getDateComptableAgence();
		boolean etatValiditeBenif = false;
		boolean etatBenifMemeAgence = false;
		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");

		Long[] etatsVirements =
				{ Constants.COD_ETAT_VIREMENT_ATTENTE, Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN,
						Constants.COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF };

		String heureString = formaterHeure.format(new Date());

		long montant_Rejets = 0;
		int nbreRejets = 0;

		List<DetailVirement> listeDetailVirementsFinal = new ArrayList<DetailVirement>();
		this.setCroFlag(false);

		long numLotAgence = 0;
		String numLot = "";

		long mntVirementADT = 0;
		long nbrVirementADT = 0;
		long numeroLigneADT = 0;
		String codRefcVir = "";
		try {

			// ************** Recupuration Rib DO **********//

			PrimitiveVO primitiveVO = new PrimitiveVO();

			GetRibTrt getRibTrt = new GetRibTrt();

			primitiveVO = (PrimitiveVO) getRibTrt.exec(globalVirement.getContratCpt());
			ribDO = primitiveVO.getVString();

			operationMoyPayCompteDO = virementVo.getOperationMoyPay();

			// *********** Find Liste details for global virement *******************//

			// ISearchEngine searchEngine =
			// (ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");
			// ICriteria criteria = searchEngine.createCriteria();
			// IExpression expression = searchEngine.createExpression();
			//
			// criteria.add(expression.eq("detailVirementId.numSeqGvir", globalVirement.getNumSeqGvir()));
			// criteria.add(expression.in("etaDetvDetv", etatsVirements));
			//
			// List<DetailVirement> list_Details =
			// new ArrayList<DetailVirement>(searchEngine.find(DetailVirement.class, criteria));
			//
			// // itérateur
			// Iterator<DetailVirement> iterateur = list_Details.iterator();
			//
			// while (iterateur.hasNext()) {
			// DetailVirement detailVirementObj = (DetailVirement) iterateur.next();
			//
			// if (dateComptable.compareTo(detailVirementObj.getDatEchDetv()) == 0
			// || dateComptable.compareTo(detailVirementObj.getDatEchDetv()) == 1) {
			// trouve = true;
			// listeDetailVirementsFinal.add(detailVirementObj);
			//
			// }
			// }
			//
			// // *********** Parcours liste details one by one *******************//
			//
			// if (listeDetailVirementsFinal != null && listeDetailVirementsFinal.size() > 0) {
			//
			// for (DetailVirement detailVirementObj : listeDetailVirementsFinal) {

			String codeAgenceCompteBenif = "";

			codeBanque = detailVirementObj.getRibBenDetv().substring(0, 2);
			Produit produit = new Produit();
			produit.setCodPrdPrd(Constants.COD_PRODUIT_VIREMENT_PERMANENT);

			// ************** Reference Inter siege *********//
			GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
			String codRefInter =
					generateReferenceInterSiege.getRISWithUpdate(globalVirement.getStructure().getCodStrcStrc(),
							dateComptable);

			// ***** Bénéficiaire BNA *****//
			if (codeBanque.equals("03")) {

				// *****Verifier Benif *****//
				VirementVo virementVoBenif = new VirementVo();

				virementVoBenif.setGlobalVirement(globalVirement);
				virementVoBenif.setDetailVirement(detailVirementObj);
				virementVoBenif.setDateComptableAgence(dateComptable);
				virementVoBenif.setStrRib("BENIF");
				virementVoBenif.setBoolValiderContratCptBENIF(false);
				VerifierValiditerRibBenifTrt verifierValiditerRibBenif = new VerifierValiditerRibBenifTrt();
				virementVoBenif = (VirementVo) verifierValiditerRibBenif.exec(virementVoBenif);
				String msgValidator = virementVoBenif.getMessageVerificationRib();
				logger.info("\n  /***** virementVo.isBoolValiderContratCptBENIF() ="
						+ virementVoBenif.isBoolValiderContratCptBENIF() + " ***/   \n");

				// / True : Valide ----- False : Invalide ---------------- ///
				// / virementVo.setBoolValiderContratCptBENIF(boolVerfierRibBenif);
				if (virementVoBenif.isBoolValiderContratCptBENIF() == false) {

					etatValiditeBenif = false;

					logger.info("\n  /***** RIB BENIF Non Valide  ***/   \n");
				} else {
					etatValiditeBenif = true;
					logger.info("\n  /***** RIB BENIF Valide  ***/   \n");
				}
				// **************** Virement même BNA/BNA même Agence *************//
				codeAgenceCompteBenif = detailVirementObj.getRibBenDetv().substring(5, 8);
				if (new Long(codeAgenceCompteBenif).longValue() == globalVirement.getStructure().getCodStrcStrc()
						.longValue()) {
					etatBenifMemeAgence = true;
				} else {
					etatBenifMemeAgence = false;

				}

				// **** RIB Bénéficiaire en Dinars Convertible et RIB DO est en Dinars Convertible ****//
				boolean boolRibBenfiEnDinarsConvertible = false;
				boolean boolRibDOEnDinarsConv = false;
				boolean verifierLiaisonCompte = false;
				String CodeProduitCpteBenif = detailVirementObj.getRibBenDetv().substring(8, 12);
				int i = 0;

				while (boolRibDOEnDinarsConv == false && i < Constants.listCompteEnDinarsConvertibles.length) {
					if (globalVirement.getContratCpt().getContratCptId().getCodPrdPrd().longValue() == Constants.listCompteEnDinarsConvertibles[i]
							.longValue()) {
						boolRibDOEnDinarsConv = true;
					}
					i++;
				}

				i = 0;
				while (boolRibBenfiEnDinarsConvertible == false && i < Constants.listCompteEnDinarsConvertibles.length) {
					if (new Long(CodeProduitCpteBenif).longValue() == Constants.listCompteEnDinarsConvertibles[i]
							.longValue()) {
						boolRibBenfiEnDinarsConvertible = true;
					}
					i++;
				}
				if (boolRibBenfiEnDinarsConvertible == true && boolRibDOEnDinarsConv == true) {

					verifierLiaisonCompte = true;
				}

				// ********************************************************************//

				if (verifierLiaisonCompte == true) {
					// ***** Envoi Cro 1064 *****//
					VirementVo virementVo1064 = new VirementVo();
					Operation operation1064 = new Operation();

					operation1064.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT_COMPTE_DEVISES);
					virementVo1064.setOperation(operation1064);
					virementVo1064.setProduit(produit);
					virementVo1064.setGlobalVirement(globalVirement);
					virementVo1064.setDetailVirement(detailVirementObj);
					virementVo1064.setDateComptableAgence(dateComptable);
					virementVo1064.setBoolValiderContratCptBENIF(etatValiditeBenif);
					virementVo1064.setEtatBenifMemeAgence(etatBenifMemeAgence);
					CreationCROPositionVirementCompteDevisesTrt creationCROPositionVirementCompteDevises =
							new CreationCROPositionVirementCompteDevisesTrt();
					virementVo1064 = (VirementVo) creationCROPositionVirementCompteDevises.exec(virementVo1064);

				} else {
					// ***** Envoi Cro 822 *****//

					VirementVo virementVo2 = new VirementVo();
					Operation operation = new Operation();

					operation.setCodOperOper(Constants.COD_OPER_POSITION_VIREMENT);
					virementVo2.setOperation(operation);
					virementVo2.setProduit(produit);
					virementVo2.setGlobalVirement(globalVirement);
					virementVo2.setDetailVirement(detailVirementObj);
					virementVo2.setDateComptableAgence(dateComptable);
					virementVo2.setBoolValiderContratCptBENIF(etatValiditeBenif);
					virementVo2.setEtatBenifMemeAgence(etatBenifMemeAgence);
					CreationCROPositionVirementTrt createCroPositionVirement = new CreationCROPositionVirementTrt();
					virementVo2 = (VirementVo) createCroPositionVirement.exec(virementVo2);
				}
				if (new Long(codeAgenceCompteBenif).longValue() == globalVirement.getStructure().getCodStrcStrc()
						.longValue()) {

					etatBenifMemeAgence = true;

					if (etatValiditeBenif == false) {

						// ***** Envoi Cro 823 *****//
						VirementVo virementVoRejet = new VirementVo();
						Operation operationRejet = new Operation();
						operationRejet.setCodOperOper(Constants.COD_OPER_REJET_VIREMENT);
						virementVoRejet.setOperation(operationRejet);
						virementVoRejet.setProduit(produit);
						virementVoRejet.setDetailVirement(detailVirementObj);
						virementVoRejet.setDateComptableAgence(dateComptable);
						virementVoRejet.setOperationMoyPay(operationMoyPayCompteDO);
						virementVoRejet.setEtatBenifMemeAgence(etatBenifMemeAgence);
						CreationCRORejetVirementTrt createCroRejetVirement = new CreationCRORejetVirementTrt();
						virementVoRejet = (VirementVo) createCroRejetVirement.exec(virementVoRejet);

						// ***** Envoi Cro 948 *****//
						VirementVo virementVoReaf = new VirementVo();
						Operation operationReaf = new Operation();
						operationReaf.setCodOperOper(Constants.COD_OPER_REAFFECTATION_REJETS_VIREMENT);
						virementVoReaf.setOperation(operationReaf);
						virementVoReaf.setProduit(produit);
						virementVoReaf.setDetailVirement(detailVirementObj);
						virementVoReaf.setDateComptableAgence(dateComptable);
						CreationCROReaffectationRejetsVirementTrt createCroReaffectationRejetsVirement =
								new CreationCROReaffectationRejetsVirementTrt();
						virementVoReaf = (VirementVo) createCroReaffectationRejetsVirement.exec(virementVoReaf);
						montant_Rejets += detailVirementObj.getMntDetvDetv();
						nbreRejets++;
						detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
						detailVirementObj.setMotifRejetDetv(msgValidator);
					} else {
						detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_EXECUTER);
					}

				} else if (new Long(codeAgenceCompteBenif).longValue() != globalVirement.getStructure()
						.getCodStrcStrc().longValue()) {

					etatBenifMemeAgence = false;

					// ********* Insertion dans Flux Comptable Virement *******//

					FluxComptVirement fluxComptVirement = new FluxComptVirement();
					Long numSeqFlux = virementGlobalDAO.getSequenceFluxComptableVirement();

					fluxComptVirement.setNumSeqFlux(numSeqFlux);
					fluxComptVirement.setNumSeqGvir(detailVirementObj.getDetailVirementId().getNumSeqGvir());
					fluxComptVirement.setCodStrcStrc(new Long(codeAgenceCompteBenif));
					fluxComptVirement.setMntVirStrc(detailVirementObj.getMntDetvDetv());
					fluxComptVirement.setNbreVirStrc(new Long(1));
					fluxComptVirement.setTypeFluxVir(new Long(0));
					fluxComptVirement.setDatOperFlux(dateComptable);
					fluxComptVirement.setDatSysFlux(new Date());

					crudService.create(fluxComptVirement);

					// ***** Envoi Cro 721 *****//
					VirementVo virementVo3 = new VirementVo();
					Operation operation2 = new Operation();
					operation2.setCodOperOper(Constants.COD_OPER_ENVOI_VIREMENT);
					virementVo3.setOperationMoyPay(operationMoyPayCompteDO);
					virementVo3.setOperation(operation2);
					virementVo3.setProduit(produit);
					virementVo3.setDateComptableAgence(dateComptable);
					virementVo3.setDetailVirement(detailVirementObj);
					virementVo3.setGlobalVirement(globalVirement);
					virementVo3.setCodStrcRecp(fluxComptVirement.getCodStrcStrc());
					virementVo3.setMontantGlobalByStructure(fluxComptVirement.getMntVirStrc().longValue());
					virementVo3.setCodRefInter(codRefInter);
					CreationCROEnvoiVirementTrt createCroEnvoiVirement = new CreationCROEnvoiVirementTrt();
					virementVo3 = (VirementVo) createCroEnvoiVirement.exec(virementVo3);

					// ***** Envoi Cro 821 *****//
					VirementVo virementVo4 = new VirementVo();
					Operation operation3 = new Operation();
					operation3.setCodOperOper(Constants.COD_OPER_RECEPTION_VIREMENT_AGENCE);
					virementVo4.setOperationMoyPay(operationMoyPayCompteDO);
					virementVo4.setOperation(operation3);
					virementVo4.setProduit(produit);
					virementVo4.setDateComptableAgence(dateComptable);
					virementVo4.setGlobalVirement(globalVirement);
					virementVo4.setDetailVirement(detailVirementObj);
					virementVo4.setCodStrcRecp(fluxComptVirement.getCodStrcStrc());
					virementVo4.setMontantGlobalByStructure(fluxComptVirement.getMntVirStrc().longValue());
					virementVo4.setCodRefInter(codRefInter);
					CreationCROReceptionVirementParAgenceTrt createCroReceptionVirementParAgence =
							new CreationCROReceptionVirementParAgenceTrt();
					virementVo4 = (VirementVo) createCroReceptionVirementParAgence.exec(virementVo4);

					if (etatValiditeBenif == false) {

						// ***** Envoi Cro 823 *****//
						VirementVo virementVoRejet = new VirementVo();
						Operation operationRejet = new Operation();
						operationRejet.setCodOperOper(Constants.COD_OPER_REJET_VIREMENT);
						virementVoRejet.setOperation(operationRejet);
						virementVoRejet.setProduit(produit);
						virementVoRejet.setDetailVirement(detailVirementObj);
						virementVoRejet.setDateComptableAgence(dateComptable);
						virementVoRejet.setOperationMoyPay(operationMoyPayCompteDO);
						virementVoRejet.setEtatBenifMemeAgence(etatBenifMemeAgence);
						CreationCRORejetVirementTrt createCroRejetVirement = new CreationCRORejetVirementTrt();
						virementVoRejet = (VirementVo) createCroRejetVirement.exec(virementVoRejet);

						// ***** Envoi Cro 948 *****//
						VirementVo virementVoReaf = new VirementVo();
						Operation operationReaf = new Operation();
						operationReaf.setCodOperOper(Constants.COD_OPER_REAFFECTATION_REJETS_VIREMENT);
						virementVoReaf.setOperation(operationReaf);
						virementVoReaf.setProduit(produit);
						virementVoReaf.setDetailVirement(detailVirementObj);
						virementVoReaf.setDateComptableAgence(dateComptable);
						CreationCROReaffectationRejetsVirementTrt createCroReaffectationRejetsVirement =
								new CreationCROReaffectationRejetsVirementTrt();
						virementVoReaf = (VirementVo) createCroReaffectationRejetsVirement.exec(virementVoReaf);

						// ***** Envoi Cro 824 *****//

						VirementVo virementVoEnvoiRejets = new VirementVo();
						Operation operationEnvoiRejets = new Operation();
						operationEnvoiRejets.setCodOperOper(Constants.COD_OPER_ENVOI_REJETS_VIREMENT);
						virementVoEnvoiRejets.setOperation(operationEnvoiRejets);
						virementVoEnvoiRejets.setProduit(produit);
						virementVoEnvoiRejets.setDetailVirement(detailVirementObj);
						virementVoEnvoiRejets.setDateComptableAgence(dateComptable);
						virementVoEnvoiRejets.setOperationMoyPay(operationMoyPayCompteDO);
						CreationCROEnvoiRejetsVirementTrt createCroEnvoiRejetsVirement =
								new CreationCROEnvoiRejetsVirementTrt();
						virementVoEnvoiRejets = (VirementVo) createCroEnvoiRejetsVirement.exec(virementVoEnvoiRejets);

						// ***** Envoi Cro 947 *****//
						VirementVo virementVoRecptRejets = new VirementVo();
						Operation operationRecptRejets = new Operation();
						operationRecptRejets.setCodOperOper(Constants.COD_OPER_RECEPTION_REJETS_VIREMENT);
						virementVoRecptRejets.setOperation(operationRecptRejets);
						virementVoRecptRejets.setProduit(produit);
						virementVoRecptRejets.setDetailVirement(detailVirementObj);
						virementVoRecptRejets.setDateComptableAgence(dateComptable);
						virementVoRecptRejets.setOperationMoyPay(operationMoyPayCompteDO);
						CreationCROReceptionRejetsVirementParAgenceTrt creationCROReceptionRejetsVirementParAgence =
								new CreationCROReceptionRejetsVirementParAgenceTrt();
						virementVoRecptRejets =
								(VirementVo) creationCROReceptionRejetsVirementParAgence.exec(virementVoRecptRejets);

						// *************************//

						montant_Rejets += detailVirementObj.getMntDetvDetv();
						nbreRejets++;
						detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_REJETER);
						detailVirementObj.setMotifRejetDetv(msgValidator);
					} else {
						detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_EXECUTER);
					}

				}

				// *********** Update Detail ***********//

				detailVirementObj.setDatExecDetv(dateComptable);
				detailVirementObj.setDatSysDetv(new Date());
				detailVirementObj.setTimeExecDetv(heureString);
				detailVirementObj.setCodFlagDetv(Constants.COD_FLAG_FICHIER_VIREMENT_NON_TRAITER);
				crudService.update(detailVirementObj);

			}

			// ***** Bénéficiaire non BNA *****//

			else {
				etatBenifMemeAgence = false;
				// **** Virement autre Banque SIBTEL ****//

				if (detailVirementObj.getMntDetvDetv().longValue() < Constants.MONTANT_VIREMENT_SGMT.longValue()) {

					// ************** Recupuration du Numero de Lot **************** //
					VirementVo virementVoLot = new VirementVo();

					virementVoLot.setStructure(globalVirement.getStructure());
					GetNumLotAgenceTrt getNumLotAgence = new GetNumLotAgenceTrt();
					virementVoLot = (VirementVo) getNumLotAgence.exec(virementVoLot);

					numLotAgence = virementVoLot.getNumLot();

					numLot += numLotAgence;

					for (int i = new String(numLotAgence + "").length(); i < 4; i++) {

						numLot = "0" + numLot;

					}

					logger.info("numLot : " + numLot);
					String codRefInterFichier = codRefInter.substring(0, 6) + lpadS(numLotAgence + "", "0", 3);

					// ***** Partie Creation Fichier *******//

					String codeStrcBCT =
							virementGlobalDAO.getCodeStructureBCT(globalVirement.getStructure().getCodStrcStrc());

					pathVirementTravail =
							File.separatorChar + Configuration.getParentPath() + File.separatorChar
									+ Configuration.getLocalPathCheque() + File.separatorChar + "emis"
									+ File.separatorChar + "virement" + File.separatorChar + "agence" + codeStrcBCT
									+ File.separatorChar + formaterDate2.format(dateComptableFichier)
									+ File.separatorChar + "travail" + File.separatorChar;

					fileName =
							pathVirementTravail + "03" + "-" + codeStrcBCT + "-" + "10" + "-" + "21" + "-" + numLot
									+ "-" + formaterDate2.format(dateComptable) + "-"
									+ formaterHeureFichier.format(new Date()) + "-" + codeDevise + ".ENV";

					// ********* Insertion dans Flux Comptable Virement *******//

					FluxComptVirement fluxComptVirement = new FluxComptVirement();
					Long numSeqFlux = virementGlobalDAO.getSequenceFluxComptableVirement();

					fluxComptVirement.setNumSeqFlux(numSeqFlux);
					fluxComptVirement.setNumSeqGvir(detailVirementObj.getDetailVirementId().getNumSeqGvir());
					fluxComptVirement.setCodStrcStrc(new Long(900));
					fluxComptVirement.setMntVirStrc(detailVirementObj.getMntDetvDetv());
					fluxComptVirement.setNbreVirStrc(new Long(1));
					fluxComptVirement.setTypeFluxVir(new Long(0));
					fluxComptVirement.setDatOperFlux(dateComptable);
					fluxComptVirement.setDatSysFlux(new Date());

					crudService.create(fluxComptVirement);

					// ***** Envoi Cro 721 *****//
					Produit produitADT = new Produit();
					produitADT.setCodPrdPrd(Constants.COD_PRODUIT_VIREMENT_ADT);
					VirementVo virementVo3 = new VirementVo();
					Operation operation2 = new Operation();
					operation2.setCodOperOper(Constants.COD_OPER_ENVOI_VIREMENT);
					virementVo3.setOperationMoyPay(operationMoyPayCompteDO);
					virementVo3.setOperation(operation2);
					virementVo3.setProduit(produitADT);
					virementVo3.setDateComptableAgence(dateComptable);
					virementVo3.setDetailVirement(detailVirementObj);
					virementVo3.setGlobalVirement(globalVirement);
					virementVo3.setCodStrcRecp(fluxComptVirement.getCodStrcStrc());
					virementVo3.setMontantGlobalByStructure(fluxComptVirement.getMntVirStrc().longValue());
					// virementVo3.setCodRefInter(codRefInter);
					virementVo3.setCodRefInter(codRefInterFichier);
					CreationCROEnvoiVirementTrt createCroEnvoiVirement = new CreationCROEnvoiVirementTrt();
					virementVo3 = (VirementVo) createCroEnvoiVirement.exec(virementVo3);

					// ***** Ecriture dans le fichier *****//

					file = new File(fileName);
					logger.info("file : " + file.getAbsolutePath());

					boolean exists = file.exists();

					if (!exists) {
						file.createNewFile();

					}

					mntVirementADT += detailVirementObj.getMntDetvDetv();
					nbrVirementADT++;
					numeroLigneADT++;
					VirementVo virementVoCreationFile = new VirementVo();

					virementVoCreationFile.setFile(file);
					virementVoCreationFile.setGlobalVirement(globalVirement);
					virementVoCreationFile.setDetailVirement(detailVirementObj);
					virementVoCreationFile.setDateComptableAgence(dateComptable);
					virementVoCreationFile.setMntVirementADT(mntVirementADT);
					virementVoCreationFile.setNbrVirementADT(nbrVirementADT);
					virementVoCreationFile.setNumeroLigneADT(numeroLigneADT);
					virementVoCreationFile.setNumeroLot(numLot);
					CreationFichierVirementTrt creerFichierVirement = new CreationFichierVirementTrt();
					virementVoCreationFile = (VirementVo) creerFichierVirement.exec(virementVoCreationFile);

					// **** update Detail ****//
					detailVirementObj.setCodFlagDetv(Constants.COD_FLAG_FICHIER_VIREMENT_TRAITER);
					detailVirementObj.setRefFichDetv(file.getName());

					// **** Recupuration du fichier ****//
					virementVo.setNumeroLot(numLot);
					virementVo.getListeFile().add(file);
					virementVo.getListeFilesNames().add(file.getName());

				}

				// **** Virement autre Banque SGMT****//

				else {

					// ********* Insertion dans Flux Comptable Virement *******//

					FluxComptVirement fluxComptVirement = new FluxComptVirement();
					Long numSeqFlux = virementGlobalDAO.getSequenceFluxComptableVirement();

					fluxComptVirement.setNumSeqFlux(numSeqFlux);
					fluxComptVirement.setNumSeqGvir(detailVirementObj.getDetailVirementId().getNumSeqGvir());
					fluxComptVirement.setCodStrcStrc(new Long(900));
					fluxComptVirement.setMntVirStrc(detailVirementObj.getMntDetvDetv());
					fluxComptVirement.setNbreVirStrc(new Long(1));
					fluxComptVirement.setTypeFluxVir(new Long(1));
					fluxComptVirement.setDatOperFlux(dateComptable);
					fluxComptVirement.setDatSysFlux(new Date());

					crudService.create(fluxComptVirement);

					// ***** Envoi Cro 721 *****//

					VirementVo virementVo3 = new VirementVo();
					Operation operation2 = new Operation();
					operation2.setCodOperOper(Constants.COD_OPER_ENVOI_VIREMENT);
					virementVo3.setOperationMoyPay(operationMoyPayCompteDO);
					virementVo3.setOperation(operation2);
					Produit produitADT = new Produit();
					produitADT.setCodPrdPrd(Constants.COD_PRODUIT_VIREMENT_ADT);
					virementVo3.setProduit(produitADT);
					virementVo3.setDateComptableAgence(dateComptable);
					virementVo3.setDetailVirement(detailVirementObj);
					virementVo3.setGlobalVirement(globalVirement);
					virementVo3.setCodStrcRecp(fluxComptVirement.getCodStrcStrc());
					virementVo3.setMontantGlobalByStructure(fluxComptVirement.getMntVirStrc().longValue());
					virementVo3.setCodRefInter(codRefInter);
					CreationCROEnvoiVirementTrt createCroEnvoiVirement = new CreationCROEnvoiVirementTrt();
					virementVo3 = (VirementVo) createCroEnvoiVirement.exec(virementVo3);

					// ***** Envoi Cro 1203 *****//

					VirementVo virementVoSGMT = new VirementVo();
					Operation operationSGMT = new Operation();
					operationSGMT.setCodOperOper(Constants.COD_OPER_RECEPTION_VIREMENT_SGMT);
					virementVoSGMT.setOperationMoyPay(operationMoyPayCompteDO);
					virementVoSGMT.setOperation(operationSGMT);
					virementVoSGMT.setProduit(produit);
					virementVoSGMT.setDateComptableAgence(dateComptable);
					virementVoSGMT.setDetailVirement(detailVirementObj);
					virementVoSGMT.setGlobalVirement(globalVirement);
					virementVoSGMT.setCodStrcRecp(fluxComptVirement.getCodStrcStrc());
					virementVoSGMT.setCodRefInter(codRefInter);
					virementVoSGMT.setMontantGlobalByStructure(fluxComptVirement.getMntVirStrc().longValue());
					CreationCROReceptionVirementSGMTTrt creationCROReceptionVirementSGMT =
							new CreationCROReceptionVirementSGMTTrt();
					virementVoSGMT = (VirementVo) creationCROReceptionVirementSGMT.exec(virementVoSGMT);

					codRefcVir = codRefInter;

					// ***** Insertion dans la table SGMT *****//

					// ***** Recupuration du Numero de Vir *** //

					VirementVo virementSGMT = new VirementVo();

					GetNumVirSGMTTrt getNumVirSGMTTrt = new GetNumVirSGMTTrt();

					virementSGMT = (VirementVo) getNumVirSGMTTrt.exec(virementSGMT);

					Long numVirSgmt = virementSGMT.getNumVirSgmt();

					logger.info("numVirSgmt : " + numVirSgmt);

					VirSgmt virSgmt = new VirSgmt();

					SimpleDateFormat formaterDateddMMyy = new SimpleDateFormat("ddMMyy");
					String codCodVir = formaterDateddMMyy.format(dateComptable) + numVirSgmt;
					virSgmt.setCodCodVir(codCodVir);

					virSgmt.setDatOperVir(formaterDate.parse(formaterDate.format(detailVirementObj.getDatEchDetv())));
					virSgmt.setDatVirVir(formaterDate.parse(formaterDate.format(dateComptable)));
					virSgmt.setCodDevDev(new Short(codeDevise));
					virSgmt.setNumVirVir(new Integer(detailVirementObj.getDetailVirementId().getNumSeqDetv()));
					virSgmt.setCodRefVir(globalVirement.getNumSeqGvir());
					virSgmt.setCodSensVir("1");

					if (ribDO.length() == 20) {
						virSgmt.setNumRibdVir(new BigDecimal(ribDO));

					}
					virSgmt.setCodBdBank(new Short("3"));

					String nomPre_RsDO = globalVirement.getContratCpt().getNomIntiCcpt();
					nomPre_RsDO = UtilCtr.corrigerChaineCaractere(nomPre_RsDO);

					if (nomPre_RsDO.length() > 30) {
						virSgmt.setNomNomdVir(nomPre_RsDO.substring(0, 30));
					} else {
						virSgmt.setNomNomdVir(nomPre_RsDO);
					}
					virSgmt.setNumRibbVir(new BigDecimal(detailVirementObj.getRibBenDetv()));
					String codeBanqueBenif = detailVirementObj.getRibBenDetv().substring(0, 2);
					String codeAgenceBenif = detailVirementObj.getRibBenDetv().substring(2, 5);

					if (new Short(codeBanqueBenif).shortValue() == 0) // ****** Bank Centrale
					{
						if (new Short(codeAgenceBenif).shortValue() == 999) {

							virSgmt.setCodOperOpgm("VBSC");

						} else if (detailVirementObj.getRibBenDetv().equals("00038000401001700004")) {

							virSgmt.setCodOperOpgm("VBO");

						} else if (detailVirementObj.getRibBenDetv().substring(0, 12).equals("000380004012")) {

							virSgmt.setCodOperOpgm("VBB");

						} else {

							virSgmt.setCodOperOpgm("VBTC");
						}
					} else if (new Short(codeBanqueBenif).shortValue() == 17) {

						virSgmt.setCodOperOpgm("VBCP");

					} else {
						virSgmt.setCodOperOpgm("VBP");
					}

					virSgmt.setCodBbBank(new Short(codeBanqueBenif));

					String nomPre_RsBenif = detailVirementObj.getNomBenDetv();
					nomPre_RsBenif = UtilCtr.corrigerChaineCaractere(nomPre_RsBenif);
					if (nomPre_RsBenif.length() > 30) {
						virSgmt.setNomNombVir(nomPre_RsBenif.substring(0, 30));
					} else {
						virSgmt.setNomNombVir(nomPre_RsBenif);
					}

					virSgmt.setMntVirVir(detailVirementObj.getMntDetvDetv());

					String motifOperation = detailVirementObj.getMotiDetvDetv();
					motifOperation = UtilCtr.corrigerChaineCaractere(motifOperation);

					if (motifOperation.length() > 45) {
						virSgmt.setLibMotifVir(motifOperation.substring(0, 45));
					} else {
						virSgmt.setLibMotifVir(motifOperation);
					}

					virSgmt.setDatEnvVir(formaterDate.parse("11/11/1111"));
					virSgmt.setCodAgAg(globalVirement.getStructure().getCodStrcStrc().shortValue());

					virSgmt.setCodSitdVir(new Short("0"));
					virSgmt.setCodTypcVir(new Short("1"));
					virSgmt.setCodNatcVir("0");
					virSgmt.setCodVldVir(new Short("2")); // A verifier
					virSgmt.setCodUserUser("9999");
					virSgmt.setCodResVir(new Short("0"));
					virSgmt.setCodRefcVir(codRefcVir);
					// virSgmt.setCodUsrvUser("9999");
					virSgmt.setDatDatJrn(formaterDate.parse(formaterDate.format(dateComptable)));

					crudService.create(virSgmt);

					detailVirementObj.setRefFichDetv("SGMT");
					detailVirementObj.setCodFlagDetv(Constants.COD_FLAG_FICHIER_VIREMENT_TRAITER);

				}

				// *********** Update Detail ***********//
				detailVirementObj.setEtaDetvDetv(Constants.COD_ETAT_VIREMENT_EXECUTER);
				detailVirementObj.setDatExecDetv(dateComptable);
				detailVirementObj.setDatSysDetv(new Date());
				detailVirementObj.setTimeExecDetv(heureString);
				crudService.update(detailVirementObj);

			}

			// / ------------------------------------------------------------------ ///
			virementVo.setStadeEnregistrement(true);

		} catch (Exception e) {

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans ExecutionDoVirPermanentTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("ExecutionDoVirPermanentTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau ExecutionDoVirPermanentTrt : ", e);
			virementVo.setMessageValidation("Probléme dans ExecutionDoVirPermanentTrt");
			if (file != null && file.exists()) {

				file.delete();

			}
			virementVo.setStadeEnregistrement(false);
			throw new RuntimeException();

		}
		return (virementVo);

	}

	public void genCroText(ValueObject vo) {
	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public static String lpadS(String valueToPad, String filler, int size) {
		StringBuilder builder = new StringBuilder();

		while (builder.length() + valueToPad.length() < size) {
			builder.append(filler);
		}
		builder.append(valueToPad);
		return builder.toString();
	}
}