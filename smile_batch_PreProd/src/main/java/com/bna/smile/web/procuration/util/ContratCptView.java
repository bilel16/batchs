package com.bna.smile.web.procuration.util;

import java.util.Date;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

public class ContratCptView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cleContrat;
	private String dateContrat;
	private String codeAgence;
	private String codeProduit;
	private String numeroCompte;
	private ContratCpt contratCpt = new ContratCpt();
	private String cleCpt;
	private String solde;
	private String sens;
	private String relation;
	private String numTelephone;
	private Long montantSolde;
	private Long montantAutorisation;
	private Date dateAutorisation;
	private String etatCompte;
	private String montantAutoFormatted;
	private String compte13;
	private Long codePrdPack;
	private String periodPrelevement;
	/*****************************************/
	public ContratCptView() {

	}

	public void setCleContrat(String cleContrat) {
		this.cleContrat = cleContrat;
	}

	public String getCleContrat() {
		return cleContrat;
	}

	public void setContratCpt(ContratCpt contratCpt) {
		this.contratCpt = contratCpt;
	}

	public ContratCpt getContratCpt() {
		return contratCpt;
	}

	public void setDateContrat(String dateContrat) {
		this.dateContrat = dateContrat;
	}

	public String getDateContrat() {
		return dateContrat;
	}

	public void setCodeAgence(String codeAgence) {
		this.codeAgence = codeAgence;
	}

	public String getCodeAgence() {
		return codeAgence;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}

	public String getCodeProduit() {
		return codeProduit;
	}

	public void setNumeroCompte(String numeroCompte) {
		this.numeroCompte = numeroCompte;
	}

	public String getNumeroCompte() {
		return numeroCompte;
	}

	public void setCleCpt(String cleCpt) {
		this.cleCpt = cleCpt;
	}

	public String getCleCpt() {
		String vCle = "";
		if (contratCpt.getContratCptId() != null) {
			vCle = Constants.determinerCle(
					StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), '0', 3),
					StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), '0', 4),
					StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), '0', 6));
		}
		return vCle;
	}

	public void setSolde(String solde) {
		this.solde = solde;
	}

	public String getSolde() {
		return solde;
	}

	public void setSens(String sens) {
		this.sens = sens;
	}

	public String getSens() {
		return sens;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getNumTelephone() {
		return numTelephone;
	}

	public void setNumTelephone(String numTelephone) {
		this.numTelephone = numTelephone;
	}

	public Long getMontantSolde() {
		return montantSolde;
	}

	public void setMontantSolde(Long montantSolde) {
		this.montantSolde = montantSolde;
	}

	public Long getMontantAutorisation() {
		return montantAutorisation;
	}

	public void setMontantAutorisation(Long montantAutorisation) {
		this.montantAutorisation = montantAutorisation;
	}

	public Date getDateAutorisation() {
		return dateAutorisation;
	}

	public void setDateAutorisation(Date dateAutorisation) {
		this.dateAutorisation = dateAutorisation;
	}

	public String getEtatCompte() {
		return etatCompte;
	}

	public void setEtatCompte(String etatCompte) {
		this.etatCompte = etatCompte;
	}

	public String getMontantAutoFormatted() {
		return montantAutoFormatted;
	}

	public void setMontantAutoFormatted(String montantAutoFormatted) {
		this.montantAutoFormatted = montantAutoFormatted;
	}

	public String getCompte13() {
		return compte13;
	}

	public void setCompte13(String compte13) {
		this.compte13 = compte13;
	}

	public Long getCodePrdPack() {
		return codePrdPack;
	}

	public void setCodePrdPack(Long codePrdPack) {
		this.codePrdPack = codePrdPack;
	}

	public String getPeriodPrelevement() {
		return periodPrelevement;
	}

	public void setPeriodPrelevement(String periodPrelevement) {
		this.periodPrelevement = periodPrelevement;
	}
}
