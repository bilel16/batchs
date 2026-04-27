package com.bna.smile.model.domainecommun.traitement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.NomencElemtCondition;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.service.CommunService;
import com.bna.commun.traitements.InsertionOperationMoyPaySansCROTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmilePlacementException;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ParamSms;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertCroBNASmsTrt extends Traitement {

	OperationMoyPay operationMoyPay = new OperationMoyPay();
	boolean exo_tva = false;
	public InsertCroBNASmsTrt() {
	}

	/**
	 * methode qui insère dans la table des CRO, les facturations collectées à partir de la tbale SMSFacturation...
	 * 
	 * @param vo
	 *            : ParamSMS
	 * @return : PARAMSMS
	 * @autor : EL ARBI HASSINE
	 * @date : 25/07/2010
	 */

	public IValueObject perform(IValueObject vo) throws SmilePlacementException {
		Context context = ContextHandler.getContext();
		// ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");

		ParamSms paramSms = (ParamSms) vo;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			logger.info("debut de traitement");
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
			String dateTraietement = formater.format(new Date());
			Date dateComptable = new Date();
			if (CalanderHandler.isJourFerier(dateComptable)) {
				dateComptable = CalanderHandler.GetNextWorkingDay(dateComptable);
			}
			// *******************Contrat_cpt *****************//

			String compte = paramSms.getNumCompte();
			ContratCpt contratCpt = new ContratCpt();
			ContratCptId contratCptId = new ContratCptId();
			contratCptId.setCodStrcStrc(Long.valueOf(compte.substring(0, 3)));
			contratCptId.setCodPrdPrd(Long.valueOf(compte.substring(3, 7)));
			contratCptId.setNumCcptCcpt(Long.valueOf(compte.substring(7, 13)));
			contratCpt.setContratCptId(contratCptId);

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");

			contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, contratCptId);

			if (contratCpt != null && contratCpt.getClient() != null
					&& contratCpt.getClient().getNumSeqPers() != null) {

				logger.info(dateTraietement);
				String requeteSms = "UPDATE   BNA_BILLING@NEWSMSDB.BNA.TN  set  ETAT = 'T', date_traitement = to_date('"
						+ dateTraietement + "')" + " where ID = " + paramSms.getID();

				logger.info(requeteSms);
				stmt = paramSms.getCon().createStatement();
				stmt.executeUpdate(requeteSms);

				// *********** AJOUTER OPERATION-MOY-PAY *****************//

				String requeteExoneration =
						"select count(*) from EXONERATION_CLT_TVA where num_seq_cli in (select num_seq_pers from "
								+ " personne where num_pce_pers = '" + paramSms.getCin() + "')"
								+ " and COD_ETAT_ETVA = 'V' and  DAT_FIN_ETVA >= to_date('"
								+ DateHandler.dateToStr(this.getDateOperation()) + "')";

				logger.info(requeteExoneration);

				stmt = paramSms.getCon().createStatement();
				rs = stmt.executeQuery(requeteExoneration);

				
				while (rs.next()) {
					if ((Long.valueOf(rs.getInt(1)).equals(Long.valueOf(0)))) {

						exo_tva = false;
					} else
						exo_tva = true;
				}

				// / ------------------Operation Moyen de Paiement ---------------------------- ///

				// 00. setting obj operationMoyPay
				operationMoyPay.setLibObjOpOmp("SMILE.ABONN.SMS");

				// 01. setting personnel initiateur et valideur
				Personnel personnelInit = new Personnel();
				personnelInit.setNumMatrUser("9999");
				operationMoyPay.setPersonnelInitiateur(personnelInit);
				operationMoyPay.setPersonnelValideur(personnelInit);

				// 02. setting structure initiatrice
				Structure structureInit = new Structure();
				structureInit.setCodStrcStrc(Long.valueOf(paramSms.getNumCompte().substring(0, 3)));
				operationMoyPay.setStructureInitiatrice(structureInit);

				// 03. setting structure receptrice
				Structure structureRecep = new Structure();
				structureRecep.setCodStrcStrc(Long.valueOf(paramSms.getNumCompte().substring(0, 3)));
				operationMoyPay.setStructureReceptrice(structureRecep);

				// 05. getting montant a retirer

				// 07. setting devise et montant
				Devise devise = new Devise();

				// 07.2 Virement en dinar
				devise.setCodDevDev(Constants.COD_DEV_DINAR);
				Long mntComTva = 0L;
				Long mntTva = 0L;
				Long mntCom = 0L;

				if (paramSms.getCodeOperation().equals("663")) {
					mntCom = paramSms.getMontant();
					mntComTva = paramSms.getMontant();
					if (!exo_tva) {
						mntComTva += (paramSms.getMontant() * paramSms.getTax() / 100);
						mntTva = (paramSms.getMontant() * paramSms.getTax() / 100);
					} else {
						mntComTva += 0L;
						mntTva = 0L;
					}
				} else if (paramSms.getCodeOperation().equals("664")) {

					mntCom = Long.valueOf(Math.round(paramSms.getMontant() / Double.valueOf(1.19)));
					mntComTva = Long.valueOf(paramSms.getMontant());
					Long t = paramSms.getMontant() - mntCom;
					mntTva = Long.valueOf(Math.round(t));

				}

				if (contratCpt != null) {
					operationMoyPay.setMontDinOmp(mntCom);
					operationMoyPay.setMontApreOmp(contratCpt.getMontSoldCcpt() - mntComTva);
				}

				operationMoyPay.setMontSoldCcpt(contratCpt.getMontSoldCcpt());
				operationMoyPay.setDevise(devise);

				// 08. setting contrat compte
				operationMoyPay.setContratCpt(contratCpt);

				// 09. setting type demandeur (Titulaire, CoTitulaire, Mandataire)

				operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

				// 10.1 insertion du Donneur D'ordre

				TypePiece typePieceDemandeur = new TypePiece();
				typePieceDemandeur.setCodTpceTpce(
						Long.valueOf(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce()));
				operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
				operationMoyPay.setNumPcedOmp(contratCpt.getClient().getPersonne().getNumPcePers());
				operationMoyPay.setNomNomdOmp(contratCpt.getClient().getPersonne().getNomNomPers());
				operationMoyPay.setNomPrndOmp(contratCpt.getClient().getPersonne().getNomPrnPers());

				// 11 Preparing data of Operation and tache
				Operation oper = new Operation();
				oper.setCodOperOper(Long.valueOf(paramSms.getCodeOperation()));
				Tache tache = new Tache();
				tache.setOperation(oper);
				TacheId tacheId = new TacheId();
				tacheId.setCodOperOper(Long.valueOf(paramSms.getCodeOperation()));
				tacheId.setCodTachTach(1L);
				tache.setTacheId(tacheId);
				operationMoyPay.setTache(tache);

				// 12. setting date operation moyen paiement
				operationMoyPay.setDatOperOmp(dateComptable);

				// 13. setting date valeur moyen paiement
				Date dateValOmp = null;
				if (paramSms.getDateValeur() != null && paramSms.getDateValeur().length() > 0) {
					dateValOmp = DateHandler.strToDate(paramSms.getDateValeur());

					if (CalanderHandler.isJourFerier(dateValOmp) == true) {
						dateValOmp = CalanderHandler.GetNextWorkingDay(dateValOmp);
					}

				} else {
					dateValOmp = dateComptable;
				}
				operationMoyPay.setDatValOmp(dateValOmp);

				// 14.1 setting date system moyen paiement
				Date dateSysOmp = new Date();
				operationMoyPay.setDatSystOmp(dateSysOmp);

				// 14.2 setting date valeur Commission moyen paiement

				operationMoyPay.setDateValeurCommission(dateValOmp);

				// 15. setting sens operation
				operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);

				// 16. setting montant tva
				operationMoyPay.setMontTvaOmp(Long.valueOf(mntTva));

				// 17. Insertion dans la table DETAIL_OPERATION_MOY_PAIEMENT

				Set<DetailOperMoyPaiement> setDetOpm = new HashSet<DetailOperMoyPaiement>();
				NomencElemtCondition nomencElemtCondition = new NomencElemtCondition();
				DetailOperMoyPaiement detailOmpCommission = new DetailOperMoyPaiement();

				if (mntCom != 0) {

					nomencElemtCondition.setCodNecdNecd(89 + "");
					detailOmpCommission.setNomencElemtCondition(nomencElemtCondition);
					detailOmpCommission.setCodTypDomp(Constants.COD_TYPE_COMMISSION);
					detailOmpCommission.setMontValDomp(Long.valueOf(mntCom));
					detailOmpCommission.setDatValDomp(DateHandler.strToDate(paramSms.getDateValeur()));
					detailOmpCommission.setOperationMoyPay(operationMoyPay);
					setDetOpm.add(detailOmpCommission);

					// operationMoyPay.setDetailOperMoyPaiements(setDetOpm);

				}

				// 19. Insertion code ref client
				// champ obligtaoire dans la BD, valeur par defaut proposee par Chiraz CHELLY 999

				operationMoyPay.setCodRefcOmp(paramSms.getNumAbonnement() + "");

				// 20. Insertion motif operation
				// valeur proposee par Chiraz CHELLY libelle operation
				operationMoyPay.setLibMotfOmp("SMILE.ABONN.SMS");
				operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
				// 02. insertion operation moyen paiement

				InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
						new InsertionOperationMoyPaySansCROTrt();
				operationMoyPay = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPay);

				logger.info("operationMoyPay :" + operationMoyPay);

				this.setCroFlag(true);

				// *************** Update Compte ***********//

				long montantAjouterAuSolde = mntComTva;

				CommunService communService = (CommunService) context.getBean("communService");
				ContratCptSold contratCptSold = new ContratCptSold();
				contratCptSold.setContratCpt(contratCpt);
				contratCptSold.setSens(Constants.COD_SENS_DB);
				contratCptSold.setSolde(montantAjouterAuSolde);
				contratCpt = (ContratCpt) communService.UpdateSoldOperationMoyPay(contratCptSold);
			}
			return (paramSms);
		}

		catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur InsertCroBNASmsTrt ");
			text.append(e.toString());
			erreur.setCode("400");
			erreur.setDescription(text.toString());
			erreur.setKey("GenererAbonnementTrt");
			paramSms.addError(erreur);
			logger.error(" *** Erreur lors de ReajusterAbonnementTrt"
					/*
					 * concernant l'agence "+avancRembLiquid.get().getCodStrcMand()
					 */ + " : ", e);
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
			}

		}
	}

	public void genCroText(ValueObject vo) {
		ParamSms paramSms = (ParamSms) vo;
		/* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */
		ResultSet rs = null;
		Statement stmt = null;
		String vcodStrcStrc = "";
		Date dateComptable = new Date();
		Date dateValCRO = null;
		try {

			if (CalanderHandler.isJourFerier(dateComptable)) {
				dateComptable = CalanderHandler.GetNextWorkingDay(dateComptable);
			}

			Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			com.oxia.security.abc.model.Personnel user = null;
			if (obj instanceof UserDetails) {
				user = (com.oxia.security.abc.model.Personnel) obj;
			}
			if (operationMoyPay != null && operationMoyPay.getNumOperOmp() != null) {
				this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
			} else {
				this.setNumRefCro(Long.valueOf(paramSms.getNumAbonnement()));
			}
			this.setLibRefCro("SMILE.ABONN.SMS");

			if (paramSms.getDateValeur() != null && paramSms.getDateValeur().length() > 0) {
				dateValCRO = DateHandler.strToDate(paramSms.getDateValeur());

				if (CalanderHandler.isJourFerier(dateValCRO) == true) {
					dateValCRO = CalanderHandler.GetNextWorkingDay(dateValCRO);
				}

			} else {
				dateValCRO = dateComptable;
			}
			this.setDatValCro(dateValCRO);
			this.setDatValCom(dateValCRO);
			vcodStrcStrc = paramSms.getNumCompte().substring(0, 3);

			this.setCodeStructInitiatrice(vcodStrcStrc.toString());
			this.setCodStrcImpt(Long.valueOf(paramSms.getNumCompte().substring(0, 3)));
			this.setCodEtatCro(0);
			this.setCodeProduit(paramSms.getCodProduit().toString());

			this.setOperationId(paramSms.getCodeOperation().toString());

			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
			// String dateSms = formater.format(new Date());

			// this.setDateOperation(DateHandler.strToDate(dateSms));
			this.setDateOperation(dateComptable);
			// SimpleDateFormat formater1=new SimpleDateFormat("dd/MM/yyyy");
			formater = new SimpleDateFormat("HH:mm:ss");
			String heureString = formater.format(new Date());
			this.setHeureOperation(heureString);
			this.setTypeOperationCro("O");
			this.setCodTachTach(1);

			this.setCodRefcOmp(paramSms.getNumAbonnement().toString());
			this.setDatExecCro(new Date());

			this.setNumCinUser(user.getNumMatrUser());
			this.setCodTypUser(user.getMatriculeTyp());

			/* ------------------Garniture de la partie VARIABLE du CRO---------------------------------- */

			// String requeteSms =
			// "select count(*) from EXONERATION_CLT_TVA where num_seq_cli in (select num_seq_pers from "
			// + " personne where num_pce_pers = '" + paramSms.getCin() + "')"
			// + " and COD_ETAT_ETVA = 'V' and DAT_FIN_ETVA >= to_date('"
			// + DateHandler.dateToStr(this.getDateOperation()) + "')";
			//
			// System.out.println(requeteSms);
			//
			// stmt = paramSms.getCon().createStatement();
			// rs = stmt.executeQuery(requeteSms);

//			boolean exo_tva = false;
//			while (rs.next()) {
//				if ((Long.valueOf(rs.getInt(1)).equals(Long.valueOf(0)))) {
//
//					exo_tva = false;
//				} else
//					exo_tva = true;
//			}

			StringBuffer cro = new StringBuffer("");

			cro.append("SMS.numCptBna=");
			cro.append(paramSms.getNumCompte() + ";");

			cro.append("SMS.NUM_SEQ_ABONN=");
			cro.append(paramSms.getNumAbonnement() + ";");

			if (paramSms.getCodeOperation().equals("663")) {
				cro.append("SMS.MONTANT_ABONN=");
				cro.append(paramSms.getMontant() + ";");
				cro.append("SMS.TVA_ABONN=");
				if (!exo_tva)
					cro.append((paramSms.getMontant() * paramSms.getTax() / 100) + ";");
				else
					cro.append(0 + ";");

			} else if (paramSms.getCodeOperation().equals("664")) {
				cro.append("SMS.MONTANT_COMM=");
				cro.append(Math.round(paramSms.getMontant() / Double.valueOf(1.19)) + ";");
				Long w = Long.valueOf(Math.round(paramSms.getMontant() / Double.valueOf(1.19)));
				Long z = paramSms.getMontant();
				Long t = z - w;
				cro.append("SMS.TVA_COMM=");
				cro.append(Math.round(t) + ";");

			}

			this.setCroText(cro.toString());

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur InsertCroBNASmsTrt ");
			text.append(e.toString());
			erreur.setCode("400");
			erreur.setDescription(text.toString());
			erreur.setKey("GenererAbonnementTrt");
			paramSms.addError(erreur);
			logger.error(" *** Erreur lors de ReajusterAbonnementTrt"
					/*
					 * concernant l'agence "+avancRembLiquid.get().getCodStrcMand()
					 */ + " : ", e);
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

}
