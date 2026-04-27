package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Produit;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementEffetVo;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class Cro834RejetEffetAutoTrt extends Traitement {

	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");

	public Cro834RejetEffetAutoTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		ReglementEffetVo reglementEffetVo = (ReglementEffetVo) vo;

		ContratCpt contratCptTireur = reglementEffetVo.getContratCpt();
		// TODO : check this : if contrat is null, (39), why we generate cro ?

		this.setCroFlag(true);
		try {

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans Cro834RejetEffetAutoTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("Cro834RejetEffetAutoTrt");
			reglementEffetVo.addError(erreur);
			logger.error("Erreur au niveau Cro834RejetEffetAutoTrt  : ", e);
			throw new RuntimeException(e);

		}

		return (reglementEffetVo);

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		try {
			ReglementEffetVo remiseEffetVo = (ReglementEffetVo) vo;
			ContratCpt contratCptTire = remiseEffetVo.getContratCpt();

			Operation operation = new Operation();

			operation.setCodOperOper(Constants.COD_OPERATION_REJET_LCR);
			Produit produit = new Produit();
			produit.setCodPrdPrd(Constants.COD_PRODUIT_EFFET);

			/*
			 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
			 */

			this.setNumRefCro(Long.valueOf(9999));
			this.setLibRefCro("REJET AUTO LCR");
			this.setDatExecCro(new Date());
			this.setCodEtatCro(0);
			this.setCodeProduit(produit.getCodPrdPrd().toString());
			this.setOperationId(operation.getCodOperOper().toString());
			this.setDateOperation(remiseEffetVo.getDateComptable());
			this.setDatValCro(remiseEffetVo.getDateComptable());
			this.setDatValCom(null);
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
			formater = new SimpleDateFormat("HH:mm:ss");
			String heureString = formater.format(new Date());
			this.setHeureOperation(heureString);
			this.setTypeOperationCro("O");
			this.setCodTachTach(Constants.COD_TACHE_EFFET);
			this.setCodRefcOmp(remiseEffetVo.getEffetRecu().getEffetId().getNumEff());
			this.setNumCinUser("99999");
			this.setCodTypUser("M");
			StringBuffer cro = new StringBuffer("");
			if (!remiseEffetVo.getEffetRecu().getCodRej1().equals(Constants.COD_MREJ_EFF_VAL_MAL_ACHEMINEE)) {
				this.setCodeStructInitiatrice(contratCptTire.getContratCptId().getCodStrcStrc() + "");

				this.setCodStrcImpt(contratCptTire.getContratCptId().getCodStrcStrc());

				/*
				 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
				 */

				// *** Montant effet ****//
				cro.append("MNT_EFF_EFF=");
				Long mntEff = remiseEffetVo.getEffetRecu().getMntEff();
				cro.append(mntEff + ";");
				// *** Numero effet ****//
				cro.append("NUM_EFF_EFF=");
				cro.append(remiseEffetVo.getEffetRecu().getEffetId().getNumEff() + ";");

				// *** Numéro du compte client ****//
				cro.append("numCptBna=");
				cro.append(StrHandler.lpad(contratCptTire.getContratCptId().getCodStrcStrc().toString(), '0', 3));
				// cro.append("COD_PRD_PRD=");
				cro.append(StrHandler.lpad(contratCptTire.getContratCptId().getCodPrdPrd().toString(), '0', 4));
				// cro.append("NUM_CCPT_CCPT=");
				cro.append(StrHandler.lpad(contratCptTire.getContratCptId().getNumCcptCcpt().toString(), '0', 6) + ";");

				// *** statut client taxable ****//
				cro.append("COD_TVA_CLT=");

				cro.append(Long.valueOf(1) + ";");

				// *** ETAT CPT ****//
				cro.append("ETAT_CPT=");
				cro.append(0 + ";");
				if (!contratCptTire.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
					// *** Montant Commission ****//
					cro.append("4" + "=");
					cro.append(0 + ";");

					// *** Montant Tva Commission ****//
					cro.append("MNT_TVA_EFF=");
					cro.append(0 + ";");

					// *** Montant Tva +Commission ****//
					cro.append("MONT_COM_TVA_EFF_827=");
					cro.append(0 + ";");
					cro.append("TYPE_CPT=");
					cro.append(3 + ";");
					// ***MNT EFF DEVISE****//

					Long mntDev =
							UtilCtr.changeTNDToDevise(mntEff, contratCptTire.getDevise().getNbrDecDev(), contratCptTire
									.getDevise().getNbrUnitDev(), UtilCtr.getCoursAchatBna(""
									+ contratCptTire.getDevise()));
					cro.append("MNT_DEV_EFF=");
					cro.append(mntDev.longValue() + ";");
					// ***MNT EFF DEVISE EN DINARS****//

					// ***cours fixe ****//
					cro.append("TAUX_FIX_BBE=");
					cro.append(UtilCtr.getCoursFixe(remiseEffetVo.getDateComptable(), contratCptTire.getDevise()
							.getCodDevDev())
							+ ";");
					// ***montant converti cours fixe ****//
					Long montantConverti =
							UtilCtr.changeDeviseToTND(mntDev, contratCptTire.getDevise().getNbrDecDev(), contratCptTire
									.getDevise().getNbrUnitDev(), UtilCtr.getCoursFixe(
									remiseEffetVo.getDateComptable(), contratCptTire.getDevise().getCodDevDev()));
					cro.append("MNT_CTR_CFIX=");
					cro.append(montantConverti + ";");
				} else {
					// *** Montant Commission ****//
					cro.append("4" + "=");
					cro.append(0 + ";");

					// *** Montant Tva Commission ****//
					cro.append("MNT_TVA_EFF=");
					cro.append(0 + ";");

					// *** Montant Tva +Commission ****//
					cro.append("MONT_COM_TVA_EFF_827=");
					cro.append(0 + ";");
					cro.append("TYPE_CPT=");
					cro.append(2 + ";");
				}
				// ***Code devise****//
				cro.append("COD_DEV_DEV=");
				cro.append(contratCptTire.getDevise().getCodDevDev() + ";");
			} else {
				// cro mal acheminé
				this.setCodeStructInitiatrice(remiseEffetVo.getStructure() + "");
				this.setCodStrcImpt(Long.valueOf(remiseEffetVo.getStructure()));

				/*
				 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
				 */

				// *** Montant effet ****//
				cro.append("MNT_EFF_EFF=");
				Long mntEff = remiseEffetVo.getEffetRecu().getMntEff();
				cro.append(mntEff + ";");
				// *** Numero effet ****//
				cro.append("NUM_EFF_EFF=");
				cro.append(remiseEffetVo.getEffetRecu().getEffetId().getNumEff() + ";");

				//si compte invalide strc_imp=structure cpt
				cro.append("numCptBna=");
				cro.append(""+Long.valueOf(remiseEffetVo.getStructure()));
				//cro.append(remiseEffetVo.getEffetRecu().getRibTir().substring(5, 8));
				// cro.append("COD_PRD_PRD=");
				cro.append(remiseEffetVo.getEffetRecu().getRibTir().substring(8, 12));
				// cro.append("NUM_CCPT_CCPT=");
				cro.append(remiseEffetVo.getEffetRecu().getRibTir().substring(12, 18) + ";");

				// *** statut client taxable ****//
				cro.append("COD_TVA_CLT=");

				cro.append(Long.valueOf(1) + ";");

				// *** ETAT CPT ****//
				cro.append("ETAT_CPT=");
				cro.append(0 + ";");

				// *** Montant Commission ****//
				cro.append("4" + "=");
				cro.append(0 + ";");

				// *** Montant Tva Commission ****//
				cro.append("MNT_TVA_EFF=");
				cro.append(0 + ";");

				// *** Montant Tva +Commission ****//
				cro.append("MONT_COM_TVA_EFF_827=");
				cro.append(0 + ";");
				cro.append("TYPE_CPT=");
				cro.append(2 + ";");

				// ***Code devise****//
				cro.append("COD_DEV_DEV=");
				cro.append("788;");

			}
			this.setCroText(cro.toString());
			logger.info("Cro generer avec succés");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}