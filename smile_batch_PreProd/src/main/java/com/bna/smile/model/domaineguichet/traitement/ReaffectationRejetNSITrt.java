package com.bna.smile.model.domaineguichet.traitement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.BatchRejetVirNSI;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DepPersonnel;
import com.bna.commun.model.Devise;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.service.CURService;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratCptByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetContratEtatCmd;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineguichet.dao.GuichetDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class ReaffectationRejetNSITrt extends Traitement {

	public ReaffectationRejetNSITrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	// à ne pas laisser en variable global
	ICriteria criteria = searchEngine.createCriteria();
	ICriteria criteriaAvanc = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	GuichetDAO guichetDao;
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
	String etatOp = "";
	BatchRejetVirNSI batchRejetVirNSI;

	public IValueObject perform(IValueObject vo) {
		VirementVo virementVo = (VirementVo) vo;
		batchRejetVirNSI = virementVo.getBatchRejetVirNSI();
		String ligne = "";
		Date dateCpt = new Date();
		Date dateSys = new Date();
		Date dateVal = new Date();
		Structure strc = new Structure();
		strc.setCodStrcStrc(949L);
		try {
			this.setVerifDomaine(false);
			this.setCroFlag(false);
			ligne = batchRejetVirNSI.getDonneBatr();

			dateCpt = DateHandler.strToDate(formatDate.format(new Date()));
			dateSys = DateHandler.strToDate(formatDate.format(new Date()));
			dateVal = DateHandler.strToDate(formatDate.format(new Date()));
			Long mnt = 0L;
			Long mntDev = 0L;
			String tmpMntDev = "";
			String motifOperation = ""; // Ajouté by Hichem pour extrait

			guichetDao = (GuichetDAO) context.getBean("guichetDAO");
			ContratCptId contratCptId = new ContratCptId();
			contratCptId.setCodStrcStrc(new Long(ligne.trim().substring(0, 3)));
			strc.setCodStrcStrc(contratCptId.getCodStrcStrc());

			dateCpt = virementVo.getDateComptableAgence();
			dateSys = virementVo.getDateComptableAgence();

			contratCptId.setCodPrdPrd(new Long(ligne.trim().substring(3, 7)));
			contratCptId.setNumCcptCcpt(new Long(ligne.trim().substring(7, 13)));
			GetContratCptByIdCmd getContratCptByIdCmd = new GetContratCptByIdCmd();
			ContratCpt cptPret = new ContratCpt();
			cptPret.setContratCptId(contratCptId);
			cptPret = (ContratCpt) getContratCptByIdCmd.execute(cptPret);

			if (cptPret != null && cptPret.getContratCptId() != null && cptPret.getCodEtatCcpt() != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
				dateVal = formatter.parse(ligne.trim().substring(25, 31));
				dateCpt = formatter.parse(ligne.trim().substring(17, 23));
				mnt = Long.valueOf(ligne.trim().substring(32, 43));
				motifOperation = ligne.trim().substring(44);

				OperationMoyPay operationMoyPay = new OperationMoyPay();
				Structure structureInit = new Structure();
				structureInit.setCodStrcStrc(contratCptId.getCodStrcStrc());
				Structure structureRecep = new Structure();
				structureRecep.setCodStrcStrc(contratCptId.getCodStrcStrc());

				Devise devise = new Devise();
				devise.setCodDevDev(cptPret.getDevise().getCodDevDev());
				operationMoyPay.setDevise(devise);
				operationMoyPay.setContratCpt(cptPret);
				operationMoyPay.setStructureInitiatrice(structureInit);
				operationMoyPay.setStructureReceptrice(structureRecep);

				operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
				Personnel personnel = new Personnel();
				personnel.setNumMatrUser(virementVo.getParamAgence().getNumMatrUser());
				operationMoyPay.setPersonnelInitiateur(personnel);// / personne initiatrice seulement au
																  // cas
																  // de

				operationMoyPay.setPersonnelValideur(personnel);// / personnel initiatrice = personnel
																// validateur
				Tache tache = new Tache();
				TacheId tacheId = new TacheId();
				tacheId.setCodOperOper(Constants.COD_OPER_VIR_SIEGE);
				tacheId.setCodTachTach(1L);
				tache.setTacheId(tacheId);
				operationMoyPay.setTache(tache);
				operationMoyPay.setDatOperOmp(dateCpt);
				operationMoyPay.setDatSystOmp(dateSys);
				operationMoyPay.setDatValOmp(dateVal);
				if (cptPret.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
					String libOp = getLibOp(Integer.parseInt(ligne.trim().substring(23, 25)));
					String libFichier = "";
					if (ligne.trim().length() > 53) {
						libFichier = ligne.trim().substring(53);
					}

					/********** Ajouté pour extrait *********/

					String codRefbOmp = libOp + " " + motifOperation;
					if (codRefbOmp.length() > 30) {
						codRefbOmp = codRefbOmp.substring(0, 29);
					}
					operationMoyPay.setCodRefbOmp(codRefbOmp);

					/*******************************************/

					// operationMoyPay.setCodRefbOmp(getLib(libOp, libFichier));
					operationMoyPay.setRefIns1Omp(ligne.trim().substring(44, Math.min(53, ligne.trim().length())));
				} else {
					tmpMntDev = ligne.trim().substring(44).replaceAll(" ", "");
					String libOp = getLibOp(Integer.parseInt(ligne.trim().substring(23, 25)));
					String libFichier = "";
					if (ligne.trim().length() > 63) {
						libFichier = ligne.trim().substring(63);
					}
					if (ligne.trim().substring(44).startsWith("      "))
						operationMoyPay.setCodRefbOmp(getLib(libOp, ""));
					else
						operationMoyPay.setCodRefbOmp(getLib(libOp, libFichier));
					operationMoyPay
							.setRefIns1Omp(tmpMntDev.trim().substring(10, Math.min(19, tmpMntDev.trim().length())));
				}
				operationMoyPay.setCodRefcOmp(ligne.trim().substring(13, 17) + ligne.trim().substring(23, 25));
				TypePiece typePieceDem = cptPret.getClient().getPersonne().getTypePiece();
				operationMoyPay.setTypePieceDemandeur(typePieceDem);
				operationMoyPay.setNumPcedOmp(cptPret.getClient().getPersonne().getNumPcePers());
				if (cptPret.getClient().getPersonne() != null
						&& cptPret.getClient().getPersonne().getNomRsPers() != null
						&& cptPret.getClient().getPersonne().getNomRsPers().length() > 0) {
					if (cptPret.getClient().getPersonne().getNomRsPers().length() > 60) {
						operationMoyPay.setNomNomdOmp(cptPret.getClient().getPersonne().getNomRsPers().substring(0, 59));
					} else {
						operationMoyPay.setNomNomdOmp(cptPret.getClient().getPersonne().getNomRsPers());
					}
				} else {
					operationMoyPay.setNomNomdOmp(cptPret.getClient().getPersonne().getNomNomPers());
					operationMoyPay.setNomPrndOmp(cptPret.getClient().getPersonne().getNomPrnPers());
				}
				if (ligne.trim().substring(43, 44).equals("-"))
					operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);
				else
					operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);

				if (cptPret.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
					operationMoyPay.setMontSoldCcpt(cptPret.getMontSoldCcpt());
					operationMoyPay.setMontDinOmp(mnt);
					if (operationMoyPay.getCodSensOmp().equals(Constants.COD_SENS_DB))
						operationMoyPay.setMontApreOmp(cptPret.getMontSoldCcpt() - operationMoyPay.getMontDinOmp());
					else
						operationMoyPay.setMontApreOmp(cptPret.getMontSoldCcpt() + operationMoyPay.getMontDinOmp());
				} else {

					mntDev = Long.valueOf(tmpMntDev.trim().substring(0, 10));
					operationMoyPay.setMontSoldCcpt(cptPret.getMontSoldCcpt());
					operationMoyPay.setMontSdevCcpt(cptPret.getMontSdevCcpt());
					operationMoyPay.setMontDinOmp(mnt);
					operationMoyPay.setMontDevOmp(mntDev);
					if (operationMoyPay.getCodSensOmp().equals(Constants.COD_SENS_DB)) {
						operationMoyPay.setMontApreOmp(cptPret.getMontSoldCcpt() - operationMoyPay.getMontDinOmp());
						operationMoyPay.setMontDevApreOmp(cptPret.getMontSdevCcpt() - mntDev);
					} else {
						operationMoyPay.setMontApreOmp(cptPret.getMontSoldCcpt() + operationMoyPay.getMontDinOmp());
						operationMoyPay.setMontDevApreOmp(mntDev + cptPret.getMontSdevCcpt());
					}

				}
				operationMoyPay.setCodDemOmp("T"); // /*** type demandeur (Titulaire,CoTitul,Mandataire)
				operationMoyPay.setLibMotfOmp(ligne.trim().substring(44));
				boolean isSalair = false;
				if (Integer.parseInt(ligne.trim().substring(23, 25)) == 36 && ligne.trim().contains("PAIE MENSUELL")) {
					depPerso(operationMoyPay,
							ligne.trim().substring(ligne.indexOf("PAIE MENSUELL") + 13,
									ligne.indexOf("PAIE MENSUELL") + 15),
							ligne.trim().substring(ligne.indexOf("PAIE MENSUELL") + 15,
									ligne.indexOf("PAIE MENSUELL") + 19));
					isSalair = true;
				}
				if (((!cptPret.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR))
						&& operationMoyPay.getCodSensOmp().equals(Constants.COD_SENS_DB)
						&& cptPret.getMontSdevCcpt() < operationMoyPay.getMontDevOmp())
						|| ((Arrays.asList(Constants.listCompteEnDinarsConvertibles)
								.contains((Integer) (contratCptId.getCodPrdPrd().intValue())))
								&& operationMoyPay.getCodSensOmp().equals(Constants.COD_SENS_DB)
								&& cptPret.getMontSoldCcpt() < operationMoyPay.getMontDinOmp())) {
					String sens = "C";
					if (ligne.trim().substring(43, 44).equals("-"))
						sens = Constants.COD_SENS_CR;
					else
						sens = Constants.COD_SENS_DB;
					String etat = "Manque de provision";
					etatOp = "Manque de provision";

				} else {

					InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt();
					insertOperationMoyPayTrt.setVerifDomaine(false);
					operationMoyPay = (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPay);

					if (!isSalair) {
						UtilCtr.updateSoldeDevDin(operationMoyPay.getContratCpt(), operationMoyPay.getCodSensOmp(), mnt,
								mntDev);
					}
					etatOp = "La réaffectation a été effectuée avec succès";
					crudService.update(batchRejetVirNSI);
					virementVo.setEtatEnregistrement(true);

				}
			} else {
				String sens = "C";
				if (ligne.trim().substring(43, 44).equals("-"))
					sens = Constants.COD_SENS_CR;
				else
					sens = Constants.COD_SENS_DB;
				String etat = cptPret.getCodEtatCcpt();

				GetContratEtatCmd getContratEtatCmd = new GetContratEtatCmd();
				ValueObject retObj = (ValueObject) getContratEtatCmd.execute(cptPret.getContratCptId());

				if (!vo.hasError()) {
					ContratCptMandat contratCptMandat = (ContratCptMandat) retObj;
					etat = " " + contratCptMandat.getMessageEtat();
					etatOp = " " + contratCptMandat.getMessageEtat();
				}

			}

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans ReaffectationRejetNSITrt : ");
			text.append(e.getMessage());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("ReaffectationRejetNSITrt");
			logger.error("Exception : ", e);
			virementVo.addError(erreur);
			virementVo.setEtatEnregistrement(false);
			// /*** gerer une exception
			throw new RuntimeException(e);
		}
		virementVo.setMessageValidation(etatOp);
		return virementVo;
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public String getLib(String libOp, String libFichier) {
		String res = "";
		if (libFichier != null && libFichier.length() >= 15) {
			res = libOp.substring(0, Math.min(13, libOp.length())) + ". " + libFichier;
		} else if (libFichier != null) {
			if (libOp.length() <= (13 + (15 - libFichier.length())))
				res = libOp.substring(0, Math.min(13 + (15 - libFichier.length()), libOp.length())) + " " + libFichier;
			else
				res = libOp.substring(0, Math.min(13 + (15 - libFichier.length()), libOp.length())) + ". " + libFichier;
		} else
			res = libOp;

		return res.substring(0, Math.min(30, res.length()));
	}

	public Date getDateEch(String month, String year) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date convertedDate = dateFormat.parse(month + "/01/" + year);
		Calendar c = Calendar.getInstance();
		c.setTime(convertedDate);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	public void depPerso(OperationMoyPay operationMoyPay, String month, String year) throws Exception {
		DepPersonnel depPersonnel = new DepPersonnel();
		String codeAgence = operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc().toString();
		// creer crédit : 000 + 00 + 00000
		// CodStruct + annee + num_seq

		List lmaxNum = guichetDao.getMaxNumCreditCG(codeAgence);
		String numDem = "0000000";
		for (Iterator it = lmaxNum.iterator(); it.hasNext();) {
			ListOrderedMap ob = (ListOrderedMap) it.next();
			for (int i1 = 0; i1 < ob.size(); i1++) {
				if (ob.getValue(i1) != null)
					numDem = ob.getValue(i1).toString();
				i1 = i1 + 1;
			}
		} // / Num Seq
		String DateString = DateHandler.dateToStr(new Date());
		String anneeEncours = DateString.substring(8, 10);
		String annee = numDem.substring(0, 2);
		if (anneeEncours.equals(annee)) {
			long IdDemandeLong = new Long(numDem);
			IdDemandeLong = IdDemandeLong + 1;
			numDem = "" + IdDemandeLong;
			for (int j = numDem.length() + 1; j <= 7; j = j + 1)
				numDem = "0" + numDem;
			numDem = codeAgence + numDem;
		} else {
			numDem = codeAgence + anneeEncours + "00001";
		}
		depPersonnel.setNumCredCredeps(new Long(numDem));
		depPersonnel.setMontCredCredeps(operationMoyPay.getMontDinOmp());
		depPersonnel.setMontAutCredeps(operationMoyPay.getMontDinOmp());
		depPersonnel.setDatEchCredeps(getDateEch(month, year));
		depPersonnel.setDatRealCredeps(new Date());
		depPersonnel.setCodPrdPrd(Constants.COD_PRD_FACIL_CAISSE);
		depPersonnel.setCodDetatCredps("6");
		String cpt = StrHandler.lpad("" + operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc(), '0', 3)
				+ StrHandler.lpad("" + operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd(), '0', 4)
				+ StrHandler.lpad("" + operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt(), '0', 6);
		depPersonnel.setNumCptCredeps(cpt);
		depPersonnel.setCodStrcStrc(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc());
		depPersonnel.setNumSeqPers(operationMoyPay.getContratCpt().getClient().getPersonne().getNumSeqPers());

		// Long da = d.getMontDaDetcg(); //000
		// Long ga = d.getMontGarDetcg(); //000
		CURService crudService = (CURService) context.getBean("CURService");
		crudService.create(depPersonnel);

		// pb il faut modifier le sold avant la date echance sionn la modification ne sera pas executé
		UtilCtr.updateSoldeDevDin(operationMoyPay.getContratCpt(), operationMoyPay.getCodSensOmp(),
				operationMoyPay.getMontDinOmp(), null);

		ContratCpt contratCpt = (ContratCpt) searchEngine.loadForUpdate(ContratCpt.class,
				operationMoyPay.getContratCpt().getContratCptId());
		contratCpt.setDatEautCcpt(depPersonnel.getDatEchCredeps());
		contratCpt.setMontAutCcpt(operationMoyPay.getMontDinOmp());
		crudService.update(contratCpt);

	}

	public static int getQuantieme(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(GregorianCalendar.DAY_OF_YEAR);
	}

	public String getLibOp(int codOper) {

		switch (codOper) {
		case 1:
			return "ACHAT DEVISE";
		case 2:
			return "ACHATS TITRES";
		case 3:
			return "ANNULATION";
		case 4:
			return "CHEQUE CERTIFIE";
		case 5:
			return "RETRAIT CHEQUE";
		case 6:
			return "CHQ.COMP.REJETE";
		case 7:
			return "COMMISSIONS";
		case 8:
			return "CREDIT DOCUMENTAIRE";
		case 9:
			return "EFFET RECLAME";
		case 10:
			return "ENCAISS. EFFET";
		case 11:
			return "ENCAISSEMENT COUPONS";
		case 12:
			return "ENCAISSEMENT MANDAT";
		case 13:
			return "FRAIS DIVERS";
		case 14:
			return "GARDE DE TITRES";
		case 15:
			return "INTERETS CREDITEURS";
		case 16:
			return "INTERETS DEBITEURS";
		case 17:
			return "IMPAYE EFFET";
		case 18:
			return "LOCATION COFFRE";
		case 19:
			return "NET ESCOMPTE";
		case 20:
			return "REGLEMENT DIVIDENDE";
		case 21:
			return "REGLEMENT EFFET DIRECT";
		case 22:
			return "REGLEMENT EFFET DOMICILIE";
		case 23:
			return "REGLEMENT FACTURE";
		case 24:
			return "REGULARISATION";
		case 25:
			return "REMISE CHEQUES";
		case 26:
			return "RETENUE DEBLOQUEE";
		case 27:
			return "RETENUES DIVERSES";
		case 28:
			return "RETENUES POUR PROVISION";
		case 29:
			return "RISTOURNE";
		case 30:
			return "VENTE DEVISES";
		case 31:
			return "VENTE TITRES";
		case 32:
			return "VERSEMENT ESPECES";
		case 33:
			return "VIREMENT";
		case 34:
			return "VIREMENT TELECOMPENSE";
		case 35:
			return "AMORTISSEMENT EFFET DIRECT";
		case 36:
			return "VIREMENT SALAIRE";
		case 37:
			return "ESCOMPTE COMMERCIAL";
		case 38:
			return "PREAVIS DE REJET";
		case 39:
			return "TVA  SUR COMMISSIONS";
		case 40:
			return "EFFETS ENCAISSEMENT";
		case 41:
			return "PAIEMENT DEPLACE";
		case 42:
			return "VERSEMENT DEPLACE";
		case 43:
			return "VIREMENT DEPLACE";
		case 44:
			return "CREDIT FAPS OU AV/PRIMES";
		case 45:
			return "IMPAYES REMISES BANQUES";
		case 46:
			return "ENVOIS DE FONDS EN DINARS";
		case 47:
			return "ASSURANCE MALADIE";
		case 48:
			return "IMPAYE ENCAISSEMENT";
		case 49:
			return "VIREMENT A CONTENTIEUX";
		case 50:
			return "EFFETS ESCOMPTE";
		case 51:
			return "EFFET SUR CREANCES ADMINISTRAT";
		case 52:
			return "EFFETS SUR CREDITS SAISONNIERS";
		case 53:
			return "EFFETS M T A RENOUVELER";
		case 54:
			return "AUTRES EFFETS DE MOBILISATION";
		case 55:
			return "FINANCEMENT DE STOCK";
		case 56:
			return "CREDIT REFINANCABLE SUR ENG";
		case 57:
			return "CREDIT DE COMPAGNE";
		case 58:
			return "CREDITS RELAIS OU REVOLVI";
		case 59:
			return "CREDIT DIRECT";
		case 60:
			return "EFFETS SUR CREANCES ADMINISTRA";
		case 61:
			return "EFFETS SUR CREDITS SAISONNIERS";
		case 62:
			return "EFFETS A MOYEN TERME";
		case 63:
			return "AUTRES EFFETS DE MOBILSATION";
		case 64:
			return "IMPAY.ESC.EFFET";
		case 65:
			return "EFFETS RECUS DE COMPENSATION";
		case 66:
			return "EFFET COMPENS.REJETE";
		case 67:
			return "CHEQUES RECUS DE COMPENSATION";
		case 68:
			return "CHQ.COMP.REJETE";
		case 69:
			return "CHEQUES A COMPENSER";
		case 70:
			return "CHEQUES A COMPENSER REJETES";
		case 71:
			return "PORTEFEUILLE L T BNA";
		case 72:
			return "PENALITE CHQ SANS POVISION";
		case 73:
			return "ENVOIS DE DEVISES";
		case 74:
			return "ESCOMPTE CHEQUES";
		case 75:
			return "PREAVIS DE REJET DE CHEQUE";
		case 76:
			return "PLACEMENT";
		case 77:
			return "CHANGE MANUEL";
		case 78:
			return "CREDIT L.T CONSOLIDATION";
		case 79:
			return "RETRAIT GUICHET";
		case 80:
			return "FRAIS GESTION COMPTE";
		case 81:
			return "RECUP.F/TELECOMP.";
		case 82:
			return "COMMERCE ELECTRONIQUE";
		case 83:
			return "SOUSCRIPTION DE PLACEMENT";
		case 84:
			return "LIQUIDATION DE PLACEMENT";
		case 85:
			return "RACHAT PLAC. AVANT ECH.";
		case 86:
			return "AVANCE SUR PLACEMENT";
		case 87:
			return "TRANSFERT RECU DE L'TRANGER";
		case 88:
			return "RETRAIT DNT/COMPTE DEVISE";
		case 89:
			return "VIR. CPT.DEVISE A CPT.DNT";
		case 90:
			return "REGLEMENT REMISE DE L'ETRANGER";
		case 91:
			return "COMMISSION DOMICILIATION";
		case 92:
			return "COMMISSION  A V A";
		case 93:
			return "ORDRE DE PAIEMENT FINANCIER";
		case 94:
			return "VIREMENT PORTEFEUILLE EXPORT";
		case 95:
			return "REGEL. DOSSIER SCOLARITE";
		case 96:
			return "ORDRE DE PAIEMENT COMMERCIAL";
		case 97:
			return "TRANSF.RECU BANQUE PLACE";
		case 98:
			return "VIREMENT MONETIQUE";
		case 99:
			return "COMMERCE ELECTRONIQUE";

		default:
			return "";

		}
	}
}
