package com.bna.smile.model.virement.traitement;

import java.util.Date;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.service.VirementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class VerifierValiditerRibBenifTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	String msgValidator = "";

	public VerifierValiditerRibBenifTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		VirementVo virementVo = (VirementVo) vo;

		DetailVirement detailVirement = (DetailVirement) virementVo.getDetailVirement();
		GlobalVirement globalVirement = (GlobalVirement) virementVo.getGlobalVirement();

		Date dateComptableAgence = new Date();
		dateComptableAgence = virementVo.getDateComptableAgence();
		// // Verifier si le RIB Beneficiaire n'est pas en Devise
		boolean boolRibBenifEnDevise = false;
		boolean verifierLiaisonCompte = false;
		boolean boolRibBenfiEnDinarsConvertible = false;
		boolean boolRibBenfiSpeciauxEnDinars = false;
		boolean boolRibDOEnDinars = false;

		this.setCroFlag(false);

		boolean boolVerfierRibBenif = false;

		VirementService virementService = (VirementService) context.getBean("iVirementService");

		try {

			String RibBenif = "";

			RibBenif = detailVirement.getRibBenDetv();

			VirementVo objVirementVoContratCptBenif = new VirementVo();
			ContratCpt contratCptObj = new ContratCpt();
			ContratCptId contratCptIdObj = new ContratCptId();

			if (RibBenif.length() == 20) {

				contratCptIdObj.setCodStrcStrc(new Long(RibBenif.substring(5, 8)));
				contratCptIdObj.setCodPrdPrd(new Long(RibBenif.substring(8, 12)));
				contratCptIdObj.setNumCcptCcpt(new Long(RibBenif.substring(12, 18)));

				System.out.println("RibBenif.substring(5, 8) = " + RibBenif.substring(5, 8));
				System.out.println("RibBenif.substring(8, 12) = " + RibBenif.substring(8, 12));
				System.out.println("RibBenif.substring(12, 18) = " + RibBenif.substring(12, 18));

				contratCptObj.setContratCptId(contratCptIdObj);

				objVirementVoContratCptBenif.setContratCpt(contratCptObj);
				objVirementVoContratCptBenif.setGlobalVirement(globalVirement);
				objVirementVoContratCptBenif.setDetailVirement(detailVirement);
				objVirementVoContratCptBenif.setDateComptableAgence(dateComptableAgence);

				objVirementVoContratCptBenif.setStrRib("BENIF");

				// virementVo.getStrRib().equals("BENIF")
				// / false : Contrat Invalde ; True Contrat valide

				objVirementVoContratCptBenif =
						(VirementVo) virementService.verfierContratCpt(objVirementVoContratCptBenif);
				boolVerfierRibBenif = objVirementVoContratCptBenif.isBoolValiderContratCpt();

				if (boolVerfierRibBenif == false) {
					// System.out.println(" RIB DO NON VALIDE ");
					// logger.info("\n  /***** RIB DO NON VALIDE ***/   \n" );

					// / Rejeter Detail Virement
					objVirementVoContratCptBenif =
							(VirementVo) virementService.rejeterDetailVirement(objVirementVoContratCptBenif);
					msgValidator = "Compte Bénéficiaire non valide";
					boolVerfierRibBenif = false;

				} else {

					if (contratCptObj != null) {

						int i = 0;
						while (boolRibBenifEnDevise == false && i < Constants.listCompteEnDevises.length) {
							if (new Long(contratCptObj.getContratCptId().getCodPrdPrd()).longValue() == Constants.listCompteEnDevises[i]
									.longValue()) {
								boolRibBenifEnDevise = true;
							}
							i++;
						}

						if (boolRibBenifEnDevise == true) {
							// msgValidator =
							// "Impossible d'éffectuer un virement vers un RIB Bénéficiaire en Devises !";
							msgValidator = "Compte Bénéficiaire en Devise";
							verifierLiaisonCompte = true;

						} else {

							// / Veriffier si Le RIB Beneficiaire est en Dinars Convertible
							i = 0;
							while (boolRibBenfiEnDinarsConvertible == false
									&& i < Constants.listCompteEnDinarsConvertibles.length) {
								if (new Long(contratCptObj.getContratCptId().getCodPrdPrd()).longValue() == Constants.listCompteEnDinarsConvertibles[i]
										.longValue()) {
									boolRibBenfiEnDinarsConvertible = true;
								}
								i++;
							}

							// / Veriffier si Le RIB Beneficiaire est un Compte Spéciaux en Dinars
							i = 0;
							while (boolRibBenfiSpeciauxEnDinars == false
									&& i < Constants.listCompteSpeciauxEnDinars.length) {
								if (new Long(contratCptObj.getContratCptId().getCodPrdPrd()).longValue() == Constants.listCompteSpeciauxEnDinars[i]
										.longValue()) {
									boolRibBenfiSpeciauxEnDinars = true;
								}
								i++;
							}

							// / Veriffier si Le RIB Donneur d'Ordre est en Dinars
							i = 0;
							while (boolRibDOEnDinars == false && i < Constants.listCompteEnDinars.length) {
								if (globalVirement.getContratCpt().getContratCptId().getCodPrdPrd().longValue() == Constants.listCompteEnDinars[i]
										.longValue()) {
									boolRibDOEnDinars = true;
								}
								i++;
							}

							// / RIB Bénéficiaire en Dinars Convertible et RIB DO est en Dinars
							if (boolRibBenfiEnDinarsConvertible == true && boolRibDOEnDinars == true) {
								msgValidator = "Compte Bénéficiaire en Dinars Convertible";

								// msgValidator =
								// "Impossible d'éffectuer un virement depuis un Compte en Dinars vers un Compte En Dinars Convertible !";
								verifierLiaisonCompte = true;
							}// / RIB Bénéficiaire en Dinars Convertible et RIB DO est en Dinars
							else if (boolRibBenfiSpeciauxEnDinars == true && boolRibDOEnDinars == true) {
								// msgValidator =
								// "Impossible d'éffectuer un virement depuis un Compte en Dinars vers un Compte Spéciaux en Dinars !";
								msgValidator = "Compte Bénéficiaire Spéciaux en Dinars";
								verifierLiaisonCompte = true;
							}

						}
					}

					if (verifierLiaisonCompte == true) {

						boolVerfierRibBenif = false;

					} else {

						boolVerfierRibBenif = true;

						// System.out.println(" RIB DO VALIDE ");
						// logger.info("\n  /***** RIB DO  VALIDE ***/   \n" );

					}
				}

			}

			// / True : Valide ----- False : Invalide ---------------- ///
			System.out.println("boolVerfierRibBenif =" + boolVerfierRibBenif);
			logger.info("\n  /***** boolVerfierRibBenif =" + boolVerfierRibBenif + " ***/   \n");
			// virementVo.isBoolValiderContratCptBENIF()
			virementVo.setBoolValiderContratCptBENIF(boolVerfierRibBenif);

			virementVo.setMessageVerificationRib(msgValidator);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans VerifierValiditerRibBenifTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("VerifierValiditerRibBenifTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau VerifierValiditerRibBenifTrt : ", e);
			virementVo.setMessageValidation("Probléme dans VerifierValiditerRibBenifTrt");
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